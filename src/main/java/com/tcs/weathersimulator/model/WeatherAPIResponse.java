package com.tcs.weathersimulator.model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class which holds the response from the openweathermap api
 * @author johnthotekat
 *
 */
public class WeatherAPIResponse {

	/*
	 * "list" is the parent element in the openweathermap api response
	 * 
	 * A sample response :
	 * 
	 * {"cnt":15,"list":[{"coord":{"lon":34.35,"lat":61.78},"sys":{"type":0,"id"
	 * :0,"message":0.0029,"country":"RU","sunrise":1499991967,"sunset":
	 * 1500059355},"weather":[{"id":800,"main":"Clear","description":"clear sky"
	 * ,"icon":"01d"}],"main":{"temp":16.43,"pressure":1006.76,"humidity":89,
	 * "temp_min":16.43,"temp_max":16.43,"sea_level":1019.02,"grnd_level":1006.
	 * 76},"wind":{"speed":2.46,"deg":130.502},"clouds":{"all":0},"dt":
	 * 1500005774,"id":509820,"name":"Petrozavodsk"}]
	 */
	@SerializedName("list")
	@Expose
	private java.util.List<WeatherData> weatherData = new ArrayList<WeatherData>();

	public java.util.List<com.tcs.weathersimulator.model.WeatherData> getWeatherData() {
		return weatherData;
	}

	public void setWeatherData(java.util.List<com.tcs.weathersimulator.model.WeatherData> list) {
		this.weatherData = list;
	}

}
