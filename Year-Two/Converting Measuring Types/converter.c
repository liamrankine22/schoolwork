/************************************************************************************
 *                                                                                  *
 * Converter.c                                                                      *
 *                                                                                  *
 * Computer Science 2211B - Winter 2024                                             *
 * Assignment 2 Part 1                                                              *
 * Student: Liam Rankine                                                            *
 *                                                                                  *
 * This program allows the user to convert several different units of measurement   *
 * to another unit of measurment.                                                   *
 ************************************************************************************/

#include <stdio.h>

/*
 * Converts from Celius to Fahrenheit and vice versa
 *
 * Parameters:
 *     user_input_value: Contains user input value to be converted
 *     conversion_type: Contains the type of conversion input by the user;
 *
 * Returns: The converted value stored in converted_value
 */
char ctof()
{
    // Storing Conversion Type
    char conversion_type;
    // Storing user input to be converted
    float  user_input_value;
    // Part of the conversion from the two temperatures
    const float FREEZING_PT = 32.0f;
    // Part of the conversion from the two temperatures
    const float SCALE_FACTOR = (5.0f / 9.0f);

    // Infinte loop for incase inputs are wrong
    while(1){

        // Asks the user to input the type of conversion then stores it
        printf("Enter C to convert from Celius to Fahrenheit, or F to convert from Fahrenheit to Celcius: ");
        scanf(" %c", &conversion_type);

        // If the input is valid, asks the user the value to be converted
        if(conversion_type == 'c' || conversion_type == 'C' || conversion_type == 'f' || conversion_type == 'F'){
            printf("Enter value to be converted: ");
            scanf("%f", &user_input_value);
        }

        // Lets the user know an error has occured that they inputted an incorrect value
        else
        {
            printf("Error: Invalid Conversion Type\n");
        }

        // Converts celcius to fahrenheit
        if(conversion_type == 'c' || conversion_type == 'C')
        {
            float converted_value = (user_input_value * SCALE_FACTOR) + FREEZING_PT;
            printf("%.2f C = %.2f F\n", user_input_value, converted_value);
            return 0;
        }

        // Converts fahrenheit to celcius
        if(conversion_type == 'f' || conversion_type == 'F')
        {
            float converted_value = (user_input_value - FREEZING_PT) * SCALE_FACTOR;
            printf("%.2f F = %.2f C\n", user_input_value, converted_value);
            return 0;
        }
    }
}
/*
 * Converts kilometers to miles or vice versa
 *
 * Parameters:
 *     user_input_value: Contains the user input value to be converted
 *     conversion_type: Contains the type of conversion input by the user
 *
 * Returns: The converted value stored in converted_value
 */

int ktom()
{
      // infinite loop incase inputs are wrong
    while(1){

        // Stores the type of conversion
        char conversion_type;
        // Value containing the user input value to be converted
        float user_input_value;
        // Constant conversion value for both kilometers and miles
        const float CONVERSION_VALUE = 1.609344;
        // Contains the converted value done by an operation using both the input value and conversion constant
        float converted_value;

        // Asks the user if they want to convert from kilometers to miles or vice versa then stores their option
        printf("Enter K to convert from Kilometers to Miles, or M to convert from Miles to Kilometers: ");
        scanf(" %c", &conversion_type);

        // if the input is valid it asks the user for the value to be converted
        if(conversion_type == 'm' || conversion_type == 'M' || conversion_type == 'k' || conversion_type == 'K')
        {
            printf("Enter value to be converted: ");
            scanf("%f", &user_input_value);
        }

        // Lets the user know their input was invalid
        else
        {
            printf("Error: Invalid Conversion Type\n");
            continue;
        }

        // Converts from kilometers to miles
        if(conversion_type == 'k' || conversion_type == 'K')
        {
            converted_value = user_input_value/CONVERSION_VALUE;
            printf("%.2f K = %.2f M\n", user_input_value, converted_value);
            return 0;
        }

        // Converts from miles to kilometers
        if(conversion_type == 'm' || conversion_type == 'M')
        {
            converted_value = user_input_value * CONVERSION_VALUE;
            printf("%.2f M = %.2f K\n", user_input_value, converted_value);
            return 0;
        }
    }
}
/*
 * Converts Joules to Calories and vice versa
 *
 * Parameters:
 *     user_input_value: Stores the user input value to be converted
 *     converstion_type: Stores the type of conversion input by the user
 *
 * Return: the converted value stored in converted_value
 */
int jtoc()
{
    //Infinite loop incase inputs are wrong
    while(1)
    {

        // Contains the type of conversion to be done by the user
        char conversion_type;
        // Contains the value input by the user to converted
        float  user_input_value;
        // Contains the constant conversion value between Joules and Calories
        const float CONVALUE = 4.184;
        // Stores the converted value obtained by an operation including the input value and conversio constant
        float converted_value;

        // Asks the user if they would like to convert from joules to calories and vice versa then stores their option
        printf("Enter J to convert from Joules to Calories, or C to convert from Calories to Joules: ");
        scanf(" %c", &conversion_type);

        //Checks if the user's input was valid then asks what value theyd like to convert
        if(conversion_type == 'j' || conversion_type == 'J' || conversion_type == 'c' || conversion_type == 'C')
        {
            printf("Enter value to convert: ");
            scanf("%f", &user_input_value);
        }

        // Lets the user know their input was invalid
        else
        {
            printf("Error: Invalid Conversion Type\n");
        }

        // Converts from joules to calories
        if(conversion_type == 'j' || conversion_type == 'J')
        {
            converted_value = user_input_value/CONVALUE;
            printf("%.3f J = %.3f C\n", user_input_value, converted_value);
            return 0;
        }

        // Converts from calories to joules
        if(conversion_type == 'c' || conversion_type == 'C')
        {
            converted_value = user_input_value * CONVALUE;
            printf("%.3f C = %.3f J\n", user_input_value, converted_value);
            return 0;
        }
    }
}
/*
 * Main interface for helping the user access the different conversion methods
 *
 * Parameters:
 *     operation_type: contains the number to be refrenced to its relative operation
 *
 * Returns: The interface for converting the requested conversion type
 */
int  main()
{
      // Infinite loop so that the user has to properly input the operation to exit
    while(1)
    {
        // Stores the integer containing which type of conversion the user would like to make.
        int operation_type;

        // Asks the user what type of conversion they'd like to calculate and stores their input
        printf("Enter 1 for conversion between Celcius and Fahrenheit\n"
        "Enter 2 for conversion between Kilometers and Miles\n"
        "Enter 3 for conversion between Joules and Calories\n"
        "Enter 0 to exit\n");
        printf("Enter option: ");
        scanf("%d", &operation_type);

        // If the user input 1 they go to the method calculating celcius and fahrenheit
        if(operation_type == 1)
        {
            ctof();
        }

        // If the user input 2 they go to the method calculating kilometers and miles
        if(operation_type == 2)
        {
            ktom();
        }

        // if the user input 3 they go to the method calculating joules and calories
        if(operation_type == 3)
        {
            jtoc();
        }

        // if the user input 0 the program exits
        if(operation_type == 0)
        {
            return 0;
        }

        // Lets the user know their input didn't match to a valid operation
        if(operation_type != 0 || operation_type != 1 || operation_type != 2 || operation_type != 3)
        {
            printf("Error: Invalid integer entered / no respective operation found\n");
        }
    }
}
