package com.saphamrah.MVP.View.RptThreeMonthPurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.saphamrah.R;
import com.saphamrah.Utils.StateMaintainer;

import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

public class RptThreeMonthPurchaseActivity extends AppCompatActivity  {

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, RptThreeMonthPurchaseActivity.this);


    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_three_month_purchase);
        ButterKnife.bind(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        startFragmentLevel1();
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount()>1){
                    getSupportFragmentManager().popBackStackImmediate();
                }else{
                    finish();
                }
            }
        });



    }



    private void startFragmentLevel1() {
        RptThreeMonthPurchaseFragment rptThreeMonthPurchaseFragment=new RptThreeMonthPurchaseFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag,rptThreeMonthPurchaseFragment)
                .addToBackStack(TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount()>1){
            getSupportFragmentManager().popBackStackImmediate();
        }else{
            finish();
        }

    }
}
