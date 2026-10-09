package com.example.codefirst.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "announcements_images")
@Data
public class AnnouncementsImage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image_data", nullable = false,columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @ManyToMany 
    @JoinColumn(name = "announcement_id",nullable = false)
    private Announcement announcement_id;
}

