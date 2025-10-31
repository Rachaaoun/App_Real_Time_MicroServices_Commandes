package com.ecommerce.order.repository;

import com.ecommerce.order.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    // Tu peux ajouter ici des requêtes personnalisées si besoin, ex :
    // List<OrderEntity> findByCustomerId(String customerId);
}
