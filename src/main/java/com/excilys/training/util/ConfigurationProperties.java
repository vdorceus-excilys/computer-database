package com.excilys.training.util;

import static java.lang.System.out;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

public class ConfigurationProperties {
	
	public final static String DEFAULT_PERSISTANCE_PATH="com/excilys/training/persistance/persistance.properties";
	
	private Map<String,String> config;
	
	public ConfigurationProperties() {
		config  =  new HashMap<String,String>();
	}
	
	public void load(String path) throws IOException,ConfigurationException {
		Properties properties = new Properties();
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
        } catch ( IOException e ) {
            out.println("IOException reading properties");
        }
	}
	public Map<String,String> getAllProperties(){
		return this.config;
	}
	public String readProperty(String name) {
		return this.config.get(name);
	}
	
}
