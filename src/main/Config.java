package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

public class Config {
    private static Hashtable<String, Integer> properties = new  Hashtable<String, Integer>();
    /**
     * Get properties from config file
     */
    public static Hashtable<String, Integer> getProperties() {
        try {
            InputStream propFile = Application.class.getClassLoader().getResourceAsStream("resources/config.properties");
            // так теж працює
            // InputStream propFile = Application.class.getResourceAsStream("../resources/config.properties");
            Properties prop = new Properties();
            prop.load(propFile);
            propFile.close();

            // get the property values
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                properties.put(key, Integer.parseInt(value));
            }

        } catch(IOException ignored) {
        }
        return properties;
    }
}
