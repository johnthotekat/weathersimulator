package com.tcs.weathersimulator.handler;

import java.util.List;

import org.apache.log4j.Logger;

import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.model.WeatherSimulatorRecord;
import com.tcs.weathersimulator.service.WeatherService;
import com.tcs.weathersimulator.service.WeatherSimulationService;
import com.tcs.weathersimulator.utils.ServiceFactory;

public class WeatherServiceHandler {
	
	public static final Logger LOGGER = Logger.getLogger(WeatherServiceHandler.class);

	/**
	 * Execution of the Weather Simulator starts here
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			WeatherServiceHandler weatherService = new WeatherServiceHandler();
			weatherService.doProcess();
		} catch (WeatherSimulatorException e) {
			e.printStackTrace();
			LOGGER.info("Error while processing application request "
					+ e.getMessage());
		}
	}

	/**
	 * This method invokes the WeatherService API and the WeatherSimulationService
	 * 
	 * @throws WeatherSimulatorException
	 */
	public void doProcess() throws WeatherSimulatorException {

		WeatherService weatherService = ServiceFactory.getWeatherServiceInstance();
		WeatherSimulationService weatherSimultionService=ServiceFactory.getWeatherSimulationServiceInstance();
		
		List<WeatherSimulatorRecord> resultData = weatherService.invokeWeatherDataAPI();
		LOGGER.info("Weather Service Invoked - Output size: "+resultData.size());
		
		List<WeatherSimulatorRecord> records=weatherSimultionService.generateWeatherSimulationData(resultData);
		
		LOGGER.info("Weather Simulation Completed - Output size: "+records.size());
		
		weatherSimultionService.writeWeatherSimulationToFile(records);
	}
}
