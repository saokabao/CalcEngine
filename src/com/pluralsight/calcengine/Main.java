package com.pluralsight.calcengine;

import org.omg.CORBA.INTERNAL;

import javax.sound.midi.SysexMessage;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.stream.StreamSupport;

public class Main {

    public static void main(String[] args) {

		switch(args.length) {
			case 0 :
				double[] leftVals = {100.0d, 20.0d,30.0d,40.0d};
				double[] rightVals = {20.0d, 30.0d, 40.0d, 50.0d};
				char[] opCodes = {'a', 'm', 's', 'd'};
				double[] results = new double[opCodes.length];

				for(int i = 0; i < opCodes.length; i++){
					results[i] = performOperation(opCodes[i], leftVals[i], rightVals[i]);
				}
				for (int i = 0; i < opCodes.length; i++){
					displayResult(opCodes[i], leftVals[i], rightVals[i], results[i]);
				}
				break;
			case 1:
				executeInteractively();
				break;
			case 3:
				handleCommandLine(args);
				break;
			default:
				System.out.println("ERR: Invalid args length");
		};
	}

	private static void displayResult(char opCode, double leftVal, double rightVal, double result) {
    	char opSymbol = getOpSymbolFromOpCode(opCode);
    	System.out.printf("%.2f %c %.2f = %.2f\n", leftVal, opSymbol, rightVal, result);
	}

	private static char getOpSymbolFromOpCode(char opCode) {
    	char x = 'x';
    	switch(opCode){
			case 'a':
				x = '+';
				break;
			case 'd':
				x = '/';
				break;
			case 'm':
				x = '*';
				break;
			case 's':
				x = '-';
				break;
			default:
				System.out.println("Err: opCode not recognized");
		};
    	return x;
	}

	private static double performOperation(char opCode, double leftVal, double rightVal) {
    	double result = 0.0d;
    	switch (opCode){
			case 'a':
				result = leftVal + rightVal;
				break;
			case 's':
				result = leftVal - rightVal;
				break;
			case 'd':
				result = rightVal == 0 ? -1: leftVal/rightVal;
				break;
			case 'm':
				result = leftVal * rightVal;
				break;
		};
    	return result;
	}

	private static void handleCommandLine(String[] args) {
		char opCode = opCodeFromWord(args[0]);
		if (opCode == 'w'){
			handleWhen(args);
		} else {
			double leftVal = valueFromWord(args[1]);
			double rightVal = valueFromWord(args[2]);
			double result = performOperation(opCode, leftVal, rightVal);
			displayResult(opCode, leftVal, rightVal, result);
		}
	}

	private static void executeInteractively() {
		System.out.println("Enter opCode and two numbers:");
    	Scanner scanner = new Scanner(System.in);
    	String argLine = scanner.nextLine();
    	String[] args = argLine.split(" ");
    	char opCode = opCodeFromWord(args[0]);
    	if (opCode == 'w') {
			handleWhen(args);
		} else {
			double leftVal = valueFromWord(args[1]);
			double rightVal = valueFromWord(args[2]);
			double result = performOperation(opCode, leftVal, rightVal);
			displayResult(opCode, leftVal, rightVal, result);
		}
	}

	private static void handleWhen(String[] args) {
    	LocalDate startDate = LocalDate.parse(args[1]);
    	long daysToAdd = Integer.parseInt(args[2]);
    	LocalDate result = startDate.plusDays(daysToAdd);
    	System.out.printf("Date after %d days from %s is %s", daysToAdd, startDate.toString(), result.toString());
	}

	private static char opCodeFromWord(String arg) {
    	return arg.charAt(0);
	}

	private static double valueFromWord(String arg) {
    	String[] nums = {"zero", "one", "two", "three", "four", "five",
		"six", "seven", "eight", "nine", "ten"};
    	double result = -1;
    	for(int i = 0; i < nums.length; i++){
    		if (arg.equals(nums[i])){
    			result = i;
    			break;
			}
		}
    	if (result == -1){
    		return Double.parseDouble(arg);
		}
    	return result;
	}
}
