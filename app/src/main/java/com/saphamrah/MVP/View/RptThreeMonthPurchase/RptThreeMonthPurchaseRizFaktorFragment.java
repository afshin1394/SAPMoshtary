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

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptThreeMonthPurchaseAdapter;
import com.saphamrah.Adapter.RptThreeMonthPurchaseRizFaktorAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptThreeMonthPurchaseMVP;
import com.saphamrah.MVP.Presenter.RptThreeMonthPurchasePresenter;
import com.saphamrah.Model.Rpt3MonthPurchaseModel;
import com.saphamrah.Model.Rpt3MonthGetSumModel;
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

public class RptThreeMonthPurchaseRizFaktorFragment extends Fragment implements RptThreeMonthPurchaseMVP.RequiredViewOps {
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private RptThreeMonthPurchaseMVP.PresenterOps mPresenter;
    private Context context;
    private CustomAlertDialog customAlertDialog;
    @BindView(R.id.layTableFooter)
    LinearLayout layTableFooter;

    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lblSumTedadFaktor)
    TextView lblSumTedad;
    @BindView(R.id.lblSumMablaghFaktor)
    TextView lblSumMablaghFaktor ;
    int ccMoshtaryArguments = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_rpt_three_month_purchase_rizfaktor , container , false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Calligrapher calligrapher = new Calligrapher(BaseApplication.getContext());
        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ccMoshtaryArguments = getArguments().getInt("ccMoshtary");
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

    // just use first fragment
    @Override
    public void setListAdapter(ArrayList<Rpt3MonthGetSumModel> rtp3MonthGetSumModels, int sumTedad, long sumMablaghFaktor) {
    }

    @Override
    public void hideFooter() {
        layTableFooter.setVisibility(View.GONE);
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
        if (rpt3MonthPurchaseModels.size() > 0) {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            RptThreeMonthPurchaseRizFaktorAdapter adapter = new RptThreeMonthPurchaseRizFaktorAdapter(context, rpt3MonthPurchaseModels);
            recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context, 0));
            recyclerView.setAdapter(adapter);
            layTableFooter.setVisibility(View.VISIBLE);
            lblSumTedad.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumTedad), sumTedad));
            lblSumMablaghFaktor.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumMablagh), formatter.format(sumMablaghFaktor)));
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


    private void initialize(RptThreeMonthPurchaseMVP.RequiredViewOps view) {
        try {
            mPresenter = new RptThreeMonthPurchasePresenter(view);
            stateMaintainer.put(RptThreeMonthPurchaseMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchaseActivity", "initialize", "");
        }
    }


    private void reinitialize(RptThreeMonthPurchaseMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptThreeMonthPurchaseMVP.PresenterOps.class.getSimpleName());
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
}
