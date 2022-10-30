package com.saphamrah.customer.presentation.view.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.saphamrah.customer.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    private NavigationView drawerView;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout_main);
        drawerView = findViewById(R.id.drawer_main);

        ImageView menuImg = findViewById(R.id.menu_drawer_main);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        drawerView.addHeaderView(getLayoutInflater().inflate(R.layout.layout_drawer_header, null));

        drawerView.setNavigationItemSelectedListener(this);

        menuImg.setOnClickListener(view -> {
            if(drawerLayout.isDrawerOpen(drawerView))
                drawerLayout.closeDrawer(Gravity.LEFT);
            else
                drawerLayout.openDrawer(Gravity.RIGHT);
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Navigation.findNavController(this,R.id.mainNavigation_host).navigate(R.id.action_mainFragment_to_profileFragment);
        }
        if (id == R.id.nav_main) {
            Navigation.findNavController(this,R.id.mainNavigation_host).navigate(R.id.action_profileFragment_to_mainFragment);
        }
        return true;
    }
}