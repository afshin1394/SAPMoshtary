package com.saphamrah.MVP.View.RptThreeMonthPurchase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Adapter.RptThreeMonthPurchaseRizFaktorAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptThreeMonth.RptThreeMonthMVP;
import com.saphamrah.BaseMVP.RptThreeMonth.RptThreeMonthRizFaktorMVP;
import com.saphamrah.MVP.Presenter.RptThreeMonthRizFaktorPresenter;
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

public class RptThreeMonthPurchaseRizFaktorFragment extends Fragment implements RptThreeMonthRizFaktorMVP.RequiredViewOps,ThreeMonthEvents.RizFactor {
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private RptThreeMonthRizFaktorMVP.PresenterOps mPresenter;
    private Context context;
    private CustomAlertDialog customAlertDialog;


    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    int ccMoshtaryArguments = 0;

    RptThreeMonthPurchaseRizFaktorAdapter adapter;

    ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels;
    int sumTedad;
    long sumMablaghFaktor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "onCreateView: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).rizFactorEvents = RptThreeMonthPurchaseRizFaktorFragment.this;
        return inflater.inflate(R.layout.fragment_rpt_three_month_purchase_rizfaktor , container , false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**Alter fab icons**/

        ((RptThreeMonthPurchaseActivity) getActivity()).fabRefresh.setVisibility(View.GONE);
        ((RptThreeMonthPurchaseActivity) getActivity()).fabSearchName.setVisibility(View.GONE);
        ((RptThreeMonthPurchaseActivity) getActivity()).fabSearchCode.setVisibility(View.GONE);
        ((RptThreeMonthPurchaseActivity) getActivity()).fabSearchFactor.setVisibility(View.VISIBLE);



        ButterKnife.bind(this, view);
        Calligrapher calligrapher = new Calligrapher(BaseApplication.getContext());
        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ccMoshtaryArguments = getArguments().getInt(CC_MOSHTARY);
        }
        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , BaseApplication.getContext());

        customAlertDialog = new CustomAlertDialog(getActivity());
        customLoadingDialog = new CustomLoadingDialog();
        startMVPOps();

        mPresenter.getRizFaktor(ccMoshtaryArguments);




    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public Context getAppContext() {
        return context;
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

    @Override
    public void onGetRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels,int sumTedad, long sumMablaghFaktor) {
        this.rpt3MonthPurchaseModels=rpt3MonthPurchaseModels;
        this.sumTedad=sumTedad;
        this.sumMablaghFaktor=sumMablaghFaktor;
        if (rpt3MonthPurchaseModels.size() > 0) {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            adapter = new RptThreeMonthPurchaseRizFaktorAdapter(context, rpt3MonthPurchaseModels);
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
    public void onGetFilteredRizFaktor(ArrayList<Rpt3MonthPurchaseModel> rpt3MonthPurchaseModels, int sumTedad, long sumMablaghFaktor) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        adapter.setModels(rpt3MonthPurchaseModels);
        adapter.notifyDataSetChanged();
        ((RptThreeMonthPurchaseActivity) getActivity()).layTableFooter.setVisibility(View.VISIBLE);
        ((RptThreeMonthPurchaseActivity) getActivity()).lblSumTedad.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumTedad), sumTedad));
        ((RptThreeMonthPurchaseActivity) getActivity()).lblSumMablaghFaktor.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumMablagh), formatter.format(sumMablaghFaktor)));
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


    private void initialize(RptThreeMonthRizFaktorMVP.RequiredViewOps view) {
        try {
            mPresenter = new RptThreeMonthRizFaktorPresenter(view);
            stateMaintainer.put(RptThreeMonthMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchaseActivity", "initialize", "");
        }
    }


    private void reinitialize(RptThreeMonthRizFaktorMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptThreeMonthRizFaktorMVP.PresenterOps.class.getSimpleName());
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
    public void onSearchByFactorNu() {
        Log.i(TAG, "onSearchByName: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).fabMenu.close(true);
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.showSearch();
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.setHint(getResources().getString(R.string.searchFactorNumber));
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 3;
    }

    @Override
    public void onActionUpButton() {
        Log.i(TAG, "onActionUpButton: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.closeSearch();
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 0;
        onGetRizFaktor(rpt3MonthPurchaseModels, sumTedad,sumMablaghFaktor);
    }

    @Override
    public void onActionEmptyButton() {
        Log.i(TAG, "onActionEmptyButton: ");
        ((RptThreeMonthPurchaseActivity) getActivity()).searchView.closeSearch();
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 0;
        onGetRizFaktor(rpt3MonthPurchaseModels, sumTedad , sumMablaghFaktor);
    }

    @Override
    public void onSearchViewClosed() {
        Log.i(TAG, "onSearchViewClosed: ");
        ((RptThreeMonthPurchaseActivity) getActivity()). btnBack.setVisibility(View.VISIBLE);
        ((RptThreeMonthPurchaseActivity) getActivity()).searchStatus = 0;
        onGetRizFaktor(rpt3MonthPurchaseModels, sumTedad,sumMablaghFaktor);
    }

    @Override
    public void onSearchViewShown() {
        Log.i(TAG, "onSearchViewShown: ");
        ((RptThreeMonthPurchaseActivity) getActivity()). btnBack.setVisibility(View.GONE);
        ((RptThreeMonthPurchaseActivity) getActivity()).visibleCloseSearchIcon();
    }

    @Override
    public void onQueryTextSubmit(String query) {
        String searchWord =new PubFunc().new LanguageUtil().convertFaNumberToEN(query.trim());
        Log.i(TAG, "onQueryTextSubmit: ");
        if (searchWord.length()>0) {
            if (((RptThreeMonthPurchaseActivity) getActivity()).searchStatus == 3) {
                mPresenter.getFilteredListByFactorNu(ccMoshtaryArguments,searchWord);
            }
        }else {
            mPresenter.getRizFaktor(ccMoshtaryArguments);
        }
    }

    @Override
    public void onQueryTextChange(String newText) {
        Log.i(TAG, "onQueryTextChange: ");
        String searchWord =new PubFunc().new LanguageUtil().convertFaNumberToEN(newText.trim());

        if (newText.trim().length() > 0) {
            if (((RptThreeMonthPurchaseActivity) getActivity()).searchStatus == 3) {
                mPresenter.getFilteredListByFactorNu(ccMoshtaryArguments,searchWord);
            }
        } else {
            mPresenter.getRizFaktor(ccMoshtaryArguments);
            ((RptThreeMonthPurchaseActivity) getActivity()).visibleCloseSearchIcon();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: "+        getActivity().getSupportFragmentManager().getBackStackEntryCount());
        ((RptThreeMonthPurchaseActivity) getActivity()).rizFactorEvents = null;
        getActivity().getSupportFragmentManager().beginTransaction().remove(RptThreeMonthPurchaseRizFaktorFragment.this).commit();
        Log.i(TAG, "onDestroyView: "+        getActivity().getSupportFragmentManager().getBackStackEntryCount());

    }
}
