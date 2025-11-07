package com.ecommerce.order.kafka;

import com.ecommerce.order.avro.OrderAvro;
import com.ecommerce.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
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

        Object futureObj = kafkaTemplate.send(TOPIC, String.valueOf(order.getOrderId()), orderAvro);

        // Cas ListenableFuture (Spring classic)
        if (futureObj instanceof ListenableFuture) {
            @SuppressWarnings("unchecked")
            ListenableFuture<SendResult<String, OrderAvro>> lf = (ListenableFuture<SendResult<String, OrderAvro>>) futureObj;
            lf.addCallback(new ListenableFutureCallback<SendResult<String, OrderAvro>>() {
                @Override
                public void onSuccess(SendResult<String, OrderAvro> result) {
                    RecordMetadata meta = result.getRecordMetadata();
                    log.info("✅ Kafka send success — topic={}, partition={}, offset={}",
                            meta.topic(), meta.partition(), meta.offset());
                }
                @Override
                public void onFailure(Throwable ex) {
                    log.error("❌ Kafka send failed for order {} : {}", order.getOrderId(), ex.getMessage(), ex);
                }
            });
            return;
        }

        // Cas CompletableFuture (Java 8+)
        if (futureObj instanceof CompletableFuture) {
            @SuppressWarnings("unchecked")
            CompletableFuture<SendResult<String, OrderAvro>> cf = (CompletableFuture<SendResult<String, OrderAvro>>) futureObj;
            cf.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("❌ Kafka send failed for order {} : {}", order.getOrderId(), ex.getMessage(), ex);
                } else {
                    RecordMetadata meta = result.getRecordMetadata();
                    log.info("✅ Kafka send success — topic={}, partition={}, offset={}",
                            meta.topic(), meta.partition(), meta.offset());
                }
            });
            return;
        }

        // Cas inattendu (dégrade gracieusement)
        log.warn("Type de future inconnu ({}) — envoi non surveillé", futureObj != null ? futureObj.getClass().getName() : "null");
    }
}
