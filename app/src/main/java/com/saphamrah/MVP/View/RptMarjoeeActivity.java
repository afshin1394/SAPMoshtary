package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptMarjoeeAdapter;
import com.saphamrah.BaseMVP.RptMarjoeeMVP;
import com.saphamrah.MVP.Presenter.RptMarjoeePresenter;
import com.saphamrah.Model.RptMarjoeeKalaModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptMarjoeeActivity extends AppCompatActivity implements RptMarjoeeMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptMarjoeeActivity.this);
    private RptMarjoeeMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;
    private FloatingActionButton fabPrint;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_marjoee);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);
        fabPrint = findViewById(R.id.fabPrint);

        customAlertDialog = new CustomAlertDialog(RptMarjoeeActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();

        mPresenter.getMarjoeeList();

        fabRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                alertDialog = customLoadingDialog.showLoadingDialog(RptMarjoeeActivity.this);
                fabMenu.close(true);
                mPresenter.updateMarjoeeList();
            }
        });


        fabPrint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                Intent intent = new Intent(RptMarjoeeActivity.this , PrintMarjoeeActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return RptMarjoeeActivity.this;
    }


    @Override
    public void onGetMarjoeeList(List<RptMarjoeeKalaModel> marjoeeKalaModels, HashMap<RptMarjoeeKalaModel , List<RptMarjoeeKalaModel>> hashMapMarjoeeKala, long sumCost, int sumCount)
    {
        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        RptMarjoeeAdapter adapter = new RptMarjoeeAdapter(RptMarjoeeActivity.this, marjoeeKalaModels, hashMapMarjoeeKala);
        expandableListView.setAdapter(adapter);
        try
        {
            expandableListView.expandGroup(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ConstraintLayout layoutSum = findViewById(R.id.laySum);
        //fabPrint.setVisibility(View.VISIBLE);
        if (sumCost > 0 && sumCount > 0)
        {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            TextView lblSumCount = findViewById(R.id.lblSumCount);
            TextView lblSumCost = findViewById(R.id.lblSumCost);

            layoutSum.setVisibility(View.VISIBLE);
            lblSumCost.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumMablagh), formatter.format(sumCost)));
            lblSumCount.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumTedad), sumCount));
        }
        else
        {
            hideFooterAndPrint();
        }
    }

    @Override
    public void hideFooterAndPrint()
    {
        ConstraintLayout layoutSum = findViewById(R.id.laySum);
        layoutSum.setVisibility(View.GONE);
        fabPrint.setVisibility(View.GONE);
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptMarjoeeActivity", "closeLoadingDialog", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptMarjoeeActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptMarjoeeActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptMarjoeeMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = new RptMarjoeePresenter(view);
            stateMaintainer.put(RptMarjoeeMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptMarjoeeActivity", "initialize", "");
        }
    }


    private void reinitialize(RptMarjoeeMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptMarjoeeMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptMarjoeeActivity", "reinitialize", "");
            }
        }
    }


}
