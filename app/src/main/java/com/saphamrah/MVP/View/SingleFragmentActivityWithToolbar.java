package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.saphamrah.R;
import com.saphamrah.databinding.ActivitySingleFragmentWithToolbarBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public abstract class SingleFragmentActivityWithToolbar extends AppCompatActivity {

    public abstract Fragment createFragment();
    public abstract String getActivityTitle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySingleFragmentWithToolbarBinding binding = DataBindingUtil.setContentView(this , R.layout.activity_single_fragment_with_toolbar);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragmentManager.beginTransaction()
                    .add( R.id.fragment_container ,createFragment())
                    .commit();
        }
        binding.lblActivityTitle.setText(getActivityTitle());
    }
}