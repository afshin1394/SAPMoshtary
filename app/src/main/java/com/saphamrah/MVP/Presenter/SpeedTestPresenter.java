package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.SpeedTestMVP;
import com.saphamrah.MVP.Model.SpeedTestModel;
import com.saphamrah.MVP.View.SpeedTestActivity;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;

public class SpeedTestPresenter implements SpeedTestMVP.PresenterOps , SpeedTestMVP.RequiredPresenterOps
{

    private WeakReference<SpeedTestMVP.RequiredViewOps> mView;
    private SpeedTestMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public SpeedTestPresenter(SpeedTestMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new SpeedTestModel(this);
    }

    @Override
    public void onConfigurationChanged(SpeedTestMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void checkInternetProvider()
    {
        mModel.getInternetProvider();
    }

    @Override
    public void checkResult(double downloadSpeed, double uploadSpeed)
    {
        try
        {
            /*float download = Float.parseFloat(downloadSpeed);
            float upload = Float.parseFloat(uploadSpeed);*/
            int downloadRate = Constants.SPEED_TEST_BAD();
            int uploadRate = Constants.SPEED_TEST_BAD();
            if (downloadSpeed > 5.0)
            {
                downloadRate = Constants.SPEED_TEST_GOOD();
            }
            else if (downloadSpeed >= 2.0 && downloadSpeed <= 5.0)
            {
                downloadRate = Constants.SPEED_TEST_MEDIUM();
            }
            else if (downloadSpeed < 2.0)
            {
                downloadRate = Constants.SPEED_TEST_BAD();
            }

            if (uploadSpeed > 2.0)
            {
                uploadRate = Constants.SPEED_TEST_GOOD();
            }
            else if (uploadSpeed >= 1.0 && uploadSpeed <= 2.0)
            {
                uploadRate = Constants.SPEED_TEST_MEDIUM();
            }
            else if (uploadSpeed < 1.0)
            {
                uploadRate = Constants.SPEED_TEST_BAD();
            }

            if (downloadRate == Constants.SPEED_TEST_GOOD() && uploadRate == Constants.SPEED_TEST_GOOD())
            {
                mView.get().showGoodAlert();
            }
            else if ( (downloadRate == Constants.SPEED_TEST_MEDIUM() || uploadRate == Constants.SPEED_TEST_MEDIUM())
                    && (downloadRate != Constants.SPEED_TEST_BAD() && uploadRate != Constants.SPEED_TEST_BAD()) )
            {
                mView.get().showMediumAlert();
            }
            else if (downloadRate == Constants.SPEED_TEST_BAD() || uploadRate == Constants.SPEED_TEST_BAD())
            {
                mView.get().showBadAlert();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", SpeedTestActivity.class.getSimpleName(), "showSpeedTestResultAlert", "");
        }
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig) {

    }

    @Override
    public Context getAppContext() {
        return mView.get().getAppContext();
    }


    @Override
    public void onGetInternetProvider(String internetProvider)
    {
        mView.get().showInternetProvider(internetProvider);
    }



}
