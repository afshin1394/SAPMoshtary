package com.saphamrah.MVP.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.CustomSpinnerAdapter;
import com.saphamrah.Adapter.JayezehParentAlertAdapter;
import com.saphamrah.Adapter.RequestGoodsAdapter;
import com.saphamrah.Adapter.RequestGoodsListAdapter;
import com.saphamrah.Adapter.RequestedGoodAdapter;
import com.saphamrah.Adapter.RequestedGridGoodAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.DarkhastKalaMVP;
import com.saphamrah.CustomView.BottomBar;
import com.saphamrah.CustomView.CustomScrollView;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.CustomView.MyBounceInterpolator;
import com.saphamrah.CustomView.SnapToBlock;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.MVP.Presenter.DarkhastKalaPresenter;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.PubFunc.LanguageUtil;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.JayezehByccKalaCodeModel;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaFilterUiModel;
import com.saphamrah.UIModel.KalaMojodiZaribModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;


import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class DarkhastKalaActivity extends AppCompatActivity implements DarkhastKalaMVP.RequiredViewOps, JayezehParentAlertAdapter.OnItemClickListener {

    private final String TAG = this.getClass().getSimpleName();

    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, DarkhastKalaActivity.this);
    private DarkhastKalaMVP.PresenterOps mPresenter;
    //    public int heightOfRecycler = 0;
//    public int widthOfRecycler = 0;
    int firstVisibleItemPosition = 0;
    private int tedadKalaAsasi;


    private int tedadKalaAsasiWithTedad;
    private int ccMoshtary;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;
    private ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels;
    private ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels; // goods that user added (customer request this goods)
    private RequestedGoodAdapter adapter;// goods that user added (customer request this goods)
    private RequestGoodsAdapter adapterRequestKala;

    private RequestGoodsListAdapter adapterRequestKalaList;
    private ElatAdamDarkhastModel elatAdamDarkhastModel;
    private final int READ_EXTERNAL_STORAGE_PERMISSION = 100;
    private final int TAKE_IMAGE = 101;
    private JayezehParentAlertAdapter jayezehAlertAdapter;
private FloatingActionButton fabShowMoshtaryGharardad;

    private View alertView;
    private AlertDialog show;
    private EditText edttxtCountCarton;
    private EditText edttxtCountBaste;
    private EditText edttxtCountAdad;
    private FloatingActionButton fabAdamSefaresh;
    private FloatingActionButton fabNazaratAndPishnahad;
    FloatingActionButton fabAddCatalog;
    FloatingActionButton fabAddList;
    //TODO
    private RequestedGridGoodAdapter adapterRequestKalaListGrid;


    //New GridView listItems
    private ArrayList<Integer> selectedPosition = new ArrayList<>();
    private ArrayList<KalaMojodiZaribModel> selectedItem = new ArrayList<>();
    private RecyclerView recyclerViewNew;
    private GridLayoutManager mLayoutManager;
    private ImageView btn_menu;

    private ArrayList<KalaFilterUiModel> kalaFilterUiModels = new ArrayList<>();
    private ArrayList<String> itemsKalaFilter = new ArrayList<>();
    public int numberOfDisplayItems = 4;
    private int SpanCount = 2;


    private ImageView btnChooseShowType;
    private int goodsNumberItemEachSection;

    //

    public enum AddItemType {
        SHOW_LIST, SHOW_GRID_LIST, NONE
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darkhast_kala);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);


        kalaDarkhastFaktorModels = new ArrayList<>();
        kalaMojodiZaribModels = new ArrayList<>();
        //TODO


        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        fabAddCatalog = findViewById(R.id.fabAddAsCatalog);
        fabAddList = findViewById(R.id.fabAddAsList);
        FloatingActionButton fabSearch = findViewById(R.id.fabSearch);
        FloatingActionButton fabShowCustomerInfo = findViewById(R.id.fabShowCustomerInfo);
        fabShowMoshtaryGharardad = findViewById(R.id.fabShowMoshtaryGharardad);
        fabNazaratAndPishnahad = findViewById(R.id.fabNazaratAndPishnahad);
        //New Floating Action Button
        //TODO

        fabAdamSefaresh = findViewById(R.id.fabAdamSefaresh);
        RecyclerView recyclerViewRequestedGoods = findViewById(R.id.recyclerView);
        adapter = new RequestedGoodAdapter(DarkhastKalaActivity.this, kalaDarkhastFaktorModels, true, new RequestedGoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final KalaDarkhastFaktorSatrModel kalaDarkhastFaktorSatrModel, final int position) {
                customAlertDialog.showLogMessageAlert(DarkhastKalaActivity.this, false, getResources().getString(R.string.warning), getResources().getString(R.string.deleteWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
                    @Override
                    public void setOnCancelClick() {
                    }

                    @Override
                    public void setOnApplyClick() {
                        mPresenter.checkRemoveKala(kalaDarkhastFaktorSatrModel, position, ccMoshtary);
                    }
                });
            }

            @Override
            public void onItemClickJayezeh(int CcKalaCode, int tedadKala, Long ccDarkhastFaktor, double mablaghForosh) {
                Log.i("DarkhastKala", "CcKalaCode : " + String.valueOf(CcKalaCode) + " , tedadKala  :" + tedadKala + "  ,  ccDarkhastFaktor : " + ccDarkhastFaktor);
                mPresenter.checkJayezehParent(CcKalaCode, tedadKala, ccDarkhastFaktor, mablaghForosh);
            }

        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DarkhastKalaActivity.this);
        recyclerViewRequestedGoods.setLayoutManager(mLayoutManager);
        recyclerViewRequestedGoods.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRequestedGoods.addItemDecoration(new DividerItemDecoration(DarkhastKalaActivity.this, 0));
        recyclerViewRequestedGoods.setAdapter(adapter);


        adapterRequestKala = new RequestGoodsAdapter(DarkhastKalaActivity.this, this.kalaMojodiZaribModels, new RequestGoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel, int position) {
            }
        });
        adapterRequestKalaList = new RequestGoodsListAdapter(DarkhastKalaActivity.this, this.kalaMojodiZaribModels, false, new RequestGoodsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel, int position) {

            }
        });

        adapterRequestKalaListGrid = new RequestedGridGoodAdapter(DarkhastKalaActivity.this, this.kalaMojodiZaribModels, new RequestedGridGoodAdapter.OnItemEventListener() {
            @Override
            public void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel, int position) {

            }

            @Override
            public void onLeftSwipeItemScroll(CustomScrollView view) {

            }


        });

        customAlertDialog = new CustomAlertDialog(DarkhastKalaActivity.this);
        customLoadingDialog = new CustomLoadingDialog();
        startMVPOps();
        mPresenter.getKalaFilter();
        mPresenter.checkZanjiree();


        new BottomBar(DarkhastKalaActivity.this, 2, new BottomBar.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //mPresenter.checkBottomBarClick(position);
                mPresenter.checkBottomBarClick(position, kalaDarkhastFaktorModels.size());
            }
        });

        Intent getIntent = getIntent();
        ccMoshtary = getIntent.getIntExtra("ccMoshtary", -1);
        tedadKalaAsasi = 0;
        tedadKalaAsasiWithTedad = 0;
        mPresenter.getNoeMasouliat();
        mPresenter.getNameListOfKalaAdamForosh();

        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(DarkhastKalaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(DarkhastKalaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(DarkhastKalaActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissions.size() > 0) {
                ActivityCompat.requestPermissions(DarkhastKalaActivity.this, permissions.toArray(new String[permissions.size()]), READ_EXTERNAL_STORAGE_PERMISSION);
            } else {
                mPresenter.getAllKalaWithMojodiZarib(ccMoshtary,  AddItemType.NONE);
            }
        } else {
            mPresenter.getAllKalaWithMojodiZarib(ccMoshtary,  AddItemType.NONE);
        }
        //TODO  loading اجرا می شود پس نیاز نیست getAllKalaWithMojodiZarib این متد در داخل
//        mPresenter.getAllRequestedGoods();





        fabAddCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fabMenu.close(true);
//                showAddItemAlert(true);
                fabMenu.close(true);
//                showGridItemAlert();
                mPresenter.getAllKalaWithMojodiZarib(ccMoshtary,  AddItemType.SHOW_GRID_LIST);

                Log.i("fabNewAddList", "onClick: ");
            }
        });

        fabAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                mPresenter.getAllKalaWithMojodiZarib(ccMoshtary,  AddItemType.SHOW_LIST);


            }
        });


        fabSearch.setVisibility(View.GONE);

        fabAdamSefaresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getElatAdamSefaresh(ccMoshtary);
            }
        });


        //TODO
