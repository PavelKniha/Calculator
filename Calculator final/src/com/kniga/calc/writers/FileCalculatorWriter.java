package com.kniga.calc.writers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileCalculatorWriter implements CalculatorWriter {

	@Override
	public void writeResult(String result) {

		Scanner writeScanner = new Scanner(System.in);
		System.out.println("Enter full path of .txt OUTPUT file\n"
				+ "Example: C:\\output.txt\n");

		String filePath = writeScanner.nextLine();
		write(filePath, result);
	}

	private void write(String fileName, String text) {
		try (PrintWriter printWriter = new PrintWriter(
				new File(fileName).getAbsoluteFile())) {
			printWriter.print(text);
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
