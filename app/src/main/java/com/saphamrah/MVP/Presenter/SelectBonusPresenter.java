package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.SelectBonusMVP;
import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.MVP.Model.SelectBonusModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.UIModel.JayezehEntekhabiMojodiModel;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectBonusPresenter implements SelectBonusMVP.PresenterOps , SelectBonusMVP.RequiredPresenterOps
{

    private WeakReference<SelectBonusMVP.RequiredViewOps> mView;
    private SelectBonusMVP.ModelOps mModel;
    //private boolean mIsChangingConfig;

    public SelectBonusPresenter(SelectBonusMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new SelectBonusModel(this);
    }



    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(SelectBonusMVP.RequiredViewOps view)
    {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void getBonus()
    {
        mModel.getBonus();
    }

    @Override
    public void checkBeforeSelectBonus(int ccTakhfif)
    {
        mView.get().openSpinnerSelectBonus();
    }

    @Override
    public void getKalaForJayezeh(int ccJayezehTakhfif, int ccJayezehSatr, int noeJayezehTakhfif)
    {
        mModel.getKalaForJayezeh(ccJayezehTakhfif, ccJayezehSatr, noeJayezehTakhfif);
    }

    @Override
    public void calculateMablaghJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , String mablaghTakhfif, int noeJayezehTakhfif)
    {
        Log.d("bonus" , "mablaghTakhfif : " + mablaghTakhfif);
        float mablaghJayezeh = 0;
        float fltMablaghTakhfif = 0;
        float fltMandeh = 0;
        for (JayezehEntekhabiMojodiModel model : jayezehEntekhabiMojodiModels)
        {
            if (model.getSelectedCount() > 0)
            {
                mablaghJayezeh += model.getSelectedCount() * model.getGheymatForosh();
            }
        }
        Log.d("bonus" , "mablaghJayezeh : " + mablaghJayezeh + ", mablaghTakhfif: " + mablaghTakhfif);
        try
        {
            fltMablaghTakhfif = Float.parseFloat(mablaghTakhfif.trim().replace("," , ""));
            fltMandeh = fltMablaghTakhfif - mablaghJayezeh;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "SelectBonusModel", "", "calculateMablaghJayezeh", "");
        }
        mView.get().onCalculateMablaghJayezeh(mablaghJayezeh , fltMandeh, noeJayezehTakhfif);
    }




    @Override
    public void checkInsert(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, String mablaghTakhfif, String mablaghJayezeh, String mandeh, String maxTedadJayeze, ArrayList<KalaMojodiModel> kalaMojodiModelsMaxShomarehBach , ArrayList<KalaMojodiModel> kalaMojodiModelsMaxMojodi)
    {
        try
        {
            boolean hasError = false;
            int sumTedadJavayezEntekhabi = 0;
            int sumSelectedCount = 0;
            int intMaxTedadJayezeh = Integer.parseInt(maxTedadJayeze.trim().replace(","  , ""));
            float fltMandeh = 0.0F;
            float fltMablaghJayezeh = 0.0F;
            float fltMablaghTakhfif = 0.0F;
            int TedadSefarshDarkhast=0;
            if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif())
            {
                try
                {
                    fltMandeh = Float.parseFloat(mandeh.trim().replace(","  , ""));
                    fltMablaghJayezeh = Float.parseFloat(mablaghJayezeh.trim().replace(","  , ""));
                    fltMablaghTakhfif = Float.parseFloat(mablaghTakhfif.trim().replace(","  , ""));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            Log.d("bonus" , "intMaxTedadJayezeh : " + intMaxTedadJayezeh);
            Map<Integer,Integer> mapKalaCount = new HashMap<>();
                for (int i = 0; i < jayezehEntekhabiMojodiModels.size(); i++) {
                    JayezehEntekhabiMojodiModel model = jayezehEntekhabiMojodiModels.get(i);
                Log.d("bonus" , "model : " + model.toString());

                TedadSefarshDarkhast = model.getMax_MojodyByShomarehBach()-model.getTedad();

                sumTedadJavayezEntekhabi += model.getTedad();
                sumSelectedCount += model.getSelectedCount();

                Integer countCcKalaCode = mapKalaCount.get(model.getCcKalaCode());
                countCcKalaCode = countCcKalaCode == null ? 0 : countCcKalaCode;
                countCcKalaCode += model.getSelectedCount();
                mapKalaCount.put(model.getCcKalaCode() , countCcKalaCode);

                int maxMojodi = 0;

                    for (int j = 0; j <kalaMojodiModelsMaxMojodi.size() ; j++) {
                       if (jayezehEntekhabiMojodiModels.get(i).getCcKala() == kalaMojodiModelsMaxMojodi.get(j).getCcKalaCode()){
                           maxMojodi = kalaMojodiModelsMaxMojodi.get(j).getMax_Mojody();
                       }
                    }

                    int maxShomarehBach = 0;
                    for (int j = 0; j <kalaMojodiModelsMaxShomarehBach.size() ; j++) {
                        if (jayezehEntekhabiMojodiModels.get(i).getCcKala() == kalaMojodiModelsMaxShomarehBach.get(j).getCcKalaCode()){
                            if (jayezehEntekhabiMojodiModels.get(i).getShomarehBach().equals(kalaMojodiModelsMaxShomarehBach.get(j).getShomarehBach()))
                            if (jayezehEntekhabiMojodiModels.get(i).getGheymatForosh() == kalaMojodiModelsMaxShomarehBach.get(j).getGheymatForosh())
                            if (jayezehEntekhabiMojodiModels.get(i).getGheymatMasrafKonandeh() == kalaMojodiModelsMaxShomarehBach.get(j).getGheymatMasrafKonandeh())
                            if (jayezehEntekhabiMojodiModels.get(i).getCcTaminKonandeh() == kalaMojodiModelsMaxShomarehBach.get(j).getCcTaminKonandeh())
                                maxShomarehBach = kalaMojodiModelsMaxShomarehBach.get(j).getMax_MojodyByShomarehBach();
                        }
                    }

                Log.d("bonus", "selectedCount : " + model.getSelectedCount() + " , Max_MojodyByShomarehBach : " + maxShomarehBach + " , getMax_Mojody : " + maxMojodi + " , countCcKalaCode : " + countCcKalaCode + ", TedadSefareshDarkhast:" + TedadSefarshDarkhast);
              //mView.get().toastTest(model.getSelectedCount() , maxShomarehBach , maxMojodi,0);
              if (model.getSelectedCount() > model.getTedad() || model.getSelectedCount() > maxMojodi || model.getSelectedCount() > maxShomarehBach)
                {
                    mView.get().onErrorInsert(R.string.errorSelectedBiggerMojodi , model.getNameKala());
                    hasError = true;
                    break;
                }
            }

            if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeJayezeh())
            {
                if (sumSelectedCount > intMaxTedadJayezeh)
                {
                    mView.get().onErrorInsert(R.string.errorSelectedCountJayezehBigger , "");
                    hasError = true;
                }
                else if (sumSelectedCount < intMaxTedadJayezeh)
                {
                    mView.get().onErrorInsert(R.string.errorSelectedCountLessThanMaxJayezeh , "");
                    hasError = true;
                }
            }

            if (!hasError)
            {
                boolean insertTakhfifNaghdi = false;
                if (fltMandeh < 0)
                {
                    mView.get().onErrorInsert(R.string.errorNegativeRemain , "");
                    return;
                }
                if (fltMablaghJayezeh > fltMablaghTakhfif)
                {
                    mView.get().onErrorInsert(R.string.errorSelectedMablaghJayezehBigger , "");
                    return;
                }

                Log.d("bonus" , "noeJayezehTakhfif : " + noeJayezehTakhfif);
                if (intMaxTedadJayezeh < sumTedadJavayezEntekhabi)
                {
                    Log.d("bonus" , "in if : " + " , intMaxTedadJayezeh : " + intMaxTedadJayezeh + " , sumTedadJavayezEntekhabi : " + sumTedadJavayezEntekhabi);
                    if (fltMandeh >= jayezehEntekhabiMojodiModels.get(0).getMablaghForosh())
                    {
                        mView.get().onErrorInsert(R.string.errorRemainBiggerThanCost , "");
                        return;
                    }
                    else
                    {
                        if (fltMandeh > 0 && noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif())
                        {
                            insertTakhfifNaghdi = true;
                        }
                        Log.d("bonus" , "insertTakhfifNaghdi 1 : " + insertTakhfifNaghdi);
                        mModel.insert(noeJayezehTakhfif, jayezehEntekhabiMojodiModels, darkhastFaktorJayezehTakhfifModel, selectedccTakhfif, fltMablaghTakhfif, fltMablaghJayezeh, fltMandeh, intMaxTedadJayezeh, insertTakhfifNaghdi);
                    }
                }
                else
                {
                    if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif())
                    {
                        insertTakhfifNaghdi = true;
                    }
                    Log.d("bonus" , "insertTakhfifNaghdi 2 : " + insertTakhfifNaghdi);
                    mModel.insert(noeJayezehTakhfif, jayezehEntekhabiMojodiModels, darkhastFaktorJayezehTakhfifModel, selectedccTakhfif, fltMablaghTakhfif, fltMablaghJayezeh, fltMandeh, intMaxTedadJayezeh, insertTakhfifNaghdi);
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mModel.setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "SelectBonusPresenter", "", "checkInsert", "");
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
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////

    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onError(int resId)
    {
        mView.get().showToast(resId, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetBonus(ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels)
    {
        if (darkhastFaktorJayezehTakhfifModels.size() > 0)
        {
            mView.get().setBonus(darkhastFaktorJayezehTakhfifModels);
        }
        else
        {
            mView.get().onEmptyGoodsHaveBonus();
        }
    }

    @Override
    public void onGetKalaForJayezeh(ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach ,
                                    ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi, int noeJayezehTakhfif)
    {
        mView.get().onGetKalaForJayezeh(jayezehEntekhabiMojodiModels,KalaMojodiModelsMaxShomarehBach,KalaMojodiModelsMaxMojodi, noeJayezehTakhfif);
        /*if (jayezehEntekhabiMojodiModels.size() > 0)
        {

        }
        else
        {
            mView.get().showToast(R.string.emptyList, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
        }*/
    }

    @Override
    public void onSuccessInsert()
    {
        mView.get().onSuccessInsert();
    }

    @Override
    public void onFailedInsert()
    {
        mView.get().showToast(R.string.errorOperation, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }
}
