package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.MainFirstFragmentMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.ForoshandehMoshtaryDAO;
import com.saphamrah.DAO.RptForoshDAO;
import com.saphamrah.DAO.RptHadafForoshDAO;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.WeatherDataModel;
import com.saphamrah.Model.WeatherModel;
import com.saphamrah.Model.WindModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.WeatherShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceOwghat;
import com.saphamrah.WebService.APIServiceWeather;
import com.saphamrah.WebService.ApiClientOwghat;
import com.saphamrah.WebService.ServiceResponse.ApiClientWeather;
import com.saphamrah.WebService.ServiceResponse.OwghatResult;
import com.saphamrah.WebService.ServiceResponse.WeatherResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFirstFragmentModel implements MainFirstFragmentMVP.ModelOps
{

    private MainFirstFragmentMVP.RequiredPresenterOps mPresenter;

    public MainFirstFragmentModel(MainFirstFragmentMVP.RequiredPresenterOps presenterOps)
    {
        mPresenter = presenterOps;
    }

    @Override
    public void getNoeForoshandehMamorPakhsh()
    {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne());
        mPresenter.onGetNoeForoshandehMamorPakhsh(noeMasouliat);
    }

    @Override
    public void getOwghat(final String currentTime)
    {
        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider(mPresenter.getAppContext());
        APIServiceOwghat apiService = ApiClientOwghat.getClient().create(APIServiceOwghat.class);
        Call<OwghatResult> call = apiService.getOwghatByLatLong(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude());
        call.enqueue(new Callback<OwghatResult>() {
            @Override
            public void onResponse(Call<OwghatResult> call, Response<OwghatResult> response)
            {
                if (response.isSuccessful())
                {
                    OwghatResult result = response.body();
                    if (result != null)
                    {
                        if (result.getOk())
                        {
                            mPresenter.onGetOwghat(result.getResult() , currentTime);
                        }
                        else
                        {
                            mPresenter.onGetOwghat(null , currentTime);
                        }
                    }
                    else
                    {
                        mPresenter.onGetOwghat(null , currentTime);
                    }
                }
                else
                {
                    mPresenter.onGetOwghat(null , currentTime);
                }
            }

            @Override
            public void onFailure(Call<OwghatResult> call, Throwable t)
            {
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), t.toString(), "MainModel", "", "getOwghat", "onFailure");
                mPresenter.onGetOwghat(null , currentTime);
            }
        });
    }

    @Override
    public void getForoshandehMamorPakhshName()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        String name = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh().getFullName();
        if (name != null)
        {
            mPresenter.onGetForoshandehMamorPakhshName(name);
        }
        else
        {
            mPresenter.onGetForoshandehMamorPakhshName("");
        }
    }

    @Override
    public void getCurrentDate()
    {
        int[] currentDate = new PubFunc().new DateUtils().splittedTodayPersianDate(mPresenter.getAppContext());
        if (currentDate != null)
        {
            String monthName = new PubFunc().new DateUtils().getMonthName(mPresenter.getAppContext() , currentDate[1]);
            mPresenter.onGetCurrentDate(String.format("%1$s %2$s %3$s" , currentDate[2] , monthName , currentDate[0]));
        }
    }

    @Override
    public void getWeather(final int type)
    {
        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider(mPresenter.getAppContext());
        APIServiceWeather apiServiceWeather = ApiClientWeather.getClient().create(APIServiceWeather.class);
        Call<WeatherResult> call = apiServiceWeather.getWeatherInfoByCoordinate(Constants.API_KEY_OPEN_WEATHER_MAP(), googleLocationProvider.getLatitude(), googleLocationProvider.getLongitude(), "fa", "metric");
        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response)
            {
                if (response.isSuccessful())
                {
                    WeatherResult result = response.body();
                    if (result != null)
                    {
                        if (result.getCod() == 200)
                        {
                            if (result.getWeather() != null && result.getWeather().size() > 0 && result.getWeatherData() != null && result.getWind() != null)
                            {
                                WeatherShared weatherShared = new WeatherShared(mPresenter.getAppContext());
                                weatherShared.removeAll();
                                weatherShared.putString(weatherShared.MAIN_ID() , result.getWeather().get(0).getId().toString());
                                weatherShared.putString(weatherShared.DESC() , result.getWeather().get(0).getDescription());
                                weatherShared.putString(weatherShared.ICON() , result.getWeather().get(0).getIcon());
                                weatherShared.putString(weatherShared.TEMPERATURE() , result.getWeatherData().getTemp().toString());
                                weatherShared.putString(weatherShared.MAX_TEMPERATURE() , result.getWeatherData().getTempMax().toString());
                                weatherShared.putString(weatherShared.MIN_TEMPERATURE() , result.getWeatherData().getTempMin().toString());
                                weatherShared.putString(weatherShared.HUMIDITY() , result.getWeatherData().getHumidity().toString());
                                weatherShared.putString(weatherShared.WIND_SPEED() , result.getWind().getSpeed().toString());
                                mPresenter.onGetWeather(type , result.getWeather().get(0), result.getWeatherData(), result.getWind());
                            }
                            else
                            {
                                getWeatherFromShared(type);
                            }
                        }
                        else
                        {
                            getWeatherFromShared(type);
                        }
                    }
                    else
                    {
                        getWeatherFromShared(type);
                    }
                }
                else
                {
                    getWeatherFromShared(type);
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), t.toString(), "MainModel", "", "getOwghat", "onFailure");
                getWeatherFromShared(type);
            }
        });
    }


    private void getWeatherFromShared(int type)
    {
        try
        {
            WeatherShared weatherShared = new WeatherShared(mPresenter.getAppContext());
            // WeatherModel
            int weatherId = Integer.parseInt(weatherShared.getString(weatherShared.MAIN_ID() , "0"));
            String weatherDesc = weatherShared.getString(weatherShared.DESC() , "");
            String weatherIcon = weatherShared.getString(weatherShared.ICON() , "");
            WeatherModel weatherModel = new WeatherModel();
            weatherModel.setId(weatherId);
            weatherModel.setDescription(weatherDesc);
            weatherModel.setIcon(weatherIcon);

            // WeatherDataModel
            double temperature = Double.parseDouble(weatherShared.getString(weatherShared.TEMPERATURE() , "0.0"));
            double maxTemperature = Double.parseDouble(weatherShared.getString(weatherShared.MAX_TEMPERATURE() , "0.0"));
            double minTemperature = Double.parseDouble(weatherShared.getString(weatherShared.MIN_TEMPERATURE() , "0.0"));
            int humidity = Integer.parseInt(weatherShared.getString(weatherShared.HUMIDITY() , "0"));
            WeatherDataModel weatherDataModel = new WeatherDataModel();
            weatherDataModel.setTemp(temperature);
            weatherDataModel.setTempMax(maxTemperature);
            weatherDataModel.setTempMin(minTemperature);
            weatherDataModel.setHumidity(humidity);

            //WindModel
            double windSpeed = Double.parseDouble(weatherShared.getString(weatherShared.WIND_SPEED() , "0.0"));
            WindModel windModel = new WindModel();
            windModel.setSpeed(windSpeed);

            if (temperature == 0.0)
            {
                mPresenter.onFailedGetWeather();
            }
            else
            {
                mPresenter.onGetWeather(type, weatherModel, weatherDataModel, windModel);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MainFirstFragmentModel", "", "getWeatherFromShared" , "");
            mPresenter.onFailedGetWeather();
        }
    }



    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType , message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {}


}
