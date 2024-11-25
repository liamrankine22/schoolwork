def generateReceipt(pizzaOrder):
#Defines function with the parameter of pizzaOrder
    if len(pizzaOrder) == 0:
        print('You did not order anything')
    #Checks if the user entered nothing for tuple
        return

    print('Your Order: ')
    price = 0
    totalExtraToppingCost = 0
    #Sets price and total extra toppings cost to 0 to be incremented later, as well as starting off with the title of the order
    for i in range (len(pizzaOrder)):
        if pizzaOrder[i][0].upper() == "S":
            priceSmall = 7.99
            price += 7.99
            extraToppingCost = 0.5
            print('Pizza %d: %-2s %15.2f' % (i + 1, pizzaOrder[i][0].upper(), priceSmall))
            #Checks the size of the pizza and adds the cost of the pizza to the total price along with printing it on the receipt. Same goes for the 3 other if statements below
        elif pizzaOrder[i][0].upper() == "M":
            priceMedium = 9.99
            price += 9.99
            extraToppingCost = 0.75
            print('Pizza %d: %-2s %15.2f' % (i + 1, pizzaOrder[i][0].upper(), priceMedium))
        elif pizzaOrder[i][0].upper() == "L":
            priceLarge = 11.99
            price += 11.99
            extraToppingCost = 1.00
            print('Pizza %d: %-2s %15.2f' % (i + 1, pizzaOrder[i][0].upper(), priceLarge))
        elif pizzaOrder[i][0].upper() == "XL":
            priceXLarge = 13.99
            price += 13.99
            extraToppingCost = 1.25
            print('Pizza %d: %-2s %15.2f'%(i+1, pizzaOrder[i][0].upper(), priceXLarge))
        for x in range(len(pizzaOrder[i][1])):
            print('- %s' %(pizzaOrder[i][1][x].upper()))
            #prints the toppings entered given from the tuple
        for z in range(len(pizzaOrder[i][1])):
            if z > 2:
                print('Extra Topping %-4s %8.2f'%("(" + pizzaOrder[i][0] + ")", extraToppingCost))
                totalExtraToppingCost += extraToppingCost
                #Prints out extra topping along with their price based on size for each number of toppings there are above 3. It also adds it to a total of extra topping
    tax = (price+totalExtraToppingCost)*0.13
    print('Tax: %22.2f'%(tax))
    print('Total: %20.2f'%(price+totalExtraToppingCost+tax))
    #Calculates the tax and final price and prints at the end of the receipt after all entered pizzas
