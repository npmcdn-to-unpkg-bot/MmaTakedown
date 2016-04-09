package com.ftpix.mmatakedown.jobs.subjobs;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.models.Event;
import com.ftpix.mmatakedown.models.Fight;
import com.ftpix.mmatakedown.models.Fighter;

public class EventJobs {
	private final int FIGHTER1_COLUMN = 1, FIGHTER2_COLUMN = 2;
	private final FighterJobs fighterJobs = new FighterJobs();
	private final Logger logger = LoggerFactory.getLogger(EventJobs.class);

	/**
	 * Gets the fights for a particular event
	 * @param event
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public List<Fight> getFights(Event event) throws IOException, SQLException, ParseException {
		List<Fight> fights = new ArrayList<Fight>();
		Document doc = Jsoup.connect(event.getShergodUrl()).get();
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
				
				DB.fightDao.create(fight);
				fight = new Fight();
				i = 1;
			}
		}

		logger.info("Found {} fights for: {}", fights.size(), event.getName());

		return fights;
	}
}
