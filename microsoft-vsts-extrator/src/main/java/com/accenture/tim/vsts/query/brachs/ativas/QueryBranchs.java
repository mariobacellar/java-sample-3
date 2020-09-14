package com.accenture.tim.vsts.query.brachs.ativas;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.accenture.tim.vsts.query.Query;
import com.accenture.tim.vsts.query.brachs.ativas.json.branch.QueryRetBranches;
import com.accenture.tim.vsts.query.brachs.ativas.json.branch.Value;

public class QueryBranchs extends Query {

	final static Logger log  = Logger.getLogger(QueryBranchs.class);

	
	public QueryBranchs(String user, String pass) {
		super(user, pass);
		urlQuery = null;
	}
	
	
	public QueryBranchs(String user, String pass, String url) {
		super(user, pass, url);
	}
	

	public QueryRetBranches getResultSet() throws Exception {
		log.info("-> getResultSet() ");

		if (urlQuery==null)
			throw new Exception("The 'urlQuery' parameter is null!!!");
		
		buildClient();
		
		buildHeaders();

		QueryRetBranches q = (QueryRetBranches) restClientServiceImpl.sendGetMethod(urlQuery,"",headers,  QueryRetBranches.class);
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
			String repoId=
			//"af2f61ca-e40f-4794-a1f6-f3a7f1b67594";
			"c106abbb-f3d3-4202-999e-fdb898ae2c10";
			
			String url= "https://accenturetim.visualstudio.com/WEB_VAS/_apis/git/repositories/"+repoId+"/refs?api-version=4.1";
					
			QueryBranchs		qBranch		= new QueryBranchs(user,pass,url);
			QueryRetBranches	retBranch	= qBranch.getResultSet();

			Integer			reposCount	= retBranch.getCount();
			List<Value> 	repoValues	= retBranch.getValue();
			System.out.println("reposCount=["+reposCount+"]");
			
			for (Value value : repoValues) {
				System.out.println("Name=["+value.getName()+"]");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
