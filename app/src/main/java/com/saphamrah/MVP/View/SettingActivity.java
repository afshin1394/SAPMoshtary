package com.saphamrah.MVP.View;

import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.graphics.drawable.DrawableCompat;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.SettingMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.SettingPresenter;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SettingActivity extends AppCompatActivity implements SettingMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , SettingActivity.this);
    private SettingMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;

    private EditText edttxtPaperSizeForPrint;
    private EditText edttxtPrinterType;
    private EditText edttxtPrintType;
    private EditText editTextMapService;
    private EditText editTextNumberGoodsEachPage;
    private EditText editTextGetProgram;
    private EditText edtTxtSortTreasuryList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgBack = findViewById(R.id.imgBack);
        edttxtPaperSizeForPrint = findViewById(R.id.txtPrinterSize);
        edttxtPrinterType = findViewById(R.id.txtPrinterType);
        edttxtPrintType = findViewById(R.id.txtPrintType);
        editTextMapService = findViewById(R.id.txtMapService);
        edtTxtSortTreasuryList = findViewById(R.id.txtSortTreasuryList);
        editTextNumberGoodsEachPage= findViewById(R.id.txtGoodView);
        editTextGetProgram = findViewById(R.id.txtGetProgramService);

        Button btnClearData = findViewById(R.id.btnClearData);

        startMVPOps();

        customAlertDialog = new CustomAlertDialog(SettingActivity.this);

        mPresenter.getSetting();


        //////////////////////// Paper Size For Print ////////////////////////
        edttxtPaperSizeForPrint.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtPaperSizeForPrint , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getPaperSizesForPrint();
                }
            }
        });
        edttxtPaperSizeForPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPaperSizesForPrint();
            }
        });
        //////////////////////// Paper Size For Print ////////////////////////


        //////////////////////// Printer Type ////////////////////////
        edttxtPrinterType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtPrinterType , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getPrinterTypes();
                }
            }
        });
        edttxtPrinterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPrinterTypes();
            }
        });
        //////////////////////// Printer Type ////////////////////////


        ////////////////////// Print Type ////////////////////////
        edttxtPrintType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtPrintType , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getPrintTypes();
                }
            }
        });
        edttxtPrintType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPrintTypes();
            }
        });
        //////////////////////// Printer Type ////////////////////////

        editTextMapService.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeDrawableLeftTint(editTextMapService , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getMapServiceType();
                }
            }
        });

        editTextMapService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getMapServiceType();
            }
        });


        edtTxtSortTreasuryList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeDrawableLeftTint(edtTxtSortTreasuryList , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getSortTreasuryList();
                }
            }
        });

        edtTxtSortTreasuryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getSortTreasuryList();
            }
        });

        editTextNumberGoodsEachPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getGoodsShowItemNumber();
            }
        });
        editTextGetProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getProgramService();
            }
        });

        editTextNumberGoodsEachPage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                changeDrawableLeftTint(editTextNumberGoodsEachPage , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getGoodsShowItemNumber();
                }
            }
        });

        editTextGetProgram.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                changeDrawableLeftTint(editTextGetProgram , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getProgramService();
                }
            }
        });


        btnClearData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                customAlertDialog.showLogMessageAlert(SettingActivity.this, false, "", getString(R.string.clearDataWarning), Constants.INFO_MESSAGE(), getString(R.string.cancel), getString(R.string.yes), new CustomAlertDialogResponse()
                {
                    @Override
                    public void setOnCancelClick() {}
                    @Override
                    public void setOnApplyClick() { mPresenter.clearData(getPackageName()); }
                });
            }
        });




        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SettingActivity.this.finish();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return SettingActivity.this;
    }

    @Override
    public void onGetSetting(String printerPaperSizeTitle, String printerTypeTitle, String printType,String mapServiceType,String goodItemsCountType,String sortTreasuryList,String getProgramService)
    {
        if (printerPaperSizeTitle != null && printerPaperSizeTitle.trim().length() > 0)
        {
            edttxtPaperSizeForPrint.setText(printerPaperSizeTitle);
        }
        if (printerTypeTitle != null && printerTypeTitle.trim().length() > 0)
        {
            edttxtPrinterType.setText(printerTypeTitle);
        }
        if (printType != null && printType.trim().length() > 0)
        {
            edttxtPrintType.setText(printType);
        }
        if (mapServiceType !=null && mapServiceType.trim().length() >0){
            editTextMapService.setText(mapServiceType);
        }
        if (goodItemsCountType !=null && goodItemsCountType.trim().length() >0){
            editTextNumberGoodsEachPage.setText(goodItemsCountType);
        }
        if (sortTreasuryList !=null && sortTreasuryList.trim().length()>0){
            edtTxtSortTreasuryList.setText(sortTreasuryList);
        }
        if (getProgramService !=null && getProgramService.trim().length()>0){
            editTextGetProgram.setText(getProgramService);
        }
    }

    @Override
    public void onGetPaperSizesForPrint(final ArrayList<String> arrayListTitles, final ArrayList<String> arrayListValues)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(SettingActivity.this, arrayListTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtPaperSizeForPrint.setText(arrayListTitles.get(selectedIndex));
                mPresenter.checkForSavePrinterPaperSize(arrayListValues.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void onGetPrinterTypes(final ArrayList<String> arrayListTitles, final ArrayList<String> arrayListValues)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(SettingActivity.this, arrayListTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtPrinterType.setText(arrayListTitles.get(selectedIndex));
                mPresenter.checkForSavePrinterType(arrayListValues.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void onGetPrintTypes(final ArrayList<String> arrayListTitles, final ArrayList<String> arrayListValues)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(SettingActivity.this, arrayListTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtPrintType.setText(arrayListTitles.get(selectedIndex));
                mPresenter.checkForSavePrintType(arrayListValues.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void onGetMapServiceTypes(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues) {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(SettingActivity.this, arrayListTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                editTextMapService.setText(arrayListTitles.get(selectedIndex));
                mPresenter.checkForSaveMapTypes(arrayListStringValues.get(selectedIndex));
               // BaseApplication.MAP_TYPE=Integer.parseInt(arrayListStringValues.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }


    @Override
    public void onGetShowGoodsItemNumber(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues,ArrayList<String> arrayListImages) {

        ArrayList<Integer> listImages=new ArrayList<>();
            listImages.add(R.drawable.ic_1section);
            listImages.add(R.drawable.ic_2section);
            listImages.add(R.drawable.ic_4section);



        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(SettingActivity.this, arrayListTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {

                editTextNumberGoodsEachPage.setText(arrayListTitles.get(selectedIndex));
                editTextNumberGoodsEachPage.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_arrow_down),null,getResources().getDrawable(listImages.get(selectedIndex)),null);
                mPresenter.checkForSaveShowItemNumbers(arrayListStringValues.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(SettingActivity.this, getString(resId), messageType, duration);
    }

    @Override
    public void showAlertDialog(int resId, int messageType)
    {
        customAlertDialog.showMessageAlert(SettingActivity.this, false, "", getString(resId), messageType, getString(R.string.apply));
    }

    @Override
    public void onGetSortTreasuryList(ArrayList<String> arrayListTitles, ArrayList<String> arrayListStringValues) {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(SettingActivity.this, arrayListTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edtTxtSortTreasuryList.setText(arrayListTitles.get(selectedIndex));
                mPresenter.checkSortTreasuryList(arrayListStringValues.get(selectedIndex));
                // BaseApplication.MAP_TYPE=Integer.parseInt(arrayListStringValues.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void onGetProgramService(ArrayList<String> arrayListTitle, ArrayList<String> arrayListValue) {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(SettingActivity.this, arrayListTitle, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                editTextGetProgram.setText(arrayListTitle.get(selectedIndex));
                mPresenter.checkGetProgramService(arrayListValue.get(selectedIndex));
                // BaseApplication.MAP_TYPE=Integer.parseInt(arrayListStringValues.get(selectedIndex));
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
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(SettingActivity.this, R.drawable.ic_arrow_down);
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
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(SettingActivity.this, R.drawable.ic_arrow_down);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SettingActivity", "startMVPOps", "");
        }
    }


    private void initialize(SettingMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new SettingPresenter(view);
            stateMaintainer.put(SettingMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SettingActivity", "initialize", "");
        }
    }


    private void reinitialize(SettingMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(SettingMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SettingActivity", "reinitialize", "");
            }
        }
    }








}
