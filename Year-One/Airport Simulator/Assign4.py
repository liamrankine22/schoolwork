from Flight import *
from Airport import *
# Imports functions from the Flight and Airport files

# LISTS FOR USE IN CODE
allAirports = []
allFlights = {}
testList = []
airportCode = []
city = []
country = []
originList = []
destinationList = []
flightNo = []
origin = []
destination = []
flightObjects = []
cityList = []
countryList =[]

def loadData(airportFile, flightFile):
    try:
    # Checks if the required files work with the function, if not it returns False
        airportFileOpen = open(airportFile, "r")
        flightFileOpen = open(flightFile, "r")
        #opens the files required for program to work

        for line in airportFileOpen:
            entries1 = line.split(",")
            airportCode.append(entries1[0].strip())
            country.append(entries1[1].strip())
            city.append(entries1[2].strip("\n").strip('\t').strip())
            # Extracts the airport file into indiviual values to be added to lists while removing white space and additonal eleemtns

        for i in range(len(airportCode)):
            allAirports.append(Airport(airportCode[i],city[i],country[i]))
            # Adds the lists created from the airport file's information into a singular list

        for line in flightFileOpen:
            entries2 = line.split(",")
            flightNo.append(entries2[0].strip())
            origin.append(entries2[1].strip())
            destination.append(entries2[2].strip("\n").strip('\t').strip())
            # Extracts the flight file's info and puts them into, individual lists while removing white space and additional elements

        for i in range(len(origin)):
            for c in range(len(allAirports)):
                if origin[i] == (allAirports[c].getCode()):
                    originList.append(allAirports[c])
                    # Checks if the origin is equal to every airport object's code and puts the airport objects into lists if their code is equal to the origin
        for i in range(len(destination)):
            for c in range(len(allAirports)):
                if destination[i] == (allAirports[c].getCode()):
                    destinationList.append(allAirports[c])
                    # Destination if the origin is equal to every airport object's code and puts the airport objects into lists if their code is equal to the destination
        for i in range(len(originList)):
            if origin[i] not in allFlights:
                allFlights[origin[i]] = [Flight(flightNo[i], originList[i], destinationList[i])]
                # Adds the first element into the dictionary
            else:
                allFlights[origin[i]].append(Flight(flightNo[i], originList[i], destinationList[i]))
                # Adds the rest of the elements into the dictionary
        return True
    except:
        return False


def getAirportByCode(code):
    for i in range(len(allAirports)):
        if code == allAirports[i].getCode():
            return allAirports[i]
            # Checks if the given code is equal to each airport's code and returns the given airport object that matches
        #if it fails it returns -1
    return -1

def findAllCityFlights(city):
    for i in range(len(origin)):
        for c in range(len(allFlights[origin[i]])):
            if city == allFlights[origin[i]][c].getOrigin().getCity() or city == allFlights[origin[i]][c].getDestination().getCity():
                if allFlights[origin[i]][c] not in cityList:
                    cityList.append(allFlights[origin[i]][c])
    return cityList
    # Checks if the given city is equal to each flight's origin city or destintation city and adds it to a list and returns it

def findAllCountryFlights(country):
    for i in range(len(origin)):
        for c in range(len(allFlights[origin[i]])):
            if country == allFlights[origin[i]][c].getOrigin().getCountry() or country == allFlights[origin[i]][c].getDestination().getCountry():
                if allFlights[origin[i]][c] not in countryList:
                    countryList.append(allFlights[origin[i]][c])
    return countryList
    # Checks if the given country is equal to each flight's origin country or destintation country and adds it to a list and returns it

def findFlightBetween(origAirport,destAirport):
    flightBetweenSet = set()
    for i in range(len(origin)):
        for c in range(len(allFlights[origin[i]])):
            if origAirport.getCode() == allFlights[origin[i]][c].getOrigin().getCode() and destAirport.getCode() == allFlights[origin[i]][c].getDestination().getCode():
                return 'Direct Flight: '+origAirport.getCode()+' to '+destAirport.getCode()
                # Goes through every flight and checks if their code is equal to the given airport object's code as well as its destination code
                # If they both are equal it returns the direct flight with their airport codes
    for i in range(len(origin)):
        for c in range(len(allFlights[origin[i]])):
            if origAirport == allFlights[origin[i]][c].getOrigin():
                x = allFlights[origin[i]][c].getDestination().getCode()
                for p in range(len(allFlights[x])):
                    if allFlights[x][p].getDestination() == destAirport:
                        flightBetweenSet.add(x)
                        # If its not a direct flight finds if there are any flights locations in between the origin and destination and adds them to a list
    if len(flightBetweenSet) > 0:
        return flightBetweenSet
    return -1
    # Checks if it finds any inbetween locations and if so it returns them, if not it returns -1

def findReturnFlight(firstFlight):

    for i in range(len(origin)):
        for c in range(len(allFlights[origin[i]])):
            if allFlights[origin[i]][c].getDestination() == firstFlight.getOrigin() and allFlights[origin[i]][c].getOrigin() == firstFlight.getDestination():
                return allFlights[origin[i]][c]
    return -1
    #Checks if the flight object's origin is equal to any flight objects in the dictonary destinations, and vice versa, if it finds the flight object that fufills the requirements
