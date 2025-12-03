package org.example.library.model;

import java.time.LocalDate;

public class Invoice {

    private long id;
    private Member member;
    private Book book;
    private double amount;
    private LocalDate date;
    private InvoiceStatus status;

    public Invoice(long id, Member member, Book book, double amount,
                   LocalDate date, InvoiceStatus status) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.amount = amount;
        this.date = date;
        this.status = status;
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

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", member=" + member.getName() +
                ", book=" + book.getTitle() +
                ", amount=" + amount +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
