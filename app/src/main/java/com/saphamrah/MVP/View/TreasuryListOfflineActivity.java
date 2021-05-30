package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.TreasuryAdapter;
import com.saphamrah.BaseMVP.TreasuryListOfflineMVP;
import com.saphamrah.MVP.Presenter.TreasuryListOfflinePresenter;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TreasuryListOfflineActivity extends AppCompatActivity implements TreasuryListOfflineMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , TreasuryListOfflineActivity.this);
    private TreasuryListOfflineMVP.PresenterOps mPresenter;
    private final String ACTIVITY_NAME = "TreasuryListOfflineActivity";

    private final int SORT_TYPE_CUSTOMER_CODE = 1;
    private final int SORT_TYPE_ROUTING = 2;

    private CustomAlertDialog customAlertDialog;
    private int faktorRooz; // 0 -> today , 1 -> last
    private ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels;

    private TextView lblActivityTitle;
    //private TextView lblSortTitle;
    private RecyclerView recyclerViewFaktorRooz;
    private RecyclerView recyclerViewFaktorMandeDar;
    private FloatingActionButton fabChangeList;
    /*private FloatingActionButton fabSortByCustomerCode;
    private FloatingActionButton fabSortByRouting;
    private FloatingActionButton fabShowMap;*/
    private final int OPEN_INVOICE_SETTLEMENT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasury_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        fabChangeList = findViewById(R.id.fabChangeList);
        /*fabSortByCustomerCode = findViewById(R.id.fabSortByCustomerCode);
        fabSortByRouting = findViewById(R.id.fabSortByRouting);
        fabShowMap = findViewById(R.id.fabShowMap);*/
        lblActivityTitle = findViewById(R.id.lblActivityTitle);
        //lblSortTitle = findViewById(R.id.lblSortTitle);
        recyclerViewFaktorRooz = findViewById(R.id.recyclerViewRooz);
        recyclerViewFaktorMandeDar = findViewById(R.id.recyclerViewMandeDar);
        ImageView imgBack = findViewById(R.id.imgBack);

        recyclerViewFaktorRooz.setVisibility(View.VISIBLE);
        recyclerViewFaktorMandeDar.setVisibility(View.GONE);
        faktorRooz = 0;
        fabChangeList.setLabelText(getResources().getString(R.string.mandehDarVosolList));

        customAlertDialog = new CustomAlertDialog(TreasuryListOfflineActivity.this);
        darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();

        startMVPOps();

        mPresenter.checkDateAndFakeLocation();
        fabMenu.setVisibility(View.GONE);
        fabChangeList.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);

    }


    @Override
    public Context getAppContext()
    {
        return TreasuryListOfflineActivity.this;
    }



    @Override
    public void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListDarkhastFaktorMoshtaryForoshandeModel , int noeMasouliat)
    {
        this.darkhastFaktorMoshtaryForoshandeModels.clear();
        this.darkhastFaktorMoshtaryForoshandeModels.addAll(arrayListDarkhastFaktorMoshtaryForoshandeModel);
        TreasuryAdapter adapter = new TreasuryAdapter(TreasuryListOfflineActivity.this, this.darkhastFaktorMoshtaryForoshandeModels, true, noeMasouliat, new TreasuryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                if (operation == Constants.SHOW_LOCATION())
                {
                    mPresenter.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModels.get(position));
                }
                else if (operation == Constants.CLEARING())
                {
                    showToast(R.string.offlineEditVosol,Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
                else if (operation == Constants.SHOW_IMAGE())
                {
                    mPresenter.getFaktorImage(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SHOW_FAKTOR_DETAIL())
                {
                    openFaktorDetailActivity(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
//                else if (operation == Constants.SEND())
//                {
//                    showSendAlert(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor() , darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat(), position);
//                }
                else if (operation == Constants.EDIT_DARKHAST())
                {
                    showToast(R.string.offlineEditVosol,Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TreasuryListOfflineActivity.this);
        recyclerViewFaktorRooz.setLayoutManager(mLayoutManager);
        recyclerViewFaktorRooz.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFaktorRooz.addItemDecoration(new DividerItemDecoration(TreasuryListOfflineActivity.this , 0));
        recyclerViewFaktorRooz.setAdapter(adapter);
    }


    @Override
    public void onGetCustomerAddress(double latitude, double longitude)
    {
        Intent intent = new Intent(TreasuryListOfflineActivity.this , AddCustomerMapActivity.class);
        intent.putExtra("customerlat" , latitude);
        intent.putExtra("customerlng" , longitude);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void onGetFaktorImage(byte[] faktorImage)
    {
        customAlertDialog.showImage(TreasuryListOfflineActivity.this, faktorImage, false, new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}
            @Override
            public void setOnApplyClick() {}
        });
    }


    @Override
    public void openDarkhastKalaActivity(long ccDarkhastFaktor , int ccMoshtary)
    {
        Intent intent = new Intent(TreasuryListOfflineActivity.this , DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        TreasuryListOfflineActivity.this.finish();
    }



    @Override
    public void onError(final boolean closeActivity, int errorResId)
    {
        customAlertDialog.showMessageAlert(TreasuryListOfflineActivity.this, "", getResources().getString(errorResId), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                if (closeActivity)
                {
                    TreasuryListOfflineActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void onError(boolean closeActivity, String message)
    {
        customAlertDialog.showMessageAlert(TreasuryListOfflineActivity.this, closeActivity, "", message, Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showAlertMessage(int resId, int messageType)
    {
        customAlertDialog.showMessageAlert(TreasuryListOfflineActivity.this, false, "", getResources().getString(resId), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void showAlertMessage(int resId, String parameter, int messageType)
    {
        customAlertDialog.showMessageAlert(TreasuryListOfflineActivity.this, false, "", getResources().getString(resId , parameter), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(TreasuryListOfflineActivity.this, getResources().getString(resId), messageType, duration);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_INVOICE_SETTLEMENT)
        {
            mPresenter.getTreasuryList(faktorRooz , Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
        }
    }

    private void openFaktorDetailActivity(long ccDarkhastFaktor)
    {
        Intent intent = new Intent(TreasuryListOfflineActivity.this , FaktorDetailsActivity.class);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("sourceActivity" , "TreasuryListOfflineActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "startMVPOps", "");
        }
    }


    private void initialize(TreasuryListOfflineMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new TreasuryListOfflinePresenter(view);
            stateMaintainer.put(TreasuryListOfflineMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "initialize", "");
        }
    }


    private void reinitialize(TreasuryListOfflineMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(TreasuryListOfflineMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "reinitialize", "");
            }
        }
    }



}
