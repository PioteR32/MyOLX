package com.example.codefirst.repository;

import com.example.codefirst.entities.Announcement;
import com.example.codefirst.entities.Order;
import com.example.codefirst.entities.Town;
import com.example.codefirst.entities.User;
import com.example.codefirst.entities.UsersOrder;
import jakarta.persistence.EntityManager;
import java.util.List;
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

//#region CreateUser

    private int phoneNumCounter = 100000000;
    private int getPhoneNumCounter() {
        return phoneNumCounter++;
    }
    private void setPhoneNumCounter(int phoneNumCounter) {
        if(phoneNumCounter < 100000000) {
            throw new IllegalArgumentException("Phone number counter must be at least 100000000");
        }
        if(phoneNumCounter > 999999999) {
            throw new IllegalArgumentException("Phone number counter must be at most 999999999");
        }
        this.phoneNumCounter = phoneNumCounter;
    }
    private static final List<String> firstNames = List.of("Jan", "Anna", "Piotr", "Katarzyna", "Michał", "Agnieszka", "Tomasz", "Magdalena", "Paweł", "Joanna");
    private static final List<String> lastNames = List.of("Kowalski", "Nowak", "Wiśniewski", "Wójcik", "Kowalczyk", "Kamiński", "Lewandowski", "Zieliński", "Szymański", "Woźniak"); 

    public static String getRandomFirstName() {
        return firstNames.get((int) (Math.random() * firstNames.size()));
    }
    public static String getRandomLastName() {
        return lastNames.get((int) (Math.random() * lastNames.size()));
    }
    
    public static final String usersCorrectPasswords = "test-password";

    public User createUser(String email,String phone, Town town, String password) {
        User user = new User();
        user.setFirstName(getRandomFirstName());
        user.setLastName(getRandomLastName());
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNum(phone);
        user.setTown(town);
        entityManager.persist(user);
        return user;
    }

    public User createUser(String email, String phone, Town town) {
        return createUser(email, phone, town, usersCorrectPasswords);
    }

    public User createUser(String email, Town town) {
        return createUser(email, String.valueOf(getPhoneNumCounter()), town, usersCorrectPasswords);
    }
//#endregion

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
