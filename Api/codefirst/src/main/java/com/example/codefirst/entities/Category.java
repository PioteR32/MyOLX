package com.example.codefirst.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String title; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // DOMYŚLNIE NULLABLE = TRUE (kategorie główne nie mają rodzica!)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories = new ArrayList<Category>();

    @ManyToMany(mappedBy = "subCategories", cascade = CascadeType.ALL)
    private List<Filter> filters = new ArrayList<Filter>();
}
