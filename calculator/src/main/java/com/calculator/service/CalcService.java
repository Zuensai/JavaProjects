package com.calculator.service;

import com.calculator.model.CalcRequest;
import com.calculator.model.CalcResult;
import com.calculator.model.Operator;

//implements = the calculate method we call in iCalcService
public class CalcService implements iCalcService {

    //constant that never changes
    private static final double MIN_VALUE = 0;
    private static final double MAX_VALUE = 10000;

    //catches error
    @Override //if you make a typo, interface would be unfulfilled
    //return type | method name | parameter (contains object)
    public CalcResult calculate(CalcRequest request) {

        //calc request has a, b and operator so i create local variable
        double a = request.getA();
        double b = request.getB();
        Operator operator = request.getOperator();

        //validate - call method and store in valResult, either success or failure
        CalcResult validationResult = validate (a,b);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        return compute (a, b, operator);
    }

    private CalcResult validate (double a, double b) {
        if (a < MIN_VALUE || a > MAX_VALUE) {
            return CalcResult.failure(
                    "First input must be between " + MIN_VALUE + " and " + MAX_VALUE
            );
        }
        if (b < MIN_VALUE || b > MAX_VALUE) {
            return CalcResult.failure(
                    "Second input must be between " + MIN_VALUE + " and " + MAX_VALUE
            );
        }
        return CalcResult.success(0, null);
    }

    private CalcResult compute(double a, double b, Operator operator){
        switch (operator) {
            case ADD:
                return CalcResult.success(a + b, "Result");
            case SUBTRACT:
                return CalcResult.success(a - b, "Result");
            case MULTIPLY:
                return CalcResult.success(a * b, "Result");
            case DIVIDE:
                if (b == 0) {
                    return CalcResult.failure("Cannot divide by zero");
                }
                return CalcResult.success(a / b, "Result");
            case MODULO:
                if (b == 0) {
                    return CalcResult.failure("Cannot divide by zero");
                }
                return CalcResult.success(a % b, "Remainder");
            default:
                return CalcResult.failure("unknown operator");
        }
    }
}
