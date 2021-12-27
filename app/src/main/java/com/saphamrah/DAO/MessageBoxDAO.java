package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.APIServiceGet;

import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.ServiceResponse.GetMessageBoxResult;
import com.saphamrah.protos.MessageBoxGrpc;
import com.saphamrah.protos.MessageBoxReply;
import com.saphamrah.protos.MessageBoxReplyList;
import com.saphamrah.protos.MessageBoxRequest;

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

public class MessageBoxDAO
{

    private DBHelper dbHelper;
    private Context context;
    private static final String CLASS_NAME = "MessageBoxDAO";


    public MessageBoxDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MessageBoxDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            MessageBoxModel.COLUMN_ccMessage(),
            MessageBoxModel.COLUMN_Title(),
            MessageBoxModel.COLUMN_Message(),
            MessageBoxModel.COLUMN_NoeMessage(),
            MessageBoxModel.COLUMN_ccUser(),
            MessageBoxModel.COLUMN_Active(),
            MessageBoxModel.COLUMN_Tarikh(),
            MessageBoxModel.COLUMN_ccForoshandeh(),
            MessageBoxModel.COLUMN_ccMamorPakhsh(),
            MessageBoxModel.COLUMN_StatusForoshandeh(),
            MessageBoxModel.COLUMN_ActiveForoshandeh(),
            MessageBoxModel.COLUMN_NotificationForoshandeh()
        };
    }


    public void fetchMessagesGrpc(final Context context, final String activityNameForLog,final String ccForoshandeh, String ccMamorPakhsh, final RetrofitResponse retrofitResponse)
    {
        try {
            ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
            if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                String message = "can't find server";
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MessageBoxDAO.class.getSimpleName(), activityNameForLog, "fetchMessagesGrpc", "");
                retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
            } else {

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                MessageBoxGrpc.MessageBoxBlockingStub messageBoxBlockingStub = MessageBoxGrpc.newBlockingStub(managedChannel);
                MessageBoxRequest request = MessageBoxRequest.newBuilder().setSalesManID(ccForoshandeh).setDistributerID(ccMamorPakhsh).build();

                Callable<MessageBoxReplyList> replyListCallable = () -> messageBoxBlockingStub.getMessageBox(request);
                RxAsync.makeObservable(replyListCallable)

                        .map(replyList -> {
                            ArrayList<MessageBoxModel> models = new ArrayList<>();
                            for (MessageBoxReply reply : replyList.getMessageBoxsList()) {
                                MessageBoxModel model = new MessageBoxModel();

                                model.setCcMessage(reply.getMessageID());
                                model.setTitle(reply.getTitle());
                                model.setMessage(reply.getMessage());
                                model.setNoeMessage(reply.getMessageType());
                                model.setCcUser(reply.getUserID());
                                model.setActive(reply.getActive());
                                model.setTarikh(reply.getDate());
                                model.setCcForoshandeh(reply.getSalesManID());
                                model.setCcMamorPakhsh(reply.getDistributerID());
                                model.setStatusForoshandeh(reply.getSalesManStatus());
                                model.setActiveForoshandeh(reply.getActiveSalesMan());
                                model.setNotificationForoshandeh(reply.getSalesManNotification());

                                models.add(model);
                            }

                            return models;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<MessageBoxModel>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ArrayList<MessageBoxModel> models) {
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

    public void fetchMessages(final Context context, final String activityNameForLog,final String ccForoshandeh, String ccMamorPakhsh, final RetrofitResponse retrofitResponse)
    {
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals(""))
        {
            String message = "can't find server";
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMessages", "");
            retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR() , message);
        }
        else
        {
            APIServiceGet apiServiceGet = ApiClientGlobal.getInstance().getClientServiceGet(serverIpModel);
            Call<GetMessageBoxResult> call = apiServiceGet.getMessageBoxResult(ccForoshandeh, ccMamorPakhsh);
            call.enqueue(new Callback<GetMessageBoxResult>() {
                @Override
                public void onResponse(Call<GetMessageBoxResult> call, Response<GetMessageBoxResult> response)
                {
                    try
                    {
                        if (response.raw().body() != null)
                        {
                            long contentLength = response.raw().body().contentLength();
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_RESPONSE_CONTENT_LENGTH(), "content-length(byte) = " + contentLength, CLASS_NAME, "", "fetchMessages", "onResponse");
                        }
                    }
                    catch (Exception e){e.printStackTrace();}
                    try
                    {
                        if (response.isSuccessful())
                        {
                            GetMessageBoxResult result = response.body();
                            if (result != null)
                            {
                                if (result.getSuccess())
                                {
                                    retrofitResponse.onSuccess(result.getData());
                                }
                                else
                                {
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), result.getMessage(), CLASS_NAME, activityNameForLog, "fetchMessages", "onResponse");
                                    retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), result.getMessage());
                                }
                            }
                            else
                            {
                                String endpoint = getEndpoint(call);
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", context.getResources().getString(R.string.resultIsNull), endpoint), CLASS_NAME, activityNameForLog, "fetchMessages", "onResponse");
                                retrofitResponse.onFailed(Constants.RETROFIT_RESULT_IS_NULL(), context.getResources().getString(R.string.resultIsNull));
                            }
                        }
                        else
                        {
                            String endpoint = getEndpoint(call);
                            String message = String.format("error body : %1$s , code : %2$s * %3$s" , response.message() , response.code(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, CLASS_NAME, activityNameForLog, "fetchMessages", "onResponse");
                            retrofitResponse.onFailed(Constants.RETROFIT_NOT_SUCCESS_MESSAGE(), message);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), CLASS_NAME, activityNameForLog, "fetchMessages", "onResponse");
                        retrofitResponse.onFailed(Constants.RETROFIT_EXCEPTION() , exception.toString());
                    }
                }

                @Override
                public void onFailure(Call<GetMessageBoxResult> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, activityNameForLog, "fetchMessages", "onFailure");
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

    public boolean insertGroup(ArrayList<MessageBoxModel> messageBoxModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MessageBoxModel messageBoxModel : messageBoxModels)
            {
                ContentValues contentValues = modelToContentvalue(messageBoxModel);
                db.insertOrThrow(MessageBoxModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            String message = context.getResources().getString(R.string.errorGroupInsert , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<MessageBoxModel> getAll()
    {
        ArrayList<MessageBoxModel> messageBoxModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MessageBoxModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    messageBoxModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "getAll" , "");
        }
        return messageBoxModels;
    }


    public MessageBoxModel getByccMessage(int ccMessage)
    {
        MessageBoxModel messageBoxModel = new MessageBoxModel();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select * from " + MessageBoxModel.TableName() + " where " + MessageBoxModel.COLUMN_ccMessage() + " = " + ccMessage + " limit 1";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    messageBoxModel = cursorToModel(cursor).get(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "getByccMessage" , "");
        }
        return messageBoxModel;
    }


    public ArrayList<MessageBoxModel> getAllUnread()
    {
        ArrayList<MessageBoxModel> messageBoxModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MessageBoxModel.TableName(), allColumns(), MessageBoxModel.COLUMN_StatusForoshandeh() + " = 0" , null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    messageBoxModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "getAll" , "");
        }
        return messageBoxModels;
    }


    public ArrayList<MessageBoxModel> getAllForSendNotification()
    {
        ArrayList<MessageBoxModel> messageBoxModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MessageBoxModel.TableName(), allColumns(), MessageBoxModel.COLUMN_NotificationForoshandeh() + " = 0" , null, null, null, null);
            if (cursor != null)
            {
                Log.d("message" , "cursor count : " + cursor.getCount());
                if (cursor.getCount() > 0)
                {
                    messageBoxModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "getAll" , "");
        }
        return messageBoxModels;
    }

    public int getUnreadCount()
    {
        int count = 0;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //select count(ccMessage) from MessageBox where StatusForoshandeh = 0
            String query = "select count(" + MessageBoxModel.COLUMN_ccMessage() + ") from " + MessageBoxModel.TableName() + " where " + MessageBoxModel.COLUMN_StatusForoshandeh() + " = 0";
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "getUnreadCount" , "");
        }
        return count;
    }


    public void updateNotifStatus(String ccMessages)
    {
        String query = "update " + MessageBoxModel.TableName() + " set " + MessageBoxModel.COLUMN_NotificationForoshandeh() + " = 1 where " + MessageBoxModel.COLUMN_ccMessage() + " in (" + ccMessages + ")";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "updateNotifStatus" , "");
        }
    }

    public void updateStatusMessage(int ccMessages)
    {
        String query = "update " + MessageBoxModel.TableName() + " set " + MessageBoxModel.COLUMN_StatusForoshandeh() + " = 1 where " + MessageBoxModel.COLUMN_ccMessage() + " = " + ccMessages;
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            db.execSQL(query);
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorUpdate , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "updateStatusMessage" , "");
        }
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MessageBoxModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MessageBoxModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MessageBoxDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MessageBoxModel messageBoxModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MessageBoxModel.COLUMN_ccMessage() , messageBoxModel.getCcMessage());
        contentValues.put(MessageBoxModel.COLUMN_Title() , messageBoxModel.getTitle());
        contentValues.put(MessageBoxModel.COLUMN_Message() , messageBoxModel.getMessage());
        contentValues.put(MessageBoxModel.COLUMN_NoeMessage() , messageBoxModel.getNoeMessage());
        contentValues.put(MessageBoxModel.COLUMN_ccUser() , messageBoxModel.getCcUser());
        contentValues.put(MessageBoxModel.COLUMN_Active() , messageBoxModel.getActive());
        contentValues.put(MessageBoxModel.COLUMN_Tarikh() , messageBoxModel.getTarikh());
        contentValues.put(MessageBoxModel.COLUMN_ccForoshandeh() , messageBoxModel.getCcForoshandeh());
        contentValues.put(MessageBoxModel.COLUMN_ccMamorPakhsh() , messageBoxModel.getCcMamorPakhsh());
        contentValues.put(MessageBoxModel.COLUMN_StatusForoshandeh() , messageBoxModel.getStatusForoshandeh());
        contentValues.put(MessageBoxModel.COLUMN_ActiveForoshandeh() , messageBoxModel.getActiveForoshandeh());
        contentValues.put(MessageBoxModel.COLUMN_NotificationForoshandeh() , messageBoxModel.getNotificationForoshandeh());

        return contentValues;
    }


    private ArrayList<MessageBoxModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MessageBoxModel> messageBoxModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MessageBoxModel messageBoxModel = new MessageBoxModel();

            messageBoxModel.setCcMessage(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_ccMessage())));
            messageBoxModel.setTitle(cursor.getString(cursor.getColumnIndex(MessageBoxModel.COLUMN_Title())));
            messageBoxModel.setMessage(cursor.getString(cursor.getColumnIndex(MessageBoxModel.COLUMN_Message())));
            messageBoxModel.setNoeMessage(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_NoeMessage())));
            messageBoxModel.setCcUser(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_ccUser())));
            messageBoxModel.setActive(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_Active())));
            messageBoxModel.setTarikh(cursor.getString(cursor.getColumnIndex(MessageBoxModel.COLUMN_Tarikh())));
            messageBoxModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_ccForoshandeh())));
            messageBoxModel.setCcMamorPakhsh(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_ccMamorPakhsh())));
            messageBoxModel.setStatusForoshandeh(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_StatusForoshandeh())));
            messageBoxModel.setActiveForoshandeh(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_ActiveForoshandeh())));
            messageBoxModel.setNotificationForoshandeh(cursor.getInt(cursor.getColumnIndex(MessageBoxModel.COLUMN_NotificationForoshandeh())));

            messageBoxModels.add(messageBoxModel);
            cursor.moveToNext();
        }
        return messageBoxModels;
    }

}
