package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saphamrah.BaseMVP.AddCustomerApplyMVP;
import com.saphamrah.MVP.Presenter.AddCustomerApplyPresenter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AddCustomerApplyFragment extends Fragment implements AddCustomerApplyMVP.RequiredViewOps
{

    private Context context;
    private TextView lblPersonalInfo;
    private TextView lblNameFamily;
    private TextView lblBirthDate;
    private TextView lblNationalCode;
    private TextView lblMobile;
    private TextView lblTabloName;
    private TextView lblMasahateMaghaze;
    private TextView lblAnbar;
    private TextView lblMoshtaryRotbe;
    private TextView lblNoeFaaliat;
    private TextView lblNoeHaml;
    private TextView lblNoeShakhsiat;
    private TextView lblNoeSenf;
    private TextView lblNoeVosol;
    private TextView lblSaateVisitTitle;
    private TextView lblSaateVisit;
    private TextView lblAddressTitle;
    private TextView lblAddress;
    private TextView lblShomareHesabTitle;
    private TextView lblShomareHesab;
    private Button btnOK;
    /*private TextView lblDocsTitle;
    private TextView lblNationalCard;
    private TextView lblJavazeKasb;
    private TextView lblDastehCheck;
    private ImageView imgNationalCard;
    private ImageView imgJavazehKasb;
    private ImageView imgDastehCheck;*/
    private AddCustomerInfoModel addCustomerInfoModel;

    private AddCustomerApplyMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.d("fragment" , "onview restored");
        mPresenter.getAddCustomerInfoModel();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (getView() != null)
            {
                mPresenter.getAddCustomerInfoModel();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.add_customer_apply_fragment , container , false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);
        setFont();

        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , context);
        startMVPOps();

        Log.d("fragment" , "onview created");

        mPresenter.getAddCustomerInfoModel();


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkAddNewCustomer(addCustomerInfoModel);
            }
        });

    }

    private void bindViews(View view)
    {
        lblPersonalInfo = (TextView)view.findViewById(R.id.lblPersonalInfo);
        lblNameFamily = (TextView)view.findViewById(R.id.lblNameFamily);
        lblBirthDate = (TextView)view.findViewById(R.id.lblBirthDate);
        lblNationalCode = (TextView)view.findViewById(R.id.lblNationalCode);
        lblMobile = (TextView)view.findViewById(R.id.lblMobile);
        lblTabloName = (TextView)view.findViewById(R.id.lblTabloName);
        lblMasahateMaghaze = (TextView)view.findViewById(R.id.lblMasahateMaghaze);
        lblAnbar = (TextView)view.findViewById(R.id.lblAnbar);
        lblMoshtaryRotbe = (TextView)view.findViewById(R.id.lblMoshtaryRotbe);
        lblNoeFaaliat = (TextView)view.findViewById(R.id.lblNoeFaaliat);
        lblNoeHaml = (TextView)view.findViewById(R.id.lblNoeHaml);
        lblNoeShakhsiat = (TextView)view.findViewById(R.id.lblNoeShakhsiat);
        lblNoeSenf = (TextView)view.findViewById(R.id.lblNoeSenf);
        lblNoeVosol = (TextView)view.findViewById(R.id.lblNoeVosol);
        lblSaateVisitTitle = (TextView)view.findViewById(R.id.lblSaateVisitTitle);
        lblSaateVisit = (TextView)view.findViewById(R.id.lblSaateVisit);
        lblAddressTitle = (TextView)view.findViewById(R.id.lblAddressTitle);
        lblAddress = (TextView)view.findViewById(R.id.lblAddress);
        lblShomareHesabTitle = (TextView)view.findViewById(R.id.lblShomareHesabTitle);
        lblShomareHesab = (TextView)view.findViewById(R.id.lblShomareHesab);
        btnOK = (Button) view.findViewById(R.id.btnApply);
        /*lblDocsTitle = (TextView)view.findViewById(R.id.lblDocs);
        lblNationalCard = (TextView)view.findViewById(R.id.lblNationalCardImage);
        lblJavazeKasb = (TextView)view.findViewById(R.id.lblJavazeKasbImage);
        lblDastehCheck = (TextView)view.findViewById(R.id.lblDasteCheckImage);
        imgNationalCard = (ImageView)view.findViewById(R.id.imgNationalCard);
        imgJavazehKasb = (ImageView)view.findViewById(R.id.imgJavazeKasb);
        imgDastehCheck = (ImageView)view.findViewById(R.id.imgDasteCheck);*/
    }

    private void setFont()
    {
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        lblPersonalInfo.setTypeface(font);
        lblNameFamily.setTypeface(font);
        lblBirthDate.setTypeface(font);
        lblNationalCode.setTypeface(font);
        lblMobile.setTypeface(font);
        lblTabloName.setTypeface(font);
        lblMasahateMaghaze.setTypeface(font);
        lblAnbar.setTypeface(font);
        lblMoshtaryRotbe.setTypeface(font);
        lblNoeFaaliat.setTypeface(font);
        lblNoeHaml.setTypeface(font);
        lblNoeShakhsiat.setTypeface(font);
        lblNoeSenf.setTypeface(font);
        lblNoeVosol.setTypeface(font);
        lblSaateVisitTitle.setTypeface(font);
        lblSaateVisit.setTypeface(font);
        lblAddressTitle.setTypeface(font);
        lblAddress.setTypeface(font);
        lblShomareHesabTitle.setTypeface(font);
        lblShomareHesab.setTypeface(font);
        btnOK.setTypeface(font);
        /*lblDocsTitle.setTypeface(font);
        lblNationalCard.setTypeface(font);
        lblJavazeKasb.setTypeface(font);
        lblDastehCheck.setTypeface(font);*/
    }


    private void setValueOfViews()
    {
        lblNameFamily.setText(String.format("%1$s : %2$s %3$s" , getResources().getString(R.string.nameFamily) , addCustomerInfoModel.getFirstName() , addCustomerInfoModel.getLastName()));
        lblBirthDate.setText(String.format("%1$s : %2$s " , getResources().getString(R.string.birthDate) , addCustomerInfoModel.getBirthDate()));
        lblNationalCode.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.nationalCode) , addCustomerInfoModel.getNationalCode()));
        lblMobile.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.mobile) , addCustomerInfoModel.getMobile()));
        lblTabloName.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.tabloName) , addCustomerInfoModel.getTabloName()));
        lblMasahateMaghaze.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.masahateMaghazaeh) , addCustomerInfoModel.getMasahatMaghaze()));
        lblAnbar.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.anbar) , addCustomerInfoModel.getAnbarTitle()));
        lblMoshtaryRotbe.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.rotbeh) , addCustomerInfoModel.getRotbeTitle()));
        lblNoeFaaliat.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.noeFaaliat) , addCustomerInfoModel.getNoeFaaliatTitle()));
        lblNoeHaml.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.noeHaml) , addCustomerInfoModel.getNoeHamlTitle()));
        lblNoeShakhsiat.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.noeShakhsiat) , addCustomerInfoModel.getNoeShakhsiatTitle()));
        lblNoeSenf.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.noeSenf) , addCustomerInfoModel.getNoeSenfTitle()));
        lblNoeVosol.setText(String.format("%1$s : %2$s" , getResources().getString(R.string.noeVosol) , addCustomerInfoModel.getNoeVosolTitle()));
        lblSaateVisit.setText(addCustomerInfoModel.getSaateVisit());
        lblSaateVisitTitle.setText(String.format("%1$s : " , getResources().getString(R.string.saateVisit)));


        String addresses = "";
        if (addCustomerInfoModel.getMoshtaryAddressModels() !=  null && addCustomerInfoModel.getMoshtaryAddressModels().size() > 0)
        {
            for (int i = 0 ; i < addCustomerInfoModel.getMoshtaryAddressModels().size() ; i++)
            {
                MoshtaryAddressModel address = addCustomerInfoModel.getMoshtaryAddressModels().get(i);
                if (address.getTelephone() != null && address.getTelephone().trim().length() > 3)
                {
                    addresses = address.getNameNoeAddress() + "\n" + address.getTelephone() + "\n" + address.getAddress() + "\n \n" + addresses;
                }
                else
                {
                    addresses = address.getNameNoeAddress() + "\n" + address.getAddress() + "\n \n" + addresses;
                }
            }
        }
        lblAddress.setText(addresses);

        String shomareHesabs = "";
        if (addCustomerInfoModel.getMoshtaryShomarehHesabModels() !=  null && addCustomerInfoModel.getMoshtaryShomarehHesabModels().size() > 0)
        {
            for (MoshtaryShomarehHesabModel shomarehHesab : addCustomerInfoModel.getMoshtaryShomarehHesabModels())
            {
                shomareHesabs = shomarehHesab.getNameBank() + "\n" + shomarehHesab.getNameNoeHesab() + "\n" + shomarehHesab.getShomarehHesab() + "\n \n" + shomareHesabs;
            }
        }
        lblShomareHesab.setText(shomareHesabs);

        /*if (addCustomerInfoModel.getNationalCardImage() != null && addCustomerInfoModel.getNationalCardImage().length > 0)
        {
            try
            {
                imgNationalCard.setImageResource(R.drawable.ic_success);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }*/

