package com.accenture.tim.vsts.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.commons.DateUtils;
import com.accenture.tim.vsts.commons.FileUtils;
import com.accenture.tim.vsts.comparator.EpicEmDesenvolvimentCSVComparator;
import com.accenture.tim.vsts.csv.model.CSVModelAbstract;
import com.accenture.tim.vsts.csv.read.BranchsAtivasCSVReader;
import com.accenture.tim.vsts.csv.read.EpicEmDesenvolvimentoCSVReader;
import com.accenture.tim.vsts.csv.write.EpicEmDesenvolvimentDiffCSVWriter;
import com.accenture.tim.vsts.email.SendEmail;

public class TaskEpicosEmDevBranchAtiva extends TaskAbstract {

	public static Logger log  = Logger.getLogger(TaskEpicosEmDevBranchAtiva.class);

	public TaskEpicosEmDevBranchAtiva() {
		super();
	}
	
	
	/**
	 * 1) A tarefa se resume em receber 2 arquivos de entrada, gerar 1 arquivo de saída e enviar esse arquivo de saida por email.
	 * 
	 * 2) Esses arquivos são conhecidos como:
	 * . domain ou domínio................: Para esta classe (TaskEpicosEmDevBranchAtiva), são as brachs ativas
	 * . contradomain ou contradomínio....: Para esta classe (TaskEpicosEmDevBranchAtiva), são os épicos em desenvolvimento
	 * . função...........................: Para esta classe (TaskEpicosEmDevBranchAtiva), são as branchs que tem épicos em desenvolvimento 
	 * 
	 * 3) Para a execução dessa tarefa cOnfigure os parametro 'task._epicos.branchs_.' do arquivo application.properties
	 * 
	 * 4) Para o envio do email configure o arquivo send.email.properties
	 * 
	 * 5) O arquivo de dominio é gerado pelo scrip git-branchs-rpt
	 * 
	 * 6) O arquivo de contradominio é gerado pelo Java com.accenture.tim.vsts.query.epicos.desenvolvimento.Execute
	 * 
	 * 
	 * @throws Exception
	 */
	public void task() throws Exception {
		log.info("-> task");
		
		

		log.info("========================================================================");
		log.info("-  Exrtai as BRANCHS do Git/VSTS (gera arquivo) ...");
		log.info("========================================================================");
		new com.accenture.tim.vsts.query.brachs.ativas.Execute().run();

		log.info("========================================================================");
		log.info("-  Exrtai as ÉPICOS em Desenvolvimento do Git/VSTS (gera arquivo)...");
		log.info("========================================================================");
		new com.accenture.tim.vsts.query.epicos.desenvolvimento.Execute().run();

		log.info("========================================================================");
		log.info("-  Exrtai os BUGS do Git/VSTS (gera arquivo)...");
		log.info("========================================================================");
		new com.accenture.tim.vsts.query.bugs.Execute().run();
				
		
		

		log.info("========================================================================");
		log.info("-  Append 'Bugs'(Workitens) into 'Epicos'(Workitens) ...");
		log.info("========================================================================");
		String	epicfilePattern	= prop.getProperty("query._epicos.branchs_.file.pattern"); // REPO_EPIC_
		String	epicfileDir   	= prop.getProperty("query._epicos.branchs_.dir"); // csv
		String  epicFileName	= FileUtils.name2CSVFile(epicfilePattern); // REPO_EPIC_20180615.csv
		Path	epicFilePath	= Paths.get( epicfileDir, epicFileName);
		File 	epicFile		= epicFilePath.toFile();
			
		String	bugfilePattern	= prop.getProperty("query._bugs_.file.pattern"); // REPO_BUGS_
		String	bugfileDir   	= prop.getProperty("query._bugs_.dir"); // csv
		String  bugFileName		= FileUtils.name2CSVFile(bugfilePattern); // REPO_BUGS_20180615.csv
		Path	bugFilePath		= Paths.get( bugfileDir, bugFileName);
		File 	bugFile			= bugFilePath.toFile();
	
		String	appendFilePattern= prop.getProperty("task._epicos.branchs_.contradomain.file.pattern"); // REPO_EPIC_BUG_
		String	appendFileDir   =  prop.getProperty("task._epicos.branchs_.contradomain.dir"); // diff/
		String 	appendFileName	=  FileUtils.name2CSVFile(appendFilePattern); // REPO_EPIC_BUG_20180615.csv
		Path	appendFilePath	=  Paths.get( appendFileDir, appendFileName);
		File 	appendFile		=  appendFilePath.toFile();

		File[]  filesToAppend	= {epicFile,bugFile};
		FileUtils.append(filesToAppend, appendFile); // Creates REPO_EPIC_BUG_20180615.csv file to compare with 		
		
		
		
			
		log.info("========================================================================");
		log.info("-  Verifcia se existe aquivo para processar com a data de hoje=["+DateUtils.date2CSVFileName()+"]");
		log.info("========================================================================");
		String	domainFolder		= prop.getProperty("task._epicos.branchs_.domain.dir");
		String	domainPattern		= prop.getProperty("task._epicos.branchs_.domain.file.pattern");//REPO_BRANCH_
		String	contradomainFolder  = prop.getProperty("task._epicos.branchs_.contradomain.dir");
		String	contradomainPattern = prop.getProperty("task._epicos.branchs_.contradomain.file.pattern");//REPO_EPIC_BUG_

		log.debug("-domainFolder        = ["+domainFolder		+"]");
		log.debug("-domainPattern       = ["+domainPattern		+"]");
		log.debug("-contradomainFolder  = ["+contradomainFolder	+"]");
		log.debug("-contradomainPattern = ["+contradomainPattern+"]");
		
		List<File> ltFiles2CompareToday = FileUtils.getFiles2CompareToday(	domainFolder		, domainPattern			, 
																			contradomainFolder	, contradomainPattern	);
		if (ltFiles2CompareToday==null) {
			log.warn("-  @@@ None file to process for ltFiles2CompareToday @@@");
			return;
		}

		File domainFile4Today			= ltFiles2CompareToday.get(0);
		File contradomainFile4Today		= ltFiles2CompareToday.get(1);
		
		if (domainFile4Today==null || "".equals(domainFile4Today.getName().trim()) ) {
			log.warn("-  @@@ None file to process for pattern=["+domainPattern+"] @@@");
			return;
		}
		
		if (contradomainFile4Today==null || "".equals(contradomainFile4Today.getName().trim()) ) {
			log.warn("-  @@@ None file to process for pattern=["+contradomainPattern+"] @@@");
			return;
		}
		
		log.info("-  Processing file (domain)........=["+domainFile4Today.getName()+"]");
		log.info("-  Processing file (contradomain)..=["+contradomainFile4Today.getName()+"]");
		
		

		
		log.info("========================================================================");
		log.info("-  Monta dados do DOMINIO/BRANCHS ("+domainFile4Today.getName()+")...");
		log.info("========================================================================");
		String					dominioDir			= domainFolder;
		String					dominioCSVFile		= domainFile4Today.getName();
		String					dominioCSVSplitBy	= ";";
		BranchsAtivasCSVReader	dominioReader		= new BranchsAtivasCSVReader(dominioDir, dominioCSVFile, dominioCSVSplitBy);
		dominioReader.load();
		List<CSVModelAbstract>  ltDominio = dominioReader.getLtCSVModel();

		log.info("========================================================================");
		log.info("-  Monta dados do CONTRADOMINIO/EPICOS ("+contradomainFile4Today.getName()+")...");
		log.info("========================================================================");
		String                          contradominioDir		= contradomainFolder;
		String                          contradominioCSVFile	= contradomainFile4Today.getName();
		String                          contradominioCSVSplitBy	= ";";
		EpicEmDesenvolvimentoCSVReader	contradominioReader		= new EpicEmDesenvolvimentoCSVReader(contradominioDir, contradominioCSVFile, contradominioCSVSplitBy);
		contradominioReader.load();
		List<CSVModelAbstract>  		ltContradominio	= contradominioReader.getLtCSVModel();

		log.info("========================================================================");
		log.info("-  Monta dados da FUNÇÂO / EPICOSx x BRANCHS...");
		log.info("========================================================================");
		EpicEmDesenvolvimentCSVComparator
		comparator = new EpicEmDesenvolvimentCSVComparator(ltDominio, ltContradominio);
		comparator.compare();
		
		if ( !comparator.hasExistDiff()) {
			log.warn("-  There is no element from domain file ["+dominioCSVFile+"] info contradomain file ["+contradominioCSVFile+"]");
			return;
		}

		log.info("========================================================================");
		log.info("-  Grava dados da FUNÇÂO / EPICOSx x BRANCHS...");
		log.info("========================================================================");
		String writerDir		=                        prop.getProperty("task._epicos.branchs_.funcao.dir");//"diff/";
		String writerCSVFile	= FileUtils.name2CSVFile(prop.getProperty("task._epicos.branchs_.funcao.file.pattern")); // REPO_EPIC_BUG_20180607.csv";
		String writerCSVSplitBy = ";";
		EpicEmDesenvolvimentDiffCSVWriter 
		writer = new EpicEmDesenvolvimentDiffCSVWriter(writerDir, writerCSVFile, writerCSVSplitBy, comparator.getLtFuncao());
		writer.write();
		

		log.info("========================================================================");
		log.info("-> Envia email");
		log.info("========================================================================");
		SendEmail sendEmail = new SendEmail();
		String from			= prop.getProperty("task._epicos.branchs_.from");
		String subject		= prop.getProperty("task._epicos.branchs_.subject");
		String msgHeadline	= prop.getProperty("task._epicos.branchs_.msg.headline");
		String msgLine1		= prop.getProperty("task._epicos.branchs_.msg.line1");
		String msg 			= msgHeadline + msgLine1;

		File		file2Attach = new File(writerDir + writerCSVFile);
		InputStream attachment	= new FileInputStream(file2Attach);
		String 		mimeType	= "application/txt";
		
		List<String> multRecipients = sendEmail.buildRecipients(); 
		log.debug("- multRecipients=["+multRecipients+"]");
		
        sendEmail.send(from, multRecipients, subject, msg, Arrays.asList(attachment), Arrays.asList(writerCSVFile), Arrays.asList(mimeType));
        
        FileUtils.purgeTodaysFiles( prop.getProperty( "task._epicos.branchs_.from") 			);
        FileUtils.purgeTodaysFiles( prop.getProperty( "task._epicos.branchs_.domain.dir") 		);
        FileUtils.purgeTodaysFiles( prop.getProperty( "task._epicos.branchs_.contradomain.dir") );
        FileUtils.purgeTodaysFiles( prop.getProperty( "task._epicos.branchs_.funcao.dir") 		);
        FileUtils.purgeTodaysFiles( prop.getProperty( "query._epicos.branchs_.dir") 			);
        FileUtils.purgeTodaysFiles( prop.getProperty( "query._branchs.ativas_.dir") 			);
        FileUtils.purgeTodaysFiles( prop.getProperty( "query._bugs_.dir") 						);
        
		log.info("<- task");
	}
	
	
	public static void main(String[] args) {
		try {
			log.info("-> main()");
			TaskEpicosEmDevBranchAtiva task = new TaskEpicosEmDevBranchAtiva();
			task.task();
			log.info("-> main()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
