package com.ecommerce.notification.service;

import com.ecommerce.order.avro.OrderAvro;
import com.ecommerce.notification.entity.NotificationEntity;
import com.ecommerce.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void handleOrderCreated(OrderAvro order) {
        String orderId = String.valueOf(order.getOrderId());
        Optional<NotificationEntity> opt = repo.findByOrderId(orderId);

        if (opt.isPresent() && opt.get().isSent()) {
            log.info("Notification already sent for order {}", orderId);
            return;
        }

        NotificationEntity n = opt.orElseGet(() -> {
            NotificationEntity e = new NotificationEntity();
            e.setOrderId(orderId);
            e.setType("ORDER_CREATED");
            e.setCreatedAt(Instant.now());
            e.setTries(0);
            e.setSent(false);
            e.setPayload(order.toString());
            return e;
        });

        // simulate send
        boolean ok = sendNotification(order);

        n.setTries(n.getTries() + 1);
        if (ok) {
            n.setSent(true);
            n.setSentAt(Instant.now());
            log.info("Notification sent for order {}", orderId);
        } else {
            log.warn("Notification failed for order {}", orderId);
            // save state then throw to let error handler handle retries / DLQ
        }
        repo.save(n);

        if (!ok) {
            throw new RuntimeException("Failed to send notification for order " + orderId);
        }
    }

    private boolean sendNotification(OrderAvro order) {
        // TODO : integrate real mail/sms provider. For now log/simulate.
        log.info("SIMULATED NOTIFICATION -> orderId={} customer={}", order.getOrderId(), order.getCustomerId());
        return true;
    }
}
