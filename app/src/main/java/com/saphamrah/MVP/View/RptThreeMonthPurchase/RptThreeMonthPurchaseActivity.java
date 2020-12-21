package com.saphamrah.MVP.View.RptThreeMonthPurchase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.R;
import com.saphamrah.Utils.StateMaintainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;

public class RptThreeMonthPurchaseActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    public static final String CC_MOSHTARY = "ccMoshtary";

    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, RptThreeMonthPurchaseActivity.this);
    public int searchStatus; // not in search mode = 0 , search with name = 1 , search with code = 2 ,search with FactorNumber = 3

    ThreeMonthEvents.Purchase purchaseEvents;
    ThreeMonthEvents.RizFactor rizFactorEvents;
    ImageView btnBack;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;

    @BindView(R.id.fabRefresh)
    FloatingActionButton fabRefresh;

    @BindView(R.id.fabSearchName)
    FloatingActionButton fabSearchName;

    @BindView(R.id.fabSearchCode)
    FloatingActionButton fabSearchCode;

    @BindView(R.id.fabSearchFactor)
    FloatingActionButton fabSearchFactor;

    @BindView(R.id.searchView)
    MaterialSearchView searchView;

    @BindView(R.id.layTableFooter)
    LinearLayout layTableFooter;

    @BindView(R.id.lblSumTedadFaktor)
    TextView lblSumTedad;

    @BindView(R.id.lblSumMablaghFaktor)
    TextView lblSumMablaghFaktor;


    @OnClick(R.id.fabRefresh)
    void Refresh(View view) {
        purchaseEvents.onRefresh();
    }

    @OnClick(R.id.fabSearchCode)
    void searchByCode(View view) {
        purchaseEvents.onSearchByCode();
    }

    @OnClick(R.id.fabSearchFactor)
    void searchByFactorNu(View view) {
        rizFactorEvents.onSearchByFactorNu();
    }


    @OnClick(R.id.fabSearchName)
    void searchByName(View view) {
        purchaseEvents.onSearchByName();
    }

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
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStackImmediate();

                } else {
                    finish();
                }
            }
        });

        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (purchaseEvents!=null)
                    purchaseEvents.onQueryTextSubmit(query);
                if (rizFactorEvents!=null)
                    rizFactorEvents.onQueryTextSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (purchaseEvents!=null)
                    purchaseEvents.onQueryTextChange(newText);
                if (rizFactorEvents!=null)
                    rizFactorEvents.onQueryTextChange(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                if (purchaseEvents!=null)
                    purchaseEvents.onSearchViewShown();
                if (rizFactorEvents!=null)
                    rizFactorEvents.onSearchViewShown();
            }

            @Override
            public void onSearchViewClosed() {
                if (purchaseEvents!=null)
                    purchaseEvents.onSearchViewClosed();
                if (rizFactorEvents!=null)
                    rizFactorEvents.onSearchViewClosed();
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (purchaseEvents != null)
                    purchaseEvents.onActionUpButton();
                if (rizFactorEvents != null)
                    rizFactorEvents.onActionUpButton();
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (purchaseEvents != null)
                    purchaseEvents.onActionEmptyButton();
                if (rizFactorEvents != null)
                    rizFactorEvents.onActionEmptyButton();
            }
        });

    }

    public void visibleCloseSearchIcon() {
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof ThreeMonthEvents.Purchase) {
            purchaseEvents = ((ThreeMonthEvents.Purchase) fragment);
        } else if (fragment instanceof ThreeMonthEvents.RizFactor) {
            rizFactorEvents = ((ThreeMonthEvents.RizFactor) fragment);
        }
    }

    private void startFragmentLevel1() {
        RptThreeMonthPurchaseFragment rptThreeMonthPurchaseFragment = new RptThreeMonthPurchaseFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag, rptThreeMonthPurchaseFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();

        } else {
            finish();
        }
    }

}


interface ThreeMonthEvents {

    interface Purchase {
        void onRefresh();

        void onSearchByName();

        void onSearchByCode();

        void onActionUpButton();

        void onActionEmptyButton();

        void onSearchViewClosed();

        void onSearchViewShown();

        void onQueryTextSubmit(String query);

        void onQueryTextChange(String newText);

    }

    interface RizFactor {


        void onSearchByFactorNu();

        void onActionUpButton();

        void onActionEmptyButton();

        void onSearchViewClosed();

        void onSearchViewShown();

        void onQueryTextSubmit(String query);

        void onQueryTextChange(String newText);
    }


}
