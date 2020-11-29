package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.saphamrah.Adapter.CustomersListAdapter;
import com.saphamrah.Adapter.GetProgramItemsStatusAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.CustomersListMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.MVP.Presenter.CustomersListPresenter;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import it.sephiroth.android.library.viewrevealanimator.ViewRevealAnimator;
import me.anwarshahriar.calligrapher.Calligrapher;

public class CustomersListActivity extends AppCompatActivity implements CustomersListMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , CustomersListActivity.this);
    private CustomersListMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private CustomersListAdapter adapter;
    private ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels;
    private boolean searchMode;
    private RecyclerView recyclerView;
    private MaterialSearchView searchView;


    private List<Integer> getCustomerInfoItemsStatus;
    private ViewRevealAnimator viewRevealAnimator;
    private RecyclerView recyclerViewGetProgramItems;
    private ProgressBar progressBar;
    private TextView lblPassedItemCounter;
    private TextView lblProgressPercentage;
    private ShineButton imgGetProgramResultFailed;
    private ShineButton imgGetProgramResultSuccess;
    private TextView lblGetProgramResult;
    private Button btnGetProgramResultClose;
    private GetProgramItemsStatusAdapter alertAdapter;
    private AlertDialog show;
    private int getCustomerInfoItemCount;
    private int selectedccMasir;

    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        recyclerView = findViewById(R.id.recyclerView);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabSearch = findViewById(R.id.fabSearch);
        FloatingActionButton fabChooseRoute = findViewById(R.id.fabChooseRoute);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);
        searchView = (MaterialSearchView) findViewById(R.id.searchView);
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        removeToolbar();

        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(CustomersListActivity.this);
        allMoshtaryForoshandehModels = new ArrayList<>();
        searchMode = false;
        getCustomerInfoItemCount = getResources().getStringArray(R.array.getCustomerInfo).length;
        selectedccMasir = -1;

        startMVPOps();

        mPresenter.getAllCustomers();


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String searchWord = query.trim();
                mPresenter.searchCustomer(searchWord , allMoshtaryForoshandehModels);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0)
                {
                    mPresenter.searchCustomer(newText , allMoshtaryForoshandehModels);
                }
                else
                {
                    visibleCloseSearchIcon();
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                visibleCloseSearchIcon();
            }

            @Override
            public void onSearchViewClosed() {
                searchMode = false;
                mPresenter.getAllCustomers();
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = ((TextView)findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView)).getText().toString().trim();
                mPresenter.searchCustomer(searchWord , allMoshtaryForoshandehModels);
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.closeSearch();
                searchMode = false;
                mPresenter.getAllCustomers();
            }
        });

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch(true);
                searchMode = true;
            }
        });


        fabChooseRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.closeSearch();
                mPresenter.getAllMasirs();
            }
        });

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
                int ccForoshandeh = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh().getCcForoshandeh();
                mPresenter.getAllMoshtarian(TAG , ccForoshandeh);
                alertDialogLoading = customLoadingDialog.showLoadingDialog(CustomersListActivity.this);

            }
        });

    }

    private List<Integer> initializeItemsStatus()
    {
        return new ArrayList<>(Collections.nCopies(getCustomerInfoItemCount , 0));
    }

    @Override
    public Context getAppContext()
    {
        return CustomersListActivity.this;
    }

    @Override
    public void onGetAllMasirs(final ArrayList<MasirModel> masirModels)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        ArrayList<String> masirsNames = new ArrayList<>();
        for (MasirModel masir : masirModels)
        {
            masirsNames.add(masir.getNameMasir());
        }
        customSpinner.showSpinner(CustomersListActivity.this, masirsNames, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                selectedccMasir = masirModels.get(selectedIndex).getCcMasir();
                mPresenter.getCustomersByccMasir(selectedccMasir);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetAllCustomers(final ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels)
    {
        this.allMoshtaryForoshandehModels = new ArrayList<>();
        this.allMoshtaryForoshandehModels.addAll(allMoshtaryForoshandehModels);
        adapter = new CustomersListAdapter(CustomersListActivity.this, allMoshtaryForoshandehModels, new CustomersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getCustomerInfoItemsStatus = initializeItemsStatus();
                showItemStatusList();
                mPresenter.checkSelectedCustomerForGetInfo(position , allMoshtaryForoshandehModels.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomersListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(CustomersListActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onGetCustomersByccMasir(final ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels)
    {
        this.allMoshtaryForoshandehModels = new ArrayList<>();
        this.allMoshtaryForoshandehModels.addAll(allMoshtaryForoshandehModels);
        adapter = new CustomersListAdapter(CustomersListActivity.this, allMoshtaryForoshandehModels, new CustomersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getCustomerInfoItemsStatus = initializeItemsStatus();
                showItemStatusList();
                mPresenter.checkSelectedCustomerForGetInfo(position , allMoshtaryForoshandehModels.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomersListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(CustomersListActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetSearchResult(final ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels)
    {
        adapter = new CustomersListAdapter(CustomersListActivity.this, allMoshtaryForoshandehModels, new CustomersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getCustomerInfoItemsStatus = initializeItemsStatus();
                showItemStatusList();
                mPresenter.checkSelectedCustomerForGetInfo(position , allMoshtaryForoshandehModels.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomersListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(CustomersListActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSuccessfullyGetNewItemOfInfo(int itemIndex)
    {
        getCustomerInfoItemsStatus.set(itemIndex , 1);
        alertAdapter.notifyDataSetChanged();
        recyclerViewGetProgramItems.smoothScrollToPosition(itemIndex + 1);
        int percentage = (itemIndex*100)/getCustomerInfoItemCount;
        progressBar.setProgress(percentage);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , percentage));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(itemIndex + 1) , String.valueOf(getCustomerInfoItemCount)));
    }

    @Override
    public void onCompleteGetCustomerInfo()
    {
        recyclerViewGetProgramItems.smoothScrollToPosition(getCustomerInfoItemCount);
        progressBar.setProgress(100);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 100));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(getCustomerInfoItemCount) , String.valueOf(getCustomerInfoItemCount)));
        imgGetProgramResultFailed.setVisibility(View.GONE);
        imgGetProgramResultSuccess.setVisibility(View.VISIBLE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.successfullyDoneOps));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_success));
        imgGetProgramResultSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultSuccess.performClick();
                imgGetProgramResultSuccess.setPressed(true);
                imgGetProgramResultSuccess.invalidate();
            }
        } , 800);
    }

    @Override
    public void onFailedGetCustomerInfo(int itemIndex, String error)
    {
        getCustomerInfoItemsStatus.set(itemIndex , -1);
        alertAdapter.notifyDataSetChanged();
        imgGetProgramResultFailed.setVisibility(View.VISIBLE);
        imgGetProgramResultSuccess.setVisibility(View.GONE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.getProgramError , getItemName(itemIndex)));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_failed));
        imgGetProgramResultFailed.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultFailed.performClick();
                imgGetProgramResultFailed.setPressed(true);
                imgGetProgramResultFailed.invalidate();
            }
        } , 800);
    }

    private String getItemName(int itemIndex)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(getResources().getStringArray(R.array.getCustomerInfo)[itemIndex]);
            return jsonObject.getString("name");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomersListActivity", "getItemName", "");
            return "";
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(CustomersListActivity.this, getResources().getString(resId), messageType, duration);
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


    public void showItemStatusList()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CustomersListActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_recyclerlist_without_btn , null);
        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        viewRevealAnimator = myview.findViewById(R.id.animator);
        recyclerViewGetProgramItems = (RecyclerView)myview.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) myview.findViewById(R.id.progress);
        lblPassedItemCounter = (TextView) myview.findViewById(R.id.lblItemCounter);
        lblProgressPercentage = (TextView) myview.findViewById(R.id.lblProgressPercentage);
        imgGetProgramResultFailed = (ShineButton) myview.findViewById(R.id.shineBtnGetProgramResultFailed);
        imgGetProgramResultSuccess = (ShineButton) myview.findViewById(R.id.shineBtnGetProgramResultSuccess);
        lblGetProgramResult = (TextView) myview.findViewById(R.id.lblStatus);
        btnGetProgramResultClose = (Button) myview.findViewById(R.id.btnApply);

        List<String> itemsTitle = Arrays.asList(getResources().getStringArray(R.array.getCustomerInfo));
        alertAdapter = new GetProgramItemsStatusAdapter(CustomersListActivity.this , getCustomerInfoItemsStatus , itemsTitle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomersListActivity.this);
        recyclerViewGetProgramItems.setLayoutManager(mLayoutManager);
        recyclerViewGetProgramItems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGetProgramItems.addItemDecoration(new DividerItemDecoration(CustomersListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerViewGetProgramItems.setAdapter(alertAdapter);
        progressBar.setProgress(0);
        lblGetProgramResult.setTypeface(font);
        btnGetProgramResultClose.setTypeface(font);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 0));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , "0" , String.valueOf(getCustomerInfoItemCount)));

        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        btnGetProgramResultClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (show.getWindow() != null)
                {
                    try
                    {
                        show.dismiss();
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }
        });

        if (!(CustomersListActivity.this).isFinishing())
        {
            show = builder.show();
            imgGetProgramResultFailed.setFixDialog(show);
            imgGetProgramResultSuccess.setFixDialog(show);

            imgGetProgramResultFailed.setClickable(false);
            imgGetProgramResultFailed.setFocusable(false);
            imgGetProgramResultFailed.setFocusableInTouchMode(false);

            imgGetProgramResultSuccess.setClickable(false);
            imgGetProgramResultSuccess.setFocusable(false);
            imgGetProgramResultSuccess.setFocusableInTouchMode(false);

            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomersListActivity", "startMVPOps", "");
        }
    }


    private void initialize(CustomersListMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new CustomersListPresenter(view);
            stateMaintainer.put(CustomersListMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomersListActivity", "initialize", "");
        }
    }


    private void reinitialize(CustomersListMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(CustomersListMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomersListActivity", "reinitialize", "");
            }
        }
    }


    private void visibleCloseSearchIcon()
    {
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
    }

    private void removeToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
