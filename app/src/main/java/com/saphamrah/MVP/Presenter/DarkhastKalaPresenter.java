package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.DarkhastKalaMVP;
import com.saphamrah.MVP.Model.DarkhastKalaModel;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.JayezehByccKalaCodeModel;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaMojodiZaribModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DarkhastKalaPresenter implements DarkhastKalaMVP.PresenterOps , DarkhastKalaMVP.RequiredPresenterOps
{

    private WeakReference<DarkhastKalaMVP.RequiredViewOps> mView;
    private DarkhastKalaMVP.ModelOps mModel;
    private boolean mIsChangingConfig;

    public DarkhastKalaPresenter(DarkhastKalaMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new DarkhastKalaModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(DarkhastKalaMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

	@Override
    public void getNoeMasouliat()
    {
        mModel.getNoeMasouliat();
    }
	
    @Override
    public void checkBottomBarClick(int position , int selectedGoodsCount)
    {
        if (position == 0)
        {
            PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
            bottomBarConfig.getConfig(getAppContext());
            if (bottomBarConfig.getShowBarkhordAvalie())
            {
                mView.get().openBarkhordAvalieActivity();
            }
            else
            {
                mView.get().showToast(R.string.errorDisableThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        if (position == 3)
        {
            if (selectedGoodsCount == 0)
            {
                mView.get().showToast(R.string.errorCantSetEmptyRequest, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
            else
            {
                mModel.checkData();
            }
        }
        else if (position == 1 || position == 4 || position == 5)
        {
            mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }


    @Override
    public void getNameListOfKalaAdamForosh()
    {
        mModel.getNameListOfKalaAdamForosh();
    }

    @Override
    public void getAllRequestedGoods()
    {
        mModel.getAllRequestedGoods();
    }

    @Override
    public void getAllKalaWithMojodiZarib(int ccMoshtary)
    {
        if (ccMoshtary > 0)
        {
            mView.get().showAlertLoading();
            mModel.getAllKalaWithMojodiZarib(ccMoshtary, true, true, true);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectCustomer , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        }
    }

    @Override
    public void checkAddKala(int ccMoshtary , int position , KalaMojodiZaribModel kalaMojodiZaribModel, String cartonCount, String basteCount, String adadCount)
    {
        boolean validData = true;
        try
        {
            SelectFaktorShared shared = new SelectFaktorShared(getAppContext());
            int requestCountCarton = Integer.parseInt(cartonCount.length()==0 ? "0" : cartonCount);
            int requestCountBaste = Integer.parseInt(basteCount.length()==0 ? "0" : basteCount);
            int requestCountAdad = Integer.parseInt(adadCount.length()==0 ? "0" : adadCount);
            int requestCountSum = (requestCountCarton * kalaMojodiZaribModel.getTedadDarKarton()) + (requestCountBaste * kalaMojodiZaribModel.getTedadDarBasteh()) + (requestCountAdad * kalaMojodiZaribModel.getAdad());
            int ccGorohNoeSenf = shared.getInt(shared.getCcGorohNoeSenf() , 0);
            final int ccNoeSenfMoshtary_NemoonehKala = 345;
            Log.d("DarkhastKala","requestCountCarton:"+requestCountCarton);
            Log.d("DarkhastKala","requestCountBaste:"+requestCountBaste);
            Log.d("DarkhastKala","requestCountAdad:"+requestCountAdad);
            Log.d("DarkhastKala","requestCountSum:"+requestCountSum);
            Log.d("DarkhastKala","kalaMojodiZaribModel.getTedad():"+kalaMojodiZaribModel.getTedad()+" ,kalaMojodiZaribModel.getMax_MojodyByShomarehBach():"+kalaMojodiZaribModel.getMax_MojodyByShomarehBach());

            if (requestCountSum > kalaMojodiZaribModel.getTedad())
            {
                mView.get().onErrorAddNewRequestedKala(R.string.errorBiggerThanMojodi);
                validData = false;
            }
            else if (requestCountSum > kalaMojodiZaribModel.getMax_MojodyByShomarehBach())
            {
                mView.get().onErrorAddNewRequestedKala(R.string.errorBiggerThanMaxMojodiByBach, String.valueOf(kalaMojodiZaribModel.getMax_MojodyByShomarehBach()));
                validData = false;
            }
            else if (requestCountSum == 0)
            {
                mView.get().onErrorAddNewRequestedKala(R.string.errorBiggerThanZero);
                validData = false;
            }
            else if ( (kalaMojodiZaribModel.getZaribForosh() != 0) &&
                    (requestCountSum % kalaMojodiZaribModel.getZaribForosh() != 0 ) && (requestCountSum % kalaMojodiZaribModel.getTedadDarKarton() != 0) &&
                    (requestCountSum - kalaMojodiZaribModel.getTedadMojodyGhabelForosh() != 0 ) && (ccGorohNoeSenf != ccNoeSenfMoshtary_NemoonehKala))
            {
                mView.get().onErrorAddNewRequestedKala(R.string.errorZaribForosh);
                validData = false;
            }


            if (validData)
            {
                mModel.insertNewFaktorSatr(ccMoshtary , position , kalaMojodiZaribModel , requestCountSum);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mView.get().onErrorAddNewRequestedKala(R.string.errorInvalidCount);
        }
    }

    @Override
    public void checkRemoveKala(KalaDarkhastFaktorSatrModel kalaDarkhastFaktorModel , int position , int ccMoshtary)
    {
        if (kalaDarkhastFaktorModel != null)
        {
            mView.get().showAlertLoading();
            mModel.removeKala(kalaDarkhastFaktorModel , position , ccMoshtary);
        }
    }

    @Override
    public void getElatAdamSefaresh(int ccMoshtary)
    {
        mModel.getElatAdamSefaresh(ccMoshtary);
    }

    @Override
    public void checkSeletedAdamSefareshItem(int ccMoshtary, ElatAdamDarkhastModel elatAdamDarkhastModel)
    {
        if (elatAdamDarkhastModel.getGetImage() == 1)
        {
            mView.get().showTakeImageAlert(elatAdamDarkhastModel);
        }
        else if (elatAdamDarkhastModel.getCcElatAdamDarkhast() == Constants.NEED_CUSTOMER_DUPLICATED_CODE())
        {
            mView.get().showDuplicatedCustomerCodeAlert(elatAdamDarkhastModel);
        }
        else
        {
            checkAdamSefareshForInsert(ccMoshtary, elatAdamDarkhastModel, null , "");
        }
    }

    @Override
    public void checkAdamSefareshForInsert(int ccMoshtary, ElatAdamDarkhastModel elatAdamDarkhastModel, byte[] imageAdamSefaresh, String codeMoshtaryTekrari)
    {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        SelectFaktorShared shared = new SelectFaktorShared(getAppContext());
        String saatVorodVemaghazeh = shared.getString(shared.getSaatVorodBeMaghazeh(),sdfDateTime.format(new Date()));
        boolean isMorajehShodeh = shared.getBoolean(shared.getIsMorajehShodeh(),false);
        int minModatHozor = shared.getInt(shared.getMinModatHozor(),0);
        Date currentDate = null;
        Date saatVorod= null;
        Log.d("DarkhastKala","saatVorodVemaghazeh:"+saatVorodVemaghazeh);
        try {
            currentDate = sdfDateTime.parse(sdfDateTime.format(new Date()));
            saatVorod = sdfDateTime.parse(saatVorodVemaghazeh);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long DateDiff = currentDate.getTime() - saatVorod.getTime();
        long diffInMin = TimeUnit.MILLISECONDS.toMinutes(DateDiff);
        Log.d("DarkhastKala","currentDate:"+currentDate+" , saatVorod:"+saatVorod + " , diffInMin:"+diffInMin+ " , isMorajehShodeh"+isMorajehShodeh + " , minModatHozor:" + minModatHozor);
        if (elatAdamDarkhastModel == null)
        {
            mView.get().showToast(R.string.errorSelectElatAdamSefaresh, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            if(elatAdamDarkhastModel.getCodeNoe()==0 & diffInMin<minModatHozor & !isMorajehShodeh)
            {
                mView.get().showToast(R.string.errorModatZamanHozorDarMaghazeh, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
            else if (elatAdamDarkhastModel.getGetImage() == 1)
            {
                if (imageAdamSefaresh != null && imageAdamSefaresh.length > 0)
                {
                    mModel.insertAdamSefaresh(ccMoshtary, elatAdamDarkhastModel.getCcElatAdamDarkhast(), imageAdamSefaresh, codeMoshtaryTekrari);
                }
                else
                {
                    mView.get().showToast(R.string.errorSelectImage, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
            }
            else if (elatAdamDarkhastModel.getCcElatAdamDarkhast() == Constants.NEED_CUSTOMER_DUPLICATED_CODE())
            {
                if (codeMoshtaryTekrari != null && codeMoshtaryTekrari.trim().length() > 0)
                {
                    mModel.insertAdamSefaresh(ccMoshtary, elatAdamDarkhastModel.getCcElatAdamDarkhast(), imageAdamSefaresh, codeMoshtaryTekrari);
                }
                else
                {
                    mView.get().showToast(R.string.errorCustomerDuplicatedCode, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
            }
            else
            {
                mModel.insertAdamSefaresh(ccMoshtary, elatAdamDarkhastModel.getCcElatAdamDarkhast(), imageAdamSefaresh, codeMoshtaryTekrari);
            }
        }
    }


    @Override
    public void checkOnBackPress()
    {
        PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
        bottomBarConfig.getConfig(getAppContext());
        if (!bottomBarConfig.getShowBarkhordAvalie())
        {
            mView.get().closeActivity();
        }
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
    {}



    @Override
    public void checkJayezehParent(int ccKalaCode , int tedadKala , Long ccDarkhastFaktor, double mablaghForosh) {
        mModel.checkJayezehParent(ccKalaCode ,  tedadKala , ccDarkhastFaktor,mablaghForosh);
    }

    @Override
    public void getRecyclerDetails() {
        mModel.getRecyclerDetails();
    }

    @Override
    public void checkJayezeh(int ccJayezeh, int tedadKala, double mablaghForosh, int ccKalaCode, Long ccDarkhastFaktor,int position) {
        mModel.checkJayezeh(ccJayezeh,tedadKala,mablaghForosh,ccKalaCode,ccDarkhastFaktor ,position);
    }



    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }
	
	@Override
    public void onGetNoeMasouliat(int noeMasouliat)
    {
        if (noeMasouliat != 1 && noeMasouliat != 2 && noeMasouliat != 3 && noeMasouliat!=6 && noeMasouliat!=8)
        {
            mView.get().hideNoRequestButton();
        }
    }

    @Override
    public void onGetRequestedGoods(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels)
    {
        if (kalaDarkhastFaktorModels.size() > 0)
        {
            mView.get().onGetRequestedGoods(kalaDarkhastFaktorModels);
        }
        else
        {
            mView.get().showToast(R.string.emptyList, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetAllKalaWithMojodiZarib(ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels)
    {
        Log.d("DarkhastKala", "on get kala in presenter");
        mView.get().closeAlertLoading();
        if (kalaMojodiZaribModels.size() > 0)
        {
            mView.get().onGetAllKalaWithMojodiZarib(kalaMojodiZaribModels);
        }
        else
        {
            mView.get().showAlertDialog(R.string.notFoundKalaMojodiInAnbarak, Constants.FAILED_MESSAGE(), true);
        }
    }

    @Override
    public void onErrorInsertFaktor(int errorResId)
    {
        mView.get().showToast(errorResId , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        mView.get().closeSelectNewGoodAlert( 0 , 0);
    }

    @Override
    public void onSuccessInsertFaktor(int position , int count)
    {
        mView.get().showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        mView.get().closeSelectNewGoodAlert(position , count);
    }

    @Override
    public void onSuccessRemoveKala(int position)
    {
        mView.get().showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        mView.get().onSuccessRemoveKala(position);
    }

    @Override
    public void onErrorRemoveKala()
    {
        mView.get().showToast(R.string.failedOps, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessInsertAdamDarkhast()
    {
        mView.get().onSuccessInsertAdamDarkhast();
    }

    @Override
    public void onFailedInsertAdamDarkhast(int errorResId)
    {
        mView.get().showToast(errorResId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void showNameListOfKalaAdamForosh(String kalaNames)
    {
        mView.get().showNameListOfKalaAdamForosh(kalaNames);
    }

    @Override
    public void onSuccessCheckData()
    {
        mView.get().prepareDataForCheckInsertFaktor();
    }

    @Override
    public void onErrorNotSelectKalaAsasi()
    {
        mView.get().showAlertDialog(R.string.errorKalaAsasiNotInDarkhast, Constants.FAILED_MESSAGE(), false);
    }

    @Override
    public void onErrorBiggerThanMaxKalaMojodi(List<KalaModel> kalaModels)
    {
        mView.get().showErrorAlertLoadingMaxMojodi(kalaModels);
    }

    @Override
    public void onError()
    {
        mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }



    @Override
    public void onGetElatAdamSefaresh(ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels)
    {
        ArrayList<Integer> elatAdamDarkhastIds = new ArrayList<>();
        ArrayList<String> elatAdamDarkhastTitles = new ArrayList<>();
        for (ElatAdamDarkhastModel model : elatAdamDarkhastModels)
        {
            elatAdamDarkhastIds.add(model.getCcElatAdamDarkhast());
            elatAdamDarkhastTitles.add(model.getNameElatAdamDarkhast());
        }
        mView.get().onGetElatAdamDarkhast(elatAdamDarkhastModels , elatAdamDarkhastIds , elatAdamDarkhastTitles);
    }

    @Override
    public void onCheckJayezehParent(ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeParentModels, int tedadKala, double mablaghForosh , int ccKalaCode , Long ccDarkhastFaktor) {
        mView.get().onCheckJayezehParent(jayezehByccKalaCodeParentModels  , tedadKala,mablaghForosh,ccKalaCode,ccDarkhastFaktor);
    }

    @Override
    public void onCheckJayezeh(int position) {
        mView.get().onCheckJayezeh(position);
    }
    @Override
    public void onGetRecyclerDetails(int ItemCountPerScreen, ArrayList<KalaPhotoModel> kalaPhotoModels) {
        mView.get().onGetGridRecyclerDetails(ItemCountPerScreen,kalaPhotoModels);
    }

}
