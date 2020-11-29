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
import com.saphamrah.BaseMVP.RptSaleGoalLevel2MVP;

import com.saphamrah.MVP.Presenter.SaleGoalPresenters.RptSaleGoalLevel2Presenter;
import com.saphamrah.MVP.View.RptSaleGoalActivity;
import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import static com.saphamrah.MVP.View.RptSaleGoalActivity.ShowType;


public class RptSaleGoalFragmentLevel2 extends Fragment implements RptSaleGoalLevel2MVP.RequiredViewOps, SaleGoalBarChartsAdapter.OnItemClickListener {

    View view;
    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;
    RecyclerView recyclerViewLevel2;
    Context context;
    SaleGoalBarChartsAdapter saleGoalBarChartAdapterLevel2;
    RptSaleGoalLevel2MVP.PresenterOps mPresenter;
    private StateMaintainer stateMaintainer;
    ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ccBrand";
    private final String TAG = this.getClass().getSimpleName();



    // TODO: Rename and change types of parameters
    private int mBrand;

    ImageView imgBackSaleGoalLevel2;



    public RptSaleGoalFragmentLevel2() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RptSaleGoalFragmentLevel2 newInstance(String mBrand) {
        RptSaleGoalFragmentLevel2 fragment = new RptSaleGoalFragmentLevel2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mBrand);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RptSaleGoalActivity)getActivity()).fabRefresh.setVisibility(View.GONE);

        if (getArguments() != null) {

            mBrand = getArguments().getInt(ARG_PARAM1);
            Log.i("ARGPARAM", "onCreate: " + mBrand);
            RptSaleGoalActivity.ccBrand = mBrand;
        }
        RptSaleGoalActivity.activityState = "FragmentLevel2";
        Log.i("backStacks", "onCreate: " + getActivity().getSupportFragmentManager().getBackStackEntryCount());
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sale_goal_level2, container, false);
        findViews(view);

        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RptSaleGoalActivity.activityState = "FragmentLevel2";
        stateMaintainer = new StateMaintainer(getFragmentManager(), "RptSaleGoalFragmentLevel2", context);
        startMVPOps();
        switch (ShowType){
            case Numerical:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.groupCommodityReportSaleGoalNumerical);
                break;
            case Percentage:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.groupCommodityReportSaleGoalPercentage);
                break;
            case Table:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.groupCommodityReportSaleGoalTable);
                break;
        }
//        initializeSaleGoalReportLevel2(rptGorohKalaHadafForoshModels);
        if (mBrand != 0)
            mPresenter.getSaleGoalReportLevel2(mBrand);
        else
            mPresenter.getAllSaleGoalReportLevel2();
        Log.d(TAG, "onViewCreated: ");



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d(TAG, "onAttach: ");
    }

    private void findViews(View view) {
        customLoadingDialog = new CustomLoadingDialog();
        recyclerViewLevel2 = view.findViewById(R.id.recyclerSaleGoalLevel2);



        ((RptSaleGoalActivity)getActivity()).fabNumerical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptGorohKalaHadafForoshModels.clear();
                RptSaleGoalActivity.ShowType= RptSaleGoalActivity.ShowType.Numerical;
                ((RptSaleGoalActivity)getActivity()).fabMenu.close(true);
                Log.d(TAG, "onClick: fabMenu"+RptSaleGoalActivity.activityState);
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.GONE);


                    if (mBrand != 0)
                        mPresenter.getSaleGoalReportLevel2(mBrand);
                    else
                        mPresenter.getAllSaleGoalReportLevel2();


//                    ((RptSaleGoalActivity)getActivity()).fabNumerical.setLabelText(getActivity().getResources().getString(R.string.showNumerical));
//                    ((RptSaleGoalActivity)getActivity()).fabNumerical.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_sharp));
                    ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.groupCommodityReportSaleGoalNumerical);


                    saleGoalBarChartAdapterLevel2.notifyDataSetChanged();

            }
        });
        (((RptSaleGoalActivity) getActivity()).fabPercentage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptGorohKalaHadafForoshModels.clear();
                RptSaleGoalActivity.ShowType= RptSaleGoalActivity.ShowType.Percentage;
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.GONE);

                if (mBrand != 0)
                    mPresenter.getSaleGoalReportLevel2(mBrand);
                else
                    mPresenter.getAllSaleGoalReportLevel2();

