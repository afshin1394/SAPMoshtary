package com.saphamrah.MVP.View;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.BaseMVP.PrintAndShareMVP;
import com.saphamrah.MVP.Presenter.PrintAndSharePresenter;
import com.saphamrah.Adapter.PrintAndShareAdapter;
import com.saphamrah.Model.PrintFaktorModel;
import com.saphamrah.Model.SystemConfigTabletModel;
import com.saphamrah.PubFunc.ImageUtils;
import com.saphamrah.R;
import com.saphamrah.Service.BluetoothPrintService;
import com.saphamrah.Utils.BixolonPrinter;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.Printer;
import com.saphamrah.Utils.UrovoPrinter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrintAndShareActivity extends AppCompatActivity implements PrintAndShareMVP.RequiredViewOps {
    private final File dir = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/SapHamrah/Print");

    private AlertDialog alertDialogLoading;
    private CustomLoadingDialog customLoadingDialog;
    private CustomAlertDialog customAlertDialog;
    private PrintAndSharePresenter mPresenter;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    private PrintAndShareAdapter adapter;
    private ArrayList<PrintFaktorModel> printFaktorModels = new ArrayList<>();
    private PrintFaktorModel printFaktorModel = new PrintFaktorModel();
    private SystemConfigTabletDAO systemconfig_tabletDAO;
    private Printer printer;
    //-------------------bluetooth----------------------------
    private static BluetoothPrintService mPrintService = null;
    private BluetoothAdapter mBluetoothAdapter = null;

    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.fabRefresh)
    public void fabRefresh() {
        fabMenu.close(true);
        alertDialogLoading = customLoadingDialog.showLoadingDialog(this);
        mPresenter.update();
    }

    @OnClick(R.id.fabSearch)
    public void fabSearch() {
        Intent intent = new Intent(PrintAndShareActivity.this, BixolonDeviceListActivity.class);
        startActivityForResult(intent, REQUEST_CONNECT_DEVICE_INSECURE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_share);
        ButterKnife.bind(this);
        mPresenter = new PrintAndSharePresenter(this);
        systemconfig_tabletDAO = new SystemConfigTabletDAO(BaseApplication.getContext());
        customLoadingDialog = new CustomLoadingDialog();
        customAlertDialog = new CustomAlertDialog(PrintAndShareActivity.this);

        File tempdir = dir;
        if (!tempdir.exists())
        {
            tempdir.mkdirs();
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
        {
            Toast.makeText(this, R.string.hasnt, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        setRecycler();
        mPresenter.getAllPrintFaktor();
    }


    /**
     * setup recycler
     */
    private void setRecycler() {
        adapter = new PrintAndShareAdapter(BaseApplication.getContext(), printFaktorModels, (action, position) -> {
            String fileName = "Print-" + printFaktorModels.get(position).getUniqID_Tablet() + ".jpg";

            mPresenter.getImagePrintFaktor(printFaktorModels.get(position).getUniqID_Tablet());

            boolean isFileExists = checkFileExists(printFaktorModel, fileName);
            if (action == Constants.PRINT()) {
                if (isFileExists){
                    try {
                        Resize(fileName);
                    } catch (Exception e){
                        e.getMessage();
                        showToast(R.string.errorHaveNotImageForPrint, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                    }
                    boolean check = setPrinter(position);
                    if (check) {
                        String PathFaktorImage = Environment.getExternalStorageDirectory() + "/SapHamrah/Print/Print-" + printFaktorModels.get(position).getUniqID_Tablet() + ".jpg";
                        printer.print(PathFaktorImage);
                    }
                } else {
                    showToast(R.string.errorHaveNotImageForPrint, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }

            } else if (action == Constants.SHARE) {
                if (isFileExists) {
                    openShare(fileName);
                } else {
                    showToast(R.string.errorHaveNotImageForPrint, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }

            } else if (action == Constants.IMAGE){
                byte[] image = Base64.decode(printFaktorModel.getFaktorImage(), Base64.NO_WRAP);

                customAlertDialog.showImage(PrintAndShareActivity.this,image , false, new CustomAlertDialogResponse() {
                    @Override
                    public void setOnCancelClick() {

                    }

                    @Override
                    public void setOnApplyClick() {

                    }
                });
            }

        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BaseApplication.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getContext(), 0));
        recyclerView.setAdapter(adapter);
    }

    private boolean checkFileExists(PrintFaktorModel model , String fileName){
        boolean fileExists = false;
        File file = new File(dir, fileName);
        if (file.exists()) {
            fileExists = true;
        }
        else if (model.getCcDarkhastFaktorNoeForosh() == Constants.ccNoeFaktor) {
            ImageUtils imageUtils = new ImageUtils();
            byte[] image = Base64.decode(model.getFaktorImage(), Base64.NO_WRAP);
            imageUtils.bitmapToFile(image, dir + "/" + fileName, dir.toString());

//            file = new File(dir, fileName);

            fileExists = file.exists();

        }


        return fileExists;
    }

    /**
     * open activity for share faktor
     *
     */
    private void openShare(String fileName) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


            File file = new File(dir, fileName);
            Uri outputFileUri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");
            intent.putExtra(Intent.EXTRA_STREAM, outputFileUri);
            startActivity(Intent.createChooser(intent, " اشتراک گذاری فاکتور... "));
        }



    /**
     * setup printer for print
     */
    private boolean setPrinter(int position) {

        ArrayList<SystemConfigTabletModel> systemConfigTabletModels = systemconfig_tabletDAO.getAll();
        if (systemConfigTabletModels.size() > 0) {
            if (systemConfigTabletModels.get(0).getNamePrinter() == 0) {
                printer = new BixolonPrinter(PrintAndShareActivity.this, 0);
            } else if (systemConfigTabletModels.get(0).getNamePrinter() == 1) {
                return false;
                //printer = new WoosimPrinter(PrintActivity.this , ccDarkhastFaktor);
            } else if (systemConfigTabletModels.get(0).getNamePrinter() == 2) {
                printer = new UrovoPrinter(PrintAndShareActivity.this, 0);
                String printerStatus = printer.getPrinterStateMessage();
                customAlertDialog.showToast(PrintAndShareActivity.this, printerStatus, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * resize pic before print
     */
    public void Resize(String fileName)
    {
        float Zarib = 0;
        int ResizeWidth = 0, ResizeHeight = 0;
        int quality = 100;

            //-----------------resize-------------------------
            Bitmap tmp = BitmapFactory.decodeFile(dir + "/" + fileName);
            int printerSize = systemconfig_tabletDAO.getAll().get(0).getSizePrint();
            if (printerSize == 384) {
                Zarib = (float) 384 / (tmp.getWidth());
                ResizeWidth = (int) (tmp.getWidth() * Zarib);
                ResizeHeight = (int) (tmp.getHeight() * Zarib);
            } else if (printerSize == 576) {
                Zarib = (float) 576 / (tmp.getWidth());
                ResizeWidth = (int) (tmp.getWidth() * Zarib);
                ResizeHeight = (int) (tmp.getHeight() * Zarib);
            } else if (printerSize == 832) {
                Zarib = (float) 832 / (tmp.getWidth());
                ResizeWidth = (int) (tmp.getWidth() * Zarib);
                ResizeHeight = (int) (tmp.getHeight() * Zarib);
            }

            Bitmap bitmap = Bitmap.createBitmap(ResizeWidth, ResizeHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);


            Bitmap scaled = Bitmap.createScaledBitmap(tmp, ResizeWidth, ResizeHeight, true);
            int leftOffset = 0;//(bitmap.getWidth() - scaled.getWidth()) / 2;
            int topOffset = 0;
            canvas.drawBitmap(scaled, leftOffset, topOffset, null);

            File imageFile = new File(dir + "/" + fileName);


            FileOutputStream outStream;

            try {
                outStream = new FileOutputStream(imageFile);

                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    /**
     * ***********    response from interface MVP    **************
     */
    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(PrintAndShareActivity.this, getResources().getString(resId), messageType, duration);

    }

    @Override
    public void onGetAllPrintFaktor(ArrayList<PrintFaktorModel> modelArrayList) {
        printFaktorModels.clear();
        printFaktorModels.addAll(modelArrayList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetImagePrintFaktor(PrintFaktorModel model) {
        printFaktorModel=model;
    }

    @Override
    public void closeLoadingDialog() {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * activityResult for result print
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_INSECURE:
                if (resultCode == Activity.RESULT_OK) {
                    String printerName = printer.setPrinter(data);
                    if (printerName != null && printerName.trim().length() > 0) {
                        CustomAlertDialog customAlertDialog = new CustomAlertDialog(PrintAndShareActivity.this);
                        customAlertDialog.showToast(PrintAndShareActivity.this, getResources().getString(R.string.connectTo, printerName), Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
                    }
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode != Activity.RESULT_OK) {
                    Log.d("onActivityResult", "BT not enabled");
                    Toast.makeText(this, "bt_not_enabled_leaving", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    /**
     * ***********    Lifecycle    **************
     */
    public void onStart()
    {
        super.onStart();
        Log.e("onStart", "++ ON START ++");

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, 100);
            }
            else
            {
                if (!mBluetoothAdapter.isEnabled())
                {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

                }
            }
        }
        else
        {
            if (!mBluetoothAdapter.isEnabled())
            {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }
        }


    }

    public synchronized void onResume()
    {
        super.onResume();
        Log.e("onResume", "+ ON RESUME +");
        if (mPrintService != null)
        {
            if (mPrintService.getState() == BluetoothPrintService.STATE_NONE)
            {
                mPrintService.start();
            }
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        if (mPrintService != null)
            mPrintService.stop();
        Log.e("onDestroy", "--- ON DESTROY ---");
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
