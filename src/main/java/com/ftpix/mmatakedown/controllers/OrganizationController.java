package com.ftpix.mmatakedown.controllers;

import static com.ftpix.mmatakedown.db.DB.organizationDao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.exception.NotFoundException;
import com.ftpix.mmatakedown.models.Event;
import com.ftpix.mmatakedown.models.Organization;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.query.OrderBy;

public class OrganizationController implements ModelController<Organization, Integer> {
	final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	/**
	 * Get all the organizations from the DB
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Organization> getAll() throws SQLException {
		logger.info("[Organization] get");
		return organizationDao.queryForAll();
	}

	/**
	 * Get a single organization by Id
	 * 
	 * @param parseInt
	 *            ID of the organization
	 * @return
	 * @throws SQLException
	 */
	public Organization get(Integer parseInt) throws SQLException {
		logger.info("[Organization] get({})", parseInt);
		return organizationDao.queryForId(parseInt);
	}
	

	/**
	 * Get events for a particular organization
	 * @param parseInt
	 * @return
	 */
	public List<Event> getEvents(int orgId) throws SQLException{
		logger.info("[Organization] getEvents({})", orgId);
		

		QueryBuilder<Event, Integer> queryBuilder = DB.eventDao.queryBuilder();
		Where<Event, Integer> where = queryBuilder.where();
		where.eq("organization_id", orgId);
	
		
		queryBuilder.orderBy("date", true);
		
		PreparedQuery<Event> preparedQuery = queryBuilder.prepare();

		
		return DB.eventDao.query(preparedQuery);
	}


	/**
	 * Insert a new organization
	 * 
	 * @param organization
	 * @return
	 * @throws SQLException
	 */
	public Organization insert(Organization organization) throws SQLException {
		logger.info("[Organization] insert");

		organizationDao.create(organization);

		return organization;
	}

	/**
	 * Update an organization
	 * 
	 * @param organization
	 * @return
	 * @throws SQLException
	 */
	public Organization update(Organization organization) throws SQLException {
		logger.info("[Organization] update({})", organization.getId());

		organizationDao.update(organization);

		return organization;
	}

	/**
	 * Delete an organization
	 * 
	 * @param parseInt
	 * @return
	 * @throws SQLException
	 * @throws NotFoundException
	 */
	public boolean delete(Integer parseInt) throws SQLException, NotFoundException {
		logger.info("[Organization] delete({})", parseInt);

		return organizationDao.deleteById(parseInt) == 1;

	}

}
