package com.saphamrah.MVP.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        List<BarcodeFormat> barcodeFormats = new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        barcodeFormats.add(BarcodeFormat.UPC_A);
        barcodeFormats.add(BarcodeFormat.UPC_E);
        barcodeFormats.add(BarcodeFormat.EAN_13);
        barcodeFormats.add(BarcodeFormat.EAN_8);
        barcodeFormats.add(BarcodeFormat.RSS_14);
        barcodeFormats.add(BarcodeFormat.CODE_39);
        barcodeFormats.add(BarcodeFormat.CODE_93);
        barcodeFormats.add(BarcodeFormat.CODE_128);
        barcodeFormats.add(BarcodeFormat.ITF);
        barcodeFormats.add(BarcodeFormat.CODABAR);
        barcodeFormats.add(BarcodeFormat.DATA_MATRIX);
        barcodeFormats.add(BarcodeFormat.PDF_417);

        scannerView = new ZXingScannerView(this);
        scannerView.setFormats(barcodeFormats);
        setContentView(scannerView);


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        scannerView.startCamera();
        scannerView.setResultHandler(this);
    }

    @Override
    public void handleResult(Result rawResult)
    {
        Log.d("scanner" , "data : " + rawResult.getText());

        /*CustomAlertDialog customAlertDialog = new CustomAlertDialog(BarcodeScannerActivity.this);
        customAlertDialog.showMessageAlert(BarcodeScannerActivity.this, false, "", rawResult.getText(), Constants.NONE_MESSAGE(), getString(R.string.apply));
        scannerView.resumeCameraPreview(this);*/
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data" , rawResult.getText());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data" , "");
        setResult(RESULT_CANCELED);
        finish();
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        scannerView.stopCamera();
    }
}
