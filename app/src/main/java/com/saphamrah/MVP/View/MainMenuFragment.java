package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Adapter.MainMenuAdapter;
import com.saphamrah.BaseMVP.MainMenuMVP;
import com.saphamrah.MVP.Presenter.MainMenuPresenter;
import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

public class MainMenuFragment extends Fragment implements MainMenuMVP.RequiredViewOps
{

    private Context context;
    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer;
    MainMenuMVP.PresenterOps mPresenter;

    private RecyclerView recyclerView;
    private String parentsId;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        if (args != null)
        {
            parentsId = args.getString("parentsId");
        }
        return inflater.inflate(R.layout.sell_menu_fragment , container , false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , context);
        startMVPOps();

        Log.d("main" , "parentId : " + parentsId);

        mPresenter.getMenuItems(parentsId);
    }


    @Override
    public Context getAppContext()
    {
        return context;
    }

    @Override
    public void setMenuAdapter(ArrayList<SystemMenuModel> systemMenuModels)
    {
        MainMenuAdapter adapter = new MainMenuAdapter(context, systemMenuModels, new MainMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SystemMenuModel systemMenuModel, int position) {
                Log.d("MainMenuFragment","Link: " + systemMenuModel.getLink());
                mPresenter.checkSelectedMenuItem(systemMenuModel);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context , getSpanCount());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showToast((Activity)context, getResources().getString(resId), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void openActivity(String activityName)
    {
        try
        {
            Log.d("MainMenu",context.getPackageName()+Constants.DIRECTORY_OF_ACTIVITY()+activityName);
            Class<?> activityClass = Class.forName(context.getPackageName() + Constants.DIRECTORY_OF_ACTIVITY() + activityName);
            Log.d("MainMenu",activityClass.toString());
            Intent intent = new Intent(context , activityClass);
            startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
        catch (Exception exception)
        {
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "" , "SellMenuFragment", "openActivity" , "");
            exception.printStackTrace();
        }
    }

    @Override
    public void showAboutAlert(String currentVersion, String newestVersion, String lastStableVersion, String newFeatures)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showAboutAlert((Activity)context, currentVersion, newestVersion, lastStableVersion, newFeatures);
    }

    @Override
    public void showExitAlert()
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showLogMessageAlert((Activity) context, false, getResources().getString(R.string.warning), getResources().getString(R.string.exitWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
            }

            @Override
            public void setOnApplyClick() {
                try
                {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            }
        });
    }

    private int getSpanCount()
    {
        int spanCount = 3;
        Configuration config = context.getResources().getConfiguration();
        if (config.smallestScreenWidthDp < 600)
        {
            spanCount = 2;
        }
        return spanCount;
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SellMenuFragment", "startMVPOps", "");
        }
    }


    private void initialize(MainMenuMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new MainMenuPresenter(view);
            stateMaintainer.put(MainMenuMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SellMenuFragment", "initialize", "");
        }
    }


    private void reinitialize(MainMenuMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(MainMenuMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SellMenuFragment", "reinitialize", "");
            }
        }
    }





}
