package com.saphamrah.MVP.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.Adapter.RequestCustomerListAdapter;
import com.saphamrah.BaseMVP.RequestCustomerListMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.MVP.Model.GetProgramModel;
import com.saphamrah.MVP.Presenter.RequestCustomerListPresenter;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

import me.anwarshahriar.calligrapher.Calligrapher;

public class RequestCustomerListActivity extends AppCompatActivity implements RequestCustomerListMVP.RequiredViewOps {

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager(), TAG, RequestCustomerListActivity.this);
    RequestCustomerListMVP.PresenterOps mPresenter;
    private final int TAKE_IMAGE = 100;
    private final int CAMERA_PERMISSION = 200;


    private MaterialSearchView searchView;
    private RequestCustomerListAdapter adapter;
    private ArrayList<MoshtaryModel> moshtaryModels;
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels;
    private ArrayList<Integer> moshtaryNoeMorajeh;
    private ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels;

    private ArrayList<MoshtaryModel> moshtaryModelsSearch; // result of search
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModelsSearch; // result of search
    private ArrayList<MoshtaryGharardadModel> moshtaryGharardadModelsSearch; // result of search

    private boolean canUpdateCustomer;
    //private ArrayList<Integer> moshtaryNoeMorajehSearch;
    private ElatAdamDarkhastModel elatAdamDarkhastModel;
    private AlertDialog alertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private CustomAlertDialog customAlertDialog;
    private int searchStatus; // not in search mode = 0 , search with name = 1 , search with code = 2 , search with NameTablo = 3 , search with Telephone = 4;
    private final int LOCATION_PERMISSION = 100;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_customer_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(RequestCustomerListActivity.this);

        /*moshtaryModels = new ArrayList<>();
        moshtaryAddressModels = new ArrayList<>();
        moshtaryNoeMorajeh = new ArrayList<>();*/
        searchStatus = 0;
        moshtaryModelsSearch = new ArrayList<>();
        moshtaryAddressModelsSearch = new ArrayList<>();
        canUpdateCustomer = false;
        //moshtaryNoeMorajehSearch = new ArrayList<>();

        searchView = findViewById(R.id.searchView);
        FloatingActionButton fabSearchName = findViewById(R.id.fabSearchName);
        FloatingActionButton fabSearchCode = findViewById(R.id.fabSearchCode);
        FloatingActionButton fabSearchNameTablo = findViewById(R.id.fabSearchNameTablo);
        FloatingActionButton fabSearchTelephone = findViewById(R.id.fabSearchTelephone);
        FloatingActionButton fabRefresh = findViewById(R.id.fabRefresh);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try
        {
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        startMVPOps();

        if (Build.VERSION.SDK_INT >= 23)
        {
            ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(RequestCustomerListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(RequestCustomerListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissions.size() > 0)
            {
                ActivityCompat.requestPermissions(RequestCustomerListActivity.this, permissions.toArray(new String[permissions.size()]), LOCATION_PERMISSION);
            }
            else
            {
                alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
                mPresenter.checkDateOfGetProgram();
                mPresenter.getCustomers();
            }
        }
        else
        {
            alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
            mPresenter.checkDateOfGetProgram();
            mPresenter.getCustomers();
        }


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String searchWord = query.trim();
                if (searchStatus == 1)
                {
                    //TODO search
                    mPresenter.searchCustomerName(searchWord, moshtaryModels, moshtaryAddressModels, moshtaryGharardadModels , moshtaryNoeMorajeh);
                }
                else if (searchStatus == 2)
                {
                    //TODO search
                    mPresenter.searchCustomerCode(searchWord, moshtaryModels, moshtaryAddressModels, moshtaryGharardadModels, moshtaryNoeMorajeh);
                } else if (searchStatus == 3) {
                    mPresenter.searchNameTablo(searchWord, moshtaryModels, moshtaryAddressModels, moshtaryGharardadModels, moshtaryNoeMorajeh);

                } else if (searchStatus == 4) {
                    mPresenter.searchTelephone(searchWord, moshtaryModels, moshtaryAddressModels, moshtaryGharardadModels, moshtaryNoeMorajeh);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0)
                {
                    if (searchStatus == 1)
                    {
                        //TODO search
                        mPresenter.searchCustomerName(newText, moshtaryModels, moshtaryAddressModels,moshtaryGharardadModels , moshtaryNoeMorajeh);
                    }
                    else if (searchStatus == 2)
                    {
                        //TODO search
                        mPresenter.searchCustomerCode(newText, moshtaryModels, moshtaryAddressModels, moshtaryGharardadModels, moshtaryNoeMorajeh);
                    } else if (searchStatus == 3) {
                        mPresenter.searchNameTablo(newText, moshtaryModels, moshtaryAddressModels, moshtaryGharardadModels, moshtaryNoeMorajeh);

                    } else if (searchStatus == 4) {
                        mPresenter.searchTelephone(newText, moshtaryModels, moshtaryAddressModels, moshtaryGharardadModels, moshtaryNoeMorajeh);
                    }
                }
                else
                {
                    visibleCloseSearchIcon();
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                visibleCloseSearchIcon();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSearchViewClosed() {
                searchStatus = 0;
                onGetCustomers(moshtaryModels , moshtaryAddressModels , moshtaryNoeMorajeh,moshtaryGharardadModels, canUpdateCustomer);
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = ((TextView)findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView)).getText().toString().trim();
                if (searchStatus == 1)
                {
                    //TODO search
                    mPresenter.searchCustomerName(searchWord, moshtaryModels, moshtaryAddressModels , moshtaryGharardadModels, moshtaryNoeMorajeh);
                }
                else if (searchStatus == 2)
                {
                    //TODO search
                    mPresenter.searchCustomerCode(searchWord, moshtaryModels, moshtaryAddressModels,moshtaryGharardadModels , moshtaryNoeMorajeh);
                }
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                searchView.closeSearch();
                searchStatus = 0;
                onGetCustomers(moshtaryModels , moshtaryAddressModels , moshtaryNoeMorajeh,moshtaryGharardadModels, canUpdateCustomer);
            }
        });


        fabSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch();
                searchView.setHint(getResources().getString(R.string.searchCustomerName));
                searchStatus = 1;
            }
        });


        fabSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch();
                searchView.setHint(getResources().getString(R.string.searchCustomerCode));
                searchStatus = 2;
            }
        });

        fabSearchNameTablo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch();
                searchView.setHint(getResources().getString(R.string.searchNameTablo));
                searchStatus = 3;
            }
        });
        fabSearchTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch();
                searchView.setHint(getResources().getString(R.string.searchTelephone));
                searchStatus = 4;
            }
        });

        fabRefresh.setOnClickListener(v -> {
            fabMenu.close(true);
            alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
            mPresenter.updateMoshtaryMorajehShodehRooz();
            recyclerView.setAdapter(null);

        });

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.checkFakeLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
                mPresenter.checkDateOfGetProgram();
                mPresenter.getCustomers();
            }
            else
            {
                customAlertDialog.showMessageAlert(RequestCustomerListActivity.this, true, "", getResources().getString(R.string.errorAccessToLocation), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
            }
        }
    }

    private void visibleCloseSearchIcon()
    {
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
    }

    @Override
    public Context getAppContext() {
        return RequestCustomerListActivity.this;
    }

    @Override
    public void onGetDateOfGetProgram(String date)
    {
        TextView lblTitle = findViewById(R.id.lblActivityTitle);
        lblTitle.setText(getResources().getString(R.string.RequestCustomerListActivityTitle , date));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onGetCustomers(final ArrayList<MoshtaryModel> moshtaryModels , final ArrayList<MoshtaryAddressModel> moshtaryAddressModels , ArrayList<Integer> arrayListNoeMorajeh, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels , boolean canUpdateCustomer)
    {

        this.moshtaryModels = moshtaryModels;
        this.moshtaryAddressModels = moshtaryAddressModels;
        this.moshtaryNoeMorajeh = arrayListNoeMorajeh;
        this.canUpdateCustomer = canUpdateCustomer;
        this.moshtaryGharardadModels=moshtaryGharardadModels;
        recyclerView = findViewById(R.id.recyclerView);
//        moshtaryGharardadModels.forEach(new Consumer<MoshtaryGharardadModel>() {
//            @Override
//            public void accept(MoshtaryGharardadModel moshtaryGharardadModel) {
//                Log.i("forEach", "accept: "+moshtaryGharardadModel.toString());
//            }
//        });

        adapter = new RequestCustomerListAdapter(RequestCustomerListActivity.this, moshtaryModels , moshtaryAddressModels , arrayListNoeMorajeh,moshtaryGharardadModels , canUpdateCustomer , new RequestCustomerListAdapter.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(int operation, int position) {
                Log.i("RequestCustomerListAdapter", "checkDuplicateRequestForCustomer: "+moshtaryGharardadModels.get(position).getCcSazmanForosh());

                onListItemClickListener(position,operation , moshtaryModels.get(position) , moshtaryAddressModels.get(position),moshtaryGharardadModels.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RequestCustomerListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RequestCustomerListActivity.this , 0));
        recyclerView.setAdapter(adapter);
        closeLoading();
    }


    @Override
    public void onGetSearch(ArrayList<MoshtaryModel> moshtaryModels, ArrayList<MoshtaryAddressModel> moshtaryAddressModels,ArrayList<MoshtaryGharardadModel>  moshtaryGharardadModels, ArrayList<Integer> arrayListNoeMorajeh)
    {
        moshtaryModelsSearch = moshtaryModels;
        moshtaryAddressModelsSearch = moshtaryAddressModels;
        moshtaryGharardadModelsSearch = moshtaryGharardadModels;
        //moshtaryNoeMorajehSearch = arrayListNoeMorajeh;
//        adapter.notifyDataSetChanged();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RequestCustomerListAdapter(RequestCustomerListActivity.this, moshtaryModels, moshtaryAddressModels, arrayListNoeMorajeh,moshtaryGharardadModels, canUpdateCustomer, new RequestCustomerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int operation, int position) {
                onListItemClickListener(position,operation , moshtaryModelsSearch.get(position) , moshtaryAddressModelsSearch.get(position),moshtaryGharardadModelsSearch.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RequestCustomerListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RequestCustomerListActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showBarkhordAvalieActivity(int ccMoshtary)
    {
        //customLoadingDialog.closeLoadingDialog(RequestCustomerListActivity.this);
        Intent intent = new Intent(RequestCustomerListActivity.this , BarkhordAvalieForoshandehActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        RequestCustomerListActivity.this.finish();
    }

    @Override
    public void showMojoodiGiriActivity(int ccMoshtary,int ccSazmanForosh)
    {
        //customLoadingDialog.closeLoadingDialog(RequestCustomerListActivity.this);
        Intent intent = new Intent(RequestCustomerListActivity.this , MojodiGiriActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccSazmanForosh" , ccSazmanForosh);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        RequestCustomerListActivity.this.finish();
    }

    @Override
    public void showDarkhastKalaActivity(int ccMoshtary,int ccSazmanForosh)
    {
        //customLoadingDialog.closeLoadingDialog(RequestCustomerListActivity.this);
        Intent intent = new Intent(RequestCustomerListActivity.this , DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccSazmanForoshGharardad",ccSazmanForosh);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        RequestCustomerListActivity.this.finish();
    }



    @Override
    public void showAlertDuplicateRequestForCustomer(MoshtaryModel moshtaryModel,MoshtaryGharardadModel moshtaryGharardadModel)
    {
        customAlertDialog.showLogMessageAlert(RequestCustomerListActivity.this, false, "", getResources().getString(R.string.warningDuplicateRequestForCustomer), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse()
        {
            @Override
            public void setOnCancelClick()
            {

            }

            @Override
            public void setOnApplyClick()
            {
                alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
                mPresenter.checkSelectedCustomer(moshtaryModel.getCcMoshtary(),moshtaryGharardadModel.getCcSazmanForosh(),moshtaryGharardadModel.getCcMoshtaryGharardad());
                Log.i(TAG, "setOnApplyClick: ");
            }
        });
    }


    public void showAlertOneRequestForCustomer()
    {
        customAlertDialog.showMessageAlert(RequestCustomerListActivity.this, false, "", getResources().getString(R.string.warningOneRequestForCustomer), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void closeLoading()
    {
        if (alertDialog != null)
        {
            try
            {
                alertDialog.dismiss();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }


    private void onListItemClickListener(int position,int operation , MoshtaryModel moshtaryModel , MoshtaryAddressModel moshtaryAddressModel, MoshtaryGharardadModel moshtaryGharardadModel)
    {
        if (operation == Constants.REQUEST_CUSTOMER_SHOW_LOCATION())
        {
            Intent intent = new Intent(RequestCustomerListActivity.this , AddCustomerMapActivity.class);
            intent.putExtra("customerlat" , moshtaryAddressModel.getLatitude_y());
            intent.putExtra("customerlng" , moshtaryAddressModel.getLongitude_x());
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
        else if (operation == Constants.REQUEST_CUSTOMER_CHANGE_LOCATION())
        {
            alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
            Log.i("onListItemClic ", "2");
            mPresenter.sendCustomerLocation(position,moshtaryAddressModel);
        }
        else if (operation == Constants.REQUEST_CUSTOMER_SHOW_CUSTOMER_INFO())
        {
            Intent intent = new Intent(RequestCustomerListActivity.this , CustomerInfoActivity.class);
            intent.putExtra(CustomerInfoActivity.CCMOSHTARY_KEY , moshtaryModel.getCcMoshtary());
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
        else if (operation == Constants.REQUEST_CUSTOMER_CHANGE_CUSTOMER_INFO())
        {
            Intent intent = new Intent(RequestCustomerListActivity.this , EditCustomerActivity.class);
            intent.putExtra("ccMoshtary" , moshtaryModel.getCcMoshtary());
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        }
        else if (operation == Constants.REQUEST_CUSTOMER_SELECT_CUSTOMER())
        {
            //customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
            alertDialog = customLoadingDialog.showLoadingDialog(RequestCustomerListActivity.this);
            mPresenter.checkDuplicateRequestForCustomer(moshtaryModel,moshtaryGharardadModel);
            //showBarkhordAvalieActivity(moshtaryModel.getCcMoshtary());
        }
        else if (operation == Constants.REQUEST_CUSTOMER_UPDATE_CREDIT())
        {
            mPresenter.checkUpdateEtebarMoshtary(moshtaryModel);
        }
        else if (operation == Constants.REQUEST_NON_REQUEST()){
            mPresenter.getElatAdamDarkhast(moshtaryModel.getCcMoshtary());
        }
    }


    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(RequestCustomerListActivity.this , getResources().getString(resId) , messageType , duration);
    }

    @Override
    public void showErrorAlert(int resId, int messageType, boolean closeActivity)
    {
        customAlertDialog.showMessageAlert(RequestCustomerListActivity.this, closeActivity, getResources().getString(R.string.error), getResources().getString(resId), messageType, getResources().getString(R.string.apply));
    }

    @Override
    public void onSuccessUpdateCustomerAddress(int position) {
        moshtaryAddressModels.get(position).setExtraProp_HasLocation(1);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onGetElatAdamDarkhast(int ccMoshtary,ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels, ArrayList<String> elatAdamDarkhastTitles) {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(RequestCustomerListActivity.this, elatAdamDarkhastTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                mPresenter.checkSeletedAdamDarkhastItem(ccMoshtary, elatAdamDarkhastModels.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void showTakeImageAlert(ElatAdamDarkhastModel elatAdamDarkhastModel) {
        this.elatAdamDarkhastModel = elatAdamDarkhastModel;
        customAlertDialog.showMessageAlert(RequestCustomerListActivity.this, "", getResources().getString(R.string.needTakeImage), Constants.INFO_MESSAGE(), getResources().getString(R.string.takeImage), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick()
            {
                checkCameraPermission();
            }

            @Override
            public void setOnApplyClick() {

            }
        });
    }

    private void checkCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(RequestCustomerListActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            {
                openCamera();
            }
            else
            {
                ActivityCompat.requestPermissions(RequestCustomerListActivity.this, new String[]{Manifest.permission.CAMERA} , CAMERA_PERMISSION);
            }
        }
        else
        {
            openCamera();
        }
    }


    public void openCamera()
    {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            {
                File photoFile = null;
                try
                {
                    photoFile = createImageFile();
                }
                catch (IOException ex)
                {ex.printStackTrace();}
                if (photoFile != null)
                {
                    Uri photoURI = FileProvider.getUriForFile(RequestCustomerListActivity.this, "com.saphamrah.imagefileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, TAKE_IMAGE); //add 10 to index for difference of requestcode between gallery and camera
                }
            }
        }
    }
    String mCurrentPhotoPath;
    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "ADAMDARKHAST_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void showDuplicatedCustomerCodeAlert(int ccMoshtary,ElatAdamDarkhastModel elatAdamDarkhastModel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RequestCustomerListActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_duplicated_customer_code , null);
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
        if (!(RequestCustomerListActivity.this).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(RequestCustomerListActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "MojodiGiriActivity", "showDuplicatedCustomerCodeAlert", "");
            }

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    txtinputCode.setError(null);
                    if (edttxt.getText().toString().length() > 0)
                    {
                        mPresenter.checkAdamDarkhastForInsert(ccMoshtary, elatAdamDarkhastModel, null, edttxt.getText().toString());
                    }
                    else
                    {
                        txtinputCode.setError(getResources().getString(R.string.errorCustomerDuplicatedCode));
                    }
                }
            });
        }
    }

    @Override
    public void onSuccessInsertAdamDarkhast() {
        customAlertDialog.showMessageAlert(RequestCustomerListActivity.this, getResources().getString(R.string.success), getResources().getString(R.string.successfullyDoneOps), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                Intent intent = new Intent(RequestCustomerListActivity.this , TemporaryRequestsListActivity.class);
                intent.putExtra("requests" , false);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                RequestCustomerListActivity.this.finish();
            }
        });
    }

    public void startMVPOps()
    {
        try
        {
            if ( stateMaintainer.firstTimeIn() )
            {
                initialize(this);
            }
            else
            {
                reinitialize(this);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RequestCustomerListActivity", "startMVPOps", "");
        }
    }


    private void initialize(RequestCustomerListMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RequestCustomerListPresenter(view);
            stateMaintainer.put(RequestCustomerListMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RequestCustomerListActivity", "initialize", "");
        }
    }


    private void reinitialize(RequestCustomerListMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RequestCustomerListMVP.PresenterOps.class.getSimpleName());
            if ( mPresenter == null )
            {
                initialize( view );
            }
            else
            {
                mPresenter.onConfigurationChanged(view);
            }
        }
        catch (Exception exception)
        {
            if (mPresenter != null)
            {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RequestCustomerListActivity", "reinitialize", "");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(false);

    }
}
