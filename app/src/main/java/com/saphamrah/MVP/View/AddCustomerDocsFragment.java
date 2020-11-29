package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.saphamrah.BaseMVP.AddCustomerDocsMVP;
import com.saphamrah.MVP.Presenter.AddCustomerDocsPresenter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MoshtaryPhotoPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import me.anwarshahriar.calligrapher.Calligrapher;

import static android.app.Activity.RESULT_OK;

public class AddCustomerDocsFragment extends Fragment implements AddCustomerDocsMVP.RequiredViewOps
{

    private ImageView imgSelectNationalCard;
    private ImageView imgSelectJavazeKasb;
    private ImageView imgSelectDasteCheck;
    private Context context;
    private final int REQUEST_CODE_NATIONAL_CARD = 1;
    private final int REQUEST_CODE_JAVAZE_KASB = 2;
    private final int REQUEST_CODE_DASTE_CHECK = 3;
    private boolean nationalCardStatus;
    private boolean javazehKasbStatus;
    private boolean dastehCheckStatus;
    private boolean isOld;

    //private AddCustomerInfoModel addCustomerInfoModel;

    AddCustomerDocsMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (getView() != null)
            {
                //mPresenter.getImageStatus();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.d("docs" , "on save instance");
        //outState.putParcelable("addCustomerInfoModel" , addCustomerInfoModel);
        outState.putBoolean("nationalCard" , nationalCardStatus);
        outState.putBoolean("javazeKasb" , javazehKasbStatus);
        outState.putBoolean("dasteCheck" , dastehCheckStatus);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
        {
            nationalCardStatus = savedInstanceState.getBoolean("nationalCard");
            javazehKasbStatus = savedInstanceState.getBoolean("javazeKasb");
            dastehCheckStatus = savedInstanceState.getBoolean("dasteCheck");
            onGetImageStatus(nationalCardStatus, javazehKasbStatus, dastehCheckStatus, isOld);
        }
        /*if (savedInstanceState != null)
        {
            addCustomerInfoModel = savedInstanceState.getParcelable("addCustomerInfoModel");
            if (addCustomerInfoModel != null)
            {
                if (addCustomerInfoModel.getNationalCardImage() != null)
                {
                    imgSelectNationalCard.setImageResource(R.drawable.ic_success);
                }
                if (addCustomerInfoModel.getJavazeKasbImage() != null)
                {
                    imgSelectJavazeKasb.setImageResource(R.drawable.ic_success);
                }
                if (addCustomerInfoModel.getDastehCheckImage() != null)
                {
                    imgSelectDasteCheck.setImageResource(R.drawable.ic_success);
                }
            }
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.add_customer_docs_fragment , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Calligrapher calligrapher = new Calligrapher(context);
        calligrapher.setFont(view , context.getResources().getString(R.string.fontPath));

        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

        CardView cardViewNationalCard = (CardView)view.findViewById(R.id.crdviewNationalCode);
        CardView cardViewJavazeKasb = (CardView)view.findViewById(R.id.crdviewJavazeKasb);
        CardView cardViewDasteCheck = (CardView)view.findViewById(R.id.crdviewDasteCheck);
        imgSelectNationalCard = (ImageView) view.findViewById(R.id.imgNationalCard);
        imgSelectJavazeKasb = (ImageView)view.findViewById(R.id.imgJavazeKasb);
        imgSelectDasteCheck = (ImageView)view.findViewById(R.id.imgDasteCheck);
        TextView lblSelectNationalCard = (TextView) view.findViewById(R.id.lblSelectNationalCode);
        TextView lblSelectJavazeKasb = (TextView)view.findViewById(R.id.lblSelectJavazeKasb);
        TextView lblSelectDasteCheck = (TextView)view.findViewById(R.id.lblSelectDasteCheck);

        lblSelectNationalCard.setTypeface(font);
        lblSelectJavazeKasb.setTypeface(font);
        lblSelectDasteCheck.setTypeface(font);


        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , context);
        startMVPOps();

        //mPresenter.getImageStatus();


        cardViewNationalCard.setOnClickListener(new View.OnClickListener()
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

        cardViewJavazeKasb.setOnClickListener(new View.OnClickListener()
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

        cardViewDasteCheck.setOnClickListener(new View.OnClickListener()
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

    }


    private void openCamera(int requestCode)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getPackageManager()) != null)
        {
            startActivityForResult(intent , requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            try
            {
                if (data.getExtras() != null)
                {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    //saveImage(bitmap);
                    if (requestCode == REQUEST_CODE_NATIONAL_CARD)
                    {
                        //mPresenter.checkNationalCardImage(new PubFunc().new ImageUtils().convertBitmapToByteArray(context, bitmap , Constants.BITMAP_TO_BYTE_QUALITY()));
                    }
                    else if (requestCode == REQUEST_CODE_JAVAZE_KASB)
                    {
                        //mPresenter.checkJavazehKasbImage(new PubFunc().new ImageUtils().convertBitmapToByteArray(context, bitmap , Constants.BITMAP_TO_BYTE_QUALITY()));
                    }
                    else if (requestCode == REQUEST_CODE_DASTE_CHECK)
                    {
                        //mPresenter.checkDastehCheckImage(new PubFunc().new ImageUtils().convertBitmapToByteArray(context, bitmap , Constants.BITMAP_TO_BYTE_QUALITY()));
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsFragment", "onActivityResult", "");
            }
        }
    }

    /*private void saveImage(Bitmap bitmap)
    {

        File myDir=new File("/sdcard/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "sapimage-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Override
    public void onGetAddCustomerInfoModel(AddCustomerInfoModel addCustomerInfoModel)
    {
/*        if (addCustomerInfoModel == null)
        {
            this.addCustomerInfoModel = new AddCustomerInfoModel();
        }
        else
        {
            this.addCustomerInfoModel = addCustomerInfoModel;
            if (addCustomerInfoModel.getNationalCardImage() != null && addCustomerInfoModel.getNationalCardImage().length > 0)
            {
                try
                {
                    *//*byte[] nationalCard = addCustomerInfoModel.getNationalCardImage();
                    Bitmap bitmapNationalCard = BitmapFactory.decodeByteArray(nationalCard , 0 , nationalCard.length);
                    imgSelectNationalCard.setImageBitmap(bitmapNationalCard);
                    Toast.makeText(context, "nationalcard size : " + bitmapNationalCard.getByteCount() , Toast.LENGTH_LONG).show();*//*
                    imgSelectNationalCard.setImageResource(R.drawable.ic_success);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }

            if (addCustomerInfoModel.getJavazeKasbImage() != null && addCustomerInfoModel.getJavazeKasbImage().length > 0)
            {
                try
                {
                    *//*byte[] javazekasb = addCustomerInfoModel.getJavazeKasbImage();
                    Bitmap bitmapJavazekasb = BitmapFactory.decodeByteArray(javazekasb , 0 , javazekasb.length);
                    imgSelectJavazeKasb.setImageBitmap(bitmapJavazekasb);
                    Toast.makeText(context, "JavazeKasb size : " + bitmapJavazekasb.getByteCount() , Toast.LENGTH_LONG).show();*//*
                    imgSelectJavazeKasb.setImageResource(R.drawable.ic_success);
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
                    imgSelectDasteCheck.setImageBitmap(bitmapDastehCheck);
                    Toast.makeText(context, "DastehCheck size : " + bitmapDastehCheck.getByteCount() , Toast.LENGTH_LONG).show();*//*
                    imgSelectDasteCheck.setImageResource(R.drawable.ic_success);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }*/
    }

