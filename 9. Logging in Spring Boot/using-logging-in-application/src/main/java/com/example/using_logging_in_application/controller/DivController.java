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
public class DivController {

    private final CalculatorService service;

    @GetMapping("/div/{a}/{b}")
    public double div(@PathVariable double a, @PathVariable double b) {
        log.info("Called div(..)");
        log.debug("Calculating division of {} and {}", a, b);
        double result = service.divide(a, b);
        log.debug("Calculated division result is {}", result);
        return result;
    }
}
