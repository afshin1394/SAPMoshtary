package com.saphamrah.PubFunc;

import android.content.Context;
import android.util.Log;

import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.LogPPCDAO;
import com.saphamrah.Model.EmailLogPPCModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Network.AsyncTaskResponse;
import com.saphamrah.Shared.EmailLogPPCShared;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public  class Logger {

   private static Logger instance = null;

   public static Logger getInstance(){
       if (instance == null){
           instance = new Logger();
       }
       return instance;
   }

        public boolean insertLogToDB(Context context, int logType, String logMessage, String logClass, String logActivity, String logFunctionParent, String logFunctionChild) {
            int ccAfrad;
            Log.d("insertLogToDB", "imei:" + new PubFunc().new DeviceInfo().getIMEI(context) + "-" + logType + "-" + logMessage + "-" + new PubFunc().new DeviceInfo().getAPILevel(context));
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(context);
            LogPPCDAO logPPCDAO = new LogPPCDAO(context);
            ccAfrad = foroshandehMamorPakhshDAO.getCCAfrad();
            LogPPCModel logModel = new LogPPCModel();
            logModel.setCcAfrad(ccAfrad);
            logModel.setIMEI(new PubFunc().new DeviceInfo().getIMEI(context));
            logModel.setLogType(logType);
            logModel.setLogMessage(logMessage);
            logModel.setLogClass(logClass);
            logModel.setLogActivity(logActivity);
            logModel.setLogFunctionParent(logFunctionParent);
            logModel.setLogFunctionChild(logFunctionChild);
            logModel.setLogDate(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(Calendar.getInstance().getTime()));
            logModel.setExtraProp_IsOld(0);
            logModel.setAndroidAPI(new PubFunc().new DeviceInfo().getAPILevel(context));
            return logPPCDAO.insert(logModel);
        }




        public boolean sendLogToServer(Context context, String logMessage, String logClass, String logActivity, String logFunctionParent, String logFunctionChild) {
            LogPPCModel logModel = new LogPPCModel();
            logModel.setCcAfrad(new ForoshandehMamorPakhshDAO(context).getCCAfrad());
            logModel.setIMEI(new PubFunc().new DeviceInfo().getIMEI(context));
            logModel.setLogMessage(logMessage);
            logModel.setLogClass(logClass);
            logModel.setLogActivity(logActivity);
            logModel.setLogFunctionParent(logFunctionParent);
            logModel.setLogFunctionChild(logFunctionChild);
            logModel.setLogDate(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(Calendar.getInstance().getTime()));
            logModel.setExtraProp_IsOld(0);
            logModel.setAndroidAPI(new PubFunc().new DeviceInfo().getAPILevel(context));
            return true;
        }


        public void sendLogToMail(Context context, String logMessage, String logClass, String logActivity, String logFunctionParent, String logFunctionChild, final AsyncTaskResponse callback) {
            EmailLogPPCShared emailLogPPCShared = new EmailLogPPCShared(context);
            String email = emailLogPPCShared.getString(emailLogPPCShared.EMAIL(), "");
            String pass = emailLogPPCShared.getString(emailLogPPCShared.PASSWORD(), "");

            Log.d("mail", "email : " + email);
            Log.d("mail", "encrypted pass : " + pass);

            if (!email.trim().equals("") && !pass.trim().equals("")) {
                PubFunc.Encryption encryption = new PubFunc().new Encryption();
                String decryptedPass = encryption.decrypt(context, pass);

                Log.d("mail", "decryptedPass pass : " + decryptedPass);

                if (!decryptedPass.trim().equals("")) {
                    PubFunc.SendMail sendMail = new PubFunc().new SendMail();
                    String IMEI = new PubFunc().new DeviceInfo().getIMEI(context);
                    int androidAPI = new PubFunc().new DeviceInfo().getAPILevel(context);
                    String todayDate = new PubFunc().new DateUtils().todayDateGregorian();

                    String emailBody = " Message : " + logMessage + "\n Class : " + logClass + "\n Activity : " + logActivity +
                            "\n FunctionParent : " + logFunctionParent + "\n Function Child : " + logFunctionChild +
                            "\n IMEI : " + IMEI + "\n date : " + todayDate + "\n android API : " + androidAPI;
                    sendMail.sendGmail(email, decryptedPass, "LOG FOR SAP APPLICATION", emailBody, EmailLogPPCModel.MAIL_RECEIVER, new AsyncTaskResponse() {
                        @Override
                        public void processFinished(Object object) {
                            callback.processFinished(object);
                        }
                    });
                } else {
                    callback.processFinished(false);
                }
            } else {
                callback.processFinished(false);
            }
        }



}
