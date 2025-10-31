package com.ecommerce.order.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private String orderId;
    private String product;
    private int quantity;
    private double price;

    public String getOrderId() {
       return this.orderId;
    }

    public String getProductId() {
        return this.product;
    }

    public int getQuantity() {
       return this.quantity;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getOrderDate() {
       return this.getOrderDate();
    }
}
