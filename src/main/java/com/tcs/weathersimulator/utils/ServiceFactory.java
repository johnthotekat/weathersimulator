package com.tcs.weathersimulator.utils;

import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.serviceimpl.WeatherServiceImpl;
import com.tcs.weathersimulator.serviceimpl.WeatherSimulationServiceImpl;

public class ServiceFactory {
/**
 * A singleton class which provides the WeatherService and WeatherSimulationService to the required classes
 */
	private static WeatherServiceImpl weatherServiceImpl = null;
	private static WeatherSimulationServiceImpl weatherSimulationServiceImpl=null;

	private ServiceFactory() {

	}

	/**
	 * Method to return the Weather Service object
	 * @return WeatherServiceImpl
	 */
	public static WeatherServiceImpl getWeatherServiceInstance(){
		if(weatherServiceImpl==null)
			try {
				weatherServiceImpl = new WeatherServiceImpl();
			} catch (WeatherSimulatorException e) {
				e.printStackTrace();
			}
		return weatherServiceImpl;
	}
	/**
	 * Method to return Weather Simulation Service object
	 * @return WeatherSimulationServiceImpl
	 */
	public static WeatherSimulationServiceImpl getWeatherSimulationServiceInstance() {
		if(weatherSimulationServiceImpl==null)
			weatherSimulationServiceImpl=new WeatherSimulationServiceImpl();
		return weatherSimulationServiceImpl;
	}
}