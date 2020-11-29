package com.saphamrah.MVP.Presenter;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.saphamrah.BaseMVP.RptMarjoeeMVP;
import com.saphamrah.MVP.Model.RptMarjoeeModel;
import com.saphamrah.Model.RptMarjoeeKalaModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class RptMarjoeePresenter implements RptMarjoeeMVP.PresenterOps , RptMarjoeeMVP.RequiredPresenterOps
{


    private WeakReference<RptMarjoeeMVP.RequiredViewOps> mView;
    private RptMarjoeeMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptMarjoeePresenter(RptMarjoeeMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptMarjoeeModel(this);
    }

    @Override
    public void onConfigurationChanged(RptMarjoeeMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getMarjoeeList()
    {
        mModel.getMarjoeeList();
    }

    @Override
    public void updateMarjoeeList()
    {
        mModel.updateMarjoeeList();
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
    public void onGetMarjoeeList(ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels)
    {
        ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels1Headers = new ArrayList<>();
        HashMap hashMap = new HashMap();
        long sumCost = 0;
        int sumCount = 0;

        if (rptMarjoeeKalaModels.size() > 0)
        {
            ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModelsChild = new ArrayList<>();
            /*rptMarjoeeKalaModels1Headers.add(rptMarjoeeKalaModels.get(0));
            sumCost += rptMarjoeeKalaModels.get(0).getFee();
            sumCount += rptMarjoeeKalaModels.get(0).getTedad3();
            for (int i=1 ; i<rptMarjoeeKalaModels.size() ; i++)
            {
                if (rptMarjoeeKalaModels.get(i).getRadif() == -1)
                {
                    sumCost += rptMarjoeeKalaModels.get(i).getFee();
                    sumCount += rptMarjoeeKalaModels.get(i).getTedad3();
                    hashMap.put(rptMarjoeeKalaModels1Headers.get(rptMarjoeeKalaModels1Headers.size() - 1) , rptMarjoeeKalaModelsChild);
                    rptMarjoeeKalaModelsChild = new ArrayList<>();
                    rptMarjoeeKalaModels1Headers.add(rptMarjoeeKalaModels.get(i));
                }
                else
                {
                    rptMarjoeeKalaModelsChild.add(rptMarjoeeKalaModels.get(i));
                }
            }
            hashMap.put(rptMarjoeeKalaModels1Headers.get(rptMarjoeeKalaModels1Headers.size() - 1) , rptMarjoeeKalaModelsChild);*/




            for (RptMarjoeeKalaModel marjoee : rptMarjoeeKalaModels)
            {
                if (marjoee.getRadif() == -1)
                {
                    sumCost += marjoee.getFee();
                    sumCount += marjoee.getTedad3();
                    hashMap.put(marjoee , rptMarjoeeKalaModelsChild);
                    rptMarjoeeKalaModelsChild = new ArrayList<>();
                    rptMarjoeeKalaModels1Headers.add(marjoee);
                }
                else
                {
                    rptMarjoeeKalaModelsChild.add(marjoee);
                }
            }
            mView.get().onGetMarjoeeList(rptMarjoeeKalaModels1Headers , hashMap , sumCost , sumCount);
        }
        else
        {
            mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
            mView.get().hideFooterAndPrint();
        }
    }

    @Override
    public void onErrorUpdateMarjoee()
    {
        mView.get().closeLoading();
        mView.get().showToast(R.string.errorUpdateMarjoeeList, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessUpdateMarjoee()
    {
        mView.get().closeLoading();
        mView.get().showToast(R.string.updateMarjoeeList, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }
}
