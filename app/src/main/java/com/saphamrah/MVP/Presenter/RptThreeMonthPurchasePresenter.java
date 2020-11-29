package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.RptThreeMonthPurchaseMVP;
import com.saphamrah.MVP.Model.RptThreeMonthPurchaseModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RptThreeMonthPurchasePresenter implements RptThreeMonthPurchaseMVP.PresenterOps , RptThreeMonthPurchaseMVP.RequiredPresenterOps
{

    private WeakReference<RptThreeMonthPurchaseMVP.RequiredViewOps> mView;
    private RptThreeMonthPurchaseMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public RptThreeMonthPurchasePresenter(RptThreeMonthPurchaseMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new RptThreeMonthPurchaseModel(this);
    }

    @Override
    public void onConfigurationChanged(RptThreeMonthPurchaseMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getList()
    {
        mModel.getList();
    }

    @Override
    public void getRizFaktor(int ccMoshtary)
    {
        mModel.getRizFaktor(ccMoshtary);
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
    @Override
    public void updateData(String activityNameForLog)
    {
        mModel.updateData(activityNameForLog);
    }

    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetList(ArrayList<Rpt3MonthGetSumModel> moshtaryFaktorModels)
    {
        //bigger than one, because sum of rows is a record even that there is no record
        if (moshtaryFaktorModels.size() > 1)
        {
            int sumTedadFaktor = moshtaryFaktorModels.size();
            long sumMablaghFaktor = 0;
            for (int i = 0 ; i < sumTedadFaktor ; i++){
                sumMablaghFaktor += moshtaryFaktorModels.get(i).getSumMablagh() ;

            }
            mView.get().setListAdapter(moshtaryFaktorModels , sumTedadFaktor , sumMablaghFaktor);
        }
        else
        {
            mView.get().hideFooter();
        }
    }

    @Override
    public  void onGetRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels)
    {
        if (rpt3MonthPurchaseModels.size() > 0)
        {
            int sumTedadFaktor = rpt3MonthPurchaseModels.size();
            long sumMablaghFaktor = 0;
            for (int i = 0 ; i < sumTedadFaktor ; i++){
                sumMablaghFaktor += rpt3MonthPurchaseModels.get(i).getMablaghKhalesFaktor() ;
            }
            mView.get().onGetRizFaktor(rpt3MonthPurchaseModels , sumTedadFaktor , sumMablaghFaktor);
        }
        else
        {
            mView.get().hideFooter();
        }

    }

    @Override
    public void onUpdateData() {
        mView.get().closeLoadingDialog();
    }

    @Override
    public void failedUpdate() {
        mView.get().closeLoadingDialog();
        mView.get().showToast(R.string.errorGetData, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


}
