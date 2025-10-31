package com.ecommerce.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.avro.Order;
import com.ecommerce.order.kafka.OrderProducer;
import java.util.HashMap;
import java.util.Map;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer orderProducer;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderController(OrderProducer orderProducer,KafkaTemplate<String, Order> kafkaTemplate) {
        this.orderProducer = orderProducer;
         this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Order service is running!";
    }


  @PostMapping("/send")
    public ResponseEntity<String> sendOrder(
            @RequestParam String orderId,
            @RequestParam String customerId,
            @RequestParam String productId,
            @RequestParam int quantity,
            @RequestParam double price,
            @RequestParam String orderDate
    ) {
        try {
            // Construire l'objet Order Avro
            Order order = Order.newBuilder()
                    .setOrderId(orderId)
                    .setCustomerId(customerId)
                    .setProductId(productId)
                    .setQuantity(quantity)
                    .setPrice(price)
                    .setOrderDate(orderDate)
                    .build();

            // Envoyer à Kafka
            kafkaTemplate.send("orders", order.getOrderId().toString(), order);

            return ResponseEntity.ok("Order envoyé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi de l'order : " + e.getMessage());
        }
    }
}
