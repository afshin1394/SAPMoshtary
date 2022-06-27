package com.saphamrah.MVP.View.MainViewPagerFragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.MainViewPagerMVP.HadafForoshDarsadFragmentMVP;
import com.saphamrah.BaseMVP.MainViewPagerMVP.HadafForoshTedadyFragmentMVP;
import com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter.HadafForoshDarsadFragmentPresenter;
import com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter.HadafForoshTedadyFragmentsPresenter;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChartHadafForoshDarsad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChartHadafForoshDarsad extends Fragment implements HadafForoshDarsadFragmentMVP.RequiredViewOps {

    private static final String TAG = FragmentChartHadafForoshDarsad.class.getSimpleName();

    private BarChart barChartHadaForoshDarsad;
    Context context;
    StateMaintainer stateMaintainer;
    HadafForoshDarsadFragmentMVP.PresenterOps mPresenter;
    View view;
    float limitLinePercentage = 85f;
    public FragmentChartHadafForoshDarsad() {
        // Required empty public constructor
    }

    private static FragmentChartHadafForoshDarsad instance=null;
    public static FragmentChartHadafForoshDarsad getInstance(){
        if (instance==null)
            instance=new FragmentChartHadafForoshDarsad();

        return instance;
    }

    public FragmentChartHadafForoshDarsad newInstance() {
        FragmentChartHadafForoshDarsad fragment = new FragmentChartHadafForoshDarsad();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        EventBus.getDefault().register(FragmentChartHadafForoshDarsad.this);
        view=inflater.inflate(R.layout.fragment_chart_hadaf_forosh_darsad, container, false);

        findViews(view);



        return view;
    }



    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: ");
        super.onDetach();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        stateMaintainer = new StateMaintainer(getChildFragmentManager(), TAG, context);

        super.onViewCreated(view, savedInstanceState);
//        startMVPOps();
        mPresenter=new HadafForoshDarsadFragmentPresenter(this);
//        mPresenter.getHadafForoshTedady();
//        EventBus.getDefault().register(this);
//        startMVPOps();
//        mPresenter.getHadafForoshTedady();

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (getView() != null)
            {
                mPresenter.getHadafForoshTedady();
            }
        }
    }
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
//    {
//        super.onViewStateRestored(savedInstanceState);
//        Log.d("fragment" , "onview restored");
//        mPresenter.getHadafForoshTedady();
//    }



    private void findViews(View view) {
        Calligrapher calligrapher = new Calligrapher(context);
        calligrapher.setFont(view , context.getResources().getString(R.string.fontPath));
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        barChartHadaForoshDarsad = view.findViewById(R.id.barChartHadafForoshDarsad);
        setNoDataText(barChartHadaForoshDarsad,font);

    }
    private void setNoDataText(BarChart barChart ,Typeface font){
        barChart.setNoDataText(context.getResources().getString(R.string.loadData));
        barChart.setNoDataTextColor(Color.RED);
        barChart.setNoDataTextTypeface(font);
    }
    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            Log.i("messageee", "startMVPOps: "+exception.getLocalizedMessage());
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainFragment", "startMVPOps", "");
        }
    }


    private void initialize(HadafForoshDarsadFragmentMVP.RequiredViewOps view) {
        try {

             mPresenter = new HadafForoshDarsadFragmentPresenter(view);
            stateMaintainer.put(HadafForoshDarsadFragmentMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "HadafForoshDarsadFragment", "initialize", "");
        }
    }


    private void reinitialize(HadafForoshDarsadFragmentMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(HadafForoshDarsadFragmentMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "HadafforoshDarsadFragment", "reinitialize", "");
            }
        }
    }


    @Override
    public Context getAppContext() {
        return context;
    }

    @Override
    public void onGetHadafForoshTedady(BaseHadafForoshModel baseHadafForoshModel) {
        String legendLabel1,legendLabel2;

        legendLabel1=getString(R.string.goalLegendLabel);
        legendLabel2=getString(R.string.saleLegendLable);
        ArrayList<String> xAxisLables=new ArrayList<>();
        ArrayList<String> legendLables=new ArrayList<>();

        xAxisLables.add(getResources().getString(R.string.untilToday));
        xAxisLables.add(getResources().getString(R.string.today));

        legendLables.add(legendLabel1);
        legendLables.add(legendLabel2);
        Log.i(TAG, "onGetHadafForoshTedady: "+baseHadafForoshModel.getTedadForoshMah());

        ArrayList<Float> percentValues=new PubFunc().new ChartUtils().calculatePercentFromNumeric(baseHadafForoshModel.getTedadForoshMah(), baseHadafForoshModel.getTedadHadafMah(),baseHadafForoshModel.getTedadForoshRooz() , baseHadafForoshModel.getTedadHadafRooz());
        Log.i("SDFASD", "onGetHadafForoshTedady: value1:"+percentValues.get(1) +"value2:"+percentValues.get(0)  );
        new PubFunc().new ChartUtils().drawSingleBarBarChart(context,barChartHadaForoshDarsad, percentValues.get(1)  ,percentValues.get(0) , baseHadafForoshModel.getNameBrand(),xAxisLables,limitLinePercentage,true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view=null;
//        getChildFragmentManager().beginTransaction().remove(FragmentChartHadafForoshDarsad.this).commit();

    }
}