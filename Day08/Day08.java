package Day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day08 {
	public static int getPath(String directions, HashMap<String, String> rightNodes, HashMap<String, String> leftNodes) { //Uses the directions string walk while determining to pull from the right or left nodes
		int steps = 0;
		String currentNode = "AAA";
		String endNode = "ZZZ";
		
		ArrayList<String> pathTraveled = new ArrayList<String>(); //Keeps track of all the nodes we have visisted
		pathTraveled.add(currentNode);
		
		while(true) {
			for(int i = 0; i < directions.length(); i++) {
				if(directions.charAt(i) == 'L') { //Pull the value from the left nodes map
					currentNode = leftNodes.get(currentNode);
					steps++;
				}
				else { //Pull the value from the right nodes map
					currentNode = rightNodes.get(currentNode);
					steps++;
				}
				System.out.println(currentNode);
				pathTraveled.add(currentNode); //Adds the current node to our grand journey
			}
			if(pathTraveled.lastIndexOf(endNode) != -1) {
				break; //Breaks out of the while loop if the end node exists in the journey
			}
		}
		
		return steps;
	}
	
	public static String populateMaps(HashMap<String, String> rightNodes, HashMap<String, String> leftNodes) { //Populates our maps and returns the set of direction instructions
		String directions = "";
		
		try {
			File inputText = new File("./src/Day08/Day08Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			boolean needsDirections = true;
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String currLine = scanner.nextLine();
				
				if(((currLine.charAt(0) == 'R') //Copies the list of directions to a string
						|| (currLine.charAt(0) == 'L'))
						&& needsDirections) {
					directions = directions.concat(currLine);
					needsDirections = false; //Ensures we don't accidentally put nodes into the directions string
					currLine = scanner.nextLine(); //Skips the next line, which is just indentations
				}
				else {
					currLine = currLine.replace("(", ""); //Cleans up the strings a bit
					currLine = currLine.replace(")", "");
					
					String temp[] = currLine.split(" = "); // Separates the current node from the left and right next nodes
					String temp2[] = temp[1].split(", "); //Separates the left and right next nodes
					//System.out.println(temp[0] + "-" + temp2[0] + "," + temp2[1]);
					
					leftNodes.put(temp[0], temp2[0]); //temp[0] = current node, temp2[0] = left node
					rightNodes.put(temp[0], temp2[1]); //temp[0] = current node, temp2[1] = right node
				}
			}					
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		return directions;
	}
	
	public static void main(String[] args) {
		HashMap<String, String> rightNodes = new HashMap<String, String>();
		HashMap<String, String> leftNodes = new HashMap<String, String>();
		String directions = populateMaps(rightNodes, leftNodes);	
		System.out.println(directions + "\n" + leftNodes + "\n" + rightNodes);
		
		int steps = getPath(directions, rightNodes, leftNodes);		
		System.out.println("This journey took " + steps + " steps");
	}
}
