package com.testproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String PROPERTY_FILE = "/web_driver.properties";
    private static Properties properties;

    public static final String URL = "url";
    public static final String THREAD_COUNT = "threadCount";
    public static final String BROWSER = "browser";

    private Config() {}

    public static String getProperty(String key) {
        if (properties == null) {
            properties = loadProperties(PROPERTY_FILE);
        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        } else {
            return "";
        }
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    private static Properties loadProperties(String file) {
        Properties result = new Properties();
        try (InputStream is = Config.class.getResourceAsStream(file)) {
            result.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
