package com.saphamrah.MVP.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Adapter.SelectBonusAdapter;
import com.saphamrah.BaseMVP.SelectBonusMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.SelectBonusPresenter;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.UIModel.JayezehEntekhabiMojodiModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.ICustomEditableAlert;
import com.saphamrah.Utils.StateMaintainer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SelectBonusActivity extends AppCompatActivity implements SelectBonusMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , SelectBonusActivity.this);
    private SelectBonusMVP.PresenterOps mPresenter;

    private SelectBonusAdapter adapter;
    private CustomAlertDialog customAlertDialog;
    private ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorTakhfifModels;
    private ArrayList<String> bonusTitles;
    private ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels;
    private int selectedccTakhfif;
    private int selectedIndexTakhfif;
    private DecimalFormat formatter;

    private EditText editTextSelectBonus;
    private EditText editTextTakhfifFaktor;
    private EditText editTextMablaghJayezeh;
    private EditText editTextMande;
    private EditText editTextMaxTedadJayezeh;
    private RecyclerView recyclerView;
    private CustomTextInputLayout customTextInputLayout;
   private ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach = new ArrayList<>();
   private ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bonus);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        customAlertDialog = new CustomAlertDialog(SelectBonusActivity.this);
        darkhastFaktorTakhfifModels = new ArrayList<>();
        bonusTitles = new ArrayList<>();
        jayezehEntekhabiMojodiModels = new ArrayList<>();
        selectedccTakhfif = -1;
        selectedIndexTakhfif = 0;
        formatter = new DecimalFormat("#,###,###");


        editTextSelectBonus = findViewById(R.id.txtSelectBonus);
        editTextTakhfifFaktor = findViewById(R.id.txtTakhfifFaktor);
        editTextMablaghJayezeh = findViewById(R.id.txtMablaghJayezeh);
        editTextMande = findViewById(R.id.txtMandeh);
        editTextMaxTedadJayezeh = findViewById(R.id.txtMaxTedadJayezeh);
        customTextInputLayout = findViewById(R.id.txtinputMandeh);
        recyclerView = findViewById(R.id.recyclerView);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnApply = findViewById(R.id.btnApply);

        setJayezehEntekhabiAdapter();

        mPresenter.getBonus();


        editTextSelectBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.checkBeforeSelectBonus(selectedccTakhfif);
            }
        });

        editTextSelectBonus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(editTextSelectBonus , hasFocus);
                if (hasFocus)
                {
                    mPresenter.checkBeforeSelectBonus(selectedccTakhfif);
                }
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.checkArzeshAfzoodeh(darkhastFaktorTakhfifModels.get(selectedIndexTakhfif).getNoeJayezehTakhfif(), jayezehEntekhabiMojodiModels, darkhastFaktorTakhfifModels.get(selectedIndexTakhfif), selectedccTakhfif, editTextTakhfifFaktor.getText().toString(), editTextMablaghJayezeh.getText().toString(), editTextMande.getText().toString(), editTextMaxTedadJayezeh.getText().toString(),
                        KalaMojodiModelsMaxShomarehBach, KalaMojodiModelsMaxMojodi);

