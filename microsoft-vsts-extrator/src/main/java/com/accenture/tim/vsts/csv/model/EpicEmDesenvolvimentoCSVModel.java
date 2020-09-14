package com.accenture.tim.vsts.csv.model;

public class EpicEmDesenvolvimentoCSVModel extends CSVModelAbstract{

	private String id;
	private String title;
	private String state;
	private String efftort;
	private String targetdate;
	private String internalstatus;
	private String area;
	private String textfield3;
	private String assignedto;
	private String link;

	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public String getState() {
		return state;
	}




	public void setState(String state) {
		this.state = state;
	}




	public String getEfftort() {
		return efftort;
	}




	public void setEfftort(String efftort) {
		this.efftort = efftort;
	}




	public String getTargetdate() {
		return targetdate;
	}




	public void setTargetdate(String targetdate) {
		this.targetdate = targetdate;
	}




	public String getInternalstatus() {
		return internalstatus;
	}




	public void setInternalstatus(String internalstatus) {
		this.internalstatus = internalstatus;
	}




	public String getArea() {
		return area;
	}




	public void setArea(String area) {
		this.area = area;
	}




	public String getTextfield3() {
		return textfield3;
	}




	public void setTextfield3(String textfield3) {
		this.textfield3 = textfield3;
	}




	public String getAssignedto() {
		return assignedto;
	}




	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}




	public String getLink() {
		return link;
	}




	public void setLink(String link) {
		this.link = link;
	}




	@Override
	public String toString() {
		return    "id=["+id+"] - "
				+ "title=["+title+"] - "
				+ "state=["+state+"] - "
				+ "efftort=["+efftort+"] - "
				+ "targetdate=["+targetdate+"] - "
				+ "internalstatus=["+internalstatus+"] - "
				+ "area=["+area+"] - "
				+ "textfield3=["+textfield3+"] - "
				+ "assignedto=["+assignedto+"] -  "
				+ "link=["+ link+"]\n"
				+ "lineFromCSV=["+getLineFromCSV()+"]";
	}
	
}
