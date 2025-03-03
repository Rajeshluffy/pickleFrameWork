package com.general.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {

	private static Properties properties;

	public static String config(String key) {
		properties = new Properties();
		String value = null;
		try {
			properties.load(new FileInputStream(new File("src/test/resources/Config.properties")));
			value = properties.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String secret(String key) {
		properties = new Properties();
		String value = null;
		try {
			properties.load(new FileInputStream(new File("src/test/resources/Secret.properties")));
			value = properties.getProperty(key);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(
					"Unable to found \"src/test/resources/secret.properties\" file in the mentioned location.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

}