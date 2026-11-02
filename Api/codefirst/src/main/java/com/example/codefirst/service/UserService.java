package com.example.codefirst.service;

import com.example.codefirst.dto.OperationsDto.UserLoginDTO;
import com.example.codefirst.dto.OperationsDto.UserRegistrationDTO;


import com.example.codefirst.entities.Town;
import com.example.codefirst.entities.User;
import com.example.codefirst.repository.TownRepository;
import com.example.codefirst.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final TownRepository townRepository;

    public UserService(UserRepository userRepository, TownRepository townRepository) {
        this.userRepository = userRepository;
        this.townRepository = townRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public String Login(UserLoginDTO userLoginDTO) {
      User user = userRepository.findByLogin(userLoginDTO.getLogin()).orElseGet(() -> {
          throw new RuntimeException("User not found with email or phone " + userLoginDTO.getLogin());
        });
        if(!user.getPassword().equals(userLoginDTO.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return "1";
    }
    boolean isUserValid(UserRegistrationDTO user) {
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

    boolean isUserValid(User user) {
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
    public User save(UserRegistrationDTO user) {
        this.isUserValid(user);
        String townName = user.getTown_name().toLowerCase();
        Town town = townRepository.findByName(townName).orElseGet(() -> {
            return townRepository.save(new Town(townName));
        });
       
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNum(user.getPhoneNum());
        newUser.setPassword(user.getPassword());
        newUser.setTown(town);
        return userRepository.save(newUser);
    }

    public User update(Long id, User userDetails) {
        this.isUserValid(userDetails);
        
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setPassword(userDetails.getPassword());
                    user.setPhoneNum(userDetails.getPhoneNum());
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
