package com.accenture.tim.vsts.csv.read;

import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.csv.model.CSVModelAbstract;
import com.accenture.tim.vsts.csv.model.EpicEmDesenvolvimentoCSVModel;

public class EpicEmDesenvolvimentoCSVReader extends CSVReaderAbstract{
	

	final static Logger log  = Logger.getLogger(EpicEmDesenvolvimentoCSVReader.class);
	
	
	public EpicEmDesenvolvimentoCSVReader(String dir, String csvFile, String cvsSplitBy) {
		super(dir,csvFile,cvsSplitBy);
	}
	
	
	protected CSVModelAbstract pasrser(String line) {
        log.debug("-> pasrser("+line+")");
        
		//Id;Title;State;Efftort;TargetDate;InternalStatus;Area;TextField3;AssignedTo;Link
		String[] arrLine = line.split(cvsSplitBy);
		EpicEmDesenvolvimentoCSVModel 
		model = new EpicEmDesenvolvimentoCSVModel();
		model.setId				(arrLine[0]);
		model.setTitle			(arrLine[1]);
		model.setState			(arrLine[2]);
		model.setEfftort		(arrLine[3]);
		model.setTargetdate		(arrLine[4]);
		model.setInternalstatus	(arrLine[5]);
		model.setArea			(arrLine[6]);
		model.setTextfield3		(arrLine[7]);
		model.setAssignedto		(arrLine[8]);
		model.setLink			(arrLine[9]);
		model.setLineFromCSV	(line);

		
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
			
	        String dir		= "C:\\workspace\\VstsExtrator\\csv\\";
			String csvFile	= "REPOEPICDEV_20180605.csv";
			String cvsSplitBy	= ";";


			EpicEmDesenvolvimentoCSVReader 
			reader = new EpicEmDesenvolvimentoCSVReader(dir, csvFile, cvsSplitBy);
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
