package com.example.codefirst.service;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;

import com.example.codefirst.test.TestDataFactory;
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
import com.example.codefirst.dto.OperationsDto.UserLoginDTO;


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
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    private static final String FIRST_USER_EMAIL = "first@example.com";
    private static final String FIRST_PHONE = "999999998";

    private Town town;


    @BeforeEach
    void prepareData() {
        TestDataFactory factory = new TestDataFactory(entityManager);
        town = factory.createTown("Warszawa");
        
        User user = factory.createUser(FIRST_USER_EMAIL, town);
        User other = factory.createUser("other@example.com", town);
        User third = factory.createUser("third@example.com", FIRST_PHONE, town);

        factory.flushAndClear();
    }

    @Test
    void shouldFindUserById() {
        User user = userService.findById((Long)1L).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(FIRST_USER_EMAIL);
    }

    @Test
    void shouldNotFindUserById() {
        User user = userService.findById((Long)-12L).orElse(null);
        assertThat(user).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {FIRST_USER_EMAIL, FIRST_PHONE})
    void shouldLogin() {
        User user = userService.Login(new UserLoginDTO(FIRST_USER_EMAIL, TestDataFactory.usersCorrectPasswords)).orElse(null);
        assertThat(user).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings ={"wrong@example.com", "99999999999",""})
    void shouldNotLogin(String login) {
        assertThrows(RuntimeException.class, () -> {
            userService.Login(new UserLoginDTO(login, TestDataFactory.usersCorrectPasswords));
        });
    }
}
