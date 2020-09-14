package com.accenture.tim.vsts.task;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.email.SendEmail;

public abstract class TaskAbstract {


	public static Logger log  = Logger.getLogger(TaskAbstract.class);

	protected Properties prop = new Properties();
	
	protected  TaskAbstract() {
		prop = new Properties();
		try {
			prop.load(SendEmail.class.getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
			
		}	
	} 

	
}
