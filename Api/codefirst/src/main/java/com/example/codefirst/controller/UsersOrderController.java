package com.example.codefirst.controller;

import com.example.codefirst.entities.UsersOrder;
import com.example.codefirst.service.UsersOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users-orders")
public class UsersOrderController {

    private final UsersOrderService usersOrderService;

    public UsersOrderController(UsersOrderService usersOrderService) {
        this.usersOrderService = usersOrderService;
    }

    @GetMapping
    public List<UsersOrder> getAllUsersOrders() {
        return usersOrderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersOrder> getUserOrderById(@PathVariable Long id) {
        return usersOrderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UsersOrder createUserOrder(@RequestBody UsersOrder usersOrder) {
        return usersOrderService.save(usersOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersOrder> updateUserOrder(@PathVariable Long id, @RequestBody UsersOrder usersOrderDetails) {
        try {
            return ResponseEntity.ok(usersOrderService.update(id, usersOrderDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserOrder(@PathVariable Long id) {
        usersOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
