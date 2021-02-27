package com.saphamrah.MVP.View;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.Adapter.MojodiGiriAdapter;
import com.saphamrah.Adapter.MojodiGiriKalaListAdapter;
import com.saphamrah.BaseMVP.MojodiGiriMVP;
import com.saphamrah.CustomView.BottomBar;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.MojodiGiriPresenter;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaMojodiGiriModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
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
import me.anwarshahriar.calligrapher.Calligrapher;

public class MojodiGiriActivity extends AppCompatActivity implements MojodiGiriMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , MojodiGiriActivity.this);
    private MojodiGiriMVP.PresenterOps mPresenter;

    private MaterialSearchView searchView;
    private MojodiGiriAdapter adapter;
    private CustomAlertDialog customAlertDialog;
    private int ccMoshtary;
    private int ccSazmanForosh;
	private boolean isMamorPakhsh;							  
    private ArrayList<KalaModel> kalaModels; //list of all kala that inserted in kala table
    private ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels; //list of kala that foroshandeh inserted in mojodigiri table at today for this moshtary
    private boolean searchStatus; // not in search mode = false , search with name = true
    private final int TAKE_IMAGE = 100;
    private final int CAMERA_PERMISSION = 200;
    private ElatAdamDarkhastModel elatAdamDarkhastModel;

    private FloatingActionButton fabAdamDarkhast;
    private View alertView;
    private AlertDialog show;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mojodi_giri);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        FloatingActionButton fabSearch = findViewById(R.id.fabSearch);
        FloatingActionButton fabShowCustomerInfo = findViewById(R.id.fabShowCustomerInfo);
        fabAdamDarkhast = findViewById(R.id.fabAdamDarkhast);
        searchView = findViewById(R.id.searchView);
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        removeToolbar();

        new BottomBar(MojodiGiriActivity.this, 1, new BottomBar.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mPresenter.checkBottomBarClick(position);
            }
        });

        Intent getIntent = getIntent();
        ccMoshtary = getIntent.getIntExtra("ccMoshtary" , -1);
        ccSazmanForosh = getIntent.getIntExtra("ccSazmanForosh" , -1);
        customAlertDialog = new CustomAlertDialog(MojodiGiriActivity.this);
        kalaModels = new ArrayList<>();
        kalaMojodiGiriModels = new ArrayList<>();
        searchStatus = false;
		isMamorPakhsh = false;					  

        startMVPOps();

		mPresenter.updateHaveMojodiGiri();								  
        mPresenter.getInsertedKalaMojodi(ccMoshtary);
        mPresenter.getKala(ccMoshtary);
		mPresenter.getNoeMasouliat();


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String searchWord = query.trim();
                mPresenter.searchInsertedKalaMojodi(searchWord , kalaMojodiGiriModels);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0)
                {
                    mPresenter.searchInsertedKalaMojodi(newText , kalaMojodiGiriModels);
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

            @Override
            public void onSearchViewClosed() {
                searchStatus = false;
                mPresenter.getInsertedKalaMojodi(ccMoshtary);
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = ((TextView)findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView)).getText().toString().trim();
                mPresenter.searchInsertedKalaMojodi(searchWord , kalaMojodiGiriModels);
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.closeSearch();
                searchStatus = false;
                mPresenter.getInsertedKalaMojodi(ccMoshtary);
            }
        });


        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch();
                searchView.setHint(getResources().getString(R.string.searchKalaName));
                searchStatus = true;
            }
        });


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                showAddItemAlert();
            }
        });

        fabAdamDarkhast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                mPresenter.getElatAdamDarkhast(ccMoshtary);
            }
        });


        fabShowCustomerInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MojodiGiriActivity.this , CustomerInfoActivity.class);
                intent.putExtra(CustomerInfoActivity.CCMOSHTARY_KEY , ccMoshtary);
                intent.putExtra(CustomerInfoActivity.CCSAZMANFOROSH_KEY , ccSazmanForosh);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (elatAdamDarkhastModel != null && elatAdamDarkhastModel.getNameElatAdamDarkhast() != null)
        {
            outState.putInt("ccElatAdamDarkhast" , elatAdamDarkhastModel.getCcElatAdamDarkhast());
            outState.putString("nameElatAdamDarkhast" , elatAdamDarkhastModel.getNameElatAdamDarkhast());
            outState.putInt("getImageElatAdamDarkhast" , elatAdamDarkhastModel.getGetImage());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
        {
            elatAdamDarkhastModel.setCcElatAdamDarkhast(savedInstanceState.getInt("ccElatAdamDarkhast"));
            elatAdamDarkhastModel.setNameElatAdamDarkhast(savedInstanceState.getString("nameElatAdamDarkhast"));
            elatAdamDarkhastModel.setGetImage(savedInstanceState.getInt("getImageElatAdamDarkhast"));
        }
    }


    @Override
    public Context getAppContext()
    {
        return MojodiGiriActivity.this;
    }

	@Override
    public void hideNoRequestButton()
    {
        isMamorPakhsh = true;
        fabAdamDarkhast.setVisibility(View.GONE);
    }
	
    @Override
    public void openDarkhastActivity()
    {
        Intent intent = new Intent(MojodiGiriActivity.this , DarkhastKalaActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        MojodiGiriActivity.this.finish();
    }

    @Override
    public void openBarkhordAvalieActivity()
    {
        Intent intent = new Intent(MojodiGiriActivity.this , BarkhordAvalieForoshandehActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        MojodiGiriActivity.this.finish();
    }

    @Override
    public void onGetKala(ArrayList<KalaModel> kalaModels)
    {
        this.kalaModels = kalaModels;
    }

    @Override
    public void onGetInsertedKalaMojodi(ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels)
    {
        this.kalaMojodiGiriModels = kalaMojodiGiriModels;
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new MojodiGiriAdapter(MojodiGiriActivity.this, kalaMojodiGiriModels, new MojodiGiriAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KalaMojodiGiriModel kalaMojodiGiriModel, int position) {
                mPresenter.checkRemoveKalaMojodiGiri(kalaMojodiGiriModel.getCcMojoodiGiri() , position);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MojodiGiriActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MojodiGiriActivity.this , 0));
        recyclerView.setAdapter(adapter);
        if (kalaMojodiGiriModels.size() == 0)
        {
			if (!isMamorPakhsh)
            {
                fabAdamDarkhast.setVisibility(View.VISIBLE);
            }
            showToast(R.string.notFoundKalaMojodiGiri , Constants.INFO_MESSAGE() , Constants.DURATION_SHORT());
        }
        /*else
        {
            fabAdamDarkhast.setVisibility(View.GONE);
            PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
            bottomBarConfig.getConfig(getAppContext());
            if (!bottomBarConfig.getMultipleMojoodiGiri())
            {
                customAlertDialog.showMessageAlert(MojodiGiriActivity.this, "", getResources().getString(R.string.errorMultipleMojodiGiri), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
                    @Override
                    public void setOnCancelClick() {

                    }

                    @Override
                    public void setOnApplyClick() {
                        openDarkhastActivity();
                    }
                });
            }
        }*/
    }

    @Override
    public void onSearchInsertedKalaMojodi(ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MojodiGiriAdapter adapter = new MojodiGiriAdapter(MojodiGiriActivity.this, kalaMojodiGiriModels, new MojodiGiriAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KalaMojodiGiriModel kalaMojodiGiriModel, int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MojodiGiriActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MojodiGiriActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onErrorInputCountForAddNewKala(int errorResId)
    {
        CustomTextInputLayout txtinputCount = alertView.findViewById(R.id.txtinputCount);
        txtinputCount.setError(getResources().getString(errorResId));
    }

    @Override
    public void onErrorForAddNewKala(int errorResId)
    {
        TextView lblError = alertView.findViewById(R.id.lblError);
        lblError.setVisibility(View.VISIBLE);
        lblError.setText(getResources().getString(errorResId));
    }

    @Override
    public void onSuccessAddNewKala()
    {
        if (searchStatus)
        {
            searchView.closeSearch();
        }
        //show.dismiss();
        showToast(R.string.successfullyDoneOps , Constants.SUCCESS_MESSAGE() , Constants.DURATION_SHORT());
    }

    @Override
    public void onSuccessRemoveKalaMojodiGiri(int position)
    {
        kalaMojodiGiriModels.remove(position);
        adapter.notifyDataSetChanged();
        Log.d("mojodi" , "size : " + kalaMojodiGiriModels.size());
        if (kalaMojodiGiriModels.size() == 0 && !isMamorPakhsh)
        {
            fabAdamDarkhast.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetElatAdamDarkhast(final ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels , final ArrayList<String> elatAdamDarkhastTitles)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(MojodiGiriActivity.this, elatAdamDarkhastTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                mPresenter.checkSeletedAdamDarkhastItem(ccMoshtary , elatAdamDarkhastModels.get(selectedIndex));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void showTakeImageAlert(ElatAdamDarkhastModel elatAdamDarkhastModel)
    {
        this.elatAdamDarkhastModel = elatAdamDarkhastModel;
        customAlertDialog.showMessageAlert(MojodiGiriActivity.this, "", getResources().getString(R.string.needTakeImage), Constants.INFO_MESSAGE(), getResources().getString(R.string.takeImage), new CustomAlertDialogResponse() {
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

    @Override
    public void showDuplicatedCustomerCodeAlert(final ElatAdamDarkhastModel elatAdamDarkhastModel)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MojodiGiriActivity.this);
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
        if (!(MojodiGiriActivity.this).isFinishing())
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
                logger.insertLogToDB(MojodiGiriActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "MojodiGiriActivity", "showDuplicatedCustomerCodeAlert", "");
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
    public void onSuccessInsertAdamDarkhast()
    {
        customAlertDialog.showMessageAlert(MojodiGiriActivity.this, getResources().getString(R.string.success), getResources().getString(R.string.successfullyDoneOps), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                Intent intent = new Intent(MojodiGiriActivity.this , TemporaryRequestsListActivity.class);
                intent.putExtra("requests" , false);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                MojodiGiriActivity.this.finish();
            }
        });
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(MojodiGiriActivity.this , getResources().getString(resId) , messageType , duration);
    }

    private void checkCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(MojodiGiriActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            {
                openCamera();
            }
            else
            {
                ActivityCompat.requestPermissions(MojodiGiriActivity.this, new String[]{Manifest.permission.CAMERA} , CAMERA_PERMISSION);
            }
        }
        else
        {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openCamera();
            }
        }
    }


    Bitmap bitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //if requestcode in (1,2,3) show that user select pic from gallery
        //else if requestcode in (11,12,13) show that user select pic from camera
        if (requestCode == TAKE_IMAGE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                try
                {
                    bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                    mPresenter.checkAdamDarkhastForInsert(ccMoshtary, elatAdamDarkhastModel, new PubFunc().new ImageUtils().convertBitmapToByteArray(MojodiGiriActivity.this, bitmap, 50), "");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
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
                    Uri photoURI = FileProvider.getUriForFile(MojodiGiriActivity.this, "com.saphamrah.imagefileprovider", photoFile);
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





    private void showAddItemAlert()
    {
        final ArrayList<KalaModel> selectedKalaModel = new ArrayList<>(); //this array contain only one item, declare as array for prevent declare global variable
        final ArrayList<KalaModel> arrayListKalaModel = new ArrayList<>(kalaModels); //local list for using in adapter
        final AlertDialog.Builder builder = new AlertDialog.Builder(MojodiGiriActivity.this);
        alertView = getLayoutInflater().inflate(R.layout.alert_add_kala_to_mojodi , null);
        RecyclerView recyclerView = alertView.findViewById(R.id.recyclerView);
        final MojodiGiriKalaListAdapter adapter = new MojodiGiriKalaListAdapter(MojodiGiriActivity.this, arrayListKalaModel, new MojodiGiriKalaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KalaModel kalaModel, int position) {
                selectedKalaModel.clear();
                selectedKalaModel.add(kalaModel);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MojodiGiriActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MojodiGiriActivity.this , 0));
        recyclerView.setAdapter(adapter);

        final CustomTextInputLayout txtinputSearch = alertView.findViewById(R.id.txtinputSearchKalaName);
        final CustomTextInputLayout txtinputCount = alertView.findViewById(R.id.txtinputCount);
        final EditText edttxtSearch = alertView.findViewById(R.id.txtSearchKalaName);
        final EditText edttxtCount = alertView.findViewById(R.id.txtCount);
        final TextView lblError = alertView.findViewById(R.id.lblError);
        Button btnCancel = alertView.findViewById(R.id.btnCancel);
        Button btnOK = alertView.findViewById(R.id.btnApply);
        final Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputSearch.setTypeface(font);
        txtinputCount.setTypeface(font);
        edttxtSearch.setTypeface(font);
        edttxtCount.setTypeface(font);
        lblError.setTypeface(font);
        btnCancel.setTypeface(font);
        btnOK.setTypeface(font);
        lblError.setVisibility(View.GONE);
        builder.setCancelable(true);
        builder.setView(alertView);
        builder.create();

        if (!(MojodiGiriActivity.this).isFinishing())
        {
            show = builder.show();
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
                logger.insertLogToDB(MojodiGiriActivity.this,Constants.LOG_EXCEPTION(), exception.toString(), "", "MojodiGiriActivity", "showAddItemAlert", "");
            }

            edttxtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0)
                    {
                        String searchWord = new PubFunc().new LanguageUtil().convertFaNumberToEN(s.toString());
                        arrayListKalaModel.clear();
                        adapter.clearSelecedItem();
                        for (int i=0 ; i<kalaModels.size()  ; i++)
                        {
                            if (kalaModels.get(i).getNameKala().contains(searchWord))
                            {
                                arrayListKalaModel.add(kalaModels.get(i));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    else //if (s.length() == 0)
                    {
                        arrayListKalaModel.clear();
                        adapter.clearSelecedItem();
                        arrayListKalaModel.addAll(kalaModels);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    show.dismiss();
                }
            });

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    lblError.setVisibility(View.GONE);
                    txtinputCount.setError(null);
                    if (selectedKalaModel.size() > 0 && selectedKalaModel.get(0) != null)
                    {
                        mPresenter.checkAddNewKala(kalaMojodiGiriModels, selectedKalaModel, ccMoshtary, edttxtCount.getText().toString().trim());
                        edttxtCount.setText("");
                        edttxtSearch.setText("");
                    }
                    else
                    {
                        lblError.setVisibility(View.VISIBLE);
                        lblError.setText(getResources().getString(R.string.errorSelectItem));
                    }
                }
            });

        }
    }

    private void visibleCloseSearchIcon()
    {
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
    }

    private void removeToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", MojodiGiriActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(MojodiGiriMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new MojodiGiriPresenter(view);
            stateMaintainer.put(MojodiGiriMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", MojodiGiriActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(MojodiGiriMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(MojodiGiriMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", MojodiGiriActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new PubFunc().new LocationProvider().stopLocationProvider();
    }

}
