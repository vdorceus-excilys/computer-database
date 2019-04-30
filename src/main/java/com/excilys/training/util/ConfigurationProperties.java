package com.excilys.training.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationProperties {
	
	public final static String DEFAULT_PERSISTANCE_PATH="default.properties";
	public final static String H2_TEST_PERSISTANCE_PATH="test.properties";
	private static Logger logger = LogManager.getLogger(ConfigurationProperties.class);
	private Properties properties;
	
	private Map<String,String> config;
	
	public ConfigurationProperties() {
		config  =  new HashMap<String,String>();
	}
	
	public void load(String path) throws IOException,ConfigurationException {
		properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fileProperties = classLoader.getResourceAsStream(path);
		
		if ( fileProperties == null ) {
            throw new ConfigurationException("Failed to load properties from file");
        }
		try {
            properties.load(fileProperties);
            for(String name : properties.stringPropertyNames()) {
            	this.config.put(name,properties.getProperty(name));
            }
        } catch ( IOException exp ) {
            logger.error("IOException reading properties",exp);
        }
	}
	public Map<String,String> getAllProperties(){
		return this.config;
	}
	public String readProperty(String name) {
		return this.config.get(name);
	}
	public Properties getPropertiesObject() {
		return properties;
	}
	
}
