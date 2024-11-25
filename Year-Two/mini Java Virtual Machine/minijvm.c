/*
 * minijvm.c
 *
 * Computer Science 2211B - Winter 2024
 * Assignment 5
 * Student: Liam Rankine
 *
 * This program allows for the creation and use of a mini Java Virtual Machine which will execute simple bytecode programs
 */


#include <stdio.h>
#include <stdlib.h>
#include "minijvm.h"
#include "stack.h"
#include <string.h>

/*
 * Reads the bytes from a specified file into an array
 *
 * Parameters:
 *      filename: Name of the .mclass file from which to read (without the .mclass extention)
 *
 * Returns:
 *      A pointer to the array of bytecode
 */
char* jvm_read(const char* filename)
{

    static char buffer[MAX_CLASS_SIZE]; // Array storing all bytes from filename
    int bytes_read;
    char* file_extention = ".mclass"; // File extention to be added to filename

    char temp[MAX_CLASS_SIZE]; // placeholder for new filename
    strcpy(temp, filename); //Copies filename into temp
    strcat(temp, file_extention); // Adds file extention to filename

    FILE* f = fopen(temp, "r"); // Opens the file for reading
    if(f == NULL)
    {
        fprintf(stderr, "File '%s' not found\n", temp); // Returns error if something goes wrong opening the file
        return;
    }

    bytes_read = fread(buffer, sizeof(char), sizeof(buffer), f); // Reads the bytes into the buffer array
    if(bytes_read == 0)
    {
        fprintf(stderr, "Error Reading from File\n"); // Returns error if no bytes are read
        fclose(f);
    }

    fclose(f); // Closes file after reading
    return buffer; // Returns pointer to array

}

/*
 * Helper function used for calculating action from byte value
 *
 * Parameters:
 *      program: char value containing the byte to be analyzed for which operation to be computed
 *
 * Returns: Nothing
 */
