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
import com.saphamrah.Adapter.RptMostarianKharidNakardeAdapter;
import com.saphamrah.BaseMVP.RptFaktorMandehDarMVP;
import com.saphamrah.BaseMVP.RptMoshtarianKharidNakardeMVP;
import com.saphamrah.MVP.Presenter.RptFaktorMandehDarPresenter;
import com.saphamrah.MVP.Presenter.RptMoshtarianKharidNakardePresenter;
import com.saphamrah.Model.RptMoshtaryKharidNakardeModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RptMoshtarianKharidNakardeActivity extends AppCompatActivity implements RptMoshtarianKharidNakardeMVP.RequiredViewOps {
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptMoshtarianKharidNakardeActivity.this);
    private RptMoshtarianKharidNakardeMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_moshtarian_kharid_nakarde);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        findViews();
        startMVPOps();
        mPresenter.getListMoshtarianKharidNakarde();
    }

    private void findViews() {
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);

        customAlertDialog = new CustomAlertDialog(RptMoshtarianKharidNakardeActivity.this);
        customLoadingDialog = new CustomLoadingDialog();



        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                alertDialog = customLoadingDialog.showLoadingDialog(RptMoshtarianKharidNakardeActivity.this);
                mPresenter.updateListMoshtarianKharidNakarde();
            }
        });
    }

    @Override
    public Context getAppContext() {
        return RptMoshtarianKharidNakardeActivity.this;
    }

    @Override
    public void onGetListMoshtarianKharidNakarde(ArrayList<RptMoshtaryKharidNakardeModel> rptMoshtaryKharidNakardeModels) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RptMostarianKharidNakardeAdapter adapter=new RptMostarianKharidNakardeAdapter(RptMoshtarianKharidNakardeActivity.this,rptMoshtaryKharidNakardeModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMoshtarianKharidNakardeActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptMoshtarianKharidNakardeActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void closeLoadingDialog() {
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
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(RptMoshtarianKharidNakardeActivity.this, getResources().getString(resId), messageType, duration);

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


    private void initialize(RptMoshtarianKharidNakardeMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptMoshtarianKharidNakardePresenter(view);
            stateMaintainer.put(RptMoshtarianKharidNakardeMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptMoshtarianKharidNakardeActivity\"", "initialize", "");
        }
    }


    private void reinitialize(RptMoshtarianKharidNakardeMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptMoshtarianKharidNakardeMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptMoshtarianKharidNakardeActivity", "reinitialize", "");
            }
        }
    }
}