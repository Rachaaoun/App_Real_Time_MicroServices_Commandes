package com.ecommerce.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.avro.Order;
import com.ecommerce.order.kafka.OrderProducer;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Order service is running!";
    }


    @GetMapping("/send")
    public String sendOrder(@RequestParam String orderId,
                            @RequestParam String productName,
                            @RequestParam double price) {
        orderProducer.sendTestOrder(orderId, productName, price);
        return "Order envoyé avec succès à Kafka !";
    }

}
