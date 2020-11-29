package com.saphamrah.CustomView;

import android.app.Activity;
import android.content.Context;
import android.provider.FontRequest;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;


public class BottomBar
{


    public interface OnItemClickListener
    {
        void onClick(int position);
    }


    private Context context;
    private int currentIndex;
    private OnItemClickListener listener;
    private LinearLayout layBottomBarBarkhordAvalie;
    private LinearLayout layBottomBarMojodiGiri;
    private LinearLayout layBottomBarDarkhast;
    private LinearLayout layBottomBarTaeedDarkhast;
    private LinearLayout layBottomBarTasvieDarkhast;
    private LinearLayout layBottomBarEmzaMoshtary;

    private boolean showBarkhordAvalie;
    private boolean showMojoodiGiri;
    private boolean forceBarkhordAvalie;
    private boolean forceMojoodiGiri;
    private boolean multipleMojoodiGiri; //only one time can do this or more


    public BottomBar(Context context , int currentIndex , final OnItemClickListener listener)
    {
        this.context = context;
        this.currentIndex = currentIndex;
        this.listener = listener;

        PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
        bottomBarConfig.getConfig(context);

        showBarkhordAvalie = bottomBarConfig.getShowBarkhordAvalie();
        showMojoodiGiri = bottomBarConfig.getShowMojoodiGiri();
        forceBarkhordAvalie = bottomBarConfig.getForceBarkhordAvalie();
        forceMojoodiGiri = bottomBarConfig.getForceMojoodiGiri();
        multipleMojoodiGiri = bottomBarConfig.getMultipleMojoodiGiri();

        bindViews();
        setCurrentView();

        layBottomBarBarkhordAvalie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(0);
            }
        });


        layBottomBarMojodiGiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(1);
            }
        });


        layBottomBarDarkhast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(2);
            }
        });


        layBottomBarTaeedDarkhast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(3);
            }
        });


        layBottomBarTasvieDarkhast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(4);
            }
        });


        layBottomBarEmzaMoshtary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(5);
            }
        });

    }


    /*private void getConfig()
    {
        ChildParameterDAO childParameterDAO = new ChildParameterDAO(context);
        ArrayList<ChildParameterModel> childParameterModels = childParameterDAO.getAllByParentsId(String.format("%1$s , %2$S", Constants.CC_BARKHORD_AVALIE_CONFIG(), Constants.CC_MOJOODI_GIRI_CONFIG()));
        for (ChildParameterModel model : childParameterModels)
        {
            if (model.getCcParameter() == Constants.CC_MOJOODI_GIRI_CONFIG())
            {
                if (model.getName().trim().equalsIgnoreCase(Constants.SHOW()))
                {
                    showMojoodiGiri = model.getValue().trim().equals("1");
                }
                else if (model.getName().trim().equalsIgnoreCase(Constants.FORCE()))
                {
                    forceMojoodiGiri = model.getValue().trim().equals("1");
                }
                else if (model.getName().trim().equalsIgnoreCase(Constants.MULTIPLE()))
                {
                    multipleMojoodiGiri = model.getValue().trim().equals("1");
                }
            }
            else if (model.getCcParameter() == Constants.CC_BARKHORD_AVALIE_CONFIG())
            {
                if (model.getName().trim().equalsIgnoreCase(Constants.SHOW()))
                {
                    showBarkhordAvalie = model.getValue().trim().equals("1");
                }
                else if (model.getName().trim().equalsIgnoreCase(Constants.FORCE()))
                {
                    forceBarkhordAvalie = model.getValue().trim().equals("1");
                }
            }
        }
    }*/

    private void bindViews()
    {
        layBottomBarBarkhordAvalie = (LinearLayout)((Activity)context).findViewById(R.id.layBottomBarBarkhordAvalie);
        layBottomBarMojodiGiri = (LinearLayout)((Activity)context).findViewById(R.id.layBottomBarMojodiGiri);
        layBottomBarDarkhast = (LinearLayout)((Activity)context).findViewById(R.id.layBottomBarDarkhast);
        layBottomBarTaeedDarkhast = (LinearLayout)((Activity)context).findViewById(R.id.layBottomBarTaeedDarkhast);
        layBottomBarTasvieDarkhast = (LinearLayout)((Activity)context).findViewById(R.id.layBottomBarTasvieDarkhast);
        layBottomBarEmzaMoshtary = (LinearLayout)((Activity)context).findViewById(R.id.layBottomBarEmzaMoshtary);

        //layBottomBarTasvieDarkhast.setVisibility(View.GONE);
        //layBottomBarTasvieDarkhast.setEnabled(false);

        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(context);
        ForoshandehMamorPakhshModel model = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh();
        /*if (new PubFunc().new ForoshandehMamorPakhsh().getNoeMasouliat(model) == 2)
        {
            //layBottomBarTasvieDarkhast.setVisibility(View.VISIBLE);
            layBottomBarTasvieDarkhast.setEnabled(true);
        }*/
        if (!showMojoodiGiri)
        {
            //layBottomBarMojodiGiri.setVisibility(View.GONE);
            //layBottomBarMojodiGiri.setEnabled(false);
        }

        if (!showBarkhordAvalie)
        {
            //layBottomBarBarkhordAvalie.setVisibility(View.GONE);
            //layBottomBarBarkhordAvalie.setEnabled(false);
        }

    }


    private void setCurrentView()
    {
        switch (currentIndex)
        {
            case 0:
                layBottomBarBarkhordAvalie.setBackgroundColor(context.getResources().getColor(R.color.colorBackAddCustomerDocSwipe));
                break;
            case 1:
                layBottomBarMojodiGiri.setBackgroundColor(context.getResources().getColor(R.color.colorBackAddCustomerDocSwipe));
                break;
            case 2:
                layBottomBarDarkhast.setBackgroundColor(context.getResources().getColor(R.color.colorBackAddCustomerDocSwipe));
                break;
            case 3:
                layBottomBarTaeedDarkhast.setBackgroundColor(context.getResources().getColor(R.color.colorBackAddCustomerDocSwipe));
                break;
            case 4:
                layBottomBarTasvieDarkhast.setBackgroundColor(context.getResources().getColor(R.color.colorBackAddCustomerDocSwipe));
                break;
            case 5:
                layBottomBarEmzaMoshtary.setBackgroundColor(context.getResources().getColor(R.color.colorBackAddCustomerDocSwipe));
                break;
            default:
                break;
        }
    }



}
