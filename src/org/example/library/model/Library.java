package org.example.library.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private Map<Long, Book> booksById = new HashMap<>();
    private Map<Long, Member> membersById = new HashMap<>();
    private List<BorrowRecord> borrowRecords = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();

    public Library() {
    }

    public Map<Long, Book> getBooksById() {
        return booksById;
    }

    public void setBooksById(Map<Long, Book> booksById) {
        this.booksById = booksById;
    }

    public Map<Long, Member> getMembersById() {
        return membersById;
    }

    public void setMembersById(Map<Long, Member> membersById) {
        this.membersById = membersById;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(List<BorrowRecord> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
