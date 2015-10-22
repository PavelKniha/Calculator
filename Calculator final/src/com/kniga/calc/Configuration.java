package com.kniga.calc;

import java.util.Scanner;

import com.kniga.calc.computelogic.BinaryComputeLogic;
import com.kniga.calc.computelogic.ComputeLogic;
import com.kniga.calc.computelogic.DecimalComputeLogic;
import com.kniga.calc.computelogic.HeximalComputeLogic;
import com.kniga.calc.readers.CalculatorReader;
import com.kniga.calc.readers.ConsoleCalculatorReader;
import com.kniga.calc.readers.DatabaseCalculatorReader;
import com.kniga.calc.readers.FileCalculatorReader;
import com.kniga.calc.writers.CalculatorWriter;
import com.kniga.calc.writers.ConsoleCalculatorWriter;
import com.kniga.calc.writers.DatabaseCalculatorWriter;
import com.kniga.calc.writers.FileCalculatorWriter;
import com.kniga.calc.writers.UICalculatorWriter;

/**
 * class for install configuration
 *
 */
public class Configuration {
	
	private static Configuration defaultConfiguration;

	// configured interfaces
	private final CalculatorReader reader;
	private final CalculatorWriter writer;
	private final ComputeLogic computeLogic;


	
	//private default configuration
	private Configuration() {
		
		this.reader = new ConsoleCalculatorReader();
		this.writer = new ConsoleCalculatorWriter();
		this.computeLogic = new DecimalComputeLogic();

	}

	//private custom cinfiguration
	private Configuration(CalculatorReader reader, CalculatorWriter writer,
			ComputeLogic computeLogic) {
		this.reader = reader;
		this.writer = writer;
		this.computeLogic = computeLogic;

	}
	
	// default factory
	public static Configuration getDefaultConfiguration(){
		if(defaultConfiguration == null){
			defaultConfiguration = new Configuration();
		}
		return defaultConfiguration;
	}
	// custom factory
	public static Configuration getCustomConfiguration(){
		CalculatorReader reader;
		CalculatorWriter writer;
		ComputeLogic computeLogic;	
		Scanner configScanner = new Scanner(System.in);
		
		System.out.print("Enter:\n" + "1 to choose binary logic\n"
				+ "2 to choose decimal(default) logic\n"
				+ "3 to choose heximal logic\n");
		int logicType = configScanner.nextInt();
		// initialize interface from input
		switch (logicType) {
		case 1:
			computeLogic = new BinaryComputeLogic();
			break;
		case 2:
			computeLogic = new DecimalComputeLogic();
			break;
		case 3:
			computeLogic = new HeximalComputeLogic();
			break;
		default:
			computeLogic = new DecimalComputeLogic();
			break;
		}
		System.out.print("Enter:\n" + "1 to read from console(default)\n"
				+ "2 to read from database(not implemented yet)\n" + "3 to read from file\n");
		int readerType = configScanner.nextInt();
		// initialize interface from input
		switch (readerType) {
		case 1:
			reader = new ConsoleCalculatorReader();
			break;
		case 2:
			reader = new DatabaseCalculatorReader();
			break;
		case 3:
			reader = new FileCalculatorReader();
			break;
		default:
			reader = new ConsoleCalculatorReader();
			break;
		}

		System.out.print("Enter:\n" + "1 - write to console(default)\n"
				+ "2 - write to file\n" + "3 - write to UI\n"
				+ "4 - write to database(not implemented yet)\n");
		int writerType = configScanner.nextInt();
		// initialize interface from input
		switch (writerType) {
		case 1:
			writer = new ConsoleCalculatorWriter();
			break;
		case 2:
			writer = new FileCalculatorWriter();
			break;
		case 3:
			writer = new UICalculatorWriter();
			break;
		case 4:
			writer = new DatabaseCalculatorWriter();
		default:
			writer = new ConsoleCalculatorWriter();
			break;
		}
		// create configuration object according to our custom choose
		return new Configuration(reader, writer, computeLogic);
		
	}

	public CalculatorReader getReader() {
		return reader;
	}

	public CalculatorWriter getWriter() {
		return writer;
	}

	public ComputeLogic getComputeLogic() {
		return computeLogic;
	}
	
	
	
	
	



}
