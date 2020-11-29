package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.MainFirstFragmentMVP;
import com.saphamrah.MVP.Model.MainFirstFragmentModel;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.Model.OwghatModel;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.WeatherDataModel;
import com.saphamrah.Model.WeatherModel;
import com.saphamrah.Model.WindModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainFirstFragmentPresenter implements MainFirstFragmentMVP.PresenterOps , MainFirstFragmentMVP.RequiredPresenterOps
{

    private WeakReference<MainFirstFragmentMVP.RequiredViewOps> mView;
    private MainFirstFragmentMVP.ModelOps mModel;
    private boolean mIsChangingConfig;
    private int noeMasouliatForoshandehMamorPakhsh;

    public MainFirstFragmentPresenter(MainFirstFragmentMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MainFirstFragmentModel(this);
    }

    @Override
    public void onConfigurationChanged(MainFirstFragmentMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getNoeForoshandehMamorPakhsh()
    {
        mModel.getNoeForoshandehMamorPakhsh();
    }

    @Override
    public void checkOwghat(String currentTime)
    {
        mModel.getOwghat(currentTime);
    }

    @Override
    public void getForoshandehMamorPakhshName()
    {
        mModel.getForoshandehMamorPakhshName();
    }





    @Override
    public void getCurrentDate()
    {
        mModel.getCurrentDate();
    }

    @Override
    public void getWeather(int type)
    {
        mModel.getWeather(type);
    }



    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetNoeForoshandehMamorPakhsh(int noeMasouliat)
    {
        noeMasouliatForoshandehMamorPakhsh = noeMasouliat;
    }

    @Override
    public void onGetOwghat(OwghatModel owghatModel , String currentDate)
    {
        if (owghatModel == null)
        {
            mView.get().onFailedGetOwghat();
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.TIME_FORMAT());
            try
            {
                Calendar calendar = Calendar.getInstance();
                Date currentTime = sdf.parse(calendar.get(Calendar.HOUR_OF_DAY ) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
                //Date currentTime = sdf.parse(currentDate);


                Date sunrise = sdf.parse(owghatModel.getToluAftab());
                calendar.setTime(sunrise);
                calendar.add(Calendar.HOUR_OF_DAY , 2);
                Date twoHourAfterSunrise = sdf.parse(calendar.get(Calendar.HOUR_OF_DAY ) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));

                Date midday = sdf.parse(owghatModel.getAzanZohr());

                calendar.setTime(midday);
                calendar.add(Calendar.HOUR_OF_DAY , 2);
                Date twoHourAfterMidday = sdf.parse(calendar.get(Calendar.HOUR_OF_DAY ) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));

                Date sunset = sdf.parse(owghatModel.getAzanMaghreb());
                /*calendar.setTime(sunset);
                calendar.add(Calendar.HOUR_OF_DAY , -1);
                Date oneHourBeforeSunset = sdf.parse(calendar.get(Calendar.HOUR_OF_DAY ) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));*/


                calendar.setTime(sunset);
                calendar.add(Calendar.HOUR_OF_DAY , 1);
                Date oneHourAfterSunset = sdf.parse(calendar.get(Calendar.HOUR_OF_DAY ) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));


                Log.d("time" , "current : " + currentTime);
                Log.d("time" , "tolo aftab : " + sunrise);
                Log.d("time" , "two hour after tolo aftab : " + twoHourAfterSunrise);
                //Log.d("time" , "OneHourBeforeSunset : " + oneHourBeforeSunset);
                Log.d("time" , "sunset : " + sunset);
                Log.d("time" , "oneHourAfterSunset : " + oneHourAfterSunset);


                int stringId = R.string.error;
                int drawableId = R.drawable.midday;

                if (currentTime.compareTo(sunrise)==0 || (currentTime.after(sunrise) && currentTime.before(twoHourAfterSunrise)))
                {
                    stringId = R.string.goodMorning;
                    drawableId = R.drawable.sunrise;
                    Log.d("time" , "in sunrise range");
                }
                else if (currentTime.compareTo(twoHourAfterSunrise)==0 || (currentTime.after(twoHourAfterSunrise) && currentTime.before(midday)))
                {
                    stringId = R.string.goodDay;
                    drawableId = R.drawable.midday;
                }
                else if (currentTime.compareTo(midday)==0 || (currentTime.after(midday) && currentTime.before(twoHourAfterMidday)))
                {
                    stringId = R.string.goodAfternoon;
                    drawableId = R.drawable.midday_one;
                }
                else if (currentTime.compareTo(twoHourAfterMidday)==0 ||  (currentTime.after(twoHourAfterMidday) && currentTime.before(sunset)))
                {
                    long diff = sunset.getTime() - twoHourAfterMidday.getTime();
                    int range = (int)(diff / 2);

                    calendar.setTime(twoHourAfterMidday);
                    calendar.add(Calendar.MILLISECOND , range);
                    Date middayTwo = sdf.parse(calendar.get(Calendar.HOUR_OF_DAY ) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));

                    Log.d("time" , "midday Two : " + middayTwo);

                    /*calendar.setTime(middayTwo);
                    calendar.add(Calendar.MILLISECOND , range);
                    Date evening = sdf.parse(calendar.get(Calendar.HOUR_OF_DAY ) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));

                    Log.d("time" , "evening : " + evening);*/

                    if (currentTime.compareTo(twoHourAfterMidday)==0 || (currentTime.after(twoHourAfterMidday) && currentTime.before(middayTwo)))
                    {
                        Log.d("time" , "midday Two : " + middayTwo);
                        stringId = R.string.goodAfternoon;
                        drawableId = R.drawable.midday_two;
                    }
                    else
                    {
                        stringId = R.string.goodEvening;
                        drawableId = R.drawable.sunset;
                    }
                }
                else if (currentTime.compareTo(sunset)==0 || (currentTime.after(sunset) && currentTime.before(oneHourAfterSunset)))
                {
                    stringId = R.string.goodNight;
                    drawableId = R.drawable.sunset_one;
                    Log.d("time" , "in sunset1 range");
                }
                else //if (currentTime.compareTo(oneHourAfterSunset)==0 || currentTime.after(oneHourAfterSunset))
                {
                    stringId = R.string.goodNight;
                    drawableId = R.drawable.night;
                    Log.d("time" , "in night range");
                }

                mView.get().onShowOwghat(drawableId , stringId , owghatModel);

                /*long diff = currentTime.getTime() - sunrise.getTime();

                if (diff > 0) // after sunrise
                {
                    int days = (int) (diff / (1000*60*60*24));
                    int hourDiff = (int) ((diff - (1000*60*60*24*days)) / (1000*60*60));
                    Log.d("time" , "day : " + days + " hour : " + hourDiff);
                    if (hourDiff < 2)
                    {
                        mView.get().onShowOwghat(R.drawable.sunrise , R.string.goodMorning);
                    }
                    else
                    {

                    }
                }
                else // before sunrise
                {

                }*/
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), MainPresenter.class.getSimpleName(), "", "onGetOwghat", "");
                mView.get().onFailedGetOwghat();
            }
        }
    }

    @Override
    public void onGetForoshandehMamorPakhshName(String name)
    {
        mView.get().onGetForoshandehMamorPakhshName(name);
    }

    @Override
    public void onGetCurrentDate(String currentDate)
    {
        if (currentDate != null)
        {
            mView.get().onGetCurrentDate(currentDate);
        }
    }

    @Override
    public void onGetWeather(int type , WeatherModel weatherModel, WeatherDataModel weatherDataModel, WindModel windModel)
    {
        if (type == 1)
        {
            mView.get().onGetWeather(weatherModel , weatherDataModel , windModel);
        }
        else if(type == 2)
        {
            mView.get().showAlertWeatherDetails(weatherModel , weatherDataModel , windModel);
        }
    }

    @Override
    public void onFailedGetWeather()
    {
        mView.get().onFailedGetWeather();
    }



}
