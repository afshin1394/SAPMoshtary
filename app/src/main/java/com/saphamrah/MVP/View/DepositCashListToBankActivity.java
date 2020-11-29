package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.BaseMVP.DepositCashListToBankMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.DepositCashListToBankPresenter;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.TafkikJozeModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import me.anwarshahriar.calligrapher.Calligrapher;

public class DepositCashListToBankActivity extends AppCompatActivity implements DepositCashListToBankMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , DepositCashListToBankActivity.this);
    private DepositCashListToBankMVP.PresenterOps mPresenter;

    private EditText editTextTafkikJozeNumber;
    private EditText editTextShomareHesab;
    private EditText editTextMablaghMandehVajh;
    private CustomAlertDialog customAlertDialog;
    private CustomSpinner customSpinner;
    private List<TafkikJozeModel> tafkikJozeModels;
    private List<String> tafkikJozeNumbers;
    private List<MarkazShomarehHesabModel> markazShomarehHesabModels;
    private List<String> shomareHesabTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_cash_list_to_bank);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);


        ImageView imgviewBack = findViewById(R.id.imgBack);
        editTextTafkikJozeNumber = findViewById(R.id.txtTafkikJozeNumber);
        editTextShomareHesab = findViewById(R.id.txtShomareHesab);
        editTextMablaghMandehVajh = findViewById(R.id.txtMablaghMandehVajhNaghd);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        FloatingActionButton fabBanksInfo = findViewById(R.id.fabBanksInfo);

        customAlertDialog = new CustomAlertDialog(DepositCashListToBankActivity.this);
        customSpinner = new CustomSpinner();
        tafkikJozeModels = new ArrayList<>();
        tafkikJozeNumbers = new ArrayList<>();
        markazShomarehHesabModels = new ArrayList<>();
        shomareHesabTitles = new ArrayList<>();

        startMVPOps();

        mPresenter.getAllTafkik();
        mPresenter.getAllShomareHesab();

        editTextTafkikJozeNumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSpinnerTafkikJozeNumbers();
            }
        });

        editTextTafkikJozeNumber.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(editTextTafkikJozeNumber , hasFocus);
                if (hasFocus)
                {
                    openSpinnerTafkikJozeNumbers();
                }
            }
        });


        fabBanksInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                startActivity(new Intent(DepositCashListToBankActivity.this , BanksInfoActivity.class));
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                startActivity(new Intent(DepositCashListToBankActivity.this , AddDepositCashToBankActivity.class));
            }
        });


        imgviewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DepositCashListToBankActivity.this.finish();
            }
        });


    }


    @Override
    public Context getAppContext()
    {
        return DepositCashListToBankActivity.this;
    }

    @Override
    public void showAllTafkik(ArrayList<TafkikJozeModel> tafkikJozeModels)
    {
        this.tafkikJozeModels = tafkikJozeModels;
        for (TafkikJozeModel tafkikJozeModel : tafkikJozeModels)
        {
            tafkikJozeNumbers.add(String.valueOf(tafkikJozeModel.getShomarehTafkikJoze()));
        }
        editTextTafkikJozeNumber.setText(tafkikJozeNumbers.get(0));
        mPresenter.checkTafkikForMablaghMandehVajh(tafkikJozeModels.get(0).getCcTafkikJoze());
    }

    @Override
    public void showAlertNotFoundTafkik()
    {
        customAlertDialog.showMessageAlert(DepositCashListToBankActivity.this, true, "", getString(R.string.notFoundTafkik), Constants.INFO_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showAlertNotSelectedTafkik()
    {
        customAlertDialog.showMessageAlert(DepositCashListToBankActivity.this, false, "", getString(R.string.notSelectedTafkik), Constants.INFO_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showAllShomareHesab(List<MarkazShomarehHesabModel> markazShomarehHesabModels)
    {
        this.markazShomarehHesabModels = markazShomarehHesabModels;
        for (MarkazShomarehHesabModel model : markazShomarehHesabModels)
        {
            shomareHesabTitles.add(String.format("%1$s - %2$s", model.getNameBank(), model.getShomarehHesab()));
        }
        editTextShomareHesab.setText(shomareHesabTitles.get(0));
    }

    @Override
    public void showAlertNotFoundShomareHesab()
    {
        customAlertDialog.showMessageAlert(DepositCashListToBankActivity.this, true, "", getString(R.string.emptyShomareHesab), Constants.INFO_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showMablaghMandehVajhNaghd(String sumMablaghVojohNaghd)
    {
        editTextMablaghMandehVajh.setText(sumMablaghVojohNaghd);
    }

    private void openSpinnerTafkikJozeNumbers()
    {
        customSpinner.showSpinner(this, tafkikJozeNumbers, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                editTextTafkikJozeNumber.setText(tafkikJozeNumbers.get(selectedIndex));
                mPresenter.checkTafkikForMablaghMandehVajh(tafkikJozeModels.get(selectedIndex).getCcTafkikJoze());
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
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
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "startMVPOps", "");
        }
    }


    private void initialize(DepositCashListToBankMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new DepositCashListToBankPresenter(view);
            stateMaintainer.put(DepositCashListToBankMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "initialize", "");
        }
    }


    private void reinitialize(DepositCashListToBankMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(DepositCashListToBankMVP.PresenterOps.class.getSimpleName());
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
