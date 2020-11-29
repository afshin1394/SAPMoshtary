package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.saphamrah.Adapter.MessageBoxAdapter;
import com.saphamrah.BaseMVP.MessageBoxMVP;
import com.saphamrah.MVP.Presenter.MessageBoxPresenter;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MessageBoxActivity extends AppCompatActivity implements MessageBoxMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , MessageBoxActivity.this);
    private MessageBoxMVP.PresenterOps mPresenter;

    CustomAlertDialog customAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_box);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imgviewBack = findViewById(R.id.imgBack);

        startMVPOps();

        customAlertDialog = new CustomAlertDialog(MessageBoxActivity.this);

        mPresenter.getMessages();

        imgviewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MessageBoxActivity.this.finish();
            }
        });


    }


    @Override
    public Context getAppContext()
    {
        return MessageBoxActivity.this;
    }

    @Override
    public void onGetMessages(final ArrayList<MessageBoxModel> messageBoxModels)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MessageBoxAdapter adapter = new MessageBoxAdapter(MessageBoxActivity.this, messageBoxModels, new MessageBoxAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position, MessageBoxModel message)
            {
                openMessageDetailActivity(message.getCcMessage());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageBoxActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void openMessageDetailActivity(int ccMessage)
    {
        Intent intent = new Intent(MessageBoxActivity.this , MessageDetailActivity.class);
        intent.putExtra("ccMessage" , ccMessage);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(MessageBoxActivity.this, getResources().getString(resId), messageType, duration);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MessageBoxActivity", "startMVPOps", "");
        }
    }


    private void initialize(MessageBoxMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new MessageBoxPresenter(view);
            stateMaintainer.put(MessageBoxMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MessageBoxActivity", "initialize", "");
        }
    }


    private void reinitialize(MessageBoxMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(MessageBoxMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MessageBoxActivity", "reinitialize", "");
            }
        }
    }


}
