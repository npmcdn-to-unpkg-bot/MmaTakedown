package com.ftpix.mmatakedown.models;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "settings")
public class Setting{
	
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String KICKASS = "kickass";
	public static final String KICKASS_PROXY = "kickass-proxy";
	public static final String PIRATEBAY = "piratebay";
	public static final String PIRATEBAY_PROXY = "piratebay-proxy";
	public static final String TRANSMISSION = "transmission";
	public static final String TRANSMISSION_URL = "transmission-url";
	public static final String TRANSMISSION_LOGIN = "transmission-login";
	public static final String TRANSMISSION_PASSWORD = "transmission-password";
	public static final String TRANSMISSION_TORRENTPATH = "transmission-torrent-path";
	public static final String TRANSMISSION_PORT = "transmission-port";
	public static final String BLACKHOLE = "blackhole";
	public static final String BLACKHOLE_PATH = "blackhole-path";
	public static final String RENAMER = "renamer";
	public static final String RENAMER_READ_FROM = "renamer-read-from";
	public static final String RENAMER_DESTINATION_FOLDER = "renamer-desination-folder";
	public static final String RENAMER_FILE_FORMAT = "renamer-file-format";
	public static final String RENAMER_MOVE_FILES = "renamer-move-files";
	
	
	@DatabaseField(id = true)
	@Expose
	public String name;
	
	@DatabaseField
	@Expose
	public  String value;
	

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	
	
	
}
