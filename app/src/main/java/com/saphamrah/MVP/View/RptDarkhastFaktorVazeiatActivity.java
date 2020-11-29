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
import com.saphamrah.Adapter.RptDarkhastFaktorVazeiatAdapter;
import com.saphamrah.BaseMVP.RptDarkhastFaktorVazeiatMVP;
import com.saphamrah.MVP.Presenter.RptDarkhastFaktorVazeiatPresenter;
import com.saphamrah.Model.RptDarkhastFaktorVazeiatPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptDarkhastFaktorVazeiatActivity extends AppCompatActivity implements RptDarkhastFaktorVazeiatMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptDarkhastFaktorVazeiatActivity.this);
    private RptDarkhastFaktorVazeiatMVP.PresenterOps mPresenter;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_darkhast_faktor_vazeiat);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        customAlertDialog = new CustomAlertDialog(RptDarkhastFaktorVazeiatActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();

        mPresenter.getDarkhastFaktorVazeiatList();

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                alertDialogLoading = customLoadingDialog.showLoadingDialog(RptDarkhastFaktorVazeiatActivity.this);
                mPresenter.updateData();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return RptDarkhastFaktorVazeiatActivity.this;
    }

    @Override
    public void onGetDarkhastFaktorVazeiatList(ArrayList<RptDarkhastFaktorVazeiatPPCModel> darkhastFaktorVazeiatPPCModels)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptDarkhastFaktorVazeiatAdapter adapter = new RptDarkhastFaktorVazeiatAdapter(RptDarkhastFaktorVazeiatActivity.this, darkhastFaktorVazeiatPPCModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptDarkhastFaktorVazeiatActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptDarkhastFaktorVazeiatActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void closeLoadingAlert()
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptDarkhastFaktorVazeiatActivity", "closeLoadingAlert", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptDarkhastFaktorVazeiatActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptDarkhastFaktorVazeiatActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptDarkhastFaktorVazeiatMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptDarkhastFaktorVazeiatPresenter(view);
            stateMaintainer.put(RptDarkhastFaktorVazeiatMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptDarkhastFaktorVazeiatActivity", "initialize", "");
        }
    }


    private void reinitialize(RptDarkhastFaktorVazeiatMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptDarkhastFaktorVazeiatMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptDarkhastFaktorVazeiatActivity", "reinitialize", "");
            }
        }
    }



}
