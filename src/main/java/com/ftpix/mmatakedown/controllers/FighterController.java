package com.ftpix.mmatakedown.controllers;

import static com.ftpix.mmatakedown.db.DB.fighterDao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.jobs.subjobs.FighterJobs;
import com.ftpix.mmatakedown.models.Fight;
import com.ftpix.mmatakedown.models.Fighter;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class FighterController implements ModelController<Fighter, Integer> {
	private final static Logger logger = LoggerFactory.getLogger(FighterController.class);

	@Override
	public List<Fighter> getAll() throws Exception {
		logger.info("[Fighter] get");
		return fighterDao.queryForAll();
	}

	@Override
	public Fighter get(Integer id) throws Exception {
		logger.info("[Fighter] get({})", id);
		return fighterDao.queryForId(id);
	}

	@Override
	public Fighter insert(Fighter object) throws Exception {
		logger.info("[Fighter] insert");
		fighterDao.create(object);
		return object;
	}

	@Override
	public Fighter update(Fighter object) throws Exception {
		logger.info("[Fighter] update");
		fighterDao.update(object);
		return object;
	}

	@Override
	public boolean delete(Integer id) throws Exception {
		logger.info("[Fighter] delete({})", id);
		return fighterDao.deleteById(id) == 1;
	}

	/**
	 * Gets a fighter fights
	 * 
	 * @param parseInt
	 * @return
	 */
	public List<Fight> getFights(int fighterId) throws SQLException {
		logger.info("[Fighter] getFights({})", fighterId);

		QueryBuilder<Fight, Integer> queryBuilder = DB.fightDao.queryBuilder();
		Where<Fight, Integer> where = queryBuilder.where();
		where.eq("fighter1_id", fighterId);
		where.or();
		where.eq("fighter2_id", fighterId);

		queryBuilder.orderBy("date", true);

		PreparedQuery<Fight> preparedQuery = queryBuilder.prepare();

		return DB.fightDao.query(preparedQuery);
	}

	/**
	 * Search for fighter based on the name
	 * 
	 * @param parseInt
	 * @return
	 * @throws SQLException 
	 */
	public List<Fighter> search(String query) throws SQLException {
		logger.info("[Fighter] search({})", query);

		QueryBuilder<Fighter, Integer> queryBuilder = fighterDao.queryBuilder();
		Where<Fighter, Integer> where = queryBuilder.where();
		where.raw("LOWER(`name`) LIKE LOWER('%"+query+"%')");
		//where.like("LOWER(name)", "LOWER(%"+query+"%)");

		queryBuilder.orderBy("name", true);

		PreparedQuery<Fighter> preparedQuery = queryBuilder.prepare();

		return fighterDao.query(preparedQuery);
	}

	/**
	 * Refreshes the DB data for a specific fighter
	 * @param parseInt
	 * @return
	 */
	public Fighter refresh(int id) throws Exception {
		logger.info("[Fighter] refresh({})", id);

		Fighter fighter = get(id);
		
		FighterJobs fighterJobs = new FighterJobs();
		fighter = fighterJobs.refreshInfo(fighter);
		
		return fighter;
	}
}
