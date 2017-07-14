package com.tcs.weathersimulator.serviceimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tcs.weathersimulator.common.WeatherSimulatorConstants;
import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.model.WeatherAPIResponse;
import com.tcs.weathersimulator.model.WeatherData;
import com.tcs.weathersimulator.model.WeatherSimulatorRecord;
import com.tcs.weathersimulator.service.WeatherService;
import com.tcs.weathersimulator.utils.Utility;


/**
 * This class makes has the methods while helps to invoke the weather data api
 * from the api urls specified in the application.properties
 * 
 * readJsonUtil () - executes the given apiURL and gives the json/xml response
 * invokeWeatherDataAPI() - invokes the weather data api and cast JSON response
 * to ResultData object
 * 
 * @author johnthotekat
 *
 */
public class WeatherServiceImpl implements WeatherService {
	public static final Logger LOGGER = Logger.getLogger(WeatherServiceImpl.class);

	public WeatherServiceImpl() throws WeatherSimulatorException {
		Utility.getInstance().loadPropertyFile();
	}

	/**
	 * This method makes the webservice calls to the given URL and returns the
	 * json/xml
	 * 
	 * @param jsonAPIUrl
	 * @return
	 * @throws WeatherSimulatorException
	 */
	private String readJsonUtil(String jsonAPIUrl) throws WeatherSimulatorException {
		URL url;
		HttpURLConnection conn;
		int responseCode;
		String line;
		StringBuilder json = new StringBuilder();
		try {
			url = new URL(jsonAPIUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "json/xml");
			responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				while ((line = br.readLine()) != null) {
					json.append(line);
				}
			}
		} catch (MalformedURLException e) {
			LOGGER.error("Malformed exception while making calls to API");
			throw new WeatherSimulatorException(e);
		} catch (IOException e) {
			LOGGER.error("IOException while making calls to API");
			throw new WeatherSimulatorException(e);
		}
		return json.toString();
	}

	/**
	 * This method invokes fetches the weather data and the elevation data from the API url's specified in the
	 * application.properties , for the specified cities in the property file.
	 * 
	 * The webservices are invoked and the JSON responses are parsed and stored in WeatherSimulatorRecord
	 * for every city , this method returns a List of WeatherSimulatorRecord
	 * @return
	 * @throws WeatherSimulatorException
	 */
	
	public List<WeatherSimulatorRecord> invokeWeatherDataAPI() throws WeatherSimulatorException {
		String weatherServiceURL = Utility.getInstance().getWeatherAPIUrl();
		String weatherServiceAPI = Utility.getInstance().getAppID();
		String weatherCities = Utility.getInstance().getCityIDs();
		WeatherAPIResponse resultData = null;
		List<WeatherSimulatorRecord> weatherData=new ArrayList<WeatherSimulatorRecord>(0);
		try {

			String composedWeatherURL = MessageFormat.format(weatherServiceURL, weatherCities, weatherServiceAPI);
			String output = readJsonUtil(composedWeatherURL);
			LOGGER.info("Response from API " + composedWeatherURL);

			Gson gson = new Gson();
			resultData = gson.fromJson(output, WeatherAPIResponse.class);
		} catch (Exception ex) {
			throw new WeatherSimulatorException(ex);
		}
		
		for(WeatherData data:resultData.getWeatherData()){
			WeatherSimulatorRecord wSimulation=new WeatherSimulatorRecord();
			wSimulation.setCityName(data.getName());
			wSimulation.setLatitude(data.getCoord().getLat());
			wSimulation.setLongitude(data.getCoord().getLon());
			wSimulation.setElevation(getElevation(wSimulation.getLatitude(), wSimulation.getLongitude()));
			weatherData.add(wSimulation);
		}

		LOGGER.info("Object after casting " + resultData);
		return weatherData;
	}
	
	/**
	 * This method invokes the google_api specified in the application.properties
	 * with the given latitude and longitude
	 * and returns the elevation from the google map api 
	 * @return double
	 * @throws WeatherSimulatorException
	 */
	public double getElevation(double latitude, double longitude) throws WeatherSimulatorException {
		double elevation = 0;
		String elevationApiURL = Utility.getInstance().getElevationApiURL();
		String elevationApiKey = Utility.getInstance().getElevationApiKey();
		try {
			String composedElevationURL = MessageFormat.format(elevationApiURL, latitude, longitude, elevationApiKey);
			String jsonString = readJsonUtil(composedElevationURL);
			JsonElement jElement = new JsonParser().parse(jsonString);
			JsonObject jObject = jElement.getAsJsonObject();
			elevation = jObject.getAsJsonArray(WeatherSimulatorConstants.JSON_ELEMENT_RESULT).get(0).getAsJsonObject()
					.get(WeatherSimulatorConstants.JSON_ELEMENT_ELEVATION).getAsDouble();
		} catch (Exception ex) {
			throw new WeatherSimulatorException(ex);
		}
		LOGGER.info(MessageFormat.format("Elevation for Lat-Long {0},{1} is {2}", latitude, longitude, elevation));
		return elevation;
	}
}