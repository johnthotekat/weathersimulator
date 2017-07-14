package com.tcs.weathersimulator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class which iterates through each and every element in the json array 
 * having the parent as "list"
 * @author johnthotekat
 *
 */
public class WeatherData {

	// Holds the coordinates of the location from the webservice response
	@SerializedName("coord")
	@Expose
	private Coord coord;
	
	// Holds the name of the cities from the webservice response
	@SerializedName("name")
	@Expose
	private String name;

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}