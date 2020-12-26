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
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.MainViewPagerMVP.MablaghTedadForoshFragmentsBaseMVP;
import com.saphamrah.BaseMVP.MainFirstFragmentMVP;
import com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter.MablaghTedadForoshFragmentsPresenter;
import com.saphamrah.MVP.View.RptThreeMonthPurchase.RptThreeMonthPurchaseActivity;
import com.saphamrah.MVP.View.RptThreeMonthPurchase.RptThreeMonthPurchaseRizFaktorFragment;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChartMablaghForosh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChartMablaghForosh extends Fragment implements MablaghTedadForoshFragmentsBaseMVP.RequiredViewOps {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG =  FragmentChartMablaghForosh.class.getClass().getSimpleName();
    private View view;
    private BarChart barChartMablaghForosh;
    Context context;
    StateMaintainer stateMaintainer;

    MablaghTedadForoshFragmentsBaseMVP.PresenterOps mPresenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentChartMablaghForosh() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment FragmentChartMablaghForosh.
     */


    private static FragmentChartMablaghForosh instance=null;
    public static FragmentChartMablaghForosh getInstance(){
        if (instance==null)
            instance=new FragmentChartMablaghForosh();

        return instance;
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentChartMablaghForosh newInstance() {
        FragmentChartMablaghForosh fragment = new FragmentChartMablaghForosh();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_chart_mablagh_forosh, container, false);
        findViews(view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stateMaintainer = new StateMaintainer(getChildFragmentManager(), TAG, context);

//        startMVPOps();
        mPresenter=new MablaghTedadForoshFragmentsPresenter(this);
//        mPresenter.getAmarForosh();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
//    {
//        super.onViewStateRestored(savedInstanceState);
//        Log.d("fragment" , "onview restored");
//        mPresenter.getAmarForosh();
//    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (getView() != null)
            {
                mPresenter.getAmarForosh();
            }
        }
    }

    private void findViews(View view) {
        Calligrapher calligrapher = new Calligrapher(context);
        calligrapher.setFont(view , context.getResources().getString(R.string.fontPath));
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        barChartMablaghForosh = view.findViewById(R.id.barChartMablaghForosh);
        setNoDataText(barChartMablaghForosh,font);
    }
    private void setNoDataText(BarChart barChart ,Typeface font){
        barChart.setNoDataText(context.getResources().getString(R.string.errorGetData));
        barChart.setNoDataTextColor(Color.RED);
        barChart.setNoDataTextTypeface(font);
    }



    @Override
    public Context getAppContext() {
        return context;
    }

    @Override
    public void onGetAmarForosh(ArrayList<RptForoshModel> rptForoshModels) {
        if (rptForoshModels.size()>0) {
            float sumMablaghFaktorRooz = rptForoshModels.get(0).getSumMablaghFaktorRooz() / 1000000;
            float sumMablaghFaktorMah = (float) rptForoshModels.get(0).getSumMablaghFaktorMah() / 1000000;
            ArrayList<String> xAxisLables=new ArrayList<>();
            xAxisLables.add(getResources().getString(R.string.untilToday));
            xAxisLables.add(getResources().getString(R.string.today));
            new PubFunc().new ChartUtils().drawSingleBarBarChart( context,barChartMablaghForosh, sumMablaghFaktorMah,sumMablaghFaktorRooz,"",xAxisLables,null,false);
        }
    }




    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "FragmentChartMablaghForosh", "startMVPOps", "");
        }
    }


    private void initialize(MablaghTedadForoshFragmentsBaseMVP.RequiredViewOps view) {
        try {
            mPresenter = new MablaghTedadForoshFragmentsPresenter(view);
            stateMaintainer.put(MainFirstFragmentMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MablagheForosh", "initialize", "");
        }
    }


    private void reinitialize(MablaghTedadForoshFragmentsBaseMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(MablaghTedadForoshFragmentsBaseMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MablagheforoshFragment", "reinitialize", "");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view=null;
//        getChildFragmentManager().beginTransaction().remove(FragmentChartMablaghForosh.this).commit();

    }

}