void jvm_run_helper(minijvm* jvm, unsigned char program)
{
    short temp; // temp variables for use within operations
    short temp2;
    short temp5; // empty variable for temp3 to point at since stack_pop kept setting them to 0 if I used multiple
    short *temp3 = &temp5;
    unsigned short temp4;

    switch(program)
    {
        // Pushes 0 onto the operand stack
        case 3:
            //printf("Pushing 0...\n");
            int zero = 0;
            temp = (char)zero;
            stack_push(jvm->operands, temp);
            stack_peek(jvm->operands, temp3);
            //printf("%d\n", *temp3);
            break;

        // Pops the top item off the stack and discards
        case 87:
            //printf("Popping Top Item...\n");
            stack_pop(jvm->operands, temp3);
            break;

        // Duplicates top item on the stack
        case 89:
            //printf("Duplicating...\n");
            stack_pop(jvm->operands, temp3);
            temp = *temp3;
            stack_push(jvm->operands, temp);
            stack_push(jvm->operands, temp);
            //printf("Duplicated %d!\n", temp);
            break;

        // Pops and adds the two top items on the stack, pushing their sum back on the stack
        case 96:
            //printf("Adding...\n");
            stack_pop(jvm->operands, temp3);
            temp = *temp3;
            stack_pop(jvm->operands, temp3);
            temp2 = *temp3;
            stack_push(jvm->operands, temp + temp2);
            //printf("Added %d and %d to make %d!\n", temp, temp2, temp+temp2);
            break;

        // Pops and subtracts two top items on the stack, pushing their difference back on the stack
        case 100:
            //printf("Subtracting...\n");
            stack_pop(jvm->operands, temp3);
            temp = *temp3;
            stack_pop(jvm->operands, temp3);
            temp2 = *temp3;
            stack_push(jvm->operands, temp2 - temp);
            //printf("Subtracted %d and %d to make %d!\n", temp2, temp, temp2-temp);
            break;

        // Pops and multiplies the two top items on the stack, pushing their product back on the stack
        case 104:
            //printf("Multiplying...\n");
            stack_pop(jvm->operands, temp3);
            temp = *temp3;
            stack_pop(jvm->operands, temp3);
            temp2 = *temp3;
            stack_push(jvm->operands, temp * temp2);
            //printf("Multiplied %d and %d to make %d!\n", temp, temp2, temp*temp2);
            break;

        // Pops and divides the two top items on the stack, pushing their quotient back on the stack
        case 108:
            //printf("Dividing...\n");
            stack_pop(jvm->operands, temp3);
            temp = *temp3;
            if(temp == 0)
            {
                fprintf(stderr, "Division by zero\n");
                jvm->return_value = 1;
                break;
            }
            stack_pop(jvm->operands, temp3);
            temp2 = *temp3;
            stack_push(jvm->operands, temp2 / temp);
            //printf("Dividing %d and %d to make %d!\n", temp2, temp, temp2/temp);
            break;

        // Pops and divides the two top items on the stack, pushing their remainder back on the stack
        case 112:
            //printf("Finding Remainder...\n");
            stack_pop(jvm->operands, temp3);
            temp = *temp3;
            if(temp == 0)
            {
                fprintf(stderr, "Division by zero\n");
                jvm->return_value = 1;
                break;
            }
            stack_pop(jvm->operands, temp3);
            temp2 = *temp3;
            stack_push(jvm->operands, temp2 % temp);
            //printf("Remainder of %d and %d making %d!\n", temp2, temp, temp2%temp);
            break;

        // Pops and shifts the two top items on the stack, pushing their result back on the stack
        case 122:
            //printf("Shifting...\n");
            stack_pop(jvm->operands, temp3);
            temp = *temp3;
            stack_pop(jvm->operands, temp3);
            temp2 = *temp3;
            stack_push(jvm->operands, temp2 >> temp);
            //printf("Shifted %d by %d to make %d!\n", temp2, temp, temp2 >> temp);
            break;

        // Returns to main method implying program executed smoothly
        case 177:
            //printf("Returning...\n");
            jvm->return_value = 0;
            break;

        // Prints the top item on the stack
        case 187:
            //printf("Printing Top Of Stack...\n");
            stack_peek(jvm->operands, temp3);
            printf("%d\n", *temp3);
            break;

        // Pushes signed byte b onto the stack
        case 16:
            //printf("Pushing into Stack...\n");
            jvm->pc++;
            temp = *jvm->pc;
            stack_push(jvm->operands, temp);
            //printf("Pushed %d!\n", temp);
            break;

        // Pushes local variable at index n onto the stack
        case 21:
            //printf("Pushing local variable...\n");
            jvm->pc++;
            temp = *jvm->pc;
            temp2 = jvm->locals[(int)temp];
            stack_push(jvm->operands, temp2);
            //printf("Pushed local variable %d!\n", temp2);
            break;

        // Pops the top of the stack and stores in in local variable at index n
        case 54:
            //printf("Storing into local variables...\n");
            jvm->pc++;
            temp = *jvm->pc;
            stack_pop(jvm->operands, temp3);
            temp2 = *temp3;
            jvm->locals[(int)temp] = (int)temp2;
            //printf("Stored %d in locals[%d]!\n", temp2, temp);
            break;

        // Increments local variable at index n by d
        case 132:
            //printf("Incrementing local variable...\n");
            jvm->pc++;
            temp = *jvm->pc;
            jvm->pc++;
            temp2 = *jvm->pc;
            jvm->locals[(int)temp] = jvm->locals[(int)temp] + (int)temp2;
            //printf("Incremented locals[%d] by %d to make %d!\n", temp, temp2, jvm->locals[(int)temp]);
            break;

        // Pops the top item off the stack, banches to offset if it is equal to 0
        case 153:
            //printf("Checking if equal...\n");
            stack_pop(jvm->operands, temp3);
            temp = *temp3;
            char byte = 0;

            if(temp == byte)
            {
                 //printf("Is equal...\n");
                 *jvm->pc++;
                 temp = *jvm->pc;
                 *jvm->pc++;
                 temp2 = *jvm->pc;
                 short combinedValue = (temp << 8) | temp2;
                 int offset = (int)combinedValue;

                 jvm->pc-=2; // Reset position to instruction byte
                 jvm->pc+=(offset);
                 return; // Skips extra increment
            }

            else
            {
                jvm->pc+=2;
                return;
            }

        // Branches to offset
        case 167:
            //printf("Going to...\n");
            *jvm->pc++;
            temp = *jvm->pc;
            *jvm->pc++;
            temp2 = *jvm->pc;
            short combinedValue = (temp << 8) | temp2;
            int offset = (int)combinedValue;

            jvm->pc-=2; //Reset position to instruction byte
            jvm->pc+=(offset); // Multiply by 2 due to short usage
            return; // Skips increment

    }

    jvm->pc++; // Increments pointer to next instruction

}

