package com.saphamrah.MVP.View;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.saphamrah.BaseMVP.AddDepositCashToBankMVP;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.AddDepositCashToBankPresenter;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.StateMaintainer;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AddDepositCashToBankActivity extends AppCompatActivity implements AddDepositCashToBankMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , AddDepositCashToBankActivity.this);
    private AddDepositCashToBankMVP.PresenterOps mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deposit_cash_to_bank);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);


        ImageView imageViewBack = findViewById(R.id.imgBack);
        CustomTextInputLayout txtinputDate = findViewById(R.id.txtinputDate);
        CustomTextInputLayout txtinputTafkik = findViewById(R.id.txtinputTafkik);
        CustomTextInputLayout txtinputNumber = findViewById(R.id.txtinputNumber);
        CustomTextInputLayout txtinputHesab = findViewById(R.id.txtinputHesab);
        CustomTextInputLayout txtinputPrice = findViewById(R.id.txtinputPrice);
        final EditText editTextDate = findViewById(R.id.txtDate);
        EditText editTextTafkik = findViewById(R.id.txtTafkik);
        EditText editTextNumber = findViewById(R.id.txtNumber);
        EditText editTextHesab = findViewById(R.id.txtHesab);
        EditText editTextPrice = findViewById(R.id.txtPrice);
        Button btnApply = findViewById(R.id.btnApply);


        startMVPOps();


        editTextDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDatePickerAlert(editTextDate);
            }
        });


        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus)
                {
                    showDatePickerAlert(editTextDate);
                }
            }
        });


        btnApply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });


        imageViewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddDepositCashToBankActivity.this.finish();
            }
        });


    }


    @Override
    public Context getAppContext()
    {
        return AddDepositCashToBankActivity.this;
    }


    public void showDatePickerAlert(final EditText editText)
    {
        PersianCalendar persianCalendar = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
                    {
                        try
                        {
                            monthOfYear++;
                            String month = monthOfYear < 10 ? "0" + monthOfYear : String.valueOf(monthOfYear);
                            String day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                            String selectedDate = getResources().getString(R.string.dateWithSplashFormat , String.valueOf(year) , month , day);
                            editText.setText(selectedDate);
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "showDatePickerAlert", "onDateSet");
                        }
                    }
                }, persianCalendar.getPersianYear(), persianCalendar.getPersianMonth() , persianCalendar.getPersianDay());
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
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
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "startMVPOps", "");
        }
    }


    private void initialize(AddDepositCashToBankMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddDepositCashToBankPresenter(view);
            stateMaintainer.put(AddDepositCashToBankMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "initialize", "");
        }
    }


    private void reinitialize(AddDepositCashToBankMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddDepositCashToBankMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "reinitialize", "");
            }
        }
    }


}
