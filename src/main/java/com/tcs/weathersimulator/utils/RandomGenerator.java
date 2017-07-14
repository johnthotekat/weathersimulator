package com.tcs.weathersimulator.utils;

import java.util.Random;

import org.apache.log4j.Logger;

/**
 * @author J
 * 
 *         Class to Generate Random Range Facts
 * 
 */
public final class RandomGenerator {

	public static final Logger LOGGER = Logger.getLogger(RandomGenerator.class);

	public static int generateRandomInteger(int min, int max, Random rand) {
		if (min > max) {
			LOGGER.info("Min cannot exceed Max");
			throw new IllegalArgumentException("Min cannot be greater than Max");
		}
		long range = (long) max - (long) min + 1;
		long fraction = (long) (range * rand.nextDouble());
		return (int) (fraction + min);
	}

	public static double generateRandomDecimal(double min, double max) {
		if (min > max) {
			LOGGER.info("Min cannot exceed Max");
			throw new IllegalArgumentException("Min cannot exceed Max.");
		}
		Random r = new Random();
		return (r.nextInt((int) ((max - min) * 10 + 1)) + min * 10) / 10.0;
	}

}
