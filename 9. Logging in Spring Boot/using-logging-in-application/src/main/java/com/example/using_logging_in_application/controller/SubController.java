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
public class SubController {

    private final CalculatorService service;

    @GetMapping("/sub/{a}/{b}")
    public double sub(@PathVariable double a, @PathVariable double b) {
        log.info("Called sub(..)");
        log.debug("Calculating subtraction of {} and {}", a, b);
        double result = service.subtract(a, b);
        log.debug("Calculated subtraction result is {}", result);
        return result;
    }
}
