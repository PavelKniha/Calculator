package com.kniga.calc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.kniga.calc.Token.OperatorType;
import com.kniga.calc.Token.TokenType;
import com.kniga.calc.computelogic.ComputeLogic;
import com.kniga.calc.computelogic.DecimalComputeLogic;
import com.kniga.calc.readers.CalculatorReader;
import com.kniga.calc.readers.ConsoleCalculatorReader;
import com.kniga.calc.writers.CalculatorWriter;
import com.kniga.calc.writers.ConsoleCalculatorWriter;

/**
 * eveluate postfix notation with configured logic input and output
 *
 */
public class ShuntingYardCalculator implements Calculator {
	// configuration :logic, input and output
	private CalculatorReader reader;
	private CalculatorWriter writer;
	private ComputeLogic computeLogic;

	// initialize :logic, input and output
	public ShuntingYardCalculator(Configuration config) {
		this.reader = config.getReader();
		this.writer = config.getWriter();
		this.computeLogic = config.getComputeLogic();
	}

	// initialize default configuration
	public ShuntingYardCalculator() {
		this(Configuration.getDefaultConfiguration());
	}


	@Override
	public void calculate() {
		// read
		final String expression = reader.readExpression();
		// compute
		final String result = calculate(expression, computeLogic);
		// write
		writer.writeResult(result);
	}

	// calculate logic
	private String calculate(String expression, ComputeLogic computeLogic) {
		String result;
		Deque<Token> postfixNotationQueue;
		// get parser(with predifined logic) to get postfix notation of input expression
		Parser parser = new Parser(computeLogic);
		try {
			// get postfix notation
			postfixNotationQueue = parser.convertToPostfix(expression);
			// eveluate postfix notation
			result = evaluatePostfix(postfixNotationQueue);
		} catch (Exception e) {
			return e.getMessage();
		}
		return result;
	}
	

	private String evaluatePostfix(Deque<Token> postfixNotationQueue) {
		if (postfixNotationQueue.size() == 0){
			throw new RuntimeException("postfix notation is empty");
		}

		Token token;

		Deque<Token> postfixQueue = postfixNotationQueue;
		Deque<Double> operatorStack = new LinkedList<Double>();

		while (postfixQueue.size() > 0) {
			try {
				token = postfixQueue.removeFirst();
				TokenType tokenType = token.getTokenType();
				OperatorType operatorType = token.getOperatorType();
				char operator = token.getOperator();

				if (tokenType == Token.TokenType.NUMBER) {
					operatorStack.addLast((double) token.getValue());
				} else if (tokenType == Token.TokenType.OPERATOR) {
					double resultStorage = operatorStack.removeLast();
					if (operator == '+') {
						operatorStack.addLast(resultStorage
								+ operatorStack.removeLast());
					} else if (operator == '-'
							&& operatorType == Token.OperatorType.LEFT_ASSOCIATIVE) {
						operatorStack.addLast(operatorStack.removeLast()
								- resultStorage);
					} else if (operator == '-'
							&& operatorType == Token.OperatorType.UNARY_PREFIX) {
						operatorStack.addLast(-resultStorage);
					} else if (operator == '*') {
						operatorStack.addLast(resultStorage
								* operatorStack.removeLast());
					} else if (operator == '/') {
						operatorStack.addLast(operatorStack.removeLast()
								/ resultStorage);
					}
				}
			} catch (NoSuchElementException e) {
				System.out.println("No more tokens to evaluate");
				break;
			}

		}

		if (operatorStack.size() != 1) {
			return new String("Invalid postfix");
		} else {
			String result = operatorStack.getLast().toString();
			return result;
		}

	}
}
