package cn.argento.askia.times;


import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * 这两个类一般用于时间方面的计算问题
 * 其中Duration用于计算时间、period一般用于计算日期
 */
public class PeriodDurationDemo {
    public static void main(String[] args) {
        Period of = Period.of(2020, 12, 12);
//        "P2Y"             -- Period.ofYears(2)
//        "P3M"             -- Period.ofMonths(3)
//        "P4W"             -- Period.ofWeeks(4)
//        "P5D"             -- Period.ofDays(5)
//        "P1Y2M3D"         -- Period.of(1, 2, 3)
//        "P1Y2M3W4D"       -- Period.of(1, 2, 25)
//        "P-1Y2M"          -- Period.of(-1, 2, 0)
//        "-P1Y2M"          -- Period.of(-1, -2, 0)
        Period p2Y = Period.parse("P2Y");
        Period period = of.withDays(3);
        System.out.println(period);
        //           "PT20.345S" -- parses as "20.345 seconds"
        //          "PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
        //          "PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
        //          "P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
        //          "P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
        //          "P-6H3M"    -- parses as "-6 hours and +3 minutes"
        //          "-P6H3M"    -- parses as "-6 hours and -3 minutes"
        //          "-P-6H+3M"  -- parses as "+6 hours and -3 minutes"
        Duration parse = Duration.parse("P3DT3H4M20.345S");
        System.out.println(parse);

        // 但是在加的时候要特别注意夏令时，使用Duration可能会因为加上夏令时回拨了一个小时，
        // 而想要忽略夏令时的影响则需要使用Period
        ZonedDateTime meeting = ZonedDateTime.of(LocalDate.of(2013, 3, 8),
                LocalTime.of(14, 30), ZoneId.of("America/Los_Angeles"));
        System.out.println("meeting: " + meeting);
        System.out.println(meeting.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
        ZonedDateTime nextMeeting = meeting.plus(Duration.ofDays(7));
        // Caution! Won’t work with daylight savings time
        System.out.println("nextMeeting: " + nextMeeting);
        nextMeeting = meeting.plus(Period.ofDays(7)); // OK
        System.out.println("nextMeeting: " + nextMeeting);
    }
}
