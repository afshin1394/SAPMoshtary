package com.saphamrah.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.pax.dal.IDAL;
import com.pax.dal.IDeviceInfo;
import com.pax.dal.IMag;
import com.pax.dal.IPrinter;
import com.pax.dal.ISys;
import com.pax.dal.entity.ETermInfoKey;
import com.pax.neptunelite.api.NeptuneLiteUser;
import com.saphamrah.Application.BaseApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PaxGlPrinter extends Printer {
    private Activity activity;
    private static PaxGlPrinter ourInstance;
    private static IPrinter prn;
    private Context context;
    private static NeptuneLiteUser ppUser;
    private volatile static IDAL dal;
    private static ISys iSys;
    private static IMag mag;

   public PaxGlPrinter(Activity activity, long ccDarkhastFaktor) {
        super(ccDarkhastFaktor);
        this.activity = activity;
        try {
            ppUser = NeptuneLiteUser.getInstance();
            dal = ppUser.getDal(BaseApplication.getContext());
            prn = dal.getPrinter();
            iSys = dal.getSys();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("PaxGlPrinter", "PaxGlPrinter: "+e.getMessage());
        }

    }

    @Override
    public String setPrinter(Intent data) {
        return null;
    }

    @Override
    public String getPrinterStateMessage() {
        try {
            if (hasPrinter()) {
                return printerStatus();
            } else {
                return Constants.EnPrinterStatus.NotFound.toString();
            }
        } catch (Exception ex) {
            return Constants.EnPrinterStatus.NotFound.toString();
        }
    }

    private Bitmap preparePrint(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(filePath, options);
    }


    @Override
    public void print(String filePath) {
        Bitmap receipt = preparePrint(filePath);
        if (receipt != null && hasPrinter()) {
            directPrint(receipt);
        }
    }



    public synchronized String directPrint(Bitmap bitmap) {

        Integer[] paperError = new Integer[]{240, 2, 4, 8};

      /*  Bitmap[] bitmaps = splitBitmap(bitmap);


        for (int i = 0; i <bitmaps.length ; i++) {*/


        try {
            prn.init();

            if (prn.getStatus() != 0) {

                return "Printer not ready";
            }

            String model = null;
            try {

                model = getModel();

            } catch (Exception e) {

            }


            if (model != null) {


                if (model.equals("A920") || model.equals("a920")) {
                    prn.printBitmap(bitmap);

                } else {

                    prn.setGray(4);

                    //  prn.printBitmapWithMonoThreshold(bitmap,255);
                    prn.printBitmap(bitmap);

                }


            } else {

                prn.printBitmap(bitmap);
                return "ok";

            }


            //   prn.setGray(2);
            //prn.printBitmap(bitmaps[i]);
            //   prn.printBitmap(bitmap);
            // prn.printBitmapWithMonoThreshold(bitmap,255);


            prn.start();
            int printerStatus = prn.getStatus();
            while (printerStatus == 1) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printerStatus = prn.getStatus();
            }


            if (printerStatus == 0) {
                return "ok";
            } else {
                return "error : " + printerStatus;
            }
        } catch (Exception e) {
            try {
                e.printStackTrace();
                return e.getMessage();
            } catch (Exception ex) {
                return e.getMessage();
            }

        }

        // }
    }

    @Override
    public boolean checkIsAvailable() {
        Map<String, String> support = new HashMap<String, String>();
        String KEYBOARD = null;
        String PRINTER = null;
        try {
            Map<Integer, IDeviceInfo.ESupported> supportedMap = dal.getDeviceInfo().getModuleSupported();

            PRINTER = supportedMap.get(IDeviceInfo.MODULE_PRINTER).toString();
        } catch (Exception ex) {
        }
        String model = null;
        try {
            model = iSys.getTermInfo().get(ETermInfoKey.MODEL);


        if (model.equals("A920") || model.equals("a920") || model.equals("A930") || model.equals("a930") || model.equals("A80") || model.equals("a80") || model.equals("A910") || model.equals("a910")) {
            return true;
        } else {
            if (PRINTER != null && PRINTER.equals("YES")) {
                return true;
            } else {
                return false;
            }

        }
        } catch (Exception ex) {
            Log.i("PaxGlPrinter", "checkIsAvailable: " + ex.getMessage());
            return  false;
        }

    }


    public String getModel() {
        try {
            Map<ETermInfoKey, String> terminalInfo = dal.getSys().getTermInfo();
            return terminalInfo.get(ETermInfoKey.MODEL).trim();
        } catch (Exception exceptions) {
            exceptions.printStackTrace();
        }
        return null;
    }

    public String printerStatus() {
        try {
            prn.init();
            int status = prn.getStatus();

            switch (status) {
                case 0:
                    return Constants.EnPrinterStatus.Ready.toString();
                case 1:
                    return Constants.EnPrinterStatus.Busy.toString();
                case 2:
                    return Constants.EnPrinterStatus.PaperError.toString();
                default:
                    return Constants.EnPrinterStatus.Error.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.EnPrinterStatus.Error.toString();
    }


    public boolean hasPrinter() {
        Map<String, String> support = new HashMap<String, String>();
        String KEYBOARD = null;
        String PRINTER = null;
        try {
            Map<Integer, IDeviceInfo.ESupported> supportedMap = dal.getDeviceInfo().getModuleSupported();

            PRINTER = supportedMap.get(IDeviceInfo.MODULE_PRINTER).toString();
        } catch (Exception ex) {
        }
        String model = null;
        try {
            model = iSys.getTermInfo().get(ETermInfoKey.MODEL);
        } catch (Exception ex) {
        }

        if (model.equals("A920") || model.equals("a920") || model.equals("A930") || model.equals("a930") || model.equals("A80") || model.equals("a80") || model.equals("A910") || model.equals("a910")) {
            return true;
        } else {
            if (PRINTER != null && PRINTER.equals("YES")) {
                return true;
            } else {
                return false;
            }

        }


    }
}
