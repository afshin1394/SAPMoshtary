package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.saphamrah.BaseMVP.OnlineSupportMVP;
import com.saphamrah.MVP.Presenter.OnlineSupportPresenter;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import me.anwarshahriar.calligrapher.Calligrapher;


public class OnlineSupportActivity extends AppCompatActivity implements OnlineSupportMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , OnlineSupportActivity.this);
    OnlineSupportMVP.PresenterOps mPresenter;

    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_support);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        imgBack = findViewById(R.id.imgBack);

        mPresenter.getCrispId();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineSupportActivity.this.finish();
            }
        });

    }


    @Override
    public Context getAppContext() {
        return OnlineSupportActivity.this;
    }

    @Override
    public void onGetCrispId(String crispId)
    {
        WebView webview = findViewById(R.id.webView);

        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);

        String CrispUrl = getResources().getString(R.string.crispURL) + crispId;

        webview.loadUrl(CrispUrl);
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(OnlineSupportActivity.this);
        customAlertDialog.showMessageAlert(OnlineSupportActivity.this, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {

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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "OnlineSupportActivity", "startMVPOps", "");
        }
    }


    private void initialize(OnlineSupportMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new OnlineSupportPresenter(view);
            stateMaintainer.put(OnlineSupportMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "OnlineSupportActivity", "initialize", "");
        }
    }


    private void reinitialize(OnlineSupportMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(OnlineSupportMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "OnlineSupportActivity", "reinitialize", "");
            }
        }
    }



}
