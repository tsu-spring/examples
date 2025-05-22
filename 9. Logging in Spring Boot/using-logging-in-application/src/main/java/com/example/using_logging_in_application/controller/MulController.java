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
public class MulController {

    private final CalculatorService service;

    @GetMapping("/mul/{a}/{b}")
    public double mul(@PathVariable double a, @PathVariable double b) {
        log.info("Called mul(..)");
        log.debug("Calculating multiplication of {} and {}", a, b);
        double result = service.multiply(a, b);
        log.debug("Calculated multiplication result is {}", result);
        return result;
    }
}
