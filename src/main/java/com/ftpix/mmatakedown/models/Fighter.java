package com.ftpix.mmatakedown.models;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "fighters")
public class Fighter {
	
	@DatabaseField(generatedId = true)
	@Expose
	private long id;

	@DatabaseField
	@Expose
	private String name = "";
	
	
	@DatabaseField
	@Expose
	private String nickname = "";
	
	@DatabaseField
	@Expose
	private String height = "";
	
	@DatabaseField
	@Expose
	private String weight = "";
	
	@DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
	@Expose
	private Date birthday;
		
	@DatabaseField
	@Expose
	private int wins = 0;
	
	
	@DatabaseField
	@Expose
	private int losses = 0;
	
	@DatabaseField
	@Expose
	private int draws = 0;
	
	@DatabaseField
	@Expose
	private int nc = 0;
	
	@DatabaseField
	@Expose
	private String picture = "";
	
	@DatabaseField(unique = true)
	@Expose
	private String sherdogUrl;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSherdogUrl() {
		return sherdogUrl;
	}

	public void setSherdogUrl(String sherdogUrl) {
		this.sherdogUrl = sherdogUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getDraws() {
		return draws;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	public int getNc() {
		return nc;
	}

	public void setNc(int nc) {
		this.nc = nc;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
	
	
}
