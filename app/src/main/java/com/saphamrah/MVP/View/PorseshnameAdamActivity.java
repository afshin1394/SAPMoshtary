package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.AdamAdapter;
import com.saphamrah.Adapter.PorseshnamehAdapter;
import com.saphamrah.BaseMVP.PorseshnameAdamMVP;
import com.saphamrah.MVP.Presenter.PorseshnameAdamPresenter;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerVisitModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class PorseshnameAdamActivity extends AppCompatActivity implements PorseshnameAdamMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , this);
    private PorseshnameAdamMVP.PresenterOps mPresenter;

    private TextView txtviewTitle;
    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabChangeList;
    private RecyclerView recyclerView;
    private boolean showPorseshname;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porseshname_adam);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imageViewBack = findViewById(R.id.imgBack);
        txtviewTitle = findViewById(R.id.lblActivityTitle);
        fabMenu = findViewById(R.id.fabMenu);
        fabChangeList = findViewById(R.id.fabChangeList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customAlertDialog = new CustomAlertDialog(this);
        customLoadingDialog = new CustomLoadingDialog();

        startMVPOps();

        mPresenter.getAllPorseshname();

        fabChangeList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                if (showPorseshname)
                {
                    mPresenter.getAllAdamFaal();
                }
                else
                {
                    mPresenter.getAllPorseshname();
                }
            }
        });


        imageViewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PorseshnameAdamActivity.this.finish();
            }
        });

    }

    @Override
    public Context getAppContext()
    {
        return this;
    }

    @Override
    public void changeTitleToPorseshname()
    {
        showPorseshname = true;
        txtviewTitle.setText(getString(R.string.porseshname));
    }

    @Override
    public void changeTitleToAdam()
    {
        showPorseshname = false;
        txtviewTitle.setText(getString(R.string.adamFaal));
    }

    @Override
    public void changeFabToAdam()
    {
        fabChangeList.setLabelText(getString(R.string.adamFaal));
    }

    @Override
    public void changeFabToPorseshname()
    {
        fabChangeList.setLabelText(getString(R.string.porseshname));
    }

    @Override
    public void showAllPorseshname(final List<PorseshnamehModel> porseshnamehModels)
    {
        PorseshnamehAdapter adapter = new PorseshnamehAdapter(this, porseshnamehModels, new PorseshnamehAdapter.onClickListener()
        {
            @Override
            public void onItemClickListener(int operation, int position)
            {
                if (operation == PorseshnamehAdapter.SEND)
                {
                    mPresenter.sendPorseshname(porseshnamehModels.get(position).getCcPorseshnameh());
                }
                else if (operation == PorseshnamehAdapter.DELETE)
                {
                    deletePorseshnameh(porseshnamehModels.get(position).getCcPorseshnameh());
                }
                else if (operation == PorseshnamehAdapter.EDIT)
                {
                    openEditPorseshnameh(porseshnamehModels.get(position).getCcPorseshnameh(), porseshnamehModels.get(position).getCcMoshtary());
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }


    private void deletePorseshnameh(final int ccPorseshnameh)
    {
        customAlertDialog.showLogMessageAlert(this, false, "", getString(R.string.deleteWarning), Constants.INFO_MESSAGE(), getString(R.string.no), getString(R.string.yes), new CustomAlertDialogResponse()
        {
            @Override
            public void setOnCancelClick() {}

            @Override
            public void setOnApplyClick()
            {
                mPresenter.deletePorseshname(ccPorseshnameh);
            }
        });
    }

    private void openEditPorseshnameh(int ccPorseshnameh, int ccMoshtary)
    {
        Intent intent = new Intent(PorseshnameAdamActivity.this, AddPorseshnameInfoActivity.class);
        intent.putExtra(AddPorseshnameInfoActivity.CC_PORSESHNAMEH_KEY, ccPorseshnameh);
        intent.putExtra(AddPorseshnameInfoActivity.CC_MOSHTARY_KEY, ccMoshtary);
        intent.putExtra(AddPorseshnameInfoActivity.CODE_MOSHTARY_KEY, "");
        intent.putExtra(AddPorseshnameInfoActivity.CC_MASIR_KEY, 0);
        startActivity(intent);
        PorseshnameAdamActivity.this.finish();
    }

    @Override
    public void showAllAdamFaal(final List<CustomerVisitModel> customerVisitModels)
    {
        AdamAdapter adapter = new AdamAdapter(this, customerVisitModels, new AdamAdapter.onClickListener()
        {
            @Override
            public void onItemClickListener(int operation, int position)
            {
                if (operation == AdamAdapter.SEND)
                {
                    mPresenter.sendAdamFaal(customerVisitModels.get(position).getCcVisitMoshtary());
                }
                else if (operation == AdamAdapter.DELETE)
                {
                    deleteAdamFaal(customerVisitModels.get(position).getCcVisitMoshtary());
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void deleteAdamFaal(int ccVisitMoshtary)
    {
        mPresenter.deleteAdamFaal(ccVisitMoshtary);
    }

    @Override
    public void showAlertSuccessDelete()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.successfullyDoneOps), Constants.SUCCESS_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorMessage(String message)
    {
        customAlertDialog.showMessageAlert(this, false, "", message, Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }


    @Override
    public void showSuccessSendData()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.successSendData), Constants.SUCCESS_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showNotFoundData()
    {
        fabChangeList.setLabelText(getString(R.string.porseshname));
    }

    @Override
    public void showLoading()
    {
        alertDialogLoading = customLoadingDialog.showLoadingDialog(this);
    }

    @Override
    public void closeLoading()
    {
        if (alertDialogLoading != null)
        {
            try
            {
                alertDialogLoading.dismiss();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "startMVPOps", "");
        }
    }

    private void initialize(PorseshnameAdamMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new PorseshnameAdamPresenter(view);
            stateMaintainer.put(PorseshnameAdamMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "initialize", "");
        }
    }

    private void reinitialize(PorseshnameAdamMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(PorseshnameAdamMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "reinitialize", "");
            }
        }
    }

}
