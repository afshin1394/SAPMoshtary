package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TaghiratVersionPPCModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetAllTaghiratVersionPPCResult;
import com.saphamrah.protos.VersionModificationGrpc;
import com.saphamrah.protos.VersionModificationReply;
import com.saphamrah.protos.VersionModificationReplyList;
import com.saphamrah.protos.VersionModificationRequest;

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

public class TaghiratVersionPPCDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public TaghiratVersionPPCDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "TaghiratVersionPPCDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            TaghiratVersionPPCModel.COLUMN_ccTaghirat(),
            TaghiratVersionPPCModel.COLUMN_Noe(),
            TaghiratVersionPPCModel.COLUMN_PocketPCVersion(),
            TaghiratVersionPPCModel.COLUMN_Sharh()
        };
    }

    public void fetchTaghiratVersionPPCGrpc(final Context context, final String activityNameForLog, final String noe, String currentVersion, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            //       ServerIpModel serverIpModel = new ServerIpModel();
            //       serverIpModel.setServerIp("192.168.80.181");
            serverIpModel.setPort("5000");

            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TaghiratVersionPPCDAO.class.getSimpleName(), activityNameForLog, "fetchTaghiratVersionPPCGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                VersionModificationGrpc.VersionModificationBlockingStub versionModificationBlockingStub = VersionModificationGrpc.newBlockingStub(managedChannel);
                VersionModificationRequest versionModificationRequest = VersionModificationRequest.newBuilder().setVersion(currentVersion).setType(noe).build();
                Callable<VersionModificationReplyList> versionModificationReplyListCallable = () -> versionModificationBlockingStub.getVersionModification(versionModificationRequest);
                RxAsync.makeObservable(versionModificationReplyListCallable)
                        .map(versionModificationReplyList ->  {
                            ArrayList<TaghiratVersionPPCModel> models = new ArrayList<>();
                            for (VersionModificationReply reply : versionModificationReplyList.getVersionModificationsList()) {
                                TaghiratVersionPPCModel model = new TaghiratVersionPPCModel();
                                model.setCcTaghirat(reply.getModificationsID());
                                model.setNoe(reply.getType());
                                model.setSharh(reply.getDescription());
                                model.setPocketPCVersion(reply.getPocketPCversion());
                                models.add(model);
                            }
                            return models;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<TaghiratVersionPPCModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<TaghiratVersionPPCModel> rptSanadModels) {
                                retrofitResponse.onSuccess(rptSanadModels);
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
        }catch (Exception exception){
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), TaghiratVersionPPCDAO.class.getSimpleName(), activityNameForLog, "fetchTaghiratVersionPPCGrpc", "");
            retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
        }

    }

    public void fetchTaghiratVersionPPC(final Context context, final String activityNameForLog, final String noe, String currentVersion, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TaghiratVersionPPCDAO.class.getSimpleName(), activityNameForLog, "fetchTaghiratVersionPPC", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetAllTaghiratVersionPPCResult> call = apiServiceGet.getAllTaghiratVersionPPC(noe , currentVersion);
            call.enqueue(new Callback<GetAllTaghiratVersionPPCResult>() {
                @Override
                public void onResponse(Call<GetAllTaghiratVersionPPCResult> call, Response<GetAllTaghiratVersionPPCResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, TaghiratVersionPPCDAO.class.getSimpleName(), "", "fetchTaghiratVersionPPC", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetAllTaghiratVersionPPCResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), TaghiratVersionPPCDAO.class.getSimpleName(), activityNameForLog, "fetchTaghiratVersionPPC", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), TaghiratVersionPPCDAO.class.getSimpleName(), activityNameForLog, "fetchTaghiratVersionPPC", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, TaghiratVersionPPCDAO.class.getSimpleName(), activityNameForLog, "fetchTaghiratVersionPPC", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), TaghiratVersionPPCDAO.class.getSimpleName(), activityNameForLog, "fetchTaghiratVersionPPC", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetAllTaghiratVersionPPCResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), TaghiratVersionPPCDAO.class.getSimpleName(), activityNameForLog, "fetchTaghiratVersionPPC", "onFailure");
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

    public boolean insertGroup(ArrayList<TaghiratVersionPPCModel> taghiratVersionPPCModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (TaghiratVersionPPCModel taghiratVersionPPCModel : taghiratVersionPPCModels)
            {
                ContentValues contentValues = modelToContentvalue(taghiratVersionPPCModel);
                db.insertOrThrow(TaghiratVersionPPCModel.TableName() , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , TaghiratVersionPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TaghiratVersionPPCDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public ArrayList<TaghiratVersionPPCModel> getAll()
    {
        ArrayList<TaghiratVersionPPCModel> taghiratVersionPPCModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TaghiratVersionPPCModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    taghiratVersionPPCModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TaghiratVersionPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TaghiratVersionPPCDAO" , "" , "getAll" , "");
        }
        return taghiratVersionPPCModels;
    }


    public String getNewFeaturesDesc()
    {
        String desc = "";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TaghiratVersionPPCModel.TableName(), new String[]{TaghiratVersionPPCModel.COLUMN_Sharh()}, null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        desc += cursor.getString(0) + "#";
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , TaghiratVersionPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TaghiratVersionPPCDAO" , "" , "getNewFeaturesDesc" , "");
        }
        return desc;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TaghiratVersionPPCModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , TaghiratVersionPPCModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "TaghiratVersionPPCDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(TaghiratVersionPPCModel taghiratVersionPPCModel)
    {
        ContentValues contentValues = new ContentValues();

        if (taghiratVersionPPCModel.getCcTaghirat() > 0)
        {
            contentValues.put(TaghiratVersionPPCModel.COLUMN_ccTaghirat() , taghiratVersionPPCModel.getCcTaghirat());
        }
        contentValues.put(TaghiratVersionPPCModel.COLUMN_Noe() , taghiratVersionPPCModel.getNoe());
        contentValues.put(TaghiratVersionPPCModel.COLUMN_PocketPCVersion() , taghiratVersionPPCModel.getPocketPCVersion());
        contentValues.put(TaghiratVersionPPCModel.COLUMN_Sharh() , taghiratVersionPPCModel.getSharh());

        return contentValues;
    }


    private ArrayList<TaghiratVersionPPCModel> cursorToModel(Cursor cursor)
    {
        ArrayList<TaghiratVersionPPCModel> taghiratVersionPPCModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            TaghiratVersionPPCModel taghiratVersionPPCModel = new TaghiratVersionPPCModel();

            taghiratVersionPPCModel.setCcTaghirat(cursor.getInt(cursor.getColumnIndex(TaghiratVersionPPCModel.COLUMN_ccTaghirat())));
            taghiratVersionPPCModel.setNoe(cursor.getInt(cursor.getColumnIndex(TaghiratVersionPPCModel.COLUMN_Noe())));
            taghiratVersionPPCModel.setPocketPCVersion(cursor.getString(cursor.getColumnIndex(TaghiratVersionPPCModel.COLUMN_PocketPCVersion())));
            taghiratVersionPPCModel.setSharh(cursor.getString(cursor.getColumnIndex(TaghiratVersionPPCModel.COLUMN_Sharh())));

            taghiratVersionPPCModels.add(taghiratVersionPPCModel);
            cursor.moveToNext();
        }
        return taghiratVersionPPCModels;
    }
    
}
