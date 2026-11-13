package com.example.codefirst.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.example.codefirst.enums.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName; 
    
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private UserRole role = UserRole.U;
    
    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "[1-9][0-9]{8}")
    @Column(name = "phone_num",unique = true,length = 9)
    private String phoneNum;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "seller", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<UsersOrder> userListOrders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "buyer", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<UsersOrder> buyerListOrders = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<Announcement> announcements = new ArrayList<>();

    @JsonIgnore
    @ManyToOne 
    @JoinColumn(name = "town_id", nullable = false)
    private Town town;
}
