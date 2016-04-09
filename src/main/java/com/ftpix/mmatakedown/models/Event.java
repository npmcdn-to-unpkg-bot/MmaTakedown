package com.ftpix.mmatakedown.models;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Event {
	private final Logger logger = LoggerFactory.getLogger(Event.class);

	@DatabaseField(generatedId = true)
	@Expose
	private int id;

	@DatabaseField
	@Expose
	private int status = 0;

	@DatabaseField
	@Expose
	private int prelimStatus = 0;
	
	@DatabaseField
	@Expose
	private int earlyPrelimStatus = 0;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
	@Expose
	private Organization organization;

	@DatabaseField
	@Expose
	private String name;
	
	@DatabaseField
	@Expose
	private String originalName;

	@DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
	@Expose
	private Date date;

	@DatabaseField
	@Expose
	private String shergodUrl;

	@DatabaseField
	@Expose
	private String location;
	
	@DatabaseField
	@Expose
	private String prelimLocation;
	
	@DatabaseField
	@Expose
	private String earlyPrelimLocation;;

	@ForeignCollectionField(eager = false, maxEagerLevel = 0)
	private ForeignCollection<Fight> fights;

	public static final int STATUS_SKIPPED = 0, STATUS_WANTED = 1, STATUS_SNATCHED = 2, STATUS_AVAILABLE = 3;

	public final static int TYPE_MAIN = 0, TYPE_PRELIM = 1, TYPE_EARLY_PRELIM = 2;

	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getPrelimStatus() {
		return prelimStatus;
	}



	public void setPrelimStatus(int prelimStatus) {
		this.prelimStatus = prelimStatus;
	}



	public int getEarlyPrelimStatus() {
		return earlyPrelimStatus;
	}



	public void setEarlyPrelimStatus(int earlyPrelimStatus) {
		this.earlyPrelimStatus = earlyPrelimStatus;
	}



	public Organization getOrganization() {
		return organization;
	}



	public void setOrganization(Organization organization) {
		this.organization = organization;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getOriginalName() {
		return originalName;
	}



	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public String getShergodUrl() {
		return shergodUrl;
	}



	public void setShergodUrl(String shergodUrl) {
		this.shergodUrl = shergodUrl;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getPrelimLocation() {
		return prelimLocation;
	}



	public void setPrelimLocation(String prelimLocation) {
		this.prelimLocation = prelimLocation;
	}



	public String getEarlyPrelimLocation() {
		return earlyPrelimLocation;
	}



	public void setEarlyPrelimLocation(String earlyPrelimLocation) {
		this.earlyPrelimLocation = earlyPrelimLocation;
	}



	public Fight[] getFights() {
		return fights.toArray(new Fight[fights.size()]);
	}



	public void setFights(ForeignCollection<Fight> fights) {
		this.fights = fights;
	}



	@Override
	public boolean equals(Object arg0) {
		try {
			Event event = (Event) arg0;
			
			return event.getShergodUrl().equalsIgnoreCase(shergodUrl);
		} catch (Exception e) {
			logger.error("Error while compairng", e);
			return false;
		}
	}
}
