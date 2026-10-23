package com.example.codefirst.entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "filter_options")
public class FilterOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // DOMYŚLNIE NULLABLE = TRUE (kategorie główne nie mają rodzica!)
    private FilterOption parentFilterOption;

    @OneToMany(mappedBy = "parentFilterOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilterOption> subFilterOptions = new ArrayList<FilterOption>();
 
}
