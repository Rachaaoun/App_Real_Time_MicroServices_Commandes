package com.ecommerce.order.service;

import com.ecommerce.order.model.OrderEntity;
import com.ecommerce.order.avro.OrderAvro;
import com.ecommerce.order.kafka.OrderProducer;
import com.ecommerce.order.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(OrderEntity order) {
        // 1️⃣ Sauvegarde dans la base de données
        OrderEntity savedOrder = orderRepository.save(order);

        // 2️⃣ Conversion vers le modèle Avro
        OrderAvro avroOrder = new OrderAvro();
        avroOrder.setOrderId(savedOrder.getOrderId());
        avroOrder.setCustomerId(savedOrder.getCustomerId());
        avroOrder.setProductId(savedOrder.getProductId());
        avroOrder.setQuantity(savedOrder.getQuantity());
        avroOrder.setPrice(savedOrder.getPrice());
        avroOrder.setOrderDate(savedOrder.getOrderDate().toString());

        // 3️⃣ Envoi du message à Kafka
        orderProducer.sendOrder(avroOrder);
    }
}
