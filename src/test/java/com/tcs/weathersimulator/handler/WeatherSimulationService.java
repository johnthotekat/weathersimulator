package com.tcs.weathersimulator.handler;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tcs.weathersimulator.exception.WeatherSimulatorException;
import com.tcs.weathersimulator.model.WeatherCondition;
import com.tcs.weathersimulator.model.WeatherSimulatorRecord;
import com.tcs.weathersimulator.service.WeatherService;
import com.tcs.weathersimulator.serviceimpl.WeatherSimulationServiceImpl;
import com.tcs.weathersimulator.utils.ServiceFactory;

/**
 * Junit Test cases for WeatherSimulationService
 * 
 */
public class WeatherSimulationService {
	
	List<WeatherSimulatorRecord> weatherServiceData = new ArrayList<WeatherSimulatorRecord>();
	
	@Before
	public void setUp() {
		System.out.println("JUnit SETUP RUNNING..");
		WeatherService weatherService  = ServiceFactory.getWeatherServiceInstance();
		try {
			weatherServiceData = weatherService.invokeWeatherDataAPI();
		} catch (WeatherSimulatorException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		System.out.println("TEARDOWN RUNNING ..");
	}

	/**
	 * Unit test case to generate the contents to write to file from the api response
	 * @throws Exception
	 */
	@Test
	public void generateFileContentDataString() {

		WeatherSimulationServiceImpl weatherSimultionService=ServiceFactory.getWeatherSimulationServiceInstance();
		List<WeatherSimulatorRecord> records = null;
		try {
			records = weatherSimultionService.generateWeatherSimulationData(weatherServiceData);
		} catch (WeatherSimulatorException e) {
			e.printStackTrace();
		}
		assertNotNull(records);
	}
	
	/**
	 * JUnit to test getAnnualMeanTemp() in WeatherSimulationService
	 * @throws Exception
	 */
	@Test
	public void testAnnualMeanTemp() throws Exception {
		WeatherSimulationServiceImpl weatherSimulationService=ServiceFactory.getWeatherSimulationServiceInstance();
		Method m = WeatherSimulationServiceImpl.class
				.getDeclaredMethod("getAnnualMeanTemp", new Class[] {
						double.class, double.class });
		m.setAccessible(true);
		double annualMeanTemp = (Double) m.invoke(weatherSimulationService, -23.666d,
				155.22d);
		assertNotNull(annualMeanTemp);
	}

	/**
	 * JUnit to test getMonthlyMeanTemperature() in WeatherSimulationService
	 * @throws Exception
	 */
	@Test
	public void testMonthlyMeanTemperature() throws Exception {
		WeatherSimulationServiceImpl weatherSimulationService=ServiceFactory.getWeatherSimulationServiceInstance();
		Method m = WeatherSimulationServiceImpl.class
				.getDeclaredMethod("getMonthlyMeanTemperature", new Class[] {
						double.class, double.class });
		m.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<Integer, Double> MonthlyTempMap = (Map<Integer, Double>) m.invoke(
				weatherSimulationService, -22.5444d, 112.333d);
		assertNotNull(MonthlyTempMap);
	}

	/**
	 * JUnit to test getTemperature() in WeatherSimulationService
	 * @throws Exception
	 */
	@Test
	public void testTemperature() throws Exception {
		WeatherSimulationServiceImpl weatherSimulationService=ServiceFactory.getWeatherSimulationServiceInstance();
		Method m = WeatherSimulationServiceImpl.class
				.getDeclaredMethod("getTemperature", new Class[] {
						Date.class,double.class, double.class });
		m.setAccessible(true);
		double temp = (Double) m.invoke(weatherSimulationService, new Date(), -35.678975d,
				132.22437d);
		assertNotNull(temp);
	}

	/**
	 * JUnit to test getHumidity() in WeatherSimulationService
	 * @throws Exception
	 */
	@Test
	public void testHumidity() throws Exception {
		WeatherSimulationServiceImpl weatherSimulationService=ServiceFactory.getWeatherSimulationServiceInstance();
		Method m = WeatherSimulationServiceImpl.class
				.getDeclaredMethod("getHumidity", new Class[] {
						double.class, double.class ,WeatherCondition.class});
		m.setAccessible(true);
		int tempRange = (Integer) m.invoke(weatherSimulationService, 1010.25, 19,
				WeatherCondition.SNOW);
		assertNotNull(tempRange);
	}

	@Test
	public void testPressure() throws Exception {
		WeatherSimulationServiceImpl weatherSimulationService=ServiceFactory.getWeatherSimulationServiceInstance();
		Method m = WeatherSimulationServiceImpl.class
				.getDeclaredMethod("getPressure", new Class[] {
						double.class, double.class });
		m.setAccessible(true);
		double pressure = (Double) m.invoke(weatherSimulationService, 1010d, 133.267d);
		assertNotNull(pressure);
	}

	/**
	 * JUnit to test calculateWeatherCondition() in WeatherSimulationService
	 * @throws Exception
	 */
	@Test
	public void testConditions() throws Exception {
		WeatherSimulationServiceImpl weatherSimulationService=ServiceFactory.getWeatherSimulationServiceInstance();
		Method m = WeatherSimulationServiceImpl.class
				.getDeclaredMethod("calculateWeatherCondition", new Class[] {
						double.class });
		m.setAccessible(true);
		WeatherCondition condition = (WeatherCondition) m.invoke(weatherSimulationService,
				1010);
		assertNotNull(condition);
	}
	
	/**
	 * JUnit to test getLocalTime() in WeatherSimulationService
	 * @throws Exception
	 */
	@Test
	public void testLocalTime() throws Exception {
		WeatherSimulationServiceImpl weatherSimulationService=ServiceFactory.getWeatherSimulationServiceInstance();
		Method m = WeatherSimulationServiceImpl.class
				.getDeclaredMethod("getLocalTime", new Class[] {
						int.class});
		m.setAccessible(true);
		List<Date> date = (List<Date>) m.invoke(weatherSimulationService, 10);
		assertNotNull(date);
	}

}