/*        if (addCustomerInfoModel.getJavazeKasbImage() != null && addCustomerInfoModel.getJavazeKasbImage().length > 0)
        {
            try
            {
                *//*byte[] javazekasb = addCustomerInfoModel.getJavazeKasbImage();
                Bitmap bitmapJavazekasb = BitmapFactory.decodeByteArray(javazekasb , 0 , javazekasb.length);
                imgJavazehKasb.setImageBitmap(bitmapJavazekasb);
                Toast.makeText(context, "JavazeKasb size : " + bitmapJavazekasb.getByteCount() , Toast.LENGTH_LONG).show();*//*
                imgJavazehKasb.setImageResource(R.drawable.ic_success);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        if (addCustomerInfoModel.getDastehCheckImage() != null && addCustomerInfoModel.getDastehCheckImage().length > 0)
        {
            try
            {
                *//*byte[] dastehCheck = addCustomerInfoModel.getDastehCheckImage();
                Bitmap bitmapDastehCheck = BitmapFactory.decodeByteArray(dastehCheck , 0 , dastehCheck.length);
                imgDastehCheck.setImageBitmap(bitmapDastehCheck);
                Toast.makeText(context, "DastehCheck size : " + bitmapDastehCheck.getByteCount() , Toast.LENGTH_LONG).show();*//*
                imgDastehCheck.setImageResource(R.drawable.ic_success);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }*/

    }


    @Override
    public Context getAppContext()
    {
        return context;
    }

    @Override
    public void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel)
    {
        Log.d("fragment" , "on get add customer info");
        if (addCustomerInfoModel == null)
        {
            Log.d("fragment" , "addcustomer info model was null");
            this.addCustomerInfoModel = new AddCustomerInfoModel();
        }
        else
        {
            Log.d("fragment" , "addcustomer info model was not null");
            this.addCustomerInfoModel = addCustomerInfoModel;
            setValueOfViews();
        }
    }

    @Override
    public void onErrorFirstName()
    {
        lblNameFamily.setText(String.format("%1$s %2$s", lblNameFamily.getText().toString(), getResources().getString(R.string.errorFirstName)));
        lblNameFamily.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorLastName()
    {
        lblNameFamily.setText(String.format("%1$s %2$s", lblNameFamily.getText().toString(), getResources().getString(R.string.errorLastName)));
        lblNameFamily.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorTabloName()
    {
        lblTabloName.setText(String.format("%1$s %2$s", lblTabloName.getText().toString(), getResources().getString(R.string.errorTabloName)));
        lblTabloName.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorNationalCode()
    {
        lblNationalCode.setText(String.format("%1$s %2$s", lblNationalCode.getText().toString(), getResources().getString(R.string.errorNationalCode)));
        lblNationalCode.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorMobile()
    {
        lblMobile.setText(String.format("%1$s %2$s", lblMobile.getText().toString(), getResources().getString(R.string.errorMobile)));
        lblMobile.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorNoeShakhsiat()
    {
        lblNoeShakhsiat.setText(String.format("%1$s %2$s", lblNoeShakhsiat.getText().toString(), getResources().getString(R.string.errorNoeShakhsiat)));
        lblNoeShakhsiat.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorNoeFaaliat()
    {
        lblNoeFaaliat.setText(String.format("%1$s %2$s", lblNoeFaaliat.getText().toString(), getResources().getString(R.string.errorNoeFaaliat)));
        lblNoeFaaliat.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorNoeSenf()
    {
        lblNoeSenf.setText(String.format("%1$s %2$s", lblNoeSenf.getText().toString(), getResources().getString(R.string.errorNoeSenf)));
        lblNoeSenf.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorNoeVosol()
    {
        lblNoeVosol.setText(String.format("%1$s %2$s", lblNoeVosol.getText().toString(), getResources().getString(R.string.errorNoeVosol)));
        lblNoeVosol.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorNoeHaml()
    {
        lblNoeHaml.setText(String.format("%1$s %2$s", lblNoeHaml.getText().toString(), getResources().getString(R.string.errorNoeHaml)));
        lblNoeHaml.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorEmptyAddress()
    {
        lblAddress.setText(getResources().getString(R.string.errorAddress));
        lblAddress.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorNeedTahvilAddress()
    {
        lblAddressTitle.setText(String.format("%1$s %2$s", getResources().getString(R.string.address), getResources().getString(R.string.errorNeedTahvilAddress)));
        lblAddressTitle.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorNeedDarkhastAddress()
    {
        lblAddressTitle.setText(String.format("%1$s %2$s", getResources().getString(R.string.address), getResources().getString(R.string.errorNeedDarkhastAddress)));
        lblAddressTitle.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorExistAllTypeOfAddress()
    {
        lblAddressTitle.setText(String.format("%1$s %2$s", getResources().getString(R.string.address), getResources().getString(R.string.errorExistAllTypeOfAddress)));
        lblAddressTitle.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorWrongNameSahebHesab()
    {
        lblShomareHesabTitle.setText(String.format("%1$s %2$s", getResources().getString(R.string.shomareHesab), getResources().getString(R.string.errorWrongNameSahebHesab)));
        lblShomareHesabTitle.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onErrorDuplicateShomareHesab()
    {
        lblShomareHesabTitle.setText(String.format("%1$s %2$s", getResources().getString(R.string.shomareHesab), getResources().getString(R.string.errorDuplicateShomareHesab)));
        lblShomareHesabTitle.setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    public void onSuccessInsertNewCustomer(AddCustomerInfoModel addCustomerInfoModel)
    {
        showToast(R.string.successSaveData, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        Intent intent = new Intent();
        Log.d("addcustomer" , "noeSenf : " + addCustomerInfoModel.getNoeSenfTitle());
        intent.putExtra("newcustomer" , addCustomerInfoModel);
        ((Activity)context).setResult(Activity.RESULT_OK , intent);
        ((Activity)context).finish();
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        customAlertDialog.showMessageAlert((Activity)context, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showServerMessage(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId)
    {

    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        customAlertDialog.showToast((Activity)context, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerApplyFragment.class.getSimpleName(), "startMVPOps", "");
        }
    }

    private void initialize(AddCustomerApplyMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddCustomerApplyPresenter(view);
            stateMaintainer.put(AddCustomerApplyMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerApplyFragment.class.getSimpleName(), "initialize", "");
        }
    }

    private void reinitialize(AddCustomerApplyMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddCustomerApplyMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerApplyFragment.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


}
