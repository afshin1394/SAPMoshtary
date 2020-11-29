package com.saphamrah.MVP.Presenter;

import com.saphamrah.BaseMVP.TizerMVP;
import com.saphamrah.MVP.Model.TizersModel;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TizerPresenter implements TizerMVP.PresenterOps, TizerMVP.RequiredPresenterOps {

    private WeakReference<TizerMVP.RequiredViewOps> mView;
    private TizerMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public TizerPresenter(TizerMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new TizersModel(this);
    }

    @Override
    public void onConfigurationChanged(TizerMVP.RequiredViewOps view) {
        this.mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////


    @Override
    public void getListFolder() {
        mModel.getListFolder();
    }

    @Override
    public void updateData(String activityNameForLog) {
        mModel.updateData(activityNameForLog);
    }

    @Override
    public void getListFile(String folderName) {
        mModel.getListFile(folderName);
    }


    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals("")) {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public void onGetListFolder(ArrayList<String> tizerResultModels) {
        mView.get().closeLoadingDialog();
        mView.get().setListFolder(tizerResultModels);

    }

    @Override
    public void onGetListFile(ArrayList<TizerModel> tizerModels) {
        mView.get().setListFile(tizerModels);

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
