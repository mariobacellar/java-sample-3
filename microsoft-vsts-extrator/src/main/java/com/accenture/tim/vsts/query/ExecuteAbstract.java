package com.accenture.tim.vsts.query;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.email.SendEmail;

public abstract class ExecuteAbstract {

	final static Logger log  = Logger.getLogger(ExecuteAbstract.class);
	
	protected static String queryName;
	protected static String basicAuth_user;
	protected static String basicAuth_password;
	protected Properties prop;
	protected String queryUrlWorkItems;
	

	
	protected ExecuteAbstract() {
		prop = new Properties();
		try {
			prop.load(SendEmail.class.getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		}

	}
	
	/**
	 * 
	 * @param prefix
	 * @return
	 */
	public static String buildFileName(String prefix) {
		log.debug("-> buildFileName("+prefix+")");
		DateFormat	df = new SimpleDateFormat("yyyyMMdd");
		Date		date = new Date();					
		String		filename = prefix + df.format(date);
		log.debug("<- buildFileName("+prefix+")");
		return filename ;
	}
	
	
	/**
	 * 
	 */
	public static File buildFileCSV(String namePattern) throws Exception {
		log.debug("-> buildFileCSV("+namePattern+")");
		String	fileNamePattern = buildFileName(namePattern);
		String	fileName		= ".//csv//"+fileNamePattern.replaceAll("\\s+","")+".csv";
		File	csvFile			= new File(fileName);	 
		log.debug("<- buildFileCSV("+csvFile.getName()+")");
		return csvFile ;
	}
	
	
	
}
