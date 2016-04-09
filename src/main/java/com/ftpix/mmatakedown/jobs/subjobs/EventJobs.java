package com.ftpix.mmatakedown.jobs.subjobs;

import static com.ftpix.mmatakedown.db.DB.eventDao;
import static com.ftpix.mmatakedown.db.DB.fightDao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.Constants;
import com.ftpix.mmatakedown.controllers.EventController;
import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.models.Event;
import com.ftpix.mmatakedown.models.Fight;
import com.ftpix.mmatakedown.models.Fighter;

public class EventJobs {
	private final int FIGHTER1_COLUMN = 1, FIGHTER2_COLUMN = 2;
	private final FighterJobs fighterJobs = new FighterJobs();
	private final Logger logger = LoggerFactory.getLogger(EventJobs.class);
	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HHmmssZ");

	/**
	 * Gets the fights for a particular event
	 * 
	 * @param event
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException
	 */

	public List<Fight> getFights(Event event) throws IOException, SQLException, ParseException {
		Document doc = Jsoup.connect(event.getShergodUrl()).timeout(Constants.PARSING_TIMEOUT).get();
		return getFights(event, doc);
	}

	public List<Fight> getFights(Event event, Document doc) throws IOException, SQLException, ParseException {
		logger.info("Getting fights for event #{}[{}]", event.getId(), event.getName());
		List<Fight> fights = new ArrayList<Fight>();
		Elements names = doc.select(".fighter h3 a, .event_match a[itemprop=\"url\"]");
		int i = 1;

		Fight fight = new Fight();

		for (Element element : names) {
			// logger.info(element.html());
			String name, url;
			Fighter fighter;
			switch (i) {
			case FIGHTER1_COLUMN:
				Elements name1 = element.select("span[itemprop=\"name\"");
				name = name1.get(0).html();
				url = element.attr("abs:href");

				fighter = fighterJobs.checkFighter(url, name);

				fight.setFighter1(fighter);
				break;
			case FIGHTER2_COLUMN:
				Elements name2 = element.select("span[itemprop=\"name\"");

				name = name2.get(0).html();
				url = element.attr("abs:href");

				fighter = fighterJobs.checkFighter(url, name);
				fight.setFighter2(fighter);
				break;
			default:
				break;
			}

			i++;
			if (i == 3) {
				fight.setEvent(event);
				fight.setDate(event.getDate());
				fights.add(fight);

				fight = new Fight();
				i = 1;
			}
		}

		//getting current fights for the event
		EventController eventController = new EventController();
		List<Fight> currentFights = eventController.getFights(event.getId());
		logger.info("There are now {} fights for this event", currentFights.size());
		for (Fight current : currentFights) {
			if (fights.contains(current)) {
				fights.get(fights.indexOf(current)).setId(current.getId());
			} else {
				logger.info("Fight #{}[{} vs {}] doesn't exist anymore, deleting it", current.getId(), current.getFighter1().getName(), current.getFighter2().getName());
				// this event doesn't seem to exist anymore we need to delete it
				fightDao.delete(current);
			}
		}

		// saving the new events
		fights.forEach((toUpdate) -> {
			try {
				fightDao.createOrUpdate(toUpdate);
			} catch (Exception e) {
				logger.error("Error while saving event! ", e);
			}
		});
		
		logger.info("Found {} fights for: {}", fights.size(), event.getName());

		return fights;
	}

	public Event refreshEvent(Event event) throws IOException, SQLException, ParseException {
		logger.info("Refreshing event data: #{}[{}]", event.getId(), event.getName());
		Document doc = Jsoup.connect(event.getShergodUrl()).timeout(Constants.PARSING_TIMEOUT).get();
		//refreshing name and date
		event.setOriginalName(doc.select(".header .section_title span[itemprop=\"name\"]").get(0).html());
		event.setName(event.getOriginalName());
		for (String s : event.getOrganization().getToRemoveFromEventName().split(",")) {
			if (s.trim().length() > 0) {
				event.setName(event.getName().replaceAll("(?i)" + s.trim(), ""));
			}
		}

		if (event.getName().trim().startsWith("-")) {
			event.setName(event.getName().replaceFirst("\\-", ""));
		}

		event.setName(event.getName().replaceAll("( )+", " "));
		event.setName(event.getName().trim());
		
		String date = doc.select("meta[itemprop=\"startDate\"").get(0).attr("content").replace(":", "");
		Date eventDate = df.parse(date);
		event.setDate(eventDate);
		
		DB.fightDao.delete(Arrays.asList(event.getFights()));
		getFights(event, doc);

		DB.eventDao.update(event);
		return event;
	}
}
