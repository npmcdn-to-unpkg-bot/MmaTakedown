package com.ftpix.mmatakedown.controllers;

import static com.ftpix.mmatakedown.db.DB.fightDao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.models.Fight;

public class FightController implements ModelController<Fight, Integer>{
	private final static Logger logger = LoggerFactory.getLogger(FightController.class);

	@Override
	public List<Fight> getAll() throws Exception {
		logger.info("[Fight] get");
		return fightDao.queryForAll();
	}

	@Override
	public Fight get(Integer id) throws Exception {
		logger.info("[Fight] get({})", id);
		return fightDao.queryForId(id);
	}

	@Override
	public Fight insert(Fight object) throws Exception {
		logger.info("[Fight] insert");
		fightDao.create(object);
		return object;
	}

	@Override
	public Fight update(Fight object) throws Exception {
		logger.info("[Fight] update");
		fightDao.update(object);
		return object;
	}

	@Override
	public boolean delete(Integer id) throws Exception {
		logger.info("[Fight] delete({})", id);
		return fightDao.deleteById(id) == 1;
	}

}
