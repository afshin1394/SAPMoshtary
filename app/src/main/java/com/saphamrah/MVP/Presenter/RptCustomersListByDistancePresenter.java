package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptCustomersListByDistanceMVP;
import com.saphamrah.MVP.Model.RptCustomersListByDistanceModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptCustomersListByDistancePresenter implements RptCustomersListByDistanceMVP.PresenterOps , RptCustomersListByDistanceMVP.RequiredPresenterOps
{

    private WeakReference<RptCustomersListByDistanceMVP.RequiredViewOps> mView;
    private RptCustomersListByDistanceMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptCustomersListByDistancePresenter(RptCustomersListByDistanceMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptCustomersListByDistanceModel(this);
    }

    @Override
    public void onConfigurationChanged(RptCustomersListByDistanceMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getRadiusConfig()
    {
        mModel.getRadiusConfig();
    }

    @Override
    public void getCustomerList(String radius , String latitude , String longitude)
    {
        if (radius != null && !radius.equals(""))
        {
            try
            {
                float fltRadius = Float.parseFloat(radius);
                fltRadius = fltRadius / 1000;
                mModel.getCustomerList(String.valueOf(fltRadius) , latitude , longitude);
            }
            catch (Exception exception)
            {
                mModel.setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RptCustomersListByDistancePresenter", "", "getCustomerList", "");
            }
        }
        else
        {
            mView.get().showToast(R.string.errorRadius, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
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
    public void onGetRadiusConfig(String maxRadius, String stepRadius)
    {
        try
        {
            int intMaxRadius = 0;
            float fltStepRadius = 0;
            if (maxRadius != null && !maxRadius.equals(""))
            {
                intMaxRadius = Integer.parseInt(maxRadius);
            }
            if (stepRadius != null && !stepRadius.equals(""))
            {
                fltStepRadius = Float.parseFloat(stepRadius);
            }
            if (intMaxRadius == 0 || fltStepRadius == 0)
            {
                mView.get().onFailedGetConfig();
            }
            else
            {
                ArrayList<String> arrayListRadiusItems = new ArrayList<>();
                int stepInMeter = (int)(intMaxRadius * fltStepRadius * 1000);
                int item = stepInMeter;
                arrayListRadiusItems.add(String.valueOf(item));
                while (item < intMaxRadius * 1000)
                {
                    item += stepInMeter;
                    arrayListRadiusItems.add(String.valueOf(item));
                }
                mView.get().onGetRadiusConfig(arrayListRadiusItems);
            }
        }
        catch (Exception exception)
        {
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RptCustomersListByDistancePresenter", "", "onGetRadiusConfig", "");
            mView.get().onFailedGetConfig();
        }
    }

    @Override
    public void onGetCustomerList(ArrayList<ListMoshtarianModel> customerList , String radius)
    {
        mView.get().onGetCustomerList(customerList , radius);
    }

    @Override
    public void onErrorGetCustomerList()
    {
        mView.get().closeLoadingAlert();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }



}
