package com.example.codefirst.controller;

import com.example.codefirst.entities.Town;
import com.example.codefirst.service.TownService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/towns")
public class TownController {

    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping
    public List<Town> getAllTowns() {
        return townService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Town> getTownById(@PathVariable Long id) {
        return townService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Town createTown(@RequestBody Town town) {
        return townService.save(town);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Town> updateTown(@PathVariable Long id, @RequestBody Town townDetails) {
        try {
            return ResponseEntity.ok(townService.update(id, townDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTown(@PathVariable Long id) {
        townService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
