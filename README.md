# task-airport
aiport api 

* [General info](#general-info)
* [Technologies](#technologies)
* [ProjectAssumptions](#projectAssumptions)
* [Setup](#technologies)
* [Example](#example)
* [SpecialCase](#specialcase)
* [Data](#data)
* [ProjectStructure](#projectStructure)
* [Tests](#tests)
* [Source](#source)

## General info
Airport REST API with two functonalities:

First for requested flight number and date it returns
* Cargo weight
* Baggage weight
* Total weight
	
Second for requested IATA code and date responds
* Number of flights departing/arriving at aiport
* Number of pieces of baggage departing/arriving at aiport.
	
	
## Technologies
Project is created with:
* Java 11
* Spring Boot 2.3.10
* IntelliJ IDE
* JUnit, Mockito, Gson

## ProjectAssumptions
* Because information in task.pdf file wasn't precise enough about what type of date should application respond to request, following assumption was made that each request has to be in Zoned Date Time. Wich is capable of matching ISO-8601 date format with offset eg. 2011-12-03T10:15:30 +01:00   
* Thus searching based on ISO date format with offset is too unique, that means there was almost no possibility to have same matching dates on IATA Airports codes with such small data sample.
* That's why special cases of a few dates flights with same IATA code for arriving/departing and Dates were made in sample JSON file's in order to show correctness of calculating results.
* Weight is returned in kg.

	
## Setup
Designed endpoints for api.

In order to get information about flights arriving/departing and piecies of baggage arriving/departing from airport :
```
/api/airport/xxx/date/yyyy-MM-dd'T'HH:mm:ss XXX
```
Where "xxx" is IATA airport code, and "yyyy-MM-dd'T'HH:mm:ss XXX" is zoned date time eg 2019-07-15T07:15:45 -02:00

Accepted IATA codes:
* SEA 
* YYZ 
* YYT 
* ANC 
* LAX 
* MIT 
* LEW 
* GDN 
* KRK 
* PPX
#

In order to get information about concrete flight's cargo, baggage and total weight:
```
/api/flight/zzz/date/yyyy-MM-dd'T'HH:mm:ss XXX
```
Where "zzz" is Flight number that is accepted in between 1000 and 9000. And "yyyy-MM-dd'T'HH:mm:ss XXX"  wich is described above.

## Example 

Example of running api :

For requested IATA Airport Code and date in url:
```
http://localhost:8080/api/airport/LAX/date/2017-12-16T12:37:42%20-01:00
```
Returns airport information in JSON in following format:
```
 {
    "flightsDeparting": "1",
    "flightsArriving": "0",
    "baggageArrivingPieces": "0",
    "baggageDepartingPieces": "1768"
  }
```
#
For requested Flight Number and date in url:
```
http://localhost:8080/api/flight/6902/date/2017-12-16T12:37:42%20-01:00
```
Api responds with information about concrete flight's weight in kg, in JSON format:
```
 {
    "cargoWeight": "1917",
    "baggageWeight": "811",
    "totalWeight": "2728",
  }
```
## SpecialCase
Because one of project assumption and uniqueness of Date. In order to check possibility of flights arriving/departing at same aiport that is specified in requested IATAcode. Modified JSONs were created with same Date but different airport's IATAcodes for arrival/departing.
```
{
    "flightId": 0,
    "flightNumber": 7103,
    "departureAirportIATACode": "ANC",
    "arrivalAirportIATACode": "GDN",
    "departureDate": "2021-02-06T04:15:29 -01:00"
  },
  {
    "flightId": 1,
    "flightNumber": 3782,
    "departureAirportIATACode": "GDN",
    "arrivalAirportIATACode": "YYT",
    "departureDate": "2021-02-06T04:15:29 -01:00"
  }
```
Based on preceding JSONs, api produces depicted below respond for following url:
```
/api/airport/GDN/date/2021-02-06T04:15:29 -01:00
 
 {
    "flightsDeparting": "1",
    "flightsArriving": "1",
    "baggageArrivingPieces": "2866",
    "baggageDepartingPieces": "4789"
  }
```
## Data
Random JSON data is located in following directory:
```
src/main/resources/json/
```
It contains:
* flight.json that stores information about flightEntity
* cargo.json it stores information about cargoEntity and nested within objects.
* Each data file is generated with https://www.json-generator.com/ with parameters set to {{repeat(20)}}


## ProjectStructure
Project structure and business logic

```
src/main/java/com/aviation/task/airport/bootstrap/
```
Bootsrap package contains two classes DataInit wich loads generated JSON data from json files and DataLoader wich is responsible to start and initlizie data with spring boot application execution.

#
```
src/main/java/com/aviation/task/airport/controller/
```
Controller package inside hides a rest AiportController wich listens to URL endpoints described in Example category

#
```
src/main/java/com/aviation/task/airport/converter/
```
Conventer package contains gson customized ZonedDateTime converter

#
```
src/main/java/com/aviation/task/airport/exception/
```
Exception package includes as name suggest exceptionHandler, that for eg. catches validation exceptions.

#
```
src/main/java/com/aviation/task/airport/model/
```
Model package is responsible to map JSON to POJO

#
```
src/main/java/com/aviation/task/airport/service/
```

The Service package is heart of business logic that produces expected by client results.
* It's divided by Interfaces and classes that implement them, each service is using java's  streampipelines & lambdas in order to deliver correct outcomes.
* FlightEntityServiceImpl calculates number of flights arriving/departing and pieces of baggage arriving/departing at concrete airport. Additionaly it finds flightId based on flightNumber and Date. 
* CargoEntityServiceImpl produces information about concrete flight's cargo weight, baggage weight and total weight, it uses FlightEntityServiceImpl's help in order to find correct flight ID.

## Tests

Each class has their own corresponding test class that makes unit and integration(Some tests need application context to inject correct dependencies) tests. Except classes that come from "model" package. 

Tests that were made, are based on different from main applciation JSON data files, they are located in following directory:
```
main/src/test/resources/json/testcargo.json
main/src/test/resources/json/testflight.json
```

## Source
JSON random data https://www.json-generator.com/ 

Mapping of objects https://www.jsonschema2pojo.org/
