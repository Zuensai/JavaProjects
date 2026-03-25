package com.calculator.model;

public class CalcResult {

    private final double result;
    private final String label;
    private final String errorMessage;
    private final boolean succes;

    private CalcResult(double result, String label, String errorMessage, boolean succes) {
        this.result = result;
        this.label = label;
        this.errorMessage = errorMessage;
        this.succes = succes;
    }

    public static CalcResult success(double result, String label) {
        return new CalcResult(result, label, null, true);
    }

    public static CalcResult failure(String errorMessage) {
        return new CalcResult(0, null, errorMessage, false);
    }

    public double getResult() {
        return result;
    }

    public String getLabel() {
        return label;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return succes;
    }
}
