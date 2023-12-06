package Day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day06 {
	public static int populateRaces(ArrayList<Long> raceTime, ArrayList<Long> raceDistance) { //Populates raceTime and raceDistance using our puzzle input
		int raceCount = 0;
		
		try {
			File inputText = new File("./src/Day06/Day06Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String temp = scanner.nextLine();
				temp = temp.concat(" "); //Adds trailing whitespace to catch all characters
				String[] currLine = temp.split(":");
				int length = 0; //Keeps track of the length of the number to parse
				
				if(currLine[0].compareTo("Time") == 0) { //Sign to populate the raceTime arraylist
					for(int i = 0; i < currLine[1].length(); i++) {
						if(Character.isDigit(currLine[1].charAt(i))) { //Keeps track of the length of the number
							length++;
						}
						else if (length > 0){ //Adds the number to the list of winning numbers
							raceTime.add(Long.parseLong(currLine[1].substring((i - length), i)));
							length = 0;
							raceCount++; //Increments the race count
						}
					}						
				}
				else if(currLine[0].compareTo("Distance") == 0) { //Sign to populate the raceDistance arraylist
					for(int i = 0; i < currLine[1].length(); i++) {
						if(Character.isDigit(currLine[1].charAt(i))) { //Keeps track of the length of the number
							length++;
						}
						else if (length > 0){ //Adds the number to the list of winning numbers
							raceDistance.add(Long.parseLong(currLine[1].substring((i - length), i)));
							length = 0;
						}
					}
				}
			}					
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		return raceCount;
	}
	
	public static void populateRacesTwo(ArrayList<Long> raceTime, ArrayList<Long> raceDistance) { //Populates raceTime and raceDistance using part two's requirements
		try {
			File inputText = new File("./src/Day06/Day06Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String temp = scanner.nextLine();
				temp = temp.concat("e"); //Adds trailing letter to signify the end of the number to record
				String[] currLine = temp.split(":");
								
				String time = "";
				String distance = "";
				
				if(currLine[0].compareTo("Time") == 0) { //Sign to populate the raceTime arraylist
					for(int i = 0; i < currLine[1].length(); i++) {
						if(Character.isDigit(currLine[1].charAt(i))) { //Concatenates the digits into a single string
							time = time.concat(currLine[1].substring(i, i + 1));
						}
						else if (currLine[1].charAt(i) == 'e'){ //The trailing 'e' signifies it's time to parse our string to add to raceTime
							raceTime.add(Long.parseLong(time));
						}
					}						
				}
				else if(currLine[0].compareTo("Distance") == 0) { //Sign to populate the raceDistance arraylist
					for(int i = 0; i < currLine[1].length(); i++) {
						if(Character.isDigit(currLine[1].charAt(i))) { //Concatenates the digits into a single string
							distance = distance.concat(currLine[1].substring(i, i+1));
						}
						else if (currLine[1].charAt(i) == 'e'){ //The trailing 'e' signifies it's time to parse our string to add to raceDistance
							raceDistance.add(Long.parseLong(distance));
						}
					}
				}
			}					
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
	
	public static void calculateRaces(ArrayList<Long> raceTime, ArrayList<Long> raceDistance, ArrayList<ArrayList<Integer>> waysToWin) { //Calculates what charge times result in beating all the race's records, and populates waysToWin with those values
		int charge = 0;
		int distance = 0;
		
		for(int i = 0; i < raceTime.size(); i++) { //Loops through all entries in the raceTime arraylist
			for(int j = 0; j < raceTime.get(i); j++) { //Loops from 0 to the number saved in the raceTime at the current index
				charge = j; //How long the toy boat's button is being held
				for(int k = 0; k < raceTime.get(i) - charge; k++) { //Loops from 0 milliseconds to the amount of milliseconds left of the race
					distance += charge; //For each millisecond left in the race, increment our distance by the power of charge
				}
				if(distance > raceDistance.get(i)){ //If the distance our toy boat went is higher than the race's record, add it to our arrayList of winning charge times
					waysToWin.get(i).add(j);
				}
				distance = 0;
			}
		}
	}
	
	public static int calculateRacesTwo(long raceTime, long raceDistance) { //Calculates how many possible ways to win there are for the single race's chrage time and record
		double start = 0;
		double end = 0;
		
		start = Math.ceil((raceTime - Math.sqrt(Math.pow(raceTime, 2) - 4 * raceDistance)) / 2); //Math
		end = Math.floor((raceTime + Math.sqrt(Math.pow(raceTime, 2) - 4 * raceDistance)) / 2);

		return ((int)end - (int)start + 1); //Returns the total amount of times that could beat the record
	}
	
	public static void main(String[] args) {
		ArrayList<Long> raceTime = new ArrayList<>();
		ArrayList<Long> raceDistance = new ArrayList<>();
		ArrayList<ArrayList<Integer>> waysToWin = new ArrayList<>();
		
		int wonRacesProduct = 1;
		int raceCount = populateRaces(raceTime, raceDistance);
		long totalWaysToWin = 0;
		//System.out.println(raceCount);
		//System.out.println(raceTime);
		//System.out.println(raceDistance);
		
		for(int i = 0; i < raceCount; i++) { //Initializes our waysToWin array list of array lists
			waysToWin.add(new ArrayList());
		}	
		
		calculateRaces(raceTime, raceDistance, waysToWin);
		//System.out.println(waysToWin);
		
		for(int i = 0; i < waysToWin.size(); i++) {
			int totalWonRaces = 0;
			System.out.println("Hold times to win race " + (i+1) + ": " + waysToWin.get(i));
			for(int j = 0; j < waysToWin.get(i).size(); j++) {
				totalWonRaces++;
			}
			System.out.println("There are " + totalWonRaces + " ways to win this race.");
			wonRacesProduct *= totalWonRaces;
		}
		
		System.out.println("There are " + wonRacesProduct + " total ways to win.\n");

		raceTime.clear(); //Clears our array lists to use again
		raceDistance.clear();
		
		populateRacesTwo(raceTime, raceDistance);
		//System.out.println(raceTime);
		//System.out.println(raceDistance);
		
		totalWaysToWin = calculateRacesTwo(raceTime.get(0), raceDistance.get(0));
		
		System.out.println("There are " + totalWaysToWin + " total hold times to beat the record " + raceDistance.get(0));

	}
}
