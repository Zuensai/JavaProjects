//interface
//any class that implements me, must have these methods"
//so like defining WHAT it can do, not HOW it does it
//in this example ; whatever service i use, i will guarantuee it has 'calculate' method
//that takes requests and returns a result

package com.calculator.service;

import com.calculator.model.CalcResult;
import com.calculator.model.CalcRequest;

public interface iCalcService {

    CalcResult calculate(CalcRequest request);
}

