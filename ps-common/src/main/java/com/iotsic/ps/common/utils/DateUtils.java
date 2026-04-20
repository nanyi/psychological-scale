package com.iotsic.ps.common.utils;

import com.iotsic.ps.common.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(SystemConstant.DATE_TIME_FORMAT);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(SystemConstant.DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(SystemConstant.TIME_FORMAT);

    /*
     * 日期格式
     * "yyyy"：4位数的年份，例如：2023年表示为"2023"。
     * "yy"：2位数的年份，例如：2023年表示为"23"。
     * "MM"：2位数的月份，取值范围为01到12，例如：7月表示为"07"。
     * "M"：不带前导零的月份，取值范围为1到12，例如：7月表示为"7"。
     * "dd"：2位数的日期，取值范围为01到31，例如：22日表示为"22"。
     * "d"：不带前导零的日期，取值范围为1到31，例如：22日表示为"22"。
     * "EEEE"：星期的全名，例如：星期三表示为"Wednesday"。
     * "E"：星期的缩写，例如：星期三表示为"Wed"。
     * "DDD" 或 "D"：一年中的第几天，取值范围为001到366，例如：第200天表示为"200"。
     * 时间格式
     * "HH"：24小时制的小时数，取值范围为00到23，例如：下午5点表示为"17"。
     * "hh"：12小时制的小时数，取值范围为01到12，例如：下午5点表示为"05"。
     * "mm"：分钟数，取值范围为00到59，例如：30分钟表示为"30"。
     * "ss"：秒数，取值范围为00到59，例如：45秒表示为"45"。
     * "SSS"：毫秒数，取值范围为000到999，例如：123毫秒表示为"123"。
     */

    /**
     * 例如：2023年表示为"23"
     */
    public final static String YY = "yy";

    /**
     * 例如：2023年表示为"2023"
     */
    public final static String YYYY = "yyyy";

    /**
     * 例例如，2023年7月可以表示为 "2023-07"
     */
    public final static String YYYY_MM = "yyyy-MM";

    /**
     * 例如，日期 "2023年7月22日" 可以表示为 "2023-07-22"
     */
    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 例如，当前时间如果是 "2023年7月22日下午3点30分"，则可以表示为 "2023-07-22 15:30"
     */
    public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 例如，当前时间如果是 "2023年7月22日下午3点30分45秒"，则可以表示为 "2023-07-22 15:30:45"
     */
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 例如：下午3点30分45秒，表示为 "15:30:45"
     */
    public final static String HH_MM_SS = "HH:mm:ss";

    /**
     * 例例如，2023年7月可以表示为 "2023/07"
     */
    public final static String YYYY_MM_SLASH = "yyyy/MM";

    /**
     * 例如，日期 "2023年7月22日" 可以表示为 "2023/07/22"
     */
    public final static String YYYY_MM_DD_SLASH = "yyyy/MM/dd";

    /**
     * 例如，当前时间如果是 "2023年7月22日下午3点30分45秒"，则可以表示为 "2023/07/22 15:30:45"
     */
    public final static String YYYY_MM_DD_HH_MM_SLASH = "yyyy/MM/dd HH:mm";

    /**
     * 例如，当前时间如果是 "2023年7月22日下午3点30分45秒"，则可以表示为 "2023/07/22 15:30:45"
     */
    public final static String YYYY_MM_DD_HH_MM_SS_SLASH = "yyyy/MM/dd HH:mm:ss";

    /**
     * 例例如，2023年7月可以表示为 "2023.07"
     */
    public final static String YYYY_MM_DOT = "yyyy.MM";

    /**
     * 例如，日期 "2023年7月22日" 可以表示为 "2023.07.22"
     */
    public final static String YYYY_MM_DD_DOT = "yyyy.MM.dd";

    /**
     * 例如，当前时间如果是 "2023年7月22日下午3点30分"，则可以表示为 "2023.07.22 15:30"
     */
    public final static String YYYY_MM_DD_HH_MM_DOT = "yyyy.MM.dd HH:mm";

    /**
     * 例如，当前时间如果是 "2023年7月22日下午3点30分45秒"，则可以表示为 "2023.07.22 15:30:45"
     */
    public final static String YYYY_MM_DD_HH_MM_SS_DOT = "yyyy.MM.dd HH:mm:ss";

    /**
     * 例如，2023年7月可以表示为 "202307"
     */
    public final static String YYYYMM = "yyyyMM";

    /**
     * 例如，2023年7月22日可以表示为 "20230722"
     */
    public final static String YYYYMMDD = "yyyyMMdd";

    /**
     * 例如，2023年7月22日下午3点可以表示为 "2023072215"
     */
    public final static String YYYYMMDDHH = "yyyyMMddHH";

    /**
     * 例如，2023年7月22日下午3点30分可以表示为 "202307221530"
     */
    public final static String YYYYMMDDHHMM = "yyyyMMddHHmm";

    /**
     * 例如，2023年7月22日下午3点30分45秒可以表示为 "20230722153045"
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";


    private DateUtils() {
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    public static String formatTime(java.time.LocalTime time) {
        if (time == null) {
            return null;
        }
        return time.format(TIME_FORMATTER);
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return formatDateTime(toLocalDateTime(date));
    }

    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 指定毫秒后的时间（格式化 ：yyyy-MM-dd HH:mm:ss）
     * @param ms 指定毫秒后
     * @return 格式化后的时间
     */
    public static Object formatAfterDate(long ms) {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis() + ms);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        return zonedDateTime.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
    }

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static boolean isBefore(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return false;
        }
        return dateTime1.isBefore(dateTime2);
    }

    public static boolean isAfter(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return false;
        }
        return dateTime1.isAfter(dateTime2);
    }

    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    public static long getTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return 0;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static Date nowDate() {
        return new Date();
    }

    public static LocalDateTime addDays(LocalDateTime dateTime, int days) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusDays(days);
    }

    public static LocalDateTime addHours(LocalDateTime dateTime, int hours) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusHours(hours);
    }

    public static LocalDateTime addMinutes(LocalDateTime dateTime, int minutes) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusMinutes(minutes);
    }

    public static LocalDateTime addSeconds(LocalDateTime dateTime, int seconds) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusSeconds(seconds);
    }

    public static long diffDays(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return 0;
        }
        return java.time.Duration.between(dateTime1, dateTime2).toDays();
    }

    public static long diffHours(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return 0;
        }
        return java.time.Duration.between(dateTime1, dateTime2).toHours();
    }

    public static long diffMinutes(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return 0;
        }
        return java.time.Duration.between(dateTime1, dateTime2).toMinutes();
    }
}
