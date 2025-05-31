package com.example.application_with_tests.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    @Autowired
    private CalculatorRepository calculatorRepository;

    public double add(double a, double b) {
        var result = a + b;
        return saveAndReturn(result);
    }

    public double sub(double a, double b) {
        var result = a - b;
        return saveAndReturn(result);
    }

    public double mul(double a, double b) {
        var result = a * b;
        return saveAndReturn(result);
    }

    public double div(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("b must not be 0");
        }
        var result = a / b;
        return saveAndReturn(result);
    }

    private double saveAndReturn(double result) {
        if (calculatorRepository.saveCalculationResult(result)) {
            return result;
        } else {
            throw new RuntimeException("Couldn't save in DB");
        }
    }
}
