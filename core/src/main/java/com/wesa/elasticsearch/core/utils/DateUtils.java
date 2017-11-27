package com.wesa.elasticsearch.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

  private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

  public static Timestamp getTimestamp(String date) {
    return new Timestamp(stringToDate(date).getTime());
  }

  public static Timestamp getTimestamp(Date date) {
    return new Timestamp(date.getTime());
  }

  public static Date addDays(String date, int amount) {
    return org.apache.commons.lang3.time.DateUtils.addDays(stringToDate(date), amount);
  }

  public static Date addDays(Date date, int amount) {
    return org.apache.commons.lang3.time.DateUtils.addDays(date, amount);
  }

  public static Date stringToDate(String date) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {
      logger.error("Cannot parse date: " + date);
      throw new BadRequestException("Cannot parse date: " + date);
    }
  }

  public static Date toDayStart(Date date){
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date toDayEnd(Date date){
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 999);
    return cal.getTime();
  }




}
