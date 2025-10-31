package com.ecommerce.order.kafka;

import com.ecommerce.order.avro.OrderAvro;
import com.ecommerce.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderAvro> kafkaTemplate;
    private static final String TOPIC = "orders";

    public void sendOrder(Order order) {
        OrderAvro orderAvro = OrderAvro.newBuilder()
                .setOrderId(order.getOrderId())
                .setProduct(order.getProduct())
                .setQuantity(order.getQuantity())
                .setPrice(order.getPrice())
                .build();

        kafkaTemplate.send(TOPIC, order.getOrderId().toString(), orderAvro);
    }
}
