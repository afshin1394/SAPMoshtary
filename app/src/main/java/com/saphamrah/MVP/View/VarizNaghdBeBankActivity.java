package com.saphamrah.MVP.View;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.saphamrah.Adapter.AllMoshtaryVarizBeBankAdapter;
import com.saphamrah.Adapter.VarizBeBankAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.VarizNaghdBeBankMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.VarizNaghdBeBankPresenter;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Model.VarizBeBankModel;
import com.saphamrah.Utils.AnimApp;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;

public class VarizNaghdBeBankActivity extends AppCompatActivity implements VarizNaghdBeBankMVP.RequiredViewOps {

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, VarizNaghdBeBankActivity.this);
    private VarizNaghdBeBankMVP.PresenterOps mPresenter;
    private CustomAlertDialog customAlertDialog;
    private DecimalFormat formatter;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;
    private View alertView;
    private DateUtils dateUtils = new DateUtils();

    private String nameBank = "";
    private int ccBank = 0;
    private String nameShobehBank = "";
    private String mablaghUpdate = "";
    private String codeShobe = "";
    private double sum = 0;
    private String selectedDate = "";
    private int ccShomarehHesab = 0;
    private CustomSpinner customSpinner;
    private DecimalFormat decimalFormatter;
    private double checkMablagh =0;
    private String inputString;

    private AllMoshtaryVarizBeBankAdapter allMoshtaryVarizBeBankAdapter;
    private VarizBeBankAdapter varizBeBankAdapter;
    private ArrayList<VarizBeBankModel> selectedVarizBeBankModels = new ArrayList<>();
    private ArrayList<VarizBeBankModel> modelsUpdate = new ArrayList<>();
    private ArrayList<VarizBeBankModel> varizBeBankModelsRecycler = new ArrayList<>();


    @BindView(R.id.editTextFishBankiNumber)
    EditText editTextFishBankiNumber;
    @BindView(R.id.editTextFishBankiDate)
    EditText editTextFishBankiDate;
    @BindView(R.id.editTextNameBank)
    EditText editTextNameBank;
    @BindView(R.id.editTextShobeBank)
    EditText editTextShobeBank;
    @BindView(R.id.editTextMablagh)
    EditText editTextMablagh;
    @BindView(R.id.editTextMablaghEdit)
    EditText editTextMablaghEdit;
    @BindView(R.id.editTextShomarehHesab)
    EditText editTextShomarehHesab;
    @BindView(R.id.img_mablagh)
    ImageView img_mablagh;
    @BindView(R.id.customTextFishBankiNumber)
    CustomTextInputLayout customTextFishBankiNumber;
    @BindView(R.id.customTextFishBankiDate)
    CustomTextInputLayout customTextFishBankiDate;
    @BindView(R.id.customTextNameBanki)
    CustomTextInputLayout customTextNameBanki;
    @BindView(R.id.customTextNameShobeBank)
    CustomTextInputLayout customTextNameShobeBank;
    @BindView(R.id.customTextShomarehHesab)
    CustomTextInputLayout customTextShomarehHesab;
    @BindView(R.id.customTextMablagh)
    CustomTextInputLayout customTextMablagh;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.switch_auto_faktor)
    SwitchCompat switch_auto_faktor;
    @BindView(R.id.lay_varizBeBank)
    ConstraintLayout lay_varizBeBank;
    @BindView(R.id.recycler_view_variz_be_Bank)
    RecyclerView recycler_view_variz_be_Bank;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variz_be_bank);
        ButterKnife.bind(this);
        customSpinner = new CustomSpinner();
        customLoadingDialog = new CustomLoadingDialog();
        // set font
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        decimalFormatter = new DecimalFormat("#,###,###");
        customAlertDialog = new CustomAlertDialog(VarizNaghdBeBankActivity.this);

        // setup before anyThing
        mPresenter = new VarizNaghdBeBankPresenter(this);
        recyclerSetting();
        editTextMablagh.setEnabled(false);
        // request for get all check
        mPresenter.onGetAllBank();

        // data
        editTextFishBankiDate.setOnClickListener(v -> showDatePickerAlert(editTextFishBankiDate));

        editTextFishBankiDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePickerAlert(editTextFishBankiDate);
            }
        });

        // text Mablagh
        editTextMablagh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCommaToPrice(editTextMablagh, s, this);
                mablaghUpdate = s.toString();
            }
        });

        // text Mablagh edit
        editTextMablaghEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCommaToPrice(editTextMablaghEdit, s, this);
            }
        });

        // listener switch
        switch_auto_faktor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i("varizBeBank", String.valueOf(isChecked));
            boolean change = false;
            if (isChecked) {

                editTextMablagh.setEnabled(false);
                editTextMablagh.setText(String.valueOf(sum));
            } else {
                editTextMablagh.setEnabled(true);
            }
        });

        // listener mablagh
        img_mablagh.setOnClickListener(v -> {
            mPresenter.getAllMoshtary();
        });
    }

    @Override
    protected void onResume() {
        mPresenter.getSumMablagh();
        mPresenter.getAllVarizBeBank();
        super.onResume();

    }

    /**
     * on click fab refresh
     */
    @OnClick(R.id.fabRefresh)
    public void fabRefresh() {
        fabMenu.close(true);
        customAlertDialog.showLogMessageAlert(this, false, getResources().getString(R.string.warning), getResources().getString(R.string.warningUpadateVarizBeBank),
                Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
                    @Override
                    public void setOnCancelClick() {

                    }

                    @Override
                    public void setOnApplyClick() {
                        alertDialog = customLoadingDialog.showLoadingDialog(VarizNaghdBeBankActivity.this);
                        mPresenter.getRefresh();
                    }
                });
    }

    /**
     * on click fab add
     */
    @OnClick(R.id.fabAdd)
    public void fabAdd() {
        fabMenu.close(true);
        AnimApp.expand(lay_varizBeBank);
        AnimApp.collapse(recycler_view_variz_be_Bank);

    }




    /**
     * on click btnCancel
     */

    @OnClick(R.id.btnCancel)
    public void btnCancel() {
        AnimApp.collapse(lay_varizBeBank);
        AnimApp.expand(recycler_view_variz_be_Bank);
        clearItem();
        clearError();
    }


    /**
     * result request get all bank
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onGetAllBank(ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels) {

        ArrayList<String> arrayListTitles = new ArrayList<>();

        if (markazShomarehHesabModels.size() > 0) {
            Log.i("VarizBeBank", String.valueOf(markazShomarehHesabModels.size()));
            for (MarkazShomarehHesabModel model : markazShomarehHesabModels) {
                arrayListTitles.add(model.getNameBank() + " - " + model.getNameShobeh() + " " + model.getCodeShobeh() + " -  " + model.getShomarehHesab());
            }
        }

        editTextNameBank.setOnClickListener(v ->
                customSpinner.showSpinner(VarizNaghdBeBankActivity.this, arrayListTitles, new CustomSpinnerResponse() {
                    @Override
                    public void onApplySingleSelection(int selectedIndex) {
                        if (markazShomarehHesabModels.size() > 0) {
                            nameBank = markazShomarehHesabModels.get(selectedIndex).getNameBank() + " - " + markazShomarehHesabModels.get(selectedIndex).getCodeShobeh() + " - " + markazShomarehHesabModels.get(selectedIndex).getShomarehHesab();
                            ccBank = markazShomarehHesabModels.get(selectedIndex).getCcBank();
                            nameShobehBank = markazShomarehHesabModels.get(selectedIndex).getNameShobeh();
                            codeShobe = markazShomarehHesabModels.get(selectedIndex).getCodeShobeh();
                            editTextNameBank.setText(markazShomarehHesabModels.get(selectedIndex).getNameBank());
                            editTextShomarehHesab.setText(markazShomarehHesabModels.get(selectedIndex).getShomarehHesab());
                            editTextShobeBank.setText(markazShomarehHesabModels.get(selectedIndex).getNameShobeh() + " - " + " کد شعبه : " + markazShomarehHesabModels.get(selectedIndex).getCodeShobeh());
                        }
                    }

                    @Override
                    public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                    }
                }));

        editTextNameBank.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                customSpinner.showSpinner(VarizNaghdBeBankActivity.this, arrayListTitles, new CustomSpinnerResponse() {
                    @Override
                    public void onApplySingleSelection(int selectedIndex) {
                        if (markazShomarehHesabModels.size() > 0) {
                            codeShobe = markazShomarehHesabModels.get(selectedIndex).getCodeShobeh();
                            nameBank = markazShomarehHesabModels.get(selectedIndex).getNameBank() + " - " + markazShomarehHesabModels.get(selectedIndex).getCodeShobeh() + " - " + markazShomarehHesabModels.get(selectedIndex).getShomarehHesab();
                            ccBank = markazShomarehHesabModels.get(selectedIndex).getCcBank();
                            nameShobehBank = markazShomarehHesabModels.get(selectedIndex).getNameShobeh();
                            ccShomarehHesab = markazShomarehHesabModels.get(selectedIndex).getCcShomarehHesab();
                            editTextShomarehHesab.setText(markazShomarehHesabModels.get(selectedIndex).getShomarehHesab());
                            editTextNameBank.setText(markazShomarehHesabModels.get(selectedIndex).getNameBank());
                            editTextShobeBank.setText(markazShomarehHesabModels.get(selectedIndex).getNameShobeh() + " - " + " کد شعبه : " + markazShomarehHesabModels.get(selectedIndex).getCodeShobeh());

                        }
                    }

                    @Override
                    public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                    }
                });
            }
        });
    }

    /**
     * get sum mablagh result
     */
    @Override
    public void onGetSumMablagh(ArrayList<VarizBeBankModel> models) {
        modelsUpdate = models;
        /*
        *** This 'for' is for summing numbers with out selected
         */
        sum = 0;
        for (int i = 0; i < models.size(); i++) {
            if(models.get(i).getExtraProp_IsSelected()==0) {
                sum = sum + models.get(i).getMablagh();
                mablaghUpdate = String.valueOf(sum);
            }
        }
        checkMablagh = sum;
        Log.i("VarizBebank", "sum mablagh : " + sum);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        editTextMablagh.setText(formatter.format(sum));
        editTextMablaghEdit.setText(formatter.format(sum));
    }


    /**
     * show Toast
     */
    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(this, getResources().getString(resId), messageType, duration);
    }

    /**
     * get all variz be bank for show recycler
     * @param varizBeBankModels
     */
    @Override
    public void onGetAllVarizBeBank(ArrayList<VarizBeBankModel> varizBeBankModels) {
        varizBeBankModelsRecycler.clear();
        varizBeBankModelsRecycler.addAll(varizBeBankModels) ;
        varizBeBankAdapter.notifyDataSetChanged();
    }



    /**
     * set up recycler in create class
     */
    private void recyclerSetting(){
        varizBeBankAdapter = new VarizBeBankAdapter(BaseApplication.getContext(), varizBeBankModelsRecycler, (operation, position) -> {
            if (operation == Constants.SEND()){
                alertDialog = customLoadingDialog.showLoadingDialog(VarizNaghdBeBankActivity.this);
                mPresenter.sendVariz(varizBeBankModelsRecycler.get(position));
            }
        });

        recycler_view_variz_be_Bank.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext() , LinearLayoutManager.VERTICAL , false));
        recycler_view_variz_be_Bank.setItemAnimator(new DefaultItemAnimator());
        recycler_view_variz_be_Bank.addItemDecoration(new DividerItemDecoration(this , 0));
        recycler_view_variz_be_Bank.setAdapter(varizBeBankAdapter);
        ViewPropertyAnimatorCompat viewAnimator = ViewCompat.animate(recycler_view_variz_be_Bank)
                .scaleY(1).scaleX(1)
                .setStartDelay(800)
                .setDuration(500);

    }

    /**
     * response update
     */
    @Override
    public void onUpdateDao(boolean update) {
        if (update) {
            customAlertDialog.showToast(this, getString(R.string.successSaveData), Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
            clearItem();
            editTextMablagh.setText("");
            mPresenter.getSumMablagh();
            mPresenter.getAllVarizBeBank();
            selectedVarizBeBankModels.clear();
            AnimApp.collapse(lay_varizBeBank);
            AnimApp.expand(recycler_view_variz_be_Bank);
        } else {
            customAlertDialog.showToast(this, getResources().getString(R.string.errorGetData), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    /**
     * result get all moshtary
     * @param varizBeBankModels
     */
    @Override
    public void onGetAllMoshtary(ArrayList<VarizBeBankModel> varizBeBankModels) {
        showAllMoshtaryDialog(varizBeBankModels);
    }


    /**
     * check Have Shomareh Sanad For Update
     * if haveShomarehSanad == true ;  ShomarehSanad is duplicate
     * if haveShomarehSanad == false ;  ShomarehSanad is not duplicate and we can update json
     * * @param haveShomarehSanad
     */
    @Override
    public void onCheckHaveShomarehSanadForUpdate(boolean haveShomarehSanad) {
        /**
         * when selectedListMoshtaryVarizBeBankModels.size() > 0 we use update selected
         * when ! selectedListMoshtaryVarizBeBankModels.size() > 0 we use updateAll
         */
        if (checkData()) {
            if (!haveShomarehSanad){
                if (selectedVarizBeBankModels.size() > 0) {
                    mPresenter.updateDaoAll(Integer.parseInt(editTextFishBankiNumber.getText().toString()),
                            nameShobehBank,
                            codeShobe,
                            editTextShomarehHesab.getText().toString(),
                            ccShomarehHesab,
                            editTextFishBankiNumber.getText().toString(),
                            editTextNameBank.getText().toString(),
                            editTextFishBankiDate.getText().toString(),
                            selectedVarizBeBankModels,
                            editTextMablaghEdit.getText().toString()

                    );
                } else {
                    mPresenter.updateDaoAll(Integer.parseInt(editTextFishBankiNumber.getText().toString()),
                            nameShobehBank,
                            codeShobe,
                            editTextShomarehHesab.getText().toString(),
                            ccShomarehHesab,
                            editTextFishBankiNumber.getText().toString(),
                            editTextNameBank.getText().toString(),
                            editTextFishBankiDate.getText().toString(),
                            modelsUpdate,
                            editTextMablaghEdit.getText().toString()
                    );
                }
            } else {
                customAlertDialog.showToast(this, getString(R.string.haveShomarehSanadForUpdate), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

            }

        }
    }

    @Override
    public void showAlertMessage(int resId, int messageType) {
        customAlertDialog.showMessageAlert(this, false, "", getResources().getString(resId), messageType, getResources().getString(R.string.apply));

    }

    @Override
    public void closeLoading() {
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

    /**
     * on click btnApply
     */

    @OnClick(R.id.btnApply)
    public void btnApply() {
        Log.i("sumMablagh" , String.valueOf(checkMablagh));
        Log.i("sumMablaghEntekhabi" , editTextMablaghEdit.getText().toString());
        if (checkMablagh < Double.parseDouble(inputString)){
            customAlertDialog.showToast(this, getResources().getString(R.string.errorMablaghEntekhabi), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        } else {
            mPresenter.checkHaveShomarehSanadForUpdate(editTextFishBankiNumber.getText().toString());
        }
    }


    /**
     * clear item
     */
    private void clearItem() {
        editTextFishBankiNumber.setText("");
        editTextFishBankiDate.setText("");
        editTextNameBank.setText("");
        editTextShobeBank.setText("");
        editTextShomarehHesab.setText("");
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        editTextMablagh.setText(formatter.format(sum));
    }


    /**
     * all moshtary dialog
     */
    private void showAllMoshtaryDialog(ArrayList<VarizBeBankModel> varizBeBankModels) {
        selectedVarizBeBankModels.clear();
        ArrayList<VarizBeBankModel> searchModel = varizBeBankModels;
        final Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        final AlertDialog.Builder builder = new AlertDialog.Builder(VarizNaghdBeBankActivity.this);
        alertView = getLayoutInflater().inflate(R.layout.alert_all_moshtary_variz_be_bank, null);
        RecyclerView recyclerView = alertView.findViewById(R.id.recyclerView);
        Button btnCancel = alertView.findViewById(R.id.btnCancel);
        Button btnOK = alertView.findViewById(R.id.btnApply);
        TextView txtMablaghKhales = alertView.findViewById(R.id.txtMablaghKhales);
        TextView lblError = alertView.findViewById(R.id.lblError);
        lblError.setTypeface(font);
        btnCancel.setTypeface(font);
        btnOK.setTypeface(font);
        txtMablaghKhales.setTypeface(font);
        builder.setCancelable(true);
        builder.setView(alertView);
        builder.create();

        formatter = new DecimalFormat("#,###,###");
        allMoshtaryVarizBeBankAdapter = new AllMoshtaryVarizBeBankAdapter(BaseApplication.getContext(), varizBeBankModels, new AllMoshtaryVarizBeBankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(double mablagh, int position, ArrayList<VarizBeBankModel> modelsList) {
                selectedVarizBeBankModels = modelsList;
                txtMablaghKhales.setText(String.format("%1$s : %2$s", "مبلغ کل ", formatter.format(mablagh)));
                editTextMablaghEdit.setText(formatter.format(mablagh));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(allMoshtaryVarizBeBankAdapter);
        recyclerView.measure(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.UNSPECIFIED);

        if (!(VarizNaghdBeBankActivity.this).isFinishing()) {
            alertDialog = builder.show();
            try {
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(VarizNaghdBeBankActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), "", "VarizBeBankActivity", "showAddItemAlert", "");
            }


            btnCancel.setOnClickListener(v -> {
                        alertDialog.dismiss();
                        editTextMablaghEdit.setText(formatter.format(sum));
                        editTextMablagh.setText(formatter.format(sum));
                    }
            );

            btnOK.setOnClickListener(v -> {
                lblError.setVisibility(View.GONE);
                lblError.setText("");
                double sumEntekhabi = 0;
                if (selectedVarizBeBankModels.size() > 0) {
                    /*
                     *** This 'for' is for summing numbers when we select
                     */

                    for (int i = 0; i < selectedVarizBeBankModels.size(); i++) {
                        if(selectedVarizBeBankModels.get(i).getExtraProp_IsSelected()==0) {
                            sumEntekhabi = sumEntekhabi + selectedVarizBeBankModels.get(i).getMablagh();
                        }
                    }
                    checkMablagh = sumEntekhabi;
                    editTextMablagh.setText(formatter.format(sumEntekhabi));

                } else {
                    editTextMablagh.setText(formatter.format(sum));
                    editTextMablaghEdit.setText(formatter.format(sum));
                    checkMablagh = sum;
                }
                alertDialog.dismiss();

            });

        }

    }

    /**
     * date picker
     */

    private void showDatePickerAlert(final EditText editText) {
        PersianCalendar persianCalendar = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {
                    try {
                        monthOfYear++;
                        String month = monthOfYear < 10 ? "0" + monthOfYear : String.valueOf(monthOfYear);
                        String day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        selectedDate = getResources().getString(R.string.dateWithSplashFormat, String.valueOf(year), month, day);
                        editText.setText(selectedDate);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }, persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }

    /**
     * set #,###,###
     */

    private void addCommaToPrice(EditText editText, Editable s, TextWatcher textWatcher) {
        editText.removeTextChangedListener(textWatcher);
        try {
            inputString = s.toString();
            inputString = inputString.replaceAll(",", "");
            Long longval = Long.parseLong(inputString);
            String formattedString = decimalFormatter.format(longval);
            editText.setText(formattedString);
            editText.setSelection(editText.getText().length());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        editText.addTextChangedListener(textWatcher);
    }

    // click back press device
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    /**
     * check data for update
     */
    private boolean checkData() {
        boolean isOk = true;
        clearError();

        // check fish banki number
        if (editTextFishBankiNumber.getText().length() == 0) {
            customTextFishBankiNumber.setError(getResources().getString(R.string.errorFishBanki));
            isOk = false;
        }

        // check date
        if (editTextFishBankiDate.getText().length() > 0) {
            if (dateUtils.isDateSelectedBiggerToday(selectedDate)) {
                customTextFishBankiDate.setError(getResources().getString(R.string.errorDateBiggerTodat));
                isOk = false;
            } else {
                if (dateUtils.differenceDatesWithToday(selectedDate) > 0) {
                    customTextFishBankiDate.setError(getResources().getString(R.string.errorDifferenceDatesWithToday));
                    isOk = false;
                }
            }

        } else {
            customTextFishBankiDate.setError(getResources().getString(R.string.errorDateSet));
            isOk = false;
        }

        // check bank
        if (editTextNameBank.getText().length() == 0) {
            customTextNameBanki.setError(getResources().getString(R.string.errorNameBanki));
            customTextShomarehHesab.setError(getResources().getString(R.string.errorNameBanki));
            customTextNameShobeBank.setError(getResources().getString(R.string.errorNameBanki));
            isOk = false;
        }

        String mablagh = editTextMablagh.getText().toString();
        // check mablagh
        if (mablagh.equals("0") || mablagh.equals("")) {
            customTextMablagh.setError(getResources().getString(R.string.errorFishMablagh));
            isOk = false;
        }

        return isOk;
    }

    /**
     * clear error
     */
    private void clearError() {

        customTextFishBankiNumber.setError(null);
        customTextFishBankiDate.setError(null);
        customTextNameBanki.setError(null);
        customTextShomarehHesab.setError(null);
        customTextNameShobeBank.setError(null);
        customTextMablagh.setError(null);
    }


}