//                ((RptSaleGoalActivity)getActivity()).fabPercentage.setLabelText(getActivity().getResources().getString(R.string.showInPercent));
//                ((RptSaleGoalActivity)getActivity()).fabPercentage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_percent));
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.groupCommodityReportSaleGoalPercentage);


                saleGoalBarChartAdapterLevel2.notifyDataSetChanged();
            }
        });
        (((RptSaleGoalActivity) getActivity()).fabTable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptGorohKalaHadafForoshModels.clear();
                ((RptSaleGoalActivity)context).markTitleName.setText(getResources().getString(R.string.gorohKala));
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.VISIBLE);

                RptSaleGoalActivity.ShowType= RptSaleGoalActivity.ShowType.Table;
                if (mBrand != 0)
                    mPresenter.getSaleGoalReportLevel2(mBrand);
                else
                    mPresenter.getAllSaleGoalReportLevel2();

//                ((RptSaleGoalActivity)getActivity()).fabPercentage.setLabelText(getActivity().getResources().getString(R.string.showAsTable));
//                ((RptSaleGoalActivity)getActivity()).fabPercentage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_table_list));
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.groupCommodityReportSaleGoalTable);


                saleGoalBarChartAdapterLevel2.notifyDataSetChanged();
            }
        });






        //mPresenter.showPercentageReportLevel2();



    }

    @Override
    public Context getAppContext() {
        return context;
    }

    @Override
    public void drawSaleGoalReportLevel2(ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels) {
        Log.i("drawSaleGoal2", "drawSaleGoalReportLevel2: " + rptGorohKalaHadafForoshModels.size());
        initializeSaleGoalReportLevel2(rptGorohKalaHadafForoshModels);
    }


    @Override
    public void closeLoadingDialog() {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptReportSaleGoalFragment3", "closeLoadingAlert", "");
            }
        }
    }


    @Override
    public void showToast(int resId, int messageType, int duration) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(getActivity());
        customAlertDialog.showToast(getActivity(), getResources().getString(resId), messageType, duration);
    }

    private void initializeSaleGoalReportLevel2(ArrayList<BaseHadafForoshModel> rptGorohKalaHadafForoshModels) {
        if (rptGorohKalaHadafForoshModels.size()==1&&rptGorohKalaHadafForoshModels.get(0).getNameGorohKala().equals("جمع")){
            rptGorohKalaHadafForoshModels.clear();
        }
        saleGoalBarChartAdapterLevel2 = new SaleGoalBarChartsAdapter(rptGorohKalaHadafForoshModels, context, this);
        Log.d("saleGoalSizeinit", "initializeSaleGoalReportLevel2: " + rptGorohKalaHadafForoshModels.size());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewLevel2.setLayoutManager(linearLayoutManager);
        recyclerViewLevel2.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLevel2.setAdapter(saleGoalBarChartAdapterLevel2);
        SnapHelper helper = new PagerSnapHelper();
        recyclerViewLevel2.setOnFlingListener(null);
        helper.attachToRecyclerView(recyclerViewLevel2);


    }

    private void initialize(RptSaleGoalLevel2MVP.RequiredViewOps view) {
        try {
            mPresenter = new RptSaleGoalLevel2Presenter(view);
            stateMaintainer.put(RptSaleGoalLevel2MVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleReportFragmentLevel2", "initialize", "");
        }
    }


    private void reinitialize(RptSaleGoalLevel2MVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptSaleGoalLevel2MVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            Log.i("exceptionMesssagere", "reinitialize: " + exception.getMessage());
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptSaleGoalActivity", "reinitialize", "");
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleGoalReportFragmentLevel2", "startMVPOps", "");
        }
    }



    @Override
    public void onItemClickListener(BaseHadafForoshModel baseHadafForoshModel, int position) {
        ((RptSaleGoalActivity)context).markTitleName.setText(getResources().getString(R.string.nameKala));
        Bundle bundle = new Bundle();
        if (position == saleGoalBarChartAdapterLevel2.getItemCount() - 1) {
            if (mBrand != 0) {
                bundle.putInt("ccBrand", RptSaleGoalActivity.ccBrand);
                bundle.putInt("ccGoroohKala", 0);
            } else {
                bundle.putInt("ccBrand", 0);
                bundle.putInt("ccGoroohKala", 0);
            }
        } else {
            bundle.putInt("ccBrand", baseHadafForoshModel.getCcBrand());
            bundle.putInt("ccGoroohKala", baseHadafForoshModel.getCcGorohKala());
        }
        RptSaleGoalFragmentLevel3 saleGoalFragmentLevel3 = new RptSaleGoalFragmentLevel3();
        saleGoalFragmentLevel3.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, saleGoalFragmentLevel3)
                .addToBackStack(null)
                .commit();
    }
}