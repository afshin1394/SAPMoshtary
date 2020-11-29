package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptCheckBargashtyAdapter;
import com.saphamrah.BaseMVP.RptCheckBargashtyMVP;
import com.saphamrah.MVP.Presenter.RptCheckBargashtyPresenter;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptCheckBargashtyActivity extends AppCompatActivity implements RptCheckBargashtyMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptCheckBargashtyActivity.this);
    private RptCheckBargashtyMVP.PresenterOps mPresenter;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_check_bargashty);


        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        customAlertDialog = new CustomAlertDialog(RptCheckBargashtyActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();

        mPresenter.getListBargashty();

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                alertDialogLoading = customLoadingDialog.showLoadingDialog(RptCheckBargashtyActivity.this);
                mPresenter.updateListBargashty();
            }
        });


    }



    @Override
    public Context getAppContext()
    {
        return RptCheckBargashtyActivity.this;
    }

    @Override
    public void onGetListBargashty(ArrayList<BargashtyModel> bargashtyModels)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptCheckBargashtyAdapter adapter = new RptCheckBargashtyAdapter(RptCheckBargashtyActivity.this, bargashtyModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptCheckBargashtyActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptCheckBargashtyActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void closeLoadingDialog()
    {
        if (alertDialogLoading != null)
        {
            try
            {
                alertDialogLoading.dismiss();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCheckBargashtyActivity", "closeLoadingAlert", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptCheckBargashtyActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCheckBargashtyActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptCheckBargashtyMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptCheckBargashtyPresenter(view);
            stateMaintainer.put(RptCheckBargashtyMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCheckBargashtyActivity", "initialize", "");
        }
    }


    private void reinitialize(RptCheckBargashtyMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptCheckBargashtyMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCheckBargashtyActivity", "reinitialize", "");
            }
        }
    }



}
