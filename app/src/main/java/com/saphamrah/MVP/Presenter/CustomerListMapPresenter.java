


package com.saphamrah.MVP.Presenter;

        import android.content.Context;
        import android.text.style.AlignmentSpan;

        import com.saphamrah.BaseMVP.CustomerListMapMVP;
        import com.saphamrah.BaseMVP.RptCustomersListByDistanceMVP;
        import com.saphamrah.MVP.Model.CustomersListMapModel;
        import com.saphamrah.MVP.Model.RptCustomersListByDistanceModel;
        import com.saphamrah.Model.AllMoshtaryForoshandehModel;
        import com.saphamrah.Model.ListMoshtarianModel;
        import com.saphamrah.PubFunc.PubFunc;
        import com.saphamrah.R;
        import com.saphamrah.Utils.Constants;

        import java.lang.ref.WeakReference;
        import java.util.ArrayList;

public class CustomerListMapPresenter implements CustomerListMapMVP.PresenterOps , CustomerListMapMVP.RequiredPresenterOps
{

    private WeakReference<CustomerListMapMVP.RequiredViewOps> mView;
    private CustomerListMapMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public CustomerListMapPresenter(CustomerListMapMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new CustomersListMapModel(this);
    }

    @Override
    public void onConfigurationChanged(CustomerListMapMVP.RequiredViewOps view)
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

    @Override
    public void onFailedGetNewItem(int position, String errorMessage)
    {

            mView.get().onFailedGetCustomerInfo(position , errorMessage);

    }


    @Override
    public void onSuccessfullyGetNewItem(int sumOfItems, int position)
    {
        if (position != sumOfItems - 1)
        {
            mView.get().onSuccessfullyGetNewItemOfInfo(position);
        }
        else
        {
            mView.get().onCompleteGetCustomerInfo();
        }
    }

    @Override
    public void checkSelectedCustomerForGetInfo(ListMoshtarianModel listMoshtarianModel)
    {
        if ( listMoshtarianModel != null)
        {
            mModel.getSelectedCustomerInfo(listMoshtarianModel);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }
}
