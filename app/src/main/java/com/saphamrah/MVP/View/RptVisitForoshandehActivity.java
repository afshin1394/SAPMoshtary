package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptVisitForoshandeAdapter;
import com.saphamrah.BaseMVP.RptForoshandehVisitMVP;
import com.saphamrah.MVP.Presenter.RptForoshandehVisitPresenter;
import com.saphamrah.Model.RptVisitForoshandehMoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

//public class RptVisitForoshandehActivity extends AppCompatActivity implements RptForoshandehVisitMVP.RequiredViewOps {
public class RptVisitForoshandehActivity extends AppCompatActivity implements RptForoshandehVisitMVP.RequiredViewOps {

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, RptVisitForoshandehActivity.this);
    private RptForoshandehVisitMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;

    private TextView kharidtt;
    private TextView adamsefareshtt;
    private TextView adamDarkhasttt;
    private TextView adamMorajehtt;

    private TextView lbladammorajehee;
    private TextView lbladamdarkhastee;
    private TextView lbladamsefareshee;
    private TextView lblkharidee;


    private int kharidKardeh = 0;
    private int adamSefaresh = 0;
    private int adamDarkhast = 0;
    private int adamMoraje = 0;
    private int counter = 0;

    private int showSingle = 0;
    private int showSingleSec = 0;
    private boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_visit_foroshandeh);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);
        FloatingActionButton fabShowAll = findViewById(R.id.fabShowAll);
        FloatingActionButton fabShowTable = findViewById(R.id.fabShowTable);

        customAlertDialog = new CustomAlertDialog(RptVisitForoshandehActivity.this);
        kharidtt = findViewById(R.id.lblkharid);
        adamsefareshtt = findViewById(R.id.lbladamsefaresh);
        adamDarkhasttt = findViewById(R.id.lbladamdarkhast);
        adamMorajehtt = findViewById(R.id.lbladammorajeh);

        lbladammorajehee = findViewById(R.id.lbladammorajehee);
        lbladamdarkhastee = findViewById(R.id.lbladamdarkhastee);
        lbladamsefareshee = findViewById(R.id.lbladamsefareshee);
        lblkharidee = findViewById(R.id.lblkharidee);

        lblkharidee.setOnClickListener(view -> {
            refreshFunc(1, 0, false);
        });

        lbladammorajehee.setOnClickListener(view -> {
            refreshFunc(0, 0, false);
        });

        lbladamdarkhastee.setOnClickListener(view -> {
            refreshFunc(-1, 1, false);
        });

        lbladamsefareshee.setOnClickListener(view -> {
            refreshFunc(-1, 0, false);
        });

        startMVPOps();

        mPresenter.getVisitList();


        fabRefresh.setOnClickListener(v -> {
            fabMenu.close(true);

            kharidKardeh = 0;
            adamSefaresh = 0;
            adamDarkhast = 0;
            adamMoraje = 0;
            counter = 0;
            flag = true;
            mPresenter.updateReport();
        });

        fabShowAll.setOnClickListener(view -> {
            fabMenu.close(true);

            refreshFunc(0,0,true);
        });

        fabShowTable.setOnClickListener(view -> {
            startActivity(new Intent(this, RptForoshandehVisitActivity.class));
            this.finish();
        });

    }


    @Override
    public Context getAppContext() {
        return RptVisitForoshandehActivity.this;
    }

    @Override
    public void setAdapter(ArrayList<RptVisitForoshandehMoshtaryModel> rptVisitForoshandehMoshtaryModels, RptVisitForoshandehMoshtaryModel rptVisitForoshandehMoshtarySum) {

        RecyclerView recyclerView = findViewById(R.id.recyclerView_visit_forosh);

        ArrayList<RptVisitForoshandehMoshtaryModel> tempList = new ArrayList<>();

        if (flag)
            tempList = rptVisitForoshandehMoshtaryModels;
        else
            for (RptVisitForoshandehMoshtaryModel model : rptVisitForoshandehMoshtaryModels) {
                if (model.getIsMorajeh() == showSingle)
                    if (!(showSingle == -1))
                        tempList.add(model);
                    else if (model.getCodeNoeAdamDarkhast() == showSingleSec)
                        tempList.add(model);
            }


        RptVisitForoshandeAdapter adapter = new RptVisitForoshandeAdapter(RptVisitForoshandehActivity.this, tempList, 1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RptVisitForoshandehActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptVisitForoshandehActivity.this, 0));
        recyclerView.setAdapter(adapter);

        for (RptVisitForoshandehMoshtaryModel model : rptVisitForoshandehMoshtaryModels) {
            counter++;

            if (model.getIsMorajeh() == 1) {
                kharidKardeh++;
            }
            //the perfect way would be getting reasons from server
            else if (model.getIsMorajeh() == -1 && model.getCodeNoeAdamDarkhast() == 1) {
                adamDarkhast++;
            } else if (model.getIsMorajeh() == -1 && model.getCodeNoeAdamDarkhast() == 0) {
                adamSefaresh++;
            }

            if (model.getIsMorajeh() == 0) {
                adamMoraje++;
            }

            if (rptVisitForoshandehMoshtaryModels.size() == counter) {
                kharidtt.setText(kharidKardeh + "");
                adamsefareshtt.setText(adamSefaresh + "");
                adamDarkhasttt.setText(adamDarkhast + "");
                adamMorajehtt.setText(adamMoraje + "");

            }

        }
    }

    @Override
    public void emptyList() {
        showToast(R.string.notFoundData, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(RptVisitForoshandehActivity.this, getResources().getString(resId), messageType, duration);
    }


    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehVisitActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptForoshandehVisitMVP.RequiredViewOps view) {
        try {
            mPresenter = new RptForoshandehVisitPresenter(view);
            stateMaintainer.put(RptForoshandehVisitMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehVisitActivity", "initialize", "");
        }
    }


    private void reinitialize(RptForoshandehVisitMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptForoshandehVisitMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptForoshandehVisitActivity", "reinitialize", "");
            }
        }
    }

    private void refreshFunc(int n, int m, boolean flag) {
        showSingle = n;
        showSingleSec = m;
        this.flag = flag;

        mPresenter.updateOffline();

        kharidKardeh = 0;
        adamSefaresh = 0;
        adamDarkhast = 0;
        adamMoraje = 0;
        counter = 0;
    }
}
