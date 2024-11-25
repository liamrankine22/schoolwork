public class Counter {
	PowerSet<Card> cardps;
	Card starter;
	//intializes powerset and the starter card
	
	public Counter(Card[] hand, Card starter) {
		this.starter = starter;
		this.cardps = new PowerSet<>(hand);
		//COnstructor that takes in a card object called hand and a card object called starter it then assignes starter to the class
		//starter and then assigns hand to a powerset
	}
	
	public int countPoints() {
		int points = 0;
		
		points += countRuns();
		points += countFifteen();
		points += countKnobs();
		points += countFlushes();
		points += countPairs();
		
		return points;
		//Adds all the points from each of the counter and returns it to the user
	}
	
	private boolean isRun(Set<Card> set) {
		int n = set.getLength();
		
		if (n <= 2) return false; // Run must be at least 3 in length.
		
		int[] rankArr = new int[13];
		for (int i = 0; i < 13; i++) rankArr[i] = 0; // Ensure the default values are all 0.
		
		for (int i = 0; i < n; i++) {
			rankArr[set.getElement(i).getRunRank()-1] += 1;
		}

		// Now search in the array for a sequence of n consecutive 1's.
		int streak = 0;
		int maxStreak = 0;
		for (int i = 0; i < 13; i++) {
			if (rankArr[i] == 1) {
				streak++;
				if (streak > maxStreak) maxStreak = streak;
			} else {
				streak = 0;
			}
		}
		if (maxStreak == n) { // Check if this is the maximum streak.
			return true;
		} else {
			return false;
		}
		
	}

	private int countRuns() {
		int longestRun = 0;
		int longestRunCount = 0;
		int points = 0;
		//Initializes the longestRun, longestRunCount and points variables for use withing the method
		for (int i = 0; i < cardps.getLength(); i++) {
			if (isRun(cardps.getSet(i)) == true) {
				if (cardps.getSet(i).getLength()>longestRun) {
					longestRun = cardps.getSet(i).getLength();
					longestRunCount = 0;
					longestRunCount += 1;
					//Uses for loop to go through every subset then uses if statements to check if the subset is a run and it if it
					// if its greater than the longest run and if so it replaces it
				
				}
				else if (cardps.getSet(i).getLength() == longestRun) {
					longestRunCount += 1;
					//If its notgreater it checks if its equal to it and increases the count by one
				}
			}
		}
		points = longestRun * longestRunCount;
		return points;
		//Tallies the points by multiplying the number of runs by the size of it and returns the points to the counter
	}
	
	private int countFifteen() {
		int points = 0;
		int count = 0;
		//INitializes the points and count variables for use within the method
		for (int i = 0; i < cardps.getLength(); i++) {
			count = 0;
			for (int p = 0; p < cardps.getSet(i).getLength(); p++) {
				count += cardps.getSet(i).getElement(p).getFifteenRank();
				//Uses a for loop to go through every subset and resets the count to 0 then uses another for loop to get the length
				//of the set and raises the count for the fifteenrank of each card within the set
				
			}
			if (count == 15) {
				points += 2;
				//If the count within the hand of cards is equal to 15 the points are raised by 2
			}
		}
		return points;
		//Returns the amount of points when the method is finished
	}
	
	private int countKnobs() {
		int points = 0;
		//initializes points variable
		for (int i = 0; i < cardps.getLength(); i++) {
			if (!cardps.getSet(i).contains(starter)) {
				if (cardps.getSet(i).getLength() == 4) {
					for (int p = 0; p < cardps.getSet(i).getLength();p++) {
						if (cardps.getSet(i).getElement(p).getLabel() == "J") {
							if (cardps.getSet(i).getElement(p).getSuit() == starter.getSuit()) {
								points += 1;
								//Uses for loop to go through each subset and checks if one of the cards in the set is the starter
								//If not it continues and checks if the subset has a length of 4, if it does it moves on and checks
								//If theres a Jack in the set and then checks if the starter has the same suit as the jack
							}
						}
					}
				}
			}
		}
		return points;
		//returns points to the user
	}
	
	private int countFlushes() {
        int points = 0;
        int count = 0;
        String suit = null;
        //initializes starting variables
        for (int i = 0; i < cardps.getLength(); i++) {
            if (!cardps.getSet(i).contains(starter) && cardps.getSet(i).getLength() == 4) {
                suit = null;
                count = 0;
                for (int p = 0; p < cardps.getSet(i).getLength();p++) {
                    if (suit == null) {
                        suit = cardps.getSet(i).getElement(p).getSuit();
                    }
                    if (cardps.getSet(i).getElement(p).getSuit() == suit) {
                        count += 1;
                    }
                    //Begins with a for loop that goes through every subset and then checks if the set contains starter and the length
                    // is 4. If so it sets the suit to null and count to 0 for reset after the upcoming for loop which checks if the
                    //suit is null and if so sets the suit to beginning card. Then checks if every other card is equal to that suit
                    //If it is is added to the count
                }
                if (starter.getSuit() == suit) {
                    count += 1;
                }
                if (count == 4) {
                    points = 4;
                    return points;
                }
                else if (count == 5) {
                    points = 5;
                    return points;
                    //These next if statements are to check if the starter's suit is equal to the set suit at the beginning and
                    //If the count is equal to 4, if so the amount of points are set to 4 and returned. If its 5 the amount of points
                    //Is set to 5 and is returned to the counter
                }
            }
        }
        return points;
    }
	
	
	
	private int countPairs() {
		int points = 0;
		//initializes points
		String label = null;
		for (int i = 0; i < cardps.getLength(); i++) {
			if (cardps.getSet(i).getLength() == 5) {
			label = null;
			for (int p = 0; p < cardps.getSet(i).getLength();p++){
				if(label == null) {
					label = cardps.getSet(i).getElement(p).getLabel();
				}
				else if (cardps.getSet(i).getElement(p).getLabel().equals(label)) {
					points += 2;
					//Uses a foor loop to go through each sub set then checks if the subset has a length of 5 and sets the label to
					//null. Then uses another for loop for the length of each subset and checks if label is null, then sets the next
					//set's label to the new label and checks every other card in the subset if it has that label and adds 2 points
					//If it does.
				}
			}	
			}
		}
	return points;
	//Returns the points to the counter
	}
	
}
