package com.accenture.tim.vsts.comparator;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.csv.model.BranchsAtivasCSVModel;
import com.accenture.tim.vsts.csv.model.CSVModelAbstract;
import com.accenture.tim.vsts.csv.model.EpicEmDesenvolvimentoCSVModel;
import com.accenture.tim.vsts.csv.read.BranchsAtivasCSVReader;
import com.accenture.tim.vsts.csv.read.EpicEmDesenvolvimentoCSVReader;
import com.accenture.tim.vsts.csv.write.EpicEmDesenvolvimentDiffCSVWriter;

public class EpicEmDesenvolvimentCSVComparator extends CSVComparator {

	final static Logger log  = Logger.getLogger(EpicEmDesenvolvimentCSVComparator.class);

	private List<CSVModelAbstract> 	ltDominio;		//Lista de dados que será varrida
	private List<CSVModelAbstract> 	ltContradominio; //Lista de dados que será verificada a ocorrência de cada elemento do ltDominio
	private List<CSVModelAbstract>	ltFuncao;		//Lista de dados que existe no ltContradominio para cada ltDominio 
	
	private String idDomino;
	private String idContradomino;
	
	/**
	 * 
	 * @param ltDominio        Lista de dados que será varrida
	 * @param ltContradominio  Lista de dados que será verificada a ocorrência de cada elemento do ltDominio
	 */
	public EpicEmDesenvolvimentCSVComparator(List<CSVModelAbstract> dominio, List<CSVModelAbstract> contradominio) {
		this.ltDominio		= dominio;
		this.ltContradominio= contradominio;
		this.ltFuncao		= new ArrayList<CSVModelAbstract>();
		this.ltFuncao.add( contradominio.get(0));// A primeira linha é o header
	}

	
	public String getIdDomino()      { return this.idDomino;}
	public String getIdContradomino(){ return this.idContradomino;}

	public void setIdDomino			(String idDomino)    	{ this.idDomino		 = idDomino; }
	public void setIdContradomino	(String idContradomino)	{ this.idContradomino= idContradomino; }


	public void setLtDominio		(List<CSVModelAbstract> ltDominio		){ this.ltDominio 		= ltDominio			;}
	public void setLtFuncao			(List<CSVModelAbstract> ltFuncao		){ this.ltFuncao		= ltFuncao			;}
	public void setLtContradominio	(List<CSVModelAbstract> ltContradominio	){ this.ltContradominio = ltContradominio	;}
	
	public List<CSVModelAbstract> getLtDominio() 		{ return ltDominio; }
	public List<CSVModelAbstract> getLtContradominio() 	{ return ltContradominio; }
	public List<CSVModelAbstract> getLtFuncao() 		{ return ltFuncao; }


	/**
	 * 
	 * @throws Exception
	 */
	public void compare() throws Exception {
		
		log.debug("-> compare()");
        
		int count = 0;
		
		log.info ("-  Identificando Registros nas 'Branchs Ativas' que existem nos 'Epicos em Desenvolvimento'");
		for (CSVModelAbstract dominioModel : ltDominio) {
			
			setIdDomino( ((BranchsAtivasCSVModel)dominioModel).getBranch() );
			log.debug("- BranchsAtivasCSVModel.branch=["+getIdDomino()+"]");

			boolean hasBranch_into_EpicRecords = false;
			
			//count = 0 -> header
			if (count>0) { 
				
				for (CSVModelAbstract contradominioModel : ltContradominio) {
					
					EpicEmDesenvolvimentoCSVModel funcao = new EpicEmDesenvolvimentoCSVModel();
							
					setIdContradomino( ((EpicEmDesenvolvimentoCSVModel)contradominioModel).getTitle() );
					log.debug("- EpicEmDesenvolvimentoCSVModel.Title=["+getIdContradomino()+"]");
			        
			        if ( compareTo(getIdDomino(), getIdContradomino())  ) {
			        	
			        	// A Branh existe nos Épicos em DEsenvolvimento
			        	funcao.setModelDomain      (dominioModel);
			        	funcao.setModelContradomain(contradominioModel);
	
			        	ltFuncao.add( funcao );
						log.info("- [ADDING] funcao=[ dominio.........=("+getIdDomino()      +")");
						log.info("- [ADDING] funcao=[ contradominio...=("+getIdContradomino()+")");
						
						if (!hasExistDiff()) hasExistDiff(true);
						if (!hasBranch_into_EpicRecords) hasBranch_into_EpicRecords=true;
			        }
				}
				
	        	// A Branch não existe nos Épicos em Desenvolvimento. Será informado no arquivo de Diff somente os dados da Branch
				if (!hasBranch_into_EpicRecords) {
					
					EpicEmDesenvolvimentoCSVModel 
					funcao = new EpicEmDesenvolvimentoCSVModel();
		        	funcao.setModelDomain      (dominioModel);
		        	funcao.setModelContradomain(null);
		        	ltFuncao.add( funcao );
					log.info("- [ADDING  ONLY BRANCH funcao=[ dominio.........=("+getIdDomino()+")");
					log.info("- [ADDING] ONLY BRANCH funcao=[ contradominio...=(null)");
				}
			}
			count++; //count = 0 -> header

		}
		log.info("<- compare()");
	}

	
	
	public static void main(String[] args) {
		try {
			log.info("-> main");
	
			String					dominioDir			= "git/";
			String					dominioCSVFile		= "REPO_BRANCH_20180615.csv";
			String					dominioCSVSplitBy	= ";";
			BranchsAtivasCSVReader	dominioReader		= new BranchsAtivasCSVReader(dominioDir, dominioCSVFile, dominioCSVSplitBy);
			dominioReader.load();
			List<CSVModelAbstract>  ltDominio = dominioReader.getLtCSVModel();

			
			String                          contradominioDir		= "csv/";
			String                          contradominioCSVFile	= "REPO_EPICDEV_20180615.csv";
			String                          contradominioCSVSplitBy	= ";";
			EpicEmDesenvolvimentoCSVReader	contradominioReader		= new EpicEmDesenvolvimentoCSVReader(contradominioDir, contradominioCSVFile, contradominioCSVSplitBy);
			contradominioReader.load();
			List<CSVModelAbstract>  		ltContradominio	= contradominioReader.getLtCSVModel();
			
			EpicEmDesenvolvimentCSVComparator 
			comparator = new EpicEmDesenvolvimentCSVComparator(ltDominio, ltContradominio);
			comparator.compare();

			
			
			log.info("========================================================================");
			log.info("-  Grava dados da FUNÇÂO / EPICOSx x BRANCHS...");
			log.info("========================================================================");
			String writerDir		= "diff/";
			String writerCSVFile	= "REPO_EPICDIFF_TESTE.csv";
			String writerCSVSplitBy = ";";
			EpicEmDesenvolvimentDiffCSVWriter 
			writer = new EpicEmDesenvolvimentDiffCSVWriter(writerDir, writerCSVFile, writerCSVSplitBy, comparator.getLtFuncao());
			writer.write();
			
			
			log.info("<- main");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}



}
