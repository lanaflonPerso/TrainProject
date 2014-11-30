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
    public static Hashtable getProperties() {
        String[] keys = new String[] {
                "Window.WIDTH", "Window.HEIGHT",
                "Map.WIDTH", "Map.HEIGHT", "Map.PADDING_LEFT", "Map.PADDING_TOP",
                "ElementsPanel.WIDTH", "ElementsPanel.PADDING_LEFT", "ElementsPanel.PADDING_LEFT", "ElementsPanel.PADDING_TOP",
                "InterruptsPanel.HEIGHT", "InterruptsPanel.PADDING_LEFT", "InterruptsPanel.PADDING_TOP",
                "App.DELAY"
        };
        try {
            InputStream propFile = Application.class.getClassLoader().getResourceAsStream("resources/config.properties");
            Properties prop = new Properties();
            prop.load(propFile);
            // get the property values
            String value;
            for (String key : keys) {
                value = prop.getProperty(key);
                properties.put(key, Integer.parseInt(value));
            }
        } catch(IOException ignored) {
        }
        return properties;
    }
}
