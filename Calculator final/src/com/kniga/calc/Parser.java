package com.kniga.calc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.kniga.calc.Token.OperatorType;
import com.kniga.calc.Token.TokenType;
import com.kniga.calc.computelogic.ComputeLogic;

/**
 * Parse String expression into Deque with Tokens in postfix notation
 *
 */
public class Parser {
	// strategy of input data
	private final ComputeLogic computeLogic;

	public Parser(ComputeLogic computeLogic) {
		this.computeLogic = computeLogic;
	}

	
	/**
	 * @param expression include /*+-)( and digits
	 * @return Deque with tokens in postfix notation
	 */
	public Deque<Token> convertToPostfix(String expression){
		
		// convert String expression to Deque with Infix notation and tokenized
		Deque<Token> infixQueue = convertToInfix(expression);

		if (infixQueue.size() == 0) {
			throw new RuntimeException("empty expression");
		}

		Token token;
		Deque<Token> shuntingStack = new LinkedList<Token>();
		Deque<Token> postfixNotationQueue = new LinkedList<Token>();

		while (infixQueue.size() > 0) {
			token = infixQueue.removeFirst();
			TokenType tokenType = token.getTokenType();
			OperatorType operatorType = token.getOperatorType();

			if (tokenType == Token.TokenType.NUMBER) {
				postfixNotationQueue.addLast(token);
			} else if (tokenType == Token.TokenType.OPERATOR) {
				if (operatorType == Token.OperatorType.UNARY_PREFIX) {
					shuntingStack.addLast(token);
				} else if (operatorType == Token.OperatorType.LEFT_ASSOCIATIVE) {
					while (shuntingStack.size() > 0
							&& shuntingStack.getLast().getPrecidence() >= token
									.getPrecidence()) {
						postfixNotationQueue
								.addLast(shuntingStack.removeLast());
					}
					shuntingStack.addLast(token);
				}
			} else if (tokenType == Token.TokenType.BRACKET_LEFT) {
				shuntingStack.addLast(token);
			} else if (tokenType == Token.TokenType.BRACKET_RIGHT) {
				try {
					while (shuntingStack.getLast().getTokenType() != Token.TokenType.BRACKET_LEFT) {
						postfixNotationQueue
								.addLast(shuntingStack.removeLast());
					}
				} catch (NoSuchElementException e) {
					throw new RuntimeException(
							"Mismatched brackets on shunting stack");
				}
				shuntingStack.removeLast();
			}

		}
		while (shuntingStack.size() > 0) {
			if (shuntingStack.getLast().getTokenType() != Token.TokenType.OPERATOR) {
				throw new RuntimeException("Non-operator on shunting stack");
			} else {
				postfixNotationQueue.addLast(shuntingStack.removeLast());
			}
		}

		return postfixNotationQueue;
	}

	private Deque<Token> convertToInfix(String inputString){

		Deque<Token> infixNotationQueue = new LinkedList<Token>();
		int index = 0;
		char currentChar;
		StringBuilder fragmentsOfNumber;

		while (index < inputString.length()) {
			currentChar = inputString.charAt(index);
			if (Character.isWhitespace(currentChar)) {
				index++;
				continue;
			} else if (computeLogic.isNumeric(currentChar)) {
				fragmentsOfNumber = new StringBuilder();
				fragmentsOfNumber.append(currentChar);
				index++;
				while (index < inputString.length()
						&& computeLogic.isNumeric(inputString.charAt(index))) {
					fragmentsOfNumber.append(inputString.charAt(index));
					index++;
				}
				index--;

				try {
					float number = computeLogic.valueOf(fragmentsOfNumber
							.toString());
					infixNotationQueue.addLast(new Token(number));
				} catch (NumberFormatException e) {
					throw new RuntimeException("Invalid number '" + fragmentsOfNumber);
				}
			} else if (currentChar == '+') {
				infixNotationQueue.addLast(new Token(currentChar,
						Token.OperatorType.LEFT_ASSOCIATIVE, 50));
			} else if (currentChar == '-') {
				// If a previous object (number, or bracketed expression) exists
				// then - is binary, otherwise it is unary
				if (infixNotationQueue.size() == 0
						|| (infixNotationQueue.getLast().getTokenType() != Token.TokenType.NUMBER && infixNotationQueue
								.getLast().getTokenType() != Token.TokenType.BRACKET_RIGHT)) {
					infixNotationQueue.addLast(new Token(currentChar,
							Token.OperatorType.UNARY_PREFIX, 100));
				} else {
					infixNotationQueue.addLast(new Token(currentChar,
							Token.OperatorType.LEFT_ASSOCIATIVE, 50));
				}
			} else if (currentChar == '*' || currentChar == '/') {
				infixNotationQueue.addLast(new Token(currentChar,
						Token.OperatorType.LEFT_ASSOCIATIVE, 70));
			} else if (currentChar == '(') {
				infixNotationQueue.addLast(new Token(
						Token.TokenType.BRACKET_LEFT));
			} else if (currentChar == ')') {
				infixNotationQueue.addLast(new Token(
						Token.TokenType.BRACKET_RIGHT));
			} else {
				infixNotationQueue.clear();
				throw new RuntimeException("Unexpected character '" + currentChar);
			}
			index++;
		}
		return infixNotationQueue;
	}

}
