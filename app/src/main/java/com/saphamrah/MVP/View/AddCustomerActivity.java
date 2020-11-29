package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.saphamrah.Adapter.AddCustomerPagerAdapter;
import com.saphamrah.BaseMVP.AddCustomerMVP;
import com.saphamrah.MVP.Presenter.AddCustomerPresenter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;
import java.util.ArrayList;
import devlight.io.library.ntb.NavigationTabBar;
import me.anwarshahriar.calligrapher.Calligrapher;


public class AddCustomerActivity extends AppCompatActivity implements AddCustomerMVP.RequiredViewOps , AddCustomerBaseInfoFragment.onVisiblePersonalInformationListener
{


    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , AddCustomerActivity.this);
    private AddCustomerMVP.PresenterOps mPresenter;

    private ImageView imgBack;
    private ViewPager viewPager;
    private NavigationTabBar navigationTabBar;
    private AddCustomerPagerAdapter pagerAdapter;
    private CustomAlertDialog customAlertDialog;


    @Override
    public void sendData(AddCustomerInfoModel addCustomerInfoModel)
    {
        if (addCustomerInfoModel == null)
        {
            customAlertDialog.showMessageAlert(AddCustomerActivity.this, "", getResources().getString(R.string.errorPersonalInfo), Constants.NONE_MESSAGE(),
                    getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
                        @Override
                        public void setOnCancelClick() {
                            viewPager.setCurrentItem(3);
                        }

                        @Override
                        public void setOnApplyClick() {
                            viewPager.setCurrentItem(3);
                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        imgBack = (ImageView)findViewById(R.id.imgBack);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        navigationTabBar = (NavigationTabBar)findViewById(R.id.navigationTabBar);
        pagerAdapter = new AddCustomerPagerAdapter(getSupportFragmentManager());
        customAlertDialog = new CustomAlertDialog(AddCustomerActivity.this);

        startMVPOps();

        viewPager.setAdapter(pagerAdapter);
        navigationTabBar.setModels(createNavigationTabItems());
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ALL);
        navigationTabBar.setViewPager(viewPager , 4);
        navigationTabBar.setTypeface(getResources().getString(R.string.fontPath));
        navigationTabBar.setIsTitled(true);

        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                //addCustomerBaseInfoFragment.setUserVisibleHint(false);
                /*Log.d("fragment" , "position : " + position);
                if (previousPage == null || previousPage.trim().equals("") || previousPage.equals("4"))
                {
                    AddCustomerBaseInfoFragment addCustomerBaseInfoFragment = new AddCustomerBaseInfoFragment();
                    Log.d("fragment" , "name : " + addCustomerBaseInfoFragment.name);
                    if(!addCustomerBaseInfoFragment.checkData())
                    {
                        viewPager.setCurrentItem(4);
                        previousPage = "4";
                    }
                    else
                    {
                        previousPage = String.valueOf(position);
                    }
                }
                else
                {
                    previousPage = String.valueOf(position);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustomerActivity.this.finish();
            }
        });

    }


    @Override
    public Context getAppContext()
    {
        return AddCustomerActivity.this;
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(AddCustomerActivity.this, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showServerMessage(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(AddCustomerActivity.this, closeActivity, getResources().getString(titleResId), message, messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(AddCustomerActivity.this, getResources().getString(resId), messageType, duration);
    }


    public ArrayList<NavigationTabBar.Model> createNavigationTabItems()
    {
        String[] colors = getResources().getStringArray(R.array.addCustomerNavigationTabBarColors);

        ArrayList<NavigationTabBar.Model> tabItms = new ArrayList<>();
        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_save), Color.parseColor(colors[0]))
                .title(getResources().getString(R.string.apply)).build());

/*        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_files), Color.parseColor(colors[1]))
                .title(getResources().getString(R.string.docs)).build());*/

        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_bank_account), Color.parseColor(colors[2]))
                .title(getResources().getString(R.string.bank)).build());

        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_location), Color.parseColor(colors[3]))
                .title(getResources().getString(R.string.address)).build());
        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_user), Color.parseColor(colors[4]))
                .title(getResources().getString(R.string.personalInfo)).build());

        return tabItms;
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(AddCustomerMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddCustomerPresenter(view);
            stateMaintainer.put(AddCustomerMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(AddCustomerMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddCustomerMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


}
