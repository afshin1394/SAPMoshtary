package com.saphamrah.MVP.Presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.saphamrah.BaseMVP.TreasuryListOfflineMVP;
import com.saphamrah.MVP.Model.TreasuryListOfflineModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.PubFunc.FileUtils;
import com.saphamrah.PubFunc.ImageUtils;
import com.saphamrah.PubFunc.PrinterUtils;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorMoshtaryForoshandeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.Printer;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TreasuryListOfflinePresenter implements TreasuryListOfflineMVP.PresenterOps, TreasuryListOfflineMVP.RequiredPresenterOps {

    private WeakReference<TreasuryListOfflineMVP.RequiredViewOps> mView;
    private TreasuryListOfflineMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public TreasuryListOfflinePresenter(TreasuryListOfflineMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new TreasuryListOfflineModel(this);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(TreasuryListOfflineMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }




    @Override
    public void checkDateAndFakeLocation()
    {
        mModel.checkDateAndFakeLocation();
    }

    @Override
    public void getTreasuryList(int faktorRooz , int sortType)
    {
        mModel.getTreasuryList(faktorRooz, sortType);
    }



    @Override
    public void getCustomerLocation(DarkhastFaktorMoshtaryForoshandeModel darkhastFaktorMoshtaryForoshandeModel)
    {
        mModel.getCustomerLocation(darkhastFaktorMoshtaryForoshandeModel);
    }

    @Override
    public void getFaktorImage(long ccDarkhastFaktor,int action)
    {
        mModel.getFaktorImage(ccDarkhastFaktor,action);
    }


    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        if (!message.trim().equals("") || !logClass.trim().equals("") || !logActivity.trim().equals("") || !functionParent.trim().equals("") || !functionChild.trim().equals(""))
        {
            mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }





    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Activity getActivity() {
        return mView.get().getActivity();
    }

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }




    @Override
    public void onErrorUseFakeLocation()
    {
        mView.get().onError(true , R.string.errorFakeLocation);
    }

    @Override
    public void onCheckServerTime(boolean valid, String message)
    {
        if (valid)
        {
            mModel.getTreasuryList(0 , Constants.SORT_TREASURY_BY_CUSTOMER_CODE);
        }
        else
        {
            mView.get().onError(true, message);
        }
    }

    @Override
    public void onGetCustomerAddress(double latitude , double longitude)
    {
        if (latitude > 0 && longitude > 0)
        {
            mView.get().onGetCustomerAddress(latitude , longitude);
        }
        else
        {
            mView.get().showToast(R.string.errorFindCustomerAddress, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetFaktorImage(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel,int action)
    {

        if (darkhastFaktorEmzaMoshtaryModel != null && darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage() != null && darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage().length > 0) {
            if (action == Constants.PRINT()) {
                try {
                    print(darkhastFaktorEmzaMoshtaryModel);
                } catch (Exception e) {
                    e.getMessage();
                    mView.get().showToast(R.string.errorHaveNotImageForPrint, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
            } else if (action == Constants.SHOW_IMAGE()) {
                mView.get().onGetFaktorImage(darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage());
            }
        }
        else
        {
            mView.get().showToast(R.string.notFoundAnyFaktorImage, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    public void print(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel) {
        File dir = new File(android.os.Environment.getExternalStoragePublicDirectory("/SapHamrah/").getAbsolutePath() +"/Print" );

        String fileName = "Print-" + darkhastFaktorEmzaMoshtaryModel.getExtraProp_UniqueID() + ".jpg";
        File file = ImageUtils.saveImageInFile(darkhastFaktorEmzaMoshtaryModel.getDarkhastFaktorImage(),String.valueOf( darkhastFaktorEmzaMoshtaryModel.getExtraProp_UniqueID()),dir + "/" + fileName);
        if (file.exists()){
            try {
                Bitmap tmp = BitmapFactory.decodeFile(dir + "/" + fileName);
                FileUtils.Resize(getAppContext(),dir,tmp,String.valueOf( darkhastFaktorEmzaMoshtaryModel.getExtraProp_UniqueID()));
            } catch (Exception e) {
                e.getMessage();
                mView.get().showToast(R.string.errorHaveNotImageForPrint, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
            Printer printer = PrinterUtils.setPrinter(getActivity());

            if (printer!=null) {
                mView.get().showToast(printer.getPrinterStateMessage(),Constants.INFO_MESSAGE(),Constants.DURATION_LONG());
                String PathFaktorImage = Environment.getExternalStoragePublicDirectory("/SapHamrah/") + "/Print/Print-" + darkhastFaktorEmzaMoshtaryModel.getExtraProp_UniqueID() + ".jpg";
                if (printer.checkIsAvailable()){
                    printer.print(PathFaktorImage);
                }else{
                    mView.get().showToast(R.string.PrinterNotAvailable, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }

            }else{
                mView.get().showToast(R.string.PrinterNotAvailable, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
    }


    @Override
    public void onGetFaktorRooz(ArrayList<DarkhastFaktorMoshtaryForoshandeModel> darkhastFaktorMoshtaryForoshandeModels , int faktorRooz , int noeMasouliat)
    {
        if (faktorRooz == 0)
        {
            if (darkhastFaktorMoshtaryForoshandeModels.size() > 0)
            {
                mView.get().onGetFaktorRooz(darkhastFaktorMoshtaryForoshandeModels , noeMasouliat);
            }
            else
            {
                mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
            }
        }

    }


    @Override
    public void onErrorAccessToLocation()
    {
        mView.get().showToast(R.string.errorAccessToLocation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onError(int resId)
    {
        mView.get().showAlertMessage(resId , Constants.FAILED_MESSAGE());
    }
}
