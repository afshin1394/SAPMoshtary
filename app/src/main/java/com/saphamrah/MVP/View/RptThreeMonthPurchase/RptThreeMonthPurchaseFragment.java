package com.saphamrah.MVP.View.RptThreeMonthPurchase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Adapter.RptThreeMonthPurchaseAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptThreeMonth.RptThreeMonthMVP;
import com.saphamrah.MVP.Presenter.RptThreeMonthPurchasePresenter;
import com.saphamrah.Model.Rpt3MonthGetSumModel;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

import static com.saphamrah.MVP.View.RptThreeMonthPurchase.RptThreeMonthPurchaseActivity.CC_MOSHTARY;

public class RptThreeMonthPurchaseFragment extends Fragment implements RptThreeMonthMVP.RequiredViewOps, ThreeMonthEvents.Purchase {
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private RptThreeMonthMVP.PresenterOps mPresenter;
    private Context context;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;





    RptThreeMonthPurchaseAdapter adapter;
    private ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels;
    private int sumTedad;
    private long sumMablaghFaktor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "onCreateView: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).purchaseEvents = RptThreeMonthPurchaseFragment.this;
        return inflater.inflate(R.layout.fragment_rpt_three_month_purchase, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Calligrapher calligrapher = new Calligrapher(BaseApplication.getContext());
        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);
        ((RptThreeMonthPurchaseActivity) getActivity()).fabRefresh.setVisibility(View.VISIBLE);
        ((RptThreeMonthPurchaseActivity) getActivity()).fabSearchName.setVisibility(View.VISIBLE);
        ((RptThreeMonthPurchaseActivity) getActivity()).fabSearchCode.setVisibility(View.VISIBLE);
        ((RptThreeMonthPurchaseActivity) getActivity()).fabSearchFactor.setVisibility(View.GONE);
        stateMaintainer = new StateMaintainer(getFragmentManager(), TAG, BaseApplication.getContext());

        customAlertDialog = new CustomAlertDialog(getActivity());
        customLoadingDialog = new CustomLoadingDialog();
        startMVPOps();

        mPresenter.getList();




    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public Context getAppContext() {
        return context;
    }



