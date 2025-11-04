package com.ecommerce.order.controller;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

@PostMapping
public Order createOrder(
        @RequestParam String orderId,
        @RequestParam String customerId,
        @RequestParam String productId,
        @RequestParam int quantity,
        @RequestParam double price,
        @RequestParam String orderDate
) {
    Order order = Order.builder()
            .orderId(orderId)
            .customerId(customerId)
            .productId(productId)
            .quantity(quantity)
            .price(price)
            .orderDate(orderDate)
            .build();

    return orderService.createOrder(order);
}

}
