public class Configurations  {
	
	private int board_size;
	private int lengthToWin;
	private int max_levels;
	private HashDictionary hDict;
	private char[][] board;
	//initializes variables for use within the program
	
	public Configurations (int board_size, int lengthToWin, int max_levels) {
		this.board_size = board_size;
		this.lengthToWin = lengthToWin;
		this.max_levels = max_levels;
		//sets local variables with their values
		
		board = new char[board_size][board_size];
		for(int i = 0;i < board_size;i++) {
			for(int p = 0;p < board_size;p++) {
				board[i][p] = ' ';
			}
			//initializes a double array called board and all values within setting them all to ' '
		}
	}
	
	public HashDictionary createDictionary() {
		HashDictionary hDict = new HashDictionary(7951);
		
		return hDict;
		//creates a new hashdictionary and returns it to the user
	}
	
	public int repeatedConfiguration(HashDictionary hashTable) {
		String s = "";
		for (int i = 0;i < board_size;i++) {
			for(int k = 0;k < board_size;k++) {
				s += board[k][i];
				//first creates a string with all values of the board in the form of X, O, or ' '
			}
		}
		if (hashTable.get(s) != -1){
			return hashTable.get(s);
			//checks if the input hashTable has the configuration s and returns it to the computer or user if it doesn't it returns
			//-1
		}
		return -1;
		
	}
	
	public void addConfiguration(HashDictionary hashDictionary, int score) {
		String s = "";
		for (int i = 0;i < board_size;i++) {
			for(int k = 0;k < board_size;k++) {
				s += board[k][i];
			}
			//Does the same as above creating a string with all values of the board in the form of X, O, or ' '
		}
		Data data = new Data(s, score);
		hashDictionary.put(data);
		//Creates a new piece of data from the string and score and puts it in the input hashDictionary
	}
	
	public void savePlay(int row, int col, char symbol) {
		board[row][col] = symbol;
		//Sets the location of the board at row and col to a certain symbol
	}
	
	public boolean squareIsEmpty(int row, int col) {
		if (board[row][col] == ' '){
			return true;
		} else {
			return false;
		}
		//Checks if the square is empty at the given row and col if it is it returns true, if not returns false
	}
	
	public boolean wins(char symbol) {
		for(int i = 1;i < board_size-1;i++) {
			for(int p = 1;p < board_size-1;p++) {
				if (board[i][p] == symbol) {
					
					int centreRow = i;
					int centreCol = p;
					
					if(plusX(centreRow, centreCol, symbol) == true) {
						return true;
					} else if(diagonalX(centreRow, centreCol, symbol)) {
						return true;
					}
					//Loops through all squares in a game based for a given symbol outside of the edges of the game box if it finds
					//one it puts its location into 2 private methods that helps find if its an x or not if it is an x it returns true
					//and if not it returns false
					
				}
			}
		}
		return false;
	}
	
	public boolean isDraw() {
		for (int i = 0;i < board_size;i++) {
			for(int k = 0;k < board_size;k++) {
				if(board[i][k] != ' ');
				else return false;
			}
		}
		boolean Owins=wins('O');//computer won
		boolean Xwins=wins('X');//human won
		if(Owins==true||Xwins==true) {
			return false;
		}
		return true;
		//Checks if all values within the board are taken if not it returns false, then checks if either of the symbols have won
		//the game and returns false if these are true. If neither of these are true it returns true to signify the game is a draw
	}
	
	public int evalBoard() {
		boolean Owins=wins('O');//computer won
		boolean Xwins=wins('X');//human won
		if(Owins == true) {
			return 3;
		} else if (Xwins == true) {
			return 0;
		} else if (isDraw() == true) {
			return 2;
		} else {
			return 1;
		}
		//Returns the value predetermined to let other programs know what occured during the outcome of the game, 3 for a computer win
		//2 for a draw, 1 for anything else, and 0 for a human win
		
	}
	
