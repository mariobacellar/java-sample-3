package com.accenture.tim.vsts.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.springframework.util.ResourceUtils;

public abstract class ApplicationProperties {

	private static final Logger LOG = Logger.getLogger(ApplicationProperties.class.getName());

	public static Properties properties = new Properties();
	
	public static Properties appProperties(){
		LOG.info("-> appProperties()");
        try {
    		LOG.info("- Reading application.properties");
            
    		File file= ResourceUtils.getFile("classpath:application.properties");
            
            InputStream in	= new FileInputStream(file);
            
            properties.load(in);

    		LOG.info("- Reading application.properties");
    		Enumeration<Object> keys = properties.keys();
    		while(keys.hasMoreElements()){
    			String param = (String) keys.nextElement();
    			System.out.println("- param=["+param+"] - \t\tvalue=["+properties.getProperty(param)+"]");
    		}    		
            LOG.info("-> appProperties(): NORMAL");
        } catch (IOException e) {
    		LOG.info("<- appProperties(): ERROR");
            LOG.error(e.getMessage());
        }
        return properties;
    }
	
	
	/*
     * Authentication information. 
     * HTTP_PROXY_URL should be set to null if none is desired.
     */
    public static String APP_VSTS_USERNAME = "mario.bacellar@accenture.com";
    public static String APP_VSTS_PASSWORD = "Senha123";
    
    public static String APP_VSTS_HTTP_PROXY_URL      = null;
    public static String APP_VSTS_HTTP_PROXY_USERNAME = "";
    public static String APP_VSTS_HTTP_PROXY_PASSWORD = "";

    /**
     * A team project name (without the leading "$/") where files, work items, and builds can be created and modified.
     */
    public static String PROJECT_NAME = "WEB_VAS";

}

	
