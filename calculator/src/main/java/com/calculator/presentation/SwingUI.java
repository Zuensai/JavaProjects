package com.calculator.presentation;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import com.calculator.model.CalcResult;
import com.calculator.model.Operator;

public class SwingUI implements iUserInterface {

    private final iCalcController controller;
    private JFrame frame;

    //components
    private JTextField inputA;
    private JTextField inputB;
    private JLabel resultLabel;

    //track operator
    private Operator selectedOperator = null;

    public SwingUI(iCalcController controller) {
        this.controller = controller;
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            buildWindow();
            buildComponents();
            frame.setVisible(true);
        });
    }

    private void buildWindow() {
        frame = new JFrame("Calculator");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private void buildComponents() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20,20));

        inputA = new JTextField(10);
        inputB = new JTextField(10);
        resultLabel = new JLabel("Result will appear here!");

        //OPERATOR BUTTONS
        JButton addButton      = new JButton("+");
        JButton subtractButton = new JButton("-");
        JButton multiplyButton = new JButton("*");
        JButton divideButton   = new JButton("/");
        JButton moduloButton   = new JButton("%");

        //operator button listeners
        addButton.addActionListener(e      -> { selectedOperator = Operator.ADD; });
        subtractButton.addActionListener(e -> { selectedOperator = Operator.SUBTRACT; });
        multiplyButton.addActionListener(e -> { selectedOperator = Operator.MULTIPLY; });
        divideButton.addActionListener(e   -> { selectedOperator = Operator.DIVIDE; });
        moduloButton.addActionListener(e   -> { selectedOperator = Operator.MODULO; });

        //calulate button
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> handleCalculate());

        panel.add(new JLabel("First number:"));
        panel.add(inputA);
        panel.add(new JLabel("Second number:"));
        panel.add(inputB);
        panel.add(addButton);
        panel.add(subtractButton);
        panel.add(multiplyButton);
        panel.add(divideButton);
        panel.add(moduloButton);
        panel.add(calculateButton);
        panel.add(new JLabel(""));
        panel.add(resultLabel);

        frame.add(panel);
    }

    private void handleCalculate() {
        try {
            double a = Double.parseDouble(inputA.getText());
            double b = Double.parseDouble(inputB.getText());

            CalcResult result = controller.handleInput(a, selectedOperator, b);

            if (result.isSuccess()) {
                resultLabel.setText(result.getLabel() + ": " + result.getResult());
            } else {
                resultLabel.setText("Error: " + result.getErrorMessage());
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter valid numbers!");
        }
    }
}
