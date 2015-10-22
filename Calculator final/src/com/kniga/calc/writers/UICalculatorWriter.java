package com.kniga.calc.writers;

import javax.swing.JOptionPane;

public class UICalculatorWriter implements CalculatorWriter {

	@Override
	public void writeResult(String result) {
		JOptionPane.showMessageDialog(null, "Result = " + result);

	}

}
