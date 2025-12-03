package org.example.library.model;

import java.util.HashSet;
import java.util.Set;

public class Member extends Person {

    private int maxBookLimit = 5;
    private Set<Book> borrowedBooks = new HashSet<>();

    public Member() {
    }

    public Member(long id, String name, String email, String phone) {
        super(id, name, email, phone);
    }

    public int getMaxBookLimit() {
        return maxBookLimit;
    }

    public void setMaxBookLimit(int maxBookLimit) {
        this.maxBookLimit = maxBookLimit;
    }

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public boolean canBorrowMore() {
        return borrowedBooks.size() < maxBookLimit;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", borrowedBooks=" + borrowedBooks.size() +
                '}';
    }
}
