package com.ecommerce.order.service;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.kafka.OrderProducer;
import com.ecommerce.order.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        Order savedOrder = orderRepository.save(order);
        try {
            orderProducer.sendOrder(savedOrder); // non-bloquant grâce au callback/whenComplete
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi Kafka pour order {} : {}", savedOrder.getOrderId(), e.getMessage(), e);
        }
        return savedOrder;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
