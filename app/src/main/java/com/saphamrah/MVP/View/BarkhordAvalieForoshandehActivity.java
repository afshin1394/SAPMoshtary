package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.BarkhordAvalieAdapter;
import com.saphamrah.BaseMVP.BarkhordAvalieMVP;
import com.saphamrah.CustomView.BottomBar;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Model.BarkhordAvalieModel;
import com.saphamrah.MVP.Presenter.BarkhordAvaliePresenter;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.PubFunc.ConcurrencyUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class BarkhordAvalieForoshandehActivity extends AppCompatActivity implements BarkhordAvalieMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , BarkhordAvalieForoshandehActivity.this);
    private BarkhordAvalieMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private int ccMoshtary;

    private ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhordAvalieModels;
    private BarkhordAvalieAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barkhord_avalie_foroshandeh);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        ImageView imgBack = findViewById(R.id.imgBack);

        customAlertDialog = new CustomAlertDialog(BarkhordAvalieForoshandehActivity.this);
        initializeAdapter();

        new BottomBar(BarkhordAvalieForoshandehActivity.this, 0, new BottomBar.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mPresenter.checkBottomBarClick(position, ccMoshtary);
            }
        });

        startMVPOps();

        Intent getIntent = getIntent();
        ccMoshtary = getIntent.getIntExtra("ccMoshtary" , -1);

        mPresenter.getBarkhords(ccMoshtary);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                showAddBakhordAvalieAlert();
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BarkhordAvalieForoshandehActivity.this.finish();
            }
        });

    }

    private void initializeAdapter() {
        barkhordAvalieModels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new BarkhordAvalieAdapter(BarkhordAvalieForoshandehActivity.this, barkhordAvalieModels, new BarkhordAvalieAdapter.OnItemClickListener() {
            @Override
            public void deleteItem(BarkhordForoshandehBaMoshtaryModel barkhord, int position) {
                mPresenter.checkRemoveBarkhord(barkhord);
            }

            @Override
            public void addToFavorite(BarkhordForoshandehBaMoshtaryModel barkhords, int position,boolean operator) {
                Log.i("opertaorre", "addToFavorite: "+operator);
                mPresenter.checkFavoriteOperation(barkhords,position,operator);

            }
        });



        linearLayoutManager = new LinearLayoutManager(BarkhordAvalieForoshandehActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(BarkhordAvalieForoshandehActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public Context getAppContext()
    {
        return BarkhordAvalieForoshandehActivity.this;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onGetBarkhords(final ArrayList<BarkhordForoshandehBaMoshtaryModel> barkhords)
    {
        barkhordAvalieModels = barkhords;
        adapter.setBarkhords(barkhordAvalieModels);

    }

    @Override
    public void openMojodiGiriActivity()
    {
        Intent intent = new Intent(BarkhordAvalieForoshandehActivity.this , MojodiGiriActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        BarkhordAvalieForoshandehActivity.this.finish();
    }

    @Override
    public void openDarkhastKalaActivity()
    {
        Intent intent = new Intent(BarkhordAvalieForoshandehActivity.this , DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        BarkhordAvalieForoshandehActivity.this.finish();
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(BarkhordAvalieForoshandehActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void onGetNewBarkhord(BarkhordForoshandehBaMoshtaryModel model) {

    }

    @Override
    public void onSuccessAddToFavorite(int position , boolean operator) {
//        PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(new PubFunc.ConcurrencyEvents() {
//            @Override
//            public void uiThreadIsReady() {
                adapter.updateBarkhordFavorite(position,operator);
//            }
//        });

    }


    private void showAddBakhordAvalieAlert()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BarkhordAvalieForoshandehActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_add_barkhord_avalie , null);
        final CustomTextInputLayout txtinputLayout = myview.findViewById(R.id.txtinputDesc);
        final EditText editTextDesc = (EditText)myview.findViewById(R.id.txtDesc);
        Button btnOK = (Button)myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputLayout.setTypeface(font);
        editTextDesc.setTypeface(font);
        btnOK.setTypeface(font);
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();

        if (!(BarkhordAvalieForoshandehActivity.this).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "BarkhordAvalieForoshandehActivity" , "showAddBakhordAvalieAlert", "");
            }
            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    txtinputLayout.setError(null);
                    String desc = editTextDesc.getText().toString().trim();
                    if (desc.equals("") || desc.replace(" " , "").length() < 6)
                    {
                        txtinputLayout.setError(getResources().getString(R.string.errorInputDescription));
                    }
                    else
                    {
                        show.dismiss();

                        mPresenter.checkNewBarkhord(ccMoshtary , editTextDesc.getText().toString().trim());
                    }
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.updateRecentBarkhords();
        barkhordAvalieModels = null;
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", BarkhordAvalieForoshandehActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(BarkhordAvalieMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new BarkhordAvaliePresenter(view);
            stateMaintainer.put(BarkhordAvalieMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", BarkhordAvalieForoshandehActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(BarkhordAvalieMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(BarkhordAvalieMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", BarkhordAvalieForoshandehActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }




}
