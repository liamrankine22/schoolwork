/*
 * compress.c
 *
 * Computer Science 2211b - Winter 2024
 * Assignment 3
 * Student: Liam Rankine
 *
 * This program compresses a string of characters and orders them by their character
 * and the number of how many of them are in a row
 */

#include<stdio.h>
#include<ctype.h>

/*
 * Reads the input of the user and stores the number of non  whitespace characters
 *
 * Parameters
 *      index: Stores the current character in the buffer string
 *      character: stores the current character for calculation of a run
 *      non_whitespace_count: Contains the number of non whitespaces
 *      buffer[1024]: Stores all the characters to compress
 *
 * Returns: The number of non whitespaces characters
 */
int read_input(char buffer[1024])
{
    //Stores the current character in the buffer string
    int index = 0;
    //Stores the current character to calcualte a run
    char character;
    //Stores the number of non whitespace characters
    int non_whitespace_count = 0;

    //Goes through the entire buffer and checks how many non whitespace characters there are
    while ((character = getchar()) != EOF && index < 1023)
    {
        if(!isspace(character))
        {
            buffer[index++] = character;
            non_whitespace_count++;
        }
    }
    return non_whitespace_count;
}
/*
 * Compresses all characters into their character and how many of them there are in a run
 *
 * Parameters:
 *      current_character: Stores the currently running character
 *      run: Stores the size of the run
 *
 * Returns: A compressed version of a string by its character and size of it's run
 */
void compress(char buffer[1024], int length)
{
    //Stores the currently checked character
    char current_character = '+';
    //Stores the size of the run
    int run = 0;

    //Checks if the run is over and prints the size of the run and character
    //If the run is over it switches the current character to the new one and restarts the run
    for(int i = 0; i < length; i++)
    {
        if(current_character != buffer[i])
        {
            if(current_character != '+' || current_character == EOF)
            {
                printf("%d %c\n",run, current_character);
                run = 1;
                current_character = buffer[i];
            }
            else
            {
                current_character = buffer[i];
                run = 1;
            }
        }
        else
        {
            run++;
        }
    }
}
/*
 * Main method which calls the compress method and sets up all required variables
 *
 * Parameters
 *      array[1024]: Creates a character array with 1024 indexes
 *      length: Stores the size of the array of non whitespaces
 *
 * Returns: Nothing
 */
int main()
{
    //Creates the character array with 1024 indexes
    char array[1024];
    //Stores the size of the array of non whitespaces
    int length;

    //Stores the number of non whitespaces using the read_input method
    length = read_input(array);
    if(length > 0)
    {
        //Calls the compression method
        compress(array, length);
    }
    return 0;
}
