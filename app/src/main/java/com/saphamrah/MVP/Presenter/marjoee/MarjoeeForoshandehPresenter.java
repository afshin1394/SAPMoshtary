package com.saphamrah.MVP.Presenter.marjoee;

import com.saphamrah.BaseMVP.marjoee.MarjoeeForoshandehMVP;
import com.saphamrah.MVP.Model.marjoee.MarjoeeForoshandehModel;
import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MarjoeeForoshandehPresenter implements MarjoeeForoshandehMVP.PresenterOps, MarjoeeForoshandehMVP.RequiredPresenterOps {

    private WeakReference<MarjoeeForoshandehMVP.RequiredViewOps> mView;
    private MarjoeeForoshandehMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public MarjoeeForoshandehPresenter(MarjoeeForoshandehMVP.RequiredViewOps viewOps) {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MarjoeeForoshandehModel(this);
    }

    @Override
    public void onConfigurationChanged(MarjoeeForoshandehMVP.RequiredViewOps view) {
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
    public void getForoshandehMamorPakhshInfo() {
        mModel.getForoshandehMamorPakhshInfo();
    }

    @Override
    public void checkTaeidSabtMarjoee( ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModelCheckTaeidSabt ,long ccDarkhastFaktor, int itemCount, int selectedCount, int position,boolean insert) {
        mModel.checkTaeidSabtMarjoee(elamMarjoeeForoshandehModelCheckTaeidSabt , ccDarkhastFaktor  , itemCount , selectedCount,position,insert);
    }

//    @Override
//    public void deleteMarjoee(long ccDarkhastFaktor,int ccKalaCode) {
////        mModel.deleteMarjoee(ccDarkhastFaktor, ccKalaCode);
//    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public void onGetMarjoee(ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {
        mView.get().onGetMarjoee(elamMarjoeeForoshandehModels);
        if (elamMarjoeeForoshandehModels.size() == 0)
            mView.get().showToast(R.string.errorGetMarjoee, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetForoshandehMamorPakhshInfo(int noeMasouliat, int noeSabtMarjoee) {
        mView.get().onGetForoshandehMamorPakhshInfo(noeMasouliat, noeSabtMarjoee);
    }

    @Override
    public void onTaeidSabtMarjoee(int ccKardexSatr,int selectedCount, int position) {
        mView.get().onTaeidSabtMarjoee(ccKardexSatr,selectedCount,position);
    }


}
