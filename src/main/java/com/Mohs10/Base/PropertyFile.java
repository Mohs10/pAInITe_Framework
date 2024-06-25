package com.Mohs10.Base;

import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {
    public static String getValueForKey(String key) throws Throwable {
        Properties config = new Properties();
        // Use a relative path assuming Config.properties is in the resources folder
        InputStream input = PropertyFile.class.getClassLoader().getResourceAsStream("Config.properties");

        if (input == null) {
            System.out.println("Sorry, unable to find Config.properties");
            return null;
        }

        try {
            config.load(input);
            return config.getProperty(key);
        } finally {
            input.close();
        }
    }
}
