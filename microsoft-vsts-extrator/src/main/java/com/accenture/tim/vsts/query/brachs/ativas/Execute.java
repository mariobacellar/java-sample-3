package com.accenture.tim.vsts.query.brachs.ativas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.commons.FileUtils;
import com.accenture.tim.vsts.email.SendEmail;
import com.accenture.tim.vsts.query.ExecuteAbstract;
import com.accenture.tim.vsts.query.brachs.ativas.json.branch.QueryRetBranches;
import com.accenture.tim.vsts.query.brachs.ativas.json.repository.QueryRetRepositories;
import com.accenture.tim.vsts.query.brachs.ativas.json.repository.Value;

public class Execute extends ExecuteAbstract {

	final static Logger log  = Logger.getLogger(Execute.class);

	private static String queryName = "Branchs Ativas";
	
	private static String basicAuth_user;
	private static String basicAuth_password;
	
	private static int repoCount;
	private static int branchCount;
	
	public void run() {
		BufferedWriter writer = null;
		try {
			log.info("-> run()");
			log.info("- Runnig Query=["+queryName+"]");
			
			Properties 
			prop = new Properties();
			prop.load(SendEmail.class.getResourceAsStream("/application.properties"));
			
			basicAuth_user     = prop.getProperty("query._branchs.ativas_.basicAuth_user") ;//"mariobacellar";
			basicAuth_password = prop.getProperty("query._branchs.ativas_.basicAuth_password") ;//"Senha123";
			
			String querRepositoryUrl   = prop.getProperty("query._branchs.ativas_.repos.url"  );//"https://accenturetim.visualstudio.com/ac3236be-af02-4584-90ab-e4d75e1f7764/_apis/wit/wiql/6c058252-f794-4a0c-bd75-938f243b4c8a"; 
			log.info("- querRepositoryUrl=["+querRepositoryUrl+"]");
			
			QueryRepositories queryRepos = new QueryRepositories(basicAuth_user, basicAuth_password, querRepositoryUrl);
			
			log.info("- Getting all repositories from -> Query=["+queryName+"]");
			QueryRetRepositories	queryRetRepo = queryRepos.getResultSet();
			List<Value>				ltRepoValues = queryRetRepo.getValue();
			log.info("- Returning all repositories = ["+queryRetRepo.getCount().toString()+"]");

			if (ltRepoValues==null)
				
				log.info("- None Repositories returned from -> Query=["+queryName+"]");

			 else {
				 
				 repoCount = 0;
				 String  csvline = "";
				 
				 File csvFile = getFileName(prop); 
				 writer = new BufferedWriter (new FileWriter(csvFile));
				 writeHeader(writer);
				 log.debug("- Writing file ["+csvFile+"]");

				 for (Value value : ltRepoValues) {
					 
					 String repoId	 = value.getId();   
					 String repoName = value .getName(); 
					 
					 //String repo2debug = 
					 //"Servicos_CadAuto";
					 //"TIM_Components_infobus";
					 //"TIM_Components_nakedSim";
					 //"Camada_Exposicao_API_Grupo_Internet";
					 //"Servicos_Captive"
					 //"TIM_Components_restClient";
					 //"TIM_Components_customerInformationManagement"
					 
					 boolean isDebug = true;//repoName.equals(repo2debug);
					 
					 if (isDebug) {

					 log.info("- repoId......=["+repoId		+ "]");
					 log.info("- repoName....=["+repoName	+ "]");

					 repoCount++;
					 if (repoCount==100)
						 Thread.sleep(20*1000);
					 
//					 File csvFile = getFileNameSeq(prop); 
//					 writer = new BufferedWriter (new FileWriter(csvFile));
//					 log.debug("- Writing file ["+csvFile+"]");
					 
					 
					 String 
					 querBranchyUrl = prop.getProperty("query._branchs.ativas_.branchs.url");//"https://accenturetim.visualstudio.com/ac3236be-af02-4584-90ab-e4d75e1f7764/_apis/wit/wiql/6c058252-f794-4a0c-bd75-938f243b4c8a"; 
					 querBranchyUrl = querBranchyUrl.replace("${repoId}", repoId);
					 log.info("- querBranchyUrl=["+querBranchyUrl+"]");

					 QueryBranchs		queryBranchs	= new QueryBranchs(basicAuth_user, basicAuth_password, querBranchyUrl);
					 QueryRetBranches	queryRetBranchs = queryBranchs.getResultSet();
					 
					 List<com.accenture.tim.vsts.query.brachs.ativas.json.branch.Value>  ltQueryBranchs	= queryRetBranchs.getValue();
					 
//					 writeHeader(writer);
					 branchCount=0;
					 for (com.accenture.tim.vsts.query.brachs.ativas.json.branch.Value	valueBranch : ltQueryBranchs) {
						 
						 branchCount++;
						 
						 String fullName	= valueBranch.getName();
						 String branchName	= getShortBranchName(fullName);
						 String link		= "https://accenturetim.visualstudio.com/WEB_VAS/WEB_VAS%20Team/_git/" + repoName + "?_a=contents&version=GB" + branchName;

						 boolean isOnlyHeadsBranchs = (fullName.indexOf("/heads/")>0 );
								 
						 if (isOnlyHeadsBranchs) {
							 csvline = repoName + ";" + branchName + ";" + link + "\n";
							 writer.write(csvline);					 
							 writer.flush();
							 log.debug("- ===========================================");
							 log.debug("- Repository.......("+String.format("%03d", repoCount)  +")....=["+repoName+"]");
							 log.debug("- Branch Full......("+String.format("%03d", branchCount)+")....=["+fullName+"]");
							 log.debug("- Branch Short.....("+String.format("%03d", branchCount)+")....=["+branchName+"]");
							 log.debug("- Link......................=["+link+"]");
							 log.debug("- csvline...................=["+csvline+"]");
							 log.debug("- ===========================================");					 
						}
						}		 
				 }

				 }// if isDebug

//				 String dirFilesToMerge		= prop.getProperty("query._branchs.ativas_.dir.tmp");
//				 String pattern   			= prop.getProperty("query._branchs.ativas_.file.pattern");
//				 String dirToWriteMergedFile= prop.getProperty("query._branchs.ativas_.dir");
//				 String fileNameMerged		= FileUtils.name2CSVFile(pattern); 
//				 FileUtils.mergeFiles(dirFilesToMerge, dirToWriteMergedFile, fileNameMerged); 
			 }
						
			log.info("<- run()");
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e);
			
		} finally {
			if (writer !=null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e);
				}
		}
	}
	
	
	
	private void writeHeader(BufferedWriter writer) throws IOException {
		writer.write(formatHeader());
		writer.flush();
	}


	private static File getFileNameSeq(Properties prop) {
		String dir       		= prop.getProperty("query._branchs.ativas_.dir.tmp");
		String pattern   		= prop.getProperty("query._branchs.ativas_.file.pattern");
		String qtdRepos			= String.format("%03d", repoCount)+"_";
		String fileName  		= FileUtils.name2CSVFile(dir+pattern+qtdRepos);
		return new File(fileName);
	}

	private static File getFileName(Properties prop) {
		String dir       		= prop.getProperty("query._branchs.ativas_.dir");
		String pattern   		= prop.getProperty("query._branchs.ativas_.file.pattern");
		String fileName  		= FileUtils.name2CSVFile(dir+pattern);
		return new File(fileName);
	}

	private static String getShortBranchName(String fullName) {
		String ret = fullName.substring( fullName.lastIndexOf('/') +1);
		return ret;
	}

	public String formatHeader() {
		String	ret = "Repositorio;Branch;Link\n"; 
		return ret;
	}
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			log.info("-> main");
			
