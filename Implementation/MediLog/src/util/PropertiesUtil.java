package util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
	public static final String DATABASE_PROPERTIES_FILEPATH = "./database_connection.properties"; // TODO Configure for production environment
	
	public static Properties loadProperties(String propertiesPath) throws IOException {
		
		FileReader fileReader = new FileReader(propertiesPath);
		
		Properties properties = new Properties();
		properties.load(fileReader);
		
		return properties;
	}

}
