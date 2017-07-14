package com.tcs.weathersimulator.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;

import com.tcs.weathersimulator.common.WeatherSimulatorConstants;
import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.model.WeatherCondition;
import com.tcs.weathersimulator.model.WeatherSimulatorRecord;
import com.tcs.weathersimulator.service.WeatherSimulationService;
import com.tcs.weathersimulator.utils.FileUtils;
import com.tcs.weathersimulator.utils.RandomGenerator;
import com.tcs.weathersimulator.utils.Utility;

/**
 * Class to generate data to simulate the Weather Parameters
 * @author johnthotekat
 *
 */

public class WeatherSimulationServiceImpl implements WeatherSimulationService {

	public static final Logger LOGGER = Logger.getLogger(WeatherSimulationServiceImpl.class);

	public double monthlyVariation;

	/**
	 * Method to simulate the data for the locations from the web service response
	 * 
	 * @param weatherData
	 * @return Records having the simulated data 
	 */
	public List<WeatherSimulatorRecord> generateWeatherSimulationData(List<WeatherSimulatorRecord> weatherData)
			throws WeatherSimulatorException {

		LOGGER.info("[START] Generating Weather Data Simulation ");
		List<WeatherSimulatorRecord> weatherDataList = new ArrayList<WeatherSimulatorRecord>();
		
		double annualMeanTemp = 0;
		double dailyTempRange = 0;
		double temperature = 0;
		double pressure = 0;
		WeatherCondition weatherCondition;

		for (WeatherSimulatorRecord wData : weatherData) {
			annualMeanTemp = getAnnualMeanTemp(wData.getLatitude(), wData.getElevation());
			Map<Integer, Double> MonthlyMeanTempMap = getMonthlyMeanTemperature(annualMeanTemp, wData.getLatitude());
			dailyTempRange = getDailyTempRange(wData.getElevation());

			for (Entry<Integer, Double> entry : MonthlyMeanTempMap.entrySet()) {
				List<Date> localTimeList = getLocalTime(entry.getKey());
				for (Date localTime : localTimeList) {
					temperature = getTemperature(localTime, entry.getValue(), dailyTempRange);
					pressure = getPressure(wData.getElevation(), temperature);
					weatherCondition = getConditions(temperature);
					wData.setDay(new SimpleDateFormat(WeatherSimulatorConstants.ISO_FORMAT).format(localTime));
					wData.setConditions(weatherCondition.condition());
					wData.setTemperature(temperature);
					wData.setPressure(pressure);
					wData.setHumidity(getHumidity(pressure, temperature, weatherCondition));
					
					weatherDataList.add(Utility.getInstance().weatherBeanMapper(wData));
				}
			}
		}

		if (weatherDataList.size() > 0) {
			LOGGER.info("Weather Simulation successfull! ");
			writeWeatherSimulationToFile(weatherDataList);
			return weatherDataList;
		} else {
			LOGGER.info("Could not generate weather data ..");
			throw new WeatherSimulatorException("There is no record to generate or there was an error !");
		}
	}
	
	/**
	 * This method captures the type of weather condition based upon the
	 * temperature
	 * 
	 * @param temperature
	 * @return WeatherCondition
	 */
	public WeatherCondition calculateWeatherCondition(double temperature) {
		LOGGER.info("Calculating weather condition");
		if (temperature <= WeatherSimulatorConstants.TEMP_SNOW_MAX) {
			return WeatherCondition.SNOW;
		} else if (temperature <= WeatherSimulatorConstants.TEMP_RAIN_MAX && temperature > WeatherSimulatorConstants.TEMP_RAIN_MIN) {
			return WeatherCondition.RAIN;
		} else if (temperature <= WeatherSimulatorConstants.TEMP_WIND_MAX && temperature > WeatherSimulatorConstants.TEMP_WIND_MIN) {
			return WeatherCondition.WIND;
		} else {
			return WeatherCondition.SUNNY;
		}
	}

