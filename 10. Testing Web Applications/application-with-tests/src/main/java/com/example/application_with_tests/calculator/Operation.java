package com.example.application_with_tests.calculator;

public enum Operation {
    ADD("Addition", "+"),
    SUBTRACT("Subtraction", "-"),
    MULTIPLY("Multiplication", "*"),
    DIVIDE("Division", "/");

    private final String displayName;
    private final String displaySymbol;

    Operation(String displayName, String displaySymbol) {
        this.displayName = displayName;
        this.displaySymbol = displaySymbol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }
}
