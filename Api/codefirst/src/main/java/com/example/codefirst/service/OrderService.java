package com.example.codefirst.service;

import com.example.codefirst.entities.Order;
import com.example.codefirst.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, Order orderDetails) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setName(orderDetails.getName());
                    order.setStatus(orderDetails.getStatus());
                    order.setCorrect(orderDetails.isCorrect());
                    order.setProtected(orderDetails.isProtected());
                    order.setAnnouncement(orderDetails.getAnnouncement());
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
