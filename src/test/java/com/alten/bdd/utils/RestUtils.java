package com.alten.bdd.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;

public class RestUtils {


    private static Properties properties = new Properties();
    private static final String pathToPropertiesFile = "src/test/resources/configuration.properties";
	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(RestUtils.class);
	private static final String URL = "URL";
	
	
	
	

    public static void setup() throws Exception {	
    	LOGGER.info("setup");
    	properties.load(new BufferedReader(new FileReader(pathToPropertiesFile)));
        String url = (properties.getProperty(URL));
        RestAssured.baseURI = url;
		RestAssured.useRelaxedHTTPSValidation();
        LOGGER.info("url="+url);
    } 
}

