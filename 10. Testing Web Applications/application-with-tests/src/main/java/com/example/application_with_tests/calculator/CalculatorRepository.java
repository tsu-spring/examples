package com.example.application_with_tests.calculator;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CalculatorRepository {

    private static final List<Double> LAST_RESULTS = new ArrayList<>();

    public boolean saveCalculationResult(double result) {
        LAST_RESULTS.add(result);
        return true;
    }
}
