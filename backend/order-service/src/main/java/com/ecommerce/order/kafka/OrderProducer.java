package com.ecommerce.order.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.avro.Order;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/orders")
public class OrderProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private static final String TOPIC = "orders";

    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/kafka/send")
    public String sendOrder(@RequestBody Order order) {
        kafkaTemplate.send(TOPIC, order.getOrderId().toString(), order);
        return "Order sent to Kafka: " + order.getOrderId();
    }
        // Méthode pour envoyer un Order à Kafka
    public void sendTestOrder(String orderId, String productName, double price) {
        Order order = Order.newBuilder()
                .setOrderId(orderId)
                .setCustomerId("CUST-001")       // Valeur par défaut
                .setProductId(productName)       // ici tu peux mettre productName
                .setQuantity(1)
                .setPrice(price)
                .setOrderDate("2025-10-31")      // ou LocalDate.now().toString()
                .build();

        kafkaTemplate.send("orders", order.getOrderId().toString(), order);
        System.out.println("✅ Order envoyé avec succès : " + order);
    }
}
