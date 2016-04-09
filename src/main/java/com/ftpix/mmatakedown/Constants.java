package com.ftpix.mmatakedown;

import java.io.File;

public class Constants {
	public final static String SHERDOG_TIME_ZONE = "America/New_York";
	public final static String CACHE_FOLDER = "cache/";
	public final static String FIGHTER_PICTURE_CACHE_FOLDER = CACHE_FOLDER+"pictures/fighters/";
	
	static{
		File f = new File(FIGHTER_PICTURE_CACHE_FOLDER);
		if(!f.exists()){
			f.mkdirs();
		}
	}
}
