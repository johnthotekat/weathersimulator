package com.tcs.weathersimulator.service;

import java.util.List;

import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.model.WeatherSimulatorRecord;

/**
 * This interface contains the methods necessary to generate the weather simulation data 
 * @author johnthotekat
 *
 */
public interface WeatherSimulationService {
	
	/**
	 * Method to intake response from the openweather data api and simulates the weather data
	 * @param resultData
	 * @return The list of records with simulated data 
	 * @throws WeatherSimulatorException
	 */
	public List<WeatherSimulatorRecord> generateWeatherSimulationData(List<WeatherSimulatorRecord> weatherData) throws WeatherSimulatorException;

	/**
	 * Method to write the weather data to file
	 * @param weatherSimulatorRecords
	 * @throws WeatherSimulatorException
	 */
	public void writeWeatherSimulationToFile(List<WeatherSimulatorRecord> weatherSimulatorRecords) throws WeatherSimulatorException;
}
