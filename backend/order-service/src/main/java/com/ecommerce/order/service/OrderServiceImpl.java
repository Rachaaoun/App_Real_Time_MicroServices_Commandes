package com.ecommerce.order.service;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.kafka.OrderProducer;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderProducer orderProducer;

    @Override
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        orderProducer.sendOrder(savedOrder);
        return savedOrder;
    }
}
