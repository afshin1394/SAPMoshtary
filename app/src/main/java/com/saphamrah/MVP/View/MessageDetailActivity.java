package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.saphamrah.BaseMVP.MessageDetailMVP;
import com.saphamrah.MVP.Presenter.MessageDetailPresenter;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MessageDetailActivity extends AppCompatActivity implements MessageDetailMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , MessageDetailActivity.this);
    private MessageDetailMVP.PresenterOps mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgviewBack = findViewById(R.id.imgBack);

        startMVPOps();

        Intent intent = getIntent();
        int ccMessage = intent.getIntExtra("ccMessage" , -1);
        mPresenter.getMessage(ccMessage);


        imgviewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MessageDetailActivity.this.finish();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return MessageDetailActivity.this;
    }

    @Override
    public void onGetMessage(MessageBoxModel messageBoxModel)
    {
        TextView lblMessageTitle = findViewById(R.id.lblMessageTitle);
        ScrollView scrollView = findViewById(R.id.scrollview);
        WebView webViewMessageContent = findViewById(R.id.webviewMessageContent);
        TextView lblMessageContent = findViewById(R.id.lblMessageContent);

        lblMessageTitle.setText(messageBoxModel.getTitle());

        if (messageBoxModel.getNoeMessage() == 1)
        {
            scrollView.setVisibility(View.GONE);
            lblMessageContent.setVisibility(View.GONE);
            webViewMessageContent.setVisibility(View.VISIBLE);

            webViewMessageContent.getSettings().setJavaScriptEnabled(true);
            //webViewMessageContent.loadData(messageBoxModel.getMessage(), "text/html", "UTF-8");
            webViewMessageContent.loadDataWithBaseURL(null,messageBoxModel.getMessage(), "text/html", "utf-8",null);
        }
        else
        {
            scrollView.setVisibility(View.VISIBLE);
            lblMessageContent.setVisibility(View.VISIBLE);
            webViewMessageContent.setVisibility(View.GONE);
            lblMessageContent.setText(Html.fromHtml(messageBoxModel.getMessage()));
        }
    }

    @Override
    public void onFailedGetMessage()
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(MessageDetailActivity.this);
        customAlertDialog.showMessageAlert(MessageDetailActivity.this, true, "", getResources().getString(R.string.notFoundMessage), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MessageDetailActivity", "startMVPOps", "");
        }
    }


    private void initialize(MessageDetailMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new MessageDetailPresenter(view);
            stateMaintainer.put(MessageDetailMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MessageDetailActivity", "initialize", "");
        }
    }


    private void reinitialize(MessageDetailMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(MessageDetailMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MessageDetailActivity", "reinitialize", "");
            }
        }
    }



}
