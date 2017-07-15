# Weather Simulator

Weather Simulator is a Weather Data Simulation application which could be used in a scenario where weather data needs to be simulated. 

Weather Simulator does :
- Invoke the Weather Data API provided by OpenWeatherMap and Google Map
- Simulate the Temperature , Pressure , Humidity and Time based on the Longitude,Latitude and Elevation of the cities.
- Writes the Simulated Data into a Text File


## How To Build

The source code for this solution is available on GitHub at .
Maven is the build tool and Maven install command will generate jar as the final output of the build process. The jar will contain all the class files and dependencies.

This Library requires Java JDK 7+, Eclipse and Maven running locally in order to execute and perform the tests.


To build the Weather Simulator:
Clone the [Weather Simulator](https://github.com/johnthotekat/weathersimulator) repository using this command:

```bash
$ git clone https://github.com/johnthotekat/weathersimulator
```
After cloning the repository, build the jar using:
```bash
$ mvn clean install
```
Note that during the build, a number of unit tests are run . If you want to ignore the unit tests, use:
```bash
$  mvn clean install -Dmaven.test.skip=true
```

On conclusion of the build, the JAR `weathersimulation-1.0-SNAPSHOT.jar` will be located in the subdirectory `target/`.

Below are the Maven dependencies used in the Weather Simulator Project:
- Google Gson	- Helper for parsing the response from the web services
- JUnit	- For executing the unit test cases
- Log4J	- For Logging Purpose
```xml
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.2.4</version>
		</dependency>
		
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.11</version>
			<scope>test</scope>
		</dependency>
		
		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>1.2.17</version>
		</dependency>
```

## Usage
The assembled JAR can be run anywhere having a JDK 7+ providing it's required libraries on the class path.

Configure the below parameters in the application.properties 

- apiurl : OpenWeatherMap API URL
- google_api : Google Map API URL 
- app_id : OpenWeatherMap API Key
- google_api_key : Google Map API Key
- city_ids : City ID's from OpenWeatherMap city database http://openweathermap.org/help/city_list.txt
- outputfile : Output file location where the simulated data is to be saved.

You can also include the dependency into your pom.xml .

```xml
<dependency>
	<groupId>com.tcs.weathersimulation</groupId>
	<artifactId>weathersimulator</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

And invoke the WeatherService and WeatherSimulation Service as below :  

```java
WeatherService weatherService = ServiceFactory.getWeatherServiceInstance();
List<WeatherSimulatorRecord> resultData = weatherService.invokeWeatherDataAPI();

WeatherSimulationService weatherSimultionService=ServiceFactory.getWeatherSimulationServiceInstance();
List<WeatherSimulatorRecord> records=weatherSimultionService.generateWeatherSimulationData(resultData);
```

The execution of the WeatherSimulator starts from `WeatherServiceHandler.java` .

## Running the tests
WeatherServiceHandler.java class can be used to simulate the weather data after the simulator is configured with proper values in the properties file.

## Output
Output is location can be configured in the application.properties.

The file contents are as below :

- Location : City Name is fetched from the OpenWeatherData API.
- Latitude,Longitude,Elevation : Longitude and Latitude are fetched from the OpenWeatherData API and the Elevation is fetched from the Google Map API.
- Local time : Time for a particular location is generated for the Past two years everyday for the matched current month.
- Conditions is either Snow,Rain,Sunny
- Temperature is in °C 
- Pressure is in hPa
- Relative humidity is a%.




