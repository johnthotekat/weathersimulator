package com.tcs.weathersimulator.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.tcs.weathersimulator.common.WeatherSimulatorConstants;
import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.model.WeatherSimulatorRecord;
/**
 * Class which ontains the simple utilities
 * @author johnthotekat
 *
 */
public class Utility {
	public static final Logger LOGGER = Logger.getLogger(Utility.class);
	private String weatherAPIUrl = null;
	private String appID = null;
	private String cityIDs = null;
	private String serviceURL = null;
	private String outputFileName = null;
	private String outputDir = null;
	private String elevationApiURL=null;
	private String elevationApiKey=null;
	private static final Utility utility = new Utility();

	private Utility() {

	}

	/**
	 * This method will return the singleton class object
	 * @return Utility
	 */
	public static Utility getInstance() {
		return utility;
	}

	/**
	 * This method loads the properties from the properties fiel
	 * @throws WeatherSimulatorException 
	 */
	public void loadPropertyFile() throws WeatherSimulatorException {
		Properties prop = new Properties();
		String propFileName = "application.properties";
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);
		if (inputStream != null) {
			try{
			prop.load(inputStream);
			} catch(IOException ex){
				throw new WeatherSimulatorException(ex);
			}
			weatherAPIUrl = prop.getProperty(WeatherSimulatorConstants.REMOTE_HOST_URL);
			appID = prop.getProperty(WeatherSimulatorConstants.APP_ID);
			cityIDs = prop.getProperty(WeatherSimulatorConstants.CITY_IDS);
			outputFileName = prop.getProperty(WeatherSimulatorConstants.OUTPUTFILE);
			outputDir = prop.getProperty(WeatherSimulatorConstants.OUTPUTDIR);
			elevationApiURL=prop.getProperty(WeatherSimulatorConstants.GOOGLE_MAP_API);
			elevationApiKey=prop.getProperty(WeatherSimulatorConstants.GOOGLE_API_KEY);
		} else {
			LOGGER.info("property file not found in the classpath ");
			throw new WeatherSimulatorException("property file '" + propFileName
					+ "' not found in the classpath");
		}
	}

	public String getFileName() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH); // 0 to 11
		int day = cal.get(Calendar.DATE);
		return Utility.getInstance().getOutputFilename() + day + "_" + month
				+ "_" + year;
	}
	
	public WeatherSimulatorRecord weatherBeanMapper(WeatherSimulatorRecord wData){
		WeatherSimulatorRecord wSim=new WeatherSimulatorRecord();
		wSim.setCityName(wData.getCityName());
		wSim.setLongitude(wData.getLongitude());
		wSim.setLatitude(wData.getLatitude());
		wSim.setElevation(wData.getElevation());
		wSim.setDay(wData.getDay());
		wSim.setConditions(wData.getConditions());
		wSim.setTemperature(wData.getTemperature());
		wSim.setPressure(wData.getPressure());
		wSim.setHumidity(wData.getHumidity());
		return wSim;
	}

	public String getserviceURL() {
		return serviceURL;
	}

	public String getOutputFilename() {
		return outputFileName;
	}

	public String getOutputdir() {
		return outputDir;
	}


	public String getElevationApiURL() {
		return elevationApiURL;
	}

	public String getElevationApiKey() {
		return elevationApiKey;
	}


	public String getAppID() {
		return appID;
	}


	public String getCityIDs() {
		return cityIDs;
	}

	public String getWeatherAPIUrl() {
		return weatherAPIUrl;
	}
}