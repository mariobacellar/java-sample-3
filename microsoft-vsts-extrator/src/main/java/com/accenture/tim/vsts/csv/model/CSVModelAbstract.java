package com.accenture.tim.vsts.csv.model;

import java.util.List;

public abstract class CSVModelAbstract {


	private List<CSVModelAbstract>	ltModel;
	private String					lineFromCSV;

	private CSVModelAbstract modelDomain;
	private CSVModelAbstract modelContradomain;
	
	public String getLineFromCSV() {
		return lineFromCSV;
	}

	public void setLineFromCSV(String lineFromCSV) {
		this.lineFromCSV = lineFromCSV;
	}

	public List<CSVModelAbstract> getLtModel() {
		return this.ltModel;
	}

	public void setLtModel(List<CSVModelAbstract> ltmodel) {
		this.ltModel = ltmodel;
	}

	public CSVModelAbstract getModelDomain() {
		return modelDomain;
	}

	public void setModelDomain(CSVModelAbstract modelDomain) {
		this.modelDomain = modelDomain;
	}

	public CSVModelAbstract getModelContradomain() {
		return modelContradomain;
	}

	public void setModelContradomain(CSVModelAbstract modelContradomain) {
		this.modelContradomain = modelContradomain;
	}


	
	
}