//                mPresenter.checkInsert(darkhastFaktorTakhfifModels.get(selectedIndexTakhfif).getNoeJayezehTakhfif() ,jayezehEntekhabiMojodiModels, darkhastFaktorTakhfifModels.get(selectedIndexTakhfif), selectedccTakhfif, editTextTakhfifFaktor.getText().toString(), editTextMablaghJayezeh.getText().toString(), editTextMande.getText().toString(), editTextMaxTedadJayezeh.getText().toString(),
//                        KalaMojodiModelsMaxShomarehBach  , KalaMojodiModelsMaxMojodi
//                );
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectBonusActivity.this.finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public Context getAppContext()
    {
        return SelectBonusActivity.this;
    }

    @Override
    public void setBonus(ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels)
    {
        this.darkhastFaktorTakhfifModels.clear();
        this.darkhastFaktorTakhfifModels.addAll(darkhastFaktorJayezehTakhfifModels);
        bonusTitles.clear();
        for (DarkhastFaktorJayezehTakhfifModel model : this.darkhastFaktorTakhfifModels)
        {
            bonusTitles.add(model.getSharhJayezehTakhfif());
        }
        if (darkhastFaktorTakhfifModels.size() > 0)
        {
            if (darkhastFaktorTakhfifModels.get(0).getNoeJayezehTakhfif() == DarkhastFaktorJayezehTakhfifModel.NoeJayezeh())
            {
                showJayezehData(0);
            }
            else if (darkhastFaktorTakhfifModels.get(0).getNoeJayezehTakhfif() == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif())
            {
                showTakhfifData(0);
            }else if (darkhastFaktorTakhfifModels.get(0).getNoeJayezehTakhfif() == DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh()){

                showJayezehArzeshAfzodehData(0);
            }
        }
    }

    private void showJayezehData(int selectedIndex)
    {
        editTextSelectBonus.setText(darkhastFaktorTakhfifModels.get(selectedIndex).getSharhJayezehTakhfif());
        editTextMaxTedadJayezeh.setText(String.valueOf(darkhastFaktorTakhfifModels.get(selectedIndex).getTedadJayezeh()));
        selectedccTakhfif = darkhastFaktorTakhfifModels.get(selectedIndex).getCcJayezehTakhfif();
        editTextTakhfifFaktor.setVisibility(View.GONE);
        editTextMande.setVisibility(View.GONE);
        editTextMablaghJayezeh.setVisibility(View.GONE);
        selectedIndexTakhfif = selectedIndex;
        mPresenter.getKalaForJayezeh(selectedccTakhfif, darkhastFaktorTakhfifModels.get(selectedIndex).getExtraProp_ccJayezehSatr(), darkhastFaktorTakhfifModels.get(selectedIndexTakhfif).getNoeJayezehTakhfif());
    }


    private void showTakhfifData(int selectedIndex)
    {
        editTextSelectBonus.setText(darkhastFaktorTakhfifModels.get(selectedIndex).getSharhJayezehTakhfif());
        editTextMaxTedadJayezeh.setText(String.valueOf(darkhastFaktorTakhfifModels.get(selectedIndex).getTedadJayezeh()));
        editTextTakhfifFaktor.setText(String.valueOf(darkhastFaktorTakhfifModels.get(selectedIndex).getMablaghJayezehTakhfif()));
        selectedccTakhfif = darkhastFaktorTakhfifModels.get(selectedIndex).getCcJayezehTakhfif();
        editTextTakhfifFaktor.setVisibility(View.VISIBLE);
        editTextMande.setVisibility(View.VISIBLE);
        editTextMablaghJayezeh.setVisibility(View.VISIBLE);
        selectedIndexTakhfif = selectedIndex;
        mPresenter.getKalaForJayezeh(selectedccTakhfif, 0, darkhastFaktorTakhfifModels.get(selectedIndexTakhfif).getNoeJayezehTakhfif());
    }

    private void showJayezehArzeshAfzodehData(int selectedIndex)
    {
        editTextSelectBonus.setText(darkhastFaktorTakhfifModels.get(selectedIndex).getSharhJayezehTakhfif());
        editTextMaxTedadJayezeh.setText(String.valueOf(darkhastFaktorTakhfifModels.get(selectedIndex).getTedadJayezeh()));
        editTextTakhfifFaktor.setText(String.valueOf(darkhastFaktorTakhfifModels.get(selectedIndex).getMablaghJayezehTakhfif()));
        selectedccTakhfif = darkhastFaktorTakhfifModels.get(selectedIndex).getCcJayezehTakhfif();
        editTextMaxTedadJayezeh.setVisibility(View.GONE);
        editTextTakhfifFaktor.setVisibility(View.VISIBLE);
        editTextMande.setVisibility(View.VISIBLE);
        editTextMablaghJayezeh.setVisibility(View.VISIBLE);
        selectedIndexTakhfif = selectedIndex;
        mPresenter.getKalaForJayezeh(selectedccTakhfif, darkhastFaktorTakhfifModels.get(selectedIndex).getExtraProp_ccJayezehSatr(), darkhastFaktorTakhfifModels.get(selectedIndexTakhfif).getNoeJayezehTakhfif());
    }

    @Override
    public void onEmptyGoodsHaveBonus()
    {
        customAlertDialog.showMessageAlert(SelectBonusActivity.this, true, "", getResources().getString(R.string.emptyJayezeh), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void openSpinnerSelectBonus()
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(SelectBonusActivity.this, bonusTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                selectedIndexTakhfif = selectedIndex;
                if (darkhastFaktorTakhfifModels.get(selectedIndex).getNoeJayezehTakhfif() == DarkhastFaktorJayezehTakhfifModel.NoeJayezeh())
                {
                    showJayezehData(selectedIndex);
                }
                else if (darkhastFaktorTakhfifModels.get(selectedIndex).getNoeJayezehTakhfif() == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif())
                {
                    showTakhfifData(selectedIndex);
                }
                else if (darkhastFaktorTakhfifModels.get(selectedIndex).getNoeJayezehTakhfif() == DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh()){
                    showJayezehArzeshAfzodehData(selectedIndex);
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetKalaForJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi, int noeJayezehTakhfif)
    {
        this.KalaMojodiModelsMaxShomarehBach = KalaMojodiModelsMaxShomarehBach;
        this.KalaMojodiModelsMaxMojodi = KalaMojodiModelsMaxMojodi;
        this.jayezehEntekhabiMojodiModels.clear();
        this.jayezehEntekhabiMojodiModels.addAll(jayezehEntekhabiMojodiModels);
        if (darkhastFaktorTakhfifModels.get(selectedIndexTakhfif).getNoeJayezehTakhfif() == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif())
        {
            if (jayezehEntekhabiMojodiModels.size() > 0)
            {
                editTextMaxTedadJayezeh.setText(String.valueOf(Math.round(darkhastFaktorTakhfifModels.get(selectedIndexTakhfif).getMablaghJayezehTakhfif() / jayezehEntekhabiMojodiModels.get(0).getMablaghForosh())));
            }
            else
            {
                editTextMaxTedadJayezeh.setText("0");
            }
        }
        setJayezehEntekhabiAdapter();
        mPresenter.calculateMablaghJayezeh(jayezehEntekhabiMojodiModels , editTextTakhfifFaktor.getText().toString(), noeJayezehTakhfif);
        //showAlertSelectKala(jayezehEntekhabiMojodiModels);
    }

    private void setJayezehEntekhabiAdapter()
    {
        adapter = new SelectBonusAdapter(SelectBonusActivity.this, jayezehEntekhabiMojodiModels, new SelectBonusAdapter.OnItemClickListener() {
            @Override
            public void onRemoveFocus(JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel, int position , String count)
            {
                int intCount = 0;
                try
                {
                    intCount = Integer.parseInt(count);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
                jayezehEntekhabiMojodiModel.setSelectedCount(intCount);
                int noeJayezehTakhfif = jayezehEntekhabiMojodiModel.getCodeNoe();
                mPresenter.calculateMablaghJayezeh(jayezehEntekhabiMojodiModels , editTextTakhfifFaktor.getText().toString(),noeJayezehTakhfif);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SelectBonusActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(SelectBonusActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCalculateMablaghJayezeh(float mablaghJayezeh, float mandeh, int noeJayezehTakhfif)
    {
        editTextMande.setText(formatter.format(mandeh));
        editTextMablaghJayezeh.setText(formatter.format(mablaghJayezeh));

        if (mandeh < 0 && (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif() || noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh()))
        {
            customTextInputLayout.setError(getResources().getString(R.string.errorNegativeRemain));
        }
        else
        {
            customTextInputLayout.setError(null);
        }
    }

    @Override
    public void onErrorInsert(int resId, String kalaName)
    {
        String error = "";
        if (kalaName.length() == 0)
        {
            error = getResources().getString(resId);
        }
        else
        {
            error = getResources().getString(resId , kalaName);
        }
        customAlertDialog.showMessageAlert(SelectBonusActivity.this, false, "", error, Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void onSuccessInsert()
    {

        showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        setResult(RESULT_OK);
        SelectBonusActivity.this.finish();
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(SelectBonusActivity.this, getResources().getString(resId), messageType, duration);
    }

    @SuppressLint("ShowToast")
    @Override
    public void toastTest(int SelectedCount, int Max_MojodyByShomarehBach, int Max_Mojody, int TedadSefarshDarkhast) {
        customAlertDialog.showToast(SelectBonusActivity.this, "تعداد جایزه" + SelectedCount + " مکس موجودی بچ" +Max_MojodyByShomarehBach +  "مکس موجودی" +Max_Mojody + "تعداد سفارش درخواست" + TedadSefarshDarkhast, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

    }

    @Override
    public void showTaghirMandehDialog(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, String mablaghTakhfif, String mablaghJayezeh, long mandeh, String maxTedadJayeze, ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi) {


        customAlertDialog.showEditableTextAlert(SelectBonusActivity.this, getString(R.string.txt_remaining_bonus), String.valueOf(mandeh).trim().replace(","  , ""), getString(R.string.cancel), getString(R.string.confirm), new ICustomEditableAlert() {
             @Override
             public void setOnCancelClick() {

             }

             @Override
             public void setOnApplyClick(CustomTextInputLayout customTextInputLayout,Object message) {



                 String englishNumerals = new BigDecimal(String.valueOf(message)).toString();

                 float modifiedMondeh = Float.parseFloat(englishNumerals.replace("," , ""));
                 if (modifiedMondeh <= mandeh && modifiedMondeh >= 0 ){
                     mPresenter.checkInsertMandehArzeshAfzoodeh(noeJayezehTakhfif, jayezehEntekhabiMojodiModels, darkhastFaktorJayezehTakhfifModel, selectedccTakhfif, mablaghTakhfif,mablaghJayezeh,englishNumerals,maxTedadJayeze,KalaMojodiModelsMaxShomarehBach,KalaMojodiModelsMaxMojodi);
                 }else{
                     customTextInputLayout.setError(getResources().getString(R.string.errorNegativeRemainDialog));
                 }


             }

             @Override
             public void onTextChange(CustomTextInputLayout customTextInputLayout,String s) {
                     if (Float.parseFloat(s) > mandeh) {
                         customTextInputLayout.setError(getResources().getString(R.string.errorNegativeRemainDialog));
                     }else{
                         customTextInputLayout.setError(null);
                     }
                 }
             });
    }

    @Override
    public void closeArzeshAfzoodehDialog() {
        customAlertDialog.hideEditableTextAlert();
    }

    @Override
    public void onInsertJayezehNaghdyArzeshAfzoodeh(float fltMandeh) {
        SelectBonusActivity.this.finish();
    }

    private void showAlertSelectKala(final ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels)
    {
        final DecimalFormat formatter = new DecimalFormat("#,###,###");
        final ArrayList<JayezehEntekhabiMojodiModel> selectedKalaModel = new ArrayList<>(); //this array contain only one item, declare as array for prevent declare global variable
        final ArrayList<String> selectedKalaCount = new ArrayList<>(); //this array contain only one item, declare as array for prevent declare global variable
        final AlertDialog.Builder builder = new AlertDialog.Builder(SelectBonusActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.alert_select_bonus , null);
        RecyclerView recyclerView = alertView.findViewById(R.id.recyclerView);
        for (int i=0 ; i < jayezehEntekhabiMojodiModels.size() ; i++)
        {
            selectedKalaCount.add("0");
        }
        SelectBonusAdapter adapter = new SelectBonusAdapter(SelectBonusActivity.this, jayezehEntekhabiMojodiModels, new SelectBonusAdapter.OnItemClickListener() {
            @Override
            public void onRemoveFocus(JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel, int position , String count)
            {
                selectedKalaCount.set(position , count);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SelectBonusActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(SelectBonusActivity.this , 0));
        recyclerView.setAdapter(adapter);
        final CustomTextInputLayout txtinputCount = alertView.findViewById(R.id.txtinputCount);
        final EditText edttxtCount = alertView.findViewById(R.id.txtCount);
        final TextView lblTakhfifFaktor = alertView.findViewById(R.id.lblTakhfifFaktor);
        final TextView lblMablaghJayezeh = alertView.findViewById(R.id.lblMablaghJayezeh);
        final TextView lblMandehJayezeh = alertView.findViewById(R.id.lblMandeh);
        final TextView lblMaxTedadJayezeh = alertView.findViewById(R.id.lblMaxTedadJayezeh);
        final TextView lblError = alertView.findViewById(R.id.lblError);
        Button btnCancel = alertView.findViewById(R.id.btnCancel);
        Button btnOK = alertView.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputCount.setTypeface(font);
        edttxtCount.setTypeface(font);
        lblTakhfifFaktor.setTypeface(font);
        lblMablaghJayezeh.setTypeface(font);
        lblMandehJayezeh.setTypeface(font);
        lblMaxTedadJayezeh.setTypeface(font);
        int maxTedad = Math.round(darkhastFaktorTakhfifModels.get(0).getMablaghJayezehTakhfif() / jayezehEntekhabiMojodiModels.get(0).getMablaghForosh());
        lblTakhfifFaktor.setText(String.format("%1$s : %2$s", getResources().getString(R.string.takhfifFaktor), formatter.format(darkhastFaktorTakhfifModels.get(0).getMablaghJayezehTakhfif())));
        lblMablaghJayezeh.setText(String.format("%1$s : %2$s", getResources().getString(R.string.mablaghJayezeh), formatter.format(darkhastFaktorTakhfifModels.get(0).getMablaghJayezehTakhfif())));
        lblMandehJayezeh.setText(String.format("%1$s : %2$s", getResources().getString(R.string.mande), formatter.format(darkhastFaktorTakhfifModels.get(0).getMablaghJayezehTakhfif())));
        lblMaxTedadJayezeh.setText(String.format("%1$s : %2$s", getResources().getString(R.string.maxTedadJayezeh), maxTedad));
        lblMaxTedadJayezeh.setText(String.format("%1$s : %2$s", getResources().getString(R.string.maxTedadJayezeh), maxTedad));
        lblError.setTypeface(font);
        btnCancel.setTypeface(font);
        btnOK.setTypeface(font);
        lblError.setVisibility(View.GONE);
        builder.setCancelable(true);
        builder.setView(alertView);
        builder.create();
        if (!(SelectBonusActivity.this).isFinishing())
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
                logger.insertLogToDB(SelectBonusActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "SelectBonusActivity", "showAlertSelectKala", "");
            }

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
                    if (selectedKalaModel.size() > 0 && selectedKalaModel.get(0) != null)
                    {

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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SelectBonusActivity", "startMVPOps", "");
        }
    }


    private void initialize(SelectBonusMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new SelectBonusPresenter(view);
            stateMaintainer.put(SelectBonusMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SelectBonusActivity", "initialize", "");
        }
    }


    private void reinitialize(SelectBonusMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(SelectBonusMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SelectBonusActivity", "reinitialize", "");
            }
        }
    }


    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(SelectBonusActivity.this, R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable , null , null , null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        else
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(SelectBonusActivity.this, R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_arrow_down) , null , null , null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

}
