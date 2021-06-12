package com.saphamrah.MVP.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.saphamrah.Adapter.invoiceSettelment.SettlementAdapter;
import com.saphamrah.Adapter.invoiceSettelment.SettlementPishDaryaftAdapter;
import com.saphamrah.BaseMVP.InvoiceSettlementMVP;
import com.saphamrah.CustomView.BottomBar;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.InvoiceSettlementPresenter;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.ConfigNoeVosolMojazeFaktorModel;
import com.saphamrah.Model.ConfigNoeVosolMojazeMoshtaryModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.PosShomarehHesabModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class InvoiceSettlementActivity extends AppCompatActivity implements InvoiceSettlementMVP.RequiredViewOps {

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, InvoiceSettlementActivity.this);
    private InvoiceSettlementMVP.PresenterOps mPresenter;
    private SettlementAdapter adapter;
    private SettlementPishDaryaftAdapter adapterPishDaryaft;
    private int from = -1;
    private DecimalFormat decimalFormatter;
    private CustomSpinner customSpinner;
    private CustomAlertDialog customAlertDialog;
    private int ccMoshtary;
    private long ccDarkhastFaktor;
    private ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModelArrayList;
    private ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModelArrayList;
    private ArrayList<String> noeVosolTitles;
    private ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModelsVosol;
    private ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModelsVosol;
    private int codeNoeVosolMoshtary;
    private LinearLayout lay_details;
    private String sourceActivity;

    // default values
    private static final int REQUEST_CODE_BARCODE_SCANNER = 100;
    private String number = "";
    private String date = "";
    private String nameBank = "";
    private int ccBank = 0;
    private String nameShobe = "";
    private String codeShobe = "";
    private String mablagh = "";
    private int ccMoshtaryHesab = 0;
    private String shomareHesab = "";
    private String sharhMoshtaryShomarehHesab = "";
    private int flagInputHesab = 0;
    private String tarikhSarResid = "";
    private int showSayyadCheckScanner = View.GONE;
    private FloatingActionButton fabAdd;


   private FloatingActionMenu fabMenu;
    private LinearLayout layShowMoreCheckInfo;
    private LinearLayout layCheckDefault;
    private RecyclerView recyclerView;
    private CardView layAddVosol;
    private LinearLayout layoutVajhNaghdInfo;
    private LinearLayout layoutPOSInfo;
    private LinearLayout layoutIranCheckInfo;
    private LinearLayout layoutFishBankiInfo;
    private LinearLayout layoutCheckInfo;
    private EditText editTextSelectNoeVosol;
    private EditText editTextMablaghMandeh;
    private EditText editTextShomareDarkhast;
    private TextView lblActivityTitle;
    // check bargashti
    private CustomTextInputLayout txtInputLayoutInvoiceCode;
    private CustomTextInputLayout editTxtNoeVosol;
    private int ccDarajeh;
    private int ccNoeMoshtary;
    private String ShomarehSanad;

    private EditText txtCost;
    private EditText txtRemain;
    private EditText editTextNoeVosol;
    private EditText editTextMablaghKol;
    //Vajh Naghd
    private EditText editTextVajhNaghdMablagh;

    //POS
    private EditText editTextPOSNumber;
    private EditText editTextPOSDate;
    private EditText editTextPOSBank;
    private EditText editTextPOSShobeBank;
    private EditText editTextPOSMablagh;

    //Iran Check
    private EditText editTextIranCheckNumber;
    private EditText editTextIranCheckMablagh;

    //Fish Banki
    private EditText editTextFishBankiNumber;
    private EditText editTextFishBankiDate;
    private EditText editTextFishBankiBank;
    private EditText editTextFishBankiShobeBank;
    private EditText editTextFishBankiMablagh;

    //Check
    private EditText editTextCheckNumber;
    private EditText editTextCheckDate;
    private EditText editTextCheckHesab;
    private EditText editTextCheckBank;
    private EditText editTextCheckHesabNumber;
    private EditText editTextCheckCodeShobe;
    private EditText editTextCheckNameShobe;
    private EditText editTextCheckMablagh;

    // tajil
    private LinearLayout lay_tajil_naghd;
    private EditText edt_naghdi_tajil;
    private EditText edt_mablagh_pas_az_kasr_naghd_tajil;
    private EditText edt_check_tajil;
    private EditText edt_mablagh_pas_az_kasr_check_tajil;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_settlement);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        fabMenu = findViewById(R.id.fabMenu);
        fabAdd = findViewById(R.id.fabAdd);


        FloatingActionButton fabInquirySayyadCheck = findViewById(R.id.fabInquirySayyadCheck);
        lblActivityTitle = findViewById(R.id.lblActivityTitle);
        layAddVosol = findViewById(R.id.layAddVosol);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(InvoiceSettlementActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(InvoiceSettlementActivity.this, 0));
        Button btnApplyAddVosol = findViewById(R.id.btnApply);
        final Button btnCancelAddVosol = findViewById(R.id.btnCancel);
        final Button btnCancelEditVosol = findViewById(R.id.btnCancelEdit);
        editTextSelectNoeVosol = findViewById(R.id.txtSelectNoeVosol);

        txtCost = findViewById(R.id.txtCost);
        txtRemain = findViewById(R.id.txtRemain);
        editTextMablaghMandeh = findViewById(R.id.txtRemain);
        txtInputLayoutInvoiceCode = findViewById(R.id.txtInputLayoutInvoiceCode);
        lay_details = findViewById(R.id.lay_details);
        LinearLayout layBottomBar = findViewById(R.id.layBottomBar);
        LinearLayout layButtons = findViewById(R.id.layButtons);
        editTextShomareDarkhast = findViewById(R.id.txtInvoiceCode);
        editTextNoeVosol = findViewById(R.id.txtNoeVosol);
        editTextMablaghKol = findViewById(R.id.txtCost);
        editTxtNoeVosol = findViewById(R.id.editTxtNoeVosol);
        //Vajh Naghd
        editTextVajhNaghdMablagh = findViewById(R.id.txtMablagh);

        //POS
        editTextPOSNumber = findViewById(R.id.txtPOSNumber);
        editTextPOSDate = findViewById(R.id.txtPOSDate);
        editTextPOSBank = findViewById(R.id.txtPOSBank);
        editTextPOSShobeBank = findViewById(R.id.txtPOSShobeBank);
        editTextPOSMablagh = findViewById(R.id.txtPOSMablagh);

        //Iran Check
        editTextIranCheckNumber = findViewById(R.id.txtIranCheckNumber);
        editTextIranCheckMablagh = findViewById(R.id.txtIranCheckMablagh);

        //Fish Banki
        editTextFishBankiNumber = findViewById(R.id.txtFishBankiNumber);
        editTextFishBankiDate = findViewById(R.id.txtFishBankiDate);
        editTextFishBankiBank = findViewById(R.id.txtFishBankiBank);
        editTextFishBankiShobeBank = findViewById(R.id.txtFishBankiShobeBank);
        editTextFishBankiMablagh = findViewById(R.id.txtFishBankiMablagh);

        //Check
        layShowMoreCheckInfo = findViewById(R.id.layMoreCheckInfo);
        layCheckDefault = findViewById(R.id.layCheckDefault);
        editTextCheckNumber = findViewById(R.id.txtCheckNumber);
        editTextCheckDate = findViewById(R.id.txtCheckDate);
        editTextCheckHesab = findViewById(R.id.txtCheckHesab);
        editTextCheckBank = findViewById(R.id.txtCheckBank);
        editTextCheckHesabNumber = findViewById(R.id.txtCheckHesabNumber);
        editTextCheckCodeShobe = findViewById(R.id.txtCheckCodeShobe);
        editTextCheckNameShobe = findViewById(R.id.txtCheckNameShobe);
        editTextCheckMablagh = findViewById(R.id.txtCheckMablagh);

        layoutVajhNaghdInfo = findViewById(R.id.layVajhNaghd);
        layoutPOSInfo = findViewById(R.id.layPOS);
        layoutIranCheckInfo = findViewById(R.id.layIranCheck);
        layoutFishBankiInfo = findViewById(R.id.layFishBanki);
        layoutCheckInfo = findViewById(R.id.layCheck);
        layAddVosol.setVisibility(View.GONE);


        // tajil

        lay_tajil_naghd = findViewById(R.id.lay_tajil);
        edt_naghdi_tajil = findViewById(R.id.edt_naghdi_tajil);
        edt_mablagh_pas_az_kasr_naghd_tajil = findViewById(R.id.edt_mablagh_pas_az_kasr_naghd_tajil);
        edt_check_tajil = findViewById(R.id.edt_check_tajil);
        edt_mablagh_pas_az_kasr_check_tajil = findViewById(R.id.edt_mablagh_pas_az_kasr_check_tajil);


        decimalFormatter = new DecimalFormat("#,###,###");
        customSpinner = new CustomSpinner();
        customAlertDialog = new CustomAlertDialog(InvoiceSettlementActivity.this);
        configNoeVosolMojazeFaktorModelArrayList = new ArrayList<>();
        noeVosolTitles = new ArrayList<>();
        dariaftPardakhtDarkhastFaktorPPCModelsVosol = new ArrayList<>();
        configNoeVosolMojazeMoshtaryModelArrayList = new ArrayList<>();
        dariaftPardakhtPPCModelsVosol = new ArrayList<>();
        codeNoeVosolMoshtary = -1;

        Intent getIntent = getIntent();

        ccMoshtary = getIntent.getIntExtra("ccMoshtary", -1);
        Log.d("invoice", "ccMoshtary : " + ccMoshtary);
        /*
         * when from == check bargashty ( ccDarkhastFaktor => ccDaryaftPardakht)
         */
        ccDarkhastFaktor = getIntent.getLongExtra("ccDarkhastFaktor", -1);
        Log.d("invoice", "ccDarkhastFaktor : " + ccDarkhastFaktor);


        sourceActivity = getIntent.getStringExtra("sourceActivity");
        sourceActivity = sourceActivity == null ? "" : sourceActivity;
        boolean getFromShared = false;
        Log.d("invoice", "sourceActivity : " + sourceActivity);

        /**
         * from 1 = TreasuryListActivity
         * from 2 = PishDaryaftActivity
         * from 3 = CheckBargashtiActivity
         * else   = VerifyRequestActivity(Sabt Faktor)
         */
        if (sourceActivity.equals("TreasuryListActivity")) {
            layBottomBar.setVisibility(View.GONE);
            layButtons.setVisibility(View.VISIBLE);
            getFromShared = false;
            from = 1;
            setRecyclerCheckBargashtyAndTreasuryList();
        } else if (sourceActivity.equals("PishDaryaftActivity")) {
            layBottomBar.setVisibility(View.GONE);
            layButtons.setVisibility(View.VISIBLE);
            lay_details.setVisibility(View.GONE);
            getFromShared = false;
            from = 2;
            setRecyclerPishDaryaft();
        } else if (sourceActivity.equals("CheckBargashtiActivity")) {
            layBottomBar.setVisibility(View.GONE);
            layButtons.setVisibility(View.VISIBLE);
            getFromShared = false;
            txtInputLayoutInvoiceCode.setHint(getResources().getString(R.string.shomareSanad));
            from = 3;
            ccDarajeh = getIntent.getIntExtra("ccDarajeh", -1);
            ccNoeMoshtary = getIntent.getIntExtra("ccNoeMoshtary", -1);
            ShomarehSanad = getIntent.getStringExtra("ShomarehSanad");
            setRecyclerCheckBargashtyAndTreasuryList();
            mPresenter.getDetailsCheckBargashti(ShomarehSanad, ccDarajeh, ccNoeMoshtary);
        } else {
            layBottomBar.setVisibility(View.VISIBLE);
            layButtons.setVisibility(View.GONE);
            new BottomBar(InvoiceSettlementActivity.this, 4, new BottomBar.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    mPresenter.checkRegisteredVosol(position);
                    Log.d("invoice", "sourceActivity position : " + position);
                }
            });
            getFromShared = true;

            setRecyclerCheckBargashtyAndTreasuryList();
        }

        mPresenter.getInfo(ccDarkhastFaktor, from);
        mPresenter.getNoeVosols(ccDarkhastFaktor, getFromShared, from, ccMoshtary);
        if (from == Constants.FROM_TREASURYLIST || from == Constants.FROM_CHECK_BARGASHTI || from == -1) {
            mPresenter.getVosols(ccDarkhastFaktor);
        } else if (from == Constants.FROM_PISH_DARYAFT) {
            mPresenter.getVosolsPishDariaft(ccMoshtary);
        }

        Log.i("showSaayadCheckI", "BeforeGetConfig");
        mPresenter.getConfig();
        Log.i("showSaayadCheckI", "AfterGetConfig");


        editTextSelectNoeVosol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectNoeVosolAlert();
            }
        });

        editTextSelectNoeVosol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeDrawableLeftTint(editTextSelectNoeVosol, hasFocus);
                if (hasFocus) {

                    showSelectNoeVosolAlert();
                }
            }
        });


        editTextPOSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerAlert(editTextPOSDate);
            }
        });

        editTextPOSDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerAlert(editTextPOSDate);
                }
            }
        });


        editTextFishBankiDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerAlert(editTextFishBankiDate);
            }
        });

        editTextFishBankiDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerAlert(editTextFishBankiDate);
                }
            }
        });


        editTextCheckDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerAlert(editTextCheckDate);
            }
        });

        editTextCheckDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerAlert(editTextCheckDate);
                }
            }
        });


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);

                boolean haveNotIsTaeedShodeAndIsSend = checkHaveNotIsTaeedShodehAndIsSend();
                if (haveNotIsTaeedShodeAndIsSend) {
                    customAlertDialog.showMessageAlert(InvoiceSettlementActivity.this, false, "", getResources().getString(R.string.isTaeedShode), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
                } else {
                    layAddVosol.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    clearValues();
                }

            }
        });






        fabInquirySayyadCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InvoiceSettlementActivity.this, InquirySayyadCheckActivity.class));
            }
        });


        btnApplyAddVosol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mablaghMandeh = editTextMablaghMandeh.getText().toString().trim().replace(",", "");
                mPresenter.checkRemainCost(codeNoeVosolMoshtary, mablaghMandeh, from);
            }
        });


        btnCancelAddVosol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                layAddVosol.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });


        btnCancelEditVosol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                InvoiceSettlementActivity.this.finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (from == Constants.FROM_PISH_DARYAFT) {
            lblActivityTitle.setText(R.string.pishDaryaft);
            lay_tajil_naghd.setVisibility(View.GONE);
        } else if (from == Constants.FROM_CHECK_BARGASHTI) {
            lblActivityTitle.setText(R.string.checkBargashti);
            lay_tajil_naghd.setVisibility(View.GONE);
        }

