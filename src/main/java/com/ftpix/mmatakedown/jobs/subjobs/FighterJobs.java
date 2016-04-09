package com.ftpix.mmatakedown.jobs.subjobs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftpix.mmatakedown.Constants;
import com.ftpix.mmatakedown.db.DB;
import com.ftpix.mmatakedown.models.Fighter;

public class FighterJobs {
	private final Logger logger = LoggerFactory.getLogger(FighterJobs.class);

	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM");

	/**
	 * Check if a fighter exists in the DB via it's url, if not it'll create it
	 * 
	 * @param sherdogURL
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public Fighter checkFighter(String sherdogURL, String name) throws SQLException, IOException, ParseException {
		Fighter fighter;
		try {
			fighter = DB.fighterDao.queryForEq("sherdogUrl", sherdogURL).get(0);

			return fighter;
		} catch (Exception e) {
			if (e.getClass() != SQLException.class) {
				fighter = new Fighter();
				fighter.setName(name);
				fighter.setSherdogUrl(sherdogURL);
				DB.fighterDao.createOrUpdate(fighter);
				refreshInfo(fighter);
				return fighter;

			} else {
				throw e;
			}

		}

	}

	/**
	 * Parse fighter's sherdog page to get more info
	 * 
	 * @param fighter
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public Fighter refreshInfo(Fighter fighter) throws IOException, ParseException, SQLException {
		logger.info("Refreshing fighter {}", fighter.getName());
		Document doc = Jsoup.connect(fighter.getSherdogUrl()).get();

		// Getting nick name
		try {
			Elements nickname = doc.select("h1[itemprop=\"name\"] span.nickname em");
			fighter.setNickname(nickname.get(0).html());
		} catch (Exception e) {
			// no info, skipping
		}

		// Birthday
		try {
			Elements birthday = doc.select("span[itemprop=\"birthDate\"]");
			fighter.setBirthday(df.parse(birthday.get(0).html()));
		} catch (Exception e) {
			// no info, skipping
		}
		// height
		try {
			Elements height = doc.select(".size_info .height strong");
			fighter.setHeight(height.get(0).html());
		} catch (Exception e) {
			// no info, skipping
		}
		// weight
		try {
			Elements weight = doc.select(".size_info .weight strong");
			fighter.setWeight(weight.get(0).html());
		} catch (Exception e) {
			// no info, skipping
		}
		// wins
		try {
			Elements wins = doc.select(".bio_graph .counter");
			fighter.setWins(Integer.parseInt(wins.get(0).html()));
		} catch (Exception e) {
			// no info, skipping
		}
		// wins
		try {
			Elements losses = doc.select(".bio_graph.loser .counter");
			fighter.setLosses(Integer.parseInt(losses.get(0).html()));
		} catch (Exception e) {
			// no info, skipping
		}
		// draws and NC
		Elements drawsNc = doc.select(".right_side .bio_graph .card");
		for (Element element : drawsNc) {

			switch (element.select("span.result").html()) {
			case "Draws":
				fighter.setDraws(Integer.parseInt(element.select("span.counter").html()));
				break;

			case "N/C":
				fighter.setNc(Integer.parseInt(element.select("span.counter").html()));
				break;
			}

		}

		Elements picture = doc.select(".bio_fighter .content img[itemprop=\"image\"]");
		String url = picture.attr("src").trim();

		if (url.length() > 0) {

			String newPath = Constants.FIGHTER_PICTURE_CACHE_FOLDER + fighter.getId() + ".JPG";
			File f = new File(newPath);
			FileUtils.copyURLToFile(new URL(url), f);
			fighter.setPicture(newPath.replace("cache/", ""));
		}

		DB.fighterDao.update(fighter);

		return fighter;
	}

}
