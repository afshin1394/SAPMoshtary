package com.saphamrah.MVP.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.Adapter.RptJashnvarehFirstLevelAdapter;
import com.saphamrah.Adapter.RptJashnvarehSecondLevelAdapter;
import com.saphamrah.BaseMVP.RptJashnvarehForoshMVP;
import com.saphamrah.MVP.Presenter.RptJashnvarehForoshPresenter;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptJashnvarehForoshModel;
import com.saphamrah.R;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;



import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;


public class RptJashnvarehActivity extends AppCompatActivity implements RptJashnvarehForoshMVP.RequiredViewOps {


    private CustomAlertDialog customAlertDialog;
    private RptJashnvarehForoshMVP.PresenterOps mPresenter;
    private BottomSheetBehavior bottomSheetBehavior;
    private RptJashnvarehFirstLevelAdapter rptJashnvarehFirstLevelAdapter;
    private RptJashnvarehSecondLevelAdapter rptJashnvarehSecondLevelAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<RptJashnvarehForoshModel> allJashnvareh;
    private ArrayList<RptJashnvarehForoshModel> allCustomers;
    ArrayList<RptJashnvarehForoshModel> tempFirstLevelModel;
    ArrayList<RptJashnvarehForoshModel> tempSecondLevelModel;
    private ArrayList<RptJashnvarehForoshModel> rawData;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;
    private LinearLayout cardViewRootBtmSheet;
    private State state;

    private FloatingActionButton fabRefresh, fabSearchMoshtary, fabSearchJashnvareh;
    private FloatingActionMenu fabMenu;
    private MaterialSearchView searchView;
    private RecyclerView recyclerViewCustomers;
    private RecyclerView recyclerViewJashnvareh;
    private TextView lblTitle, lblNameForoshandeh, lblEmtiazForoshandeh;
    private int ccMoshtaryExtra;
    private Mode mode;

    public enum Mode{
        ForoshandehFromMenu,
        MamorPakhshFromMenu,
        ForoshandehFromVerify,
        MamorPakhshFromVerify
    }



    public enum State {
        Jashnvareh, Moshtary
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rpt_jashnvareh);
        customAlertDialog = new CustomAlertDialog(RptJashnvarehActivity.this);
        allCustomers = new ArrayList<>();
        allJashnvareh = new ArrayList<>();
        tempFirstLevelModel = new ArrayList<>();
        tempSecondLevelModel = new ArrayList<>();
        rawData = new ArrayList<>();
        cardViewRootBtmSheet = findViewById(R.id.linBottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(cardViewRootBtmSheet);
        customLoadingDialog = new CustomLoadingDialog();
        mPresenter = new RptJashnvarehForoshPresenter(RptJashnvarehActivity.this);
        ccMoshtaryExtra =  getIntent().getIntExtra("ccMoshtary",0);
        mPresenter.checkNoeMasouliat(ccMoshtaryExtra);


    }

    @SuppressLint("NotifyDataSetChanged")
    private void initViews(int ccMoshtary) {
        lblTitle = findViewById(R.id.lblActivityTitle);
        fabRefresh = findViewById(R.id.fabRefresh);
        fabSearchMoshtary = findViewById(R.id.fabSearchMoshtary);
        fabSearchJashnvareh = findViewById(R.id.fabSearchJashnvareh);
        searchView = findViewById(R.id.searchView);
        fabMenu = findViewById(R.id.fabMenu);
        recyclerViewCustomers = findViewById(R.id.recycler_view);
        recyclerViewJashnvareh = findViewById(R.id.recycler_view_jashnvareh);
        lblNameForoshandeh = findViewById(R.id.lbl_NameForoshandeh);
        lblEmtiazForoshandeh = findViewById(R.id.lbl_EmtiazForoshandeh);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        lblTitle.setText(R.string.RptJashnvarehUponCustomer);
        initFirstLevelAdapter();
        initSecondLevelAdapter();

        fabRefresh.setOnClickListener(view -> {
            mPresenter.getAll(ccMoshtary,mode);
            fabMenu.close(true);

        });

        fabSearchJashnvareh.setOnClickListener(view -> {
            state = State.Jashnvareh;
            fabMenu.close(true);
            searchView.showSearch();
            tempSecondLevelModel.clear();
            rptJashnvarehSecondLevelAdapter.notifyDataSetChanged();
            searchView.setHint(getResources().getString(R.string.searchSharhJashnvareh));
            mPresenter.getAllJashnvareh(ccMoshtary);
        });

        fabSearchMoshtary.setOnClickListener(view -> {
            state = State.Moshtary;
            fabMenu.close(true);
            searchView.showSearch();
            tempSecondLevelModel.clear();
            rptJashnvarehSecondLevelAdapter.notifyDataSetChanged();
            searchView.setHint(getResources().getString(R.string.searchCustomerName));
            mPresenter.getAllCustomers(ccMoshtary);
        });


    }



