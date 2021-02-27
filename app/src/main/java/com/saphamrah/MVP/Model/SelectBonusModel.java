package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.SelectBonusMVP;
import com.saphamrah.DAO.DarkhastFaktorJayezehDAO;
import com.saphamrah.DAO.DarkhastFaktorJayezehTakhfifDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.DarkhastFaktorTakhfifDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.JayezehEntekhabiMojodiDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.TakhfifNaghdyDAO;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.UIModel.JayezehEntekhabiMojodiModel;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SelectBonusModel implements SelectBonusMVP.ModelOps
{

    private SelectBonusMVP.RequiredPresenterOps mPresenter;

    public SelectBonusModel(SelectBonusMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getBonus()
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        if (ccDarkhastFaktor == -1)
        {
            mPresenter.onError(R.string.errorFindccDarkhastFaktor);
        }
        else
        {
            String ccTakhfifs = selectFaktorShared.getString(selectFaktorShared.getCcTakhfifJayezes() , "");
            DarkhastFaktorJayezehTakhfifDAO darkhastFaktorJayezehTakhfifDAO = new DarkhastFaktorJayezehTakhfifDAO(mPresenter.getAppContext());
            ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels = darkhastFaktorJayezehTakhfifDAO.getByccDarkhastFaktorccTakhfif(ccDarkhastFaktor , ccTakhfifs);
            Log.d("bonus" , "darkhastFaktorJayezehTakhfif size : " + darkhastFaktorJayezehTakhfifModels.size());
            mPresenter.onGetBonus(darkhastFaktorJayezehTakhfifModels);
        }
    }


    @Override
    public void getKalaForJayezeh(int ccJayezehTakhfif , int ccJayezehSatr, int noeJayezehTakhfif)
    {
        Log.d("bonus" , "noeJayezehTakhfif : " + noeJayezehTakhfif);
        Log.d("bonus" , "ccJayezehTakhfif : " + ccJayezehTakhfif);
        if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif())
        {
            JayezehEntekhabiMojodiDAO jayezehEntekhabiMojodiDAO = new JayezehEntekhabiMojodiDAO(mPresenter.getAppContext());
            ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = jayezehEntekhabiMojodiDAO.getByccTakhfifHajmi(ccJayezehTakhfif);
            Log.d("bouns", "jayezehEntekhabiMojodiModels Takhfif:" + jayezehEntekhabiMojodiModels);
            mPresenter.onGetKalaForJayezeh(jayezehEntekhabiMojodiModels , noeJayezehTakhfif);
        }
        else if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeJayezeh())
        {
            JayezehEntekhabiMojodiDAO jayezehEntekhabiMojodiDAO = new JayezehEntekhabiMojodiDAO(mPresenter.getAppContext());
            ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = jayezehEntekhabiMojodiDAO.getByccJayezeh(ccJayezehTakhfif, ccJayezehSatr);
            Log.d("bouns", "jayezehEntekhabiMojodiModels Jayezeh:" + jayezehEntekhabiMojodiModels);
            mPresenter.onGetKalaForJayezeh(jayezehEntekhabiMojodiModels , noeJayezehTakhfif);
        }

        /*for (JayezehEntekhabiMojodiModel model : jayezehEntekhabiMojodiModels)
        {
            ccKalaCodes = ccKalaCodes + "," + model.getCcKalaCode();
        }
        if (ccKalaCodes.startsWith(","))
        {
            ccKalaCodes = ccKalaCodes.substring(1);
        }
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = darkhastFaktorJayezehDAO.getByccKalaCodesAndccDarkhastFaktor(ccKalaCodes , ccDarkhastFaktor);
        Log.d("jayeze" , "ccdarkhastFaktor : " + ccDarkhastFaktor + " ccKala : " + ccKalaCodes + " , size : " + darkhastFaktorJayezehModels.size());
        for (DarkhastFaktorJayezehModel model : darkhastFaktorJayezehModels)
        {
            for (JayezehEntekhabiMojodiModel mojodiModel : jayezehEntekhabiMojodiModels)
            {
                if (model.getCcKalaCode() == mojodiModel.getCcKalaCode())
                {
                    mojodiModel.setSelectedCount(mojodiModel.getTedad());
                    break;
                }
            }
        }*/
    }

    @Override
    public void insert(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, double mablaghTakhfif, double mablaghJayezeh, double mandeh, int maxTedadJayeze, boolean insertTakhfifNaghdi)
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        int FinalCCAfrad = foroshandehMamorPakhshDAO.getIsSelect().getCcAfrad();
        Log.d("bonus" , "takhfifNaghdi : " + insertTakhfifNaghdi);
        Log.d("bonus" , "ccDarkhastFaktor : " + ccDarkhastFaktor);
        Log.d("bonus" , "FinalCCAfrad : " + FinalCCAfrad);
        if (insertTakhfifNaghdi)
        {
            int ccGorohNoeMoshatry = selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeMoshtary() , -1);
            String codeNoeTakhfif = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_NAGHDI());
            DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
            DarkhastFaktorTakhfifModel insertDarkhastFaktorTakhfifModel = new DarkhastFaktorTakhfifModel();
            insertDarkhastFaktorTakhfifModel.setCcTakhfif(new TakhfifNaghdyDAO(mPresenter.getAppContext()).getccTakhfifNaghdiByccGorohMoshtary(ccGorohNoeMoshatry));
            insertDarkhastFaktorTakhfifModel.setCcDarkhastFaktor(ccDarkhastFaktor);
            insertDarkhastFaktorTakhfifModel.setMablaghTakhfif(Math.round(mandeh));
            insertDarkhastFaktorTakhfifModel.setDarsadTakhfif((float) ((mandeh*100)/mablaghTakhfif));
            insertDarkhastFaktorTakhfifModel.setCodeNoeTakhfif(Integer.parseInt(codeNoeTakhfif));
            insertDarkhastFaktorTakhfifModel.setSharhTakhfif(mPresenter.getAppContext().getResources().getString(R.string.takhfifNaghdiforJayezeh));
            insertDarkhastFaktorTakhfifModel.setExtraProp_ForJayezeh(0);
            insertDarkhastFaktorTakhfifModel.setExtraProp_IsTakhfifMazad(1);
            insertDarkhastFaktorTakhfifModel.setExtraProp_MustSendToSql(1);
            insertDarkhastFaktorTakhfifModel.setExtraProp_ccJayezehTakhfif(selectedccTakhfif);
            darkhastFaktorTakhfifDAO.insert(insertDarkhastFaktorTakhfifModel);
        }
        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
        KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        int count = 0;
        int insertedCount = 0;
        for (JayezehEntekhabiMojodiModel model : jayezehEntekhabiMojodiModels)
        {
            if (model.getSelectedCount() > 0)
            {
                count++;
                DarkhastFaktorJayezehModel darkhastFaktorJayezehModel = new DarkhastFaktorJayezehModel();
                darkhastFaktorJayezehModel.setCcKalaCode(model.getCcKalaCode());
                darkhastFaktorJayezehModel.setCcKala(model.getCcKala());
                darkhastFaktorJayezehModel.setCcJayezeh(0);
                darkhastFaktorJayezehModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                darkhastFaktorJayezehModel.setTedad(model.getSelectedCount());
                darkhastFaktorJayezehModel.setSharh(model.getNameKala());
                darkhastFaktorJayezehModel.setExtraProp_IsJayezehEntekhabi(1);
                darkhastFaktorJayezehModel.setExtraProp_CodeNoeJayezeh(DarkhastFaktorJayezehModel.CodeNoeJayezehAuto());
                darkhastFaktorJayezehModel.setExtraProp_ccJayezehTakhfif(selectedccTakhfif);
                if (darkhastFaktorJayezehDAO.insert(darkhastFaktorJayezehModel))
                {
                    String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
                    KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();
                    kalaMojodiModel.setCcTaminKonandeh(model.getCcTaminKonandeh());
                    kalaMojodiModel.setTedad(-1 * model.getSelectedCount());
                    kalaMojodiModel.setTarikhDarkhast(currentDate);
                    kalaMojodiModel.setTarikhTolid(model.getTarikhTolid());
                    kalaMojodiModel.setZamaneSabt(model.getZamaneSabt());
                    kalaMojodiModel.setShomarehBach(model.getShomarehBach());
                    kalaMojodiModel.setGheymatMasrafKonandeh(model.getGheymatMasrafKonandeh());
                    kalaMojodiModel.setGheymatForosh(model.getGheymatForosh());
                    kalaMojodiModel.setCcKalaCode(model.getCcKalaCode());
                    kalaMojodiModel.setCcForoshandeh(selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh() , model.getCcForoshandeh()));
                    kalaMojodiModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                    kalaMojodiModel.setForJayezeh(1);
                    kalaMojodiModel.setZamaneSabt(currentDate);
                    kalaMojodiModel.setMax_Mojody(model.getMax_Mojody());
                    kalaMojodiModel.setMax_MojodyByShomarehBach(model.getMax_MojodyByShomarehBach());
                    kalaMojodiModel.setCcAfrad(FinalCCAfrad);
                    if (kalaMojodiDAO.insert(kalaMojodiModel))
                    {
                        Log.d("bonus" , "model.getGheymatForosh() : " + model.getGheymatForosh());
                        DarkhastFaktorSatrModel darkhastFaktorSatrModel = new DarkhastFaktorSatrModel();
                        darkhastFaktorSatrModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                        darkhastFaktorSatrModel.setCcTaminKonandeh(model.getCcTaminKonandeh());
                        darkhastFaktorSatrModel.setCcKala(model.getCcKalaCode());
                        darkhastFaktorSatrModel.setCcKalaCode(model.getCcKalaCode());
                        darkhastFaktorSatrModel.setTedad3(model.getSelectedCount());
                        darkhastFaktorSatrModel.setCodeNoeKala(2);
                        darkhastFaktorSatrModel.setShomarehBach(model.getShomarehBach());
                        darkhastFaktorSatrModel.setTarikhTolid(model.getTarikhTolid());
                        darkhastFaktorSatrModel.setTarikhEngheza(new KalaDAO(mPresenter.getAppContext()).getByccKalaCode(model.getCcKalaCode()).getTarikhEngheza());
                        darkhastFaktorSatrModel.setMablaghForosh(1);
                        darkhastFaktorSatrModel.setMablaghForoshKhalesKala(model.getGheymatForosh());
                        Log.d("bonus" , "model.setMablaghForoshKhalesKala() : " + darkhastFaktorSatrModel.getMablaghForoshKhalesKala());
                        darkhastFaktorSatrModel.setMablaghTakhfifNaghdiVahed(0);
                        darkhastFaktorSatrModel.setMaliat(0);
                        darkhastFaktorSatrModel.setAvarez(0);
                        darkhastFaktorSatrModel.setCcAfrad(0);
                        darkhastFaktorSatrModel.setExtraProp_IsOld(false);
                        darkhastFaktorSatrModel.setGheymatMasrafKonandeh(model.getGheymatMasrafKonandeh());
                        darkhastFaktorSatrModel.setGheymatForoshAsli(model.getGheymatForosh());
                        if (darkhastFaktorSatrDAO.insert(darkhastFaktorSatrModel))
                        {
                            insertedCount++;
                        }
                    }
                }
            }
        }

        if (insertedCount == count)
        {
            if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif())
            {
                if (removeTakhfif(selectFaktorShared, selectedccTakhfif))
                {
                    mPresenter.onSuccessInsert();
                }
                else
                {
                    mPresenter.onFailedInsert();
                }
            }
            else if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeJayezeh())
            {
                if (removeJayezeh(darkhastFaktorJayezehTakhfifModel.getCcDarkhastFaktor(), darkhastFaktorJayezehTakhfifModel.getCcJayezehTakhfif()))
                {
                    mPresenter.onSuccessInsert();
                }
                else
                {
                    mPresenter.onFailedInsert();
                }
            }
        }
        else
        {
            mPresenter.onFailedInsert();
        }
    }


    private boolean removeTakhfif(SelectFaktorShared selectFaktorShared, int selectedccTakhfif)
    {
        try
        {
            String ccTakhfifs = selectFaktorShared.getString(selectFaktorShared.getCcTakhfifJayezes() , "");
            Log.d("takhfif" , "ccTakhfifs in insert bonus : " + ccTakhfifs);
            if (ccTakhfifs.replace("," , "").length() > 0)
            {
                ArrayList<String> splittedccTakhfif = new ArrayList<>(Arrays.asList(ccTakhfifs.split(",")));
                if (splittedccTakhfif.size() == 0)
                {
                    //in this case, shared have only one cc and we add this cc to array
                    splittedccTakhfif.add(ccTakhfifs);
                }
                for (int i = 0 ; i < splittedccTakhfif.size() ; i++)
                {
                    if (splittedccTakhfif.get(i).equals(String.valueOf(selectedccTakhfif)))
                    {
                        splittedccTakhfif.remove(i);
                        break;
                    }
                }
                ccTakhfifs = "";
                if (splittedccTakhfif.size() > 0)
                {
                    for (String str : splittedccTakhfif)
                    {
                        ccTakhfifs += str + ",";
                    }
                    if (ccTakhfifs.endsWith(","))
                    {
                        ccTakhfifs = ccTakhfifs.substring(0 , ccTakhfifs.length() -1);
                    }
                }
                Log.d("takhfif" , "ccTakhfifs after delete selected : " + ccTakhfifs);
                selectFaktorShared.putString(selectFaktorShared.getCcTakhfifJayezes() , ccTakhfifs);
            }
            return true;
        }
        catch (Exception e)
        {
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SelectBonusModel", "", "removeTakhfif", "");
            return false;
        }
    }


    private boolean removeJayezeh(long ccDarkhastFaktor , int ccJayezeh)
    {
        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
        return darkhastFaktorJayezehDAO.deleteByccDarkhastFaktorAndccJayeze(ccDarkhastFaktor, ccJayezeh);
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy()
    {

    }

}
