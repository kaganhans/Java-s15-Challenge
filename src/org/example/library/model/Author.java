package org.example.library.model;

public class Author extends Person {

    public Author() {
    }

    public Author(long id, String name, String email, String phone) {
        super(id, name, email, phone);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
