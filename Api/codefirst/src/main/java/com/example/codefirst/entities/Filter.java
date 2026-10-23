package com.example.codefirst.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "filters")
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name; 

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
        name = "Categories_Filters", // Nazwa tabeli łączącej w bazie danych
        joinColumns = @JoinColumn(name = "filter_id"), // Kolumna wskazująca na tę encję
        inverseJoinColumns = @JoinColumn(name = "category_id") // Kolumna wskazująca drugą encję
    )
    private List<Category> subCategories = new ArrayList<Category>();

}
