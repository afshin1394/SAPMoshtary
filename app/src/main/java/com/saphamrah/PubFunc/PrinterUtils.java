package com.saphamrah.PubFunc;

import static com.saphamrah.Utils.Constants.BIXOLON;
import static com.saphamrah.Utils.Constants.UROVO;

import android.app.Activity;
import android.content.Context;
import android.device.PrinterManager;

import com.bxl.config.editor.BXLConfigLoader;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.MVP.View.PrintAndShareActivity;
import com.saphamrah.Model.SystemConfigTabletModel;
import com.saphamrah.Utils.BixolonPrinter;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.Printer;
import com.saphamrah.Utils.UrovoPrinter;

import java.util.ArrayList;

public class PrinterUtils {


    public static int checkUrovo(Activity activity){
        UrovoPrinter urovoPrinter = new UrovoPrinter(activity,0);


        if (urovoPrinter.checkIsAvailable()){
            return UROVO;
        }else{
            return BIXOLON;
        }

    }

    /**
     * setup printer for print
     */
    public static  Printer setPrinter(Activity activity) {

        Printer printer = null;
        ArrayList<SystemConfigTabletModel> systemConfigTabletModels =new SystemConfigTabletDAO(activity).getAll();
        if (systemConfigTabletModels.size() > 0) {
            if (systemConfigTabletModels.get(0).getNamePrinter() == 0) {
                printer = new BixolonPrinter(activity, 0);
            } else if (systemConfigTabletModels.get(0).getNamePrinter() == 1) {
                return printer;
                //printer = new WoosimPrinter(PrintActivity.this , ccDarkhastFaktor);
            } else if (systemConfigTabletModels.get(0).getNamePrinter() == 2) {
                printer = new UrovoPrinter(activity, 0);
            }
        }

        return printer;
    }
}
