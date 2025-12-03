package org.example.library.ui;

import org.example.library.model.Author;
import org.example.library.model.Book;
import org.example.library.model.Library;
import org.example.library.model.Member;
import org.example.library.service.LibraryService;

import java.util.Scanner;

public class LibraryApplication {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Library library = new Library();
        LibraryService libraryService = new LibraryService(library);

        boolean running = true;

        System.out.println("=== KÜTÜPHANE OTOMASYONU ===");

        while (running) {
            printMenu();
            System.out.print("Seçiminiz: ");
            String input = scanner.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı girin.");
                continue;
            }

            switch (choice) {
                case 1 -> handleAddMember(libraryService);
                case 2 -> handleAddBook(libraryService);
                case 3 -> handleSearchBookById(libraryService);
                case 4 -> handleSearchBookByTitle(libraryService);
                case 5 -> handleSearchBookByAuthor(libraryService);
                case 6 -> handleListByCategory(libraryService);
                case 7 -> handleUpdateBook(libraryService);
                case 8 -> handleDeleteBook(libraryService);
                case 9 -> libraryService.printAllBooks();
                case 10 -> handleBorrowBook(libraryService);
                case 11 -> handleReturnBook(libraryService);
                case 0 -> {
                    running = false;
                    System.out.println("Program sonlandırılıyor. Görüşmek üzere!");
                }
                default -> System.out.println("Geçersiz seçim. Tekrar deneyin.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("--------- MENÜ ---------");
        System.out.println("1 - Yeni Üye Ekle");
        System.out.println("2 - Yeni Kitap Ekle");
        System.out.println("3 - ID ile Kitap Ara");
        System.out.println("4 - İsim ile Kitap Ara");
        System.out.println("5 - Yazar ile Kitap Ara");
        System.out.println("6 - Kategoriye Göre Kitap Listele");
        System.out.println("7 - Kitap Güncelle");
        System.out.println("8 - Kitap Sil");
        System.out.println("9 - Tüm Kitapları Listele");
        System.out.println("10 - Kitap Ödünç Al");
        System.out.println("11 - Kitap İade Et");
        System.out.println("0 - Çıkış");
        System.out.println("------------------------");
    }

    // ---------- ÜYE İŞLEMLERİ ----------

    private static void handleAddMember(LibraryService libraryService) {
        try {
            System.out.print("Üye ID: ");
            long id = Long.parseLong(scanner.nextLine());

            System.out.print("İsim: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Telefon: ");
            String phone = scanner.nextLine();

            Member member = new Member(id, name, email, phone);
            libraryService.addMember(member);
        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş. ID sayı olmalıdır.");
        }
    }

    // ---------- KİTAP İŞLEMLERİ ----------

    private static void handleAddBook(LibraryService libraryService) {
        try {
            System.out.print("Kitap ID: ");
            long id = Long.parseLong(scanner.nextLine());

            System.out.print("Kitap Adı: ");
            String title = scanner.nextLine();

            System.out.print("Yazar ID: ");
            long authorId = Long.parseLong(scanner.nextLine());

            System.out.print("Yazar Adı: ");
            String authorName = scanner.nextLine();

            System.out.print("Yazar Email: ");
            String authorEmail = scanner.nextLine();

            System.out.print("Yazar Telefon: ");
            String authorPhone = scanner.nextLine();

            System.out.print("Kategori: ");
            String category = scanner.nextLine();

            System.out.print("Fiyat: ");
            double price = Double.parseDouble(scanner.nextLine());

            Author author = new Author(authorId, authorName, authorEmail, authorPhone);
            Book book = new Book(id, title, author, category, price);

            libraryService.addBook(book);
        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş. ID ve fiyat sayı olmalıdır.");
        }
    }

    private static void handleSearchBookById(LibraryService libraryService) {
        try {
            System.out.print("Aranacak kitap ID: ");
            long id = Long.parseLong(scanner.nextLine());

            Book book = libraryService.findBookById(id);
            if (book == null) {
                System.out.println("Bu ID ile kitap bulunamadı.");
            } else {
                System.out.println("Bulunan kitap: " + book);
            }
        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş. ID sayı olmalıdır.");
        }
    }

    private static void handleSearchBookByTitle(LibraryService libraryService) {
        System.out.print("Kitap adı (tam/parsiyel): ");
        String title = scanner.nextLine();

        var books = libraryService.findBooksByTitle(title);
        libraryService.printBooks(books);
    }

    private static void handleSearchBookByAuthor(LibraryService libraryService) {
        System.out.print("Yazar adı (tam/parsiyel): ");
        String authorName = scanner.nextLine();

        var books = libraryService.findBooksByAuthorName(authorName);
        libraryService.printBooks(books);
    }

    private static void handleListByCategory(LibraryService libraryService) {
        System.out.print("Kategori adı: ");
        String category = scanner.nextLine();

        var books = libraryService.findBooksByCategory(category);
        libraryService.printBooks(books);
    }

    private static void handleUpdateBook(LibraryService libraryService) {
        try {
            System.out.print("Güncellenecek kitap ID: ");
            long id = Long.parseLong(scanner.nextLine());

            Book book = libraryService.findBookById(id);
            if (book == null) {
                System.out.println("Bu ID ile kitap bulunamadı.");
                return;
            }

            System.out.println("Boş bırakırsanız eski değer korunur.");

            System.out.print("Yeni kitap adı (eski: " + book.getTitle() + "): ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isBlank()) {
                book.setTitle(newTitle);
            }

            System.out.print("Yeni kategori (eski: " + book.getCategory() + "): ");
            String newCategory = scanner.nextLine();
            if (!newCategory.isBlank()) {
                book.setCategory(newCategory);
            }

            System.out.print("Yeni fiyat (eski: " + book.getPrice() + "): ");
            String priceInput = scanner.nextLine();
            if (!priceInput.isBlank()) {
                try {
                    double newPrice = Double.parseDouble(priceInput);
                    book.setPrice(newPrice);
                } catch (NumberFormatException e) {
                    System.out.println("Fiyat güncellenemedi (geçersiz sayı), eski değer korunuyor.");
                }
            }

            libraryService.updateBook(book);
        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş. ID sayı olmalıdır.");
        }
    }

    private static void handleDeleteBook(LibraryService libraryService) {
        try {
            System.out.print("Silinecek kitap ID: ");
            long id = Long.parseLong(scanner.nextLine());
            libraryService.deleteBook(id);
        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş. ID sayı olmalıdır.");
        }
    }

    // ---------- ÖDÜNÇ / İADE ----------

    private static void handleBorrowBook(LibraryService libraryService) {
        try {
            System.out.print("Üye ID: ");
            long memberId = Long.parseLong(scanner.nextLine());

            System.out.print("Kitap ID: ");
            long bookId = Long.parseLong(scanner.nextLine());

            libraryService.borrowBook(memberId, bookId);
        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş. ID'ler sayı olmalıdır.");
        }
    }

    private static void handleReturnBook(LibraryService libraryService) {
        try {
            System.out.print("Üye ID: ");
            long memberId = Long.parseLong(scanner.nextLine());

            System.out.print("Kitap ID: ");
            long bookId = Long.parseLong(scanner.nextLine());

            libraryService.returnBook(memberId, bookId);
        } catch (NumberFormatException e) {
            System.out.println("Hatalı giriş. ID'ler sayı olmalıdır.");
        }
    }
}
