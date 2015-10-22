package com.kniga.calc.writers;

public class ConsoleCalculatorWriter implements CalculatorWriter {

    @Override
    public void writeResult(String result) {
        System.out.println("Result = " + result);
    }
}
