package com.accenture.tim.vsts.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


	public static String date2CSVFileName() {
		String ret=new SimpleDateFormat("yyyyMMdd").format(new Date());
		return ret;
	}
	
	
	public static void main(String[] args) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
