package com.tcs.weathersimulator.model;

/**
 * Model class which holds the simulated data
 * @author johnthotekat
 *
 */

public class WeatherSimulatorRecord {
	
	private String cityName;
	private double latitude;
	private double longitude;
	private double elevation;
	private String day;
	private String conditions;
	private double temperature;
	private double pressure;
	private int humidity;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getElevation() {
		return elevation;
	}
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	
	@Override
	public String toString() {
		return cityName+"-"+latitude+","+longitude+","+elevation+"-"+pressure+"-"+temperature+"-"+humidity;
	}
}
