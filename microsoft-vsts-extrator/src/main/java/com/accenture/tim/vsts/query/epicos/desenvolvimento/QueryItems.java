package com.accenture.tim.vsts.query.epicos.desenvolvimento;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.accenture.rest.legacy.exception.ValidationException;
import com.accenture.tim.vsts.query.ExecuteAbstract;
import com.accenture.tim.vsts.query.Query;
import com.accenture.tim.vsts.query.epicos.desenvolvimento.json.items.QueryRetEpicosEmDesenvolvimentoItems;
import com.accenture.tim.vsts.query.epicos.desenvolvimento.json.items.WorkItems;

public class QueryItems extends Query {

	final static Logger log  = Logger.getLogger(ExecuteAbstract.class);

	/**
	 * 
	 * @param user
	 * @param pass
	 */
	public QueryItems(String user, String pass) {
		super(user, pass);
		urlQuery = null;
	}


	public QueryItems(String user, String pass, String url) {
		super(user, pass, url);
	}
	
		
	/**
	 * Executa a qyery "Epicos em Desenvolvimento"
	 * @return
	 * @throws ClientProtocolException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws ValidationException
	 */
	public List<WorkItems> getResultSet() throws Exception /*ClientProtocolException, UnsupportedEncodingException, IOException , ValidationException*/ {
		log.info("-> getResultSet() ");

		if (urlQuery==null)
			throw new Exception("The 'urlQuery' parameter is null!!!");

		buildClient();
		
		buildHeaders();

		QueryRetEpicosEmDesenvolvimentoItems q = (QueryRetEpicosEmDesenvolvimentoItems) restClientServiceImpl.sendGetMethod(urlQuery,"",headers,  QueryRetEpicosEmDesenvolvimentoItems.class);
		log.info( ToStringBuilder.reflectionToString(q, ToStringStyle.MULTI_LINE_STYLE) );
		
		List<WorkItems> ret = q.getWorkItems();
		log.info("<- getResultSet() ");
		return ret;
	}

}
