package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.RouteMVP;
import com.saphamrah.MVP.Model.RouteModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Valhalla.Location;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class RoutePresenter implements RouteMVP.PresenterOps , RouteMVP.RequiredPresenterOps
{

    private WeakReference<RouteMVP.RequiredViewOps> mView;
    private RouteMVP.ModelOps mModel;


    public RoutePresenter(RouteMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        this.mModel = new RouteModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(RouteMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getTripInfo(String ccMoshtary, int tripTime, double tripDistance)
    {
        try
        {
            //calculate trip distance
            int resIdUnit = R.string.kilometer;
            if (tripDistance < 1.000D)
            {
                resIdUnit = R.string.meter;
                tripDistance *= 1000;
            }
            mView.get().showTripDistance(String.valueOf(tripDistance) , resIdUnit);

            //calculate trip time
            int hour = tripTime / 3600;
            int min = (tripTime % 3600) / 60;

            //calculate time that arrival to destination
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.SHORT_TIME_FORMAT());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY , hour);
            calendar.add(Calendar.MINUTE , min);
            mView.get().showTripTime(hour, min, sdf.format(calendar.getTime()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            int intCcCustomer = Integer.parseInt(ccMoshtary);
            if (intCcCustomer > 0)
            {
                mModel.getCustomerName(intCcCustomer);
            }
            else
            {
                mView.get().hideCustomerName();
            }
        }
        catch (Exception e)
        {
            mView.get().hideCustomerName();
            checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "RoutePresenter", "", "getCustomerName", "");														  
        }
    }
	
	@Override
    public void route(Location startLocation, Location destinationLocation, String routeId)
    {
        Log.d("Route" , "start location presenter : " + startLocation.getLat() + " , " + startLocation.getLon());
        Log.d("Route" , "des location presenter: " + destinationLocation.getLat() + " , " + destinationLocation.getLon());
        if (startLocation != null && destinationLocation != null)
        {
            mModel.route(startLocation, destinationLocation, routeId);
        }
        else
        {
            mView.get().showToast(R.string.errorGetLocation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
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
    public void onGetCustomerName(MoshtaryModel moshtaryModel)
    {
        if (moshtaryModel != null && moshtaryModel.getNameMoshtary() != null && !moshtaryModel.getNameMoshtary().trim().equals(""))
        {
            mView.get().showCustomerName(moshtaryModel.getNameMoshtary());
        }
        else
        {
            mView.get().hideCustomerName();
        }
    }
    @Override
    public void onGetRoute(String routeResult)
    {
        if (routeResult == null || routeResult.trim().equals(""))
        {
            mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mView.get().onGetRoute(routeResult);
        }
    }

    @Override
    public void onError(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
