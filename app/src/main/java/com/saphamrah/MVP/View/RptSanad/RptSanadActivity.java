package com.saphamrah.MVP.View.RptSanad;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.saphamrah.MVP.View.RptSanad.RptSanadFragment;
import com.saphamrah.MVP.View.SingleFragmentActivityWithToolbar;
import com.saphamrah.R;

public class RptSanadActivity extends SingleFragmentActivityWithToolbar {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context , RptSanadActivity.class);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        return RptSanadFragment.newInstance();
    }

    @Override
    public String getActivityTitle() {
        return getResources().getString(R.string.rptSanadTilte);
    }

}