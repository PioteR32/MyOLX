package com.example.codefirst.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "announcements_images")
@Data
public class AnnouncementsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image_data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;
}

