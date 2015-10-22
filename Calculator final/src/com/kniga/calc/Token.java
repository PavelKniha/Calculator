package com.kniga.calc;



public class Token {
	private char operator;
	private float value;
	private int precidence;
	private OperatorType operatorType;
	private TokenType tokenType;

	enum OperatorType {
		UNARY_PREFIX, LEFT_ASSOCIATIVE
	};

	enum TokenType {
		NUMBER, OPERATOR, BRACKET_LEFT, BRACKET_RIGHT
	};


	public Token(char operator, OperatorType operatorType, int precidence) {
		this.operator = operator;
		this.tokenType = TokenType.OPERATOR;
		this.operatorType = operatorType;
		this.precidence = precidence;
	}

	public Token(float num) {
		this.tokenType = TokenType.NUMBER;
		this.value = num;
	}

	public Token(TokenType tokenType) {
		if (tokenType == TokenType.BRACKET_LEFT || tokenType == TokenType.BRACKET_RIGHT)
			this.tokenType = tokenType;
	}
		

	public char getOperator() {
		return operator;
	}

	public float getValue() {
		return value;
	}

	public int getPrecidence() {
		return precidence;
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

}