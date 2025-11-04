package com.ecommerce.order.kafka;

import com.ecommerce.order.avro.OrderAvro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {

    @KafkaListener(topics = "orders_V2", groupId = "order-service-group")
    public void consume(OrderAvro orderAvro) {
        log.info("📥 Message reçu depuis Kafka topic 'orders_V2': {}", orderAvro);
        System.out.println("✅ Nouvelle commande reçue : " + orderAvro);
    }
}
