package com.kniga.calc.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileCalculatorReader implements CalculatorReader {
	

	@Override
	public String readExpression() {

		Scanner in = new Scanner(System.in);
		System.out.println("Enter full path of .txt INPUT file\n"
				+ "Example: C:\\file.txt\n");
		String filePath = in.nextLine();
		String file = read(filePath);
		return file;
	}

	private String read(String fileName) {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader in = new BufferedReader(new FileReader(new File(
				fileName).getAbsoluteFile()))) {
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}
}
