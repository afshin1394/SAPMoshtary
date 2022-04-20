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
import com.saphamrah.BaseMVP.MainViewPagerMVP.CountFaktorForoshMVP;
import com.saphamrah.BaseMVP.MainViewPagerMVP.MablaghTedadForoshFragmentsBaseMVP;
import com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter.CountFaktorForoshFragmentPresenter;
import com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter.MablaghTedadForoshFragmentsPresenter;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChartTedadFactorForosh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChartTedadFactorForosh extends Fragment implements CountFaktorForoshMVP.RequiredViewOps {
    private static final String TAG = FragmentChartTedadFactorForosh.class.getClass().getSimpleName();
    private CountFaktorForoshMVP.PresenterOps mPresenter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    StateMaintainer stateMaintainer;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    View view;
    BarChart barChartCountFaktor;
    float faktorRooz,faktorMah;

    public FragmentChartTedadFactorForosh() {
        // Required empty public constructor
    }


    private static FragmentChartTedadFactorForosh instance=null;

    public static FragmentChartTedadFactorForosh getInstance(){
        if (instance==null)
            instance = new FragmentChartTedadFactorForosh();
        return instance;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentChartTedadFactorForosh.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentChartTedadFactorForosh newInstance() {
        FragmentChartTedadFactorForosh fragment = new FragmentChartTedadFactorForosh();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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

        view = inflater.inflate(R.layout.fragment_chart_tedad_factor_forosh, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stateMaintainer = new StateMaintainer(getChildFragmentManager(), TAG, context);


//        startMVPOps();
        mPresenter = new CountFaktorForoshFragmentPresenter(this);

//        mPresenter.getAmarForosh();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
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
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
//    {
//        super.onViewStateRestored(savedInstanceState);
//        Log.d("fragment" , "onview restored");
//        mPresenter.getAmarForosh();
//    }

    private void findViews(View view) {
        Calligrapher calligrapher = new Calligrapher(context);
        calligrapher.setFont(view, context.getResources().getString(R.string.fontPath));
        Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));
        barChartCountFaktor = view.findViewById(R.id.barChartCountFaktor);
        setNoDataText(barChartCountFaktor, font);
    }

    private void setNoDataText(BarChart barChart, Typeface font) {
        barChart.setNoDataText(context.getResources().getString(R.string.errorGetData));
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
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainFragment", "startMVPOps", "");
        }
    }


    private void initialize(CountFaktorForoshMVP.RequiredViewOps view) {
        try {
            mPresenter = new CountFaktorForoshFragmentPresenter(view);
            stateMaintainer.put(MablaghTedadForoshFragmentsBaseMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MablagheForosh", "initialize", "");
        }
    }


    private void reinitialize(CountFaktorForoshMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(CountFaktorForoshMVP.PresenterOps.class.getSimpleName());
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
    public Context getAppContext() {
        return context;
    }

    @Override
    public void onGetAmarForosh(ArrayList<RptForoshModel> rptForoshModels) {
        ArrayList<String> xAxisLables=new ArrayList<>();
        xAxisLables.add(getResources().getString(R.string.untilToday));
        xAxisLables.add(getResources().getString(R.string.today));

        if (rptForoshModels.size()>0)
        {
            faktorRooz = (float) rptForoshModels.get(0).getCountFaktorRooz();
            faktorMah = (float) rptForoshModels.get(0).getCountFaktorMah();
            new PubFunc().new ChartUtils().drawSingleBarBarChart(context, barChartCountFaktor, faktorRooz , faktorMah , "", xAxisLables, null, false);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view=null;
//        getChildFragmentManager().beginTransaction().remove(FragmentChartTedadFactorForosh.this).commit();

    }


}