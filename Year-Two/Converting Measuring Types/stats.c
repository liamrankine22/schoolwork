/***************************************************************
 *                                                             *
 * stats.c                                                     *
 *                                                             *
 * Computer Science 2211b - Winter 2024                        *
 * Assignment 2 Part 2                                         *
 * Student: Liam Rankine                                       *
 *                                                             *
 * This program asks the user to input 10 digits then returns  *
 * the sum, count and average of those digits                  *
 *                                                             *
 ***************************************************************/

#include <stdio.h>

/*
 * Asks the user for 10 digits and then computes several pieces of information about said decimals
 * such as the sum total number of digits and their average
 *
 * Parameters
 *     current_value: Stores the input digit or character from the user
 *
 * Returns: The sum, count and average of all input digits
 */
int main()
{
    // Contains the total values of all input numbers
    int sum = 0;
    // Contains the number of input numbers disregarding incorrect inputs and exit qs
    int count = 0;
    // Contains the average of all numbers input
    float average = 0.00;
    // Contains the most recently input number
    int current_value;
    // Contains an array storing all numbers
    char input[10];

    //For loop going up to 10 for all possibly digit entries
    for(int i = 0; i < 10; i++)
    {
        //Asks the user to input a digit or q to quit
        printf("Enter a digit (q to quit): ");
        scanf("%s", input);

        // Checks if the input value is q in which the program quits to show the sum, count and average to that point
        if(input[0] ==  'q' && input[1] == '\0')
        {
            break;
        }

        // Checks if the input value is greater than a single digit
        if(input[1] != '\0' || input[0] < '0' || input[0] > '9')
        {
            printf("Please enter only 1 digit.\n");
            i = i - 1;
            continue;
        }

        // Converts athe input to an integer and increasing the count and sum
        sscanf(input, "%d", &current_value);
        count = i + 1;
        sum = sum + current_value;

    }

    // Calulates the average and returns the sum, count and average to the user
    average = (float)sum/ (float)count;
    printf("Sum     : %d\n", sum);
    printf("Count   : %d\n", count);
    printf("Average : %.2f\n", average);

    return 0;
}
