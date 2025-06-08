package com.example.application_with_monitoring.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("payment")
public class PaymentGatewayHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Randomly show health as up or down
        if (new Random().nextBoolean()) {
            return Health.up().build();
        } else {
            return Health.down()
                    .withDetail("error", "Gateway unreachable!")
                    .build();
        }
    }
}
