package Day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day07 {
	public static boolean tieBreak(String firstHand, String secondHand, String labels) {
		boolean firstWins = false;
		
		for(int i = 0; i < 5; i++) {
			if(firstWins) {
				break;
			}
			
			if(firstHand.charAt(i) == secondHand.charAt(i)) {
				//System.out.println(firstHand.charAt(i) + " " + secondHand.charAt(i));
				continue;
			}		
			
			for(int j = 0; j < labels.length(); j++) {
				//System.out.println(firstHand.charAt(i) + " " + labels.charAt(j));
				if(firstHand.charAt(i) == labels.charAt(j)) {
					firstWins = true;
					break;
				}
				else if(secondHand.charAt(i) == labels.charAt(j)) {
					break;
				}
			}
		}
		
		//System.out.println(firstWins);
		
		return firstWins;
	}
	
	public static void rankHands(int[][]cardCount, HashMap<Integer, Integer> typeMap) {
		for(int i = 0; i < cardCount.length; i++) {
			System.out.println("Game " + (i + 1));
			for(int j = 0; j < 13; j++) {
				//System.out.println(labels.charAt(j) + ": " + cardCount[i][j]);
			}
			switch(checkHands(cardCount[i])) {
			case 7:
				typeMap.put(i+1, 7);
				System.out.println("Five of a kind\n");
				break;
				
			case 6:
				typeMap.put(i+1, 6);
				System.out.println("Four of a kind\n");
				break;
				
			case 5:
				typeMap.put(i+1, 5);
				System.out.println("Full house\n");
				break;
				
			case 4:
				typeMap.put(i+1, 4);
				System.out.println("Three of a kind\n");
				break;
				
			case 3:
				typeMap.put(i+1, 3);
				System.out.println("Two pair\n");
				break;
				
			case 2:
				typeMap.put(i+1, 2);
				System.out.println("One pair\n");
				break;
				
			case 1:
				typeMap.put(i+1, 1);
				System.out.println("High card\n");
				break;
				
			default:
				break;
			}
		}
	}
	
	public static int checkHands(int[]cardCount) { //Checks what type of hand the game is
		int matchType = 1; //Default type is high card
		boolean houseFlagThrees = false;
		boolean houseFlagTwos = false;
		boolean twoPairFlag = false;
		
		for(int i = 0; i < cardCount.length; i++) {
			switch(cardCount[i]){
			case 5:
				matchType = 7; //Five of a kind
				break;
				
			case 4:
				matchType = 6; //Four of a kind
				break;
				
			case 3:
				matchType = 4; //Three of a kind
				houseFlagThrees = true; //Set flag to look for a full house
				break;
				
			case 2:
				if(twoPairFlag) {
					matchType = 3; //Two pair
				}
				else {
					matchType = 2; //One pair
					houseFlagTwos = true; //Set flag to look for a full house
					twoPairFlag = true; //Set flag to look for a second pair
				}
				
				break;
				
			default:
				break;
			}			
		}
		
		if(houseFlagThrees && houseFlagTwos) {
			matchType = 1; //Full house
		}
		
		return matchType;
	}
	
	public static void checkCards(ArrayList<String> cardHand, int[][] cardCount) {
		for(int i = 0; i < cardHand.size(); i++) { //Loops through all the card hands for every game and counts the different labels
			for(int j = 0; j < 5; j++) {
				switch(cardHand.get(i).charAt(j)) {
				case 'A':
					cardCount[i][0] += 1;
					break;
					
				case 'K':
					cardCount[i][1] += 1;
					break;
					
				case 'Q':
					cardCount[i][2] += 1;
					break;
					
				case 'J':
					cardCount[i][3] += 1;
					break;
					
				case 'T':
					cardCount[i][4] += 1;
					break;
					
				case '9':
					cardCount[i][5] += 1;
					break;
					
				case '8':
					cardCount[i][6] += 1;
					break;
					
				case '7':
					cardCount[i][7] += 1;
					break;
					
				case '6':
					cardCount[i][8] += 1;
					break;
					
				case '5':
					cardCount[i][9] += 1;
					break;
					
				case '4':
					cardCount[i][10] += 1;
					break;
					
				case '3':
					cardCount[i][11] += 1;
					break;
					
				case '2':
					cardCount[i][12] += 1;
					break;
					
				default:
					break;
				}
			}
		}
	}
	
	public static int populateGames(ArrayList<String> cardHand, ArrayList<Integer> bidAmount) { //Populates cardHand and bidAmount using our puzzle input
		int games = 0;
		
		try {
			File inputText = new File("./src/Day07/test.txt"); //Puzzle input saved to txt file
			Scanner scanner = new Scanner(inputText); //New scanner opened to scan the txt file
			
			while(scanner.hasNextLine()) { //Loop to continue while a new line is present
				String currLine = scanner.nextLine();
				
				cardHand.add(currLine.split(" ")[0]);
				bidAmount.add(Integer.parseInt(currLine.split(" ")[1]));
				games++;
			}					
			scanner.close();
		} catch (FileNotFoundException e) { //Catch value for if txt file with puzzle input is not found
			System.out.println("File not found.");
			e.printStackTrace();
		}
		
		return games;
	}

	public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> typeMap){ //Sorts the typeMap map and returns a sorted version, by value
		List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(typeMap.entrySet()); //New list using entries from typeMap
		
		//Sorts the list
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>(){
			public int compare(Map.Entry<Integer, Integer> o1, 
					Map.Entry<Integer, Integer> o2)
			{
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		
		HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
		for (Map.Entry<Integer, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		
		return temp;
	}
	
	public static void main(String[] args) {
		ArrayList<String> cardHand = new ArrayList<String>();
		ArrayList<Integer> bidAmount = new ArrayList<Integer>();
		HashMap<Integer, Integer> typeMap = new HashMap<Integer, Integer>();
		String labels = "AKQJT98765432";
		int totalEarnings = 0;
		
		int games = populateGames(cardHand, bidAmount);
		//System.out.println(cardHand);
		//System.out.println(bidAmount);
		
		int[][] cardCount = new int[games][13];
		int[] gamesRank = new int[games];
		for(int i = 0; i < games; i++) {
			Arrays.fill(cardCount[i], 0);
			//System.out.println(cardCount[i][0]);
		}
		Arrays.fill(gamesRank, 0);
		
		checkCards(cardHand, cardCount);
		
		rankHands(cardCount, typeMap);
		//System.out.println(typeMap);

		Set<Integer> temp2 = typeMap.keySet();
		int[] unsortKeySet = new int[games];
		int in = 0;
		for(Integer key : temp2) {
			unsortKeySet[in] = key;
			in++;
		}
		
		Map<Integer, Integer> sortedTypeMap = sortByValue(typeMap); //Sorted by value (hand rank)
		
		Set<Integer> temp = sortedTypeMap.keySet();
		int[] keySet = new int[games];
		in = 0;
		for(Integer key : temp) {
			keySet[in] = key;
			in++;
		}
		System.out.println(sortedTypeMap);
		
		Collection<Integer> temp3 = sortedTypeMap.values();
		Integer[] valueSet = temp3.toArray(new Integer[temp3.size() + 1]);

		for(int i = 0; i < games; i++) {
			//System.out.println(valueSet[i]);
			if(valueSet[i] != valueSet[i + 1]) {
				gamesRank[i] = unsortKeySet[i];
			}
			else {
				String first = "";
				String second = "";
				int firstN = 0;
				int secondN = 0;
				
				for(int j = 0; j < games; j++) {
					if(keySet[i] == unsortKeySet[j]) {
						first = cardHand.get(j);
						firstN = j;
					}
					else if(keySet[i + 1] == unsortKeySet[j]) {
						second = cardHand.get(j);
						secondN = j;
					}
					if((first.compareTo("") != 0) && (second.compareTo("") != 0)) {
						break;
					}
				}
				
				//System.out.println(first + " " + second);
				//System.out.println(firstN + " " + secondN);
				//System.out.println(unsortKeySet[i + 1] + " " + unsortKeySet[i]);
				//System.out.println(keySet[firstN] + " " + keySet[secondN]);
				
				if(tieBreak(first, second, labels)) {
					gamesRank[i] = unsortKeySet[secondN];
					gamesRank[i + 1] = unsortKeySet[firstN];
				}
				else {
					gamesRank[i] = unsortKeySet[firstN];
					gamesRank[i + 1] = unsortKeySet[secondN];
				}
				i++;
			}
		}
		for(int i = 0; i < games; i++) {
			System.out.println("Game " + unsortKeySet[i] + "(" + 
		cardHand.get(i) + ") gets rank " + gamesRank[i] + " and earns " 
					+ (gamesRank[i] * bidAmount.get(i)) + " points");
			totalEarnings += (gamesRank[i] * bidAmount.get(i));
		}
		
		System.out.println("\nYour total earnings are " + totalEarnings + " points");
	}
}
