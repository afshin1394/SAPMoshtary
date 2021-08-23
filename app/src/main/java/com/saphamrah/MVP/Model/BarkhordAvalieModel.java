package com.saphamrah.MVP.Model;

import android.content.ContentValues;

import com.samiei.central.exceptionHandling.AsyncUtils;
import com.saphamrah.BaseMVP.BarkhordAvalieMVP;
import com.saphamrah.DAO.BarkhordForoshandehBaMoshtaryDAO;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Repository.BarkhordForoshandehBaMoshtaryRepository;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BarkhordAvalieModel implements BarkhordAvalieMVP.ModelOps {

    private BarkhordAvalieMVP.RequiredPresenterOps mPresenter;
    private CompositeDisposable compositeDisposable;

    public BarkhordAvalieModel(BarkhordAvalieMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void countBarkhordForToday(int ccMoshtary) {
        BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        int countTodayBarkhord = barkhordForoshandehBaMoshtaryDAO.getCountTodayByccMoshtary(ccMoshtary);
        if (countTodayBarkhord > 0) {
            mPresenter.onGetCountTodayBarkhord();
        } else {
            mPresenter.onError(R.string.forceBarkhordAvalie);
        }
    }



    @Override
    public void getBarkhords(int ccMoshtary) {
        BarkhordForoshandehBaMoshtaryDAO dao = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhords = dao.getAllByccMoshtary(ccMoshtary);
        mPresenter.onGetBarkhords(barkhords);
    }

    @Override
    public void insertNewBarkhord(int ccMoshtary, String desc) {
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        BarkhordForoshandehBaMoshtaryDAO dao = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        BarkhordForoshandehBaMoshtaryModel model = new BarkhordForoshandehBaMoshtaryModel();
        model.setTarikh(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
        model.setTozihat(desc);
        model.setCcMoshtary(ccMoshtary);
        model.setExtraProp_IsOld(0);
        model.setIsFavorite(0);
        model.setExtraProp_IsRecent(1);
        model.setCcForoshandeh(shared.getInt(shared.getCcForoshandeh(), 0));
        long id =dao.insert(model);
        if (id!=-1)
        {
            getBarkhords(ccMoshtary);
            model.setCcBarkhordForoshandeh(((int) id));
            mPresenter.onSuccessInsertNewBarkhord(model);
        } else {
            mPresenter.onFailedInsertNewBarkhord();
        }
    }


    @Override
    public void removeBarkhord(int ccBarkhord, int ccMoshtary) {
        BarkhordForoshandehBaMoshtaryDAO dao = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        if (dao.deleteByccBarkhord(ccBarkhord, ccMoshtary)) {
            getBarkhords(ccMoshtary);
        } else {
            mPresenter.onFailedRemoveBarkhord();
        }
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null) {
          if (!compositeDisposable.isDisposed()) {
              compositeDisposable.dispose();
          }
            compositeDisposable = null;
        }
    }


    /**
     * @param barkhords
     * @param position
     * @param operator  = 1 add && =2 delete
     */
    @Override
    public void addToFavorite(BarkhordForoshandehBaMoshtaryModel barkhords, int position, boolean operator) {
        BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        boolean updateIsFavoriteByccBarkhord = barkhordForoshandehBaMoshtaryDAO.updateIsFavoriteByccBarkhord(barkhords.getCcBarkhordForoshandeh(), operator);
        if (!updateIsFavoriteByccBarkhord)
            mPresenter.onError(R.string.errorUpdate);
        else
            mPresenter.onSuccessAddToFavorite(position,operator);

//        BarkhordForoshandehBaMoshtaryRepository barkhordForoshandehBaMoshtaryRepository = new BarkhordForoshandehBaMoshtaryRepository(mPresenter.getAppContext());
//        Disposable updateIsFavoriteDisposable = barkhordForoshandehBaMoshtaryRepository
//                .updateIsFavoriteByccBarkhord(barkhords.getCcBarkhordForoshandeh(), operator)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(updateIsFavorite -> {
//                    if (!updateIsFavorite)
//                    {
//                        mPresenter.onError(R.string.errorUpdate);
//                    }
////                    else
////                    {
////                        getBarkhords(barkhords.getCcMoshtary());
////                    }
//                });
//        compositeDisposable.add(updateIsFavoriteDisposable);

    }

    @Override
    public void updateRecentBarkhords() {
        BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        barkhordForoshandehBaMoshtaryDAO.updateAllRecentBarkhords();
    }

}
