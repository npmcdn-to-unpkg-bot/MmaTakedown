package com.ftpix.mmatakedown.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import io.gsonfire.annotations.ExposeMethodResult;


@DatabaseTable(tableName = "organizations")
public class Organization {

	@DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
	@Expose
	private int id;

	@DatabaseField
	@Expose
	private String name;
	
	@DatabaseField
	@Expose
	private String shortName;
	
	@DatabaseField(unique = true)
	@Expose
	private String sherdogUrl;

	@DatabaseField
	@Expose
	private boolean following = false;
	
	@DatabaseField
	@Expose
	private boolean getPrelims = false;

	@DatabaseField
	@Expose
	private String toRemoveFromEventName;

	@DatabaseField
	@Expose
	private String searchInterceptor = "";

	@DatabaseField
	@Expose
	private int quality = QUALITY_SD;

	@DatabaseField
	@Expose
	private String picture;
	
	public static final int QUALITY_SD = 0, QUALITY_HD = 1, QUALITY_ANY = 2;

	@ForeignCollectionField(eager = false, maxEagerLevel = 0)
	public ForeignCollection<Event> events;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSherdogUrl() {
		return sherdogUrl;
	}

	public void setSherdogUrl(String sherdogUrl) {
		this.sherdogUrl = sherdogUrl;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isGetPrelims() {
		return getPrelims;
	}

	public void setGetPrelims(boolean getPrelims) {
		this.getPrelims = getPrelims;
	}

	public String getToRemoveFromEventName() {
		return toRemoveFromEventName;
	}

	public void setToRemoveFromEventName(String toRemoveFromEventName) {
		this.toRemoveFromEventName = toRemoveFromEventName;
	}

	public String getSearchInterceptor() {
		return searchInterceptor;
	}

	public void setSearchInterceptor(String searchInterceptor) {
		this.searchInterceptor = searchInterceptor;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}


	public void setEvents(ForeignCollection<Event> events) {
		this.events = events;
	}

	public ForeignCollection<Event> getEvents() {
		return events;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	


	
}
