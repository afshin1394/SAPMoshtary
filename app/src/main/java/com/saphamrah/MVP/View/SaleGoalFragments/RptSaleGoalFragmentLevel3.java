package com.saphamrah.MVP.View.SaleGoalFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.saphamrah.Adapter.SaleGoalBarChartsAdapter;
import com.saphamrah.BaseMVP.RptSaleGoalLevel3MVP;
import com.saphamrah.MVP.Presenter.SaleGoalPresenters.RptSaleGoalLevel3Presenter;

import com.saphamrah.MVP.View.RptSaleGoalActivity;
import com.saphamrah.MVP.View.RptSaleGoalActivity;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;

import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import static com.saphamrah.MVP.View.RptSaleGoalActivity.ShowType;


public class RptSaleGoalFragmentLevel3 extends Fragment implements RptSaleGoalLevel3MVP.RequiredViewOps {
    Context context;
    View view;
    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;
    RecyclerView recyclerViewLevel3;
    ImageView imgBackSaleGoalLevel3;
    SaleGoalBarChartsAdapter saleGoalBarChartAdapterLevel3;

    RptSaleGoalLevel3MVP.PresenterOps mPresenter;
    private StateMaintainer stateMaintainer;
    ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ccBrand";
    private static final String ARG_PARAM2 = "ccGoroohKala";

    // TODO: Rename and change types of parameters
    private int ccBrand;
    private int ccGoroohKala;
    private final String TAG = this.getClass().getSimpleName();


    public RptSaleGoalFragmentLevel3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaleGoalFragmentLevel3.
     */
    // TODO: Rename and change types and number of parameters
    public static RptSaleGoalFragmentLevel3 newInstance(String param1, String param2) {
        RptSaleGoalFragmentLevel3 fragment = new RptSaleGoalFragmentLevel3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((RptSaleGoalActivity)getActivity()).fabRefresh.setVisibility(View.GONE);
        if (getArguments() != null) {
            ccBrand = getArguments().getInt(ARG_PARAM1);
            ccGoroohKala = getArguments().getInt(ARG_PARAM2);
        }
        Log.i("backStacks", "onCreate: " + getActivity().getSupportFragmentManager().getBackStackEntryCount());
        RptSaleGoalActivity.activityState = "FragmentLevel3";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sale_goal_level3, container, false);
        findViews(view);




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("BrandsAndGorooh", "onViewCreated: " + ccBrand + "  " + ccGoroohKala);
        stateMaintainer = new StateMaintainer(getFragmentManager(), "SaleGoalFragmentLevel3", context);
        startMVPOps();
        switch (ShowType){
            case Numerical:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.commodityReportSaleGoalNumerical);
                break;
            case Percentage:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.commodityReportSaleGoalPercentage);
                break;
            case Table:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.commodityReportSaleGoalTable);
                break;
        }
//        initializeSaleGoalReportLevel3(rptKalaHadafForoshModels);
        if (ccBrand != 0 && ccGoroohKala != 0)
            mPresenter.getSaleGoalReportLevel3(ccBrand, ccGoroohKala);
        else if (ccBrand != 0)
            mPresenter.getAllSaleGoalByBrandLevel3(ccBrand);
        else
            mPresenter.getAllSaleGoalReportLevel3();





    }

    private void findViews(View view) {


        recyclerViewLevel3 = view.findViewById(R.id.recyclerSaleGoalLevel3);
        customLoadingDialog = new CustomLoadingDialog();



        ((RptSaleGoalActivity)getActivity()).fabNumerical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptKalaHadafForoshModels.clear();
                RptSaleGoalActivity.ShowType= RptSaleGoalActivity.ShowType.Numerical;
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.GONE);

                ((RptSaleGoalActivity)getActivity()).fabMenu.close(true);

                    if (ccBrand != 0 && ccGoroohKala != 0)
                        mPresenter.getSaleGoalReportLevel3(ccBrand, ccGoroohKala);
                    else if (ccBrand != 0)
                        mPresenter.getAllSaleGoalByBrandLevel3(ccBrand);
                    else
                        mPresenter.getAllSaleGoalReportLevel3();

                    ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.commodityReportSaleGoalNumerical);
