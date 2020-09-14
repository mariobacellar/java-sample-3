package com.accenture.tim.vsts.csv.write;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.csv.model.CSVModelAbstract;

public abstract class CSVWriterAbstract {
	
	final static Logger log  = Logger.getLogger(CSVWriterAbstract.class);
	
	protected String csvDir;
	protected String csvFile;
	protected String cvsSplitBy;
	protected String cvsHeader;
	
	protected List<CSVModelAbstract> ltCSVModel;
	
	protected boolean hasHeaderWritten;
	
	public List<CSVModelAbstract> getLtCSVModel() {
		return ltCSVModel;
	}

	public void setLtCSVModel(List<CSVModelAbstract> ltCSVModel) {
		this.ltCSVModel = ltCSVModel;
	}

	

	public String getCvsHeader() {
		return cvsHeader;
	}

	public void setCvsHeader(String cvsHeader) {
		this.cvsHeader = cvsHeader;
	}

	/**
	 * 
	 */
	protected CSVWriterAbstract(String dir, String csvFile, String cvsSplitBy, List<CSVModelAbstract> ltCSVModel) {
		log.debug("-> CSVWriterAbstract()");
		this.csvDir 	= dir;
		this.csvFile	= dir+csvFile;
		this.cvsSplitBy = cvsSplitBy;
		this.ltCSVModel = ltCSVModel;
		log.debug("- csvDir    =["+dir+"]");
		log.debug("- csvFile   =["+csvFile+"]");
		log.debug("- cvsSplitBy=["+cvsSplitBy+"]");
		log.debug("<- CSVWriterAbstract()");
	}
	
    
	protected abstract String  formatLine(CSVModelAbstract model);
	
	
	/**
	 * 
	 * @param args
	 */
    public void write() {

    	log.debug("-> run()");
    	
    	BufferedWriter writer = null;
    	
        try {
        	
        	Path path = Paths.get(csvFile);
        	log.debug("-  path=["+path+"]");
        	
        	File file = path.toFile();
        	log.debug("-  file=["+file.toURI()+"]");
        	
        	writer = new BufferedWriter( new FileWriter(file) );
        	
        	boolean hasHeader = (getCvsHeader()!=null);
        	
        	if ( hasHeader  && !hasHeaderWritten) {
        		
        		String cvsHeader = getCvsHeader() ;
        		log.debug("- CvsHeader=["+cvsHeader+"]");
        		
        		writer.write( cvsHeader+"\n" );
        		writer.flush();
        		
        		setCvsHeader(null);
        		hasHeaderWritten=true;
        	}
        	
        	
            String line = null;

            ltCSVModel	= getLtCSVModel();
            for (CSVModelAbstract csvModelAbstract : ltCSVModel) {
            	
            	line = formatLine(csvModelAbstract);
            	
            	if ( line!=null && !"null".equals(line)) {
                	writer.write(line);
                    log.info("line=["+line+"]");
            	}
			
            }
            writer.close();

        } catch (Exception e) {
        	
            e.printStackTrace();
            log.error(e);
            
        } finally {
        	if (writer != null) {
                try {
                	writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        log.debug("<- run()");
    }
}
