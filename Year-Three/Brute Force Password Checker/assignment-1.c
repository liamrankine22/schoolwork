#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdbool.h>
#include "checkPassword.h"

int main(int argc, char *argv[])
{

	//Initializing variables with ASCII code for !
	int firstChar = 33;
	int secondChar = 33;
	int thirdChar = 33;

	//Creating base string to be checked
	char str[4];
	str[0] = (char)firstChar;
	str[1] = (char)secondChar;
	str[2] = (char)thirdChar;

	//If only argument passed is the code name it will run without forking
	if(argc == 1)
	{

		//Finds process ID and parent process ID
		pid_t processID = getpid();
		pid_t parentProcessID = getppid();

		//Lets user know the program has begun
		printf("Process %d with parent %d cracking the password...\n", processID, parentProcessID);

		//Initializing success variables for each section of the password
		int successOne = -1;
		int successTwo = -1;
		int successThree = -1;
		int successFour = -1;

		//Variables to check if the message for a completed section has occured
		int sucOneMsg = 0;
		int sucTwoMsg = 0;
		int sucThreeMsg = 0;
		int sucFourMsg = 0;

		//Infinite loop that goes until the string reaches ~~~ or all sections of the password have been found
		while(1)
		{

			//Checks if the first section has been found
			if(successOne != 0)
			{
				//If not checks the password again
				successOne = checkPassword(str, 0);
				if(successOne == 0)
				{
					//Used for making sure the string doesnt increment if the password has been found after the checking
					continue;
				}
			}

			//Checks if if the second section has been found after 1 has been found
			else if(successTwo != 0)
			{
				//Prints the completion message of the section and the password for that section
				if(sucOneMsg == 0)
				{

					//Copies the found password into another string to be printed
					char dest[5];
					int i = 0;
					while (str[i] != '\0')
					{
						dest[i] = str[i];
						i += 1;
					}
					dest[i] = '\0';
					printf("Process %d with parent %d finds 0-2: %s\n", processID, parentProcessID, dest);
					sucOneMsg += 1;

					//resets the string to !!!
					firstChar = 33;
					secondChar = 33;
					thirdChar = 33;
					str[0] = (char)firstChar;
					str[1] = (char)secondChar;
					str[2] = (char)thirdChar;
					continue;
				}

				//Checks section 2
				successTwo = checkPassword(str, 3);
				if(successTwo == 0)
				{
					continue;
				}
			}

			//Checks section 3 if 2 and 1 have been found
			else if(successThree != 0)
			{
				if(sucTwoMsg == 0)
				{
					//Copies password into another string
					char dest[5];
					int i = 0;
					while(str[i] != '\0')
					{
						dest[i] = str[i];
						i += 1;
					}
					dest[i] = '\0';
					printf("Process %d with parent %d finds 3-5: %s\n", processID, parentProcessID, dest);
					sucTwoMsg += 1;

					//Resets string
					firstChar = 33;
					secondChar = 33;
					thirdChar = 33;
					str[0] = (char)firstChar;
					str[1] = (char)secondChar;
					str[2] = (char)thirdChar;
					continue;
				}

				//Checks if section 3 is a success
				successThree = checkPassword(str, 6);
				if(successThree == 0)
				{
					continue;
				}
			}

			//Checks if sections 1-3 are completed
			else if(successFour != 0)
			{

				//Prints a message if section 3 was found displaying the password and what process got it
				if(sucThreeMsg == 0)
				{
					//Copies the password
					char dest[5];
					int i = 0;
					while(str[i] != '\0')
					{
						dest[i] = str[i];
						i += 1;
					}
					dest[i] = '\0';
					printf("Process %d with parent %d finds 6-8: %s\n", processID, parentProcessID, dest);
					sucThreeMsg += 1;

					//Resets the string
					firstChar = 33;
					secondChar = 33;
					thirdChar = 33;
					str[0] = (char)firstChar;
					str[1] = (char)secondChar;
					str[2] = (char)thirdChar;
					continue;
				}
				//Checks if the password is correct
				successFour = checkPassword(str, 9);
			}

			//Checks if all sections of the password are found
			if(successOne == 0 && successTwo == 0 && successThree == 0 && successFour == 0)
			{
				//Copies string for completion message of section 4
				char dest[5];
				int i = 0;
				while(str[i] != '\0')
				{
					dest[i] = str[i];
					i += 1;
				}
				dest[i] = '\0';
				printf("Process %d with parent %d finds 9-11: %s\n", processID, parentProcessID, dest);
				return 0;
			}

			//Checks if the last character in the string is less than ~ if so increments it
			if(thirdChar < 126)
			{
				thirdChar += 1;
			}
			else
			{
				//If all characters are ~ it then exits with a -1 value to show a password was not found
				if(firstChar == 126 && secondChar == 126 && thirdChar == 126)
				{
					//Backup for if the password is ~~~ since it would not check that
					str[0] = (char)firstChar;
					str[1] = (char)secondChar;
					str[2] = (char)thirdChar;
					if(successOne != 0)
					{
						successOne = checkPassword(str, 0);
						if(successOne == 0)
						{
							continue;
						}
					}
					else if(successTwo != 0)
					{
						successTwo = checkPassword(str, 3);
						if(successTwo == 0)
						{
							continue;
						}
					}
					else if(successThree != 0)
					{
						successThree = checkPassword(str,6);
						if(successThree == 0)
						{
							continue;
						}
					}
					else if (successFour != 0)
					{
						successFour = checkPassword(str, 9);
						if(successFour == 0)
						{
							continue;
						}
					}
					printf("Reached the end\n");
					return -1;
				}
				else
				{
					//Sets the last character back to ! and may increment the middle character if its less than ~
					thirdChar = 33;
					if(secondChar < 126)
					{
						secondChar += 1;
					}
					else
					{
						//Resets the middle character to !
						secondChar = 33;
						if(firstChar < 126)
						{
							//Increments the first character in the string
							firstChar += 1;
						}
					}
				}
			}

			//Creates the new string with the incremented or decremented characters
			str[0] = (char)firstChar;
			str[1] = (char)secondChar;
			str[2] = (char)thirdChar;
		}

	}
	else
	{
		//Checks if the -f option is applied to calling the program
		if(strcmp(argv[1], "-f") == 0)
		{
			//Constant number of forks for this program
			const int NUM_CHILD = 4;
			//pids of all children processes
			pid_t pids[NUM_CHILD];

			for(int i = 0; i < NUM_CHILD; i += 1)
			{

				//Initializes the success of the process to be false
				int success = -1;

				//Creates the fork
				pid_t pid = fork();

				//Checks if the fork was created improperly and lets the user know
				if(pid < 0)
				{
					perror("Fork failed\n");
					return -1;
				}
				//If the fork was created properly it then executes
				else if (pid == 0)
				{

					//Prints the process id and its parent id thats cracking the password
					printf("Process %d with parent %d cracking the password...\n", getpid(), getppid());

					//Each process works on a different section and its determined by which order it was created
					int section = i * 3;

					//Infinite loop for the program to run until there is an error or the password was found
					while(1)
					{
						//Checks if the password has not been found
						if (success != 0)
						{
							//Checks the password for the new string and its section
							success = checkPassword(str, section);
							if(success == 0)
							{
								continue;
							}
						}

						//If the password was found it then prints the password and the process that found it in the given section
						else
						{
							int i = 0;
							char dest[5];
							while(str[i] != '\0')
							{
								dest[i] = str[i];
								i += 1;
							}
							dest[i] = '\0';

							printf("Process %d with parent %d finds section %d-%d: %s\n", getpid(), getppid(), section, section + 2, str);
							break;
						}

						//Exact same string incrementer as in the non-forked method
						if(thirdChar < 126)
						{
							thirdChar += 1;
						}
						else
						{
							if(firstChar == 126 && secondChar == 126 && thirdChar == 126)
							{
								//Sets the string to be ~~~ to check the final value
								str[0] = (char)firstChar;
								str[1] = (char)secondChar;
								str[2] = (char)thirdChar;

								//Checks if the password is ~~~ otherwise returns -1 signifying the program did not execute properly
								if(success != 0)
								{
									success = checkPassword(str, section);
									if(success == 0)
									{
										continue;
									}
								}
								printf("Reached the end\n");
								return -1;
							}
							else
							{
								thirdChar = 33;
								if(secondChar < 126)
								{
									secondChar += 1;
								}
								else
								{
									secondChar = 33;
									if(firstChar < 126)
									{
										firstChar += 1;
									}
								}
							}
						}

						//Sets the new string
						str[0] = (char)firstChar;
						str[1] = (char)secondChar;
						str[2] = (char)thirdChar;
					}

					//exits with 0 to show program completed correctly
					return 0;
				}
				else
				{
					//Stores process ids in the array
					pids[i] = pid;
				}
			}

			//Waits for all child processes to complete
			for(int i = 0; i < NUM_CHILD; i += 1)
			{
				wait(NULL);
			}

			//Returns 0 to signify the end of the program and its proper completion
			return 0;

		}

	}

	//If it has reached this point the program has completed abnormally and returns -1 to signify a full completion
	return -1;

}
