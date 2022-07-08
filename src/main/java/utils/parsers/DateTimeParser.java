package utils.parsers;

import com.google.cloud.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.google.cloud.Date;

/**
 * Mandatory to use google.cloud.Timestamp
 */
public final class DateTimeParser {


    public static Timestamp getCurrentTime() {
        return Timestamp.now();
    }

    public static Timestamp getYesterdayTime() {

        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);

        return Timestamp.of(java.util.Date.from(yesterday));
    }

    public static Date getCurrentDate() {
        return Date.parseDate(LocalDate.now().toString());
    }

    public static Date getDateMinusMonth() {
        return Date.parseDate((LocalDate.now().toString()));
    }

    public static Date getDatePlusMonth() {
        return Date.parseDate((LocalDate.now().plusMonths(1).toString()));
    }

}