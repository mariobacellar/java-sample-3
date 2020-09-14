package com.accenture.tim.vsts.query.bugs;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.accenture.tim.vsts.query.Query;
import com.accenture.tim.vsts.query.bugs.json.items.QueryRetBugsItems;
import com.accenture.tim.vsts.query.bugs.json.items.WorkItems;

public class QueryItems extends Query{

	final static Logger log  = Logger.getLogger(QueryItems.class);

	public QueryItems(String user, String pass) {
		super(user, pass);
	}

	public QueryItems(String basicAuth_user, String basicAuth_password, String queryUrl) {
		super(basicAuth_user, basicAuth_password, queryUrl);
	}

	@Override
	public List<WorkItems> getResultSet() throws Exception {
		log.info("-> getResultSet() ");
		
		if (urlQuery==null)
			throw new Exception("The 'urlQuery' parameter is null!!!");
		
		buildClient();
		
		buildHeaders();
		
		QueryRetBugsItems q = (QueryRetBugsItems) restClientServiceImpl.sendGetMethod(urlQuery,"",headers,  QueryRetBugsItems.class);
		log.info( ToStringBuilder.reflectionToString(q, ToStringStyle.MULTI_LINE_STYLE) );
		
		List<WorkItems> ret = q.getWorkItems();
		log.info("<- getResultSet() ");
		return ret;
	}




}
