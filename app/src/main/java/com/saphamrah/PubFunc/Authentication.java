package com.saphamrah.PubFunc;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.MessageBoxDAO;
import com.saphamrah.MVP.splash.SplashActivity;
import com.saphamrah.MVP.splash.SplashModel;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxCallback;
import com.saphamrah.Network.RxNetwork.RxHttpRequest;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.RxUtils.RxAsync;
import com.saphamrah.WebService.GrpcService.GrpcChannel;
import com.saphamrah.WebService.RxService.APIServiceRxjava;
import com.saphamrah.WebService.RxService.Response.DataResponse.CodeMelyResponse;
import com.saphamrah.protos.CheckPersonsGrpc;
import com.saphamrah.protos.CheckPersonsReply;
import com.saphamrah.protos.CheckPersonsRequest;
import com.saphamrah.protos.MessageBoxGrpc;
import com.saphamrah.protos.MessageBoxReply;
import com.saphamrah.protos.MessageBoxReplyList;
import com.saphamrah.protos.MessageBoxRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

import io.grpc.ManagedChannel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Authentication {

    private static final String CLASS_NAME = Authentication.class.getSimpleName();
    private static Authentication instance = null;

    public static Authentication getInstance() {
        if (instance == null) {
            instance = new Authentication();
        }

        return instance;
    }


    public String encrypt(String userIdentityCode,String hashKey) {

/**this method must be delete after test**/

        String threeRightCharDeviceModelName = getDeviceName().substring(0, 3);
        String fourRightCharUserIdenticalNumber = userIdentityCode.substring(0, 4);
        String threeLeftCharUserIdenticalNumber = userIdentityCode.substring(userIdentityCode.length() - 4, userIdentityCode.length() - 1);
        String fiveRightCharUniqueCode = hashKey.substring(5, 10);
        if (!checkIfFileExists())
        generateNoteOnRoot(Constants.SYSTEM_INFO,userIdentityCode+","+hashKey);

        String encryptedString = convertCharacters(threeRightCharDeviceModelName + fourRightCharUserIdenticalNumber + threeLeftCharUserIdenticalNumber + fiveRightCharUniqueCode);

        return encryptedString;

    }
    public void fetchUserHashKeyGrpc(Context context, String identityCode, RetrofitResponse retrofitResponse)
        {
            try {
                ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
                if (serverIpModel.getServerIp().trim().equals("") || serverIpModel.getPort().trim().equals("")) {
                    String message = "can't find server";
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, MessageBoxDAO.class.getSimpleName(),context.getClass().getSimpleName() , "fetchMessagesGrpc", "");
                    retrofitResponse.onFailed(Constants.RETROFIT_HTTP_ERROR(), message);
                } else {

                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                    ManagedChannel managedChannel = GrpcChannel.channel(serverIpModel);
                    CheckPersonsGrpc.CheckPersonsBlockingStub checkPersonsBlockingStub = CheckPersonsGrpc.newBlockingStub(managedChannel);
                    CheckPersonsRequest request = CheckPersonsRequest.newBuilder().setIdentityCode(identityCode).build();

                    Callable<CheckPersonsReply> replyListCallable = () -> checkPersonsBlockingStub.getCheckPersons(request);
                    RxAsync.makeObservable(replyListCallable)
                            .map(CheckPersonsReply::getOutput)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    compositeDisposable.add(d);
                                }

                                @Override
                                public void onNext(@NonNull String output) {
                                    ArrayList<String> outputs = new ArrayList<>();
                                    outputs.add(output);

                                    retrofitResponse.onSuccess(outputs);
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
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.getMessage(), Authentication.class.getSimpleName(), context.getClass().getSimpleName() , "fetchMessagesGrpc", "");
                retrofitResponse.onFailed(Constants.HTTP_EXCEPTION(), exception.getMessage());
            }
        }

    private void fetchUserHashKey(Context context, String identityCode, RxResponseHandler rxResponseHandler) {

        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(context);
        Log.i("fetchUserHashKey", "fetchUserHashKey: " + serverIpModel.getPort() + " " + serverIpModel.getServerIp());
        APIServiceRxjava apiServiceRxjava = RxHttpRequest.getInstance().getApiRx(serverIpModel);
        RxHttpRequest.getInstance().execute(apiServiceRxjava.checkAfrad(identityCode), SplashActivity.class.getSimpleName(), SplashModel.class.getSimpleName(), "authenticateUser", new RxCallback<CodeMelyResponse>() {
            @Override
            public void onStart(Disposable disposable) {
                rxResponseHandler.onStart(disposable);
            }

            @Override
            public void onSuccess(CodeMelyResponse response) {
                Log.i("fetchUserHashKey", "onSuccess: " + response.getMessage());
                if (response != null) {
                    if (response.getMessage() != null) {
                        switch (response.getMessage()) {
                            /**INVALID_IDENTITY_CODE**/
                            case "-1":
                                onError(context.getString(R.string.invalidIdentityCodeLength), "INVALID_IDENTITY_CODE_LENGTH");

                                break;
                            /**INVALID_IDENTITY_CODE_LENGTH**/
                            case "-2":
                                onError(context.getString(R.string.invalidIdentityCode), "INVALID_IDENTITY_CODE");

                                break;
                            /**VALID_IDENTITY_CODE**/
                            case "1":
                                ArrayList hashCode = new ArrayList();
                                hashCode.add(response.getHashCode());
                                rxResponseHandler.onSuccess(hashCode);
                                break;

                        }
                    } else {
                        onError(context.getString(R.string.invalidIdentityCodeLength), "INVALID_IDENTITY_CODE_LENGTH");
                    }
                }

            }

            @Override
            public void onError(String message, String type) {
                rxResponseHandler.onFailed(message, type);
            }
        });
    }

    public String convertCharacters(String string) {


        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '0' || string.charAt(i) == '2' || string.charAt(i) == '3' || string.charAt(i) == '5' || string.charAt(i) == '6' || string.charAt(i) == '7' || string.charAt(i) == '9') {
                Log.i("Authentication", "rawString: " + string);
                Character rawChar = string.charAt(i);
                int asci = ((int) rawChar) + 20;
                Log.i("Authentication", "convertCharactersAscii: " + asci + "   rawCharacters ascii: " + ((int) rawChar));
                char convertedChar = (char) asci;
                Log.i("Authentication", "convertCharacters: " + convertedChar + "  rawChar: " + rawChar);
                string = string.replace(rawChar, convertedChar);
            }

        }
        return string;

    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER.toUpperCase();
        String model = Build.MODEL.toUpperCase();

        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + model;
    }


    private String generateUniqueDeviceId() {
        String randomString = randomStringBuilder();
        return randomString;
    }

    private String randomStringBuilder() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();

        char tempChar;
        for (int i = 0; i < Constants.MAX_LENGTH_STRING_FILE; i++) {
            int randomAsciiCode = generator.nextInt(9) + 48;
            tempChar = ((char) randomAsciiCode);
            Log.i("Authentication", "tempChar: " + tempChar + " tempCharAsci" + ((int) tempChar));
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void generateNoteOnRoot(String fileName,String sBody) {
        try {
            File systemFile = new File(Environment.getExternalStorageDirectory(), Constants.File_Path);
            if (!systemFile.exists())
            {
                systemFile.mkdirs();
            }
            File info = new File(systemFile, fileName);
            FileWriter writer = new FileWriter(info);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Authentication", "generateNoteOnRoot: "+e.getMessage());
        }
    }

    private String convertStreamToString(InputStream is)  {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        }
        catch (Exception exception){
            Log.i("convertStreamToString", "convertStreamToString: "+exception.getMessage());
            return null;
        }

    }

    private String getStringFromFile(String filePath) {
        File file = new File(filePath);
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("Authentication", "getStringFromFile: "+e.getMessage());
            PubFunc.Logger logger = new PubFunc().new Logger();
           // logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), e.getMessage(), CLASS_NAME, SplashActivity.class.getSimpleName(), "encrypt", "getStringFromFile");
        }
        String ret = null;
        try {
            ret = convertStreamToString(fin);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Authentication", "getStringFromFile: "+e.getMessage());
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), e.getMessage(), CLASS_NAME, SplashActivity.class.getSimpleName(), "encrypt", "getStringFromFile");
        }
        /**Make sure you close all streams.**/
        if (fin!=null) {
            try {
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), e.getMessage(), CLASS_NAME, SplashActivity.class.getSimpleName(), "encrypt", "getStringFromFile");
            }
        }
        return ret;
    }

    public String getIdentityCodeWithHashKey(){
        return  getStringFromFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+Constants.File_Path+"/"+Constants.SYSTEM_INFO );
    }

    private void removeFileIfExist() {

        File file = new File(Environment.getExternalStorageDirectory() + Constants.File_Path);
        if (file.exists())
            file.delete();
    }

    public boolean checkIfFileExists() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+Constants.File_Path+"/"+Constants.SYSTEM_INFO);
        Log.d("checkAuthentication",file.getPath());
        return file.exists();
    }


}
