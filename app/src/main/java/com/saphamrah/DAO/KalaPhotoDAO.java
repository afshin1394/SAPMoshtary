package com.saphamrah.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.KalaOlaviatModel;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientMultiRequests;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.KalaPhotoResponse;
import com.saphamrah.WebService.ServiceResponse.GetImageKalaResult;
import com.saphamrah.WebService.ServiceResponse.KalaPhotoResult;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

import static io.reactivex.plugins.RxJavaPlugins.onError;

public class KalaPhotoDAO {
    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = KalaPhotoDAO.class.getSimpleName();


    public KalaPhotoDAO(Context context) {
        this.context = context;
        try {
            dbHelper = new DBHelper(context);
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaPhotoDAO", "", "constructor", "");
        }
    }


    private String[] allColumns() {
        return new String[]
                {
                        KalaPhotoModel.getCOLUMN_ccKalaPhoto(),
                        KalaPhotoModel.getCOLUMN_ccKalaCode(),
                        KalaPhotoModel.getColumnImage()
                };


    }

    public int progress = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    public void fetchKalaPhotoRxJava(final Context context, final String activityNameForLog, ArrayList<Integer> ccKalaCodes, final RxResponseHandler rxResponseHandler) {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().multiServerFromShared(context);
        /**
         * multi parallel apiCalls for Gallery
         **/
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);

