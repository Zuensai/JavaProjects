package com.calculator.model;

public enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE ("/"),
    MODULO ("%");

    private final String symbol;
    //this declares string variable that belongs to each enum constant.
    //so every operator gets its own symbol stored in it.

    Operator(String symbol) {
        this.symbol = symbol;
    }
    //Constructor and symbol = value (ex; + or -)
    //So like: store the value i received into my own symbol variable

    public String getSymbol() {
        return symbol;
    }

    public static Operator fromSymbol(String symbol) {
        for (Operator oper : values()) {
            if (oper.symbol.equals(symbol)) {
                return oper;
            }
        }
        throw new IllegalArgumentException("Unknown operator: " + symbol);
    }
}
//values is built-in enum method. it returns all constants.
//For loop is looping through each one, calling it 'op' temporarily
//equals checks if constant stored matches to user input
//return op - if it matches, return that constant.
