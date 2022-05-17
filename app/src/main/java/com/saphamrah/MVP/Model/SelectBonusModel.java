package com.saphamrah.MVP.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.SelectBonusMVP;
import com.saphamrah.DAO.DarkhastFaktorJayezehDAO;
import com.saphamrah.DAO.DarkhastFaktorJayezehTakhfifDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.DarkhastFaktorTakhfifDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.JayezehEntekhabiMojodiDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.DAO.KalaMojodiZaribForoshDAO;
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
import com.saphamrah.UIModel.KalaMojodiZaribModel;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectBonusModel implements SelectBonusMVP.ModelOps {
    CompositeDisposable compositeDisposable;
    private SelectBonusMVP.RequiredPresenterOps mPresenter;

    public SelectBonusModel(SelectBonusMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        compositeDisposable=new CompositeDisposable();
    }

    @Override
    public void getBonus() {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(), -1);

        if (ccDarkhastFaktor == -1) {
            mPresenter.onError(R.string.errorFindccDarkhastFaktor);
        } else {
            String ccTakhfifs = selectFaktorShared.getString(selectFaktorShared.getCcTakhfifJayezes(), "");
            Log.d("bonus", "Jayezeh getBonus ccDarkhastFaktor : " + ccDarkhastFaktor + " ccTakhfifs:" +ccTakhfifs);

            DarkhastFaktorJayezehTakhfifDAO darkhastFaktorJayezehTakhfifDAO = new DarkhastFaktorJayezehTakhfifDAO(mPresenter.getAppContext());
            ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels = darkhastFaktorJayezehTakhfifDAO.getByccDarkhastFaktorccTakhfif(ccDarkhastFaktor, ccTakhfifs);
            Log.d("bonus", "Jayezeh darkhastFaktorJayezehTakhfif size : " + darkhastFaktorJayezehTakhfifModels.size());



            mPresenter.onGetBonus(darkhastFaktorJayezehTakhfifModels);
        }
    }


    @Override
    public void getKalaForJayezeh(int ccJayezehTakhfif, int ccJayezehSatr, int noeJayezehTakhfif) {
        KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(BaseApplication.getContext());
        ArrayList<KalaMojodiModel> KalaMojodiModelsMaxShomarehBach = new ArrayList<>();
        ArrayList<KalaMojodiModel> KalaMojodiModelsMaxMojodi = new ArrayList<>();
        Log.d("bonus", "Jayezeh noeJayezehTakhfif : " + noeJayezehTakhfif);
        Log.d("bonus", "Jayezeh ccJayezehTakhfif : " + ccJayezehTakhfif);
        if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif()) {
            JayezehEntekhabiMojodiDAO jayezehEntekhabiMojodiDAO = new JayezehEntekhabiMojodiDAO(mPresenter.getAppContext());
            ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = jayezehEntekhabiMojodiDAO.getByccTakhfifHajmi(ccJayezehTakhfif);
            Log.d("bouns", "Jayezeh jayezehEntekhabiMojodiModels Takhfif:" + jayezehEntekhabiMojodiModels);

            /**
             * get max mojodi and max shomarehbach
             */
            KalaMojodiModelsMaxShomarehBach = kalaMojodiDAO.getMaxShomarehBach(jayezehEntekhabiMojodiModels.get(0).getCcJayezeh(), jayezehEntekhabiMojodiModels.get(0).getCcJayezehSatr());
            KalaMojodiModelsMaxMojodi = kalaMojodiDAO.getMaxMojodi(jayezehEntekhabiMojodiModels.get(0).getCcJayezeh(), jayezehEntekhabiMojodiModels.get(0).getCcJayezehSatr());
            Log.d("bouns", "Jayezeh takhfif KalaMojodiModelsMaxShomarehBach:" + KalaMojodiModelsMaxShomarehBach.toString());
            Log.d("bouns", "Jayezeh takhfif KalaMojodiModelsMaxMojodi:" + KalaMojodiModelsMaxMojodi.toString());

            mPresenter.onGetKalaForJayezeh(jayezehEntekhabiMojodiModels, KalaMojodiModelsMaxShomarehBach, KalaMojodiModelsMaxMojodi,noeJayezehTakhfif);
        } else if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeJayezeh()) {
            JayezehEntekhabiMojodiDAO jayezehEntekhabiMojodiDAO = new JayezehEntekhabiMojodiDAO(mPresenter.getAppContext());
            ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = jayezehEntekhabiMojodiDAO.getByccJayezeh(ccJayezehTakhfif, ccJayezehSatr);
            Log.d("bouns", "Jayezeh jayezehEntekhabiMojodiModels Jayezeh:" + jayezehEntekhabiMojodiModels);


            /**
             * get max mojodi and max shomarehbach
             */
            KalaMojodiModelsMaxShomarehBach = kalaMojodiDAO.getMaxShomarehBach(ccJayezehTakhfif, ccJayezehSatr);
            KalaMojodiModelsMaxMojodi = kalaMojodiDAO.getMaxMojodi(ccJayezehTakhfif, ccJayezehSatr);

            Log.d("bouns", "Jayezeh KalaMojodiModelsMaxShomarehBach:" + KalaMojodiModelsMaxShomarehBach.toString());
            Log.d("bouns", "Jayezeh KalaMojodiModelsMaxMojodi:" + KalaMojodiModelsMaxMojodi.toString());

            mPresenter.onGetKalaForJayezeh(jayezehEntekhabiMojodiModels, KalaMojodiModelsMaxShomarehBach, KalaMojodiModelsMaxMojodi,noeJayezehTakhfif);

        }else if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh()){
            JayezehEntekhabiMojodiDAO jayezehEntekhabiMojodiDAO = new JayezehEntekhabiMojodiDAO(mPresenter.getAppContext());
            ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels = jayezehEntekhabiMojodiDAO.getByccJayezehForArzeshAfzodeh(ccJayezehTakhfif, ccJayezehSatr);
            Log.d("bouns", "Jayezeh jayezehEntekhabiMojodiModels Takhfif:" + jayezehEntekhabiMojodiModels);
            mPresenter.onGetKalaForJayezeh(jayezehEntekhabiMojodiModels, KalaMojodiModelsMaxShomarehBach, KalaMojodiModelsMaxMojodi,noeJayezehTakhfif);

            /**
             * get max mojodi and max shomarehbach
             */
            KalaMojodiModelsMaxShomarehBach = kalaMojodiDAO.getMaxShomarehBach(ccJayezehTakhfif, ccJayezehSatr);
            KalaMojodiModelsMaxMojodi = kalaMojodiDAO.getMaxMojodi(ccJayezehTakhfif, ccJayezehSatr);


            mPresenter.onGetKalaForJayezeh(jayezehEntekhabiMojodiModels, KalaMojodiModelsMaxShomarehBach, KalaMojodiModelsMaxMojodi,noeJayezehTakhfif);
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
    public void insert(int noeJayezehTakhfif, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels, DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel, int selectedccTakhfif, double mablaghTakhfif, double mablaghJayezeh, double mandeh, int maxTedadJayeze, boolean insertTakhfifNaghdi) {

        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(), -1);
        int  ccForoshandeh = selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh(), -1);
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        int FinalCCAfrad = foroshandehMamorPakhshDAO.getIsSelect().getCcAfrad();
        Log.d("bonus", "Jayezeh takhfifNaghdi : " + insertTakhfifNaghdi);
        Log.d("bonus", "Jayezeh noeJayezehTakhfif : " + noeJayezehTakhfif);
        Log.d("bonus", "Jayezeh ccDarkhastFaktor : " + ccDarkhastFaktor);
        Log.d("bonus", "Jayezeh FinalCCAfrad : " + FinalCCAfrad);
        if (insertTakhfifNaghdi) {
            int ccGorohNoeMoshatry = selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeMoshtary(), -1);
            String codeNoeTakhfif = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_NAGHDI());
            DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
            DarkhastFaktorTakhfifModel insertDarkhastFaktorTakhfifModel = new DarkhastFaktorTakhfifModel();
            insertDarkhastFaktorTakhfifModel.setCcTakhfif(new TakhfifNaghdyDAO(mPresenter.getAppContext()).getccTakhfifNaghdiByccGorohMoshtary(ccGorohNoeMoshatry));
            insertDarkhastFaktorTakhfifModel.setCcDarkhastFaktor(ccDarkhastFaktor);
            insertDarkhastFaktorTakhfifModel.setMablaghTakhfif(Math.round(mandeh));
            Log.d("bonus", "Jayezeh mandeh : " + mandeh + " ,mablaghTakhfif:" +mablaghTakhfif);
            if (mablaghTakhfif!=0) {
                DecimalFormat dfSharp = new DecimalFormat("#.##");
                insertDarkhastFaktorTakhfifModel.setDarsadTakhfif(Float.parseFloat( dfSharp.format(((mandeh * 100) / mablaghTakhfif))));
            }
            else
                insertDarkhastFaktorTakhfifModel.setDarsadTakhfif(0f);

            insertDarkhastFaktorTakhfifModel.setCodeNoeTakhfif(Integer.parseInt(codeNoeTakhfif));
            insertDarkhastFaktorTakhfifModel.setExtraProp_ForJayezeh(0);
            insertDarkhastFaktorTakhfifModel.setExtraProp_MustSendToSql(1);
            insertDarkhastFaktorTakhfifModel.setExtraProp_ccJayezehTakhfif(selectedccTakhfif);
            if(noeJayezehTakhfif==DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh())
            {
                insertDarkhastFaktorTakhfifModel.setSharhTakhfif(mPresenter.getAppContext().getResources().getString(R.string.takhfifNaghdiforJayezehArzeshAfzodeh));
                insertDarkhastFaktorTakhfifModel.setExtraProp_IsTakhfifMazad(4);
            }
            else
            {
                insertDarkhastFaktorTakhfifModel.setSharhTakhfif(mPresenter.getAppContext().getResources().getString(R.string.takhfifNaghdiforJayezeh));
                insertDarkhastFaktorTakhfifModel.setExtraProp_IsTakhfifMazad(1);
            }
            darkhastFaktorTakhfifDAO.insert(insertDarkhastFaktorTakhfifModel);

            if(insertDarkhastFaktorTakhfifModel.getExtraProp_IsTakhfifMazad()==4){
                DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = darkhastFaktorSatrDAO.getByccDarkhastFaktorAndccForoshandeh(ccDarkhastFaktor, ccForoshandeh,true);
                calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr(Math.round(mandeh),darkhastFaktorSatrModels);
            }
            else {
                DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = darkhastFaktorSatrDAO.getByccDarkhastFaktorAndccForoshandeh(ccDarkhastFaktor, ccForoshandeh,false);
                calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr(Math.round(mandeh),darkhastFaktorSatrModels);

            }

        }
        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
        KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        int count = 0;
        int insertedCount = 0;
        for (JayezehEntekhabiMojodiModel model : jayezehEntekhabiMojodiModels) {
            Log.d("bonus", "jayezeh model: " + model);

            if (model.getSelectedCount() > 0) {
                Log.d("bonus", "jayezeh select count >0 ");
// todo - double
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
                if (darkhastFaktorJayezehDAO.insert(darkhastFaktorJayezehModel)) {

                    KalaMojodiZaribForoshDAO kalaMojodiZaribForoshDAO = new KalaMojodiZaribForoshDAO(mPresenter.getAppContext());
                    ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModelWithGheymatKharids = kalaMojodiZaribForoshDAO.getForInsertGheymatKharid(model.getCcKalaCode(),model.getShomarehBach(),model.getTarikhTolid(),model.getTarikhEngheza(),model.getGheymatForosh(),model.getGheymatMasrafKonandeh(),model.getCcTaminKonandeh());
                    Log.d("bonus", "jayezeh kalaMojodiZaribModelWithGheymatKharids : " + kalaMojodiZaribModelWithGheymatKharids );

                    int insertedKala = 0;
                    int tedadDarkhastiCount = model.getSelectedCount();
                    for (KalaMojodiZaribModel kalaMojody : kalaMojodiZaribModelWithGheymatKharids) {
                        if (insertedKala < model.getSelectedCount()) {
                            int countNew = (kalaMojody.getTedad() >= tedadDarkhastiCount) ? tedadDarkhastiCount : kalaMojody.getTedad();
                            Log.d("bonus", "jayezeh In While after else count new : " + countNew + " , kala.getTedad() : " + kalaMojody.getTedad());
                            Log.d("bonus", "jayezeh In While after else kalaMojody model " + kalaMojody.toString());

                            String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
                            KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();
                            kalaMojodiModel.setCcTaminKonandeh(kalaMojody.getCcTaminKonandeh());
                            kalaMojodiModel.setTedad(-1 * countNew);
                            kalaMojodiModel.setTarikhDarkhast(currentDate);
                            kalaMojodiModel.setTarikhTolid(kalaMojody.getTarikhTolid());
                            kalaMojodiModel.setTarikhEngheza(kalaMojody.getTarikhEngheza());
                            kalaMojodiModel.setZamaneSabt(model.getZamaneSabt());
                            kalaMojodiModel.setShomarehBach(kalaMojody.getShomarehBach());
                            kalaMojodiModel.setGheymatMasrafKonandeh(kalaMojody.getGheymatMasrafKonandehAsli());
                            kalaMojodiModel.setGheymatForosh(kalaMojody.getGheymatForoshAsli());
                            kalaMojodiModel.setGheymatKharid(kalaMojody.getGheymatKharid());
                            kalaMojodiModel.setCcKalaCode(kalaMojody.getCcKalaCode());
                            kalaMojodiModel.setCcForoshandeh(selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh(), model.getCcForoshandeh()));
                            kalaMojodiModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                            kalaMojodiModel.setForJayezeh(1);
                            kalaMojodiModel.setZamaneSabt(currentDate);
                            kalaMojodiModel.setMax_Mojody(-1 * countNew);
                            kalaMojodiModel.setMax_MojodyByShomarehBach(-1 * countNew);
                            kalaMojodiModel.setCcAfrad(FinalCCAfrad);
                            if (kalaMojodiDAO.insert(kalaMojodiModel)) {
                                SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
                                Log.d("bonus", "model.getGheymatForosh() : " + model.getGheymatForosh() + " ,ccForoshandeh=" + ccForoshandeh);
                                DarkhastFaktorSatrModel darkhastFaktorSatrModel = new DarkhastFaktorSatrModel();
                                darkhastFaktorSatrModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                                darkhastFaktorSatrModel.setCcTaminKonandeh(kalaMojody.getCcTaminKonandeh());
                                darkhastFaktorSatrModel.setCcKala(kalaMojody.getCcKalaCode());
                                darkhastFaktorSatrModel.setCcKalaCode(kalaMojody.getCcKalaCode());
                                darkhastFaktorSatrModel.setTedad3(countNew);
                                darkhastFaktorSatrModel.setCodeNoeKala(2);
                                darkhastFaktorSatrModel.setShomarehBach(kalaMojody.getShomarehBach());
                                darkhastFaktorSatrModel.setTarikhTolid(kalaMojody.getTarikhTolid());
                                darkhastFaktorSatrModel.setTarikhEngheza(kalaMojody.getTarikhEngheza());
                                darkhastFaktorSatrModel.setMablaghForosh(1);
                                darkhastFaktorSatrModel.setMablaghForoshKhalesKala((double) kalaMojody.getGheymatForosh());
                                Log.d("bonus", "model.setMablaghForoshKhalesKala() : " + darkhastFaktorSatrModel.getMablaghForoshKhalesKala());
                                darkhastFaktorSatrModel.setMablaghTakhfifNaghdiVahed(0);
                                darkhastFaktorSatrModel.setMaliat(0);
                                darkhastFaktorSatrModel.setAvarez(0);
                                darkhastFaktorSatrModel.setCcAfrad(ccForoshandeh);
                                darkhastFaktorSatrModel.setExtraProp_IsOld(false);
                                darkhastFaktorSatrModel.setGheymatMasrafKonandeh((double) kalaMojody.getMablaghMasrafKonandeh());
                                darkhastFaktorSatrModel.setGheymatForoshAsli(kalaMojody.getGheymatForoshAsli());
                                darkhastFaktorSatrModel.setGheymatMasrafKonandehAsli(kalaMojody.getGheymatMasrafKonandehAsli());
                                darkhastFaktorSatrModel.setGheymatKharid(kalaMojody.getGheymatKharid());
                                if (darkhastFaktorSatrDAO.insert(darkhastFaktorSatrModel)) {
                                    tedadDarkhastiCount -= countNew;
                                    insertedKala += countNew;
                                }
                            }
                        }
                    }
                    if(tedadDarkhastiCount==0)
                        insertedCount++;
                }
            }
            else if (model.getSelectedCount() == 0 ){
                Log.d("bonus", "jayezeh select count ==0 ");

                DarkhastFaktorJayezehModel darkhastFaktorJayezehModel = new DarkhastFaktorJayezehModel();
                darkhastFaktorJayezehModel.setCcKalaCode(model.getCcKalaCode());
                darkhastFaktorJayezehModel.setCcKala(model.getCcKala());
                darkhastFaktorJayezehModel.setCcJayezeh(0);
                darkhastFaktorJayezehModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                darkhastFaktorJayezehModel.setTedad(0);
                darkhastFaktorJayezehModel.setSharh(model.getNameKala());
                darkhastFaktorJayezehModel.setExtraProp_IsJayezehEntekhabi(1);
                darkhastFaktorJayezehModel.setExtraProp_CodeNoeJayezeh(DarkhastFaktorJayezehModel.CodeNoeJayezehAuto());
                darkhastFaktorJayezehModel.setExtraProp_ccJayezehTakhfif(selectedccTakhfif);
                darkhastFaktorJayezehDAO.insert(darkhastFaktorJayezehModel);
            }
        }

        if (insertedCount == count) {
            if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeTakhfif()) {
                if (removeTakhfif(selectFaktorShared, selectedccTakhfif)) {
                    mPresenter.onSuccessInsert();
                } else {
                    mPresenter.onFailedInsert();
                }
            } else if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeJayezeh()) {
                if (removeJayezeh(darkhastFaktorJayezehTakhfifModel.getCcDarkhastFaktor(), darkhastFaktorJayezehTakhfifModel.getCcJayezehTakhfif(), darkhastFaktorJayezehTakhfifModel.getExtraProp_ccJayezehSatr())){
                    mPresenter.onSuccessInsert();
                } else {
                    mPresenter.onFailedInsert();
                }
            }else if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh()){
                if (removeJayezeh(darkhastFaktorJayezehTakhfifModel.getCcDarkhastFaktor(), darkhastFaktorJayezehTakhfifModel.getCcJayezehTakhfif(), darkhastFaktorJayezehTakhfifModel.getExtraProp_ccJayezehSatr())) {
                    mPresenter.onSuccessInsert();
                } else {
                    mPresenter.onFailedInsert();
                }
            }
        } else if (noeJayezehTakhfif == DarkhastFaktorJayezehTakhfifModel.NoeJayezeh()){
            mPresenter.onFailedInsert();
        }
    }


    private void calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr (double mablaghTakhfif ,ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels){
        Observable.just(darkhastFaktorSatrModels)
                .map(models -> {
                    double sumMablagh = 0;

                    for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrModels) {
                        sumMablagh += darkhastFaktorSatrModel.getMablaghForosh() * darkhastFaktorSatrModel.getTedad3();
                        Log.d("VerifyRequest","calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr darkhastFaktorSatrModel:" + darkhastFaktorSatrModel);

                    }
                    Log.d("VerifyRequest","calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr sumMablagh:" + sumMablagh );
                    double zaribTakhfif = mablaghTakhfif/sumMablagh;
                    Log.d("VerifyRequest","calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr mablaghTakhfif:" + mablaghTakhfif + " ,sumMablagh: " + sumMablagh);
                    DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                    for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrModels) {
                        double mablaghTakhfifNaghdyVahed = (zaribTakhfif * darkhastFaktorSatrModel.getMablaghForosh()) + darkhastFaktorSatrModel.getMablaghTakhfifNaghdiVahed();
                        Log.d("VerifyRequest","calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr zaribTakhfif:" + zaribTakhfif + " ,MablaghForosh: " + darkhastFaktorSatrModel.getMablaghForosh());
                        Log.d("VerifyRequest","calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr MablaghTakhfifNaghdiVahedsatr:" + darkhastFaktorSatrModel.getMablaghTakhfifNaghdiVahed());
                        Log.d("VerifyRequest","calculateMablaghVahedTakhfifNaghdiDarkhastFaktorSatr mablaghTakhfifNaghdyVahed:" + mablaghTakhfifNaghdyVahed);
                        boolean updateMablaghTakhfifNaghdyVahed = darkhastFaktorSatrDAO.updateMablaghTakhfifNaghdyVahed(darkhastFaktorSatrModel.getCcDarkhastFaktorSatr(),mablaghTakhfifNaghdyVahed);
                        if (!updateMablaghTakhfifNaghdyVahed){
                            return false;
                        }
                    }
                    return true;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean updateMablaghTakhfifNaghdyVahed) {
                        if (!updateMablaghTakhfifNaghdyVahed)
                            onError(new Throwable());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mPresenter.onErrorCalculateDiscount(R.string.errorUpdateMablaghTakhfifVahed);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private boolean removeTakhfif(SelectFaktorShared selectFaktorShared, int selectedccTakhfif) {
        try {
            String ccTakhfifs = selectFaktorShared.getString(selectFaktorShared.getCcTakhfifJayezes(), "");
            Log.d("takhfif", "Jayezeh ccTakhfifs in insert bonus : " + ccTakhfifs);
            if (ccTakhfifs.replace(",", "").length() > 0) {
                ArrayList<String> splittedccTakhfif = new ArrayList<>(Arrays.asList(ccTakhfifs.split(",")));
                if (splittedccTakhfif.size() == 0) {
                    //in this case, shared have only one cc and we add this cc to array
                    splittedccTakhfif.add(ccTakhfifs);
                }
                for (int i = 0; i < splittedccTakhfif.size(); i++) {
                    if (splittedccTakhfif.get(i).equals(String.valueOf(selectedccTakhfif))) {
                        splittedccTakhfif.remove(i);
                        break;
                    }
                }
                ccTakhfifs = "";
                if (splittedccTakhfif.size() > 0) {
                    for (String str : splittedccTakhfif) {
                        ccTakhfifs += str + ",";
                    }
                    if (ccTakhfifs.endsWith(",")) {
                        ccTakhfifs = ccTakhfifs.substring(0, ccTakhfifs.length() - 1);
                    }
                }
                Log.d("takhfif", "ccTakhfifs after delete selected : " + ccTakhfifs);
                selectFaktorShared.putString(selectFaktorShared.getCcTakhfifJayezes(), ccTakhfifs);
            }
            return true;
        } catch (Exception e) {
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "SelectBonusModel", "", "removeTakhfif", "");
            return false;
        }
    }


    private boolean removeJayezeh(long ccDarkhastFaktor, int ccJayezeh, int ccJayezehSatr) {
        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
        return darkhastFaktorJayezehDAO.deleteByccDarkhastFaktorAndccJayeze(ccDarkhastFaktor, ccJayezeh, ccJayezehSatr);
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }






}
