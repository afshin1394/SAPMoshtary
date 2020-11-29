package com.saphamrah.PubFunc;

import android.content.Context;

import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest
{

    private int intTodayDateJalaliExpected = 13980928;
    private String strTodayDateJalaliExpected = "1398/09/28";
    private String strTodayDateGregorianExpected = "2019/12/19";
    private int[] splittedTodayJalaliExpected = new int[]{1398 , 9 , 28};
    private Date currentDate; //this use for pass as parameter
    private DateUtils dateUtils;

    // mock objects
    @Mock
    private Context context;


    @Before
    public void initialize()
    {
        context = mock(Context.class);
        dateUtils = new DateUtils();

        currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR , 2019);
        cal.set(Calendar.MONTH , 12);
        cal.set(Calendar.DAY_OF_MONTH , 19);
        cal.set(Calendar.HOUR_OF_DAY , 9);
        cal.set(Calendar.MINUTE , 50);
        cal.set(Calendar.SECOND , 20);
        currentDate = cal.getTime();
    }


    private Date getSpecificDate(int year, int month, int day, int hour, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR , year);
        calendar.set(Calendar.MONTH , month);
        calendar.set(Calendar.DAY_OF_MONTH , day);
        calendar.set(Calendar.HOUR_OF_DAY , hour);
        calendar.set(Calendar.MINUTE , minute);
        calendar.set(Calendar.SECOND , second);
        return calendar.getTime();
    }


    /*@Test
    public void todayDate()
    {
        assertEquals(intTodayDateJalaliExpected , dateUtils.todayDate(context));
    }*/


    @Test
    public void todayDateWithSlash()
    {
        String returnedData = dateUtils.todayDateWithSlash(context);
        assertEquals(strTodayDateJalaliExpected , returnedData);
    }

    @Test
    public void splittedTodayPersianDate()
    {
        assertArrayEquals(splittedTodayJalaliExpected , dateUtils.splittedTodayPersianDate(context));
    }

    @Test
    public void gregorianToPersianDateTime()
    {

    }

    @Test
    public void todayDateGregorian()
    {
        // برای چک کردن تاریخ نمی توان از assertEqual استفاده کرد زیرا حتی اگر یک میلی ثانیه بین دو تاریخی که مقایسه می کنیم، اختلاف وجود داشته باشد، تست با خطا مواجه می شود.
        // برای همین باید یک بازه به عنوان مقدار اختلاف مجاز مشخص کنیم و از تابع assertTrue استفاده کنیم.
        try
        {
            String returnedDate = dateUtils.todayDateGregorian();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.NORMAL_DATE_TIME_FORMAT());
            Date date = sdf.parse(returnedDate);
            assertTrue(Math.abs(date.getTime() - new Date().getTime()) < 1000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void todayGregorian()
    {
        // در متد todayGregorian از کلاس DateUtils ، تاریخ امروز و زمان الان را به فرمت مورد نیاز تبدیل می کنیم که این فرمت شامل '-' برای جداسازی تاریخ و ':' برای جداسازی زمان می باشد.
        // با بررسی وجود این دو کاراکتر در مقدار بازگشتی از متد، می توانیم مطمئن شویم که تاریخ و زمان حال به درستی به فرمت موردنظر تبدیل شده یا خیر
        // نیازی به بررسی اختلاف زمانی وجود ندارد زیرا این متد تاریخ و زمان حال را بر میگرداند.
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        String strToday = sdf.format(dateUtils.todayGregorian());
        assertTrue(strToday != null && strToday.length() > 0 && strToday.contains("-") && strToday.contains(":"));
    }

    @Test
    public void todayDateGregorianWithSlash()
    {
        assertEquals(strTodayDateGregorianExpected , dateUtils.todayDateGregorianWithSlash());
    }

    @Test
    public void persianWithSlashToGregorianSlash()
    {
        String persianDate1 = "1398/09/28";
        String persianDate2 = "1398/9/28";
        String persianDate3 = "1398/9/8";
        String persianDate4 = "1398/10/8";
        String persianDate5 = "1398108";
        String persianDate6 = "";
        String persianDate7 = null;

        String expectedGregorianDate1 = "2019/12/19";
        String expectedGregorianDate2 = "2019/12/19";
        String expectedGregorianDate3 = "2019/11/29";
        String expectedGregorianDate4 = "2019/12/29";
        String expectedGregorianDate5 = "20191229";
        String expectedGregorianDate6 = "";
        String expectedGregorianDate7 = null;

        assertEquals(expectedGregorianDate1 , dateUtils.persianWithSlashToGregorianSlash(persianDate1));
        assertEquals(expectedGregorianDate2 , dateUtils.persianWithSlashToGregorianSlash(persianDate2));
        assertEquals(expectedGregorianDate3 , dateUtils.persianWithSlashToGregorianSlash(persianDate3));
        assertEquals(expectedGregorianDate4 , dateUtils.persianWithSlashToGregorianSlash(persianDate4));
        assertEquals(expectedGregorianDate5 , dateUtils.persianWithSlashToGregorianSlash(persianDate5));
        assertEquals(expectedGregorianDate6 , dateUtils.persianWithSlashToGregorianSlash(persianDate6));
        assertEquals(expectedGregorianDate7 , dateUtils.persianWithSlashToGregorianSlash(persianDate7));
    }

    @Test
    public void gregorianWithSlashToPersianSlash()
    {
        String gregorianDate1 = "1398/09/28";
        String gregorianDate2 = "1398/9/28";
        String gregorianDate3 = "1398/9/8";
        String gregorianDate4 = "1398/10/8";
        String gregorianDate5 = "1398108";
        String gregorianDate6 = "";
        String gregorianDate7 = null;

        String expectedPersianDate1 = "2019/12/19";
        String expectedPersianDate2 = "2019/12/19";
        String expectedPersianDate3 = "2019/11/29";
        String expectedPersianDate4 = "2019/12/29";
        String expectedPersianDate5 = "20191229";
        String expectedPersianDate6 = "";
        String expectedPersianDate7 = null;

        assertEquals(expectedPersianDate1 , dateUtils.persianWithSlashToGregorianSlash(gregorianDate1));
        assertEquals(expectedPersianDate2 , dateUtils.persianWithSlashToGregorianSlash(gregorianDate2));
        assertEquals(expectedPersianDate3 , dateUtils.persianWithSlashToGregorianSlash(gregorianDate3));
        assertEquals(expectedPersianDate4 , dateUtils.persianWithSlashToGregorianSlash(gregorianDate4));
        assertEquals(expectedPersianDate5 , dateUtils.persianWithSlashToGregorianSlash(gregorianDate5));
        assertEquals(expectedPersianDate6 , dateUtils.persianWithSlashToGregorianSlash(gregorianDate6));
        assertEquals(expectedPersianDate7 , dateUtils.persianWithSlashToGregorianSlash(gregorianDate7));
    }

    @Test
    public void getCurrentTime()
    {
        String currentTime = dateUtils.getCurrentTime();
        assertTrue(currentTime != null && currentTime.length() == 8 && currentTime.contains(":"));
    }

    
    @Test
    public void getMonthName()
    {
        final String FARVARDIN = "فروردین";
        final String ORDIBEHESHT = "اردیبهشت";
        final String KHORDAD = "خرداد";
        final String TIR = "تیر";
        final String MORDAD = "مرداد";
        final String SHAHRIVAR = "شهریور";
        final String MEHR = "مهر";
        final String ABAN = "آبان";
        final String AZAR = "آذر";
        final String DEY = "دی";
        final String BAHMAN = "بهمن";
        final String ESFAND = "اسفند";

        when(context.getString(R.string.farvardin)).thenReturn(FARVARDIN);
        when(context.getString(R.string.ordibehesht)).thenReturn(ORDIBEHESHT);
        when(context.getString(R.string.khordad)).thenReturn(KHORDAD);
        when(context.getString(R.string.tir)).thenReturn(TIR);
        when(context.getString(R.string.mordad)).thenReturn(MORDAD);
        when(context.getString(R.string.shahrivar)).thenReturn(SHAHRIVAR);
        when(context.getString(R.string.mehr)).thenReturn(MEHR);
        when(context.getString(R.string.aban)).thenReturn(ABAN);
        when(context.getString(R.string.azar)).thenReturn(AZAR);
        when(context.getString(R.string.dey)).thenReturn(DEY);
        when(context.getString(R.string.bahman)).thenReturn(BAHMAN);
        when(context.getString(R.string.esfand)).thenReturn(ESFAND);

        for (int monthId = 1 ; monthId <= 12 ; monthId++)
        {
            String monthName = dateUtils.getMonthName(context , monthId);
            String expectedMonthName = "";
            switch (monthId)
            {
                case 1:
                    expectedMonthName = "فروردین";
                    break;
                case 2:
                    expectedMonthName = "اردیبهشت";
                    break;
                case 3:
                    expectedMonthName = "خرداد";
                    break;
                case 4:
                    expectedMonthName = "تیر";
                    break;
                case 5:
                    expectedMonthName = "مرداد";
                    break;
                case 6:
                    expectedMonthName = "شهریور";
                    break;
                case 7:
                    expectedMonthName = "مهر";
                    break;
                case 8:
                    expectedMonthName = "آبان";
                    break;
                case 9:
                    expectedMonthName = "آذر";
                    break;
                case 10:
                    expectedMonthName = "دی";
                    break;
                case 11:
                    expectedMonthName = "بهمن";
                    break;
                case 12:
                    expectedMonthName = "اسفند";
                    break;
            }
            assertEquals(expectedMonthName , monthName);
        }
    }

    @Test
    public void getDateDiffAsDay()
    {
        assertEquals(0, dateUtils.getDateDiffAsDay(
                getSpecificDate(2020, 9, 20, 17, 5, 5),
                getSpecificDate(2020, 9, 21, 0, 0, 0)));



        assertEquals(2 , dateUtils.getDateDiffAsDay(
                        getSpecificDate(2019, 9, 20, 17, 30, 10),
                        getSpecificDate(2019, 9, 22, 17, 45, 40)));

        assertEquals(0 , dateUtils.getDateDiffAsDay(
                getSpecificDate(2019, 9, 20, 0, 0, 1),
                getSpecificDate(2019, 9, 20, 23, 59, 59)));

        assertEquals(1 , dateUtils.getDateDiffAsDay(
                getSpecificDate(2019, 9, 19, 0, 0, 1),
                getSpecificDate(2019, 9, 20, 23, 59, 59)));

        assertEquals(3 , dateUtils.getDateDiffAsDay(
                getSpecificDate(2019, 12, 29, 0, 0, 1),
                getSpecificDate(2020, 1, 1, 23, 59, 59)));

        assertEquals(3 , dateUtils.getDateDiffAsDay(null, getSpecificDate(2020, 1, 1, 23, 59, 59)));
        assertEquals(3 , dateUtils.getDateDiffAsDay(getSpecificDate(2019, 12, 29, 0, 0, 1), null));
        assertEquals(3 , dateUtils.getDateDiffAsDay(null, null));
    }


    @Test
    public void addDay()
    {
        assertEquals(getSpecificDate(2019, 12, 22, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2019, 12, 21, 17, 20, 25) , 1));

        assertEquals(getSpecificDate(2020, 1, 1, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2019, 12, 29, 17, 20, 25) , 3));

        assertEquals(getSpecificDate(2020, 1, 28, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2019, 12, 29, 17, 20, 25) , 30));

        assertEquals(getSpecificDate(2020, 1, 29, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2019, 12, 29, 17, 20, 25) , 31));

        assertEquals(getSpecificDate(2020, 1, 30, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2019, 12, 29, 17, 20, 25) , 32));

        assertEquals(getSpecificDate(2019, 12, 28, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2019, 12, 29, 17, 20, 25) , -1));

        assertEquals(getSpecificDate(2019, 12, 28, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2020, 1, 1, 17, 20, 25) , -4));

        assertEquals(getSpecificDate(2019, 11, 30, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2019, 12, 29, 17, 20, 25) , -30));

        assertEquals(getSpecificDate(2019, 12, 29, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2020, 1, 29, 17, 20, 25) , -31));

        assertEquals(getSpecificDate(2019, 12, 28, 17, 20, 25),
                dateUtils.addDay(getSpecificDate(2020, 1, 29, 17, 20, 25) , -32));
    }


}