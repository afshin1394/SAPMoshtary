package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.saphamrah.Adapter.GetProgramAdapter;
import com.saphamrah.BaseMVP.AddCustomerBaseInfoMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.AddCustomerBaseInfoPresenter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddCustomerBaseInfoFragment extends Fragment implements AddCustomerBaseInfoMVP.RequiredViewOps
{

    public interface onVisiblePersonalInformationListener
    {
        void sendData(AddCustomerInfoModel addCustomerInfoModel);
    }

    private EditText edttxtinputFirstName;
    private EditText edttxtinputLastName;
    private EditText edttxtinputBirthDate;
    private EditText edttxtinputTabloName;
    private EditText edttxtinputNationalCode;
    private EditText edttxtinputMobile;
    private EditText edttxtinputAnbar;
    private EditText edttxtinputMasahateMaghazeh;
    private EditText edttxtinputRotbeh;
    private EditText edttxtinputNoeFaaliat;
    private EditText edttxtinputNoeHaml;
    private EditText edttxtinputNoeShakhsiat;
    private EditText edttxtinputNoeSenf;
    private EditText edttxtinputNoeVosol;
    private EditText edttxtinputSaateVisit;
    private EditText edttxtCodeEghtesady;
    private CustomTextInputLayout txtinputFirstName;
    private CustomTextInputLayout txtinputLastName;
    private CustomTextInputLayout txtinputBirthDate;
    private CustomTextInputLayout txtinputTabloName;
    private CustomTextInputLayout txtinputNationalCode;
    private CustomTextInputLayout txtinputMobile;
    private CustomTextInputLayout txtinputAnbar;
    private CustomTextInputLayout txtinputMasahateMaghazeh;
    private CustomTextInputLayout txtinputMoshtaryRotbeh;
    private CustomTextInputLayout txtinputNoeFaaliat;
    private CustomTextInputLayout txtinputNoeHaml;
    private CustomTextInputLayout txtinputNoeShakhsiat;
    private CustomTextInputLayout txtinputNoeSenf;
    private CustomTextInputLayout txtinputNoeVosol;
    private CustomTextInputLayout txtinputSaateVisit;
    private CustomTextInputLayout txtinputCodeEghtesady;
    private Button btnApply;
    private Context context;

    private String noeShakhsiatId="";
    private String noeFaaliatId="";
    private String noeSenfId="";
    private String rotbeId="";
    private String noeVosolId="";
    private String noeHamlId="";
    private String anbarId="";
    private String selectedDate="";

    private boolean requireCodeMeli;
    private boolean requireMobile;
    private boolean requireMasahat;			   
	
    AddCustomerBaseInfoMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;

    private onVisiblePersonalInformationListener listener;

    private  AddCustomerInfoModel addCustomerInfoModel;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser)
        {
            if (getView() != null)
            {
                mPresenter.getBaseInfoFromShared();
            }
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            this.context = context;
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.add_customer_base_info_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        /*noeShakhsiatId = "";
        noeFaaliatId = "";
        noeSenfId = "";
        rotbeId = "";
        noeVosolId = "";
        noeHamlId = "";
        anbarId = "";*/

        addCustomerInfoModel = new AddCustomerInfoModel();

        bindTextInputLayouts(view);
        bindEditTextInput(view);
        btnApply = view.findViewById(R.id.btnApply);
        setTypeFace();

        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , context);
        startMVPOps();

        this.listener = (onVisiblePersonalInformationListener)context;


		edttxtinputBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerAlert();
            }
        });
        edttxtinputBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputBirthDate , hasFocus);
                if (hasFocus)
                {
                    showDatePickerAlert();
                }
            }
        });
		
        edttxtinputAnbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getAnbar();
            }
        });

        edttxtinputAnbar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputAnbar , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getAnbar();
                }
            }
        });


        edttxtinputRotbeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getRotbe();
            }
        });

        edttxtinputRotbeh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputRotbeh , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getRotbe();
                }
            }
        });

        edttxtinputNoeFaaliat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //showSpinnerNoeFaaliat();
                edttxtinputNoeVosol.setText("");
                noeVosolId = "";
                edttxtinputNoeVosol.setText("");
                mPresenter.getNoeFaaliatItems();
            }
        });

        edttxtinputNoeFaaliat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputNoeFaaliat , hasFocus);
                if (hasFocus)
                {
                    //showSpinnerNoeFaaliat();
                    edttxtinputNoeVosol.setText("");
                    noeVosolId = "";
                    edttxtinputNoeVosol.setText("");
                    mPresenter.getNoeFaaliatItems();
                }
            }
        });


        edttxtinputNoeHaml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getNoeHaml();
            }
        });

        edttxtinputNoeHaml.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputNoeHaml , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getNoeHaml();
                }
            }
        });


        edttxtinputNoeShakhsiat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getNoeShakhsiat();
            }
        });

        edttxtinputNoeShakhsiat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputNoeShakhsiat , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getNoeShakhsiat();
                }
            }
        });


        edttxtinputNoeSenf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getNoeSenfItems(noeFaaliatId);
            }
        });

        edttxtinputNoeSenf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputNoeSenf , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getNoeSenfItems(noeFaaliatId);
                }
            }
        });

        edttxtinputNoeVosol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getNoeVosol(noeFaaliatId);
            }
        });

        edttxtinputNoeVosol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputNoeVosol , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getNoeVosol(noeFaaliatId);
                }
            }
        });


        edttxtinputSaateVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showTimePicker();
            }
        });

        edttxtinputSaateVisit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtinputSaateVisit , hasFocus);
                if (hasFocus)
                {
                    showTimePicker();
                }
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkData();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return context;
    }

    @Override
    public void onGetConfig(boolean requireCodeMeli, boolean requireMobile, boolean requireMasahat)
    {
        this.requireCodeMeli = requireCodeMeli;
        this.requireMobile = requireMobile;
        this.requireMasahat = requireMasahat;
    }

    @Override
    public void showCodeMeliHint(int resId)
    {
        txtinputNationalCode.setHint(getString(resId));
    }

    @Override
    public void showMobileHint(int resId)
    {
        txtinputMobile.setHint(getString(resId));
    }

    @Override
    public void showMasahateMaghazeHint(int resId)
    {
        txtinputMasahateMaghazeh.setHint(getString(resId));
    }

    @Override
    public void showBirthDateHint(int resId) {
        txtinputBirthDate.setHint(getString(resId));
    }

    @Override
    public void onGetAnbarItems(final ArrayList<Integer> itemIds, final ArrayList<String> itemTitles)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner((Activity)context, itemTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (itemTitles.size() > 0)
                {
                    edttxtinputAnbar.setText(itemTitles.get(selectedIndex));
                    anbarId = itemIds.get(selectedIndex).toString();
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetRotbeItems(final ArrayList<Integer> itemsId , final ArrayList<String> itemTitle)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner((Activity)context, itemTitle, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (itemTitle.size() > 0)
                {
                    edttxtinputRotbeh.setText(itemTitle.get(selectedIndex));
                    rotbeId = itemsId.get(selectedIndex).toString();
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetNoeShakhsiatItems(final ArrayList<Integer> itemIds, final ArrayList<String> itemTitles)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner((Activity)context, itemTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (itemTitles.size() > 0)
                {
                    edttxtinputNoeShakhsiat.setText(itemTitles.get(selectedIndex));
                    noeShakhsiatId = itemIds.get(selectedIndex).toString();

//                    edttxtinputBirthDate.setHint();
                    addCustomerInfoModel.setNationalCode(edttxtinputNationalCode.getText().toString().trim());
                    addCustomerInfoModel.setNoeShakhsiatId(noeShakhsiatId);
                    addCustomerInfoModel.setNoeShakhsiatTitle(edttxtinputNoeShakhsiat.getText().toString().trim());
                    mPresenter.getConfig(addCustomerInfoModel);
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetNoeFaaliatItems(final ArrayList<GorohModel> noeFaaliats)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        final ArrayList<String> noeFaaliatTitles = new ArrayList<>();
        for (GorohModel gorohModel : noeFaaliats)
        {
            noeFaaliatTitles.add(gorohModel.getNameGoroh());
        }
        customSpinner.showSpinner((Activity)context, noeFaaliatTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (noeFaaliats.size() > 0)
                {
                    edttxtinputNoeFaaliat.setText(noeFaaliatTitles.get(selectedIndex));
                    noeFaaliatId = noeFaaliats.get(selectedIndex).getCcGoroh().toString();
                    noeSenfId = "";
                    edttxtinputNoeSenf.setText("");
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetNoeSenfItems(final ArrayList<GorohModel> noeSenfItems)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        final ArrayList<String> noeSenfTitle = new ArrayList<>();

        for (GorohModel gorohModel : noeSenfItems)
        {
            noeSenfTitle.add(gorohModel.getNameGoroh());
        }

        customSpinner.showSpinner((Activity)context, noeSenfTitle, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (noeSenfItems.size() > 0)
                {
                    edttxtinputNoeSenf.setText(noeSenfTitle.get(selectedIndex));
                    noeSenfId = noeSenfItems.get(selectedIndex).getCcGoroh().toString();
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetNoeHamlItems(final ArrayList<Integer> itemIds , final ArrayList<String> itemTitle)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner((Activity)context, itemTitle, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (itemTitle.size() > 0)
                {
                    edttxtinputNoeHaml.setText(itemTitle.get(selectedIndex));
                    noeHamlId = itemIds.get(selectedIndex).toString();
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetNoeVosolItems(final ArrayList<Integer> itemIds , final ArrayList<String> itemTitle)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner((Activity)context, itemTitle, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                if (itemTitle.size() > 0)
                {
                    edttxtinputNoeVosol.setText(itemTitle.get(selectedIndex));
                    noeVosolId = itemIds.get(selectedIndex).toString();
                }
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onErrorFirstName()
    {
        txtinputFirstName.setError(getResources().getString(R.string.errorFirstName));
    }

    @Override
    public void onErrorLastName()
    {
        txtinputLastName.setError(getResources().getString(R.string.errorLastName));
    }

    @Override
    public void onErrorBirthDate()
    {
        txtinputBirthDate.setError(getResources().getString(R.string.errorBirthDate));
    }

    @Override
    public void onErrorTabloName()
    {
        txtinputTabloName.setError(getResources().getString(R.string.errorTabloName));
    }

    @Override
    public void onErrorNationalCode()
    {
        txtinputNationalCode.setError(getResources().getString(R.string.errorNationalCode));
    }

    @Override
    public void onErrorMobile()
    {
        txtinputMobile.setError(getResources().getString(R.string.errorMobile));
    }

    @Override
    public void onErrorNoeShakhsiat()
    {
        txtinputNoeShakhsiat.setError(getResources().getString(R.string.errorNoeShakhsiat));
    }

    @Override
    public void onErrorNoeFaaliat()
    {
        txtinputNoeFaaliat.setError(getResources().getString(R.string.errorNoeFaaliat));
    }

    @Override
    public void onErrorNoeSenf()
    {
        txtinputNoeSenf.setError(getResources().getString(R.string.errorNoeSenf));
    }

    @Override
    public void onErrorNoeVosol()
    {
        txtinputNoeVosol.setError(getResources().getString(R.string.errorNoeVosol));
    }

    @Override
    public void onErrorNoeHaml()
    {
        txtinputNoeHaml.setError(getResources().getString(R.string.errorNoeHaml));
    }

    @Override
    public void onErrorRotbeh()
    {
        txtinputMoshtaryRotbeh.setError(getResources().getString(R.string.errorRotbeh));
    }

	@Override
    public void onErrorMasahateMaghaze()
    {
        txtinputMasahateMaghazeh.setError(getResources().getString(R.string.errorMasahateMaghazehEmpty));
    }
	
    @Override
    public void onGetBaseInfoFromShared(AddCustomerInfoModel addCustomerInfoModel)
    {
        listener.sendData(addCustomerInfoModel);
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showMessageAlert((Activity)context, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        customAlertDialog.showToast((Activity)context, context.getResources().getString(resId), Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }


    private void bindTextInputLayouts(View view)
    {
        txtinputFirstName = (CustomTextInputLayout) view.findViewById(R.id.txtinputFirstName);
        txtinputLastName = (CustomTextInputLayout) view.findViewById(R.id.txtinputLastName);
        txtinputBirthDate = (CustomTextInputLayout)view.findViewById(R.id.txtinputBirthDate);
        txtinputTabloName = (CustomTextInputLayout) view.findViewById(R.id.txtinputTabloName);
        txtinputNationalCode = (CustomTextInputLayout) view.findViewById(R.id.txtinputNationalCode);
        txtinputMobile = (CustomTextInputLayout) view.findViewById(R.id.txtinputMobile);
        txtinputAnbar = (CustomTextInputLayout) view.findViewById(R.id.txtinputAnbar);
        txtinputMasahateMaghazeh = (CustomTextInputLayout) view.findViewById(R.id.txtinputMasahateMaghazeh);
        txtinputMoshtaryRotbeh = (CustomTextInputLayout) view.findViewById(R.id.txtinputMoshtaryRotbeh);
        txtinputNoeFaaliat = (CustomTextInputLayout) view.findViewById(R.id.txtinputNoeFaaliat);
        txtinputNoeHaml = (CustomTextInputLayout) view.findViewById(R.id.txtinputNoeHaml);
        txtinputNoeShakhsiat = (CustomTextInputLayout) view.findViewById(R.id.txtinputNoeShakhsiat);
        txtinputNoeSenf = (CustomTextInputLayout) view.findViewById(R.id.txtinputNoeSenf);
        txtinputNoeVosol = (CustomTextInputLayout) view.findViewById(R.id.txtinputNoeVosol);
        txtinputSaateVisit = (CustomTextInputLayout) view.findViewById(R.id.txtinputSaateVisit);
        txtinputCodeEghtesady = (CustomTextInputLayout) view.findViewById(R.id.txtinputCodeEghtesady);
    }

    private void bindEditTextInput(View view)
    {
        edttxtinputFirstName = (EditText)view.findViewById(R.id.txtFirstName);
        edttxtinputLastName = (EditText)view.findViewById(R.id.txtLastName);
        edttxtinputBirthDate = (EditText)view.findViewById(R.id.txtBirthDate);
        edttxtinputTabloName = (EditText)view.findViewById(R.id.txtTabloName);
        edttxtinputNationalCode = (EditText)view.findViewById(R.id.txtNationalCode);
        edttxtinputMobile = (EditText)view.findViewById(R.id.txtMobile);
        edttxtinputAnbar = (EditText)view.findViewById(R.id.txtAnbar);
        edttxtinputMasahateMaghazeh = (EditText)view.findViewById(R.id.txtMasahateMaghazeh);
        edttxtinputRotbeh = (EditText)view.findViewById(R.id.txtMoshtaryRotbeh);
        edttxtinputNoeFaaliat = (EditText)view.findViewById(R.id.txtNoeFaaliat);
        edttxtinputNoeHaml = (EditText)view.findViewById(R.id.txtNoeHaml);
        edttxtinputNoeShakhsiat = (EditText)view.findViewById(R.id.txtNoeShakhsiat);
        edttxtinputNoeSenf = (EditText)view.findViewById(R.id.txtNoeSenf);
        edttxtinputNoeVosol = (EditText)view.findViewById(R.id.txtNoeVosol);
        edttxtinputSaateVisit = (EditText)view.findViewById(R.id.txtSaateVisit);
        edttxtCodeEghtesady = (EditText)view.findViewById(R.id.txtCodeEghtesady);
    }

    private void setTypeFace()
    {
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

        edttxtinputFirstName.setTypeface(font);
        edttxtinputLastName.setTypeface(font);
        edttxtinputBirthDate.setTypeface(font);
        edttxtinputTabloName.setTypeface(font);
        edttxtinputNationalCode.setTypeface(font);
        edttxtinputMobile.setTypeface(font);
        edttxtinputAnbar.setTypeface(font);
        edttxtinputMasahateMaghazeh.setTypeface(font);
        edttxtinputRotbeh.setTypeface(font);
        edttxtinputNoeFaaliat.setTypeface(font);
        edttxtinputNoeHaml.setTypeface(font);
        edttxtinputNoeShakhsiat.setTypeface(font);
        edttxtinputNoeSenf.setTypeface(font);
        edttxtinputNoeVosol.setTypeface(font);
        edttxtinputSaateVisit.setTypeface(font);
        edttxtCodeEghtesady.setTypeface(font);

        txtinputFirstName.setTypeface(font);
        txtinputLastName.setTypeface(font);
        txtinputBirthDate.setTypeface(font);
        txtinputTabloName.setTypeface(font);
        txtinputNationalCode.setTypeface(font);
        txtinputMobile.setTypeface(font);
        txtinputAnbar.setTypeface(font);
        txtinputMasahateMaghazeh.setTypeface(font);
        txtinputMoshtaryRotbeh.setTypeface(font);
        txtinputNoeFaaliat.setTypeface(font);
        txtinputNoeHaml.setTypeface(font);
        txtinputNoeShakhsiat.setTypeface(font);
        txtinputNoeSenf.setTypeface(font);
        txtinputNoeVosol.setTypeface(font);
        txtinputSaateVisit.setTypeface(font);
        txtinputCodeEghtesady.setTypeface(font);

        btnApply.setTypeface(font);
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
                edttxtinputSaateVisit.setText(String.format("%1$s:%2$s" , hour , min));
            }
        },hour , min , true);
        timePickerDialog.show(((Activity)context).getFragmentManager() , "TimePickerDialog");
    }


    private void checkData()
    {
        txtinputFirstName.setError(null);
        txtinputLastName.setError(null);
        txtinputBirthDate.setError(null);
        txtinputTabloName.setError(null);
        txtinputNationalCode.setError(null);
        txtinputMobile.setError(null);
        txtinputNoeFaaliat.setError(null);
        txtinputNoeSenf.setError(null);
        txtinputNoeShakhsiat.setError(null);
        txtinputNoeHaml.setError(null);
        txtinputNoeVosol.setError(null);
        txtinputMoshtaryRotbeh.setError(null);
		txtinputMasahateMaghazeh.setError(null);										

        addCustomerInfoModel.setFirstName(edttxtinputFirstName.getText().toString().trim());
        addCustomerInfoModel.setLastName(edttxtinputLastName.getText().toString().trim());
        addCustomerInfoModel.setBirthDate(edttxtinputBirthDate.getText().toString().trim());
        addCustomerInfoModel.setTabloName(edttxtinputTabloName.getText().toString().trim());
        addCustomerInfoModel.setNationalCode(edttxtinputNationalCode.getText().toString().trim());
        addCustomerInfoModel.setMobile(edttxtinputMobile.getText().toString().trim());
        addCustomerInfoModel.setNoeShakhsiatId(noeShakhsiatId);
        addCustomerInfoModel.setNoeShakhsiatTitle(edttxtinputNoeShakhsiat.getText().toString().trim());
        addCustomerInfoModel.setNoeFaaliatId(noeFaaliatId);
        addCustomerInfoModel.setNoeFaaliatTitle(edttxtinputNoeFaaliat.getText().toString().trim());
        addCustomerInfoModel.setNoeSenfId(noeSenfId);
        addCustomerInfoModel.setNoeSenfTitle(edttxtinputNoeSenf.getText().toString().trim());
        addCustomerInfoModel.setRotbeId(rotbeId);
        addCustomerInfoModel.setRotbeTitle(edttxtinputRotbeh.getText().toString().trim());
        addCustomerInfoModel.setNoeHamlId(noeHamlId);
        addCustomerInfoModel.setNoeHamlTitle(edttxtinputNoeHaml.getText().toString().trim());
        addCustomerInfoModel.setNoeVosolId(noeVosolId);
        addCustomerInfoModel.setNoeVosolTitle(edttxtinputNoeVosol.getText().toString().trim());
        addCustomerInfoModel.setAnbarId(anbarId);
        addCustomerInfoModel.setAnbarTitle(edttxtinputAnbar.getText().toString().trim());
        addCustomerInfoModel.setMasahatMaghaze(edttxtinputMasahateMaghazeh.getText().toString().trim());
        addCustomerInfoModel.setSaateVisit(edttxtinputSaateVisit.getText().toString().trim());
        addCustomerInfoModel.setCodeEghtesady(edttxtCodeEghtesady.getText().toString().trim());
        mPresenter.checkCustomerBaseInfo(addCustomerInfoModel, requireCodeMeli, requireMobile, requireMasahat);
    }

    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_arrow_down);
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
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_arrow_down);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerBaseInfoFragment.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(AddCustomerBaseInfoMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddCustomerBaseInfoPresenter(view);
            stateMaintainer.put(AddCustomerBaseInfoMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerBaseInfoFragment.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(AddCustomerBaseInfoMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddCustomerBaseInfoMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerBaseInfoFragment.class.getSimpleName(), "reinitialize", "");
            }
        }
    }

    public void showDatePickerAlert() {
        PersianCalendar persianCalendar = new PersianCalendar();
        persianCalendar.setPersianDate(1370,1,1);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            monthOfYear++;
                            String month = monthOfYear < 10 ? "0" + monthOfYear : String.valueOf(monthOfYear);
                            String day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                            selectedDate = getResources().getString(R.string.dateWithSplashFormat, String.valueOf(year), month, day);
                            edttxtinputBirthDate.setText(selectedDate);


                        } catch (Exception exception) {
                            exception.printStackTrace();
                            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", GetProgramActivity.class.getSimpleName(), "showDatePickerAlert", "onDateSet");
                        }
                    }
                }, persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }


}


