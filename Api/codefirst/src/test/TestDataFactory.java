package com.example.repository.test.repository;

import com.example.codefirst.entities.Announcement;
import com.example.codefirst.entities.Order;
import com.example.codefirst.entities.Town;
import com.example.codefirst.entities.User;
import com.example.codefirst.entities.UsersOrder;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

/** Tworzy i zapisuje encje używane w testach. */
public class TestDataFactory {

    private final EntityManager entityManager;

    public TestDataFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Town createTown(String name) {
        Town town = new Town(name);
        entityManager.persist(town);
        return town;
    }

    public User createUser(String email, Town town) {
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail(email);
        user.setPassword("test-password");
        user.setPhoneNum("123456789");
        user.setTown(town);
        entityManager.persist(user);
        return user;
    }

    public Announcement createAnnouncement(User seller) {
        Announcement announcement = new Announcement();
        announcement.setTitle("Laptop");
        announcement.setDescription("Ogłoszenie testowe");
        announcement.setPrice(new BigDecimal("2500.00"));
        announcement.setActive(true);
        announcement.setUser(seller);
        entityManager.persist(announcement);
        return announcement;
    }

    public Order createOrder(Announcement announcement) {
        Order order = new Order();
        order.setName("Zakup laptopa");
        order.setStatus("NEW");
        order.setCorrect(true);
        order.setProtected(true);
        order.setAnnouncement(announcement);
        entityManager.persist(order);
        return order;
    }

    public Order createOrder(User seller) {
        return createOrder(createAnnouncement(seller));
    }

    public UsersOrder createUsersOrder(Order order, User buyer, User seller) {
        UsersOrder usersOrder = new UsersOrder();
        usersOrder.setOrder(order);
        usersOrder.setBuyer(buyer);
        usersOrder.setSeller(seller);
        entityManager.persist(usersOrder);
        return usersOrder;
    }

    public void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
