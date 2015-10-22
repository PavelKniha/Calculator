package com.kniga.calc.computelogic;

import java.math.BigInteger;


public class BinaryComputeLogic implements ComputeLogic{

	@Override
	public boolean isNumeric(char c) {
		String values = "10.";
		return values.contains(Character.toString(c));
	}

	@Override
	public float valueOf(String value){
	
		return new BigInteger(value, 2).floatValue();
		
	}

}
