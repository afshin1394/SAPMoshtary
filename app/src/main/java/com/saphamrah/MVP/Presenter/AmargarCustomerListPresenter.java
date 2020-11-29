package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.AmargarCustomerListMVP;
import com.saphamrah.MVP.Model.AmargarCustomerListModel;
import com.saphamrah.Model.AmargarMarkazSazmanForoshModel;
import com.saphamrah.Model.ElatAdamMoarefiMoshtaryModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Valhalla.Location;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class AmargarCustomerListPresenter implements AmargarCustomerListMVP.PresenterOps, AmargarCustomerListMVP.RequiredPresenterOps
{

    private WeakReference<AmargarCustomerListMVP.RequiredViewOps> mView;
    private AmargarCustomerListModel mModel;

    public AmargarCustomerListPresenter(AmargarCustomerListMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
        mModel = new AmargarCustomerListModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AmargarCustomerListMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void getAllCustomers()
    {
        mModel.getAllCustomers();
    }

    @Override
    public void getAmargarMarkazForosh()
    {
        mModel.getAmargarMarkazForosh();
    }

    @Override
    public void getAmargarSazmanForosh(Integer selectedMarkazForosh)
    {
        if (selectedMarkazForosh == null || selectedMarkazForosh <= 0)
        {
            mView.get().showErrorSelectMarkaz();
        }
        else
        {
            mModel.getAmargarSazmanForosh(selectedMarkazForosh);
        }
    }

    @Override
    public void getForoshandeh(Integer selectedSazmanId)
    {
        if (selectedSazmanId == null || selectedSazmanId <= 0)
        {
            mView.get().showErrorSelectSazman();
        }
        else
        {
            mModel.getForoshandeh(selectedSazmanId);
        }
    }

    @Override
    public void getMasir(Integer selectedForoshandehId)
    {
        if (selectedForoshandehId == null || selectedForoshandehId <= 0)
        {
            mView.get().showErrorSelectForoshandeh();
        }
        else
        {
            mModel.getMasir(selectedForoshandehId);
        }
    }

    @Override
    public void getListMoshtarian(Integer selectedMasirId)
    {
        if (selectedMasirId == null || selectedMasirId <= 0)
        {
            mView.get().showErrorSelectMasir();
        }
        else
        {
            mView.get().showAlertLoading();
            mModel.getListMoshtarian(selectedMasirId);
        }
    }

    @Override
    public void getCustomerListByLocation(String radius, String latitude, String longitude)
    {
        try
        {
            if (radius.trim().equals(""))
            {
                mView.get().showErrorSelectRadius();
            }
            else if (latitude.trim().equals("0") || longitude.trim().equals("0"))
            {
                mView.get().showErrorWrongLocation();
            }
            else
            {
                float fltRadius = Float.parseFloat(radius);
                fltRadius = fltRadius / 1000;
                mView.get().showAlertLoading();
                mModel.getCustomerListByLocation(String.valueOf(fltRadius) , latitude , longitude);
            }

        }
        catch (Exception exception)
        {
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RptCustomersListByDistancePresenter", "", "getCustomerListByLocation", "");
            mView.get().showErrorSelectRadius();
        }
    }

    @Override
    public void getRadiusConfig()
    {
        mModel.getRadiusConfig();
    }

    @Override
    public void checkCustomerLocation(String customerName, double latitude, double longitude)
    {
        if (latitude == 0 || longitude == 0)
        {
            mView.get().showErrorWrongCustomerLocation(customerName);
        }
        else
        {
            mView.get().showCustomerLocation(latitude, longitude);
        }
    }

    @Override
    public void checkForSendPorseshname(int ccMoshtary)
    {
        if (ccMoshtary > -1)
        {
            mModel.sendPorseshname(ccMoshtary);
        }
        else
        {
            mView.get().showErrorSelectCustomer();
        }
    }

    @Override
    public void getElatAdamMoarefiMoshtary(int ccMoshtary)
    {
        mModel.getElatAdamMoarefiMoshtary(ccMoshtary);
    }

    @Override
    public void checkElatAdamMoarefi(int selectedccElat, int ccMoshtary)
    {
        if (selectedccElat == 2)
        {
            mView.get().showAlertGetCodeMoshtaryTekrari(selectedccElat, ccMoshtary);
        }
        else
        {
            mModel.saveAdamFaal(selectedccElat, ccMoshtary, "0");
        }
    }

    @Override
    public void checkForAddPorseshname(Location currentLocation, ListMoshtarianModel model)
    {
        mModel.checkForAddPorseshname(currentLocation, model);
    }

    @Override
    public void insertAdamFaal(int ccMoshtary, int selectedccElat, String codeMoshtaryTekrari)
    {
        mModel.saveAdamFaal(selectedccElat, ccMoshtary, codeMoshtaryTekrari);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetAllCustomers(List<ListMoshtarianModel> listMoshtarianModels)
    {
        if (listMoshtarianModels.size() > 0)
        {
            mView.get().showListMoshtarian(listMoshtarianModels);
        }
        else
        {
            mView.get().showNotFoundMoshtary();
        }
    }

    @Override
    public void onGetAmargarMarkazForosh(List<AmargarMarkazSazmanForoshModel> markazModels)
    {
        if (markazModels.size() > 0)
        {
            List<String> titles = new ArrayList<>();
            for (AmargarMarkazSazmanForoshModel markazModel : markazModels)
            {
                titles.add(markazModel.getNameMarkazForosh());
            }
            mView.get().onGetMarkazForosh(markazModels, titles);
        }
        else
        {
            mView.get().showErrorGetMarkaz();
        }
    }

    @Override
    public void onGetAmargarSazmanForosh(List<AmargarMarkazSazmanForoshModel> markazModels)
    {
        if (markazModels.size() > 0)
        {
            List<String> titles = new ArrayList<>();
            for (AmargarMarkazSazmanForoshModel model : markazModels)
            {
                titles.add(model.getNameSazmanForosh());
            }
            mView.get().onGetSazmanForosh(markazModels, titles);
        }
        else
        {
            mView.get().showErrorNotFoundForoshandeh();
        }
    }

    @Override
    public void onGetForoshandeh(List<ForoshandehModel> foroshandehModels)
    {
        if (foroshandehModels.size() > 0)
        {
            List<String> titles = new ArrayList<>();
            for (ForoshandehModel foroshandehModel : foroshandehModels)
            {
                titles.add(foroshandehModel.getCodeForoshandeh() + " - " + foroshandehModel.getFullNameForoshandeh());
            }
            mView.get().onGetForoshandeh(foroshandehModels, titles);
        }
        else
        {
            mView.get().showErrorNotFoundForoshandeh();
        }
    }

    @Override
    public void onGetMasir(List<MasirModel> masirModels)
    {
        if (masirModels.size() > 0)
        {
            List<String> titles = new ArrayList<>();
            for (MasirModel masirModel : masirModels)
            {
                titles.add(masirModel.getNameMasir());
            }
            mView.get().onGetMasir(masirModels, titles);
        }
        else
        {
            mView.get().showErrorNotFoundMasir();
        }
    }

    @Override
    public void onGetListMoshtarian(ArrayList<ListMoshtarianModel> arrayListData)
    {
        mView.get().closeAlertLoading();
        if (arrayListData == null)
        {
            mView.get().showErrorGetListMoshtarian();
        }
        else if (arrayListData.size() == 0)
        {
            mView.get().showNotFoundMoshtary();
        }
        else
        {
            mView.get().showListMoshtarian(arrayListData);
        }
    }

    @Override
    public void onGetListMoshtarianByLocation(ArrayList<ListMoshtarianModel> arrayListData)
    {
        mView.get().closeAlertLoading();
        if (arrayListData == null)
        {
            mView.get().showErrorGetListMoshtarian();
        }
        else if (arrayListData.size() == 0)
        {
            mView.get().showNotFoundMoshtary();
        }
        else
        {
            mView.get().showListMoshtarianByLocation(arrayListData);
        }
    }

    @Override
    public void onGetRadiusConfig(ArrayList<ParameterChildModel> childParameterModelsConfig)
    {
        String maxRadius = "0";
        String stepRadius = "0";
        for (ParameterChildModel model : childParameterModelsConfig)
        {
            if (model.getCcParameterChild() == Constants.CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST())
            {
                maxRadius = model.getValue();
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST())
            {
                stepRadius = model.getValue();
            }
        }

        if (maxRadius.trim().equals("0") || stepRadius.trim().equals("0"))
        {
            mView.get().showErrorGetRadiusConfig();
        }
        else
        {
            try
            {
                int intMaxRadius = Integer.parseInt(maxRadius);
                float fltStepRadius = Float.parseFloat(stepRadius);
                if (intMaxRadius == 0 || fltStepRadius == 0)
                {
                    mView.get().showErrorGetRadiusConfig();
                }
                else
                {
                    ArrayList<String> arrayListRadiusItems = new ArrayList<>();
                    int stepInMeter = (int)(intMaxRadius * fltStepRadius * 1000);
                    int item = stepInMeter;
                    arrayListRadiusItems.add(String.valueOf(item));
                    while (item < intMaxRadius * 1000)
                    {
                        item += stepInMeter;
                        arrayListRadiusItems.add(String.valueOf(item));
                    }
                    arrayListRadiusItems.add("2000");
                    arrayListRadiusItems.add("3000");
                    arrayListRadiusItems.add("4000");
                    arrayListRadiusItems.add("5000");
                    mView.get().onGetRadiusConfig(arrayListRadiusItems);
                }
            }
            catch (Exception exception)
            {
                mModel.setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "RptCustomersListByDistancePresenter", "", "onGetRadiusConfig", "");
            }
        }
    }

    @Override
    public void onGetElatAdamMoarefiMoshtary(ArrayList<ElatAdamMoarefiMoshtaryModel> elatAdamMoarefiMoshtaryModels, int ccMoshtary)
    {
        if (elatAdamMoarefiMoshtaryModels.size() > 0)
        {
            List<String> titles = new ArrayList<>();
            for (ElatAdamMoarefiMoshtaryModel model : elatAdamMoarefiMoshtaryModels)
            {
                titles.add(model.getNameElatAdamMoarefiMoshtary());
            }
            mView.get().showElatAdamMoarefiMoshtary(elatAdamMoarefiMoshtaryModels, titles, ccMoshtary);
        }
        else
        {
            mView.get().showErrorGetElatAdamMoarefiMoshtary();
        }
    }

    @Override
    public void onSaveAdamFaal(boolean result)
    {
        if (result)
        {
            mView.get().showSuccessInsertAdamFaal();
        }
        else
        {
            mView.get().showErrorInsertAdamFaal();
        }
    }

    @Override
    public void onCheckAddPorsehsname(int resultCode, int ccMoshtary, String codeMoshtary)
    {
        if (resultCode == 1)
        {
            mView.get().openAddPorseshnameActivity(ccMoshtary, codeMoshtary);
        }
        else if (resultCode == -1)
        {
            mView.get().showErrorDistanceForAddPorseshname();
        }
        else if (resultCode == -2)
        {
            mView.get().showErrorSendAllData();
        }
    }

    @Override
    public void onErrorSendPorseshname(int resultCode)
    {
        switch (resultCode)
        {
            case -1:
                mView.get().showErrorSendPorseshname();
                break;
            case -2:
                mView.get().showErrorGetPorseshname();
                break;
            default:
                mView.get().showErrorGetPorseshname();
        }
    }
}
