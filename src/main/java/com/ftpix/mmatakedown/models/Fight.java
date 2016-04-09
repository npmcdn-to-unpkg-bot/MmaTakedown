package com.ftpix.mmatakedown.models;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

public class Fight {

	@DatabaseField(generatedId = true)
	@Expose
	public int id;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
	@Expose
	private Event event;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
	@Expose
	private Fighter fighter1;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
	@Expose
	private Fighter fighter2;

	@DatabaseField
	@Expose
	private Date date;

	@DatabaseField
	@Expose
	private int result;

	@DatabaseField
	@Expose
	private String winType;

	public static int FIGHTER_1_WIN = 0, FIGHTER_2_WIN = 1, DRAW = 2, NO_CONTEST = 3;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fighter getFighter1() {
		return fighter1;
	}

	public void setFighter1(Fighter fighter1) {
		this.fighter1 = fighter1;
	}

	public Fighter getFighter2() {
		return fighter2;
	}

	public void setFighter2(Fighter fighter2) {
		this.fighter2 = fighter2;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getWinType() {
		return winType;
	}

	public void setWinType(String winType) {
		this.winType = winType;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Fight fight = (Fight) obj;

			boolean equals = fight.getFighter1().getId() == fighter1.getId()
					&& fight.getFighter2().getId() == fighter2.getId() && event.getId() == fight.getEvent().getId();

			return equals;
		} catch (Exception e) {
			return false;
		}
	}

}
