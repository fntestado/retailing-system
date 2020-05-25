package util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DateModifier {
    private DateModifier() {}

    public static LocalDateTime convertToLocalDateTime(LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();

        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    public static LocalDate convertToLocalDate(LocalDateTime localDateTime) {
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int dayOfMonth = localDateTime.getDayOfMonth();

        return LocalDate.of(year, month, dayOfMonth);
    }
}
