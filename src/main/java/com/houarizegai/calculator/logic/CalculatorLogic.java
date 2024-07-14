package com.houarizegai.calculator.logic;

import java.util.Map;
import java.util.function.BiFunction;


import java.util.regex.Pattern;
public class CalculatorLogic {
    private static final String DOUBLE_OR_NUMBER_REGEX = "([-]?\\d+[.]\\d*)|(\\d+)|(-\\d+)";
    private char selectedOperator = ' ';
    private boolean go = true; // For calculate with Opt != (=)
    private boolean addToDisplay = true; // Connect numbers in display
    private double typedValue = 0;
    public char getSelectedOperator() {
        return selectedOperator;
    }
    public void setSelectedOperator(char selectedOperator) {
        this.selectedOperator = selectedOperator;
    }
    public boolean isGo() {
        return go;
    }
    public void setGo(boolean go) {
        this.go = go;
    }
    public boolean isAddToDisplay() {
        return addToDisplay;
    }
    public void setAddToDisplay(boolean addToDisplay) {
        this.addToDisplay = addToDisplay;
    }
    public double getTypedValue() {
        return typedValue;
    }
    public void setTypedValue(double typedValue) {
        this.typedValue = typedValue;
    }

    private static final Map<Character, BiFunction<Double, Double, Double>> OPERATIONS = Map.of(
            '+', (a, b) -> add(a, b),
            '-', (a, b) -> subtract(a, b),
            '*', (a, b) -> multiply(a, b),
            '/', (a, b) -> divide(a, b),
            '%', (a, b) -> modulus(a, b),
            '^', (a, b) -> power(a, b),
            '√', (a, b) -> sqrt(b),
            'l', (a, b) -> log(b)
    );

    public static double calculate(double firstNumber, double secondNumber, char operator) {
        BiFunction<Double, Double, Double> operation = OPERATIONS.get(operator);
        if (operation == null) {
            throw new IllegalArgumentException("Unexpected operator: " + operator);
        }
        return operation.apply(firstNumber, secondNumber);
    }
    private static double add(double a, double b) {
        return a + b;
    }
    private static double subtract(double a, double b) {
        return a - b;
    }
    private static double multiply(double a, double b) {
        return a * b;
    }
    private static double divide(double a, double b) {
        return a / b;
    }
    private static double modulus(double a, double b) {
        return a % b;
    }
    private static double power(double a, double b) {
        return Math.pow(a, b);
    }
    private static double sqrt(double a) {
        return Math.sqrt(a);
    }
    private static double log(double a) {
        return Math.log(a);
    }
    public boolean isValidNumber(String input) {
        return Pattern.matches(DOUBLE_OR_NUMBER_REGEX, input);
    }
    public void handleOperator(String input, char operator) {  //moved from UI to Logic due to feature envy
        if (isValidNumber(input)) {
            setTypedValue(Double.parseDouble(input));
            setSelectedOperator(operator);
            setGo(true);
            setAddToDisplay(false);
        }
    }
    public void clearInput() {
        setGo(true);
        setAddToDisplay(true);
        setTypedValue(0);
        setSelectedOperator(' ');
    }

    public double evaluate(String input) throws NumberFormatException {
        double result;
        if (getSelectedOperator() == ' ') {
            result = Double.parseDouble(input);
        } else {
            result = calculate(getTypedValue(), Double.parseDouble(input), getSelectedOperator());
        }
        setGo(true);
        return result;
    }
}
