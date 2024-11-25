import java.io.BufferedReader;
import java.io.FileReader;

public class Interface {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(args[0]));
		BSTDictionary dictionary = new BSTDictionary();
		String word = in.readLine();
	    String definition;
	    //Setting up variables and the text file reader to be used within the algorithm
	    
	    //First while loop goes through each line and checks the description of each word to see what type it gets
	    //After the Type is defined the word, type and description are all put into the binary search tree
		while(word!=null) {
			int TYPE = 1;
			definition = in.readLine();
			if (definition.charAt(0) == '/') {
				TYPE = 2;
			} else if (definition.charAt(0) == '-') {
				TYPE = 3;
			} else if (definition.charAt(0) == '+') {
				TYPE = 4;
			} else if (definition.charAt(0) == '*') {
				TYPE = 5;
			} else if (definition.indexOf('.') != -1) {
				String[] strArr = definition.split("\\.");
				for(int i = 0;i < strArr.length; i++) {
					if(strArr[i].equals("jpg")) {
						TYPE = 6;
					} else if (strArr[i].equals("gif")) {
						TYPE = 7;
					} else if (strArr[i].equals("html")) {
						TYPE = 8;
					}
				}
			}
			Record a = new Record(new Key(word,TYPE),definition);
			dictionary.put(a);
			word = in.readLine();
		}
		
		//Second while loop is infinite until exit is input by the user, this while loop goes through every command
		while(true) {
		StringReader keyboard = new StringReader();
		String line = keyboard.read("Enter next command: ");
		String[] strArr = line.split(" ");
		//These are variables to be used in either to save what the user has input or divide it into it's useful parts
		//The string array splits what the user inputs into its command stored at index 0 and everything else is in the other indexes
		if(strArr[0].equals("define")) {
			Record rec = dictionary.get(new Key(strArr[1], 1));
			//Works the same in every other method, first after splitting what the user input 1st index will contain what they're
			//looking for in the command and since this is a define we're looking for anything with the type 1 so we will get
			//the record containing what the user is looking for and it's type in order to make sure we get the correct record
			if(rec == null) {
				System.out.println("The word "+strArr[1]+" is not in the ordered dictionary");
				//Checks if the word is in the dictionary if not displays a message to the user
			} else {
				System.out.println(dictionary.get(new Key(strArr[1], 1)).getDataItem());
				//Otherwise returns the definition of the word
			}
		}else if (strArr[0].equals("translate")) {
			Record rec = dictionary.get(new Key(strArr[1], 2));
			if(rec == null) {
				System.out.println("There is no definition for the word "+strArr[1]);
				//Checks if the translation exists in the binary search tree and displays a message to the user if not
			} else {
				String translation = dictionary.get(new Key(strArr[1], 2)).getDataItem();
				System.out.println(translation.substring(1));
				//Otherwise prints the translation to the user;
				//Also uses substring to not include the front character which would cause an error
			}
		}else if(strArr[0].equals("sound")) {
			Record rec = dictionary.get(new Key(strArr[1], 3));
			if(rec == null) {
				System.out.println("There is no sound file for "+strArr[1]);
				//Checks if the sound is in the binary search tree and if not displays a message to the user
				//
			} else {
				String sound = dictionary.get(new Key(strArr[1], 3)).getDataItem();
				SoundPlayer soundMethod = new SoundPlayer();
				soundMethod.play(sound.substring(1));
				//Creates a new SoundPlayer object in order to use the play method on the found record.
				//Also uses substring to not include the front character which would cause an error
			}
		}else if (strArr[0].equals("play")) {
			Record rec = dictionary.get(new Key(strArr[1], 4));
			if(rec == null) {
				System.out.println("There is no music file for "+strArr[1]);
				//Checks if the music file is in the binary search tree, if not displays a message to the user
			} else {
				String sound = dictionary.get(new Key(strArr[1], 4)).getDataItem();
				SoundPlayer soundMethod = new SoundPlayer();
				soundMethod.play(sound.substring(1));
				//Creates a new SoundPlayer object in order to use the play method on the found record.
				//Also uses substring to not include the front character which would cause an error
			}
		}else if (strArr[0].equals("say")) {
			Record rec = dictionary.get(new Key(strArr[1], 5));
			if(rec == null) {
				System.out.println("There is no voice file for "+strArr[1]);
				//Checks if voice file is in the binary search tree, if not displays a message to the user
			} else {
				String sound = dictionary.get(new Key(strArr[1], 5)).getDataItem();
				SoundPlayer soundMethod = new SoundPlayer();
				soundMethod.play(sound.substring(1));
				//Creates a new SoundPlayer object in order to use the play method on the found record.
				//Also uses substring to not include the front character which would cause an error
			}
		}else if (strArr[0].equals("show")) {
			Record rec = dictionary.get(new Key(strArr[1], 6));
			if(rec == null) {
				System.out.println("There is no image file for "+strArr[1]);
				//Checks if the image file exists in the tree, if not it displays a message to the user
			} else {
				String picture = dictionary.get(new Key(strArr[1], 6)).getDataItem();
				PictureViewer showMethod = new PictureViewer();
				showMethod.show(picture);
				//Creates a new PictureViewer object in order to use the show method on the found record.
				//Does not use substring because the jpg, gif and html's do not have a beginning character to differentiate like
				//the music voice and more have.
			}
		}else if (strArr[0].equals("animate")) {
			Record rec = dictionary.get(new Key(strArr[1], 7));
			if(rec == null) {
				System.out.println("There is no animated image file for "+strArr[1]);
				//Checks if the gif is in the binary tree, if not it displays a message to the user
			} else {
				String picture = dictionary.get(new Key(strArr[1], 7)).getDataItem();
				PictureViewer showMethod = new PictureViewer();
				showMethod.show(picture);
				//Creates a new PictureViewer object in order to use the show method on the found record.
				//Does not use substring because the jpg, gif and html's do not have a beginning character to differentiate like
				//the music voice and more have.
			}
		}else if (strArr[0].equals("browse")) {
			Record rec = dictionary.get(new Key(strArr[1], 8));
			if(rec == null) {
				System.out.println("There is no webpage called "+strArr[1]);
				//Checks if the web page address exists in the binary tree, if not it displays a message to the user
			} else {
				String website = dictionary.get(new Key(strArr[1], 8)).getDataItem();
				ShowHTML showMethod = new ShowHTML();
				showMethod.show(website);
				//Creates a new ShowHTML object in order to use the show method on the found record.
				//Does not use substring because the jpg, gif and html's do not have a beginning character to differentiate like
				//the music voice and more have.
			}
		}else if (strArr[0].equals("delete")) {
			int number = Integer.parseInt(strArr[2]);
			//Converts the 3rd part of the input to an int to get the type since it will be originally a string
			Record rec = dictionary.get(new Key(strArr[1], number));
			if(rec == null) {
				System.out.println("No record in the ordered dictionary has key ("+strArr[1]+","+strArr[2]+")");
				//Checks if the dictionary contains the given key, if not it displays a message to the user
			}else {
				dictionary.remove(rec.getKey());
				//Otherwise uses the remove method to remove the node from the tree and the dictionary
			}
		}else if (strArr[0].equals("add")) {
			int number = Integer.parseInt(strArr[2]);
			try {
				dictionary.put(new Record(new Key(strArr[1], number), strArr[3]));
				//Tries to add a value to the dictionary like normal
			} catch (Exception DictionaryException) {
				System.out.println("A record with the given key ("+strArr[1]+","+strArr[2]+") is already in the ordered dictionary");
				//Catches a dictionary exception if a record with the same key is already in the dictionary and tree and displays
				//a message to the user
			}
		}
		else if (strArr[0].equals("list")){
			Key key = new Key(strArr[1], 1);
			Record rec = dictionary.get(key);
			if(rec == null) {
				rec = dictionary.successor(key);
				if(rec.getKey().getLabel().startsWith(strArr[1])) {
					while(rec.getKey().getLabel().startsWith(strArr[1])) {
						System.out.print(rec.getKey().getLabel()+", ");
						rec = dictionary.successor(rec.getKey());
						//First checks if the prefix is in the tree, if not it gets its successor which would be the place
						//where if the prefixes were the same it would be there
						//if the successor contains the prefix is enters a while loop that continues if the successor starts with the
						//prefix and then prints the label and updates the node and get's that node's successor
					}
					System.out.println();
					continue;
					//For when the prefix is not longer within the successor mainly formatting and the continue for avoiding the
					//print statement at the end which is used for if the prefix is not in the dictionary at all
				}
			} else {
					while (rec.getKey().getLabel().startsWith(strArr[1])) {
						System.out.print(rec.getKey().getLabel()+", ");
						rec = dictionary.successor(rec.getKey());
						//Same as if the prefix was not in the dictionary but this time it is, it continues into the while loop and
						//proceeds the same as if it were not in
					} 
					System.out.println();
					continue;
					//For formatting and avoiding final print statement
			}
			System.out.println("No label attributes in the ordered dictionary start with prefix "+strArr[1]);	
			//Only prints if no labels contain the given prefix due to the continue statements which loop back to the start of
			//the infinite while loop
			
		}else if (strArr[0].equals("first")) {
			Record rec = dictionary.smallest();
			//Returns the smallest node in the dictionary
			System.out.println(rec.getKey().getLabel()+","+rec.getKey().getType()+","+rec.getDataItem());
		}else if (strArr[0].equals("last")) {
			Record rec = dictionary.largest();
			//Returns the largest node in the dictionary
			System.out.println(rec.getKey().getLabel()+","+rec.getKey().getType()+","+rec.getDataItem());
		}else if (strArr[0].equals("exit")) {
			break;
			//Exits the infinite while loop closing the program
		}
		continue;
		//After the respective command is finished it continues the infinite while loop
		}
		
	}
}