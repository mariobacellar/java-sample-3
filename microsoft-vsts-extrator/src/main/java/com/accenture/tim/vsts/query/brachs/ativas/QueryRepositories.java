package com.accenture.tim.vsts.query.brachs.ativas;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.accenture.tim.vsts.query.Query;
import com.accenture.tim.vsts.query.brachs.ativas.json.repository.QueryRetRepositories;
import com.accenture.tim.vsts.query.brachs.ativas.json.repository.Value;

public class QueryRepositories extends Query {

	final static Logger log  = Logger.getLogger(QueryRepositories.class);

	
	public QueryRepositories(String user, String pass) {
		super(user, pass);
		urlQuery = null;
	}
	
	
	public QueryRepositories(String user, String pass, String url) {
		super(user, pass, url);
	}
	

	public QueryRetRepositories getResultSet() throws Exception {
		log.info("-> getResultSet() ");

		if (urlQuery==null)
			throw new Exception("The 'urlQuery' parameter is null!!!");
		
		buildClient();
		
		buildHeaders();

		QueryRetRepositories q = (QueryRetRepositories) restClientServiceImpl.sendGetMethod(urlQuery,"",headers,  QueryRetRepositories.class);
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
			String url= "https: //accenturetim.visualstudio.com/WEB_VAS/_apis/git/repositories?api-version=4.1";
					
			QueryRepositories		qRepo	= new QueryRepositories(user,pass,url);
			QueryRetRepositories	retRepo = qRepo.getResultSet();

			Integer			reposCount	= retRepo.getCount();
			List<Value> 	repoValues	= retRepo.getValue();
			System.out.println("reposCount=["+reposCount+"]");
			for (Value value : repoValues) {
				System.out.println("Id=["+value.getId()+"] - name=["+value.getName()+"]");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
