package com.accenture.tim.vsts.query.bugs.json.items;
import java.io.Serializable;
import java.util.List;

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
public class QueryRetBugsItems implements Serializable {
	
	@SerializedName("queryType")
	@Expose
	private String queryType;
	
	@SerializedName("queryResultType")
	@Expose
	private String queryResultType;
	
	@SerializedName("asOf")
	@Expose
	private String asOf;
	
	@SerializedName("columns")
	@Expose
	private List<Column> columns = null;
	
	@SerializedName("workItems")
	@Expose
	private List<WorkItems> workItems = null;

	private final static long serialVersionUID = -8439012213552826525L;

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	public String getQueryResultType() {
		return queryResultType;
	}
	
	public void setQueryResultType(String queryResultType) {
		this.queryResultType = queryResultType;
	}
	
	public String getAsOf() {
		return asOf;
	}
	
	public void setAsOf(String asOf) {
		this.asOf = asOf;
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public List<WorkItems> getWorkItems() {
		return workItems;
	}
	
	public void setWorkItems(List<WorkItems> workItems) {
		this.workItems = workItems;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("queryType"			, queryType			)
				                        .append("queryResultType"	, queryResultType	)
				                        .append("asOf"				, asOf				)
				                        .append("columns"			, columns			)
				                        .append("workItems"			, workItems			).toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(workItems).append(queryType).append(asOf).append(columns).append(queryResultType).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof QueryRetBugsItems) == false) {
			return false;
		}
		QueryRetBugsItems rhs = ((QueryRetBugsItems) other);
		return new EqualsBuilder().append(workItems, rhs.workItems).append(queryType, rhs.queryType).append(asOf, rhs.asOf).append(columns, rhs.columns).append(queryResultType, rhs.queryResultType).isEquals();
	}

}