package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.UIModel.RptMoshtaryGharardadUiModel;
import com.saphamrah.WebService.ServiceResponse.GetVersionResult;

import java.util.ArrayList;

public interface RptMoshtaryGharardadMVP
{

    interface RequiredViewOps
    {
        void onGetMoshtary(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<String> title);
        void onGetMoshtaryGharardad(ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , ArrayList<String> title);
        void onGetKala(ArrayList<RptMoshtaryGharardadUiModel> models);
        void showToast(int resId, int messageType, int duration);
        void onDetailsMoshtary(String nameMoshtary , String codeMoshtary , String shomarehGharardad , String nameSazmanForosh);
        void onFinishActivity();


    }
    interface PresenterOps
    {
        void onConfigurationChanged(RequiredViewOps view);
        void getMoshtary();
        void getGharardadMoshtary(int ccMoshtary);
        void getKala(String ccMoshtaryGharardad , String ccSazmanForosh,int from);

    }


    interface RequiredPresenterOps
    {
        void onGetMoshtary(ArrayList<MoshtaryModel> moshtaryModels , ArrayList<String> title);
        void onGetMoshtaryGharardad(ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , ArrayList<String> title);
        void onGetKala(ArrayList<RptMoshtaryGharardadUiModel> models);
        void onDetailsMoshtary(String nameMoshtary , String codeMoshtary , String shomarehGharardad , String nameSazmanForosh);

        void onError(int resId,boolean isFinish);


    }
    interface ModelOps
    {
        void getMoshtary();
        void getGharardadMoshtary(int ccMoshtary);
        void getKala(String ccMoshtaryGharardad , String ccSazmanForosh,int from);
    }


}
