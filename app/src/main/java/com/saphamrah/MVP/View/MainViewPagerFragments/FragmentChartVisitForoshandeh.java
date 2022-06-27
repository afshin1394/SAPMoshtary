package com.saphamrah.MVP.View.MainViewPagerFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.BarChart;
import com.saphamrah.Adapter.RptVisitForoshandeAdapter;
import com.saphamrah.BaseMVP.MainViewPagerMVP.CountFaktorForoshMVP;
import com.saphamrah.BaseMVP.MainViewPagerMVP.MablaghTedadForoshFragmentsBaseMVP;
import com.saphamrah.BaseMVP.RptForoshandehVisitMVP;
import com.saphamrah.MVP.Presenter.MainFragmentViewPagerPresenter.CountFaktorForoshFragmentPresenter;
import com.saphamrah.MVP.Presenter.RptForoshandehVisitPresenter;
import com.saphamrah.MVP.View.RptForoshandehVisitActivity;
import com.saphamrah.MVP.View.RptVisitForoshandehActivity;
import com.saphamrah.Model.RptForoshModel;
import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentChartVisitForoshandeh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentChartVisitForoshandeh extends Fragment implements RptForoshandehVisitMVP.RequiredViewOps {
    private static final String TAG = FragmentChartVisitForoshandeh.class.getClass().getSimpleName();

    Context context;
    View view;
    BarChart barChartCountFaktor;


    private StateMaintainer stateMaintainer ;
    private RptForoshandehVisitMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;

    private int kharidKardeh = 0;
    private int adamSefaresh = 0;
    private int adamDarkhast = 0;
    private int adamMoraje = 0;
    private int counter = 0;

    private int showSingle = 0;
    private int showSingleSec = 0;
    private boolean flag = true;

    public FragmentChartVisitForoshandeh() {
        // Required empty public constructor
    }


    private static FragmentChartVisitForoshandeh instance = null;

    public static FragmentChartVisitForoshandeh getInstance() {
        if (instance == null)
            instance = new FragmentChartVisitForoshandeh();
        return instance;
    }

    public static FragmentChartVisitForoshandeh newInstance() {
        FragmentChartVisitForoshandeh fragment = new FragmentChartVisitForoshandeh();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_chart_visit_foroshandeh, container, false);
        findViews(view);


        TextView updateRecorde = view.findViewById(R.id.updateRptVFtv);

        updateRecorde.setOnClickListener(view1 -> {
            updateFunc();
        });

//        Calligrapher calligrapher = new Calligrapher(getAppContext());
//        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);


        customAlertDialog = new CustomAlertDialog(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        stateMaintainer = new StateMaintainer(getChildFragmentManager(), TAG, context);

        stateMaintainer = new StateMaintainer(getChildFragmentManager(), TAG, getAppContext());

        startMVPOps();

        mPresenter.getVisitList();

//        mPresenter.getAmarForosh();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    @Override
    public Context getAppContext() {
        return context;
    }

    @Override
    public void setAdapter(ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels, RptVisitForoshandehMoshtaryModel rptVisitForoshandehMoshtarySum) {

        ArrayList<RptVisitForoshandehMoshtaryModel> tempList = new ArrayList<>();


        for (RptVisitForoshandehMoshtaryModel model : rptVisitForoshandehMoshtaryModels) {
            counter++;

            if (model.getIsMorajeh() == 1) {
                kharidKardeh++;
            }
            //the perfect way would be getting reasons from server
            else if (model.getIsMorajeh() == -1 && model.getCodeNoeAdamDarkhast() == 1) {
                adamDarkhast++;
            } else if (model.getIsMorajeh() == -1 && model.getCodeNoeAdamDarkhast() == 0) {
                adamSefaresh++;
            }

            if (model.getIsMorajeh() == 0) {
                adamMoraje++;
            }

            if (rptVisitForoshandehMoshtaryModels.size() == counter) {
                ArrayList<String> xAxisLables = new ArrayList<>();
                xAxisLables.add(("کل"));
                xAxisLables.add(("خرید کرده"));
                xAxisLables.add("عدم سفارش");
                xAxisLables.add("عدم درخواست");
                xAxisLables.add("مراجعه نکرده");

                if (rptVisitForoshandehMoshtaryModels.size() > 0) {
//            faktorRooz = (float) rptVisitForoshandehMoshtaryModels.get(0);
//            faktorMah = (float) rptForoshModels.get(0).getCountFaktorMah();
//                    new PubFunc().new ChartUtils().drawSingleBarBarChart(context, barChartCountFaktor ,(float) adamDarkhast, (float) kharidKardeh, "", xAxisLables, null, false);
                    new PubFunc().new ChartUtils().drawSingleFourBarChart(context, barChartCountFaktor ,(float) adamDarkhast, (float) kharidKardeh, (float) adamSefaresh, (float) adamMoraje, xAxisLables, null, counter);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }

    @Override
    public void emptyList() {
        showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(getActivity(), getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "FragmentChartVisitForoshandeh", "startMVPOps", "");
        }
    }


    private void initialize(RptForoshandehVisitMVP.RequiredViewOps view) {
        try {
            mPresenter = new RptForoshandehVisitPresenter(view);
            stateMaintainer.put(RptForoshandehVisitMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "FragmentChartVisitForoshandeh", "initialize", "");
        }
    }


    private void reinitialize(RptForoshandehVisitMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptForoshandehVisitMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "FragmentChartVisitForoshandeh", "reinitialize", "");
            }
        }
    }

    private void updateFunc() {
        kharidKardeh = 0;
        adamSefaresh = 0;
        adamDarkhast = 0;
        adamMoraje = 0;
        counter = 0;
        mPresenter.updateReport();
    }


}