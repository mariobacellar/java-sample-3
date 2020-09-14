package com.accenture.tim.vsts.query.bugs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.accenture.tim.vsts.query.Query;
import com.accenture.tim.vsts.query.bugs.json.item.QueryRetBugsItem;


public class QueryItem extends Query {
	
	final static Logger log  = Logger.getLogger(QueryItem.class);

	
	public QueryItem(String user, String pass) {
		super(user, pass);
	}

	public QueryItem(String basicAuth_user, String basicAuth_password, String wiUrl) {
		super(basicAuth_user, basicAuth_password, wiUrl);
	}

	@Override
	public QueryRetBugsItem getResultSet() throws Exception {
		log.info("-> getResultSet() ");
		
		if (urlQuery==null)
			throw new Exception("The 'urlQuery' parameter is null!!!");
		
		buildClient();
		
		buildHeaders();
		
		QueryRetBugsItem q = (QueryRetBugsItem) restClientServiceImpl.sendGetMethod(urlQuery,"",headers,  QueryRetBugsItem.class);
		log.info( ToStringBuilder.reflectionToString(q, ToStringStyle.MULTI_LINE_STYLE) );
		
		log.info("<- getResultSet() ");
		return q;
	}

}
