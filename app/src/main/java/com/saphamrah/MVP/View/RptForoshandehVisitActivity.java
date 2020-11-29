package com.saphamrah.MVP.View;

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
import com.saphamrah.Adapter.RptForoshandehVisitAdapter;
import com.saphamrah.BaseMVP.RptForoshandehVisitMVP;
import com.saphamrah.MVP.Presenter.RptForoshandehVisitPresenter;
import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptForoshandehVisitActivity extends AppCompatActivity implements RptForoshandehVisitMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptForoshandehVisitActivity.this);
    private RptForoshandehVisitMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private LinearLayout layFooter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_foroshandeh_visit);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        customAlertDialog = new CustomAlertDialog(RptForoshandehVisitActivity.this);
        layFooter = findViewById(R.id.layTableFooter);

        startMVPOps();

        mPresenter.getVisitList();


        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                mPresenter.updateReport();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return RptForoshandehVisitActivity.this;
    }

    @Override
    public void setAdapter(ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels, RptVisitForoshandehMoshtaryModel rptVisitForoshandehMoshtarySum)
    {
        layFooter.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptForoshandehVisitAdapter adapter = new RptForoshandehVisitAdapter(RptForoshandehVisitActivity.this , rptVisitForoshandehMoshtaryModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RptForoshandehVisitActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptForoshandehVisitActivity.this , 0));
        recyclerView.setAdapter(adapter);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        TextView lblSumRialKharid = findViewById(R.id.lblSumRialKharid);
        TextView lblSumTedadAghlam = findViewById(R.id.lblSumTedadAghlam);
        lblSumRialKharid.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumRialKharid), formatter.format(rptVisitForoshandehMoshtarySum.getRialKharid())));
        lblSumTedadAghlam.setText(String.format("%1$s : %2$s", getResources().getString(R.string.sumTedad), rptVisitForoshandehMoshtarySum.getTedad_AghlamFaktor()));
    }

    @Override
    public void emptyList()
    {
        layFooter.setVisibility(View.GONE);
        showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptForoshandehVisitActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehVisitActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptForoshandehVisitMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = new RptForoshandehVisitPresenter(view);
            stateMaintainer.put(RptForoshandehVisitMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehVisitActivity", "initialize", "");
        }
    }


    private void reinitialize(RptForoshandehVisitMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptForoshandehVisitMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehVisitActivity", "reinitialize", "");
            }
        }
    }






}
