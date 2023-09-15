package com.video.music.downloader.AdsUtils.Utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static String UTC_DATE_FORMAT = "yyyy-mm-dd hh:MM:ss";
    // List of all date formats that we want to parse.
    // Add your own format here.
    private static List<SimpleDateFormat>
            dateFormats = new ArrayList<SimpleDateFormat>() {
        {
            add(new SimpleDateFormat("yyyy-MM-dd"));
            /*add(new SimpleDateFormat("M/dd/yyyy"));
            add(new SimpleDateFormat("dd.M.yyyy"));
            add(new SimpleDateFormat("M/dd/yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.M.yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.MMM.yyyy"));
            add(new SimpleDateFormat("dd-MMM-yyyy"));*/
        }
    };
    public long MILLISECONDS_IN_SECONDS = 1000;
    public String MONTH_DATE_FORMAT = "MMM dd";
    public String CHALLENGES_END_DATE_FORMAT = "MMM dd, yyyy";

    /**
     * Convert String with various formats into java.util.Date
     *
     * @param input Date as a string
     * @return java.util.Date object if input string is parsed
     * successfully else returns null
     */
    public static Date convertToDate(String input) {
        Date date = null;
        if (null == input) {
            return null;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = format.parse(input);
            } catch (ParseException e) {
                //Shhh.. try other formats
            }
            if (date != null) {
                break;
            }
        }

        return date;
    }

    public static void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        MessageLogger.PrintMsg("startDate : " + startDate);
        MessageLogger.PrintMsg("endDate : " + endDate);
        MessageLogger.PrintMsg("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }

    public static String getAge(String ageStr) {
        if (!TextUtils.isEmpty(ageStr)) {
            try {
                Calendar dob = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                dob.setTime(sdf.parse(ageStr));

                Calendar today = Calendar.getInstance();

                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }

                Integer ageInt = new Integer(age);
                String ageS = ageInt.toString();

                return ageS;
            } catch (Exception e) {
                e.printStackTrace();
                return "0";
            }

        }
        return null;
    }

    public static Date dateFromUTCString(String dateStr) {

        Date date = null;

        SimpleDateFormat format = new SimpleDateFormat(UTC_DATE_FORMAT);

        try {

            date = format.parse(dateStr);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return date;
    }

    public static Date dateFromString(String dateStr, String dateFormat) {

        Date date = null;

        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);

        try {
            date = formater.parse(dateStr.replaceAll("\\.", ""));

        } catch (ParseException e) {

            e.printStackTrace();
            if (dateStr.contains("AM")) {
                dateStr = dateStr.substring(0, dateStr.length() - 2);
                dateStr = dateStr + "a.m.";
            } else if (dateStr.contains("PM")) {
                dateStr = dateStr.substring(0, dateStr.length() - 2);
                dateStr = dateStr + "p.m.";
            }

            try {
                date = formater.parse(dateStr);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        return date;
    }

    public static String getDateFromLong(long milliseconds, String format) {
        SimpleDateFormat formatter = null;
        Calendar calendar = null;
        try {
            formatter = new SimpleDateFormat(format, Locale.US);
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatter.format(calendar.getTime());
    }

    public static String getDateWithShortPrefix(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        format.setTimeZone(TimeZone.getDefault());
        String strDate = format.format(new Date(timestamp));

        if (strDate.endsWith("1") && !strDate.endsWith("11"))
            format = new SimpleDateFormat("d'st' MMM yyyy");
        else if (strDate.endsWith("2") && !strDate.endsWith("12"))
            format = new SimpleDateFormat("d'nd' MMM yyyy");
        else if (strDate.endsWith("3") && !strDate.endsWith("13"))
            format = new SimpleDateFormat("d'rd' MMM yyyy");
        else
            format = new SimpleDateFormat("d'th' MMM yyyy");

        return format.format(new Date(timestamp));

    }

    public static String getDateInDefault(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(timestamp);
        String date = android.text.format.DateFormat.format("dd MMM yyyy", cal).toString();
        cal.getTimeInMillis();
        return date;
    }

    public static String getDateInDefaultForCalender(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(timestamp);
        String date = android.text.format.DateFormat.format("dd-MMM-yyyy", cal).toString();
        cal.getTimeInMillis();
        return date;
    }

    public static Calendar getDateInNewDefaultFormatCalender(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(timestamp);
        String date = android.text.format.DateFormat.format("dd-MM-yyyy", cal).toString();
        cal.getTimeInMillis();
        return cal;
    }

    public static String getTimeInDefaultTimeZone(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(timestamp);
        String time = android.text.format.DateFormat.format("h:mm A", cal).toString();
        cal.getTimeInMillis();
        return time.toUpperCase();
    }

    public static long getMilliSecondsInDefaultTimeZone(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(timestamp);
        return cal.getTimeInMillis();
    }

    public static Date getDayInDefaultTimeZone(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(timestamp);
        return cal.getTime();
    }

    public static String getDateInUTC(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(timestamp);
        String date = android.text.format.DateFormat.format("MMM dd", cal).toString();
        cal.getTimeInMillis();
        return date;
    }

    public static Long getTimeStampInUTC() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        return cal.getTimeInMillis();
    }

    public static String getDateInForCalenderUTC(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(timestamp);
        String date = android.text.format.DateFormat.format("MMM-dd-yyyy", cal).toString();
        cal.getTimeInMillis();
        return date;
    }

    public static String getCommentDate(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(timestamp);
        String date = android.text.format.DateFormat.format("MM/dd/yyyy", cal).toString();
        return date;
    }

    public static String getReceivedDate(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(timestamp);
        String date = android.text.format.DateFormat.format("MM/dd", cal).toString();
        return date;
    }

    public static String getTimeInUTC(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(timestamp);
        String time = android.text.format.DateFormat.format("h:mm A", cal).toString();
        return time.toUpperCase();
    }

    public static String getTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(timestamp);
        String time = android.text.format.DateFormat.format("h:mm A", cal).toString();
        return time.toUpperCase();
    }

    public static Date getDay(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(timestamp);
        return cal.getTime();
    }

    public static int getDateDiffString(long TimeDiff) {
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (TimeDiff) / oneDay;

        if (delta > 0) {
            return Integer.parseInt(delta + "");
        }
        if (delta < 0) {
            return -1;
        }

        return 0;
    }

    public static int getTimeDiffString(long TimeDiff) {
        long oneMin = 1000 * 60 * 60;
        long delta = (TimeDiff) / oneMin;

        if (delta > 0) {
            return Integer.parseInt(delta + "");
        }
        return 0;
    }

    public static int getMinDiffString(long TimeDiff) {
        long oneMin = 1000 * 60;
        long delta = TimeDiff / oneMin;

        if (delta > 0) {
            return Integer.parseInt(delta + "");
        }
        return 0;
    }

    public static String getAge(int year, int month, int day) {


//Now access the values as below
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        int m = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
        int d = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);

		/*  if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
		              age--;
		                   }*/

		/*if ((month < today.get(Calendar.MONTH))
		|| ((month == today.get(Calendar.MONTH)) && (day < today
		        .get(Calendar.DAY_OF_MONTH)))) {
		    --age;
		    --m;
		    --d;
		   }*/

        if (month > today
                .get(Calendar.MONTH)) {
            --age;

        } else if (month == today
                .get(Calendar.MONTH)) {
            if (day > today
                    .get(Calendar.DAY_OF_MONTH)) {
                --age;
            }
        }


        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();


        return ageS;
    }

    public static int getAgeFromDOBString(String date) {

        int age = 1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date date1 = sdf.parse(date);
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date1);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }

    public static Calendar getDOBFromAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeZone(TimeZone.getDefault());
        Calendar calculatedDate = Calendar.getInstance();
        calculatedDate.setTimeZone(TimeZone.getDefault());
        Calendar today = Calendar.getInstance();
        today.setTimeZone(TimeZone.getDefault());
        dob.set(year, month, day);

        today.add(Calendar.DAY_OF_MONTH, -day);
        today.add(Calendar.MONTH, -month);
        today.add(Calendar.YEAR, -year);

        int d = today.get(Calendar.DAY_OF_MONTH);
        int m = today.get(Calendar.MONTH);
        int y = today.get(Calendar.YEAR);

        calculatedDate.set(y, m, d);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        String formatted = format1.format(today.getTime());
        return today;
    }

    public static long getMillisecondsFromDateString(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date date = null;
        try {
            date = sdf.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getMillisecondsFromManualDateString(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(dob);
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getCurrentTimeInDefault() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());

//		MessageLogger.debug("Local : " + calendar.getTimeInMillis());
//
//		SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		//Current Date Time in Local Timezone
//		MessageLogger.PrintMsg("Current Date and Time in local timezone: " + localDateFormat.format( new Date(calendar.getTimeInMillis())));
//
//		SimpleDateFormat gmtDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//
//		//Current Date Time in GMT
//		MessageLogger.PrintMsg("Current Date and Time in GMT time zone: " + gmtDateFormat.format(new Date(calendar.getTimeInMillis())));


        return calendar.getTimeInMillis();
    }

    public static long getEndOfDay(Date date) {

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimeZone tz = TimeZone.getDefault();
        calendar.setTimeZone(tz);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public static long getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimeZone tz = TimeZone.getDefault();
        calendar.setTimeZone(tz);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getStartOfDayInDefaultTimeZone() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getEndOfDayInDefaultTimeZone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public static boolean isBetweenDates(Date dateOne, Date dateTwo, Date dateSelected) {
        if (dateSelected.equals(dateOne) || dateSelected.equals(dateTwo)) {
            return true;
        }
        if (dateSelected.after(dateOne) && dateSelected.before(dateTwo)) {
            return true;
        }
        return false;
    }

    public static String Req_Date_Req(String time, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            str = dateFromStringForAMPM(time, inputPattern, outputPattern);
            e.printStackTrace();
        }
        return str;
    }

    public static String dateFromStringForAMPM(String dateStr1, String inputPattern, String outputPattern) {
        String dateStr = dateStr1;
        Date date = null;
        String str = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            if (dateStr.contains("AM")) {
                dateStr = dateStr.substring(0, dateStr.length() - 2);
                dateStr = dateStr + "a.m.";
            } else if (dateStr.contains("PM")) {
                dateStr = dateStr.substring(0, dateStr.length() - 2);
                dateStr = dateStr + "p.m.";
            }
            date = inputFormat.parse(dateStr);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            str = dateStr1;
        }
        return str;
    }

    public Date dateFromUTCLong(long time) {
        return null;

    }

    public String fromDate(Date date, String timezone, SimpleDateFormat dateFormat) {

        String dateStr = "";

        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        dateStr = dateFormat.format(date);

        return dateStr;
    }

    public String dateWithClientTimezone(Date date, SimpleDateFormat dateFormat) {

        String dateStr = "";

        dateFormat.setTimeZone(TimeZone.getDefault());
        dateStr = dateFormat.format(date);

        return dateStr;
    }

    public SimpleDateFormat getDateFormatFromString(String dateFormatStr) {

        return new SimpleDateFormat(dateFormatStr);
    }

