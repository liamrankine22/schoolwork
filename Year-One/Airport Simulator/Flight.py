from Airport import *
# Import Airport functions from Airport.py
class Flight:
    def __init__(self,flightNo,origin,destination):
        if isinstance(origin,Airport) == False or isinstance(destination,Airport) == False:
            raise TypeError('the origin and destination must be airport objects')
        # Checks if origin and destination are Airport objects before proceeding
        else:
            self._flightNo = flightNo
            self._origin = origin
            self._destination = destination
        # Defines all required variables

    def __repr__(self):
        if self.isDomesticFlight() == True:
        # First checks if the flight it within the country then determines if its domestic or international based on if the countries in the airport object match
            return "Flight: " + str(self._flightNo) + " from " + str(self._origin.getCity()) + " to " + str(self._destination.getCity()) + " {Domestic}"
            # Then prints the required output based on if its domestic or international
        else:
            return "Flight: "+str(self._flightNo)+" from "+str(self._origin.getCity())+" to "+str(self._destination.getCity())+" {International}"

    def __eq__(self, other):
        if isinstance(other,Flight) == False:
            return False
            # Checks if other is a Flight object, moves on to the next if statement if it's not false and returns False if it is False
        else:
            if other.getOrigin() == self._origin and other.getDestination() == self._destination:
                return True
                # if checks if the other object's origin and destination is equal to the defined origin and destination and returns True if it is
            else:
                return False

    def getFlightNumber(self):
        return self._flightNo
        # gets flight number
    def getOrigin(self):
        return self._origin
        # gets flight origin
    def getDestination(self):
        return self._destination
        # gets flight destination
    def isDomesticFlight(self):
        if self._origin.getCountry() == self._destination.getCountry():
            return True
            # checks if the origin country is equal to the destination country and returns true if they are equal, false if they are not
        else:
            return False
    def setOrigin(self,origin):
        self._origin = origin
        # sets the origin of the flight
    def setDestination(self,destination):
        self._destination = destination
        # sets the destination of the flight
