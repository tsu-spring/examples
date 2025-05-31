package com.example.application_with_tests.unit;

import com.example.application_with_tests.calculator.CalculatorRepository;
import com.example.application_with_tests.calculator.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    @Mock
    private CalculatorRepository calculatorRepository;

    @InjectMocks
    private CalculatorService calculatorService;

    @ParameterizedTest
    @CsvFileSource(resources = "test-add.csv")
    void testAdd(double a, double b, double expected) {
        when(calculatorRepository.saveCalculationResult(expected)).thenReturn(true);
        assertThat(calculatorService.add(a, b))
                .withFailMessage("%f + %f should equal to %f", a, b, expected)
                .isEqualTo(expected);
        verify(calculatorRepository).saveCalculationResult(expected);
    }

    @Test
    void testDivideByZero() {
        var e = assertThrows(IllegalArgumentException.class, () -> calculatorService.div(2, 0));
        assertEquals("b must not be 0", e.getMessage());
    }

    // And same for mul, div, sub...
}
