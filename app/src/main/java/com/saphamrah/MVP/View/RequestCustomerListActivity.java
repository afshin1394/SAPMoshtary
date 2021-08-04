package com.saphamrah.MVP.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.Adapter.RequestCustomerListAdapter;
import com.saphamrah.BaseMVP.RequestCustomerListMVP;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.MVP.Presenter.RequestCustomerListPresenter;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;
import java.util.function.Consumer;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RequestCustomerListActivity extends AppCompatActivity implements RequestCustomerListMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RequestCustomerListActivity.this);
    RequestCustomerListMVP.PresenterOps mPresenter;

    private MaterialSearchView searchView;
    private RequestCustomerListAdapter adapter;
    private ArrayList<MoshtaryModel> moshtaryModels;
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels;
    private ArrayList<Integer> moshtaryNoeMorajeh;
    private ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels;

    private ArrayList<MoshtaryModel> moshtaryModelsSearch; // result of search
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModelsSearch; // result of search
    private boolean canUpdateCustomer;
    //private ArrayList<Integer> moshtaryNoeMorajehSearch;

    private AlertDialog alertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private CustomAlertDialog customAlertDialog;
    private int searchStatus; // not in search mode = 0 , search with name = 1 , search with code = 2
    private final int LOCATION_PERMISSION = 100;
    private  RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_customer_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(RequestCustomerListActivity.this);

        /*moshtaryModels = new ArrayList<>();
        moshtaryAddressModels = new ArrayList<>();
        moshtaryNoeMorajeh = new ArrayList<>();*/
        searchStatus = 0;
        moshtaryModelsSearch = new ArrayList<>();
        moshtaryAddressModelsSearch = new ArrayList<>();
        canUpdateCustomer = false;
        //moshtaryNoeMorajehSearch = new ArrayList<>();

        searchView = findViewById(R.id.searchView);
        FloatingActionButton fabSearchName = findViewById(R.id.fabSearchName);
        FloatingActionButton fabSearchCode = findViewById(R.id.fabSearchCode);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try
        {
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        startMVPOps();

        if (Build.VERSION.SDK_INT >= 23)
        {
            ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(RequestCustomerListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(RequestCustomerListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissions.size() > 0)
            {
                ActivityCompat.requestPermissions(RequestCustomerListActivity.this, permissions.toArray(new String[permissions.size()]), LOCATION_PERMISSION);
            }
            else
            {
                mPresenter.checkDateOfGetProgram();
                mPresenter.getCustomers();
            }
        }
        else
        {
            mPresenter.checkDateOfGetProgram();
            mPresenter.getCustomers();
        }


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String searchWord = query.trim();
                if (searchStatus == 1)
                {
                    mPresenter.searchCustomerName(searchWord, moshtaryModels, moshtaryAddressModels , moshtaryNoeMorajeh);
                }
                else if (searchStatus == 2)
                {
                    mPresenter.searchCustomerCode(searchWord, moshtaryModels, moshtaryAddressModels , moshtaryNoeMorajeh);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0)
                {
                    if (searchStatus == 1)
                    {
                        mPresenter.searchCustomerName(newText, moshtaryModels, moshtaryAddressModels , moshtaryNoeMorajeh);
                    }
                    else if (searchStatus == 2)
                    {
                        mPresenter.searchCustomerCode(newText, moshtaryModels, moshtaryAddressModels , moshtaryNoeMorajeh);
                    }
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

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSearchViewClosed() {
                searchStatus = 0;
                onGetCustomers(moshtaryModels , moshtaryAddressModels , moshtaryNoeMorajeh,moshtaryGharardadModels, canUpdateCustomer);
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = ((TextView)findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView)).getText().toString().trim();
                if (searchStatus == 1)
                {
                    mPresenter.searchCustomerName(searchWord, moshtaryModels, moshtaryAddressModels , moshtaryNoeMorajeh);
                }
                else if (searchStatus == 2)
                {
                    mPresenter.searchCustomerCode(searchWord, moshtaryModels, moshtaryAddressModels , moshtaryNoeMorajeh);
                }
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                searchView.closeSearch();
                searchStatus = 0;
                onGetCustomers(moshtaryModels , moshtaryAddressModels , moshtaryNoeMorajeh,moshtaryGharardadModels, canUpdateCustomer);
            }
        });


        fabSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch();
                searchView.setHint(getResources().getString(R.string.searchCustomerName));
                searchStatus = 1;
            }
        });


        fabSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch();
                searchView.setHint(getResources().getString(R.string.searchCustomerCode));
                searchStatus = 2;
            }
        });

        fabRefresh.setOnClickListener(v->{
            fabMenu.close(true);
            alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
            mPresenter.updateMoshtaryMorajehShodehRooz();
            recyclerView.setAdapter(null);

        });

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.checkFakeLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                mPresenter.checkDateOfGetProgram();
                mPresenter.getCustomers();
            }
            else
            {
                customAlertDialog.showMessageAlert(RequestCustomerListActivity.this, true, "", getResources().getString(R.string.errorAccessToLocation), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
            }
        }
    }

    private void visibleCloseSearchIcon()
    {
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
    }

    @Override
    public Context getAppContext() {
        return RequestCustomerListActivity.this;
    }

    @Override
    public void onGetDateOfGetProgram(String date)
    {
        TextView lblTitle = findViewById(R.id.lblActivityTitle);
        lblTitle.setText(getResources().getString(R.string.RequestCustomerListActivityTitle , date));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onGetCustomers(final ArrayList<MoshtaryModel> moshtaryModels , final ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , boolean canUpdateCustomer)
    {
        this.moshtaryModels = moshtaryModels;
        this.moshtaryAddressModels = moshtaryAddressModels;
        this.moshtaryNoeMorajeh = arrayListNoeMorajeh;
        this.canUpdateCustomer = canUpdateCustomer;
        this.moshtaryGharardadModels=moshtaryGharardadModels;
        recyclerView = findViewById(R.id.recyclerView);
//        moshtaryGharardadModels.forEach(new Consumer<MoshtaryGharardadModel>() {
//            @Override
//            public void accept(MoshtaryGharardadModel moshtaryGharardadModel) {
//                Log.i("forEach", "accept: "+moshtaryGharardadModel.toString());
//            }
//        });
        adapter = new RequestCustomerListAdapter(RequestCustomerListActivity.this, moshtaryModels , moshtaryAddressModels , arrayListNoeMorajeh,moshtaryGharardadModels , canUpdateCustomer , new RequestCustomerListAdapter.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(int operation, int position) {
                Log.i("RequestCustomerListAdapter", "checkDuplicateRequestForCustomer: "+moshtaryGharardadModels.get(position).getCcSazmanForosh());

                onListItemClickListener(operation , moshtaryModels.get(position) , moshtaryAddressModels.get(position),moshtaryGharardadModels.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RequestCustomerListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RequestCustomerListActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onGetSearch(ArrayList<MoshtaryModel> moshtaryModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh)
    {
        moshtaryModelsSearch = moshtaryModels;
        moshtaryAddressModelsSearch = moshtaryAddressModels;
        //moshtaryNoeMorajehSearch = arrayListNoeMorajeh;
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RequestCustomerListAdapter(RequestCustomerListActivity.this, moshtaryModels, moshtaryAddressModels, arrayListNoeMorajeh,moshtaryGharardadModels, canUpdateCustomer, new RequestCustomerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                onListItemClickListener(operation , moshtaryModelsSearch.get(position) , moshtaryAddressModelsSearch.get(position),moshtaryGharardadModels.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RequestCustomerListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RequestCustomerListActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showBarkhordAvalieActivity(int ccMoshtary)
    {
        //customLoadingDialog.closeLoadingDialog(RequestCustomerListActivity.this);
        Intent intent = new Intent(RequestCustomerListActivity.this , BarkhordAvalieForoshandehActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        RequestCustomerListActivity.this.finish();
    }

    @Override
    public void showMojoodiGiriActivity(int ccMoshtary,int ccSazmanForosh)
    {
        //customLoadingDialog.closeLoadingDialog(RequestCustomerListActivity.this);
        Intent intent = new Intent(RequestCustomerListActivity.this , MojodiGiriActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccSazmanForosh" , ccSazmanForosh);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        RequestCustomerListActivity.this.finish();
    }

    @Override
    public void showDarkhastKalaActivity(int ccMoshtary,int ccSazmanForosh)
    {
        //customLoadingDialog.closeLoadingDialog(RequestCustomerListActivity.this);
        Intent intent = new Intent(RequestCustomerListActivity.this , DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccSazmanForoshGharardad",ccSazmanForosh);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        RequestCustomerListActivity.this.finish();
    }



    @Override
    public void showAlertDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel)
    {
        customAlertDialog.showLogMessageAlert(RequestCustomerListActivity.this, false, "", getResources().getString(R.string.warningDuplicateRequestForCustomer), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse()
        {
            @Override
            public void setOnCancelClick()
            {

            }

            @Override
            public void setOnApplyClick()
            {
                alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
                mPresenter.checkSelectedCustomer(moshtaryModel.getCcMoshtary(),moshtaryGharardadModel.getCcSazmanForosh(),moshtaryGharardadModel.getCcMoshtaryGharardad());
                Log.i(TAG, "setOnApplyClick: ");
            }
        });
    }

    @Override
    public void closeLoading()
    {
        if (alertDialog != null)
        {
            try
            {
                alertDialog.dismiss();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    private void onListItemClickListener(int operation , MoshtaryModel moshtaryModel , MoshtaryAddressModel moshtaryAddressModel, MoshtaryGharardadModel moshtaryGharardadModel)
    {
        if (operation == Constants.REQUEST_CUSTOMER_SHOW_LOCATION())
        {
            Intent intent = new Intent(RequestCustomerListActivity.this , AddCustomerMapActivity.class);
            intent.putExtra("customerlat" , moshtaryAddressModel.getLatitude_y());
            intent.putExtra("customerlng" , moshtaryAddressModel.getLongitude_x());
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
        else if (operation == Constants.REQUEST_CUSTOMER_CHANGE_LOCATION())
        {
            Log.i("onListItemClic ", "2");
        }
        else if (operation == Constants.REQUEST_CUSTOMER_SHOW_CUSTOMER_INFO())
        {
            Intent intent = new Intent(RequestCustomerListActivity.this , CustomerInfoActivity.class);
            intent.putExtra(CustomerInfoActivity.CCMOSHTARY_KEY , moshtaryModel.getCcMoshtary());
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
        else if (operation == Constants.REQUEST_CUSTOMER_CHANGE_CUSTOMER_INFO())
        {
            Intent intent = new Intent(RequestCustomerListActivity.this , EditCustomerActivity.class);
            intent.putExtra("ccMoshtary" , moshtaryModel.getCcMoshtary());
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
        else if (operation == Constants.REQUEST_CUSTOMER_SELECT_CUSTOMER())
        {
            //customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
            alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
            mPresenter.checkDuplicateRequestForCustomer(moshtaryModel,moshtaryGharardadModel);
            //showBarkhordAvalieActivity(moshtaryModel.getCcMoshtary());
        }
        else if (operation == Constants.REQUEST_CUSTOMER_UPDATE_CREDIT())
        {
            mPresenter.checkUpdateEtebarMoshtary(moshtaryModel);
        }
    }


    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RequestCustomerListActivity.this , getResources().getString(resId) , messageType , duration);
    }

    @Override
    public void showErrorAlert(int resId, int messageType, boolean closeActivity)
    {
        customAlertDialog.showMessageAlert(RequestCustomerListActivity.this, closeActivity, getResources().getString(R.string.error), getResources().getString(resId), messageType, getResources().getString(R.string.apply));
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RequestCustomerListActivity", "startMVPOps", "");
        }
    }


    private void initialize(RequestCustomerListMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RequestCustomerListPresenter(view);
            stateMaintainer.put(RequestCustomerListMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RequestCustomerListActivity", "initialize", "");
        }
    }


    private void reinitialize(RequestCustomerListMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RequestCustomerListMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RequestCustomerListActivity", "reinitialize", "");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(false);

    }
}
