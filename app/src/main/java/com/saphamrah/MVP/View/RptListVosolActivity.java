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
import com.saphamrah.Adapter.RptListVosolAdapter;
import com.saphamrah.BaseMVP.RptListVosolMVP;
import com.saphamrah.MVP.Presenter.RptListVosolPresenter;
import com.saphamrah.Model.RptListVosolModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptListVosolActivity extends AppCompatActivity implements RptListVosolMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptListVosolActivity.this);
    private RptListVosolMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_list_vosol);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        customAlertDialog = new CustomAlertDialog(RptListVosolActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();

        mPresenter.getListVosol();


        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                alertDialog = customLoadingDialog.showLoadingDialog(RptListVosolActivity.this);
                mPresenter.updateListVosol();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return RptListVosolActivity.this;
    }

    @Override
    public void onGetListVosol(ArrayList<RptListVosolModel> rptListVosolModels)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptListVosolAdapter adapter = new RptListVosolAdapter(RptListVosolActivity.this, rptListVosolModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptListVosolActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptListVosolActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void closeLoadingDialog()
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptListVosolActivity", "closeLoadingDialog", "");
            }
        }
    }


    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptListVosolActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptListVosolActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptListVosolMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptListVosolPresenter(view);
            stateMaintainer.put(RptListVosolMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptListVosolActivity", "initialize", "");
        }
    }


    private void reinitialize(RptListVosolMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptListVosolMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptListVosolActivity", "reinitialize", "");
            }
        }
    }


}
