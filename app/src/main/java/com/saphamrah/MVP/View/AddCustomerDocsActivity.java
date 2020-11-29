package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.saphamrah.BaseMVP.AddCustomerDocsMVP;
import com.saphamrah.MVP.Presenter.AddCustomerDocsPresenter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MoshtaryPhotoPPCModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.anwarshahriar.calligrapher.Calligrapher;


public class AddCustomerDocsActivity extends AppCompatActivity implements AddCustomerDocsMVP.RequiredViewOps
{

    private ImageView imgSelectNationalCard;
    private ImageView imgSelectJavazeKasb;
    private ImageView imgSelectDasteCheck;
    private final int REQUEST_CODE_NATIONAL_CARD = 1;
    private final int REQUEST_CODE_JAVAZE_KASB = 2;
    private final int REQUEST_CODE_DASTE_CHECK = 3;
    private boolean nationalCardStatus;
    private boolean javazehKasbStatus;
    private boolean dastehCheckStatus;
    private boolean isOld;

    private CustomAlertDialog customAlertDialog;
    private int ccMoshtary;

    private AddCustomerDocsMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_docs);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgviewBack = findViewById(R.id.imgBack);
        imgSelectNationalCard = findViewById(R.id.imgNationalCard);
        imgSelectJavazeKasb = findViewById(R.id.imgJavazeKasb);
        imgSelectDasteCheck = findViewById(R.id.imgDasteCheck);
        TextView lblNationalCard = findViewById(R.id.lblSelectNationalCode);
        TextView lblJavazeKasb = findViewById(R.id.lblSelectJavazeKasb);
        TextView lblDasteCheck = findViewById(R.id.lblSelectDasteCheck);

        customAlertDialog = new CustomAlertDialog(AddCustomerDocsActivity.this);

        stateMaintainer = new StateMaintainer(getSupportFragmentManager() , TAG , AddCustomerDocsActivity.this);
        startMVPOps();

        Intent intent = getIntent();
        ccMoshtary = intent.getIntExtra("ccmoshtary" , -1);
        if (ccMoshtary == -1)
        {
            customAlertDialog.showMessageAlert(AddCustomerDocsActivity.this, true, getResources().getString(R.string.error), getResources().getString(R.string.errorSelectCustomer), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }

        mPresenter.getImageStatus(ccMoshtary);

        lblNationalCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isOld)
                {
                    showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
                else
                {
                    openCamera(REQUEST_CODE_NATIONAL_CARD);
                }
            }
        });

        lblJavazeKasb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isOld)
                {
                    showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
                else
                {
                    openCamera(REQUEST_CODE_JAVAZE_KASB);
                }
            }
        });

        lblDasteCheck.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isOld)
                {
                    showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
                else
                {
                    openCamera(REQUEST_CODE_DASTE_CHECK);
                }
            }
        });


        imgSelectNationalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getNationalCardImage(ccMoshtary);
            }
        });

        imgSelectJavazeKasb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getJavazeKasbImage(ccMoshtary);
            }
        });

        imgSelectDasteCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getDasteCheckImage(ccMoshtary);
            }
        });


        imgviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustomerDocsActivity.this.finish();
            }
        });


    }


    /*private void openCamera(int requestCode)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent , requestCode);
        }
    }*/



    public void openCamera(int requestCode)
    {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            {
                File photoFile = null;
                try
                {
                    photoFile = createImageFile();
                }
                catch (IOException ex)
                {ex.printStackTrace();}
                if (photoFile != null)
                {
                    Uri photoURI = FileProvider.getUriForFile(AddCustomerDocsActivity.this, "com.saphamrah.imagefileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, requestCode); //add 10 to index for difference of requestcode between gallery and camera
                }
            }
        }
    }

    String mCurrentPhotoPath;
    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Doc_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    Bitmap bitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            try
            {
                //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                if (requestCode == REQUEST_CODE_NATIONAL_CARD)
                {
                    mPresenter.checkNationalCardImage(ccMoshtary , new PubFunc().new ImageUtils().convertBitmapToByteArray(AddCustomerDocsActivity.this, bitmap , 50));
                }
                else if (requestCode == REQUEST_CODE_JAVAZE_KASB)
                {
                    mPresenter.checkJavazehKasbImage(ccMoshtary , new PubFunc().new ImageUtils().convertBitmapToByteArray(AddCustomerDocsActivity.this, bitmap , 50));
                }
                else if (requestCode == REQUEST_CODE_DASTE_CHECK)
                {
                    mPresenter.checkDastehCheckImage(ccMoshtary , new PubFunc().new ImageUtils().convertBitmapToByteArray(AddCustomerDocsActivity.this, bitmap , 50 ));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsFragment", "onActivityResult", "");
            }
        }
    }


    @Override
    public Context getAppContext() {
        return AddCustomerDocsActivity.this;
    }

    @Override
    public void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel) 
    {

    }

    @Override
    public void onGetImageStatus(boolean savedNationalCard, boolean savedJavazeKasb, boolean savedDasteCheck, boolean isOld)
    {
        if (savedNationalCard)
        {
            nationalCardStatus = savedNationalCard;
            imgSelectNationalCard.setImageResource(R.drawable.ic_success);
        }
        if (savedJavazeKasb)
        {
            javazehKasbStatus = savedJavazeKasb;
            imgSelectJavazeKasb.setImageResource(R.drawable.ic_success);
        }
        if (savedDasteCheck)
        {
            dastehCheckStatus = savedDasteCheck;
            imgSelectDasteCheck.setImageResource(R.drawable.ic_success);
        }
        this.isOld = isOld;
    }

    @Override
    public void onSuccessSavedNationalCardImage()
    {
        try
        {
            nationalCardStatus = true;
            imgSelectNationalCard.setImageResource(R.drawable.ic_success);
        }
        catch (Exception exception)
        {
            showToast(R.string.errorSelectImage , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsActivity", "onSuccessSavedNationalCardImage","");
        }
    }

    @Override
    public void onSuccessSavedJavazehKasbImage()
    {
        try
        {
            javazehKasbStatus = true;
            imgSelectJavazeKasb.setImageResource(R.drawable.ic_success);
        }
        catch (Exception exception)
        {
            showToast(R.string.errorSelectImage , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsActivity", "onSuccessSavedJavazehKasbImage","");
        }
    }

    @Override
    public void onSuccessSavedDastehCheckImage()
    {
        try
        {
            dastehCheckStatus = true;
            imgSelectDasteCheck.setImageResource(R.drawable.ic_success);
        }
        catch (Exception exception)
        {
            showToast(R.string.errorSelectImage , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsActivity", "onSuccessSavedDastehCheckImage","");
        }
    }

    @Override
    public void onGetNationalCardImage(final MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        customAlertDialog.showImage(AddCustomerDocsActivity.this, moshtaryPhotoPPCModel.getImageMadrak(), true, new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
            }

            @Override
            public void setOnApplyClick() {
                mPresenter.deleteNationalCardImage(moshtaryPhotoPPCModel.getCcMoshtaryPhoto());
            }
        });
    }

    @Override
    public void onGetJavazeKasbImage(final MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        customAlertDialog.showImage(AddCustomerDocsActivity.this, moshtaryPhotoPPCModel.getImageMadrak(), true, new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                mPresenter.deleteJavazeKasbImage(moshtaryPhotoPPCModel.getCcMoshtaryPhoto());
            }
        });
    }

    @Override
    public void onGetDasteCheckImage(final MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        customAlertDialog.showImage(AddCustomerDocsActivity.this, moshtaryPhotoPPCModel.getImageMadrak(), true, new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                mPresenter.deleteDasteCheckImage(moshtaryPhotoPPCModel.getCcMoshtaryPhoto());
            }
        });
    }

    @Override
    public void onSuccessDeletedNationalCardImage()
    {
        nationalCardStatus = false;
        imgSelectNationalCard.setImageResource(R.drawable.ic_national_card_doc);
    }

    @Override
    public void onSuccessDeletedJavazeKasbImage()
    {
        javazehKasbStatus = false;
        imgSelectJavazeKasb.setImageResource(R.drawable.ic_javaze_kasb);
    }

    @Override
    public void onSuccessDeletedDasteCheckImage()
    {
        dastehCheckStatus = false;
        imgSelectDasteCheck.setImageResource(R.drawable.ic_check_book);
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(AddCustomerDocsActivity.this);
        customAlertDialog.showMessageAlert(AddCustomerDocsActivity.this, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) AddCustomerDocsActivity.this);
        customAlertDialog.showToast(AddCustomerDocsActivity.this, getResources().getString(resId), messageType, duration);
    }


    private void startMVPOps()
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerDocsActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }

    private void initialize(AddCustomerDocsMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddCustomerDocsPresenter(view);
            stateMaintainer.put(AddCustomerDocsMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerDocsActivity.class.getSimpleName(), "initialize", "");
        }
    }

    private void reinitialize(AddCustomerDocsMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddCustomerDocsMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerDocsActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


}
