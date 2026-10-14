package com.example.codefirst.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.ArrayList;


import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "towns")
@Data
public class Town{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name; 

    @OneToMany(mappedBy = "town", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
}
