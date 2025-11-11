package com.ecommerce.notification.controller;

import com.ecommerce.notification.entity.NotificationEntity;
import com.ecommerce.notification.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationRepository repo;

    public NotificationController(NotificationRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<NotificationEntity> all() {
        return repo.findAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<NotificationEntity> byOrder(@PathVariable String orderId) {
        return repo.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/resend/{orderId}")
    public ResponseEntity<Void> resend(@PathVariable String orderId) {
        // endpoint simple — on pourrait invoquer service pour re-send basé sur DB
        return ResponseEntity.accepted().build();
    }
}
