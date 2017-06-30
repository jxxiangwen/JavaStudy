package cn.edu.shu.fourth;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jxxiangwen on 17-5-14.
 */
public class CalendarTest {
    public static void main(String[] args) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        int today = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        int month = gregorianCalendar.get(Calendar.MONTH);

        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);

        int weekday = gregorianCalendar.get(Calendar.DAY_OF_WEEK);

        int firstDayOfWeek = gregorianCalendar.getFirstDayOfWeek();

        int indent = 0;

        while (weekday != firstDayOfWeek) {
            indent++;
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
            weekday = gregorianCalendar.get(Calendar.DAY_OF_WEEK);
        }

        String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();

        do {
            System.out.printf("%4s", weekdayNames[weekday]);
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            weekday = gregorianCalendar.get(Calendar.DAY_OF_WEEK);
        } while (weekday != firstDayOfWeek);

        System.out.println();

        for (int i = 1; i <= indent; i++) {
            System.out.print("    ");
        }

        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);

        do {
            int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
            System.out.printf("%3d", day);

            if (day == today) {
                System.out.print("*");
            } else {
                System.out.print(" ");
            }

            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            weekday = gregorianCalendar.get(Calendar.DAY_OF_WEEK);
            if (weekday == firstDayOfWeek) {
                System.out.println();
            }

        } while (gregorianCalendar.get(Calendar.MONTH) == month);

        if (weekday != firstDayOfWeek) {
            System.out.println();
        }
    }
}
