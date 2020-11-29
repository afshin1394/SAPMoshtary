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
import com.saphamrah.Adapter.RptFaktorMandehDar;
import com.saphamrah.BaseMVP.RptFaktorMandehDarMVP;
import com.saphamrah.MVP.Presenter.RptFaktorMandehDarPresenter;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptFaktorMandehDarActivity extends AppCompatActivity implements RptFaktorMandehDarMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptFaktorMandehDarActivity.this);
    private RptFaktorMandehDarMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_faktor_mandeh_dar);


        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        customAlertDialog = new CustomAlertDialog(RptFaktorMandehDarActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();

        mPresenter.getListMandehDar();

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                alertDialog = customLoadingDialog.showLoadingDialog(RptFaktorMandehDarActivity.this);
                mPresenter.updateListMandehDar();
            }
        });

    }



    @Override
    public Context getAppContext()
    {
        return RptFaktorMandehDarActivity.this;
    }

    @Override
    public void onGetListMandehDar(ArrayList<RptMandehdarModel> rptMandehdarModels)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptFaktorMandehDar adapter = new RptFaktorMandehDar(RptFaktorMandehDarActivity.this, rptMandehdarModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptFaktorMandehDarActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptFaktorMandehDarActivity.this , 0));
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorMandehDarActivity", "closeLoadingDialog", "");
            }
        }
    }


    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptFaktorMandehDarActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorMandehDarActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptFaktorMandehDarMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptFaktorMandehDarPresenter(view);
            stateMaintainer.put(RptFaktorMandehDarMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorMandehDarActivity", "initialize", "");
        }
    }


    private void reinitialize(RptFaktorMandehDarMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptFaktorMandehDarMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorMandehDarActivity", "reinitialize", "");
            }
        }
    }



}
