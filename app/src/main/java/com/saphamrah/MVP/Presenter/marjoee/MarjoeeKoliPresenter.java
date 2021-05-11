package com.saphamrah.MVP.Presenter.marjoee;

import android.content.Context;

import com.saphamrah.BaseMVP.marjoee.MarjoeekoliMVP;
import com.saphamrah.MVP.Model.marjoee.MarjoeeKoliModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MarjoeeKoliPresenter implements MarjoeekoliMVP.PresenterOps, MarjoeekoliMVP.RequiredPresenterOps {

    private WeakReference<MarjoeekoliMVP.RequiredViewOps> mView;
    private MarjoeekoliMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public MarjoeeKoliPresenter(MarjoeekoliMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MarjoeeKoliModel(this);
    }

    @Override
    public void onConfigurationChanged(MarjoeekoliMVP.RequiredViewOps view) {
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
    public void getMarjoee(long ccDarkhastFaktor) {
        mModel.getMarjoee(ccDarkhastFaktor);
    }

    @Override
    public void getElatMarjoeeKol() {
        mModel.getElatMarjoeeKol();
    }

    @Override
    public void checkTaeidSabtMarjoee(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, int position , int ccDarkhastFaktor) {
        mModel.checkTaeidSabtMarjoee(elatMarjoeeKalaModels , position , ccDarkhastFaktor);
    }

    @Override
    public void checkMarjoeeForInsert(byte[] imageMarjoee) {
        if (imageMarjoee != null && imageMarjoee.length > 0)
        {
            mModel.insertMarjoee(imageMarjoee);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }




    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext() {
        return null;
    }

    @Override
    public void onGetMarjoee(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels) {
        mView.get().onGetMarjoee(kalaDarkhastFaktorSatrModels);
        if (kalaDarkhastFaktorSatrModels.size() == 0)
            mView.get().showToast(R.string.errorGetMarjoee, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetElatMarjoeeKol(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, ArrayList<String> elatAdamDarkhastTitles) {
        mView.get().onGetElatMarjoeeKol(elatMarjoeeKalaModels ,elatAdamDarkhastTitles);
    }

    @Override
    public void onCheckTaeidSabtMarjoee(boolean isHaveImage, boolean isVosol) {
        if (isHaveImage && isVosol){
            mView.get().onCheckTaeidSabtMarjoee(isHaveImage , isVosol);
        } else if (isHaveImage && !isVosol){
            mView.get().showToast(R.string.isValidDataSabtMarjoeeKol, Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        } else if (!isHaveImage && isVosol) {
            mView.get().onCheckTaeidSabtMarjoee(isHaveImage , isVosol);
        } else if (!isHaveImage && !isVosol){
            mView.get().showToast(R.string.isValidDataSabtMarjoeeKol, Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        }

    }

    @Override
    public void onErrorSaveProfileImage() {
        mView.get().showToast(R.string.errorSaveImage , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
    }


}
