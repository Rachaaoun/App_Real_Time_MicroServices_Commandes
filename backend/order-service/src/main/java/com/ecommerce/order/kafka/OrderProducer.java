package com.ecommerce.order.kafka;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.avro.OrderAvro;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final String TOPIC = "orders";

    private final KafkaTemplate<String, OrderAvro> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderAvro> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {
        OrderAvro orderAvro = new OrderAvro(
            order.getOrderId(),
            order.getCustomerId(),
            order.getProductId(),
            order.getQuantity(),
            order.getPrice(),
            order.getOrderDate()
        );

        kafkaTemplate.send(TOPIC, String.valueOf(order.getOrderId()), orderAvro);
        System.out.println("✅ Message Kafka envoyé : " + orderAvro);
    }
}
