package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.saphamrah.BaseMVP.AddPorseshnameInfoMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.AddPorseshnameInfoPresenter;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.NoeTablighatModel;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.Model.PorseshnamehTablighatModel;
import com.saphamrah.Model.PorseshnamehTozihatModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AddPorseshnameInfoActivity extends AppCompatActivity implements AddPorseshnameInfoMVP.RequiredViewOps
{

    public static final String CC_PORSESHNAMEH_KEY = "ccPorseshnameh";
    public static final String CC_MOSHTARY_KEY = "ccMoshtary";
    public static final String CODE_MOSHTARY_KEY = "codeMoshtary";
    public static final String CC_MASIR_KEY = "ccMasir";

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG, AddPorseshnameInfoActivity.this);
    private AddPorseshnameInfoMVP.PresenterOps mPresenter;


    private EditText edttxtProducts;
    private EditText edttxtAds;
    private EditText edttxtFirstName;
    private EditText edttxtLastName;
    private EditText edttxtShopName;
    private EditText edttxtNationalId;
    private EditText edttxtMasahateMaghaze;
    private EditText edttxtAnbar;
    private EditText edttxtNoeFaaliat;
    private EditText edttxtNoeSenf;
    private EditText edttxtOstan;
    private EditText edttxtShahrestan;
    private EditText edttxtBakhsh;
    private EditText edttxtShahr;
    private EditText edttxtMantaghe;
    private EditText edttxtNameMahale;
    private EditText edttxtPishShomare;
    private EditText edttxtTelephone;
    private EditText edttxtMobile;
    private EditText edttxtPostalCode;
    private EditText edttxtKhiabanAsli;
    private EditText edttxtKhiabanFareeAval;
    private EditText edttxtKhiabanFareeDovom;
    private EditText edttxtKoocheAsli;
    private EditText edttxtKoocheFareeAval;
    private EditText edttxtPelak;
    private EditText edttxtDesc;
    private TextInputLayout txtinputFirstName;
    private TextInputLayout txtinputLastName;
    private TextInputLayout txtinputShopName;
    private TextInputLayout txtinputMasahateMaghaze;
    private TextInputLayout txtinputNoeFaaliat;
    private TextInputLayout txtinputNoeSenf;
    private TextInputLayout txtinputOstan;
    private TextInputLayout txtinputShahrestan;
    private TextInputLayout txtinputBakhsh;
    private TextInputLayout txtinputShahr;
    private TextInputLayout txtinputMantaghe;
    private TextInputLayout txtinputNameMahale;
    private TextInputLayout txtinputPishShomare;
    private TextInputLayout txtinputMobile;
    private TextInputLayout txtinputKhiabanAsli;
    private TextInputLayout txtinputKoocheAsli;
    private TextInputLayout txtinputPelak;

    private CustomAlertDialog customAlertDialog;
    private CustomSpinner customSpinner;

    private ArrayList<String> productItems;
    private Map<String,Integer> mapProductItems;
    private Integer selectedProductId;

    private ArrayList<String> anbarItems;
    private Map<String,Integer> mapAnbarItems;
    private Integer selectedAnbarId;

    private List<String> adsTitles;
    private List<NoeTablighatModel> noeTablighatModels;
    private List<Integer> selectedAdsId;

    private List<String> descTitles;
    private List<PorseshnamehTozihatModel> porseshnamehTozihatModels;
    private Integer selectedDescId;

    private List<String> noeFaaliatTitles;
    private List<GorohModel> noeFaaliatModels;
    private Integer selectedNoeFaaliatId;
    private Integer selectedNoeSenfId;
    private Integer selectedccMasir;

    private List<String> ostanTitles;
    private List<MahalModel> ostanModels;
    private Integer selectedOstanId;
    private Integer selectedShahrestanId;
    private Integer selectedBakhshId;
    private Integer selectedShahrId;
    private Integer selectedMantagheId;
    private int ccMoshtary;
    private Integer ccPorseshname;
    private String oldMobile = "";
    private String oldTelephone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_porseshname_info);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgBack = findViewById(R.id.imgBack);
        edttxtProducts = findViewById(R.id.txtProducts);
        edttxtAds = findViewById(R.id.txtAds);
        edttxtFirstName = findViewById(R.id.txtFirstName);
        edttxtLastName = findViewById(R.id.txtLastName);
        edttxtShopName = findViewById(R.id.txtShopName);
        edttxtNationalId = findViewById(R.id.txtNationalCode);
        edttxtMasahateMaghaze = findViewById(R.id.txtMasahateMaghazeh);
        edttxtAnbar = findViewById(R.id.txtAnbar);
        edttxtNoeFaaliat = findViewById(R.id.txtNoeFaaliat);
        edttxtNoeSenf = findViewById(R.id.txtNoeSenf);
        edttxtOstan = findViewById(R.id.txtOstan);
        edttxtShahrestan = findViewById(R.id.txtShahrestan);
        edttxtBakhsh = findViewById(R.id.txtBakhsh);
        edttxtShahr = findViewById(R.id.txtShahr);
        edttxtMantaghe = findViewById(R.id.txtMantaghe);
        edttxtNameMahale = findViewById(R.id.txtNameMahale);
        edttxtPishShomare = findViewById(R.id.txtTelephoneCode);
        edttxtTelephone = findViewById(R.id.txtTelephone);
        edttxtMobile = findViewById(R.id.txtMobile);
        edttxtPostalCode = findViewById(R.id.txtCodePosti);
        edttxtKhiabanAsli = findViewById(R.id.txtKhiabanAsli);
        edttxtKhiabanFareeAval = findViewById(R.id.txtKhiabanFaree1);
        edttxtKhiabanFareeDovom = findViewById(R.id.txtKhiabanFaree2);
        edttxtKoocheAsli = findViewById(R.id.txtKoocheAsli);
        edttxtKoocheFareeAval = findViewById(R.id.txtKoochefaree1);
        edttxtPelak = findViewById(R.id.txtPelak);
        edttxtDesc = findViewById(R.id.txtDesc);


        txtinputFirstName = findViewById(R.id.txtinputFirstName);
        txtinputLastName = findViewById(R.id.txtinputLastName);
        txtinputShopName = findViewById(R.id.txtinputShopName);
        txtinputMasahateMaghaze = findViewById(R.id.txtinputMasahateMaghazeh);
        txtinputNoeFaaliat = findViewById(R.id.txtinputNoeFaaliat);
        txtinputNoeSenf = findViewById(R.id.txtinputNoeSenf);
        txtinputOstan = findViewById(R.id.txtinputOstan);
        txtinputShahrestan = findViewById(R.id.txtinputShahrestan);
        txtinputBakhsh = findViewById(R.id.txtinputBakhsh);
        txtinputShahr = findViewById(R.id.txtinputShahr);
        txtinputMantaghe = findViewById(R.id.txtinputMantaghe);
        txtinputNameMahale = findViewById(R.id.txtinputNameMahale);
        txtinputMobile = findViewById(R.id.txtinputMobile);
        txtinputKhiabanAsli = findViewById(R.id.txtinputKhiabanAsli);
        txtinputKoocheAsli = findViewById(R.id.txtinputKoocheAsli);
        txtinputPelak = findViewById(R.id.txtinputPelak);


        Button btnApply = findViewById(R.id.btnApply);

        customAlertDialog = new CustomAlertDialog(this);
        customSpinner = new CustomSpinner();

        productItems = new ArrayList<>();
        mapProductItems = new HashMap<>();
        anbarItems = new ArrayList<>();
        mapAnbarItems = new HashMap<>();
        adsTitles = new ArrayList<>();
        selectedAdsId = new ArrayList<>();
        noeTablighatModels = new ArrayList<>();
        descTitles = new ArrayList<>();
        porseshnamehTozihatModels = new ArrayList<>();
        ostanTitles = new ArrayList<>();
        ostanModels = new ArrayList<>();

        startMVPOps();

        Intent getIntent = getIntent();
        ccMoshtary = getIntent.getIntExtra(CC_MOSHTARY_KEY , -1);
        String codeMoshtary = getIntent.getStringExtra(CODE_MOSHTARY_KEY);
        selectedccMasir = getIntent.getIntExtra(CC_MASIR_KEY, 0);
        ccPorseshname = getIntent.getIntExtra(CC_PORSESHNAMEH_KEY, -1);

        mPresenter.getProductsItem();
        mPresenter.getAnbarItems();
        mPresenter.getAds();
        mPresenter.getDescription();
        mPresenter.getOstanItems();
        mPresenter.getAllNoeFaaliat();
        if (ccPorseshname == -1)
        {
            Log.d("AddPorsesh" , "in if : ");
            mPresenter.saveInfo();
            mPresenter.getMoshtary(ccMoshtary, codeMoshtary);
        }
        else
        {
            mPresenter.getPorseshnamehInfo(ccPorseshname);
        }


        edttxtProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openProductSpinner();
            }
        });
        edttxtProducts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtProducts , hasFocus);
                if (hasFocus)
                {
                    openProductSpinner();
                }
            }
        });


        edttxtAnbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openAnbarSpinner();
            }
        });
        edttxtAnbar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtProducts , hasFocus);
                if (hasFocus)
                {
                    openAnbarSpinner();
                }
            }
        });


        edttxtAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openAdsSpinner();
            }
        });
        edttxtAds.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtAds , hasFocus);
                if (hasFocus)
                {
                    openAdsSpinner();
                }
            }
        });


        edttxtOstan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openOstanSpinner();
            }
        });
        edttxtOstan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtOstan , hasFocus);
                if (hasFocus)
                {
                    openOstanSpinner();
                }
            }
        });


        edttxtShahrestan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getShahrestanItems(selectedOstanId);
            }
        });
        edttxtShahrestan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtShahrestan , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getShahrestanItems(selectedOstanId);
                }
            }
        });


        edttxtBakhsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getBakhshItems(selectedShahrestanId);
            }
        });
        edttxtBakhsh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtBakhsh , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getBakhshItems(selectedShahrestanId);
                }
            }
        });


        edttxtShahr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getShahrItems(selectedBakhshId);
            }
        });
        edttxtShahr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtShahr , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getShahrItems(selectedBakhshId);
                }
            }
        });


        edttxtMantaghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getMantagheItems(selectedShahrId);
            }
        });
        edttxtMantaghe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtMantaghe , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getMantagheItems(selectedShahrId);
                }
            }
        });


        edttxtDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openElatSpinner();
            }
        });
        edttxtDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtDesc , hasFocus);
                if (hasFocus)
                {
                    openElatSpinner();
                }
            }
        });


        edttxtNoeFaaliat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openNoeFaaliatSpinner();
            }
        });
        edttxtNoeFaaliat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtNoeFaaliat , hasFocus);
                if (hasFocus)
                {
                    openNoeFaaliatSpinner();
                }
            }
        });


        edttxtNoeSenf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getNoeSenf(selectedNoeFaaliatId);
            }
        });
        edttxtNoeSenf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtNoeSenf , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getNoeSenf(selectedNoeFaaliatId);
                }
            }
        });


        btnApply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String fname = edttxtFirstName.getText().toString();
                String lname = edttxtLastName.getText().toString();
                String nationalCode = edttxtNationalId.getText().toString();
                String nameMaghazeh = edttxtShopName.getText().toString();
                String masahateMaghazeh = edttxtMasahateMaghaze.getText().toString();
                String ostan = edttxtOstan.getText().toString();
                String shahrestan = edttxtShahrestan.getText().toString();
                String bakhsh = edttxtBakhsh.getText().toString();
                String shahr = edttxtShahr.getText().toString();
                String mantaghe = edttxtMantaghe.getText().toString();
                String nameMahale = edttxtNameMahale.getText().toString();
                String telephone = edttxtTelephone.getText().toString();
                String mobile = edttxtMobile.getText().toString();
                String postalCode = edttxtPostalCode.getText().toString();
                String khiabanAsli = edttxtKhiabanAsli.getText().toString();
                String khiabanFaree1 = edttxtKhiabanFareeAval.getText().toString();
                String khiabanFaree2 = edttxtKhiabanFareeDovom.getText().toString();
                String koocheAsli = edttxtKoocheAsli.getText().toString();
                String koocheFaree = edttxtKoocheFareeAval.getText().toString();
                String pelak = edttxtPelak.getText().toString();

                mPresenter.insertPorseshname(ccPorseshname, ccMoshtary, selectedProductId, selectedAdsId, fname, lname, nationalCode, nameMaghazeh, masahateMaghazeh, selectedccMasir, selectedAnbarId, selectedNoeFaaliatId, selectedNoeSenfId, ostan, shahrestan, bakhsh, shahr, mantaghe, selectedMantagheId, nameMahale, telephone, oldTelephone, mobile, oldMobile, postalCode, khiabanAsli, khiabanFaree1, khiabanFaree2, koocheAsli, koocheFaree, pelak, selectedDescId);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddPorseshnameInfoActivity.this.finish();
            }
        });


    }


    @Override
    public Context getAppContext()
    {
        return this;
    }

    @Override
    public void showMoshtaryInfo(ListMoshtarianModel listMoshtarianModel)
    {
        edttxtFirstName.setText(listMoshtarianModel.getFNameMoshtary());
        edttxtLastName.setText(listMoshtarianModel.getLNameMoshtary());
        edttxtNationalId.setText(listMoshtarianModel.getCodeMely());
        edttxtShopName.setText(listMoshtarianModel.getNameTablo());
        edttxtMasahateMaghaze.setText(String.valueOf(listMoshtarianModel.getMasahatMaghazeh()));
        edttxtPostalCode.setText(listMoshtarianModel.getCodePosty());
        edttxtKhiabanAsli.setText(listMoshtarianModel.getKhiabanAsli());
        edttxtKhiabanFareeAval.setText(listMoshtarianModel.getKhiabanFarei1());
        edttxtKhiabanFareeDovom.setText(listMoshtarianModel.getKhiabanFarei2());
        edttxtKoocheAsli.setText(listMoshtarianModel.getKoocheAsli());
        edttxtKoocheFareeAval.setText(listMoshtarianModel.getKoocheFarei1());
        edttxtPelak.setText(listMoshtarianModel.getPelak());
        oldMobile = listMoshtarianModel.getMobile();
        oldTelephone = listMoshtarianModel.getTelephone();

        selectedAnbarId = listMoshtarianModel.getHasAnbar();
        if (listMoshtarianModel.getHasAnbar() == 1)
        {
            edttxtAnbar.setText(getString(R.string.has));
        }
        else
        {
            edttxtAnbar.setText(getString(R.string.hasnt));
        }
    }

    @Override
    public void onGetPorseshnamehInfo(PorseshnamehModel porseshnamehModel)
    {
        edttxtFirstName.setText(porseshnamehModel.getFNameMoshtary());
        edttxtLastName.setText(porseshnamehModel.getLNameMoshtary());
        edttxtNationalId.setText(porseshnamehModel.getCodeMely());
        edttxtShopName.setText(porseshnamehModel.getNameMaghazeh());
        edttxtMasahateMaghaze.setText(String.valueOf(porseshnamehModel.getMasahatMaghazeh()));
        edttxtPostalCode.setText(porseshnamehModel.getCodePosty());
        edttxtKhiabanAsli.setText(porseshnamehModel.getKhiabanAsli());
        edttxtKhiabanFareeAval.setText(porseshnamehModel.getKhiabanFarei1());
        edttxtKhiabanFareeDovom.setText(porseshnamehModel.getKhiabanFarei2());
        edttxtKoocheAsli.setText(porseshnamehModel.getKoocheAsli());
        edttxtKoocheFareeAval.setText(porseshnamehModel.getKoocheFarei1());
        edttxtPelak.setText(porseshnamehModel.getPelak());
        edttxtNameMahale.setText(porseshnamehModel.getNameMahaleh());
        edttxtMobile.setText(porseshnamehModel.getMobile());
        edttxtTelephone.setText(porseshnamehModel.getTelephone());

        oldMobile = porseshnamehModel.getMobile();
        oldTelephone = porseshnamehModel.getTelephone();
        selectedProductId = porseshnamehModel.getHasDelpazir();
        selectedAnbarId = porseshnamehModel.getHasAnbar();
        selectedNoeFaaliatId = porseshnamehModel.getCcNoeMoshtary();
        selectedNoeSenfId = porseshnamehModel.getCcSenfMoshtary();
        selectedDescId = porseshnamehModel.getCcPorseshnamehTozihat();

        // set has anbar
        if (porseshnamehModel.getHasAnbar() == 1)
        {
            edttxtAnbar.setText(getString(R.string.has));
        }
        else
        {
            edttxtAnbar.setText(getString(R.string.hasnt));
        }

        // set has products
        if (porseshnamehModel.getHasDelpazir() == 1)
        {
            edttxtProducts.setText(getString(R.string.has));
        }
        else
        {
            edttxtProducts.setText(getString(R.string.hasnt));
        }

        //set customer description
        for (PorseshnamehTozihatModel model : porseshnamehTozihatModels)
        {
            if (model.getCcPorseshnamehTozihat() == porseshnamehModel.getCcPorseshnamehTozihat())
            {
                edttxtDesc.setText(model.getSharh());
                break;
            }
        }

        mPresenter.getAdsOfPorseshnameh(porseshnamehModel.getCcPorseshnameh());
        mPresenter.getNameSenfMoshtary(porseshnamehModel.getCcSenfMoshtary());
        mPresenter.getStatesOfMantaghe(porseshnamehModel.getCcMahal());
    }

    @Override
    public void showAdsOfPorseshnameh(List<PorseshnamehTablighatModel> porseshnamehTablighatModels)
    {
        String selectedAdsTitle = "";
        for (PorseshnamehTablighatModel model : porseshnamehTablighatModels)
        {
            selectedAdsId.add(model.getCcNoeTablighat());
            for (NoeTablighatModel noeTablighatModel : noeTablighatModels)
            {
                if (model.getCcNoeTablighat() == noeTablighatModel.getCcNoeTablighat())
                {
                    selectedAdsTitle += noeTablighatModel.getNameNoeTablighat() + ",";
                    break;
                }
            }
        }

        if (selectedAdsTitle.endsWith(","))
        {
            selectedAdsTitle = selectedAdsTitle.substring(0, (selectedAdsTitle.length()-1));
        }
        edttxtAds.setText(selectedAdsTitle);
    }

    @Override
    public void showStatesOfMantaghe(MahalModel mantagheModel, MahalModel shahrModel, MahalModel bakhshModel, MahalModel shahrestanModel, MahalModel ostanModel)
    {
        selectedOstanId = ostanModel.getCcMahal();
        selectedShahrestanId = shahrestanModel.getCcMahal();
        selectedBakhshId = bakhshModel.getCcMahal();
        selectedShahrId = shahrModel.getCcMahal();
        selectedMantagheId = mantagheModel.getCcMahal();

        edttxtOstan.setText(ostanModel.getNameMahal());
        edttxtShahrestan.setText(shahrestanModel.getNameMahal());
        edttxtBakhsh.setText(bakhshModel.getNameMahal());
        edttxtShahr.setText(shahrModel.getNameMahal());
        edttxtMantaghe.setText(mantagheModel.getNameMahal());
    }

    @Override
    public void showNameSenfMoshtary(String nameGoroh)
    {
        edttxtNoeSenf.setText(nameGoroh);
    }

    @Override
    public void setProductItems(Map<String,Integer> mapProductItems)
    {
        productItems.addAll(mapProductItems.keySet());
        this.mapProductItems = mapProductItems;
        edttxtProducts.setText(productItems.get(0));
        selectedProductId = mapProductItems.get(edttxtProducts.getText().toString());
    }

    @Override
    public void showErrorGetProductItems()
    {
        customAlertDialog.showMessageAlert(this, true, "", getString(R.string.errorGetProductItems), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void setAnbarItems(Map<String, Integer> mapAnbarItems)
    {
        anbarItems.addAll(mapAnbarItems.keySet());
        this.mapAnbarItems = mapAnbarItems;
        if (ccMoshtary == -1)
        {
            edttxtAnbar.setText(anbarItems.get(0));
        }
        selectedAnbarId = mapAnbarItems.get(edttxtAnbar.getText().toString());
    }

    @Override
    public void showErrorGetAnbarItems()
    {
        customAlertDialog.showMessageAlert(this, true, "", getString(R.string.errorGetAnbarItems), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void setAds(List<NoeTablighatModel> noeTablighatModels, List<String> adsTitle)
    {
        this.adsTitles.addAll(adsTitle);
        this.noeTablighatModels = noeTablighatModels;
        selectedAdsId = new ArrayList<>();
    }

    @Override
    public void showErrorNotFoundAds()
    {
        customAlertDialog.showMessageAlert(this, true, "", getString(R.string.errorGetAds), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }

    @Override
    public void setDescrption(List<PorseshnamehTozihatModel> porseshnamehTozihatModels, List<String> descTitles)
    {
        this.descTitles.addAll(descTitles);
        this.porseshnamehTozihatModels = porseshnamehTozihatModels;
        edttxtDesc.setText(porseshnamehTozihatModels.get(0).getSharh());
        selectedDescId = 0;
    }

    @Override
    public void showErrorGetDescrption()
    {
        customAlertDialog.showMessageAlert(this, true, "", getString(R.string.errorGetElatAdamMoarefiMoshtary), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void setNoeFaaliat(ArrayList<GorohModel> noeFaaliatItems, List<String> titles)
    {
        this.noeFaaliatModels = noeFaaliatItems;
        noeFaaliatTitles = titles;
        edttxtNoeFaaliat.setText(titles.get(0));
        selectedNoeFaaliatId = noeFaaliatItems.get(0).getCcGoroh();
    }

    @Override
    public void setNoeSenf(final ArrayList<GorohModel> noeSenfItems, final List<String> titles)
    {
        customSpinner.showSpinner(this, titles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtNoeSenf.setText(titles.get(selectedIndex));
                selectedNoeSenfId = noeSenfItems.get(selectedIndex).getCcGoroh();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void setOstanItems(ArrayList<MahalModel> mahalModels, List<String> ostanTitles)
    {
        this.ostanTitles.addAll(ostanTitles);
        ostanModels = mahalModels;
    }

    @Override
    public void setShahrestanTitlesItems(final ArrayList<MahalModel> mahalModels, final List<String> shahrestanTitles)
    {
        customSpinner.showSpinner(this, shahrestanTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtShahrestan.setText(shahrestanTitles.get(selectedIndex));
                selectedShahrestanId = mahalModels.get(selectedIndex).getCcMahal();

                selectedBakhshId = null;
                selectedShahrId = null;
                selectedMantagheId = null;

                edttxtBakhsh.setText("");
                edttxtShahr.setText("");
                edttxtPishShomare.setText("");
                edttxtMantaghe.setText("");
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void setBakhshTitlesItems(final ArrayList<MahalModel> mahalModels, final List<String> bakhshTitles)
    {
        customSpinner.showSpinner(this, bakhshTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtBakhsh.setText(bakhshTitles.get(selectedIndex));
                selectedBakhshId = mahalModels.get(selectedIndex).getCcMahal();

                selectedShahrId = null;
                selectedMantagheId = null;

                edttxtShahr.setText("");
                edttxtPishShomare.setText("");
                edttxtMantaghe.setText("");
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void setShahrTitlesItems(final ArrayList<MahalModel> mahalModels, final List<String> shahrTitles)
    {
        customSpinner.showSpinner(this, shahrTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtShahr.setText(shahrTitles.get(selectedIndex));
                selectedShahrId = mahalModels.get(selectedIndex).getCcMahal();
                edttxtPishShomare.setText(mahalModels.get(selectedIndex).getPishShomareh());

                selectedMantagheId = null;

                edttxtMantaghe.setText("");
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void setMantagheTitlesItems(final ArrayList<MahalModel> mahalModels, final List<String> mantagheTitles)
    {
        customSpinner.showSpinner(this, mantagheTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtMantaghe.setText(mantagheTitles.get(selectedIndex));
                selectedMantagheId = mahalModels.get(selectedIndex).getCcMahal();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void openKalaAmargarActivity(int ccPorseshname)
    {
        Intent intent = new Intent(this, KalaAmargarActivity.class);
        intent.putExtra(KalaAmargarActivity.CC_PORSESHNAME_KEY, ccPorseshname);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showAlertChangedPhone(final int ccPorseshname)
    {
        customAlertDialog.showMessageAlert(this, "", getString(R.string.phoneChanged), Constants.INFO_MESSAGE(), getString(R.string.apply), new CustomAlertDialogResponse()
        {
            @Override
            public void setOnCancelClick() {}

            @Override
            public void setOnApplyClick()
            {
                openKalaAmargarActivity(ccPorseshname);
            }
        });
    }

    @Override
    public void showCustomerAddress(MahalModel ostanModel, MahalModel shahrestanModel, MahalModel bakhshModel, MahalModel shahrModel, MahalModel mantagheModel)
    {
        edttxtOstan.setText(ostanModel.getNameMahal());
        edttxtShahrestan.setText(shahrestanModel.getNameMahal());
        edttxtBakhsh.setText(bakhshModel.getNameMahal());
        edttxtShahr.setText(shahrModel.getNameMahal());
        edttxtMantaghe.setText(mantagheModel.getNameMahal());

        selectedOstanId = ostanModel.getCcMahal();
        selectedShahrestanId = shahrestanModel.getCcMahal();
        selectedBakhshId = bakhshModel.getCcMahal();
        selectedShahrId = shahrModel.getCcMahal();
        selectedMantagheId = mantagheModel.getCcMahal();
    }

    private void openProductSpinner()
    {
        customSpinner.showSpinner(this, productItems, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtProducts.setText(productItems.get(selectedIndex));
                selectedProductId = mapProductItems.get(edttxtProducts.getText().toString());
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    private void openAnbarSpinner()
    {
        customSpinner.showSpinner(this, anbarItems, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtAnbar.setText(anbarItems.get(selectedIndex));
                selectedAnbarId = mapAnbarItems.get(edttxtAnbar.getText().toString());
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    private void openAdsSpinner()
    {
        customSpinner.showMultiSelectSpinner(this, adsTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {}

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {
                selectedAdsId.clear();
                String ads = "";
                for (Integer selectedIndex : selectedIndexes)
                {
                    ads += adsTitles.get(selectedIndex) + ",";
                }
                if (ads.endsWith(","))
                {
                    ads = ads.substring(0, ads.lastIndexOf(','));
                }
                edttxtAds.setText(ads);
                for (Integer i : selectedIndexes)
                {
                    selectedAdsId.add(noeTablighatModels.get(i).getCcNoeTablighat());
                }
            }
        });
    }

    private void openElatSpinner()
    {
        customSpinner.showSpinner(this, descTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtDesc.setText(descTitles.get(selectedIndex));
                selectedDescId = selectedIndex;
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    private void openNoeFaaliatSpinner()
    {
        customSpinner.showSpinner(this, noeFaaliatTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtNoeFaaliat.setText(noeFaaliatTitles.get(selectedIndex));
                selectedNoeFaaliatId = noeFaaliatModels.get(selectedIndex).getCcGoroh();
                edttxtNoeSenf.setText("");
                selectedNoeSenfId = null;
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    private void openOstanSpinner()
    {
        customSpinner.showSpinner(this, ostanTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtOstan.setText(ostanTitles.get(selectedIndex));
                selectedOstanId = ostanModels.get(selectedIndex).getCcMahal();

                selectedShahrestanId = null;
                selectedBakhshId = null;
                selectedShahrId = null;
                selectedMantagheId = null;

                edttxtShahrestan.setText("");
                edttxtBakhsh.setText("");
                edttxtShahr.setText("");
                edttxtPishShomare.setText("");
                edttxtMantaghe.setText("");
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void showErrorNotSelectedOstan()
    {
        customAlertDialog.showMessageAlert(this, false, "", getResources().getString(R.string.errorOstan), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorNotSelectedShahrestan()
    {
        customAlertDialog.showMessageAlert(this, false, "", getResources().getString(R.string.errorShahrestan), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorNotSelectedBakhsh()
    {
        customAlertDialog.showMessageAlert(this, false, "", getResources().getString(R.string.errorBakhsh), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorNotSelectedShahr()
    {
        customAlertDialog.showMessageAlert(this, false, "", getResources().getString(R.string.errorShahr), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorFname()
    {
        txtinputFirstName.setError(getString(R.string.errorFirstName));
    }

    @Override
    public void showErrorLname()
    {
        txtinputLastName.setError(getString(R.string.errorLastName));
    }

    @Override
    public void showErrorNameMaghazeh()
    {
        txtinputShopName.setError(getString(R.string.errorShopName));
    }

    @Override
    public void showErrorMasahateMaghazeh()
    {
        txtinputMasahateMaghaze.setError(getString(R.string.errorMasahateMaghazehEmpty));
    }

    @Override
    public void showErrorOstan()
    {
        txtinputOstan.setError(getString(R.string.errorOstan));
    }

    @Override
    public void showErrorShahrestan()
    {
        txtinputShahrestan.setError(getString(R.string.errorShahrestan));
    }

    @Override
    public void showErrorBakhsh()
    {
        txtinputBakhsh.setError(getString(R.string.errorBakhsh));
    }

    @Override
    public void showErrorShahr()
    {
        txtinputShahr.setError(getString(R.string.errorShahr));
    }

    @Override
    public void showErrorMantaghe()
    {
        txtinputMantaghe.setError(getString(R.string.errorMantaghe));
    }

    @Override
    public void showErrorNameMahale()
    {
        txtinputNameMahale.setError(getString(R.string.errorNameMahale));
    }

    @Override
    public void showErrorKhiabanAsli()
    {
        txtinputKhiabanAsli.setError(getString(R.string.errorKhiabanAsli));
    }


    @Override
    public void showErrorKoocheAsli()
    {
        txtinputKoocheAsli.setError(getString(R.string.errorKoocheAsli));
    }

    @Override
    public void showErrorPelak()
    {
        txtinputPelak.setError(getString(R.string.errorPelak));
    }

    @Override
    public void showErrorAddress()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorAddress), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorWrongMobile()
    {
        txtinputMobile.setError(getString(R.string.errorMobile));
    }

    @Override
    public void showErrorInsertPorseshname()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSaveData), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorGetNoeFaaliat()
    {
        customAlertDialog.showMessageAlert(this, true, "", getString(R.string.errorNotFoundNoeSenf), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorNotSelectedNoeFaaliat()
    {
        customAlertDialog.showMessageAlert(this, true, "", getString(R.string.errorNotFoundNoeFaaliat), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorGetNoeSenf()
    {
        customAlertDialog.showMessageAlert(this, true, "", getString(R.string.errorNotFoundNoeSenf), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorNoeFaaliat()
    {
        txtinputNoeFaaliat.setError(getString(R.string.errorNoeFaaliat));
    }

    @Override
    public void showErrorNoeSenf()
    {
        txtinputNoeSenf.setError(getString(R.string.errorNoeSenf));
    }

    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_arrow_down);
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
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_arrow_down);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "startMVPOps", "");
        }
    }

    private void initialize(AddPorseshnameInfoMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddPorseshnameInfoPresenter(view);
            stateMaintainer.put(AddPorseshnameInfoMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "initialize", "");
        }
    }

    private void reinitialize(AddPorseshnameInfoMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddPorseshnameInfoMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "reinitialize", "");
            }
        }
    }


}
