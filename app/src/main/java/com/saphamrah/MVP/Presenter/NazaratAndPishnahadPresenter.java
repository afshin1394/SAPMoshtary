package com.saphamrah.MVP.Presenter;

import com.saphamrah.BaseMVP.NazaratAndPishnahadMVP;
import com.saphamrah.MVP.Model.NazaratAndPishnahadModel;
import com.saphamrah.Model.NoePishnahadModel;
import com.saphamrah.Model.SuggestModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class NazaratAndPishnahadPresenter implements NazaratAndPishnahadMVP.PresenterOps , NazaratAndPishnahadMVP.RequiredPresenterOps
{
    private WeakReference<NazaratAndPishnahadMVP.RequiredViewOps> mView;
    private NazaratAndPishnahadMVP.ModelOps mModel;

    public NazaratAndPishnahadPresenter(NazaratAndPishnahadMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new NazaratAndPishnahadModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void insertPishnahad(int ccNoePishnahad, String description,String descriptionPishnehad,int ccMoshtary) {
        mModel.insertPishnahad(ccNoePishnahad ,description,descriptionPishnehad,ccMoshtary);
    }

    @Override
    public void deleteSuggest(int ccSuggest,int ccMoshtary) {
        mModel.deleteSuggest(ccSuggest,ccMoshtary);
    }

    @Override
    public void getNoePishnahad(int noePishnahad) {
        mModel.getNoePishnahad(noePishnahad);
    }

    @Override
    public void getSuggest(int ccMoshtary) {
        mModel.getSuggest(ccMoshtary);
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public void onSuccessInsert() {
        mView.get().showToast(R.string.successSaveData, Constants.SUCCESS_MESSAGE(),Constants.DURATION_LONG());
    }

    @Override
    public void onErrorInsert() {
        mView.get().showToast(R.string.errorSaveData,Constants.FAILED_MESSAGE(),Constants.DURATION_LONG());
    }

    @Override
    public void onGetNoePishnahadat(ArrayList<NoePishnahadModel> noePishnahadModels,ArrayList<String> noePishnahadTitles ) {
        mView.get().onGetNoePishnahadat(noePishnahadModels,noePishnahadTitles);
    }

    @Override
    public void onGetSuggest(ArrayList<SuggestModel> suggestModels) {
        mView.get().onGetSuggest(suggestModels);
    }

    @Override
    public void onSuccessDeleteSuggest() {
        mView.get().showToast(R.string.successDeleteSuggest, Constants.SUCCESS_MESSAGE(),Constants.DURATION_LONG());
    }

    @Override
    public void onErrorDeleteSuggest() {
        mView.get().showToast(R.string.errorDeleteSuggest,Constants.FAILED_MESSAGE(),Constants.DURATION_LONG());
    }


}