/*
    public long getCurrentTimestamp()
    {
        return  System.currentTimeMillis();
    }*/

    public long getDateFromDateString(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:MM:ss");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date.getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public String getDateDiff(long milliSeconds) {
        String diff = null;
        long diffDays = 0, diffMinutes = 0, diffHours = 0;

        long commentTime = (milliSeconds) * 1000L;
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String strDate = format.format(new Date(commentTime));

        DateFormat todayDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        todayDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String strTodayDate = todayDateFormat.format(new Date());

        Date d1 = null;
        Date d2 = null;

        try {
            DateFormat convertDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            d1 = convertDateFormat.parse(strDate);

            d2 = convertDateFormat.parse(strTodayDate);

        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        commentTime = d1.getTime();
        long currentTime = d2.getTime();

        try {
            long diffmill = currentTime - commentTime;
            diffDays = diffmill / (24 * 60 * 60 * 1000);
            diffMinutes = diffmill / (60 * 1000) % 60;
            diffHours = diffmill / (60 * 60 * 1000) % 24;

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (diffDays > 30) {
            Date date = new Date(commentTime);

            DateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
            diff = format1.format(date);
            return diff;
        } else if (diffDays > 1 && diffDays < 30) {
            diff = String.valueOf(diffDays);
            return diff + " days ago";
        } else if (diffDays == 1) {
            diff = String.valueOf(diffDays);
            return diff + " day ago";
        } else if (diffHours > 1) {
            diff = String.valueOf(diffHours);
            return diff + " hours ago";
        } else if (diffHours == 1) {
            diff = String.valueOf(diffHours);
            return diff + " hour ago";
        } else if (diffMinutes > 1) {
            diff = String.valueOf(diffMinutes);
            return diff + " minutes ago";
        } else {
            diff = "Just Now";
            return diff;
        }

    }

    @SuppressLint("SimpleDateFormat")
    public String getDateDiffForJudge(long milliSeconds) {
        String diff = null;

        long diffmill = 0;
        long commentTime = (milliSeconds) * 1000L;
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String strDate = format.format(new Date(commentTime));

        DateFormat todayDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        todayDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String strTodayDate = todayDateFormat.format(new Date());

        Date d1 = null;
        Date d2 = null;

        try {
            DateFormat convertDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            d1 = convertDateFormat.parse(strDate);

            d2 = convertDateFormat.parse(strTodayDate);

        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        commentTime = d1.getTime();
        long currentTime = d2.getTime();

        try {
            diffmill = currentTime - commentTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (diffmill < 0) {
            return "00:00:00";
        } else {
            diff = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(diffmill), TimeUnit.MILLISECONDS.toMinutes(diffmill) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffmill)), TimeUnit.MILLISECONDS.toSeconds(diffmill) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diffmill)));
            return diff;
        }

    }

    public String getDateDiffCounter(long commentTime) {
        String diff = null;
        long diffDays = 0;
        long currentTime = new Date().getTime();

        long diffmill = commentTime - currentTime;
        diffDays = diffmill / (24 * 60 * 60 * 1000);

        if (diffDays > 0) {
            diff = String.valueOf(diffDays);
            if (diffDays == 1) {
                diff = diff + " Day";
            } else {
                diff = diff + " Days";
            }
            return diff;
        } else if (diffmill > 0) {
            return null;
        } else if (diffmill < 0) {
            return "00:00:00";
        } else {
            return null;
        }

    }

    public String getDateDiffCounter(String commentTime) {
        String diff = null;
        int diffDays = 0;
        if (commentTime != null && !commentTime.equals("")) {
            String[] separated = commentTime.split(":");

            diffDays = Integer.parseInt(separated[0]);
            int diffHours = Integer.parseInt(separated[1]);
            int diffMin = Integer.parseInt(separated[2]);
            int diffSec = Integer.parseInt(separated[3]);

            long diffmill = ((diffHours * 60 + diffMin) * 60 + diffSec) * 1000;

            if (diffDays > 0) {
                diff = String.valueOf(diffDays);
                if (diffDays == 1) {
                    diff = diff + " Day";
                } else {
                    diff = diff + " Days";
                }
                return diff;
            } else if (diffmill > 0) {
                return null;
            } else {
                return "00:00:00";
            }
        } else {
            return "00:00:00";
        }

    }

    public String getDateDownCounter(long commentTime) {
        String diff = null;
        long diffDays = 0, diffHours = 0;
        long currentTime = new Date().getTime();

        long diffmill = currentTime - commentTime;
        diffDays = diffmill / (24 * 60 * 60 * 1000);
        diffHours = diffmill / (60 * 60 * 1000) % 24;
        if (diffDays > 0) {
            diff = String.valueOf(diffDays);
            return diff + " Days";
        } else if (diffHours > 0 && diffHours <= 24) {
            return null;
        } else {
            return "0 Days";
        }

    }

    public String getTimeInMin(long milliSeconds) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)), TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
    }

    public long getTimeInMillies(String timeRemaining) {
        if (timeRemaining != null && !timeRemaining.equals("")) {
            String[] separated = timeRemaining.split(":");

            int hours = Integer.parseInt(separated[1]);
            int min = Integer.parseInt(separated[2]);
            int sec = Integer.parseInt(separated[3]);

            long millies = ((hours * 60 + min) * 60 + sec) * 1000;
            return millies;
        } else {
            return 0;
        }
    }

    public long getDateInmilliesLocal(String date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date d1 = null;
        long startDateMillisec = 0;
        try {
            d1 = format.parse(date);

            startDateMillisec = d1.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return startDateMillisec;
    }

    public long getDateInmillies(String date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        TimeZone obj = TimeZone.getTimeZone("UTC");
        format.setTimeZone(obj);
        Date d1 = null;
        long startDateMillisec = 0;
        try {
            d1 = format.parse(date);

            startDateMillisec = d1.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return startDateMillisec;
    }

    public String getDateWithPrefix(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        format.setTimeZone(TimeZone.getDefault());
        String strDate = format.format(new Date(timestamp));

        if (strDate.endsWith("1") && !strDate.endsWith("11"))
            format = new SimpleDateFormat("d'st' MMMM yyyy");
        else if (strDate.endsWith("2") && !strDate.endsWith("12"))
            format = new SimpleDateFormat("d'nd' MMMM yyyy");
        else if (strDate.endsWith("3") && !strDate.endsWith("13"))
            format = new SimpleDateFormat("d'rd' MMMM yyyy");
        else
            format = new SimpleDateFormat("d'th' MMMM yyyy");

        return format.format(new Date(timestamp));

    }

    public String getDateWithShortPrefixs(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        format.setTimeZone(TimeZone.getDefault());
        String strDate = format.format(new Date(timestamp));

        if (strDate.endsWith("1") && !strDate.endsWith("11"))
            format = new SimpleDateFormat("d'st' MMM yy");
        else if (strDate.endsWith("2") && !strDate.endsWith("12"))
            format = new SimpleDateFormat("d'nd' MMM yy");
        else if (strDate.endsWith("3") && !strDate.endsWith("13"))
            format = new SimpleDateFormat("d'rd' MMM yy");
        else
            format = new SimpleDateFormat("d'th' MMM yy");

        return format.format(new Date(timestamp));

    }

    public String getDateWithPrefixForCreateChallenge(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        format.setTimeZone(TimeZone.getDefault());
        String strDate = format.format(new Date(timestamp));

        if (strDate.endsWith("1") && !strDate.endsWith("11"))
            format = new SimpleDateFormat("MMM d'st' ");
        else if (strDate.endsWith("2") && !strDate.endsWith("12"))
            format = new SimpleDateFormat("MMM d'nd' ");
        else if (strDate.endsWith("3") && !strDate.endsWith("13"))
            format = new SimpleDateFormat("MMM d'rd' ");
        else
            // format = new SimpleDateFormat("MMM d'th' yyyy");
            format = new SimpleDateFormat("MMM d'th' ");
        return format.format(new Date(timestamp));

    }

    @SuppressLint("SimpleDateFormat")
    public boolean getDateDiff(String firstDate, String secondDate) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

        Date d1 = null;
        Date d2 = null;
        long diffDays = 0;
        try {
            d1 = format.parse(firstDate);
            d2 = format.parse(secondDate);


            long diff = d2.getTime() - d1.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
            // birthDateMillisec = d1.getTime() / 1000;


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (diffDays > 6573L) {
            return true;
        } else {
            return false;
        }

    }

    public String getChallengesEndDate(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat(CHALLENGES_END_DATE_FORMAT);
        format.setTimeZone(TimeZone.getDefault());
        String strDate = format.format(new Date(timestamp));

        return strDate;

    }

    public List<String> getBetweenYears(long oldTimestamp) {
        List<String> listYears = new ArrayList<String>();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
        int currentYear = Integer.parseInt(simpleDateformat.format(new Date()));
        int registeredYear = Integer.parseInt(simpleDateformat.format(new Date(oldTimestamp)));
        while (registeredYear <= currentYear) {
            listYears.add("" + currentYear);
            --currentYear;
        }
        return listYears;
    }

    public static String getDateFromSeconds(long milliseconds) {
        //We are converting millisec to date here.
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(milliseconds);
            SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            return dateFormat.format(cal.getTime());
        } catch (Exception e) {
            Global.sout("Date format error", e.getLocalizedMessage());
        }
        return "";
    }

}