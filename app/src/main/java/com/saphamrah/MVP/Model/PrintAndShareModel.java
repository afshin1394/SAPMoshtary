package com.saphamrah.MVP.Model;

import static com.saphamrah.Utils.Constants.ccNoeFaktor;
import static com.saphamrah.Utils.Constants.ccNoeHavale;

import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.PrintFaktorDAO;
import com.saphamrah.BaseMVP.PrintAndShareMVP;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.PrintFaktorModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Repository.PrintFaktorRepository;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class PrintAndShareModel implements PrintAndShareMVP.ModelOps {

    private PrintAndShareMVP.RequiredPresenterOps mPresenter;
    private PrintFaktorDAO printFaktorDAO = new PrintFaktorDAO(BaseApplication.getContext());
    private ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());

    public PrintAndShareModel(PrintAndShareMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void update() {
        int ccAfrad  = foroshandehMamorPakhshDAO.getCCAfrad();

        final Handler handler = new Handler(msg -> {
            if (msg.arg1 == 1)
            {
                getAllPrintFaktor();
                mPresenter.onUpdateData();
            }
            else if (msg.arg1 == -1)
            {
                mPresenter.failedUpdate();
            }
            return false;
        });

        printFaktorDAO.fetchPrintFaktor(BaseApplication.getContext(), "printFaktor", ccAfrad, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread()
                {
                    @Override
                    public void run(){

                        boolean deleteResult = printFaktorDAO.deleteAll();
                        boolean insertResult = printFaktorDAO.insertGroup(arrayListData);
                        Message message = new Message();
                        if (deleteResult && insertResult)
                        {
                            message.arg1 = 1;
                        }
                        else
                        {
                            message.arg1 = -1;
                        }
                        handler.sendMessage(message);

                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.failedUpdate();
            }
        });
    }

    @Override
    public void getAllPrintFaktor() {
        //ArrayList<PrintFaktorModel> modelArrayList = printFaktorDAO.getAll();
        ArrayList<PrintFaktorModel> modelArrayList = printFaktorDAO.getAllWithOutImage();
        mPresenter.onGetAllPrintFaktor(modelArrayList);
    }

    public void getImagePrintFaktor(String UniqID) {
        PrintFaktorModel model = printFaktorDAO.getImageWithUniqID(UniqID);
        mPresenter.onGetImagePrintFaktor(model);
    }

    @Override
    public void getFaktorImage(int action , PrintFaktorModel printFaktorModel) {
        Log.i("uniqueeeee", "updateImage: "+printFaktorModel.getUniqID_Tablet());

          PrintFaktorRepository printFaktorRepository = new PrintFaktorRepository(BaseApplication.getContext());
          printFaktorRepository.checkPrintFaktorExist(printFaktorModel.getUniqID_Tablet())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<Boolean>() {
                      @Override
                      public void onSubscribe(@NonNull Disposable d) {

                      }

                      @Override
                      public void onNext(@NonNull Boolean exists) {
                            if (exists){
                                getPrintFaktorLocal(action ,printFaktorModel.getUniqID_Tablet());
                            }else{
                                getPrintFaktorFromServer( action , printFaktorModel);

                            }
                      }

                      @Override
                      public void onError(@NonNull Throwable e) {
                          mPresenter.onError(R.string.errorCheckPrintFaktorExist);

                      }

                      @Override
                      public void onComplete() {

                      }
                  });



    }



    private void getPrintFaktorLocal(int action,String uniqueID) {
        PrintFaktorRepository printFaktorRepository = new PrintFaktorRepository(BaseApplication.getContext());
        printFaktorRepository.getImageWithUniqID(uniqueID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrintFaktorModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PrintFaktorModel printFaktorModel) {

                        mPresenter.onGetPrintFaktor(uniqueID,action ,Base64.decode(printFaktorModel.getFaktorImage(), Base64.NO_WRAP));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorGetImageWithUniqID);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void getPrintFaktorFromServer(int action,PrintFaktorModel printFaktorModel) {



        String ccDarkhastFaktor = "";
        String ccDarkhastHavaleh = "";
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(BaseApplication.getContext());


        switch (printFaktorModel.getCcDarkhastFaktorNoeForosh()) {
            case ccNoeHavale:
                ccDarkhastFaktor ="0";
                ccDarkhastHavaleh = String.valueOf(printFaktorModel.getCcDarkhastFaktor());
                break;

            case ccNoeFaktor:
                ccDarkhastHavaleh ="0";
                ccDarkhastFaktor = String.valueOf(printFaktorModel.getCcDarkhastFaktor());
                break;
        }
        Log.i("uniqueeeee", "updateImage: "+printFaktorModel.getUniqID_Tablet());

        PrintFaktorRepository printFaktorRepository = new PrintFaktorRepository(BaseApplication.getContext());
        printFaktorRepository.fetchApiServiceRx(serverIpModel,"printAndShareActivity",ccDarkhastFaktor,ccDarkhastHavaleh)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String image) {
                            if (!image.equals(""))
                                updateImage(action, image, printFaktorModel.getUniqID_Tablet());
                            else
                                mPresenter.onWarning(R.string.FaktorImageNotAvailable);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorGetPrintFaktorFromServer);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void  updateImage(int action,String image , String uniqueID)
    {
        Log.i("uniqueeeee", "updateImage: "+uniqueID);

        PrintFaktorRepository printFaktorRepository = new PrintFaktorRepository(BaseApplication.getContext());
        printFaktorRepository.updateImage(image,uniqueID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean updated) {
                        if (updated)
                            getPrintFaktorLocal(action,uniqueID);
                        else
                            onError(new Throwable());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onError(R.string.errorupdateImage);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
