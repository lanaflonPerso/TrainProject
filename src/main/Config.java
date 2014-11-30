package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
            URL resource = Application.class.getClassLoader().getResource("resources\\config.properties");
            File file = new File(resource.toURI());
            FileInputStream propFile = new FileInputStream(file);

            Properties prop = new Properties();
            prop.load(propFile);
            // get the property values
            String value;
            for (String key : keys) {
                value = prop.getProperty(key);
                properties.put(key, Integer.parseInt(value));
            }
        } catch(IOException ignored) {
        } catch (URISyntaxException ignored) {
        }
        return properties;
    }
}
