package com.ecommerce.order.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ecommerce.order.avro.Order;

@Component
public class OrderConsumer {

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void consume(Order order) {
        System.out.println("Order received from Kafka: " + order);
    }
}