    private void initFirstLevelAdapter() {


        rptJashnvarehFirstLevelAdapter = new RptJashnvarehFirstLevelAdapter(RptJashnvarehActivity.this, tempFirstLevelModel, (state, rptJashnvarehForoshModel,position) -> {
            switch (state) {
                case Jashnvareh:
                    mPresenter.getSumDetails(state,ccMoshtaryExtra, rptJashnvarehForoshModel.getCcJashnvarehForosh(),position);
                    break;
                case Moshtary:
                    mPresenter.getSumDetails(state,ccMoshtaryExtra, rptJashnvarehForoshModel.getCcMoshtary(),position);
                    break;
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
        linearLayoutManager = new LinearLayoutManager(RptJashnvarehActivity.this);
        recyclerViewCustomers.setLayoutManager(linearLayoutManager);
        recyclerViewCustomers.addItemDecoration(new DividerItemDecoration(RptJashnvarehActivity.this, DividerItemDecoration.VERTICAL));
        recyclerViewCustomers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCustomers.setAdapter(rptJashnvarehFirstLevelAdapter);
    }


    private void initSecondLevelAdapter() {
        rptJashnvarehSecondLevelAdapter = new RptJashnvarehSecondLevelAdapter(RptJashnvarehActivity.this, this.tempSecondLevelModel, (rptJashnvarehForoshModel, position) -> {
            mPresenter.getDetails(state,rptJashnvarehForoshModel,ccMoshtaryExtra,position);
            Log.i("initSecondLevelAdapter", " ccJaashnvareForosh"+rptJashnvarehForoshModel.getCcJashnvarehForosh() + " ccMoshtary :"+rptJashnvarehForoshModel.getCcMoshtary() +" position: "+position);

        });
        linearLayoutManager = new LinearLayoutManager(RptJashnvarehActivity.this);
        recyclerViewJashnvareh.setLayoutManager(linearLayoutManager);
        recyclerViewJashnvareh.setItemAnimator(new DefaultItemAnimator());
        recyclerViewJashnvareh.setAdapter(rptJashnvarehSecondLevelAdapter);


    }

    @Override
    public Context getAppContext() {
        return RptJashnvarehActivity.this;
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(RptJashnvarehActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void onGetAllJashnvareh(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        initialSearchView();
        allJashnvareh = rptJashnvarehForoshModels;
        if (state.equals(State.Jashnvareh)) {
            this.tempFirstLevelModel.clear();
            this.tempFirstLevelModel.addAll(rptJashnvarehForoshModels);
            this.rptJashnvarehFirstLevelAdapter.setViewType(State.Jashnvareh);
            bottomSheetBehavior.setState(rptJashnvarehForoshModels.size()>0?BottomSheetBehavior.STATE_EXPANDED:BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onGetCustomers(ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels) {
        initialSearchView();
        allCustomers = jashnavareForoshModels;
        if (state.equals(State.Moshtary)) {
            this.tempFirstLevelModel.clear();
            this.tempFirstLevelModel.addAll(jashnavareForoshModels);
            this.rptJashnvarehFirstLevelAdapter.setViewType(State.Moshtary);
            bottomSheetBehavior.setState(jashnavareForoshModels.size()>0?BottomSheetBehavior.STATE_EXPANDED:BottomSheetBehavior.STATE_COLLAPSED);
        }
    }


    @Override
    public void onGetAll(ArrayList<RptJashnvarehForoshModel> jashnavareForoshModels) {
        rawData.clear();
        rawData.addAll(jashnavareForoshModels);
        mPresenter.getSumForoshandehScore(ccMoshtaryExtra);
    }

    @Override
    public void showLoading() {
        alertDialogLoading = customLoadingDialog.showLoadingDialog(RptJashnvarehActivity.this);
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
    public void onGetForoshandehScore(RptJashnvarehForoshModel rptJashnvarehForoshModel, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {

        if (mode.equals(Mode.ForoshandehFromMenu) || mode.equals(Mode.ForoshandehFromVerify))
        lblNameForoshandeh.setText(String.format("%1$s: %2$s", getResources().getString(R.string.foroshandeh), foroshandehMamorPakhshModel.getFullName()));
        else
        lblNameForoshandeh.setText(String.format("%1$s: %2$s", getResources().getString(R.string.mamorPakhsh), foroshandehMamorPakhshModel.getFullName()));



        if (mode.equals(Mode.ForoshandehFromVerify) || mode.equals(Mode.MamorPakhshFromVerify))
        lblEmtiazForoshandeh.setText(String.format("%1$s: %2$s", getResources().getString(R.string.sumEmtiazMoshtary), rptJashnvarehForoshModel.getEmtiazMoshtary()));
        else
        lblEmtiazForoshandeh.setText(String.format("%1$s: %2$s", getResources().getString(R.string.sumEmtiazMoshtarian), rptJashnvarehForoshModel.getEmtiazMoshtary()));

    }



    @Override
    public void onGetSumDetails(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels,int position) {
        this.tempSecondLevelModel.clear();
        this.tempSecondLevelModel.addAll(jashnvarehForoshModels);
        this.rptJashnvarehSecondLevelAdapter.setViewType(state,position);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }

    @Override
    public void onGetSearch(ArrayList<RptJashnvarehForoshModel> rptJashnvarehForoshModels) {
        this.tempFirstLevelModel.clear();
        this.tempFirstLevelModel.addAll(rptJashnvarehForoshModels);
        this.rptJashnvarehFirstLevelAdapter.setViewType(state);
    }

    @Override
    public void onGetDetails(ArrayList<RptJashnvarehForoshModel> jashnvarehForoshModels, int position) {
        rptJashnvarehSecondLevelAdapter.initChild(jashnvarehForoshModels,position);
    }

    @Override
    public void onIsForoshandehFromMenu() {
        initViews(ccMoshtaryExtra);
        state = State.Moshtary;
        mPresenter.getSumForoshandehScore(ccMoshtaryExtra);
        mode = Mode.ForoshandehFromMenu;

    }

    @Override
    public void onIsMamorPakhshFromMenu() {
        initViews(ccMoshtaryExtra);
        state = State.Moshtary;
        mPresenter.getSumForoshandehScore(ccMoshtaryExtra);
        mode = Mode.MamorPakhshFromMenu;
    }

    @Override
    public void onIsFroshandehFromVerifyRequest(int ccMoshtary) {
        initViews(ccMoshtaryExtra);
        state = State.Moshtary;
        mPresenter.getSumForoshandehScore(ccMoshtaryExtra);
        mode = Mode.ForoshandehFromVerify;
    }

    @Override
    public void onIsMamorpakhshVerifyRequest(int ccMoshtaryExtra) {
        initViews(ccMoshtaryExtra);
        state = State.Moshtary;
        mPresenter.getSumForoshandehScore(ccMoshtaryExtra);
        mode = Mode.MamorPakhshFromVerify;
    }

    private void initialSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    String searchWord = query.trim();
                    switch (state) {
                        case Moshtary:
                            mPresenter.search(state, searchWord, allCustomers);
                            break;

                        case Jashnvareh:
                            mPresenter.search(state, searchWord, allJashnvareh);
                            break;
                    }

                } else {
                    switch (state) {
                        case Moshtary:
                            onGetCustomers(allCustomers);
                            break;

                        case Jashnvareh:
                            onGetAllJashnvareh(allJashnvareh);
                            break;
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0)
                    switch (state) {
                        case Moshtary:
                            mPresenter.search(state, newText, allCustomers);
                            break;

                        case Jashnvareh:
                            mPresenter.search(state, newText, allJashnvareh);
                            break;
                    }
                else {
                    switch (state) {
                        case Moshtary:
                            onGetCustomers(allCustomers);
                            break;

                        case Jashnvareh:
                            onGetAllJashnvareh(allJashnvareh);
                            break;
                    }
                    visibleCloseSearchIcon();
                }

                return false;
            }
        });


        findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(v -> {
            String searchWord = ((TextView) findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView)).getText().toString().trim();

            switch (state) {
                case Moshtary:
                    mPresenter.search(state, searchWord, allCustomers);
                    break;

                case Jashnvareh:
                    mPresenter.search(state, searchWord, allJashnvareh);
                    break;
            }
        });
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                searchView.closeSearch();
                switch (state) {
                    case Moshtary:
                        onGetCustomers(allCustomers);
                        break;

                    case Jashnvareh:
                        onGetAllJashnvareh(allJashnvareh);
                        break;
                }
            }

        });
        searchView.post(() -> {
            searchView.showSearch();
            searchView.setHint(getResources().getString(R.string.searchCustomerName));
        });



    }

    private void visibleCloseSearchIcon() {
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy(false);
        super.onDestroy();

    }
}