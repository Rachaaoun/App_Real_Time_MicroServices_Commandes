package com.ecommerce.notification.kafka;

import com.ecommerce.order.avro.OrderAvro;
import com.ecommerce.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);
    private final NotificationService service;

    public NotificationListener(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "orders", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(OrderAvro order) {
        log.info("Kafka -> received order id={} customer={}", order.getOrderId(), order.getCustomerId());
        service.handleOrderCreated(order);
    }
}
