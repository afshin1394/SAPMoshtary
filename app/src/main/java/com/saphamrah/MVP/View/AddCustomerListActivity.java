package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.AddCustomerAdapter;
import com.saphamrah.BaseMVP.AddCustomerListMVP;
import com.saphamrah.MVP.Presenter.AddCustomerListPresenter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;


public class AddCustomerListActivity extends AppCompatActivity implements AddCustomerListMVP.RequiredViewOps
{
    private AddCustomerAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionMenu fabMenu;
    private ArrayList<AddCustomerInfoModel> moshtaryModels;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , AddCustomerListActivity.this);
    private AddCustomerListMVP.PresenterOps mPresenter;

    private final int ADD_NEW_CUSTOMER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgBack = findViewById(R.id.imgBack);
        recyclerView = findViewById(R.id.recyclerView);
        fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabAddCustomer = findViewById(R.id.fabAddCsutomer);
        FloatingActionButton fabShowMap = findViewById(R.id.fabShowMap);
        moshtaryModels = new ArrayList<>();
        customAlertDialog = new CustomAlertDialog(AddCustomerListActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();


        mPresenter.getConfig();


        fabAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                openAddCustomer();
            }
        });

        fabShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                //PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider(AddCustomerListActivity.this);
                openMap(0.0 , 0.0);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustomerListActivity.this.finish();
            }
        });

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.getNewCustomers();
    }

    @Override
    public Context getAppContext() {
        return AddCustomerListActivity.this;
    }

    @Override
    public void onErrorNotAccessForInsertNewCustomer()
    {
        customAlertDialog.showMessageAlert(AddCustomerListActivity.this, true, "", getResources().getString(R.string.cantInserNewCustomer), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void onGetNewCustomers(ArrayList<AddCustomerInfoModel> addCustomerInfoModels)
    {
        moshtaryModels = addCustomerInfoModels;
        adapter = new AddCustomerAdapter(AddCustomerListActivity.this, moshtaryModels, new AddCustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int action , int position) {
                onClickAdapter(action , position);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AddCustomerListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(AddCustomerListActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteCustomer(int ccMoshtary , int position)
    {
        moshtaryModels.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRemoveAddCustomerInfoShared()
    {
        Intent intent = new Intent(AddCustomerListActivity.this , AddCustomerActivity.class);
        startActivityForResult(intent , ADD_NEW_CUSTOMER);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void onOutOfPolygonError()
    {
        customAlertDialog.showMessageAlert(AddCustomerListActivity.this,false, getResources().getString(R.string.error), getResources().getString(R.string.errorOutOfPolygon), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void onErrorAccessToLocation()
    {
        customAlertDialog.showMessageAlert(AddCustomerListActivity.this,false, getResources().getString(R.string.error), getResources().getString(R.string.errorAccessToLocation), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void onSuccessSendToServer(int newccMoshtary, int oldccMoshtary)
    {
        customAlertDialog.showMessageAlert(AddCustomerListActivity.this, false, "", getResources().getString(R.string.successfullyDoneOps), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.apply));
        for (AddCustomerInfoModel model : moshtaryModels)
        {
            if (model.getCcMoshtary() == oldccMoshtary)
            {
                model.setCcMoshtary(newccMoshtary);
                model.setIsOld(1);
            }
        }
        adapter.notifyDataSetChanged();
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

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(AddCustomerListActivity.this, false, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(AddCustomerListActivity.this , getResources().getString(resId) , messageType , duration);
    }

    private void openAddCustomer()
    {
        mPresenter.checkAndRemoveAddCustomerInfoShared();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_CUSTOMER)
        {
            if (resultCode == RESULT_OK)
            {
                try
                {
                    if (data != null)
                    {
                        AddCustomerInfoModel addCustomerInfoModel = data.getParcelableExtra("newcustomer");
                        moshtaryModels.add(addCustomerInfoModel);
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
    }

    private void openMap(double customerLat , double customerLng)
    {
        Log.d("location" , "open map : " + customerLat + " , " + customerLng);
        Intent intent = new Intent(AddCustomerListActivity.this , AddCustomerMapActivity.class);
        intent.putExtra("customerlat" , customerLat);
        intent.putExtra("customerlng" , customerLng);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }


    private void onClickAdapter(int action , final int position)
    {
        if (action == Constants.DELETE_CUSTOMER())
        {
            customAlertDialog.showLogMessageAlert(AddCustomerListActivity.this, false, getResources().getString(R.string.warning), getResources().getString(R.string.warningDeleteCustomer),
                    Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
                        @Override
                        public void setOnCancelClick()
                        {

                        }

                        @Override
                        public void setOnApplyClick()
                        {
                            mPresenter.checkDeleteCustomer(moshtaryModels.get(position).getCcMoshtary() , position);
                        }
                    });
        }
        else if (action == Constants.SHOW_ADD_DOCS())
        {
            Intent intent = new Intent(AddCustomerListActivity.this , AddCustomerDocsActivity.class);
            intent.putExtra("ccmoshtary" , moshtaryModels.get(position).getCcMoshtary());
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
        else if (action == Constants.SHOW_CUSTOMER_LOCATION())
        {
            openMap(moshtaryModels.get(position).getMoshtaryAddressModels().get(0).getLatitude_y() , moshtaryModels.get(position).getMoshtaryAddressModels().get(0).getLongitude_x());
        }
        else if (action == Constants.SEND_CUSTOMER())
        {
            alertDialog = customLoadingDialog.showLoadingDialog(AddCustomerListActivity.this);
            mPresenter.checkSendToServer(moshtaryModels.get(position).getCcMoshtary());
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerListActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(AddCustomerListMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddCustomerListPresenter(view);
            stateMaintainer.put(AddCustomerListMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerListActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(AddCustomerListMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddCustomerListMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerListActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


}
