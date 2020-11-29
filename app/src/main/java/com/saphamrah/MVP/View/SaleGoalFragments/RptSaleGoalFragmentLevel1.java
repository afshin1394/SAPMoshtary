package com.saphamrah.MVP.View.SaleGoalFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.saphamrah.Adapter.SaleGoalBarChartsAdapter;
import com.saphamrah.BaseMVP.RptSaleGoalLevel1MVP;
import com.saphamrah.BaseMVP.SaleReportMVP;
import com.saphamrah.MVP.Presenter.SaleGoalPresenters.RptSaleGoalLevel1Presenter;

import com.saphamrah.MVP.View.RptSaleGoalActivity;

import com.saphamrah.Model.HadafForosh.BaseHadafForoshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import static com.saphamrah.MVP.View.RptSaleGoalActivity.ShowType;
import static com.saphamrah.MVP.View.RptSaleGoalActivity.ShowType.Table;


public class RptSaleGoalFragmentLevel1 extends Fragment implements RptSaleGoalLevel1MVP.RequiredViewOps, SaleGoalBarChartsAdapter.OnItemClickListener {
    Context context;
    View view;
    ImageView imgBack;
    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;
    RecyclerView recyclerView;
    SaleGoalBarChartsAdapter saleGoalBarChartsAdapter;
    ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels = new ArrayList<>();
    private RptSaleGoalLevel1MVP.PresenterOps mPresenter;
    private StateMaintainer stateMaintainer;
    private final String TAG = this.getClass().getSimpleName();
    public static boolean isDataReceived;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RptSaleGoalFragmentLevel1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RptSaleGoalFragmentLevel1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment RptSaleGoalFragmentLevel1(String param1, String param2) {
       RptSaleGoalFragmentLevel1 fragment = new RptSaleGoalFragmentLevel1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        ((RptSaleGoalActivity)getActivity()).fabRefresh.setVisibility(View.VISIBLE);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        RptSaleGoalActivity.activityState = "FragmentLevel1";
        switch (ShowType){
            case Table:
            default:

        }

        Log.i("backStacks", "onCreateLevel1: " + getActivity().getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.fragment_sale_goal_level1, container, false);
        findViews(view);
        ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.ReportSaleGoalNumerical);

        return view;
    }


    @SuppressLint("LongLogTag")
    private void findViews(View view) {

        customLoadingDialog = new CustomLoadingDialog();
        recyclerView = view.findViewById(R.id.recyclerSaleGoal);
        imgBack = view.findViewById(R.id.imgBackSaleGoal);
        Log.i("RptSaleGoalFragmentLevel1", "findViews: " + imgBack);

        ((RptSaleGoalActivity)getActivity()).fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((RptSaleGoalActivity)getActivity()).fabMenu.close(true);
                alertDialogLoading = customLoadingDialog.showLoadingDialog(getActivity());
                mPresenter.updateSaleGoalReport();
            }
        });
        ((RptSaleGoalActivity)getActivity()).fabNumerical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptBrandHadafForoshModels.clear();
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.GONE);

                ShowType= ShowType.Numerical;
                ((RptSaleGoalActivity) getActivity()).fabMenu.close(true);

                Log.d(TAG, "onClick: " + RptSaleGoalActivity.activityState);
                mPresenter.getSaleGoalReport();
                ((RptSaleGoalActivity) getActivity()).ShowType = ShowType.Numerical;
//                ((RptSaleGoalActivity) getActivity()).fabNumerical.setLabelText(getActivity().getResources().getString(R.string.showNumerical));
//                ((RptSaleGoalActivity) getActivity()).fabNumerical.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_sharp));
                ((RptSaleGoalActivity) getActivity()).activityTitle.setText(R.string.ReportSaleGoalNumerical);
                saleGoalBarChartsAdapter.notifyDataSetChanged();


            }
        });
        ((RptSaleGoalActivity)getActivity()).fabPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptBrandHadafForoshModels.clear();
                ShowType= ShowType.Percentage;
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.GONE);

                ((RptSaleGoalActivity) getActivity()).fabMenu.close(true);

                Log.d(TAG, "onClick: " + RptSaleGoalActivity.activityState);
                mPresenter.getSaleGoalReport();
                ((RptSaleGoalActivity) getActivity()).ShowType = ShowType.Percentage;
