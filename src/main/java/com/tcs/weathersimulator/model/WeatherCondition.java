package com.tcs.weathersimulator.model;

/**
 * This enumeration holds the Weather Condition information
 * @author johnthotekat
 *
 */
	public enum WeatherCondition {

		WIND("Windy"),SNOW("Snow"), SUNNY("Sunny"), RAIN("Rain");

		private String condition;

		WeatherCondition(String condition) {
			this.condition = condition;
		}

		public String condition() {
			return condition;
		}
	}