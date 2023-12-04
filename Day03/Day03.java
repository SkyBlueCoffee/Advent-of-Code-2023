package Day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day03 {
	public static int getLineSize() { //This is solely to get the line size to create a multi dimensional list with
		int lineLength = 0;
		try {
			File inputText = new File("./src/Day03/Day03Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			String line = scanner.nextLine(); 
			lineLength = line.length();
			
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		} 
		
		return lineLength;
	}
	
	public static void main(String[] args) {
		int partNumberSum = 0;
		int lineLength = getLineSize();
		int puzzleDataLength = 0;
		int gearRatioSum = 0;
		
		ArrayList<ArrayList<Character>> puzzleDataNumbers = new ArrayList<>(lineLength); //Two lists of strings, one to hold all the numbers in the puzzle data and one to hold the symbols
		ArrayList<ArrayList<Character>> puzzleDataSymbols = new ArrayList<>(lineLength);
		HashMap<Integer, Integer> gearMap = new HashMap<>();
		HashMap<Integer, Integer> gearValueMap = new HashMap<>();
		
		for(int i = 0; i < lineLength; i++) {
			puzzleDataNumbers.add(new ArrayList());
			puzzleDataSymbols.add(new ArrayList());
		}
		
		String symbolString = "!@#$%^&*()_-+={[}]\\|;:'\",<>?/`~";
		char symbolGear = '*';
		List<Character> symbolList =  new ArrayList<Character>(); //A list of symbols to compare string input to
		for(int i = 0; i < symbolString.length(); i++) {
			symbolList.add(symbolString.charAt(i));
		}
		
		try {
			File inputText = new File("./src/Day03/Day03Text.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String currLine = scanner.nextLine();
				
				puzzleDataNumbers.get(puzzleDataLength).add('.'); //Leading character to help avoid null pointers later
				puzzleDataSymbols.get(puzzleDataLength).add('.');
				
				for(int i = 0; i < currLine.length(); i++) {
					if(Character.isDigit(currLine.charAt(i))) { //If the index character is a digit, append it to the num string builder and a period to the sym string builder
						puzzleDataNumbers.get(puzzleDataLength).add(currLine.charAt(i));
						puzzleDataSymbols.get(puzzleDataLength).add('.');
					}
					else if(symbolList.contains(currLine.charAt(i))) { //Do the vice versa if the index character is held within the symbol list string
						puzzleDataSymbols.get(puzzleDataLength).add(currLine.charAt(i));
						puzzleDataNumbers.get(puzzleDataLength).add('.');
					}
					else { //Append a period to both if the index character is a period
						puzzleDataNumbers.get(puzzleDataLength).add('.');
						puzzleDataSymbols.get(puzzleDataLength).add('.');
					}
				}
				puzzleDataNumbers.get(puzzleDataLength).add('.'); //Trailing character to help avoid null pointers later
				puzzleDataSymbols.get(puzzleDataLength).add('.');
				
				puzzleDataLength++; //Keeping track of the actual length of the puzzle data, and additionally the current column while looping
			}
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}		
		
		//System.out.println("All the numbers in the puzzle data:\n" + puzzleDataNumbers);
		//System.out.println("All the symbols in the puzzle data:\n" + puzzleDataSymbols);

		for(int i = 0; i < puzzleDataNumbers.size(); i++) { //Looping through the columns of the multi directional arraylist of just the numbers
			int length = 0;
			int num = 0;
			int power = 1;
			for(int j = 0; j < puzzleDataNumbers.get(i).size(); j++) { //Loops through the rows of said array list
				if(Character.isDigit(puzzleDataNumbers.get(i).get(j))){ //While the index character is numeric, keep track of how many digits there are
					length++;
				}
				else if(length > 0) { //If the current index is not a number, and length isn't zero, the full number has been pulled
					for(int k = j - 1; k >= (j - length); k--) { //Loop to grab our numbers
						num += Character.getNumericValue(puzzleDataNumbers.get(i).get(k)) * (power); //Combines the numeric characters into a proper int
						power *= 10; //For each loop, power is increased by ten for proper digit placement
					}
					for(int k = (j - length - 1); k < j + 1; k++) { //Loop to compare the values in the symbols arraylist to find special characters
						if(i == 0) { //Edge case for the first entry, to only compare the first and second entries
							if(puzzleDataSymbols.get(i).get(k) != '.' 
									|| puzzleDataSymbols.get(i+1).get(k) != '.') {
								partNumberSum += num; //If a special character (i.e not a period) is found in the adjacent indexes, add that number to our sum.
							}
							
							if(puzzleDataSymbols.get(i).get(k) == symbolGear) { //If a gear symbol (*) is found, records the number and the coordiantes of the gear symbol
								gearMap.merge(((i * 1000) + k), 1, Integer::sum);
								gearValueMap.put(num, ((i * 1000) + k));
							}
							else if(puzzleDataSymbols.get(i+1).get(k) == symbolGear) {
								gearMap.merge((((i + 1) * 1000) + k), 1, Integer::sum);
								gearValueMap.put(num, (((i + 1) * 1000) + k));
							}
						}
						else if(i == puzzleDataNumbers.size() - 1) { //Edge case for the last entry, to only compare the last and next-to-last entries
							if(puzzleDataSymbols.get(i).get(k) != '.' 
									|| puzzleDataSymbols.get(i-1).get(k) != '.') {
								partNumberSum += num;
								
								if(puzzleDataSymbols.get(i).get(k) == symbolGear) {
									gearMap.merge(((i * 1000) + k), 1, Integer::sum);
									gearValueMap.put(num, ((i * 1000) + k));
								}
								else if(puzzleDataSymbols.get(i-1).get(k) == symbolGear) {
									gearMap.merge((((i - 1) * 1000) + k), 1, Integer::sum);
									gearValueMap.put(num, (((i - 1) * 1000) + k));
								}
							}
						}
						else { //Compares the entry, entry before, and entry after
							if(puzzleDataSymbols.get(i).get(k) != '.' 
									|| puzzleDataSymbols.get(i+1).get(k) != '.' 
									|| puzzleDataSymbols.get(i-1).get(k) != '.') {
								partNumberSum += num;
							}
							
							if(puzzleDataSymbols.get(i).get(k) == symbolGear) {
								gearMap.merge(((i * 1000) + k), 1, Integer::sum);
								gearValueMap.put(num, ((i * 1000) + k));
							}
							else if(puzzleDataSymbols.get(i-1).get(k) == symbolGear) {
								gearMap.merge((((i - 1) * 1000) + k), 1, Integer::sum);
								gearValueMap.put(num, (((i - 1) * 1000) + k));
							}
							else if(puzzleDataSymbols.get(i+1).get(k) == symbolGear) {
								gearMap.merge((((i + 1) * 1000) + k), 1, Integer::sum);
								gearValueMap.put(num, (((i + 1) * 1000) + k));
							}
						}
					}
					length = 0; //Reset our digit length to zero
				}				
				num = 0; //Reset the held number and number's power to default
				power = 1;
			}
		}
		
		System.out.println("The sum of all the part numbers is: " + partNumberSum);
		
		//System.out.println(gearMap);
		//System.out.println(gearValueMap);
		
		ArrayList<Integer> pairGearsCoords = new ArrayList<>();
		ArrayList<Integer> pairGearsValues = new ArrayList<>();

		
		for(Iterator<Map.Entry<Integer, Integer>> it = gearMap.entrySet().iterator(); it.hasNext(); ) { //Removes all keys that have a value that isn't 2, in gearMap and gearValueMap
			Map.Entry<Integer, Integer> entry = it.next();
		    if(!entry.getValue().equals(2)) {
		        it.remove();
		    }
		    else {
		    	for(Iterator<Map.Entry<Integer, Integer>> it2 = gearValueMap.entrySet().iterator(); it2.hasNext(); ) {
		    		Map.Entry<Integer, Integer> entry2 = it2.next();
		    		if(entry2.getValue().equals(entry.getKey())) { //Places the resulting coordinates and values into two new arraylists to be compared once again
		    			//System.out.println(entry.getValue() + " " + entry.getKey());
		    			//System.out.println(entry2.getKey() + " " + entry2.getValue());
		    			pairGearsCoords.add(entry.getKey());
		    			pairGearsValues.add(entry2.getKey());
		    		}
		    	}
		    }
		}
		
		for(int i = 0; i < pairGearsCoords.size() - 1; i++) { //Loops through the two array lists, which are ordered slightly and only contain values counted twice, and multiplies them together 
			if(pairGearsCoords.get(i) == pairGearsCoords.get(i+1)) {
				//System.out.println(pairGearsCoords.get(i) + " " + pairGearsValues.get(i));
				//System.out.println(pairGearsCoords.get(i+1) + " " + pairGearsValues.get(i+1));
				gearRatioSum += pairGearsValues.get(i) * pairGearsValues.get(i + 1);
			}
		} //This is so scuffed
		
		System.out.println("The sum of all the gear ratios is: " + gearRatioSum);		
	}
}