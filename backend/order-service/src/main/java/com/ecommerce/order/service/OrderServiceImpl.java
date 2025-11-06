package com.ecommerce.order.service;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.kafka.OrderProducer;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @Override
    public Order createOrder(Order order) {
        // Sauvegarde dans la base
        Order savedOrder = orderRepository.save(order);

        // Envoi dans Kafka
        orderProducer.sendOrder(savedOrder);

        return savedOrder;
    }
}
