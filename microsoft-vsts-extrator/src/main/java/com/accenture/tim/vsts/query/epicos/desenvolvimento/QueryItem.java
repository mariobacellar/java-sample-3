package com.accenture.tim.vsts.query.epicos.desenvolvimento;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.accenture.tim.vsts.query.Query;
import com.accenture.tim.vsts.query.epicos.desenvolvimento.json.item.QueryRetEpicosEmDesenvolvimentoItem;

public class QueryItem extends Query {

	final static Logger log  = Logger.getLogger(QueryItem.class);

	
	public QueryItem(String user, String pass) {
		super(user, pass);
		urlQuery = null;
	}
	
	
	public QueryItem(String user, String pass, String url) {
		super(user, pass, url);
	}
	

	public QueryRetEpicosEmDesenvolvimentoItem getResultSet() throws Exception /*ClientProtocolException, UnsupportedEncodingException, IOException , ValidationException*/ {

		log.info("-> getResultSet() ");

		if (urlQuery==null)
			throw new Exception("The 'urlQuery' parameter is null!!!");
		
		buildClient();
		
		buildHeaders();

		QueryRetEpicosEmDesenvolvimentoItem q = (QueryRetEpicosEmDesenvolvimentoItem) restClientServiceImpl.sendGetMethod(urlQuery,"",headers,  QueryRetEpicosEmDesenvolvimentoItem.class);
		 log.info( ToStringBuilder.reflectionToString(q, ToStringStyle.MULTI_LINE_STYLE) );
		 log.info("<- getResultSet() ");
		 return q;
	}
	

	/**
	 * TESTE
	 */
	public static void main(String[] args) {
		try {
			
			String user="mariobacellar";
			String pass="Senha123";
			String url=null;
					
			QueryItem qItem = null;
			QueryRetEpicosEmDesenvolvimentoItem retItem = null;
			Integer itemId		= null;
			String  itemTitle	= null;
			String  itemState	= null;
			
			url			= "https://accenturetim.visualstudio.com/ac3236be-af02-4584-90ab-e4d75e1f7764/_apis/wit/workItems/1753";
			qItem		= new QueryItem(user,pass,url);
			retItem		= qItem.getResultSet();
			itemId		= retItem.getId();
			itemTitle	= retItem.getFields().getSystemTitle();
			itemState	= retItem.getFields().getSystemState();
			System.out.println("itemId=["+itemId+"]-itemTitle=["+itemTitle+"]-itemState=["+itemState+"]");
			 
			url			= "https://accenturetim.visualstudio.com/ac3236be-af02-4584-90ab-e4d75e1f7764/_apis/wit/workItems/2120";
			qItem		= new QueryItem(user,pass,url);
			retItem 	= qItem.getResultSet();
			itemId		= retItem.getId();
			itemTitle	= retItem.getFields().getSystemTitle();
			itemState	= retItem.getFields().getSystemState();
			System.out.println("itemId=["+itemId+"]-itemTitle=["+itemTitle+"]-itemState=["+itemState+"]");
		
			url			= "https://accenturetim.visualstudio.com/ac3236be-af02-4584-90ab-e4d75e1f7764/_apis/wit/workItems/3667";
			qItem		= new QueryItem(user,pass,url);
			retItem 	= qItem.getResultSet();
			itemId		= retItem.getId();
			itemTitle	= retItem.getFields().getSystemTitle();
			itemState	= retItem.getFields().getSystemState();
			System.out.println("itemId=["+itemId+"]-itemTitle=["+itemTitle+"]-itemState=["+itemState+"]");
		
			url			= "https://accenturetim.visualstudio.com/ac3236be-af02-4584-90ab-e4d75e1f7764/_apis/wit/workItems/17189";
			qItem		= new QueryItem(user,pass,url);
			retItem 	= qItem.getResultSet();
			itemId		= retItem.getId();
			itemTitle	= retItem.getFields().getSystemTitle();
			itemState	= retItem.getFields().getSystemState();
			System.out.println("itemId=["+itemId+"]-itemTitle=["+itemTitle+"]-itemState=["+itemState+"]");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
