package de.mannheim.wifo2.fesas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class FesasWraperProperties {

	private static InputStream inputStream;
	private static FesasWraperProperties instance;
	

	 /** ID of the Device. */
	public static String DEVICE_ID;
	/** Name of the config file for the AL. */
	public static String AL_CONFIG_FILE;
			 /** Name of the config file for the communication. */		 
	public static String COMMUNICATION_CONFIG_FILE;
	/** Name of the scenario (= folder for config and log files). */
	public static String SCENARIO_NAME;
	/** Delay after initialization of AL and before initialization of the communication (in ms): */
	public static String DELAY;
	/** Use of ALM -> load ALM proxy. */
	public static String USE_ALM;
	/** Open the management UI. */
	public static boolean USE_GUI;
	/** One threaded or multi-threaded execution of the logic. */
	public static boolean ONE_THREADED = false;
	
	 

	/**
	 * Private constructor -> Singleton pattern.
	 */
	private FesasWraperProperties() {
		// intended to be empty
	}
	
	public static FesasWraperProperties getInstance() {
		if (instance == null) {
			instance = new FesasWraperProperties();
		}
		return instance;
	}
	
	
	public void initializeProps() {
	
		try {
			Properties prop = new Properties();
			String propFileName = "/wrapper.properties";  // Need relative file path here

			inputStream = getClass().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			DEVICE_ID = prop.getProperty("DEVICE_ID");
			AL_CONFIG_FILE = prop.getProperty("AL_CONFIG_FILE");
			COMMUNICATION_CONFIG_FILE = prop.getProperty("COMMUNICATION_CONFIG_FILE");
			SCENARIO_NAME = prop.getProperty("SCENARIO_NAME");
			DELAY = "" + Integer.parseInt(prop.getProperty("DELAY"));
			USE_ALM = "" + Boolean.parseBoolean(prop.getProperty("USE_ALM"));
			USE_GUI = Boolean.parseBoolean(prop.getProperty("USE_GUI"));
			ONE_THREADED = Boolean.parseBoolean(prop.getProperty("ONE_THREADED"));
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
