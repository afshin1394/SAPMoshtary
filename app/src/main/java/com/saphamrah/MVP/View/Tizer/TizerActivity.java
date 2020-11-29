package com.saphamrah.MVP.View.Tizer;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.saphamrah.MVP.View.Tizer.TizerFolderFragment;
import com.saphamrah.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

public class TizerActivity extends AppCompatActivity  {


    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.btn_back)
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TizerActivity","oncreate");
        setContentView(R.layout.activity_tizer);
        ButterKnife.bind(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        startFragmentLevel1();


        btnBack.setOnClickListener(v -> {
            if (getSupportFragmentManager().getBackStackEntryCount()>1){
                getSupportFragmentManager().popBackStackImmediate();
            }else{
                finish();
            }
        });
    }

    private void startFragmentLevel1() {
        TizerFolderFragment tizerFolderFragment=new TizerFolderFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag,tizerFolderFragment)
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
