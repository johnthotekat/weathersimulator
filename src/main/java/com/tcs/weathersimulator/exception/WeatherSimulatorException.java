package com.tcs.weathersimulator.exception;

/**
 * Weather Simulator Exception class 
 * @author johnthotekat
 *
 */

public class WeatherSimulatorException extends Exception {

	private static final long serialVersionUID = 1804964740345836956L;

	public WeatherSimulatorException(Throwable ex) {
		super(ex);
	}
	
	public WeatherSimulatorException(String ex) {
		super(ex);
	}
	

}