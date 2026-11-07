package com.example.codefirst.controller;

import com.example.codefirst.dto.OperationsDto.UserRegistrationDTO;
import com.example.codefirst.dto.OperationsDto.UserLoginDTO;

import com.example.codefirst.entities.User;
import com.example.codefirst.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll(); 
    }


    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            return ResponseEntity.ok(userService.Login(userLoginDTO).map(user -> "User logged in successfully: " + user.getEmail())
                    .orElse("User not found"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationDTO user) {
        try {
        return ResponseEntity.ok(userService.save(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            return ResponseEntity.ok(userService.update(id, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
