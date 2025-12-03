package org.example.library.service;

import org.example.library.model.Author;
import org.example.library.model.Book;
import org.example.library.model.BorrowRecord;
import org.example.library.model.Invoice;
import org.example.library.model.InvoiceStatus;
import org.example.library.model.Library;
import org.example.library.model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibraryService {

    private final Library library;
    private long borrowRecordSequence = 1;
    private long invoiceSequence = 1;

    public LibraryService(Library library) {
        this.library = library;
    }

    // ---------- KİTAP İŞLEMLERİ ----------

    public void addBook(Book book) {
        Map<Long, Book> books = library.getBooksById();
        if (books.containsKey(book.getId())) {
            System.out.println("Bu ID ile zaten bir kitap var: " + book.getId());
            return;
        }
        books.put(book.getId(), book);
        System.out.println("Kitap eklendi: " + book);
    }

    public Book findBookById(long id) {
        return library.getBooksById().get(id);
    }

    public List<Book> findBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();

        for (Book book : library.getBooksById().values()) {
            if (book.getTitle() != null &&
                    book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findBooksByAuthorName(String authorName) {
        List<Book> result = new ArrayList<>();

        for (Book book : library.getBooksById().values()) {
            Author author = book.getAuthor();
            if (author != null &&
                    author.getName() != null &&
                    author.getName().toLowerCase().contains(authorName.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findBooksByCategory(String category) {
        List<Book> result = new ArrayList<>();

        for (Book book : library.getBooksById().values()) {
            if (book.getCategory() != null &&
                    book.getCategory().equalsIgnoreCase(category)) {
                result.add(book);
            }
        }
        return result;
    }

    public void updateBook(Book updatedBook) {
        Map<Long, Book> books = library.getBooksById();
        if (!books.containsKey(updatedBook.getId())) {
            System.out.println("Bu ID ile bir kitap bulunamadı: " + updatedBook.getId());
            return;
        }
        books.put(updatedBook.getId(), updatedBook);
        System.out.println("Kitap güncellendi: " + updatedBook);
    }

    public void deleteBook(long bookId) {
        Map<Long, Book> books = library.getBooksById();
        Book removed = books.remove(bookId);
        if (removed == null) {
            System.out.println("Silinecek kitap bulunamadı. ID: " + bookId);
        } else {
            System.out.println("Kitap silindi: " + removed);
        }
    }

    public void printBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("Liste boş.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void printAllBooks() {
        if (library.getBooksById().isEmpty()) {
            System.out.println("Kütüphanede hiç kitap yok.");
            return;
        }
        for (Book book : library.getBooksById().values()) {
            System.out.println(book);
        }
    }

    // ---------- ÜYE İŞLEMLERİ ----------

    public void addMember(Member member) {
        Map<Long, Member> members = library.getMembersById();
        if (members.containsKey(member.getId())) {
            System.out.println("Bu ID ile zaten bir üye var: " + member.getId());
            return;
        }
        members.put(member.getId(), member);
        System.out.println("Üye eklendi: " + member);
    }

    public Member findMemberById(long id) {
        return library.getMembersById().get(id);
    }

    // ---------- ÖDÜNÇ ALMA / İADE ----------

    public boolean borrowBook(long memberId, long bookId) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Üye bulunamadı. ID: " + memberId);
            return false;
        }

        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Kitap bulunamadı. ID: " + bookId);
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("Kitap şu anda başka bir üyede: " + book);
            return false;
        }

        if (!member.canBorrowMore()) {
            System.out.println("Üyenin kitap limiti dolu. Limit: " + member.getMaxBookLimit());
            return false;
        }

        book.setAvailable(false);
        member.borrowBook(book);

        BorrowRecord record = new BorrowRecord(
                borrowRecordSequence++,
                member,
                book,
                LocalDate.now()
        );
        library.getBorrowRecords().add(record);

        Invoice invoice = new Invoice(
                invoiceSequence++,
                member,
                book,
                book.getPrice(),
                LocalDate.now(),
                InvoiceStatus.PAID
        );
        library.getInvoices().add(invoice);

        System.out.println("Kitap başarıyla ödünç verildi.");
        System.out.println("BorrowRecord: " + record);
        System.out.println("Invoice: " + invoice);

        return true;
    }

    public boolean returnBook(long memberId, long bookId) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Üye bulunamadı. ID: " + memberId);
            return false;
        }

        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Kitap bulunamadı. ID: " + bookId);
            return false;
        }

        if (!member.getBorrowedBooks().contains(book)) {
            System.out.println("Bu kitap bu üyenin üzerinde görünmüyor.");
            return false;
        }

        member.returnBook(book);
        book.setAvailable(true);

        BorrowRecord activeRecord = null;
        for (BorrowRecord record : library.getBorrowRecords()) {
            if (record.getBook().equals(book)
                    && record.getMember().equals(member)
                    && !record.isReturned()) {
                activeRecord = record;
                break;
            }
        }

        if (activeRecord != null) {
            activeRecord.setReturned(true);
            activeRecord.setReturnDate(LocalDate.now());
        }

        Invoice refundInvoice = new Invoice(
                invoiceSequence++,
                member,
                book,
                -book.getPrice(),
                LocalDate.now(),
                InvoiceStatus.REFUNDED
        );
        library.getInvoices().add(refundInvoice);

        System.out.println("Kitap iade edildi.");
        if (activeRecord != null) {
            System.out.println("Güncellenen BorrowRecord: " + activeRecord);
        }
        System.out.println("İade faturası: " + refundInvoice);

        return true;
    }
}
