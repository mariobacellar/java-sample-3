package com.accenture.tim.vsts.csv.write;

import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.tim.vsts.csv.model.BranchsAtivasCSVModel;
import com.accenture.tim.vsts.csv.model.CSVModelAbstract;
import com.accenture.tim.vsts.csv.model.EpicEmDesenvolvimentoCSVModel;

public class EpicEmDesenvolvimentDiffCSVWriter extends CSVWriterAbstract{

	final static Logger log  = Logger.getLogger(EpicEmDesenvolvimentDiffCSVWriter.class);
	
	public  EpicEmDesenvolvimentDiffCSVWriter(String dir, String csvFile, String cvsSplitBy, List<CSVModelAbstract> ltCSVModel) {
		super(dir, csvFile, cvsSplitBy, ltCSVModel);
		String header="Repositorio;Branch;Internal;State;Assigedto;Area;Title;LinkBranch;LinkWorkitem";
		setCvsHeader(header);
		hasHeaderWritten=false;
	}

	@Override
	protected String  formatLine(CSVModelAbstract model) {
		
		log.debug("-> formatLine()");
		
		CSVModelAbstract modelDomain		= model.getModelDomain();
		CSVModelAbstract modelContradomain	= model.getModelContradomain();
		
		if ( modelDomain==null && modelContradomain==null) {
			log.debug("- Passando pelo cabecalho...");
			return null;
		}

		BranchsAtivasCSVModel branchModel = (BranchsAtivasCSVModel)modelDomain;
		String repositorio	= branchModel.getRepositorio();
		String branch		= branchModel.getBranch();
		String linkBrach	= branchModel.getLink();

		
		EpicEmDesenvolvimentoCSVModel	epicDevModel = ( (modelContradomain!=null) ? (EpicEmDesenvolvimentoCSVModel)modelContradomain : null );
		String title		= "";
		String internal		= "";
		String state		= "";
		String assigedto	= "";
		String area			= "";
		String linkWorkitem = "";

		if (epicDevModel!=null) {
			title		 = epicDevModel.getTitle();
			internal	 = epicDevModel.getInternalstatus();
			state		 = epicDevModel.getState();
			assigedto	 = epicDevModel.getAssignedto();
			area		 = epicDevModel.getArea();
			linkWorkitem = epicDevModel.getLink();
		}
		
		String line = repositorio+";"+branch+";"+internal+";"+state+";"+assigedto+";"+area+";"+title+";"+linkBrach+";"+linkWorkitem;
		log.debug("<- header..=["+getCvsHeader()+"])");
		log.debug("<- line....=["+line+"])");
		log.debug("<- formatLine()");
		return line+"\n";
	}

	
}
