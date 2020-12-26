package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saphamrah.Adapter.Interfaces.MainPagerAdapterEvents;
import com.saphamrah.BaseMVP.MainFirstFragmentMVP;
import com.saphamrah.CustomView.ZoomOutPageTransformer;
import com.saphamrah.DAO.DBHelper;
import com.saphamrah.MVP.Presenter.MainFirstFragmentPresenter;
import com.saphamrah.SliderPagerMainFrag;
import com.saphamrah.Model.OwghatModel;
import com.saphamrah.Model.WeatherDataModel;
import com.saphamrah.Model.WeatherModel;
import com.saphamrah.Model.WindModel;
import com.saphamrah.R;
import com.saphamrah.Shared.WeatherShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import me.anwarshahriar.calligrapher.Calligrapher;


public class MainFragment extends Fragment implements MainFirstFragmentMVP.RequiredViewOps, MainPagerAdapterEvents
{
    private Context context;
    private ImageView imgDayTime;
    private TextView lblDayTime;
    private TextView lblTemperature;
    private TextView lblCurrentTime;
    private TextView lblCurrentDate;
    private TextView textView;

    private LinearLayout layCharts;

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer;
    MainFirstFragmentMVP.PresenterOps mPresenter;

    private String foroshandehMamorPakhshName;
    private final int TEMPERATURE = 1;
    private final int WEATHER_DETAIL = 2;
    int getDatabase = 0;

    private static final long DELAY_MS = 2000;
    private static final long PERIOD_MS = 10000;
    private static final long LONG_CLICK_DURATION= 800;
    private View view;
    ConstraintLayout parentConstraintP;
    ConstraintLayout parentConstraintL;
    ConstraintLayout child1Constraint;
    ConstraintLayout child2Constraint;
    NestedScrollView nestedScrollView;
    private ViewPager viewPagerMainFrag;
    ArrayList<Fragment> fragList = new ArrayList<>();
    private SliderPagerMainFrag viewPagerAdapter;
    TimerTask timerTask;
    Timer timer;
    Runnable update;
    Handler handler;
    int screenHeight;
    boolean isLongClick = false;




    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        //TODO
//        startTimer();
    }
//todo
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("FRAGMENT_VISIBLE", "setUserVisibleHint: ");
        if (isVisibleToUser)
        startTimer();
        else {
            if (timer!=null)
            stopTimer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.m_frag, container, false);
        viewPagerMainFrag = view.findViewById(R.id.view_pager_mainFrag);
        findViews(view);
        initFragments();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        Log.i(TAG, "onCreateView: " + screenHeight);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            int navigationBarSize = getActivity().findViewById(R.id.navigationTabBar).getLayoutParams().height;
            int appBarSize  = getActionBarSize();
//            Log.i(TAG, "onCreateView: " + getActivity().findViewById(R.id.appbarlayout).getLayoutParams().height);
            Log.i(TAG, "onConfigurationChanged: " + navigationBarSize + " " + appBarSize + " " + getStatusBarHeight());
            screenHeight = screenHeight - appBarSize - navigationBarSize - getStatusBarHeight();

            child1Constraint.getLayoutParams().height = screenHeight;

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            int navigationBarSize = getActivity().findViewById(R.id.navigationTabBar).getLayoutParams().height;
            int appBarSize = getActionBarSize();
            parentConstraintP.getLayoutParams().height = (int) convertPixelsToDp(screenHeight, context);
            ;
            screenHeight = screenHeight - appBarSize - navigationBarSize - getStatusBarHeight();
            child2Constraint.getLayoutParams().height = screenHeight / 2;
            child1Constraint.getLayoutParams().height = screenHeight / 2;
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        Calligrapher calligrapher = new Calligrapher(context);
        calligrapher.setFont(view , context.getResources().getString(R.string.fontPath));
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

        textView = view.findViewById(R.id.lbl_mainfrgmnt);
        imgDayTime = view.findViewById(R.id.imgDayTime);
        lblDayTime = view.findViewById(R.id.lblDayTime);
        lblTemperature = view.findViewById(R.id.lblTemperature);
        lblCurrentTime = view.findViewById(R.id.lblCurrentTime);
        lblCurrentDate = view.findViewById(R.id.lblCurrentDate);


        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , context);
        startMVPOps();

        mPresenter.getNoeForoshandehMamorPakhsh();

        lblTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getWeather(WEATHER_DETAIL);
            }
        });




        lblCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               getDatabase++;
               if(getDatabase>10 && getDatabase <20)
               {
                   Toast.makeText(context, "تعداد دفعات جهت نمایش " + (getDatabase) , Toast.LENGTH_SHORT).show();
               }
                if(getDatabase==20)
                {
                    exportDatabse("DataBase.sqlite");
                    Toast.makeText(context, "دیتا ذخیره شد"  , Toast.LENGTH_SHORT).show();
                    getDatabase=0;
                }

            }
        });


