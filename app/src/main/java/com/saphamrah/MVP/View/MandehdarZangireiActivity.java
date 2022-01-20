package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.Adapter.MandehdarZangireiAdapter;
import com.saphamrah.BaseMVP.MandehdarZangireiMVP;
import com.saphamrah.MVP.Model.MandehdarZangireiModel;
import com.saphamrah.MVP.Presenter.MandehdarZangireiPresenter;
import com.saphamrah.Model.RptMandehdarModel;
import com.saphamrah.PubFunc.LanguageUtil;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;

public class MandehdarZangireiActivity extends AppCompatActivity implements MandehdarZangireiMVP.RequiredViewOps {


    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.fabRefresh)
    FloatingActionButton fabRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.txtSearchCustomer)
    EditText txtSearchCustomer;

    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;
    private MandehdarZangireiMVP.PresenterOps mPresenter;
    private MandehdarZangireiAdapter adapter;
    private ArrayList<RptMandehdarModel> mandehdarModels = new ArrayList<>();
    private ArrayList<RptMandehdarModel> mandehdarModelsSeaerch = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandehdar_zangirei);
        ButterKnife.bind(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        customAlertDialog = new CustomAlertDialog(MandehdarZangireiActivity.this);
        customLoadingDialog = new CustomLoadingDialog();
        mPresenter = new MandehdarZangireiPresenter(this);
        recyclerSetup();
        mPresenter.getListMandehDar();


        txtSearchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    String searchWord = new LanguageUtil().convertFaNumberToEN(s.toString());
                    mandehdarModelsSeaerch.clear();
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < mandehdarModels.size(); i++) {
                        String CustomerName = new LanguageUtil().convertFaNumberToEN(mandehdarModels.get(i).getNameMoshtary() + mandehdarModels.get(i).getCodeMoshtary());
                        if (CustomerName.contains(searchWord)) {
                            mandehdarModelsSeaerch.add(mandehdarModels.get(i));
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else //if (s.length() == 0)
                {
                    mandehdarModelsSeaerch.clear();
                    mandehdarModelsSeaerch.addAll(mandehdarModels);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    @OnClick(R.id.fabRefresh)
    public void fabRefresh(){
        fabMenu.close(true);
        alertDialog = customLoadingDialog.showLoadingDialog(MandehdarZangireiActivity.this);
        mPresenter.updateListMandehDar();
        txtSearchCustomer.setText("");
    }
    @OnClick(R.id.imgBack)
    public void imgBack(){
        finish();
    }
    @OnClick(R.id.fabSearch)
    public void fabSearch(){
        fabMenu.close(true);
    }

    @Override
    public Context getAppContext() {
        return MandehdarZangireiActivity.this;
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MandehdarZangireiActivity", "closeLoadingDialog", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(MandehdarZangireiActivity.this, getResources().getString(resId), messageType, duration);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onGetListMandehDar(ArrayList<RptMandehdarModel> mandehdarModels) {
        this.mandehdarModels.clear();
        this.mandehdarModels.addAll(mandehdarModels);
        mandehdarModelsSeaerch.clear();
        mandehdarModelsSeaerch.addAll(mandehdarModels);
        adapter.notifyDataSetChanged();
    }

    private void recyclerSetup(){
        adapter = new MandehdarZangireiAdapter(this, mandehdarModelsSeaerch, model -> {
            alertDialog = customLoadingDialog.showLoadingDialog(MandehdarZangireiActivity.this);
            mPresenter.getDetails(model);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}