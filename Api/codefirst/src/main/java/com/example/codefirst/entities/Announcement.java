package com.example.codefirst.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "announcements")
@Data
public class Announcement{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String title; 

    @Column(nullable = false,length = 255)
    private String description;
     
    @Column(nullable = false,length = 255,columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal price; 
    
    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Instant createdAt;
    
    @Column(nullable = false)
    private boolean isActive;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "announcement", cascade = jakarta.persistence.CascadeType.ALL)
    private List<AnnouncementsImage> announcementsImages;

    @JsonIgnore
    @OneToOne(cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;
}