//        if (from == Constants.FROM_TREASURYLIST || from == Constants.FROM_CHECK_BARGASHTI || from == -1)
//
//            mPresenter.callTajil(ccDarkhastFaktor, 0);
    }

    @Override
    public Context getAppContext() {
        return InvoiceSettlementActivity.this;
    }

    @Override
    public void showSayyadCheckScanner(int visibility) {
        showSayyadCheckScanner = visibility;
    }

    @Override
    public void openBarkhordAvalieActivity() {
        Intent intent = new Intent(InvoiceSettlementActivity.this, BarkhordAvalieForoshandehActivity.class);
        intent.putExtra("ccMoshtary", ccMoshtary);
        startActivity(intent);
        InvoiceSettlementActivity.this.finish();
    }

    @Override
    public void openEmzaMoshtaryActivity() {
        Intent intent = new Intent(InvoiceSettlementActivity.this, VerifyCustomerRequestActivity.class);
        intent.putExtra("ccMoshtary", ccMoshtary);
        startActivity(intent);
        InvoiceSettlementActivity.this.finish();
    }

    @Override
    public void onGetInfo(String shomareDarkhast, int ccNoeVosol, String noeVosol, String cost, String remainCost) {


        if (from != Constants.FROM_CHECK_BARGASHTI) {
            editTextMablaghMandeh.setText(remainCost);
            editTextShomareDarkhast.setText(String.valueOf(shomareDarkhast));
            editTextNoeVosol.setText(noeVosol);
            editTextMablaghKol.setText(cost);
        }

    }

    @Override
    public void onGetVosols(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels) {
        dariaftPardakhtDarkhastFaktorPPCModelsVosol.clear();
        dariaftPardakhtDarkhastFaktorPPCModelsVosol.addAll(dariaftPardakhtDarkhastFaktorPPCModels);
        setRecyclerCheckBargashtyAndTreasuryList();
//        adapter.notifyDataSetChanged();
    }

    /**
     * check IsTaeedShodeh and isSend for set vosol in check Bargashty
     */

    private boolean checkHaveNotIsTaeedShodehAndIsSend() {
        boolean haveNot = false;

        if (from == Constants.FROM_CHECK_BARGASHTI) {
            for (int i = 0; i < dariaftPardakhtDarkhastFaktorPPCModelsVosol.size(); i++) {
                if (dariaftPardakhtDarkhastFaktorPPCModelsVosol.get(i).getIsTaeedShodeh() == 0 && dariaftPardakhtDarkhastFaktorPPCModelsVosol.get(i).getExtraProp_IsSend()==1) {
                    haveNot = true;
                    break;
                }
            }
        }
        return haveNot;
    }

    /**
     * get vosols from pish daryaft
     *
     * @param dariaftPardakhtPPCModels
     */
    @Override
    public void onGetVosolsPishDaryaft(ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels) {
        dariaftPardakhtPPCModelsVosol.clear();
        dariaftPardakhtPPCModelsVosol.addAll(dariaftPardakhtPPCModels);
        setRecyclerPishDaryaft();
    }

    /**
     * get tajil details for first
     *
     * @param mablaghTajil_Naghd
     * @param mandehFaktorPasAzTajil_Naghd
     * @param mablaghTajil_Check
     * @param mandehFaktorPasAzTajil_Check
     */
    @Override
    public void oncallTajil(long mablaghTajil_Naghd, long mandehFaktorPasAzTajil_Naghd, long mablaghTajil_Check, long mandehFaktorPasAzTajil_Check, boolean canGetTajil) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (canGetTajil) {
            edt_naghdi_tajil.setText(formatter.format(mablaghTajil_Naghd));
            edt_check_tajil.setText(formatter.format(mablaghTajil_Check));
            edt_mablagh_pas_az_kasr_naghd_tajil.setText(formatter.format(mandehFaktorPasAzTajil_Naghd));
            edt_mablagh_pas_az_kasr_check_tajil.setText(formatter.format(mandehFaktorPasAzTajil_Check));
        }
    }

    @Override
    public void onVisibilityLayoutTajil(boolean visible) {
        if (visible){
            lay_tajil_naghd.setVisibility(View.VISIBLE);
        } else {
            lay_tajil_naghd.setVisibility(View.GONE);
        }
    }

    /**
     * when mablagh mandeh is zero ("0") we can not add
     */
    @Override
    public void canNotAdd() {
        customAlertDialog.showToast(InvoiceSettlementActivity.this, getResources().getString(R.string.canNotAddMoney), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    /**
     * get code noe vosol
     *
     * @param configNoeVosolMojazeFaktorModels
     */
    @Override
    public void onGetNoeVosols(ArrayList<ConfigNoeVosolMojazeFaktorModel> configNoeVosolMojazeFaktorModels) {
        for (ConfigNoeVosolMojazeFaktorModel model : configNoeVosolMojazeFaktorModels) {
            noeVosolTitles.add(model.getTxtNoeVosol());
            configNoeVosolMojazeFaktorModelArrayList.add(model);
        }
    }


    /**
     * get noe vosol for checkBargashti
     *
     * @param configNoeVosolMojazeMoshtaryModels
     */
    @Override
    public void onGetNoeVosolsMojazMoshtary(ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels) {
        for (ConfigNoeVosolMojazeMoshtaryModel model : configNoeVosolMojazeMoshtaryModels) {
            noeVosolTitles.add(model.getTxtNoeVosol());
            configNoeVosolMojazeMoshtaryModelArrayList.add(model);
        }
    }


    @Override
    public void showVosolNaghdInfo(int codeNoevosol) {
        //codeNoeVosolMoshtary = codeNoevosol;

        layoutVajhNaghdInfo.setVisibility(View.VISIBLE);
        layoutPOSInfo.setVisibility(View.GONE);
        layoutIranCheckInfo.setVisibility(View.GONE);
        layoutFishBankiInfo.setVisibility(View.GONE);
        layoutCheckInfo.setVisibility(View.GONE);

        editTextVajhNaghdMablagh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCommaToPrice(editTextVajhNaghdMablagh, s, this);
            }
        });
    }

    @Override
    public void showVosolPOSInfo(final ArrayList<PosShomarehHesabModel> posShomarehHesabModels, int codeNoevosol) {
        //codeNoeVosolMoshtary = codeNoevosol;

        layoutVajhNaghdInfo.setVisibility(View.GONE);
        layoutPOSInfo.setVisibility(View.VISIBLE);
        layoutIranCheckInfo.setVisibility(View.GONE);
        layoutFishBankiInfo.setVisibility(View.GONE);
        layoutCheckInfo.setVisibility(View.GONE);

        final ArrayList<String> arrayListTitles = new ArrayList<>();

        if (posShomarehHesabModels.size() > 0) {
            Log.d("codeShobe", "code shobe : " + posShomarehHesabModels.get(0).getCodeShobeh());
            nameBank = posShomarehHesabModels.get(0).getNameBank();
            ccBank = posShomarehHesabModels.get(0).getCcBank();
            nameShobe = posShomarehHesabModels.get(0).getNameShobeh();
            codeShobe = posShomarehHesabModels.get(0).getCodeShobeh();
            ccMoshtaryHesab = posShomarehHesabModels.get(0).getCcShomarehHesab();
//            editTextPOSBank.setText(posShomarehHesabModels.get(0).getNameBank());
//            editTextPOSShobeBank.setText(String.format("%1$s - %2$s", posShomarehHesabModels.get(0).getNameShobeh(), posShomarehHesabModels.get(0).getCodeShobeh()));
        }

        for (PosShomarehHesabModel model : posShomarehHesabModels) {
            arrayListTitles.add(model.getNameBank());
        }

        editTextPOSBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSpinner.showSpinner(InvoiceSettlementActivity.this, arrayListTitles, new CustomSpinnerResponse() {
                    @Override
                    public void onApplySingleSelection(int selectedIndex) {
                        Log.d("Invoice", "posShomarehHesabModels:" + posShomarehHesabModels.toString() + "--- selected" + posShomarehHesabModels.get(selectedIndex).toString());
                        if (posShomarehHesabModels != null && posShomarehHesabModels.size() > 0) {
                            Log.d("codeShobe", "code shobe : " + posShomarehHesabModels.get(selectedIndex).getCodeShobeh());
                            nameBank = posShomarehHesabModels.get(selectedIndex).getNameBank();
                            ccBank = posShomarehHesabModels.get(selectedIndex).getCcBank();
                            nameShobe = posShomarehHesabModels.get(selectedIndex).getNameShobeh();
                            codeShobe = posShomarehHesabModels.get(selectedIndex).getCodeShobeh();
                            ccMoshtaryHesab = posShomarehHesabModels.get(selectedIndex).getCcShomarehHesab();
                            editTextPOSBank.setText(posShomarehHesabModels.get(selectedIndex).getNameBank());
                            editTextPOSShobeBank.setText(String.format("%1$s - %2$s", posShomarehHesabModels.get(selectedIndex).getNameShobeh(), posShomarehHesabModels.get(selectedIndex).getCodeShobeh()));
                        }
                    }

                    @Override
                    public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                    }
                });
            }
        });

        editTextPOSBank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customSpinner.showSpinner(InvoiceSettlementActivity.this, arrayListTitles, new CustomSpinnerResponse() {
                        @Override
                        public void onApplySingleSelection(int selectedIndex) {
                            if (posShomarehHesabModels != null && posShomarehHesabModels.size() > 0) {
                                Log.d("codeShobe", "code shobe : " + posShomarehHesabModels.get(selectedIndex).getCodeShobeh());
                                nameBank = posShomarehHesabModels.get(selectedIndex).getNameBank();
                                ccBank = posShomarehHesabModels.get(selectedIndex).getCcBank();
                                nameShobe = posShomarehHesabModels.get(selectedIndex).getNameShobeh();
                                codeShobe = posShomarehHesabModels.get(selectedIndex).getCodeShobeh();
                                ccMoshtaryHesab = posShomarehHesabModels.get(selectedIndex).getCcShomarehHesab();
                                editTextPOSBank.setText(posShomarehHesabModels.get(selectedIndex).getNameBank());
                                editTextPOSShobeBank.setText(String.format("%1$s - %2$s", posShomarehHesabModels.get(selectedIndex).getNameShobeh(), posShomarehHesabModels.get(selectedIndex).getCodeShobeh()));
                            }
                        }

                        @Override
                        public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                        }
                    });
                }
            }
        });

        editTextPOSMablagh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCommaToPrice(editTextPOSMablagh, s, this);
            }
        });
    }

    @Override
    public void showVosolIranCheckInfo(final ArrayList<ParameterChildModel> childParameterModels, int codeNoevosol) {
        //codeNoeVosolMoshtary = codeNoevosol;

        layoutVajhNaghdInfo.setVisibility(View.GONE);
        layoutPOSInfo.setVisibility(View.GONE);
        layoutIranCheckInfo.setVisibility(View.VISIBLE);
        layoutFishBankiInfo.setVisibility(View.GONE);
        layoutCheckInfo.setVisibility(View.GONE);

        final ArrayList<String> arrayListTitles = new ArrayList<>();
        for (ParameterChildModel model : childParameterModels) {
            arrayListTitles.add(model.getTxt());
        }
        if (arrayListTitles.size() > 0) {
            mablagh = childParameterModels.get(0).getValue();
            editTextIranCheckMablagh.setText(childParameterModels.get(0).getTxt());
        }

        editTextIranCheckMablagh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSpinner.showSpinner(InvoiceSettlementActivity.this, arrayListTitles, new CustomSpinnerResponse() {
                    @Override
                    public void onApplySingleSelection(int selectedIndex) {
                        mablagh = childParameterModels.get(selectedIndex).getValue();
                        editTextIranCheckMablagh.setText(childParameterModels.get(selectedIndex).getTxt());
                    }

                    @Override
                    public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                    }
                });
            }
        });

        editTextIranCheckMablagh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customSpinner.showSpinner(InvoiceSettlementActivity.this, arrayListTitles, new CustomSpinnerResponse() {
                        @Override
                        public void onApplySingleSelection(int selectedIndex) {
                            mablagh = childParameterModels.get(selectedIndex).getValue();
                            editTextIranCheckMablagh.setText(childParameterModels.get(selectedIndex).getTxt());
                        }

                        @Override
                        public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                        }
                    });
                }
            }
        });

    }

    @Override
    public void showVosolFishBankiInfo(final ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels, int codeNoevosol) {
        //codeNoeVosolMoshtary = codeNoevosol;

        layoutVajhNaghdInfo.setVisibility(View.GONE);
        layoutPOSInfo.setVisibility(View.GONE);
        layoutIranCheckInfo.setVisibility(View.GONE);
        layoutFishBankiInfo.setVisibility(View.VISIBLE);
        layoutCheckInfo.setVisibility(View.GONE);
        final ArrayList<String> arrayListTitles = new ArrayList<>();

        if (markazShomarehHesabModels.size() > 0) {
//            editTextFishBankiBank.setText(markazShomarehHesabModels.get(0).getNameBank());
//            editTextFishBankiShobeBank.setText(String.format("%1$s - %2$s", markazShomarehHesabModels.get(0).getNameShobeh(), markazShomarehHesabModels.get(0).getCodeShobeh()));

            for (MarkazShomarehHesabModel model : markazShomarehHesabModels) {
                arrayListTitles.add(model.getNameBank());
            }
        }

        editTextFishBankiBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSpinner.showSpinner(InvoiceSettlementActivity.this, arrayListTitles, new CustomSpinnerResponse() {
                    @Override
                    public void onApplySingleSelection(int selectedIndex) {
                        if (markazShomarehHesabModels.size() > 0) {
                            nameBank = markazShomarehHesabModels.get(selectedIndex).getNameBank();
                            ccBank = markazShomarehHesabModels.get(selectedIndex).getCcBank();
                            nameShobe = markazShomarehHesabModels.get(selectedIndex).getNameShobeh();
                            codeShobe = markazShomarehHesabModels.get(selectedIndex).getCodeShobeh();
                            shomareHesab = markazShomarehHesabModels.get(selectedIndex).getShomarehHesab();
                            ccMoshtaryHesab = markazShomarehHesabModels.get(selectedIndex).getCcShomarehHesab();
                            editTextFishBankiBank.setText(markazShomarehHesabModels.get(selectedIndex).getNameBank());
                            editTextFishBankiShobeBank.setText(String.format("%1$s - %2$s", markazShomarehHesabModels.get(selectedIndex).getNameShobeh(), markazShomarehHesabModels.get(selectedIndex).getCodeShobeh()));
                        }
                    }

                    @Override
                    public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                    }
                });
            }
        });

        editTextFishBankiBank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customSpinner.showSpinner(InvoiceSettlementActivity.this, arrayListTitles, new CustomSpinnerResponse() {
                        @Override
                        public void onApplySingleSelection(int selectedIndex) {
                            if (markazShomarehHesabModels.size() > 0) {
                                nameBank = markazShomarehHesabModels.get(selectedIndex).getNameBank();
                                ccBank = markazShomarehHesabModels.get(selectedIndex).getCcBank();
                                nameShobe = markazShomarehHesabModels.get(selectedIndex).getNameShobeh();
                                codeShobe = markazShomarehHesabModels.get(selectedIndex).getCodeShobeh();
                                shomareHesab = markazShomarehHesabModels.get(selectedIndex).getShomarehHesab();
                                ccMoshtaryHesab = markazShomarehHesabModels.get(selectedIndex).getCcShomarehHesab();
                                editTextFishBankiBank.setText(markazShomarehHesabModels.get(selectedIndex).getNameBank());
                                editTextFishBankiShobeBank.setText(String.format("%1$s - %2$s", markazShomarehHesabModels.get(selectedIndex).getNameShobeh(), markazShomarehHesabModels.get(selectedIndex).getCodeShobeh()));
                            }
                        }

                        @Override
                        public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                        }
                    });
                }
            }
        });


        editTextFishBankiMablagh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCommaToPrice(editTextFishBankiMablagh, s, this);
            }
        });

    }

    @Override
    public void showVosolCheckInfo(final ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels, final ArrayList<BankModel> bankModels, int codeNoevosol, String tarikhSarResidForCheckShamsi, String tarikhSarResidForCheckGregorian) {
        //codeNoeVosolMoshtary = codeNoevosol;

        ImageView imgExpandHesabInfo = findViewById(R.id.imgShowInputCheck);
        ImageView imgBarcodeScanner = findViewById(R.id.imgBarcodeScanner);
        imgBarcodeScanner.setVisibility(showSayyadCheckScanner);

        final ArrayList<String> shomareHesabTitles = new ArrayList<>();
        final ArrayList<String> bankTitles = new ArrayList<>();

        for (MoshtaryShomarehHesabModel model : moshtaryShomarehHesabModels) {

            shomareHesabTitles.add(String.format("%1$s - %2$s - %3$s", model.getNameMoshtary(), model.getNameBank()+" - "+
                    model.getShomarehHesab(), model.getNameNoeHesab()));
        }
        for (BankModel model : bankModels) {
            bankTitles.add(model.getNameBank());
        }

        layoutVajhNaghdInfo.setVisibility(View.GONE);
        layoutPOSInfo.setVisibility(View.GONE);
        layoutIranCheckInfo.setVisibility(View.GONE);
        layoutFishBankiInfo.setVisibility(View.GONE);
        layoutCheckInfo.setVisibility(View.VISIBLE);
        layShowMoreCheckInfo.setVisibility(View.GONE);
        layCheckDefault.setVisibility(View.VISIBLE);

        editTextCheckDate.setText(tarikhSarResidForCheckShamsi);
        tarikhSarResid = tarikhSarResidForCheckGregorian;

        imgExpandHesabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layShowMoreCheckInfo.setVisibility(View.VISIBLE);
                layCheckDefault.setVisibility(View.GONE);
                flagInputHesab = 1;
            }
        });


        imgBarcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(InvoiceSettlementActivity.this, BarcodeScannerActivity.class), REQUEST_CODE_BARCODE_SCANNER);
            }
        });

        editTextCheckBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSpinner.showSpinner(InvoiceSettlementActivity.this, bankTitles, new CustomSpinnerResponse() {
                    @Override
                    public void onApplySingleSelection(int selectedIndex) {
                        if (bankTitles.size() > 0) {
                            Log.d("InvoiceSettlementActiv", "bankModels" + bankModels.toString() + " - selected : " + nameBank + "-" + ccBank);
                            nameBank = bankModels.get(selectedIndex).getNameBank();
                            ccBank = bankModels.get(selectedIndex).getCcBank();
                            editTextCheckBank.setText(bankTitles.get(selectedIndex));
                            //flagInputHesab = 1;
                        }
                    }

                    @Override
                    public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                    }
                });
            }
        });

        editTextCheckBank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customSpinner.showSpinner(InvoiceSettlementActivity.this, bankTitles, new CustomSpinnerResponse() {
                        @Override
                        public void onApplySingleSelection(int selectedIndex) {
                            if (bankTitles.size() > 0) {
                                Log.d("InvoiceSettlementActiv", "bankModels" + bankModels.toString() + " - selected : " + nameBank + "-" + ccBank);
                                nameBank = bankModels.get(selectedIndex).getNameBank();
                                ccBank = bankModels.get(selectedIndex).getCcBank();
                                editTextCheckBank.setText(bankTitles.get(selectedIndex));
                                //flagInputHesab = 0;
                            }
                        }

                        @Override
                        public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                        }
                    });
                }
            }
        });


        editTextCheckHesab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSpinner.showSpinner(InvoiceSettlementActivity.this, shomareHesabTitles, new CustomSpinnerResponse() {
                    @Override
                    public void onApplySingleSelection(int selectedIndex) {
                        if (shomareHesabTitles.size() > 0) {
                            Log.d("InvoiceSettlementActiv", "moshtaryShomarehHesabModels" + moshtaryShomarehHesabModels.toString() + " - selected : " + moshtaryShomarehHesabModels.get(selectedIndex).getCcMoshtaryShomarehHesab());
                            ccMoshtaryHesab = moshtaryShomarehHesabModels.get(selectedIndex).getCcMoshtaryShomarehHesab();
                            nameBank = moshtaryShomarehHesabModels.get(selectedIndex).getNameBank();
                            ccBank = moshtaryShomarehHesabModels.get(selectedIndex).getCcBank();
                            nameShobe = moshtaryShomarehHesabModels.get(selectedIndex).getNameShobeh();
                            codeShobe = moshtaryShomarehHesabModels.get(selectedIndex).getCodeShobeh();
                            shomareHesab = moshtaryShomarehHesabModels.get(selectedIndex).getShomarehHesab();
                            Log.d("InvoiceSettlementActiv", "ccMoshtaryHesab : " + ccMoshtaryHesab + " -  " + nameBank + " -  " + ccBank + " -  " + nameShobe + " -  " + codeShobe + " -  " + shomareHesab);
                            editTextCheckHesab.setText(shomareHesabTitles.get(selectedIndex));
                        }
                    }

                    @Override
                    public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                    }
                });
            }
        });

        editTextCheckHesab.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customSpinner.showSpinner(InvoiceSettlementActivity.this, shomareHesabTitles, new CustomSpinnerResponse() {
                        @Override
                        public void onApplySingleSelection(int selectedIndex) {
                            if (shomareHesabTitles.size() > 0) {
                                Log.d("InvoiceSettlementActiv", "moshtaryShomarehHesabModels" + moshtaryShomarehHesabModels.toString() + " - selected : " + moshtaryShomarehHesabModels.get(selectedIndex).getCcMoshtaryShomarehHesab());
                                ccMoshtaryHesab = moshtaryShomarehHesabModels.get(selectedIndex).getCcMoshtaryShomarehHesab();
                                nameBank = moshtaryShomarehHesabModels.get(selectedIndex).getNameBank();
                                ccBank = moshtaryShomarehHesabModels.get(selectedIndex).getCcBank();
                                nameShobe = moshtaryShomarehHesabModels.get(selectedIndex).getNameShobeh();
                                codeShobe = moshtaryShomarehHesabModels.get(selectedIndex).getCodeShobeh();
                                shomareHesab = moshtaryShomarehHesabModels.get(selectedIndex).getShomarehHesab();
                                Log.d("InvoiceSettlementActiv", "ccMoshtaryHesab : " + ccMoshtaryHesab + " -  " + nameBank + " -  " + ccBank + " -  " + nameShobe + " -  " + codeShobe + " -  " + shomareHesab);
                                Log.d("InvoiceSettlementActiv", "ccMoshtaryHesab : " + ccMoshtaryHesab + shomareHesabTitles.get(selectedIndex));
                                editTextCheckHesab.setText(shomareHesabTitles.get(selectedIndex));
                            }
                        }

                        @Override
                        public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                        }
                    });
                }
            }
        });

        editTextCheckMablagh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addCommaToPrice(editTextCheckMablagh, s, this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BARCODE_SCANNER) {
            if (resultCode == RESULT_OK) {
                setDataFromScannerToView(data);
            }
        }
    }

    private void setDataFromScannerToView(Intent data) {
        layShowMoreCheckInfo.setVisibility(View.VISIBLE);
        layCheckDefault.setVisibility(View.GONE);
        flagInputHesab = 1;
        if (data != null) {
            String scannerData = data.getStringExtra("data");
            mPresenter.checkDataFromScanner(scannerData);
        } else {
            customAlertDialog.showToast(InvoiceSettlementActivity.this, getString(R.string.notFoundData), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void showDataOfCheckFromScanner(String checkHesabNumber, String checkCodeShobe, String sayyadId) {
        editTextCheckHesabNumber.setText(checkHesabNumber);
        editTextCheckCodeShobe.setText(checkCodeShobe);
        editTextCheckNumber.setFocusable(true);
        editTextCheckNumber.setFocusableInTouchMode(true);
        editTextCheckNumber.findFocus();
        editTextCheckNumber.requestFocus();
    }

    @Override
    public void onGetBank(BankModel bankModel) {
        nameBank = bankModel.getNameBank();
        ccBank = bankModel.getCcBank();
        editTextCheckBank.setText(bankModel.getNameBank());
    }

    @Override
    public void showVosolResidInfo(int codeNoevosol) {
        //codeNoeVosolMoshtary = codeNoevosol;

        layoutVajhNaghdInfo.setVisibility(View.GONE);
        layoutPOSInfo.setVisibility(View.GONE);
        layoutIranCheckInfo.setVisibility(View.GONE);
        layoutFishBankiInfo.setVisibility(View.GONE);
        layoutCheckInfo.setVisibility(View.GONE);
    }

    @Override
    public void showZeroRemainCostAlert() {
        customAlertDialog.showLogMessageAlert(InvoiceSettlementActivity.this, false, "", getResources().getString(R.string.warningZeroRemainCost), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                prepareDataForCheck();
            }
        });
    }

    @Override
    public void onSuccessCheckRemainCost() {
        prepareDataForCheck();
    }


    private void prepareDataForCheck() {
        String strCodeNoeVosolMoshtary = String.valueOf(codeNoeVosolMoshtary);

        if (strCodeNoeVosolMoshtary.equals(Constants.VALUE_VAJH_NAGHD())) {
            mablagh = editTextVajhNaghdMablagh.getText().toString().trim();
            number = "";
            date = "";
            nameBank = "";
            ccBank = 0;
            nameShobe = "";
            codeShobe = "";
            ccMoshtaryHesab = 0;
            shomareHesab = "";
            sharhMoshtaryShomarehHesab = "";
            flagInputHesab = 0;
        } else if (strCodeNoeVosolMoshtary.equals(Constants.VALUE_POS())) {
            mablagh = editTextPOSMablagh.getText().toString().trim();
            number = editTextPOSNumber.getText().toString().trim();
            date = editTextPOSDate.getText().toString();
            //nameBank = value of this variable set when user select from spinner
            //ccBank = value of this variable set when user select from spinner
            //nameShobe = value of this variable set when user select from spinner
            //codeShobe = value of this variable set when user select from spinner
            //ccMoshtaryHesab = value of this variable set when user select from spinner;
            shomareHesab = "";
            sharhMoshtaryShomarehHesab = "";
            flagInputHesab = 0;
        } else if (strCodeNoeVosolMoshtary.equals(Constants.VALUE_IRANCHECK())) {
            //mablagh = value of this variable set when user select from spinner
            number = editTextIranCheckNumber.getText().toString().trim();
            date = "";
            nameBank = "";
            ccBank = 0;
            nameShobe = "";
            codeShobe = "";
            ccMoshtaryHesab = 0;
            shomareHesab = "";
            sharhMoshtaryShomarehHesab = "";
            flagInputHesab = 0;
        } else if (strCodeNoeVosolMoshtary.equals(Constants.VALUE_FISH_BANKI())) {
            mablagh = editTextFishBankiMablagh.getText().toString().trim();
            number = editTextFishBankiNumber.getText().toString().trim();
            date = editTextFishBankiDate.getText().toString().trim();
            //nameBank = value of this variable set when user select from spinner
            //ccBank = value of this variable set when user select from spinner
            //nameShobe = value of this variable set when user select from spinner
            //codeShobe = value of this variable set when user select from spinner
            //ccMoshtaryHesab = 0; value of this variable set when user select from spinner
            //shomareHesab = ""; value of this variable set when user select from spinner
            sharhMoshtaryShomarehHesab = "";
            flagInputHesab = 0;
        } else if (strCodeNoeVosolMoshtary.equals(Constants.VALUE_CHECK())) {
            mablagh = editTextCheckMablagh.getText().toString().trim();
            number = editTextCheckNumber.getText().toString().trim();
            date = editTextCheckDate.getText().toString().trim();
            Log.d("invoice", "flagInputHesab : " + flagInputHesab);
            Log.d("invoice", "shomareHesab : " + shomareHesab);
            if (flagInputHesab == 0) {
                //ccMoshtaryHesab = value of this variable set when user select from spinner
            } else if (flagInputHesab == 1) {
                //nameBank = value of this variable set when user select from spinner
                //ccBank = value of this variable set when user select from spinner
                nameShobe = editTextCheckNameShobe.getText().toString().trim();
                codeShobe = editTextCheckCodeShobe.getText().toString().trim();
                shomareHesab = editTextCheckHesabNumber.getText().toString().trim();
                sharhMoshtaryShomarehHesab = "";
            }
        } else if (strCodeNoeVosolMoshtary.equals(Constants.VALUE_RESID())) {
            mablagh = "0";
            number = "";
            date = "";
            nameBank = "";
            ccBank = 0;
            nameShobe = "";
            codeShobe = "";
            ccMoshtaryHesab = 0;
            shomareHesab = "";
            sharhMoshtaryShomarehHesab = "";
            flagInputHesab = 0;
        }

        try {
            Log.d("InvoiceSettlementActiv", "code shobe : " + codeShobe);
            Log.d("InvoiceSettlementActiv", "ccMoshtaryHesab : " + ccMoshtaryHesab);
            Log.d("InvoiceSettlementActiv", "shomareHesab : " + shomareHesab);
            DariaftPardakhtPPCModel dariaftPardakhtPPCModel = new DariaftPardakhtPPCModel();
            dariaftPardakhtPPCModel.setMablagh(Double.parseDouble(mablagh.trim().replace(",", "")));
            dariaftPardakhtPPCModel.setShomarehSanad(number);
            dariaftPardakhtPPCModel.setTarikhSanadShamsi(date);
            dariaftPardakhtPPCModel.setNameBankSanad(nameBank);
            dariaftPardakhtPPCModel.setCcBankSanad(ccBank);
            dariaftPardakhtPPCModel.setNameShobehSanad(nameShobe);
            dariaftPardakhtPPCModel.setCodeShobehSanad(codeShobe.replace("\\r\\n", "").trim());
            dariaftPardakhtPPCModel.setCcShomarehHesab(ccMoshtaryHesab);
            dariaftPardakhtPPCModel.setShomarehHesabSanad(shomareHesab);
            dariaftPardakhtPPCModel.setSharhShomarehHesab(sharhMoshtaryShomarehHesab);

            if (from == Constants.FROM_TREASURYLIST || from == -1) {
                mPresenter.checkInsert(ccMoshtary, ccDarkhastFaktor, codeNoeVosolMoshtary, flagInputHesab, editTextMablaghMandeh.getText().toString().trim().replace(",", ""), dariaftPardakhtPPCModel, configNoeVosolMojazeFaktorModelArrayList);
            } else if (from == Constants.FROM_CHECK_BARGASHTI) {
                mPresenter.checkInsertCheckBargashty(ccMoshtary, ccDarkhastFaktor, codeNoeVosolMoshtary, flagInputHesab, editTextMablaghMandeh.getText().toString().trim().replace(",", ""), dariaftPardakhtPPCModel, configNoeVosolMojazeMoshtaryModelArrayList);
            } else if (from == Constants.FROM_PISH_DARYAFT) {
                mPresenter.checkInsertCheckPishDrayaft(ccMoshtary, codeNoeVosolMoshtary, flagInputHesab, "0", dariaftPardakhtPPCModel, configNoeVosolMojazeMoshtaryModelArrayList);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            showToast(R.string.errorInputMablagh, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(InvoiceSettlementActivity.this, getResources().getString(resId), messageType, duration);
    }


    @Override
    public void showAlert(int resId, int messageType) {
        customAlertDialog.showMessageAlert(InvoiceSettlementActivity.this, false, "", getResources().getString(resId), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void showAlert(String text, int messageType) {
        customAlertDialog.showMessageAlert(InvoiceSettlementActivity.this, false, "", text, messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void onSuccessInsert(long mablaghMandeFaktor) {
        editTextMablaghMandeh.setText(decimalFormatter.format(mablaghMandeFaktor));
        recyclerView.setVisibility(View.VISIBLE);
        layAddVosol.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessRemove(int position, long mablaghMandeh) {
        showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());

//        if (from == Constants.FROM_PISH_DARYAFT) {
//            dariaftPardakhtPPCModelsVosol.remove(position);
//            adapterPishDaryaft.notifyDataSetChanged();
//        } else {
//            dariaftPardakhtDarkhastFaktorPPCModelsVosol.remove(position);
//            adapter.notifyItemRemoved(position);
//            adapter.notifyItemRangeChanged(position, dariaftPardakhtDarkhastFaktorPPCModelsVosol.size());
//            adapter.notifyDataSetChanged();
//            editTextMablaghMandeh.setText(decimalFormatter.format(mablaghMandeh));
//        }
        if (from == Constants.FROM_TREASURYLIST || from == Constants.FROM_CHECK_BARGASHTI || from == -1) {
            //mPresenter.callTajil(ccDarkhastFaktor, 0);
            mPresenter.getVosols(ccDarkhastFaktor);
            editTextMablaghMandeh.setText(decimalFormatter.format(mablaghMandeh));
        } else {
            mPresenter.getVosolsPishDariaft(ccMoshtary);
        }



    }

    /**
     * get details check bargashti
     */
    @Override
    public void onGetDetailsCheckBargashti(ArrayList<BargashtyModel> bargashtyModels) {
        if(bargashtyModels.size()>0) {
            editTextShomareDarkhast.setText(String.valueOf(bargashtyModels.get(0).getShomarehSanad()));
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            txtCost.setText(String.valueOf(formatter.format(bargashtyModels.get(0).getMablagh())));
            txtRemain.setText(String.valueOf(formatter.format(bargashtyModels.get(0).getMablaghMandeh())));
            editTxtNoeVosol.setVisibility(View.GONE);
        }
    }


    private void clearValues() {
        //Vajh Naghd
        editTextVajhNaghdMablagh.setText(editTextMablaghMandeh.getText().toString());

        //POS
        editTextPOSNumber.setText("");
        editTextPOSDate.setText("");
        editTextPOSBank.setText("");
        editTextPOSShobeBank.setText("");
        editTextPOSMablagh.setText(editTextMablaghMandeh.getText().toString());

        //Iran Check
        editTextIranCheckNumber.setText("");
        editTextIranCheckMablagh.setText("");

        //Fish Banki
        editTextFishBankiNumber.setText("");
        editTextFishBankiDate.setText("");
        editTextFishBankiBank.setText("");
        editTextFishBankiShobeBank.setText("");
        editTextFishBankiMablagh.setText(editTextMablaghMandeh.getText().toString());

        //Check
        editTextCheckNumber.setText("");
        editTextCheckDate.setText("");
        editTextCheckHesab.setText("");
        editTextCheckBank.setText("");
        editTextCheckHesabNumber.setText("");
        editTextCheckCodeShobe.setText("");
        editTextCheckNameShobe.setText("");
        editTextCheckMablagh.setText(editTextMablaghMandeh.getText().toString());

        number = "";
        date = "";
        nameBank = "";
        ccBank = 0;
        nameShobe = "";
        codeShobe = "";
        mablagh = "";
        ccMoshtaryHesab = 0;
        shomareHesab = "";
        flagInputHesab = 0;
    }

    /**
     * if use check bargashti we use model ConfigNoeVosolMojazMoshtaryModel
     * if use other we use model CodeNoeVosolModel
     */
    private void showSelectNoeVosolAlert() {
        clearValues();
        customSpinner.showSpinner(InvoiceSettlementActivity.this, noeVosolTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                if (noeVosolTitles.size() > 0) {
                    if (from == Constants.FROM_CHECK_BARGASHTI) {
                        editTextSelectNoeVosol.setText(noeVosolTitles.get(selectedIndex));
                        double mablaghMandeh = 0;

                        mablaghMandeh = Double.parseDouble(editTextMablaghMandeh.getText().toString().trim().replace(",", ""));

                        codeNoeVosolMoshtary = configNoeVosolMojazeMoshtaryModelArrayList.get(selectedIndex).getCodeNoeVosol_Tablet();
                        mPresenter.checkSelectedNoeVosol(String.valueOf(codeNoeVosolMoshtary), ccMoshtary, ccDarkhastFaktor, mablaghMandeh);
                        Log.d("noeVosol", configNoeVosolMojazeMoshtaryModelArrayList.get(selectedIndex).toString());
                    } else if (from == Constants.FROM_TREASURYLIST) {
                        editTextSelectNoeVosol.setText(noeVosolTitles.get(selectedIndex));
                        double mablaghMandeh = 0;

                        mablaghMandeh = Double.parseDouble(editTextMablaghMandeh.getText().toString().trim().replace(",", ""));

                        codeNoeVosolMoshtary = configNoeVosolMojazeFaktorModelArrayList.get(selectedIndex).getCodeNoeVosol_Tablet();
                        mPresenter.checkSelectedNoeVosol(String.valueOf(codeNoeVosolMoshtary), ccMoshtary, ccDarkhastFaktor, mablaghMandeh);
                        Log.d("noeVosol", configNoeVosolMojazeFaktorModelArrayList.get(selectedIndex).toString());
                    } else if (from == Constants.FROM_PISH_DARYAFT) {
                        editTextSelectNoeVosol.setText(noeVosolTitles.get(selectedIndex));
                        codeNoeVosolMoshtary = configNoeVosolMojazeMoshtaryModelArrayList.get(selectedIndex).getCodeNoeVosol_Tablet();
                        mPresenter.checkSelectedNoeVosol(String.valueOf(codeNoeVosolMoshtary), ccMoshtary, ccDarkhastFaktor, 0);
                        Log.d("noeVosol", configNoeVosolMojazeMoshtaryModelArrayList.get(selectedIndex).toString());
                    } else {
                        editTextSelectNoeVosol.setText(noeVosolTitles.get(selectedIndex));
                        double mablaghMandeh = 0;

                        mablaghMandeh = Double.parseDouble(editTextMablaghMandeh.getText().toString().trim().replace(",", ""));

                        codeNoeVosolMoshtary = configNoeVosolMojazeFaktorModelArrayList.get(selectedIndex).getCodeNoeVosol_Tablet();
                        mPresenter.checkSelectedNoeVosol(String.valueOf(codeNoeVosolMoshtary), ccMoshtary, ccDarkhastFaktor, mablaghMandeh);
                        Log.d("noeVosol", configNoeVosolMojazeFaktorModelArrayList.get(selectedIndex).toString());
                    }

                    //Toast.makeText(InvoiceSettlementActivity.this, "select : " + childParameterModelsNoeVosol.get(selectedIndex).getTxt() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    /**
     * set recycler Check Bargashty and TreasuryList
     */
    private void setRecyclerCheckBargashtyAndTreasuryList() {

        adapter = new SettlementAdapter(InvoiceSettlementActivity.this, dariaftPardakhtDarkhastFaktorPPCModelsVosol, (position, canDelete) ->
                customAlertDialog.showLogMessageAlert(InvoiceSettlementActivity.this, false, "", getResources().getString(R.string.deleteWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
                    @Override
                    public void setOnCancelClick() {

                    }

                    @Override
                    public void setOnApplyClick() {
                        if (dariaftPardakhtDarkhastFaktorPPCModelsVosol.size() > 0) {
                            if (canDelete){
                                mPresenter.removeItem(dariaftPardakhtDarkhastFaktorPPCModelsVosol.get(position), position, from,ccDarkhastFaktor);
                            } else {
                                customAlertDialog.showToast(InvoiceSettlementActivity.this, getResources().getString(R.string.canNotDelete), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());

                            }

                        }
                    }
                }));

        recyclerView.setAdapter(adapter);
    }

    /**
     * set recycler for pish daryaft
     */
    private void setRecyclerPishDaryaft() {
        adapterPishDaryaft = new SettlementPishDaryaftAdapter(this, dariaftPardakhtPPCModelsVosol, position ->
                customAlertDialog.showLogMessageAlert(InvoiceSettlementActivity.this, false, "", getResources().getString(R.string.deleteWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
                    @Override
                    public void setOnCancelClick() {

                    }

                    @Override
                    public void setOnApplyClick() {
                        if (dariaftPardakhtPPCModelsVosol.size() > 0) {
                            mPresenter.removeItemPishDaryaft(dariaftPardakhtPPCModelsVosol.get(position), position, from);
                        }
                    }
                }));

        recyclerView.setAdapter(adapterPishDaryaft);
    }



    public void showDatePickerAlert(final EditText editText) {
        PersianCalendar persianCalendar = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            monthOfYear++;
                            String month = monthOfYear < 10 ? "0" + monthOfYear : String.valueOf(monthOfYear);
                            String day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                            String selectedDate = getResources().getString(R.string.dateWithSplashFormat, String.valueOf(year), month, day);
                            editText.setText(selectedDate);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", GetProgramActivity.class.getSimpleName(), "showDatePickerAlert", "onDateSet");
                        }
                    }
                }, persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "InvoiceSettlementActivity", "startMVPOps", "");
        }
    }


    private void initialize(InvoiceSettlementMVP.RequiredViewOps view) {
        try {
            mPresenter = new InvoiceSettlementPresenter(view);
            stateMaintainer.put(InvoiceSettlementMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "InvoiceSettlementActivity", "initialize", "");
        }
    }


    private void reinitialize(InvoiceSettlementMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(InvoiceSettlementMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "InvoiceSettlementActivity", "reinitialize", "");
            }
        }
    }


    private void changeDrawableLeftTint(EditText editText, boolean hasFocus) {
        if (hasFocus) {
            try {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(InvoiceSettlementActivity.this, R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable, null, null, null);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                editText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_arrow_down), null, null, null);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }


    private void addCommaToPrice(EditText editText, Editable s, TextWatcher textWatcher) {
        editText.removeTextChangedListener(textWatcher);
        try {
            String inputString = s.toString();
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


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