//        Button btnShowBarkhord = view.findViewById(R.id.btnShowBarkhord);
//        Button btnHideBarkhord = view.findViewById(R.id.btnHideBarkhord);
//        Button btnShowMojoodi = view.findViewById(R.id.btnShowMojoodi);
//        Button btnHideMojoodi = view.findViewById(R.id.btnHideMojoodi);
//        Button btnMultiple = view.findViewById(R.id.btnMultiple);
//        Button btnOnce = view.findViewById(R.id.btnOnce);
//
//
//        btnShowBarkhord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParameterChildDAO dao = new ParameterChildDAO(context);
//                boolean result = dao.updateShowBarkhordAvalie(1);
//                //Toast.makeText(context, "result : " + result , Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//        btnHideBarkhord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParameterChildDAO dao = new ParameterChildDAO(context);
//                boolean result = dao.updateShowBarkhordAvalie(0);
//                //Toast.makeText(context, "result : " + result , Toast.LENGTH_LONG).show();
//            }
//        });
//
//        btnShowMojoodi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParameterChildDAO dao = new ParameterChildDAO(context);
//                boolean result = dao.updateShowMojoodiGiri(1);
//                //Toast.makeText(context, "result : " + result , Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//        btnHideMojoodi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParameterChildDAO dao = new ParameterChildDAO(context);
//                boolean result = dao.updateShowMojoodiGiri(0);
//                //Toast.makeText(context, "result : " + result , Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//        btnMultiple.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParameterChildDAO dao = new ParameterChildDAO(context);
//                boolean result = dao.updateMultipleMojoodiGiri(1);
//                //Toast.makeText(context, "result : " + result , Toast.LENGTH_LONG).show();
//            }
//        });
//
//        btnOnce.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParameterChildDAO dao = new ParameterChildDAO(context);
//                boolean result = dao.updateMultipleMojoodiGiri(0);
//                //Toast.makeText(context, "result : " + result , Toast.LENGTH_LONG).show();
//            }
//        });

    }


    private void countRowsOfAllTable()
    {
        try
        {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select name from sqlite_master where type like 'table' order by name";
            Cursor cursor = db.rawQuery(query , null);
            String count = "";
            if (cursor != null)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    String tableName = cursor.getString(0);
                    String countQuery = "select count(*) from " + tableName;
                    Cursor cursor1 = db.rawQuery(countQuery, null);
                    if (cursor1 != null)
                    {
                        cursor1.moveToFirst();
                        int intCount = cursor1.getInt(0);
                        count += "\n" + tableName + " : " + intCount;
                        cursor1.close();
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            }
            saveToFile(count);
            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void saveToFile(String data)
    {
        try
        {
            File path = context.getExternalFilesDir(null);
            File file = new File(path, "countRowsPegah.txt");
            FileOutputStream stream = new FileOutputStream(file);
            try
            {
                stream.write(data.getBytes());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void exportDatabse(String databaseName)
    {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+context.getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean unpackZip(String path, String zipname)
    {
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            is = new FileInputStream(path + zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                filename = ze.getName();

                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);

                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    private void getWeatherFromShared(int type)
    {
        try
        {
            WeatherShared weatherShared = new WeatherShared(context);
            // WeatherModel
            int weatherId = Integer.parseInt(weatherShared.getString(weatherShared.MAIN_ID() , "0"));
            String weatherDesc = weatherShared.getString(weatherShared.DESC() , "");
            String weatherIcon = weatherShared.getString(weatherShared.ICON() , "");
            WeatherModel weatherModel = new WeatherModel();
            weatherModel.setId(weatherId);
            weatherModel.setDescription(weatherDesc);
            weatherModel.setIcon(weatherIcon);

            // WeatherDataModel
            double temperature = Double.parseDouble(weatherShared.getString(weatherShared.TEMPERATURE() , "0.0"));
            double maxTemperature = Double.parseDouble(weatherShared.getString(weatherShared.MAX_TEMPERATURE() , "0.0"));
            double minTemperature = Double.parseDouble(weatherShared.getString(weatherShared.MIN_TEMPERATURE() , "0.0"));
            int humidity = Integer.parseInt(weatherShared.getString(weatherShared.HUMIDITY() , "0"));
            WeatherDataModel weatherDataModel = new WeatherDataModel();
            weatherDataModel.setTemp(temperature);
            weatherDataModel.setTempMax(maxTemperature);
            weatherDataModel.setTempMin(minTemperature);
            weatherDataModel.setHumidity(humidity);

            //WindModel
            double windSpeed = Double.parseDouble(weatherShared.getString(weatherShared.WIND_SPEED() , "0.0"));
            WindModel windModel = new WindModel();
            windModel.setSpeed(windSpeed);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mPresenter.checkOwghat("");
        foroshandehMamorPakhshName = "";
        mPresenter.getForoshandehMamorPakhshName();
        mPresenter.getCurrentDate();
        mPresenter.getWeather(TEMPERATURE);
    }

    @Override
    public Context getAppContext()
    {
        return context;
    }

    @Override
    public void onFailedGetOwghat()
    {
        lblDayTime.setVisibility(View.GONE);
    }

    @Override
    public void onShowOwghat(int drawableId, int stringId , OwghatModel owghatModel)
    {
        imgDayTime.setImageResource(drawableId);
        lblDayTime.setVisibility(View.VISIBLE);
        lblDayTime.setText(String.format("%1$s%2$s %3$s" , foroshandehMamorPakhshName, getResources().getString(R.string.comma), getResources().getString(stringId)));
        //Toast.makeText(context , "string id : " + getResources().getString(stringId) , Toast.LENGTH_SHORT).show();

        /*textView.setText(owghatModel.getAzanSobh() + "\n" + owghatModel.getToluAftab() + "\n" +
                owghatModel.getAzanZohr() + "\n" +
                owghatModel.getGhorubAftab() + "\n" + owghatModel.getAzanMaghreb() + "\n" +
                owghatModel.getNimeshab() + "\n");*/
    }

    @Override
    public void onGetForoshandehMamorPakhshName(String name)
    {
        foroshandehMamorPakhshName = name;
    }

    @Override
    public void onGetCurrentDate(String currentDate)
    {
        lblCurrentDate.setText(currentDate);
    }

    @Override
    public void onGetWeather(WeatherModel weatherModel, WeatherDataModel weatherDataModel, WindModel windModel)
    {
        lblTemperature.setText(String.format("%1$s: %2$s" , getResources().getString(R.string.temperature) , weatherDataModel.getTemp().intValue()));
        /*String weather = weatherModel.getDescription();
        String weatherData = "دما : " + weatherDataModel.getTemp() + "\n" + "فشار : " + weatherDataModel.getPressure() + "\n" +
                "رطوبت : " + weatherDataModel.getHumidity() + " \n" + "حداکثر دما : " + weatherDataModel.getTempMax() + "\n" +
                "حداقل دما : " + weatherDataModel.getTempMin();
        String wind = "سرعت وزش باد : " + windModel.getSpeed();
        textView.setText(weather + "\n \n" + weatherData + "\n \n" + wind);*/
    }

    @Override
    public void showAlertWeatherDetails(WeatherModel weatherModel, WeatherDataModel weatherDataModel, WindModel windModel)
    {
        lblTemperature.setVisibility(View.VISIBLE);
        lblTemperature.setText(String.format("%1$s: %2$s" , getResources().getString(R.string.temperature) , weatherDataModel.getTemp().intValue()));
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        customAlertDialog.showWeatherDetails((Activity)context, weatherModel.getIcon(), weatherModel.getDescription(), weatherDataModel.getTemp(), weatherDataModel.getTempMax(), weatherDataModel.getTempMin(), weatherDataModel.getHumidity(), windModel.getSpeed());
    }

    @Override
    public void onFailedGetWeather()
    {
        lblTemperature.setVisibility(View.GONE);
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void findViews(View view) {
        parentConstraintP = view.findViewById(R.id.mainConstraint);
        parentConstraintL = view.findViewById(R.id.mainConstraintLandScape);
        child1Constraint = view.findViewById(R.id.constraint1MainFrag);
        child2Constraint = view.findViewById(R.id.constraint2MainFrag);
        nestedScrollView = view.findViewById(R.id.nestedScrollViewMainFragment);

    }

//TODO
    //handles action up and down touch events
       View.OnTouchListener onTouchListener= new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {

                   final int action = MotionEventCompat.getActionMasked(motionEvent);
                   if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_BUTTON_PRESS) {
                       if (motionEvent.getDownTime() >= LONG_CLICK_DURATION) {
                           isLongClick = true;
                        stopTimer();
                       }
                   }
                   if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {

                       if (isLongClick) {
                        startTimer();
                           Log.i("touchEvent", "onReleaseClick: ");
                       }
                   }
                   return false;
               }

       };








    public void startTimer() {

        handler = new Handler();
        update = new Runnable() {
            public void run() {
                viewPagerMainFrag.setCurrentItem(viewPagerMainFrag.getCurrentItem() - 1);
            }
        };

        Log.i(TAG, "startTimer: " + handler);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        };
        timer.schedule(timerTask, DELAY_MS, PERIOD_MS);
        Log.i(TAG, "startTimer: " + timer + " " + timerTask + " " + update + " " + handler);
    }

    public void stopTimer() {
        Log.i(TAG, "stopTimer: " + handler);
        Log.i(TAG, "stopTimer: " + timer);
        timer.cancel();
        timerTask.cancel();

        timerTask = null;
        timer = null;
        update = null;
        handler.removeCallbacksAndMessages(null);
        handler = null;
        Log.i(TAG, "startTimer: " + timer + " " + timerTask + " " + update + " " + handler);

    }



    public void initFragments() {

//        if (fragList.size() >= 4) {
//            fragList.clear();
//            Log.i(TAG, "initFragments: " + fragList.size());
//        }
//        FragmentChartHadafForoshDarsad fragmentChartHadafForoshDarsad = FragmentChartHadafForoshDarsad.newInstance();
//        FragmentChartMablaghForosh fragmentChartMablaghForosh = FragmentChartMablaghForosh.newInstance();
//        FragmentChartTedadFactorForosh fragmentChartTedadFactorForosh = FragmentChartTedadFactorForosh.newInstance();
//        FragmentChartHadafForoshTedad fragmentChartHadafForoshTedad = FragmentChartHadafForoshTedad.newInstance();
//        fragList.add(fragmentChartHadafForoshDarsad);
//        fragList.add(fragmentChartHadafForoshTedad);
//        fragList.add(fragmentChartMablaghForosh);
//        fragList.add(fragmentChartTedadFactorForosh);
//
//        viewPagerAdapter = new MainFragPagerAdapter(getFragmentManager(), fragList, MainFragment.this);
        viewPagerAdapter=new SliderPagerMainFrag(getFragmentManager());
        Log.i("frgListSize", "initFragments: " + fragList.size());
        // actually an InfiniteViewPager

        viewPagerMainFrag.setAdapter(viewPagerAdapter);
        viewPagerMainFrag.setPageTransformer(false, new ZoomOutPageTransformer());
        viewPagerMainFrag.setCurrentItem(Integer.MAX_VALUE/2);
        viewPagerMainFrag.setOnTouchListener(onTouchListener);



    }

    private int getActionBarSize(){
        TypedValue tv = new TypedValue();
        int actionBarHeight;
        if ( getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
            return actionBarHeight;
        }else{
            return 0;
        }

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        DisplayMetrics displayMetricsAl;
        displayMetricsAl = new DisplayMetrics();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {


            screenHeight = displayMetricsAl.heightPixels;
            int navigationBarSize = getActivity().findViewById(R.id.navigationTabBar).getLayoutParams().height;
            int appBarSize = getActionBarSize();
//            Log.i(TAG, "onConfigurationChanged: " + navigationBarSize + " " + appBarSize + " " + getStatusBarHeight());
            screenHeight=screenHeight - appBarSize- navigationBarSize - getStatusBarHeight();
            child1Constraint.getLayoutParams().height = screenHeight ;



        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            screenHeight = newConfig.screenHeightDp;
            int navigationBarSize = getActivity().findViewById(R.id.navigationTabBar).getLayoutParams().height;
//            int appBarSize = getActivity().findViewById(R.id.relAppBar).getLayoutParams().height;
            int appBarSize = getActionBarSize();
            Log.i(TAG, "onConfigurationChanged: " + screenHeight);
            screenHeight=screenHeight-navigationBarSize-appBarSize-getStatusBarHeight();
            child2Constraint.getLayoutParams().height = screenHeight / 2;
            child1Constraint.getLayoutParams().height = screenHeight / 2;

        }
    }




    //implement container onLongClickListenerEvent that contains the fragments
    //used in on instantiateItem in mainFragPagerAdapter
    //Declared in adapter/interfaces
    @Override
    public void onFragmentPagerLongClick() {
        isLongClick = true;
        Log.i("longClick", "onFragmentPagerLongClick: ");
    }
    public  float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainFragment", "startMVPOps", "");
        }
    }


    private void initialize(MainFirstFragmentMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new MainFirstFragmentPresenter(view);
            stateMaintainer.put(MainFirstFragmentMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainFragment", "initialize", "");
        }
    }


    private void reinitialize(MainFirstFragmentMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(MainFirstFragmentMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainFragment", "reinitialize", "");
            }
        }
    }





}
