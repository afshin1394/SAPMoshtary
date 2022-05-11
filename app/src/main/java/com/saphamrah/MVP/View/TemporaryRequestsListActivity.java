package com.saphamrah.MVP.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.TemporaryNoRequestsAdapter;
import com.saphamrah.Adapter.TemporaryRequestsAdapter;
import com.saphamrah.BaseMVP.TemporaryRequestsListMVP;
import com.saphamrah.MVP.Presenter.TemporaryRequestsListPresenter;
import com.saphamrah.MVP.printNoe2.PrintNoe2Activity;
import com.saphamrah.PubFunc.ImageUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerAdamDarkhastModel;
import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.io.File;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TemporaryRequestsListActivity extends AppCompatActivity implements TemporaryRequestsListMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , TemporaryRequestsListActivity.this);
    private TemporaryRequestsListMVP.PresenterOps mPresenter;

    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialog;
    private CustomAlertDialog customAlertDialog;
    private boolean requestsList;
    private TextView lblTitle;
    private FloatingActionButton fabChangeList;
    private RecyclerView recyclerViewRequests;
    private RecyclerView recyclerViewNoRequests;
    private TemporaryRequestsAdapter requestAdapter;
    private TemporaryNoRequestsAdapter noRequestAdapter;
    private ArrayList<CustomerDarkhastFaktorModel> customerDarkhastFaktorModels;
    private ArrayList<CustomerAdamDarkhastModel> customerAdamDarkhastModels;
    private final int OPEN_FAKTOR_DETAIL = 100;
    private final int TAKE_IMAGE = 101;
    private Uri imageUri;
    private int receiptImagePosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_requests_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        lblTitle = findViewById(R.id.lblActivityTitle);
        recyclerViewRequests = findViewById(R.id.recyclerViewRequests);
        recyclerViewNoRequests = findViewById(R.id.recyclerViewNoRequests);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        fabChangeList = findViewById(R.id.fabChangeList);
        ImageView imgBack = findViewById(R.id.imgBack);

        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(TemporaryRequestsListActivity.this);
        customerDarkhastFaktorModels = new ArrayList<>();
        customerAdamDarkhastModels = new ArrayList<>();
        startMVPOps();

        Intent getIntent = getIntent();
        requestsList = getIntent.getBooleanExtra("requests" , true);
        if (requestsList)
        {
            changeToRequestList();
        }
        else
        {
            changeToNoRequestList();
        }

		mPresenter.getMyIP();					 
		
        fabChangeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                if (requestsList)
                {
                    changeToNoRequestList();
                }
                else
                {
                    changeToRequestList();
                }
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemporaryRequestsListActivity.this.finish();
            }
        });

    }


    private void changeToRequestList()
    {
        lblTitle.setText(getResources().getString(R.string.requestsList));
        fabChangeList.setLabelText(getResources().getString(R.string.adamDarkhastList));
        requestsList = true;
        recyclerViewRequests.setVisibility(View.VISIBLE);
        recyclerViewNoRequests.setVisibility(View.GONE);
        mPresenter.getTemporaryRequests();
    }


    private void changeToNoRequestList()
    {
        lblTitle.setText(getResources().getString(R.string.adamDarkhastList));
        fabChangeList.setLabelText(getResources().getString(R.string.requestsList));
        requestsList = false;
        recyclerViewRequests.setVisibility(View.GONE);
        recyclerViewNoRequests.setVisibility(View.VISIBLE);
        mPresenter.getTemporaryNoRequests();
    }


    @Override
    public Context getAppContext()
    {
        return TemporaryRequestsListActivity.this;
    }

    @Override
    public void onGetTemporaryRequests(ArrayList<CustomerDarkhastFaktorModel> models, int noeForoshandehMamorPakhsh,boolean showReceiptImage)
    {
        this.customerDarkhastFaktorModels.clear();
        this.customerDarkhastFaktorModels.addAll(models);
        requestAdapter = new TemporaryRequestsAdapter(TemporaryRequestsListActivity.this, customerDarkhastFaktorModels, noeForoshandehMamorPakhsh,showReceiptImage, new TemporaryRequestsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int action, int position) {
                mPresenter.checkSelectedActionOnTempRequest(action , position , customerDarkhastFaktorModels.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TemporaryRequestsListActivity.this);
        recyclerViewRequests.setLayoutManager(mLayoutManager);
        recyclerViewRequests.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRequests.addItemDecoration(new DividerItemDecoration(TemporaryRequestsListActivity.this , 0));
        recyclerViewRequests.setAdapter(requestAdapter);
    }

    @Override
    public void onGetTemporaryNoRequests(ArrayList<CustomerAdamDarkhastModel> models)
    {
        this.customerAdamDarkhastModels.clear();
        this.customerAdamDarkhastModels.addAll(models);
        noRequestAdapter = new TemporaryNoRequestsAdapter(TemporaryRequestsListActivity.this, customerAdamDarkhastModels, new TemporaryNoRequestsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int action, int position) {
                mPresenter.checkSelectedActionOnNoTempRequest(action , position , customerAdamDarkhastModels.get(position));
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TemporaryRequestsListActivity.this);
        recyclerViewNoRequests.setLayoutManager(mLayoutManager);
        recyclerViewNoRequests.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNoRequests.addItemDecoration(new DividerItemDecoration(TemporaryRequestsListActivity.this , 0));
        recyclerViewNoRequests.setAdapter(noRequestAdapter);
    }

    @Override
    public void showDeleteAlert(final int position , final CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        customAlertDialog.showLogMessageAlert(TemporaryRequestsListActivity.this, false, getResources().getString(R.string.warning), getResources().getString(R.string.deleteWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                mPresenter.checkDeleteTempRequest(position , customerDarkhastFaktorModel);
            }
        });
    }

    @Override
    public void showSendAlert(final int position, final CustomerDarkhastFaktorModel customerDarkhastFaktorModel)
    {
        customAlertDialog.showLogMessageAlert(TemporaryRequestsListActivity.this, false, "", getResources().getString(R.string.sendWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                alertDialog = customLoadingDialog.showLoadingDialog(TemporaryRequestsListActivity.this);
                mPresenter.checkSendTempRequest(position , customerDarkhastFaktorModel);
            }
        });
    }

    @Override
    public void onSuccessDeleteTempRequest(int position)
    {
        showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        customerDarkhastFaktorModels.remove(position);
        requestAdapter.notifyItemRemoved(position);
        requestAdapter.notifyItemRangeRemoved(position , customerDarkhastFaktorModels.size());
    }

    @Override
    public void openPrintActivity(long ccDarkhastFaktor, int ccMoshtary)
    {
        Intent intent = new Intent(TemporaryRequestsListActivity.this , PrintActivity.class);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void openSaveImageActivity(long ccDarkhastFaktor,int type, int ccMoshtary)
    {
        Intent intent = null;
        if (type ==Constants.ccNoeHavale){
            intent = new Intent(TemporaryRequestsListActivity.this , FaktorDetailsActivity.class);
        }
        else if(type == Constants.ccNoeFaktor) {
           intent = new Intent(TemporaryRequestsListActivity.this , PrintNoe2Activity.class);
        }
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("sourceActivity" , "TemporaryRequestsListActivity");
        startActivityForResult(intent , OPEN_FAKTOR_DETAIL);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void onSuccessDeleteNoRequest(int position)
    {
        showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        customerAdamDarkhastModels.remove(position);
        noRequestAdapter.notifyItemRemoved(position);
        noRequestAdapter.notifyItemRangeRemoved(position , customerAdamDarkhastModels.size());
    }

    @Override
    public void showDeleteNoRequestAlert(final int position, final CustomerAdamDarkhastModel customerAdamDarkhastModel)
    {
        customAlertDialog.showLogMessageAlert(TemporaryRequestsListActivity.this, false, getResources().getString(R.string.warning), getResources().getString(R.string.deleteWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {

            }

            @Override
            public void setOnApplyClick() {
                mPresenter.checkDeleteTempNoRequest(position , customerAdamDarkhastModel);
            }
        });
    }

    @Override
    public void onSuccessSendNoRequest(int position)
    {
        showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        CustomerAdamDarkhastModel customerAdamDarkhastModel = this.customerAdamDarkhastModels.get(position);
        customerAdamDarkhastModel.setSentToServer(true);
        this.customerAdamDarkhastModels.set(position , customerAdamDarkhastModel);
        noRequestAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessSendRequest(int position , long ccDarkhastFaktorNew)
    {
        //customLoadingDialog.closeLoadingDialog(TemporaryRequestsListActivity.this);
        closeAlertDialog();
        customAlertDialog.showMessageAlert(TemporaryRequestsListActivity.this, false, "", getResources().getString(R.string.successSendData), Constants.SUCCESS_MESSAGE(), getResources().getString(R.string.apply));
        customerDarkhastFaktorModels.get(position).setExtraProp_IsOld(1);
        customerDarkhastFaktorModels.get(position).setCcDarkhastFaktor(ccDarkhastFaktorNew);
        requestAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorSendRequest(int errorId,String message)
    {
        closeAlertDialog();
        customAlertDialog.showMessageAlert(TemporaryRequestsListActivity.this, false, getResources().getString(R.string.error), getResources().getString(errorId) + message, Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(TemporaryRequestsListActivity.this, getResources().getString(resId), messageType, duration);
    }
    @Override
    public void openCamera(int position, CustomerDarkhastFaktorModel customerDarkhastFaktorModel) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        receiptImagePosition = position;

        if (intent.resolveActivity(TemporaryRequestsListActivity.this.getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_IMAGE);
        }
    }

    @Override
    public void onSuccessSaveReceiptImage(int resId,int position) {
        showToast(resId, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
//        customerDarkhastFaktorModels.get(position).setExtraProp_IsOld(1);
        customerDarkhastFaktorModels.get(position).setHasReceiptImage(true);
        requestAdapter.notifyItemChanged(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult" , "requestCode : " + requestCode + " , OPEN_FAKTOR_DETAIL : " + OPEN_FAKTOR_DETAIL);
        if (requestCode == OPEN_FAKTOR_DETAIL)
        {
            Log.d("onActivityResult" ,"in if");
            try
            {
                if (data != null)
                {
                    Log.d("onActivityResult" ,"data != null");
                    boolean savedImage = data.getBooleanExtra("imagedSaved" , false);
                    Log.d("onActivityResult" ,"savedImage : " + savedImage);
                    if (savedImage)
                    {
                        long ccDarkhastFaktor = data.getLongExtra("ccDarkhastFaktor" , -1);
                        Log.d("onActivityResult" ,"ccDarkhastFaktor : " + ccDarkhastFaktor);
                        if (ccDarkhastFaktor != -1)
                        {
                            for (CustomerDarkhastFaktorModel model : customerDarkhastFaktorModels)
                            {
                                Log.d("onActivityResult" ,"model : " + model.toString());
                                if (model.getCcDarkhastFaktor() == ccDarkhastFaktor)
                                {
                                    Log.d("onActivityResult" ,"model equal ");
                                    model.setHaveFaktorImage(true);
                                    requestAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "TemporaryRequestsListActivity", "onActivityResult", "");
            }
        }
        else if (requestCode == TAKE_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), imageUri);
                    imageUri = null;
                    if (image != null && receiptImagePosition != -1) {
                        byte[] imageBytes  = new ImageUtils().getImageBaseOnCamera(TemporaryRequestsListActivity.this,image);
                        String fileName = "Receipt-" + "test" + ".jpg";
                        new ImageUtils().bitmapToFile(imageBytes,fileName,android.os.Environment.getExternalStoragePublicDirectory("/SapHamrah/").getAbsolutePath() +"/Print" );
                        mPresenter.insertReceiptImage(imageBytes,receiptImagePosition,customerDarkhastFaktorModels.get(receiptImagePosition));
                    } else {
                        showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", TemporaryRequestsListActivity.this.getClass().getSimpleName(), "onActivityResult", "");
                }
            }
        }
    }


    private void closeAlertDialog()
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
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(TemporaryRequestsListActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), "TemporaryRequestsListActivity", "", "closeLoadingDialog", "");
            }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "TemporaryRequestsListActivity", "startMVPOps", "");
        }
    }


    private void initialize(TemporaryRequestsListMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new TemporaryRequestsListPresenter(view);
            stateMaintainer.put(TemporaryRequestsListMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "TemporaryRequestsListActivity", "initialize", "");
        }
    }


    private void reinitialize(TemporaryRequestsListMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(TemporaryRequestsListMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "TemporaryRequestsListActivity", "reinitialize", "");
            }
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unBindDisposable();
        super.onDestroy();
    }
}