/*
 * Runs the program loaded into the miniJVM
 *
 * Parameters:
 *      jvm: The miniJVM initialized with the bytecode to run
 *
 * Returns: Nothing
 */
void jvm_run(minijvm* jvm)
{

    while(1)
    {

        unsigned char current_program = (unsigned char)(*jvm->pc); // Stores current instruction to be executed by the helper function
        jvm_run_helper(jvm, current_program); // Calls the helper function
        if(jvm->return_value == 0)
        {
            break; //Checks if the jvm ended correctly
        }
        else if(jvm->return_value == 1)
        {
            break; // Checks if the jvm ended due to error
        }

    }

}

/*
 * Initializes a new miniJVM struct
 *
 * Parameters:
 *      filename: Name of the .mclass file from which to read without the .mclass extention
 *
 * Returns: A pointer to the initialized minijvm
 */
minijvm* jvm_init(const char* filename)
{

    minijvm* j = (minijvm*)malloc(sizeof(minijvm)); // Dynamically allocates space for the miniJVM

    j->bytecode = jvm_read(filename); // Gets the bytecode using jvm_read;
    j->pc = j->bytecode; // Sets the program counter to the first value of bytecode

    for(int i = 0; i < 10; i++)
    {
        j->locals[i] = 0; // Initializes the local values with 0
    }

    stack s = stack_create(); // Initializes a stack for the jvm to use
    j->operands = s;
    j->return_value = 2; // Sets the return value to 2 to avoid conflicts in jvm_run

    return j;

}

/*
 * Frees all memory allocated for the miniJVM
 *
 * Parameters:
 *      jvm: The minijvm struct to deallocate
 *
 * Returns: Nothing
 */
void jvm_free(minijvm* jvm)
{

    if(jvm == NULL)
    {
        fprintf(stderr, "Error: jvm Does Not Exist\n"); // Returns error if JVM does not exist
        return;
    }

    stack_free(jvm->operands); // Frees the stack used by the JVM
    free(jvm); // Frees the allocated space used by the JVM
    return;

}

/*
 * Prints a message for the user explaining the usage of mjava
 *
 * Parameters: Nothing
 *
 * Returns: Nothing
 */
void jvm_usage()
{
    fprintf(stderr, "Usage: mjvm FILENAME\n");
    exit(EXIT_FAILURE);
}

/*
 * Main entry point of access for use of minijvm by the user
 *
 * Parameters:
 *      argc: number of arguments input by the user
 *      argv[]: Array of parameters passed to the program
 */
int main(int argc, char** argv)
{
    int return_val = 0; // Return value for how program executed 0 for good 1 for error
    if(argc < 2)
    {
        jvm_usage(); // Prints usage if there is no input filename
        return_val = 1; // Sets the return value to 1 due to lack of filename
        return return_val; // Exits the program
    }

    minijvm* jvm = jvm_init(argv[1]); // Initializes the minijvm using the users input parameter
    jvm_run(jvm); // Runs the jvm

    if(jvm->return_value == 1)
    {
        return_val = 1; // Checks if errors occured during the program and returns 1 if so
    }

    jvm_free(jvm); // Frees memory allocated to the jvm and stack used by the jvm

    return return_val; // Returns the exit status

}
