package com.accenture.tim.vsts.query.epicos.desenvolvimento.json.item;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkItemComments implements Serializable
{

@SerializedName("href")
@Expose
private String href;
private final static long serialVersionUID = 8451676960537124909L;

public String getHref() {
return href;
}

public void setHref(String href) {
this.href = href;
}

@Override
public String toString() {
return new ToStringBuilder(this).append("href", href).toString();
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(href).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof WorkItemComments) == false) {
return false;
}
WorkItemComments rhs = ((WorkItemComments) other);
return new EqualsBuilder().append(href, rhs.href).isEquals();
}

}
