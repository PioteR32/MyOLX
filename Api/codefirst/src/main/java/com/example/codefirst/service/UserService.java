package com.example.codefirst.service;

import com.example.codefirst.entities.User;
import com.example.codefirst.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    boolean isUserValid(User user) {
         if (user.getId() != null) {
            throw new IllegalArgumentException("New user cannot have an ID");
        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty() || user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User name and email cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("User password cannot be null or empty");
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        return true;
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public User save(User user) {
        this.isUserValid(user);
        return userRepository.save(user);
    }

    public User update(Long id, User userDetails) {
        this.isUserValid(userDetails);
        
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setPassword(userDetails.getPassword());
                    user.setPhone_num(userDetails.getPhone_num());
                    user.setEmail(userDetails.getEmail());
                    user.setTown(userDetails.getTown());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void delete(Long id) {
        if(userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }
}
