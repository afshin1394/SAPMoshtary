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
import com.saphamrah.Adapter.RptKharidKalaAdapter;
import com.saphamrah.BaseMVP.RptKharidKalaMVP;
import com.saphamrah.MVP.Presenter.RptKharidKalaPresenter;
import com.saphamrah.Model.RptKharidKalaModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptKharidKalaActivity extends AppCompatActivity implements RptKharidKalaMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptKharidKalaActivity.this);
    private RptKharidKalaMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_kharid_kala);


        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        customAlertDialog = new CustomAlertDialog(RptKharidKalaActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();

        mPresenter.getListKharidKala();


        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                alertDialog = customLoadingDialog.showLoadingDialog(RptKharidKalaActivity.this);
                mPresenter.updateListKharidKala();
            }
        });


    }



    @Override
    public Context getAppContext()
    {
        return RptKharidKalaActivity.this;
    }

    @Override
    public void onGetListKharidKala(ArrayList<RptKharidKalaModel> kharidKalaModels)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptKharidKalaAdapter adapter = new RptKharidKalaAdapter(RptKharidKalaActivity.this, kharidKalaModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptKharidKalaActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptKharidKalaActivity.this , 0));
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptKharidKalaActivity", "closeLoadingDialog", "");
            }
        }
    }


    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptKharidKalaActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptKharidKalaActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptKharidKalaMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptKharidKalaPresenter(view);
            stateMaintainer.put(RptKharidKalaMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptKharidKalaActivity", "initialize", "");
        }
    }


    private void reinitialize(RptKharidKalaMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptKharidKalaMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptKharidKalaActivity", "reinitialize", "");
            }
        }
    }



}
