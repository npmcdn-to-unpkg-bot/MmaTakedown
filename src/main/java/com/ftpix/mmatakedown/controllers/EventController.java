package com.ftpix.mmatakedown.controllers;

import static com.ftpix.mmatakedown.db.DB.eventDao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.models.Event;
import com.ftpix.mmatakedown.models.Fight;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class EventController implements ModelController<Event, Integer> {
	private final static Logger logger = LoggerFactory.getLogger(EventController.class);

	@Override
	public List<Event> getAll() throws Exception {
		logger.info("[Event] get");
		return eventDao.queryForAll();
	}

	@Override
	public Event get(Integer id) throws Exception {
		logger.info("[Event] get({})", id);
		return eventDao.queryForId(id);
	}

	@Override
	public Event insert(Event object) throws Exception {
		logger.info("[Event] insert");
		eventDao.create(object);
		return object;
	}

	@Override
	public Event update(Event object) throws Exception {
		logger.info("[event] update");
		eventDao.update(object);
		return object;
	}

	@Override
	public boolean delete(Integer id) throws Exception {
		logger.info("[Event] delete({})", id);
		return eventDao.deleteById(id) == 1;
	}

	/**
	 * Gets an event fights
	 * @param eventId
	 * @return
	 * @throws SQLException 
	 */
	public List<Fight> getFights(int eventId) throws SQLException {

		QueryBuilder<Fight, Integer> queryBuilder = DB.fightDao.queryBuilder();
		Where<Fight, Integer> where = queryBuilder.where();
		where.eq("event_id", eventId);
	
		
		PreparedQuery<Fight> preparedQuery = queryBuilder.prepare();

		
		return DB.fightDao.query(preparedQuery);
	}

}
