package com.saphamrah.MVP.Model;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.DarkhastKalaMVP;
import com.saphamrah.DAO.AdamDarkhastDAO;
import com.saphamrah.DAO.CodeTypeDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorKalaPishnahadiDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.ElatAdamDarkhastDAO;
import com.saphamrah.DAO.ForoshandehDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GPSDataPpcDAO;
import com.saphamrah.DAO.JayezehDAO;
import com.saphamrah.DAO.KalaDarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.DAO.KalaMojodiGiriDAO;
import com.saphamrah.DAO.KalaMojodiZaribForoshDAO;
import com.saphamrah.DAO.KalaPhotoDAO;
import com.saphamrah.DAO.MoshtaryBrandDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryJadidDarkhastDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.Model.AdamDarkhastModel;
import com.saphamrah.Model.CodeTypeModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.Model.KalaDarkhastFaktorModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.MojoodiGiriModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.JayezehByccKalaCodeModel;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaMojodiZaribModel;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DarkhastKalaModel implements DarkhastKalaMVP.ModelOps
{

    private DarkhastKalaMVP.RequiredPresenterOps mPresenter;
    private boolean enableKalaAsasi = true;
    MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
    JayezehDAO jayezehDAO = new JayezehDAO(BaseApplication.getContext());
    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
    private ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeParentModels;
    public DarkhastKalaModel(DarkhastKalaMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
        // get calculate kala asasi from parameter
        String valueCalculateKalaAsasi = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_MOHASEBE_KALA_ASASI);
        if (valueCalculateKalaAsasi != null && valueCalculateKalaAsasi.trim().equals("0"))
        {
            enableKalaAsasi = false;
        }
    }

    @Override
    public void getNoeMasouliat()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        ForoshandehMamorPakhshUtils foroshandehMamorPakhshUtils = new ForoshandehMamorPakhshUtils();
        int noeMasouliat = foroshandehMamorPakhshUtils.getNoeMasouliat(foroshandehMamorPakhshModel);
        mPresenter.onGetNoeMasouliat(noeMasouliat);
    }

    @Override
    public void getNameListOfKalaAdamForosh()
    {
        KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
        String kalaNameOfAdamForosh = kalaMojodiDAO.getKalaAdamForosh();
        if (!kalaNameOfAdamForosh.trim().equals(""))
        {
            String message = String.format("%1$s : %2$s", mPresenter.getAppContext().getString(R.string.adamForoshKala), kalaNameOfAdamForosh);
            mPresenter.showNameListOfKalaAdamForosh(message);
        }
    }

    @Override
    public void getAllRequestedGoods()
    {
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = shared.getLong(shared.getCcDarkhastFaktor() , 0L);

        if (ccDarkhastFaktor != 0)
        {
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
            if (foroshandehMamorPakhshModel != null)
            {
                int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                boolean insertDarkhastFaktorForMamorPakhsh = shared.getBoolean(shared.getInsertDarkhastFaktorSatrForMamorPakhsh() , false);
                if ( (noeMasouliat == 4 || noeMasouliat == 5) && !insertDarkhastFaktorForMamorPakhsh)
                {
                    calculateGoodsListWithMojodiOnline(ccDarkhastFaktor , shared.getInt(shared.getCcMoshtary() , 0));
                    shared.putBoolean(shared.getInsertDarkhastFaktorSatrForMamorPakhsh() , true);
                }
                KalaDarkhastFaktorSatrDAO kalaDarkhastFaktorSatrDAO = new KalaDarkhastFaktorSatrDAO(mPresenter.getAppContext());
                ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = kalaDarkhastFaktorSatrDAO.getByccDarkhast(ccDarkhastFaktor);
                if (enableKalaAsasi)
                {
                    String ccKalaCodesOfKalaAsasi = shared.getString(shared.getCcKalaCodesOfKalaAsasi(), "");
                    setKalaAsasiOfInsertedGoods(ccKalaCodesOfKalaAsasi, kalaDarkhastFaktorSatrModels);
                }
                mPresenter.onGetRequestedGoods(kalaDarkhastFaktorSatrModels);
            }
            else
            {
                mPresenter.onError();
            }
        }
        else
        {
            mPresenter.onGetRequestedGoods(new ArrayList<KalaDarkhastFaktorSatrModel>());
        }
    }


    private void setKalaAsasiOfInsertedGoods(String ccKalaCodesOfKalaAsasi, ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels)
    {
        if (!ccKalaCodesOfKalaAsasi.trim().equals("") && !ccKalaCodesOfKalaAsasi.trim().equals("-1"))
        {
            String[] splittedKalaAsasi = ccKalaCodesOfKalaAsasi.split(",");
            for (KalaDarkhastFaktorSatrModel kalaDarkhastFaktor : kalaDarkhastFaktorSatrModels)
            {
                for (String strccKalaCode : splittedKalaAsasi)
                {
                    try
                    {
                        int ccKalaCode = Integer.parseInt(strccKalaCode);
                        if (ccKalaCode == kalaDarkhastFaktor.getCcKalaCode())
                        {
                            kalaDarkhastFaktor.setKalaAsasi(true);
                            break;
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "DarkhastKalaModel", "", "setKalaAsasiOfInsertedGoods", "");
                    }
                }
            }
        }
    }

    private void calculateGoodsListWithMojodiOnline(long ccDarkhastFaktor , int ccMoshtary)
    {
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        HashMap<String,Integer> hashMapSelectedGoods = darkhastFaktorSatrDAO.getCountOfKalaCodeByccDarkhastFaktor(ccDarkhastFaktor);
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        int moshtaryGharardadccSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getMoshtaryGharardadccSazmanForosh(), -1);
        if (hashMapSelectedGoods.size() > 0)
        {
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
            MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

            String daraje = String.valueOf(moshtaryModel.getDarajeh());
            int noeMoshtary = moshtaryModel.getCcNoeMoshtary();

            KalaMojodiZaribForoshDAO kalaMojodiZaribForoshDAO = new KalaMojodiZaribForoshDAO(mPresenter.getAppContext());
            for (String ccKalaCode : hashMapSelectedGoods.keySet())
            {
                int tedadDarkhasti = hashMapSelectedGoods.get(ccKalaCode);
                Log.d("DarkhastKalaModel","calculateGoodsListWithMojodiOnline: cckalacode:" +  ccKalaCode + " ccDarkhastFaktor:" + ccDarkhastFaktor);
                darkhastFaktorSatrDAO.deleteByccKalaCodeAndccDarkhastFaktor(ccDarkhastFaktor , ccKalaCode);
                ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels = kalaMojodiZaribForoshDAO.getByMoshtaryAndccKalaCode(daraje, noeMoshtary, ccKalaCode, moshtaryGharardadccSazmanForosh);
                Log.d("DarkhastKalaModel","calculateGoodsListWithMojodiOnline: kalaMojodiZaribModels:" +  kalaMojodiZaribModels );
                int sumSelectedNew = 0;
                int loopCounter = 0;
                while (sumSelectedNew < tedadDarkhasti && loopCounter < kalaMojodiZaribModels.size())
                {
                    tedadDarkhasti = tedadDarkhasti - sumSelectedNew;
                    if (kalaMojodiZaribModels.get(loopCounter).getTedad() >= tedadDarkhasti)
                    {
                        if (insertNewDarkhastFaktorSatr(darkhastFaktorSatrDAO, ccDarkhastFaktor, kalaMojodiZaribModels.get(loopCounter), tedadDarkhasti))
                        {
                            Log.d("DarkhastKalaModel" , "tedadDarkhasti : " + tedadDarkhasti);
                            insertNewKalaMojodi(kalaMojodiZaribModels.get(loopCounter).getCcKalaCode(), ccDarkhastFaktor, kalaMojodiZaribModels.get(loopCounter).getGheymatForosh(), kalaMojodiZaribModels.get(loopCounter).getMablaghMasrafKonandeh(), kalaMojodiZaribModels.get(loopCounter).getTarikhTolid(), kalaMojodiZaribModels.get(loopCounter).getShomarehBach(), kalaMojodiZaribModels.get(loopCounter).getCcTaminKonandeh(), (tedadDarkhasti*-1) , kalaMojodiZaribModels.get(loopCounter).getGheymatForoshAsli());
                            sumSelectedNew = tedadDarkhasti;
                            tedadDarkhasti = 0;
                        }
                    }
                    else
                    {
                        if (insertNewDarkhastFaktorSatr(darkhastFaktorSatrDAO, ccDarkhastFaktor, kalaMojodiZaribModels.get(loopCounter), kalaMojodiZaribModels.get(loopCounter).getTedad()))
                        {
                            Log.d("DarkhastKalaModel" , "kalaMojodiZaribModels.get(loopCounter).getTedad() : " + kalaMojodiZaribModels.get(loopCounter).getTedad());
                            insertNewKalaMojodi(kalaMojodiZaribModels.get(loopCounter).getCcKalaCode(), ccDarkhastFaktor, kalaMojodiZaribModels.get(loopCounter).getGheymatForosh(), kalaMojodiZaribModels.get(loopCounter).getMablaghMasrafKonandeh(), kalaMojodiZaribModels.get(loopCounter).getTarikhTolid(), kalaMojodiZaribModels.get(loopCounter).getShomarehBach(), kalaMojodiZaribModels.get(loopCounter).getCcTaminKonandeh(), (kalaMojodiZaribModels.get(loopCounter).getTedad()*-1),kalaMojodiZaribModels.get(loopCounter).getGheymatForoshAsli());
                            sumSelectedNew += kalaMojodiZaribModels.get(loopCounter).getTedad();
                            //tedadDarkhasti = tedadDarkhasti - kalaMojodiZaribModels.get(loopCounter).getTedad();
                        }
                    }
                    loopCounter++;
                }
            }
        }
        else
        {
            mPresenter.onGetRequestedGoods(new ArrayList<KalaDarkhastFaktorSatrModel>());
        }
    }


    private boolean insertNewDarkhastFaktorSatr(DarkhastFaktorSatrDAO darkhastFaktorSatrDAO , long ccDarkhastFaktor, KalaMojodiZaribModel kalaMojodiZaribModel, int requestedCount)
    {
        DarkhastFaktorSatrModel darkhastFaktorSatrModel = new DarkhastFaktorSatrModel();
        darkhastFaktorSatrModel.setCcDarkhastFaktor(ccDarkhastFaktor);
        darkhastFaktorSatrModel.setCcTaminKonandeh(kalaMojodiZaribModel.getCcTaminKonandeh());
        darkhastFaktorSatrModel.setCcKala(kalaMojodiZaribModel.getCcKalaCode());
        darkhastFaktorSatrModel.setCcKalaCode(kalaMojodiZaribModel.getCcKalaCode());
        darkhastFaktorSatrModel.setTedad3(requestedCount);
        darkhastFaktorSatrModel.setCodeNoeKala(1);
        darkhastFaktorSatrModel.setShomarehBach(kalaMojodiZaribModel.getShomarehBach());
        darkhastFaktorSatrModel.setTarikhTolid(kalaMojodiZaribModel.getTarikhTolid());
        darkhastFaktorSatrModel.setTarikhEngheza(kalaMojodiZaribModel.getTarikhEngheza());
        darkhastFaktorSatrModel.setMablaghForosh(kalaMojodiZaribModel.getGheymatForosh());
        darkhastFaktorSatrModel.setMablaghForoshKhalesKala(kalaMojodiZaribModel.getMablaghMasrafKonandeh());
        darkhastFaktorSatrModel.setMablaghTakhfifNaghdiVahed(0);
        darkhastFaktorSatrModel.setMaliat(0);
        darkhastFaktorSatrModel.setAvarez(0);
        darkhastFaktorSatrModel.setCcAfrad(0);
        darkhastFaktorSatrModel.setExtraProp_IsOld(false);
        darkhastFaktorSatrModel.setGheymatMasrafKonandeh(kalaMojodiZaribModel.getMablaghMasrafKonandeh());
        darkhastFaktorSatrModel.setGheymatForoshAsli(kalaMojodiZaribModel.getGheymatForoshAsli());
        darkhastFaktorSatrModel.setExtraProp_GheymatMasrafKonandehAsli(kalaMojodiZaribModel.getGheymatMasrafKonandehAsli());
        return darkhastFaktorSatrDAO.insert(darkhastFaktorSatrModel);
    }


    /**
     *
     * @param ccMoshtary شناسه مشتری
     * @param calculateKalaPishnahadi اگر اولین بار لیست کالا گرفته شود، این پارامتر true می باشد ولی اگر بعد از محاسبه کالا پیشنهادی این متد فراخوانی شود این پارامتر برابر false است
     */
    @Override
    public void getAllKalaWithMojodiZarib(final int ccMoshtary, final boolean calculateKalaPishnahadi, final boolean calculateKalaAsasi, boolean getAllRequestedGoods)
    {
        KalaMojodiZaribForoshDAO kalaMojodiZaribForoshDAO = new KalaMojodiZaribForoshDAO(mPresenter.getAppContext());
        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        final MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        //TODO
        int moshtaryGharardadccSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getMoshtaryGharardadccSazmanForosh(), -1);
        Log.i("ccSazmanForoshGharardad", "getAllKalaWithMojodiZarib: " + moshtaryGharardadccSazmanForosh);

        String daraje = String.valueOf("0," + moshtaryModel.getDarajeh());
        int noeMoshtary = moshtaryModel.getCcNoeMoshtary();
        final ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels = kalaMojodiZaribForoshDAO.getAllByMoshtary(daraje, noeMoshtary, moshtaryGharardadccSazmanForosh);

        final Handler handler = new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg)
            {
                Log.d("DarkhastKalaModel", "end of get goods and size : " + kalaMojodiZaribModels.size() + "kalaMojodiZaribModels:" + kalaMojodiZaribModels.toString());
                mPresenter.onGetAllKalaWithMojodiZarib(kalaMojodiZaribModels);
                return false;
            }
        });

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
                ForoshandehMamorPakhshUtils foroshandehMamorPakhshUtils = new ForoshandehMamorPakhshUtils();
                ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
                int noeMasouliat = foroshandehMamorPakhshUtils.getNoeMasouliat(foroshandehMamorPakhshModel);
                String enablePishnahadKala = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_PISHNAHAD_KALA);
                Log.d("darkhastKala" , "calculateKalaPishnahadi : " + calculateKalaPishnahadi + " , noeMasouliat : " + noeMasouliat + " , enablePishnahadKala : " + enablePishnahadKala);
                if (calculateKalaPishnahadi && (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat == 8) && enablePishnahadKala.trim().equals("1"))
                {
                    Log.d("darkhastKala" , "in calculate kala pishnahadi");
                    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                    DarkhastFaktorKalaPishnahadiDAO darkhastFaktorKalaPishnahadiDAO = new DarkhastFaktorKalaPishnahadiDAO(mPresenter.getAppContext());
                    SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
                    int ccForoshandeh = selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh() , -1);
                    float zaribDarkhastTablet = 0F;
                    int maxRoozPishbiniTahvil = 0;
                    try
                    {
                        zaribDarkhastTablet = Float.parseFloat(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_ZARIB_DARKHAST_TABLET));
                    }
                    catch (Exception e) { e.printStackTrace(); }
                    try
                    {
                        maxRoozPishbiniTahvil = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_MAX_ROOZ_PISHBINI_TAHVIL));
                    }
                    catch (Exception e) { e.printStackTrace(); }
                    //TODO use hardcode for ToorVisit, add toorvisit to storedprocedure and webservice and then get from database
                    int toorVisit = moshtaryDAO.getToorVisit(ccMoshtary);
                    //int toorVisit = 14;
                    SparseArray<KalaDarkhastFaktorModel> kalaDarkhastFaktorModels = darkhastFaktorKalaPishnahadiDAO.getByccMoshtaryForMinQTY(ccMoshtary);
                    try
                    {
                        Log.d("DarkhastKalaModel_pishn" , "ccMoshtary : " + ccMoshtary);
                        Log.d("DarkhastKalaModel_pishn" , "kalaMojodiZaribModels.size : " + kalaMojodiZaribModels.size());
                        Log.d("DarkhastKalaModel_pishn" , "kalaMojodiZaribModels : " + kalaMojodiZaribModels.toString());
                        Log.d("DarkhastKalaModel_pishn" , "toorVisit : " + toorVisit);
                        Log.d("DarkhastKalaModel_pishn" , "kalaDarkhastFaktorModels.size : " + kalaDarkhastFaktorModels.size());
                        Log.d("DarkhastKalaModel_pishn" , "kalaDarkhastFaktorModels : " + kalaDarkhastFaktorModels.toString());
                    }
                    catch (Exception e){e.printStackTrace();}

                    if (kalaDarkhastFaktorModels != null && kalaDarkhastFaktorModels.size() > 0)
                    {
                        calculateKalaPishnahadi(ccForoshandeh, ccMoshtary, kalaMojodiZaribModels, kalaDarkhastFaktorModels, toorVisit, zaribDarkhastTablet);
                    }
                }

                if (enableKalaAsasi && calculateKalaAsasi)
                {
                    String ccKalaCodesOfKalaAsasi = calculateKalaAsasi(kalaMojodiZaribModels, ccMoshtary, moshtaryModel.getCcNoeSenf());
                    SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
                    shared.remove(shared.getCcKalaCodesOfKalaAsasi());
                    shared.putString(shared.getCcKalaCodesOfKalaAsasi(), ccKalaCodesOfKalaAsasi);
                }
                Collections.sort(kalaMojodiZaribModels);

                Message message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
            }
        };
        thread.start();

        if (getAllRequestedGoods)
        {
            getAllRequestedGoods();
        }
    }

    private long calculateDiffDates(KalaDarkhastFaktorModel kalaDarkhastFaktorModel)
    {
        Date startDate = new Date();
        if (kalaDarkhastFaktorModel != null)
        {
            startDate = DateUtils.convertStringDateToDateClass(kalaDarkhastFaktorModel.getTarikhFaktor());
        }
        return DateUtils.getDateDiffAsDay(startDate, new Date());
    }

    private void calculateKalaPishnahadi(int ccForoshandeh, int ccMoshtary, ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels, SparseArray<KalaDarkhastFaktorModel> kalaDarkhastFaktorModels, int toorVisit, float zaribDarkhastTablet)
    {
        KalaMojodiGiriDAO kalaMojodiGiriDAO = new KalaMojodiGiriDAO(mPresenter.getAppContext());
        Map<Integer, Integer> ccKalaCodeTedadMojodiMap = kalaMojodiGiriDAO.getccKalaCodeAndTedad(ccMoshtary, ccForoshandeh);
        //ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels = kalaMojodiGiriDAO.getAll(ccMoshtary, ccForoshandeh);
        //SparseIntArray sparseIntArrayInsertResult = new SparseIntArray();
        /*for (KalaMojodiGiriModel kalaMojodiGiriModel : kalaMojodiGiriModels)
        {*/
            for (KalaMojodiZaribModel kalaMojodiZaribModel : kalaMojodiZaribModels)
            {
                /*if (kalaMojodiGiriModel.getCcKalaCode() == kalaMojodiZaribModel.getCcKalaCode())
                {*/
                    KalaDarkhastFaktorModel kalaDarkhastFaktorModel = kalaDarkhastFaktorModels.get(kalaMojodiZaribModel.getCcKalaCode());
                    if (kalaDarkhastFaktorModel != null)
                    {
                        Integer tedadMojoodiGiri = ccKalaCodeTedadMojodiMap.get(kalaDarkhastFaktorModel.getCcKalaCode());
                        tedadMojoodiGiri = (tedadMojoodiGiri == null) ? 0 : tedadMojoodiGiri;
                        long diffDays = calculateDiffDates(kalaDarkhastFaktorModel);
                        Log.d("DarkhastKalaModel_pishn" , "pishnahadi in : " + kalaMojodiZaribModel.getCcKalaCode() + " , kalaMojodiZaribModel : " + kalaMojodiZaribModel.toString());
                        Log.d("DarkhastKalaModel_pishn" , "pishnahadi in : " + kalaMojodiZaribModel.getCcKalaCode() + " , toorVisit : " + toorVisit);
                        Log.d("DarkhastKalaModel_pishn" , "pishnahadi in : " + kalaMojodiZaribModel.getCcKalaCode() + " , diffDays : " + diffDays);
                        Log.d("DarkhastKalaModel_pishn" , "pishnahadi in : " + kalaMojodiZaribModel.getCcKalaCode() + " , TedadFaktor : " + kalaDarkhastFaktorModel.getTedadFaktor());
                        Log.d("DarkhastKalaModel_pishn" , "pishnahadi in : " + kalaMojodiZaribModel.getCcKalaCode() + " , tedadMojoodiGiri : " + tedadMojoodiGiri);
                        Log.d("DarkhastKalaModel_pishn" , "pishnahadi in : " + kalaMojodiZaribModel.getCcKalaCode() + " , zaribDarkhastTablet : " + zaribDarkhastTablet);

                        SparseIntArray sparseIntArrayTedadPishnahadi = mohasebehTedadFaktorAndPishnahady(kalaMojodiZaribModel, toorVisit, diffDays, kalaDarkhastFaktorModel.getTedadFaktor(), tedadMojoodiGiri, zaribDarkhastTablet);
                        int tedadPishnahadi = sparseIntArrayTedadPishnahadi.valueAt(0);
                        Log.d("DarkhastKalaModel_pishn" , "tedadPishnahadi : " + tedadPishnahadi + /*" ccKalaCode : " + kalaMojodiGiriModel.getCcKalaCode() +*/ " name Kala : " + kalaMojodiZaribModel.getNameKala());
                        //TODO اگر تعداد پیشنهادی از صفر بزرگتر بود و از موجودی انبار کمتر باید ثبت شود
                        //TODO اگر تعداد پیشنهادی یک کالا بین دو شماره بچ متفاوت تقسیم شد و از هرکدام یک تعداد باید ثبت میشد چیکار کنیم؟
                        if (tedadPishnahadi > 0)
                        {
                            kalaMojodiZaribModel.setTedadPishnahadi(tedadPishnahadi);
                            Log.d("kalaPishnahadi" , "name : " + kalaMojodiZaribModel.getNameKala() + " , codeKala : " + kalaMojodiZaribModel.getCodeKala() + " , tedad : " + tedadPishnahadi);
                        }
                    }
                //}
            }
        //}
        //getAllRequestedGoods();
        //getAllKalaWithMojodiZarib(ccMoshtary, false);
    }

    private SparseIntArray mohasebehTedadFaktorAndPishnahady(KalaMojodiZaribModel kalaMojodiZaribModel, int toorVisit, long diffDays, int sumKharid, int mojoodyRooz, float zaribDarkhastTablet)
    {
        int tedadPishnahady = 0;
        int tedadFaktor = 0;
        try
        {
            /*SystemConfigDAO systemconfigDAO = new SystemConfigDAO(context);
            ZaribDarkhastTablet=systemconfigDAO.GetAll().get(0).getZaribDarkhastTablet();*/

            long mojoodyMoredNiaz = (sumKharid - mojoodyRooz) / ((diffDays / toorVisit <= 0 ? 1 : diffDays / toorVisit) + 1);
            float mizanSefaresh = (mojoodyMoredNiaz * zaribDarkhastTablet) - mojoodyRooz;
            int zaribForosh = kalaMojodiZaribModel.getZaribForosh();

            Log.d("DarkhastKalaModel_pishn" , "ccKalaCode : " + kalaMojodiZaribModel.getCcKalaCode());
            Log.d("DarkhastKalaModel_pishn" , "nameKala : " + kalaMojodiZaribModel.getNameKala());
            Log.d("DarkhastKalaModel_pishn" , "TedadMojodyGhabelForosh : " + kalaMojodiZaribModel.getTedadMojodyGhabelForosh());
            Log.d("DarkhastKalaModel_pishn" , "sumKharid : " + sumKharid);
            Log.d("DarkhastKalaModel_pishn" , "mojoodyRooz : " + mojoodyRooz);
            Log.d("DarkhastKalaModel_pishn" , "mojoodyMoredNiaz : " + mojoodyMoredNiaz);
            Log.d("DarkhastKalaModel_pishn" , "mizanSefaresh : " + mizanSefaresh);
            Log.d("DarkhastKalaModel_pishn" , "zaribForosh : " + zaribForosh + " , /2: " + zaribForosh/2);

            if (mizanSefaresh < zaribForosh/2)
            {
                tedadPishnahady = 0;
                tedadFaktor= 0;
            }
            else if(zaribForosh > 0)
            {
                if (mizanSefaresh < zaribForosh)
                {
                    tedadPishnahady = zaribForosh;
                    if (tedadPishnahady<= kalaMojodiZaribModel.getTedadMojodyGhabelForosh())
                    {
                        tedadFaktor= tedadPishnahady;
                    }
                    Log.d("DarkhastKalaModel_pishn","if tedadPishnahady:"+tedadPishnahady+ " ,tedadFaktor:"+tedadFaktor);
                }
                else
                {
                    tedadPishnahady = (Math.round(mizanSefaresh / zaribForosh) + 1) * zaribForosh;
                    tedadFaktor=tedadPishnahady;
                    while (tedadFaktor > kalaMojodiZaribModel.getTedadMojodyGhabelForosh())
                    {
                        tedadFaktor= tedadFaktor - zaribForosh;
                    }
                    Log.d("DarkhastKalaModel_pishn","else tedadPishnahady:"+tedadPishnahady+ " ,tedadFaktor:"+tedadFaktor);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        SparseIntArray resualt = new SparseIntArray();
        resualt.put(tedadFaktor , tedadPishnahady);

        return resualt;
    }


    //TODO check kala asasi
    private String calculateKalaAsasi(ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels, int ccMoshtary, int ccNoeSenfMoshtary)
    {
        int countMoshtaryBrand = new MoshtaryBrandDAO(mPresenter.getAppContext()).getCountByccMoshtary(ccMoshtary);
        MoshtaryJadidDarkhastDAO moshtaryJadidDarkhastDAO = new MoshtaryJadidDarkhastDAO(mPresenter.getAppContext());
        String ccKalaCodesOfKalaAsasi = "-1";

        Log.d("DarkhastKala" , "ccNoeSenfMoshtary : " + ccNoeSenfMoshtary);
        Log.d("DarkhastKala" , "ccMoshtary : " + ccMoshtary);
        Log.d("DarkhastKala" , "countMoshtaryBrand : " + countMoshtaryBrand);

        for (KalaMojodiZaribModel kalaMojodi : kalaMojodiZaribModels)
        {
            //DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
            //int tedadKalaKharidKardeh3Mah = darkhastFaktorSatrDAO.getTedadKharid3Mah(ccMoshtary);
            DarkhastFaktorKalaPishnahadiDAO darkhastFaktorKalaPishnahadiDAO = new DarkhastFaktorKalaPishnahadiDAO(mPresenter.getAppContext());
            int tedadKalaKharidKardeh3Mah = darkhastFaktorKalaPishnahadiDAO.getTedadKharid3Mah(ccMoshtary);
            int sathKala = 0;
            boolean hasKalaAsasi = false;

            Log.d("DarkhastKala" , "ccKalaCode : " + kalaMojodi.getCcKalaCode() + " , tedadKalaKharidKardeh3Mah : " + tedadKalaKharidKardeh3Mah);

            if (tedadKalaKharidKardeh3Mah<10)
            {
                hasKalaAsasi = moshtaryJadidDarkhastDAO.getHasKalaAsasiKharidNakardeh(kalaMojodi.getCcKalaCode(), ccNoeSenfMoshtary, ccMoshtary, countMoshtaryBrand);
                sathKala = moshtaryJadidDarkhastDAO.getSathKala(kalaMojodi.getCcKalaCode());

                Log.d("DarkhastKala" , "hasKalaAsasi : " + hasKalaAsasi + " , sathKala : " + sathKala);

                if(hasKalaAsasi && (ccNoeSenfMoshtary != 345) && (ccNoeSenfMoshtary != 339) && (sathKala == 1))
                {
                    kalaMojodi.setKalaAsasi(true);
                    ccKalaCodesOfKalaAsasi += "," + kalaMojodi.getCcKalaCode();
                    Log.d("DarkhastKala","ccKalaCodesOfKalaAsasi:"+ccKalaCodesOfKalaAsasi);
                }
            }
            else if (tedadKalaKharidKardeh3Mah>=10 & tedadKalaKharidKardeh3Mah<15)
            {
                hasKalaAsasi = moshtaryJadidDarkhastDAO.getHasKalaAsasiKharidNakardeh(kalaMojodi.getCcKalaCode(), ccNoeSenfMoshtary, ccMoshtary, countMoshtaryBrand);
                sathKala = moshtaryJadidDarkhastDAO.getSathKala(kalaMojodi.getCcKalaCode());
                if(hasKalaAsasi && (ccNoeSenfMoshtary != 345) && (ccNoeSenfMoshtary != 339) && (sathKala == 1 || sathKala == 2))
                {
                    kalaMojodi.setKalaAsasi(true);
                    ccKalaCodesOfKalaAsasi += "," + kalaMojodi.getCcKalaCode();
                }
            }
        }
        return ccKalaCodesOfKalaAsasi;
    }

    private long insertNewFaktor(int ccMoshtary , String strLatitude , String strLongitude)
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();

        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String ccChildParameters = Constants.CC_CODE_NOE_VOSOL_MPSHTARY_RESID() + "," + Constants.CC_BE_MASOLIAT_FOROSH() + "," + Constants.CC_NOE_HAML_ADDI() + "," + Constants.CC_VAZEIAT_FAKTOR_TEMP();
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccChildParameter(ccChildParameters);
        int codeNoeVosolMoshtaryResid = -1;
        int codeBeMasoliatForosh = -1;
        int codeNoeHamlAddi = -1;
        int codeVazeiatFaktorTemp = -2;

        for (ParameterChildModel model : childParameterModels)
        {
            if (model.getCcParameterChild() == Constants.CC_CODE_NOE_VOSOL_MPSHTARY_RESID())
            {
                codeNoeVosolMoshtaryResid = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_BE_MASOLIAT_FOROSH())
            {
                codeBeMasoliatForosh = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_NOE_HAML_ADDI())
            {
                codeNoeHamlAddi = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_VAZEIAT_FAKTOR_TEMP())
            {
                codeVazeiatFaktorTemp = Integer.parseInt(model.getValue());
            }
        }

        int shomareDarkhast = 0;
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        int ccForoshandeh = shared.getInt(shared.getCcForoshandeh() , 0);
        String uniqIdTablet = String.valueOf(ccForoshandeh) + ccMoshtary + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
        String saateVorodBeMaghaze = shared.getString(shared.getSaatVorodBeMaghazeh() , currentDate);
        String saateKhorojAzMaghaze = shared.getString(shared.getSaatKhorojAzMaghazeh() , currentDate);
        float latitude = Float.parseFloat(shared.getString(shared.getLatitude() , strLatitude));
        float longitude = Float.parseFloat(shared.getString(shared.getLongitude() , strLongitude));
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = new DarkhastFaktorModel();
        ArrayList<DarkhastFaktorModel> darkhastFaktorModels = darkhastFaktorDAO.getLastFaktorForoshandeh();
        if(darkhastFaktorModels.size()>0)
        {
            Log.d("darkhastFaktor" , "shomareDarkhast in if : " + darkhastFaktorModels.get(0).getShomarehDarkhast());
            shomareDarkhast = (darkhastFaktorModels.get(0).getShomarehDarkhast()+1);
            Log.d("darkhastFaktor" , "shomareDarkhast : " + shomareDarkhast);
        }
        darkhastFaktorModel.setTarikhFaktor(currentDate);
        darkhastFaktorModel.setShomarehDarkhast(shomareDarkhast);
        darkhastFaktorModel.setShomarehFaktor(0);
        darkhastFaktorModel.setCcForoshandeh(ccForoshandeh);
        darkhastFaktorModel.setCcMoshtary(ccMoshtary);
        darkhastFaktorModel.setTarikhErsal(currentDate);
        darkhastFaktorModel.setModatRoozRaasGiri(0);
        darkhastFaktorModel.setModateVosol(0);
        //darkhastFaktorModel.setCodeNoeVosolAzMoshtary(codeNoeVosolMoshtaryResid);
        darkhastFaktorModel.setCodeNoeVosolAzMoshtary(new MoshtaryDAO(mPresenter.getAppContext()).getByccMoshtary(ccMoshtary).getCodeNoeVosolAzMoshtary());
        darkhastFaktorModel.setMablaghKolFaktor(0.0f);
        darkhastFaktorModel.setTakhfifNaghdy(0);
        darkhastFaktorModel.setExtraProp_MablaghTakhfifSenfiHajmi(0);
        darkhastFaktorModel.setMablaghKhalesFaktor(0.0f);
        darkhastFaktorModel.setBeMasoliat(codeBeMasoliatForosh);
        darkhastFaktorModel.setCodeNoeHaml(codeNoeHamlAddi);
        darkhastFaktorModel.setSumMaliat(0);
        darkhastFaktorModel.setSumAvarez(0);
        darkhastFaktorModel.setSaatVorodBeMaghazeh(saateVorodBeMaghaze);
        darkhastFaktorModel.setSaatKhorojAzMaghazeh(saateKhorojAzMaghaze);
        darkhastFaktorModel.setCcAddressMoshtary(0);
        darkhastFaktorModel.setCodeVazeiat(codeVazeiatFaktorTemp);
        darkhastFaktorModel.setLatitude(latitude);
        darkhastFaktorModel.setLongitude(longitude);
        darkhastFaktorModel.setPPC_VersionNumber(new PubFunc().new DeviceInfo().getCurrentVersion(mPresenter.getAppContext()));
        //darkhastFaktorModel.setPPC_Version_Date(currentDate);
        darkhastFaktorModel.setExtraProp_IsOld(0);
        darkhastFaktorModel.setExtraProp_InsertInPPC(1);
        darkhastFaktorModel.setExtraProp_MablaghArzeshAfzoodeh(0);
        darkhastFaktorModel.setExtraProp_SumTakhfifat(0);
        darkhastFaktorModel.setExtraProp_MablaghNahaeeFaktor(0);
        darkhastFaktorModel.setMarjoeeKamel(0);
        String strShomareFarkhast = createShomareDarkhast(shomareDarkhast);
        darkhastFaktorModel.setExtraProp_ShomarehDarkhast(strShomareFarkhast);
        darkhastFaktorModel.setUniqID_Tablet(uniqIdTablet);
        darkhastFaktorModel.setDarajeh(new MoshtaryDAO(mPresenter.getAppContext()).getDarajehByccMoshtary(ccMoshtary));
        darkhastFaktorModel.setCcDarkhastFaktorNoeForosh(foroshandehMamorPakhshModel.getCcDarkhastFaktorNoeForosh());
        long newccDarkhastFaktor = darkhastFaktorDAO.insert(darkhastFaktorModel , new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel));
        shared.putLong(shared.getCcDarkhastFaktor() , newccDarkhastFaktor);

        return newccDarkhastFaktor;
    }

    @Override
    public SparseIntArray insertNewFaktorSatr(int ccMoshtary , int position , KalaMojodiZaribModel kalaMojodiZaribModel , int requestedCount)
    {
        Log.d("DarkhastKalaModel","kalaMojodiZaribModel1:" + kalaMojodiZaribModel.toString());
		SparseIntArray sparseIntArray = new SparseIntArray();																			   
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = shared.getLong(shared.getCcDarkhastFaktor() , 0L);
        if (ccDarkhastFaktor == 0)
        {
            PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
            ccDarkhastFaktor = insertNewFaktor(ccMoshtary , String.valueOf(googleLocationProvider.getLatitude()), String.valueOf(googleLocationProvider.getLongitude()));
            /*if (googleLocationProvider.getHasAccess())
            {
                ccDarkhastFaktor = insertNewFaktor(ccMoshtary , String.valueOf(googleLocationProvider.getLatitude()), String.valueOf(googleLocationProvider.getLongitude()));
            }
            else
            {
                mPresenter.onErrorInsertFaktor(R.string.errorAccessToLocation);
            }*/
        }
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());

        int tedad = darkhastFaktorSatrDAO.getTedadByKalaInfo(ccDarkhastFaktor, kalaMojodiZaribModel.getCcTaminKonandeh(), kalaMojodiZaribModel.getCcKalaCode(), kalaMojodiZaribModel.getCcKalaCode(), kalaMojodiZaribModel.getShomarehBach());
        if (tedad > 0)
        {
            Log.d("DarkhastKalaModel","kalaMojodiZaribModel1-2:" +kalaMojodiZaribModel);
            darkhastFaktorSatrDAO.delete(ccDarkhastFaktor, kalaMojodiZaribModel.getCcTaminKonandeh(), kalaMojodiZaribModel.getCcKalaCode(), kalaMojodiZaribModel.getCcKalaCode(), kalaMojodiZaribModel.getShomarehBach(), kalaMojodiZaribModel.getGheymatForosh());
            if (insertNewKalaMojodi(kalaMojodiZaribModel.getCcKalaCode(), ccDarkhastFaktor, kalaMojodiZaribModel.getGheymatForosh(), kalaMojodiZaribModel.getMablaghMasrafKonandeh(), kalaMojodiZaribModel.getTarikhTolid(), kalaMojodiZaribModel.getShomarehBach(), kalaMojodiZaribModel.getCcTaminKonandeh(), tedad , kalaMojodiZaribModel.getGheymatForoshAsli()))
            {
                kalaMojodiZaribModel.setTedad(kalaMojodiZaribModel.getTedad() + tedad);
            }
        }
        Log.d("DarkhastKalaModel","kalaMojodiZaribModel2:" + kalaMojodiZaribModel.toString());
        if (insertNewDarkhastFaktorSatr(darkhastFaktorSatrDAO, ccDarkhastFaktor, kalaMojodiZaribModel, requestedCount))
        {
            if (insertNewKalaMojodi(kalaMojodiZaribModel.getCcKalaCode(), ccDarkhastFaktor, kalaMojodiZaribModel.getGheymatForosh(), kalaMojodiZaribModel.getMablaghMasrafKonandeh(), kalaMojodiZaribModel.getTarikhTolid(), kalaMojodiZaribModel.getShomarehBach(), kalaMojodiZaribModel.getCcTaminKonandeh(), (requestedCount*-1),kalaMojodiZaribModel.getGheymatForoshAsli()))
            {
                kalaMojodiZaribModel.setTedad(kalaMojodiZaribModel.getTedad() - requestedCount);
            }
			if (position != -1)
            {
                mPresenter.onSuccessInsertFaktor(position , kalaMojodiZaribModel.getTedad());
                getAllRequestedGoods();
            }
            sparseIntArray.put(kalaMojodiZaribModel.getCcKalaCode(), kalaMojodiZaribModel.getTedad());
        }
        else
        {
            mPresenter.onErrorInsertFaktor(R.string.errorOperation);
			sparseIntArray.put(-1,-1);
        }
		return sparseIntArray;																													  
        //mojoodiGiriDAO.UpdateTedadPishnehady(SelectFaktor.getccMoshtary(), kala.getccKalaCode(), kala.getTedadPishnahady());
        //-------------------
        /*if(darkhastFaktorSatrModel.getccTaminKonandeh() == TaminKonandeh.Delpazir.getValue())
            SumMablaghDelpazir += kala.getMablaghForosh() * kala.getTedadFaktor();
        if(darkhastFaktorSatrModel.getccTaminKonandeh() == TaminKonandeh.Orbit.getValue())
            SumMablaghOrbit += kala.getMablaghForosh() * kala.getTedadFaktor();*/

    }


    @Override
    public void removeKala(KalaDarkhastFaktorSatrModel kalaDarkhastFaktorSatrModel , int position , int ccMoshtary)
    {
        Log.d("DarkhastKalaModel","kalaDarkhastFaktorSatrModel:" + kalaDarkhastFaktorSatrModel.toString());
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        if (darkhastFaktorSatrDAO.delete(kalaDarkhastFaktorSatrModel.getCcDarkhastFaktor(), kalaDarkhastFaktorSatrModel.getCcTaminKonandeh(), kalaDarkhastFaktorSatrModel.getCcKala(), kalaDarkhastFaktorSatrModel.getCcKalaCode(), kalaDarkhastFaktorSatrModel.getShomarehBach(), (long)kalaDarkhastFaktorSatrModel.getMablaghForosh()))
        {
            if (insertNewKalaMojodi(kalaDarkhastFaktorSatrModel.getCcKalaCode(), kalaDarkhastFaktorSatrModel.getCcDarkhastFaktor(), (int)kalaDarkhastFaktorSatrModel.getMablaghForosh(), (int)kalaDarkhastFaktorSatrModel.getGheymatMasrafKonandeh(), kalaDarkhastFaktorSatrModel.getTarikhTolid(), kalaDarkhastFaktorSatrModel.getShomarehBach(), kalaDarkhastFaktorSatrModel.getCcTaminKonandeh(), kalaDarkhastFaktorSatrModel.getTedad3(), ((int) kalaDarkhastFaktorSatrModel.getGheymatForoshAsli())))
            {
                getAllKalaWithMojodiZarib(ccMoshtary, true, true, false);
            }
            mPresenter.onSuccessRemoveKala(position);
        }
        else
        {
            mPresenter.onErrorRemoveKala();
        }
    }


    private boolean insertNewKalaMojodi(int ccKalaCode, long ccDarkhastFaktor, int gheymatForosh, int gheymatMasrafKonande, String tarikhTolid, String shomareBach, int ccTaminKonande, int tedad, int gheymatForoshAsli)
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        int ccForoshandeh = selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh() , 0);
        String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
        String tarikhDarkhast = currentDate;
        Log.d("darkhastKala", "ccForoshandeh in insertNewKalaMojodi : " + ccForoshandeh);
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            tarikhDarkhast = new DarkhastFaktorDAO(mPresenter.getAppContext()).getTarikhByccDarkhastFaktor(ccDarkhastFaktor);
        }

        KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
        KalaMojodiModel kalaMojodiModelFromDB = kalaMojodiDAO.getOneByccKalaCode(String.valueOf(ccKalaCode),shomareBach,gheymatForoshAsli,gheymatMasrafKonande,ccTaminKonande);
        KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();
        kalaMojodiModel.setCcDarkhastFaktor(ccDarkhastFaktor);
        kalaMojodiModel.setCcForoshandeh(ccForoshandeh);
        kalaMojodiModel.setCcKalaCode(ccKalaCode);
        kalaMojodiModel.setGheymatForosh(gheymatForoshAsli);
        kalaMojodiModel.setGheymatMasrafKonandeh(gheymatMasrafKonande);
        kalaMojodiModel.setTarikhTolid(tarikhTolid);
        kalaMojodiModel.setShomarehBach(shomareBach);
        kalaMojodiModel.setTarikhDarkhast(tarikhDarkhast);
        kalaMojodiModel.setTedad(tedad);
        kalaMojodiModel.setCcTaminKonandeh(ccTaminKonande);
        kalaMojodiModel.setZamaneSabt(currentDate);
        //kalaMojodiModel.setMax_Mojody(kalaMojodiModelFromDB.getMax_Mojody());
        kalaMojodiModel.setMax_Mojody(tedad);
        //kalaMojodiModel.setMax_MojodyByShomarehBach(kalaMojodiModelFromDB.getMax_MojodyByShomarehBach());
        kalaMojodiModel.setMax_MojodyByShomarehBach(tedad);
        kalaMojodiModel.setCcAfrad(foroshandehMamorPakhshModel.getCcAfrad());
        return kalaMojodiDAO.insert(kalaMojodiModel);
    }

    private String createShomareDarkhast(int shomarehDarkhast)
    {
        ForoshandehMamorPakhshDAO foroshandehmamorpakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehmamorpakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());
        String createShomareDarkhast = "";
        String pattern = "";
        String day = "", month = "", year = "", codeForoshandeh = "", codeMarkazForosh = "", radif = "";

        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        int ccForoshandeh = shared.getInt(shared.getCcForoshandeh() , 0);
        ForoshandehModel foroshandehModel = foroshandehDAO.getByccForoshande(ccForoshandeh);

        int[] persianDate = new PubFunc().new DateUtils().splittedTodayPersianDate(mPresenter.getAppContext());

        CodeTypeDAO codetypeDAO = new CodeTypeDAO(mPresenter.getAppContext());
        ArrayList<CodeTypeModel> codetypes =  codetypeDAO.getAll();
        if (codetypes.size() > 0)
        {
            pattern = codetypes.get(0).getPattern();
        }

        String[] patternSplit = pattern.split(",");

        if(shomarehDarkhast == 0)
        {
            shomarehDarkhast = 1;
        }

        for(int i = 0 ; i<patternSplit.length ; i++)
        {
            String PatternChild = patternSplit[i];
            switch(PatternChild)
            {
                case "{Day}":
                    day = String.valueOf(persianDate[2]);
                    createShomareDarkhast += day + "/";
                    break;
                case "{Month}":
                    month = String.valueOf(persianDate[1]);
                    createShomareDarkhast += month + "/";
                    break;
                case "{Year}":
                    year = String.valueOf(persianDate[0]);
                    createShomareDarkhast += year.substring(2) + "/";
                    break;
                case "{CodeForoshandeh}":
                    if(noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat == 8)
                    {
                        codeForoshandeh = foroshandehMamorPakhshModel.getCodeForoshandeh();
                    }
                    else if(noeMasouliat == 4 || noeMasouliat == 5)
                    {
                        codeForoshandeh = foroshandehModel.getCodeForoshandeh();
                    }
                    createShomareDarkhast += codeForoshandeh+"/";
                    break;
                case "{CodeMarkazForosh}":
                    codeMarkazForosh=String.valueOf(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
                    createShomareDarkhast += codeMarkazForosh+"/";
                    break;
                case "{Radif}":
                    radif = String.valueOf(shomarehDarkhast);
                    createShomareDarkhast += radif;
                    break;
            }
        }
        Log.d("darkhast" , "shomare darkhast : " + createShomareDarkhast);
        return createShomareDarkhast;
    }

    @Override
    public void getElatAdamSefaresh(int ccMoshtary)
    {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

        ElatAdamDarkhastDAO elatAdamDarkhastDAO = new ElatAdamDarkhastDAO(mPresenter.getAppContext());
        ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels = elatAdamDarkhastDAO.getElatAdamSefaresh(moshtaryModel.getCodeVazeiat() , moshtaryModel.getCcNoeMoshtary());

        DarkhastFaktorKalaPishnahadiDAO darkhastFaktorKalaPishnahadiDAO = new DarkhastFaktorKalaPishnahadiDAO(BaseApplication.getContext());
        int tedadAghlam3Mag = darkhastFaktorKalaPishnahadiDAO.getTedadKharid3Mah(ccMoshtary);
        KalaMojodiGiriDAO kalaMojodiGiriDAO = new KalaMojodiGiriDAO(BaseApplication.getContext());
        ArrayList<MojoodiGiriModel> mojodiGiriModels = kalaMojodiGiriDAO.getTedadMojodiGiri(ccMoshtary);
        ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModelsForShow = new ArrayList<>();
        /**
         * if we have GetCondition is 1 in list check this condition
         * if we have GetCondition is 0 add in list without condition
         * add it from list for show Adam Darkhast When the condition was met
         * getGetCondition() == 0  :  show
         * getGetCondition() == 1  :  do not show
         * CcElatAdamDarkhast = 1 : have mojodi
         * CcElatAdamDarkhast = 2 : There was no customer
         */
        for (int i = 0 ; i < elatAdamDarkhastModels.size() ; i++){
            if (elatAdamDarkhastModels.get(i).getCcElatAdamDarkhast() == 1){
                if (elatAdamDarkhastModels.get(i).getGetCondition() == 1){
                    if(tedadAghlam3Mag >= 15  && mojodiGiriModels.size() > 0)
                    {
                        elatAdamDarkhastModelsForShow.add(elatAdamDarkhastModels.get(i));
                    }
                } else if (elatAdamDarkhastModels.get(i).getGetCondition() == 0){
                    elatAdamDarkhastModelsForShow.add(elatAdamDarkhastModels.get(i));
                }
            } else if (elatAdamDarkhastModels.get(i).getCcElatAdamDarkhast() == 2) {
                if (elatAdamDarkhastModels.get(i).getGetCondition() == 1){
                    if (tedadAghlam3Mag > 0){
                        elatAdamDarkhastModelsForShow.add(elatAdamDarkhastModels.get(i));
                    }
                } else if (elatAdamDarkhastModels.get(i).getGetCondition() == 0){
                    elatAdamDarkhastModelsForShow.add(elatAdamDarkhastModels.get(i));
                }
            } else if (elatAdamDarkhastModels.get(i).getCcElatAdamDarkhast() > 2){
                elatAdamDarkhastModelsForShow.add(elatAdamDarkhastModels.get(i));
            }

        }

        mPresenter.onGetElatAdamSefaresh(elatAdamDarkhastModelsForShow);
    }

    @Override
    public void insertAdamSefaresh(int ccMoshtary, int ccElatAdamSefaresh, byte[] imageAdamSefaresh, String codeMoshtaryTekrari)
    {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        int ccForoshandeh = shared.getInt(shared.getCcForoshandeh() , 0);
        AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(mPresenter.getAppContext());
        AdamDarkhastModel adamDarkhastModel = new AdamDarkhastModel();

        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
        /*if (!googleLocationProvider.getHasAccess())
        {
            mPresenter.onFailedInsertAdamDarkhast(R.string.errorAccessToLocation);
        }
        else
        {*/
            float longitude = (float) googleLocationProvider.getLongitude();
            float latitude = (float) googleLocationProvider.getLatitude();

            adamDarkhastModel.setCcElatAdamDarkhast(ccElatAdamSefaresh);
            adamDarkhastModel.setCodeMoshtaryTekrari(codeMoshtaryTekrari);
            adamDarkhastModel.setAdamDarkhastImage(imageAdamSefaresh);
            adamDarkhastModel.setCcMoshtary(ccMoshtary);
            adamDarkhastModel.setCcForoshandeh(ccForoshandeh);
            adamDarkhastModel.setDateAdamDarkhast(getCurrentDate());
            adamDarkhastModel.setLongitude(longitude);
            adamDarkhastModel.setLatitude(latitude);
            adamDarkhastModel.setIsSentToServer(false);

            if (adamDarkhastDAO.insert(adamDarkhastModel))
            {
                int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                int ccMamorPakhsh = 0;
                if (noeMasouliat == 4 || noeMasouliat == 5)
                {
                    ccMamorPakhsh = foroshandehMamorPakhshModel.getCcMamorPakhsh();
                    ccForoshandeh = 0;
                }
                insertGpsData(ccForoshandeh,foroshandehMamorPakhshModel.getCcAfrad(), new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()), latitude, longitude, ccMamorPakhsh, ccMoshtary);
                mPresenter.onSuccessInsertAdamDarkhast();
            }
            else
            {
                mPresenter.onFailedInsertAdamDarkhast(R.string.errorOperation);
            }
        //}
    }

    @Override
    public void checkData()
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(), -1);
        List<KalaModel> kalaModels = new KalaMojodiDAO(mPresenter.getAppContext()).getMaxMojodiKalaByDarkhastFaktor(ccDarkhastFaktor);
        boolean validData = true;
        Log.d("DarkhastKala","kalaModels:"+kalaModels.toString());
        if (kalaModels.size() > 0)
        {
            mPresenter.onErrorBiggerThanMaxKalaMojodi(kalaModels);
            validData = false;
        }

        if (enableKalaAsasi)
        {
            String ccKalaCodesOfKalaAsasi = selectFaktorShared.getString(selectFaktorShared.getCcKalaCodesOfKalaAsasi(), "");
            // "-1," -> 3 char so if length bigger than 3
            if (ccKalaCodesOfKalaAsasi.trim().length() > 3)
            {
                int countccKalaCodesAsasiInDarkhast = new DarkhastFaktorSatrDAO(mPresenter.getAppContext()).getCountccKalaCodesAsasiInNewFaktor(ccKalaCodesOfKalaAsasi, ccDarkhastFaktor);
                Log.d("DarkhastKala" , "countccKalaCodesAsasiInDarkhast : " + countccKalaCodesAsasiInDarkhast);
                if (countccKalaCodesAsasiInDarkhast <= 0)
                {
                    mPresenter.onErrorNotSelectKalaAsasi();
                    validData = false;
                }
            }
        }

        if (validData)
        {
            mPresenter.onSuccessCheckData();
        }
    }

    private boolean insertGpsData(int ccForoshandeh, int ccAfrad , String currentDate , double latitude , double longtitude , int ccMamorPakhsh, int ccMoshtary)
    {
        GPSDataModel gpsDataModel = new GPSDataModel();
        GPSDataPpcDAO gpsDataDAO = new GPSDataPpcDAO(mPresenter.getAppContext()) ;
		MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());

        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
		
        gpsDataModel.setCcForoshandeh(ccForoshandeh);
        gpsDataModel.setCcAfrad(ccAfrad);
        gpsDataModel.setCcMasir(moshtaryModel.getCcMasir());
        gpsDataModel.setTarikh(currentDate);
        gpsDataModel.setLatitude(latitude);
        gpsDataModel.setLongitude(longtitude);
        gpsDataModel.setExtraProp_IsSend(0);
        gpsDataModel.setDistance(0D);
        gpsDataModel.setCcMamorPakhsh(ccMamorPakhsh);
        return gpsDataDAO.insert(gpsDataModel);
    }


    private Date getCurrentDate()
    {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        String date = sdf.format(new Date());
        try
        {
            currentDate = sdf.parse(date);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastKalaModel", "", "insertAdamSefaresh", "");
        }
        return currentDate;
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


    /**
     * get list Jayezeh from DB for show recycler
     * @param ccKalaCode
     * @param tedadKala
     * @param ccDarkhastFaktor
     * @param mablaghForosh
     */
    @Override
    public void checkJayezehParent(int ccKalaCode, int tedadKala, Long ccDarkhastFaktor, double mablaghForosh) {

        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
        jayezehByccKalaCodeParentModels = jayezehDAO.getByccKalaCodeParent(ccKalaCode, tedadKala, moshtaryModel.getCcNoeMoshtary(), moshtaryModel.getCcNoeSenf(), moshtaryModel.getDarajeh());
        mPresenter.onCheckJayezehParent(jayezehByccKalaCodeParentModels, tedadKala, mablaghForosh, ccKalaCode, ccDarkhastFaktor);
    }

    /**
     * get jayezeh details from DB
     * set jayezehByccKalaCodeModels in jayezehByccKalaCodeParentModels for show item recycler
     * @param ccJayezeh
     * @param tedadKala
     * @param mablaghForosh
     * @param ccKalaCode
     * @param ccDarkhastFaktor
     * @param position
     */

    @Override
    public void checkJayezeh(int ccJayezeh, int tedadKala, double mablaghForosh, int ccKalaCode, Long ccDarkhastFaktor, int position) {
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());

        double mablaghKol = mablaghForosh * tedadKala;
        ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeModels = jayezehDAO.getByccKalaCode(ccKalaCode, tedadKala, moshtaryModel.getCcNoeMoshtary(), moshtaryModel.getCcNoeSenf(), moshtaryModel.getDarajeh(), ccJayezeh);
        jayezehByccKalaCodeParentModels.get(position).setJayezehByccKalaCodeModels(jayezehByccKalaCodeModels);
        jayezehByccKalaCodeParentModels.get(position).setCcJayezehDetails(ccJayezeh);
        jayezehByccKalaCodeParentModels.get(position).setTedadKalaDetails(tedadKala);
        jayezehByccKalaCodeParentModels.get(position).setMablaghForoshDetails(mablaghForosh);
        jayezehByccKalaCodeParentModels.get(position).setCcKalaCodeDetails(ccKalaCode);
        jayezehByccKalaCodeParentModels.get(position).setCcDarkhastFaktorDetails(ccDarkhastFaktor);
        jayezehByccKalaCodeParentModels.get(position).setMablaghKol(mablaghKol);
        mPresenter.onCheckJayezeh(position);
    }
    @Override
    public void getRecyclerDetails() {
        int itemCountPerScreen = getItemCountPerScreen();
        ArrayList<KalaPhotoModel> kalaPhotoModels = getGallery();
        mPresenter.onGetRecyclerDetails( itemCountPerScreen , kalaPhotoModels );

    }
    @Override
    public int getItemCountPerScreen() {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        int itemCountPerScreen = systemConfigTabletDAO.getAll().get(0).getGoodsShowNumberEachPage();
        return itemCountPerScreen;
//        mPresenter.onGetItemCountPerScreen(itemCountPerScreen);
    }

    @Override
    public ArrayList<KalaPhotoModel> getGallery() {
        KalaPhotoDAO kalaPhotoDAO = new KalaPhotoDAO(mPresenter.getAppContext());
        ArrayList<KalaPhotoModel> kalaPhotoModels = kalaPhotoDAO.getAll();
        return kalaPhotoModels;
//        mPresenter.onGetGallery(kalaPhotoModels);
    }

}
