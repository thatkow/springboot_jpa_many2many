package com.hellokoding.jpa.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Publisher {
    private int id;
    private String name;
    private Set<Book> books;

    public Publisher(){

    }

    public Publisher(String name){
        this.name = name;
    }

    public Publisher(String name, Set<Book> books){
        this.name = name;
        this.books = books;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "publishers")
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    
    @Override
    public String toString() {
    	 String result = String.format(
                 "Publisher [id=%d, name='%s']%n",
                 id, name);
         if (books != null) {
             for(Book book : books) {
                 result += String.format(
                         "Book[id=%d, name='%s']%n",
                         book.getId(), book.getName());
             }
         }

         return result;
    }
}
