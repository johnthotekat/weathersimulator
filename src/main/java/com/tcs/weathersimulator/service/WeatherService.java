package com.tcs.weathersimulator.service;

import java.util.List;

import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.model.WeatherSimulatorRecord;

public interface WeatherService {
/**
 * This method invokes fetches the weather data and the elevation data from the API url's specified in the
 * application.properties , for the specified cities in the property file.
 * 
 * The web services are invoked and the JSON responses are parsed and stored in WeatherSimulatorRecord
 * for every city , this method returns a List of WeatherSimulatorRecord
 * @return
 * @throws WeatherSimulatorException
 */
	public List<WeatherSimulatorRecord> invokeWeatherDataAPI() throws WeatherSimulatorException;
	
}
