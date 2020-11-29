package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.TreasuryAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.TreasuryListMVP;
import com.saphamrah.MVP.Presenter.TreasuryListPresenter;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TreasuryListActivity extends AppCompatActivity implements TreasuryListMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , TreasuryListActivity.this);
    private TreasuryListMVP.PresenterOps mPresenter;
    private final String ACTIVITY_NAME = "TreasuryListActivity";

    private final int SORT_TYPE_CUSTOMER_CODE = 1;
    private final int SORT_TYPE_ROUTING = 2;


    private int faktorRooz; // 0 -> today , 1 -> last
    private ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels;

    private TextView lblActivityTitle;
    private TextView lblSortTitle;
    private RecyclerView recyclerViewFaktorRooz;
    private RecyclerView recyclerViewFaktorMandeDar;
    private FloatingActionButton fabChangeList;
    private FloatingActionButton fabSortByCustomerCode;
    private FloatingActionButton fabSortByRouting;
    private FloatingActionButton fabShowMap;
    private final int OPEN_INVOICE_SETTLEMENT = 100;
    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;
    private CustomAlertDialog customAlertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasury_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        fabChangeList = findViewById(R.id.fabChangeList);
        fabSortByCustomerCode = findViewById(R.id.fabSortByCustomerCode);
        fabSortByRouting = findViewById(R.id.fabSortByRouting);
        fabShowMap = findViewById(R.id.fabShowMap);
        lblActivityTitle = findViewById(R.id.lblActivityTitle);
        lblSortTitle = findViewById(R.id.lblSortTitle);
        recyclerViewFaktorRooz = findViewById(R.id.recyclerViewRooz);
        recyclerViewFaktorMandeDar = findViewById(R.id.recyclerViewMandeDar);
        ImageView imgBack = findViewById(R.id.imgBack);

        recyclerViewFaktorRooz.setVisibility(View.VISIBLE);
        recyclerViewFaktorMandeDar.setVisibility(View.GONE);
        faktorRooz = 0;
        setSortTypeTitle(SORT_TYPE_CUSTOMER_CODE);
        fabChangeList.setLabelText(getResources().getString(R.string.mandehDarVosolList));

        customAlertDialog = new CustomAlertDialog(TreasuryListActivity.this);
        customLoadingDialog = new CustomLoadingDialog();


        darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();

        startMVPOps();

        mPresenter.checkDateAndFakeLocation();

        fabChangeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                if (faktorRooz == 1)
                {
                    faktorRooz = 0;
                    lblActivityTitle.setText(getResources().getString(R.string.todayVosolList));
                    fabChangeList.setLabelText(getResources().getString(R.string.mandehDarVosolList));
                    recyclerViewFaktorRooz.setVisibility(View.VISIBLE);
                    recyclerViewFaktorMandeDar.setVisibility(View.GONE);
                }
                else if (faktorRooz == 0)
                {
                    faktorRooz = 1;
                    lblActivityTitle.setText(getResources().getString(R.string.mandehDarVosolList));
                    fabChangeList.setLabelText(getResources().getString(R.string.todayVosolList));
                    recyclerViewFaktorRooz.setVisibility(View.GONE);
                    recyclerViewFaktorMandeDar.setVisibility(View.VISIBLE);
                }
                mPresenter.getTreasuryList(faktorRooz , Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
            }
        });


        fabSortByCustomerCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                mPresenter.getTreasuryList(faktorRooz , Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
            }
        });

        fabSortByRouting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                mPresenter.getTreasuryListWithRouting(darkhastFaktorMoshtaryForoshandeModels);
            }
        });


        fabShowMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openMapList();
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TreasuryListActivity.this.finish();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return TreasuryListActivity.this;
    }



    @Override
    public void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListDarkhastFaktorMoshtaryForoshandeModel , int noeMasouliat)
    {
        this.darkhastFaktorMoshtaryForoshandeModels.clear();
        this.darkhastFaktorMoshtaryForoshandeModels.addAll(arrayListDarkhastFaktorMoshtaryForoshandeModel);
        TreasuryAdapter adapter = new TreasuryAdapter(TreasuryListActivity.this, this.darkhastFaktorMoshtaryForoshandeModels, true, noeMasouliat, new TreasuryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                if (operation == Constants.SHOW_LOCATION())
                {
                    mPresenter.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModels.get(position));
                }
                else if (operation == Constants.EDIT())
                {
                    openInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcMoshtary() , darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SHOW_IMAGE())
                {
                    mPresenter.getFaktorImage(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SHOW_FAKTOR_DETAIL())
                {
                    openFaktorDetailActivity(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SEND())
                {
                    showSendAlert(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor() , darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat(), position);
                }
                else if (operation == Constants.EDIT_DARKHAST())
                {
                    showLoading();
                    Log.d("TreasuryListActivity","Loading 208");
                    mPresenter.setDarkhastFaktorShared(darkhastFaktorMoshtaryForoshandeModels.get(position));
                }
            }
        });
        setSortTypeTitle(SORT_TYPE_CUSTOMER_CODE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TreasuryListActivity.this);
        recyclerViewFaktorRooz.setLayoutManager(mLayoutManager);
        recyclerViewFaktorRooz.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFaktorRooz.addItemDecoration(new DividerItemDecoration(TreasuryListActivity.this , 0));
        recyclerViewFaktorRooz.setAdapter(adapter);
    }

    @Override
    public void onGetFaktorMandeDar(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListDarkhastFaktorMoshtaryForoshandeModels , int noeMasouliat)
    {
        this.darkhastFaktorMoshtaryForoshandeModels.clear();
        this.darkhastFaktorMoshtaryForoshandeModels.addAll(arrayListDarkhastFaktorMoshtaryForoshandeModels);
        TreasuryAdapter adapter = new TreasuryAdapter(TreasuryListActivity.this, this.darkhastFaktorMoshtaryForoshandeModels , false, noeMasouliat, new TreasuryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                if (operation == Constants.SHOW_LOCATION())
                {
                    mPresenter.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModels.get(position));
                }
                else if (operation == Constants.EDIT())
                {
                    openInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcMoshtary() , darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SHOW_IMAGE())
                {
                    mPresenter.getFaktorImage(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SEND())
                {
                    showSendAlert(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor() , darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat(), position);
                }
            }
        });
        setSortTypeTitle(SORT_TYPE_CUSTOMER_CODE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TreasuryListActivity.this);
        recyclerViewFaktorMandeDar.setLayoutManager(mLayoutManager);
        recyclerViewFaktorMandeDar.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFaktorMandeDar.addItemDecoration(new DividerItemDecoration(TreasuryListActivity.this , 0));
        recyclerViewFaktorMandeDar.setAdapter(adapter);
    }

    @Override
    public void onGetTreasuryListWithRouting(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListDarkhastFaktorMoshtaryForoshandeModels , int noeMasouliat)
    {
        this.darkhastFaktorMoshtaryForoshandeModels.clear();
        this.darkhastFaktorMoshtaryForoshandeModels.addAll(arrayListDarkhastFaktorMoshtaryForoshandeModels);
        TreasuryAdapter adapter = new TreasuryAdapter(TreasuryListActivity.this, this.darkhastFaktorMoshtaryForoshandeModels, true, noeMasouliat, new TreasuryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                if (operation == Constants.SHOW_LOCATION())
                {
                    mPresenter.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModels.get(position));
                }
                else if (operation == Constants.EDIT())
                {
                    openInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcMoshtary() , darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SHOW_IMAGE())
                {
                    mPresenter.getFaktorImage(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SHOW_FAKTOR_DETAIL())
                {

                    openFaktorDetailActivity(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                }
                else if (operation == Constants.SEND())
                {
                    showSendAlert(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor() , darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat(), position);
                }
                else if (operation == Constants.EDIT_DARKHAST())
                {
                    showLoading();
                    Log.d("TreasuryListActivity","Loading 287");
                    mPresenter.setDarkhastFaktorShared(darkhastFaktorMoshtaryForoshandeModels.get(position));
                }
            }
        });
        setSortTypeTitle(SORT_TYPE_ROUTING);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TreasuryListActivity.this);
        recyclerViewFaktorRooz.setLayoutManager(mLayoutManager);
        recyclerViewFaktorRooz.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFaktorRooz.addItemDecoration(new DividerItemDecoration(TreasuryListActivity.this , 0));
        recyclerViewFaktorRooz.setAdapter(adapter);
    }

    @Override
    public void onGetCustomerAddress(double latitude, double longitude)
    {
        Intent intent = new Intent(TreasuryListActivity.this , AddCustomerMapActivity.class);
        intent.putExtra("customerlat" , latitude);
        intent.putExtra("customerlng" , longitude);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void onGetFaktorImage(byte[] faktorImage)
    {
        customAlertDialog.showImage(TreasuryListActivity.this, faktorImage, false, new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}
            @Override
            public void setOnApplyClick() {}
        });
    }


    @Override
    public void openDarkhastKalaActivity(long ccDarkhastFaktor , int ccMoshtary)
    {
        closeLoading();
        Log.d("TreasuryListActivity","Loading ope");
        Intent intent = new Intent(TreasuryListActivity.this , DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        TreasuryListActivity.this.finish();
    }

    @Override
    public void showHideFabButtons(boolean faktorRooz)
    {
        if (faktorRooz)
        {
            fabSortByCustomerCode.showButtonInMenu(true);
            fabSortByCustomerCode.show(true);
            fabSortByRouting.showButtonInMenu(true);
            fabSortByRouting.show(true);
            fabShowMap.showButtonInMenu(true);
            fabShowMap.show(true);
        }
        else
        {
            fabSortByCustomerCode.hideButtonInMenu(true);
            fabSortByCustomerCode.hide(true);
            fabSortByRouting.hideButtonInMenu(true);
            fabSortByRouting.hide(true);
            fabShowMap.hideButtonInMenu(true);
            fabShowMap.hide(true);
        }
    }

    @Override
    public void onError(final boolean closeActivity, int errorResId)
    {
        customAlertDialog.showMessageAlert(TreasuryListActivity.this, "", getResources().getString(errorResId), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                if (closeActivity)
                {
                    TreasuryListActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void onError(boolean closeActivity, String message)
    {
        customAlertDialog.showMessageAlert(TreasuryListActivity.this, closeActivity, "", message, Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showAlertMessage(int resId, int messageType)
    {
        customAlertDialog.showMessageAlert(TreasuryListActivity.this, false, "", getResources().getString(resId), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void showAlertMessage(int resId, String parameter, int messageType)
    {
        customAlertDialog.showMessageAlert(TreasuryListActivity.this, false, "", getResources().getString(resId , parameter), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(TreasuryListActivity.this, getResources().getString(resId), messageType, duration);
    }


    private void setSortTypeTitle(int sortType)
    {
        if (sortType == SORT_TYPE_CUSTOMER_CODE)
        {
            lblSortTitle.setText(getString(R.string.sortWithCustomerCode));
        }
        else if (sortType == SORT_TYPE_ROUTING)
        {
            lblSortTitle.setText(getString(R.string.sortWithRouting));
        }
        else
        {
            lblSortTitle.setText("");
        }
    }

    private void showSendAlert(final long ccDarkhastFaktor , final int codeVazeiat , final int position)
    {
        customAlertDialog.showLogMessageAlert(TreasuryListActivity.this, false, "", getResources().getString(R.string.sendWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}
            @Override
            public void setOnApplyClick() {
                mPresenter.getDariaftPardakhtForSend(ccDarkhastFaktor , codeVazeiat , position);
            }
        });
    }


    private void openMapList()
    {
        Intent intent = new Intent(TreasuryListActivity.this , TreasuryListMapActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }


    private void openInvoiceSettlement(int ccMoshtary , long ccDarkhastFaktor)
    {
        Log.d("treasury" , "ccDarkhastFaktor : " + ccDarkhastFaktor);
        Intent intent = new Intent(TreasuryListActivity.this , InvoiceSettlementActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("sourceActivity" , "TreasuryListActivity");
        startActivityForResult(intent , OPEN_INVOICE_SETTLEMENT);
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
        Intent intent = new Intent(TreasuryListActivity.this , FaktorDetailsActivity.class);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("sourceActivity" , "TreasuryListActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void showLoading()
    {
        Log.d("loading" , "show Loading");
        if (alertDialogLoading == null)
        {
            alertDialogLoading = customLoadingDialog.showLoadingDialog(TreasuryListActivity.this);
        }
    }

    @Override
    public void closeLoading()
    {
        if (alertDialogLoading != null)
        {
            try
            {
                alertDialogLoading.dismiss();
                alertDialogLoading = null;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "startMVPOps", "");
        }
    }


    private void initialize(TreasuryListMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new TreasuryListPresenter(view);
            stateMaintainer.put(TreasuryListMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "initialize", "");
        }
    }


    private void reinitialize(TreasuryListMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(TreasuryListMVP.PresenterOps.class.getSimpleName());
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
