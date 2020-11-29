package com.saphamrah.MVP.View;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.saphamrah.R;

public class RptEtebarActivity extends SingleFragmentActivityWithToolbar {

    private static final String EXTRA_CCFOROSHANDE = "com.saphamrah.MVP.View.ccForoshande";
    private static final String EXTRA_CCMOSHTARY ="com.saphamrah.MVP.View.ccMoshtarY" ;
    private static final String EXTRA_CCSAZMANFOROSH ="com.saphamrah.MVP.View.ccSazmanForosh" ;

    @Override
    public Fragment createFragment() {
        int ccForoshande = getIntent().getIntExtra(EXTRA_CCFOROSHANDE, 0);
        int ccMoshtary = getIntent().getIntExtra(EXTRA_CCMOSHTARY, 0);
        int ccSazmanForosh = getIntent().getIntExtra(EXTRA_CCSAZMANFOROSH, 0);
        return  RptEtebarFragment.newInstance(ccForoshande,ccMoshtary,ccSazmanForosh);
    }

    @Override
    public String getActivityTitle() {
        return getResources().getString(R.string.reportEtebar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_etebar);
    }

    public static Intent newIntent(Context context, int ccForoshande, int ccMoshtary, int ccSazmanForosh){
        
        Intent intent =  new Intent(context, RptEtebarActivity.class);
        intent.putExtra(EXTRA_CCFOROSHANDE, ccForoshande);
        intent.putExtra(EXTRA_CCMOSHTARY, ccMoshtary);
        intent.putExtra(EXTRA_CCSAZMANFOROSH, ccSazmanForosh);
        return intent;
    }
}