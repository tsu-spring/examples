package com.example.application_with_monitoring.controller;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToDoubleFunction;

@RestController
public class IncreateNumberController {

    @Autowired
    MeterRegistry meterRegistry;

    @PostConstruct
    void init() {
        Gauge.builder("my.number", 15, value -> value)
                .register(meterRegistry);
    }

    @GetMapping("/increase")
    public void increateNumber() {
        meterRegistry.counter("my.number").increment();
    }
}
