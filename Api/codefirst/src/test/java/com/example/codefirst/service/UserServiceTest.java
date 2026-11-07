package com.example.codefirst.service;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Import;

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
@Import(UserService.class)
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

    private Long firstUserId;


    @BeforeEach
    void prepareData() {
        TestDataFactory factory = new TestDataFactory(entityManager);
        Town town = factory.createTown("Warszawa");
        
        firstUserId = factory.createUser(FIRST_USER_EMAIL, town).getId();
        factory.createUser("other@example.com", town);
        factory.createUser("third@example.com", FIRST_PHONE, town);

        factory.flushAndClear();
    }

    @Test
    void shouldFindUserById() {
        User user = userService.findById(firstUserId).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(FIRST_USER_EMAIL);
    }

    @Test
    void shouldNotFindUserById() {
        User user = userService.findById(-12L).orElse(null);
        assertThat(user).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {FIRST_USER_EMAIL, FIRST_PHONE})
    void shouldLogin(String login) {
        User user = userService.Login(new UserLoginDTO(login, TestDataFactory.usersCorrectPasswords)).orElse(null);
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
