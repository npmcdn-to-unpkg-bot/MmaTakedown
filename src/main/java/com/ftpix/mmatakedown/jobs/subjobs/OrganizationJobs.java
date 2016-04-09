package com.ftpix.mmatakedown.jobs.subjobs;

import static com.ftpix.mmatakedown.db.DB.eventDao;
import static com.ftpix.mmatakedown.db.DB.fightDao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.Constants;
import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.models.Event;
import com.ftpix.mmatakedown.models.Organization;
import com.ftpix.mmatakedown.utils.DateUtils;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class OrganizationJobs {
	private final int DATE_COLUMN = 1, NAME_COLUMN = 2, LOCATION_COLUMN = 3;
	private final Logger logger = LoggerFactory.getLogger(OrganizationJobs.class);
	
	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HHmmssZ");
	private final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	
	/**
	 * 
	 * @param organization
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public List<Event> getEvents(Organization organization) throws IOException, ParseException, SQLException {

		// finding events
		List<Event> availableEvents = eventDao.queryForEq("organization_id", organization.getId());

		List<Event> events = new ArrayList<>();
		logger.info("Availale Events: {}", availableEvents.size());

		logger.info("Refreshing available event list");

		Document doc = Jsoup.connect(organization.getSherdogUrl()).get();
		Elements td = doc.select(".event td");
		int i = 1;
		td.remove(0);
		td.remove(0);
		td.remove(0);
		Event event = new Event();
		for (Element element : td) {
			switch (i) {
			case DATE_COLUMN:
				Elements metaDate = element.select("meta[itemprop=\"startDate\"");
				String date = metaDate.get(0).attr("content").replace(":", "");
				Date eventDate = df.parse(date);
				event.setDate(eventDate);
				break;
			case NAME_COLUMN:
				Elements name = element.select("span[itemprop=\"name\"");
				event.setName(name.get(0).html());
				event.setOriginalName(name.get(0).html());

				for (String s : organization.getToRemoveFromEventName().split(",")) {
					if (s.trim().length() > 0) {
						event.setName(event.getName().replaceAll("(?i)" + s.trim(), ""));
					}
				}

				if (event.getName().trim().startsWith("-")) {
					event.setName(event.getName().replaceFirst("\\-", ""));
				}

				event.setName(event.getName().replaceAll("( )+", " "));
				event.setName(event.getName().trim());
				Elements url = element.select("a[itemprop=\"url\"");
				event.setShergodUrl(url.get(0).attr("abs:href"));
				break;
			case LOCATION_COLUMN:
				String[] split = element.html().split(">");
				event.setLocation(split[1].trim());
				break;
			default:
				break;
			}
			i++;
			if (i == 4) {
				event.setOrganization(organization);

				// Date local = DateUtils.convertTimeZone(event.getDate(),
				// TimeZone.getTimeZone(Constants.SHERDOG_TIME_ZONE),
				// TimeZone.getDefault());
				// logger.info("Event date: Sherdog:{}, local:{}",
				// event.getDate(), local);

				if (!availableEvents.contains(event)) {
					logger.info("Adding event {}", event.getName());
					events.add(event);
					eventDao.create(event);
				}

				event = new Event();
				i = 1;
			}
			// logger.info(element.html());
		}

		// TODO: FIND CANCELLED EVENTS
		return events;
	}
	
	
	/**
	 * Cleans the DB from all future events and events with 0 fights
	 * 
	 * @throws SQLException
	 */
	public void cleanEvents(Organization organization) throws SQLException {
		// Finding event to remove.
		// All the event with a date (new york time,used by sherdog), that are
		// not yet passed will be refreshed, so we delete them from the DB first
		Date newYorkDate = DateUtils.convertTimeZone(new Date(), TimeZone.getDefault(),
				TimeZone.getTimeZone(Constants.SHERDOG_TIME_ZONE));
		logger.info("Deleting all future events for full refresh");
		logger.info("Time in New York:{}", df2.format(newYorkDate));

		QueryBuilder<Event, Integer> queryBuilder = DB.eventDao.queryBuilder();
		Where<Event, Integer> where = queryBuilder.where();
		where.gt("date", newYorkDate);
		where.and();
		where.eq("organization_id", organization.getId());
		PreparedQuery<Event> preparedQuery = queryBuilder.prepare();

		List<Event> eventsToDelete = DB.eventDao.query(preparedQuery);

		for (Event event : eventsToDelete) {
			int deletedFights = fightDao.delete(Arrays.asList(event.getFights()));
			logger.info("Deleted {} fights for event {}", deletedFights, event.getName());
		}
		
		int deleted = eventDao.delete(eventsToDelete);
			
		logger.info("Deleted {} future events", deleted);

		// Finding events with 0 fights
		List<Event> availableEvents = eventDao.queryForEq("organization_id", organization.getId());
		for (Event event : availableEvents) {
			if (event.getFights().length == 0) {
				logger.info("Deleting event: {} because it has no fights, planned to be refreshed", event.getName());
				DB.eventDao.delete(event);
			}
		}

	}
	
}
