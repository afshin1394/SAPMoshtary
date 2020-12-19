package com.saphamrah.MVP.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.saphamrah.Adapter.AddCustomerAddressAdapter;
import com.saphamrah.Adapter.CustomSpinnerAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.EditCustomerMVP;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.EditCustomerPresenter;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class EditCustomerActivity extends AppCompatActivity implements EditCustomerMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , EditCustomerActivity.this);
    private EditCustomerMVP.PresenterOps mPresenter;

    private CustomTextInputLayout txtinputNationalCode;
    private CustomTextInputLayout txtinputNoeFaaliat;
    private CustomTextInputLayout txtinputNoeSenf;
    private CustomTextInputLayout txtinputAnbar;
    private CustomTextInputLayout txtinputMasahatMaghaze;
    private CustomTextInputLayout txtinputSaateVisit;
    private CustomTextInputLayout txtinputMobile;
    private EditText editTextNationalCode;
    private EditText editTextNoeFaaliat;
    private EditText editTextNoeSenf;
    private EditText editTextAnbar;
    private EditText editTextMasahateMaghaze;
    private EditText editTextSaateVisit;
    private EditText editTextMobile;
    ImageView imgBack ;
    ImageView imgEditNoeFaaliatSenf ;
    ImageView imgEditSaateVisit ;
    ImageView imgEditMobile ;
    ImageView imgEditHasAnbarMasahateMaghaze ;
    ImageView imgNationalCode ;
    private CustomAlertDialog customAlertDialog;
    private int codeNoeShakhsiat;
    private int ccMoshtary;
    private int hasAnbar;
    private String nationalCode;
    private int GET_NATIONAL_CODE_IMAGE = 100;


    private Spinner spinnerNoeSenf;
    private ArrayList<Integer> noeFaaliatIds;
    private ArrayList<String> noeFaaliatNames;							  

    // set loading

    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        imgBack = findViewById(R.id.imgBack);
        imgEditNoeFaaliatSenf = findViewById(R.id.imgEditNoeFaaliatSenf);
        imgEditSaateVisit = findViewById(R.id.imgEditSaateVisit);
        imgEditMobile = findViewById(R.id.imgEditMobile);
        imgEditHasAnbarMasahateMaghaze = findViewById(R.id.imgEditShopInfo);
        imgNationalCode = findViewById(R.id.imgEditNationalCode);
        txtinputNationalCode = findViewById(R.id.txtinputLayNationalCode);
        txtinputNoeFaaliat = findViewById(R.id.txtinputLayNoeFaaliat);
        txtinputNoeSenf = findViewById(R.id.txtinputLayNoeSenf);
        txtinputAnbar = findViewById(R.id.txtinputLayAnbar);
        txtinputMasahatMaghaze = findViewById(R.id.txtinputLayMasahateMaghaze);
        txtinputSaateVisit = findViewById(R.id.txtinputLaySaateVisit);
        txtinputMobile = findViewById(R.id.txtinputLayMobile);
        editTextNationalCode = findViewById(R.id.txtNationalCode);
        editTextNoeFaaliat = findViewById(R.id.txtNoeFaaliat);
        editTextNoeSenf = findViewById(R.id.txtNoeSenf);
        editTextAnbar = findViewById(R.id.txtAnbar);
        editTextMasahateMaghaze = findViewById(R.id.txtMasahateMaghazeh);
        editTextSaateVisit = findViewById(R.id.txtSaateVisit);
        editTextMobile = findViewById(R.id.txtMobile);
        customAlertDialog = new CustomAlertDialog(EditCustomerActivity.this);
        customLoadingDialog = new CustomLoadingDialog();

        codeNoeShakhsiat = -1;
        hasAnbar = 0;
        nationalCode = "";

        startMVPOps();

        Intent getIntent = getIntent();
        ccMoshtary = getIntent.getIntExtra("ccMoshtary" , -1);

        mPresenter.getUpdateableItems();
        mPresenter.getCustomerInfo(ccMoshtary);

        imgEditNoeFaaliatSenf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getNoeFaaliatSenf();
            }
        });

        imgEditSaateVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgEditMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditMobile(editTextMobile.getText().toString().trim());
            }
        });

        imgEditHasAnbarMasahateMaghaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String masahateMaghaze = editTextMasahateMaghaze.getText().toString().trim();
                    hasAnbar = editTextAnbar.getText().toString().trim().equals("دارد")?1:0;
                    Log.d("Edit","hasAnbar: " + hasAnbar + " , editTextAnbar: " + editTextAnbar.getText().toString().trim());
                    if (masahateMaghaze.length() > 0)
                    {
                        int intMasahateMaghaze = Integer.parseInt(masahateMaghaze);
                        showEditAnbarAndMasahat(hasAnbar , intMasahateMaghaze);
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "EditCustomerActivity", "", "");
                    showEditAnbarAndMasahat(hasAnbar , 0);
                }
            }
        });

        imgNationalCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCustomerActivity.this , EditNationalCodeActivity.class);
                intent.putExtra("ccMoshtary" , ccMoshtary);
                intent.putExtra("codeNoeShakhsiat" , codeNoeShakhsiat);
                intent.putExtra("nationalCode" , nationalCode);
                startActivityForResult(intent , GET_NATIONAL_CODE_IMAGE);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCustomerActivity.this.finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_NATIONAL_CODE_IMAGE)
        {
            if (resultCode == RESULT_OK)
            {

            }
        }
    }

    @Override
    public Context getAppContext()
    {
        return EditCustomerActivity.this;
    }

    @Override
    public void hideEdttxtNationalCode()
    {
        txtinputNationalCode.setVisibility(View.GONE);
        editTextNationalCode.setVisibility(View.GONE);
        imgNationalCode.setVisibility(View.GONE);
    }

    @Override
    public void hideEdttxtNoeFaaliat()
    {
        txtinputNoeFaaliat.setVisibility(View.GONE);
        editTextNoeFaaliat.setVisibility(View.GONE);
        imgEditNoeFaaliatSenf.setVisibility(View.GONE);
    }

    @Override
    public void hideEdttxtNoeSenf()
    {
        txtinputNoeSenf.setVisibility(View.GONE);
        editTextNoeSenf.setVisibility(View.GONE);
        imgEditNoeFaaliatSenf.setVisibility(View.GONE);
    }

    @Override
    public void hideEdttxtAnbar()
    {
        txtinputAnbar.setVisibility(View.GONE);
        editTextAnbar.setVisibility(View.GONE);
        imgEditHasAnbarMasahateMaghaze.setVisibility(View.GONE);
    }

    @Override
    public void hideEdttxtMasahatMaghaze()
    {
        txtinputMasahatMaghaze.setVisibility(View.GONE);
        editTextMasahateMaghaze.setVisibility(View.GONE);
        imgEditHasAnbarMasahateMaghaze.setVisibility(View.GONE);
    }

    @Override
    public void hideEdttxtSaateVisit()
    {
        txtinputSaateVisit.setVisibility(View.GONE);
        editTextSaateVisit.setVisibility(View.GONE);
        imgEditSaateVisit.setVisibility(View.GONE);
    }

    @Override
    public void hideEdttxtMobile()
    {
        txtinputMobile.setVisibility(View.GONE);
        editTextMobile.setVisibility(View.GONE);
        imgEditMobile.setVisibility(View.GONE);
    }

    @Override
    public void onGetBaseCustomerInfo(String nationalCode, String mobile, String masahateMaghaze, int hasAnbar, String saateVisit, String noeSenf, String noeFaaliat, int codeNoeShakhsiat)
    {
        editTextNationalCode.setText(nationalCode);
        editTextMobile.setText(mobile);
        editTextMasahateMaghaze.setText(masahateMaghaze);
        editTextSaateVisit.setText(saateVisit);
        editTextNoeSenf.setText(noeSenf);
        editTextNoeFaaliat.setText(noeFaaliat);
        this.codeNoeShakhsiat = codeNoeShakhsiat;
        this.hasAnbar = hasAnbar;
        this.nationalCode = nationalCode;
        if (hasAnbar == 1)
        {
            editTextAnbar.setText(getResources().getString(R.string.has));
        }
        else
        {
            editTextAnbar.setText(getResources().getString(R.string.hasnt));
        }

        if (codeNoeShakhsiat == Constants.CODE_NOE_SHAKHSIAT_HAGHIGHI())
        {
            editTextNationalCode.setHint(R.string.natCode);
        }
        else if (codeNoeShakhsiat == Constants.CODE_NOE_SHAKHSIAT_HOGHOGHI())
        {
            editTextNationalCode.setHint(R.string.nationalId);
        }
    }

    @Override
    public void onGetMoshtaryAddress(ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        AddCustomerAddressAdapter adapter = new AddCustomerAddressAdapter(EditCustomerActivity.this, moshtaryAddressModels, 2, true, new AddCustomerAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MoshtaryAddressModel moshtaryAddressModel, int position)
            {
                showEditAddressAlert(moshtaryAddressModel.getCcAddress() , moshtaryAddressModel.getCcMoshtary() , moshtaryAddressModel.getTelephone() , moshtaryAddressModel.getCodePosty());
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EditCustomerActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(EditCustomerActivity.this , 0));
        recyclerView.setAdapter(adapter);
        closeLoading();
    }

    @Override
    public void onGetNoeFaaliat(ArrayList<Integer> noeFaaliatIds, ArrayList<String> noeFaaliatNames)
    {
        this.noeFaaliatIds = new ArrayList<>();
        this.noeFaaliatNames = new ArrayList<>();
        this.noeFaaliatIds.addAll(noeFaaliatIds);
        this.noeFaaliatNames.addAll(noeFaaliatNames);
    }

	@Override
    public void onGetNoeSenf(boolean showAlertDialog, ArrayList<Integer> noeSenfIds, ArrayList<String> noeSenfNames)
    {
        if (showAlertDialog)
        {
            showEditNoeFaaliatSenf(noeSenfIds, noeSenfNames);
        }
        else
        {
            CustomSpinnerAdapter adapterTwo = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, noeSenfNames);
            adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerNoeSenf.setAdapter(adapterTwo);
        }
    }
	
	
    @Override
    public void onUpdateNoeFaaliat(String nameNoeFaaliat)
    {
        editTextNoeFaaliat.setText(nameNoeFaaliat);
        closeLoading();
    }

    @Override
    public void onUpdateNoeSenf(String nameNoeSenf)
    {
        editTextNoeSenf.setText(nameNoeSenf);
        closeLoading();
    }

    @Override
    public void onUpdateSaateVisit(String time)
    {
        editTextSaateVisit.setText(time);
        closeLoading();
    }

    @Override
    public void onUpdateMobile(String mobile)
    {
        editTextMobile.setText(mobile);
        closeLoading();
    }

    @Override
    public void onUpdateHasAnbarMasahateMaghaze(int hasAnbar, int masahateMaghaze)
    {
        editTextMasahateMaghaze.setText(String.valueOf(masahateMaghaze));
        if (hasAnbar == 1)
        {
            editTextAnbar.setText(getResources().getString(R.string.has));
        }
        else
        {
            editTextAnbar.setText(getResources().getString(R.string.hasnt));
        }

        closeLoading();
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(EditCustomerActivity.this, getResources().getString(resId), messageType, duration);
    }


    private void showEditNoeFaaliatSenf(final ArrayList<Integer> noeSenfIds , final ArrayList<String> noeSenfNames)
    {
        final ArrayList<Integer> noeFaaliatSelectedPosition = new ArrayList<>();
        final ArrayList<Integer> noeSenfSelectedPosition = new ArrayList<>();
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditCustomerActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_two_spinner , null);
        TextView lblNoeFaaliatTitle = myview.findViewById(R.id.lblNoeFaaliatTitle);
        TextView lblNoeSenfTitle = myview.findViewById(R.id.lblNoeSenfTitle);
        Spinner spinnerNoeFaaliat = myview.findViewById(R.id.spinnerOne);
        spinnerNoeSenf = myview.findViewById(R.id.spinnerTwo);
        Button btnOK = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        lblNoeFaaliatTitle.setTypeface(font);
        lblNoeSenfTitle.setTypeface(font);
        btnOK.setTypeface(font);

        CustomSpinnerAdapter adapterOne = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, noeFaaliatNames);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNoeFaaliat.setAdapter(adapterOne);

        CustomSpinnerAdapter adapterTwo = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, noeSenfNames);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNoeSenf.setAdapter(adapterTwo);

        noeFaaliatSelectedPosition.add(-1);
        noeSenfSelectedPosition.add(-1);

        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        if (!(EditCustomerActivity.this).isFinishing())
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
                logger.insertLogToDB(EditCustomerActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomerInfoActivity", "showEditAddressAlert", "");
            }

            spinnerNoeFaaliat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
				{
                    noeFaaliatSelectedPosition.clear();
                    noeFaaliatSelectedPosition.add(position);
                    noeSenfSelectedPosition.clear();
                    noeSenfSelectedPosition.add(0);
                    mPresenter.getNoeSenf(noeFaaliatIds.get(position));								   
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerNoeSenf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
			{
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    noeSenfSelectedPosition.clear();
                    noeSenfSelectedPosition.add(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int noeFaaliatPosition = noeFaaliatSelectedPosition.get(0);
                    int noeSenfPosition = noeSenfSelectedPosition.get(0);
                    mPresenter.updateMoshtaryGoroh(ccMoshtary , noeFaaliatIds.get(noeFaaliatPosition), noeFaaliatNames.get(noeFaaliatPosition), noeSenfIds.get(noeSenfPosition), noeSenfNames.get(noeSenfPosition));
                    show.dismiss();
                    showLoading();
                }
            });

        }
    }

    private void showEditMobile(final String mobile)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditCustomerActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_one_edittext , null);
        final CustomTextInputLayout txtinputMobile = myview.findViewById(R.id.txtinputLayMobile);
        final EditText edttxt = myview.findViewById(R.id.txtMobile);
        Button btnOK = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputMobile.setTypeface(font);
        edttxt.setTypeface(font);
        btnOK.setTypeface(font);
        edttxt.setText(mobile);
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        if (!(EditCustomerActivity.this).isFinishing())
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
                logger.insertLogToDB(EditCustomerActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomerInfoActivity", "showEditAddressAlert", "");
            }

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    txtinputMobile.setError(null);
                    String mobileNumber = edttxt.getText().toString().trim();
                    if (mobileNumber.length() != 11 || !mobileNumber.startsWith("09"))
                    {
                        txtinputMobile.setError(getResources().getString(R.string.errorMobile));
                    }
                    else
                    {
                        mPresenter.updateCustomerMobile(ccMoshtary , mobileNumber);
                        showLoading();
                        show.dismiss();
                    }
                }
            });
        }
    }


    private void showEditAnbarAndMasahat(int hasAnbar , int masahateMaghaze)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditCustomerActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_edittext_switchbtn , null);
        final CustomTextInputLayout txtinputMasahateMaghaze = myview.findViewById(R.id.txtinputLayMasahateMaghaze);
        final EditText edttxt = myview.findViewById(R.id.txtMasahateMaghazeh);
        final SwitchCompat switchCompat = myview.findViewById(R.id.swtchAnbar);
        Button btnOK = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputMasahateMaghaze.setTypeface(font);
        edttxt.setTypeface(font);
        switchCompat.setTypeface(font);
        btnOK.setTypeface(font);
        edttxt.setText(String.valueOf(masahateMaghaze));
        if (hasAnbar == 1)
        {
            switchCompat.setChecked(true);
        }
        else
        {
            switchCompat.setChecked(false);
        }
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        if (!(EditCustomerActivity.this).isFinishing())
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
                logger.insertLogToDB(EditCustomerActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomerInfoActivity", "showEditAddressAlert", "");
            }

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    txtinputMasahateMaghaze.setError(null);
                    int hasAnbar = switchCompat.isChecked() ? 1 : 0;
                    try
                    {
                        int masahateMaghaze = Integer.parseInt(edttxt.getText().toString().trim());
                        mPresenter.updateHasAnbarAndMasahateMaghaze(ccMoshtary , hasAnbar , masahateMaghaze);
                        showLoading();
                        show.dismiss();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        txtinputMasahateMaghaze.setError(getResources().getString(R.string.errorInputMasahateMaghaze));
                    }
                }
            });
        }
    }

    private void showEditAddressAlert(final int ccAddress , final int ccMoshtary , String telephone , String postalCode)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditCustomerActivity.this);
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
        if (!(EditCustomerActivity.this).isFinishing())
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
                logger.insertLogToDB(EditCustomerActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomerInfoActivity", "showEditAddressAlert", "");
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
                        showLoading();
                    }
                }
            });

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
                Log.d("update" , "ccmoshtary : " + ccMoshtary);
                Log.d("update" , "time : " + String.format("%1$s : %2$s" , hour , min));
                mPresenter.updateSaateVisit(ccMoshtary , String.format("%1$s : %2$s" , hour , min));
            }
        },hour , min , true);
        timePickerDialog.show(getFragmentManager() , "TimePickerDialog");
    }

    private void showLoading(){
        alertDialogLoading = customLoadingDialog.showLoadingDialog(EditCustomerActivity.this);
    }
    private void closeLoading(){
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorTozieNashodeActivity", "closeLoadingDialog", "");
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", EditCustomerActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(EditCustomerMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new EditCustomerPresenter(view);
            stateMaintainer.put(EditCustomerMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", EditCustomerActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(EditCustomerMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(EditCustomerMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", EditCustomerActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }




}
