package com.tcs.weathersimulator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class to parse the webservice response
 * parent json element: list [WeatherAPIResponse.java]
 * @author johnthotekat
 *
 */
public class Coord {

	//Longitude from the webservice response
	@SerializedName("lon")
	@Expose
	private Double lon;
	
	//Latitude from the web service response
	@SerializedName("lat")
	@Expose
	private Double lat;

	public Double getLon() {
		return lon;
	}
	
	public Double getLat() {
		return lat;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

}