//        fabNewAddList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


        fabShowCustomerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DarkhastKalaActivity.this, CustomerInfoActivity.class);
                intent.putExtra(CustomerInfoActivity.CCMOSHTARY_KEY, ccMoshtary);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        });

        fabShowMoshtaryGharardad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DarkhastKalaActivity.this, RptMoshtaryGharardad.class);
                intent.putExtra("from", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

            }
        });

        int SedayeMoshtaryEnable = Integer.parseInt( new ParameterChildDAO(getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_Sedaye_Moshtary_Enable()));
        if(SedayeMoshtaryEnable==0)
            fabNazaratAndPishnahad.setVisibility(View.GONE);


        fabNazaratAndPishnahad.setOnClickListener(v -> {

            Intent intent = new Intent(DarkhastKalaActivity.this, NazaratAndPishnahadActivity.class);
            intent.putExtra("ccMoshtary", ccMoshtary);
            intent.putExtra("from", 1);
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        });




    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.getAllKalaWithMojodiZarib(ccMoshtary, AddItemType.NONE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (elatAdamDarkhastModel != null && elatAdamDarkhastModel.getNameElatAdamDarkhast() != null) {
            outState.putInt("ccElatAdamDarkhast", elatAdamDarkhastModel.getCcElatAdamDarkhast());
            outState.putString("nameElatAdamDarkhast", elatAdamDarkhastModel.getNameElatAdamDarkhast());
            outState.putInt("getImageElatAdamDarkhast", elatAdamDarkhastModel.getGetImage());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            elatAdamDarkhastModel.setCcElatAdamDarkhast(savedInstanceState.getInt("ccElatAdamDarkhast"));
            elatAdamDarkhastModel.setNameElatAdamDarkhast(savedInstanceState.getString("nameElatAdamDarkhast"));
            elatAdamDarkhastModel.setGetImage(savedInstanceState.getInt("getImageElatAdamDarkhast"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    if (data != null && data.getExtras() != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        mPresenter.checkAdamSefareshForInsert(ccMoshtary, elatAdamDarkhastModel, new PubFunc().new ImageUtils().convertBitmapToByteArray(DarkhastKalaActivity.this, bitmap, Constants.BITMAP_TO_BYTE_QUALITY()), "");
                    } else {
                        showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "DarkhastKalaActivity", "onActivityResult", "");
                }
            }
        }
    }

    @Override
    public Context getAppContext() {
        return DarkhastKalaActivity.this;
    }

    @Override
    public void hideNoRequestButton() {
        fabAdamSefaresh.setVisibility(View.GONE);
    }
    public void hideFabAddButton() {
        fabAddCatalog.setVisibility(View.GONE);
        fabAddList.setVisibility(View.GONE);
    }

    @Override
    public void prepareDataForCheckInsertFaktor() {
        Intent intent = new Intent(DarkhastKalaActivity.this, VerifyRequestActivity.class);
        intent.putExtra("ccMoshtary", ccMoshtary);
        startActivity(intent);
        DarkhastKalaActivity.this.finish();
        //mPresenter.checkInsertFaktor(ccMoshtary , tedadKalaAsasi , tedadKalaAsasiWithTedad);
    }

    @Override
    public void openBarkhordAvalieActivity() {
        Intent intent = new Intent(DarkhastKalaActivity.this, BarkhordAvalieForoshandehActivity.class);
        intent.putExtra("ccMoshtary", ccMoshtary);
        startActivity(intent);
        DarkhastKalaActivity.this.finish();
    }

    @Override
    public void onGetRequestedGoods(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels) {
        this.kalaDarkhastFaktorModels.clear();
        this.kalaDarkhastFaktorModels.addAll(kalaDarkhastFaktorSatrModels);
        adapter.notifyDataSetChanged();
        adapterRequestKalaListGrid.notifyDataSetChanged();
        adapterRequestKala.notifyDataSetChanged();
    }

    @Override
    public void onGetAllKalaWithMojodiZarib(ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels,  AddItemType type) {
        this.kalaMojodiZaribModels.clear();
        this.kalaMojodiZaribModels.addAll(kalaMojodiZaribModels);
        adapterRequestKalaListGrid.notifyDataSetChanged();
        adapterRequestKala.notifyDataSetChanged();
        adapterRequestKalaList.notifyDataSetChanged();



            switch (type) {
                case SHOW_GRID_LIST:
                    showGridItemAlert();
                    break;

                case SHOW_LIST:
                    showAddItemAlert(false);
                    break;
            }

    }

    @Override
    public void onErrorAddNewRequestedKala(int errorResId) {
        try {
            TextView lblError = alertView.findViewById(R.id.lblError);
            lblError.setVisibility(View.VISIBLE);
            lblError.setText(errorResId);
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "DarkhastKalaActivity", "onErrorAddNewRequestedKala", "");
        }
    }

    @Override
    public void onErrorAddNewRequestedKala(int resId, String parameter) {
        try {
            TextView lblError = alertView.findViewById(R.id.lblError);
            lblError.setVisibility(View.VISIBLE);
            String errorMessage = String.format(getString(resId), parameter);
            lblError.setText(errorMessage);
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "DarkhastKalaActivity", "onErrorAddNewRequestedKala", "");
        }
    }

    @Override
    public void onSuccessAddNewRequestedKala(boolean isTedadKalaAsasi, boolean isTedadKalaAsasiWithTedad) {

        if (isTedadKalaAsasi) {
            tedadKalaAsasi++;
        }
        if (isTedadKalaAsasiWithTedad) {
            tedadKalaAsasiWithTedad++;
        }
    }


    @Override
    public void closeSelectNewGoodAlert(int position, int count) {
//        if (count == 0) {
//            this.kalaMojodiZaribModels.remove(position);
//        }
//        adapterRequestKala.notifyDataSetChanged();
//        adapterRequestKalaList.notifyDataSetChanged();
        adapterRequestKalaList.notifyItemChanged(position);
        adapterRequestKala.notifyItemChanged(position);
        adapterRequestKalaListGrid.notifyItemChanged(position);

        try {
            edttxtCountAdad.setText("");
            edttxtCountBaste.setText("");
            edttxtCountCarton.setText("");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        //show.dismiss();
    }

    @Override
    public void onSuccessRemoveKala(int position) {
        Log.d("DarkhastKala", "remove kala position : " + position);
        this.kalaDarkhastFaktorModels.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetElatAdamDarkhast(final ArrayList<ElatAdamDarkhastModel> elatAdamSefareshModels, final ArrayList<Integer> elatAdamDarkhastIds, final ArrayList<String> elatAdamDarkhastTitles) {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(DarkhastKalaActivity.this, elatAdamDarkhastTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                mPresenter.checkSeletedAdamSefareshItem(ccMoshtary, elatAdamSefareshModels.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void showTakeImageAlert(ElatAdamDarkhastModel elatAdamDarkhastModel) {
        this.elatAdamDarkhastModel = elatAdamDarkhastModel;
        customAlertDialog.showMessageAlert(DarkhastKalaActivity.this, "", getResources().getString(R.string.needTakeImage), Constants.INFO_MESSAGE(), getResources().getString(R.string.takeImage), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
                openCamera(TAKE_IMAGE);
            }

            @Override
            public void setOnApplyClick() {

            }
        });
    }

    @Override
    public void showDuplicatedCustomerCodeAlert(final ElatAdamDarkhastModel elatAdamDarkhastModel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DarkhastKalaActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_duplicated_customer_code, null);
        final CustomTextInputLayout txtinputCode = myview.findViewById(R.id.txtinputLay);
        final EditText edttxt = myview.findViewById(R.id.txt);
        Button btnOK = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputCode.setTypeface(font);
        edttxt.setTypeface(font);
        btnOK.setTypeface(font);
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        if (!(DarkhastKalaActivity.this).isFinishing()) {
            final AlertDialog show = builder.show();
            try {
                if (show.getWindow() != null) {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    show.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                }
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(DarkhastKalaActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), "", "MojodiGiriActivity", "showDuplicatedCustomerCodeAlert", "");
            }

            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtinputCode.setError(null);
                    if (edttxt.getText().toString().length() > 0) {
                        mPresenter.checkAdamSefareshForInsert(ccMoshtary, elatAdamDarkhastModel, null, edttxt.getText().toString());
                    } else {
                        txtinputCode.setError(getResources().getString(R.string.errorCustomerDuplicatedCode));
                    }
                }
            });
        }
    }

    @Override
    public void onSuccessInsertAdamDarkhast() {
        customAlertDialog.showMessageAlert(DarkhastKalaActivity.this, getResources().getString(R.string.success), getResources().getString(R.string.successfullyDoneOps), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                Intent intent = new Intent(DarkhastKalaActivity.this, TemporaryRequestsListActivity.class);
                intent.putExtra("requests", false);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                DarkhastKalaActivity.this.finish();
            }
        });
    }

    @Override
    public void showNameListOfKalaAdamForosh(String kalaNames) {
        customAlertDialog.showMessageAlert(DarkhastKalaActivity.this, false, "", kalaNames, Constants.INFO_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void closeActivity() {
        DarkhastKalaActivity.this.finish();
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(DarkhastKalaActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void showAlertDialog(int resId, int messageType, boolean closeActivity) {
        customAlertDialog.showMessageAlert(DarkhastKalaActivity.this, closeActivity, "", getString(resId), messageType, getString(R.string.apply));
    }

    @Override
    public void showErrorAlertLoadingMaxMojodi(List<KalaModel> kalaModels) {
        String errorMessage = getString(R.string.errorBiggerThanMaxMojodi) + "\n";
        for (KalaModel kalaModel : kalaModels) {
            errorMessage += kalaModel.getCodeKala() + " - " + kalaModel.getNameKala() + " - " + getString(R.string.mojodiKol) + " " + kalaModel.getTedadMojodyGhabelForosh() + " " + getString(R.string.adad) + "\n";
        }
        customAlertDialog.showMessageAlert(DarkhastKalaActivity.this, false, "", errorMessage, Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    private void handleKalaIndices(int i) {

        edttxtCountCarton.setText("");
        edttxtCountBaste.setText("");
        edttxtCountAdad.setText("");
        if (kalaMojodiZaribModels.get(i).getTedadPishnahadi() > 0) {
            Log.i("tedadPishnahadi", "onItemClick: " + String.valueOf(kalaMojodiZaribModels.get(i).getTedadPishnahadi()));
            edttxtCountAdad.setText(String.valueOf(kalaMojodiZaribModels.get(i).getTedadPishnahadi()));
        }
    }


    private void showAddItemAlert(final boolean showCatalog) {


        final ArrayList<Integer> selectedPosition = new ArrayList<>();
        final ArrayList<KalaMojodiZaribModel> selectedItem = new ArrayList<>();

        final ArrayList<KalaMojodiZaribModel> arrayListKalaModel = new ArrayList<>(kalaMojodiZaribModels); //local list for using in adapter
        final ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModelsFilter = new ArrayList<>(kalaMojodiZaribModels); //local list for using in adapter
        Log.d("DarkhastKalaActivity", "arrayListKalaModel : " + arrayListKalaModel);
        for (int i = 0; i < arrayListKalaModel.size(); i++) {
            if (arrayListKalaModel.get(i).getTedad() == 0) {
                Log.d("DarkhastKalaActivity", "arrayListKalaModel.get(i) : " + arrayListKalaModel.get(i));

                arrayListKalaModel.remove(i);
            }
        }

        CustomSpinnerAdapter adapterSpinner = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, itemsKalaFilter);
        final AlertDialog.Builder builder = new AlertDialog.Builder(DarkhastKalaActivity.this);
        alertView = getLayoutInflater().inflate(R.layout.alert_goodslist_for_request, null);
        final CustomTextInputLayout txtinputCountCarton = alertView.findViewById(R.id.txtinputCartonCount);
        final CustomTextInputLayout txtinputCountBaste = alertView.findViewById(R.id.txtinputBastehCount);
        final CustomTextInputLayout txtinputCountAdad = alertView.findViewById(R.id.txtinputAdadCount);
         Spinner spinnerKalaFilter = alertView.findViewById(R.id.spinnerKalaFilter);
        edttxtCountCarton = alertView.findViewById(R.id.txtCartonCount);
        edttxtCountBaste = alertView.findViewById(R.id.txtBastehCount);
        edttxtCountAdad = alertView.findViewById(R.id.txtAdadCount);

        spinnerKalaFilter.setAdapter(adapterSpinner);


        final RecyclerView recyclerView = alertView.findViewById(R.id.recyclerView);
        if (showCatalog) {
            adapterRequestKala = new RequestGoodsAdapter(DarkhastKalaActivity.this, arrayListKalaModel, new RequestGoodsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel, int position) {

                }
            });


        } else {
            adapterRequestKalaList = new RequestGoodsListAdapter(DarkhastKalaActivity.this, arrayListKalaModel, false, new RequestGoodsListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel, int position) {
                    Log.i("positionvisible", "onItemClick: " + position);
                    selectedItem.clear();
                    selectedItem.add(arrayListKalaModel.get(position));
                    selectedPosition.clear();
                    selectedPosition.add(position);
                    edttxtCountCarton.setText("");
                    edttxtCountBaste.setText("");
                    edttxtCountAdad.setText("");
                    if (kalaMojodiZaribModel.getTedadPishnahadi() > 0) {
                        edttxtCountAdad.setText(String.valueOf(kalaMojodiZaribModel.getTedadPishnahadi()));
                    }
                }
            });
        }
        if (showCatalog) {
            SnapHelper snapHelper = new PagerSnapHelper();
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(DarkhastKalaActivity.this, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            snapHelper.attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(adapterRequestKala);

            handleKalaIndices(firstVisibleItemPosition);
            Log.i("firstOne", "showAddItemAlert: " + firstVisibleItemPosition);


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (recyclerView.getScrollState() == SCROLL_STATE_IDLE) {
                        firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                        Log.i("firstVisibleItemCount", "onScrolled: " + firstVisibleItemPosition);
                        handleKalaIndices(firstVisibleItemPosition);
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                }
            });

        } else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DarkhastKalaActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(DarkhastKalaActivity.this, 0));
            recyclerView.setAdapter(adapterRequestKalaList);
        }
        CustomTextInputLayout txtinputSearch = alertView.findViewById(R.id.txtinputSearchKalaName);
        EditText edttxtSearch = alertView.findViewById(R.id.txtSearchKalaName);

        final TextView lblError = alertView.findViewById(R.id.lblError);
        Button btnCancel = alertView.findViewById(R.id.btnCancel);
        Button btnOK = alertView.findViewById(R.id.btnApply);
        final Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputSearch.setTypeface(font);
        txtinputCountCarton.setTypeface(font);
        txtinputCountBaste.setTypeface(font);
        txtinputCountAdad.setTypeface(font);
        edttxtSearch.setTypeface(font);
        edttxtCountCarton.setTypeface(font);
        edttxtCountBaste.setTypeface(font);
        edttxtCountAdad.setTypeface(font);
        lblError.setTypeface(font);
        btnCancel.setTypeface(font);
        btnOK.setTypeface(font);
        lblError.setVisibility(View.GONE);
        builder.setCancelable(true);
        builder.setView(alertView);
        builder.create();

        if (!(DarkhastKalaActivity.this).isFinishing()) {
            show = builder.show();
            try {
                if (show.getWindow() != null) {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    show.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                }
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(DarkhastKalaActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), "", "DarkhastKalaActivity", "showAddItemAlert", "");
            }

            spinnerKalaFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    edttxtSearch.setText("");
                    arrayListKalaModel.clear();
                    kalaMojodiZaribModelsFilter.clear();
                    selectedItem.clear();
                    selectedPosition.clear();
                    if (kalaFilterUiModels.get(position).getCcGoroh() == 0 ){
                        kalaMojodiZaribModelsFilter.addAll(kalaMojodiZaribModels) ;
                        arrayListKalaModel.addAll(kalaMojodiZaribModels) ;
                        adapterRequestKalaListGrid.notifyDataSetChanged();
                        adapterRequestKala.notifyDataSetChanged();
                        adapterRequestKalaList.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < kalaMojodiZaribModels.size(); i++) {
                            int ccGorohKala = kalaMojodiZaribModels.get(i).getCcGorohKala();
                            if (ccGorohKala == kalaFilterUiModels.get(position).getCcGoroh()) {
                                arrayListKalaModel.add(kalaMojodiZaribModels.get(i));
                                kalaMojodiZaribModelsFilter.add(kalaMojodiZaribModels.get(i));
                                adapterRequestKalaListGrid.notifyDataSetChanged();
                                adapterRequestKala.notifyDataSetChanged();
                                adapterRequestKalaList.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edttxtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0) {
                        String searchWord = new LanguageUtil().convertFaNumberToEN(s.toString());
                        arrayListKalaModel.clear();
                        adapterRequestKalaListGrid.notifyDataSetChanged();
                        adapterRequestKala.notifyDataSetChanged();
                        adapterRequestKalaList.notifyDataSetChanged();
                        selectedItem.clear();
                        selectedPosition.clear();
                        //adapterRequestKala.clearSelecedItem();
                        for (int i = 0; i < kalaMojodiZaribModelsFilter.size(); i++) {
                            String nameKala = new LanguageUtil().convertFaNumberToEN(kalaMojodiZaribModelsFilter.get(i).getNameKala() + kalaMojodiZaribModelsFilter.get(i).getBarCode());
                            if (nameKala.contains(searchWord)) {
                                arrayListKalaModel.add(kalaMojodiZaribModelsFilter.get(i));
                                adapterRequestKalaListGrid.notifyDataSetChanged();
                                adapterRequestKala.notifyDataSetChanged();
                                adapterRequestKalaList.notifyDataSetChanged();
                            }
                        }
                    } else //if (s.length() == 0)
                    {
                        arrayListKalaModel.clear();
                        //adapterRequestKala.clearSelecedItem();
                        arrayListKalaModel.addAll(kalaMojodiZaribModelsFilter);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });

            btnOK.setOnClickListener(v -> {


                lblError.setVisibility(View.GONE);
                lblError.setText("");

                if (showCatalog) {
                    if (((LinearLayoutManager) recyclerView.getLayoutManager()) != null) {
                        try {
                            int position = firstVisibleItemPosition;
                            Log.i("TAG", "onClick: " + position);
                            if (position >= 0) {
                                selectedItem.clear();
                                selectedPosition.clear();
                                selectedItem.add(arrayListKalaModel.get(position));
                                selectedPosition.add(position);

                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                } else {

                    selectedItem.clear();
                    if (selectedPosition.size() > 0) {
                        selectedItem.add(arrayListKalaModel.get(selectedPosition.get(0)));
                    }
                }

                if (selectedPosition.size() == 0 || selectedItem.size() == 0) {
                    lblError.setVisibility(View.VISIBLE);
                    lblError.setText(getResources().getString(R.string.errorSelectItem));
                } else {
//                    if(SelectFaktor.getccGorohNoeMoshtary() != ccMoshtayZanjirei)
//                    {
//                        for (Kala_NewFaktor kala_2Gheymaty: CreateFaktorActivity.KalaForNewFaktors)
//                        {
//                            if(kala_2Gheymaty.getccKalaCode() == entity.getccKalaCode())
//                            {
//                                long DateDiff = entity.getTarikhTolid().getTime() - kala_2Gheymaty.getTarikhTolid().getTime();
//                                long diffInSec = TimeUnit.MILLISECONDS.toMinutes(DateDiff);
//
//                                if (diffInSec > 1 && entity.getTedadFaktor() <= kala_2Gheymaty.getTedadMojodyGhabelForosh() )
//                                    StrError += "در کالای " + entity.getNameKala() + " از کالا ی قدیمی تر  ثبت نمایید." + "\n";
//                            }
//                        }
//                    }


                    mPresenter.checkAddKala(ccMoshtary, selectedPosition.get(0), selectedItem.get(0), edttxtCountCarton.getText().toString().trim(), edttxtCountBaste.getText().toString().trim(), edttxtCountAdad.getText().toString().trim());
                }
            });

        }
    }


    private void openCamera(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }


    @Override
    public void showAlertLoading() {
        alertDialog = customLoadingDialog.showLoadingDialog(DarkhastKalaActivity.this);
    }

    @Override
    public void closeAlertLoading() {
        if (alertDialog != null) {
            try {
                alertDialog.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(DarkhastKalaActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), TAG, "", "closeLoadingDialog", "");
            }
        }
    }


    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", DarkhastKalaActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(DarkhastKalaMVP.RequiredViewOps view) {
        try {
            mPresenter = new DarkhastKalaPresenter(view);
            stateMaintainer.put(DarkhastKalaMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", DarkhastKalaActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(DarkhastKalaMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(DarkhastKalaMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", DarkhastKalaActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }


    /*
    show graphical requestGoods
     */
    private void showGridItemAlert() {

        alertView = getLayoutInflater().inflate(R.layout.alert_grid_goodlist_for_request, null);
        final CustomTextInputLayout txtinputCountCartonNew = alertView.findViewById(R.id.txtinputCartonCount);
        final CustomTextInputLayout txtinputCountBasteNew = alertView.findViewById(R.id.txtinputBastehCount);
        final CustomTextInputLayout txtinputCountAdadNew = alertView.findViewById(R.id.txtinputAdadCount);
        recyclerViewNew = alertView.findViewById(R.id.recyclerViewGrid);
        final ArrayList<KalaMojodiZaribModel> arrayListKalaModel = new ArrayList<>(kalaMojodiZaribModels); //local list for using in adapter

        Spinner spinnerKalaFilter = alertView.findViewById(R.id.spinnerKalaFilter);
        final ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModelsFilter = new ArrayList<>(kalaMojodiZaribModels); //local list for using in adapter
        CustomSpinnerAdapter adapterSpinner = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, itemsKalaFilter);
        spinnerKalaFilter.setAdapter(adapterSpinner);

        final AlertDialog.Builder builder = new AlertDialog.Builder(DarkhastKalaActivity.this);
        CustomTextInputLayout txtinputSearch = alertView.findViewById(R.id.txtinputSearchKalaName);
        EditText edttxtSearch = alertView.findViewById(R.id.txtSearchKalaNameGridView);


        edttxtCountCarton = alertView.findViewById(R.id.txtCartonCount);
        edttxtCountBaste = alertView.findViewById(R.id.txtBastehCount);
        edttxtCountAdad = alertView.findViewById(R.id.txtAdadCount);


        final TextView lblError = alertView.findViewById(R.id.lblError);
        Button btnCancel = alertView.findViewById(R.id.btnCancel);
        Button btnOK = alertView.findViewById(R.id.btnApply);
        btnChooseShowType = alertView.findViewById(R.id.btnChooseShowType);
        final Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputSearch.setTypeface(font);
        txtinputCountCartonNew.setTypeface(font);
        txtinputCountBasteNew.setTypeface(font);
        txtinputCountAdadNew.setTypeface(font);
        edttxtSearch.setTypeface(font);
        edttxtCountCarton.setTypeface(font);
        edttxtCountBaste.setTypeface(font);
        edttxtCountAdad.setTypeface(font);
        lblError.setTypeface(font);
        btnCancel.setTypeface(font);
        btnOK.setTypeface(font);
        lblError.setVisibility(View.GONE);
        builder.setCancelable(true);
        builder.setView(alertView);
        builder.create();
        if (!(DarkhastKalaActivity.this).isFinishing()) {
            show = builder.show();
            try {
                if (show.getWindow() != null) {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    show.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                }
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(DarkhastKalaActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), "", "DarkhastKalaActivity", "showAddItemNewAlert", "");
            }


            adapterRequestKalaListGrid = new RequestedGridGoodAdapter(DarkhastKalaActivity.this, arrayListKalaModel, new RequestedGridGoodAdapter.OnItemEventListener() {
                @Override
                public void onItemClick(KalaMojodiZaribModel kalaMojodiZaribModel, int position) {
                    selectedItem.clear();
                    selectedItem.add(kalaMojodiZaribModel);
                    selectedPosition.clear();
                    selectedPosition.add(position);
                    edttxtCountCarton.setText("");
                    edttxtCountBaste.setText("");
                    edttxtCountAdad.setText("");
                    if (kalaMojodiZaribModel.getTedadPishnahadi() > 0) {
                        edttxtCountAdad.setText(String.valueOf(kalaMojodiZaribModel.getTedadPishnahadi()));
                    }
                }

                @Override
                public void onLeftSwipeItemScroll(CustomScrollView view) {
                    if (mLayoutManager != null) {
                        recyclerViewNew.requestDisallowInterceptTouchEvent(true);


                    }
                }


            });


            setGridLayoutManager(SpanCount);
            recyclerViewNew.post(new Runnable() {
                public void run() {
                    int heightOfRecycler = recyclerViewNew.getHeight();
                    int widthOfRecycler = recyclerViewNew.getWidth();
                    adapterRequestKalaListGrid.setMeasurements(heightOfRecycler, widthOfRecycler);
                    mPresenter.getRecyclerDetails();
                }
            });


            spinnerKalaFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    edttxtSearch.setText("");
                    arrayListKalaModel.clear();
                    kalaMojodiZaribModelsFilter.clear();
                    selectedItem.clear();
                    selectedPosition.clear();
                    if (kalaFilterUiModels.get(position).getCcGoroh() == 0 ){
                        kalaMojodiZaribModelsFilter.addAll(kalaMojodiZaribModels) ;
                        arrayListKalaModel.addAll(kalaMojodiZaribModels) ;
                        adapterRequestKalaListGrid.notifyDataSetChanged();
                        adapterRequestKala.notifyDataSetChanged();
                        adapterRequestKalaList.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < kalaMojodiZaribModels.size(); i++) {
                            int ccGorohKala = kalaMojodiZaribModels.get(i).getCcGorohKala();
                            if (ccGorohKala==kalaFilterUiModels.get(position).getCcGoroh()) {
                                arrayListKalaModel.add(kalaMojodiZaribModels.get(i));
                                kalaMojodiZaribModelsFilter.add(kalaMojodiZaribModels.get(i));
                                adapterRequestKalaListGrid.notifyDataSetChanged();
                                adapterRequestKala.notifyDataSetChanged();
                                adapterRequestKalaList.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edttxtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    adapterRequestKalaListGrid.resetLastSelectedPosition();
                    adapterRequestKalaListGrid.notifyDataSetChanged();


                    if (s.length() != 0) {
                        String searchWord = new PubFunc().new LanguageUtil().convertFaNumberToEN(s.toString());
                        arrayListKalaModel.clear();
                        adapterRequestKalaListGrid.notifyDataSetChanged();
                        adapterRequestKala.notifyDataSetChanged();
                        adapterRequestKalaList.notifyDataSetChanged();
                        selectedItem.clear();
                        selectedPosition.clear();
                        //adapterRequestKala.clearSelecedItem();
                        for (int i = 0; i < kalaMojodiZaribModels.size(); i++) {
                            String nameKala = new PubFunc().new LanguageUtil().convertFaNumberToEN(kalaMojodiZaribModels.get(i).getNameKala());
                            if (nameKala.contains(searchWord)) {
                                arrayListKalaModel.add(kalaMojodiZaribModels.get(i));
                                adapterRequestKalaListGrid.notifyDataSetChanged();
                                adapterRequestKala.notifyDataSetChanged();
                                adapterRequestKalaList.notifyDataSetChanged();
                            }
                        }
                    } else //if (s.length() == 0)
                    {
                        arrayListKalaModel.clear();
                        //adapterRequestKala.clearSelecedItem();
                        arrayListKalaModel.addAll(kalaMojodiZaribModels);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();

                }
            });

            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lblError.setVisibility(View.GONE);
                    lblError.setText("");

                    Log.i("selectedPos", "onClick: " + selectedPosition);
                    selectedItem.clear();
                    if (selectedPosition.size() > 0) {
                        selectedItem.add(arrayListKalaModel.get(selectedPosition.get(0)));
                        Log.i("selectedPos", "onClick: " + selectedPosition);
                    }


                    if (selectedPosition.size() == 0 || selectedItem.size() == 0) {
                        lblError.setVisibility(View.VISIBLE);
                        lblError.setText(getResources().getString(R.string.errorSelectItem));
                        Log.i("selectedPos", "onClick: " + selectedPosition);
                    } else {
                        Log.i("selectedPos", "onClick: " + selectedPosition);
                        mPresenter.checkAddKala(ccMoshtary, selectedPosition.get(0), selectedItem.get(0), edttxtCountCarton.getText().toString().trim(), edttxtCountBaste.getText().toString().trim(), edttxtCountAdad.getText().toString().trim());
                    }

                }
            });
        }


        btnChooseShowType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterRequestKalaListGrid != null) {
                    adapterRequestKalaListGrid.updateStatus();
                    if (recyclerViewNew.getHeight() != 0 && recyclerViewNew.getWidth() != 0)
                        adapterRequestKalaListGrid.setMeasurements(recyclerViewNew.getHeight(), recyclerViewNew.getWidth());
                    setViewPackages(adapterRequestKalaListGrid.getStatus());

                }
            }
        });

    }


    private RecyclerView.LayoutManager setGridLayoutManager(int spanCount) {
        mLayoutManager = null;
        mLayoutManager = new GridLayoutManager(DarkhastKalaActivity.this, spanCount, GridLayoutManager.VERTICAL, false);
        return mLayoutManager;
    }


    private void refreshRecyclerView(int spanCount) {
        recyclerViewNew.setAdapter(null);
        recyclerViewNew.setLayoutManager(null);
        if (recyclerViewNew.getOnFlingListener() != null) {
            recyclerViewNew.clearOnScrollListeners();
            recyclerViewNew.setOnFlingListener(null);
        }
        recyclerViewNew.setLayoutManager(setGridLayoutManager(spanCount));
        recyclerViewNew.setAdapter(adapterRequestKalaListGrid);
        adapterRequestKalaListGrid.notifyDataSetChanged();
    }

    private void refreshRecyclerView(int spanCount, boolean hasCache) {
        recyclerViewNew.setAdapter(null);
        recyclerViewNew.setLayoutManager(null);
        if (recyclerViewNew.getOnFlingListener() != null) {
            recyclerViewNew.clearOnScrollListeners();
            recyclerViewNew.setOnFlingListener(null);
        }
        if (hasCache)
            recyclerViewNew.setItemViewCacheSize(adapterRequestKalaListGrid.getItemCount());


        recyclerViewNew.setLayoutManager(setGridLayoutManager(spanCount));
        recyclerViewNew.setAdapter(adapterRequestKalaListGrid);
        adapterRequestKalaListGrid.notifyDataSetChanged();
    }


    @Override
    public void onGetGridRecyclerDetails(int status, ArrayList<KalaPhotoModel> kalaPhotoModels) {

        Log.i("ITEMNUMBERPER", "onGetGridRecyclerDetails: " + status);
        adapterRequestKalaListGrid.setStatus(status);
        setViewPackages(adapterRequestKalaListGrid.getStatus());
    }

    @Override
    public void onCheckZanjiree() {
        fabShowMoshtaryGharardad.setVisibility(View.GONE);
    }

    @Override
    public void onKalaFilter(ArrayList<KalaFilterUiModel> kalaFilterUiModels,ArrayList<String> itemsKalaFilter) {
        this.kalaFilterUiModels = kalaFilterUiModels;
        this.itemsKalaFilter = itemsKalaFilter;
    }


    private void setViewPackages(int status) {
        Animation myBounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.05, 15);
        myBounceAnimation.setInterpolator(interpolator);
        Log.i("goodsShowItemNumber", "setViewPackages: " + adapterRequestKalaListGrid.getStatus());
        SnapToBlock snapToBlock = new SnapToBlock(1);
        switch (status) {
            case Constants.SINGLE_ITEM:
                Log.i("clickeonchange", "onClick: " + adapterRequestKalaListGrid.getStatus());
                btnChooseShowType.setImageDrawable(getResources().getDrawable(R.drawable.ic_2section));
                btnChooseShowType.startAnimation(myBounceAnimation);
                refreshRecyclerView(1, false);
                recyclerViewNew.startAnimation(slideAnimation);
                snapToBlock.attachToRecyclerView(recyclerViewNew);
//                adapterRequestKalaListGrid.updateStatus();


                break;


            case Constants.DOUBLE_ITEM:
                Log.i("clickeonchange", "onClick: " + goodsNumberItemEachSection + "indouble");
//                adapterRequestKalaListGrid.updateStatus();
                btnChooseShowType.setImageDrawable(getResources().getDrawable(R.drawable.ic_4section));
                btnChooseShowType.startAnimation(myBounceAnimation);
                refreshRecyclerView(1, false);
                recyclerViewNew.startAnimation(slideAnimation);
                snapToBlock.attachToRecyclerView(recyclerViewNew);
//                adapterRequestKalaListGrid.updateStatus();

                break;


            case Constants.FOUR_ITEM:
                Log.i("clickeonchange", "onClick: " + goodsNumberItemEachSection + "inFour");


                btnChooseShowType.setImageDrawable(getResources().getDrawable(R.drawable.ic_1section));
                btnChooseShowType.startAnimation(myBounceAnimation);
                refreshRecyclerView(2, false);
                recyclerViewNew.startAnimation(slideAnimation);

                //TODO for pagination in listView
//                SnapToBlock snapToBlock = new
//                (1);
//                snapToBlock.attachToRecyclerView(recyclerViewNew);

                break;


        }


    }


    /**
     * click item recycler jayezeh for get details jayezeh in jayezeh alert dialog
     *
     * @param ccJayezeh
     * @param tedadKala
     * @param mablaghForosh
     * @param ccKalaCode
     * @param ccDarkhastFaktor
     * @param position
     */
    @Override
    public void onItemClick(int ccJayezeh, int tedadKala, double mablaghForosh, int ccKalaCode, Long ccDarkhastFaktor, int position) {
        mPresenter.checkJayezeh(ccJayezeh, tedadKala, mablaghForosh, ccKalaCode, ccDarkhastFaktor, position);
    }

    /**
     * get jayezeh kala by ccKalaCode
     *
     * @param jayezehByccKalaCodeParentModels
     * @param tedadKala
     * @param mablaghForosh
     * @param ccKalaCode
     * @param ccDarkhastFaktor
     */
    @Override
    public void onCheckJayezehParent(ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeParentModels, int tedadKala, double mablaghForosh, int ccKalaCode, Long ccDarkhastFaktor) {
        showJayezehAlert(jayezehByccKalaCodeParentModels, tedadKala, mablaghForosh, ccKalaCode, ccDarkhastFaktor);
    }


    /**
     * get response for DB for Show details Jayezeh
     *
     * @param position
     */
    @Override
    public void onCheckJayezeh(int position) {
        Log.i("jayezehAdapter", "onCheckJayezeh");
    }

    /**
     * show Jayezeh Alert dialog
     *
     * @param jayezehByccKalaCodeParentModels
     * @param tedadKala
     * @param mablaghForosh
     * @param ccKalaCode
     * @param ccDarkhastFaktor
     */
    private void showJayezehAlert(ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeParentModels, int tedadKala, double mablaghForosh, int ccKalaCode, Long ccDarkhastFaktor) {
        //local list for using in adapter
        AlertDialog.Builder builder = new AlertDialog.Builder(DarkhastKalaActivity.this);
        alertView = getLayoutInflater().inflate(R.layout.alert_jayezeh, null);
        RecyclerView recyclerView = alertView.findViewById(R.id.recyclerView);
        jayezehAlertAdapter = new JayezehParentAlertAdapter(this, jayezehByccKalaCodeParentModels, tedadKala, mablaghForosh, ccKalaCode, ccDarkhastFaktor, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(jayezehAlertAdapter);
        Button btnOK = alertView.findViewById(R.id.btnApply);
        LinearLayout lay_haventGift = alertView.findViewById(R.id.lay_haventGift);
        TextView txt_haventGift = alertView.findViewById(R.id.txt_haventGift);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));

        txt_haventGift.setTypeface(font);

        btnOK.setTypeface(font);
        builder.setCancelable(true);
        builder.setView(alertView);
        builder.create();

        // show have not jayezeh Image
        if (jayezehByccKalaCodeParentModels.size() < 1) {
            lay_haventGift.setVisibility(View.VISIBLE);
        }

        btnOK.setOnClickListener(v -> {
            show.dismiss();
        });

        if (!(DarkhastKalaActivity.this).isFinishing()) {
            show = builder.show();
            try {
                if (show.getWindow() != null) {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    show.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                }
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(DarkhastKalaActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), "", "DarkhastKalaActivity", "showAddItemAlert", "");
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new PubFunc().new LocationProvider().stopLocationProvider();
    }
}
