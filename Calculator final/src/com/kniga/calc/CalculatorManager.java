package com.kniga.calc;

import java.util.Scanner;

public class CalculatorManager {
	private static final int STOP_KEY = 0;
	private static final int CUSTOM_CONFIG = 2;

	public CalculatorManager() {

		Scanner configScanner = new Scanner(System.in);
		Configuration config;

		System.out
				.print("Enter:\n"
						+ "1 if you want default configuration(logic:decimal; input:console; output:console)\n"
						+ "2 if you want custom configuration\n");
		int configMarker = configScanner.nextInt();
		// initialize configuration according to choose
		if (configMarker == CUSTOM_CONFIG) {
			config = Configuration.getCustomConfiguration();
		} else {
			config = Configuration.getDefaultConfiguration();
		}

		// create calculator that implements shunting yard algorithm
		// and with configuration(default or custom)
		Calculator calculator = new ShuntingYardCalculator(config);
		int stopKey = 1;
		do {
			//calculate expressions until 0 is pressed
			calculator.calculate();
			System.out.print("\nenter 1 to continue\n" + "enter 0 to exit\n");
			stopKey = configScanner.nextInt();
		} while (stopKey != STOP_KEY);
		configScanner.close();
		return;
	}

}
