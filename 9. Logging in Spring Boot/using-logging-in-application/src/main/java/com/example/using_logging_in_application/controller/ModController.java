package com.example.using_logging_in_application.controller;

import com.example.using_logging_in_application.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ModController {

    private final CalculatorService service;

    @GetMapping("/mod/{a}/{b}")
    public double mod(@PathVariable double a, @PathVariable double b) {
        log.info("Called mod(..)");
        log.debug("Calculating modulus of {} and {}", a, b);
        double result = service.modulo(a, b);
        log.debug("Calculated modulus result is {}", result);
        return result;
    }
}
