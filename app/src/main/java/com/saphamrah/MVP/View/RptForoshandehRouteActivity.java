package com.saphamrah.MVP.View;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptForoshandehRouteAdapter;
import com.saphamrah.BaseMVP.RptForoshandehRouteMVP;
import com.saphamrah.MVP.Presenter.RptForoshandehRoutePresenter;
import com.saphamrah.Model.RptMasirModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptForoshandehRouteActivity extends AppCompatActivity implements RptForoshandehRouteMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptForoshandehRouteActivity.this);
    private RptForoshandehRouteMVP.PresenterOps mPresenter;

    private AlertDialog alertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private CustomAlertDialog customAlertDialog;
    private LinearLayout layFooter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_foroshandeh_route);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(RptForoshandehRouteActivity.this);

        layFooter = findViewById(R.id.laySumFooter);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabUpdate = findViewById(R.id.fabRefresh);

        startMVPOps();

        mPresenter.getRouteList();


        fabUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                alertDialog = customLoadingDialog.showLoadingDialog(RptForoshandehRouteActivity.this);
                mPresenter.updateRouteList();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return RptForoshandehRouteActivity.this;
    }

    @Override
    public void setAdapter(ArrayList<RptMasirModel> rptMasirModels, RptMasirModel rptMasirModelSum, RptMasirModel rptMasirModelDailyAverage)
    {
        layFooter.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptForoshandehRouteAdapter adapter = new RptForoshandehRouteAdapter(RptForoshandehRouteActivity.this, rptMasirModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptForoshandehRouteActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptForoshandehRouteActivity.this , 0));
        recyclerView.setAdapter(adapter);


        TextView lblSumKhorde = findViewById(R.id.lblSumKhorde);
        TextView lblSumOmdeh = findViewById(R.id.lblSumOmdeh);
        TextView lblSumTaavoni = findViewById(R.id.lblSumTaavoni);
        TextView lblSumZanjiree = findViewById(R.id.lblSumZanjiree);
        TextView lblSumNamayandeh = findViewById(R.id.lblSumNamayandeh);
        TextView lblSumSum = findViewById(R.id.lblSumSum);

        TextView lblAvgKhorde = findViewById(R.id.lblAverageKhorde);
        TextView lblAvgOmdeh = findViewById(R.id.lblAverageOmdeh);
        TextView lblAvgTaavoni = findViewById(R.id.lblAverageTaavoni);
        TextView lblAvgZanjiree = findViewById(R.id.lblAverageZanjiree);
        TextView lblAvgNamayandeh = findViewById(R.id.lblAverageNamayandeh);
        TextView lblAvgSum = findViewById(R.id.lblAverageSum);

        int sum = rptMasirModelSum.getKhordeh() + rptMasirModelSum.getOmdeh() + rptMasirModelSum.getTaavoni() + rptMasirModelSum.getZanjireh() + rptMasirModelSum.getNemaiandeh();
        lblSumKhorde.setText(String.valueOf(rptMasirModelSum.getKhordeh()));
        lblSumOmdeh.setText(String.valueOf(rptMasirModelSum.getOmdeh()));
        lblSumTaavoni.setText(String.valueOf(rptMasirModelSum.getTaavoni()));
        lblSumZanjiree.setText(String.valueOf(rptMasirModelSum.getZanjireh()));
        lblSumNamayandeh.setText(String.valueOf(rptMasirModelSum.getNemaiandeh()));
        lblSumSum.setText(String.valueOf(sum));


        sum = rptMasirModelDailyAverage.getKhordeh() + rptMasirModelDailyAverage.getOmdeh() + rptMasirModelDailyAverage.getTaavoni() + rptMasirModelDailyAverage.getZanjireh() + rptMasirModelDailyAverage.getNemaiandeh();
        lblAvgKhorde.setText(String.valueOf(rptMasirModelDailyAverage.getKhordeh()));
        lblAvgOmdeh.setText(String.valueOf(rptMasirModelDailyAverage.getOmdeh()));
        lblAvgTaavoni.setText(String.valueOf(rptMasirModelDailyAverage.getTaavoni()));
        lblAvgZanjiree.setText(String.valueOf(rptMasirModelDailyAverage.getZanjireh()));
        lblAvgNamayandeh.setText(String.valueOf(rptMasirModelDailyAverage.getNemaiandeh()));
        lblAvgSum.setText(String.valueOf(sum));
    }

    @Override
    public void emptyList()
    {
        layFooter.setVisibility(View.GONE);
        showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
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
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RptForoshandehRouteActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehRouteActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptForoshandehRouteMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptForoshandehRoutePresenter(view);
            stateMaintainer.put(RptForoshandehRouteMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehRouteActivity", "initialize", "");
        }
    }


    private void reinitialize(RptForoshandehRouteMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptForoshandehRouteMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehRouteActivity", "reinitialize", "");
            }
        }
    }
    
    
    

}
