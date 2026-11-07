package com.example.codefirst.repository;

import com.example.codefirst.test.TestDataFactory;
import com.example.codefirst.entities.Order;
import com.example.codefirst.entities.Town;
import com.example.codefirst.entities.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

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
class UsersOrderRepositoryTest {

    @Autowired
    private UsersOrderRepository repository;

    @Autowired
    private EntityManager entityManager;

    private Long userId;
    private Long uninvolvedUserId;
    private Long purchaseId;
    private Long saleId;

    @BeforeEach
    void prepareData() {
        TestDataFactory factory = new TestDataFactory(entityManager);
        Town town = factory.createTown("Warszawa");

        User user = factory.createUser("user@example.com", town);
        User other = factory.createUser("other@example.com", town);
        User third = factory.createUser("third@example.com", town);
        User uninvolved = factory.createUser("uninvolved@example.com", town);

        Order purchase = factory.createOrder(other);
        Order sale = factory.createOrder(user);
        Order unrelated = factory.createOrder(other);

        factory.createUsersOrder(purchase, user, other);
        factory.createUsersOrder(sale, other, user);
        factory.createUsersOrder(unrelated, third, other);
        // Powtórzone powiązanie pozwala sprawdzić DISTINCT w zapytaniach.
        factory.createUsersOrder(purchase, user, other);
        factory.createUsersOrder(sale, other, user);

        userId = user.getId();
        uninvolvedUserId = uninvolved.getId();
        purchaseId = purchase.getId();
        saleId = sale.getId();
        factory.flushAndClear();
    }

    @Test
    void shouldReturnOnlyPurchasesWithoutDuplicates() {
        assertThat(repository.findOrdersByBuyerId(userId))
                .extracting(Order::getId)
                .containsExactly(purchaseId);
    }

    @Test
    void shouldReturnOnlySalesWithoutDuplicates() {
        assertThat(repository.findOrdersBySellerId(userId))
                .extracting(Order::getId)
                .containsExactly(saleId);
    }

    @Test
    void shouldReturnPurchasesAndSalesWithoutDuplicates() {
        assertThat(repository.findOrdersByUserId(userId))
                .extracting(Order::getId)
                .containsExactlyInAnyOrder(purchaseId, saleId);
    }

    @Test
    void shouldReturnEmptyListsForUserWithoutOrders() {
        assertThat(repository.findOrdersByBuyerId(uninvolvedUserId)).isEmpty();
        assertThat(repository.findOrdersBySellerId(uninvolvedUserId)).isEmpty();
        assertThat(repository.findOrdersByUserId(uninvolvedUserId)).isEmpty();
    }

}