    @Override
    public void onGetImageStatus(boolean savedNationalCard, boolean savedJavazeKasb, boolean savedDasteCheck, boolean isOld)
    {
        if (savedNationalCard)
        {
            imgSelectNationalCard.setImageResource(R.drawable.ic_success);
        }
        else
        {
            imgSelectNationalCard.setImageResource(R.drawable.ic_national_card_doc);
        }

        if (savedJavazeKasb)
        {
            imgSelectJavazeKasb.setImageResource(R.drawable.ic_success);
        }
        else
        {
            imgSelectJavazeKasb.setImageResource(R.drawable.ic_javaze_kasb);
        }

        if (savedDasteCheck)
        {
            imgSelectDasteCheck.setImageResource(R.drawable.ic_success);
        }
        else
        {
            imgSelectDasteCheck.setImageResource(R.drawable.ic_check_book);
        }
        this.isOld = isOld;
    }

    @Override
    public void onSuccessSavedNationalCardImage(/*byte[] nationalCardImage*/)
    {
        try
        {
            imgSelectNationalCard.setImageResource(R.drawable.ic_success);
            /*Bitmap bitmap = new PubFunc().new ImageUtils().convertByteArrayToBitmap(nationalCardImage);
            if (bitmap != null)
            {
                addCustomerInfoModel.setNationalCardImage(nationalCardImage);
                imgSelectNationalCard.setImageBitmap(bitmap);

            }*/
        }
        catch (Exception exception)
        {
            showToast(R.string.errorSelectImage , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsFragment", "onSuccessSavedNationalCardImage","");
        }
    }

    @Override
    public void onSuccessSavedJavazehKasbImage(/*byte[] javazehKasb*/)
    {
        try
        {
            imgSelectJavazeKasb.setImageResource(R.drawable.ic_success);
            /*Bitmap bitmap = new PubFunc().new ImageUtils().convertByteArrayToBitmap(javazehKasb);
            if (bitmap != null)
            {
                addCustomerInfoModel.setJavazeKasbImage(javazehKasb);
                imgSelectJavazeKasb.setImageBitmap(bitmap);
            }*/
        }
        catch (Exception exception)
        {
            showToast(R.string.errorSelectImage , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsFragment", "onSuccessSavedJavazehKasbImage","");
        }
    }

    @Override
    public void onSuccessSavedDastehCheckImage(/*byte[] dastehCheck*/)
    {
        try
        {
            imgSelectDasteCheck.setImageResource(R.drawable.ic_success);
            /*Bitmap bitmap = new PubFunc().new ImageUtils().convertByteArrayToBitmap(dastehCheck);
            if (bitmap != null)
            {
                addCustomerInfoModel.setDastehCheckImage(dastehCheck);
                imgSelectDasteCheck.setImageBitmap(bitmap);
            }*/
        }
        catch (Exception exception)
        {
            showToast(R.string.errorSelectImage , Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsFragment", "onSuccessSavedDastehCheckImage","");
        }
    }


    @Override
    public void onGetNationalCardImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel) {

    }

    @Override
    public void onGetJavazeKasbImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel) {

    }

    @Override
    public void onGetDasteCheckImage(MoshtaryPhotoPPCModel moshtaryPhotoPPCModel) {

    }

    @Override
    public void onSuccessDeletedNationalCardImage() {

    }

    @Override
    public void onSuccessDeletedJavazeKasbImage() {

    }

    @Override
    public void onSuccessDeletedDasteCheckImage() {

    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        customAlertDialog.showMessageAlert((Activity)context, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        customAlertDialog.showToast((Activity)context, getResources().getString(resId), messageType, duration);
    }


    @Override
    public Context getAppContext()
    {
        return context;
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerDocsFragment.class.getSimpleName(), "startMVPOps", "");
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerDocsFragment.class.getSimpleName(), "initialize", "");
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerDocsFragment.class.getSimpleName(), "reinitialize", "");
            }
        }
    }



}
