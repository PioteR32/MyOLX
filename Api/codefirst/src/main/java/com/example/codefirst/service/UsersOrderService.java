package com.example.codefirst.service;

import com.example.codefirst.entities.UsersOrder;
import com.example.codefirst.repository.UsersOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsersOrderService {

    private final UsersOrderRepository usersOrderRepository;

    public UsersOrderService(UsersOrderRepository usersOrderRepository) {
        this.usersOrderRepository = usersOrderRepository;
    }

    public List<UsersOrder> findAll() {
        return usersOrderRepository.findAll();
    }

    public Optional<UsersOrder> findById(Long id) {
        return usersOrderRepository.findById(id);
    }

    public UsersOrder save(UsersOrder usersOrder) {
        return usersOrderRepository.save(usersOrder);
    }

    public UsersOrder update(Long id, UsersOrder usersOrderDetails) {
        return usersOrderRepository.findById(id)
                .map(usersOrder -> {
                    // Since it's a join table/entity mapping, we might not want to update everything
                    // but for a basic CRUD we'll update the references
                    if (usersOrderDetails.getOrder() != null) {
                        // Note: In a real app, you'd likely want to handle the actual entity or ID
                        // For now, we'll assume the incoming object has the necessary associations
                    }
                    // For simplicity in this basic CRUD, we'll just update the relations if they are provided
                    // but often in such join entities, you don't "update" them, you just create/delete them.
                    // However, following the pattern of previous services:
                    usersOrder.setOrder(usersOrderDetails.getOrder());
                    usersOrder.setBuyer(usersOrderDetails.getBuyer());
                    usersOrder.setSeller(usersOrderDetails.getSeller());
                    return usersOrderRepository.save(usersOrder);
                })
                .orElseThrow(() -> new RuntimeException("UsersOrder not found with id " + id));
    }

    public void delete(Long id) {
        usersOrderRepository.deleteById(id);
    }
}
