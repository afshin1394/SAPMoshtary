package com.saphamrah.Repository;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.Utils.RxUtils.RxHttpErrorHandler;
import com.saphamrah.WebService.RxService.APIServiceRxjava;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DarkhastFaktorEmzaMoshtaryRepository {
    Context context;
    DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO;


    public DarkhastFaktorEmzaMoshtaryRepository(Context context) {
        this.context = context;
        darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(context);
    }

    private Callable<Boolean> updateReceiptImageByccDarkhastFaktorCallable(long ccDarkhastFaktor,byte[] receiptImage){
        return () -> darkhastFaktorEmzaMoshtaryDAO.updateReceiptImageByccDarkhastFaktor(ccDarkhastFaktor,receiptImage);
    }


    private Callable<Boolean> deleteAllCallable(){
        return () -> darkhastFaktorEmzaMoshtaryDAO.deleteAll();

    }


    private Callable<Boolean> insertCallable(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel){
        return () -> darkhastFaktorEmzaMoshtaryDAO.insert(darkhastFaktorEmzaMoshtaryModel);

    }

    private Callable<Boolean> insertGroupCallable(ArrayList< DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels){
        return () -> darkhastFaktorEmzaMoshtaryDAO.insertGroup(darkhastFaktorEmzaMoshtaryModels);

    }

    private Callable<Boolean> updateIsSendReceiptImageCallable(long ccDarkhastFaktor) {
        return () -> darkhastFaktorEmzaMoshtaryDAO.updateIsSendReceiptImage(ccDarkhastFaktor);
    }

    public Observable<Boolean> deleteAll(){
        return RxAsync.makeObservable(deleteAllCallable())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> updateReceiptImageByccDarkhastFaktor(long ccDarkhastFaktor,byte[] receiptImage){
        return RxAsync.makeObservable(updateReceiptImageByccDarkhastFaktorCallable(ccDarkhastFaktor,receiptImage))
                .subscribeOn(Schedulers.io());
    }


    public Observable<Boolean> insert(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel){
        return RxAsync.makeObservable(insertCallable(darkhastFaktorEmzaMoshtaryModel))
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> insertGroup(ArrayList< DarkhastFaktorEmzaMoshtaryModel> darkhastFaktorEmzaMoshtaryModels){
        return RxAsync.makeObservable(insertGroupCallable(darkhastFaktorEmzaMoshtaryModels))
                .subscribeOn(Schedulers.io());
    }
    public Observable<Boolean> updateIsSendReceiptImage(long ccDarkhastFaktor) {
        return RxAsync.makeObservable(updateIsSendReceiptImageCallable(ccDarkhastFaktor))
                .subscribeOn(Schedulers.io());
    }




    public Observable<String> sendReceiptImageRx(ServerIpModel serverIpModel, Context context,String activityNameForLog,byte[] imageBytes,long ccDarkhastFaktor) {
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonObjectFinal = new JSONObject();
            jsonObject.put("ccDarkhastFaktor", ccDarkhastFaktor);
            jsonObject.put("receiptImage", Base64.encodeToString(imageBytes, Base64.NO_WRAP));
            jsonObjectFinal.put("receiptImageDarkhast",jsonObject);
            Log.d("jsonObjectFinal", "sendReceiptImageRx: "+jsonObjectFinal.toString());
            return apiServiceRxjava.updateResidImageDarkhastJSON(jsonObjectFinal.toString())
                    .compose(RxHttpErrorHandler.parseHttpErrors(this.getClass().getSimpleName(),activityNameForLog,"fetchApiServiceRx","sendReceiptImageRx"))
                    .map(updateResidImageDarkhastResultResponse -> {
                        if (updateResidImageDarkhastResultResponse.body()!=null) {
                            if (updateResidImageDarkhastResultResponse.body().getSuccess()) {
                                if (updateResidImageDarkhastResultResponse.body().getData() != null) {
                                    return updateResidImageDarkhastResultResponse.body().getData().get(0);
                                }
                            }
                        }

                            return "";
                    }).subscribeOn(Schedulers.io());

        }catch (Exception e){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.getMessage(), "", "PrintActivity", "fetchApiServiceRx", "sendReceiptImageRx");
            return Observable.just("");
        }



    }


}
