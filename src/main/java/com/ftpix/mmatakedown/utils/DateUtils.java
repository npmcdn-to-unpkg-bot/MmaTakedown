package com.ftpix.mmatakedown.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

	private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public static Date convertTimeZone(Date date, String from, String to) {

		TimeZone fromTZ = TimeZone.getTimeZone(from);
		TimeZone toTZ = TimeZone.getTimeZone(to);

		return convertTimeZone(date, fromTZ, toTZ);
	}
	
	public static Date convertTimeZone(Date date, TimeZone from, TimeZone to) {

		int hourDiff = (to.getOffset(date.getTime())- from.getOffset(date.getTime()));
		
		logger.info("from: {}. to: {}, from->to:{}", from.getOffset(date.getTime())/3600000, to.getOffset(date.getTime())/3600000, hourDiff/3600000);
		
		Calendar toCalendar = new GregorianCalendar(to);
		toCalendar.setTime(date);
		toCalendar.add(Calendar.MILLISECOND, hourDiff);

		return toCalendar.getTime();
	}
}