	/**
	 * This Method to generate the temperature at a particular time
	 * This calculation is based upon the random time ,monthlyMean  and dailyTemperatureRange which was simulated
	 * @param localTime
	 * @param monthlyMean
	 * @param dailyTempRange
	 * @return Calculated temperature 
	 */
	private double getTemperature(Date localTime, double monthlyMean, double dailyTempRange) {
		double temperature;
		Calendar cal = new GregorianCalendar();
		cal.setTime(localTime);

		temperature = RandomGenerator.generateRandomDecimal(monthlyMean, monthlyMean + monthlyVariation);
		
		if (cal.get(Calendar.HOUR_OF_DAY) <= WeatherSimulatorConstants.TIME_MORNING_MAX) {
			return temperature - dailyTempRange;

		} else if (cal.get(Calendar.HOUR_OF_DAY) >= WeatherSimulatorConstants.TIME_NOON
				&& cal.get(Calendar.HOUR_OF_DAY) <= WeatherSimulatorConstants.TIME_NOON_MAX) {
			return temperature;

		} else if (cal.get(Calendar.HOUR_OF_DAY) >= WeatherSimulatorConstants.DAILY_EVE
				&& cal.get(Calendar.HOUR_OF_DAY) <= WeatherSimulatorConstants.DAILY_EVE_MAX) {
			return temperature - dailyTempRange - 1;
		}
		return temperature;
	}

	/**
	 * Method to calculate Annual Mean Temperature
	 * Data from a large number of stations suggest that the annual mean temperature is around 27 Degree C within 20 Degree S - 16 N degrees
	 * and then falls by about 0.85 K/degree latitude in the northern hemisphere and 0.63 K/Degree 
	 * lat in the south
	 * 
	 * @param latitude
	 * @param elevation
	 * @return Annual Mean Temperature
	 */
	private double getAnnualMeanTemp(double latitude, double elevation) {

		double seaLevelTemp = 0;
		// Sea Level Annual Mean Temperature using lattitude
		if (latitude < WeatherSimulatorConstants.SH_MIN_LAT) {
			seaLevelTemp = WeatherSimulatorConstants.TEMP_MEAN
					- WeatherSimulatorConstants.SH_VAR_TEMP * (WeatherSimulatorConstants.SH_MIN_LAT - latitude);
		} else if (latitude > WeatherSimulatorConstants.NH_MAX_LAT) {
			seaLevelTemp = WeatherSimulatorConstants.TEMP_MEAN
					- WeatherSimulatorConstants.NH_VAR_TEMP * (latitude - WeatherSimulatorConstants.NH_MAX_LAT);
		}
		// Annual Mean temperature of the city using elevation
		return seaLevelTemp - (elevation * WeatherSimulatorConstants.TEMP_ELEV) / 1000;

	}

	/**
	 * Method to calculate Monthly Mean Temperature
	 * 
	 * @param anualMeanTemp
	 * @param lattitude
	 * @return Monthly Temperature List
	 */
	private Map<Integer, Double> getMonthlyMeanTemperature(double anualMeanTemp, double lattitude) {

		Map<Integer, Double> MonthlyTempMap = new HashMap<Integer, Double>();
		int monthindex = 0;
		double meanTemp;
		double annumRange = 0.13 * Math.abs(lattitude)
				* Math.pow(WeatherSimulatorConstants.WIND_DOWN, WeatherSimulatorConstants.RANGE_POW);
		double janTemp = Math.round(anualMeanTemp - annumRange / WeatherSimulatorConstants.MEAN_DIN);
		double juneTemp = Math.round(anualMeanTemp + annumRange / WeatherSimulatorConstants.MEAN_DIN);

		monthlyVariation = (juneTemp - janTemp) / WeatherSimulatorConstants.MONTH_VAR_DIN;
		while (monthindex != WeatherSimulatorConstants.MONTH_MID_LIMIT) {
			if (monthindex == 0) {
				MonthlyTempMap.put(monthindex, janTemp);
				MonthlyTempMap.put((WeatherSimulatorConstants.MONTH_LIMIT - monthindex), janTemp);
			} else {
				meanTemp = MonthlyTempMap.get(monthindex - 1) + monthlyVariation;
				MonthlyTempMap.put(monthindex, meanTemp);
				MonthlyTempMap.put((WeatherSimulatorConstants.MONTH_LIMIT - monthindex), meanTemp);
			}
			monthindex++;
		}
		return MonthlyTempMap;
	}

	/**
	 * Method to calculate Daily Mean Temperature from the elevation
	 * 
	 * @param lattitude
	 * @param elevation
	 * @return Annual Mean Temperature
	 */
	private double getDailyTempRange(double elevation) {
		return WeatherSimulatorConstants.TEMP_DAILY_VAR + (2 * (elevation / 1000)) / WeatherSimulatorConstants.MEAN_DIN;
	}

