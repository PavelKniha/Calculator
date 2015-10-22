package com.kniga.calc.computelogic;

import java.math.BigInteger;


public class HeximalComputeLogic implements ComputeLogic{

	@Override
	public boolean isNumeric(char c) {
		String inputChar = (Character.toString(c)).toLowerCase();
		String values = "abcdef.";
		return (Character.isDigit(c) || values.contains(inputChar));
	}

	@Override
	public float valueOf(String value) {
	
		return new BigInteger(value, 16).floatValue();
	}

}
