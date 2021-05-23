/*
 * This code lets the user choose between four lists of numbers
 * from 1-1000 of varying lengths. Then, the user can choose
 * between 4 sorting types: bubble, selection, table, or quick.
 * The program outputs the time taken to sort and creates a file
 * in the program folder called "output.txt" with the sorted list
 * and the time taken, again.
 * Author: Andy Fleischer
 * 
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Sort {

	Scanner consoleInput = new Scanner(System.in);
	String input;
	Scanner fileInput;
	int[] inputArray;
	long startTime;
	
	public Sort() {
		System.out.println("Enter a number for the input file.");
		System.out.println("1. input.txt  2: input2.txt  3. input3.txt  4. input4.txt");
		input = consoleInput.nextLine();
		// Makes sure that input is 1 character long, and is either 1, 2, 3, or 4
		if (input.length() != 1 && input.charAt(0) != '1' && input.charAt(0) != '2' 
				&& input.charAt(0) != '3' && input.charAt(0) != '4'){
			System.out.println("Enter 1, 2, 3, or 4");
			while (input.length() != 1 && input.charAt(0) != '1' && input.charAt(0) != '2' 
					&& input.charAt(0) != '3' && input.charAt(0) != '4') {
				input = consoleInput.nextLine();
			}
		}
		// Try to find the file being asked for, if there's an error, print it and exit
		try {
			fileInput = new Scanner(new File("input" + input.charAt(0) + ".txt"));
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		// Reads file, compiles each number into an array, and prints every number
		String infile = fileInput.nextLine();
		String[] inputStringArray = infile.split(",");
		inputArray = new int[inputStringArray.length];
		for (int i = 0; i < inputStringArray.length; i++) {
			inputArray[i] = Integer.parseInt(inputStringArray[i]);
			//System.out.println(inputArray[i]);
		}
		// Choose sort, again making sure the number is only 1, 2, 3, or 4
		System.out.println("Enter a number for the sort you want to use.");
		System.out.println("1. Bubble  2: Selection  3. Table  4. Quick");
		input = consoleInput.nextLine();
		if (input.length() != 1 && input.charAt(0) != '1' && input.charAt(0) != '2' 
				&& input.charAt(0) != '3' && input.charAt(0) != '4'){
			System.out.println("Enter 1, 2, 3, or 4");
			while (input.length() != 1 && input.charAt(0) != '1' && input.charAt(0) != '2' 
					&& input.charAt(0) != '3' && input.charAt(0) != '4') {
				input = consoleInput.nextLine();
			}
		}
		startTime = System.currentTimeMillis();
		// Calls corresponding sort function
		if (input.equals("1")) {
			inputArray = bubbleSort(inputArray);
		}
		if (input.equals("2")) {
			inputArray = selectionSort(inputArray);
		}
		if (input.equals("3")) {
			inputArray = tableSort(inputArray);
		}
		if (input.equals("4")) {
			quickSort(inputArray, 0, inputArray.length - 1);
		}
		// Find and print total time
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println("Total time: " + totalTime);
		System.out.println("Please wait while a file is created of the fully sorted list...");
		//Try creating the file and filing it with the sorted list
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(new File("output.txt")));
			String output = "";
			for (int i = 0; i < inputArray.length; i++) {
				output += inputArray[i] + ",";
			}
			output += "\nTotal Time: " + totalTime;
			pw.write(output);
			pw.close();
			System.out.println("Done!");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		
	}
	
	//Compare each pair of numbers and move the larger to the right
	int[] bubbleSort(int[] array) {
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length - 1; i++) {
				//if the one on the left is larger
				if (array[i] > array[i+1]) {
					//swap
					int temp = array[i];
					array[i] = array[i+1];
					array[i+1] = temp;
				}
			}
		}
		return array;
	}

	// Select the smallest number (by going through the array) and swap it to the front
	int[] selectionSort(int[] array) {
		for (int j = 0; j < array.length; j++) {
			int smallestNumber = array[j];
			int smallestIndex = j;
			for (int i = j; i < array.length; i++) {
				if (array[i] < smallestNumber) {
					smallestNumber = array[i];
					smallestIndex = i;
				}
			}
			int temp = array[smallestIndex];
			array[smallestIndex] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	// Tally how often you see each number, print out the number of times
	int[] tableSort(int[] array) {
		int[] tally = new int [1001];
		for (int i = 0; i < array.length; i++) {
			tally[array[i]]++;
		}
		int count = 0;
		//i keeps track of the actual number
		for (int i = 0; i < tally.length; i++) {
			//j keeps track of how many times we've seen that number
			for (int j = 0; j < tally[i]; j++) {
				array[count] = i;
				count++;
			}
		}
		return array;
	}

	// While you haven't sorted the full list, keep calling partition then quicksort recursively
	void quickSort(int[] array, int low, int high) {
		if (low < high) {
			int p = partition(array, low, high);
			quickSort(array, low, p);
			quickSort(array, p+1, high);
		}
	}
	// Divides the array in half, and sorts across this pivot point, returning low
	int partition(int[] array, int low, int high) {
		int pivot = array[low + (high-low) / 2];

		for(int i = low; i < high; i++) {
			if(array[i] < pivot) {
				int temp = array[i];
				array[i] = array[low];
				array[low] = temp;
				low++;
			}
		}

		// Make the high equal to the low, make the low equal to the pivot
		array[high] = array[low];
		array[low] = pivot;
		return low;
	}
	
	public static void main(String[] args) {
		new Sort();
	}
}