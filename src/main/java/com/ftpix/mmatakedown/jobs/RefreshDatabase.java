package com.ftpix.mmatakedown.jobs;

import static com.ftpix.mmatakedown.db.DB.eventDao;
import static com.ftpix.mmatakedown.db.DB.fightDao;
import static com.ftpix.mmatakedown.db.DB.organizationDao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.Constants;
import com.ftpix.mmatakedown.controllers.EventController;
import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.jobs.subjobs.EventJobs;
import com.ftpix.mmatakedown.jobs.subjobs.OrganizationJobs;
import com.ftpix.mmatakedown.models.Event;
import com.ftpix.mmatakedown.models.Fight;
import com.ftpix.mmatakedown.models.Fighter;
import com.ftpix.mmatakedown.models.Organization;
import com.ftpix.mmatakedown.utils.DateUtils;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

@DisallowConcurrentExecution
public class RefreshDatabase implements Job {
	private final Logger logger = LoggerFactory.getLogger(RefreshDatabase.class);

	private OrganizationJobs organizationJobs = new OrganizationJobs();
	private EventJobs eventJobs = new EventJobs();

	public void updateDB() throws SQLException {

		List<Organization> Organizations = organizationDao.queryForAll();

		// finding events
		for (Organization organization : Organizations) {
			logger.info("=============================");
			logger.info("Finding events and fights for Organization {}", organization.getName());
			logger.info("=============================");

			// Getting event fights
			try {
				Date newYorkDate = DateUtils.convertTimeZone(new Date(), TimeZone.getDefault(),
						TimeZone.getTimeZone(Constants.SHERDOG_TIME_ZONE));
				EventController controller = new EventController();
				// organizationJobs.cleanEvents(organization);
				for (Event event : organizationJobs.getEvents(organization)) {
					// Getting fights for event that are not past yet or that
					// have no fights
					

					boolean shouldGetFights = event.getDate().after(newYorkDate)
							|| controller.getFights(event.getId()).size() == 0;
					if (shouldGetFights) {
						eventJobs.getFights(event);
					}else{
						logger.info("No need to find fights for event #{}[{}], past and already has list of fights", event.getId(), event.getName());
					}
				}
			} catch (IOException | ParseException e) {
				logger.error("Can't parse events for Organization " + organization.getName(), e);
			}
		}

	}

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		try {
			updateDB();
			logger.info("DB REFRESH COMPLETE");
		} catch (Exception e) {
			logger.error("Error while updating fight DB", e);
		}
	}

}
