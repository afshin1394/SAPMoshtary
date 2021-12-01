package com.saphamrah.MVP.Presenter;

import android.content.Context;

import com.saphamrah.BaseMVP.AddCustomerBaseInfoMVP;
import com.saphamrah.MVP.Model.AddCustomerBaseInfoModel;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AddCustomerBaseInfoPresenter implements AddCustomerBaseInfoMVP.PresenterOps , AddCustomerBaseInfoMVP.RequiredPresenterOps
{

    private WeakReference<AddCustomerBaseInfoMVP.RequiredViewOps> mView;
    private AddCustomerBaseInfoMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public AddCustomerBaseInfoPresenter(AddCustomerBaseInfoMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new AddCustomerBaseInfoModel(this);
    }

    @Override
    public void onConfigurationChanged(AddCustomerBaseInfoMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }


    /////////////////////////// PresenterOps ///////////////////////////

	
	@Override
    public void getConfig()
    {
        mModel.getConfig();
    }

    @Override
    public void getAnbar()
    {
        mModel.getAnbarItems();
    }

    @Override
    public void getRotbe()
    {
        mModel.getRotbeItems();
    }

    @Override
    public void getNoeShakhsiat()
    {
        mModel.getNoeShakhsiatItems();
    }

    @Override
    public void getNoeFaaliatItems()
    {
        mModel.getNoeFaaliatItems();
    }


    @Override
    public void getNoeSenfItems(String ccGorohLink)
    {
        try
        {
            int ccNoeFaaliat = Integer.parseInt(ccGorohLink);
            if (ccNoeFaaliat > 0)
            {
                mModel.getNoeSenfItems(ccNoeFaaliat);
            }
            else
            {
                mView.get().showResourceError(false , R.string.error , R.string.errorGetData , Constants.FAILED_MESSAGE() , R.string.apply);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "AddCustomerBaseInfoPresenter", "", "getNoeSenfItems", "");
            mView.get().showToast(R.string.errorNoeFaaliat, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void getNoeHaml()
    {
        mModel.getNoeHamlItems();
    }

    @Override
    public void getNoeVosol(String ccNoeFaaliat)
    {
        mModel.getNoeVosolItems(ccNoeFaaliat);
    }

    @Override
    public void checkCustomerBaseInfo(AddCustomerInfoModel addCustomerInfoModel, boolean requireCodeMeli, boolean requireMobile, boolean requireMasahateMaghaze)
    {
        boolean validData = true;
        if (addCustomerInfoModel != null) {
            if (addCustomerInfoModel.getFirstName() == null || addCustomerInfoModel.getFirstName().trim().equals("")) {
                validData = false;
                mView.get().onErrorFirstName();
            }
            if (addCustomerInfoModel.getNoeShakhsiatId() != null && addCustomerInfoModel.getNoeShakhsiatId().trim().equals("1")) // haghighi = 1
            {
                if (addCustomerInfoModel.getLastName() == null || addCustomerInfoModel.getLastName().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorLastName();
                }
            }
            if (addCustomerInfoModel.getTabloName() == null || addCustomerInfoModel.getTabloName().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorTabloName();
            }
            if (addCustomerInfoModel.getNoeShakhsiatId() != null && addCustomerInfoModel.getNoeShakhsiatId().trim().equals("1") || addCustomerInfoModel.getNationalCode().equals("")) // haghighi = 1
            {
                if (requireCodeMeli || (addCustomerInfoModel.getNationalCode() != null && addCustomerInfoModel.getNationalCode().trim().length() > 0))
                {
                    PubFunc.NationalCodeUtil nationalCodeUtil = new PubFunc().new NationalCodeUtil();
                    if (!nationalCodeUtil.checkNationalCode(addCustomerInfoModel.getNationalCode()))
                    {
                        validData = false;
                        mView.get().onErrorNationalCode();
                    }
                }
            }
            else if (addCustomerInfoModel.getNoeShakhsiatId() != null && addCustomerInfoModel.getNoeShakhsiatId().trim().equals("2") || addCustomerInfoModel.getNationalCode().equals("")) // hoghoghi = 2
            {
                if (requireCodeMeli || addCustomerInfoModel.getNationalCode() != null && addCustomerInfoModel.getNationalCode().trim().length() > 0)
                {
                    PubFunc.NationalCodeUtil nationalCodeUtil = new PubFunc().new NationalCodeUtil();
                    if (!nationalCodeUtil.checkNationalEconomicalCode(addCustomerInfoModel.getNationalCode()))
                    {
                        validData = false;
                        mView.get().onErrorNationalCode();
                    }
                }
            }
            if (requireMobile && (addCustomerInfoModel.getMobile() == null || addCustomerInfoModel.getMobile().trim().equals("") || (addCustomerInfoModel.getMobile().trim().length() > 0 && (addCustomerInfoModel.getMobile().trim().length() != 11 || !addCustomerInfoModel.getMobile().trim().startsWith("09")) )) )
            {
                validData = false;
                mView.get().onErrorMobile();
            }
            if (addCustomerInfoModel.getNoeShakhsiatId() == null || addCustomerInfoModel.getNoeShakhsiatId().trim().equals("") || addCustomerInfoModel.getNoeShakhsiatTitle() == null || addCustomerInfoModel.getNoeShakhsiatTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeShakhsiat();
            }
            if (addCustomerInfoModel.getNoeFaaliatId() == null || addCustomerInfoModel.getNoeFaaliatId().trim().equals("") || addCustomerInfoModel.getNoeFaaliatTitle() == null || addCustomerInfoModel.getNoeFaaliatTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeFaaliat();
            }
            if (addCustomerInfoModel.getNoeSenfId() == null || addCustomerInfoModel.getNoeSenfId().trim().equals("") || addCustomerInfoModel.getNoeSenfTitle() == null || addCustomerInfoModel.getNoeSenfTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeSenf();
            }
            if (addCustomerInfoModel.getNoeVosolId() == null || addCustomerInfoModel.getNoeVosolId().trim().equals("") || addCustomerInfoModel.getNoeVosolTitle() == null || addCustomerInfoModel.getNoeVosolTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeVosol();
            }
            if (addCustomerInfoModel.getNoeHamlId() == null || addCustomerInfoModel.getNoeHamlId().trim().equals("") || addCustomerInfoModel.getNoeHamlTitle() == null || addCustomerInfoModel.getNoeHamlTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorNoeHaml();
            }
            if (addCustomerInfoModel.getRotbeId() == null || addCustomerInfoModel.getRotbeId().trim().equals("") || addCustomerInfoModel.getRotbeTitle() == null || addCustomerInfoModel.getRotbeTitle().trim().equals(""))
            {
                validData = false;
                mView.get().onErrorRotbeh();
            }
            if (requireMasahateMaghaze && (addCustomerInfoModel.getMasahatMaghaze() == null || addCustomerInfoModel.getMasahatMaghaze().trim().equals("")))
            {
                validData = false;
                mView.get().onErrorMasahateMaghaze();
            }
        }
        else
        {
            validData = false;
            mView.get().showResourceError(false, R.string.error, R.string.errorPersonalInfo, Constants.FAILED_MESSAGE(), R.string.apply);
        }

        if (validData)
        {
            mModel.saveCustomerBaseInfo(addCustomerInfoModel);
        }
    }

    @Override
    public void getBaseInfoFromShared()
    {
        mModel.getBaseInfoFromShared();
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
    public void onGetConfig(boolean requireCodeMeli, boolean requireMobile, boolean requireMasahat)
    {
        mView.get().onGetConfig(requireCodeMeli, requireMobile, requireMasahat);
        int resIdHintCodeMeli = requireCodeMeli ? R.string.nationalCodeWithStar : R.string.nationalCode;
        int resIdHintMobile = requireMobile ? R.string.mobileWithStar : R.string.mobile;
        int resIdHintMasahat = requireMasahat ? R.string.masahateMaghazaehWithStar : R.string.masahateMaghazaeh;
        mView.get().showCodeMeliHint(resIdHintCodeMeli);
        mView.get().showMobileHint(resIdHintMobile);
        mView.get().showMasahateMaghazeHint(resIdHintMasahat);
    }
	
    @Override
    public void onGetAnbarItems(ArrayList<Integer> itemIds, ArrayList<String> itemTitles)
    {
        mView.get().onGetAnbarItems(itemIds , itemTitles);
    }

    @Override
    public void onGetRotbeItems(ArrayList<Integer> itemsId , ArrayList<String> itemTitle)
    {
        mView.get().onGetRotbeItems(itemsId , itemTitle);
    }

    @Override
    public void onGetNoeShakhsiatItems(ArrayList<Integer> itemIds, ArrayList<String> itemTitles)
    {
        mView.get().onGetNoeShakhsiatItems(itemIds , itemTitles);
    }

    @Override
    public void onGetNoeFaaliatItems(ArrayList<GorohModel> noeFaaliats)
    {
        mView.get().onGetNoeFaaliatItems(noeFaaliats);
    }

    @Override
    public void onGetNoeSenfItems(ArrayList<GorohModel> noeSenfs)
    {
        mView.get().onGetNoeSenfItems(noeSenfs);
    }

    @Override
    public void onGetNoeHaml(ArrayList<Integer> itemIds, ArrayList<String> itemTitle)
    {
        mView.get().onGetNoeHamlItems(itemIds , itemTitle);
    }

    @Override
    public void onGetNoeVosol(ArrayList<Integer> itemIds, ArrayList<String> itemTitle)
    {
        mView.get().onGetNoeVosolItems(itemIds , itemTitle);
    }

    @Override
    public void onSuccessfullySavedCustomerBaseInfo()
    {
        mView.get().showToast(R.string.dataCorrect, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onFailedSaveCustomerBaseInfo()
    {
        mView.get().showResourceError(false, R.string.error, R.string.errorSaveData, Constants.FAILED_MESSAGE(), R.string.apply);
    }

    @Override
    public void onGetBaseInfoFromShared(AddCustomerInfoModel addCustomerInfoModel)
    {
        mView.get().onGetBaseInfoFromShared(addCustomerInfoModel);
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }



}
