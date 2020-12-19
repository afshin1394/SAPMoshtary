package com.saphamrah.MVP.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptKalaInfoAdapter;
import com.saphamrah.BaseMVP.GoodsInfoMVP;
import com.saphamrah.CustomView.CustomProgressBar;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.GoodsInfoPresenter;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;

public class GoodsInfoActivity extends AppCompatActivity implements GoodsInfoMVP.RequiredViewOps {

    GoodsInfoMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(getSupportFragmentManager(), TAG, GoodsInfoActivity.this);
    private AlertDialog alertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private CustomAlertDialog customAlertDialog;
    private CustomProgressBar customProgressBar;
    private CustomSpinner customSpinner;
    private ArrayList<RptKalaInfoModel> kalaInfoByCcBrand;
    private ArrayList<RptKalaInfoModel> kalaInfoByCcGoroh;
    private String ccBrands = "";
    private String ccGoroh = "";
    private RptKalaInfoAdapter adapter;


    // find view
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;

    @BindView(R.id.fabUpdateGallery)
    FloatingActionButton fabUpdateGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        customSpinner = new CustomSpinner();
        kalaInfoByCcBrand = new ArrayList<>();
        kalaInfoByCcGoroh = new ArrayList<>();
        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(GoodsInfoActivity.this);
        customProgressBar = new CustomProgressBar();
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        mPresenter.getListOfGoods();

    }

    // on click listeners

    /*
        click filter by goroh fab
         */
    @OnClick(R.id.fabFilterByGoroh)
    public void fabFilterByGoroh() {
        fabMenu.close(true);
        mPresenter.getKalaInfoCcGorohList(ccBrands);
    }

    /*
        click filter by brand fab
         */
    @OnClick(R.id.fabFilterByccBrand)
    public void fabFilterByccBrand() {
        fabMenu.close(true);
        mPresenter.getKalaInfoCcBrandList();
    }

    @OnClick(R.id.fabUpdateGallery)
    public void fabUpdateGallery() {
//        fabUpdateGallery.setClickable(false);
        fabMenu.close(true);
        if (adapter!=null){
            customAlertDialog.showLogMessageAlert(GoodsInfoActivity.this, false, "", getResources().getString(R.string.wantUpdateGallery), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse()
            {
                @Override
                public void setOnCancelClick()
                {

                }

                @Override
                public void setOnApplyClick()
                {
                    adapter.getKalaPhoto().clear();
                    mPresenter.updateGallery();
                }
            });
        }else{
            showToast(R.string.updateKalaInfoWarning, Constants.INFO_MESSAGE());
        }
    }

    @OnClick(R.id.fabRefresh)
    public void fabRefresh() {
        fabMenu.close(true);
        alertDialog = customLoadingDialog.showLoadingDialog(GoodsInfoActivity.this);
        mPresenter.updateGoodsList();
    }

    @OnClick(R.id.imgBack)
    public void imgBack() {
        GoodsInfoActivity.this.finish();
    }





    private void showProgressBar() {
        customProgressBar.showGalleryProgressBar(GoodsInfoActivity.this);
    }

    @Override
    public Context getAppContext() {
        return GoodsInfoActivity.this;
    }

    /*
    show update progress bar
     */
    @Override
    public void updateProgressBar(int progress) {
        customProgressBar.updateProgress(progress);
    }

    @Override
    public void onStartProgressBar() {
        showProgressBar();
    }

    @Override
    public void onUpdateGallery(ArrayList<KalaPhotoModel> kalaPhotoModels) {
        fabUpdateGallery.setClickable(true);
        Log.i("onUpdateGallery", "onUpdateGallery: "+kalaPhotoModels.size());
        if (adapter!=null){
        adapter.setKalaPhoto(kalaPhotoModels);
        adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetGallery(ArrayList<KalaPhotoModel> kalaPhotoModels) {
        adapter.setKalaPhoto(kalaPhotoModels);
    }

    @Override
    public void closeProgressBar() {
        customProgressBar.closeProgress();
    }

    @Override
    public void closeLoading() {
        if (alertDialog != null) {
            try {
                alertDialog.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void showAlert(int resId, int messageType) {
        customAlertDialog.showMessageAlert(GoodsInfoActivity.this, false, "", getString(resId), messageType, getString(R.string.apply));
    }

    @Override
    public void showToast(int resId, int messageType) {
        customAlertDialog.showToast(GoodsInfoActivity.this, getString(resId), messageType, Constants.DURATION_LONG());
    }




    /*
  result kala and show to recycler
   */
    @Override
    public void onGetListOfGoods(ArrayList<RptKalaInfoModel> rptKalaInfoModels) {
            adapter = new RptKalaInfoAdapter(GoodsInfoActivity.this, rptKalaInfoModels);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GoodsInfoActivity.this, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            mPresenter.getGallery();
    }

    /*
    get kala ccBrand list for show to filter list
       and show Spinner list
     */
    @Override
    public void onGetKalaInfoCcBrandList(ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList) {
        kalaInfoByCcBrand = kalaInfoByCcBrandList;
        showSelectFilterByCcBrand();

    }

    /*
   get kala ccGoroh list for show to filter list
   and show Spinner list
    */
    @Override
    public void onGetKalaInfoCcGorohList(ArrayList<RptKalaInfoModel> kalaInfoByCcGorohList) {
        kalaInfoByCcGoroh = kalaInfoByCcGorohList;
        showSelectFilterByGoroh();
    }

    /*
     show filter Spinner by brand
     */
    private void showSelectFilterByCcBrand() {
        ArrayList<String> nameBrand = new ArrayList<>();

        for (int i = 0; i < kalaInfoByCcBrand.size(); i++) {
            nameBrand.add(kalaInfoByCcBrand.get(i).getNameBrand());

        }
        customSpinner.showMultiSelectSpinner(this, nameBrand, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {

            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                ccBrands = "";
                for (Integer selectedIndex : selectedIndexes) {
                    ccBrands += kalaInfoByCcBrand.get(selectedIndex).getCcBrand() + ",";
                }
                if (ccBrands.endsWith(",")) {
                    ccBrands = ccBrands.substring(0, ccBrands.lastIndexOf(','));
                }
                if (ccBrands.length() > 0) {
                    if (ccBrands.equals("0")) {
                        mPresenter.getListOfGoods();
                    } else {
                        mPresenter.getKalaByCcBrand(ccBrands);
                    }
                }
            }
        });
    }


    /*
    show filter Spinner by goroh
    */
    private void showSelectFilterByGoroh() {
        ArrayList<String> nameBrand = new ArrayList<>();

        for (int i = 0; i < kalaInfoByCcGoroh.size(); i++) {
            nameBrand.add(kalaInfoByCcGoroh.get(i).getNameGoroh());

        }
        customSpinner.showMultiSelectSpinner(this, nameBrand, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                ccGoroh = "";
                for (Integer selectedIndex : selectedIndexes) {
                    ccGoroh += kalaInfoByCcGoroh.get(selectedIndex).getCcGoroh() + ",";
                }
                if (ccGoroh.endsWith(",")) {
                    ccGoroh = ccGoroh.substring(0, ccGoroh.lastIndexOf(','));
                }
                if (ccGoroh.length() > 0) {
                    mPresenter.getKalaByCcGoroh(ccGoroh);
                }
            }
        });
    }


    /*
    setup start
     */

    private void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "startMVPOps", "");
        }
    }

    private void initialize(GoodsInfoMVP.RequiredViewOps view) {
        try {
            mPresenter = new GoodsInfoPresenter(view);
            stateMaintainer.put(GoodsInfoMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "initialize", "");
        }
    }

    private void reinitialize(GoodsInfoMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(GoodsInfoMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "reinitialize", "");
            }
        }
    }


}
