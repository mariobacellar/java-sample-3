package com.accenture.rest.client.model.analytics;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DetailEventTO {
	
	private String hitType;
	private String campaignSource;
	private String campaignMedium;
	private String campaignName;
	private String campaignContent;
	private String eventCategory;
	private String eventAction;
	private String eventLabel;
	private String eventValue;
	private String eventDate;
	private String schema;
	private Channel channel;
	private String laValue;
	private String userValue;
	
	/**
	 * @return the hitType
	 */
	public String getHitType() {
		return hitType;
	}



	/**
	 * @param hitType the hitType to set
	 */
	public void setHitType(String hitType) {
		this.hitType = hitType;
	}

	/**
	 * @return the campaignSource
	 */
	public String getCampaignSource() {
		return campaignSource;
	}


	/**
	 * @param campaignSource the campaignSource to set
	 */
	public void setCampaignSource(String campaignSource) {
		this.campaignSource = campaignSource;
	}


	/**
	 * @return the campaignMedium
	 */
	public String getCampaignMedium() {
		return campaignMedium;
	}


	/**
	 * @param campaignMedium the campaignMedium to set
	 */
	public void setCampaignMedium(String campaignMedium) {
		this.campaignMedium = campaignMedium;
	}


	/**
	 * @return the campaignName
	 */
	public String getCampaignName() {
		return campaignName;
	}


	/**
	 * @param campaignName the campaignName to set
	 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}


	/**
	 * @return the campaignContent
	 */
	public String getCampaignContent() {
		return campaignContent;
	}


	/**
	 * @param campaignContent the campaignContent to set
	 */
	public void setCampaignContent(String campaignContent) {
		this.campaignContent = campaignContent;
	}


	/**
	 * @return the eventCategory
	 */
	public String getEventCategory() {
		return eventCategory;
	}


	/**
	 * @param eventCategory the eventCategory to set
	 */
	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}


	/**
	 * @return the eventAction
	 */
	public String getEventAction() {
		return eventAction;
	}


	/**
	 * @param eventAction the eventAction to set
	 */
	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}


	/**
	 * @return the eventLabel
	 */
	public String getEventLabel() {
		return eventLabel;
	}


	/**
	 * @param eventLabel the eventLabel to set
	 */
	public void setEventLabel(String eventLabel) {
		this.eventLabel = eventLabel;
	}


	/**
	 * @return the eventValue
	 */
	public String getEventValue() {
		return eventValue;
	}


	/**
	 * @param eventValue the eventValue to set
	 */
	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}


	/**
	 * @return the eventDate
	 */
	public String getEventDate() {
		return eventDate;
	}


	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}


	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}


	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}


	/**
	 * @return the channel
	 */
	public Channel getChannel() {
		return channel;
	}


	/**
	 * @param channel the channel to set
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}



	public String getLaValue() {
		return laValue;
	}



	public void setLaValue(String laValue) {
		this.laValue = laValue;
	}



	public String getUserValue() {
		return userValue;
	}



	public void setUserValue(String userValue) {
		this.userValue = userValue;
	}
}
