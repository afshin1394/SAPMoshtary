package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.MVP.splash.SplashModel;
import com.saphamrah.Model.ForoshandehAmoozeshiModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetForoshandehAmoozeshiResult;
import com.saphamrah.protos.EducationalSalesManDeviceNumberGrpc;
import com.saphamrah.protos.EducationalSalesManDeviceNumberReply;
import com.saphamrah.protos.EducationalSalesManDeviceNumberReplyList;
import com.saphamrah.protos.EducationalSalesManDeviceNumberRequest;

import java.util.ArrayList;
import java.util.List;
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

public class ForoshandehAmoozeshiDeviceNumberDAO
{

    private DBHelper dbHelper;
    private Context context;


    public ForoshandehAmoozeshiDeviceNumberDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ForoshandehAmoozeshiDeviceNumberDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            ForoshandehAmoozeshiModel.COLUMN_ccDeviceNumber(),
            ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAmoozeshi(),
            ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAsli()
        };
    }


    public void getForoshandehAmoozeshiGrpc(Context context, ServerIpModel serverIpModel, String activityNameForLog, RetrofitResponse retrofitResponse)
    {
        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();

        final String deviceIMEI = deviceInfo.getIMEI(context);
        try {
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MessageBoxDAO.class.getSimpleName(), activityNameForLog, "fetchMessagesGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                EducationalSalesManDeviceNumberGrpc.EducationalSalesManDeviceNumberBlockingStub educationalSalesManDeviceNumberBlockingStub = EducationalSalesManDeviceNumberGrpc.newBlockingStub(managedChannel);
                EducationalSalesManDeviceNumberRequest request = EducationalSalesManDeviceNumberRequest.newBuilder().setDeviceSerialNumber(deviceIMEI).build();

                Callable<EducationalSalesManDeviceNumberReplyList> replyListCallable = () -> educationalSalesManDeviceNumberBlockingStub.getEducationalSalesManDeviceNumber(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<ForoshandehAmoozeshiModel> models = new ArrayList<>();
                            for (EducationalSalesManDeviceNumberReply reply : replyList.getEducationalSalesManDeviceNumbersList()) {
                                ForoshandehAmoozeshiModel model = new ForoshandehAmoozeshiModel();

                                model.setCcDeviceNumber(reply.getDiveceNumberID());
                                model.setDeviceNumberForoshandehAmoozeshi(reply.getEducationalSalesManDeviceNumber());
                                model.setDeviceNumberForoshandehAsli(reply.getOrginalSalesManDeviceNumber());


                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<ForoshandehAmoozeshiModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<ForoshandehAmoozeshiModel> models) {
                                retrofitResponse.onSuccess(models);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
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
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), MessageBoxDAO.class.getSimpleName(), activityNameForLog, "fetchMessagesGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }



    }

    public void getForoshandehAmoozeshi(Context context, ServerIpModel serverIpModel, String activityNameForLog, RetrofitResponse retrofitResponse) {

        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();

        APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);//ApiClient.getClient(serverIP, port).create(APIServiceGet.class);
        final String deviceIMEI = deviceInfo.getIMEI(context);
        Log.d("SplashModel", "getForoshandehAmoozeshi deviceIMEI:" + deviceIMEI);
        Call<GetForoshandehAmoozeshiResult> call = apiServiceGet.getForoshandehAmoozeshi(deviceIMEI);
        call.enqueue(new Callback<GetForoshandehAmoozeshiResult>() {
            @Override
            public void onResponse(Call<GetForoshandehAmoozeshiResult> call, Response<GetForoshandehAmoozeshiResult> response) {
                if (response.raw().body() != null) {
                    Log.d("intercept", "in on response SplashModel.getForoshandehAmoozeshi and response : " + response.raw().body().contentLength());
                    long contentLength = response.raw().body().contentLength();
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, SplashModel.class.getSimpleName(), "", "getForoshandehAmoozeshi", "onResponse");
                }
                if (response.isSuccessful()) {
                    ArrayList arrayList = new ArrayList(response.body().getData());
                    retrofitResponse.onSuccess(arrayList);
                } else {
                    retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), "response not successFull");
                }
            }

            @Override
            public void onFailure(Call<GetForoshandehAmoozeshiResult> call, Throwable message) {
                Log.d("fail", "error message : " + message.getMessage());
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message.getMessage(), ForoshandehAmoozeshiDeviceNumberDAO.class.getSimpleName(), activityNameForLog, "fetchMessages", "onResponse");
                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), "response not successFull");
            }
        });

    }

    public boolean insertGroup(List<ForoshandehAmoozeshiModel> listForoshandehAmoozeshi)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (ForoshandehAmoozeshiModel foroshandehAmoozeshiModel : listForoshandehAmoozeshi)
            {
                ContentValues contentValues = modelToContentValue(foroshandehAmoozeshiModel);
                db.insertOrThrow(ForoshandehAmoozeshiModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , ForoshandehAmoozeshiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ForoshandehAmoozeshiDeviceNumberDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<ForoshandehAmoozeshiModel> getAll()
    {
        ArrayList<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ForoshandehAmoozeshiModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    foroshandehAmoozeshiModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ForoshandehAmoozeshiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "ForoshandehAmoozeshiDeviceNumberDAO" , "" , "getAll" , "");
        }
        return foroshandehAmoozeshiModels;
    }


    public boolean deleteAll()
    {
        String query = "delete from " + ForoshandehAmoozeshiModel.TableName();
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ForoshandehAmoozeshiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ForoshandehAmoozeshiDeviceNumberDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private ContentValues modelToContentValue(ForoshandehAmoozeshiModel foroshandehAmoozeshiModel)
    {
        ContentValues contentValues = new ContentValues();
        if (foroshandehAmoozeshiModel.getCcDeviceNumber() != null && foroshandehAmoozeshiModel.getCcDeviceNumber() > 0)
        {
            contentValues.put(ForoshandehAmoozeshiModel.COLUMN_ccDeviceNumber() , foroshandehAmoozeshiModel.getCcDeviceNumber());
        }
        contentValues.put(ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAmoozeshi() , foroshandehAmoozeshiModel.getDeviceNumberForoshandehAmoozeshi());
        contentValues.put(ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAsli() , foroshandehAmoozeshiModel.getDeviceNumberForoshandehAsli());
        return contentValues;
    }


    private ArrayList<ForoshandehAmoozeshiModel> cursorToModel(Cursor cursor)
    {
        ArrayList<ForoshandehAmoozeshiModel> foroshandehAmoozeshiModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ForoshandehAmoozeshiModel foroshandehAmoozeshiModel = new ForoshandehAmoozeshiModel();

            foroshandehAmoozeshiModel.setCcDeviceNumber(cursor.getInt(cursor.getColumnIndex(ForoshandehAmoozeshiModel.COLUMN_ccDeviceNumber())));
            foroshandehAmoozeshiModel.setDeviceNumberForoshandehAmoozeshi(cursor.getString(cursor.getColumnIndex(ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAmoozeshi())));
            foroshandehAmoozeshiModel.setDeviceNumberForoshandehAsli(cursor.getString(cursor.getColumnIndex(ForoshandehAmoozeshiModel.COLUMN_DeviceNumber_ForoshandehAsli())));

            foroshandehAmoozeshiModels.add(foroshandehAmoozeshiModel);
            cursor.moveToNext();
        }
        return foroshandehAmoozeshiModels;
    }

}
