# HereMap
This service helps in finding the Places(Restaurant, Parking, Charging Station) nearby any location using HereMap APIs

This service use the Async execution for calling APIs parallely.
It also uses the Caching to store the result and to avoid repeated call.

#Setup
It can be run by cloning the sourceCode or by running the docker image as shared below :

Prerequisite   :
Maven should be installed.
Java8 should be installed.
Docker should be installed, if we want to run via docker.
Lombok plugin.

Steps for the local set-up
1. clone the repo using https://github.com/ashutosh0494/HereMap.git
2. Open the project in any Ide and run the command : mvn clean install
3. Run the main application class : HeremapApplication.class
4. server will start on port 8080 by default

Steps for the set-up using docker 
1. run the command docker pull ashutosh0494/heremapnew:latest   
2. docker run -p8080:8080 ashutosh0494/heremapnew  

Once the server  is up, open the swagger on URL : http://localhost:8080/swagger-ui.html#/
and hit the api  /api/searchNearByPlacesForAllCatg with input as any city name, lets say : Bangalore

Or use the Curl command http://localhost:8080/api/searchNearByPlacesForAllCatg?locName=Bangalore to get the response.