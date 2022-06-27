package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.MojodiGiriMVP;

import com.saphamrah.DAO.AmvalDAO;
import com.saphamrah.DAO.MojoodiGiriDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.MVP.Model.MojodiGiriModel;
											 
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.KalaModel;
											
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.KalaFilterUiModel;
import com.saphamrah.UIModel.KalaMojodiGiriModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
								  
import java.util.ArrayList;

public class MojodiGiriPresenter implements MojodiGiriMVP.PresenterOps , MojodiGiriMVP.RequiredPresenterOps
{

    private WeakReference<MojodiGiriMVP.RequiredViewOps> mView;
    private MojodiGiriMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public MojodiGiriPresenter(MojodiGiriMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new MojodiGiriModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(MojodiGiriMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

	@Override
    public void updateHaveMojodiGiri()
    {
        mModel.updateHaveMojodiGiri();
    }

    @Override
    public void getNoeMasouliat()
    {
        mModel.getNoeMasouliat();
    }
	
    @Override
    public void checkBottomBarClick(int position) {
        PubFunc.RequestBottomBarConfig bottomBarConfig = new PubFunc().new RequestBottomBarConfig();
        bottomBarConfig.getConfig(getAppContext());

        if (position == 0)
        {
            if (bottomBarConfig.getShowBarkhordAvalie())
            {
                mView.get().openBarkhordAvalieActivity();
            }
            else
            {
                mView.get().showToast(R.string.errorDisableThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            }
        }
        else if (position == 2)
        {
            SelectFaktorShared shared = new SelectFaktorShared(getAppContext());
            MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(getAppContext());
            AmvalDAO amvalDAO = new AmvalDAO(getAppContext());
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(getAppContext());

            int ccMoshtary = shared.getInt(shared.getCcMoshtary() , -1);
            int count = mojoodiGiriDAO.getCountMojodiGiriByMoshtaryForCheck(ccMoshtary, true);
            boolean haveMojoodiGiri = count != 0;


            ParameterChildDAO parameterChildDAO = new ParameterChildDAO(getAppContext());
            boolean requireCheckAmvalMoshtary = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_CheckAmvalMoshtary).trim().equals("1");

            boolean hasAmval = moshtaryDAO.getHasAmval(ccMoshtary);
            boolean CheckSabtAmval = amvalDAO.getCheckAllSabtAmvallByccMoshtary(ccMoshtary);

            Log.d("MojodiGiriPresenter", "requireCheckAmvalMoshtary:" + requireCheckAmvalMoshtary + " ,hasAmval:" + hasAmval + " , CheckSabtAmval:"+ CheckSabtAmval);
            if (bottomBarConfig.getForceMojoodiGiri() && !haveMojoodiGiri)
                mView.get().showToast(R.string.forceMojodigiri, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
            else if (requireCheckAmvalMoshtary && hasAmval)
                if (!CheckSabtAmval)
                    mView.get().showToast(R.string.forceSabtAmval, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                else
                    mView.get().openDarkhastActivity();
            else
                mView.get().openDarkhastActivity();
        }
        else if (position == 3 || position == 4 || position == 5)
        {
            mView.get().showToast(R.string.errorCantSelectThisItem, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
    }

    @Override
    public void getKala(int ccMoshtary)
    {
        mModel.getKala(ccMoshtary);
    }

    @Override
    public void getInsertedKalaMojodi(int ccMoshtary)
    {
        mModel.getAllInsertedKalaMojodi(ccMoshtary);
    }

    @Override
    public void searchInsertedKalaMojodi(String searchWord, ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels)
    {
        ArrayList<KalaMojodiGiriModel> arrayList = new ArrayList<>();
        for (KalaMojodiGiriModel kalaMojodiGiriModel : kalaMojodiGiriModels)
        {
            if (kalaMojodiGiriModel.getNameKala().contains(searchWord))
            {
                arrayList.add(kalaMojodiGiriModel);
            }
        }
        mView.get().onSearchInsertedKalaMojodi(arrayList);
    }


    @Override
    public void checkAddNewKala(ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels , ArrayList<KalaModel> selectedKalaModel , int ccMoshtary , String countMojodi)
    {
        if (countMojodi.trim().equals(""))
        {
            mView.get().onErrorInputCountForAddNewKala(R.string.errorInputCount);
        }
        else
        {
            try
            {
                int intCountMojodi = Integer.parseInt(countMojodi);
                if (intCountMojodi <= 0)
                {
                    mView.get().onErrorInputCountForAddNewKala(R.string.errorBiggerThanZero);
                }
                else
                {
                    boolean duplicate = false;
                    int ccKalaCode = selectedKalaModel.get(0).getCcKalaCode();
                    for (KalaMojodiGiriModel kalaMojodiGiriModel : kalaMojodiGiriModels)
                    {
                        if (kalaMojodiGiriModel.getCcKalaCode() == ccKalaCode)
                        {
                            duplicate = true;
                            mView.get().onErrorForAddNewKala(R.string.errorDuplicateKala);
                            break;
                        }
                    }

                    if (!duplicate)
                    {
                        mModel.addNewKala(ccMoshtary, ccKalaCode, Float.parseFloat(countMojodi));
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                mView.get().onErrorInputCountForAddNewKala(R.string.errorInputCount);
            }
        }
    }

    @Override
    public void checkRemoveKalaMojodiGiri(int ccKalaMojodiGiri, int position)
    {
        if (ccKalaMojodiGiri > 0)
        {
            mModel.removeKalaMojodiGiri(ccKalaMojodiGiri, position);
        }
        else
        {
            mView.get().showToast(R.string.errorSelectItem , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        }
    }

    @Override
    public void getElatAdamDarkhast(int ccMoshtary)
    {
        mModel.getElatAdamDarkhast(ccMoshtary);
    }

    @Override
    public void checkSeletedAdamDarkhastItem(int ccMoshtary , ElatAdamDarkhastModel elatAdamDarkhastModel)
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
            checkAdamDarkhastForInsert(ccMoshtary, elatAdamDarkhastModel, null , "");
        }
    }

    @Override
    public void checkAdamDarkhastForInsert(int ccMoshtary , ElatAdamDarkhastModel elatAdamDarkhastModel, byte[] imageAdamDarkhast, String codeMoshtaryTekrari)
    {
        if (elatAdamDarkhastModel == null)
        {
            mView.get().showToast(R.string.errorSelectElatAdamDarkhast, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }
        else
        {
            if (elatAdamDarkhastModel.getGetImage() == 1)
            {
                if (imageAdamDarkhast != null && imageAdamDarkhast.length > 0)
                {
                    mModel.insertAdamDarkhast(ccMoshtary, elatAdamDarkhastModel.getCcElatAdamDarkhast(), imageAdamDarkhast, codeMoshtaryTekrari);
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
                    mModel.insertAdamDarkhast(ccMoshtary, elatAdamDarkhastModel.getCcElatAdamDarkhast(), imageAdamDarkhast, codeMoshtaryTekrari);
                }
                else
                {
                    mView.get().showToast(R.string.errorCustomerDuplicatedCode, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                }
            }
            else
            {
                mModel.insertAdamDarkhast(ccMoshtary, elatAdamDarkhastModel.getCcElatAdamDarkhast(), imageAdamDarkhast, codeMoshtaryTekrari);
            }
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
    {

    }

    @Override
    public void getKalaFilter() {
        mModel.getKalaFilter();
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
        if (noeMasouliat != 1 && noeMasouliat != 2 && noeMasouliat != 3 && noeMasouliat != 6 && noeMasouliat != 8) {
            mView.get().hideNoRequestButton();
            mView.get().hideSabtAmvalButton();
        }
    }
	
    @Override
    public void onGetKala(ArrayList<KalaModel> kalaModels)
    {
        if (kalaModels.size() > 0)
        {
            mView.get().onGetKala(kalaModels);
        }
        else
        {
            mView.get().showToast(R.string.errorGetDataAndGetProgram , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
        }
    }

    @Override
    public void onGetInsertedKalaMojodi(ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels)
    {
        mView.get().onGetInsertedKalaMojodi(kalaMojodiGiriModels);
    }

    @Override
    public void onSuccessAddNewKala()
    {
        mView.get().onSuccessAddNewKala();
    }

    @Override
    public void onErrorAddNewKala()
    {
        mView.get().showToast(R.string.errorOperation , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onSuccessRemoveKalaMojodiGiri(int position)
    {
        mView.get().onSuccessRemoveKalaMojodiGiri(position);
    }

    @Override
    public void onFailedRemoveKalaMojodiGiri()
    {
        mView.get().showToast(R.string.errorOperation , Constants.FAILED_MESSAGE() , Constants.DURATION_LONG());
    }

    @Override
    public void onGetElatAdamDarkhast(ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels)
    {
        ArrayList<String> elatAdamDarkhastTitles = new ArrayList<>();
        for (ElatAdamDarkhastModel model : elatAdamDarkhastModels)
        {
            elatAdamDarkhastTitles.add(model.getNameElatAdamDarkhast());
        }
        mView.get().onGetElatAdamDarkhast(elatAdamDarkhastModels , elatAdamDarkhastTitles);
    }

    @Override
    public void onSuccessInsertAdamDarkhast()
    {
        mView.get().onSuccessInsertAdamDarkhast();
    }

    @Override
    public void onFailedInsertAdamDarkhast()
    {
        mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }


    @Override
    public void onErrorAccessToLocation()
    {
        mView.get().showToast(R.string.errorAccessToLocation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onKalaFilter(ArrayList<KalaFilterUiModel> kalaFilterUiModels) {
        ArrayList<String> itemsKalaFilter = new ArrayList<>();
        for (KalaFilterUiModel model : kalaFilterUiModels){
            itemsKalaFilter.add(model.getNameGoroh());
        }

        mView.get().onKalaFilter(kalaFilterUiModels,itemsKalaFilter);
    }
}
