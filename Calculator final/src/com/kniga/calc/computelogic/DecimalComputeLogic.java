package com.kniga.calc.computelogic;


public class DecimalComputeLogic implements ComputeLogic{

	@Override
	public boolean isNumeric(char c) {
		return (Character.isDigit(c) || c == '.');
	}

	@Override
	public float valueOf(String value) {
		Float number = Float.valueOf(value);
		return number;
	}

}
