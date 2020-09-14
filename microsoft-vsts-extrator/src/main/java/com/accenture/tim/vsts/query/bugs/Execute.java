package com.accenture.tim.vsts.query.bugs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.commons.FileUtils;
import com.accenture.tim.vsts.query.ExecuteAbstract;
import com.accenture.tim.vsts.query.bugs.json.item.QueryRetBugsItem;
import com.accenture.tim.vsts.query.bugs.json.items.WorkItems;

public class Execute extends ExecuteAbstract {

	private final static Logger log  = Logger.getLogger(Execute.class);

	private QueryItems queryItems;

	public Execute() {
		super();
		basicAuth_user		= prop.getProperty("query._bugs_.basicAuth_user") ;
		basicAuth_password	= prop.getProperty("query._bugs_.basicAuth_password");
		queryUrlWorkItems	= prop.getProperty("query._bugs_.url"); 
		queryItems			= new QueryItems(basicAuth_user, basicAuth_password, queryUrlWorkItems);
	}
	
	public void run() {
		try {
			log.info("- Runnig Query=["+queryName+"]");

			log.info("- Getting all workitems from -> Query=["+queryName+"]");
			List<WorkItems> ltWorkItems = (List<WorkItems>)queryItems.getResultSet();

			if (ltWorkItems==null)
				
				log.info("- None workitems returned from -> Query=["+queryName+"]");

			 else {
				 String dir     = prop.getProperty("query._bugs_.dir");
				 String pattern = prop.getProperty("query._bugs_.file.pattern");
				 String fileName= FileUtils.name2CSVFile(dir+pattern);
				 File	csvFile = new File(fileName); 
				 log.debug("- Writing file ["+csvFile+"]");
				 
				 BufferedWriter writer = new BufferedWriter (new FileWriter(csvFile));

				 QueryItem itemQuery = null;
				 QueryRetBugsItem itemQueryReturned	= new QueryRetBugsItem();
				 
				 String 
				 csvline = itemQueryReturned.formatHeader();
				 csvline=csvline+"\n";
				 writer.write(csvline);

				 log.debug("- Amount of SDN=["+ltWorkItems.size()+"]");
				for (WorkItems workItems : ltWorkItems) {

					 Integer wiId  = workItems.getId();
					 String  wiUrl = workItems.getUrl();
					 log.info("- wId=["+wiId+"]-wUrl=["+wiUrl+"]");
					 
					 itemQuery			= new QueryItem(basicAuth_user, basicAuth_password, wiUrl);
					 itemQueryReturned	= new QueryRetBugsItem();
					 itemQueryReturned	= (QueryRetBugsItem) itemQuery.getResultSet();
					 
					 csvline = itemQueryReturned.formatCSV();
					 writer.write(csvline);
					 writer.flush();
					 log.debug("- "+csvline);
				 }
				writer.close();
			 }
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("-> main");
		new Execute().run();
		log.info("<- main");
	}
	
	
}
