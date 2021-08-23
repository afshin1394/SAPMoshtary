package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;


public class AddCustomerDocsActivity extends AppCompatActivity implements AddCustomerDocsMVP.RequiredViewOps
{


    private final int REQUEST_CODE_NATIONAL_CARD = 1;
    private final int REQUEST_CODE_JAVAZE_KASB = 2;
    private final int REQUEST_CODE_DASTE_CHECK = 3;
    private final int REQUEST_CODE_EMZA = 4;
    /**
     * 1 == camera
     * 2 == gallery
     */
    private int choiceUploadImage = 0;

    private boolean nationalCardStatus;
    private boolean javazehKasbStatus;
    private boolean dastehCheckStatus;
    private boolean emzaStatus;
    private boolean isOld;

    private CustomAlertDialog customAlertDialog;
    private int ccMoshtary;

    private AddCustomerDocsMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;


    @BindView(R.id.imgBack)
    ImageView imgviewBack;
    @BindView(R.id.imgNationalCard)
    ImageView imgSelectNationalCard;
    @BindView(R.id.imgJavazeKasb)
    ImageView imgSelectJavazeKasb;
    @BindView(R.id.imgDasteCheck)
    ImageView imgSelectDasteCheck;
    @BindView(R.id.imgEmaza)
    ImageView imgSelectEmza;


    @OnClick(R.id.lblSelectNationalCodeCamera)
    public void selectNationalCodeCamera(){
        if (isOld)
        {
            showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        } else {
            openCamera(REQUEST_CODE_NATIONAL_CARD);
        }
    }
    @OnClick(R.id.lblSelectNationalCodeGallery)
    public void selectNationalCodeGallery(){
        if (isOld)
        {
            showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }else {
            openGallery(REQUEST_CODE_NATIONAL_CARD);
        }
    }
    @OnClick(R.id.lblSelectJavazeKasbCamera)
    public void selectJavazeKasbCamera(){
        if (isOld)
        {
            showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }else {
            openCamera(REQUEST_CODE_JAVAZE_KASB);
        }
    }
    @OnClick(R.id.lblSelectJavazeKasbGallery)
    public void selectJavazeKasbGallery(){
        if (isOld)
        {
            showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }else {
            openGallery(REQUEST_CODE_JAVAZE_KASB);
        }
    }
    @OnClick(R.id.lblSelectDasteCheckCamera)
    public void selectDasteCheckCamera(){
        if (isOld)
        {
            showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }else {
            openCamera(REQUEST_CODE_DASTE_CHECK);
        }
    }
    @OnClick(R.id.lblSelectDasteCheckGallery)
    public void selectDasteCheckGallery(){
        if (isOld)
        {
            showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }else {
            openGallery(REQUEST_CODE_DASTE_CHECK);
        }
    }

    @OnClick(R.id.lblSelectEmazaCamera)
    public void selectEmzaCamera(){
        if (isOld)
        {
            showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }else {
            openCamera(REQUEST_CODE_EMZA);
        }
    }
    @OnClick(R.id.lblSelectEmazaGallery)
    public void selectEmzaGallery(){
        if (isOld)
        {
            showToast(R.string.errorCantAddForSendedCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }else {
            openGallery(REQUEST_CODE_EMZA);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_docs);
        ButterKnife.bind(this);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);



        customAlertDialog = new CustomAlertDialog(AddCustomerDocsActivity.this);

        stateMaintainer = new StateMaintainer(getSupportFragmentManager() , TAG , AddCustomerDocsActivity.this);
        mPresenter = new AddCustomerDocsPresenter(this);

        Intent intent = getIntent();
        ccMoshtary = intent.getIntExtra("ccmoshtary" , -1);
        if (ccMoshtary == -1)
        {
            customAlertDialog.showMessageAlert(AddCustomerDocsActivity.this, true, getResources().getString(R.string.error), getResources().getString(R.string.errorSelectCustomer), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }

        mPresenter.getImageStatus(ccMoshtary);


        imgSelectNationalCard.setOnClickListener(v -> mPresenter.getNationalCardImage(ccMoshtary));

        imgSelectJavazeKasb.setOnClickListener(v -> mPresenter.getJavazeKasbImage(ccMoshtary));

        imgSelectDasteCheck.setOnClickListener(v -> mPresenter.getDasteCheckImage(ccMoshtary));

        imgSelectEmza.setOnClickListener(v -> mPresenter.getEmzaImage(ccMoshtary));

        imgviewBack.setOnClickListener(v -> AddCustomerDocsActivity.this.finish());


    }


    public void openGallery(int requestCode){
        choiceUploadImage =2;
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , requestCode);
    }


    public void openCamera(int requestCode)
    {
        choiceUploadImage =1;
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
                if (choiceUploadImage == 1){
                    bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                } else if (choiceUploadImage == 2){
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                }
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
                else if (requestCode == REQUEST_CODE_EMZA)
                {
                    mPresenter.checkEmzaImage(ccMoshtary , new PubFunc().new ImageUtils().convertBitmapToByteArray(AddCustomerDocsActivity.this, bitmap , 50 ));
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
    public void onGetImageStatus(boolean savedNationalCard, boolean savedJavazeKasb, boolean savedDasteCheck,boolean savedEmza, boolean isOld)
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
        if (savedEmza)
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
    public void onSuccessSavedEmzaImage()
    {
        try
        {
            emzaStatus = true;
            imgSelectEmza.setImageResource(R.drawable.ic_success);
        }
        catch (Exception exception)
        {
            showToast(R.string.errorSelectImage , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsActivity", "onSuccessSavedEmzaImage","");
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
    public void onGetEmzaImage(final MoshtaryPhotoPPCModel moshtaryPhotoPPCModel)
    {
        customAlertDialog.showImage(AddCustomerDocsActivity.this, moshtaryPhotoPPCModel.getImageMadrak(), true, new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                mPresenter.deleteEmzaImage(moshtaryPhotoPPCModel.getCcMoshtaryPhoto());
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
    public void onSuccessDeletedEmzaImage()
    {
        dastehCheckStatus = false;
        imgSelectEmza.setImageResource(R.drawable.ic_signature);
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


}
