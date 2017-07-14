package com.tcs.weathersimulator.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tcs.weathersimulator.model.WeatherSimulatorRecord;
import com.tcs.weathersimulator.service.WeatherService;
import com.tcs.weathersimulator.utils.ServiceFactory;
import com.tcs.weathersimulator.utils.Utility;

/**
 * Junit Test cases for Weather service api
 * 
 * 
 */
public class WeatherServiceTest {

	@Before
	public void setUp() {
		System.out.println("JUnit SETUP RUNNING..");
	}

	@After
	public void tearDown() {
		System.out.println("TEARDOWN RUNNING ..");
	}

	@Test
	public void testGenerateOutputFile() throws Exception {
		WeatherServiceHandler weatherServiceClient = new WeatherServiceHandler();
		weatherServiceClient.doProcess();
		File f = new File(Utility.getInstance().getFileName());
		System.out.println(f.exists());
		assertTrue(f.exists());
	}

	@Test
	public void testInvokeWeatherDataAPI() throws Exception {
		WeatherService weatherService = ServiceFactory.getWeatherServiceInstance();
		List<WeatherSimulatorRecord> records = weatherService.invokeWeatherDataAPI();
		assertNotNull(records);
	}
}