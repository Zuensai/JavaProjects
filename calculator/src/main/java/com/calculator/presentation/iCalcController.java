package com.calculator.presentation;

import com.calculator.model.CalcResult;
import com.calculator.model.Operator;

public interface iCalcController {

    //receives raw input from UI, returns result
    CalcResult handleInput(double a, Operator operator, double b);

}

