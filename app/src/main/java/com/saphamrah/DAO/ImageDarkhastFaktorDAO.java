package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.GetImageStringModel;
import com.saphamrah.Model.ImageDarkhastFaktorModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetImageJsonResult;
import com.saphamrah.WebService.ServiceResponse.GetImageStringResult;
import com.saphamrah.protos.InvoiceRequestSignatureImageGrpc;
import com.saphamrah.protos.InvoiceRequestSignatureImageReply;
import com.saphamrah.protos.InvoiceRequestSignatureImageReplyList;
import com.saphamrah.protos.InvoiceRequestSignatureImageRequest;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDarkhastFaktorDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ImageDarkhastFaktorDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ImageDarkhastFaktorDAO.class.getSimpleName() , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ImageDarkhastFaktorModel.COLUMN_Image_DarkhastFaktor()
        };
    }

    public void fetchGetImageJSONGrpc(final Context context, final String activityNameForLog, final String ccDarkhastFaktor, final String ccDarkhastHavaleh, final RetrofitResponse retrofitResponse){
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
//        ServerIpModel serverIpModel = new ServerIpModel();
//        serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ImageDarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchGetImageJSONGRPC", "");
                retrofitResponse.onFailed(Constants.HTTP_WRONG_ENDPOINT(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                InvoiceRequestSignatureImageGrpc.InvoiceRequestSignatureImageBlockingStub invoiceRequestSignatureImageBlockingStub = InvoiceRequestSignatureImageGrpc.newBlockingStub(managedChannel);
                InvoiceRequestSignatureImageRequest invoiceRequestSignatureImageRequest = InvoiceRequestSignatureImageRequest.newBuilder().setInvoiceRequestID(Long.parseLong(ccDarkhastFaktor)).setDraftRequestID(Long.parseLong(ccDarkhastHavaleh)).build();
                Callable<InvoiceRequestSignatureImageReplyList> invoiceRequestSignatureImageReplyListCallable = () -> invoiceRequestSignatureImageBlockingStub.getInvoiceRequestSignatureImage(invoiceRequestSignatureImageRequest);
                RxAsync.makeObservable(invoiceRequestSignatureImageReplyListCallable)
                        .map(invoiceRequestSignatureImageReplyList -> {
                            ArrayList<GetImageStringModel> getImageStringModels = new ArrayList<>();
                            for (InvoiceRequestSignatureImageReply invoiceRequestSignatureImageReply : invoiceRequestSignatureImageReplyList.getInvoiceRequestSignatureImageList()) {
                                GetImageStringModel getImageStringModel = new GetImageStringModel();
                                getImageStringModel.setImage(invoiceRequestSignatureImageReply.getInvoiceImage());
                                getImageStringModels.add(getImageStringModel);
                            }

                            return getImageStringModels;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<GetImageStringModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<GetImageStringModel> getImageStringModels) {
                                retrofitResponse.onSuccess(getImageStringModels);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), e.toString(), ImageDarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchGetImageJSONGRPC", "CatchException");
                                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                                if (!compositeDisposable.isDisposed()) {
                                    compositeDisposable.dispose();
                                }
                                compositeDisposable.clear();
                            }
                        });

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), ImageDarkhastFaktorDAO.class.getSimpleName(), activityNameForLog, "fetchGetImageJSONGRPC", "CatchException");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }
    }




    public void fetchImageString(final Context context, final String activityNameForLog, final String ccDarkhastFaktor, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageString", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetImageStringResult> call = apiServiceGet.getImageString(ccDarkhastFaktor);
            call.enqueue(new Callback<GetImageStringResult>() {
                @Override
                public void onResponse(Call<GetImageStringResult> call, Response<GetImageStringResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "ImageDarkhastFaktorDAO", "", "fetchImageString", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetImageStringResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageString", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageString", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageString", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageString", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetImageStringResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s , %2$s", t.getMessage(), endpoint), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageString", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }

    public void fetchImageStringForMamorPakhsh(final Context context, final String activityNameForLog, final String ccDarkhastFaktor, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageStringForMamorPakhsh", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
Call<GetImageStringResult> call = apiServiceGet.getImageStringForMamorPakhsh(ccDarkhastFaktor);
            call.enqueue(new Callback<GetImageStringResult>() {
                @Override
                public void onResponse(Call<GetImageStringResult> call, Response<GetImageStringResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "ImageDarkhastFaktorDAO", "", "fetchImageStringForMamorPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetImageStringResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageStringForMamorPakhsh", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageStringForMamorPakhsh", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageStringForMamorPakhsh", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageStringForMamorPakhsh", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetImageStringResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s",t.getMessage(), endpoint), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchImageStringForMamorPakhsh", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    public void fetchGetImageJSON(final Context context, final String activityNameForLog, final String ccDarkhastFaktor, final String ccDarkhastHavaleh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ImageDarkhastFaktorDAO", activityNameForLog, "fetchGetImageJSON", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetImageJsonResult> call = apiServiceGet.getImageJSON(ccDarkhastFaktor , ccDarkhastHavaleh);
            call.enqueue(new Callback<GetImageJsonResult>() {
                @Override
                public void onResponse(Call<GetImageJsonResult> call, Response<GetImageJsonResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, "fetchGetImageJSON", "", "fetchImageStringForMamorPakhsh", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetImageJsonResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    if (result.getData() != null)
                                    {
                                        retrofitResponse.onSuccess(result.getData());
                                    }
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchGetImageJSON", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchGetImageJSON", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ImageDarkhastFaktorDAO", activityNameForLog, "fetchGetImageJSON", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchGetImageJSON", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetImageJsonResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), "ImageDarkhastFaktorDAO", activityNameForLog, "fetchGetImageJSON", "onFailure");
                    retrofitResponse.onFailed(Constants.RETROFIT_THROWABLE() , t.getMessage());
                }
            });
        }
    }


    private String getEndpoint(Call call)
    {
        String endpoint = "";
        try
        {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
        }
        catch (Exception e)
        {e.printStackTrace();}
        return endpoint;
    }


    public boolean insertGroup(ArrayList<ImageDarkhastFaktorModel> imageDarkhastFaktorModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ImageDarkhastFaktorModel imageDarkhastFaktorModel : imageDarkhastFaktorModels)
            {
                ContentValues contentValues = modelToContentvalue(imageDarkhastFaktorModel);
                db.insertOrThrow(ImageDarkhastFaktorModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , ImageDarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ImageDarkhastFaktorDAO.class.getSimpleName() , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(ImageDarkhastFaktorModel imageDarkhastFaktorModel)
    {
        ContentValues contentValues = modelToContentvalue(imageDarkhastFaktorModel);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(ImageDarkhastFaktorModel.TableName() , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , ImageDarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ImageDarkhastFaktorDAO" , "" , "insert" , "");
            return false;
        }
    }


    public ArrayList<ImageDarkhastFaktorModel> getAll()
    {
        ArrayList<ImageDarkhastFaktorModel> imageDarkhastFaktorModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ImageDarkhastFaktorModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    imageDarkhastFaktorModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ImageDarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ImageDarkhastFaktorDAO.class.getSimpleName() , "" , "getAll" , "");
        }
        return imageDarkhastFaktorModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(ImageDarkhastFaktorModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ImageDarkhastFaktorModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, ImageDarkhastFaktorDAO.class.getSimpleName() , "" , "deleteAll" , "");
            return false;
        }
    }


    private static ContentValues modelToContentvalue(ImageDarkhastFaktorModel imageDarkhastFaktorModel)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageDarkhastFaktorModel.COLUMN_Image_DarkhastFaktor() , imageDarkhastFaktorModel.getImageDarkhastFaktor());
        return contentValues;
    }


    private ArrayList<ImageDarkhastFaktorModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ImageDarkhastFaktorModel> imageDarkhastFaktorModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ImageDarkhastFaktorModel imageDarkhastFaktorModel = new ImageDarkhastFaktorModel();
            imageDarkhastFaktorModel.setImageDarkhastFaktor(cursor.getBlob(cursor.getColumnIndex(ImageDarkhastFaktorModel.COLUMN_Image_DarkhastFaktor())));

            imageDarkhastFaktorModels.add(imageDarkhastFaktorModel);
            cursor.moveToNext();
        }
        return imageDarkhastFaktorModels;
    }


}
