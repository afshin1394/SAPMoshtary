package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.MandehdarZangireiMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.RptMandehdarDAO;
import com.saphamrah.MVP.View.RptJashnvarehActivity;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.NetworkUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Repository.DariaftPardakhtDarkhastFaktorPPCRepository;
import com.saphamrah.Repository.DariaftPardakhtPPCRepository;
import com.saphamrah.Repository.DarkhastFaktorRepository;
import com.saphamrah.Repository.MoshtaryRepository;
import com.saphamrah.Repository.RptMandehdarRepository;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MandehdarZangireiModel implements MandehdarZangireiMVP.ModelOps {
    private CompositeDisposable compositeDisposable;
    private MandehdarZangireiMVP.RequiredPresenterOps mPresenter;
    private DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
    private MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
    private DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
    private ServerIpModel serverIpModel =new PubFunc().new NetworkUtils().getServerFromShared(BaseApplication.getContext());
    private ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
    private String tagActivity = "MandehMojodiZanjirei";

    RptMandehdarRepository rptMandehdarRepository = new RptMandehdarRepository(BaseApplication.getContext());
    private ForoshandehMamorPakhshModel foroshandehMamorPakhshModel;
    public MandehdarZangireiModel(MandehdarZangireiMVP.RequiredPresenterOps presenterOps) {
        mPresenter = presenterOps;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }


    @Override
    public void getListMandehDar() {

        Disposable getAllMandehdar = rptMandehdarRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(madehdar ->{
                    mPresenter.onGetListMandehDar(madehdar);
                });
        compositeDisposable.add(getAllMandehdar);
    }

    @Override
    public void updateListMandehDar() {
        RptMandehdarDAO rptMandehdarDAO = new RptMandehdarDAO(mPresenter.getAppContext());
        foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        switch (serverIpModel.getWebServiceType()) {
            case REST:
                rptMandehdarRepository.fetchAllMandehdar(serverIpModel,tagActivity,String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<RptMandehdarModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<RptMandehdarModel> rptMandehdarModels) {
                                compositeDisposable.add(rptMandehdarRepository.deleteAll()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(delete ->{
                                    if (delete) {
                                       compositeDisposable.add(rptMandehdarRepository.insertGroup(rptMandehdarModels)
                                               .observeOn(AndroidSchedulers.mainThread())
                                       .subscribe(insert ->{
                                           if (insert){
                                               mPresenter.onUpdateData();
                                               mPresenter.onGetListMandehDar(rptMandehdarModels);
                                           } else {
                                               setLogToDB(Constants.LOG_EXCEPTION(), "insertGroup", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                               mPresenter.failedUpdate();
                                           }
                                       }));
                                    } else {
                                        setLogToDB(Constants.LOG_EXCEPTION(), "insertGroup", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                        mPresenter.failedUpdate();
                                    }
                                }));
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                setLogToDB(Constants.LOG_EXCEPTION(), "updateListMandehDar" + e.getMessage(), MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                mPresenter.failedUpdate();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            break;
            case gRPC:
                rptMandehdarDAO.fetchAllMandehdarGrpc(mPresenter.getAppContext(), tagActivity, String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()), new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData) {
                        boolean delete = rptMandehdarDAO.deleteAll();
                        boolean insert = rptMandehdarDAO.insertGroup(arrayListData);
                        if (delete && insert){
                            mPresenter.onUpdateData();
                            mPresenter.onGetListMandehDar(arrayListData);
                        } else {
                            setLogToDB(Constants.LOG_EXCEPTION(), "fetchAllMandehdarGrpc", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                            mPresenter.failedUpdate();
                        }
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        setLogToDB(Constants.LOG_EXCEPTION(), "fetchAllMandehdarGrpc " + error, MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                        mPresenter.failedUpdate();
                    }
                });
                break;
        }
    }

    @Override
    public void getDetails(RptMandehdarModel model) {
        foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        getDarkhastFaktor(model);
    }

    private void getDarkhastFaktor(RptMandehdarModel model) {
        DarkhastFaktorRepository darkhastFaktorRepository = new DarkhastFaktorRepository(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()) {
            case REST:
                darkhastFaktorRepository.fetchDarkhastFaktorByccDarkhastFaktorServiceRx(serverIpModel, tagActivity, String.valueOf(model.getCcDarkhastFaktor()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<DarkhastFaktorModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {
                                compositeDisposable.add(darkhastFaktorRepository.deleteByccDarkhastFaktor(darkhastFaktorModels.get(0).getCcDarkhastFaktor())
                                .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(delete ->{
                                            if (delete){
                                                compositeDisposable.add(darkhastFaktorRepository.insert(darkhastFaktorModels.get(0))
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(insertGroup -> {
                                                            if (insertGroup) {
                                                                getDariaftPardakht(model,darkhastFaktorModels);
                                                            } else {
                                                                setLogToDB(Constants.LOG_EXCEPTION(), "insertGroup", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                                                mPresenter.failedUpdate();
                                                            }
                                                        }));
                                            } else {
                                                setLogToDB(Constants.LOG_EXCEPTION(), "Delete", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                                mPresenter.failedUpdate();
                                            }
                                        }));

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                setLogToDB(Constants.LOG_EXCEPTION(), "getDarkhasdFaktor " + e.getMessage(), MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "getDarkhasdFaktor", "OnError");
                                mPresenter.failedUpdate();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case gRPC:
                darkhastFaktorDAO.fetchDarkhastFaktorByccDarkhastFaktorGrpc(BaseApplication.getContext(), tagActivity, String.valueOf(model.getCcDarkhastFaktor()), new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData) {
                        boolean delete = darkhastFaktorDAO.deleteByccDarkhastFaktor(model.getCcDarkhastFaktor());
                        boolean insert = darkhastFaktorDAO.insertGroup(arrayListData);
                        if (delete && insert)
                            getDariaftPardakht(model,arrayListData);
                        else {
                            setLogToDB(Constants.LOG_EXCEPTION(), "fetchDarkhastFaktorByccDarkhastFaktorGrpc ", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "getDarkhasdFaktor", "OnError");
                            mPresenter.failedUpdate();
                        }
                    }

                    @Override
                    public void onFailed(String type, String error) {

                    }
                });
                break;
        }


    }

    private void getDariaftPardakht(RptMandehdarModel model, ArrayList<DarkhastFaktorModel> darkhastFaktorModels){
        DariaftPardakhtPPCRepository dariaftPardakhtPPCRepository = new DariaftPardakhtPPCRepository(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorModels.get(0);
        switch (serverIpModel.getWebServiceType()) {
            case REST:
               dariaftPardakhtPPCRepository.fetchDariaftPardakhtServiceRx(serverIpModel,tagActivity,"1",String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor()))
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Observer<ArrayList<DariaftPardakhtPPCModel>>() {
                           @Override
                           public void onSubscribe(@NonNull Disposable d) {
                               compositeDisposable.add(d);
                           }

                           @Override
                           public void onNext(@NonNull ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels) {
                               compositeDisposable.add(dariaftPardakhtPPCRepository.deleteByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor())
                               .observeOn(AndroidSchedulers.mainThread())
                                       .subscribe(delete ->{
                                           if (delete){
                                               compositeDisposable.add(dariaftPardakhtPPCRepository.insertGroup(dariaftPardakhtPPCModels)
                                                       .observeOn(AndroidSchedulers.mainThread())
                                                       .subscribe(insert ->{
                                                           if (insert){
                                                               getDariaftPardakhtDarkhastFaktor(model,darkhastFaktorModel);
                                                           } else {
                                                               setLogToDB(Constants.LOG_EXCEPTION(), "insertGroup", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                                               mPresenter.failedUpdate();
                                                           }
                                                       }));
                                           } else {
                                               setLogToDB(Constants.LOG_EXCEPTION(), "Delete", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                               mPresenter.failedUpdate();
                                           }
                                       }));


                           }

                           @Override
                           public void onError(@NonNull Throwable e) {
                               setLogToDB(Constants.LOG_EXCEPTION(), "getDariaftPardakht " + e.getMessage(), MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                               mPresenter.failedUpdate();
                           }

                           @Override
                           public void onComplete() {

                           }
                       });
                break;
            case gRPC:
                dariaftPardakhtPPCDAO.fetchDariaftPardakhtPPCGrpc(mPresenter.getAppContext(), tagActivity, "1", String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor()), new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData) {
                        boolean delete = dariaftPardakhtPPCDAO.deleteByccDarkhastFaktor(model.getCcDarkhastFaktor());
                        boolean insert = dariaftPardakhtPPCDAO.insertGroup(arrayListData, true);
                        if (delete && insert){
                            getDariaftPardakhtDarkhastFaktor(model,darkhastFaktorModel);
                        } else {
                            setLogToDB(Constants.LOG_EXCEPTION(), "insertGroup And delete", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                            mPresenter.failedUpdate();
                        }
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        setLogToDB(Constants.LOG_EXCEPTION(), "fetchDariaftPardakhtPPCGrpc " + error, MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                        mPresenter.failedUpdate();
                    }
                });
                break;
        }
    }

    private void getDariaftPardakhtDarkhastFaktor(RptMandehdarModel model, DarkhastFaktorModel darkhastFaktorModel){
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        DariaftPardakhtDarkhastFaktorPPCRepository dpdfRepository = new DariaftPardakhtDarkhastFaktorPPCRepository(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST:
                dpdfRepository.fetchDariaftPardakhtDarkhastFaktorServiceRx(serverIpModel,tagActivity,"1",String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<DariaftPardakhtDarkhastFaktorPPCModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels) {
                                compositeDisposable.add(dpdfRepository.deleteByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(delete ->{
                                    if (delete){
                                        compositeDisposable.add(dpdfRepository.insertGroup(dariaftPardakhtDarkhastFaktorPPCModels , true)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(insert ->{
                                                    if (insert){
                                                        getmoshtary(model,darkhastFaktorModel);
                                                    } else {
                                                        setLogToDB(Constants.LOG_EXCEPTION(), "insertGroup", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                                        mPresenter.failedUpdate();
                                                    }
                                                })
                                        );
                                    } else {
                                        setLogToDB(Constants.LOG_EXCEPTION(), "Delete", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                        mPresenter.failedUpdate();
                                    }
                                }));

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                setLogToDB(Constants.LOG_EXCEPTION(), "getDariaftPardakhtDarkhastFaktor " + e.getMessage(), MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                mPresenter.failedUpdate();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;

            case gRPC:
                dariaftPardakhtDarkhastFaktorPPCDAO.fetchDariaftPardakhtDarkhastFaktorPPCGrpc(mPresenter.getAppContext(), tagActivity, "1", String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor()), new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData) {
                      boolean delete =  dariaftPardakhtDarkhastFaktorPPCDAO.deleteByccDarkhastFaktor(model.getCcDarkhastFaktor());
                      boolean insert =  dariaftPardakhtDarkhastFaktorPPCDAO.insertGroup(arrayListData, true);
                        if (delete && insert){
                            getmoshtary(model,darkhastFaktorModel);
                        } else {
                            setLogToDB(Constants.LOG_EXCEPTION(), "delete and insert", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                            mPresenter.failedUpdate();
                        }
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        setLogToDB(Constants.LOG_EXCEPTION(), "fetchDariaftPardakhtDarkhastFaktorPPCGrpc " + error, MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                        mPresenter.failedUpdate();
                    }
                });
                break;
        }

    }


    private void getmoshtary(RptMandehdarModel model, DarkhastFaktorModel darkhastFaktorModel) {
        MoshtaryRepository moshtaryRepository = new MoshtaryRepository(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()) {
            case REST:
              moshtaryRepository.fetchSingleMoshtaryByccMasirServiceRx(serverIpModel,tagActivity,String.valueOf(darkhastFaktorModel.getCcForoshandeh()),"-1",darkhastFaktorModel.getCodeMoshtary())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<MoshtaryModel>() {
                          @Override
                          public void onSubscribe(@NonNull Disposable d) {
                              compositeDisposable.add(d);
                          }

                          @Override
                          public void onNext(@NonNull MoshtaryModel moshtaryModel) {
                              compositeDisposable.add(moshtaryRepository.deleteByCcMoshtary(darkhastFaktorModel.getCcMoshtary())
                              .observeOn(AndroidSchedulers.mainThread())
                              .subscribe(delete ->{
                                  if (delete){
                                      compositeDisposable.add(moshtaryRepository.insert(moshtaryModel)
                                              .observeOn(AndroidSchedulers.mainThread())
                                              .subscribe(insert ->{
                                                  if (insert){
                                                      mPresenter.onUpdateData();
                                                  } else {
                                                      setLogToDB(Constants.LOG_EXCEPTION(), "insert ", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                                      mPresenter.failedUpdate();
                                                  }
                                              }));
                                  } else {
                                      setLogToDB(Constants.LOG_EXCEPTION(), "Delete ", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                      mPresenter.failedUpdate();
                                  }
                              }));
                          }

                          @Override
                          public void onError(@NonNull Throwable e) {
                              setLogToDB(Constants.LOG_EXCEPTION(), "getmoshtary " + e.getMessage(), MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                              mPresenter.failedUpdate();
                          }

                          @Override
                          public void onComplete() {

                          }
                      });
                break;
            case gRPC:
                moshtaryDAO.fetchAllMoshtaryByccMasirGrpc(BaseApplication.getContext(),
                        tagActivity,
                        String.valueOf(darkhastFaktorModel.getCcForoshandeh()),
                        "-1",
                        darkhastFaktorModel.getCodeMoshtary(),
                        new RetrofitResponse() {
                            @Override
                            public void onSuccess(ArrayList arrayListData) {
                                boolean delete = moshtaryDAO.deleteByccMoshtary(model.getCcMoshtary());
                                boolean insert = moshtaryDAO.insertGroup(arrayListData);
                                if (delete && insert){
                                    mPresenter.onUpdateData();
                                } else {
                                    setLogToDB(Constants.LOG_EXCEPTION(), "insert and delete ", MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                    mPresenter.failedUpdate();
                                }
                            }

                            @Override
                            public void onFailed(String type, String error) {
                                setLogToDB(Constants.LOG_EXCEPTION(), "fetchAllMoshtaryByccMasirGrpc " + error, MandehdarZangireiModel.class.getSimpleName(), MandehdarZangireiModel.class.getSimpleName(), "insertGroup", "OnError");
                                mPresenter.failedUpdate();
                            }
                        }
                );
                break;
        }


    }


}
