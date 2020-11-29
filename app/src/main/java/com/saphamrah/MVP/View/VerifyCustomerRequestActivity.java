package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.BaseMVP.VerifyCustomerRequestMVP;
import com.saphamrah.CustomView.BottomBar;
import com.saphamrah.CustomView.DrawingView;
import com.saphamrah.MVP.Presenter.VerifyCustomerRequestPresenter;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import me.anwarshahriar.calligrapher.Calligrapher;

public class VerifyCustomerRequestActivity extends AppCompatActivity implements VerifyCustomerRequestMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , VerifyCustomerRequestActivity.this);
    VerifyCustomerRequestMVP.PresenterOps mPresenter;

    private DrawingView drawingView;
    private CustomAlertDialog customAlertDialog;
    private int ccMoshtary;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_customer_request);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabSave = findViewById(R.id.fabSave);
        FloatingActionButton fabClearScreen = findViewById(R.id.fabCls);
        FloatingActionButton fabShowInvoice = findViewById(R.id.fabShowInvoice);
        new BottomBar(VerifyCustomerRequestActivity.this, 5, new BottomBar.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mPresenter.checkBottomBarClick(position);
            }
        });

        customAlertDialog = new CustomAlertDialog(VerifyCustomerRequestActivity.this);
        initialSignLayout();
        startMVPOps();

        Intent getIntent = getIntent();
        ccMoshtary = getIntent.getIntExtra("ccMoshtary" , -1);

        mPresenter.getAgreementContent(ccMoshtary);


        fabClearScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                drawingView.clearCanvas();
            }
        });


        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                checkSaveSignPic();
            }
        });


        fabShowInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getccDarkhastFaktor();
            }
        });


    }


    @Override
    public Context getAppContext()
    {
        return VerifyCustomerRequestActivity.this;
    }

    @Override
    public void openBarkhordAvalieActivity()
    {
        Intent intent = new Intent(VerifyCustomerRequestActivity.this , BarkhordAvalieForoshandehActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        VerifyCustomerRequestActivity.this.finish();
    }

    @Override
    public void onGetAgreementContent(String text)
    {
        TextView lblAgreementContent = findViewById(R.id.lbl);
        lblAgreementContent.setText(text);
    }

    @Override
    public void onSuccessInsertCustomerSign()
    {
        customAlertDialog.showMessageAlert(VerifyCustomerRequestActivity.this, getResources().getString(R.string.success), getResources().getString(R.string.successfullyDoneOps), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                openTemporaryRequestsList();
            }
        });
    }

    @Override
    public void openFaktorDetailActivity(long ccDarkhastFaktor)
    {
        Intent intent = new Intent(VerifyCustomerRequestActivity.this , FaktorDetailsActivity.class);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("sourceActivity" , "VerifyCustomerRequestActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    private void openTemporaryRequestsList()
    {
        Intent intent = new Intent(VerifyCustomerRequestActivity.this , TemporaryRequestsListActivity.class);
        intent.putExtra("requests" , true);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        VerifyCustomerRequestActivity.this.finish();
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(VerifyCustomerRequestActivity.this, getResources().getString(resId), messageType, duration);
    }

    private void initialSignLayout()
    {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        LinearLayout laySign = (LinearLayout) findViewById(R.id.laySign);
        drawingView = new DrawingView(this, mPaint);
        laySign.addView(drawingView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void checkSaveSignPic()
    {
        Bitmap bitmap = drawingView.getBitmap();
        Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        emptyBitmap.eraseColor(getResources().getColor(R.color.colorWhite));
        if (bitmap.sameAs(emptyBitmap))
        {
            showToast(R.string.errorEmptyEmzaMoshtary, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mPresenter.checkSaveBitmap(ccMoshtary, new PubFunc().new ImageUtils().convertBitmapToByteArray(VerifyCustomerRequestActivity.this, bitmap, 100));
        }
        bitmap.recycle();
        emptyBitmap.recycle();
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "VerifyCustomerRequestActivity", "startMVPOps", "");
        }
    }


    private void initialize(VerifyCustomerRequestMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new VerifyCustomerRequestPresenter(view);
            stateMaintainer.put(VerifyCustomerRequestMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "VerifyCustomerRequestActivity", "initialize", "");
        }
    }


    private void reinitialize(VerifyCustomerRequestMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(VerifyCustomerRequestMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "VerifyCustomerRequestActivity", "reinitialize", "");
            }
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
