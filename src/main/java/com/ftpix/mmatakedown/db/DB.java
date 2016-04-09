package com.ftpix.mmatakedown.db;

import java.sql.SQLException;

import com.ftpix.mmatakedown.Constants;
import com.ftpix.mmatakedown.models.Event;
import com.ftpix.mmatakedown.models.Fight;
import com.ftpix.mmatakedown.models.Fighter;
import com.ftpix.mmatakedown.models.Organization;
import com.ftpix.mmatakedown.models.Setting;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DB {
	public static Dao<Organization, Integer> organizationDao = null;
	public static Dao<Event, Integer> eventDao = null;
	public static Dao<Fight, Integer> fightDao = null;
	public static Dao<Fighter, Integer> fighterDao = null;
	public static Dao<Setting, Integer> settingsDao = null;
	
	private final static String databaseUrl = "jdbc:h2:"+Constants.DB_PATH;
	static {
		try {

			// this uses h2 by default but change to match your database
			// create a connection source to our database
			ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, "sa", "");
			

			organizationDao = DaoManager.createDao(connectionSource, Organization.class);
			TableUtils.createTableIfNotExists(connectionSource, Organization.class);

			eventDao = DaoManager.createDao(connectionSource, Event.class);
			TableUtils.createTableIfNotExists(connectionSource, Event.class);

			fightDao = DaoManager.createDao(connectionSource, Fight.class);
			TableUtils.createTableIfNotExists(connectionSource, Fight.class);

			fighterDao = DaoManager.createDao(connectionSource, Fighter.class);
			TableUtils.createTableIfNotExists(connectionSource, Fighter.class);

			settingsDao = DaoManager.createDao(connectionSource, Setting.class);
			TableUtils.createTableIfNotExists(connectionSource, Setting.class);
			
			
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

	}


}
