package com.saphamrah.MVP.View.marjoee;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Adapter.marjoee.MarjoeeKoliAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.marjoee.MarjoeekoliMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.marjoee.MarjoeeKoliPresenter;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

import static android.app.Activity.RESULT_OK;

public class MarjoeeKoliFragment extends Fragment implements MarjoeekoliMVP.RequiredViewOps , IOnclickSabtMarjoee {
    private final String TAG = this.getClass().getSimpleName();
    private View view;
    private StateMaintainer stateMaintainer;
    private MarjoeekoliMVP.PresenterOps mPresenter;
    private String ccDarkhastFaktor;
    private CustomAlertDialog customAlertDialog;
    private String ccMoshtary;
    private final int IMAGE_CAPTURE_RESULT = 1;
    private final int CAMERA_PERMISSION = 200;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    MarjoeeKoliAdapter marjoeeKoliAdapter;
    private String profileImageCurrentPath = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_marjoee_koli, container, false);
        Constants.SELECTED_FRAGMENT = 3;
        TextView lblActivityTitle = getActivity().findViewById(R.id.lblActivityTitle);
        lblActivityTitle.setText("مرجوعی کلی");

        return view;
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Calligrapher calligrapher = new Calligrapher(BaseApplication.getContext());
        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);
        /**
         * get ccDarkhastFaktor and ccMoshtary from bundle
         */
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ccDarkhastFaktor = getArguments().getString("marjoee");
            ccMoshtary = getArguments().getString("ccMoshtaryMarjoee");
        }
        stateMaintainer = new StateMaintainer(getFragmentManager(), TAG, BaseApplication.getContext());
        startMVPOps();
        customAlertDialog = new CustomAlertDialog(getActivity());
        mPresenter.getMarjoee(Long.parseLong(ccDarkhastFaktor));


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     *click on fab sabt marjoee
     */
    @Override
    public void clickSabtMarjoee() {
        mPresenter.getElatMarjoeeKol();
    }

    /**
     * show Toast for every result
     * @param resId
     * @param messageType
     * @param duration
     */
    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(getActivity() , getResources().getString(resId) , messageType , duration);
    }

    /**
     * get result sabt marjoee
     */
    @Override
    public void onGetMarjoee(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels) {
        marjoeeKoliAdapter = new MarjoeeKoliAdapter(BaseApplication.getContext(), kalaDarkhastFaktorSatrModels, false, new MarjoeeKoliAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KalaDarkhastFaktorSatrModel kalaDarkhastFaktorSatrModels, int position) {
                //TODO::
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(marjoeeKoliAdapter);

    }

    /**
     *get result elat marjoee
     */
    @Override
    public void onGetElatMarjoeeKol(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels , ArrayList<String> elatMarjoeeKalaTitles) {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(getActivity(), elatMarjoeeKalaTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                mPresenter.checkTaeidSabtMarjoee(elatMarjoeeKalaModels , selectedIndex , Integer.parseInt(ccDarkhastFaktor));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    /**
     * when sabt Marjoee need Image
     * @param isHaveImage
     * @param isValidData
     */
    @Override
    public void onCheckTaeidSabtMarjoee(boolean isHaveImage, boolean isValidData) {
        try {
        if (isHaveImage){
            customAlertDialog.showMessageAlert(getActivity(), "", getResources().getString(R.string.needTakeImage), Constants.INFO_MESSAGE(), getResources().getString(R.string.takeImage), new CustomAlertDialogResponse() {
                @Override
                public void setOnCancelClick()
                {
                    checkCameraPermission();
                }

                @Override
                public void setOnApplyClick() {

                }
            });
        } else {
            customAlertDialog.showToast(getActivity(),getResources().getString(R.string.successMarjoee),Constants.SUCCESS_MESSAGE(), Toast.LENGTH_LONG);
        }

        } catch (Exception e){
            Log.i("MarjoeeKoli" , e.getMessage());
        }
    }

    /**
     * setting before open camera
     */
    private void checkCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            {
                openCamera();
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA} , CAMERA_PERMISSION);
            }
        }
        else
        {
            openCamera();
        }
    }

    /**
     * open camera for take photo Marjoee
     */
    private void openCamera()
    {
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null)
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
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.saphamrah.imagefileprovider", photoFile);
                    List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList)
                    {
                        String packageName = resolveInfo.activityInfo.packageName;
                        getActivity().grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, IMAGE_CAPTURE_RESULT);
                }
            }
        }
        else
        {
            customAlertDialog.showMessageAlert(getActivity(), false, getResources().getString(R.string.error), getResources().getString(R.string.cameraNotFount), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }
    }

    /**
     * create Image File
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MARJOEEKOLI_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        profileImageCurrentPath = image.getAbsolutePath();
        return image;
    }

    /**
     * when take photo , get Image from camera
     */
    Bitmap bitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_RESULT)
        {
            if (resultCode == RESULT_OK)
            {
                try
                {
                    bitmap = BitmapFactory.decodeFile(profileImageCurrentPath);

                    mPresenter.checkMarjoeeForInsert(new PubFunc().new ImageUtils().convertBitmapToByteArray(getActivity(), bitmap, 50));
                }
                catch (Exception exception)
                {
                    customAlertDialog.showToast(getActivity(), getResources().getString(R.string.errorSelectImage), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                    exception.printStackTrace();
                    mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeKoliFragment", "onActivityResult", "");
                }
            }
        }
    }

    /**
     * set setting class
     */
    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeiKoliFragment", "startMVPOps", "");
        }
    }

    private void initialize(MarjoeekoliMVP.RequiredViewOps view) {
        try {
            mPresenter = new MarjoeeKoliPresenter(view);
            stateMaintainer.put(MarjoeekoliMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeiKoliFragment", "initialize", "");
        }
    }

    private void reinitialize(MarjoeekoliMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(MarjoeekoliMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeiKoliFragment", "reinitialize", "");
            }
        }
    }



}