	/**
	 * Method to generate Humidity at the given location
	 * 
	 * Absolute humidity is defined as the mass of water vapour in a certain
	 * volume. If ideal gas behaviour is assumed the absolute humidity can be
	 * calculated using A = C · Pw/T (g/m3) , where C = Constant
	 * 2.16679 gK/J Pw = Vapour pressure in Pa T = Temperature in K
	 * 
	 * Assumed Vapour pressure as the calculated Presure and may not be accurate
	 * 
	 * @param elevation
	 * @return
	 */
	private int getHumidity(double pressure, double temperature, WeatherCondition cond) {

		Double humidity = new Double(WeatherSimulatorConstants.HUM_CONSTANT
				* ((pressure * 2) / (WeatherSimulatorConstants.CELSIUS_CONV + temperature)));
		return humidity.intValue();
	}

	/**
	 * This method generates the pressure in hPa
	 * 
	 * P = SeaLevel Pressure * power((1- 0.0065h/ T+0.0065h + 273.15), 5.257)
	 * 
	 * @param elev
	 * @param temp
	 * @return The calculated pressure in hPa
	 */
	private double getPressure(double elev, double temp) {

		double pressure = WeatherSimulatorConstants.PRESSURE_SEA_LEVEL * Math.pow(
				(1 - ((0.0065 * elev) / (temp + (0.0065 * elev) + WeatherSimulatorConstants.CELSIUS_CONV))),
				WeatherSimulatorConstants.PRESSURE_POW);
		return pressure;
	}

	/**
	 * Method which simulates Dates from the last two years depending on the month given
	 * For a particular day 3 times [Morning, Noon and Evening] are generated with respect to the previous two years of the given month
	 * @param month
	 * @return List of dates
	 */
	public List<Date> getLocalTime(int month) {

		Random ran = new Random();
		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.YEAR, -2);
		Date startDate = startCal.getTime();

		List<Date> localTimeList = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);

		while (calendar.getTime().before(new Date())) {

			if (month == calendar.get(Calendar.MONTH)) {
				calendar.set(Calendar.HOUR_OF_DAY, WeatherSimulatorConstants.DAILY_MORN);
				calendar.add(Calendar.MINUTE,
						RandomGenerator.generateRandomInteger(WeatherSimulatorConstants.ONE_MIN, WeatherSimulatorConstants.TWO_HR, ran));
				localTimeList.add(calendar.getTime());

				calendar.set(Calendar.HOUR_OF_DAY, WeatherSimulatorConstants.TIME_NOON);
				calendar.add(Calendar.MINUTE,
						RandomGenerator.generateRandomInteger(WeatherSimulatorConstants.ONE_MIN, WeatherSimulatorConstants.TWO_HR, ran));
				localTimeList.add(calendar.getTime());

				calendar.set(Calendar.HOUR_OF_DAY, WeatherSimulatorConstants.DAILY_EVE);
				calendar.add(Calendar.MINUTE,
						RandomGenerator.generateRandomInteger(WeatherSimulatorConstants.ONE_MIN, WeatherSimulatorConstants.TWO_HR, ran));
				localTimeList.add(calendar.getTime());
			}
			calendar.add(Calendar.DATE,1);
		}
		return localTimeList;
	}

	/**
	 * This method is used to get the weather Condition based on temperature
	 * 
	 * @param temperature
	 * @return WeatherCondition
	 */
	private WeatherCondition getConditions(double temperature) {

		if (temperature <= WeatherSimulatorConstants.TEMP_SNOW_MAX) {
			return WeatherCondition.SNOW;
		} else if (temperature <= WeatherSimulatorConstants.TEMP_WIND_MAX && temperature > WeatherSimulatorConstants.TEMP_WIND_MIN) {
			return WeatherCondition.WIND;
		} else if (temperature <= WeatherSimulatorConstants.TEMP_RAIN_MAX && temperature > WeatherSimulatorConstants.TEMP_RAIN_MIN) {
			return WeatherCondition.RAIN;
		} else {
			return WeatherCondition.SUNNY;
		}
	}

	public void writeWeatherSimulationToFile(List<WeatherSimulatorRecord> weatherSimulatorRecords) throws WeatherSimulatorException {
		FileUtils.fileWriteUtil(weatherSimulatorRecords);
	}
}
