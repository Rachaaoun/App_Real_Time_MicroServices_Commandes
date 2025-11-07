package com.ecommerce.order.service;

import com.ecommerce.order.model.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> findAll();
    void deleteById(Long id);
}
