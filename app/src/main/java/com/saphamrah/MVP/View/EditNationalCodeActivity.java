package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.saphamrah.BaseMVP.EditNationalCodeMVP;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.DAO.MoshtaryTaghiratDAO;
import com.saphamrah.MVP.Presenter.EditNationalCodePresenter;
import com.saphamrah.Model.MoshtaryTaghiratModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import me.anwarshahriar.calligrapher.Calligrapher;

public class EditNationalCodeActivity extends AppCompatActivity implements EditNationalCodeMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , EditNationalCodeActivity.this);
    private EditNationalCodeMVP.PresenterOps mPresenter;

    private CustomTextInputLayout textInputNationalCode;
    private EditText editTextNationalCode;
    private ImageView imgNationalCard;

    private int ccMoshtary;
    private String nationalCode;
    private int codeNoeShakhsiat;
    private CustomAlertDialog customAlertDialog;
    private int GET_NATIONAL_CARD = 100;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_national_code);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgBack = findViewById(R.id.imgBack);
        imgNationalCard = findViewById(R.id.imgNationalCard);
        textInputNationalCode = findViewById(R.id.txtinputLayNationalCode);
        editTextNationalCode = findViewById(R.id.txtNationalCode);
        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        Button btnApply = findViewById(R.id.btnApply);
        customAlertDialog = new CustomAlertDialog(EditNationalCodeActivity.this);

        startMVPOps();

        Intent intent = getIntent();
        ccMoshtary = intent.getIntExtra("ccMoshtary" , -1);
        nationalCode = intent.getStringExtra("nationalCode");
        codeNoeShakhsiat = intent.getIntExtra("codeNoeShakhsiat" , -1);

        if (nationalCode == null || ccMoshtary <= 0 || codeNoeShakhsiat <= 0)
        {
            customAlertDialog.showMessageAlert(EditNationalCodeActivity.this, true, getResources().getString(R.string.error), getResources().getString(R.string.errorGetData), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }

        editTextNationalCode.setText(nationalCode);



        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(GET_NATIONAL_CARD);
            }
        });


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null)
                {
                    Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth() , bitmap.getHeight() , bitmap.getConfig());
                    if (bitmap.sameAs(emptyBitmap))
                    {
                        showToast(R.string.errorNationalCardImage , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
                    }
                    else
                    {
                        mPresenter.checkNationalCode(ccMoshtary,codeNoeShakhsiat, editTextNationalCode.getText().toString(), new PubFunc().new ImageUtils().convertBitmapToByteArray(EditNationalCodeActivity.this, bitmap, Constants.BITMAP_TO_BYTE_QUALITY()));
                    }
                }
                else
                {
                    showToast(R.string.errorNationalCardImage , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
                }
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNationalCodeActivity.this.finish();
            }
        });

    }


    private void openCamera(int requestCode)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null)
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
                    if (requestCode == GET_NATIONAL_CARD)
                    {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        imgNationalCard.setImageBitmap(bitmap);
                        //mPresenter.checkNationalCardImage(ccMoshtary , new PubFunc().new ImageUtils().convertBitmapToByteArray(AddCustomerDocsActivity.this, bitmap , Constants.BITMAP_TO_BYTE_QUALITY()));
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                //mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerDocsFragment", "onActivityResult", "");
            }
        }
    }


    @Override
    public Context getAppContext()
    {
        return EditNationalCodeActivity.this;
    }

    @Override
    public void onSuccessInsert()
    {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK , returnIntent);
        EditNationalCodeActivity.this.finish();
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(EditNationalCodeActivity.this, getResources().getString(resId), messageType , duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "EditNationalCodeActivity", "startMVPOps", "");
        }
    }


    private void initialize(EditNationalCodeMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new EditNationalCodePresenter(view);
            stateMaintainer.put(EditNationalCodeMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "EditNationalCodeActivity", "initialize", "");
        }
    }


    private void reinitialize(EditNationalCodeMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(EditNationalCodeMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "EditNationalCodeActivity", "reinitialize", "");
            }
        }
    }

}
