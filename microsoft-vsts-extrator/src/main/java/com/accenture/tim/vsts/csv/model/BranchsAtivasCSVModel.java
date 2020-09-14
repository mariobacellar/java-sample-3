package com.accenture.tim.vsts.csv.model;

public class BranchsAtivasCSVModel extends CSVModelAbstract{

	private String repositorio;
	private String branch;
	private String link;
	
	public String getRepositorio() {
		return repositorio;
	}
	public void setRepositorio(String repositorio) {
		this.repositorio = repositorio;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	

	@Override
	public String toString() {
		return 	"repositorio=["+repositorio+"]-branch=["+branch+"]=link=["+link+"]";
	}
}
