package com.saphamrah.PubFunc;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.MVP.splash.SplashActivity;
import com.saphamrah.Utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

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

    private String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
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
        try {
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(BaseApplication.getContext(), Constants.LOG_EXCEPTION(), e.getMessage(), CLASS_NAME, SplashActivity.class.getSimpleName(), "encrypt", "getStringFromFile");
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
