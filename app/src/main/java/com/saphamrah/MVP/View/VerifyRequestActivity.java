package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.textfield.TextInputLayout;
import com.saphamrah.Adapter.BonusAdapter;
import com.saphamrah.Adapter.MarjoeeAdapter;
import com.saphamrah.Adapter.RequestedGoodAdapter;
import com.saphamrah.Adapter.TakhfifForoshAdapter;
import com.saphamrah.BaseMVP.VerifyRequestMVP;
import com.saphamrah.CustomView.BottomBar;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.VerifyRequestPresenter;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

public class VerifyRequestActivity extends AppCompatActivity implements VerifyRequestMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , VerifyRequestActivity.this);
    private VerifyRequestMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels;
    private ArrayList<String> moshtaryAddressTitles;
    private ArrayList<ParameterChildModel> childParameterModelsVosols;
    private ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels;
    private ArrayList<String> vosolTitles;
    private int ccMoshtary;
    private int ccSazmanForosh;
    private long ccDarkhastFaktor;
    private int modatVosol;
    private int ccChildParameterNoeVosol;
    private int noeVosol;
    private int modatRoozRaasgiri;
    private String nameNoeVosol;
    private int ccAddress;
    private boolean haveBonus;
    private Date tarikhPishbiniTahvil = null;
    private final int OPEN_MARJOEE_KALA = 100;
    private final int OPEN_BONUS = 101;
    private DecimalFormat costFormatter;

    private TextView lblBonusTitle;
    private TextView lblRequestedGoodsTitle;
    private TextView lblMarjoeeTitle;
    private TextView lblTakhfifTitle;
    private EditText edttxtinputNoeVosol;
    private EditText edttxtinputModatVosol;
    private EditText edttxtinputCustomerAddress;
    private EditText edttxtinputTarikhPishbiniTahvil;
    private EditText editTextSumMablaghKol;
    private EditText editTextsumTakhfif;
    private EditText editTextMablaghKhales;
    private EditText editTextMablaghBaArzeshAfzoode;
    private EditText editTextVaznFaktor;
    private EditText editTextHajmFaktor;
    private EditText editTextTedadPishnahadi;
    private EditText editTextTedadAghlam;
    private EditText editTextSumTedad;
    private TextInputLayout txtinputLayTarikhPishbiniTahvil;
    private FloatingActionButton fabAddMarjoee;
    private FloatingActionButton fabSelectBonus;
    private RecyclerView recyclerViewRequestedGoods;
    private RecyclerView recyclerViewMarjoee;
    private RecyclerView recyclerViewTakhfif;
    private RecyclerView recyclerViewJayezeh;
    private AlertDialog alertDialog;
    private CustomLoadingDialog customLoadingDialog;


    //getModatRoozRaasgiri -> calculate and get Discounts -> calculate and get request details

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_request);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(VerifyRequestActivity.this);
        moshtaryAddressModels = new ArrayList<>();
        moshtaryAddressTitles = new ArrayList<>();
        childParameterModelsVosols = new ArrayList<>();
        kalaDarkhastFaktorSatrModels = new ArrayList<>();
        vosolTitles = new ArrayList<>();
        modatVosol = 0;
        ccChildParameterNoeVosol = 0;
        noeVosol = 0;
        nameNoeVosol = "";
        modatRoozRaasgiri = 0;
        ccAddress = -1;
        haveBonus = false;
        costFormatter = new DecimalFormat("#,###,###");


        lblBonusTitle = findViewById(R.id.lblBonusListTitle);
        lblRequestedGoodsTitle = findViewById(R.id.lblGoodsListTitle);
        lblMarjoeeTitle = findViewById(R.id.lblMarjoeeListTitle);
        lblTakhfifTitle = findViewById(R.id.lblTakhfifListTitle);
        editTextSumMablaghKol = findViewById(R.id.txtSumCost);
        editTextsumTakhfif = findViewById(R.id.txtMablaghTakhfif);
        editTextMablaghKhales = findViewById(R.id.txtMablaghKhales);
        editTextMablaghBaArzeshAfzoode = findViewById(R.id.txtMablaghBaArzeshAfzoode);
        editTextVaznFaktor = findViewById(R.id.txtVaznFaktor);
        editTextHajmFaktor = findViewById(R.id.txtFaktorHajm);
        editTextTedadPishnahadi = findViewById(R.id.txtTedadPishnahadi);
        editTextTedadAghlam = findViewById(R.id.txtTedadAghlam);
        editTextSumTedad = findViewById(R.id.txtSumTedad);
        edttxtinputNoeVosol = findViewById(R.id.txtNoeVosol);
        edttxtinputModatVosol = findViewById(R.id.txtModatVosol);
        edttxtinputCustomerAddress = findViewById(R.id.txtTahvilAddress);
        edttxtinputTarikhPishbiniTahvil = findViewById(R.id.txtTarikhPishbiniTahvil);
        txtinputLayTarikhPishbiniTahvil = findViewById(R.id.inputLayTarikhPishbiniTahvil);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        fabAddMarjoee = findViewById(R.id.fabAddMarjoee);
        fabSelectBonus = findViewById(R.id.fabSelectBonus);
        FloatingActionButton fabShowCustomerInfo = findViewById(R.id.fabShowCustomerInfo);
        recyclerViewRequestedGoods = findViewById(R.id.recyclerViewSefaresh);
        recyclerViewMarjoee = findViewById(R.id.recyclerViewReturned);
        recyclerViewTakhfif = findViewById(R.id.recyclerViewDiscount);
        recyclerViewJayezeh = findViewById(R.id.recyclerViewBonus);

        hideSections();

        new BottomBar(VerifyRequestActivity.this, 3, new BottomBar.OnItemClickListener() {
            @Override
            public void onClick(int position)
            {
                int intModatVosolVaredShode = 0;
                try
                {
                    Log.d("VerifyRequestAct","position:"+position);
                    intModatVosolVaredShode = Integer.valueOf(edttxtinputModatVosol.getText().toString());
                    mPresenter.checkData(position,editTextSumMablaghKol.getText().toString().replace(",",""), editTextsumTakhfif.getText().toString().replace(",",""),
                            editTextMablaghKhales.getText().toString().replace(",",""),editTextMablaghKhales.getText().toString().replace(",",""),
                            ccAddress, intModatVosolVaredShode, noeVosol, edttxtinputNoeVosol.getText().toString(), modatRoozRaasgiri, editTextVaznFaktor.getText().toString().replace(",",""),
                            editTextHajmFaktor.getText().toString().replace(",",""), tarikhPishbiniTahvil, editTextTedadAghlam.getText().toString().replace(",",""));
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    showToast(R.string.errorModatVosol, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
            }
        });

        Intent getIntent = getIntent();
        ccMoshtary = getIntent.getIntExtra("ccMoshtary" , -1);
        ccSazmanForosh = getIntent.getIntExtra("ccSazmanForosh" , -1);
        ccDarkhastFaktor = -1;

        startMVPOps();

        mPresenter.getConfig();
        mPresenter.getRequestsList();
        mPresenter.getCustomerInfo(ccMoshtary, ccSazmanForosh);
        mPresenter.getMarjoeeList();


        edttxtinputCustomerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openCustomerAddressSpinner();
            }
        });

        edttxtinputCustomerAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputCustomerAddress , hasFocus);
                if (hasFocus)
                {
                    openCustomerAddressSpinner();
                }
            }
        });


        edttxtinputNoeVosol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openNoeVosolSpinner();
            }
        });

        edttxtinputNoeVosol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputNoeVosol , hasFocus);
                if (hasFocus)
                {
                    openNoeVosolSpinner();
                }
            }
        });


        edttxtinputTarikhPishbiniTahvil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.calculateTarikhPishbiniTahvil();
            }
        });

        edttxtinputTarikhPishbiniTahvil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputTarikhPishbiniTahvil , hasFocus);
                if (hasFocus)
                {
                    mPresenter.calculateTarikhPishbiniTahvil();
                }
            }
        });


        fabAddMarjoee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                mPresenter.checkForOpenMarjoeeKalaActivity(ccDarkhastFaktor , ccMoshtary , editTextMablaghKhales.getText().toString().replace("," , ""));
            }
        });


        fabSelectBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                onDeleteBonus(true);
                //mPresenter.deleteBonus(ccDarkhastFaktor);
            }
        });


        fabShowCustomerInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(VerifyRequestActivity.this , CustomerInfoActivity.class);
                intent.putExtra(CustomerInfoActivity.CCMOSHTARY_KEY , ccMoshtary);
                intent.putExtra(CustomerInfoActivity.CCSAZMANFOROSH_KEY, ccSazmanForosh);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return VerifyRequestActivity.this;
    }

    @Override
    public void onGetConfig(boolean showBtnMarjoee)
    {
        if (showBtnMarjoee)
        {
            fabAddMarjoee.setVisibility(View.VISIBLE);
            checkShowForFabMenu();
        }
        else
        {
            fabAddMarjoee.setVisibility(View.GONE);
            checkShowForFabMenu();
        }
    }

    @Override
    public void showTarikhPishbiniTahvil()
    {
        txtinputLayTarikhPishbiniTahvil.setVisibility(View.VISIBLE);
        edttxtinputTarikhPishbiniTahvil.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTarikhPishbiniTahvil()
    {
        txtinputLayTarikhPishbiniTahvil.setVisibility(View.GONE);
        edttxtinputTarikhPishbiniTahvil.setVisibility(View.GONE);
    }

    @Override
    public void onGetCustomerAddress(ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<String> moshtaryAddressTitles)
    {
        this.moshtaryAddressModels.clear();
        this.moshtaryAddressTitles.clear();
        this.moshtaryAddressModels.addAll(moshtaryAddressModels);
        this.moshtaryAddressTitles.addAll(moshtaryAddressTitles);
        if (moshtaryAddressModels.size() > 0)
        {
            edttxtinputCustomerAddress.setText(moshtaryAddressModels.get(0).getAddress());
            ccAddress = moshtaryAddressModels.get(0).getCcAddress();
        }
    }

    @Override
    public void openBarkhordAvalieActivity()
    {
        mPresenter.deleteBonus(ccDarkhastFaktor , false);
        Intent intent = new Intent(VerifyRequestActivity.this , BarkhordAvalieForoshandehActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        VerifyRequestActivity.this.finish();
    }

    @Override
    public void openDarkhastActivity()
    {
        mPresenter.deleteBonus(ccDarkhastFaktor , false);
        Intent intent = new Intent(VerifyRequestActivity.this , DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        VerifyRequestActivity.this.finish();
    }

    @Override
    public void openInvoiceSettlementActivity()
    {
        Log.d("ccAddress" , "ccaddress : " + ccAddress);
        mPresenter.checkUpdateDarkhastFaktor(edttxtinputModatVosol.getText().toString(), modatRoozRaasgiri, editTextSumMablaghKol.getText().toString(), editTextMablaghKhales.getText().toString(), editTextMablaghBaArzeshAfzoode.getText().toString(),editTextsumTakhfif.getText().toString(), noeVosol, edttxtinputNoeVosol.getText().toString(), ccAddress);
        Intent intent = new Intent(VerifyRequestActivity.this , InvoiceSettlementActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("sourceActivity" , "VerifyRequestActivity");
        startActivity(intent);
        VerifyRequestActivity.this.finish();
    }

    @Override
    public void openCustomerSignActivity()
    {
        mPresenter.checkUpdateDarkhastFaktor(edttxtinputModatVosol.getText().toString(), modatRoozRaasgiri, editTextSumMablaghKol.getText().toString(), editTextMablaghKhales.getText().toString(), editTextMablaghBaArzeshAfzoode.getText().toString(),editTextsumTakhfif.getText().toString(), noeVosol, edttxtinputNoeVosol.getText().toString(), ccAddress);
        Intent intent = new Intent(VerifyRequestActivity.this , VerifyCustomerRequestActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        VerifyRequestActivity.this.finish();
    }

    @Override
    public void onGetInfo(long ccDarkhastFaktor , int modatVosol , ArrayList<ParameterChildModel> childParameterModelsVosols , ArrayList<String> vosolTitles)
    {
        this.childParameterModelsVosols.clear();
        this.vosolTitles.clear();
        this.ccDarkhastFaktor = ccDarkhastFaktor;
        this.modatVosol = modatVosol;
        edttxtinputModatVosol.setText(String.valueOf(modatVosol));
        this.childParameterModelsVosols.addAll(childParameterModelsVosols);
        this.vosolTitles.addAll(vosolTitles);
        if (childParameterModelsVosols.size() > 0)
        {
            if (childParameterModelsVosols.get(0).getValue().equals(String.valueOf(Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD())))
            {
                edttxtinputModatVosol.setText("0");
            }
            edttxtinputNoeVosol.setText(childParameterModelsVosols.get(childParameterModelsVosols.size()-1).getTxt());
            ccChildParameterNoeVosol = childParameterModelsVosols.get(childParameterModelsVosols.size()-1).getCcParameterChild();
            noeVosol = Integer.parseInt(childParameterModelsVosols.get(childParameterModelsVosols.size()-1).getValue());
            nameNoeVosol = childParameterModelsVosols.get(childParameterModelsVosols.size()-1).getTxt();
            modatRoozRaasgiri = modatVosol;
            alertDialog = customLoadingDialog.showLoadingDialog(VerifyRequestActivity.this);
            //mPresenter.getModatRoozRaasgiri(ccChildParameterNoeVosol);
            mPresenter.getModatRoozRaasgiri(noeVosol);
        }
    }

    @Override
    public void onGetRequestDetail(int sumTedadAghlam, int tedadAghlam, double sumVazn, double sumHajm, double vaznFaktor, double hajmFaktor, double sumMablaghKol, double sumMablaghBaArzeshAfzoodeh, double sumMablaghKhales, int sumTedadAghlamPishnehadiBiSetareh)
    {
        editTextSumMablaghKol.setText(costFormatter.format(sumMablaghKol));
        editTextMablaghKhales.setText(costFormatter.format(sumMablaghKhales));
        editTextMablaghBaArzeshAfzoode.setText(costFormatter.format(sumMablaghBaArzeshAfzoodeh));
        editTextVaznFaktor.setText(String.format("%.2f", vaznFaktor));
        editTextHajmFaktor.setText(String.format("%.2f", hajmFaktor));
        editTextTedadPishnahadi.setText(String.valueOf(sumTedadAghlamPishnehadiBiSetareh));
        editTextTedadAghlam.setText(String.valueOf(tedadAghlam));
        editTextSumTedad.setText(String.valueOf(sumTedadAghlam));
        //mPresenter.calculateDiscounts(ccChildParameterNoeVosol , noeVosol);
    }


    @Override
    public void showModatRoozRaasgiri(int modatRoozRaasgiri, boolean isSelectedVosolVajhNagh)
    {
        if (isSelectedVosolVajhNagh)
        {
            this.modatVosol = 0;
        }
        else
        {
            this.modatVosol = modatRoozRaasgiri;
        }
        edttxtinputModatVosol.setText(String.valueOf(modatVosol));
        this.modatRoozRaasgiri = modatRoozRaasgiri;
        mPresenter.calculateDiscounts(ccChildParameterNoeVosol , noeVosol);
        //mPresenter.getRequestDetail(kalaDarkhastFaktorSatrModels);
    }

    @Override
    public void openMarjoeeKalaActivity(long ccDarkhastFaktor, int ccMoshtary)
    {
        Intent intent = new Intent(VerifyRequestActivity.this , MarjoeeKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        startActivityForResult(intent , OPEN_MARJOEE_KALA);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void onGetRequestsList(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels)
    {
        this.kalaDarkhastFaktorSatrModels.clear();
        this.kalaDarkhastFaktorSatrModels.addAll(kalaDarkhastFaktorSatrModels);
        if (kalaDarkhastFaktorSatrModels.size() == 0)
        {
            lblRequestedGoodsTitle.setVisibility(View.GONE);
            recyclerViewRequestedGoods.setVisibility(View.GONE);
        }
        else
        {
            lblRequestedGoodsTitle.setVisibility(View.VISIBLE);
            recyclerViewRequestedGoods.setVisibility(View.VISIBLE);
            RequestedGoodAdapter adapter = new RequestedGoodAdapter(VerifyRequestActivity.this, kalaDarkhastFaktorSatrModels, false, new RequestedGoodAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(KalaDarkhastFaktorSatrModel kalaDarkhastFaktorModel, int position) {

                }

                @Override
                public void onItemClickJayezeh(int CcKalaCode, int tedadKala, Long ccDarkhastFaktor, double mablaghForosh) {

                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(VerifyRequestActivity.this);
            recyclerViewRequestedGoods.setLayoutManager(mLayoutManager);
            recyclerViewRequestedGoods.setItemAnimator(new DefaultItemAnimator());
            recyclerViewRequestedGoods.addItemDecoration(new DividerItemDecoration(VerifyRequestActivity.this , 0));
            recyclerViewRequestedGoods.setAdapter(adapter);
            //mPresenter.getRequestDetail(kalaDarkhastFaktorModels , ccChildParameterNoeVosol);
        }
    }

    @Override
    public void onGetMarjoeeList(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels)
    {
        if (kalaElamMarjoeeModels.size() == 0)
        {
            lblMarjoeeTitle.setVisibility(View.GONE);
            recyclerViewMarjoee.setVisibility(View.GONE);
        }
        else
        {
            lblMarjoeeTitle.setVisibility(View.VISIBLE);
            recyclerViewMarjoee.setVisibility(View.VISIBLE);
            MarjoeeAdapter adapter = new MarjoeeAdapter(VerifyRequestActivity.this, kalaElamMarjoeeModels, false, new MarjoeeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int operation, KalaElamMarjoeeModel kalaElamMarjoeeModel, int position) {

                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(VerifyRequestActivity.this);
            recyclerViewMarjoee.setLayoutManager(mLayoutManager);
            recyclerViewMarjoee.setItemAnimator(new DefaultItemAnimator());
            recyclerViewMarjoee.addItemDecoration(new DividerItemDecoration(VerifyRequestActivity.this , 0));
            recyclerViewMarjoee.setAdapter(adapter);
        }
    }

    @Override
    public void onGetDiscounts(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels, double sumMablaghTakhfif)
    {
        Log.d("takhfif" , "darkhastFaktorTakhfifModels size : " + darkhastFaktorTakhfifModels.size());

        editTextsumTakhfif.setText(costFormatter.format(sumMablaghTakhfif));
        if (darkhastFaktorTakhfifModels.size() > 0)
        {
            lblTakhfifTitle.setVisibility(View.VISIBLE);
            recyclerViewTakhfif.setVisibility(View.VISIBLE);
            TakhfifForoshAdapter takhfifForoshAdapter = new TakhfifForoshAdapter(VerifyRequestActivity.this, darkhastFaktorTakhfifModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(VerifyRequestActivity.this);
            recyclerViewTakhfif.setLayoutManager(mLayoutManager);
            recyclerViewTakhfif.setItemAnimator(new DefaultItemAnimator());
            recyclerViewTakhfif.addItemDecoration(new DividerItemDecoration(VerifyRequestActivity.this , 0));
            recyclerViewTakhfif.setAdapter(takhfifForoshAdapter);
        }
        else
        {
            lblTakhfifTitle.setVisibility(View.GONE);
            recyclerViewTakhfif.setVisibility(View.GONE);
        }
		mPresenter.getRequestDetail(kalaDarkhastFaktorSatrModels);
    }

    @Override
    public void onGetBonus(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels , boolean showAddBonusBtn, boolean haveBonus)
    {
        Log.d("bonus" , "have bonus : " + haveBonus);
        Log.d("bonus" , "showAddBonusBtn : " + showAddBonusBtn);
        Log.d("bonus" , "darkhastFaktorJayezehModels.size : " + darkhastFaktorJayezehModels.size());
        this.haveBonus = haveBonus;
        if (haveBonus)
        {
            if (darkhastFaktorJayezehModels.size() == 0)
            {
                fabSelectBonus.setVisibility(View.VISIBLE);
                checkShowForFabMenu();
                lblBonusTitle.setVisibility(View.GONE);
                recyclerViewJayezeh.setVisibility(View.GONE);
            }
            else
            {
                lblBonusTitle.setVisibility(View.VISIBLE);
                recyclerViewJayezeh.setVisibility(View.VISIBLE);
            }

            if (showAddBonusBtn)
            {
                fabSelectBonus.setVisibility(View.VISIBLE);
                checkShowForFabMenu();
            }
            else
            {
                fabSelectBonus.setVisibility(View.GONE);
                checkShowForFabMenu();
            }
            BonusAdapter adapter = new BonusAdapter(VerifyRequestActivity.this, darkhastFaktorJayezehModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(VerifyRequestActivity.this);
            recyclerViewJayezeh.setLayoutManager(mLayoutManager);
            recyclerViewJayezeh.setItemAnimator(new DefaultItemAnimator());
            recyclerViewJayezeh.addItemDecoration(new DividerItemDecoration(VerifyRequestActivity.this , 0));
            recyclerViewJayezeh.setAdapter(adapter);
        }
        else
        {
            fabSelectBonus.setVisibility(View.VISIBLE);
            checkShowForFabMenu();
            lblBonusTitle.setVisibility(View.GONE);
            recyclerViewJayezeh.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessCalculateDiscount(boolean haveBonus, boolean canSelectBonus)
    {
        //closeLoading();
        Log.d("onSucCalDiscount" , "haveBonus : " + haveBonus + " , canSelectBonus : " + canSelectBonus);
        this.haveBonus = haveBonus;
        showToast(R.string.successfullyCalculateDiscount, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        if (haveBonus)
        {
            lblBonusTitle.setVisibility(View.VISIBLE);
            recyclerViewJayezeh.setVisibility(View.VISIBLE);
            mPresenter.getBonusList();
        }
        if (canSelectBonus)
        {
            fabSelectBonus.setVisibility(View.VISIBLE);
            checkShowForFabMenu();
            //onDeleteBonus(true);
        }
        //closeLoading();
        /*if (fabAddMarjoee.getVisibility() == View.GONE && fabSelectBonus.getVisibility() == View.GONE)
        {
            fabMenu.setVisibility(View.GONE);
        }
        else
        {
            fabMenu.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onFailedCalculateDiscount(final int resId)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                closeLoading();
                customAlertDialog.showMessageAlert(VerifyRequestActivity.this, "", getResources().getString(resId), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse()
                {
                    @Override
                    public void setOnCancelClick()
                    {

                    }

                    @Override
                    public void setOnApplyClick()
                    {
                        Intent intent = new Intent(VerifyRequestActivity.this , TemporaryRequestsListActivity.class);
                        intent.putExtra("requests" , true);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                        VerifyRequestActivity.this.finish();
                    }
                });
            }
        });
    }

    @Override
    public void showCalculatedDateOfTarikhPishbiniTahvil(final Map<String, Date> mapDates)
    {
        final ArrayList<String> titles = new ArrayList<>(mapDates.keySet());
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(VerifyRequestActivity.this, titles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtinputTarikhPishbiniTahvil.setText(titles.get(selectedIndex));
                tarikhPishbiniTahvil = mapDates.get(titles.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onDeleteBonus(boolean openSelectBonusActivity)
    {
        if (openSelectBonusActivity)
        {
            Intent intent = new Intent(VerifyRequestActivity.this , SelectBonusActivity.class);
            startActivityForResult(intent , OPEN_BONUS);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(VerifyRequestActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void showToast(int resId, String param, int messageType, int duration)
    {
        customAlertDialog.showToast(VerifyRequestActivity.this, getResources().getString(resId, param), messageType, duration);
    }

	@Override
    public void showAlertDialog(int resId, boolean closeActivity, int messageType)
    {
        customAlertDialog.showMessageAlert(VerifyRequestActivity.this, closeActivity, "", getString(resId), messageType, getString(R.string.apply));
    }
	
    @Override																										   
    public void showLoading()
    {
        Log.d("loading" , "show Loading");
        if (alertDialog == null)
        {
            alertDialog = customLoadingDialog.showLoadingDialog(VerifyRequestActivity.this);
        }
    }

    @Override
    public void closeLoading()
    {
        if (alertDialog != null)
        {
            try
            {
                alertDialog.dismiss();
                alertDialog = null;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_MARJOEE_KALA)
        {
            mPresenter.getMarjoeeList();
        }
        else if (requestCode == OPEN_BONUS)
        {
			//showLoading();
            mPresenter.getBonusList();
            mPresenter.getDiscounts(ccDarkhastFaktor);
        }
    }

    private void hideSections()
    {
        lblRequestedGoodsTitle.setVisibility(View.GONE);
        recyclerViewRequestedGoods.setVisibility(View.GONE);

        lblMarjoeeTitle.setVisibility(View.GONE);
        recyclerViewMarjoee.setVisibility(View.GONE);

        lblTakhfifTitle.setVisibility(View.GONE);
        recyclerViewTakhfif.setVisibility(View.GONE);

        fabSelectBonus.setVisibility(View.GONE);
        checkShowForFabMenu();
        lblBonusTitle.setVisibility(View.GONE);
        recyclerViewJayezeh.setVisibility(View.GONE);
    }


    private void checkShowForFabMenu()
    {
        /*if (fabAddMarjoee.getVisibility() == View.GONE && fabSelectBonus.getVisibility() == View.GONE)
        {
            fabMenu.setVisibility(View.GONE);
        }
        else
        {
            fabMenu.setVisibility(View.VISIBLE);
        }*/
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "VerifyRequestActivity", "startMVPOps", "");
        }
    }


    private void initialize(VerifyRequestMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new VerifyRequestPresenter(view);
            stateMaintainer.put(VerifyRequestMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "VerifyRequestActivity", "initialize", "");
        }
    }


    private void reinitialize(VerifyRequestMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(VerifyRequestMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "VerifyRequestActivity", "reinitialize", "");
            }
        }
    }


    private void openCustomerAddressSpinner()
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(VerifyRequestActivity.this, moshtaryAddressTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                edttxtinputCustomerAddress.setText(moshtaryAddressModels.get(selectedIndex).getAddress());
                ccAddress = moshtaryAddressModels.get(selectedIndex).getCcAddress();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    private void openNoeVosolSpinner()
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(VerifyRequestActivity.this, vosolTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                if (childParameterModelsVosols.get(selectedIndex).getValue().equals(String.valueOf(Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD())))
                {
                    edttxtinputModatVosol.setText("0");
                }
                else
                {
                    edttxtinputModatVosol.setText(String.valueOf(modatRoozRaasgiri));
                }
                edttxtinputNoeVosol.setText(childParameterModelsVosols.get(selectedIndex).getTxt());
                ccChildParameterNoeVosol = childParameterModelsVosols.get(selectedIndex).getCcParameterChild();
                noeVosol = Integer.parseInt(childParameterModelsVosols.get(selectedIndex).getValue());
                nameNoeVosol = childParameterModelsVosols.get(selectedIndex).getTxt();
                alertDialog = customLoadingDialog.showLoadingDialog(VerifyRequestActivity.this);
                //mPresenter.calculateDiscounts(ccChildParameterNoeVosol , noeVosol);
                //mPresenter.getModatRoozRaasgiri(ccChildParameterNoeVosol);
                mPresenter.getModatRoozRaasgiri(noeVosol);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(VerifyRequestActivity.this , R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable , null , editText.getCompoundDrawables()[2] , null);
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
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(VerifyRequestActivity.this , R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_arrow_down) , null , editText.getCompoundDrawables()[2] , null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
