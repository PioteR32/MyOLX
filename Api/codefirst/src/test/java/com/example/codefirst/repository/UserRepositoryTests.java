package com.example.codefirst.repository;

import com.example.codefirst.test.TestDataFactory;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.codefirst.entities.User;
import com.example.codefirst.entities.Town;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:users-order-test",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.test.database.replace=none"
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
public class UserRepositoryTests {
    @Autowired
    private UserRepository repository;

    @Autowired
    private EntityManager entityManager;

    private Town town;

    private static final String FIRST_PHONE = "999999998";
    private static final String SECOND_PHONE = "999999999";
    private static final String FIRST_USER_EMAIL = "user@example.com";
    
    @BeforeEach
    void prepareData() {
        TestDataFactory factory = new TestDataFactory(entityManager);
        town = factory.createTown("Warszawa");

        User user = factory.createUser(FIRST_USER_EMAIL, town);
        User other = factory.createUser("other@example.com", town);
        User third = factory.createUser("third@example.com", FIRST_PHONE, town);
        User uninvolved = factory.createUser("uninvolved@example.com", SECOND_PHONE, town);

        factory.flushAndClear();
    }

    @Test
    void shouldCreateUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("shouldCreateUserTest@example.com");
        user.setPassword(TestDataFactory.usersCorrectPasswords);
        user.setPhoneNum("999999997");
        user.setTown(town);
        int initialCount = repository.findAll().size();
        repository.saveAndFlush(user);
        assertThat(repository.findAll().size()).isEqualTo(initialCount + 1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678", "aaaaaaaaaa", "1234567890", "abcdefghij",""})
    void shouldNotCreateUserByPhoneNum(String phoneNum) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword(TestDataFactory.usersCorrectPasswords);
        user.setPhoneNum(phoneNum);
        user.setTown(town);
        assertThrows(ConstraintViolationException.class,
                () -> repository.saveAndFlush(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {FIRST_PHONE, SECOND_PHONE})
    void shouldNotCreateUserWithDuplicatePhoneNum(String phoneNum) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("duplicate-phone@example.com");
        user.setPassword(TestDataFactory.usersCorrectPasswords);
        user.setPhoneNum(phoneNum);
        user.setTown(town);

        assertThrows(DataIntegrityViolationException.class,
                () -> repository.saveAndFlush(user));
    }


    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"@com.wp", "wewew", "122312", "user@@example.com", "", "   "})
    void shouldNotCreateUserByEmail(String email) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(email);
        user.setPassword(TestDataFactory.usersCorrectPasswords);
        user.setTown(town);
        assertThrows(ConstraintViolationException.class,
                () -> repository.saveAndFlush(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {FIRST_USER_EMAIL})
    void shouldNotCreateUserByDuplicateEmail(String email) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(email);
        user.setPassword(TestDataFactory.usersCorrectPasswords);
        user.setTown(town);
        assertThrows(DataIntegrityViolationException.class,
                () -> repository.saveAndFlush(user));
    }
    @ParameterizedTest
    @ValueSource(strings = {FIRST_PHONE, FIRST_USER_EMAIL})
    void shouldFindUserWithLogin(String login) {
        assertThat(repository.findByLogin(login)).hasValueSatisfying(user ->
                assertThat(login).isIn(user.getEmail(), user.getPhoneNum()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "nonexistent@example.com", "12345678"})
    void shouldNotFindUserWithLogin(String login) {
        assertThat(repository.findByLogin(login)).isEmpty();
    }
}
