package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.saphamrah.Adapter.AddCustomerAddressAdapter;
import com.saphamrah.Adapter.AddCustomerShomareHesabAdapter;
import com.saphamrah.BaseMVP.CustomerInfoMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.CustomerInfoPresenter;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.anwarshahriar.calligrapher.Calligrapher;

public class CustomerInfoActivity extends AppCompatActivity implements CustomerInfoMVP.RequiredViewOps
{
    public final static String CCMOSHTARY_KEY = "ccMoshtary";
    public final static String CCSAZMANFOROSH_KEY = "ccSazmanForosh";

    private CustomTextInputLayout txtinputNationalCode;
    private CustomTextInputLayout txtinputSaateVisit;
    private EditText edttxtAnbar;
    private EditText edttxtSaateVisit;


    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , CustomerInfoActivity.this);
    private CustomerInfoMVP.PresenterOps mPresenter;

    private int ccMoshtary;
    private int ccSazmanForosh;
    private CustomAlertDialog customAlertDialog;
    private int anbarId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgBack = findViewById(R.id.imgBack);
        //Button btnApplyChange = findViewById(R.id.btnApplyChanges);
        txtinputNationalCode = findViewById(R.id.txtinputLayNationalCode);
        CustomTextInputLayout txtinputMasahateMaghaze = findViewById(R.id.txtinputLayMasahateMaghaze);
        CustomTextInputLayout txtinputAnbar = findViewById(R.id.txtinputLayAnbar);
        txtinputSaateVisit = findViewById(R.id.txtinputLaySaateVisit);

        final EditText edttxtNationalCode = findViewById(R.id.txtNationalCode);
        final EditText edttxtMasahateMaghazeh = findViewById(R.id.txtMasahateMaghazeh);
        edttxtAnbar = findViewById(R.id.txtAnbar);
        edttxtSaateVisit = findViewById(R.id.txtSaateVisit);

        customAlertDialog = new CustomAlertDialog(CustomerInfoActivity.this);

        startMVPOps();

        Intent getIntent = getIntent();
        ccMoshtary = getIntent.getIntExtra(CCMOSHTARY_KEY , -1);
        ccSazmanForosh= getIntent.getIntExtra(CCSAZMANFOROSH_KEY,-1);
        anbarId = -1;

        mPresenter.getCustomerInfo(ccMoshtary,ccSazmanForosh);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerInfoActivity.this.finish();
            }
        });

        startFragmentRptEtebar();


    }

    private void startFragmentRptEtebar() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_etebar);
        if(fragment == null){
            fragmentManager.beginTransaction()
                    .add( R.id.fragment_container_etebar ,RptEtebarFragment.newInstance(0, ccMoshtary, -1))
                    .commit();
        }
    }


    @Override
    public Context getAppContext()
    {
        return CustomerInfoActivity.this;
    }

    /*@Override
    public void onGetCustomerBaseInfo(MoshtaryModel moshtaryModel , MoshtaryEtebarSazmanForoshModel moshtaryEtebar , String noeSenf , String noeMoshtary , String noeHamlName , String noeVosolName , String darajeName , String saateVisit , String olaviat)
    {
        EditText edttxtSumEtebarRiali = (EditText)findViewById(R.id.txtSumEtebarRiali);
        EditText edttxtMandehEtebarRiali = (EditText)findViewById(R.id.txtMandehEtebarRiali);
        EditText edttxtSumEtebarTedadi = (EditText)findViewById(R.id.txtSumEtebarTedadi);
        EditText edttxtMandehEtebarTedadi = (EditText)findViewById(R.id.txtMandehEtebarTedadi);
        EditText edttxtEtebarRialiBargashty = (EditText)findViewById(R.id.txtEtebarRialiBargashty);
        EditText edttxtMandehEtebarRialiBargashty = (EditText)findViewById(R.id.txtMandehEtebarRialiBargashty);
        EditText edttxtTedadBargashty = (EditText)findViewById(R.id.txtTedadBargashty);
        EditText edttxtMandehTedadBargashty = (EditText)findViewById(R.id.txtMandehTedadBargashty);

        edttxtSumEtebarRiali.setText(String.valueOf(moshtaryEtebar.getSaghfEtebarRiali()));
    }*/


    @Override
    public void onGetCustomerBaseInfo(MoshtaryModel moshtaryModel)
    {
        EditText edttxtFullName = findViewById(R.id.txtNameFamily);
        EditText edttxtCustomerCode = findViewById(R.id.txtCustomerCode);
        EditText edttxtNationalCode = findViewById(R.id.txtNationalCode);
        EditText edttxtTabloName = findViewById(R.id.txtTabloName);
        EditText edttxtModatVosol = findViewById(R.id.txtModatVosol);
        EditText edttxtMasahateMaghaze = findViewById(R.id.txtMasahateMaghazeh);
        EditText edttxtAnbar = findViewById(R.id.txtAnbar);
        EditText edttxtDarajeh = findViewById(R.id.txtDaraje);

        String nationalCode = (moshtaryModel.getCodeMely() == null || moshtaryModel.getCodeMely().trim().equals("")) ? moshtaryModel.getShenasehMely() : moshtaryModel.getCodeMely();
        String anbar = moshtaryModel.getHasAnbar()==1 ? getResources().getString(R.string.has) : getResources().getString(R.string.hasnt);
        anbarId = moshtaryModel.getHasAnbar();

        edttxtFullName.setText(moshtaryModel.getNameMoshtary());

        edttxtCustomerCode.setText(moshtaryModel.getCodeMoshtary());
        edttxtNationalCode.setText(nationalCode);
        edttxtTabloName.setText(moshtaryModel.getNameTablo());
        edttxtModatVosol.setText(String.valueOf(moshtaryModel.getModateVosol()));
        edttxtAnbar.setText(anbar);
        edttxtMasahateMaghaze.setText(String.valueOf(moshtaryModel.getMasahatMaghazeh()));
        edttxtDarajeh.setText(moshtaryModel.getNameDarajeh());
    }

    @Override
    public void onGetNoeVosolHamlDarajeShakhsiat(String noeVosolName, String noeHamlName, String darajeName , String noeShakhsiat, int olaviat)
    {
        EditText edttxtNoeVosol = findViewById(R.id.txtNoeVosol);
        EditText edttxtNoeHaml = findViewById(R.id.txtNoeHaml);
        EditText edttxtOlaviat = findViewById(R.id.txtOlaviat);
        EditText edttxtNoeShakhsiat = findViewById(R.id.txtNoeShakhsiat);

        edttxtNoeHaml.setText(noeHamlName);
        edttxtNoeVosol.setText(noeVosolName);
        edttxtOlaviat.setText(String.valueOf(olaviat));
        edttxtNoeShakhsiat.setText(noeShakhsiat);
    }

    @Override
    public void onGetSaateVisitOlaviat(String saateVisit, String olaviat)
    {
        EditText edttxtNoeVosol = findViewById(R.id.txtSaateVisit);
        try
        {
            edttxtSaateVisit.setText(saateVisit);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetNoeSenfNoeMoshtary(String noeSenf, String noeMoshtary)
    {
        EditText edttxtCustomerType = findViewById(R.id.txtCustomerType);
        EditText edttxtNoeSenf = findViewById(R.id.txtNoeSenf);

        edttxtCustomerType.setText(noeMoshtary);
        edttxtNoeSenf.setText(noeSenf);
    }


    @Override
    public void onGetCustomerAddresses(ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAddress);
        AddCustomerAddressAdapter adapter = new AddCustomerAddressAdapter(CustomerInfoActivity.this, moshtaryAddressModels, 2, false, new AddCustomerAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MoshtaryAddressModel moshtaryAddressModel, int position)
            {
                showEditAddressAlert(moshtaryAddressModel.getCcAddress() , moshtaryAddressModel.getTelephone() , moshtaryAddressModel.getCodePosty());
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomerInfoActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(CustomerInfoActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void hideCustomerAddress()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAddress);
        TextView lblAddressTitle = (TextView)findViewById(R.id.lblAddressTitle);
        recyclerView.setVisibility(View.GONE);
        lblAddressTitle.setVisibility(View.GONE);
    }

    @Override
    public void onGetCustomerShomareHesab(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels)
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerViewShomareHesab);
        AddCustomerShomareHesabAdapter adapter = new AddCustomerShomareHesabAdapter(CustomerInfoActivity.this, moshtaryShomarehHesabModels, 2, new AddCustomerShomareHesabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel, int position)
            {
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomerInfoActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(CustomerInfoActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void hideCustomerShomareHesab()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewShomareHesab);
        TextView lblShomareHesabTitle = findViewById(R.id.lblShomareHesabTitle);
        recyclerView.setVisibility(View.GONE);
        lblShomareHesabTitle.setVisibility(View.GONE);
    }

    @Override
    public void onGetAnbarItems(final ArrayList<Integer> itemIds , final ArrayList<String> itemTitles)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(CustomerInfoActivity.this, itemTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (itemTitles.size() > 0)
                {
                    edttxtAnbar.setText(itemTitles.get(selectedIndex));
                    anbarId = itemIds.get(selectedIndex);
                }
            }
            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onErrorNationalCode()
    {
        txtinputNationalCode.setError(getResources().getString(R.string.errorNationalCode));
    }

    @Override
    public void onErrorSaateVisit()
    {
        txtinputSaateVisit.setError(getResources().getString(R.string.errorSaateVisit));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(CustomerInfoActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void showNotFoundCustomerError()
    {
        customAlertDialog.showMessageAlert(CustomerInfoActivity.this, true, "", getString(R.string.errorNotFoundCustomer), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    public static void closeSoftKeyboard(Context context, View view)
    {
        try
        {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void showTimePicker()
    {
        String[] currentTime = new PubFunc().new DateUtils().getCurrentTime().split(":");
        int hour = 0;
        int min = 0;
        try
        {
            hour = Integer.parseInt(currentTime[0]);
            min = Integer.parseInt(currentTime[1]);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute)
            {
                String hour = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
                String min = minute < 10 ? "0" + minute : String.valueOf(minute);
                edttxtSaateVisit.setText(String.format("%1$s : %2$s" , hour , min));
            }
        },hour , min , true);
        timePickerDialog.show(getFragmentManager() , "TimePickerDialog");
    }

    private void showEditAddressAlert(final int ccAddress , String telephone , String postalCode)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CustomerInfoActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_edit_customer_address , null);
        final CustomTextInputLayout textInputLayoutTelephone = myview.findViewById(R.id.txtinputTelephone);
        final CustomTextInputLayout textInputLayoutPostalCode = myview.findViewById(R.id.txtinputCodePosti);
        final EditText edttxtTelephone = myview.findViewById(R.id.txtTelephone);
        final EditText edttxtPostalCode = myview.findViewById(R.id.txtCodePosti);
        Button btnOK = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        edttxtTelephone.setTypeface(font);
        edttxtPostalCode.setTypeface(font);
        edttxtTelephone.setText(telephone);
        edttxtPostalCode.setText(postalCode);
        btnOK.setTypeface(font);
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        if (!(CustomerInfoActivity.this).isFinishing())
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
                logger.insertLogToDB(CustomerInfoActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomerInfoActivity", "showEditAddressAlert", "");
            }
            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    textInputLayoutTelephone.setError(null);
                    textInputLayoutPostalCode.setError(null);
                    String telephone = edttxtTelephone.getText().toString().trim();
                    String postalCode = edttxtPostalCode.getText().toString().trim();
                    boolean validData = true;
                    if (telephone.length() != 0 && telephone.length() != 11 && telephone.length() != 8)
                    {
                        textInputLayoutTelephone.setError(getResources().getString(R.string.errorTelephone));
                        validData = false;
                    }
                    if (postalCode.length() > 0 && postalCode.length() != 10)
                    {
                        textInputLayoutPostalCode.setError(getResources().getString(R.string.errorCodePosti));
                        validData = false;
                    }
                    if (validData)
                    {
                        show.dismiss();
                        mPresenter.checkNewAddressData(ccMoshtary , ccAddress , telephone , postalCode);
                    }
                }
            });

        }
    }

    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(CustomerInfoActivity.this , R.drawable.ic_arrow_down);
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
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(CustomerInfoActivity.this , R.drawable.ic_arrow_down);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", CustomerInfoActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(CustomerInfoMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new CustomerInfoPresenter(view);
            stateMaintainer.put(CustomerInfoMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", CustomerInfoActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(CustomerInfoMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(CustomerInfoMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", CustomerInfoActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }



}
