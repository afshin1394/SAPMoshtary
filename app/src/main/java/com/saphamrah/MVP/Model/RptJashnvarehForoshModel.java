package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.saphamrah.BaseMVP.RptJashnvarehForoshMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.RptJashnvarehDAO;
import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Repository.DarkhastFaktorRepository;
import com.saphamrah.Repository.ForoshandehMamorPakhshRepository;
import com.saphamrah.Repository.RptJashnvarehForoshRepository;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RptJashnvarehForoshModel implements RptJashnvarehForoshMVP.ModelOps{

    private RptJashnvarehForoshMVP.RequiredPresenterOps mPresenter;
    private CompositeDisposable compositeDisposable;

    public RptJashnvarehForoshModel(RptJashnvarehForoshMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {
      if (compositeDisposable!=null){
          if (!compositeDisposable.isDisposed()){
              compositeDisposable.dispose();
          }
          compositeDisposable = null;
      }
    }

    @Override
    public void getAllCustomers(int ccMoshtary) {
      RptJashnvarehForoshRepository  jashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
       jashnvarehForoshRepository.getAllMoshtaries(ccMoshtary)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel>>() {
                   @Override
                   public void onSubscribe(@NonNull Disposable d) {
                       compositeDisposable.add(d);
                   }

                   @Override
                   public void onNext(@NonNull ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
                       if (rptJashnvarehForoshModels.size()>0)
                       mPresenter.onGetCustomers(rptJashnvarehForoshModels);
                       else
                       mPresenter.onWarning(R.string.notFoundData);

                   }

                   @Override
                   public void onError(@NonNull Throwable e) {
                     mPresenter.onError(R.string.errorOccurred);
                     setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getJashnvarehCustomers","OnError");
                   }

                   @Override
                   public void onComplete() {

                   }
               });

    }





    @Override
    public void getAll(int ccMoshtary, RptJashnvarehActivity.Mode mode) {
    compositeDisposable.add(new ForoshandehMamorPakhshRepository(mPresenter.getAppContext())
             .getIsSelect()
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(foroshandehMamorPakhshModel -> {
                 if (mode.equals(RptJashnvarehActivity.Mode.MamorPakhshFromMenu)){
                   compositeDisposable.add( new DarkhastFaktorRepository(mPresenter.getAppContext())
                           .getAllccMoshtary()
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe(ccMoshtaryString -> {
                               getJashnvarehFromServer("-1",ccMoshtaryString);
                             }));

                 }else if (mode.equals(RptJashnvarehActivity.Mode.ForoshandehFromMenu)){
                     getJashnvarehFromServer(String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()),"-1");
                 }else{
                     getJashnvarehFromServer("-1",String.valueOf(ccMoshtary));
                 }
             }));
    }

    private void getJashnvarehFromServer(String ccforoshandehString, String ccMoshtaryString) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        RptJashnvarehDAO rptJashnvarehDAO = new RptJashnvarehDAO(mPresenter.getAppContext());

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {

                    mPresenter.onSuccess(R.string.updateSuccessed);

                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onError(R.string.updateFailed);
                }
                return false;
            }
        });

        switch (serverIpModel.getWebServiceType()){
            case REST :
                rptJashnvarehForoshRepository.fetchApiServiceRx(serverIpModel,RptJashnvarehActivity.class.getSimpleName(), ccforoshandehString, ccMoshtaryString)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
                                compositeDisposable.add(rptJashnvarehForoshRepository.deleteAll()
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(deleteAll -> {
                                            if (deleteAll) {
                                                compositeDisposable.add(rptJashnvarehForoshRepository.insertGroup(rptJashnvarehForoshModels)
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(insertGroup -> {
                                                            if (insertGroup) {
                                                                mPresenter.onSuccess(R.string.updateSuccessed);
                                                                mPresenter.onGetAll(rptJashnvarehForoshModels);
                                                            } else {
                                                                setLogToDB(Constants.LOG_EXCEPTION(),"insertGroup",RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"insertGroup","OnError");

                                                                mPresenter.onError(R.string.updateFailed);
                                                            }
                                                        }, e -> {
                                                            setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"rptJashnvarehForoshRepository.insertGroup","OnError");
                                                            mPresenter.onError(R.string.updateFailed);

                                                        }));
                                            } else {
                                                setLogToDB(Constants.LOG_EXCEPTION(),"deleteAll",RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"deleteAll","OnError");
                                                mPresenter.onError(R.string.updateFailed);
                                            }
                                        }, throwable -> {
                                            setLogToDB(Constants.LOG_EXCEPTION(),throwable.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"rptJashnvarehForoshRepository.deleteAll()","OnError");
                                            mPresenter.onError(R.string.updateFailed);
                                        }));

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"error","OnError");
                                mPresenter.onError(R.string.updateFailed);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case gRPC:
                rptJashnvarehDAO.fetchRptJashnvarehGrpc(mPresenter.getAppContext(), RptJashnvarehActivity.class.getSimpleName(), Integer.parseInt(ccforoshandehString), ccMoshtaryString, new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData) {
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run(){
                                boolean deleteResult = rptJashnvarehDAO.deleteAll();
                                boolean insertResult = rptJashnvarehDAO.insertGroup(arrayListData);
                                Message message = new Message();
                                if (deleteResult && insertResult)
                                {
                                    message.arg1 = 1;
                                    mPresenter.onGetAll(arrayListData);
                                }
                                else
                                {
                                    message.arg1 = -1;
//                            sendThreadMessage(Constants.BULK_INSERT_FAILED() ,++ itemCounter);
                                }
                                handler.sendMessage(message);
                            }
                        };
                        thread.start();
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        setLogToDB(Constants.LOG_EXCEPTION(),error,RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"error","OnError");
                        mPresenter.onError(R.string.updateFailed);
                    }
                });
                break;
        }

    }

    @Override
    public void searchCustomerName( String searchWord, ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> jashnvarehForoshCustomers)
    {
        ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        Observable.fromIterable(jashnvarehForoshCustomers)
                .subscribeOn(Schedulers.io())
                .filter(rptJashnvarehForoshModel -> rptJashnvarehForoshModel.getNameMoshtary().contains(searchWord))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<com.saphamrah.Model.RptJashnvarehForoshModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull com.saphamrah.Model.RptJashnvarehForoshModel rptJashnvarehForoshModel) {
                        rptJashnvarehForoshModels.add(rptJashnvarehForoshModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"searchCustomerName","OnError");
                    }

                    @Override
                    public void onComplete() {
                        mPresenter.onGetSearch(rptJashnvarehForoshModels);
                    }
                });
    }



    @Override
    public void getAllJashnvareh(int ccMoshtary) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getAllJashnvareh(ccMoshtary)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
                        /**اگر شامل 1 المان باشد فقط شامل همه موارد است که لزومی ندارد دیده شود**/
                        if (rptJashnvarehForoshModels.size() == 1)
                        mPresenter.onWarning(R.string.notFoundData);
                        else
                        mPresenter.onGetAllJashnvareh(rptJashnvarehForoshModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getAllJashnvareh","OnError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void searchSharhJashnvareh(String searchWord, ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> jashnvarehForoshModels) {
        ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> rptJashnvarehForoshModels = new ArrayList<>();
        Observable.fromIterable(jashnvarehForoshModels)
                .subscribeOn(Schedulers.io())
                .filter(rptJashnvarehForoshModel -> rptJashnvarehForoshModel.getSharhJashnvareh().contains(searchWord))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<com.saphamrah.Model.RptJashnvarehForoshModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull com.saphamrah.Model.RptJashnvarehForoshModel rptJashnvarehForoshModel) {
                        rptJashnvarehForoshModels.add(rptJashnvarehForoshModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"searchSharhJashnvareh","OnError");
                    }

                    @Override
                    public void onComplete() {
                        mPresenter.onGetSearch(rptJashnvarehForoshModels);
                    }
                });
    }

    @Override
    public void getSumForoshandehScore(int ccMoshtary) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getSumForoshandehScore(ccMoshtary)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<com.saphamrah.Model.RptJashnvarehForoshModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull com.saphamrah.Model.RptJashnvarehForoshModel rptJashnvarehForoshModel) {
                        ForoshandehMamorPakhshRepository foroshandehMamorPakhshRepository = new ForoshandehMamorPakhshRepository(mPresenter.getAppContext());
                       compositeDisposable.add(foroshandehMamorPakhshRepository.getIsSelect()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(foroshandehMamorPakhshModel -> mPresenter.onGetForoshandehScore(rptJashnvarehForoshModel,foroshandehMamorPakhshModel), throwable -> {
                                    mPresenter.onError(R.string.errorOccurred);
                                    setLogToDB(Constants.LOG_EXCEPTION(),throwable.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getSumForoshandehScore","OnError");

                                }));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getSumForoshandehScore","OnError");                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getAllCustomerByJashnvareh(int ccJashnvarehForosh,int ccMoshtaryExtra,int position) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getAllMoshtaryByJashnvareh(ccJashnvarehForosh,ccMoshtaryExtra)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> jashnvarehForoshModels) {
                       mPresenter.onGetDetails(jashnvarehForoshModels,position);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getAllMoshtaryByJashnvareh","OnError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getAllJashnvarehByCustomer(int ccMoshtary,int position) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getAllJashnvarehByCustomer(ccMoshtary)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> jashnvarehForoshModels) {
                        mPresenter.onGetSumDetails(jashnvarehForoshModels,position);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getAllMoshtaryByJashnvareh","OnError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getMoshtarySatr(int ccMoshtary, int position) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getAllJashnvarehSatrByCustomer(ccMoshtary)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> jashnvarehForoshModels) {
                        mPresenter.onGetDetails(jashnvarehForoshModels,position);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getAllMoshtaryByJashnvareh","OnError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getJashnvarehSatr(int ccMoshtary,int ccJashnvarehForosh,int position) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getAllMoshtarySatrByccJashnvareh(ccMoshtary,ccJashnvarehForosh)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> jashnvarehForoshModels) {
                        mPresenter.onGetDetails(jashnvarehForoshModels,position);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getAllMoshtaryByJashnvareh","OnError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getSumJashnvarehByccJashnvareh(int ccJashnvarehForosh,int ccMoshtaryExtra,int position) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getSumJashnvarehByccJashnvareh(ccJashnvarehForosh,ccMoshtaryExtra)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<com.saphamrah.Model.RptJashnvarehForoshModel> jashnvarehForoshModels) {
                        mPresenter.onGetSumDetails(jashnvarehForoshModels,position);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorOccurred);
                        setLogToDB(Constants.LOG_EXCEPTION(),e.getMessage(),RptJashnvarehForoshModel.class.getSimpleName(),RptJashnvarehActivity.class.getSimpleName(),"getSumJashnvarehByccJashnvareh","OnError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void checkNoeMasouliat(int ccMoshtaryExtra) {
        ForoshandehMamorPakhshRepository foroshandehMamorPakhshRepository = new ForoshandehMamorPakhshRepository(mPresenter.getAppContext());
       compositeDisposable.add(foroshandehMamorPakhshRepository.getIsSelect()
                .subscribe(foroshandehMamorPakhshModel -> {
                    int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                    if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM
                            || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD
                            || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART
                            || noeMasouliat == ForoshandehMamorPakhshUtils.SARPARAST
                            || noeMasouliat == ForoshandehMamorPakhshUtils.MODIR){
                        if (ccMoshtaryExtra==0)
                        mPresenter.onIsForoshandehFromMenu();
                        else
                        mPresenter.onIsForoshandehFromVerifyRequest(ccMoshtaryExtra);
                    }else {
                        if (ccMoshtaryExtra==0)
                        mPresenter.onIsMamorPakhshFromMenu();
                        else
                        mPresenter.onIsMamorPakhshFromVerifyRequest(ccMoshtaryExtra);
                    }
                }));
    }


}
