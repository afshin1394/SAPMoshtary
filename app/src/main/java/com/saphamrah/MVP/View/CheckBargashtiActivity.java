package com.saphamrah.MVP.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.CheckBargashtiAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.CheckBargashtiMVP;
import com.saphamrah.BaseMVP.TreasuryListMVP;
import com.saphamrah.MVP.Presenter.CheckBargashtiPresenter;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;

public class CheckBargashtiActivity extends AppCompatActivity implements CheckBargashtiMVP.RequiredViewOps {
    private final int OPEN_INVOICE_SETTLEMENT = 100;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, CheckBargashtiActivity.this);
    private CheckBargashtiMVP.PresenterOps mPresenter;
    private CustomAlertDialog customAlertDialog;
    private CheckBargashtiAdapter adapter;
    private final String ACTIVITY_NAME = "CheckBargashtiActivity";
    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;
    // find View
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;

    // click Listener
    @OnClick(R.id.btnBack)
    public void btnBack() {

        finish();
    }


    @OnClick(R.id.fabRefresh)
    public void fabRefresh() {
        fabMenu.close(true);
        alertDialogLoading = customLoadingDialog.showLoadingDialog(CheckBargashtiActivity.this);
        mPresenter.updateListBargashty();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bargashti);
        ButterKnife.bind(this);

        // set font
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        customAlertDialog = new CustomAlertDialog(CheckBargashtiActivity.this);
        customLoadingDialog = new CustomLoadingDialog();
        // setup before anyThing
        startMVPOps();


        recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getContext(), 0));

        mPresenter.onGetAllCheckBargashti();
    }

    // get request from SQL
    @Override
    public void onGetAllCheckBargashti(ArrayList<BargashtyModel> bargashtyModels) {
        adapter = new CheckBargashtiAdapter(BaseApplication.getContext(), bargashtyModels, (operation, position) -> {
            if (operation == Constants.EDIT()) {
                openInvoiceSettlement(bargashtyModels.get(position).getCcMoshtary()
                        , bargashtyModels.get(position).getCcDariaftPardakht()
                        , bargashtyModels.get(position).getCcNoeMoshtary()
                        , bargashtyModels.get(position).getCcDarajeh()
                        , bargashtyModels.get(position).getShomarehSanad());
            } else if (operation == Constants.SEND()) {
                showSendAlert(bargashtyModels.get(position).getCcDariaftPardakht(), position);
            }

        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(CheckBargashtiActivity.this, getResources().getString(resId), messageType, duration);

    }

    @Override
    public void showAlertMessage(int resId, int messageType) {
        customAlertDialog.showMessageAlert(this, false, "", getResources().getString(resId), messageType, getResources().getString(R.string.apply));
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

    /**
     * send check bargashty
     */
    private void showSendAlert(final long ccDarkhastFaktor, final int position) {
        customAlertDialog.showLogMessageAlert(this, false, "", getResources().getString(R.string.sendWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
            }

            @Override
            public void setOnApplyClick() {
                mPresenter.getDariaftPardakhtForSend(ccDarkhastFaktor, position);
            }
        });
    }

    /**
     * open activity
     * when is check bargashti ccDarkhastFaktor => ccDariaftPardakht
     */
    private void openInvoiceSettlement(int ccMoshtary, long ccDariaftPardakht, int ccNoeMoshtary, int ccDarajeh, String ShomarehSanad) {
        Intent intent = new Intent(this, InvoiceSettlementActivity.class);
        intent.putExtra("ccMoshtary", ccMoshtary);
        intent.putExtra("ccDarkhastFaktor", ccDariaftPardakht);
        intent.putExtra("ccNoeMoshtary", ccNoeMoshtary);
        intent.putExtra("ccDarajeh", ccDarajeh);
        intent.putExtra("ShomarehSanad", ShomarehSanad);
        intent.putExtra("sourceActivity", "CheckBargashtiActivity");
        startActivityForResult(intent, OPEN_INVOICE_SETTLEMENT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("CheckBBBBBB", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // request for get all check
//        mPresenter.onGetAllCheckBargashti();
    }

    // setup
    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "startMVPOps", "");

        }
    }


    private void initialize(CheckBargashtiMVP.RequiredViewOps view) {
        try {
            mPresenter = new CheckBargashtiPresenter(view);
            stateMaintainer.put(CheckBargashtiMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "initialize", "");
        }
    }


    private void reinitialize(CheckBargashtiMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(CheckBargashtiMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);

            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "reinitialize", "");
            }
        }
    }
}
