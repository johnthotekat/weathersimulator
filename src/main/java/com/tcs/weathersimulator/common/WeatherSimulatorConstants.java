package com.tcs.weathersimulator.common;

/**
 * This class contains all the constants used through out the weather simulator
 * @author johnthotekat
 *
 */
public class WeatherSimulatorConstants {
	
	public static final String FILE_SEPARATOR = "|";
	public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String COMMA = ",";
	public static final String NEWLINE = "\n";
	public static final String STD_FORMAT = "yyyy-MM-dd hh:mm:ss";

	public static final String JSON_PARENT = "list";
	public static final String GOOGLE_MAP_API = "google_api";
	public static final String GOOGLE_API_KEY = "google_api_key";
	public static final String JSON_ELEMENT_RESULT = "results";
	public static final String JSON_ELEMENT_ELEVATION = "elevation";

	public static final double TEMP_SNOW_MAX = 3;
	public static final double TEMP_WIND_MIN = 3;
	public static final double TEMP_WIND_MAX = 11;
	public static final double TEMP_RAIN_MIN = 11;
	public static final double TEMP_RAIN_MAX = 22;

	public static final double CELSIUS_CONV = 273.15;
	public static final double PRESSURE_POW = 5.257;

	public static final String PIPE_DELIMITER = "|";

	public static final String REMOTE_HOST_URL = "apiurl";
	public static final String APP_ID = "app_id";
	public static final String CITY_IDS = "city_ids";
	public static final String OUTPUTFILE = "outputfile";
	public static final String OUTPUTDIR = "outputfile";

	public static final int SH_MIN_LAT = -20;
	public static final int NH_MAX_LAT = 16;
	public static final int TEMP_MEAN = 27;
	public static final double NH_VAR_TEMP = 0.85;
	public static final double SH_VAR_TEMP = 0.63;
	public static final int TEMP_ELEV = 4;
	public static final int WIND_DOWN = 1400;
	public static final int MONTH_MID_LIMIT = 6;
	public static final int MONTH_LIMIT = 11;
	public static final double RANGE_POW = 0.2;
	public static final int MEAN_DIN = 2;
	public static final int MONTH_VAR_DIN = 5;
	public static final int TEMP_DAILY_VAR = 3;
	public static final int DAILY_MORN = 5;
	public static final int TIME_NOON = 12;
	public static final int DAILY_EVE = 21;
	public static final int TWO_HR = 120;
	public static final int ONE_MIN = 1;
	public static final int TIME_MORNING_MAX = 7;
	public static final int TIME_NOON_MAX = 14;
	public static final int DAILY_EVE_MAX = 23;

	public static final double PRESSURE_SEA_LEVEL = 1013.25;

	public static final double HUM_CONSTANT = 2.16679;

}
