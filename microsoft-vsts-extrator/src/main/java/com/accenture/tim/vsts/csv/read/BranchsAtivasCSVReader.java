package com.accenture.tim.vsts.csv.read;

import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.csv.model.BranchsAtivasCSVModel;
import com.accenture.tim.vsts.csv.model.CSVModelAbstract;

public class BranchsAtivasCSVReader extends CSVReaderAbstract{
	
	final static Logger log  = Logger.getLogger(BranchsAtivasCSVReader.class);

	
	public BranchsAtivasCSVReader(String dir, String csvFile, String cvsSplitBy) {
		super(dir,csvFile,cvsSplitBy);
	}
	
	
	protected CSVModelAbstract pasrser(String line) {
        log.debug("-> pasrser("+line+")");
        
		String[] arrLine = line.split(cvsSplitBy);

		BranchsAtivasCSVModel 
		model = new BranchsAtivasCSVModel();
		model.setRepositorio(arrLine[0]);
		model.setBranch		(arrLine[1]);
		model.setLink		(arrLine[2]);
		model.setLineFromCSV(line);
		
        log.debug("<- pasrser(model=["+model+"])");
		return model;
	}


	/**
	 * TESTE
	 * @param args
	 */
	public static void main(String[] args) {
		try {
	        log.info("-> main");
			
			String dir		  = "C:\\gitb\\git-branchs-rpt\\csv\\";
			String csvFile	  = "REPOBRANCH_20180605.csv";
			String cvsSplitBy = ";";
			
			BranchsAtivasCSVReader 
			reader = new BranchsAtivasCSVReader(dir, csvFile, cvsSplitBy);
			reader.load();
			
			List<CSVModelAbstract> lt = reader.getLtCSVModel();
			for (CSVModelAbstract csvModel : lt) {
		        log.info("- csvModel="+csvModel);
			}
			
	        log.info("<- main");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
