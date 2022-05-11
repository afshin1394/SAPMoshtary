package com.saphamrah.MVP.View.marjoee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.BaseMVP.marjoee.DarkhastFaktorMarjoeeMVP;
import com.saphamrah.MVP.Presenter.marjoee.DarkhastFaktorMarjoeePresenter;
import com.saphamrah.MVP.View.TemporaryRequestsListActivity;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;

public class DarkhastFaktorMarjoeeActivity extends AppCompatActivity implements DarkhastFaktorMarjoeeMVP.RequiredViewOps {
    private final String TAG = this.getClass().getSimpleName();
    private String ccDarkhastFaktor;
    private String ccMoshtary;
    private DarkhastFaktorMarjoeePresenter mPresenter;
    private CustomAlertDialog customAlertDialog;
    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;

    @BindView(R.id.lblActivityTitle)
    TextView lblActivityTitle;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.fabMarjoeeKoli)
    FloatingActionButton fabMarjoeeKoli;
    @BindView(R.id.fabMarjoeeMoredi)
    FloatingActionButton fabMarjoeeMoredi;
    @BindView(R.id.fabMarjoeeForoshandeh)
    FloatingActionButton fabMarjoeeForoshandeh;
    @BindView(R.id.fabSabtMarjoee)
    FloatingActionButton fabSabtMarjoee;
    @BindView(R.id.fabSearchNameKala)
    FloatingActionButton fabSearchNameKala;
    private IOnclickSabtMarjoee iOnclickSabtMarjoee;
    private IOnclickSearchNameKalaMarjoee iOnclickSearchNameKalaMarjoee;

    @OnClick(R.id.fabSend)
    public void fabSend(){
        showLoading();
        mPresenter.sendMarjoee(Long.parseLong(ccDarkhastFaktor),Integer.parseInt(ccMoshtary));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darkhast_faktor_marjoee);
        ButterKnife.bind(this);
        mPresenter = new DarkhastFaktorMarjoeePresenter(this);
        customAlertDialog = new CustomAlertDialog(this);
        customLoadingDialog = new CustomLoadingDialog();
        // get bundle
        Intent in = getIntent();
        Bundle content_search = in.getExtras();
        if (content_search != null) {
            ccDarkhastFaktor = content_search.getString("marjoee");
            ccMoshtary = content_search.getString("ccMoshtaryMarjoee");
        }
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startFragmentLevel1();



    }

    /**
     * this code for instantiate interface in fragments
     * @param fragment
     */
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof IOnclickSabtMarjoee) {
            iOnclickSabtMarjoee = (IOnclickSabtMarjoee) fragment;
        }
        if (fragment instanceof IOnclickSearchNameKalaMarjoee){
            iOnclickSearchNameKalaMarjoee = (IOnclickSearchNameKalaMarjoee) fragment;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * check fun visibilityFab for show item fab menu and open , close fabMenu
         */
        fabMenu.setOnMenuButtonClickListener(v -> {
            visibilityFab();
            if (fabMenu.isOpened()) {
                fabMenu.open(true);
                fabMenu.close(false);
            } else {
                fabMenu.close(true);
                fabMenu.open(false);
            }
        });
    }


    /**
     * on click for start Marjoee Koli Fragment
     */
    @OnClick(R.id.fabMarjoeeKoli)
    public void fabMarjoeeKoli() {
        fabMenu.close(true);
        getSupportFragmentManager().popBackStackImmediate();
        MarjoeeKoliFragment marjoeeKoliFragment = new MarjoeeKoliFragment();
        Bundle bundle = new Bundle();
        bundle.putString("marjoee", ccDarkhastFaktor);
        bundle.putString("ccMoshtaryMarjoee", ccMoshtary);
        marjoeeKoliFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag, marjoeeKoliFragment)
                .addToBackStack(TAG)
                .commit();
    }

    /**
     * on click for start Marjoee Moredi Fragment
     */
    @OnClick(R.id.fabMarjoeeMoredi)
    public void fabMarjoeeMoredi() {
        fabMenu.close(true);
        getSupportFragmentManager().popBackStackImmediate();
        MarjoeeMorediFragment marjoeeMorediFragment = new MarjoeeMorediFragment();
        Bundle bundle = new Bundle();
        bundle.putString("marjoee", ccDarkhastFaktor);
        bundle.putString("ccMoshtaryMarjoee", ccMoshtary);
        marjoeeMorediFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag, marjoeeMorediFragment)
                .addToBackStack(TAG)
                .commit();
    }

    /**
     * on click for start Marjoee Foroshande Fragment
     */
    @OnClick(R.id.fabMarjoeeForoshandeh)
    public void fabMarjoeeForoshandeh() {
        fabMenu.close(true);
        getSupportFragmentManager().popBackStackImmediate();
        MarjoeeForoshandehFragment marjoeiForoshandehFragment = new MarjoeeForoshandehFragment();
        Bundle bundle = new Bundle();
        bundle.putString("marjoee", ccDarkhastFaktor);
        bundle.putString("ccMoshtaryMarjoee", ccMoshtary);
        marjoeiForoshandehFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag, marjoeiForoshandehFragment)
                .addToBackStack(TAG)
                .commit();
    }

    /**
     * on click for sabt marjoee
     */
    @OnClick(R.id.fabSabtMarjoee)
    public void fabSabtMarjoee() {
        fabMenu.close(true);
        if (iOnclickSabtMarjoee != null) {
            iOnclickSabtMarjoee.clickSabtMarjoee();
        }
    }

    /**
     * on click for search name kala
     */
    @OnClick(R.id.fabSearchNameKala)
    public void fabSearchNameKala() {
        fabMenu.close(true);
        if (iOnclickSearchNameKalaMarjoee != null) {
            iOnclickSearchNameKalaMarjoee.clickSearchNameKalaMarjoee();
        }
    }

    @OnClick(R.id.btn_back)
    public void btn_back() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }

    }

    /**
     * check Constants.SELECTED_FRAGMENT for show item fab menu
     */
    private void visibilityFab() {
        if (Constants.SELECTED_FRAGMENT == 1) {
            fabMarjoeeMoredi.setVisibility(View.VISIBLE);
            fabMarjoeeKoli.setVisibility(View.VISIBLE);
            fabMarjoeeForoshandeh.setVisibility(View.GONE);
            fabSabtMarjoee.setVisibility(View.GONE);
            fabSearchNameKala.setVisibility(View.GONE);
        } else if (Constants.SELECTED_FRAGMENT == 2) {
            fabMarjoeeForoshandeh.setVisibility(View.VISIBLE);
            fabMarjoeeKoli.setVisibility(View.VISIBLE);
            fabMarjoeeMoredi.setVisibility(View.GONE);
            fabSabtMarjoee.setVisibility(View.GONE);
            fabSearchNameKala.setVisibility(View.VISIBLE);
        } else if (Constants.SELECTED_FRAGMENT == 3) {
            fabMarjoeeForoshandeh.setVisibility(View.VISIBLE);
            fabMarjoeeMoredi.setVisibility(View.VISIBLE);
            fabMarjoeeKoli.setVisibility(View.GONE);
            fabSabtMarjoee.setVisibility(View.VISIBLE);
            fabSearchNameKala.setVisibility(View.GONE);
        }
    }

    /*
    set start Fragment Foroshandeh marjoei
     */
    private void startFragmentLevel1() {
        MarjoeeForoshandehFragment marjoeiForoshandehFragment = new MarjoeeForoshandehFragment();
        Bundle bundle = new Bundle();
        bundle.putString("marjoee", ccDarkhastFaktor);
        bundle.putString("ccMoshtaryMarjoee", ccMoshtary);
        marjoeiForoshandehFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag, marjoeiForoshandehFragment)
                .addToBackStack(TAG)
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

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(this, getResources().getString(resId), messageType, duration);

    }

    @Override
    public void showLoading() {
        if (alertDialogLoading == null) {
            alertDialogLoading = customLoadingDialog.showLoadingDialog(this);
        }
    }

    @Override
    public void closeLoading() {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
                alertDialogLoading = null;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onSuccessSend() {
        customAlertDialog.showMessageAlert(this, false, "", getResources().getString(R.string.successSendData), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.apply));
    }
}
