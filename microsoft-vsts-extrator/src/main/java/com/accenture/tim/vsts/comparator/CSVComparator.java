package com.accenture.tim.vsts.comparator;

import org.apache.log4j.Logger;

public class CSVComparator {


	final static Logger log  = Logger.getLogger(CSVComparator.class);
	
	protected boolean hasExistDiff;
	
	
	protected boolean compareTo(String dominio, String contradominio) {
		return ( contradominio.indexOf(dominio)>=0 );
	}
		

	public  void hasExistDiff(boolean b) {
		this.hasExistDiff=b;
	}
	
	public boolean hasExistDiff() {
		return hasExistDiff;
	}

}
