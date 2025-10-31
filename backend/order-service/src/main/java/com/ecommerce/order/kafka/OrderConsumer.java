package com.ecommerce.order.kafka;

import com.ecommerce.order.avro.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void consumeOrder(Order order) {
        System.out.println("Received Order: " + order);
        // Ici tu peux ajouter traitement : sauvegarde DB, notification, etc.
    }
}
