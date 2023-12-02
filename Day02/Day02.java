package Day02;

import java.util.Scanner;
import java.io.*;

public class Day02 {
	public static void main(String[] args) {
		int redCubes = 12; //Keeps track of all the cubes inside the bag
		int greenCubes = 13;
		int blueCubes = 14;
		int gameId = 1;
		int gameIdSum = 0;
		boolean impossibleFlag = false; //Flag to set if a game is impossible
		
		int fewestRed = 0; //Keeps track of the lowest number of each cube color
		int fewestBlue = 0;//to find the the fewest number of cubes to play each game
		int fewestGreen = 0;
		int gamePowerSum = 0;
		
		try {
			File inputText = new File("./src/Day02/Day02Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String currGame = scanner.nextLine(); //Grabs the text for the current game
				String[] temp = currGame.split(": "); //Each game starts with "Game xx:", this splits the string to remove that
				String[] gameRounds = temp[1].split("; "); //Splits each game into rounds to look at separately
				
				System.out.println("Game " + gameId + ":");
				
				for(int i = 0; i < gameRounds.length; i++) { //For each game, iterate through each round
					System.out.println("Round " + i + ":");
					String[] colors = gameRounds[i].split(", "); //Splits the current round to look at by each cube color
					for(int j = 0; j < colors.length; j++) { //For each round, iterate through each cube color
						System.out.println(colors[j]);
						int cubeCount = 0;
						if(Character.isDigit(colors[j].charAt(1))) { //Parses double digit number to new variable
							cubeCount = Integer.parseInt(colors[j].substring(0, 2));
						}
						else { //Parses single digit number to new variable
							cubeCount = Character.getNumericValue(colors[j].charAt(0));
						}
						if(colors[j].contains("red")){ //Checks which color the cubes are, then checks if the count is higher than how many are in the bag
							if(fewestRed < cubeCount) { //Checks if the current fewest red count is smaller than the amount of cubes here
								fewestRed = cubeCount; //Updates fewest red, if so
							}
							if(cubeCount > redCubes) {
								impossibleFlag = true;
								System.out.println("Too many red cubes");
								//break; //Breaks if there's more cubes in hand than in the bag. Round is impossible
							}
						}
						else if(colors[j].contains("blue")){
							if(fewestBlue < cubeCount) {
								fewestBlue = cubeCount;
							}
							if(cubeCount > blueCubes) {
								impossibleFlag = true;
								System.out.println("Too many blue cubes");
								//break;
							}
						}
						else if(colors[j].contains("green")){
							if(fewestGreen < cubeCount) {
								fewestGreen = cubeCount;
							}
							if(cubeCount > greenCubes) {
								impossibleFlag = true;
								System.out.println("Too many green cubes");
								//break;
							}
						}
					}
					/*if(impossibleFlag) { //Breaks out of the whole game if a round was found impossible
						break;
					}*/
				}
				if(!impossibleFlag) {
					System.out.println("Legal game");
					gameIdSum += gameId;
				}
				System.out.println("This game was playable with as few as " + fewestRed + " red cubes, "
						+ fewestBlue + " blue cubes, and " + fewestGreen + " green cubes.\n");
				
				//if(fewestRed)
				
				gameId++;
				gamePowerSum += fewestRed * fewestGreen * fewestBlue; //Updates the sum of powers with this game's power
				impossibleFlag = false;
				fewestRed = 0;
				fewestGreen = 0;
				fewestBlue = 0;
			}
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		System.out.println("The sum of all legal games is " + gameIdSum);
		System.out.println("The sum of all game's powers is " + gamePowerSum);
	}
}
