package com.calculator;

import com.calculator.presentation.CalcController;
import com.calculator.presentation.SwingUI;
import com.calculator.service.CalcService;

public class Main {

    public static void main(String[] args) {

        //create service
        CalcService service = new CalcService();

        //service into controller
        CalcController controller = new CalcController(service);

        //inject controller into UI
        SwingUI ui = new SwingUI(controller);

        //start the app
        ui.start();
    }
}
