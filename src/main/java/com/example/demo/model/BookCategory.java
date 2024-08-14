package com.example.demo.model;

import jakarta.persistence.*;


@Entity
@Table(name = "BookCategories")
public class BookCategory {

    @Id
    @ManyToOne
    @JoinColumn(name = "bookID")
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;

    // Getters and Setters
}
