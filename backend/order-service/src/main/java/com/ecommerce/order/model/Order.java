package com.ecommerce.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;

    @NotBlank
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @NotBlank
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Min(1)
    @Column(nullable = false)
    private int quantity;

    @Min(0)
    @Column(nullable = false)
    private double price;

    @NotBlank
    @Column(name = "order_date", nullable = false)
    private String orderDate;
}
