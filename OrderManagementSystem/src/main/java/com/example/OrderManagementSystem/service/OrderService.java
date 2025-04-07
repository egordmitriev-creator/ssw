package com.example.OrderManagementSystem.service;

import com.example.OrderManagementSystem.model.entity.Order;
import com.example.OrderManagementSystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findOrdersByCriteria(String street, String city, String zipcode, LocalDateTime startDate, LocalDateTime endDate, Class<?> paymentType, String customerName, String orderStatus) {
        return orderRepository.findOrdersByCriteria(street, city ,zipcode, startDate, endDate, paymentType, customerName, orderStatus);
    }
}
