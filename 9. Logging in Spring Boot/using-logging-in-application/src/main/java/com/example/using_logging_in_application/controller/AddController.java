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
public class AddController {

    private final CalculatorService service;

    @GetMapping("/add/{a}/{b}")
    public double add(@PathVariable double a, @PathVariable double b) {
        log.info("Called add(..)");
        log.debug("Calculating addition of {} and {}", a, b);
        double result = service.add(a, b);
        log.debug("Calculated addition result is {}", result);
        return result;
    }
}
