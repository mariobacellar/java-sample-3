package com.accenture.tim.vsts.query.brachs.ativas.json.branch;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.accenture.tim.vsts.query.brachs.ativas.QueryBranchs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
* http://www.jsonschema2pojo.org/
* @author mario.bacellar
*
*/
public class QueryRetBranches implements Serializable {

	@SerializedName("value")
	@Expose
	private List<Value> value = null;
	@SerializedName("count")
	@Expose
	private Integer count;
	private final static long serialVersionUID = -8109490383738583561L;
	
	public List<Value> getValue() {
	return value;
	}
	
	public void setValue(List<Value> value) {
	this.value = value;
	}
	
	public Integer getCount() {
	return count;
	}
	
	public void setCount(Integer count) {
	this.count = count;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("value", value).append("count", count).toString();
	}
	
	@Override
	public int hashCode() {
	return new HashCodeBuilder().append(count).append(value).toHashCode();
	}
	
	@Override
	public boolean equals(Object other) {
	if (other == this) {
	return true;
	}
	if ((other instanceof QueryRetBranches) == false) {
	return false;
	}
	QueryRetBranches rhs = ((QueryRetBranches) other);
	return new EqualsBuilder().append(count, rhs.count).append(value, rhs.value).isEquals();
	}


	public static void main(String[] args) {
		try {
			
			String user="mariobacellar";
			String pass="Senha123";
			
			String 	idRepo_POCS="a95f16ad-af33-4bf7-a4b3-28619649cdbc";

			String url= "https://accenturetim.visualstudio.com/WEB_VAS/_apis/git/repositories/"+idRepo_POCS+"/refs?includeLinks==false&api-version=4.1";
					
			QueryBranchs		qb 			= new QueryBranchs(user,pass,url);
			QueryRetBranches	branchRet	= qb.getResultSet();

			Integer			branchsCount	= branchRet.getCount();
			List<Value> 	branchValues	= branchRet.getValue();
			
			System.out.println("branchsCount=["+branchsCount+"]");
			
			for (Value value : branchValues) {
				System.out.println("- Branch=["+value.getName()+"]");
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
