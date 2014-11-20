package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by Light on 20.11.2014.
 */
public class Config {
    private static Hashtable<String, Integer> properties = new  Hashtable<String, Integer>();
    /**
     * Get properties from config file
     */
    public static Hashtable getProperties() {
        try {
            Properties prop = new Properties();
            FileInputStream propFile = new FileInputStream("C:\\Users\\Light\\IdeaProjects\\TrainProject\\resources\\config.properties");
            prop.load(propFile);
            // get the property values
            String key, value;

            key = "Map.WIDTH";
            value = prop.getProperty(key);
            properties.put(key, Integer.parseInt(value));

            key = "Map.HEIGHT";
            value = prop.getProperty(key);
            properties.put(key, Integer.parseInt(value));

        } catch(IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
