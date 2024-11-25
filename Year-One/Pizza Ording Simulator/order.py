from pizzaReceipt import *
#Imports the function from pizzaReceipt.py
TOPPINGS = ('ONION', 'TOMATO', 'GREEN PEPPER', 'MUSHROOM', 'OLIVE', 'OLIVE', 'SPINACH', 'BROCCOLI', 'PINEAPPLE', 'HOT PEPPER', 'PEPPERONI', 'HAM', 'BACON', 'GROUND BEEF', 'CHICKEN', 'SAUSAGE')
pizzaOrder = []
orderChoice = input('Do you want to order a pizza? ')
#Lists for toppings, the parameter for the function and asks the user if they want to order pizza

if orderChoice.upper() == 'NO' or orderChoice.upper() == 'Q':
    generateReceipt(pizzaOrder)
    #If the user enters no it prints the receipt with nothing on it.
else:
    #If the user enters anything else it moves on to customizing the pizza
    a = True
    while a:
        #loop used for repeating creating pizzas
        b = True
        while b:
            #loop used for determining the size of the pizza
            pizza = ()
            toppingSelect = []
            size = input('Choose a size: S, M, L, or XL: ')
            if size.upper() == 'S':
                b = False
            elif size.upper() == 'M':
                b = False
            elif size.upper() == 'L':
                b = False
            elif size.upper() == 'XL':
                b = False
        true = True
        while true:
            #loop used to ask the user for their choice of toppings along with giving them the option to exit and show the list of toppings
            print('Type in one of our toppings to add it to your pizza. To see the list of toppings, enter \n"LIST". When you are done adding toppings enter "X"')
            toppingChoice = input()
            if toppingChoice.upper() == 'LIST':
                print(TOPPINGS)
                #prints the list containing the toppings
            elif toppingChoice.upper() == 'X':
                continueChoice = input('Do you want to continue ordering? ')
                if continueChoice.upper() == 'NO' or continueChoice.upper() == 'Q':
                    pizza = (size, toppingSelect)
                    pizzaOrder.append(pizza)
                    true = False
                    a = False
                    #if the user enters X it asks them if they would like to continue ording more pizzas or ends, in this case they enter no and it adds the size and selected toppings to the pizzaOrder parameter
                else:
                    pizza = (size, toppingSelect)
                    pizzaOrder.append(pizza)
                    true = False
                    #Same with above but the user wants to enter more toppings to it adds the given size and toppings to the tuple then resets the pizza variable
            elif toppingChoice.upper() in TOPPINGS:
                print('Added %s to your pizza'%(toppingChoice.upper()))
                toppingSelect.append(toppingChoice)
                #adds the selected topping to a list
            else:
                print('Invalid Choice')
                #if the selected topping does not match the list it says invalid choice and asks the user to input again

    generateReceipt(pizzaOrder)
    #uses the function from pizzaReceipt.py to generate a receipt based on what was inputted in this file
