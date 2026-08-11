package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    private ISO8601Utils() {
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean millis) {
        return format(date, millis, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean millis, TimeZone tz) {
        Calendar calendar = new GregorianCalendar(tz, Locale.US);
        calendar.setTime(date);
        int capacity = "yyyy-MM-ddThh:mm:ss".length();
        StringBuilder formatted = new StringBuilder(capacity + (millis ? ".sss".length() : 0) + (tz.getRawOffset() == 0 ? "Z" : "+hh:mm").length());
        padInt(formatted, calendar.get(1), "yyyy".length());
        formatted.append('-');
        padInt(formatted, calendar.get(2) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(5), "dd".length());
        formatted.append('T');
        padInt(formatted, calendar.get(11), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(12), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(13), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(14), "sss".length());
        }
        int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            int hours = Math.abs((offset / 60000) / 60);
            int minutes = Math.abs((offset / 60000) % 60);
            formatted.append(offset >= 0 ? '+' : '-');
            padInt(formatted, hours, "hh".length());
            formatted.append(':');
            padInt(formatted, minutes, "mm".length());
        } else {
            formatted.append('Z');
        }
        return formatted.toString();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x005f A[Catch: IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, TryCatch #6 {IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, blocks: (B:12:0x003a, B:14:0x0040, B:22:0x005f, B:24:0x0070, B:25:0x0072, B:27:0x007f, B:29:0x0084, B:31:0x008a, B:36:0x0096, B:42:0x00a9, B:44:0x00b1, B:57:0x00e2), top: B:112:0x003a }] */
    /* JADX WARN: Code duplicated, block: B:24:0x0070 A[Catch: IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, TryCatch #6 {IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, blocks: (B:12:0x003a, B:14:0x0040, B:22:0x005f, B:24:0x0070, B:25:0x0072, B:27:0x007f, B:29:0x0084, B:31:0x008a, B:36:0x0096, B:42:0x00a9, B:44:0x00b1, B:57:0x00e2), top: B:112:0x003a }] */
    /* JADX WARN: Code duplicated, block: B:27:0x007f A[Catch: IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, TryCatch #6 {IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, blocks: (B:12:0x003a, B:14:0x0040, B:22:0x005f, B:24:0x0070, B:25:0x0072, B:27:0x007f, B:29:0x0084, B:31:0x008a, B:36:0x0096, B:42:0x00a9, B:44:0x00b1, B:57:0x00e2), top: B:112:0x003a }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0083  */
    /* JADX WARN: Code duplicated, block: B:41:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:44:0x00b1 A[Catch: IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, TRY_LEAVE, TryCatch #6 {IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, blocks: (B:12:0x003a, B:14:0x0040, B:22:0x005f, B:24:0x0070, B:25:0x0072, B:27:0x007f, B:29:0x0084, B:31:0x008a, B:36:0x0096, B:42:0x00a9, B:44:0x00b1, B:57:0x00e2), top: B:112:0x003a }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:48:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:49:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:51:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:54:0x00da A[Catch: IllegalArgumentException | IndexOutOfBoundsException -> 0x0205, IndexOutOfBoundsException -> 0x0207, TRY_LEAVE, TryCatch #5 {IllegalArgumentException | IndexOutOfBoundsException -> 0x0205, blocks: (B:3:0x0005, B:5:0x0017, B:6:0x0019, B:8:0x0025, B:9:0x0027, B:52:0x00d4, B:54:0x00da, B:64:0x00f6), top: B:113:0x0005 }] */
    /* JADX WARN: Code duplicated, block: B:57:0x00e2 A[Catch: IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, TRY_ENTER, TRY_LEAVE, TryCatch #6 {IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, blocks: (B:12:0x003a, B:14:0x0040, B:22:0x005f, B:24:0x0070, B:25:0x0072, B:27:0x007f, B:29:0x0084, B:31:0x008a, B:36:0x0096, B:42:0x00a9, B:44:0x00b1, B:57:0x00e2), top: B:112:0x003a }] */
    /* JADX WARN: Code duplicated, block: B:59:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:71:0x0124  */
    /* JADX WARN: Code duplicated, block: B:72:0x0126 A[Catch: IllegalArgumentException -> 0x0201, IndexOutOfBoundsException -> 0x0203, TryCatch #4 {IllegalArgumentException -> 0x0201, IndexOutOfBoundsException -> 0x0203, blocks: (B:89:0x01c2, B:69:0x0119, B:73:0x0139, B:75:0x0146, B:87:0x01bd, B:78:0x0153, B:80:0x0174, B:83:0x0187, B:84:0x01b1, B:72:0x0126, B:66:0x00ff, B:67:0x0116, B:91:0x01f5, B:92:0x0200), top: B:115:0x00d8 }] */
    /* JADX WARN: Code duplicated, block: B:91:0x01f5 A[Catch: IllegalArgumentException -> 0x0201, IndexOutOfBoundsException -> 0x0203, TryCatch #4 {IllegalArgumentException -> 0x0201, IndexOutOfBoundsException -> 0x0203, blocks: (B:89:0x01c2, B:69:0x0119, B:73:0x0139, B:75:0x0146, B:87:0x01bd, B:78:0x0153, B:80:0x0174, B:83:0x0187, B:84:0x01b1, B:72:0x0126, B:66:0x00ff, B:67:0x0116, B:91:0x01f5, B:92:0x0200), top: B:115:0x00d8 }] */
    public static Date parse(String date, ParsePosition pos) throws ParseException {
        char timezoneIndicator;
        String timezoneOffset;
        String timezoneOffset2;
        TimeZone timezone;
        int offset;
        int offset2;
        int offset3;
        char c;
        int offset4;
        int seconds;
        int offset5;
        int parseEndOffset;
        int fraction;
        try {
            int offset6 = pos.getIndex();
            int offset7 = offset6 + 4;
            int year = parseInt(date, offset6, offset7);
            if (checkOffset(date, offset7, '-')) {
                offset7++;
            }
            int offset8 = offset7 + 2;
            int month = parseInt(date, offset7, offset8);
            if (checkOffset(date, offset8, '-')) {
                offset8++;
            }
            int offset9 = offset8 + 2;
            int day = parseInt(date, offset8, offset9);
            int hour = 0;
            int minutes = 0;
            int seconds2 = 0;
            int milliseconds = 0;
            boolean hasT = checkOffset(date, offset9, 'T');
            if (!hasT) {
                try {
                    if (date.length() <= offset9) {
                        Calendar calendar = new GregorianCalendar(year, month - 1, day);
                        calendar.setLenient(false);
                        pos.setIndex(offset9);
                        return calendar.getTime();
                    }
                    if (hasT) {
                        int offset10 = offset9 + 1;
                        offset2 = offset10 + 2;
                        hour = parseInt(date, offset10, offset2);
                        if (checkOffset(date, offset2, ':')) {
                            offset2++;
                        }
                        offset3 = offset2 + 2;
                        minutes = parseInt(date, offset2, offset3);
                        if (checkOffset(date, offset3, ':')) {
                            offset9 = offset3;
                        } else {
                            offset9 = offset3 + 1;
                        }
                        if (date.length() > offset9 && (c = date.charAt(offset9)) != 'Z' && c != '+' && c != '-') {
                            offset4 = offset9 + 2;
                            seconds = parseInt(date, offset9, offset4);
                            if (seconds <= 59 && seconds < 63) {
                                seconds2 = 59;
                            } else {
                                seconds2 = seconds;
                            }
                            if (checkOffset(date, offset4, '.')) {
                                offset9 = offset4;
                            } else {
                                offset5 = offset4 + 1;
                                offset9 = indexOfNonDigit(date, offset5 + 1);
                                parseEndOffset = Math.min(offset9, offset5 + 3);
                                fraction = parseInt(date, offset5, parseEndOffset);
                                switch (parseEndOffset - offset5) {
                                    case 1:
                                        milliseconds = fraction * 100;
                                        break;
                                    case 2:
                                        milliseconds = fraction * 10;
                                        break;
                                    default:
                                        milliseconds = fraction;
                                        break;
                                }
                            }
                        }
                    }
                    try {
                        if (date.length() > offset9) {
                            throw new IllegalArgumentException("No time zone indicator");
                        }
                        timezoneIndicator = date.charAt(offset9);
                        if (timezoneIndicator == 'Z') {
                            timezone = TIMEZONE_UTC;
                            offset = offset9 + 1;
                            month = month;
                        } else if (timezoneIndicator != '+' || timezoneIndicator == '-') {
                            timezoneOffset = date.substring(offset9);
                            if (timezoneOffset.length() >= 5) {
                                timezoneOffset2 = timezoneOffset;
                            } else {
                                timezoneOffset2 = timezoneOffset + "00";
                            }
                            int offset11 = offset9 + timezoneOffset2.length();
                            if (!timezoneOffset2.equals("+0000") || timezoneOffset2.equals("+00:00")) {
                                timezone = TIMEZONE_UTC;
                            } else {
                                String timezoneId = "GMT" + timezoneOffset2;
                                timezone = TimeZone.getTimeZone(timezoneId);
                                String act = timezone.getID();
                                if (act.equals(timezoneId)) {
                                    month = month;
                                    offset11 = offset11;
                                } else {
                                    month = month;
                                    offset11 = offset11;
                                    String cleaned = act.replace(":", "");
                                    if (!cleaned.equals(timezoneId)) {
                                        throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + timezoneId + " given, resolves to " + timezone.getID());
                                    }
                                }
                            }
                            offset = offset11;
                        } else {
                            throw new IndexOutOfBoundsException("Invalid time zone indicator '" + timezoneIndicator + "'");
                        }
                        Calendar calendar2 = new GregorianCalendar(timezone);
                        calendar2.setLenient(false);
                        calendar2.set(1, year);
                        calendar2.set(2, month - 1);
                        calendar2.set(5, day);
                        calendar2.set(11, hour);
                        calendar2.set(12, minutes);
                        calendar2.set(13, seconds2);
                        calendar2.set(14, milliseconds);
                        pos.setIndex(offset);
                        return calendar2.getTime();
                    } catch (IllegalArgumentException e) {
                        e = e;
                    } catch (IndexOutOfBoundsException e2) {
                        e = e2;
                    }
                } catch (IllegalArgumentException e3) {
                    e = e3;
                } catch (IndexOutOfBoundsException e4) {
                    e = e4;
                }
            } else {
                if (hasT) {
                    int offset12 = offset9 + 1;
                    offset2 = offset12 + 2;
                    hour = parseInt(date, offset12, offset2);
                    if (checkOffset(date, offset2, ':')) {
                        offset2++;
                    }
                    offset3 = offset2 + 2;
                    minutes = parseInt(date, offset2, offset3);
                    if (checkOffset(date, offset3, ':')) {
                        offset9 = offset3;
                    } else {
                        offset9 = offset3 + 1;
                    }
                    if (date.length() > offset9) {
                        offset4 = offset9 + 2;
                        seconds = parseInt(date, offset9, offset4);
                        if (seconds <= 59) {
                            seconds2 = seconds;
                        } else {
                            seconds2 = seconds;
                        }
                        if (checkOffset(date, offset4, '.')) {
                            offset9 = offset4;
                        } else {
                            offset5 = offset4 + 1;
                            offset9 = indexOfNonDigit(date, offset5 + 1);
                            parseEndOffset = Math.min(offset9, offset5 + 3);
                            fraction = parseInt(date, offset5, parseEndOffset);
                            switch (parseEndOffset - offset5) {
                                case 1:
                                    milliseconds = fraction * 100;
                                    break;
                                case 2:
                                    milliseconds = fraction * 10;
                                    break;
                                default:
                                    milliseconds = fraction;
                                    break;
                            }
                        }
                    }
                }
                if (date.length() > offset9) {
                    throw new IllegalArgumentException("No time zone indicator");
                }
                timezoneIndicator = date.charAt(offset9);
                if (timezoneIndicator == 'Z') {
                    timezone = TIMEZONE_UTC;
                    offset = offset9 + 1;
                    month = month;
                } else {
                    if (timezoneIndicator != '+') {
                    }
                    timezoneOffset = date.substring(offset9);
                    if (timezoneOffset.length() >= 5) {
                        timezoneOffset2 = timezoneOffset;
                    } else {
                        timezoneOffset2 = timezoneOffset + "00";
                    }
                    int offset13 = offset9 + timezoneOffset2.length();
                    if (!timezoneOffset2.equals("+0000")) {
                    }
                    timezone = TIMEZONE_UTC;
                    offset = offset13;
                }
                Calendar calendar3 = new GregorianCalendar(timezone);
                calendar3.setLenient(false);
                calendar3.set(1, year);
                calendar3.set(2, month - 1);
                calendar3.set(5, day);
                calendar3.set(11, hour);
                calendar3.set(12, minutes);
                calendar3.set(13, seconds2);
                calendar3.set(14, milliseconds);
                pos.setIndex(offset);
                return calendar3.getTime();
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException e5) {
            e = e5;
        }
        String input = date == null ? null : '\"' + date + '\"';
        String msg = e.getMessage();
        if (msg == null || msg.isEmpty()) {
            msg = "(" + e.getClass().getName() + ")";
        }
        ParseException ex = new ParseException("Failed to parse date [" + input + "]: " + msg, pos.getIndex());
        ex.initCause(e);
        throw ex;
    }

    private static boolean checkOffset(String value, int offset, char expected) {
        return offset < value.length() && value.charAt(offset) == expected;
    }

    private static int parseInt(String value, int beginIndex, int endIndex) throws NumberFormatException {
        if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
            throw new NumberFormatException(value);
        }
        int digit = beginIndex;
        int result = 0;
        if (digit < endIndex) {
            int i = digit + 1;
            int digit2 = Character.digit(value.charAt(digit), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
            result = -digit2;
            digit = i;
        }
        while (digit < endIndex) {
            int i2 = digit + 1;
            int digit3 = Character.digit(value.charAt(digit), 10);
            if (digit3 < 0) {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
            result = (result * 10) - digit3;
            digit = i2;
        }
        return -result;
    }

    private static void padInt(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }

    private static int indexOfNonDigit(String string, int offset) {
        for (int i = offset; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c < '0' || c > '9') {
                return i;
            }
        }
        int i2 = string.length();
        return i2;
    }
}
