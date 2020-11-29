package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saphamrah.BaseMVP.SpeedTestMVP;
import com.saphamrah.MVP.Presenter.SpeedTestPresenter;
import com.saphamrah.Network.GetSpeedTestHostsHandler;
import com.saphamrah.Network.HttpDownloadSpeedTest;
import com.saphamrah.Network.HttpUploadSpeedTest;
import com.saphamrah.Network.PingSpeedTest;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PieChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import me.anwarshahriar.calligrapher.Calligrapher;


public class SpeedTestActivity extends AppCompatActivity implements SpeedTestMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , SpeedTestActivity.this);
    SpeedTestMVP.PresenterOps mPresenter;

    TextView lblOperator;
    TextView lblServerName;
    Button btnStart;
    ImageView imgBack;
    CustomAlertDialog customAlertDialog;

    static int position = 0;
    static int lastPosition = 0;
    GetSpeedTestHostsHandler getSpeedTestHostsHandler = null;
    HashSet<String> tempBlackList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_test);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();


        lblOperator = (TextView)findViewById(R.id.lblOperator);
        lblServerName = (TextView)findViewById(R.id.lblServer);
        btnStart = (Button)findViewById(R.id.btnStart);
        imgBack = (ImageView)findViewById(R.id.imgBack);

        customAlertDialog = new CustomAlertDialog(SpeedTestActivity.this);

        final DecimalFormat dec = new DecimalFormat("#.##");
        btnStart.setText(getResources().getString(R.string.start));

        tempBlackList = new HashSet<>();

        mPresenter.checkInternetProvider();


        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                mPresenter.checkInternetProvider();

                btnStart.setEnabled(false);

                getSpeedTestHostsHandler = new GetSpeedTestHostsHandler();
                getSpeedTestHostsHandler.start();

                //Restart test icin eger baglanti koparsa
                if (getSpeedTestHostsHandler == null)
                {
                    getSpeedTestHostsHandler = new GetSpeedTestHostsHandler();
                    getSpeedTestHostsHandler.start();
                }

                new Thread(new Runnable()
                {
                    RotateAnimation rotate;
                    ImageView barImageView = (ImageView) findViewById(R.id.imgGaugePointer);
                    TextView lblPing = (TextView) findViewById(R.id.lblPing);
                    TextView lblDownload = (TextView) findViewById(R.id.lblDownload);
                    TextView lblUpload = (TextView) findViewById(R.id.lblUpload);

                    @Override
                    public void run()
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnStart.setText(getResources().getString(R.string.searchingBestServer));
                            }
                        });

                        //Get egcodes.speedtest hosts
                        int timeCount = 600; //1min
                        while (!getSpeedTestHostsHandler.isFinished())
                        {
                            timeCount--;
                            try
                            {
                                Thread.sleep(100);
                            }
                            catch (InterruptedException e)
                            {
                            }
                            if (timeCount <= 0)
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        CustomAlertDialog customAlertDialog = new CustomAlertDialog(SpeedTestActivity.this);
                                        customAlertDialog.showToast(SpeedTestActivity.this, getResources().getString(R.string.noInternetConnection), Constants.FAILED_MESSAGE(), Toast.LENGTH_SHORT);

                                        String message = String.format("%1$s : %2$s \n" , getResources().getString(R.string.status) , getResources().getString(R.string.noInternetConnection)) +
                                                getResources().getString(R.string.operator , lblOperator.getText().toString()) +
                                                String.format("\n %1$s : %2$s" , getResources().getString(R.string.server) , lblServerName.getText().toString())  +
                                                String.format("\n %1$s : %2$s" , getResources().getString(R.string.ping) , lblPing.getText().toString()) +
                                                String.format("\n %1$s : %2$s" , getResources().getString(R.string.download) , lblDownload.getText().toString()) +
                                                String.format("\n %1$s : %2$s" , getResources().getString(R.string.upload) , lblUpload.getText().toString());
                                        PubFunc.Logger logger = new PubFunc().new Logger();
                                        logger.insertLogToDB(SpeedTestActivity.this,Constants.LOG_SPEED_TEST(), message, "", SpeedTestActivity.class.getSimpleName(), "ButtonClickListener" , "");

                                        btnStart.setEnabled(true);
                                        btnStart.setText(getResources().getString(R.string.start));
                                        mPresenter.checkResult(0.0 , 0.0);
                                    }
                                });
                                getSpeedTestHostsHandler = null;
                                return;
                            }
                        }

                        //Find closest server
                        HashMap<Integer , String> mapKey = getSpeedTestHostsHandler.getMapKey();
                        HashMap<Integer , List<String>> mapValue = getSpeedTestHostsHandler.getMapValue();
                        /*double selfLat = getSpeedTestHostsHandler.getSelfLat();
                        double selfLon = getSpeedTestHostsHandler.getSelfLon();*/
                        //double tmp = 19349458;
                        //double dist = 0.0;
                        int findServerIndex = 0;
                        for (int index : mapKey.keySet())
                        {
                            if (tempBlackList.contains(mapValue.get(index).get(5)))
                            {
                                continue;
                            }

                            /*Location source = new Location("Source");
                            source.setLatitude(35.736748);
                            source.setLongitude(51.336230);

                            List<String> ls = mapValue.get(index);
                            Location dest = new Location("Dest");
                            dest.setLatitude(Double.parseDouble(ls.get(0)));
                            dest.setLongitude(Double.parseDouble(ls.get(1)));*/



                            /*double distance = source.distanceTo(dest);
                            Log.d("tag", "source lat:"+source.getLatitude()+" - sourc long:"+source.getLongitude() + "dest lat:"+dest.getLatitude()+"- dest long:"+dest.getLongitude() +" - distance:" + distance +"-tmp: "+ tmp + "-sponser:"+mapValue.get(index).get(5));
                            */
                            int Rand = new Random().nextInt(mapKey.keySet().size());
                            ;                           Log.d("tag rand",Rand+"" + ", size : " + mapKey.keySet().size());
                            // if (tmp > distance ) {
                            //tmp = distance;
                            //dist = distance;
                            findServerIndex = Rand;//index;
                            //}
                        }
                        String uploadAddr = mapKey.get(findServerIndex);
                        final List<String> info = mapValue.get(findServerIndex);
                        //final double distance = dist;

                        if (info == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(SpeedTestActivity.this);
                                    customAlertDialog.showToast(SpeedTestActivity.this, getResources().getString(R.string.cantFindServer), Constants.FAILED_MESSAGE(), Toast.LENGTH_LONG);

                                    String message = String.format("%1$s : %2$s \n" , getResources().getString(R.string.status) , getResources().getString(R.string.cantFindServer)) +
                                            getResources().getString(R.string.operator , lblOperator.getText().toString()) +
                                            String.format("\n %1$s : %2$s" , getResources().getString(R.string.server) , lblServerName.getText().toString())  +
                                            String.format("\n %1$s : %2$s" , getResources().getString(R.string.ping) , lblPing.getText().toString()) +
                                            String.format("\n %1$s : %2$s" , getResources().getString(R.string.download) , lblDownload.getText().toString()) +
                                            String.format("\n %1$s : %2$s" , getResources().getString(R.string.upload) , lblUpload.getText().toString());
                                    PubFunc.Logger logger = new PubFunc().new Logger();
                                    logger.insertLogToDB(SpeedTestActivity.this,Constants.LOG_SPEED_TEST(), message, "", SpeedTestActivity.class.getSimpleName(), "ButtonClickListener" , "");
                                }
                            });
                            return;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //btnStart.setText(String.format("Host Location: %s [Distance: %s km]", info.get(2)+"-"+info.get(5), new DecimalFormat("#.##").format(distance / 1000)));
                                lblServerName.setText(String.format("%1$s - %2$s" , info.get(2) , info.get(5)));
                                btnStart.setText(getResources().getString(R.string.testing));
                            }
                        });

                        //Init Ping graphic
                        final LinearLayout chartPing = (LinearLayout) findViewById(R.id.chartPing);
                        XYSeriesRenderer pingRenderer = new XYSeriesRenderer();
                        XYSeriesRenderer.FillOutsideLine pingFill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                        pingFill.setColor(getResources().getColor(R.color.colorChart));
                        pingRenderer.addFillOutsideLine(pingFill);
                        pingRenderer.setDisplayChartValues(false);
                        pingRenderer.setShowLegendItem(false);
                        pingRenderer.setColor(getResources().getColor(R.color.colorChart));
                        pingRenderer.setLineWidth(5);
                        final XYMultipleSeriesRenderer multiPingRenderer = new XYMultipleSeriesRenderer();
                        multiPingRenderer.setXLabels(0);
                        multiPingRenderer.setYLabels(0);
                        multiPingRenderer.setZoomEnabled(false);
                        multiPingRenderer.setXAxisColor(getResources().getColor(R.color.colorDivider));
                        multiPingRenderer.setYAxisColor(getResources().getColor(R.color.colorDivider));
                        multiPingRenderer.setPanEnabled(false, false);
                        multiPingRenderer.setZoomButtonsVisible(false);
                        multiPingRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
                        multiPingRenderer.addSeriesRenderer(pingRenderer);

                        //Init Download graphic
                        final LinearLayout chartDownload = (LinearLayout) findViewById(R.id.chartDownload);
                        XYSeriesRenderer downloadRenderer = new XYSeriesRenderer();
                        XYSeriesRenderer.FillOutsideLine downloadFill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                        downloadFill.setColor(getResources().getColor(R.color.colorChart));
                        downloadRenderer.addFillOutsideLine(downloadFill);
                        downloadRenderer.setDisplayChartValues(false);
                        downloadRenderer.setColor(getResources().getColor(R.color.colorChart));
                        downloadRenderer.setShowLegendItem(false);
                        downloadRenderer.setLineWidth(5);
                        final XYMultipleSeriesRenderer multiDownloadRenderer = new XYMultipleSeriesRenderer();
                        multiDownloadRenderer.setXLabels(0);
                        multiDownloadRenderer.setYLabels(0);
                        multiDownloadRenderer.setZoomEnabled(false);
                        multiDownloadRenderer.setXAxisColor(getResources().getColor(R.color.colorDivider));
                        multiDownloadRenderer.setYAxisColor(getResources().getColor(R.color.colorDivider));
                        multiDownloadRenderer.setPanEnabled(false, false);
                        multiDownloadRenderer.setZoomButtonsVisible(false);
                        multiDownloadRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
                        multiDownloadRenderer.addSeriesRenderer(downloadRenderer);

                        //Init Upload graphic
                        final LinearLayout chartUpload = (LinearLayout) findViewById(R.id.chartUpload);
                        XYSeriesRenderer uploadRenderer = new XYSeriesRenderer();
                        XYSeriesRenderer.FillOutsideLine uploadFill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                        uploadFill.setColor(getResources().getColor(R.color.colorChart));
                        uploadRenderer.addFillOutsideLine(uploadFill);
                        uploadRenderer.setDisplayChartValues(false);
                        uploadRenderer.setColor(getResources().getColor(R.color.colorChart));
                        uploadRenderer.setShowLegendItem(false);
                        uploadRenderer.setLineWidth(5);
                        final XYMultipleSeriesRenderer multiUploadRenderer = new XYMultipleSeriesRenderer();
                        multiUploadRenderer.setXLabels(0);
                        multiUploadRenderer.setYLabels(0);
                        multiUploadRenderer.setZoomEnabled(false);
                        multiUploadRenderer.setXAxisColor(getResources().getColor(R.color.colorDivider));
                        multiUploadRenderer.setYAxisColor(getResources().getColor(R.color.colorDivider));
                        multiUploadRenderer.setPanEnabled(false, false);
                        multiUploadRenderer.setZoomButtonsVisible(false);
                        multiUploadRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
                        multiUploadRenderer.addSeriesRenderer(uploadRenderer);

                        //Reset value, graphics
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lblPing.setText(String.format("0 %1$s" , getResources().getString(R.string.Mbps)));
                                chartPing.removeAllViews();
                                lblDownload.setText(String.format("0 %1$s" , getResources().getString(R.string.Mbps)));
                                chartDownload.removeAllViews();
                                lblUpload.setText(String.format("0 %1$s" , getResources().getString(R.string.Mbps)));
                                chartUpload.removeAllViews();
                            }
                        });
                        final List<Double> pingRateList = new ArrayList<>();
                        final List<Double> downloadRateList = new ArrayList<>();
                        final List<Double> uploadRateList = new ArrayList<>();
                        Boolean pingTestStarted = false;
                        Boolean pingTestFinished = false;
                        Boolean downloadTestStarted = false;
                        Boolean downloadTestFinished = false;
                        Boolean uploadTestStarted = false;
                        Boolean uploadTestFinished = false;

                        //Init Test
                        final PingSpeedTest pingTest = new PingSpeedTest(SpeedTestActivity.this, info.get(6).replace(":8080", ""), 6);
                        final HttpDownloadSpeedTest downloadTest = new HttpDownloadSpeedTest(SpeedTestActivity.this, uploadAddr.replace(uploadAddr.split("/")[uploadAddr.split("/").length - 1], ""));
                        final HttpUploadSpeedTest uploadTest = new HttpUploadSpeedTest(SpeedTestActivity.this, uploadAddr);


                        //Tests
                        while (true)
                        {
                            if (!pingTestStarted) {
                                pingTest.start();
                                pingTestStarted = true;
                            }
                            if (pingTestFinished && !downloadTestStarted) {
                                downloadTest.start();
                                downloadTestStarted = true;
                            }
                            if (downloadTestFinished && !uploadTestStarted) {
                                uploadTest.start();
                                uploadTestStarted = true;
                            }


                            //Ping Test
                            if (pingTestFinished) {
                                //Failure
                                if (pingTest.getAvgRtt() == 0)
                                {
                                    System.out.println("Ping error...");
                                }
                                else
                                {
                                    //Success
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run()
                                        {
                                            lblPing.setText(String.format("%1$s %2$s" , dec.format(pingTest.getAvgRtt()) , getResources().getString(R.string.ms)));
                                        }
                                    });
                                }
                            }
                            else
                            {
                                pingRateList.add(pingTest.getInstantRtt());

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lblPing.setText(String.format("%1$s %2$s" , dec.format(pingTest.getInstantRtt()) , getResources().getString(R.string.ms)));
                                    }
                                });

                                //Update chart
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Creating an  XYSeries for Income
                                        XYSeries pingSeries = new XYSeries("");
                                        pingSeries.setTitle("");

                                        int count = 0;
                                        List<Double> tmpLs = new ArrayList<>(pingRateList);
                                        for (Double val : tmpLs) {
                                            pingSeries.add(count++, val);
                                        }

                                        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                                        dataset.addSeries(pingSeries);

                                        GraphicalView chartView = ChartFactory.getLineChartView(getBaseContext(), dataset, multiPingRenderer);
                                        chartPing.addView(chartView, 0);

                                    }
                                });
                            }


                            //Download Test
                            if (pingTestFinished) {
                                if (downloadTestFinished) {
                                    //Failure
                                    if (downloadTest.getFinalDownloadRate() == 0) {
                                        System.out.println("Download error...");
                                    } else {
                                        //Success
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                lblDownload.setText(String.format("%1$s %2$s" , dec.format(downloadTest.getFinalDownloadRate()) , getResources().getString(R.string.Mbps)));
                                            }
                                        });
                                    }
                                } else {
                                    //Calc position
                                    double downloadRate = downloadTest.getInstantDownloadRate();
                                    downloadRateList.add(downloadRate);
                                    position = getPositionByRate(downloadRate);

                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            rotate = new RotateAnimation(lastPosition, position, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            rotate.setInterpolator(new LinearInterpolator());
                                            rotate.setDuration(100);
                                            barImageView.startAnimation(rotate);
                                            lblDownload.setText(String.format("%1$s %2$s" , dec.format(downloadTest.getInstantDownloadRate()) , getResources().getString(R.string.Mbps)));

                                        }

                                    });
                                    lastPosition = position;

                                    //Update chart
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Creating an  XYSeries for Income
                                            XYSeries downloadSeries = new XYSeries("");
                                            downloadSeries.setTitle("");

                                            List<Double> tmpLs = new ArrayList<>(downloadRateList);
                                            int count = 0;
                                            for (Double val : tmpLs) {
                                                downloadSeries.add(count++, val);
                                            }

                                            XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                                            dataset.addSeries(downloadSeries);

                                            GraphicalView chartView = ChartFactory.getLineChartView(getBaseContext(), dataset, multiDownloadRenderer);
                                            chartDownload.addView(chartView, 0);
                                        }
                                    });

                                }
                            }


                            //Upload Test
                            if (downloadTestFinished) {
                                if (uploadTestFinished) {
                                    //Failure
                                    if (uploadTest.getFinalUploadRate() == 0) {
                                        System.out.println("Upload error...");
                                    } else
                                        {
                                        //Success
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                lblUpload.setText(String.format("%1$s %2$s" , dec.format(uploadTest.getFinalUploadRate()) , getResources().getString(R.string.Mbps)));
                                            }
                                        });
                                    }
                                } else {
                                    //Calc position
                                    double uploadRate = uploadTest.getInstantUploadRate();
                                    uploadRateList.add(uploadRate);
                                    position = getPositionByRate(uploadRate);

                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            rotate = new RotateAnimation(lastPosition, position, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                            rotate.setInterpolator(new LinearInterpolator());
                                            rotate.setDuration(100);
                                            barImageView.startAnimation(rotate);
                                            lblUpload.setText(String.format("%1$s %2$s" , dec.format(uploadTest.getInstantUploadRate()) , getResources().getString(R.string.Mbps)));
                                        }

                                    });
                                    lastPosition = position;

                                    //Update chart
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Creating an  XYSeries for Income
                                            XYSeries uploadSeries = new XYSeries("");
                                            uploadSeries.setTitle("");

                                            int count = 0;
                                            List<Double> tmpLs = new ArrayList<>(uploadRateList);
                                            for (Double val : tmpLs) {
                                                if (count == 0) {
                                                    val = 0.0;
                                                }
                                                uploadSeries.add(count++, val);
                                            }

                                            XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                                            dataset.addSeries(uploadSeries);

                                            GraphicalView chartView = ChartFactory.getLineChartView(getBaseContext(), dataset, multiUploadRenderer);
                                            chartUpload.addView(chartView, 0);
                                        }
                                    });

                                }
                            }

                            //Test bitti
                            if (pingTestFinished && downloadTestFinished && uploadTest.isFinished())
                            {
                                break;
                            }

                            if (pingTest.isFinished()) {
                                pingTestFinished = true;
                            }
                            if (downloadTest.isFinished()) {
                                downloadTestFinished = true;
                            }
                            if (uploadTest.isFinished()) {
                                uploadTestFinished = true;
                            }

                            if (pingTestStarted && !pingTestFinished) {
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                }
                            } else {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                }
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                String message = String.format("%1$s : %2$s \n" , getResources().getString(R.string.status) , getResources().getString(R.string.success)) +
                                        getResources().getString(R.string.operator , lblOperator.getText().toString()) +
                                        String.format("\n %1$s : %2$s" , getResources().getString(R.string.server) , lblServerName.getText().toString())  +
                                        String.format("\n %1$s : %2$s" , getResources().getString(R.string.ping) , lblPing.getText().toString()) +
                                        String.format("\n %1$s : %2$s" , getResources().getString(R.string.download) , lblDownload.getText().toString()) +
                                        String.format("\n %1$s : %2$s" , getResources().getString(R.string.upload) , lblUpload.getText().toString());
                                PubFunc.Logger logger = new PubFunc().new Logger();
                                logger.insertLogToDB(SpeedTestActivity.this,Constants.LOG_SPEED_TEST(), message, "", SpeedTestActivity.class.getSimpleName(), "ButtonClickListener" , "");

                                btnStart.setEnabled(true);
                                btnStart.setText(getResources().getString(R.string.start));
                                mPresenter.checkResult(downloadTest.getFinalDownloadRate() , uploadTest.getFinalUploadRate());
                            }
                        });


                    }
                }).start();
            }
        });



        /*gaugeView = (GaugeView) findViewById(R.id.gauge_view);
        gaugeView.setShowRangeValues(true);*/
        //gaugeView.setTargetValue(0);
        /*final Random random = new Random();
        final CountDownTimer timer = new CountDownTimer(10000, 2) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                int intRandom = random.nextInt(5000);
                gaugeView.setTargetValue(intRandom);
                Log.d("random" , String.valueOf(intRandom));
            }

            @Override
            public void onFinish() {
                gaugeView.setTargetValue(0);
            }
        };*/


        /*btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //gaugeView.setTargetValue(0);
                lblDownload.setText("");
                lblUpload.setText("");
                mPresenter.startDownloadSpeedTest();
            }
        });*/


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeedTestActivity.this.finish();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        getSpeedTestHostsHandler = new GetSpeedTestHostsHandler();
        getSpeedTestHostsHandler.start();
    }


    @Override
    public Context getAppContext()
    {
        return SpeedTestActivity.this;
    }

    @Override
    public void showInternetProvider(String internetProvider)
    {
        lblOperator.setText(internetProvider);
    }


    @Override
    public void showGoodAlert()
    {
        customAlertDialog.showMessageAlert(SpeedTestActivity.this, false, "", String.format("%1$S %2$s" , getResources().getString(R.string.yourInternetStatus) , getResources().getString(R.string.good)), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void showMediumAlert()
    {
        customAlertDialog.showMessageAlert(SpeedTestActivity.this, false, "", String.format("%1$S %2$s" , getResources().getString(R.string.yourInternetStatus) , getResources().getString(R.string.medium)), Constants.INFO_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void showBadAlert()
    {
        customAlertDialog.showMessageAlert(SpeedTestActivity.this, false, "", String.format("%1$S %2$s" , getResources().getString(R.string.yourInternetStatus) , getResources().getString(R.string.bad)), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    public void startMVPOps()
    {
        try
        {
            if ( stateMaintainer.firstTimeIn() )
            {
                initialize(this);
            }
            else
            {
                reinitialize(this);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", SpeedTestActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(SpeedTestMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new SpeedTestPresenter(view);
            stateMaintainer.put(SpeedTestMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", SpeedTestActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(SpeedTestMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(SpeedTestMVP.PresenterOps.class.getSimpleName());
            if ( mPresenter == null )
            {
                initialize( view );
            }
            else
            {
                mPresenter.onConfigurationChanged(view);
            }
        }
        catch (Exception exception)
        {
            if (mPresenter != null)
            {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", SpeedTestActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


    public int getPositionByRate(double rate)
    {
        if (rate <= 1)
        {
            return (int) (rate * 30);

        }
        else if (rate <= 10)
        {
            return (int) (rate * 6) + 30;

        }
        else if (rate <= 30)
        {
            return (int) ((rate - 10) * 3) + 90;

        }
        else if (rate <= 50)
        {
            return (int) ((rate - 30) * 1.5) + 150;

        }
        else if (rate <= 100)
        {
            return (int) ((rate - 50) * 1.2) + 180;
        }

        return 0;
    }



}
