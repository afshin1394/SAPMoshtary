package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.SpeedTestMVP;
import com.saphamrah.PubFunc.NetworkUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;

public class SpeedTestModel implements SpeedTestMVP.ModelOps
{


    private SpeedTestMVP.RequiredPresenterOps mPresenter;

    public SpeedTestModel(SpeedTestMVP.RequiredPresenterOps presenterOps)
    {
        mPresenter = presenterOps;
    }


    /*@Override
    public void testInternetSpeed()
    {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SpeedTestSocket speedTestSocket = new SpeedTestSocket();

                // add a listener to wait for speedtest completion and progress
                speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

                    @Override
                    public void onCompletion(SpeedTestReport report) {
                        // called when download/upload is complete
                        Log.d("speedTest" , "download in octet/s : " + report.getTransferRateOctet());
                        Log.d("speedTest" , "download in bit/s : " + report.getTransferRateBit());

                        mPresenter.onTestInternetSpeed(report.getTransferRateBit().toString() , "");

                    }

                    @Override
                    public void onError(SpeedTestError speedTestError, String errorMessage)
                    {
                        // called when a download/upload error occur
                    }

                    @Override
                    public void onProgress(float percent, SpeedTestReport report) {
                        // called to notify download/upload progress
                        Log.d("speedTest" , "progress : " + percent + "%");
                        Log.d("speedTest" , "download in octet/s : " + report.getTransferRateOctet());
                        Log.d("speedTest" , "download in bit/s : " + report.getTransferRateBit());
                    }
                });


                speedTestSocket.startDownload("http://ipv4.ikoula.testdebit.info/1M.iso");
            }
        });




        //new AsyncSpeedTest().execute();

    }*/


    @Override
    public void getInternetProvider()
    {
        PubFunc.NetworkUtils networkUtils = new PubFunc().new NetworkUtils();
        int internetType = networkUtils.checkInternetType(mPresenter.getAppContext());
        if (internetType == NetworkUtils.CELLULAR)
        {
            String operator = networkUtils.getOperatorName(mPresenter.getAppContext());
            if (operator.trim().equals(""))
            {
                mPresenter.onGetInternetProvider("");
            }
            else
            {
                mPresenter.onGetInternetProvider(operator);
            }
        }
        else if (internetType == NetworkUtils.WIFI)
        {
            mPresenter.onGetInternetProvider(mPresenter.getAppContext().getResources().getString(R.string.wifi));
        }
        else if (internetType == NetworkUtils.NO_CONNECTION)
        {
            mPresenter.onGetInternetProvider(mPresenter.getAppContext().getResources().getString(R.string.noInternetConnection));
        }
    }


    @Override
    public void setLogToDB(int logType , String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }



    @Override
    public void onDestroy() {

    }


}
