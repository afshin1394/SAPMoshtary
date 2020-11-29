package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import com.saphamrah.MVP.View.SaleGoalFragments.RptSaleGoalFragmentLevel1;
import com.saphamrah.R;

import me.anwarshahriar.calligrapher.Calligrapher;


public class RptSaleGoalActivity extends AppCompatActivity {


    private final String TAG = this.getClass().getSimpleName();
    public static String activityState = null;
    public static int ccBrand = 0;
    //    public static final String Percentage = "PERCENTAGE";
//    public static final String Numerical = "NUMERICAL";
//    public static final String Table = "TABLE";
    public FloatingActionButton fabNumerical;
    public FloatingActionButton fabPercentage;
    public FloatingActionButton fabTable;
    public FloatingActionButton fabRefresh;
    public FloatingActionMenu fabMenu;
    public TextView activityTitle;
    public ImageView backButton;
    public static ShowType ShowType;

    public LinearLayout layTableTitle;
    public TextView markTitleName;


    //    public void setShowType(ShowType showType){
//        this.ShowType=showType;
//    }
//    public ShowType getShowType(){
//        return this.ShowType;
//    }
    public enum ShowType {
        Percentage,
        Numerical,
        Table
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_goal_report);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        findViews();
        ShowType = ShowType.Numerical;
        startFragmentLevel1();

        activityState = "MainActivity";
        Log.i("backStacks", "onCreate: " + getSupportFragmentManager().getBackStackEntryCount());


    }

    private void findViews() {
        fabNumerical = findViewById(R.id.fabShowTypeNumerical);
        fabPercentage = findViewById(R.id.fabShowTypePercent);
        fabTable = findViewById(R.id.fabShowTypeTable);
        fabMenu = findViewById(R.id.fabMenuSaleGoal);
        fabRefresh = findViewById(R.id.fabRefreshSaleGoal);
        activityTitle = findViewById(R.id.lblActivityTitle);
        backButton = findViewById(R.id.imgBackSaleGoal);
        layTableTitle = findViewById(R.id.layTableTitle);
        markTitleName =findViewById(R.id.MarkTitleName);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           switch (activityState){
               case "FragmentLevel1":



                   break;
               case "FragmentLevel2" :
                   markTitleName.setText(getResources().getString(R.string.brand));
                   switch (ShowType){
                       case Numerical:
                           activityTitle.setText(R.string.ReportSaleGoalNumerical);
                           break;
                       case Percentage:
                           activityTitle.setText(R.string.ReportSaleGoalPercentage);
                           break;
                       case Table:
                           activityTitle.setText(R.string.ReportSaleGoalTable);
                           break;
                   }
                   break;

               case "FragmentLevel3":
                   markTitleName.setText(getResources().getString(R.string.gorohKala));
                   switch (ShowType){
                       case Numerical:
                           activityTitle.setText(R.string.ReportSaleGoalNumerical);
                           break;
                       case Percentage:
                           activityTitle.setText(R.string.ReportSaleGoalPercentage);
                           break;
                       case Table:
                           activityTitle.setText(R.string.ReportSaleGoalTable);
                           break;
                   }


                   break;
           }
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
                    finish();
                }
            }
        });


    }

    private void startFragmentLevel1() {
        RptSaleGoalFragmentLevel1 rptSaleGoalFragmentLevel1 = new RptSaleGoalFragmentLevel1();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, rptSaleGoalFragmentLevel1)
                .addToBackStack(TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        switch (activityState){
            case "FragmentLevel1":


                break;
            case "FragmentLevel2" :
                markTitleName.setText(getResources().getString(R.string.brand));
                switch (ShowType){
                    case Numerical:
                        activityTitle.setText(R.string.ReportSaleGoalNumerical);
                        break;
                    case Percentage:
                        activityTitle.setText(R.string.ReportSaleGoalPercentage);
                        break;
                    case Table:
                        activityTitle.setText(R.string.ReportSaleGoalTable);
                        break;
                }
                break;

            case "FragmentLevel3":
                markTitleName.setText(getResources().getString(R.string.gorohKala));
                switch (ShowType){
                    case Numerical:
                        activityTitle.setText(R.string.ReportSaleGoalNumerical);
                        break;
                    case Percentage:
                        activityTitle.setText(R.string.ReportSaleGoalPercentage);
                        break;
                    case Table:
                        activityTitle.setText(R.string.ReportSaleGoalTable);
                        break;
                }
                break;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }

    }
}
