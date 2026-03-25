package com.calculator.presentation;

import com.calculator.model.Operator;
import com.calculator.model.CalcResult;
import com.calculator.model.CalcRequest;
import com.calculator.service.iCalcService;

public class CalcController implements iCalcController {

    private final iCalcService service;

    public CalcController(iCalcService service) {
        this.service = service;
    }

    @Override
    public CalcResult handleInput(double a, Operator operator, double b) {

        //validate not null
        if (operator == null) {
            return CalcResult.failure("please select valid operator");
        }

        //unpack everything into CalcRequest
        CalcRequest request = new CalcRequest(a, b, operator);

        //give it to service and CalcResult to UI
        return service.calculate(request);
    }
}