    @Override
    public void setListAdapter(ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels, int sumTedad, long sumMablaghFaktor) {
       this.rtp3MonthGetSumModels=rtp3MonthGetSumModels;
       this.sumTedad=sumTedad;
       this.sumMablaghFaktor=sumMablaghFaktor;


        Log.i("rptSize", "setListAdapter: "+rtp3MonthGetSumModels.size());
        if (rtp3MonthGetSumModels.size() > 0) {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
               adapter = new RptThreeMonthPurchaseAdapter(context, rtp3MonthGetSumModels, new RptThreeMonthPurchaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int ccMoshtary, int position) {
                    RptThreeMonthPurchaseRizFaktorFragment rptThreeMonthPurchaseRizFaktorFragment = new RptThreeMonthPurchaseRizFaktorFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt(CC_MOSHTARY, ccMoshtary);
                    rptThreeMonthPurchaseRizFaktorFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag, rptThreeMonthPurchaseRizFaktorFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context, 0));
            recyclerView.setAdapter(adapter);
            ((RptThreeMonthPurchaseActivity) getActivity()).layTableFooter.setVisibility(View.VISIBLE);
            ((RptThreeMonthPurchaseActivity) getActivity()).lblSumTedad.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumTedad), sumTedad));
            ((RptThreeMonthPurchaseActivity) getActivity()).lblSumMablaghFaktor.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumMablagh), formatter.format(sumMablaghFaktor)));
        }


    }


    @Override
    public void hideFooter() {
        if (adapter!=null) {
            adapter.getModels().clear();
            adapter.notifyDataSetChanged();
        }
        ((RptThreeMonthPurchaseActivity) getActivity()).layTableFooter.setVisibility(View.GONE);

    }

    @Override
    public void filterListAdapter(ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels, int sumTedad, long sumMablaghFaktor) {

        adapter.setModels(rtp3MonthGetSumModels);
        adapter.notifyDataSetChanged();
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        ((RptThreeMonthPurchaseActivity) getActivity()).layTableFooter.setVisibility(View.VISIBLE);
        ((RptThreeMonthPurchaseActivity) getActivity()).lblSumTedad.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumTedad), sumTedad));
        ((RptThreeMonthPurchaseActivity) getActivity()).lblSumMablaghFaktor.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumMablagh), formatter.format(sumMablaghFaktor)));
    }


    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(getActivity(), getResources().getString(resId), messageType, duration);
    }

    @Override
    public void closeLoadingDialog() {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorTozieNashodeActivity", "closeLoadingDialog", "");
            }
        }
    }


    @Override
    public void failedUpdate(String type, String error) {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchase", "closeLoadingDialog", "");
            }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchaseActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptThreeMonthMVP.RequiredViewOps view) {
        try {
            mPresenter = new RptThreeMonthPurchasePresenter(view);
            stateMaintainer.put(RptThreeMonthMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchaseActivity", "initialize", "");
        }
    }


    private void reinitialize(RptThreeMonthMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptThreeMonthMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchaseActivity", "reinitialize", "");
            }
        }
    }


    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).fabMenu.close(true);
        alertDialogLoading = customLoadingDialog.showLoadingDialog(getActivity());
        mPresenter.updateData(TAG);
    }

    @Override
    public void onSearchByName() {
        Log.i(TAG, "onSearchByName: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).fabMenu.close(true);
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.showSearch();
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.setHint(getResources().getString(R.string.searchCustomerName));
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 1;
    }

    @Override
    public void onSearchByCode() {
        Log.i(TAG, "onSearchByCode: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).fabMenu.close(true);
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.showSearch();
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.setHint(getResources().getString(R.string.searchCustomerCode));
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 2;
    }


    @Override
    public void onActionUpButton() {
        Log.i(TAG, "onActionUpButton: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.closeSearch();
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 0;
        if (rtp3MonthGetSumModels !=null)
        setListAdapter(rtp3MonthGetSumModels, sumTedad,sumMablaghFaktor);
    }

    @Override
    public void onActionEmptyButton() {
        Log.i(TAG, "onActionEmptyButton: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.closeSearch();
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 0;
        if (rtp3MonthGetSumModels !=null)
        setListAdapter(rtp3MonthGetSumModels, sumTedad , sumMablaghFaktor);

    }

    @Override
    public void onSearchViewClosed() {
        Log.i(TAG, "onSearchViewClosed: ");
        ((RptThreeMonthPurchaseActivity) getActivity()). btnBack.setVisibility(View.VISIBLE);
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 0;
        if (rtp3MonthGetSumModels !=null)
        setListAdapter(rtp3MonthGetSumModels, sumTedad,sumMablaghFaktor);
    }

    @Override
    public void onSearchViewShown() {
        Log.i(TAG, "onSearchViewShown: ");
        ((RptThreeMonthPurchaseActivity) getActivity()). btnBack.setVisibility(View.GONE);
        ((RptThreeMonthPurchaseActivity) getActivity()).visibleCloseSearchIcon();
    }

    @Override
    public void onQueryTextSubmit(String query) {
        Log.i(TAG, "onQueryTextSubmit: ");
        String searchWord =new PubFunc().new LanguageUtil().convertFaNumberToEN(query);
        if (searchWord.length()>0) {
            if (((RptThreeMonthPurchaseActivity) getActivity()).searchStatus == 1) {
                mPresenter.getListFilteredByName(rtp3MonthGetSumModels,searchWord);
            } else if (((RptThreeMonthPurchaseActivity) getActivity()).searchStatus == 2) {
                mPresenter.getListFilteredByCode(rtp3MonthGetSumModels,searchWord);
            }
        }else {
            mPresenter.getList();
        }

    }

    @Override
    public void onQueryTextChange(String query) {
        Log.i(TAG, "onQueryTextChange: ");
        String searchWord =new PubFunc().new LanguageUtil().convertFaNumberToEN(query);

        if (query.trim().length() > 0) {
            if (((RptThreeMonthPurchaseActivity) getActivity()).searchStatus == 1) {
                Log.i("RPTSIZEE", "onQueryTextChange: "+rtp3MonthGetSumModels.size());
                mPresenter.getListFilteredByName(rtp3MonthGetSumModels,searchWord);
            } else if (((RptThreeMonthPurchaseActivity) getActivity()).searchStatus == 2) {
                Log.i(TAG, "onQueryTextChange: "+query.trim());
                mPresenter.getListFilteredByCode(rtp3MonthGetSumModels,searchWord);
            }
        } else {
            mPresenter.getList();
            ((RptThreeMonthPurchaseActivity) getActivity()).visibleCloseSearchIcon();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((RptThreeMonthPurchaseActivity) getActivity()).purchaseEvents = null;
    }

}
