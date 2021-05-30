package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.TreasuryAdapter;
import com.saphamrah.BaseMVP.TreasuryListMVP;
import com.saphamrah.MVP.Presenter.TreasuryListPresenter;
import com.saphamrah.MVP.View.marjoee.DarkhastFaktorMarjoeeActivity;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TreasuryListActivity extends AppCompatActivity implements TreasuryListMVP.RequiredViewOps {

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, TreasuryListActivity.this);
    private TreasuryListMVP.PresenterOps mPresenter;
    private final String ACTIVITY_NAME = "TreasuryListActivity";
    private TreasuryAdapter adapter;


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
    private FloatingActionButton fabHelp;
    private FloatingActionButton fabUpdateMamorPakhshLocations;
    private final int OPEN_INVOICE_SETTLEMENT = 100;
    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;
    private CustomAlertDialog customAlertDialog;
    private int sortList = 0;
    private int noeMasouliat = 0;
    private int sort = 0;
    private View alertView;
    private AlertDialog show;
    private FloatingActionMenu fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasury_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        fabMenu = findViewById(R.id.fabMenu);
        fabChangeList = findViewById(R.id.fabChangeList);
        fabHelp = findViewById(R.id.fabHelp);
        fabSortByCustomerCode = findViewById(R.id.fabSortByCustomerCode);
        fabSortByRouting = findViewById(R.id.fabSortByRouting);
        fabShowMap = findViewById(R.id.fabShowMap);
        fabUpdateMamorPakhshLocations = findViewById(R.id.fabUpdateMamorPakhshGpsData);
        lblActivityTitle = findViewById(R.id.lblActivityTitle);
        lblSortTitle = findViewById(R.id.lblSortTitle);
        recyclerViewFaktorRooz = findViewById(R.id.recyclerViewRooz);
        recyclerViewFaktorMandeDar = findViewById(R.id.recyclerViewMandeDar);
        ImageView imgBack = findViewById(R.id.imgBack);

        recyclerViewFaktorRooz.setVisibility(View.VISIBLE);
        recyclerViewFaktorMandeDar.setVisibility(View.GONE);
        faktorRooz = 0;
        fabChangeList.setLabelText(getResources().getString(R.string.mandehDarVosolList));

        customAlertDialog = new CustomAlertDialog(TreasuryListActivity.this);
        customLoadingDialog = new CustomLoadingDialog();


        darkhastFaktorMoshtaryForoshandeModels = new ArrayList<>();

        startMVPOps();
        /*
         * set recycler
         */
        recyclerFaktorRooz();
        recylerFaktorMandeDar();
        recyclerTreasuryListWithRouting();

        fabHelp.setOnClickListener(v -> {

            fabMenu.close(true);
            showAddItemAlert();

        });

        fabChangeList.setOnClickListener(v -> {
            fabMenu.close(true);
            darkhastFaktorMoshtaryForoshandeModels.clear();
            recyclerViewFaktorMandeDar.setAdapter(null);
            recyclerViewFaktorRooz.setAdapter(null);
            if (faktorRooz == 1) {
                /*
                 ** show fab button
                 */
                fabSortByCustomerCode.showButtonInMenu(true);
                fabSortByCustomerCode.show(true);

                fabSortByRouting.showButtonInMenu(true);
                fabSortByRouting.show(true);

                fabShowMap.showButtonInMenu(true);
                fabShowMap.show(true);


                faktorRooz = 0;
                lblActivityTitle.setText(getResources().getString(R.string.todayVosolList));
                fabChangeList.setLabelText(getResources().getString(R.string.mandehDarVosolList));
                recyclerViewFaktorRooz.setVisibility(View.VISIBLE);
                recyclerViewFaktorMandeDar.setVisibility(View.GONE);
            } else if (faktorRooz == 0) {
                /*
                 ** hide fab button
                 */

                fabSortByRouting.hideButtonInMenu(true);
                fabSortByRouting.hide(true);

                fabSortByCustomerCode.hideButtonInMenu(true);
                fabSortByCustomerCode.hide(true);

                fabShowMap.hideButtonInMenu(true);
                fabShowMap.hide(true);


                faktorRooz = 1;
                lblActivityTitle.setText(getResources().getString(R.string.mandehDarVosolList));
                fabChangeList.setLabelText(getResources().getString(R.string.todayVosolList));
                recyclerViewFaktorRooz.setVisibility(View.GONE);
                recyclerViewFaktorMandeDar.setVisibility(View.VISIBLE);
            }
            mPresenter.getTreasuryList(faktorRooz, Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
        });


        fabSortByCustomerCode.setOnClickListener(v -> {
            if (sortList == Constants.SORT_TREASURY_BY_CUSTOMER_CODE) {
                customAlertDialog.showToast(TreasuryListActivity.this, getResources().getString(R.string.isSelectedSort), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            } else {
                fabMenu.close(true);
                mPresenter.getTreasuryList(faktorRooz, Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
            }
        });

        fabSortByRouting.setOnClickListener(v -> {
            if (sortList == Constants.SORT_TREASURY_BY_ROUTING) {
                customAlertDialog.showToast(TreasuryListActivity.this, getResources().getString(R.string.isSelectedSort), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            } else {
                fabMenu.close(true);
                mPresenter.getTreasuryListWithRouting(darkhastFaktorMoshtaryForoshandeModels);
            }


        });


        fabShowMap.setOnClickListener(v ->
                openMapList());


        imgBack.setOnClickListener(v ->
                TreasuryListActivity.this.finish());


        fabUpdateMamorPakhshLocations.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v)
            {
                showLoading();
                mPresenter.updateGpsData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkDateAndFakeLocation();
    }

    @Override
    public Context getAppContext() {
        return TreasuryListActivity.this;
    }



    @Override
    public void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListDarkhastFaktorMoshtaryForoshandeModel, int noeMasouliat, int sort) {

        this.darkhastFaktorMoshtaryForoshandeModels.clear();
        this.darkhastFaktorMoshtaryForoshandeModels.addAll(arrayListDarkhastFaktorMoshtaryForoshandeModel);
        this.noeMasouliat = noeMasouliat;
        this.sort = sort;

        recyclerFaktorRooz();
    }


    @Override
    public void onGetFaktorMandeDar(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListDarkhastFaktorMoshtaryForoshandeModels, int noeMasouliat) {
        this.darkhastFaktorMoshtaryForoshandeModels.clear();
        this.darkhastFaktorMoshtaryForoshandeModels.addAll(arrayListDarkhastFaktorMoshtaryForoshandeModels);

        recylerFaktorMandeDar();

    }


    @Override
    public void onGetTreasuryListWithRouting(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> arrayListDarkhastFaktorMoshtaryForoshandeModels, int noeMasouliat) {
        this.darkhastFaktorMoshtaryForoshandeModels.clear();
        this.darkhastFaktorMoshtaryForoshandeModels.addAll(arrayListDarkhastFaktorMoshtaryForoshandeModels);

        recyclerTreasuryListWithRouting();

    }

    /**
     * set recycler for first Time
     */
    private void recyclerFaktorRooz() {
        adapter = new TreasuryAdapter(TreasuryListActivity.this, this.darkhastFaktorMoshtaryForoshandeModels, true, noeMasouliat, new TreasuryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                if (operation == Constants.SHOW_LOCATION()) {
                    mPresenter.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModels.get(position));
                } else if (operation == Constants.CLEARING()) {
                    mPresenter.checkIsLocationSendToServer(darkhastFaktorMoshtaryForoshandeModels.get(position));
                } else if (operation == Constants.SHOW_IMAGE()) {
                    mPresenter.getFaktorImage(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                } else if (operation == Constants.SHOW_FAKTOR_DETAIL()) {
                    openFaktorDetailActivity(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                } else if (operation == Constants.SEND()) {
                    showSendAlert(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor(), darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat(), position);
                } else if (operation == Constants.EDIT_DARKHAST()) {
                    showLoading();
                    Log.d("TreasuryListActivity", "Loading 208");
                    mPresenter.setDarkhastFaktorShared(darkhastFaktorMoshtaryForoshandeModels.get(position));
                } else if (operation == Constants.MARJOEE) {
                    Log.d("MarjoeeKoliFragment", String.valueOf(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor()));
                    startActivityBundle(DarkhastFaktorMarjoeeActivity.class, "marjoee", String.valueOf(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor()), "ccMoshtaryMarjoee", String.valueOf(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcMoshtary()));
                } else if (operation == Constants.SAVE_SEND_LOCATION) {
                    showLoading();
                    mPresenter.checkMoshtaryKharejAzMahal(darkhastFaktorMoshtaryForoshandeModels.get(position));

                }
            }
        });
        setSortTypeTitle(sort);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TreasuryListActivity.this);
        recyclerViewFaktorRooz.setLayoutManager(mLayoutManager);
        recyclerViewFaktorRooz.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFaktorRooz.addItemDecoration(new DividerItemDecoration(TreasuryListActivity.this, 0));
        recyclerViewFaktorRooz.setAdapter(adapter);
    }

    private void recylerFaktorMandeDar() {
        adapter = new TreasuryAdapter(TreasuryListActivity.this, this.darkhastFaktorMoshtaryForoshandeModels, false, noeMasouliat, new TreasuryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                if (operation == Constants.SHOW_LOCATION()) {
                    mPresenter.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModels.get(position));
                } else if (operation == Constants.CLEARING()) {
                    openInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcMoshtary(), darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                } else if (operation == Constants.SHOW_IMAGE()) {
                    mPresenter.getFaktorImage(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                } else if (operation == Constants.SEND()) {
                    showSendAlert(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor(), darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat(), position);
                }
            }
        });
        setSortTypeTitle(Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TreasuryListActivity.this);
        recyclerViewFaktorMandeDar.setLayoutManager(mLayoutManager);
        recyclerViewFaktorMandeDar.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFaktorMandeDar.addItemDecoration(new DividerItemDecoration(TreasuryListActivity.this, 0));
        recyclerViewFaktorMandeDar.setAdapter(adapter);
    }

    private void recyclerTreasuryListWithRouting() {
        adapter = new TreasuryAdapter(TreasuryListActivity.this, this.darkhastFaktorMoshtaryForoshandeModels, true, noeMasouliat, new TreasuryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                if (fabMenu.isOpened())
                    fabMenu.close(true);
                if (operation == Constants.SHOW_LOCATION()) {
                    mPresenter.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModels.get(position));
                } else if (operation == Constants.CLEARING()) {
                    openInvoiceSettlement(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcMoshtary(), darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                } else if (operation == Constants.SHOW_IMAGE()) {
                    mPresenter.getFaktorImage(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                } else if (operation == Constants.SHOW_FAKTOR_DETAIL()) {
                    openFaktorDetailActivity(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor());
                } else if (operation == Constants.SEND()) {
                    showSendAlert(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor(), darkhastFaktorMoshtaryForoshandeModels.get(position).getCodeVazeiat(), position);
                } else if (operation == Constants.EDIT_DARKHAST()) {
                    showLoading();
                    Log.d("TreasuryListActivity", "Loading 287");
                    mPresenter.setDarkhastFaktorShared(darkhastFaktorMoshtaryForoshandeModels.get(position));
                } else if (operation == Constants.MARJOEE) {
                    startActivityBundle(DarkhastFaktorMarjoeeActivity.class, "marjoee", String.valueOf(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcDarkhastFaktor()), "ccMoshtaryMarjoee", String.valueOf(darkhastFaktorMoshtaryForoshandeModels.get(position).getCcMoshtary()));
                }
            }
        });
        setSortTypeTitle(Constants.SORT_TREASURY_BY_ROUTING);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TreasuryListActivity.this);
        recyclerViewFaktorRooz.setLayoutManager(mLayoutManager);
        recyclerViewFaktorRooz.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFaktorRooz.addItemDecoration(new DividerItemDecoration(TreasuryListActivity.this, 0));
        recyclerViewFaktorRooz.setAdapter(adapter);
    }

    @Override
    public void onGetCustomerAddress(double latitude, double longitude) {
        Intent intent = new Intent(TreasuryListActivity.this, AddCustomerMapActivity.class);
        intent.putExtra("customerlat", latitude);
        intent.putExtra("customerlng", longitude);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void onGetFaktorImage(byte[] faktorImage) {
        customAlertDialog.showImage(TreasuryListActivity.this, faktorImage, false, new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
            }

            @Override
            public void setOnApplyClick() {
            }
        });
    }


    @Override
    public void openDarkhastKalaActivity(long ccDarkhastFaktor, int ccMoshtary) {
        closeLoading();
        Log.d("TreasuryListActivity", "Loading ope");
        Intent intent = new Intent(TreasuryListActivity.this, DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary", ccMoshtary);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        TreasuryListActivity.this.finish();
    }

    @Override
    public void showHideFabButtons(boolean faktorRooz) {
        if (faktorRooz) {
            fabSortByCustomerCode.showButtonInMenu(true);
            fabSortByCustomerCode.show(true);
            fabSortByRouting.showButtonInMenu(true);
            fabSortByRouting.show(true);
            fabShowMap.showButtonInMenu(true);
            fabShowMap.show(true);
            fabUpdateMamorPakhshLocations.show(true);
            fabUpdateMamorPakhshLocations.showButtonInMenu(true);
        } else {
            fabSortByCustomerCode.hideButtonInMenu(true);
            fabSortByCustomerCode.hide(true);
            fabSortByRouting.hideButtonInMenu(true);
            fabSortByRouting.hide(true);
            fabShowMap.hideButtonInMenu(true);
            fabShowMap.hide(true);
            fabUpdateMamorPakhshLocations.hide(true);
            fabUpdateMamorPakhshLocations.hideButtonInMenu(true);

        }
    }

    @Override
    public void onError(final boolean closeActivity, int errorResId) {
        customAlertDialog.showMessageAlert(TreasuryListActivity.this, "", getResources().getString(errorResId), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                if (closeActivity) {
                    TreasuryListActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void onError(boolean closeActivity, String message) {
        customAlertDialog.showMessageAlert(TreasuryListActivity.this, closeActivity, "", message, Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showAlertMessage(int resId, int messageType) {
        customAlertDialog.showMessageAlert(TreasuryListActivity.this, false, "", getResources().getString(resId), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void showAlertMessage(int resId, String parameter, int messageType) {
        customAlertDialog.showMessageAlert(TreasuryListActivity.this, false, "", getResources().getString(resId, parameter), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(TreasuryListActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void onSuccessSend(int position) {
        mPresenter.getTreasuryList(faktorRooz, sort);
    }

    /**
     * sort is 1 = SORT_TREASURY_BY_ROUTING
     * sort is 2 = SORT_TREASURY_BY_CUSTOMER_CODE
     */
    private void setSortTypeTitle(int sortType) {
        sortList = sortType;
        if (sortType == Constants.SORT_TREASURY_BY_CUSTOMER_CODE) {
            lblSortTitle.setText(getString(R.string.sortWithCustomerCode));
        } else if (sortType == Constants.SORT_TREASURY_BY_ROUTING) {
            lblSortTitle.setText(getString(R.string.sortWithRouting));
        } else {
            lblSortTitle.setText("");
        }
    }

    private void showSendAlert(final long ccDarkhastFaktor, final int codeVazeiat, final int position) {
        customAlertDialog.showLogMessageAlert(TreasuryListActivity.this, false, "", getResources().getString(R.string.sendWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
            }

            @Override
            public void setOnApplyClick() {
                mPresenter.getDariaftPardakhtForSend(ccDarkhastFaktor, codeVazeiat, position);
            }
        });
    }

    /**
     * show dialog help
     */
    private void showAddItemAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TreasuryListActivity.this);
        alertView = getLayoutInflater().inflate(R.layout.alert_help_invoice, null);
        Button btnOk = alertView.findViewById(R.id.btnOk);
        TextView txtMandehDar = alertView.findViewById(R.id.txtMandehDar);
        TextView txtTasvieKamel = alertView.findViewById(R.id.txtTasvieKamel);
        TextView txtMarjoeeKamel = alertView.findViewById(R.id.txtMarjoeeKamel);
        TextView txtResid = alertView.findViewById(R.id.txtResid);
        TextView txtBestankari = alertView.findViewById(R.id.txtBestankari);


        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));

        btnOk.setTypeface(font);
        txtMandehDar.setTypeface(font);
        txtTasvieKamel.setTypeface(font);
        txtMarjoeeKamel.setTypeface(font);
        txtResid.setTypeface(font);
        txtBestankari.setTypeface(font);

        builder.setCancelable(true);
        builder.setView(alertView);
        builder.create();

        if (!(TreasuryListActivity.this).isFinishing()) {
            show = builder.show();
            try {
                if (show.getWindow() != null) {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(TreasuryListActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), "", "DarkhastKalaActivity", "showAddItemAlert", "");
            }


            btnOk.setOnClickListener(v -> show.dismiss());

        }

    }

    private void openMapList() {
        fabMenu.close(true);
        Intent intent = new Intent(TreasuryListActivity.this, TreasuryListMapActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }


    private void openInvoiceSettlement(int ccMoshtary, long ccDarkhastFaktor) {
        fabMenu.close(true);
        Log.d("treasury", "ccDarkhastFaktor : " + ccDarkhastFaktor);
        Intent intent = new Intent(TreasuryListActivity.this, InvoiceSettlementActivity.class);
        intent.putExtra("ccMoshtary", ccMoshtary);
        intent.putExtra("ccDarkhastFaktor", ccDarkhastFaktor);
        intent.putExtra("sourceActivity", "TreasuryListActivity");
        startActivityForResult(intent, OPEN_INVOICE_SETTLEMENT);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == OPEN_INVOICE_SETTLEMENT)
//        {
//            mPresenter.getTreasuryList(faktorRooz , Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
//        }
//    }

    private void openFaktorDetailActivity(long ccDarkhastFaktor) {
        Intent intent = new Intent(TreasuryListActivity.this, FaktorDetailsActivity.class);
        intent.putExtra("ccDarkhastFaktor", ccDarkhastFaktor);
        intent.putExtra("sourceActivity", "TreasuryListActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void showLoading() {
        Log.d("loading", "show Loading");
        if (alertDialogLoading == null) {
            alertDialogLoading = customLoadingDialog.showLoadingDialog(TreasuryListActivity.this);
        }
    }

    @Override
    public void closeLoading() {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
                alertDialogLoading = null;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }



    @Override
    public void openInvoiceSettlementActivity(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel) {
        Log.d("treasury", "ccDarkhastFaktor : " + darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor());
        Intent intent = new Intent(TreasuryListActivity.this, InvoiceSettlementActivity.class);
        intent.putExtra("ccMoshtary", darkhastFaktorMoshtaryForoshandeModel.getCcMoshtary());
        intent.putExtra("ccDarkhastFaktor", darkhastFaktorMoshtaryForoshandeModel.getCcDarkhastFaktor());
        intent.putExtra("sourceActivity", "TreasuryListActivity");
        startActivityForResult(intent, OPEN_INVOICE_SETTLEMENT);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "startMVPOps", "");
        }
    }


    private void initialize(TreasuryListMVP.RequiredViewOps view) {
        try {
            mPresenter = new TreasuryListPresenter(view);
            stateMaintainer.put(TreasuryListMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", ACTIVITY_NAME, "initialize", "");
        }
    }


    private void reinitialize(TreasuryListMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(TreasuryListMVP.PresenterOps.class.getSimpleName());
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


    /**
     * @param otherActivityClass second class start
     * @param keyValue1          key value bundle
     * @param keyValue2          key value bundle
     * @param bundle1            your object
     * @param bundle2            your object
     */
    private void startActivityBundle(Class<?> otherActivityClass, String keyValue1, String bundle1, String keyValue2, String bundle2) {
        Intent i = new Intent(TreasuryListActivity.this, otherActivityClass);
        i.putExtra(keyValue1, bundle1);
        i.putExtra(keyValue2, bundle2);
        startActivity(i);
    }


}