        Observable.fromIterable(ccKalaCodes)
                .flatMap(s ->
                        apiServiceRxjava.getAllImageKala(String.valueOf(s))
                )
                .map(new Function<Response<KalaPhotoResponse>, ArrayList<KalaPhotoModel>>() {
                    @Override
                    public ArrayList<KalaPhotoModel> apply(@NonNull Response<KalaPhotoResponse> r) throws Exception {
                        ArrayList<KalaPhotoModel> kalaPhotoModels = new ArrayList();
                        if (r.body() != null) {
                            if (r.body().getSuccess()) {
                                if (r.body().getKalaPhotoResults().size() > 0) {
                                    KalaPhotoResult kalaPhotoResult = r.body().getKalaPhotoResults().get(0);

                                    KalaPhotoModel kalaPhotoModel = new KalaPhotoModel();
                                    kalaPhotoModel.setCcKalaCodeDb(kalaPhotoResult.getCcKalaCode());
                                    kalaPhotoModel.setCcKalaPhotoDb(kalaPhotoResult.getCcKalaCode());
                                    kalaPhotoModel.setImageDb(Base64.decode(kalaPhotoResult.getImage(), Base64.NO_WRAP));
                                    kalaPhotoModels.add(kalaPhotoModel);
                                }

                            } else {
                                onError(new Throwable(Constants.RETROFIT_NOT_SUCCESS_MESSAGE()));
                            }
                        } else {
                            onError(new Throwable(Constants.RETROFIT_RESULT_IS_NULL()));
                        }

                        return kalaPhotoModels;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<KalaPhotoModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        rxResponseHandler.onStart(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<KalaPhotoModel> kalaPhotoModels) {
                        progress += 1;
                        rxResponseHandler.onProgress(progress * 100 / (ccKalaCodes.size()));
                        if (kalaPhotoModels.size() > 0)
                            rxResponseHandler.onSuccess(kalaPhotoModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        rxResponseHandler.onFailed(e.getMessage(), e.getCause().getMessage());
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s ", e.getMessage(), e.getCause().getMessage()), CLASS_NAME, activityNameForLog, "fetchKalaPhotoRxJava", "onError");
                    }

                    @Override
                    public void onComplete() {
                        rxResponseHandler.onComplete();
                    }
                });

    }


//       ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//
//       if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
//           String message = "can't find server";
//           PubFunc.Logger logger = new PubFunc().new Logger();
//           logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchImageKala", "");
//           retrofitResponse.onFailed(Constants.HTTP_ERROR(), message);
//       } else {
//           ApiRxJavaService apiRxJavaService = ApiRxJavaClient.createService(ApiRxJavaService.class,serverIpModel.getServerIp(), serverIpModel.getPort());
//           final int[] progress = {0};
//           Observable.fromIterable(ccKalaCodes)
//                   .flatMap(s -> {
//                       return apiRxJavaService.getImageKala(String.valueOf(s));
//
//                   })
//                   .subscribeOn(Schedulers.io())
//                   .observeOn(AndroidSchedulers.mainThread())
//
//
//                       .subscribe(new Observer<GetImageKalaResult>() {
//
//
//                           @Override
//                           public void onSubscribe(Disposable d) {
//
//                           }
//
//                           @Override
//                           public void onNext(GetImageKalaResult getImageKalaResult) {
//                               Log.i("getImageKalaResult1", "onNext: " + getImageKalaResult.getData());
//                               progress[0] += 1;
////                               retrofitResponse.onUpdate(progress[0]*100/ccKalaCodes.size(),getImageKalaResult);
//                               if (getImageKalaResult.getData()!=null){
//                                   Log.i("getImageKalaResult2", "onNext: " + getImageKalaResult.getData());
//
//
//                               }
//                           }
//
//                           @Override
//                           public void onError(Throwable e) {
//                               retrofitResponse.onFailed(e.getMessage(), e.getLocalizedMessage());
//                           }
//
//                           @Override
//                           public void onComplete() {
//                               retrofitResponse.onFinish();
//                               Toast.makeText(context, "completed", Toast.LENGTH_LONG).show();
//                           }
//                       });
//           }
//       }


//    public void fetchKalaPhoto(final Context context, final String activityNameForLog, int ccKalaCode, final RetrofitResponse retrofitResponse) {
//
//        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
//            String message = "can't find server";
//            PubFunc.Logger logger = new PubFunc().new Logger();
//            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchImageKala", "");
//            retrofitResponse.onFailed(Constants.HTTP_ERROR(), message);
//        } else {
//            Log.i("fetchKalaPhoto", "fetchKalaPhoto: " + serverIpModel.getServerIp() + " " + serverIpModel.getPort());
//            APIServiceGet apiServiceGet = ApiClientMultiRequests.getClient(serverIpModel.getServerIp(), serverIpModel.getPort()).create(APIServiceGet.class);
//            Call<GetImageKalaResult> call = apiServiceGet.getImageKala((String.valueOf(ccKalaCode)));
//
//            try {
//                Response<GetImageKalaResult> response = call.execute();
//
//                try {
//                    if (response.raw().body() != null) {
//                        long contentLength = response.raw().body().contentLength();
//                        PubFunc.Logger logger = new PubFunc().new Logger();
//                        Log.i("GET__RESPONSE_IMAGE", "onResponse: Response is not Null");
//                        logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, KalaOlaviatDAO.class.getSimpleName(), "", "fetchKalaImage", "onResponse");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.i("GET__RESPONSE_IMAGE", "exception in response.raw().body()");
//                }
//                try {
//
//                    if (response.isSuccessful()) {
//                        GetImageKalaResult result = response.body();
//                        if (result != null) {
//                            if (result.getSuccess()) {
//                                result.setData(result.getData());
//                                if (result.getData() != null) {
//                                    if (result.getData().size() > 0) {
//                                        Log.i("GET__RESPONSE_IMAGE", "onResponse: getTheResponse" + result.getData().get(0).getImage() + " " + response.body());
//                                        retrofitResponse.onSuccess(result.getData());
//
//                                    }
//                                }
//                            } else {
//                                Log.i("GET__RESPONSE_IMAGE", "onResponse: resultNotSuccessfull");
//                                PubFunc.Logger logger = new PubFunc().new Logger();
//                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
//                                retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
//                            }
//
//                        } else {
//                            Log.i("GET__RESPONSE_IMAGE", "onResponse: retrofit result is null");
//                            String endpoint = getEndpoint(call);
//                            PubFunc.Logger logger = new PubFunc().new Logger();
//                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaOlaviat", "onResponse");
//                            retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
//                        }
//
//                    } else {
//                        Log.i("GET__RESPONSE_IMAGE", "onResponse: failed on Api Call");
//                        String endpoint = getEndpoint(call);
//                        String message = String.format("error body : %1$s , code : %2$s * %3$s", response.message(), response.code(), endpoint);
//                        PubFunc.Logger logger = new PubFunc().new Logger();
//                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaImage", "onResponse");
//                        retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
//                    }
//                }catch (Exception exception) {
//                    Log.i("GET__RESPONSE_IMAGE", "onResponse: checking weather response is successfull" + exception.getMessage());
//                    exception.printStackTrace();
//                    PubFunc.Logger logger = new PubFunc().new Logger();
//                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaImage", "onResponse");
//                    retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION(), exception.toString());
//
//                }
//
//
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.i("GET__RESPONSE_IMAGE", "onFailure: 8");
//                String endpoint = getEndpoint(call);
//                PubFunc.Logger logger = new PubFunc().new Logger();
//                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", e.getMessage(), endpoint), KalaOlaviatDAO.class.getSimpleName(), activityNameForLog, "fetchKalaImage", "onFailure");
//                retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE(), e.getMessage());
//            }
//
//
//        }
//
//    }



    private String getEndpoint(Call call) {
        String endpoint = "";
        try {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endpoint;
    }

    //TODO getAll Kala Codes from Rpt_KalaInfo Table
    public ArrayList<Integer> getAllKalaCodes() {
        ArrayList<Integer> codeKalas = new ArrayList<>();

                String strQry = "select distinct ccKalaCode From Rpt_KalaInfo";
                try {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery(strQry, null);
                    if (cursor != null) {

                        Log.i("--CountOfItems--", "getAllKalaCodes: " + cursor.getCount());
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            while (!cursor.isAfterLast()) {
                                Log.i("Cursorsd", "getAllKalaCodes: " + cursor.getInt(0));

                                codeKalas.add(cursor.getInt(0));
                                cursor.moveToNext();
                            }
                        }
                        cursor.close();
                    }
                    db.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    String message = context.getResources().getString(R.string.errorSelectAll, KalaOlaviatModel.TableName()) + "\n" + e.toString();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "getAllKalaCodes", "", "getAllKalaCodes", "");

                }


        Log.i("codeKalas", "getAllKalaCodes: " + codeKalas.size());
        return codeKalas;


    }






    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(KalaPhotoModel.getTableName(), null, null);
            db.close();
            Log.i("AFTER_RESPONSE_DELETE", "deleteAll: succesfully");
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger ();
            String message = context.getResources().getString(R.string.errorDeleteAll , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaPhotoDAO" , "" , "deleteAll" , "");
            return false;
        }
    }
    public int getCount(){
        Cursor cursor=null;
        String strQry=" Select * From KalaPhoto ";
        try {

            SQLiteDatabase db=dbHelper.getReadableDatabase();
            cursor=db.rawQuery(strQry,null);
            Log.i("--Count_Image_Table--", "getCount: "+cursor.getCount());
            db.close();
        }catch (Exception e){
            Log.i("--Count_Image_Table--", "getCount: "+e.getMessage());
        }

        return cursor.getCount();

    }


    public boolean insertGroup(ArrayList<KalaPhotoModel> kalaPhotoModels)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            for (KalaPhotoModel kalaPhotoModel : kalaPhotoModels) {
                ContentValues contentValues = modelToContentvalue(kalaPhotoModel);
                db.insertOrThrow(KalaPhotoModel.getTableName() , null , contentValues);
            }

            db.close();
            return true;
        }
        catch (Exception exception)
        {
            Log.i("getException", "insertGroup: "+exception.getMessage());
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaPhotoModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaPhotoModelDAO" , "" , "insert" , "");
            return false;
        }
    }

    public boolean insert(KalaPhotoModel kalaPhotoModel)
    {
        try
        {
            Log.d("AFTER_RESPONSE","kalaPhotoModel:"+ kalaPhotoModel.getCcKalaCodeDb());
            ContentValues contentValues = modelToContentvalue(kalaPhotoModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(KalaPhotoModel.getTableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            Log.i("getException", "insert: "+exception.getMessage());
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , KalaPhotoModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaPhotoModelDAO" , "" , "insert" , "");
            return false;
        }
    }


    private static ContentValues modelToContentvalue(KalaPhotoModel kalaPhotoModel)
    {
        ContentValues contentValues = new ContentValues();


        contentValues.put(KalaPhotoModel.getCOLUMN_ccKalaPhoto() , kalaPhotoModel.getCcKalaPhotoDb());
        contentValues.put(KalaPhotoModel.getCOLUMN_ccKalaCode() , kalaPhotoModel.getCcKalaCodeDb());
        contentValues.put(KalaPhotoModel.getColumnImage(),kalaPhotoModel.getImageDb());

        return contentValues;
    }


    public ArrayList<KalaPhotoModel> getAll()
    {
        ArrayList<KalaPhotoModel> kalaPhotoModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(KalaPhotoModel.getTableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                Log.i("CountOfsdssd", "getAll: "+cursor.getCount());
                if (cursor.getCount() > 0)
                {
                    kalaPhotoModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger ();
            String message = context.getResources().getString(R.string.errorSelectAll , KalaPhotoModel.getTableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaPhotoDAO" , "" , "getAll" , "");
        }
        return kalaPhotoModels;
    }


    private ArrayList<KalaPhotoModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaPhotoModel> kalaPhotoModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaPhotoModel kalaPhotoModel = new KalaPhotoModel();
            kalaPhotoModel.setCcKalaPhotoDb(cursor.getInt(cursor.getColumnIndex(KalaPhotoModel.getCOLUMN_ccKalaPhoto())));
            Log.i("--GET--CCKALA--PHOTO", "cursorToModel: "+cursor.getInt(cursor.getColumnIndex(KalaPhotoModel.getCOLUMN_ccKalaPhoto())));
            kalaPhotoModel.setCcKalaCodeDb(cursor.getInt(cursor.getColumnIndex(KalaPhotoModel.getCOLUMN_ccKalaCode())));
            kalaPhotoModel.setImageDb(cursor.getBlob(cursor.getColumnIndex(KalaPhotoModel.getColumnImage())));

            kalaPhotoModels.add(kalaPhotoModel);
            cursor.moveToNext();
        }
        return kalaPhotoModels;
    }




}
