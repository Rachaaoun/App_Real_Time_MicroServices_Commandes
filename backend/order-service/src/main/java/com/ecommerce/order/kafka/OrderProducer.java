package com.ecommerce.order.kafka;

import com.ecommerce.order.avro.OrderAvro;
import com.ecommerce.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // ✅ Lombok génère un constructeur pour les champs final
public class OrderProducer {

    private final KafkaTemplate<String, OrderAvro> kafkaTemplate; // ✅ injecté automatiquement
    private static final String TOPIC = "orders_V2";

    public void sendOrder(Order order) {
        // ✅ Création de l'objet Avro à partir du modèle
        OrderAvro orderAvro = OrderAvro.newBuilder()
                .setOrderId(order.getOrderId())
                .setCustomerId(order.getCustomerId())
                .setProductId(order.getProductId())
                .setQuantity(order.getQuantity())
                .setPrice(order.getPrice())
                .setOrderDate(order.getOrderDate().toString())
                .build();

        kafkaTemplate.send(TOPIC, orderAvro.getOrderId().toString(), orderAvro);
        System.out.println("✅ Order envoyé dans Kafka: " + orderAvro);
    }
}
