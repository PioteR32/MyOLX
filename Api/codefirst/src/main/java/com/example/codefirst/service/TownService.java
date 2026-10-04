package com.example.codefirst.service;

import com.example.codefirst.entities.Town;
import com.example.codefirst.repository.TownRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TownService {

    private final TownRepository townRepository;

    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    public List<Town> findAll() {
        return townRepository.findAll();
    }

    public Optional<Town> findById(Long id) {
        return townRepository.findById(id);
    }

    public Town save(Town town) {
        return townRepository.save(town);
    }

    public Town update(Long id, Town townDetails) {
        return townRepository.findById(id)
                .map(town -> {
                    town.setName(townDetails.getName());
                    return townRepository.save(town);
                })
                .orElseThrow(() -> new RuntimeException("Town not found with id " + id));
    }

    public void delete(Long id) {
        townRepository.deleteById(id);
    }
}
