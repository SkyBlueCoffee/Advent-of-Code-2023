package Day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day04 {	
	public static int getNumberOfCards() { //Quick function to know how many instances of two-dimension array lists to initialize
		int numberOfCards = 0;
		try {
			File inputText = new File("./src/Day04/Day04Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String currLine = scanner.nextLine();
				numberOfCards++;
			}
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		return numberOfCards;
	}
	
	public static int partOne(ArrayList<ArrayList<Integer>> cardNumbers, ArrayList<ArrayList<Integer>> winningNumbers, int numberOfCards) {
		int pointSum = 0;
		for(int i = 0; i < numberOfCards; i++) {
			int power = 0;
			//System.out.println(cardNumbers.get(i));
			//System.out.println(winningNumbers.get(i));
			System.out.println("Card " + (i+1) + " pairs found:");
			for(int j = 0; j < winningNumbers.get(i).size(); j++) { //Compares all numbers in winningNumbers to all numbers in cardNumbers
				for(int k = 0; k < cardNumbers.get(i).size(); k++) {
					if(winningNumbers.get(i).get(j).compareTo((cardNumbers.get(i).get(k))) == 0){
						if(power == 0) { //Amount of points is doubled for each matching pair
							power = 1;
						}
						else {
							power *= 2;
						}						
						System.out.println(winningNumbers.get(i).get(j) + " " + cardNumbers.get(i).get(k));
					}
				}
			}
			System.out.println("Card " + (i + 1) + " worth " + power + " points.\n");
			pointSum += power;					
		}
		
		return pointSum;
	}
		
	public static int findMatchesTwo(ArrayList<Integer> cardNumbers, ArrayList<Integer> winningNumbers) {
		int matches = 0;
		for(int j = 0; j < winningNumbers.size(); j++) {
			for(int k = 0; k < cardNumbers.size(); k++) {
				if(winningNumbers.get(j).compareTo((cardNumbers.get(k))) == 0){
					matches++;
					//System.out.println(winningNumbers.get(i).get(j) + " " + cardNumbers.get(i).get(k));
				}
			}
		}
		
		return matches;
	}
	
	public static int partTwo(ArrayList<ArrayList<Integer>> cardNumbers, ArrayList<ArrayList<Integer>> winningNumbers, int numberOfCards) {
		int newCards = 0;
		int cardInstances[] = new int [numberOfCards+1]; //Keeps track of how many of each card we have
		Arrays.fill(cardInstances, 1);
		
		int matches[] = new int[numberOfCards]; //Array to keep track of how many matches each card has
		
		for(int i = 0; i < numberOfCards; i++) {
			matches[i] = findMatchesTwo(cardNumbers.get(i), winningNumbers.get(i)); //Populates our array with the total match for each card
			if(matches[i] > 0) { //If there are matches
				for(int j = 1; j <= matches[i]; j++) { //Calculate how many instances of the next card should be added
					cardInstances[j + i] += cardInstances[i];
				}
			}
		}
		
		for(int i = 0; i < numberOfCards; i++) { //User output, plus calculating the total number of cards
			System.out.println("Card " + (i + 1) + ": " + matches[i] + " matches, " + cardInstances[i] + " copies.");
			newCards += cardInstances[i];
		}
		
		return newCards;
	}
	
	public static void main(String [] args) {
		ArrayList<ArrayList<Integer>> cardNumbers = new ArrayList<>();
		ArrayList<ArrayList<Integer>> winningNumbers = new ArrayList<>();
		
		int numberOfCards = getNumberOfCards();
		for(int i = 0; i < numberOfCards; i++) {
			cardNumbers.add(new ArrayList());
			winningNumbers.add(new ArrayList());
		}
		
		int currentCard = 0;
		
		try {
			File inputText = new File("./src/Day04/Day04Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String temp = scanner.nextLine();
				String[] temp2 = temp.split(":");
				String temp3 = temp2[1].stripLeading(); //Cleans up the string a little to parse better
				String currLine = temp3.concat(" "); //For some reason my code is ignoring the last number, so this trailing white space is to give the code a new index to end on
				int i = 0;
				int length = 0; //Keeps track of the length of the number to parse
				
				//System.out.println(currLine);
				
				while(currLine.charAt(i) != '|') { //Loops through the list of winning numbers
					if(Character.isDigit(currLine.charAt(i))) { //Keeps track of the length of the number
						length++;
					}
					else if (length > 0){ //Adds the number to the list of winning numbers
						winningNumbers.get(currentCard).add(Integer.parseInt(currLine.substring((i - length), i)));
						length = 0;
					}
					i++;					
				}
				
				length = 0;
				
				for(int j = i; j < currLine.length(); j++) {
					if(Character.isDigit(currLine.charAt(j))) { //Keeps track of the length of the number
						length++;
					}
					else if (length > 0){ //Adds the number to the list of winning numbers
						cardNumbers.get(currentCard).add(Integer.parseInt(currLine.substring((j - length), j)));
						length = 0;						
					}
				}
				currentCard++;
			}
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		System.out.println("This pile of scratchcards is worth " + partOne(cardNumbers, winningNumbers, numberOfCards) + " points!\n");
		System.out.println("You have collected " + partTwo(cardNumbers, winningNumbers, numberOfCards) + " total cards!");
	}
}
