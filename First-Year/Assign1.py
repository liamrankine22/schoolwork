#Liam Rankine's Code
#Program's purpose is to calculate the amount of inflation on a given year based on the information given
#This is calculated by asking the user for expenses from the year of interest and the previous year then uses a formula
#to calculate the inflation and shows it to the user and what type of inflation it is.

year = int(input('Please enter the year that you want to calculated the personal interest rate for: '))
numCategory = int(input('Please enter the number of expenditure categories: '))
#Asks the user for the year and how many expenditure categories

if numCategory>0:
    #Ensures that the number of categories is above 0 so the code doesn't break
    firstExpense = float(input('Please enter expenses for previous year: '))
    firstInterest = float(input('Please enter expenses for year of interest: '))
    #Asks user for the first set of expenses for the previous and year or intrest

    totalExpen = firstExpense
    totalInterest = firstInterest
    #Sets the variables stated before as the current total

    counter = 0
    while counter<(numCategory - 1):
        #Loops for as many times as the user input the number of categories
        expenses = float(input('Please enter expenses for previous year: '))
        interest = float(input('Please enter expenses for year of interest: '))
        #Asks the user for the next pair of expenses for the year of interest and the previous

        counter = counter + 1
        #Increases the counter to stop the loop from going on forever

        totalExpen = totalExpen + expenses
        totalInterest = totalInterest + interest
        #Adds the new expenses to the totals

    inflRate = (((totalInterest-totalExpen)/totalInterest)*100)
    # Calculates the inflation rate by dividing the total expenses of the previous year to the year of interests than subtracts
    #it from 1 to get the real percentage and multiplies it by 100 to get the percentage in a full number

    print('Personal inflation rate for %s is %s%%.' % (year, inflRate))
    #Prints the inflation rate for the year inputted

    if inflRate < 3:
        print('Type of Inflation: Low')

    elif 3 <= inflRate < 5:
        print('Type of Inflation: Moderate')

    elif 5 <= inflRate < 10:
        print('Type of Inflation: High')

    elif inflRate >= 10:
        print('Type of Inflation: Hyper')
#If and else if statements for assigning how high the inflation percentages are
else:
    print('Numbers of categories entered were less than 1 so interest could not be calculated, please try again.')
#If the number of categories were less than 1 the code would not function so this was added to let the user know this.
