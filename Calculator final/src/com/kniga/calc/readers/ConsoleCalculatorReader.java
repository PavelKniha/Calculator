package com.kniga.calc.readers;

import java.util.Scanner;

public class ConsoleCalculatorReader implements CalculatorReader {

    @Override
    public String readExpression() {
    	
    		Scanner readScanner = new Scanner(System.in);
    		System.out.print("Input expression to calculate: \n");
            String expression = readScanner.nextLine();
            return expression;
    	
    }
}
