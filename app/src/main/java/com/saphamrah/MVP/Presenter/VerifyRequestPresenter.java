package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.VerifyRequestMVP;
import com.saphamrah.MVP.Model.VerifyRequestModel;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.TaghvimTatilModel;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VerifyRequestPresenter implements VerifyRequestMVP.PresenterOps , VerifyRequestMVP.RequiredPresenterOps
{

    private WeakReference<VerifyRequestMVP.RequiredViewOps> mView;
    private VerifyRequestMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public VerifyRequestPresenter(VerifyRequestMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new VerifyRequestModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(VerifyRequestMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getConfig()
    {
        mModel.getConfig();
    }

    @Override
    public void getRequestDetail(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels)
    {
        mModel.getRequestDetail(kalaDarkhastFaktorModels);
    }

    @Override
    public void getModatRoozRaasgiri(int ccChildSelectNoeVosol)
    {
        mModel.getModatRoozRassgiri(ccChildSelectNoeVosol);
    }

    @Override
    public void checkBottomBarClick(int position)
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
                mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        else if (position == 1)
        {
            mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else if (position == 2)
        {
            mView.get().openDarkhastActivity();
        }
        else if (position == 4)
        {
            PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
            bottomBarConfig.getConfig(getAppContext());
            if (bottomBarConfig.getShowInvoiceSettlement())
            {
                mView.get().openInvoiceSettlementActivity();
            }
            else
            {
                mView.get().showToast(R.string.errorDisableThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                mView.get().openCustomerSignActivity();
            }
        }
        else if (position == 5)
        {
            PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
            bottomBarConfig.getConfig(getAppContext());
            if (bottomBarConfig.getShowInvoiceSettlement())
            {
                mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
            else
            {
                mView.get().openCustomerSignActivity();
            }
        }
    }

    @Override
    public void getCustomerInfo(int ccMoshtary, int ccSazmanForosh)
    {
        if (ccMoshtary == -1)
        {
            mView.get().showToast(R.string.errorSelectCustomer , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        }
        else
        {
            mModel.getCustomerInfo(ccMoshtary,ccSazmanForosh);
        }
    }

    @Override
    public void checkForOpenMarjoeeKalaActivity(long ccDarkhastFaktor, int ccMoshtary, String sumMablaghKhales)
    {
        if (ccMoshtary <= 0)
        {
            mView.get().showToast(R.string.errorSelectCustomer, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            return;
        }
        else if (ccDarkhastFaktor <= 0)
        {
            mView.get().showToast(R.string.errorFindDarkhastFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            return;
        }
        else
        {
            try
            {
                float fltSumMablaghKhales = Float.parseFloat(sumMablaghKhales.trim().replace(",", ""));
                mModel.updateExtraPropMablaghNahaeeFaktor(ccDarkhastFaktor, ccMoshtary, fltSumMablaghKhales);
            }
            catch (Exception exception)
            {
                mView.get().showToast(R.string.errorInvalidMablaghKhalesFaktor, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        //mView.get().openMarjoeeKalaActivity(ccDarkhastFaktor, ccMoshtary);
    }

    @Override
    public void getRequestsList()
    {
        mModel.getRequestsList();
    }

    @Override
    public void getMarjoeeList()
    {
        mModel.getMarjoeeList();
    }

    @Override
    public void getBonusList()
    {
        mModel.getBonusList();
    }

    @Override
    public void getDiscounts(long ccDarkhastFaktor)
    {
        mModel.getDiscounts(ccDarkhastFaktor);
    }

    @Override
    public void calculateDiscounts(int ccChildParameterNoeVosol , int valueNoeVosol)
    {
        mView.get().showLoading();
        mModel.calculateDiscounts(ccChildParameterNoeVosol , valueNoeVosol);
    }

    @Override
    public void calculateTarikhPishbiniTahvil()
    {
        mModel.getTarikhPishbiniTahvilInfo();
    }

    @Override
    public void checkUpdateDarkhastFaktor(String modatVosol, int modatRoozRaasGiri, String sumMablaghKol, String sumMablaghKhales, String mablaghArzeshAfzoode, String sumTakhfifat, int codeNoeVosol, String nameNoeVosol, int ccAddress)
    {
        try
        {
            Log.d("verifyRequest" , "in presenter : " + sumMablaghKhales);
            double dblSumMablaghKol = Double.parseDouble(sumMablaghKol.trim().replace("," , ""));
            double dblSumMablaghKhales = Double.parseDouble(sumMablaghKhales.trim().replace("," , ""));
            float fltMablaghArzeshAfzoode = Float.parseFloat(mablaghArzeshAfzoode.trim().replace("," , ""));
            float fltSumTakhfifat = Float.parseFloat(sumTakhfifat.trim().replace("," , ""));
            //PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider(getAppContext());
            mModel.updateDarkhastFaktor(modatVosol, modatRoozRaasGiri, dblSumMablaghKol, dblSumMablaghKhales, fltMablaghArzeshAfzoode, fltSumTakhfifat, codeNoeVosol, nameNoeVosol, ccAddress);
            /*if (googleLocationProvider.getHasAccess())
            {
                mModel.updateDarkhastFaktor(modatVosol, modatRoozRaasGiri, fltSumMablaghKol, fltSumMablaghKhales, fltMablaghArzeshAfzoode, fltSumTakhfifat, codeNoeVosol, nameNoeVosol, ccAddress);
            }
            else
            {
                mView.get().showToast(R.string.errorAccessToLocation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }*/
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestPresenter", "", "checkDarkhastFaktor", "");
        }
    }

    @Override
    public void deleteBonus(long ccDarkhastFaktor , boolean openSelectBonusActivity)
    {
        mModel.deleteBonus(ccDarkhastFaktor , openSelectBonusActivity);
    }

    @Override
    public void checkData(int clickedBottomBarposition ,String mablaghKol, String sumTakhfifat, String mablaghFaktor , String sumMablaghBaArzeshAfzoode , int ccAddress , int modatVosol , int codeNoeVosol, String nameNoeVosol , int modatRoozRaasGiri , String vaznFaktor , String hajmFaktor, Date tarikhPishbiniTahvil, String TedadAghlam)
    {
        try
        {
			double dblMablaghKol = Double.parseDouble(mablaghKol.replace("," , ""));
			float fltSumTakhfifat = Float.parseFloat(sumTakhfifat.replace("," , ""));
			double dblMablaghFaktor = Double.parseDouble(mablaghFaktor.replace("," , ""));
			long lngMablaghBaArzeshAfzoode = Long.parseLong(sumMablaghBaArzeshAfzoode.replace("," , "") , 10);
			double doubleVaznFaktor = Double.parseDouble(vaznFaktor.replace("," , ""));
			double doubleHajmFaktor = Double.parseDouble(hajmFaktor.replace("," , ""));
			int intTedadAghlamFaktor = Integer.parseInt(TedadAghlam.replace("," , ""));
			mModel.checkData(clickedBottomBarposition, dblMablaghKol, fltSumTakhfifat, dblMablaghFaktor, lngMablaghBaArzeshAfzoode, ccAddress, modatVosol, codeNoeVosol, nameNoeVosol, modatRoozRaasGiri, doubleVaznFaktor, doubleHajmFaktor, tarikhPishbiniTahvil, intTedadAghlamFaktor);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
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


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetConfig(boolean showBtnMarjoee)
    {
        mView.get().onGetConfig(showBtnMarjoee);
    }

    @Override
    public void onGetCustomerAddress(ArrayList<MoshtaryAddressModel> moshtaryAddressModels, boolean showTarikhPishbiniTahvil)
    {
        if (showTarikhPishbiniTahvil)
        {
            mView.get().showTarikhPishbiniTahvil();
        }
        else
        {
            mView.get().hideTarikhPishbiniTahvil();
        }
        if (moshtaryAddressModels.size() > 0)
        {
            ArrayList<String> arrayListAddressTitles = new ArrayList<>();
            for (MoshtaryAddressModel model : moshtaryAddressModels)
            {
                arrayListAddressTitles.add(model.getAddress());
            }
            mView.get().onGetCustomerAddress(moshtaryAddressModels , arrayListAddressTitles);
        }
        else
        {
            mView.get().showAlertDialog(R.string.notFoundAddressTahvil, true,Constants.FAILED_MESSAGE());
        }
    }

    @Override
    public void onGetInfo(long ccDarkhastFaktor , int modatVosol , ArrayList<ParameterChildModel> childParameterModelsVosols)
    {
        ArrayList<String> vosolTitles = new ArrayList<>();
        for (ParameterChildModel model : childParameterModelsVosols)
        {
            vosolTitles.add(model.getTxt());
            Log.d("vosol","vosolTitles:" + model.getTxt());
        }
        mView.get().onGetInfo(ccDarkhastFaktor , modatVosol , childParameterModelsVosols , vosolTitles);
    }

    @Override
    public void onGetRequestDetail(int sumTedadAghlam, int tedadAghlam, double sumVazn, double sumHajm, double vaznFaktor, double hajmFaktor, double sumMablaghKol, double sumMablaghBaArzeshAfzoodeh, double sumMablaghKhales, int sumTedadAghlamPishnehadiBiSetareh)
    {
        closeLoading();
        mView.get().onGetRequestDetail(sumTedadAghlam, tedadAghlam, sumVazn, sumHajm, vaznFaktor, hajmFaktor, sumMablaghKol, sumMablaghBaArzeshAfzoodeh, sumMablaghKhales, sumTedadAghlamPishnehadiBiSetareh);
    }

    @Override
    public void onGetModatRoozRaasgiri(int modatRoozRaasgiri, boolean isSelectedVosolVajhNagh)
    {
        mView.get().showModatRoozRaasgiri(modatRoozRaasgiri, isSelectedVosolVajhNagh);
    }

    @Override
    public void onGetRequestsList(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels)
    {
        mView.get().onGetRequestsList(kalaDarkhastFaktorModels);
    }

    @Override
    public void onGetMarjoeeList(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels)
    {
        mView.get().onGetMarjoeeList(kalaElamMarjoeeModels);
    }

    @Override
    public void onGetBonus(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels , boolean showAddBonusBtn, boolean haveBonus)
    {
        mView.get().onGetBonus(darkhastFaktorJayezehModels , showAddBonusBtn, haveBonus);
    }

    @Override
    public void onErrorCalculateDiscount(int resId)
    {
        mView.get().onFailedCalculateDiscount(resId);
        //mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessCalculateDiscount(boolean haveBonus, boolean canSelectBonus)
    {
        mView.get().onSuccessCalculateDiscount(haveBonus, canSelectBonus);
    }

    @Override
    public void onGetDiscounts(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels)
    {
        double sumMablaghTakhfif = 0;
        for (DarkhastFaktorTakhfifModel model : darkhastFaktorTakhfifModels)
        {
            sumMablaghTakhfif += model.getMablaghTakhfif();
        }
        mView.get().onGetDiscounts(darkhastFaktorTakhfifModels, sumMablaghTakhfif);
    }

    @Override
    public void onGetTarikhPishbiniTahvilInfo(int maxTedadRooz, List<TaghvimTatilModel> taghvimTatilModels)
    {
        if (maxTedadRooz != 0)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
            DateUtils dateUtils = new DateUtils();
            Map<String, Date> mapDates = new TreeMap<>();
            Date todayDate = new Date();
            int counter = 1;
            int registeredDateCount = 0;

            try
            {
                todayDate = sdf.parse(sdf.format(todayDate));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            while (registeredDateCount < maxTedadRooz)
            {
                try
                {
                    Date date = dateUtils.addDay(todayDate, counter);
                    boolean valid = true;
                    for (TaghvimTatilModel taghvimTatilModel : taghvimTatilModels)
                    {
                        if (DateUtils.getDateDiffAsDay(sdf.parse(sdf.format(date)), sdf.parse(taghvimTatilModel.getTarikhTatily())) == 0)
                        {
                            valid = false;
                            break;
                        }
                    }

                    if (valid)
                    {
                        String persianDate = dateUtils.gregorianToPersianDateTime(sdf.parse(sdf.format(date))).substring(0, 10);
                        mapDates.put(persianDate, date);
                        registeredDateCount++;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                counter++;
            }
            Map<String, Date> mapFinal = new LinkedHashMap<>();
            mapFinal.put(getAppContext().getString(R.string.darEkhtiarPakhsh), null);
            mapFinal.putAll(mapDates);

            mView.get().showCalculatedDateOfTarikhPishbiniTahvil(mapFinal);
        }
        else
        {
            mView.get().showToast(R.string.errorGetMaxRoozTahvil, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onDeleteBonus(boolean openSelectBonusActivity)
    {
        mView.get().onDeleteBonus(openSelectBonusActivity);
    }

    @Override
    public void onErrorOperations(int resId)
    {
        mView.get().showToast(resId , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onErrorCheck(int errorResId, String paramOne)
    {
        if (paramOne.trim().equals(""))
        {
            mView.get().showToast(errorResId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            mView.get().showToast(errorResId, paramOne, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void onSuccessCheck(int clickedBottomBarposition)
    {
        checkBottomBarClick(clickedBottomBarposition);
    }

    @Override
    public void onUpdateMablaghNahaeeFaktor(long ccDarkhastFaktor, int ccMoshtary)
    {
        mView.get().openMarjoeeKalaActivity(ccDarkhastFaktor, ccMoshtary);
    }

    @Override
    public void deleteTakhfifJayezeForDarkhastFaktor(long ccDarkhastFaktor)
    {
        mModel.deleteTakhfifJayezeForDarkhastFaktor(ccDarkhastFaktor);
    }

    @Override
    public void showLoading()
    {
        mView.get().showLoading();
    }

    @Override
    public void closeLoading()
    {
        mView.get().closeLoading();
    }
}
