package com.ecommerce.order.kafka;

import com.ecommerce.order.model.Order; // Ton POJO Avro généré
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void consumeOrder(ConsumerRecord<String, Order> record) {
        Order order = record.value();
        System.out.println("===== Order reçu =====");
        System.out.println("ID de commande: " + order.getOrderId());
        System.out.println("Client: " + order.getCustomerId());
        System.out.println("Produit: " + order.getProductId());
        System.out.println("Quantité: " + order.getQuantity());
        System.out.println("Prix: " + order.getPrice());
        System.out.println("Date de commande: " + order.getOrderDate());
        System.out.println("=====================");
    }
}
