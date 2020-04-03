package net.fatekits.lotus.utils;

import org.apache.commons.lang.time.DurationFormatUtils;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static DecimalFormat getDecimalFormat() {
        return new DecimalFormat("0.0");
    }

    public static long parse(String input) {
        if (input == null || input.isEmpty()) {
            return -1;
        }
        long result = 0;
        StringBuilder number = new StringBuilder();
        int i = 0;
        while (i < input.length()) {
            String str;
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                result += TimeUtils.convert(Integer.parseInt(str), c);
                number = new StringBuilder();
            }
            ++i;
        }
        return result;
    }

    private static long convert(int value, char unit) {
        switch (unit) {
            case 'y': {
                return (long)value * TimeUnit.DAYS.toMillis(365);
            }
            case 'M': {
                return (long)value * TimeUnit.DAYS.toMillis(30);
            }
            case 'd': {
                return (long)value * TimeUnit.DAYS.toMillis(1);
            }
            case 'h': {
                return (long)value * TimeUnit.HOURS.toMillis(1);
            }
            case 'm': {
                return (long)value * TimeUnit.MINUTES.toMillis(1);
            }
            case 's': {
                return (long)value * TimeUnit.SECONDS.toMillis(1);
            }
        }
        return -1;
    }

    public static class IntegerCountdown {
        public static String setFormat(Integer value) {
            int remainder = value * 1000;
            int seconds = remainder / 1000 % 60;
            int minutes = remainder / 60000 % 60;
            int hours = remainder / 3600000 % 24;
            return String.valueOf(hours > 0 ? String.format("%02d:", hours) : "") + String.format("%02d:%02d", minutes, seconds);
        }
    }

    public static class LongCountdown {
        public static String setFormat(Long value) {
            if (value < TimeUnit.MINUTES.toMillis(1)) {
                return String.valueOf(TimeUtils.getDecimalFormat().format((double)value.longValue() / 1000.0)) + "s";
            }
            return DurationFormatUtils.formatDuration((long)value, (String)(String.valueOf(value >= TimeUnit.HOURS.toMillis(1) ? "HH:" : "") + "mm:ss"));
        }
    }

}
