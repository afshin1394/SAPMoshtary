package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.saphamrah.Adapter.LogsAdapter;
import com.saphamrah.BaseMVP.LogsMVP;
import com.saphamrah.MVP.Presenter.LogPresenter;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;


public class LogsActivity extends AppCompatActivity implements LogsMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , LogsActivity.this);
    LogsMVP.PresenterOps mPresenter;

    ImageView imgBack;
    RecyclerView recviewLogs;
    Button btnSend;
    TextView lblEmptyList;
    LogsAdapter adapter;
    ArrayList<LogPPCModel> arrayListLogPPCModel;
    CustomAlertDialog customAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        arrayListLogPPCModel = new ArrayList<>();
        customAlertDialog = new CustomAlertDialog(LogsActivity.this);

        imgBack = findViewById(R.id.imgBack);
        recviewLogs = findViewById(R.id.recview_Logs);
        btnSend = findViewById(R.id.btnSendLogs);
        lblEmptyList = findViewById(R.id.lblEmptyView);

        mPresenter.getExceptionsToShow();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogsActivity.this.finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkLogsForSendToServer();
            }
        });


    }

    @Override
    public Context getAppContext() {
        return LogsActivity.this;
    }

    @Override
    public void setAdapter(ArrayList<LogPPCModel> arrayListLogs)
    {
        lblEmptyList.setVisibility(View.GONE);
        recviewLogs.setVisibility(View.VISIBLE);
        arrayListLogPPCModel = new ArrayList<>();
        arrayListLogPPCModel = arrayListLogs;
        adapter = new LogsAdapter(arrayListLogPPCModel, LogsActivity.this, new LogsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final LogPPCModel logPPCModel, int position)
            {
                showMessage(logPPCModel);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(LogsActivity.this);
        recviewLogs.setLayoutManager(mLayoutManager);
        recviewLogs.setItemAnimator(new DefaultItemAnimator());
        recviewLogs.addItemDecoration(new DividerItemDecoration(LogsActivity.this , 0));
        recviewLogs.setAdapter(adapter);
    }

    private void showMessage(final LogPPCModel logPPCModel)
    {
        customAlertDialog.showLogMessageAlert(LogsActivity.this, false, getResources().getString(R.string.info), logPPCModel.toString(),
                Constants.NONE_MESSAGE(), getResources().getString(R.string.close), getResources().getString(R.string.copy), new CustomAlertDialogResponse() {
                    @Override
                    public void setOnCancelClick()
                    {}

                    @Override
                    public void setOnApplyClick()
                    {
                        mPresenter.copyLog("Log" , logPPCModel.toString());
                    }
                });
    }

    @Override
    public void disableSendButton()
    {
        recviewLogs.setVisibility(View.GONE);
        lblEmptyList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(LogsActivity.this, false, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showServerMessage(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(LogsActivity.this, false, getResources().getString(titleResId), message, messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showAlertSendEmail()
    {
        customAlertDialog.showLogMessageAlert(LogsActivity.this, false, getResources().getString(R.string.errorSendData), getResources().getString(R.string.sendLogsWithEmailAlert), Constants.FAILED_MESSAGE(),getResources().getString(R.string.close),getResources().getString(R.string.send), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick()
            {
                mPresenter.checkExceptionsToMail();
            }
        });
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        Log.d("log" , "test");
        customAlertDialog.showToast(LogsActivity.this , getResources().getString(resId) , messageType , duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "LogsActivity", "startMVPOps", "");
        }
    }


    private void initialize(LogsMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new LogPresenter(view);
            stateMaintainer.put(LogsMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "LogsActivity", "initialize", "");
        }
    }


    private void reinitialize(LogsMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(LogsMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "LogsActivity", "reinitialize", "");
            }
        }
    }


}
