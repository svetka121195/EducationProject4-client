package app.util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Светлана on 08.08.2018.
 */
public class PropertyReader {
    private static final Logger LOGGER = LogManager.getLogger(PropertyReader.class);
    private static Properties properties = loadProperties();

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            properties.load(classLoader.getResourceAsStream("hibernate.properties"));
            return properties;
        } catch (IOException e) {
            LOGGER.error("ERROR: file with properties hibernate is absent", e);
        }
        return properties;
    }
}
