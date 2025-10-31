package com.ecommerce.order.kafka;

import com.ecommerce.order.avro.OrderAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private static final String TOPIC = "orders";

    @Autowired
    private KafkaTemplate<String, OrderAvro> kafkaTemplate;

    public void sendOrder(OrderAvro order) {
        try {
            kafkaTemplate.send(TOPIC, order.getOrderId(), order);
            System.out.println("📦 Order envoyé au topic Kafka : " + order.getOrderId());
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'envoi de l'order à Kafka : " + e.getMessage());
            throw e;
        }
    }
}
