package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.ForoshandehRouteMapMVP;
import com.saphamrah.MVP.Model.ForoshandehRouteMapModel;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerAddressModel;
import com.saphamrah.Utils.Constants;

import org.osmdroid.util.GeoPoint;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ForoshandehRouteMapPresenter implements ForoshandehRouteMapMVP.PresenterOps , ForoshandehRouteMapMVP.RequiredPresenterOps
{


    private WeakReference<ForoshandehRouteMapMVP.RequiredViewOps> mView;
    private ForoshandehRouteMapMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public ForoshandehRouteMapPresenter(ForoshandehRouteMapMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new ForoshandehRouteMapModel(this);
    }

    @Override
    public void onConfigurationChanged(ForoshandehRouteMapMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getCustomerInfo()
    {
        mModel.getCustomerInfo();
    }

    @Override
    public void getGpsDatas()
    {
        mModel.getGpsDatas();
    }

    @Override
    public void updateGPSData()
    {
        mModel.updateGPSData();
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
    public void onGetCustomerInfo(ArrayList<CustomerAddressModel> customerAddressModels)
    {
        if (customerAddressModels.size() > 0)
        {
            mView.get().onGetCustomerInfo(customerAddressModels);
        }
        else
        {
            mView.get().showToast(R.string.notFoundTodayCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetGpsDatas(ArrayList<GPSDataModel> gpsDataModels)
    {
        if (gpsDataModels.size() > 0)
        {
            PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
            ArrayList<GeoPoint> geoPoints = new ArrayList<>();
            for (GPSDataModel model : gpsDataModels)
            {
                geoPoints.add(new GeoPoint(model.getLatitude() , model.getLongitude()));
            }
            geoPoints.add(new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude()));
            mView.get().onGetGpsDatas(geoPoints);
        }
        else
        {
            mView.get().showToast(R.string.notFoundLocationChangeSetting, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onSuccessUpdateGPSData(ArrayList<GPSDataModel> gpsDataModels)
    {
        onGetGpsDatas(gpsDataModels);
        mView.get().closeLoading();
        mView.get().showToast(R.string.updateSuccessed, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedUpdateGPSData()
    {
        mView.get().closeLoading();
        mView.get().showToast(R.string.errorUpdateDatabase, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