	private boolean plusX(int row, int col, char symbol){
		
		int count = 1;
		int leftArm = row - 1;
		int rightArm = row + 1;
		int topArm = col - 1;
		int botArm = col + 1;
		//sets the locations of the arms of a plus x from the centre
		
		if (board[leftArm][col] == symbol && leftArm >= 0) {
			count++;
		}
		if (board[rightArm][col] == symbol && rightArm >= 0) {
			count++;
		}
		if (board[row][topArm] == symbol && topArm >= 0) {
			count++;
		}
		if (board[row][botArm] == symbol && botArm >= 0) {
			count++;
		}
		//All of these if statements check if the locations of the arms from the centre are the same symbol as the centre, if so
		//it increases the count to see how many of them there are in order to determine if the game is won
		if (count == 5) {
			//checks if the count is the minimum to be an x if so it continues on
			if (count == lengthToWin){
				return true;
				//checks if count is equal to the length needed to win if so it returns true to signify a won game, if not it continues
			} else {
				for(int i = 1;i < board_size;i++) {
					if (leftArm-i >= 0) {
						if (board[leftArm-i][col] == symbol) {
							count++;
						}
					} if (rightArm+i <= board_size - 1) {
						if (board[rightArm+i][col] == symbol) {
							count++;
						}
					} if (topArm-i >= 0) {
						if (board[row][topArm-i] == symbol) {
							count++;
						} 
					} if (botArm+i <= board_size - 1) {
						if (board[row][botArm+i] == symbol) {
							count++;
						}
					} //all of these if statements increase or decrease the row/col of the centre to check how far each arm extends
					//in order to check the total length of the x to see if the game is won. Also has an if statement per increase
					//or decrease to make sure the arm doesnt extend out of the length of the board
				}
				if (count >= lengthToWin) {
					return true;
				} else {
					return false;
				}//another checker for the length and if its the length needed to win or more, if not it returns false
			}
		} else {
			return false;
		}//if it fails to be minimum length of an x it returns false
	}
	
	private boolean diagonalX(int row, int col, char symbol){
		int count = 1;
		int topArmOneR = row - 1;
		int topArmTwoR = row + 1;
		int botArmOneR = row - 1;
		int botArmTwoR = row + 1;
		
		int topArmOneC = col - 1;
		int topArmTwoC = col - 1;
		int botArmOneC = col + 1;
		int botArmTwoC = col + 1;
		
		if (board[topArmOneR][topArmOneC] == symbol) {
			count++;
		}
		if (board[topArmTwoR][topArmTwoC] == symbol) {
			count++;
		}
		if (board[botArmOneR][botArmOneC] == symbol) {
			count++;
		}
		if (board[botArmTwoR][botArmTwoC] == symbol) {
			count++;
		}//the same concept as plusX but instead increases or decreases BOTH of the row and col to get the correct position for all arms
		if (count == 5) {
			//checks if the count is the minimum to be an x if so it continues on
			if (count == lengthToWin){
				return true;
				//checks if count is equal to the length needed to win if so it returns true to signify a won game, if not it continues
			} else {
				for(int i = 1;i < board_size;i++) {
					if (topArmOneR-i >= 0 && topArmOneC-i >= 0) {
						if (board[topArmOneR-i][topArmOneC-i] == symbol) {
							count++;
						}
					} if (topArmTwoR+i <= board_size - 1 && topArmTwoC-1 >= 0) {
						if (board[topArmTwoR+i][topArmTwoC-1] == symbol) {
							count++;
						}
					} if (botArmOneR-i >= 0 && botArmOneC+1 <= board_size - 1) {
						if (board[botArmOneR-i][botArmOneC+1] == symbol) {
							count++;
						} 
					} if (botArmTwoR+i <= board_size - 1 && botArmTwoC+i <= board_size - 1) {
						if (board[botArmTwoR+i][botArmTwoC+i] == symbol) {
							count++;
						}//all of these if statements increase or decrease the row/col of the centre to check how far each arm extends
						//in order to check the total length of the x to see if the game is won. Also has an if statement per increase
						//or decrease to make sure the arm doesnt extend out of the length of the board
					}
				}
				if (count >= lengthToWin) {
					return true;
				} else {
					return false;
				}//another checker for the length and if its the length needed to win or more, if not it returns false
			}
		} else {
			return false;
		}//if it fails to be minimum length of an x it returns false
	}

}
