package com.saphamrah.MVP.View.MainViewPagerFragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.saphamrah.Application.BaseApplication;

import com.saphamrah.BaseMVP.MainViewPagerMVP.HadafForoshTedadyFragmentMVP;
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
 * Use the {@link FragmentChartHadafForoshTedad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChartHadafForoshTedad extends Fragment implements HadafForoshTedadyFragmentMVP.RequiredViewOps {

    private static final String TAG = FragmentChartHadafForoshTedad.class.getClass().getSimpleName();
    float limitLineNumerical = 0.85f;

    // TODO: Rename and change types of parameters
    View view;
    private BarChart barChartHadafForoshTedady;
    Context context;
    StateMaintainer stateMaintainer;
    HadafForoshTedadyFragmentMVP.PresenterOps mPresenter;

    public FragmentChartHadafForoshTedad() {
        // Required empty public constructor
    }

    private static FragmentChartHadafForoshTedad instance = null;

    public static FragmentChartHadafForoshTedad getInstance() {
        if (instance == null)
            instance = new FragmentChartHadafForoshTedad();

        return instance;
    }

    public FragmentChartHadafForoshTedad newInstance() {
        FragmentChartHadafForoshTedad fragment = new FragmentChartHadafForoshTedad();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.i(TAG, "onGetHadafForoshTedady: oncreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chart_hadaf_forosh_tedad, container, false);
        findViews(view);

        Log.i(TAG, "onGetHadafForoshTedady: oncreateView");

//        EventBus.getDefault().register(FragmentChartHadafForoshTedad.this);


        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        EventBus.getDefault().register(this);
        if (stateMaintainer == null)
            stateMaintainer = new StateMaintainer(getChildFragmentManager(), TAG, context);

        Log.i(TAG, "onGetHadafForoshTedady: onviewcreated");

//        startMVPOps();
//
//
        if (mPresenter == null)
            mPresenter = new HadafForoshTedadyFragmentsPresenter(this);
//        mPresenter.getHadafForoshTedady();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null) {
                mPresenter.getHadafForoshTedady();
            }
        }
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: ");
//        EventBus.getDefault().unregister(FragmentChartHadafForoshTedad.this);

        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
    }


    private void findViews(View view) {
        Calligrapher calligrapher = new Calligrapher(context);
        calligrapher.setFont(view, context.getResources().getString(R.string.fontPath));
        Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));
        barChartHadafForoshTedady = view.findViewById(R.id.barChartHadafForoshTedad);
        setNoDataText(barChartHadafForoshTedady, font);
    }

    private void setNoDataText(BarChart barChart, Typeface font) {
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
            Log.i("messageee", "startMVPOps: " + exception.getLocalizedMessage());
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainFragment", "startMVPOps", "");
        }
    }


    private void initialize(HadafForoshTedadyFragmentMVP.RequiredViewOps view) {
        try {
            if (mPresenter == null)
                mPresenter = new HadafForoshTedadyFragmentsPresenter(view);

            stateMaintainer.put(HadafForoshTedadyFragmentMVP.PresenterOps.class.getSimpleName(), mPresenter);

        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "HadafForoshDarsadFragment", "initialize", "");
        }
    }


    private void reinitialize(HadafForoshTedadyFragmentMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(HadafForoshTedadyFragmentMVP.PresenterOps.class.getSimpleName());
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onGetHadafForoshTedady(BaseHadafForoshModel baseHadafForoshModel) {
        String legendLabel1, legendLabel2;

        legendLabel1 = getString(R.string.saleLegendLable);
        legendLabel2 = getString(R.string.goalLegendLabel);
        ArrayList<String> xAxisLables = new ArrayList<>();
        ArrayList<String> legendLables = new ArrayList<>();

        xAxisLables.add(getResources().getString(R.string.untilToday));
        xAxisLables.add(getResources().getString(R.string.today));

        legendLables.add(legendLabel1);
        legendLables.add(legendLabel2);

        Log.i(TAG, "onGetHadafForoshTedady: ongethadafforosh");

        new PubFunc().new ChartUtils().drawGroupBarBarChart(context, barChartHadafForoshTedady, baseHadafForoshModel.getTedadForoshMah(), baseHadafForoshModel.getTedadHadafMah(), baseHadafForoshModel.getTedadForoshRooz(), baseHadafForoshModel.getTedadHadafRooz(), baseHadafForoshModel.getNameBrand(), xAxisLables, legendLables, limitLineNumerical);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
//        getChildFragmentManager().beginTransaction().remove(FragmentChartHadafForoshTedad.this).commit();

    }
}