package com.accenture.tim.vsts.query.epicos.desenvolvimento.json.item;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * http://www.jsonschema2pojo.org/
 * @author mario.bacellar
 *
 */
public class QueryRetEpicosEmDesenvolvimentoItem implements Serializable {

	@SerializedName("id")
	@Expose
	private Integer id;

	@SerializedName("rev")
	@Expose
	private Integer rev;

	@SerializedName("fields")
	@Expose
	private Fields fields;

	@SerializedName("_links")
	@Expose
	private Links links;

	@SerializedName("url")
	@Expose
	private String url;
	private final static long serialVersionUID = 3091202168790227446L;

	public Integer getId() {
	return id;
	}
	
	public void setId(Integer id) {
	this.id = id;
	}
	
	public Integer getRev() {
	return rev;
	}
	
	public void setRev(Integer rev) {
	this.rev = rev;
	}
	
	public Fields getFields() {
	return fields;
	}
	
	public void setFields(Fields fields) {
	this.fields = fields;
	}
	
	public Links getLinks() {
	return links;
	}
	
	public void setLinks(Links links) {
	this.links = links;
	}
	
	public String getUrl() {
	return url;
	}
	
	public void setUrl(String url) {
	this.url = url;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("id"	, id	)
									.append("rev"	, rev	)
									.append("fields", fields)
									.append("links"	, links	)
									.append("url"	, url	).toString();
	}
	
	@Override
	public int hashCode() {
	return new HashCodeBuilder().append(id).append(rev).append(links).append(url).append(fields).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		
		if ((other instanceof QueryRetEpicosEmDesenvolvimentoItem) == false) {
			return false;
		}
		
		QueryRetEpicosEmDesenvolvimentoItem rhs = ((QueryRetEpicosEmDesenvolvimentoItem) other);
		return new EqualsBuilder().append(id, rhs.id).append(rev, rhs.rev).append(links, rhs.links).append(url, rhs.url).append(fields, rhs.fields).isEquals();
	}

	public String formatCSV() {
		String	ret = null;
		 
		Fields 	itemQueryReturnedFields= this.getFields();
		
		Integer itemId 				= this.getId();
		String  itemTitle			= this.getFields().getSystemTitle();
		String  itemState			= this.getFields().getSystemState();
		Float   itemEfftortFloat	= itemQueryReturnedFields.getMicrosoftVSTSSchedulingEffort();
		String  itemEfftortStr		= ""+itemEfftortFloat;
		String  itemTargetDate 		= itemQueryReturnedFields.getMicrosoftVSTSSchedulingTargetDate(); 
		String  itemInternalStatus	= itemQueryReturnedFields.getCMMIPLTInternalStatus();
		String  itemArea			= itemQueryReturnedFields.getSystemAreaPath();
		String  itemTextField3		= itemQueryReturnedFields.getCMMIPLTTextfield3();
		String  itemAssignedTo		= itemQueryReturnedFields.getSystemAssignedTo();
		String	itemLink            = "https://accenturetim.visualstudio.com/WEB_VAS/_workitems/edit/"+itemId;

				
		itemEfftortStr		= (itemEfftortFloat 	==null) ? "" : itemEfftortStr; 
		itemTargetDate 		= (itemTargetDate	 	==null) ? "" : itemTargetDate; 
		itemInternalStatus	= (itemInternalStatus	==null) ? "" : itemInternalStatus;
		itemArea			= (itemArea				==null) ? "" : itemArea;
		itemTextField3		= (itemTextField3		==null) ? "" : itemTextField3;
		itemAssignedTo		= (itemAssignedTo		==null) ? "" : itemAssignedTo;
		
		
		ret = itemId+";"+itemTitle+";"+itemState+";"+itemEfftortStr+";"+itemTargetDate+";"+itemInternalStatus+";"+itemArea+";"+itemTextField3+";"+itemAssignedTo+";"+itemLink+"\n";
		return ret;
	}

	public String  formatHeader() {
		String[]	columns = {"Id","Title","State","Efftort","TargetDate","InternalStatus","Area","TextField3","AssignedTo","Link"};
		String		csvline = "";
		for (int i = 0; i < columns.length; i++) {
			if (i+1==columns.length)
				csvline = csvline + columns[i];
			else
				csvline = csvline + columns[i]+";";
		}
		 return csvline;
	}
	
}