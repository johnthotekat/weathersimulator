package com.tcs.weathersimulator.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.tcs.weathersimulator.common.WeatherSimulatorConstants;
import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.model.WeatherSimulatorRecord;
/**
 * This class is reponsible for writes the WeatherSimulation Records to file
 * @author johnthotekat
 *
 */
public class FileUtils implements Serializable {

	private static final long serialVersionUID = 1L;
/**
 * Method to Write the List of WeatherSimulatorRecord to file
 * @param weatherData
 * @throws WeatherSimulatorException
 */
	public  static void fileWriteUtil(List<WeatherSimulatorRecord> weatherData) throws WeatherSimulatorException {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(Utility.getInstance().getOutputFilename());
			bw = new BufferedWriter(fw);
			for (WeatherSimulatorRecord wData : weatherData) {
				String stringData = makeWeatherDataString(wData);
				bw.write(stringData);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * This Method is used to format weather data records in the required format
	 * 
	 * @param weatherSimulationRecord
	 * @return delimited weather data String
	 */
	private static String makeWeatherDataString(WeatherSimulatorRecord weatherSimulationRecord) {
		StringBuilder weatherData = new StringBuilder();
		weatherData.append(weatherSimulationRecord.getCityName()).append(WeatherSimulatorConstants.PIPE_DELIMITER)
				.append(weatherSimulationRecord.getLatitude()).append(WeatherSimulatorConstants.COMMA)
				.append(weatherSimulationRecord.getLongitude()).append(WeatherSimulatorConstants.COMMA)
				.append(weatherSimulationRecord.getElevation()).append(WeatherSimulatorConstants.PIPE_DELIMITER)
				.append(weatherSimulationRecord.getDay()).append(WeatherSimulatorConstants.PIPE_DELIMITER)
				.append(weatherSimulationRecord.getConditions()).append(WeatherSimulatorConstants.PIPE_DELIMITER)
				.append(weatherSimulationRecord.getTemperature()).append(WeatherSimulatorConstants.PIPE_DELIMITER)
				.append(weatherSimulationRecord.getPressure()).append(WeatherSimulatorConstants.PIPE_DELIMITER)
				.append(weatherSimulationRecord.getHumidity());
		return weatherData.toString();
	}
}