//                ((RptSaleGoalActivity) getActivity()).fabPercentage.setLabelText(getActivity().getResources().getString(R.string.showInPercent));
//                ((RptSaleGoalActivity) getActivity()).fabPercentage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_percent));
                ((RptSaleGoalActivity) getActivity()).activityTitle.setText(R.string.ReportSaleGoalPercentage);
                saleGoalBarChartsAdapter.notifyDataSetChanged();


            }
        });
        ((RptSaleGoalActivity)getActivity()).fabTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rptBrandHadafForoshModels.clear();
                ShowType= Table;
                ((RptSaleGoalActivity)context).markTitleName.setText(getResources().getString(R.string.brand));
                ((RptSaleGoalActivity)context ).layTableTitle.setVisibility(View.VISIBLE);

                ((RptSaleGoalActivity) getActivity()).fabMenu.close(true);

                Log.d(TAG, "onClick: " + RptSaleGoalActivity.activityState);
                mPresenter.getSaleGoalReport();
                ((RptSaleGoalActivity) getActivity()).ShowType = Table;
//                ((RptSaleGoalActivity) getActivity()).fabTable.setLabelText(getActivity().getResources().getString(R.string.showAsTable));
//                ((RptSaleGoalActivity) getActivity()).fabPercentage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_table_list));
                ((RptSaleGoalActivity) getActivity()).activityTitle.setText(R.string.ReportSaleGoalTable);
                saleGoalBarChartsAdapter.notifyDataSetChanged();


            }
        });





    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RptSaleGoalActivity.activityState = "FragmentLevel1";
        ((RptSaleGoalActivity)getActivity()).fabRefresh.setVisibility(View.VISIBLE);
        stateMaintainer = new StateMaintainer(getFragmentManager(), TAG, context);
        startMVPOps();
//        initializeRecyclerView(rptBrandHadafForoshModels);
        switch (ShowType){
            case Numerical:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.ReportSaleGoalNumerical);
                break;
            case Percentage:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.ReportSaleGoalPercentage);
                break;
            case Table:
                ((RptSaleGoalActivity)getActivity()).activityTitle.setText(R.string.ReportSaleGoalTable);
                break;
        }

        mPresenter.getSaleGoalReport();
        Log.i(TAG, "onViewCreated: ");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initializeRecyclerView(ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels) {
        if (rptBrandHadafForoshModels.size()==1&&rptBrandHadafForoshModels.get(0).getNameBrand().equals("جمع")){
            rptBrandHadafForoshModels.clear();
            isDataReceived=false;
        }
        saleGoalBarChartsAdapter = new SaleGoalBarChartsAdapter(rptBrandHadafForoshModels, context, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(saleGoalBarChartsAdapter);
        SnapHelper helper = new PagerSnapHelper();
        recyclerView.setOnFlingListener(null);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClickListener(BaseHadafForoshModel baseHadafForoshModel, int position) {
        ((RptSaleGoalActivity)context).markTitleName.setText(getResources().getString(R.string.gorohKala));

        if (position == saleGoalBarChartsAdapter.getItemCount() - 1) {

            RptSaleGoalFragmentLevel2 saleGoalFragmentLevel2 = new RptSaleGoalFragmentLevel2();
            Bundle bundle = new Bundle();
            bundle.putInt("ccBrand", 0);
            saleGoalFragmentLevel2.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, saleGoalFragmentLevel2)
                    .addToBackStack(null)
                    .commit();
        } else {
            RptSaleGoalFragmentLevel2 saleGoalFragmentLevel2 = new RptSaleGoalFragmentLevel2();
            Bundle bundle = new Bundle();
            bundle.putInt("ccBrand", baseHadafForoshModel.getCcBrand());
            saleGoalFragmentLevel2.setArguments(bundle);


            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, saleGoalFragmentLevel2)
                    .addToBackStack(null)
                    .commit();
        }


    }


    private void initialize(RptSaleGoalLevel1MVP.RequiredViewOps view) {
        try {
            mPresenter = new RptSaleGoalLevel1Presenter(view);
            stateMaintainer.put(SaleReportMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleReportFragmentLevel1", "initialize", "");
        }
    }


    private void reinitialize(RptSaleGoalLevel1MVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptSaleGoalLevel1MVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);

            } else {
                mPresenter.onConfigurationChanged(view);
                Log.i("mPresenterFragment", "reinitialize: " + mPresenter.toString());
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptSaleGoalActivity", "reinitialize", "");
            }
        }
    }

    public void startMVPOps() {
        try {
            Log.i("firstTimeIn", "startMVPOps: " + stateMaintainer.firstTimeIn());
            Log.i("stateMaintainer", "startMVPOps: " + stateMaintainer);
            if (stateMaintainer.firstTimeIn()) {

                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {

            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SaleGoalReportFragmentLevel1", "startMVPOps", "");
        }
    }


    @Override
    public Context getAppContext() {
        return context;
    }

    @Override
    public void drawSaleGoalReport(ArrayList<BaseHadafForoshModel> rptBrandHadafForoshModels) {
        initializeRecyclerView(rptBrandHadafForoshModels);
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
}