//                    ((RptSaleGoalActivity)getActivity()).fabNumerical.setLabelText(getActivity().getResources().getString(R.string.showNumerical));
//                    ((RptSaleGoalActivity)getActivity()).fabNumerical.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_sharp));
                    saleGoalBarChartAdapterLevel3.notifyDataSetChanged();
                    Log.i(TAG, "onViewCreated: numerical");


                }

        });
        ((RptSaleGoalActivity)getActivity()).fabPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptKalaHadafForoshModels.clear();
                RptSaleGoalActivity.ShowType= RptSaleGoalActivity.ShowType.Percentage;
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.GONE);

                ((RptSaleGoalActivity)getActivity()).fabMenu.close(true);

                if (ccBrand != 0 && ccGoroohKala != 0)
                    mPresenter.getSaleGoalReportLevel3(ccBrand, ccGoroohKala);
                else if (ccBrand != 0)
                    mPresenter.getAllSaleGoalByBrandLevel3(ccBrand);
                else
                    mPresenter.getAllSaleGoalReportLevel3();

//                ((RptSaleGoalActivity)getActivity()).fabNumerical.setLabelText(getActivity().getResources().getString(R.string.showInPercent));
//                ((RptSaleGoalActivity)getActivity()).fabNumerical.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_percent));
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.commodityReportSaleGoalPercentage);

                saleGoalBarChartAdapterLevel3.notifyDataSetChanged();



            }

        });
        ((RptSaleGoalActivity)getActivity()).fabTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptKalaHadafForoshModels.clear();
                RptSaleGoalActivity.ShowType= RptSaleGoalActivity.ShowType.Table;
                ((RptSaleGoalActivity)context).markTitleName.setText(getResources().getString(R.string.nameKala));
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.VISIBLE);


                ((RptSaleGoalActivity)getActivity()).fabMenu.close(true);

                if (ccBrand != 0 && ccGoroohKala != 0)
                    mPresenter.getSaleGoalReportLevel3(ccBrand, ccGoroohKala);
                else if (ccBrand != 0)
                    mPresenter.getAllSaleGoalByBrandLevel3(ccBrand);
                else
                    mPresenter.getAllSaleGoalReportLevel3();

//                ((RptSaleGoalActivity)getActivity()).fabNumerical.setLabelText(getActivity().getResources().getString(R.string.showAsTable));
//                ((RptSaleGoalActivity)getActivity()).fabNumerical.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_table_list));
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.commodityReportSaleGoalTable);

                saleGoalBarChartAdapterLevel3.notifyDataSetChanged();
                Log.i(TAG, "onViewCreated: numerical");


            }

        });



                //mPresenter.showPercentageReportLevel3();


    }

    @Override
    public Context getAppContext() {
        return context;
    }

    @Override
    public void drawSaleGoalReportLevel3(ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels) {
        Log.i("drawSaleGoal2", "drawSaleGoalReportLevel2: " + rptKalaHadafForoshModels.size());
        initializeSaleGoalReportLevel3(rptKalaHadafForoshModels);
    }


    @Override
    public void closeLoadingDialog() {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCheckBargashtyActivity", "closeLoadingAlert", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(getActivity());
        customAlertDialog.showToast(getActivity(), getResources().getString(resId), messageType, duration);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initializeSaleGoalReportLevel3(ArrayList<BaseHadafForoshModel> rptKalaHadafForoshModels) {
        if (rptKalaHadafForoshModels.size()==1&&rptKalaHadafForoshModels.get(0).getNameKala().equals("جمع")){
            rptKalaHadafForoshModels.clear();
        }
         saleGoalBarChartAdapterLevel3 = new SaleGoalBarChartsAdapter(rptKalaHadafForoshModels, context);
        Log.d("saleGoalSizeinit", "initializeSaleGoalReportLevel2: " + rptKalaHadafForoshModels.size());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        CenterLayoutManager centerLayoutManager=new CenterLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recyclerViewLevel3.setLayoutManager(linearLayoutManager);
        recyclerViewLevel3.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLevel3.setAdapter(saleGoalBarChartAdapterLevel3);
        SnapHelper helper = new PagerSnapHelper();
        recyclerViewLevel3.setOnFlingListener(null);
        helper.attachToRecyclerView(recyclerViewLevel3);


    }

    private void initialize(RptSaleGoalLevel3MVP.RequiredViewOps view) {
        try {
            mPresenter = new RptSaleGoalLevel3Presenter(view);
            stateMaintainer.put(RptSaleGoalLevel3MVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleReportFragmentLevel2", "initialize", "");
        }
    }


    private void reinitialize(RptSaleGoalLevel3MVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptSaleGoalLevel3MVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            Log.i("exceptionMesssagere", "reinitialize: " + exception.getMessage());
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleGoalReportFragmentLevel3", "reinitialize", "");
            }
        }
    }

    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                Log.i("firstTimeIn", "startMVPOps: firstTimeIn");
                initialize(this);
            } else {
                Log.i("firstTimeIn", "startMVPOps: notfirstTimeIn");
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleGoalReportFragmentLevel3", "startMVPOps", "");
        }
    }


}