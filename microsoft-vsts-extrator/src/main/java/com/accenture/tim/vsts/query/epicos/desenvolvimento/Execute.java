package com.accenture.tim.vsts.query.epicos.desenvolvimento;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.commons.FileUtils;
import com.accenture.tim.vsts.email.SendEmail;
import com.accenture.tim.vsts.query.ExecuteAbstract;
import com.accenture.tim.vsts.query.epicos.desenvolvimento.json.item.QueryRetEpicosEmDesenvolvimentoItem;
import com.accenture.tim.vsts.query.epicos.desenvolvimento.json.items.WorkItems;

public class Execute extends ExecuteAbstract {

	final static Logger log  = Logger.getLogger(Execute.class);

	
	
	public void run() {
		try {
			log.info("- Runnig Query=["+queryName+"]");
			
			Properties prop = new Properties();
			prop.load(SendEmail.class.getResourceAsStream("/application.properties"));
			
			basicAuth_user = prop.getProperty("query._epicos.branchs_.basicAuth_user") ;//"mariobacellar";
			basicAuth_password=prop.getProperty("query._epicos.branchs_.basicAuth_password") ;//"Senha123";
			
			// Epicos em Desenvolvimento
			String queryUrl = prop.getProperty("query._epicos.branchs_.url");//"https://accenturetim.visualstudio.com/ac3236be-af02-4584-90ab-e4d75e1f7764/_apis/wit/wiql/6c058252-f794-4a0c-bd75-938f243b4c8a"; 

			log.info("- Returning all workitems for -> Query=["+queryName+"]");
			QueryItems queryItems = new QueryItems(basicAuth_user, basicAuth_password, queryUrl);
			
			
			log.info("- Getting all workitems from -> Query=["+queryName+"]");
			List<WorkItems> ltWorkItems = (List<WorkItems>)queryItems.getResultSet();

			if (ltWorkItems==null)
				
				log.info("- None workitems returned from -> Query=["+queryName+"]");

			 else {
				 String dir     = prop.getProperty("query._epicos.branchs_.dir");
				 String pattern = prop.getProperty("query._epicos.branchs_.file.pattern");
				 String fileName= FileUtils.name2CSVFile(dir+pattern);
				 File	csvFile = new File(fileName); 
				 log.debug("- Writing file ["+csvFile+"]");
				 
				 BufferedWriter writer = new BufferedWriter (new FileWriter(csvFile));

				 QueryItem								itemQuery			= null;
				 QueryRetEpicosEmDesenvolvimentoItem	itemQueryReturned	= new QueryRetEpicosEmDesenvolvimentoItem();
				 
				 String csvline = itemQueryReturned.formatHeader();
				 csvline=csvline+"\n";
				 writer.write(csvline);

				for (WorkItems workItems : ltWorkItems) {

					 Integer wiId  = workItems.getId();
					 String  wiUrl = workItems.getUrl();
					 log.info("- wId=["+wiId+"]-wUrl=["+wiUrl+"]");
					 
					 itemQuery			= new QueryItem(basicAuth_user, basicAuth_password, wiUrl);
					 itemQueryReturned	= new QueryRetEpicosEmDesenvolvimentoItem();
					 itemQueryReturned	= itemQuery.getResultSet();
					 
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
