/******************************************************
 * tictactoe.c                                        *
 *                                                    *
 * Computer Science 2211b - Winter 2024               *
 * Assignment 3                                       *
 * Student: Liam Rankine                              *
 *                                                    *
 * This program is a tic tac toe game against a robot *
 **************************************************** */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

/*
 * Prints a visual form of the current board
 *
 * Parameters
 *      board[3][3]: Contains the values stored in the board
 *
 * Returns: The a visual form of the board
 */

void print_board(char board[3][3])
{
        //Prints the board
    printf("     0   1   2\n");
    printf("   -------------\n");
    printf(" 0 | %c | %c | %c |\n", board[0][0], board[0][1], board[0][2]);
    printf("   -------------\n");
    printf(" 1 | %c | %c | %c |\n", board[1][0], board[1][1], board[1][2]);
    printf("   -------------\n");
    printf(" 2 | %c | %c | %c |\n", board[2][0], board[2][1], board[2][2]);
    printf("   -------------\n");
}
/*
 * Asks the user where they would like to place their x and updates the board
 *
 * Parameters
 *      board[3][3]: Contains the board
 *      row: stores the row that the user entered
 *      col: stores the column the user entered
 *
 * Returns: Nothing but updates the board
 */
void prompt_move(char board[3][3])
{

    //Contains the row that the user entered
    int row = 0;
    //Contrains the column the user entered
    int col = 0;

    while(1)
    {
        //Asks the user for the row and column they'd like to play and stores it
        printf("Enter row and column for your move: ");
        scanf("%d %d",&row, &col);

        //checks if the place is not occupied and places an x accordingly
        if(board[row][col] == ' ')
        {
            board[row][col] = 'x';
            break;
        }

        //Asks the user to choose another spot if it's occupied
        else
        {
            printf("Place occupied choose another\n");
        }
    }

}
/*
 * Checks if the user won or tied and ends the game and prints who won
 *
 * Parameters
 *      board[3][3]: Stores the current board
 *
 * Returns: Returns true if the game has been won or tied
 */
bool check_win(char board[3][3])
{
    //Checks if the entire board has been filled
    //Stores the amount of non-occupied spots
    int nonspace_count = 0;

    for(int i = 0; i < 3; i++)
    {
        for(int y = 0; y < 3; y++)
        {
            if(board[i][y] != ' ')
            {
                nonspace_count++;
            }
        }
    }

    //Checks if the game has been won by horizontal or vertical win
    for(int i = 0; i < 3; i++)
    {
        if(board[i][0] == 'x' && board[i][1] == 'x' && board[i][2] == 'x')
        {
            printf("Player wins!\n");
            return true;
        }
        else if(board[i][0] == 'o' && board[i][1] == 'o' && board[i][2] == 'o')
        {
            printf("Computer wins!\n");
            return true;
        }
        else if(board[0][i] == 'o' && board[1][i] == 'o' && board[2][i] == 'o')
        {
            printf("Computer wins!\n");
            return true;
        }
        else if(board[0][i] == 'x' && board[1][i] == 'x' && board[2][i] == 'x')
        {
            printf("Player wins!\n");
            return true;
        }
    }

    //Checks if the game has been won diagonally or tie
    if(board[0][0] == 'x' && board[1][1] == 'x' && board[2][2] == 'x')
    {
        printf("Player wins!\n");
        return true;
    }
    else if(board[0][0] == 'o' && board[1][1] == 'o' && board[2][2] == 'o')
    {
        printf("Computer wins!\n");
        return true;
    }
    else if(board[0][2] == 'x' && board[1][1] == 'x' && board[2][0] == 'x')
    {
        printf("Human wins!\n");
        return true;
    }
    else if(board[0][2] == 'o' && board[1][1] == 'o' && board[2][0] == 'o')
    {
        printf("Computer wins!\n");
        return true;
    }
    else if(nonspace_count == 9)
    {
        printf("Tie!\n");
        return true;
    }
    else
    {
        return false;
    }
}
/*
 * Random number generator for the computer's move and updates the board
 *
 * Parameters
 *      board[3][3]: Stores the current board
 *      row: Stores the row that the computer places at
 *      col: Stores the column the computer places at
 *
 * Returns: An updated board with the computer's move
 */
void computer_move(char board[3][3])
{
    while(1)
    {
        //Contains the row for the computer's move
        int row = rand() % 3;
        //Contains the column for the computer's move
        int col = rand() % 3;

        //Checks where the computer chose to place is occupied, if not it tell the user where it was played
        //and updates the board
        if(board[row][col] == ' ')
        {
            printf("Computer moves to row %d, column %d\n", row, col);
            board[row][col] = 'o';
            break;
        }
        //If the computer's choice is occupied it chooses again
        else
        {
            continue;
        }
    }
}
/*
 * Main method which plays the game utilizing other methods
 *
 * Parameters: None
 *
 * Returns: A playable tic tac toe game
 */
int main()
{
    //Random number generator for the computer
    srand((unsigned int)time(NULL));

    //Initialized the board with empty spaces
    char board[3][3];
    for(int i = 0; i < 3; i++)
    {
        for(int y = 0; y < 3; y++)
        {
            board[i][y] = ' ';
        }
    }

    //Contains the game which ends when someone has won or tied
    while(1)
    {
        prompt_move(board);
        print_board(board);
        computer_move(board);
        print_board(board);
        bool win = check_win(board);
        if(win == true)
        {
             break;
        }
             continue;
    }
    return 0;
}
