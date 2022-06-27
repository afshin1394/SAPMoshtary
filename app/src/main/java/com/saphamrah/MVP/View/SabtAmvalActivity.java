package com.saphamrah.MVP.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.SabtAmvalAdapter;
import com.saphamrah.BaseMVP.SabtAmvalMVP;
import com.saphamrah.MVP.Presenter.SabtAmvalPresenter;
import com.saphamrah.Model.AmvalModel;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

public class SabtAmvalActivity extends AppCompatActivity implements SabtAmvalMVP.RequiredViewOps {

    private FloatingActionButton fabSabtAmval;
    private FloatingActionButton fabupdate;
    private FloatingActionMenu fabMenu;
    private int ccMoshtary;
    private int ccSazmanForosh;
    private static final int REQUEST_CODE_BARCODE_SCANNER = 100;

    private CustomAlertDialog customAlertDialog;
    private final String TAG = this.getClass().getSimpleName();
    private SabtAmvalMVP.PresenterOps mPresenter;
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , SabtAmvalActivity.this);

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabt_amval);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        if (bundle != null) {
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(SabtAmvalActivity.this);

            ccSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getCcSazmanForosh(), -1);

            ccMoshtary = bundle.getInt("ccMoshtary");

            Log.i(TAG, "onCreate2323: " + ccSazmanForosh);


        }
        customAlertDialog = new CustomAlertDialog(SabtAmvalActivity.this);

        fabSabtAmval = findViewById(R.id.fabSabtAmval);
        fabMenu = findViewById(R.id.fabMenu);
        fabupdate = findViewById(R.id.fabupdate);
        recyclerView = findViewById(R.id.sabtamval_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabSabtAmval.setOnClickListener(v-> {
            fabMenu.close(true);
            startActivityForResult(new Intent(SabtAmvalActivity.this, BarcodeScannerActivity.class), REQUEST_CODE_BARCODE_SCANNER);

        });

        fabupdate.setOnClickListener(view -> {
            customAlertDialog.showLogMessageAlert(SabtAmvalActivity.this, false, "", getResources().getString(R.string.confirmMessageForUpdate), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse()
            {
                @Override
                public void setOnCancelClick()
                {

                }

                @Override
                public void setOnApplyClick()
                {
                    fabMenu.close(true);
                    mPresenter.getListAmvals(ccMoshtary,ccSazmanForosh);

                    SabtAmvalAdapter adapter = new SabtAmvalAdapter(getAppContext(),new ArrayList<AmvalModel>());
                    recyclerView.setAdapter(adapter);
                }
            });
        });

        startMVPOps();

        mPresenter.getListAmvals(ccMoshtary,ccSazmanForosh);

        SabtAmvalAdapter adapter = new SabtAmvalAdapter(getAppContext(),new ArrayList<AmvalModel>());
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BARCODE_SCANNER) {
            if (resultCode == RESULT_OK) {
                setDataFromScannerToView(data);

                mPresenter.getSabtedMal(ccMoshtary);
            }
        }
    }

    private void setDataFromScannerToView(Intent data) {

        if (data != null) {
            String scannerData = data.getStringExtra("data");
//            txtAmvalBarcode.setText(scannerData);

            int isSuccess = mPresenter.amvalSabtShodeh(scannerData, ccMoshtary);


            if (isSuccess == 1){
                customAlertDialog.showLogMessageAlert(SabtAmvalActivity.this, false, "", getResources().getString(R.string.confirmMessageForBarcode), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse()
                {
                    @Override
                    public void setOnCancelClick()
                    {

                    }

                    @Override
                    public void setOnApplyClick()
                    {
                        fabMenu.close(true);
                        startActivityForResult(new Intent(SabtAmvalActivity.this, BarcodeScannerActivity.class), REQUEST_CODE_BARCODE_SCANNER);
                    }
                });
            }
            else if (isSuccess == 0){
                customAlertDialog.showLogMessageAlert(SabtAmvalActivity.this, false, "", getResources().getString(R.string.warnMessageForBarcode), Constants.FAILED_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse()
                {
                    @Override
                    public void setOnCancelClick()
                    {

                    }

                    @Override
                    public void setOnApplyClick()
                    {
                        fabMenu.close(true);
                        startActivityForResult(new Intent(SabtAmvalActivity.this, BarcodeScannerActivity.class), REQUEST_CODE_BARCODE_SCANNER);
                    }
                });
            }

            else {
                customAlertDialog.showLogMessageAlert(SabtAmvalActivity.this, false, "", getResources().getString(R.string.warnMessageForRepeatedBarcode), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse()
                {
                    @Override
                    public void setOnCancelClick()
                    {

                    }

                    @Override
                    public void setOnApplyClick()
                    {
                        fabMenu.close(true);
                        startActivityForResult(new Intent(SabtAmvalActivity.this, BarcodeScannerActivity.class), REQUEST_CODE_BARCODE_SCANNER);
                    }
                });

            }
            Log.i(TAG, "setDataFromScannerToView: " + isSuccess);
        }
    }


    public void startMVPOps()
    {
        try
        {
            if ( stateMaintainer.firstTimeIn() )
            {
                initialize(this);
            }
            else
            {
                reinitialize(this);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
//            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SabtAmvalActivity", "startMVPOps", "");
        }
    }

    private void initialize(SabtAmvalMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new SabtAmvalPresenter(view);
            stateMaintainer.put(SabtAmvalMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
//            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SabtAmvalActivity", "initialize", "");
        }
    }

    private void reinitialize(SabtAmvalMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(SabtAmvalMVP.PresenterOps.class.getSimpleName());
            if ( mPresenter == null )
            {
                initialize( view );
            }
            else
            {
                mPresenter.onConfigurationChanged(view);
            }
        }
        catch (Exception exception)
        {
            if (mPresenter != null)
            {
//                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorTozieNashodeActivity", "reinitialize", "");
            }
        }
    }

    @Override
    public Context getAppContext() {
        return SabtAmvalActivity.this;
    }

    @Override
    public void setListAdapter(ArrayList<AmvalModel> models) {
//        SabtAmvalAdapter adapter = new SabtAmvalAdapter(getAppContext(),models);
//
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onArraySabtMalListener(ArrayList<AmvalModel> sabtMoshtaryAmvalModels) {
        SabtAmvalAdapter adapter = new SabtAmvalAdapter(getAppContext(),sabtMoshtaryAmvalModels);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {

    }
}