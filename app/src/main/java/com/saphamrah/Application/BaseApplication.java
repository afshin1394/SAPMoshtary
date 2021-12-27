package com.saphamrah.Application;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.samiei.central.exceptionHandling.Crash;
import com.samiei.central.exceptionHandling.ExceptionHandlerApplication;
import com.saphamrah.PubFunc.Logger;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.List;

public class BaseApplication extends ExceptionHandlerApplication {

    private static BaseApplication instance = null;
    private static Context context;
    private static Activity currentActivity;
    public static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    protected void exceptionCached(Crash crash, List<Activity> activities) {
        Log.i("exceptionCached", "exceptionCached: "+crash);
        Logger logger = new Logger();
        logger.insertLogToDBGlobal(getContext(),Constants.LOG_EXCEPTION(),crash.getErrorMessage(),crash.getCrashClass(),crash.getCrashFile(),crash.getCrashMethod(),crash.getCrashFile(),crash.getCrashLine(),crash.getCrashStackTrace());
        for (Activity activity : activities) {
            Log.i("exceptionCached", "exceptionCached: "+activity.getLocalClassName());
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());


    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initBaseExceptionAlerts();
    }

    private void initBaseExceptionAlerts() {
        BaseApplication.defaultMethodName = getString(R.string.invalid);
        BaseApplication.defaultClassName = getString(R.string.invalid);
        BaseApplication.defaultFileName = getString(R.string.invalid);
        BaseApplication.defaultLineNumber = getString(R.string.invalid);
        BaseApplication.defaultLineNumber = getString(R.string.unAvailable);
        BaseApplication.defaultErrorStackTrace = getString(R.string.unAvailable);
    }

    /**
     * get current context anywhere in app
     * @return just use BaseApplication.getContext
     */
    public static Context getContext() {
        if (currentActivity != null) {
            return currentActivity;
        }
        return context;
    }

    public static Context getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    public static synchronized BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }



}