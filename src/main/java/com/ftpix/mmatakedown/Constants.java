package com.ftpix.mmatakedown;

import java.io.File;
import java.util.ResourceBundle;

public class Constants {
	public final static String SHERDOG_TIME_ZONE = "America/New_York";
	public final static String CACHE_FOLDER;
	public final static String FIGHTER_PICTURE_CACHE_FOLDER;
	public final static String DB_PATH;
	public final static int PORT;
	public final static int PARSING_TIMEOUT = 60000;
	
	static{
	    ResourceBundle rs = ResourceBundle.getBundle("conf/mtd");
	    
	    String path = rs.getString("cache_path");;
	    if(!path.endsWith("/")){
	    	path += "/";
	    }
	    
	    CACHE_FOLDER = path;
	    FIGHTER_PICTURE_CACHE_FOLDER = CACHE_FOLDER+"pictures/fighters/";
	    
	    DB_PATH = rs.getString("db_path");
	    
	    PORT = Integer.parseInt(rs.getString("port"));
	    
		File f = new File(FIGHTER_PICTURE_CACHE_FOLDER);
		if(!f.exists()){
			f.mkdirs();
		}
	}
}
