#main function
def getInformation(selectedCountry,rankingFileName,capitalsFileName):

    # Opening the File
    rankFile = open(rankingFileName, "r")
    capitalFile = open(capitalsFileName, "r")

    #creates dictionaries
    uniDict = {}
    continentDict = {}

    #creates lists for each catagory
    worldRank = []
    country = []
    nationalRank = []
    score = []
    country2 = []
    capital = []
    continent = []
    uniName = []
    countriesInContinent = []
    continentList = []

    #splitting the file and putting into lists for each catagory
    for line in rankFile:
        entries1 = line.split(",")
        worldRank.append(entries1[0])
        uniName.append(entries1[1].upper())
        country.append(entries1[2].upper())
        nationalRank.append(entries1[3])
        score.append(entries1[8].strip("\n"))

    for line in capitalFile:
        entries2 = line.split(",")
        country2.append(entries2[0].upper())
        capital.append(entries2[1].upper())
        continent.append(entries2[5].strip('\n').upper())

    # Removing the titles from the list
    worldRank.remove(worldRank[0])
    uniName.remove(uniName[0])
    country.remove(country[0])
    nationalRank.remove(nationalRank[0])
    score.remove(score[0])

    #Converting to int and float
    for i in range(len(worldRank)):
        worldRank[i] = int(worldRank[i].rstrip("\n"))
        nationalRank[i]= int(nationalRank[i].rstrip("\n"))
        score[i] = float(score[i].rstrip("\n"))

    #Creating keys for each dictionary
    ukeys = []
    ckeys =[country2[0],capital[0],continent[0]]

    #After making the keys it removes the titles from each of the lists so only their respective values are in the dictionary
    country2.remove(country2[0])
    capital.remove(capital[0])
    continent.remove(continent[0])

    #Creating keys for each university
    for i in range(len(worldRank)):
        ukeys.append(i)

    # adding list to dictionary
    for i in range(len(worldRank)):
        uniDict[ukeys[i]] = worldRank[i], uniName[i], country[i], nationalRank[i], score[i]

    continentDict[ckeys[0]] = country2
    continentDict[ckeys[1]] = capital
    continentDict[ckeys[2]] = continent

    #Adding all of the countries to a list
    countryList = []
    for i in range(len(country)):
        if country[i] not in countryList:
            countryList.append(country[i])

    # close files
    rankFile.close()
    capitalFile.close()

    #Calculates the university with the highest international score in the given country and it's name
    h = 101
    for i in range(len(uniDict)):
        if selectedCountry.upper() == uniDict[i][2]:
            if uniDict[i][0] <= h:
                uniNum = i
                h = uniDict[i][0]

    #Calculates the university with the top national rank in the given country
    u = 101
    for i in range(len(uniDict)):
        if selectedCountry.upper() == uniDict[i][2]:
            if uniDict[i][3] <= u:
                uniNum2 = i
                u = uniDict[i][3]

    #Calculating the average score of the selected country by taking the sum of all Universities in the selected country divided by how many of them there are
    counter = 0
    scoreSum = 0
    for i in range(len(uniDict)):
        if selectedCountry.upper() == uniDict[i][2]:
            scoreSum = scoreSum + uniDict[i][4]
            counter += 1
    average = (scoreSum / counter)

    #Calculates the average score in the continent
    #First compares the selected country to the dictionary containing all the countries and their respective continents
    for i in range(len(continentDict[ckeys[0]])):
        if selectedCountry.upper() == (continentDict[ckeys[0]][i]):
            continentChoice = continentDict[ckeys[2]][i]
            break

    #After finding the continent the country is on it compares that to the dictionary to find all of the countries that are also in that continent and adds them to a list
    for i in range(len(continentDict[ckeys[2]])):
        if continentDict[ckeys[2]][i] == continentChoice:
            if continentDict[ckeys[0]][i] not in countriesInContinent:
                countriesInContinent.append(continentDict[ckeys[0]][i])

    #Compares the countries in the continent to the University dictionary university locations and finds the highest score by replacing the highestScore value with the top university's score
    highestScore = 0
    for i in range(len(uniDict)):
        for b in range(len(countriesInContinent)):
            if uniDict[i][2] == countriesInContinent[b]:
                if uniDict[i][4] > highestScore:
                    highestScore = uniDict[i][4]

    #Calculates the continent's average by dividing the average of the country by the highest Score in the continent
    continentAvg = (average/highestScore * 100)

    #Finds the capital city of the selected country by refrencing the selected country to the countries in the continent dictionary and their respective capitals
    for i in range(len(continentDict[ckeys[0]])):
        if continentDict[ckeys[0]][i] == selectedCountry.upper():
            selectedCapital = continentDict[ckeys[1]][i]
            break

    # opening a new output file
    outFile = open("output.txt", "w")

    # Writing into the file

    # First writes the Total number of universities
    outFile.write("Total number of universities => %s" % (len(worldRank)))

    # Then writes the available countries with a comma as long as its not the last value.
    outFile.write("\nAvailable countries => ")
    for i in range(len(countryList)):
        outFile.write("%s, " % countryList[i])

    # Finds the available continent by refrencing the countries in the list earlier to the countries in the
    # continent dictionary to find their given continents and writes their continents in the order they were written
    # in the last write statement
    for i in range(len(countryList)):
        for p in range(len(continentDict[ckeys[0]])):
            if countryList[i] == continentDict[ckeys[0]][p]:
                if continentDict[ckeys[2]][p] not in continentList:
                    continentList.append(continentDict[ckeys[2]][p])

    # Like before, it writes the available continents with commas dividing them except for the last value
    outFile.write('\nAvailable continents => ')
    for p in range(len(continentList)):
        outFile.write("%s, " % continentList[p])

    # Writes university with the highest international in the given country's name and that rank's value based on calculated
    # variables written earlier
    outFile.write('\nAt international rank => %i' % h)
    outFile.write(' The university name is => %s' % uniDict[uniNum][1])

    # Writes the university with the highest national rank in the given country's name and that rank's value based on calculated
    # variables written earlier
    outFile.write('\nAt national rank => %i' % u)
    outFile.write(' The university name is => %s' % uniDict[uniNum2][1])

    # Writes the average score within the given country based on variables calculated earlier
    outFile.write("\nThe average score => %.2f%%\n" % average)

    # Writes the relative score of the top university within the given country's continent and shows the calculation and
    # the final value
    outFile.write(f'The relative score to the top university in {continentChoice} is => ({average:.2f} / {highestScore:.2f}) x 100% = {continentAvg:.2f}%\n'.format(continentChoice, average, highestScore, continentAvg))

    # Writes the selected capital of the selected country based on variables calculated earlier
    outFile.write('The capital is => %s' % selectedCapital)

    # Writes all the universities that contain the capital of their country in their name and
    # writes an incrememnting number before
    y = 0
    outFile.write('\nThe universities that contain the capital name => ')
    for i in range(len(uniDict)):
        if selectedCapital in uniDict[i][1]:
            y += 1
            outFile.write('\n    #%i %s' % (y, uniDict[i][1]))

    # After all is written it finally closes the file
    outFile.close()

getInformation("china", "TopUni.csv", "capitals.csv")
