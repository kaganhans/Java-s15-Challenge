package org.example.library.model;

import java.time.LocalDate;

public class BorrowRecord {

    private long id;
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;

    public BorrowRecord(long id, Member member, Book book, LocalDate borrowDate) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returned = false;
    }

    public long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "id=" + id +
                ", member=" + member.getName() +
                ", book=" + book.getTitle() +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", returned=" + returned +
                '}';
    }
}
