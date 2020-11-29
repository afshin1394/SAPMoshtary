package com.saphamrah.PubFunc;

import android.content.Context;
import android.text.format.DateFormat;

import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils
{

    private static final String CLASS_NAME = "DateUtils";

    /**
     *تاریخ امروز به شمسی و در قالب عددی
     * <br>
     * yyyymmdd
     * @param context
     * @return -1 if exception occurs
     */
    public int todayDate(Context context)
    {
        try
        {
            //String today = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(Calendar.getInstance().getTime());
            String today = new SimpleDateFormat("").format(Calendar.getInstance().getTime());
            String[] seperatedDate = today.split("-");
            int year = Integer.parseInt(seperatedDate[0]);
            int month = Integer.parseInt(seperatedDate[1]);
            int day = Integer.parseInt(seperatedDate[2]);

            DateConverter dateConverter = new DateConverter();
            dateConverter.gregorianToPersian(year , month , day);

            String persianYear = String.valueOf(dateConverter.getYear());
            String persianMonth = String.valueOf(dateConverter.getMonth());
            String persianDay = String.valueOf(dateConverter.getDay());

            persianMonth = (persianMonth.length() == 1) ? "0" + persianMonth : persianMonth;
            persianDay = (persianDay.length() == 1) ? "0" + persianDay : persianDay;

            int date = Integer.parseInt(persianYear + persianMonth + persianDay);
            return date;
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "todayDate", "");
            return -1;
        }
    }


    /**
     * تاریخ امروز به شمسی و با اسلش
     * <br>
     * yyyy/mm/dd
     * @param context
     * @return "" if exception occurs
     */
    public String todayDateWithSlash(Context context)
    {
        try
        {
            String today = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(Calendar.getInstance().getTime());
            String[] seperatedDate = today.split("-");
            int year = Integer.parseInt(seperatedDate[0]);
            int month = Integer.parseInt(seperatedDate[1]);
            int day = Integer.parseInt(seperatedDate[2]);

            DateConverter dateConverter = new DateConverter();
            dateConverter.gregorianToPersian(year , month , day);

            String persianYear = String.valueOf(dateConverter.getYear());
            String persianMonth = String.valueOf(dateConverter.getMonth());
            String persianDay = String.valueOf(dateConverter.getDay());

            persianMonth = (persianMonth.length() == 1) ? "0" + persianMonth : persianMonth;
            persianDay = (persianDay.length() == 1) ? "0" + persianDay : persianDay;

            return String.format("%1$s/%2$s/%3$s" , persianYear , persianMonth , persianDay);
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "todayDate", "");
            return "";
        }
    }


    /**
     * today persian date
     * @param context
     * @return [0] = year , [1] = month , [2] = day OR return null if exception occurs
     */
    public int[] splittedTodayPersianDate(Context context)
    {
        try
        {
            String today = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(Calendar.getInstance().getTime());
            String[] seperatedDate = today.split("-");
            int year = Integer.parseInt(seperatedDate[0]);
            int month = Integer.parseInt(seperatedDate[1]);
            int day = Integer.parseInt(seperatedDate[2]);

            DateConverter dateConverter = new DateConverter();
            dateConverter.gregorianToPersian(year , month , day);

            return new int[]{dateConverter.getYear() , dateConverter.getMonth() , dateConverter.getDay()};
        }
        catch (Exception e)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), e.toString(), CLASS_NAME, "", "splittedTodayPersianDate", "");
            return null;
        }
    }


    /**
     * convert gregorian date to persian date
     * @param date must be in this format : yyyy-MM-dd'T'HH:mm:ss
     * @return persian date time (format : yyyy/MM/dd - HH:mm:ss)
     */
    public String gregorianToPersianDateTime(Date date)
    {
        String gregorianDay = (String) DateFormat.format("dd" , date);
        String gregorianMonth = (String) DateFormat.format("MM" , date);
        String gregorianYear = (String) DateFormat.format("yyyy", date);
        String time = (String) DateFormat.format("HH:mm:ss" , date);

        int year = Integer.parseInt(gregorianYear);
        int month = Integer.parseInt(gregorianMonth);
        int day = Integer.parseInt(gregorianDay);

        DateConverter dateConverter = new DateConverter();
        dateConverter.gregorianToPersian(year , month , day);

        return String.format("%1$s/%2$s/%3$s - %4$s" , dateConverter.getYear() , dateConverter.getMonth() , dateConverter.getDay() , time);
    }


    /**
     * تاریخ و زمان الان به میلادی و در قالب
     * <br>
     * yyyy/MM/dd kk:mm:ss
     * @return
     */
    public String todayDateGregorian()
    {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.NORMAL_DATE_TIME_FORMAT());
        return simpleDateFormat.format(today);
    }


    public Date todayGregorian()
    {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        try
        {
            return simpleDateFormat.parse(simpleDateFormat.format(today));
        }
        catch (Exception e)
        {
            return new Date();
        }
    }


    /**
     *تاریخ الان بدون زمان و با اسلش به فرمت زیر
     * yyyy/MM/dd
     * @return
     */
    public String todayDateGregorianWithSlash()
    {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());
        return simpleDateFormat.format(today);
    }


    /**
     * تبدیل تاریخ شمسی به میلادی همراه با اسلش
     * yyyy/(m)m/(d)d
     * @param persianDate تاریخ شمسی با اسلش
     * @return تاریخ میلادی با اسلش
     */
    public String persianWithSlashToGregorianSlash(String persianDate)
    {
        String gregorianDate = "";
        String[] splittedDate = persianDate.split("/");
        int year = Integer.parseInt(splittedDate[0]);
        int month = Integer.parseInt(splittedDate[1]);
        int date = Integer.parseInt(splittedDate[2]);

        DateConverter dateConverter = new DateConverter();
        dateConverter.persianToGregorian(year , month , date);
        String gregorianYear = String.valueOf(dateConverter.getYear());
        String gregorianMonth = String.valueOf(dateConverter.getMonth());
        String gregorianDay = String.valueOf(dateConverter.getDay());
        gregorianDate = gregorianYear + "/" + gregorianMonth + "/" + gregorianDay;

        return gregorianDate;
    }


    /**
     * تبدیل تاریخ میلادی به شمسی همراه با اسلشgregorianWithSlashToPersianSlash
     * yyyy/(m)m/(d)d
     * @param gregorianDate تاریخ میلادی با اسلش
     * @return تاریخ شمسی با اسلش
     */
    public String gregorianWithSlashToPersianSlash(String gregorianDate)
    {
        String persianDate = "";
        String[] splittedDate = gregorianDate.split("/");
        int year = Integer.parseInt(splittedDate[0]);
        int month = Integer.parseInt(splittedDate[1]);
        int date = Integer.parseInt(splittedDate[2]);

        DateConverter dateConverter = new DateConverter();
        dateConverter.gregorianToPersian(year , month , date);
        String persianYear = String.valueOf(dateConverter.getYear());
        String persianMonth = String.valueOf(dateConverter.getMonth());
        String persianDay = String.valueOf(dateConverter.getDay());

        persianMonth = persianMonth.trim().length() == 1 ? "0" + persianMonth : persianMonth;
        persianDay = persianDay.trim().length() == 1 ? "0" + persianDay : persianDay;

        persianDate = persianYear + "/" + persianMonth + "/" + persianDay;

        return persianDate;
    }


    /**
     * تبدیل تاریخ میلادی ذارای دش به شمسی همراه با اسلشgregorianWithDashToPersianSlash
     * yyyy/(m)m/(d)d
     * @param gregorianDate تاریخ میلادی با اسلش
     * @return تاریخ شمسی با اسلش
     */
    public String gregorianWithDashToPersianSlash(String gregorianDate)
    {
        String persianDate = "";
        String[] splittedDate = gregorianDate.split("-");
        int year = Integer.parseInt(splittedDate[0]);
        int month = Integer.parseInt(splittedDate[1]);
        int date = Integer.parseInt(splittedDate[2]);

        DateConverter dateConverter = new DateConverter();
        dateConverter.gregorianToPersian(year , month , date);
        String persianYear = String.valueOf(dateConverter.getYear());
        String persianMonth = String.valueOf(dateConverter.getMonth());
        String persianDay = String.valueOf(dateConverter.getDay());

        persianMonth = persianMonth.trim().length() == 1 ? "0" + persianMonth : persianMonth;
        persianDay = persianDay.trim().length() == 1 ? "0" + persianDay : persianDay;

        persianDate = persianYear + "/" + persianMonth + "/" + persianDay;

        return persianDate;
    }
    /**
     *
     * @param strDate تاریخ با فرمت yyyy-mm-dd'T'hh:mm:ss
     * @return تبدیل شده تاریخ وارد شده به کلاس Date و اگر خطایی بدهد تاریخ فعلی برگردانده میشود
     */
    public static Date convertStringDateToDateClass(String strDate)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            return sdf.parse(strDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * زمان الان با فرمت
     * <br>
     * HH:mm:ss
     * @return
     */
    public String getCurrentTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT());
        return simpleDateFormat.format(new Date());
    }


    /**
     * تبدیل عدد ماه به نام ماه شمسی
     * @param context
     * @param monthId عدد ماه بین 1 تا 12
     * @return
     */
    public String getMonthName(Context context , int monthId)
    {
        switch (monthId)
        {
            case 1:
                return context.getString(R.string.farvardin);
            case 2:
                return context.getString(R.string.ordibehesht);
            case 3:
                return context.getString(R.string.khordad);
            case 4:
                return context.getString(R.string.tir);
            case 5:
                return context.getString(R.string.mordad);
            case 6:
                return context.getString(R.string.shahrivar);
            case 7:
                return context.getString(R.string.mehr);
            case 8:
                return context.getString(R.string.aban);
            case 9:
                return context.getString(R.string.azar);
            case 10:
                return context.getString(R.string.dey);
            case 11:
                return context.getString(R.string.bahman);
            case 12:
                return context.getString(R.string.esfand);
            default:
                return String.valueOf(monthId);
        }
    }

    public static long getDateDiffAsDay(Date dateStart , Date dateEnd)
    {
        long diff = dateEnd.getTime() - dateStart.getTime();
        return TimeUnit.MILLISECONDS.toDays(diff);
    }

    public Date addDay(Date date , int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH , days);
        return calendar.getTime();
    }

}
