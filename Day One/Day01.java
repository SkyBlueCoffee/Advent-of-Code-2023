package Day01;
import java.util.Scanner;
import java.io.*;

public class Day01 {
	public static void main(String[] args) {
		int digitOne = 0; //Integers to hold the scanned digits
		int digitTwo = 0;
		int newDigit = 0;
		int calibrationDigit = 0; //Total is the calibration digit	
		
		try {
			File inputText = new File("./src/Day01/Day01Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String one = scanner.nextLine(); //Replaces all spelled out numbers with their numeric counterparts
				String two = one.replace("one", "o1e");
				String three = two.replace("two", "t2o");
				String four = three.replace("three", "th3ee");
				String five = four.replace("four", "f4ur");
				String six = five.replace("five", "fi5e");
				String seven = six.replace("six", "s6x");
				String eight = seven.replace("seven", "se7en");
				String nine = eight.replace("eight", "ei8ht");
				String curLine = nine.replace("nine", "ni9e");
				
				for(int i = 0; i < curLine.length(); i++) {
					if(Character.isDigit(curLine.charAt(i))) { //Checks if current index in for loop is a digit
						if(digitOne == 0) { //Only sets digit one if it hasn't been set yet
							digitOne = Character.getNumericValue(curLine.charAt(i));
							digitTwo = Character.getNumericValue(curLine.charAt(i));
						}
						else { //Sets digit two on each numberic hit
							digitTwo = Character.getNumericValue(curLine.charAt(i));
						}
					}
				}
				newDigit = (digitOne * 10) + digitTwo; //Combines digit one, times ten, with digit two for complete two digit value
				calibrationDigit += newDigit; //Adds the two digit value of this line to the total calibration digit
				System.out.println(newDigit);
				digitOne = 0; //Resets all values to zero to ensure correct value setting.
				digitTwo = 0;
				newDigit = 0;
			}
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		System.out.println("Calibration Digit is " + calibrationDigit); //Prints the final calibration digit to terminal
	}
}
