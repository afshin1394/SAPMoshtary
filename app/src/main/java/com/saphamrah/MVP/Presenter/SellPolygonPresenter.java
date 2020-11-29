package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.SellPolygonMVP;
import com.saphamrah.MVP.Model.SellPolygonModel;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import org.osmdroid.util.GeoPoint;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SellPolygonPresenter implements SellPolygonMVP.PresenterOps , SellPolygonMVP.RequiredPresenterOps
{

    private WeakReference<SellPolygonMVP.RequiredViewOps> mView;
    private SellPolygonMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public SellPolygonPresenter(SellPolygonMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new SellPolygonModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(SellPolygonMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getCurrentLocation()
    {
        mModel.getCurrentLocation();
    }

    @Override
    public void getSellPolygon()
    {
        mModel.getSellPolygon();
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
    public void onGetCurrentLocation(GeoPoint currentPoint)
    {
        mView.get().onGetCurrentLocation(currentPoint);
    }

    @Override
    public void onGetSellPolygon(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels , String polygonColor)
    {
        if (polygonForoshSatrModels == null || polygonForoshSatrModels.size() == 0)
        {
            Log.d("sellPolygon" , "polygon size : " + polygonForoshSatrModels.size());
            mView.get().showToast(R.string.errorGetSellPolygon, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mView.get().drawSellPolygon(polygonForoshSatrModels , polygonColor);
        }
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }

    @Override
    public void onErrorAccessToLocation()
    {
        mView.get().showToast(R.string.errorAccessToLocation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
