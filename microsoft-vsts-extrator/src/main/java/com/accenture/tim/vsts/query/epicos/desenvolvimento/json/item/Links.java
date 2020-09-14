package com.accenture.tim.vsts.query.epicos.desenvolvimento.json.item;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links implements Serializable
{

@SerializedName("self")
@Expose
private Self self;
@SerializedName("workItemUpdates")
@Expose
private WorkItemUpdates workItemUpdates;
@SerializedName("workItemRevisions")
@Expose
private WorkItemRevisions workItemRevisions;
@SerializedName("workItemComments")
@Expose
private WorkItemComments workItemComments;
@SerializedName("html")
@Expose
private Html html;
@SerializedName("workItemType")
@Expose
private WorkItemType workItemType;
@SerializedName("fields")
@Expose
private Fields_ fields;
private final static long serialVersionUID = 4918896767335524468L;

public Self getSelf() {
return self;
}

public void setSelf(Self self) {
this.self = self;
}

public WorkItemUpdates getWorkItemUpdates() {
return workItemUpdates;
}

public void setWorkItemUpdates(WorkItemUpdates workItemUpdates) {
this.workItemUpdates = workItemUpdates;
}

public WorkItemRevisions getWorkItemRevisions() {
return workItemRevisions;
}

public void setWorkItemRevisions(WorkItemRevisions workItemRevisions) {
this.workItemRevisions = workItemRevisions;
}

public WorkItemComments getWorkItemComments() {
return workItemComments;
}

public void setWorkItemComments(WorkItemComments workItemComments) {
this.workItemComments = workItemComments;
}

public Html getHtml() {
return html;
}

public void setHtml(Html html) {
this.html = html;
}

public WorkItemType getWorkItemType() {
return workItemType;
}

public void setWorkItemType(WorkItemType workItemType) {
this.workItemType = workItemType;
}

public Fields_ getFields() {
return fields;
}

public void setFields(Fields_ fields) {
this.fields = fields;
}

@Override
public String toString() {
return new ToStringBuilder(this).append("self", self).append("workItemUpdates", workItemUpdates).append("workItemRevisions", workItemRevisions).append("workItemComments", workItemComments).append("html", html).append("workItemType", workItemType).append("fields", fields).toString();
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(workItemComments).append(workItemUpdates).append(html).append(self).append(workItemType).append(fields).append(workItemRevisions).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof Links) == false) {
return false;
}
Links rhs = ((Links) other);
return new EqualsBuilder().append(workItemComments, rhs.workItemComments).append(workItemUpdates, rhs.workItemUpdates).append(html, rhs.html).append(self, rhs.self).append(workItemType, rhs.workItemType).append(fields, rhs.fields).append(workItemRevisions, rhs.workItemRevisions).isEquals();
}

}
