package com.nagarro.account.util;

import com.nagarro.account.exception.BaseException;
import com.nagarro.account.exception.enums.ErrorCode;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private DateUtil() {
    }

    public static final String AFRICA_CAIRO_ZONE = "Africa/Cairo";
    public static final String DEFAULT_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static LocalDateTime getCairoZonedLocalDateTime() {
        return LocalDateTime.now(ZoneId.of(AFRICA_CAIRO_ZONE));
    }

    public static Date convertLocalDateTimeToCairoZone(LocalDateTime localDateTime) {
        return Date
                .from(localDateTime.atZone(ZoneId.of(AFRICA_CAIRO_ZONE))
                        .toInstant());
    }

    public static Date convertDateToCairoZone(Date date) {
        var formatter = new SimpleDateFormat(DEFAULT_PATTERN, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone(AFRICA_CAIRO_ZONE));
        try {
            return formatter.parse(date.toString());
        } catch (Exception e) {
            throw BaseException.builder().errorCode(ErrorCode.BAD_DATE_FORMATTER).build();
        }
    }

    public static LocalDate convertStringToLocalDate(String stringDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(stringDate, formatter);
    }

}
