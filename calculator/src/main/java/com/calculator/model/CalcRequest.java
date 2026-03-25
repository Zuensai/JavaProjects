package com.calculator.model;

public class CalcRequest {

    private final double a;
    private final double b;
    private final Operator operator;

    public CalcRequest(double a, double b, Operator operator) {
        this.a = a;
        this.b = b;
        this.operator = operator;
    }

    // getter methods for reading values outside the class because private.
    // also no setters because final.
    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Operator getOperator() {
        return operator;
    }
}
