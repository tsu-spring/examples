package com.example.application_with_tests.calculator;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CalculatorForm {

    @NotNull(message = "A cant be null")
    private Double a;

    @NotNull(message = "B cant be null")
    private Double b;

    @NotNull(message = "Operation cant be null")
    private Operation operation;
}
