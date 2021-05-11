package com.saphamrah.MVP.Presenter.marjoee;

import com.saphamrah.BaseMVP.marjoee.MarjoeeMorediMVP;
import com.saphamrah.MVP.Model.marjoee.MarjoeeMorediModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.MarjoeeMamorPakhshModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MarjoeeMorediPresenter implements MarjoeeMorediMVP.PresenterOps, MarjoeeMorediMVP.RequiredPresenterOps {

    private WeakReference<MarjoeeMorediMVP.RequiredViewOps> mView;
    private MarjoeeMorediMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public MarjoeeMorediPresenter(MarjoeeMorediMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MarjoeeMorediModel(this);
    }

    @Override
    public void onConfigurationChanged(MarjoeeMorediMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals("")) {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void getMarjoeeMoredi(int ccMoshtary) {
        mModel.getMarjoeeMoredi(ccMoshtary);
    }

    @Override
    public void checkTaeidSabtMarjoee(MarjoeeMamorPakhshModel model, int ccMarjoeeMamorPakhsh, int itemCount, int selectedCount, int position ,ArrayList<ElatMarjoeeKalaModel> elatMarjoee) {
       mModel.checkTaeidSabtMarjoee(model  , ccMarjoeeMamorPakhsh , itemCount , selectedCount , position , elatMarjoee);
    }

    @Override
    public void deleteMarjoee(int ccMarjoeeMamorPakhsh , int ccMoshtary) {
        mModel.deleteMarjoee(ccMarjoeeMamorPakhsh , ccMoshtary);
    }

    @Override
    public void getElatMarjoeeMoredi() {
        mModel.getElatMarjoeeMoredi();
    }


    @Override
    public void searchNameKala(String searchWord, ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsAdpater) {
        mModel.searchNameKala(searchWord , marjoeeMamorPakhshModelsAdpater);
    }



    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public void onGetMarjoeeMoredi(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels) {
        mView.get().onGetMarjoeeMoredi(marjoeeMamorPakhshModels);
    }

    @Override
    public void onGetElatMarjoeeMoredi(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, ArrayList<String> elatMarjoeeKalaTitles) {
        mView.get().onGetElatMarjoeeMoredi(elatMarjoeeKalaModels , elatMarjoeeKalaTitles);
    }

    @Override
    public void onTaeidSabtMarjoee(int selectedCount, int position) {
        mView.get().onTaeidSabtMarjoee(selectedCount,position);
    }

    @Override
    public void onGetSearch(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsSearch) {
        mView.get().onGetSearch(marjoeeMamorPakhshModelsSearch);
    }


}