/////////////////////////////////
new Execute().run();
/////////////////////////////////
			
			
/////////////////////////////////
//			Properties 
//			prop = new Properties();
//			prop.load(SendEmail.class.getResourceAsStream("/application.properties"));
//			
//			String 
//			querBranchyUrl = prop.getProperty("query._branchs.ativas_.branchs.url"); 
//			querBranchyUrl = querBranchyUrl.replace("${repoId}", "af2f61ca-e40f-4794-a1f6-f3a7f1b67594");
//			System.out.println("querBranchyUrl=["+querBranchyUrl+"]");
/////////////////////////////////
			
/////////////////////////////////
//			Properties 
//			prop = new Properties();
//			prop.load(SendEmail.class.getResourceAsStream("/application.properties"));
//		
//			repoCount++;
//			
//			File f = getFileName(prop); 
//			System.out.println("f=["+f+"]");
/////////////////////////////////

/////////////////////////////////
//			String fullName = "refs/heads/development";
//			String shortName= getShortBranchName(fullName);
//			System.out.println("fullName =["+fullName+"]");
//			System.out.println("shortName=["+shortName+"]");
//			
//			fullName = "development";
//			shortName= getShortBranchName(fullName);
//			System.out.println("fullName =["+fullName+"]");
//			System.out.println("shortName=["+shortName+"]");
/////////////////////////////////
			
//			String	fullName			= "refs/heads/WEB0077";
//			boolean isOnlyHeadsBranchs	= ( fullName.indexOf("/heads/")>0 );
//			System.out.println("isOnlyHeadsBranchs=["+isOnlyHeadsBranchs+"]");
			
			log.info("<- main");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
