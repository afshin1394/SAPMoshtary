package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.CustomSpinnerAdapter;
import com.saphamrah.Adapter.KalaForMarjoeeAdapter;
import com.saphamrah.Adapter.MarjoeeAdapter;
import com.saphamrah.BaseMVP.MarjoeeKalaMVP;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.MarjoeeKalaPresenter;
import com.saphamrah.Model.ElamMarjoeeSatrPPCModel;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MarjoeeKalaActivity extends AppCompatActivity implements MarjoeeKalaMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , MarjoeeKalaActivity.this);
    private MarjoeeKalaMVP.PresenterOps mPresenter;

    private ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels;
    private ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels;
    private ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels;
    private ArrayList<String> nameElatMarjoeeKala;
    private long ccDarkhastFaktor;
    private int ccMoshtary;
    private int noeSabtMarjoeeForMamorPakhsh;
    private int noeMasouliat;
    private CustomAlertDialog customAlertDialog;
    private MarjoeeAdapter adapter;

    private View alertView;
    private AlertDialog show;
    private FloatingActionMenu fabMenu;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marjoee_kala);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();
        customLoadingDialog = new CustomLoadingDialog();

        customAlertDialog = new CustomAlertDialog(MarjoeeKalaActivity.this);
        ImageView imgBack = findViewById(R.id.imgBack);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        Intent getIntent = getIntent();
        ccDarkhastFaktor = getIntent.getLongExtra("ccDarkhastFaktor" , -1);
        ccMoshtary = getIntent.getIntExtra("ccMoshtary" , -1);
        Log.d("marjoee" , "ccDarkhastFaktor in marjoee kala activity : " + ccDarkhastFaktor);
        checkDarkhastAndMoshtary();
        listKalaForMarjoeeModels = new ArrayList<>();
        kalaElamMarjoeeModels = new ArrayList<>();
        elatMarjoeeKalaModels = new ArrayList<>();
        nameElatMarjoeeKala = new ArrayList<>();

        mPresenter.checkFaktorDetails(ccDarkhastFaktor, ccMoshtary);

        adapter = new MarjoeeAdapter(MarjoeeKalaActivity.this, kalaElamMarjoeeModels, true, new MarjoeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, KalaElamMarjoeeModel kalaElamMarjoeeModel , int position)
            {
                if (operation == Constants.DELETE())
                {
                    showRemoveAlert(kalaElamMarjoeeModel , position);
                }
                else if (operation == Constants.CLEARING())
                {
                    showEditCountmAlert(kalaElamMarjoeeModel.getCcElamMarjoeeSatrPPC(), kalaElamMarjoeeModel.getNameKala(), kalaElamMarjoeeModel.getTedad3());
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MarjoeeKalaActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MarjoeeKalaActivity.this , 0));
        recyclerView.setAdapter(adapter);


        mPresenter.getForoshandehMamorPakhshInfo();
        mPresenter.updateListKalaForMarjoee(ccDarkhastFaktor);
        mPresenter.getListElatMarjoee();


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                showAddItemAlert();
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.insertDariaftPardakht(ccDarkhastFaktor , ccMoshtary);
            }
        });

    }


    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
    }

    @Override
    public Context getAppContext()
    {
        return MarjoeeKalaActivity.this;
    }

    @Override
    public void onGetForoshandehMamorPakhshInfo(int noeMasouliat , int noeSabtMarjoee)
    {
        this.noeSabtMarjoeeForMamorPakhsh = noeSabtMarjoee;
        this.noeMasouliat = noeMasouliat;
        if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            if (noeSabtMarjoee == Constants.NOE_SABT_MARJOEE_2)
            {
                fabMenu.setVisibility(View.GONE);
            }
            else
            {
                fabMenu.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onUpdateListKalaForMarjoee(ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModels)
    {
        this.listKalaForMarjoeeModels.clear();
        this.listKalaForMarjoeeModels.addAll(listKalaForMarjoeeModels);
        showToast(R.string.successfullyUpdateListMarjoee, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        mPresenter.getKalaMarjoee(ccDarkhastFaktor);
    }

    @Override
    public void onGetListElatMarjoeeKala(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels)
    {
        this.elatMarjoeeKalaModels.clear();
        this.elatMarjoeeKalaModels.addAll(elatMarjoeeKalaModels);
        for (ElatMarjoeeKalaModel model : elatMarjoeeKalaModels)
        {
            nameElatMarjoeeKala.add(model.getSharh());
        }
    }

    @Override
    public void onGetKalaMarjoee(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels)
    {
        this.kalaElamMarjoeeModels.clear();
        this.kalaElamMarjoeeModels.addAll(kalaElamMarjoeeModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorAddToMarjoee(int errorResId)
    {
        TextView lblError = alertView.findViewById(R.id.lblError);
        lblError.setVisibility(View.VISIBLE);
        lblError.setText(getResources().getString(errorResId));
    }

    @Override
    public void onSuccessInsertMarjoee()
    {
        showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        try
        {
            show.dismiss();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public void onSuccessRemoveItem(int position)
    {
        showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        kalaElamMarjoeeModels.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position , kalaElamMarjoeeModels.size());
    }

    @Override
    public void onSuccessInsertDariaftPardakht()
    {
        MarjoeeKalaActivity.this.finish();
    }

    @Override
    public void showAlert(int resIdMessage, int messageType, boolean closeActivity)
    {
        customAlertDialog.showMessageAlert(MarjoeeKalaActivity.this, closeActivity, "", getResources().getString(resIdMessage), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(MarjoeeKalaActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void showToast(String message, int messageType, int duration)
    {
        customAlertDialog.showToast(MarjoeeKalaActivity.this, message, messageType, duration);
    }


    private void showAddItemAlert()
    {
        final ArrayList<ElatMarjoeeKalaModel> elatMarjoee = new ArrayList<>();
        final ArrayList<ListKalaForMarjoeeModel> selectedKala = new ArrayList<>(); //this array contain only one item, declare as array for prevent declare global variable
        final ArrayList<ListKalaForMarjoeeModel> kalaForMojodi = new ArrayList<>(listKalaForMarjoeeModels); //local list for using in adapter
        final AlertDialog.Builder builder = new AlertDialog.Builder(MarjoeeKalaActivity.this);
        alertView = getLayoutInflater().inflate(R.layout.alert_add_kala_to_marjoee , null);
        RecyclerView recyclerViewKala = alertView.findViewById(R.id.recyclerView);
        Log.d("MarjoeeKala","kalaForMojodi:" +kalaForMojodi.toString());
        final KalaForMarjoeeAdapter adapterKala = new KalaForMarjoeeAdapter(MarjoeeKalaActivity.this, kalaForMojodi, new KalaForMarjoeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListKalaForMarjoeeModel listKalaForMarjoeeModel, int position) {
                Log.d("marjoeeKala","listKalaForMarjoeeModel:"+ listKalaForMarjoeeModel.toString() + " , Position:" + position);
                selectedKala.clear();
                selectedKala.add(listKalaForMarjoeeModel);
                Log.d("MarjoeeKala","selctedKala Sizee:"+selectedKala.size()+" , selectedKala:"+selectedKala.toString());
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MarjoeeKalaActivity.this);
        recyclerViewKala.setLayoutManager(mLayoutManager);
        recyclerViewKala.setItemAnimator(new DefaultItemAnimator());
        recyclerViewKala.addItemDecoration(new DividerItemDecoration(MarjoeeKalaActivity.this , 0));
        recyclerViewKala.setAdapter(adapterKala);
        CustomTextInputLayout txtinputSearch = alertView.findViewById(R.id.txtinputSearchKalaName);
        final CustomTextInputLayout txtinputCount = alertView.findViewById(R.id.txtinputCount);
        EditText edttxtSearch = alertView.findViewById(R.id.txtSearchKalaName);
        final EditText edttxtCount = alertView.findViewById(R.id.txtCount);
        final TextView lblError = alertView.findViewById(R.id.lblError);
        final Spinner spinnerElatMarjoee = alertView.findViewById(R.id.spinnerSelectElatMarjoee);
        Button btnCancel = alertView.findViewById(R.id.btnCancel);
        Button btnOK = alertView.findViewById(R.id.btnApply);
        final Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputSearch.setTypeface(font);
        txtinputCount.setTypeface(font);
        edttxtSearch.setTypeface(font);
        edttxtCount.setTypeface(font);
        lblError.setTypeface(font);
        btnCancel.setTypeface(font);
        btnOK.setTypeface(font);
        CustomSpinnerAdapter adapterOne = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, nameElatMarjoeeKala);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerElatMarjoee.setAdapter(adapterOne);
        lblError.setVisibility(View.GONE);
        builder.setCancelable(true);
        builder.setView(alertView);
        builder.create();

        if (!(MarjoeeKalaActivity.this).isFinishing())
        {
            show = builder.show();
            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(MarjoeeKalaActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeKalaActivity", "showAddItemAlert", "");
            }

            spinnerElatMarjoee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    elatMarjoee.clear();
                    elatMarjoee.add(elatMarjoeeKalaModels.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edttxtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0)
                    {
                        kalaForMojodi.clear();
                        adapterKala.clearSelecedItem();
                        String searchWord = new PubFunc().new LanguageUtil().convertFaNumberToEN(s.toString());
                        for (int i=0 ; i<listKalaForMarjoeeModels.size()  ; i++)
                        {
                            if (listKalaForMarjoeeModels.get(i).getNameKala().contains(searchWord))
                            {
                                kalaForMojodi.add(listKalaForMarjoeeModels.get(i));
                                adapterKala.notifyDataSetChanged();
                            }
                        }
                    }
                    else //if (s.length() == 0)
                    {
                        kalaForMojodi.clear();
                        adapterKala.clearSelecedItem();
                        kalaForMojodi.addAll(listKalaForMarjoeeModels);
                    }
                    if (kalaForMojodi.size() == 0){
                        kalaForMojodi.clear();
                        adapterKala.notifyDataSetChanged();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    show.dismiss();
                }
            });

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    lblError.setVisibility(View.GONE);
                    txtinputCount.setError(null);

                    boolean isZayeatTolid = false;

                    Log.d("marjoeeKala","selectedKala.size:"+ selectedKala.size());
                    Log.d("marjoeeKala","getCcElatMarjoeeKala: "+ elatMarjoee.get(0).getCcElatMarjoeeKala());
                    Log.d("marjoeeKala","isZayeatTolid:"+ isZayeatTolid);

                    if (selectedKala.size() > 0 && selectedKala.get(0) != null)
                    {
                        if (elatMarjoee.get(0).getIsZayeatTolid()==1 && selectedKala.get(0).getIsKalaZayeatTolid()==1)
                            isZayeatTolid=true;


                        if(!isZayeatTolid)
                        {
                            lblError.setVisibility(View.VISIBLE);
                            lblError.setText(getResources().getString(R.string.errorSelectElatMarjooeZayeatTolid));
                        }
                        else if (elatMarjoee.size() > 0 && elatMarjoee.get(0) != null )
                        {
                            ElamMarjoeeSatrPPCModel elamMarjoeeSatrPPCModel = new ElamMarjoeeSatrPPCModel();
                            elamMarjoeeSatrPPCModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                            elamMarjoeeSatrPPCModel.setCcElatMarjoeeKala(elatMarjoee.get(0).getCcElatMarjoeeKala());
                            elamMarjoeeSatrPPCModel.setCodeNoeMarjoee(elatMarjoee.get(0).getCodeNoeElat());
                            elamMarjoeeSatrPPCModel.setCcKala(selectedKala.get(0).getCcKala());
                            elamMarjoeeSatrPPCModel.setCcKalaCode(selectedKala.get(0).getCcKalaCode());
                            elamMarjoeeSatrPPCModel.setShomarehBach(selectedKala.get(0).getShomarehBach());
                            elamMarjoeeSatrPPCModel.setTarikhTolid(selectedKala.get(0).getTarikhTolid());
                            elamMarjoeeSatrPPCModel.setTarikhEngheza(selectedKala.get(0).getTarikhEngheza());
                            elamMarjoeeSatrPPCModel.setFee((int)selectedKala.get(0).getMablaghForosh());
                            elamMarjoeeSatrPPCModel.setCcTaminkonandeh(selectedKala.get(0).getCcTaminKonandeh());
                            elamMarjoeeSatrPPCModel.setGheymatMasrafkonandeh((int)selectedKala.get(0).getMablaghMasrafKonandeh());
                            mPresenter.checkKalaForAddToMarjoee(selectedKala.get(0), kalaElamMarjoeeModels, elamMarjoeeSatrPPCModel, ccMoshtary, edttxtCount.getText().toString().trim());
                        }
                        else
                        {
                            lblError.setVisibility(View.VISIBLE);
                            lblError.setText(getResources().getString(R.string.errorSelectElatMarjooe));
                        }
                    }
                    else
                    {
                        lblError.setVisibility(View.VISIBLE);
                        lblError.setText(getResources().getString(R.string.errorSelectItem));
                    }
                }
            });

        }
    }


    private void showEditCountmAlert(final int ccElamMarjoeeSatr, String itemName , final int itemCount)
    {
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        AlertDialog.Builder builder = new AlertDialog.Builder(MarjoeeKalaActivity.this);
        View view = getLayoutInflater().inflate(R.layout.alert_edit_item_count , null);
        TextView lblItemName = view.findViewById(R.id.lblItemName);
        final EditText txtCount = view.findViewById(R.id.txtCount);
        final TextView lblError = view.findViewById(R.id.lblError);
        Button btnOK = view.findViewById(R.id.btnApply);
        lblItemName.setTypeface(font);
        txtCount.setTypeface(font);
        lblError.setTypeface(font);
        btnOK.setTypeface(font);
        lblItemName.setText(itemName);
        txtCount.setText(String.valueOf(itemCount));
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        if (!(MarjoeeKalaActivity.this).isFinishing())
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
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(MarjoeeKalaActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeKalaActivity", "showEditCountmAlert", "");
            }
            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    lblError.setVisibility(View.GONE);
                    int selectedCount = itemCount;
                    try
                    {
                        selectedCount = Integer.parseInt(txtCount.getText().toString());
                        if (noeMasouliat == 4 || noeMasouliat == 5)
                        {
                            if (noeSabtMarjoeeForMamorPakhsh == Constants.NOE_SABT_MARJOEE_2)
                            {
                                if (selectedCount > 0 && selectedCount <= itemCount)
                                {
                                    show.dismiss();
                                    mPresenter.checkUpdateCountOfMarjoee(ccDarkhastFaktor, ccElamMarjoeeSatr, itemCount, selectedCount);
                                }
                                else
                                {
                                    lblError.setVisibility(View.VISIBLE);
                                    lblError.setText(getResources().getString(R.string.invalidRangeForEditMarjoee));
                                }
                            }
                            else
                            {
                                if (selectedCount > 0)
                                {
                                    show.dismiss();
                                    mPresenter.checkUpdateCountOfMarjoee(ccDarkhastFaktor, ccElamMarjoeeSatr, itemCount, selectedCount);
                                }
                                else
                                {
                                    lblError.setVisibility(View.VISIBLE);
                                    lblError.setText(getResources().getString(R.string.errorZeroCount));
                                }
                            }
                        }
                        else
                        {
                            if (selectedCount > 0 && selectedCount <= itemCount)
                            {
                                show.dismiss();
                                mPresenter.checkUpdateCountOfMarjoee(ccDarkhastFaktor, ccElamMarjoeeSatr, itemCount, selectedCount);
                            }
                            else
                            {
                                lblError.setVisibility(View.VISIBLE);
                                lblError.setText(getResources().getString(R.string.invalidRangeForEditMarjoee));
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        lblError.setVisibility(View.VISIBLE);
                        lblError.setText(getResources().getString(R.string.invalidInputCount));
                    }
                }
            });

        }
    }

    private void showRemoveAlert(final KalaElamMarjoeeModel kalaElamMarjoeeModel , final int position)
    {
        customAlertDialog.showLogMessageAlert(MarjoeeKalaActivity.this, false, "", getResources().getString(R.string.deleteWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                mPresenter.checkRemoveKalaFromMarjoee(kalaElamMarjoeeModel , position);
            }
        });
    }

    private void checkDarkhastAndMoshtary()
    {
        if (ccDarkhastFaktor == -1)
        {
            customAlertDialog.showMessageAlert(MarjoeeKalaActivity.this, "", getResources().getString(R.string.errorFindccDarkhastFaktor), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
                @Override
                public void setOnCancelClick() {

                }

                @Override
                public void setOnApplyClick() {
                    Intent returnIntent = new Intent();
                    setResult(RESULT_CANCELED , returnIntent);
                    MarjoeeKalaActivity.this.finish();
                }
            });
        }
        else if (ccMoshtary == -1)
        {
            customAlertDialog.showMessageAlert(MarjoeeKalaActivity.this, "", getResources().getString(R.string.errorSelectCustomer), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
                @Override
                public void setOnCancelClick() {

                }

                @Override
                public void setOnApplyClick() {
                    Intent returnIntent = new Intent();
                    setResult(RESULT_CANCELED , returnIntent);
                    MarjoeeKalaActivity.this.finish();
                }
            });
        }
    }

    @Override
    public void showAlertLoading() {
        alertDialog = customLoadingDialog.showLoadingDialog(MarjoeeKalaActivity.this);
    }

    @Override
    public void closeAlertLoading() {
        if (alertDialog != null)
        {
            try
            {
                alertDialog.dismiss();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(MarjoeeKalaActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), TAG, "", "closeLoadingDialog", "");
            }
        }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeKalaActivity", "startMVPOps", "");
        }
    }


    private void initialize(MarjoeeKalaMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new MarjoeeKalaPresenter(view);
            stateMaintainer.put(MarjoeeKalaMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeKalaActivity", "initialize", "");
        }
    }


    private void reinitialize(MarjoeeKalaMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(MarjoeeKalaMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeKalaActivity", "reinitialize", "");
            }
        }
    }


}
