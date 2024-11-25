/*
 * decompress.c
 *
 * Computer Science 2211b - Winter 2024
 * Assignment 3
 * Student: Liam Rankine
 *
 * Decompresses the compress version of the string
 */

#include <stdio.h>
#include <stdlib.h>

/*
 * Decompresses a compressed version of a string
 *
 * Parameters
 *      count: Stores the number of characters in a run
 *      character: Stores the current character in the run
 *
 * Returns the decompressed version of the compressed string
 */
void decompress(FILE *input)
{
    //Stores the number of characters in a row
    int count;
    //Stores the character in the run
    int character;

    //Prints the current character count number of times seperated by spaces
    while (fscanf(input, " %d %c", &count, &character) != EOF)
    {
        for(int i = 0; i < count; i++)
        {
            printf("%c ", character);
        }
    }
    printf("\n");
}
/*
 * Main method calling the compression method
 *
 * Parameters: None
 *
 * Returns: The compressed version of the string
 */
int main()
{
    //calls the decompress method using the compressed string
    decompress(stdin);
    return 0;
}
