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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptFaktorTozieNashodeAdapter;
import com.saphamrah.BaseMVP.RptFaktorTozieNashodeMVP;
import com.saphamrah.MVP.Presenter.RptFaktorTozieNashodePresenter;
import com.saphamrah.Model.RptFaktorTozieNashodehModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptFaktorTozieNashodeActivity extends AppCompatActivity implements RptFaktorTozieNashodeMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptFaktorTozieNashodeActivity.this);
    private RptFaktorTozieNashodeMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;

    private LinearLayout layTableFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_faktor_tozie_nashode);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        layTableFooter = findViewById(R.id.layTableFooter);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        customAlertDialog = new CustomAlertDialog(RptFaktorTozieNashodeActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();

        mPresenter.getListFaktorTozieNashode();


        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                alertDialog = customLoadingDialog.showLoadingDialog(RptFaktorTozieNashodeActivity.this);
                mPresenter.updateFaktorTozeieNashode();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return RptFaktorTozieNashodeActivity.this;
    }


    @Override
    public void setListAdapter(ArrayList<RptFaktorTozieNashodehModel> rptFaktorTozieNashodehModels , float sum)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptFaktorTozieNashodeAdapter adapter = new RptFaktorTozieNashodeAdapter(RptFaktorTozieNashodeActivity.this, rptFaktorTozieNashodehModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptFaktorTozieNashodeActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptFaktorTozieNashodeActivity.this , 0));
        recyclerView.setAdapter(adapter);
        TextView lblSumValue = findViewById(R.id.lblSumValue);
        layTableFooter.setVisibility(View.VISIBLE);
        lblSumValue.setText(formatter.format(sum));
    }

    @Override
    public void hideFooter()
    {
        layTableFooter.setVisibility(View.GONE);
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorTozieNashodeActivity", "closeLoadingDialog", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptFaktorTozieNashodeActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorTozieNashodeActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptFaktorTozieNashodeMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptFaktorTozieNashodePresenter(view);
            stateMaintainer.put(RptFaktorTozieNashodeMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorTozieNashodeActivity", "initialize", "");
        }
    }


    private void reinitialize(RptFaktorTozieNashodeMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptFaktorTozieNashodeMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorTozieNashodeActivity", "reinitialize", "");
            }
        }
    }



}
