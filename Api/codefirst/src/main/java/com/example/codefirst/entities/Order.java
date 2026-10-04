package com.example.codefirst.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,length = 20)
    private String status;

    @Column(nullable = false)
    private boolean isCorrect;

    @Column(nullable = false)
    private boolean isProtected;
    
    @OneToOne
    @JoinColumn(name = "announcements_id",nullable = false)
    private Announcement  announcement;

    
}
