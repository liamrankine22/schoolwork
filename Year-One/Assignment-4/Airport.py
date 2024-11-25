class Airport:
    def __init__(self,code,city,country):
        self._code = code
        self._city = city
        self._country = country
        # initialized all required variables

    def __repr__(self):
        rep = self._code+" ("+self._city+", "+self._country+")"
        return rep
        # constructor for Airport objects

    #Setters
    def getCode(self):
        return self._code
        # gets the airport code
    def getCity(self):
        return self._city
        # gets the airport city
    def getCountry(self):
        return self._country
        # gets the airport country
    #Getters
    def setCity(self,city):
        self._city = city
        # sets the airport city
    def setCountry(self,country):
        self._country = country
        # sets the airport country
