package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import com.saphamrah.R;


public class AddCustomerMapActivity extends AppCompatActivity
{
    public static final String CUSTOMER_lAT_KEY = "customerlat";
    public static final String CUSTOMER_lng_KEY = "customerlng";

    double customerLat;
    double customerLng;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_map);

        customerLat = 0.0;
        customerLng = 0.0;

        try
        {
            Intent intent = getIntent();
            customerLat = intent.getDoubleExtra("customerlat" , 0.0);
            customerLng = intent.getDoubleExtra("customerlng" , 0.0);

            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SellPolygonFragment sellPolygonFragment = new SellPolygonFragment();
            Bundle bundle = new Bundle();
            bundle.putDouble("customerlat" , customerLat);
            bundle.putDouble("customerlng" , customerLng);
            sellPolygonFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.layFragmentContainer , sellPolygonFragment);
            fragmentTransaction.commit();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }


}
