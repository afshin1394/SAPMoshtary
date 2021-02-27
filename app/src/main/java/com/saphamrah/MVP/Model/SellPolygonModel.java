package com.saphamrah.MVP.Model;


import android.util.Log;

import com.saphamrah.BaseMVP.SellPolygonMVP;
import com.saphamrah.DAO.PolygonForoshSatrDAO;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Arrays;

public class SellPolygonModel implements SellPolygonMVP.ModelOps
{

    private SellPolygonMVP.RequiredPresenterOps mPresenter;

    public SellPolygonModel(SellPolygonMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getCurrentLocation()
    {
        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
        /*Log.d("location" , "access : " + googleLocationProvider.getHasAccess());
        if (!googleLocationProvider.getHasAccess())
        {
            mPresenter.onErrorAccessToLocation();
        }
        else
        {*/
            GeoPoint currentPoint = new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude());
            Log.d("location" , "current : " + googleLocationProvider.getLatitude() + " , " + googleLocationProvider.getLongitude());
            mPresenter.onGetCurrentLocation(currentPoint);
        //}
    }

    @Override
    public void getSellPolygon()
    {
        PolygonForoshSatrDAO polygonForoshSatrDAO = new PolygonForoshSatrDAO(mPresenter.getAppContext());
        ArrayList<String> arrayListccPolygonForosh = polygonForoshSatrDAO.getAllccPolygonForosh();
        ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels = new ArrayList<>();
        String[] polygonColors = mPresenter.getAppContext().getResources().getStringArray(R.array.sellPolygonColors);
        Log.d("SellPolygon","arrayListccPolygonForosh:"+arrayListccPolygonForosh.toString()+ " polygonColors:"+polygonColors.length + ", arrayListccPolygonForosh.size()"+arrayListccPolygonForosh.size());
        if (arrayListccPolygonForosh.size() > 0)
        {
            for (int i = 0 ; i < arrayListccPolygonForosh.size() ; i++)
            {
                polygonForoshSatrModels = new ArrayList<>();
                polygonForoshSatrModels = polygonForoshSatrDAO.getAllByCcPolygonForosh(arrayListccPolygonForosh.get(i));
                if (i < polygonColors.length)
                {
                    mPresenter.onGetSellPolygon(polygonForoshSatrModels , polygonColors[i]);
                }
                else
                {
                    mPresenter.onGetSellPolygon(polygonForoshSatrModels , polygonColors[0]);
                }
            }
        }
        else
        {
            mPresenter.onGetSellPolygon(polygonForoshSatrModels , polygonColors[0]);
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }
}
