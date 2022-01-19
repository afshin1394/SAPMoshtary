package com.saphamrah.PubFunc;

import static com.saphamrah.Utils.Constants.BIXOLON;
import static com.saphamrah.Utils.Constants.UROVO;

import android.app.Activity;
import android.device.PrinterManager;

import com.bxl.config.editor.BXLConfigLoader;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.Utils.BixolonPrinter;
import com.saphamrah.Utils.UrovoPrinter;

public class PrinterUtils {


    public static int checkUrovo(Activity activity){
        UrovoPrinter urovoPrinter = new UrovoPrinter(activity,0);


        if (urovoPrinter.checkIsAvailable()){
            return UROVO;
        }else{
            return BIXOLON;
        }

    }
}
