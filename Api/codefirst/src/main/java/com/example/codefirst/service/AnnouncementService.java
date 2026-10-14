package com.example.codefirst.service;

import com.example.codefirst.entities.Announcement;
import com.example.codefirst.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    public Optional<Announcement> findById(Long id) {
        return announcementRepository.findById(id);
    }

    public Announcement save(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public Announcement update(Long id, Announcement announcementDetails) {
        return announcementRepository.findById(id)
                .map(announcement -> {
                    announcement.setTitle(announcementDetails.getTitle());
                    announcement.setDescription(announcementDetails.getDescription());
                    announcement.setActive(announcementDetails.isActive());
                    announcement.setUser(announcementDetails.getUser());
                    // announcement.setAnnouncementsImage(announcementDetails.getAnnouncementsImage());
                    // Note: createdAt is usually handled by @CreatedDate, so we don't update it
                    return announcementRepository.save(announcement);
                })
                .orElseThrow(() -> new RuntimeException("Announcement not found with id " + id));
    }

    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }
}
