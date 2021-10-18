package com.saphamrah.MVP.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saphamrah.BaseMVP.MoshtaryChidmanMVP;
import com.saphamrah.MVP.Presenter.MoshtaryChidmanPresenter;
import com.saphamrah.MVP.View.MoshtaryChidmanFragments.MoshtaryChidmanAddFragment;
import com.saphamrah.MVP.View.MoshtaryChidmanFragments.MoshtaryChidmanListFragment;
import com.saphamrah.Model.MoshtaryChidmanModel;
import com.saphamrah.PubFunc.ImageUtils;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MoshtaryChidmanActivity extends AppCompatActivity implements MoshtaryChidmanMVP.RequiredViewOps {


    private final int TAKE_IMAGE = 101;


    private ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels;
    private CustomAlertDialog customAlertDialog;
    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;
    private MoshtaryChidmanMVP.PresenterOps mPresenter;
    private Uri imageUri;
    private TextView lblActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moshtary_chidman);
        moshtaryChidmanModels = new ArrayList<>();
        mPresenter = new MoshtaryChidmanPresenter(this);

        findViews();

        mPresenter.getMoshtaryChidman();
    }

    public void openListFragment(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels) {
        clearBackStack();
        MoshtaryChidmanListFragment moshtaryChidmanListFragment = new MoshtaryChidmanListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("moshtaryChidmanModels", moshtaryChidmanModels);
        moshtaryChidmanListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, moshtaryChidmanListFragment, "MoshtaryChidmanListFragment")
                .addToBackStack(null)
                .commit();
    }

    public void openAddFragment(MoshtaryChidmanModel moshtaryChidmanModel, Action action) {

        clearBackStack();
        MoshtaryChidmanAddFragment moshtaryChidmanAddFragment = new MoshtaryChidmanAddFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("moshtaryChidmanModel", moshtaryChidmanModel);
        bundle.putSerializable("Action", action);
        moshtaryChidmanAddFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, moshtaryChidmanAddFragment, "MoshtaryChidmanAddFragment")
                .addToBackStack(null)
                .commit();
    }

    private void findViews() {
        lblActivityTitle = findViewById(R.id.lblActivityTitle);
        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(MoshtaryChidmanActivity.this);
        Typeface font = Typeface.createFromAsset(MoshtaryChidmanActivity.this.getAssets(), MoshtaryChidmanActivity.this.getResources().getString(R.string.fontPath));
        lblActivityTitle.setTypeface(font);
    }

    @Override
    public void showLoadingDialog() {
        if (customLoadingDialog!=null)
            alertDialogLoading = customLoadingDialog.showLoadingDialog(MoshtaryChidmanActivity.this);
        else {
            customAlertDialog = new CustomAlertDialog(MoshtaryChidmanActivity.this);
            alertDialogLoading = customLoadingDialog.showLoadingDialog(MoshtaryChidmanActivity.this);
        }
    }

    @Override
    public Context getAppContext() {
        return MoshtaryChidmanActivity.this;
    }

    @Override
    public void onGetMoshtaryChidman(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels) {
        this.moshtaryChidmanModels.clear();
        this.moshtaryChidmanModels.addAll(moshtaryChidmanModels);
        openListFragment(this.moshtaryChidmanModels);
    }

    @Override
    public void onInsertMoshtaryChidman(MoshtaryChidmanModel moshtaryChidmanModel) {
        mPresenter.getMoshtaryChidman();
    }


    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(MoshtaryChidmanActivity.this, getResources().getString(resId), messageType, duration);

    }

    @Override
    public void closeLoading() {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "ForoshandehRouteMapActivity", "closeLoadingDialog", "");
            }
        }
    }

    @Override

    public void onDeleteMoshtaryChidman() {
        mPresenter.getMoshtaryChidman();
    }

    @Override
    public void onUpdateMoshtaryChidman() {
        mPresenter.getMoshtaryChidman();
    }

    @Override
    public void onSendMoshtaryChidman() {
        mPresenter.getMoshtaryChidman();
    }

    @Override
    public void closeActivity() {
        clearBackStack();
        MoshtaryChidmanActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), imageUri);
                    imageUri = null;
                    if (image != null) {
                        byte[] imageBytes  = new ImageUtils().convertBitmapToByteArray(MoshtaryChidmanActivity.this,image,100, Bitmap.CompressFormat.JPEG);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes1, 0, imageBytes1.length);
//                        byte[] imageBytes2  = new ImageUtils().convertBitmapToByteArray(MoshtaryChidmanActivity.this,bitmap,50, Bitmap.CompressFormat.JPEG);

                        Log.i("imageBytess", "onActivityResult: "+ Arrays.toString(imageBytes));
                        MoshtaryChidmanModel moshtaryChidmanModel = new MoshtaryChidmanModel();
                        moshtaryChidmanModel.setImage(imageBytes);
                        openAddFragment(moshtaryChidmanModel, Action.INSERT);
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
    public void showAlertDialog(int resId, int messageType)
    {
        customAlertDialog.showMessageAlert(MoshtaryChidmanActivity.this, false, "", getString(resId), messageType, getString(R.string.apply));
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (getCurrentFragment() instanceof MoshtaryChidmanAddFragment) {
                mPresenter.getMoshtaryChidman();
            } else {
                mPresenter.checkNotSendMoshtaryChidman(moshtaryChidmanModels);

            }
        } else {
            MoshtaryChidmanActivity.this.finish();
        }
    }

    public MoshtaryChidmanMVP.PresenterOps getPresenter() {
        if (mPresenter != null)
            return mPresenter;
        else
            return new MoshtaryChidmanPresenter(this);
    }

    public void openCamera(int requestCode) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        if (intent.resolveActivity(MoshtaryChidmanActivity.this.getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }


    private void clearBackStack() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
            getSupportFragmentManager().popBackStack();
        }
    }

    private Fragment getCurrentFragment() {
        return (Fragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
    }

    public ArrayList<MoshtaryChidmanModel> getMoshtaryChidmanModels() {
        return moshtaryChidmanModels;
    }

    public void deleteMoshtaryChidman(MoshtaryChidmanModel moshtaryChidmanModel) {
        mPresenter.deleteMoshtaryChidman(moshtaryChidmanModel.getCcMoshtaryChidman());
    }

    public void sendMoshtaryChidman(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels) {

        mPresenter.sendMoshtaryChidmans(moshtaryChidmanModels);
    }



    public enum Action {
        UPDATE, INSERT, DELETE
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }








}
