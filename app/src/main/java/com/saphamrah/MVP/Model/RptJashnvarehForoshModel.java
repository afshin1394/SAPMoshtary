package com.saphamrah.MVP.Model;

import androidx.annotation.NonNull;

import com.saphamrah.BaseMVP.RptJashnvarehForoshMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Repository.ForoshandehMamorPakhshRepository;
import com.saphamrah.Repository.RptJashnvarehForoshRepository;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
    public void getAllCustomers() {
      RptJashnvarehForoshRepository  jashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
       jashnvarehForoshRepository.getAllMoshtaries()
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
    public void getAll() {

        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        rptJashnvarehForoshRepository.fetchApiServiceRx(serverIpModel,RptJashnvarehActivity.class.getSimpleName(), foroshandehMamorPakhshModel.getCcForoshandeh(), "-1")
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
    public void getAllJashnvareh() {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getAllJashnvareh()
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
    public void getSumForoshandehScore() {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getSumForoshandehScore()
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
    public void getAllCustomerByJashnvareh(int ccJashnvarehForosh,int position) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getAllMoshtaryByJashnvareh(ccJashnvarehForosh)
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
    public void getSumJashnvarehByccJashnvareh(int ccJashnvarehForosh,int position) {
        RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
        rptJashnvarehForoshRepository.getSumJashnvarehByccJashnvareh(ccJashnvarehForosh)
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


}
