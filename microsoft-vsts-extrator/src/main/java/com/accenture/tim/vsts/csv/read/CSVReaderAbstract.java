package com.accenture.tim.vsts.csv.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.csv.model.CSVModelAbstract;

public abstract class CSVReaderAbstract {
	
	final static Logger log  = Logger.getLogger(CSVReaderAbstract.class);
	
	protected String csvFile;

	protected String cvsSplitBy;
	
	protected String dir;
	
	protected List<CSVModelAbstract> ltCSVModel;
	
	
	public List<CSVModelAbstract> getLtCSVModel() {
		return ltCSVModel;
	}


	public void setLtCSVModel(List<CSVModelAbstract> ltCSVModel) {
		this.ltCSVModel = ltCSVModel;
	}


	/**
	 * 
	 */
	protected CSVReaderAbstract(String dir, String csvFile, String cvsSplitBy) {
		log.debug("-> CSVReaderAbstract()");
		this.dir 		= dir;
		this.csvFile	= dir+csvFile;
		this.cvsSplitBy = cvsSplitBy;
		log.debug("- csvDir       =["+dir+"]");
		log.debug("- csvFile   =["+csvFile+"]");
		log.debug("- cvsSplitBy=["+cvsSplitBy+"]");
		log.debug("<- CSVReaderAbstract()");
	}
	
    
	protected abstract CSVModelAbstract pasrser(String line);
	
	/**
	 * 
	 * @param args
	 */
	public void load() {
		
    	
		log.debug("-> load()");
        
		BufferedReader br = null;
		
        try {
        
        	//Path path = Paths.get(this.dir);
        	
        	Path path = Paths.get(csvFile);
        	log.debug("-  path=["+path+"]");
        	
        	File file = path.toFile();
        	log.debug("-  file=["+file.toURI()+"]");
        	
        			
        	br = new BufferedReader(new FileReader(file));
            
        	ltCSVModel = new ArrayList<CSVModelAbstract>();
            
        	CSVModelAbstract model = null;
            
        	String line = "";
            
        	while ((line = br.readLine()) != null) {
            	model = pasrser(line);
            	ltCSVModel.add(model);
                log.info("line=["+line+"]");
                log.info("model=["+model+"]");
            }
        	
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        } finally {
        	if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.debug("<- load()");
    }



}
