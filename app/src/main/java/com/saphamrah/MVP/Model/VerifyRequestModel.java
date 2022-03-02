package com.saphamrah.MVP.Model;


import static com.saphamrah.Utils.Constants.REST;
import static com.saphamrah.Utils.Constants.gRPC;

import android.os.AsyncTask;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.VerifyRequestMVP;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorJayezehDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrTakhfifDAO;
import com.saphamrah.DAO.DarkhastFaktorTakhfifDAO;
import com.saphamrah.DAO.ElamMarjoeePPCDAO;
import com.saphamrah.DAO.ElamMarjoeeSatrPPCDAO;
import com.saphamrah.DAO.ForoshandehEtebarDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GPSDataMashinDAO;
import com.saphamrah.DAO.GPSDataPpcDAO;
import com.saphamrah.DAO.JayezehDAO;
import com.saphamrah.DAO.JayezehEntekhabiDAO;
import com.saphamrah.DAO.JayezehSatrDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaDarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KalaElamMarjoeeDAO;
import com.saphamrah.DAO.KalaGorohDAO;
import com.saphamrah.DAO.KalaMojodiDAO;
import com.saphamrah.DAO.MarkazShahrMarkaziDAO;
import com.saphamrah.DAO.ModatVosolDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.NoeVosolMoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.RptKalaInfoDAO;
import com.saphamrah.DAO.TaghvimTatilDAO;
import com.saphamrah.DAO.TakhfifHajmiDAO;
import com.saphamrah.DAO.TakhfifHajmiSatrDAO;
import com.saphamrah.DAO.TakhfifNaghdyDAO;
import com.saphamrah.DAO.TakhfifSenfiDAO;
import com.saphamrah.DAO.TakhfifSenfiSatrDAO;
import com.saphamrah.MVP.View.VerifyRequestActivity;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorSatrModel;
import com.saphamrah.Model.DarkhastFaktorSatrTakhfifModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.DataTableModel;
import com.saphamrah.Model.ForoshandehEtebarModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.Model.JayezehEntekhabiModel;
import com.saphamrah.Model.JayezehModel;
import com.saphamrah.Model.JayezehSatrModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.ModatVosolModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryEtebarSazmanForoshModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.NoeVosolMoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.TaghvimTatilModel;
import com.saphamrah.Model.TakhfifHajmiSatrModel;
import com.saphamrah.Model.TakhfifHajmiTitrSatrModel;
import com.saphamrah.Model.TakhfifNaghdyModel;
import com.saphamrah.Model.TakhfifSenfiModel;
import com.saphamrah.Model.TakhfifSenfiSatrModel;
import com.saphamrah.Model.TakhfifSenfiTitrSatrModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.Discounts.TakhfifHajmi.CalculateHajmiDiscountBrand;
import com.saphamrah.PubFunc.Discounts.TakhfifHajmi.CalculateHajmiDiscountGorohKala;
import com.saphamrah.PubFunc.Discounts.TakhfifHajmi.CalculateHajmiDiscountKala;
import com.saphamrah.PubFunc.Discounts.DiscountCalculation;
import com.saphamrah.PubFunc.Discounts.TakhfifHajmi.CalculateHajmiDiscountTaminKonandeh;
import com.saphamrah.PubFunc.Discounts.TakhfifSenfi.CalculateSenfiDiscountBrand;
import com.saphamrah.PubFunc.Discounts.TakhfifSenfi.CalculateSenfiDiscountGorohKala;
import com.saphamrah.PubFunc.Discounts.TakhfifSenfi.CalculateSenfiDiscountKala;
import com.saphamrah.PubFunc.Discounts.TakhfifSenfi.CalculateSenfiDiscountTaminKonandeh;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Repository.RptJashnvarehForoshRepository;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class VerifyRequestModel implements VerifyRequestMVP.ModelOps
{

    private VerifyRequestMVP.RequiredPresenterOps mPresenter;
    ParameterChildDAO childParameterDAO = new ParameterChildDAO(BaseApplication.getContext());
    MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
    CompositeDisposable compositeDisposable;
    ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
    private static final String TAG = "VerifyRequestModel";
    public VerifyRequestModel(VerifyRequestMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getConfig()
    {
        boolean showBtnMarjoee = false;
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        showBtnMarjoee = foroshandehMamorPakhshModel.getCanGetMarjoee() == 1;
        mPresenter.onGetConfig(showBtnMarjoee);
    }

    @Override
    public void getRequestDetail(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels)
    {
        AsyncTaskRequestDetail asyncTaskRequestDetail = new AsyncTaskRequestDetail();
        asyncTaskRequestDetail.execute(kalaDarkhastFaktorSatrModels);
    }

    @Override
    public void getModatRoozRassgiri(int ccChildSelectNoeVosol)
    {
        boolean isSelectedVosolVajhNaghd = false;
        boolean isSelectedVosolResidNaghd = false;
        Log.d("modatVosol" , "ccChildSelectNoeVosol : " + ccChildSelectNoeVosol);
        Log.d("modatVosol" , "Constants.CC_CHILD_CODE_NOE_VOSOL_VAJH_NAGHD() : " + Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD());
        //todo shanbeh
        //if (ccChildSelectNoeVosol == Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD())
        int codeNoeVosolVajhNaghd2Setareh = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh()));
        int codeNoeVosolVajhNaghd1Setareh = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh()));
        int codeNoeVosolResidNaghd = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_Resid_Naghd()));
        if (ccChildSelectNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD()
                || ccChildSelectNoeVosol == codeNoeVosolVajhNaghd1Setareh
                || ccChildSelectNoeVosol == codeNoeVosolVajhNaghd2Setareh
                || ccChildSelectNoeVosol == codeNoeVosolResidNaghd)
        {
            isSelectedVosolVajhNaghd = true;
        }
        if(ccChildSelectNoeVosol == codeNoeVosolResidNaghd)
        {
            isSelectedVosolResidNaghd= true;
        }
        Log.d("modatVosol" , "isSelectedVosolVajhNaghd : " + isSelectedVosolVajhNaghd);
        Log.d("modatVosol" , "isSelectedVosolResidNaghd : " + isSelectedVosolResidNaghd);

        AsyncTaskCalculateModatVosol asyncTaskCalculateModatVosol = new AsyncTaskCalculateModatVosol(isSelectedVosolVajhNaghd, isSelectedVosolResidNaghd);
        asyncTaskCalculateModatVosol.execute();
    }

    @Override
    public void getCustomerInfo(int ccMoshtary, int ccSazmanForosh)
    {
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect());
        boolean showTarikhPishbiniTahvil = false;
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        ArrayList<ParameterChildModel> parameterChildModelsNoeAddress = childParameterDAO.getAllByccChildParameter(Constants.CC_CHILD_CC_NOE_ADDRESS_DARKHAST_TAHVIL() + "," + Constants.CC_CHILD_CC_NOE_ADDRESS_TAHVIL());
        String ccNoeAddresses = "-1";
        if (parameterChildModelsNoeAddress.size() > 0)
        {
            for (ParameterChildModel model : parameterChildModelsNoeAddress)
            {
                ccNoeAddresses += "," + model.getValue();
            }
        }
        else
        {
            ccNoeAddresses = "2,3";
        }
        ArrayList<MoshtaryAddressModel> addressModels = moshtaryAddressDAO.getByccMoshtaryAndccNoeAddress(ccMoshtary , ccNoeAddresses);
        if (addressModels.size() > 0 && (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3  || noeMasouliat == 6 || noeMasouliat ==8))
        {
            MarkazShahrMarkaziDAO markazShahrMarkaziDAO = new MarkazShahrMarkaziDAO(mPresenter.getAppContext());
            DarkhastFaktorDAO darkhastfaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            int countccMahaleh = markazShahrMarkaziDAO.getCountByccMahale(addressModels.get(0).getCcMahaleh());
            int TedadPishnahady = darkhastfaktorDAO.getTedadFaktorTarikhPishbinyTahvil();
            Log.d("verifyRequest" , "addressModels : " + addressModels.toString());
            Log.d("verifyRequest" , "countMahale : " + countccMahaleh + " , tedadPishnahadi : " + TedadPishnahady);
            int maxTedadPishnahdyTarikhTahvil = Integer.parseInt( new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_Tedad_Pishnahdy_Tarikh_Tahvil()));
            int checkShahrMarkaziTarikhTahvil = Integer.parseInt( new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_Check_Shahr_Markazi_Tarikh_Tahvil()));
            Log.d("verifyRequest" , "maxTedadPishnahdyTarikhTahvil : " + maxTedadPishnahdyTarikhTahvil + " , checkShahrMarkaziTarikhTahvil : " + checkShahrMarkaziTarikhTahvil);

            if(checkShahrMarkaziTarikhTahvil == 1)
            {
                if(countccMahaleh > 0 && TedadPishnahady < maxTedadPishnahdyTarikhTahvil)//check shahr markazi & TedadPishnahdyTarikhTahvil
                {
                    showTarikhPishbiniTahvil = true;
                }
            }
            else if(checkShahrMarkaziTarikhTahvil == 0)
            {
                if( TedadPishnahady < maxTedadPishnahdyTarikhTahvil)//check TedadPishnahdyTarikhTahvil
                {
                    showTarikhPishbiniTahvil = true;
                }
            }
        }
        mPresenter.onGetCustomerAddress(addressModels, showTarikhPishbiniTahvil);

        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);

        int ccChildParameterNoeVosol = selectFaktorShared.getInt(selectFaktorShared.getccChildCodeNoeVosol() , -1);
        if (ccChildParameterNoeVosol == -1)
        {
            ccChildParameterNoeVosol = calculateccChildParameterCodeNoeVosol(selectFaktorShared, ccDarkhastFaktor, ccMoshtary);
        }
        int modatVosol = calculateModatVosol(ccDarkhastFaktor , ccMoshtary);
        Log.d("vosol" , "ccChildParameterNoeVosol : " + ccChildParameterNoeVosol);
        Log.d("vosol" , "modatVosol : " + modatVosol);

        String parentParameter = "";

        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccChildParameter(String.valueOf(ccChildParameterNoeVosol));

        if (childParameterModels.size() > 0)
        {
            parentParameter = childParameterModels.get(0).getValue();
        }
        Log.d("vosol" , "parentParameter : " + parentParameter);
        ArrayList<ParameterChildModel> childParameterModelsVosols = childParameterDAO.getAllByccChildParameter(parentParameter);

        for (ParameterChildModel model : childParameterModelsVosols)
        {
            Log.d("vosol" , "model : " + model.toString());
        }


        NoeVosolMoshtaryDAO noeVosolMoshtaryDAO = new NoeVosolMoshtaryDAO(mPresenter.getAppContext());
        int CodeNoeVosolMoshtary = Integer.parseInt(childParameterModelsVosols.get(childParameterModelsVosols.size() - 1).getValue());
        String NameCodeNoeVosolMoshtary = childParameterModelsVosols.get(childParameterModelsVosols.size() - 1).getTxt();

        Log.d("vosol", "model childParameterModelsVosols : " + CodeNoeVosolMoshtary);
        Log.d("vosol", "model childParameterModelsVosols 0 : " + NameCodeNoeVosolMoshtary);

//        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
//        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
        ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels = noeVosolMoshtaryDAO.getByccNoeVosolMoshtary(CodeNoeVosolMoshtary , selectFaktorShared.getInt(selectFaktorShared.getCcMarkazSazmanForosh(),-1) , selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeMoshtary(),-1) , selectFaktorShared.getInt(selectFaktorShared.getMoshtaryDarajeh(),-1) );
        ParameterChildModel parameterChildModel = new ParameterChildModel();
        ArrayList<ParameterChildModel> NoeVosolsDarkhasts = new ArrayList<ParameterChildModel>();
        Log.d("vosol", "model noeVosolMoshtaryModels  : " + noeVosolMoshtaryModels.size());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO =new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
        int MaxtedadResid = foroshandehMamorPakhshDAO.getIsSelect().getMaxResidCheck();
        int MaxtedadResidNaghd = foroshandehMamorPakhshDAO.getIsSelect().getMaxResidNaghd();

        for (NoeVosolMoshtaryModel model : noeVosolMoshtaryModels) {
            Log.d("vosol", "model NoeVosolMoshtaryModel: " + model.toString());
        }

//        ArrayList<NoeVosolMoshtaryModel> noeVosolMoshtaryModels = noeVosolMoshtaryDAO.getByccNoeVosolMoshtary(CodeNoeVosolMoshtary);
//        ParameterChildModel parameterChildModel = new ParameterChildModel();
//        ArrayList<ParameterChildModel> NoeVosolsDarkhasts = new ArrayList<ParameterChildModel>();
//        Log.d("vosol" , "model noeVosolMoshtaryModels  : " + noeVosolMoshtaryModels.size());
//        for (NoeVosolMoshtaryModel model : noeVosolMoshtaryModels)
//        {
//            Log.d("vosol" , "model NoeVosolMoshtaryModel: " + model.toString());
//        }
//
//        if(noeVosolMoshtaryModels.size()==0)
//        {
//            parameterChildModel.setCcParameter(1);
//            parameterChildModel.setCcParameterChild(1);
//            parameterChildModel.setName(NameCodeNoeVosolMoshtary);
//            parameterChildModel.setValue(String.valueOf(CodeNoeVosolMoshtary));
//            parameterChildModel.setTxt(NameCodeNoeVosolMoshtary);
//            parameterChildModel.setCodeSort(1);
//            NoeVosolsDarkhasts.add(parameterChildModel);
//
//        }
//        else
//        {
//            Log.d("vosol", "size>0  - " + noeVosolMoshtaryModels.size());
//            for(NoeVosolMoshtaryModel model : noeVosolMoshtaryModels)
//            {
//                ParameterChildModel pModel = new ParameterChildModel();
//                pModel.setCcParameter(1);
//                pModel.setCcParameterChild(1);
//                pModel.setName(model.getNameNoeVosolAzMoshtary());
//                pModel.setValue(String.valueOf(model.getCodeNoeVosol()));
//                pModel.setTxt(model.getNameNoeVosol());
//                pModel.setCodeSort(1);
//                NoeVosolsDarkhasts.add(pModel);
//            }
//        }


        if (noeVosolMoshtaryModels.size() == 0) {
            parameterChildModel.setCcParameter(1);
            parameterChildModel.setCcParameterChild(1);
            parameterChildModel.setName(NameCodeNoeVosolMoshtary);
            parameterChildModel.setValue(String.valueOf(CodeNoeVosolMoshtary));
            parameterChildModel.setTxt(NameCodeNoeVosolMoshtary);
            parameterChildModel.setCodeSort(1);
            NoeVosolsDarkhasts.add(parameterChildModel);

        } else {
            Log.d("vosol", "size>0  - " + noeVosolMoshtaryModels.size());
            Log.d("vosol", "tedad Resid " + selectFaktorShared.getInt(selectFaktorShared.getTedadResid(),-1));
            Log.d("vosol", "tedad ResidNaghd " + selectFaktorShared.getInt(selectFaktorShared.getTedadResidNaghd(),-1));
            Log.d("vosol", "MaxtedadResid " + MaxtedadResid);
            Log.d("vosol", "MaxtedadResidNaghd " + MaxtedadResidNaghd);


            int CodeNoeVosolCheck= Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_CHECK()));
            int CodeNoeVosolResid= Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_RESID()));
            int CodeNoeVosolResidNaghd= Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_Resid_Naghd()));
            int CodeNoeVosolNaghd2Setareh= Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh()));
            for (NoeVosolMoshtaryModel model : noeVosolMoshtaryModels) {
                ParameterChildModel pModel = new ParameterChildModel();
                if((model.getCodeNoeVosol()==CodeNoeVosolResid || CodeNoeVosolMoshtary==CodeNoeVosolResidNaghd || CodeNoeVosolMoshtary==CodeNoeVosolCheck)
                        && selectFaktorShared.getInt(selectFaktorShared.getTedadResid(),-1) + selectFaktorShared.getInt(selectFaktorShared.getTedadResidNaghd(),-1) <= MaxtedadResid + MaxtedadResidNaghd) {
                    pModel.setCcParameter(1);
                    pModel.setCcParameterChild(1);
                    pModel.setName(model.getNameNoeVosolAzMoshtary());
                    pModel.setValue(String.valueOf(model.getCodeNoeVosol()));
                    pModel.setTxt(model.getNameNoeVosol());
                    pModel.setCodeSort(1);
                    NoeVosolsDarkhasts.add(pModel);
                }
                else if(model.getCodeNoeVosol()==CodeNoeVosolNaghd2Setareh)
                {
                    pModel.setCcParameter(1);
                    pModel.setCcParameterChild(1);
                    pModel.setName(model.getNameNoeVosolAzMoshtary());
                    pModel.setValue(String.valueOf(model.getCodeNoeVosol()));
                    pModel.setTxt(model.getNameNoeVosol());
                    pModel.setCodeSort(1);
                    NoeVosolsDarkhasts.add(pModel);
                }
                else if (model.getCodeNoeVosol()!=CodeNoeVosolResid || CodeNoeVosolMoshtary!=CodeNoeVosolResidNaghd || model.getCodeNoeVosol()!=CodeNoeVosolNaghd2Setareh)
                {
                    pModel.setCcParameter(1);
                    pModel.setCcParameterChild(1);
                    pModel.setName(model.getNameNoeVosolAzMoshtary());
                    pModel.setValue(String.valueOf(model.getCodeNoeVosol()));
                    pModel.setTxt(model.getNameNoeVosol());
                    pModel.setCodeSort(1);
                    NoeVosolsDarkhasts.add(pModel);
                }
            }
        }


//todo shanbeh
       mPresenter.onGetInfo(ccDarkhastFaktor , modatVosol , NoeVosolsDarkhasts);
//        mPresenter.onGetInfo(ccDarkhastFaktor , modatVosol , childParameterModelsVosols);
    }

    private int calculateModatVosol(long ccDarkhastFaktor , int ccMoshtary)
    {
        MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = moshtaryEtebarSazmanForoshDAO.getByccMoshtary(ccMoshtary);
        int modatVosol = moshtaryEtebarSazmanForoshModel.getModatVosol();
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        int codeNoeVosolVajhNaghd2Setareh = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh()));
        int codeNoeVosolVajhNaghd1Setareh = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh()));

        if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            if (darkhastFaktorModel != null)
            {
                //todo shanbe
                Log.d("vosol","CodeNoeVosolAzMoshtary" + darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
                Log.d("vosol","modatVosol" + modatVosol);

                if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD()
                        || darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == codeNoeVosolVajhNaghd1Setareh
                        || darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == codeNoeVosolVajhNaghd2Setareh)
                {
                    modatVosol = 0;
                    Log.d("vosol","modatVosol in if" + modatVosol);
                }
            }
        }
        else
        {
            Log.d("vosol","CodeNoeVosolAzMoshtary" + new MoshtaryDAO(mPresenter.getAppContext()).getByccMoshtary(ccMoshtary).getCodeNoeVosolAzMoshtary());
            Log.d("vosol","modatVosol" + modatVosol);
            int codeNoeVosol = new MoshtaryDAO(mPresenter.getAppContext()).getByccMoshtary(ccMoshtary).getCodeNoeVosolAzMoshtary();
            if (codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD()
                    || codeNoeVosol == codeNoeVosolVajhNaghd1Setareh
                    || codeNoeVosol == codeNoeVosolVajhNaghd2Setareh)
            {
                modatVosol = 0;
                Log.d("vosol","modatVosol in if" + modatVosol);
            }
        }
        return modatVosol;
    }

    private int calculateccChildParameterCodeNoeVosol(SelectFaktorShared selectFaktorShared , long ccDarkhastFaktor , int ccMoshtary)
    {
        int codeNoeVosol = new MoshtaryDAO(mPresenter.getAppContext()).getByccMoshtary(ccMoshtary).getCodeNoeVosolAzMoshtary();
        Log.d("verifyREq", "codeNoeVosolAzMoshtary : " + codeNoeVosol);
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        int ccChildParameterNoeVosol = -1;
        if (noeMasouliat == 4 || noeMasouliat == 5)
        {
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            if (darkhastFaktorModel != null)
            {
                if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD())
                {
                    ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_VAJH_NAGHD();
                }
                else if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK())
                {
                    ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_CHECK();
                }
                else if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID())
                {
                    ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_RESID();
                }
            }
        }
        else
        {
            Log.d("vosol" , "selectFaktorShared.getIsEtebarCheckBargashty() : " + selectFaktorShared.getBoolean(selectFaktorShared.getIsEtebarCheckBargashty() , false));
            /*if (selectFaktorShared.getBoolean(selectFaktorShared.getIsMojazForResid() , false))
            {
                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_MOJAZ_FOR_RESID();
            }
            else*/ if (selectFaktorShared.getBoolean(selectFaktorShared.getIsEtebarCheckBargashty() , false))
            {
                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_ETEBAR_CHECK_BARGASHTY();
            }
            else if (selectFaktorShared.getBoolean(selectFaktorShared.getIsEtebarAsnad() , false))
            {
                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_ETEBAR_CHECK_BARGASHTY();
            }
            else if (selectFaktorShared.getBoolean(selectFaktorShared.getMoshtaryForoshandehFlag() , false))
            {
                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_MOSHTARY_FOROSHANDE();
            }
            else if (codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD())
            {
                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_VAJH_NAGHD();
            }
            else if (codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK())
            {
                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_CHECK();
            }
            else if (selectFaktorShared.getBoolean(selectFaktorShared.getIsMojazForResid() , false) && codeNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID())
            {
                ccChildParameterNoeVosol = Constants.CC_VOSOL_IS_RESID();
            }
        }
        Log.d("verifyReq" , "ccChildParameterNoeVosol : " + ccChildParameterNoeVosol);
        return ccChildParameterNoeVosol;
    }

    @Override
    public void getRequestsList()
    {
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = shared.getLong(shared.getCcDarkhastFaktor() , 0L);
        int ccNoeMoshtary = shared.getInt(shared.getCcGorohNoeMoshtary(), -1);
        int ccMoshtaryGharardad = shared.getInt(shared.getCcMoshtaryGharardad(), -1);
        if (ccDarkhastFaktor != 0)
        {
            KalaDarkhastFaktorSatrDAO kalaDarkhastFaktorDAO = new KalaDarkhastFaktorSatrDAO(mPresenter.getAppContext());
            Log.d("Check1 verify",ccDarkhastFaktor + "," + ccNoeMoshtary + "," + ccMoshtaryGharardad);
            //todo majid
            ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = kalaDarkhastFaktorDAO.getByccDarkhast(ccDarkhastFaktor);

            //ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels = kalaDarkhastFaktorDAO.getByccDarkhastForDarkhastKala(ccDarkhastFaktor,ccNoeMoshtary,ccMoshtaryGharardad);

            String valueCalculateKalaAsasi = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_MOHASEBE_KALA_ASASI);
            if (valueCalculateKalaAsasi == null || valueCalculateKalaAsasi.trim().equals("") || valueCalculateKalaAsasi.trim().equals("1"))
            {
                setKalaAsasiOfRequestedList(kalaDarkhastFaktorSatrModels);
            }
            mPresenter.onGetRequestsList(kalaDarkhastFaktorSatrModels);
        }
        else
        {
            mPresenter.onGetRequestsList(new ArrayList<KalaDarkhastFaktorSatrModel>());
        }
    }

    private void setKalaAsasiOfRequestedList(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels)
    {
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        String ccKalaCodesOfKalaAsasi = shared.getString(shared.getCcKalaCodesOfKalaAsasi(), "");
        if (!ccKalaCodesOfKalaAsasi.trim().equals("") && !ccKalaCodesOfKalaAsasi.trim().equals("-1"))
        {
            String[] splittedccKalaCode = ccKalaCodesOfKalaAsasi.split(",");
            for (KalaDarkhastFaktorSatrModel kalaDarkhastFaktor : kalaDarkhastFaktorSatrModels)
            {
                for (String strccKalaCode : splittedccKalaCode)
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
                        setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "VerifyRequestModel", "", "setKalaAsasiOfRequestedList", "");
                    }
                }
            }
        }
    }

    @Override
    public void getMarjoeeList()
    {
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = shared.getLong(shared.getCcDarkhastFaktor() , 0L);
        if (ccDarkhastFaktor != 0)
        {
            KalaElamMarjoeeDAO kalaElamMarjoeeDAO = new KalaElamMarjoeeDAO(mPresenter.getAppContext());
            ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels = kalaElamMarjoeeDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            mPresenter.onGetMarjoeeList(kalaElamMarjoeeModels);
        }
        else
        {
            mPresenter.onGetMarjoeeList(new ArrayList<KalaElamMarjoeeModel>());
        }
    }

    @Override
    public void getBonusList()
    {

        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
																	
        if (ccDarkhastFaktor != -1)
        {
            DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
            ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = darkhastFaktorJayezehDAO.getByccDarkhastFaktorAndCodeNoe(ccDarkhastFaktor, DarkhastFaktorJayezehModel.CodeNoeJayezehAuto());

            int countJayezehInDarkhastFaktorJayzeh = darkhastFaktorJayezehDAO.getCountByccDarkhastFaktorAndCodeNoe(ccDarkhastFaktor , DarkhastFaktorJayezehModel.CodeNoeJayezehEntekhabi());
            int countccTakhfifJayezeh = selectFaktorShared.getString(selectFaktorShared.getCcTakhfifJayezes() , "").replace("," , "").length();
            boolean showAddBonusBtn = false;
            boolean haveBonus = false;
            Log.d("verifyRequest" , "Bonus countJayezehInDarkhastFaktorJayzeh:" + countJayezehInDarkhastFaktorJayzeh + " countccTakhfifJayezeh: " + countccTakhfifJayezeh);

            if (countJayezehInDarkhastFaktorJayzeh > 0 || countccTakhfifJayezeh > 0)
            {
                showAddBonusBtn = true;
            }
            if (darkhastFaktorJayezehModels.size() > 0)
            {
                haveBonus = true;
            }
            mPresenter.onGetBonus(darkhastFaktorJayezehModels , showAddBonusBtn, haveBonus);
        }

    }

    @Override
    public void calculateDiscounts(int ccChildParameterNoeVosol , int valueNoeVosol)
    {
        Log.d("verifyRequest" , " calculate discount starts");
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        int ccMoshtaryGharardad = selectFaktorShared.getInt(selectFaktorShared.getCcMoshtaryGharardad(), -1);
        int ccMoshtaryGharardadSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getMoshtaryGharardadccSazmanForosh(),-1);
        int ccMarkazSazmanForosh = selectFaktorShared.getInt(selectFaktorShared.getCcMarkazSazmanForosh(),0);
        Log.i("verifyRequest", "calculateDiscounts: ccMoshtaryGharardad\t"+ccMoshtaryGharardad+"ccMoshtaryGharardadSazmanForosh\t"+ccMoshtaryGharardadSazmanForosh);
        Log.i("verifyRequest", "calculateDiscounts: ccMarkazSazmanForosh\t"+ccMarkazSazmanForosh);

        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        if (ccDarkhastFaktor != -1)
        {
            AsyncTaskCalculateDiscount asyncTaskCalculateDiscount = new AsyncTaskCalculateDiscount(ccDarkhastFaktor,ccMoshtaryGharardad,ccMoshtaryGharardadSazmanForosh,ccMarkazSazmanForosh, ccChildParameterNoeVosol, valueNoeVosol, selectFaktorShared, new OnCalculateDiscountResponse()
            {
                @Override
                public void onFailedCalculate(int resId)
                {
                    mPresenter.onErrorCalculateDiscount(resId);
                }

                @Override
                public void onSuccessCalculate(boolean haveBonus,long ccDarkhastFaktor)
                {
                    mPresenter.onSuccessCalculateDiscount(haveBonus);
                    getDiscounts(ccDarkhastFaktor);
                }
            });
            asyncTaskCalculateDiscount.execute();
        }
        else
        {
            mPresenter.onErrorCalculateDiscount(R.string.errorFindccDarkhastFaktor);
        }
    }


    @Override
    public void updateExtraPropMablaghNahaeeFaktor(long ccDarkhastFaktor, int ccMoshtary, float sumMablaghKhales)
    {
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        darkhastFaktorDAO.updateExtraPropMablaghNahaeeFaktor(ccDarkhastFaktor , sumMablaghKhales);
        mPresenter.onUpdateMablaghNahaeeFaktor(ccDarkhastFaktor, ccMoshtary);
    }

    @Override
    public void deleteTakhfifJayezeForDarkhastFaktor(long ccDarkhastFaktor)
    {
        //delete from DarkhastFaktorTakhfif and DarkhastFaktorSatrTakhfif and DarkhastFaktorJayezeh
        DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
        DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = darkhastFaktorSatrDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        String ccDarkhastFaktorSatrs = "";
        for (DarkhastFaktorSatrModel model : darkhastFaktorSatrModels)
        {
            ccDarkhastFaktorSatrs = ccDarkhastFaktorSatrs + "," + model.getCcDarkhastFaktorSatr();
        }
        if (ccDarkhastFaktorSatrs.startsWith(","))
        {
            ccDarkhastFaktorSatrs = ccDarkhastFaktorSatrs.substring(1);
        }

        darkhastFaktorTakhfifDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
        darkhastFaktorSatrTakhfifDAO.deleteByccDarkhastFaktorSatr(ccDarkhastFaktorSatrs);
        darkhastFaktorJayezehDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
        darkhastFaktorSatrDAO.deleteJayezehForccDarkhastFaktor(ccDarkhastFaktor);

       // kalaMojodiDAO.updateMojodiForReturnJayezeh(ccDarkhastFaktor);
        kalaMojodiDAO.deleteJayzeheByccDarkhastFaktor(ccDarkhastFaktor);
    }


    @Override
    public void getDiscounts(long ccDarkhastFaktor)
    {
        DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
        //todo jayezeh
        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels = darkhastFaktorTakhfifDAO.getByccDarkhastFaktorWithoutArzeshAfzodeh(ccDarkhastFaktor);
        mPresenter.onGetDiscounts(darkhastFaktorTakhfifModels);
    }

    @Override
    public void getTarikhPishbiniTahvilInfo()
    {
        int ccMarkaz = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect().getCcMarkazForosh();
        int maxTedadRooz = 0;
        try
        {
            maxTedadRooz = Integer.parseInt(new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_MAX_ROOZ_PISHBINI_TAHVIL));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "VerifyRequestModel", "", "getTarikhPishbiniTahvilInfo", "");
        }
        List<TaghvimTatilModel> taghvimTatilModels = new TaghvimTatilDAO(mPresenter.getAppContext()).getFromNow();

        mPresenter.onGetTarikhPishbiniTahvilInfo(maxTedadRooz, taghvimTatilModels);
    }

    @Override
    public void updateDarkhastFaktor(String modatVosol, int modatRoozRaasGiri, double sumMablaghKol , double sumMablaghKhales , float mablaghArzeshAfzoode , float sumTakhfifat , int codeNoeVosol , String nameNoeVosol , int ccAddress)
    {
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider();
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        int ccMoshtary = selectFaktorShared.getInt(selectFaktorShared.getCcMoshtary(),-1);
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        // update location from SelectFaktorShared if location equal 0,0
        if (darkhastFaktorModel.getLatitude() == 0)
        {
            float lat = Float.parseFloat(selectFaktorShared.getString(selectFaktorShared.getLatitude(), "0.0"));
            darkhastFaktorModel.setLatitude(lat);
        }
        if (darkhastFaktorModel.getLongitude() == 0)
        {
            float lon = Float.parseFloat(selectFaktorShared.getString(selectFaktorShared.getLongitude(), "0.0"));
            darkhastFaktorModel.setLongitude(lon);
        }
        darkhastFaktorModel.setCcDarkhastFaktor(ccDarkhastFaktor);
        darkhastFaktorModel.setCodeNoeVosolAzMoshtary(codeNoeVosol);
        darkhastFaktorModel.setNameNoeVosolAzMoshtary(nameNoeVosol);
        darkhastFaktorModel.setCcAddressMoshtary(ccAddress);
        darkhastFaktorModel.setModatRoozRaasGiri(modatRoozRaasGiri);
        darkhastFaktorModel.setModateVosol(Integer.valueOf(modatVosol));
        darkhastFaktorModel.setMablaghKolFaktor(sumMablaghKol);
        darkhastFaktorModel.setMablaghKolDarkhast(sumMablaghKol);
        Log.d("verifyRequest" , "update faktor , sumMablaghKhales : " + sumMablaghKhales);
        darkhastFaktorModel.setMablaghKhalesFaktor(sumMablaghKhales);
        darkhastFaktorModel.setMablaghKhalesDarkhast(sumMablaghKhales);
        Log.d("verifyRequest" , "update faktor , getMablaghKhalesFaktor : " + darkhastFaktorModel.getMablaghKhalesFaktor());
        if(noeMasouliat == 1  || noeMasouliat == 6 || noeMasouliat ==8) //1-Foroshandeh-Sard
        {
            darkhastFaktorModel.setCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TAEED());
        }
        else if(noeMasouliat == 2 || noeMasouliat == 6) //2-Foroshandeh-Garm   6-SarparastForoshandeh
        {
            darkhastFaktorModel.setCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TAEED());
        }
        else if(noeMasouliat == 3)//3-Foroshandeh-Smart
        {
            darkhastFaktorModel.setCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TAEED());
        }
        else if (noeMasouliat == 4) //4-MamorPakhsh-Sard
        {
            darkhastFaktorModel.setExtraProp_IsSend(0);
            darkhastFaktorModel.setExtraProp_IsOld(0);
            darkhastFaktorModel.setCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TAEED_MAMOR_PAKHSH());
            darkhastFaktorModel.setNameVazeiat(mPresenter.getAppContext().getResources().getString(R.string.taeedDarkhastMamorPakhsh));
        }
        else if (noeMasouliat == 5) //5-MamorPakhsh-Smart
        {
            darkhastFaktorModel.setExtraProp_IsSend(0);
            darkhastFaktorModel.setExtraProp_IsOld(0);
            darkhastFaktorModel.setCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TAEED_MAMOR_PAKHSH());
            darkhastFaktorModel.setNameVazeiat(mPresenter.getAppContext().getResources().getString(R.string.taeedDarkhastMamorPakhsh));
        }
        Log.d("darkhastFaktorModel" , "before update : " +  darkhastFaktorModel.toString());
        String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
        darkhastFaktorModel.setSaatKhorojAzMaghazeh(currentDate);
        darkhastFaktorModel.setExtraProp_MablaghArzeshAfzoodeh(mablaghArzeshAfzoode);
        darkhastFaktorModel.setExtraProp_SumTakhfifat(sumTakhfifat);
        darkhastFaktorModel.setExtraProp_MablaghNahaeeFaktor(sumMablaghKhales /*(sumMablaghKol - sumTakhfifat) + sumMablaghKhales*/);
        darkhastFaktorModel.setPPC_VersionNumber(new PubFunc().new DeviceInfo().getCurrentVersion(mPresenter.getAppContext()));
        darkhastFaktorModel = darkhastFaktorDAO.update(darkhastFaktorModel);
        Log.d("darkhastFaktorModel" , "after update : " +  darkhastFaktorModel.toString());

        int ccMasirRooz = selectFaktorShared.getInt(selectFaktorShared.getCcMasirRooz() , 0);
        int ccMamorPakhsh = 0;
        int ccForoshandeh = selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh() , 0);
//        if (noeMasouliat == 4 || noeMasouliat == 5)
//        {
//            ccMamorPakhsh = foroshandehMamorPakhshModel.getCcMamorPakhsh();
//            ccForoshandeh = 0;
//            insertGPSDataMashinPPC(locationProvider, ccDarkhastFaktor, ccMamorPakhsh, foroshandehMamorPakhshModel, selectFaktorShared.getInt(selectFaktorShared.getCcMoshtary() , 0), currentDate);
//        }
        switch (darkhastFaktorModel.getCcDarkhastFaktorNoeForosh())
        {
            case Constants.FAKTOR_GHATI:
                insertGPSDataPPC(locationProvider, foroshandehMamorPakhshModel.getCcAfrad(), ccForoshandeh, ccMamorPakhsh, ccMasirRooz, ccDarkhastFaktor ,0L , ccMoshtary);
                break;
            case Constants.FAKTOR_HAVALEH:
                insertGPSDataPPC(locationProvider, foroshandehMamorPakhshModel.getCcAfrad(), ccForoshandeh, ccMamorPakhsh, ccMasirRooz,0L, ccDarkhastFaktor, ccMoshtary);
                break;

        }
    }

    private void insertGPSDataPPC(PubFunc.LocationProvider locationProvider, int ccAfrad, int ccForoshandeh, int ccMamorPakhsh, int ccMasirRooz, long ccDarkhastFaktor,long ccDarkhastHavaleh , int ccMoshtary)
    {
        GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
        GPSDataModel gpsDataModel = new GPSDataModel();
        gpsDataModel.setLatitude(locationProvider.getLatitude());
        gpsDataModel.setLongitude(locationProvider.getLongitude());
        gpsDataModel.setSpeed(0);
        gpsDataModel.setAltitude(locationProvider.getAltitude());
        gpsDataModel.setAccurancy(locationProvider.getAccuracy());
        gpsDataModel.setBearing(locationProvider.getBearing());
        gpsDataModel.setElapsedRealTimeNanos(0);
        gpsDataModel.setCcMamorPakhsh(ccMamorPakhsh);
        gpsDataModel.setExtraProp_IsSend(0);
        gpsDataModel.setCcMasir(ccMasirRooz);
        gpsDataModel.setCcForoshandeh(ccForoshandeh);
        gpsDataModel.setCcAfrad(ccAfrad);
        gpsDataModel.setDistance(0D);
        gpsDataModel.setTarikh(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(Calendar.getInstance().getTime()));
        gpsDataModel.setCcDarkhastFaktor(ccDarkhastFaktor);
        gpsDataModel.setCcDarkhastHavaleh(ccDarkhastHavaleh);
        gpsDataModel.setCcMoshtary(ccMoshtary);
        gpsDataPpcDAO.insert(gpsDataModel);
    }



    @Override
    public void deleteBonus(long ccDarkhastFaktor , boolean openSelectBonusActivity)
    {
        String codeNoeTakhfif = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_NAGHDI());
        DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
        darkhastFaktorTakhfifDAO.deleteTakhfifNaghdiByccDarkhastFaktor(ccDarkhastFaktor , codeNoeTakhfif , mPresenter.getAppContext().getResources().getString(R.string.takhfifNaghdiforJayezeh));

        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
        darkhastFaktorJayezehDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);

        mPresenter.onDeleteBonus(openSelectBonusActivity);
    }


    /**
     * بررسی اطلاعات فاکتور هنگام خروج از این اکتیویتی
     * @param clickedBottomBarposition
     * @param mablaghKol
     * @param sumTakhfifat
     * @param mablaghFaktor
     * @param sumMablaghBaArzeshAfzoode
     * @param ccAddress
     * @param modatVosol
     * @param codeNoeVosol
     * @param nameNoeVosol
     * @param modatRoozRaasGiri
     * @param vaznFaktor
     * @param hajmFaktor
     */
    @Override
    public void checkData(int clickedBottomBarposition ,double mablaghKol, float sumTakhfifat, double mablaghFaktor , long sumMablaghBaArzeshAfzoode , int ccAddress , int modatVosol , int codeNoeVosol, String nameNoeVosol , int modatRoozRaasGiri , double vaznFaktor , double hajmFaktor, Date tarikhPishbiniTahvil, int tedadAghlam)
    {
        boolean hasError = false;
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(), -1);
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(BaseApplication.getContext());
        ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = darkhastFaktorSatrDAO.getByccDarkhastFaktorAndNoeKala(ccDarkhastFaktor , 2);


       ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
       String codeNoeVosolResid = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_RESID());
       String codeNoeVosolNaghd = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD());
        String codeNoeVosolResidNaghd = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_Resid_Naghd());

        ArrayList<ParameterChildModel> childParameterModelsGorohMoshtary = childParameterDAO.getAllByccChildParameter(Constants.CC_CHILD_GOROH_MOSHTARY_KHORDE() + " , " + Constants.CC_CHILD_GOROH_MOSHTARY_OMDE());
       int ccGorohKhorde = 0;
       int ccGorohOmde = 0;
       int ccMoshtary = selectFaktorShared.getInt(selectFaktorShared.getCcMoshtary(), -1);
       int ccForoshandeh = selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh(), -1);
       for (ParameterChildModel model : childParameterModelsGorohMoshtary) {
           if (model.getCcParameterChild() == Constants.CC_CHILD_GOROH_MOSHTARY_KHORDE()) {
               ccGorohKhorde = Integer.parseInt(model.getValue());
           } else if (model.getCcParameterChild() == Constants.CC_CHILD_GOROH_MOSHTARY_OMDE()) {
               ccGorohOmde = Integer.parseInt(model.getValue());
           }
       }

       /*
       چک کردن لیست جایزه برای زمانی که از یک جایزه دوبار ثبت شود
        */
        ArrayList<DarkhastFaktorSatrModel> jayezehDarkhastFaktorSatrModels = darkhastFaktorSatrDAO.getByccDarkhastFaktorAndNoeKala(ccDarkhastFaktor, 2);
        boolean hasDuplication=new PubFunc().new DAOUtil().hasDuplicates(jayezehDarkhastFaktorSatrModels);
        if (hasDuplication) {
            hasError = true;
            mPresenter.onErrorCheck(R.string.errorSabtJayezeh, "");
            return;
        }

       if (ccAddress == 0) {
           hasError = true;
           mPresenter.onErrorCheck(R.string.errorSelectAddress, "");
           return;
       }
       if (mablaghFaktor == 0) {
           hasError = true;
           mPresenter.onErrorCheck(R.string.errorMablaghKhalesWasZero, "");
           return;
       }
       if (modatVosol > modatRoozRaasGiri) {
           hasError = true;
           Log.d("checkData", "modat Vosol : " + modatVosol + " , modat rooz raas giri : " + modatRoozRaasGiri);
           mPresenter.onErrorCheck(R.string.errorModatVosolBiggerThanRaasGiri, "");
           return;
       }
       if ((modatVosol > 0 && String.valueOf(codeNoeVosol).equals(codeNoeVosolNaghd))
        ||(modatVosol > 2 && String.valueOf(codeNoeVosol).equals(codeNoeVosolResidNaghd)))
        {
           Log.d("checkData", " modat rooz raas giri : " + modatRoozRaasGiri + " , modatvosol:" + modatVosol + " ,codenoevosol:" + codeNoeVosolNaghd);
           mPresenter.onErrorCheck(R.string.errorModatVosolNaghdBiggerThanZero, "");
           return;
       }

        if (selectFaktorShared.getFloat(selectFaktorShared.getVaznMashin(), -1) < vaznFaktor && clickedBottomBarposition!=2)
        {
            Log.d("checkData", "vazn mashin : " + selectFaktorShared.getFloat(selectFaktorShared.getVaznMashin(), -1) + " , vazn faktor : " + vaznFaktor);
            hasError = true;
            String vaznMashin =String.valueOf(selectFaktorShared.getFloat(selectFaktorShared.getVaznMashin(), -1));
            mPresenter.onErrorVazn(R.string.errorVaznFaktorBiggerVaznMashin,String.valueOf(vaznFaktor),vaznMashin);


            return;
        }
        if (selectFaktorShared.getFloat(selectFaktorShared.getHajmMashin(), -1) < hajmFaktor && clickedBottomBarposition!=2)
        {
            hasError = true;
            mPresenter.onErrorCheck(R.string.errorHajmFaktorBiggerVaznMashin, "");
            return;
        }
        boolean isMoshtaryJadid = selectFaktorShared.getBoolean(selectFaktorShared.getIsMoshtaryJadid(), false);
        if (isMoshtaryJadid)
        {
            int ccGorohNoeMoshtary = selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeMoshtary(), 0);
            if (ccGorohNoeMoshtary == ccGorohKhorde && mablaghFaktor > selectFaktorShared.getLong(selectFaktorShared.getMablaghMaxFaktorKhordeh_MoshtaryJadid(), 0))
            {
                mPresenter.onErrorCheck(R.string.errorCostBiggerThanMax, String.valueOf(selectFaktorShared.getLong(selectFaktorShared.getMablaghMaxFaktorKhordeh_MoshtaryJadid(), 0)));
                return;
            }
            else if (ccGorohNoeMoshtary == ccGorohOmde && mablaghFaktor > selectFaktorShared.getLong(selectFaktorShared.getMablaghMaxFaktorOmdeh_MoshtaryJadid(), 0))
            {
                mPresenter.onErrorCheck(R.string.errorCostBiggerThanMax, String.valueOf(selectFaktorShared.getLong(selectFaktorShared.getMablaghMaxFaktorOmdeh_MoshtaryJadid(), 0)));
                return;
            }
        }
        DecimalFormat df = new DecimalFormat("#,###");
        if ((mablaghFaktor < selectFaktorShared.getFloat(selectFaktorShared.getHadeAghalMablaghKharid(), 0)) && (selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeSenf(), 0) != 345 /*ccNoeSenfMoshtary_NemoonehKala = 345*/))
        {
            if (!isMoshtaryJadid)
            {
                if(clickedBottomBarposition!=2)//Page SabtDarkhast
                {
                    mPresenter.onErrorCheck(R.string.errorMablaghLessThanMin, String.valueOf(df.format(selectFaktorShared.getFloat(selectFaktorShared.getHadeAghalMablaghKharid(), 0))));
                    return;
                }

            }
            else
            {
                if (mablaghFaktor < selectFaktorShared.getFloat(selectFaktorShared.getHadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid(), 0) && clickedBottomBarposition!=2)
                {
                    mPresenter.onErrorCheck(R.string.errorMablaghLessThanMin, String.valueOf(df.format(selectFaktorShared.getFloat(selectFaktorShared.getHadeAghalMablaghKharidBaYekDarajehKamtarForMoshtaryJadid(), 0))));
                    return;
                }
            }
        }
        Log.d("checkData","tedadAghlam:"+ tedadAghlam + ", HadeAghalTedadKharid:" + selectFaktorShared.getFloat(selectFaktorShared.getHadeAghalTedadKharid(), 0));
        if ((tedadAghlam < selectFaktorShared.getFloat(selectFaktorShared.getHadeAghalTedadKharid(), 0)) && (selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeSenf(), 0) != 350 /*ccNoeSenfMoshtary_NemoonehKala = 345*/))
        {

            if(clickedBottomBarposition!=2)//Page SabtDarkhast
            {
                mPresenter.onErrorCheck(R.string.errorTedadLessThanMin, String.valueOf(df.format(selectFaktorShared.getFloat(selectFaktorShared.getHadeAghalTedadKharid(), 0))));
                return;
            }

        }
        //---------------------------- Etebar -------------------------------------------
        int Etebar = CheckEtebar(codeNoeVosol, sumMablaghBaArzeshAfzoode, ccMoshtary, ccDarkhastFaktor, ccForoshandeh, modatVosol);


        Log.d("etebar","checketebar " + Etebar );
        if (Etebar == 1)
        {
            mPresenter.onErrorCheck(R.string.errorMinEtebarRialiForoshandeh, "");
            return;
        }
        else if (Etebar == 2)
        {
            mPresenter.onErrorCheck(R.string.errorMinSaghfEtebarRialiForoshandeh, "");
            return;
        }
        else if (Etebar == 3)
        {
            mPresenter.onErrorCheck(R.string.errorMinEtebarRialiMoshtary, "");
            return;
        }
        else if (Etebar == 4)
        {
            mPresenter.onErrorCheck(R.string.errorMinSaghfEtebarRialiMoshtary, "");
            return;
        }
        else if (Etebar == 5)
        {
            mPresenter.onErrorCheck(R.string.errorMinEtebarTedadyForoshandeh, "");
            return;
        }
        else if (Etebar == 6)
        {
            mPresenter.onErrorCheck(R.string.errorMinSaghfEtebarTedadyForoshandeh, "");
            return;
        }
        else if (Etebar == 7)
        {
            mPresenter.onErrorCheck(R.string.errorMinEtebarTedadyMoshtary, "");
            return;
        }
        else if (Etebar==8)
        {
            mPresenter.onErrorCheck(R.string.errorMinSaghfEtebarTedadyMoshtary, "");
            return;
        }
        else if (Etebar == 9)
        {
            mPresenter.onErrorCheck(R.string.errorMinEtebarModatForoshandeh, "");
            return;
        }
        else if (Etebar == 10)
        {
            mPresenter.onErrorCheck(R.string.errorMinSaghfEtebarModatForoshandeh, "");
            return;
        }
        else if (Etebar == 11)
        {
            mPresenter.onErrorCheck(R.string.errorMinEtebarModatMoshtary, "");
            return;
        }
        else if (Etebar == 12)
        {
            mPresenter.onErrorCheck(R.string.errorMinSaghfEtebarModatMoshtary, "");
            return;
        }

        //-------------------------- Check Noe Vosol ------------------------------------
        DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());

        String ccTakhfifs = selectFaktorShared.getString(selectFaktorShared.getCcTakhfifJayezes() , "");
        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels = darkhastFaktorJayezehDAO.getByccDarkhastFaktorAndCodeNoe(ccDarkhastFaktor , DarkhastFaktorJayezehModel.CodeNoeJayezehEntekhabi());
        Log.d("takhfif" , "ccTakhfifs : " + ccTakhfifs);
        Log.d("takhfif" , "darkhastFaktorJayezehModels : " + darkhastFaktorJayezehModels.size());
        if (ccTakhfifs.replace("," , "").length() > 0 || darkhastFaktorJayezehModels.size() > 0)
        {
            if(clickedBottomBarposition!=2)//Page SabtDarkhast
            {
                ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels = darkhastFaktorTakhfifDAO.getByccDarkhastFaktorsAndccTakhfifs(ccDarkhastFaktor, ccTakhfifs);
                String sharhTakhfifs = "";
                for (DarkhastFaktorTakhfifModel model : darkhastFaktorTakhfifModels) {
                    sharhTakhfifs += model.getSharhTakhfif() + "\n";
                }
                for (DarkhastFaktorJayezehModel jayezehModel : darkhastFaktorJayezehModels) {
                    Log.d("jayezeh", "jayezehModel : " + jayezehModel.toString());
                    sharhTakhfifs += jayezehModel.getSharh() + "\n";
                }

                mPresenter.onErrorCheck(R.string.errorSelectBonus, sharhTakhfifs);
                return;
            }
        }

        /*if (darkhastFaktorTakhfifDAO.getCountJayezehByccDarkhastFaktor(ccDarkhastFaktor) > 0 && darkhastFaktorJayezehDAO.getByccDarkhastFaktor(ccDarkhastFaktor).size() == 0)
        {
            mPresenter.onErrorCheck(R.string.errorSelectBonus , "");
            return;
        }*/

        try
        {
            String strTarikhPishbiniTahvil = tarikhPishbiniTahvil == null ? null : new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(tarikhPishbiniTahvil);
            double MablaghArzeshAfzoodeh = sumMablaghBaArzeshAfzoode - (mablaghKol - sumTakhfifat);
            Log.d("verifyRequest" , "arzeshAfozde in check : " + MablaghArzeshAfzoodeh + " , sumMablaghBaArzeshAfzoode : " + sumMablaghBaArzeshAfzoode + " , (mablaghKol - sumTakhfifat) : " +(mablaghKol - sumTakhfifat));

            //int ccForoshandeh = selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh() , 0);
            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect());
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            DarkhastFaktorModel entity = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            entity.setTarikhPishbinyTahvil(strTarikhPishbiniTahvil);
            Log.d("verifyRequest" , "tarikhPishbiniTahvil : " + entity.getTarikhPishbinyTahvil());
            entity.setCodeNoeVosolAzMoshtary(codeNoeVosol);
            entity.setNameNoeVosolAzMoshtary(nameNoeVosol);
            entity.setCcAddressMoshtary(ccAddress);
            entity.setModatRoozRaasGiri(modatRoozRaasGiri);
            entity.setModateVosol(modatVosol);
            entity.setMablaghKolFaktor(mablaghKol);
            entity.setMablaghKhalesFaktor(mablaghFaktor);
            Log.d("verifyRequest" , "sumMablaghKol in checkData : " + mablaghKol + " , mablaghKol in entity : " + entity.getMablaghKolFaktor());

            entity.setCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TAEED());
            if (noeMasouliat == 4 || noeMasouliat == 5) //4-MamorPakhsh-Sard //5-MamorPakhsh-Smart
            {
                entity.setExtraProp_IsSend(0);
                entity.setExtraProp_IsOld(0);
                entity.setCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TAEED_MAMOR_PAKHSH());
                entity.setNameVazeiat(mPresenter.getAppContext().getResources().getString(R.string.taeedDarkhastMamorPakhsh));
            }
            entity.setSaatKhorojAzMaghazeh(new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(new Date()));
            entity.setExtraProp_MablaghArzeshAfzoodeh((float) MablaghArzeshAfzoodeh);
            entity.setExtraProp_SumTakhfifat(sumTakhfifat);
            Log.d("mablaghnahaee" , "entity.getExtraProp_MablaghArzeshAfzoodeh : " + entity.getExtraProp_MablaghArzeshAfzoodeh());
            Log.d("mablaghnahaee" , "mablaghKol : " + mablaghKol + " , sumTakhfifat : " + sumTakhfifat + " , MablaghArzeshAfzoodeh : " + MablaghArzeshAfzoodeh);
            Log.d("mablaghnahaee" , "setExtraProp_MablaghNahaeeFaktor : " + entity.getExtraProp_MablaghNahaeeFaktor());
            entity.setExtraProp_MablaghNahaeeFaktor( (float) ((mablaghKol - sumTakhfifat) + MablaghArzeshAfzoodeh));
            Log.d("mablaghnahaee" , "setExtraProp_MablaghNahaeeFaktor 2 : " + entity.getExtraProp_MablaghNahaeeFaktor());
            darkhastFaktorDAO.update(entity);
            //-----------------------------------------------------
            darkhastFaktorDAO.deleteAllFaktorTaeedNashode();
            if (!selectFaktorShared.getBoolean(selectFaktorShared.getVerifiedMarjoee() , false))
            {
                ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
                ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());

                elamMarjoeePPCDAO.deleteAllByccDarkhastFaktor(ccDarkhastFaktor);
                elamMarjoeeSatrPPCDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
            }
            mPresenter.onSuccessCheck(clickedBottomBarposition);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "checkData", "");
            mPresenter.onErrorCheck(R.string.errorOperation , "");
        }

    }


    private boolean insertDariaftPardakht(long ccDarkhastFaktor , int ccMoshtary , int ccForoshandeh)
    {
        long ccDariaftPardakht;
        long MablaghKolMarjoeeMoshtary = 0;
        //int ccAfradForoshandeh = 0;
        DariaftPardakhtPPCDAO dariaftPardakhtDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        DariaftPardakhtPPCModel dariaftPardakhtPPC = new DariaftPardakhtPPCModel();
        try
        {
            ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
            MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
            //ccAfradForoshandeh = foroshandehMamorPakhshModel.getCcAfrad();
            ElamMarjoeeSatrPPCDAO elammarjoeesatrDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
            MablaghKolMarjoeeMoshtary = elammarjoeesatrDAO.getSumMablaghMarjoeeByccDarkhastFaktor(ccDarkhastFaktor);

            if (MablaghKolMarjoeeMoshtary != 0)
            {
                //insertKardexAnbarakForoshandeh(ccAfradForoshandeh, ccMoshtary);
                String codeNoeVosolMarjoee = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_CODE_NOE_VOSOL_MARJOEE());
                ccDariaftPardakht = dariaftPardakhtDAO.getMarjoeeByccMoshtary(ccMoshtary , codeNoeVosolMarjoee);
                if(ccDariaftPardakht == 0)
                {
                    dariaftPardakhtPPC.setCcMarkazAnbar(foroshandehMamorPakhshModel.getCcMarkazAnbarVosol());
                    dariaftPardakhtPPC.setCcMarkazForosh(foroshandehMamorPakhshModel.getCcMarkazForosh());
                    dariaftPardakhtPPC.setCcMarkazSazmanForoshSakhtarForosh(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
                    dariaftPardakhtPPC.setCodeNoeVosol(Integer.parseInt(codeNoeVosolMarjoee));
                    dariaftPardakhtPPC.setNameNoeVosol(mPresenter.getAppContext().getResources().getString(R.string.marjoee));
                    dariaftPardakhtPPC.setCcShomarehHesab(0);
                    dariaftPardakhtPPC.setSharhShomarehHesab("");
                    dariaftPardakhtPPC.setZamaneSabt(new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(new Date()));
                    dariaftPardakhtPPC.setNameSahebHesab(moshtaryModel.getNameMoshtary());
                    dariaftPardakhtPPC.setCcBankSanad(0);
                    dariaftPardakhtPPC.setNameBankSanad("");
                    dariaftPardakhtPPC.setNameShobehSanad("");
                    dariaftPardakhtPPC.setCodeShobehSanad("");
                    dariaftPardakhtPPC.setShomarehHesabSanad("");
                    dariaftPardakhtPPC.setCcNoeHesabSanad(0);
                    dariaftPardakhtPPC.setNameNoeHesabSanad("");
                    dariaftPardakhtPPC.setCodeNoeCheck(0);
                    dariaftPardakhtPPC.setNameNoeCheck("");
                    dariaftPardakhtPPC.setShomarehSanad("");
                    dariaftPardakhtPPC.setTarikhSanadShamsi("");
                    dariaftPardakhtPPC.setTarikhSanad(null);
                    dariaftPardakhtPPC.setMablagh(MablaghKolMarjoeeMoshtary);
                    dariaftPardakhtPPC.setMablaghMandeh(darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor());
                    dariaftPardakhtPPC.setCcDariaftPardakhtLink(0);
                    dariaftPardakhtPPC.setCcAfradErsalKonandeh(foroshandehMamorPakhshModel.getCcAfrad());
                    dariaftPardakhtPPC.setCcDarkhastFaktor(ccDarkhastFaktor);
                    dariaftPardakhtPPC.setCcKardex(0);
                    dariaftPardakhtPPC.setCodeVazeiat(0);
                    dariaftPardakhtPPC.setCcMoshtary(ccMoshtary);
                    dariaftPardakhtPPC.setIsCheckMoshtary(0);
                    dariaftPardakhtPPC.setIsPishDariaft(0);
                    dariaftPardakhtPPC.setTabdil_NaghdBeFish(0);
                    dariaftPardakhtPPC.setExtraProp_IsSend(1); //CodeNoeVosol_moshatry==CodeNoeVosol.Marjoee.getValue()?1:0
                    dariaftPardakhtPPC.setExtraProp_IsDirkard(0);
                    ccDariaftPardakht = dariaftPardakhtDAO.insert(dariaftPardakhtPPC);
                    Log.d("marjoee" , "ccDariaftPardakht after insert : " + ccDariaftPardakht);
                }
                double MablaghDariaftPardakhtDarkhastFaktor = MablaghKolMarjoeeMoshtary < darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor() ? MablaghKolMarjoeeMoshtary : darkhastFaktorModel.getExtraProp_MablaghNahaeeFaktor();

                boolean insertResult = insertMarjoeeDariaftPardakhtDarkhastFaktor(foroshandehMamorPakhshModel, darkhastFaktorModel, ccDariaftPardakht,MablaghKolMarjoeeMoshtary,(long) MablaghDariaftPardakhtDarkhastFaktor);
                boolean updateResult = darkhastFaktorDAO.updateMandehDarkhastFaktor(ccDarkhastFaktor);
                if (!insertResult || !updateResult)
                {
                    return false;
                }
            }
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "insertDariaftPardakht", "");
            mPresenter.onErrorCheck(R.string.errorOperation , "");
            return false;
        }
    }


    private boolean insertMarjoeeDariaftPardakhtDarkhastFaktor(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel, DarkhastFaktorModel darkhastFaktorModel , long ccDariaftPardakht, long MablaghKolMarjoeeMoshtary, long MablaghDariaftPardakhtDarkhastFaktor)
    {
        try
        {
            String codeNoeVosolMarjoee = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_CODE_NOE_VOSOL_MARJOEE());
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO= new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPC = new DariaftPardakhtDarkhastFaktorPPCModel();
            dariaftPardakhtDarkhastFaktorPPC.setCcDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
            dariaftPardakhtDarkhastFaktorPPC.setCcDariaftPardakht(ccDariaftPardakht);
            dariaftPardakhtDarkhastFaktorPPC.setCodeNoeVosol(Integer.parseInt(codeNoeVosolMarjoee));
            dariaftPardakhtDarkhastFaktorPPC.setNameNoeVosol(mPresenter.getAppContext().getResources().getString(R.string.marjoee));
            dariaftPardakhtDarkhastFaktorPPC.setShomarehSanad("0");
            dariaftPardakhtDarkhastFaktorPPC.setTarikhSanad(new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(new Date()));
            dariaftPardakhtDarkhastFaktorPPC.setTarikhSanadShamsi("");
            dariaftPardakhtDarkhastFaktorPPC.setMablaghDariaftPardakht(MablaghKolMarjoeeMoshtary);
            dariaftPardakhtDarkhastFaktorPPC.setMablagh(MablaghDariaftPardakhtDarkhastFaktor);
            dariaftPardakhtDarkhastFaktorPPC.setCodeVazeiat(0);
            dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktor(new SimpleDateFormat(Constants.DATE_SHORT_FORMAT()).format(new Date()));
            dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktorShamsi(new PubFunc().new DateUtils().todayDateWithSlash(mPresenter.getAppContext()));
            dariaftPardakhtDarkhastFaktorPPC.setCcAfradErsalKonandeh(foroshandehMamorPakhshModel.getCcAfrad());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazAnbar(foroshandehMamorPakhshModel.getCcMarkazAnbarVosol());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazForosh(foroshandehMamorPakhshModel.getCcMarkazForosh());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazSazmanForoshSakhtarForosh(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
            dariaftPardakhtDarkhastFaktorPPC.setTabdil_NaghdBeFish(0);
            dariaftPardakhtDarkhastFaktorPPC.setCcTafkikJoze(darkhastFaktorModel.getCcTafkikJoze());
            dariaftPardakhtDarkhastFaktorPPC.setNaghlAzGhabl(0);
            dariaftPardakhtDarkhastFaktorPPC.setIsForTasviehTakhir(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsDirkard(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccKardexSatr(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsBestankari_ForTasviehTakhir(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsSend(1); // CodeNoeVosol_moshtary== CodeNoeVosol.Marjoee.getValue()?1:0
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_CanDelete(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsTajil(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccDarkhastFaktorServer(darkhastFaktorModel.getCcDarkhastFaktor());

            return dariaftPardakhtDarkhastFaktorPPCDAO.insert(dariaftPardakhtDarkhastFaktorPPC) > 0;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "insertMarjoeeDariaftPardakhtDarkhastFaktor", "");
            mPresenter.onErrorCheck(R.string.errorOperation , "");
            return false;
        }
    }


    private int CheckEtebar(int codeNoeVosol , long sumMablaghArzeshAfzoodeh , int ccMoshtary , long ccDarkhastFaktor, int ccForoshandeh, int ModatVosol)
    {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
 /*
        اگر مقدار true باشد اعتبار فروشنده چک می گردد
        */
        boolean checkEtebarForoshandeh=  moshtaryDAO.getByccMoshtary(ccMoshtary).getControlEtebarForoshandeh()==0?false:true;
        Log.d("etebar","codeNoeVosol" + codeNoeVosol + " ,checkEtebarForoshandeh:"+checkEtebarForoshandeh);

        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect());
        //------------------- TedadFaktorBazRoozMoshtary -----------------------------
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());

        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());

        double mablaghFaktor = sumMablaghArzeshAfzoodeh;

        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(String.valueOf(Constants.CC_PARAMETER_CODE_NOE_VOSOL_MOSHTARY()));
        int valueCheck = 0;
        int valueResid = 0;
        int valueVajhNaghd = 0;
        for (ParameterChildModel model : childParameterModels)
        {
            if (model.getCcParameterChild() == Constants.CC_CHILD_VOSOL_MOSHTARY_RESID())
            {
                valueResid = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_VOSOL_MOSHTARY_CHECK())
            {
                valueCheck = Integer.parseInt(model.getValue());
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD())
            {
                valueVajhNaghd = Integer.parseInt(model.getValue());
            }
        }
        if (codeNoeVosol == valueCheck || codeNoeVosol == valueResid)
        {
            if((codeNoeVosol == valueVajhNaghd /*&& CanInsertFaktorDovom()*/))
            {
                return 0;
            }
            else
            {
                DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor( selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(),-1));
                MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
                MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = moshtaryEtebarSazmanForoshDAO.getByccMoshtaryccSazmanForosh(ccMoshtary, selectFaktorShared.getInt(selectFaktorShared.getCcSazmanForosh(),-1));
                Log.d("etebar","CcSazmanForosh: "+ selectFaktorShared.getInt(selectFaktorShared.getCcSazmanForosh(),-1));
                ForoshandehEtebarDAO foroshandehEtebarDAO =new ForoshandehEtebarDAO(mPresenter.getAppContext());
                ForoshandehEtebarModel foroshandehEtebarModel = foroshandehEtebarDAO.getByccForoshandeh(ccForoshandeh);

                long MablaghHavalehSatr=0;
                //EtebarBargashty
                long rialBargahstyForoshandeh = foroshandehEtebarModel.getRialBargashty();
                int tedadBargahstyForoshandeh = foroshandehEtebarModel.getTedadBargashty();
                int modatBargashtyForoshandeh = foroshandehEtebarModel.getModatBargashty();

                long EtebarMandehRialBargashtiForoshandeh = foroshandehEtebarModel.getEtebarRialBargashty() - rialBargahstyForoshandeh;
                int EtebarMandehTedadBargashtyForoshandeh = foroshandehEtebarModel.getEtebarTedadBargashty() - tedadBargahstyForoshandeh;
                int EtabarMandehModatBargashtyForoshandeh = foroshandehEtebarModel.getEtebarModatBargashty() - modatBargashtyForoshandeh;

                // EtebarAsnad
                long rialAsnadForoshandeh = foroshandehEtebarModel.getRialAsnad();
                int tedadAsnadForoshandeh = foroshandehEtebarModel.getTedadAsnad();
                //int modatAsnadForoshandeh = foroshandehEtebarModel.getModatAsnad();

                long etebarRialAsnadForoshandeh = foroshandehEtebarModel.getEtebarRialAsnadMoshtary() + foroshandehEtebarModel.getEtebarRialAsnadShakhsi();
                int etebarTedadAsnadForoshandeh = foroshandehEtebarModel.getEtebarTedadAsnadMoshtary() + foroshandehEtebarModel.getEtebarTedadAsnadShakhsi();

                long EtebarMandehRialAsnadForoshandeh;
                DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                MablaghHavalehSatr = darkhastFaktorSatrDAO.getSumMablaghFaktorWithMaliatByccDarkhast(selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(),-1));
                if(noeMasouliat == 4 || noeMasouliat == 5)
                {
                    EtebarMandehRialAsnadForoshandeh= etebarRialAsnadForoshandeh - rialAsnadForoshandeh + selectFaktorShared.getLong(selectFaktorShared.getMablaghHavaleh(),0);
                }
                else
                {
                    EtebarMandehRialAsnadForoshandeh= etebarRialAsnadForoshandeh - rialAsnadForoshandeh;
                }
                int EtebarMandehTedadAsnadForoshandeh = etebarTedadAsnadForoshandeh - tedadAsnadForoshandeh;

                // EtebarMoavagh
                long rialMoavaghForoshandeh = foroshandehEtebarModel.getRialMoavagh();
                int tedadMoavaghForoshandeh = foroshandehEtebarModel.getTedadMoavagh();
                int modatMoavaghForoshandeh = foroshandehEtebarModel.getModatMoavagh();

                long EtebarMandehRialMoavaghForoshandeh;
                if(noeMasouliat == 4 || noeMasouliat == 5)
                {
                    EtebarMandehRialMoavaghForoshandeh = foroshandehEtebarModel.getEtebarRialMoavagh() - rialMoavaghForoshandeh + selectFaktorShared.getLong(selectFaktorShared.getMablaghHavaleh(),0);
                }
                else
                {
                    EtebarMandehRialMoavaghForoshandeh = foroshandehEtebarModel.getEtebarRialMoavagh() - rialMoavaghForoshandeh;
                }
                int EtebarMandehTedadMoavaghForoshandeh = foroshandehEtebarModel.getEtebarTedadMoavagh() - tedadMoavaghForoshandeh;
                int EtabarMandehModatMoavaghForoshandeh = foroshandehEtebarModel.getEtebarModatMoavagh() - modatMoavaghForoshandeh;

                //saghf etebar
                long saghfEtebarRialiForoshandeh = foroshandehEtebarModel.getSaghfEtebarRiali() - (rialMoavaghForoshandeh + rialAsnadForoshandeh + rialBargahstyForoshandeh - selectFaktorShared.getLong(selectFaktorShared.getMablaghHavaleh(),0));
                int saghfEtebarTedadiForoshandeh = foroshandehEtebarModel.getSaghfEtebarTedadi() - (tedadBargahstyForoshandeh + tedadAsnadForoshandeh + tedadMoavaghForoshandeh);
                int saghfEtebarModatForoshandeh = foroshandehEtebarModel.getSaghfEtebarModat() - (Math.max(modatBargashtyForoshandeh , modatMoavaghForoshandeh));


                Log.d("etebar","rialBargahstyForoshandeh : " + rialBargahstyForoshandeh + " , tedadBargahstyForoshandeh : " + tedadBargahstyForoshandeh + " , modatBargashtyForoshandeh : " + modatBargashtyForoshandeh +
                                " , EtebarMandehRialBargashtiForoshandeh : " + EtebarMandehRialBargashtiForoshandeh + " , EtebarMandehTedadBargashtyForoshandeh" + EtebarMandehTedadBargashtyForoshandeh + " , EtabarMandehModatBargashtyForoshandeh : " + EtabarMandehModatBargashtyForoshandeh +
                                " , RialAsnadForoshandeh : " + rialAsnadForoshandeh + " , TedadAsnadForoshandeh : " + tedadAsnadForoshandeh +
                                " , EtebarMandehRialAsnadForoshandeh: " + EtebarMandehRialAsnadForoshandeh + " , EtebarMandehTedadAsnadForoshandeh : " + EtebarMandehTedadAsnadForoshandeh +
                                " , rialMoavaghForoshandeh : " + rialMoavaghForoshandeh + " , tedadMoavaghForoshandeh : " + tedadMoavaghForoshandeh + " , modatMoavaghForoshandeh: " + modatMoavaghForoshandeh +
                                " , EtebarMandehRialMoavaghForoshandeh : " + EtebarMandehRialMoavaghForoshandeh + " , EtebarMandehTedadMoavaghForoshandeh : " + EtebarMandehTedadMoavaghForoshandeh + " , EtabarMandehModatMoavaghForoshandeh : " + EtabarMandehModatMoavaghForoshandeh +
                        " , saghfEtebarRialiForoshandeh: " + saghfEtebarRialiForoshandeh +
                        "saghfEtebarTedadiForoshandeh:" + saghfEtebarTedadiForoshandeh +
                        "saghfEtebarModatForoshandeh:" + saghfEtebarModatForoshandeh +
                        ", MablaghHavalehSatr" + MablaghHavalehSatr);

                //-----------------------------------
                //Etebar Moshtary

                //EtebarBargashty
                long rialBargahstyMoshtary = moshtaryEtebarSazmanForoshModel.getRialBargashty();
                int tedadBargahstyMoshtary = moshtaryEtebarSazmanForoshModel.getTedadBargashty();
                int modatBargashtyMoshtary = moshtaryEtebarSazmanForoshModel.getModatBargashty();

                Long EtebarMandehRialBargashtiMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarRialBargashty() - rialBargahstyMoshtary;
                int EtebarMandehTedadBargashtyMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarTedadBargashty() - tedadBargahstyMoshtary;
                int EtabarMandehModatBargashtyMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarModatBargashty() - modatBargashtyMoshtary;

                // EtebarAsnad
                long rialAsnadMoshtary = moshtaryEtebarSazmanForoshModel.getRialAsnad();
                int tedadAsnadMoshtary = moshtaryEtebarSazmanForoshModel.getTedadAsnad();
                //int modatAsnadMoshtary = moshtaryEtebarSazmanForoshModel.getModatAsnad();

                long etebarRialAsnadMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarRialAsnadMoshtary() + moshtaryEtebarSazmanForoshModel.getEtebarRialAsnadShakhsi();
                int etebarTedadAsnadMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarTedadAsnadMoshtary() + moshtaryEtebarSazmanForoshModel.getEtebarTedadAsnadShakhsi();
                //int etebarModatAsnadMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarModatAsnadMoshtary() + moshtaryEtebarSazmanForoshModel.getEtebarModatAsnadShakhsi();

                Long EtebarMandehRialAsnadMoshtary = etebarRialAsnadMoshtary - rialAsnadMoshtary;
                int EtebarMandehTedadAsnadMoshtary = etebarTedadAsnadMoshtary - tedadAsnadMoshtary;
                //int EtabarMandehModatAsnadMoshtary = etebarModatAsnadMoshtary - modatAsnadMoshtary;

                // EtebarMoavagh
                long rialMoavaghMoshtary = moshtaryEtebarSazmanForoshModel.getRialMoavagh();
                int tedadMoavaghMoshtary = moshtaryEtebarSazmanForoshModel.getTedadMoavagh();
                int modatMoavaghMoshtary = moshtaryEtebarSazmanForoshModel.getModatMoavagh();

                Long EtebarMandehRialMoavaghMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarRialMoavagh() - rialMoavaghMoshtary;
                int EtebarMandehTedadMoavaghMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarTedadMoavagh() - tedadMoavaghMoshtary;
                int EtabarMandehModatMoavaghMoshtary = moshtaryEtebarSazmanForoshModel.getEtebarModatMoavagh() - modatMoavaghMoshtary;



                //saghf etebar
                long saghfEtebarRialiMoshtary = moshtaryEtebarSazmanForoshModel.getSaghfEtebarRiali() - (rialMoavaghMoshtary + rialAsnadMoshtary + rialBargahstyMoshtary);
                int saghfEtebarTedadMoshtary = moshtaryEtebarSazmanForoshModel.getSaghfEtebarTedadi() - (tedadMoavaghMoshtary + tedadAsnadMoshtary + tedadBargahstyMoshtary);
                int saghfEtebarModatMoshtary = moshtaryEtebarSazmanForoshModel.getSaghfEtebarModat() - (Math.max(modatMoavaghMoshtary,modatBargashtyMoshtary));


                Log.d("etebar","rialBargahstyMoshtary : " + rialBargahstyMoshtary + " , tedadBargahstyMoshtary : " + tedadBargahstyMoshtary + " , modatBargashtyMoshtary : " + modatBargashtyMoshtary +
                        " , EtebarMandehRialBargashtiMoshtary : " + EtebarMandehRialBargashtiMoshtary +
                        " , EtebarMandehTedadBargashtyMoshtary" + EtebarMandehTedadBargashtyMoshtary +
                        " , EtabarMandehModatBargashtyMoshtary : " + EtabarMandehModatBargashtyMoshtary +
                        " , RialAsnadMoshtary : " + rialAsnadMoshtary +
                        " , TedadAsnadMoshtary : " + tedadAsnadMoshtary +
                        " , etebarRialAsnadMoshtary : " + etebarRialAsnadMoshtary +
                        " , getEtebarRialAsnadMoshtary() : " + moshtaryEtebarSazmanForoshModel.getEtebarRialAsnadMoshtary() +
                        " , getEtebarRialAsnadShakhsi() :" + moshtaryEtebarSazmanForoshModel.getEtebarRialAsnadShakhsi() +
                        " , EtebarMandehRialAsnadMoshtary : " + EtebarMandehRialAsnadMoshtary +
                        " , EtebarMandehTedadAsnadMoshtary : " + EtebarMandehTedadAsnadMoshtary +
                        " , rialMoavaghMoshtary : " + rialMoavaghMoshtary +
                        " , tedadMoavaghMoshtary : " + tedadMoavaghMoshtary +
                        " , modatMoavaghMoshtary: " + modatMoavaghMoshtary +
                        " , EtebarMandehRialMoavaghMoshtary : " + EtebarMandehRialMoavaghMoshtary +
                        " , EtebarMandehTedadMoavaghMoshtary : " + EtebarMandehTedadMoavaghMoshtary +
                        " , EtabarMandehModatMoavaghMoshtary : " + EtabarMandehModatMoavaghMoshtary +
                        " , saghfEtebarRialiMoshtary : " + saghfEtebarRialiMoshtary +
                        " , saghfEtebarTedadMoshtary : " + saghfEtebarTedadMoshtary +
                        " , saghfEtebarModatMoshtary : " + saghfEtebarModatMoshtary +
                        " , moshtaryEtebarSazmanForoshModel.getSaghfEtebarRiali() : " + moshtaryEtebarSazmanForoshModel.getSaghfEtebarRiali() +
                        " , moshtaryEtebarSazmanForoshModel.getSaghfEtebarTedadi():" + moshtaryEtebarSazmanForoshModel.getSaghfEtebarTedadi() +
                        ",  moshtaryEtebarSazmanForoshModel.getSaghfEtebarModat():" +  moshtaryEtebarSazmanForoshModel.getSaghfEtebarModat());





                if(codeNoeVosol == valueResid)
                {
                    //Check Etebar Rialy...
                    Log.d("etebar","mablaghFaktor"+ mablaghFaktor + " codeNoeVosol:" + codeNoeVosol);
                    if ((mablaghFaktor >= EtebarMandehRialMoavaghForoshandeh) && checkEtebarForoshandeh)
                    {
                        return 1;
                    }
                    if (mablaghFaktor >= saghfEtebarRialiForoshandeh && checkEtebarForoshandeh)
                    {
                        return 2;
                    }
                    if (mablaghFaktor >= EtebarMandehRialMoavaghMoshtary)
                    {
                        return 3;
                    }
                    if (mablaghFaktor >= saghfEtebarRialiMoshtary)
                    {
                        return 4;
                    }
                    //Check Etebar Tedady...
                    if (tedadMoavaghForoshandeh >= foroshandehEtebarModel.getEtebarTedadMoavagh()  && checkEtebarForoshandeh)
                    {
                        return 5;
                    }
                    if (saghfEtebarTedadiForoshandeh <= 0  && checkEtebarForoshandeh)//foroshandehEtebarModel.getSaghfEtebarTedadi())
                    {
                        return 6;
                    }
                    if (tedadMoavaghMoshtary >= moshtaryEtebarSazmanForoshModel.getEtebarTedadMoavagh())
                    {
                        return 7;
                    }
                    if (saghfEtebarTedadMoshtary <= 0)
                    {
                        return 8;
                    }
                    //Check Etebar Modat...
                    if (modatMoavaghForoshandeh > foroshandehEtebarModel.getEtebarModatMoavagh()  && checkEtebarForoshandeh)
                    {
                        return 9;
                    }
                    if (saghfEtebarModatForoshandeh<0  && checkEtebarForoshandeh)
                    {
                        return 10;
                    }
                    if (modatMoavaghMoshtary > moshtaryEtebarSazmanForoshModel.getEtebarModatMoavagh())
                    {
                        return 11;
                    }
                    if (saghfEtebarModatMoshtary<0)
                    {
                        return 12;
                    }

                }
                if(codeNoeVosol == valueCheck)
                {
                    //Check Etebar Rialy...
                    Log.d("etebar","mablaghFaktor"+mablaghFaktor+ " EtebarMandehRialAsnadForoshandeh"+ EtebarMandehRialAsnadForoshandeh+" "+codeNoeVosol);
                    if ((mablaghFaktor > EtebarMandehRialAsnadForoshandeh)  && checkEtebarForoshandeh)
                    {
                        Log.d("etebar","return1 valueCheck");
                        return 1;
                    }
                    if (mablaghFaktor > saghfEtebarRialiForoshandeh  && checkEtebarForoshandeh)
                    {
                        return 2;
                    }
                    if (mablaghFaktor > EtebarMandehRialAsnadMoshtary)
                    {
                        return 3;
                    }
                    if (mablaghFaktor > saghfEtebarRialiMoshtary)
                    {
                        return 4;
                    }
                    //Check Etebar Tedady...
                    if (tedadAsnadForoshandeh >= etebarTedadAsnadForoshandeh  && checkEtebarForoshandeh)
                    {
                        return 5;
                    }
                    if (tedadAsnadForoshandeh >= foroshandehEtebarModel.getSaghfEtebarTedadi()  && checkEtebarForoshandeh)
                    {
                        return 6;
                    }
                    if (tedadAsnadMoshtary >= etebarTedadAsnadMoshtary)
                    {
                        return 7;
                    }
                    if (tedadAsnadMoshtary >= moshtaryEtebarSazmanForoshModel.getSaghfEtebarTedadi())
                    {
                        return 8;
                    }
                }
            }
        }
        return 0;
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
;
    @Override
    public void getHashiehSoud(long ccDarkhastFaktor , String mablaghBaArzeshAfzoodeh) {
        DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO= new DarkhastFaktorTakhfifDAO(BaseApplication.getContext());
        DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(BaseApplication.getContext());
        DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO= new DarkhastFaktorJayezehDAO(BaseApplication.getContext());
        JayezehEntekhabiDAO jayezehEntekhabiDAO = new JayezehEntekhabiDAO(BaseApplication.getContext());
        RptKalaInfoDAO kalaInfoDAO = new RptKalaInfoDAO(BaseApplication.getContext());




        //HashiehSood...
        double mablaghHashiehSood = darkhastFaktorSatrDAO.getSumMablaghHashiehSood(ccDarkhastFaktor);

        //Jayezeh...
        double mablaghJayezeh = 0;
        JayezehEntekhabiModel jayezehEntekhabi ;
        RptKalaInfoModel kalaInfo = new RptKalaInfoModel();

        ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehs = darkhastFaktorJayezehDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

        for (DarkhastFaktorJayezehModel darkhastFaktorJayezeh : darkhastFaktorJayezehs)
        {
            jayezehEntekhabi = jayezehEntekhabiDAO.getByccKalaCode(darkhastFaktorJayezeh.getCcKalaCode()).get(0);
            kalaInfo = kalaInfoDAO.getByCcKalaCode(jayezehEntekhabi.getCcKalaCodeAsli()).get(0);
            mablaghJayezeh += ( darkhastFaktorJayezeh.getTedad() * kalaInfo.getGheymatMasrafKonandeh());
        }

        //TakhfifNaghdi...
        double mablaghTakhfifNaghdi = 0;
        double mablaghTakhfifHajmi = 0;

        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifs= darkhastFaktorTakhfifDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        for (DarkhastFaktorTakhfifModel darkhastFaktorTakhfif : darkhastFaktorTakhfifs)
        {
            if(darkhastFaktorTakhfif.getCodeNoeTakhfif() == 2)
                mablaghTakhfifNaghdi += darkhastFaktorTakhfif.getMablaghTakhfif();
            if(darkhastFaktorTakhfif.getCodeNoeTakhfif() == 5)
                mablaghTakhfifHajmi += darkhastFaktorTakhfif.getMablaghTakhfif();
        }

        //JamSoodMaghazeh
        double jamSoodMaghazeh = Math.round(mablaghHashiehSood) + Math.round(mablaghJayezeh) + Math.round(mablaghTakhfifNaghdi) + Math.round(mablaghTakhfifHajmi);
        double darsadSoodMaghazeh=0;

        if (Long.parseLong(mablaghBaArzeshAfzoodeh)>0)
            darsadSoodMaghazeh = Math.round((jamSoodMaghazeh / Long.parseLong(mablaghBaArzeshAfzoodeh)) * 100);


        mPresenter.onHashiehSoud(mablaghTakhfifNaghdi , mablaghTakhfifHajmi , mablaghJayezeh , mablaghHashiehSood , jamSoodMaghazeh , darsadSoodMaghazeh);

    }

    @Override
    public void updateMoshtaryEtebar(int ccMoshtary) {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
        getMoshtaryEtebarSazmanForosh(moshtaryModel, foroshandehMamorPakhshModel, noeMasouliat);
    }

    @Override
    public void checkJashnvarehAvailable(int ccMoshtary) {
       RptJashnvarehForoshRepository rptJashnvarehForoshRepository = new RptJashnvarehForoshRepository(mPresenter.getAppContext());
       compositeDisposable.add( rptJashnvarehForoshRepository.isJashnvarehAvailable(ccMoshtary)
                .subscribe(isAvailable -> {
                    if (isAvailable)
                        mPresenter.onJashnvarehAvailable();
                }, throwable -> {
                        mPresenter.onFailGetJashnvareh();
                }));
    }

    private void getMoshtaryEtebarSazmanForosh( MoshtaryModel moshtaryModel,  ForoshandehMamorPakhshModel foroshandehMamorPakhshModel,  int noeMasouliat)
    {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch(serverIpModel.getWebServiceType()){
            case REST:
                moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForosh(mPresenter.getAppContext(), "VerifyRequestModel", String.valueOf(moshtaryModel.getCcMoshtary()), String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh()), new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        if (arrayListData.size() > 0)
                        {
                            boolean deleteResult = moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(moshtaryModel.getCcMoshtary());
                            boolean insertResult = moshtaryEtebarSazmanForoshDAO.insert((MoshtaryEtebarSazmanForoshModel) arrayListData.get(0));
                            Log.d("updateEtebarMoshtary","deleteResult: " + deleteResult + " , insertResult: " + insertResult);
                            Log.d("updateEtebarMoshtary","arrayListData: " + arrayListData.toString());
                            if (deleteResult && insertResult)
                            {
                                mPresenter.onSuccessUpdateMoshtaryEtebar();
                            }
                            else
                            {
                                mPresenter.onFailedUpdateMoshtaryEtebar();
                            }
                        }
                        else
                        {
                            mPresenter.onFailedUpdateMoshtaryEtebar();
                        }
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "VerifyRequestModel", "VerifyRequestModel", "getMoshtaryEtebarSazmanForosh", "onFailed");
                        mPresenter.onFailedUpdateMoshtaryEtebar();
                    }
                });
                break;

            case gRPC:
                moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForoshGrpc(mPresenter.getAppContext(), "VerifyRequestModel", String.valueOf(moshtaryModel.getCcMoshtary()), String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh()), new RetrofitResponse()
                {
                    @Override
                    public void onSuccess(final ArrayList arrayListData)
                    {
                        if (arrayListData.size() > 0)
                        {
                            boolean deleteResult = moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(moshtaryModel.getCcMoshtary());
                            boolean insertResult = moshtaryEtebarSazmanForoshDAO.insert((MoshtaryEtebarSazmanForoshModel) arrayListData.get(0));
                            Log.d("updateEtebarMoshtary","deleteResult: " + deleteResult + " , insertResult: " + insertResult);
                            Log.d("updateEtebarMoshtary","arrayListData: " + arrayListData.toString());
                            if (deleteResult && insertResult)
                            {
                                mPresenter.onSuccessUpdateMoshtaryEtebar();
                            }
                            else
                            {
                                mPresenter.onFailedUpdateMoshtaryEtebar();
                            }
                        }
                        else
                        {
                            mPresenter.onFailedUpdateMoshtaryEtebar();
                        }
                    }
                    @Override
                    public void onFailed(String type, String error)
                    {
                        setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "VerifyRequestModel", "VerifyRequestModel", "getMoshtaryEtebarSazmanForosh", "onFailed");
                        mPresenter.onFailedUpdateMoshtaryEtebar();
                    }
                });
                break;
        }

    }

    interface OnCalculateDiscountResponse {
        void onFailedCalculate(int resId);
        //void onSuccessCalculate(boolean haveBonus , long ccDarkhastFaktor , boolean canSelectBonus);
        void onSuccessCalculate(boolean haveBonus , long ccDarkhastFaktor );
    }


    private class AsyncTaskCalculateDiscount extends AsyncTask<Void , Integer , Integer>
    {
        OnCalculateDiscountResponse onCalculateDiscountResponse;
        long ccDarkhastFaktor;
        int ccMarkazSazmanForosh;
        int ccChildParameterNoeVosol;
        int valueNoeVosol;
        boolean haveBonus = false;
        private boolean canSelectBonus = false; // if canSelectBonus == false then go to SelectBonusActivity , if canSelectBonus == true then automatically insert bonus
        SelectFaktorShared selectFaktorShared;
        int resultOfProccess = 1;

        AsyncTaskCalculateDiscount(long ccDarkhastFaktor, int ccMoshtaryGharardad,int ccMoshtaryGharardadSazmanForosh,int ccMarkazSazmanForosh, int ccChildParameterNoeVosol, int valueNoeVosol, SelectFaktorShared selectFaktorShared, OnCalculateDiscountResponse onCalculateDiscountResponse)
        {
            this.ccDarkhastFaktor = ccDarkhastFaktor;
            this.ccChildParameterNoeVosol = ccChildParameterNoeVosol;
            this.valueNoeVosol = valueNoeVosol;
            this.selectFaktorShared = selectFaktorShared;
            this.onCalculateDiscountResponse = onCalculateDiscountResponse;
            Log.i("verifyRequest", "AsyncTaskCalculateDiscount: ccMoshtaryGharardad \t"+ccMoshtaryGharardad+"ccMoshtaryGharardadSazmanForosh\t"+ccMoshtaryGharardadSazmanForosh+"ccMarkazSazmanForosh"+ccMarkazSazmanForosh);
        }

        @Override
        protected Integer doInBackground(Void... arrayLists)
        {
            deleteTakhfifJayezeForDarkhastFaktor(ccDarkhastFaktor);

            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
            String ccChildParametersOfTakhfif = "";
            Log.d("vosol","ccChildParameterNoeVosol Takhfif: "+ ccChildParameterNoeVosol);

            if (valueNoeVosol == Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD())))
            {
                ccChildParametersOfTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_NAGHD());
            }
            else if (valueNoeVosol == Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_1_Setareh())))
            {
                ccChildParametersOfTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_NAGHD());
            }
            else if (valueNoeVosol == Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD_2_Setareh())))
            {
                ccChildParametersOfTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_NAGHD());
            }
            else if (valueNoeVosol == Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_CHECK())))
            {
                ccChildParametersOfTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_CHECK());
            }
            else if (valueNoeVosol == Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_RESID())))
            {
                ccChildParametersOfTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_RESID());
            }
            else if (valueNoeVosol == Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_Resid_Naghd())))
            {
                ccChildParametersOfTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_NAGHD());
            }
//            if (ccChildParameterNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD())
//            {
//                ccChildParametersOfTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_NAGHD());
//            }
//            else if (ccChildParameterNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK())
//            {
//                ccChildParametersOfTakhfif = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_CHECK());
//            }
//            else if (ccChildParameterNoeVosol == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID())
//            {
//                ccChildParametersOfTakhfif= childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_TAKHFIF_FOR_VOSOL_RESID());
////            }
            Log.d("darkhastfaktor","ccChildParametersOfTakhfif:"+ccChildParametersOfTakhfif);
            String[] seperatedccChildParameterOfTakhfif = ccChildParametersOfTakhfif.split(",");
            Log.d("darkhastfaktor", "seperatedccChildParameterOfTakhfif : " + seperatedccChildParameterOfTakhfif.toString());
            if (seperatedccChildParameterOfTakhfif.length > 0)
            {
                DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
                Log.d("darkhastFaktor" , "calculateDiscounts : " + darkhastFaktorModel.toString());
				ArrayList<ParameterChildModel> parameterChildModels = new ParameterChildDAO(mPresenter.getAppContext()).getAllByccParameter(Constants.CC_NOE_BASTE_BANDI() + "," + Constants.CC_NOE_TEDAD_RIAL());
                Log.d("darkhastfaktor","parameterChildModels : " + parameterChildModels.toString());
                for (String ccChilParameter : seperatedccChildParameterOfTakhfif)
                {
                    Log.d("darkhastfaktor","ccChilParameter : " + ccChilParameter);

                    if (Integer.parseInt(ccChilParameter) == Constants.CC_CHILD_CODE_TAKHFIF_SENFI())
                    {
                        Log.d("takhfif" , "takhfif Senfi");
                        mohasebeTakhfifSenfi(darkhastFaktorModel, parameterChildModels);
                    }
                    else if (Integer.parseInt(ccChilParameter) == Constants.CC_CHILD_CODE_TAKHFIF_NAGHDI())
                    {
                        Log.d("takhfif" , "takhfif naghdi");
                        mohasebeTakhfifNaghdi(darkhastFaktorModel);
                    }
                    else if (Integer.parseInt(ccChilParameter) == Constants.CC_CHILD_CODE_TAKHFIF_JAYEZE())
                    {
                        calculateJayezehs(darkhastFaktorModel, parameterChildModels);
                    }
                    else if (Integer.parseInt(ccChilParameter) == Constants.CC_CHILD_CODE_TAKHFIF_HAJMI())
                    {
                        Log.d("takhfif" , "takhfif hajmi");
                        mohasebeTakhfifHajmi(darkhastFaktorModel, parameterChildModels);
                    }
                }
                DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                ArrayList<Integer> ccTakhfifs = darkhastFaktorTakhfifDAO.getccTakhfifOfJayezehByccDarkhastFaktor(ccDarkhastFaktor);
                String strccTakhfifs = "";
                Log.d("ccTakhfif" , "size : " + ccTakhfifs.size() + ccTakhfifs.toString());
                if (ccTakhfifs.size() > 0)
                {
                    canSelectBonus = true;
                    for (int i : ccTakhfifs)
                    {
                        Log.d("ccTakhfif" , "ccTakhfif : " + i);
                        strccTakhfifs += i + ",";
                    }
                    Log.d("ccTakhfif" , "strccTakhfifs for : " + strccTakhfifs);
                    if (strccTakhfifs.length() > 0 && strccTakhfifs.endsWith(","))
                    {
                        strccTakhfifs = strccTakhfifs.substring(0 , strccTakhfifs.length()-1);
                    }
                    Log.d("ccTakhfif" , "strccTakhfifs if : " + strccTakhfifs);
                }
                Log.d("ccTakhfif" , "strccTakhfifs : " + strccTakhfifs);
                selectFaktorShared.putString(selectFaktorShared.getCcTakhfifJayezes() , strccTakhfifs);
                Log.d("ccTakhfif" , "resultOfProccess : " + resultOfProccess);
				//resultOfProccess=1;
                return resultOfProccess;
                //mPresenter.onSuccessCalculateDiscount(haveBonus);
                //onCalculateDiscountResponse.onSuccessCalculate(haveBonus , ccDarkhastFaktor);
                //return resultOfProccess;
                //getDiscounts(ccDarkhastFaktor);
            }
            return 0;

        }



        ///////////////////////// Takhfif Senfi /////////////////////////
//        private boolean mohasebeTakhfifSenfiFaktor(DarkhastFaktorModel darkhastFaktor, ArrayList<ParameterChildModel> parameterChildModels,int valueNoeVosol) {
//            try {
//                DiscountCalculation discountCalculation = new DiscountCalculation(parameterChildModels);
//                TakhfifSenfiDAO takhfifSenfiDAO = new TakhfifSenfiDAO(mPresenter.getAppContext());
//                DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
//                TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(mPresenter.getAppContext());
//                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
//
//                ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrs = darkhastFaktorSatrDAO.getByccDarkhastFaktor(darkhastFaktor.getCcDarkhastFaktor());
//                MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(darkhastFaktor.getCcMoshtary());
//
//
//
//                ArrayList<TakhfifSenfiModel> takhfifSenfis = takhfifSenfiDAO.getByMoshtary(moshtary , valueNoeVosol);
//
//                if (darkhastFaktorSatrs.size() == 0 || takhfifSenfis.size() == 0) {
//                    return true;
//                }
//
//                KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
//                //********************************************** Kala.. **********************************************
//                for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs) {
//                    KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
//                    for (TakhfifSenfiModel takhfifSenfi : takhfifSenfis) {
//                        //Satrhaye Takhfif...
//                        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs =
//                                takhfifSenfiSatrDAO.getForFaktor(takhfifSenfi.getCcTakhfifSenfi(),
//                                        new int[]{discountCalculation.getTedadRialTedad(), discountCalculation.getTedadRialRial()},
//                                        new int[]{discountCalculation.getBasteBandiCarton(), discountCalculation.getBasteBandiBaste(), discountCalculation.getBasteBandiAdad()},
//                                        DiscountCalculation.NAME_NOE_FIELD_KALA, darkhastFaktorSatr.getCcKalaCode(),
//                                        darkhastFaktorSatr.getTedad3(), darkhastFaktorSatr.getTedad3() / kala.getTedadDarBasteh(),
//                                        darkhastFaktorSatr.getTedad3() / kala.getTedadDarKarton(),
//                                        darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh(), takhfifSenfi.getNoeTedadRial(),0);
//
//                        for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs) {
//                            int zarib = 0;
//                            if (takhfifSenfiSatr.getBeEza() == 0)
//                                zarib = 1;
//                            else {
//                                //Tedad..
//                                if (takhfifSenfi.getNoeTedadRial() == discountCalculation.getTedadRialTedad()) {
//                                    if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiAdad())
//                                        zarib = (int) (darkhastFaktorSatr.getTedad3() / takhfifSenfiSatr.getBeEza());
//                                    else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiBaste())
//                                        zarib = (int) ((darkhastFaktorSatr.getTedad3() / kala.getTedadDarBasteh()) / takhfifSenfiSatr.getBeEza());
//                                    if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiCarton())
//                                        zarib = (int) ((darkhastFaktorSatr.getTedad3() / kala.getTedadDarKarton()) / takhfifSenfiSatr.getBeEza());
//                                }//if
//                                //Rial..
//                                else if (takhfifSenfi.getNoeTedadRial() == discountCalculation.getTedadRialRial())
//                                    zarib = (int) (darkhastFaktor.getMablaghKolFaktor() / takhfifSenfiSatr.getBeEza());
//                            }//else
//
//                            double mablaghTakhfif = (darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100);
//                            //Insert In DarkhastFaktorTakhfif & DarkhastFaktorSatrTakhfif..
//                            if (mablaghTakhfif > 0) {
//                                insertFaktorTakhfifSenfi(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), takhfifSenfi.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif);
//                                insertFaktorSatrTakhfifSenfi(darkhastFaktorSatr.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), takhfifSenfi.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif);
//                                //بروز رسانی مبلغ تخفیف تیتر
//                                //updateMablaghTakhfifDarkhastFaktor(darkhastFaktor.getCcDarkhastFaktor() , takhfifSenfi.getCcTakhfifSenfi());
//                            }
//                        }//for
//                    }//for
//                }//for
//
//                //********************************************** Brand.. **********************************************
//                int ccBrand;
//                double sumTedadBrand; // Sum Tedad3 Darkhast Bar Hasbe Brand..
//                double sumTedadBastehBrand;
//                double sumTedadKartonBrand;
//                double sumMablaghKolBrand; // Sum MablaghKol Darkhast Bar Hasbe Brand..
//
//                ArrayList<DataTableModel> brands = darkhastFaktorSatrDAO.getTedadBeTafkikBrand(darkhastFaktor.getCcDarkhastFaktor(), -1, 1);
//                for (DataTableModel brand : brands) {
//                    ccBrand = Integer.valueOf(brand.getFiled1());
//                    sumTedadBrand = Double.parseDouble(brand.getFiled2());
//                    sumTedadBastehBrand = Double.parseDouble(brand.getFiled3());
//                    sumTedadKartonBrand = Double.parseDouble(brand.getFiled4());
//                    sumMablaghKolBrand = Double.parseDouble(brand.getFiled5());
//
//                    for (TakhfifSenfiModel takhfifSenfi : takhfifSenfis) {
//                        //Satrhaye Takhfif...
//                        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfi.getCcTakhfifSenfi(),
//                                new int[]{discountCalculation.getTedadRialTedad(), discountCalculation.getTedadRialRial()},
//                                new int[]{discountCalculation.getBasteBandiCarton(), discountCalculation.getBasteBandiBaste(), discountCalculation.getBasteBandiAdad()},
//                                DiscountCalculation.NAME_NOE_FIELD_BRAND, ccBrand,
//                                sumTedadBrand, sumTedadBastehBrand, sumTedadKartonBrand, sumMablaghKolBrand, takhfifSenfi.getNoeTedadRial(),0);
//
//                        for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs) {
//                            int zarib = 0;
//                            if (takhfifSenfiSatr.getBeEza() == 0)
//                                zarib = 1;
//                            else
//                                //Tedad..
//                                if (takhfifSenfi.getNoeTedadRial() == discountCalculation.getTedadRialTedad()) {
//                                    if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiAdad())
//                                        zarib = (int) (sumTedadBrand / takhfifSenfiSatr.getBeEza());
//                                    else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiBaste())
//                                        zarib = (int) (sumTedadBastehBrand / takhfifSenfiSatr.getBeEza());
//                                    else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiCarton())
//                                        zarib = (int) (sumTedadKartonBrand / takhfifSenfiSatr.getBeEza());
//                                }//if
//                                //Rial..
//                                else if (takhfifSenfi.getNoeTedadRial() == discountCalculation.getTedadRialRial())
//                                    zarib = (int) (sumMablaghKolBrand / takhfifSenfiSatr.getBeEza());
//
//                            double mablaghTakhfif = sumMablaghKolBrand * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100);
//                            //Insert In DarkhastFaktorTakhfif & DarkhastFaktorSatrTakhfif..
//                            long sumMablaghTakhfifSatr = 0;
//                            String allMablaghTakhfifSatr = "-1";
//                            if (mablaghTakhfif > 0) {
//                                insertFaktorTakhfifSenfi(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), takhfifSenfi.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif);
//
//                                for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs) {
//                                    KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
//                                    if (kala.getCcBrand() == ccBrand) {
//                                        double mablaghTakhfifSatr = (darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (takhfifSenfiSatr.getDarsadTakhfif() / 100);
//                                        sumMablaghTakhfifSatr += Math.round(mablaghTakhfifSatr);
//                                        allMablaghTakhfifSatr += "," + allMablaghTakhfifSatr;
//                                        insertFaktorSatrTakhfifSenfi(darkhastFaktorSatr.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), takhfifSenfi.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr);
//                                    }//if
//                                }//for
//                                //بروز رسانی مبلغ تخفیف تیتر
//                                updateMablaghTakhfifDarkhastFaktor(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), Math.round(mablaghTakhfif), sumMablaghTakhfifSatr, allMablaghTakhfifSatr);
//                            }//if
//                        }//for
//                    }//for
//                }//for
//
//                //********************************************** TaminKonandeh **********************************************
//                int ccTaminKonandeh;
//                double sumTedadTaminKonandeh; // Sum Tedad3 Darkhast Bar Hasbe TaminKonandeh..
//                double sumTedadBastehTaminKonandeh;
//                double sumTedadKartonTaminKonandeh;
//                double sumMablaghKolTaminKonandeh; // Sum MablaghKol Darkhast Bar Hasbe TaminKonandeh..
//
//                ArrayList<DataTableModel> taminKonandehs = darkhastFaktorSatrDAO.getTedadBeTafkikTaminKonandeh(darkhastFaktor.getCcDarkhastFaktor(),-1,1);
//                for (DataTableModel taminKonandeh : taminKonandehs) {
//                    ccTaminKonandeh = Integer.parseInt(taminKonandeh.getFiled1());
//                    sumTedadTaminKonandeh = Double.parseDouble(taminKonandeh.getFiled2());
//                    sumTedadBastehTaminKonandeh = Double.parseDouble(taminKonandeh.getFiled3());
//                    sumTedadKartonTaminKonandeh = Double.parseDouble(taminKonandeh.getFiled4());
//                    sumMablaghKolTaminKonandeh = Double.parseDouble(taminKonandeh.getFiled5());
//
//                    for (TakhfifSenfiModel takhfifSenfi : takhfifSenfis) {
//                        //Satrhaye Takhfif..
//                        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfi.getCcTakhfifSenfi(), new int[]{discountCalculation.getTedadRialTedad(), discountCalculation.getTedadRialRial()},
//                                new int[]{discountCalculation.getBasteBandiCarton(), discountCalculation.getBasteBandiBaste(), discountCalculation.getBasteBandiAdad()}, DiscountCalculation.NAME_NOE_FIELD_TAMIN_KONANDE, ccTaminKonandeh,
//                                sumTedadTaminKonandeh, sumTedadBastehTaminKonandeh, sumTedadKartonTaminKonandeh,
//                                sumMablaghKolTaminKonandeh, takhfifSenfi.getNoeTedadRial(),0);
//
//                        for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs) {
//                            int zarib = 0;
//                            if (takhfifSenfiSatr.getBeEza() == 0)
//                                zarib = 1;
//                            else
//                                //Tedad..
//                                if (takhfifSenfi.getNoeTedadRial() == discountCalculation.getTedadRialTedad()) {
//                                    if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiAdad())
//                                        zarib = (int) (sumTedadTaminKonandeh / takhfifSenfiSatr.getBeEza());
//                                    else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiBaste())
//                                        zarib = (int) (sumTedadBastehTaminKonandeh / takhfifSenfiSatr.getBeEza());
//                                    else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiCarton())
//                                        zarib = (int) (sumTedadKartonTaminKonandeh / takhfifSenfiSatr.getBeEza());
//                                }//if
//                                //Rial..
//                                else if (takhfifSenfi.getNoeTedadRial() == discountCalculation.getTedadRialRial())
//                                    zarib = (int) (sumMablaghKolTaminKonandeh / takhfifSenfiSatr.getBeEza());
//
//                            double mablaghTakhfif = sumMablaghKolTaminKonandeh * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100);
//
//                            //Insert In DarkhastFaktorTakhfif & DarkhastFaktorSatrTakhfif..
//                            if (mablaghTakhfif > 0) {
//                                insertFaktorTakhfifSenfi(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), takhfifSenfi.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif);
//                                long sumMablaghTakhfifSatr = 0;
//                                String allMablaghTakhfifSatr = "-1";
//                                for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs) {
//                                    KalaModel kala = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());
//                                    if (kala.getCcTaminKonandeh() == ccTaminKonandeh) {
//                                        double mablaghTakhfifSatr = (darkhastFaktorSatr.getTedad3() * darkhastFaktorSatr.getMablaghForosh()) * (takhfifSenfiSatr.getDarsadTakhfif() / 100);
//                                        sumMablaghTakhfifSatr += Math.round(mablaghTakhfifSatr);
//                                        allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
//                                        insertFaktorSatrTakhfifSenfi(darkhastFaktorSatr.getCcDarkhastFaktorSatr(), takhfifSenfi.getCcTakhfifSenfi(), takhfifSenfi.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr);
//                                    }//if
//                                }//for
//                                //بروز رسانی مبلغ تخفیف تیتر
//                                //updateMablaghTakhfifDarkhastFaktor(darkhastFaktor.getCcDarkhastFaktor() , takhfifSenfi.getCcTakhfifSenfi());
//                                updateMablaghTakhfifDarkhastFaktor(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), Math.round(mablaghTakhfif), sumMablaghTakhfifSatr, allMablaghTakhfifSatr);
//                            }//if
//                        }//for
//                    }//for
//                }//for
//
//                //********************************************** GorohKala.. **********************************************
//                double sumTedadGorohKala; // Sum Tedad Darkhast Bar Hasbe GorohKala..
//                double sumTedadBastehGorohKala;
//                double sumTedadKartonGorohKala;
//                double sumMablaghKolGorohKala; // Sum Tedad Darkhast Bar Hasbe GorohKala..
//
//                for (TakhfifSenfiModel takhfifSenfi : takhfifSenfis) {
//                    sumTedadGorohKala = 0;
//                    sumTedadBastehGorohKala = 0;
//                    sumTedadKartonGorohKala = 0;
//                    sumMablaghKolGorohKala = 0.0;
//
//                    ArrayList<DataTableModel> gorohKalas = darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi());
//                    ArrayList<DataTableModel> rowGorohKalas = darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifSenfi(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi());
//
//                    for (DataTableModel gorohKala : gorohKalas) {
//                        sumTedadGorohKala = Double.parseDouble(gorohKala.getFiled2());
//                        sumTedadBastehGorohKala = Double.parseDouble(gorohKala.getFiled3());
//                        sumTedadKartonGorohKala = Double.parseDouble(gorohKala.getFiled4());
//                        sumMablaghKolGorohKala = Double.parseDouble(gorohKala.getFiled5());
//
//                        //Satrhaye Takhfif..
//                        ArrayList<TakhfifSenfiSatrModel> takhfifSenfiSatrs = takhfifSenfiSatrDAO.getForFaktor(takhfifSenfi.getCcTakhfifSenfi(), new int[]{discountCalculation.getTedadRialTedad(), discountCalculation.getTedadRialRial()},
//                                new int[]{discountCalculation.getBasteBandiCarton(), discountCalculation.getBasteBandiBaste(), discountCalculation.getBasteBandiAdad()}, DiscountCalculation.NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()),
//                                sumTedadGorohKala, sumTedadBastehGorohKala, sumTedadKartonGorohKala, sumMablaghKolGorohKala, takhfifSenfi.getNoeTedadRial(),0);
//
//                        for (TakhfifSenfiSatrModel takhfifSenfiSatr : takhfifSenfiSatrs) {
//                            int zarib = 0;
//                            if (takhfifSenfiSatr.getBeEza() == 0)
//                                zarib = 1;
//                            else
//                                //Tedad..
//                                if (takhfifSenfi.getNoeTedadRial() == discountCalculation.getTedadRialTedad()) {
//                                    if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiAdad())
//                                        zarib = (int) (sumTedadGorohKala / takhfifSenfiSatr.getBeEza());
//                                    else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiBaste())
//                                        zarib = (int) (sumTedadBastehGorohKala / takhfifSenfiSatr.getBeEza());
//                                    else if (takhfifSenfiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiCarton())
//                                        zarib = (int) (sumTedadKartonGorohKala / takhfifSenfiSatr.getBeEza());
//                                }//if
//                                //Rial..
//                                else if (takhfifSenfi.getNoeTedadRial() == discountCalculation.getTedadRialRial())
//                                    zarib = (int) (sumMablaghKolGorohKala / takhfifSenfiSatr.getBeEza());
//
//                            double mablaghTakhfif = sumMablaghKolGorohKala * (zarib * takhfifSenfiSatr.getDarsadTakhfif() / 100);
//                            //Insert In DarkhastFaktorTakhfif & DarkhastFaktorSatrTakhfif..
//                            if (mablaghTakhfif > 0) {
//                                long sumMablaghTakhfifSatr = 0;
//                                String allMablaghTakhfifSatr = "-1";
//                                insertFaktorTakhfifSenfi(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), takhfifSenfi.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfif);
//                                for (DataTableModel row : rowGorohKalas) {
//                                /*sumMablaghTakhfifSatr = 0;
//                                allMablaghTakhfifSatr = "-1";*/
//                                    if (row.getFiled2().equals(gorohKala.getFiled1())) {
//                                        double mablaghTakhfifSatr = (Double.valueOf(row.getFiled3()) * Double.valueOf(row.getFiled4().toString())) * (takhfifSenfiSatr.getDarsadTakhfif() / 100);
//                                        sumMablaghTakhfifSatr += Math.round(mablaghTakhfifSatr);
//                                        allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
//                                        insertFaktorSatrTakhfifSenfi(Long.valueOf(row.getFiled1()), takhfifSenfi.getCcTakhfifSenfi(), takhfifSenfi.getSharhTakhfif(), takhfifSenfiSatr.getDarsadTakhfif(), mablaghTakhfifSatr);
//                                    }//if
//                                }//for
//                                updateMablaghTakhfifDarkhastFaktor(darkhastFaktor.getCcDarkhastFaktor(), takhfifSenfi.getCcTakhfifSenfi(), Math.round(mablaghTakhfif), sumMablaghTakhfifSatr, allMablaghTakhfifSatr);
//                                //بروز رسانی مبلغ تخفیف تیتر
//                                //updateMablaghTakhfifDarkhastFaktor(darkhastFaktor.getCcDarkhastFaktor() , takhfifSenfi.getCcTakhfifSenfi());
//                            }//if
//                        }//for
//                    }//for
//                }//for
//            } catch (Exception exception) {
//                exception.printStackTrace();
//                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "mohasebeTakhfifSenfiFaktor", "");
//                //mPresenter.onErrorCalculateDiscount(R.string.errorOperation);
//                //onCalculateDiscountResponse.onFailedCalculate(R.string.errorCalculateDiscount);
//                //onProgressUpdate(-1);
//                resultOfProccess = -1;
//                Log.d("VerifyRequestModel", "mohasebeTakhfifSenfiFaktor");
//                mPresenter.onErrorCalculateDiscount(R.string.errorCalculateDiscount);
//                return false;
//            }
//            return true;
//        }


        private boolean insertFaktorTakhfifSenfi(long ccDarkhastFaktor, int ccTakhfifSenfi, String sharhTakhfif, double darsadTakhfif, double mablaghTakhfif/*, int ForJayezeh*/) {
            try {
                ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                String codeTakhfifSenfi = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

                DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                DarkhastFaktorTakhfifModel darkhastFaktorTakhfifModel = new DarkhastFaktorTakhfifModel();
                darkhastFaktorTakhfifModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                darkhastFaktorTakhfifModel.setCcTakhfif(ccTakhfifSenfi);
                darkhastFaktorTakhfifModel.setSharhTakhfif(sharhTakhfif);
                darkhastFaktorTakhfifModel.setCodeNoeTakhfif(Integer.parseInt(codeTakhfifSenfi));
                darkhastFaktorTakhfifModel.setDarsadTakhfif((float) darsadTakhfif);
                darkhastFaktorTakhfifModel.setMablaghTakhfif(Math.round(mablaghTakhfif));
                //darkhastFaktorTakhfifModel.setExtraProp_ForJayezeh(ForJayezeh);
                darkhastFaktorTakhfifModel.setExtraProp_ForJayezeh(0);
                darkhastFaktorTakhfifModel.setExtraProp_IsTakhfifMazad(0);
                darkhastFaktorTakhfifDAO.insert(darkhastFaktorTakhfifModel);
            } catch (Exception exception) {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "insertFaktorTakhfifSenfi", "");
                //mPresenter.onErrorCalculateDiscount(R.string.errorOperation);
                //onCalculateDiscountResponse.onFailedCalculate(R.string.errorCalculateDiscount);
                //onProgressUpdate(-1);
                resultOfProccess = -1;
                Log.d("VerifyRequestModel", "insertFaktorTakhfifSenfi");
                mPresenter.onErrorCalculateDiscount(R.string.errorCalculateDiscount);
                return false;
            }
            return true;
        }

        private boolean insertFaktorSatrTakhfifSenfi(long ccDarkhastFaktorSatr, int ccTakhfifSenfi, String sharhTakhfif, double darsadTakhfif, double mablaghTakhfif/*, int ForJayezeh*/) {
            try {
                ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                String codeTakhfifSenfi = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_SENFI());

                DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
                DarkhastFaktorSatrTakhfifModel darkhastFaktorSatrTakhfifModel = new DarkhastFaktorSatrTakhfifModel();
                darkhastFaktorSatrTakhfifModel.setCcDarkhastFaktorSatr(ccDarkhastFaktorSatr);
                darkhastFaktorSatrTakhfifModel.setCcTakhfif(ccTakhfifSenfi);
                darkhastFaktorSatrTakhfifModel.setSharhTakhfif(sharhTakhfif);
                darkhastFaktorSatrTakhfifModel.setCodeNoeTakhfif(Integer.parseInt(codeTakhfifSenfi));
                darkhastFaktorSatrTakhfifModel.setDarsadTakhfif((float) darsadTakhfif);
                darkhastFaktorSatrTakhfifModel.setMablaghTakhfif(Math.round(mablaghTakhfif));
                //darkhastFaktorSatrTakhfifModel.setExtraProp_ForJayezeh(ForJayezeh);
                darkhastFaktorSatrTakhfifModel.setExtraProp_ForJayezeh(0);
                darkhastFaktorSatrTakhfifDAO.insert(darkhastFaktorSatrTakhfifModel);
            } catch (Exception exception) {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "insertFaktorSatrTakhfif", "");
                //mPresenter.onErrorCalculateDiscount(R.string.errorOperation);
                //onCalculateDiscountResponse.onFailedCalculate(R.string.errorCalculateDiscount);
                //onProgressUpdate(-1);
                resultOfProccess = -1;
                Log.d("VerifyRequestModel", "insertFaktorSatrTakhfifSenfi");
                mPresenter.onErrorCalculateDiscount(R.string.errorCalculateDiscount);
                return false;
            }
            return true;
        }


        ///////////////////////// Takhfif Hajmi /////////////////////////

        private void mohasebeTakhfifHajmi(DarkhastFaktorModel darkhastFaktorModel, ArrayList<ParameterChildModel> parameterChildModels)
        {
            DiscountCalculation discountCalculation = new DiscountCalculation(parameterChildModels);
            ArrayList<TakhfifHajmiTitrSatrModel> takhfifHajmiTitrSatrModels = discountCalculation.getTakhfifHajmis(mPresenter.getAppContext(), darkhastFaktorModel, valueNoeVosol, false);
            int maxOlaviat = new TakhfifHajmiDAO(mPresenter.getAppContext()).getMaxOlaviat();
            for (int i = 0 ; i <= maxOlaviat ; i++)
            {
                Log.d("takhfif" , "olaviat : " + maxOlaviat + " , i : " + i + " , takhfifHajmi.Size : " + takhfifHajmiTitrSatrModels.size());
                for (TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel : takhfifHajmiTitrSatrModels)
                {
                    Log.d("takhfif" , "olaviat takhfif : " + takhfifHajmiTitrSatrModel.getOlaviat());
                    if (takhfifHajmiTitrSatrModel.getOlaviat() == i)
                    {
                        Log.d("takhfif" , "takhfifHajmiTitrSatrModel.getCcGorohTakidi() : " + takhfifHajmiTitrSatrModel.getCcGorohTakidi());
                        Log.d("takhfif" , "takhfifHajmi : " + takhfifHajmiTitrSatrModel.toString());
                        if (takhfifHajmiTitrSatrModel.getNameNoeField() == DiscountCalculation.NAME_NOE_FIELD_KALA && takhfifHajmiTitrSatrModel.getCcGorohTakidi() == 0)
                        {
                            Log.d("takhfifHajmi" , "noe field : kala");
                            CalculateHajmiDiscountKala calculateHajmiDiscountKala = new CalculateHajmiDiscountKala(mPresenter.getAppContext());
                            if (takhfifHajmiTitrSatrModel.getIsPelekani() == 0 && takhfifHajmiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateHajmiDiscountKala.calculateDiscount(darkhastFaktorModel, takhfifHajmiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","calculateDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifHajmiTitrSatrModel.getIsPelekani() == 1 && takhfifHajmiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateHajmiDiscountKala.calculatePelekaniDiscount(darkhastFaktorModel, takhfifHajmiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","calculatePelekaniDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifHajmiTitrSatrModel.getIsPelekani() == 0 && takhfifHajmiTitrSatrModel.getCcMantagheh() != 0)
                            {
                                resultOfProccess = calculateHajmiDiscountKala.calculateMantaghe(darkhastFaktorModel, takhfifHajmiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","calculateMantaghe-resultOfProccess" +resultOfProccess);
                            }
                        }
                        else if (takhfifHajmiTitrSatrModel.getNameNoeField() == DiscountCalculation.NAME_NOE_FIELD_GOROH_KALA)
                        {
                            Log.d("takhfifHajmi" , "noe field : goroh kala");
                            CalculateHajmiDiscountGorohKala calculateHajmiDiscountGorohKala = new CalculateHajmiDiscountGorohKala(mPresenter.getAppContext());
                            if (takhfifHajmiTitrSatrModel.getCcGorohTakidi() == 0 && takhfifHajmiTitrSatrModel.getIsPelekani() == 0 && takhfifHajmiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateHajmiDiscountGorohKala.calculateDiscount(darkhastFaktorModel, takhfifHajmiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","GorohKala-calculateDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifHajmiTitrSatrModel.getCcGorohTakidi() == 0 && takhfifHajmiTitrSatrModel.getIsPelekani() == 1 && takhfifHajmiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateHajmiDiscountGorohKala.calculatePelekani(darkhastFaktorModel, takhfifHajmiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","GorohKala-calculatePelekani-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifHajmiTitrSatrModel.getCcGorohTakidi() == 0 && takhfifHajmiTitrSatrModel.getIsPelekani() == 0 && takhfifHajmiTitrSatrModel.getCcMantagheh() != 0)
                            {
                                int ccMantaghe = new MoshtaryAddressDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).get(0).getCcMahaleh();
                                resultOfProccess = calculateHajmiDiscountGorohKala.calculateMantaghe(darkhastFaktorModel, takhfifHajmiTitrSatrModel, ccMantaghe) ? 1 : -1;
                                Log.d("VerifyRequestModel","GorohKala-calculateMantaghe-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifHajmiTitrSatrModel.getCcGorohTakidi() != 0 && takhfifHajmiTitrSatrModel.getIsPelekani() == 0 && takhfifHajmiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                Log.d("takhfifHajmi" , "noe field : kala Takidi");
                                //resultOfProccess = calculateHajmiDiscountGorohKala.calculateDiscountTakidi(darkhastFaktorModel, takhfifHajmiTitrSatrModel) ? 1 : -1;
                                resultOfProccess = calculateGorohKalaTakidi(takhfifHajmiTitrSatrModel, darkhastFaktorModel, discountCalculation) ? 1 : -1;
                                Log.d("VerifyRequestModel","GorohKala-calculateGorohKalaTakidi-resultOfProccess" +resultOfProccess);
                            }
                        }
                        else if (takhfifHajmiTitrSatrModel.getNameNoeField() == DiscountCalculation.NAME_NOE_FIELD_BRAND && takhfifHajmiTitrSatrModel.getCcGorohTakidi() == 0)
                        {
                            Log.d("takhfifHajmi" , "noe field : brand");
                            CalculateHajmiDiscountBrand calculateHajmiDiscountBrand = new CalculateHajmiDiscountBrand(mPresenter.getAppContext());
                            if (takhfifHajmiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateHajmiDiscountBrand.calculateDiscount(darkhastFaktorModel, takhfifHajmiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","Brand-calculateDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifHajmiTitrSatrModel.getCcMantagheh() != 0)
                            {
                                int ccMantaghe = new MoshtaryAddressDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).get(0).getCcMahaleh();
                                resultOfProccess = calculateHajmiDiscountBrand.calculateMantaghe(darkhastFaktorModel, takhfifHajmiTitrSatrModel, ccMantaghe) ? 1 : -1;
                                Log.d("VerifyRequestModel","Brand-calculateHajmiDiscountBrand-resultOfProccess" +resultOfProccess);
                            }
                        }
                        else if (takhfifHajmiTitrSatrModel.getNameNoeField() == DiscountCalculation.NAME_NOE_FIELD_TAMIN_KONANDE && takhfifHajmiTitrSatrModel.getCcGorohTakidi() == 0)
                        {

                            CalculateHajmiDiscountTaminKonandeh calculateHajmiDiscountTaminKonandeh = new CalculateHajmiDiscountTaminKonandeh(mPresenter.getAppContext() , parameterChildModels);
                            if (takhfifHajmiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                Log.d("takhfifHajmi" , "noe field : tamin konande");
                                resultOfProccess = calculateHajmiDiscountTaminKonandeh.calculateDiscount(darkhastFaktorModel, takhfifHajmiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","TaminKonandeh-calculateDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifHajmiTitrSatrModel.getCcMantagheh() != 0)
                            {
                                int ccMantaghe = new MoshtaryAddressDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).get(0).getCcMahaleh();
                                resultOfProccess = calculateHajmiDiscountTaminKonandeh.calculateMantaghe(darkhastFaktorModel, takhfifHajmiTitrSatrModel, ccMantaghe) ? 1 : -1;
                                Log.d("VerifyRequestModel","TaminKonandeh-calculateMantaghe-resultOfProccess" +resultOfProccess);
                            }
                        }
                    }
                }
            }
        }

        private void mohasebeTakhfifSenfi(DarkhastFaktorModel darkhastFaktorModel, ArrayList<ParameterChildModel> parameterChildModels)
        {
            DiscountCalculation discountCalculation = new DiscountCalculation(parameterChildModels);
            ArrayList<TakhfifSenfiTitrSatrModel> takhfifSenfiTitrSatrModels = discountCalculation.getTakhfifSenfis(mPresenter.getAppContext(), darkhastFaktorModel, valueNoeVosol, false);
            int maxOlaviat = new TakhfifSenfiDAO(mPresenter.getAppContext()).getMaxOlaviat();
            for (int i = 0 ; i <= maxOlaviat ; i++)
            {
                Log.d("takhfif" , "olaviat : " + maxOlaviat + " , i : " + i + " , takhfifSenfi.Size : " + takhfifSenfiTitrSatrModels.size());
                for (TakhfifSenfiTitrSatrModel takhfifSenfiTitrSatrModel : takhfifSenfiTitrSatrModels)
                {
                    Log.d("takhfif" , "olaviat takhfif : " + takhfifSenfiTitrSatrModel.getOlaviat());
                    if (takhfifSenfiTitrSatrModel.getOlaviat() == i)
                    {
                        Log.d("takhfif" , "takhfifSenfiTitrSatrModel.getCcGorohTakidi() : " + takhfifSenfiTitrSatrModel.getCcGorohTakidi());
                        Log.d("takhfif" , "takhfifSenfi : " + takhfifSenfiTitrSatrModel.toString());
                        if (takhfifSenfiTitrSatrModel.getNameNoeField() == DiscountCalculation.NAME_NOE_FIELD_KALA && takhfifSenfiTitrSatrModel.getCcGorohTakidi() == 0)
                        {
                            Log.d("takhfifSenfi" , "noe field : kala");
                            CalculateSenfiDiscountKala calculateSenfiDiscountKala = new CalculateSenfiDiscountKala(mPresenter.getAppContext());
                            if (takhfifSenfiTitrSatrModel.getIsPelekani() == 0 && takhfifSenfiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateSenfiDiscountKala.calculateDiscount(darkhastFaktorModel, takhfifSenfiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","calculateDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifSenfiTitrSatrModel.getIsPelekani() == 1 && takhfifSenfiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateSenfiDiscountKala.calculatePelekaniDiscount(darkhastFaktorModel, takhfifSenfiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","calculatePelekaniDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifSenfiTitrSatrModel.getIsPelekani() == 0 && takhfifSenfiTitrSatrModel.getCcMantagheh() != 0)
                            {
                                resultOfProccess = calculateSenfiDiscountKala.calculateMantaghe(darkhastFaktorModel, takhfifSenfiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","calculateMantaghe-resultOfProccess" +resultOfProccess);
                            }
                        }
                        else if (takhfifSenfiTitrSatrModel.getNameNoeField() == DiscountCalculation.NAME_NOE_FIELD_GOROH_KALA)
                        {
                            Log.d("takhfifSenfi" , "noe field : goroh kala");
                            CalculateSenfiDiscountGorohKala calculateSenfiDiscountGorohKala = new CalculateSenfiDiscountGorohKala(mPresenter.getAppContext());
                            if (takhfifSenfiTitrSatrModel.getCcGorohTakidi() == 0 && takhfifSenfiTitrSatrModel.getIsPelekani() == 0 && takhfifSenfiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateSenfiDiscountGorohKala.calculateDiscount(darkhastFaktorModel, takhfifSenfiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","GorohKala-calculateDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifSenfiTitrSatrModel.getCcGorohTakidi() == 0 && takhfifSenfiTitrSatrModel.getIsPelekani() == 1 && takhfifSenfiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateSenfiDiscountGorohKala.calculatePelekani(darkhastFaktorModel, takhfifSenfiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","GorohKala-calculatePelekani-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifSenfiTitrSatrModel.getCcGorohTakidi() == 0 && takhfifSenfiTitrSatrModel.getIsPelekani() == 0 && takhfifSenfiTitrSatrModel.getCcMantagheh() != 0)
                            {
                                int ccMantaghe = new MoshtaryAddressDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).get(0).getCcMahaleh();
                                resultOfProccess = calculateSenfiDiscountGorohKala.calculateMantaghe(darkhastFaktorModel, takhfifSenfiTitrSatrModel, ccMantaghe) ? 1 : -1;
                                Log.d("VerifyRequestModel","GorohKala-calculateMantaghe-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifSenfiTitrSatrModel.getCcGorohTakidi() != 0 && takhfifSenfiTitrSatrModel.getIsPelekani() == 0 && takhfifSenfiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                Log.d("takhfifSenfi" , "noe field : kala Takidi");
                                //resultOfProccess = calculateSenfiDiscountGorohKala.calculateDiscountTakidi(darkhastFaktorModel, takhfifSenfiTitrSatrModel) ? 1 : -1;
                                //todo Takidi
                                //resultOfProccess = calculateGorohKalaTakidi(takhfifSenfiTitrSatrModel, darkhastFaktorModel, discountCalculation) ? 1 : -1;
                                Log.d("VerifyRequestModel","GorohKala-calculateGorohKalaTakidi-resultOfProccess" +resultOfProccess);
                            }
                        }
                        else if (takhfifSenfiTitrSatrModel.getNameNoeField() == DiscountCalculation.NAME_NOE_FIELD_BRAND && takhfifSenfiTitrSatrModel.getCcGorohTakidi() == 0)
                        {
                            Log.d("takhfifSenfi" , "noe field : brand");
                            CalculateSenfiDiscountBrand calculateSenfiDiscountBrand = new CalculateSenfiDiscountBrand(mPresenter.getAppContext());
                            if (takhfifSenfiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                resultOfProccess = calculateSenfiDiscountBrand.calculateDiscount(darkhastFaktorModel, takhfifSenfiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","Brand-calculateDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifSenfiTitrSatrModel.getCcMantagheh() != 0)
                            {
                                int ccMantaghe = new MoshtaryAddressDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).get(0).getCcMahaleh();
                                resultOfProccess = calculateSenfiDiscountBrand.calculateMantaghe(darkhastFaktorModel, takhfifSenfiTitrSatrModel, ccMantaghe) ? 1 : -1;
                                Log.d("VerifyRequestModel","Brand-calculateSenfiDiscountBrand-resultOfProccess" +resultOfProccess);
                            }
                        }
                        else if (takhfifSenfiTitrSatrModel.getNameNoeField() == DiscountCalculation.NAME_NOE_FIELD_TAMIN_KONANDE && takhfifSenfiTitrSatrModel.getCcGorohTakidi() == 0)
                        {

                            CalculateSenfiDiscountTaminKonandeh calculateSenfiDiscountTaminKonandeh = new CalculateSenfiDiscountTaminKonandeh(mPresenter.getAppContext() , parameterChildModels);
                            if (takhfifSenfiTitrSatrModel.getCcMantagheh() == 0)
                            {
                                Log.d("takhfifSenfi" , "noe field : tamin konande");
                                resultOfProccess = calculateSenfiDiscountTaminKonandeh.calculateDiscount(darkhastFaktorModel, takhfifSenfiTitrSatrModel) ? 1 : -1;
                                Log.d("VerifyRequestModel","TaminKonandeh-calculateDiscount-resultOfProccess" +resultOfProccess);
                            }
                            else if (takhfifSenfiTitrSatrModel.getCcMantagheh() != 0)
                            {
                                int ccMantaghe = new MoshtaryAddressDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).get(0).getCcMahaleh();
                                resultOfProccess = calculateSenfiDiscountTaminKonandeh.calculateMantaghe(darkhastFaktorModel, takhfifSenfiTitrSatrModel, ccMantaghe) ? 1 : -1;
                                Log.d("VerifyRequestModel","TaminKonandeh-calculateMantaghe-resultOfProccess" +resultOfProccess);
                            }
                        }
                    }
                }
            }
        }

        private boolean calculateGorohKalaTakidi(TakhfifHajmiTitrSatrModel takhfifHajmiTitrSatrModel, DarkhastFaktorModel darkhastFaktorModel, DiscountCalculation discountCalculation)
        {
            try
            {
                MoshtaryModel moshtaryModel = new MoshtaryDAO(mPresenter.getAppContext()).getByccMoshtary(darkhastFaktorModel.getCcMoshtary());
                DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(mPresenter.getAppContext());
                //********************************************** GorohKalaTakidi **********************************************
                Log.d("takhfifHajmi" , "goroh kala takidi takhfif hajmi : " + takhfifHajmiTitrSatrModel.toString());
                if (takhfifHajmiTitrSatrModel.getCcGorohTakidi() != 0)
                {
                    double sumTedadGorohKala= 0;
                    double sumTedadBastehGorohKala= 0;
                    double sumTedadKartonGorohKala= 0;
                    double sumMablaghForoshGorohKala= 0;
                    double sumMablaghKolGorohKala= 0;
                    int sizeKalaGoroh = 0;
                    int tedadKalaTakidi = 0;

                    DecimalFormat twoDForm = new DecimalFormat("#.##");

                    KalaGorohDAO kalaGorohDAO = new KalaGorohDAO(mPresenter.getAppContext());
                    ArrayList<KalaGorohModel> kalaGorohModels = kalaGorohDAO.getByccGoroh(takhfifHajmiTitrSatrModel.getCcGorohTakidi());
                    sizeKalaGoroh = kalaGorohModels.size();
                    ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrs = new DarkhastFaktorSatrDAO(mPresenter.getAppContext()).getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());

                    for (DarkhastFaktorSatrModel darkhastFaktorSatrModel : darkhastFaktorSatrs)
                    {
                        for (KalaGorohModel kalaGorohModel : kalaGorohModels)
                        {
                            if (darkhastFaktorSatrModel.getCcKalaCode() == kalaGorohModel.getCcKalaCode())
                            {
                                tedadKalaTakidi++;
                            }
                        }
                    }

                    Log.d("takhfifHajmi" , "tedadKalaTakidi : " + tedadKalaTakidi);

                    if (tedadKalaTakidi >= 1)
                    {
                        //-----------------------------------------
                        ArrayList<DataTableModel> gorohKalas= darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getOlaviat(),takhfifHajmiTitrSatrModel.getNoeGheymat());
                        ArrayList<DataTableModel> rowGorohKalas= darkhastFaktorSatrDAO.getRowsBeTafkikGorohKalaAndTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(),takhfifHajmiTitrSatrModel.getNoeGheymat());

                        for (DataTableModel gorohKala : gorohKalas)
                        {
                            sumTedadGorohKala= Double.valueOf(gorohKala.getFiled2());
                            sumTedadBastehGorohKala = Double.valueOf(gorohKala.getFiled3());
                            sumTedadKartonGorohKala = Double.valueOf(gorohKala.getFiled4());
//                            sumMablaghForoshGorohKala = Double.valueOf(gorohKala.getfiled5());
                            sumMablaghKolGorohKala = Double.valueOf(gorohKala.getFiled5());

                            Log.d("takhfif" , "takhfif hajmi : " + sumTedadGorohKala + " , " + sumTedadBastehGorohKala + " , " + sumTedadKartonGorohKala + " , " + sumMablaghKolGorohKala);

                            int DarajehBrandMoshtary = moshtaryModel.getDarajeh();
                            if(DarajehBrandMoshtary == takhfifHajmiTitrSatrModel.getDarajeh())
                            {

                                //Set Tedad Karton For TakhfifKartoni
                                TakhfifHajmiSatrModel takhfifhajmiSatr = takhfifHajmiSatrDAO.getByccTakhfifHajmi(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi()).get(0);
                                if(takhfifhajmiSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiCarton())
                                {
                                    ArrayList<DataTableModel> gorohs = darkhastFaktorSatrDAO.getTedadKartonByccGorohKala(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Integer.valueOf(gorohKala.getFiled1()));
                                    sumTedadGorohKala= Float.valueOf(gorohs.get(0).getFiled4());
                                    sumTedadBastehGorohKala = Float.valueOf(gorohs.get(0).getFiled3());
                                    sumTedadKartonGorohKala = Float.valueOf(gorohs.get(0).getFiled2());
                                    //sumTedadKartonGorohKala = Double.valueOf(twoDForm.format(sumTedadKartonGorohKala));
                                }
                                //Satrhaye Takhfif..
                                ArrayList<TakhfifHajmiSatrModel> takhfifHajmiSatrs = takhfifHajmiSatrDAO.getForFaktor(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi() ,
                                        new int[]{discountCalculation.getTedadRialTedad(),discountCalculation.getTedadRialRial(),discountCalculation.getTedadRialVazn(),discountCalculation.getTedadRialAghlam()},
                                        new int[]{discountCalculation.getBasteBandiCarton(),discountCalculation.getBasteBandiBaste(),discountCalculation.getBasteBandiAdad()},
                                        DiscountCalculation.NAME_NOE_FIELD_GOROH_KALA, Integer.valueOf(gorohKala.getFiled1()),
                                        sumTedadGorohKala, sumTedadBastehGorohKala, sumTedadKartonGorohKala, sumMablaghKolGorohKala, takhfifHajmiTitrSatrModel.getNoeTedadRial(),0,0);
                                //TODO:Vazn

                                for (TakhfifHajmiSatrModel takhfifHajmiSatr : takhfifHajmiSatrs)
                                {
                                    Log.d("takhfif" , "in for");
                                    int zarib = 0;
                                    int MablaghVahed = (int)(sumMablaghKolGorohKala/sumTedadGorohKala);
                                    int Tedad = 0;
                                    if (takhfifHajmiSatr.getBeEza()== 0)
                                    {
                                        Log.d("takhfif" , " 1 basteBandiCarton : " + discountCalculation.getBasteBandiCarton() + " , takhfifHajmiSatr.getCodeNoeBastehBandyBeEza : " + takhfifHajmiSatr.getCodeNoeBastehBandyBeEza());
                                        zarib= 1;
                                        if (takhfifHajmiTitrSatrModel.getNoeTedadRial()== discountCalculation.getTedadRialTedad())
                                        {
                                            if (takhfifHajmiSatr.getCodeNoeBastehBandy() ==  discountCalculation.getBasteBandiAdad())
                                                Tedad= (int)sumTedadGorohKala;
                                            else if (takhfifHajmiSatr.getCodeNoeBastehBandy() ==  discountCalculation.getBasteBandiBaste())
                                                Tedad = (Integer.valueOf(gorohKala.getFiled7()));
                                            else if (takhfifHajmiSatr.getCodeNoeBastehBandy() ==  discountCalculation.getBasteBandiCarton())
                                                Tedad = (Integer.valueOf(gorohKala.getFiled6()));
                                        }
                                    }
                                    else
                                    {
                                        //Tedad..
                                        if (takhfifHajmiTitrSatrModel.getNoeTedadRial()== discountCalculation.getTedadRialTedad())
                                        {
                                            Log.d("takhfif" , " 2 basteBandiCarton : " + discountCalculation.getBasteBandiCarton() + " , takhfifHajmiSatr.getCodeNoeBastehBandyBeEza : " + takhfifHajmiSatr.getCodeNoeBastehBandyBeEza());
                                            if (takhfifHajmiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiAdad())
                                            {
                                                zarib= (int)(sumTedadGorohKala/ takhfifHajmiSatr.getBeEza());
                                                Tedad= (int)sumTedadGorohKala;
                                            }
                                            else if (takhfifHajmiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiBaste())
                                            {
                                                zarib= (int)(sumTedadBastehGorohKala/ takhfifHajmiSatr.getBeEza());
                                                Tedad = (Integer.valueOf(gorohKala.getFiled7()));
                                            }
                                            else if (takhfifHajmiSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiCarton())
                                            {
                                                zarib= (int)(sumTedadKartonGorohKala/ takhfifHajmiSatr.getBeEza());
                                                Tedad = (Integer.valueOf(gorohKala.getFiled6()));
                                            }
                                        }
                                        //Rial..
                                        else if (takhfifHajmiTitrSatrModel.getNoeTedadRial()== discountCalculation.getTedadRialRial())
                                        {
                                            zarib= (int)(sumMablaghKolGorohKala/ takhfifHajmiSatr.getBeEza());
                                        }
                                    }

                                    Log.d("takhfif" , "takhfif hajim : " + zarib + " , " + Tedad);
                                    if(takhfifHajmiTitrSatrModel.getForJayezeh() != 2 )
                                    {
                                        double mablaghTakhfif=0;
//                                              mablaghTakhfif= sumMablaghKolGorohKala * (zarib* takhfifHajmiSatr.getDarsadTakhfif()/ 100);

                                        //Mohasebeh Barasas BeEza
                                        if(takhfifHajmiSatr.getBeEza()==0)
                                        {
                                            mablaghTakhfif = sumMablaghKolGorohKala  * (zarib* takhfifHajmiSatr.getDarsadTakhfif()/ 100);
                                            mablaghTakhfif = Math.round(mablaghTakhfif);
                                            Log.d("takhfif" , "takhfif hajmi in if : " + sumMablaghKolGorohKala + " , " + sumMablaghKolGorohKala + " , " + takhfifHajmiSatr.getDarsadTakhfif());
                                        }
                                        else
                                        {
                                            mablaghTakhfif= MablaghVahed * takhfifHajmiSatr.getBeEza() * (zarib* takhfifHajmiSatr.getDarsadTakhfif()/ 100);
                                            Log.d("takhfif" , "takhfif hajmi in else : " + MablaghVahed + " , " + takhfifHajmiSatr.getBeEza()  + " , " + Tedad + " , " + zarib + " , " + takhfifHajmiSatr.getDarsadTakhfif() + " , " + mablaghTakhfif);
                                        }
                                        if (mablaghTakhfif > 0)
                                        {
                                            insertFaktorTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(),
                                                    takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfif, takhfifHajmiTitrSatrModel.getForJayezeh());

                                            int TedadMandeh =((int)takhfifHajmiSatr.getBeEza()==0) ? Tedad:((int)takhfifHajmiSatr.getBeEza())*zarib;
                                            int TedadSatr =0;
                                            long sumMablaghTalkhfifSatr = 0;
                                            String allMablaghTakhfifSatr = "-1";
                                            for (DataTableModel row : rowGorohKalas)
                                            {
                                                if (row.getFiled2().equals(gorohKala.getFiled1()) /*&& ((TedadMandeh>0 && takhfifHajmi.getNoeTedadRial()== tedadRialTedad) || takhfifHajmi.getNoeTedadRial()== tedadRialRial)*/)
                                                {
                                                    double mablaghTakhfifSatr = 0;
                                                    double mablaghTakhfifVahed = 0;
                                                    if (takhfifHajmiTitrSatrModel.getNoeTedadRial() == discountCalculation.getTedadRialTedad())
                                                    {
                                                        mablaghTakhfifVahed = mablaghTakhfif/Tedad;
                                                        mablaghTakhfifSatr = Double.valueOf(row.getFiled4())* mablaghTakhfifVahed ;
                                                    }
                                                    else
                                                    {
                                                        mablaghTakhfifSatr = Double.valueOf(row.getFiled3()) * Double.valueOf(row.getFiled4())* (takhfifHajmiSatr.getDarsadTakhfif()/100);
                                                    }
                                                    if (mablaghTakhfifSatr > 0)
                                                    {
                                                        sumMablaghTalkhfifSatr += Math.round(mablaghTakhfifSatr);
                                                        allMablaghTakhfifSatr += "," + Math.round(mablaghTakhfifSatr);
                                                        insertFaktorSatrTakhfifHajmi(Long.valueOf(row.getFiled1()), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(),
                                                                takhfifHajmiSatr.getDarsadTakhfif(), mablaghTakhfifSatr, takhfifHajmiTitrSatrModel.getForJayezeh());

                                                    }
                                                }
                                            }
                                            //بروز رسانی مبلغ تخفیف تیتر
                                            updateMablaghTakhfifDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), Math.round(mablaghTakhfif), sumMablaghTalkhfifSatr, allMablaghTakhfifSatr);
                                        }
                                    }
                                    //-------------- تخفیفات جایزه پروموشن
                                    if(takhfifHajmiTitrSatrModel.getForJayezeh() == 2 )
                                    {
                                        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
                                        double mablaghTakhfifByGoroh= sumMablaghKolGorohKala * (zarib* takhfifHajmiSatr.getDarsadTakhfif()/ 100);
                                        int ccGorohNoeMoshtary = selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeMoshtary() , -1);
                                        double mablaghMandehTakhfifPasAzKasrPoromotion = setJayezehPromotionByGorohKala(takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), ccGorohNoeMoshtary, mablaghTakhfifByGoroh, darkhastFaktorModel.getCcDarkhastFaktor());
                                        if (mablaghMandehTakhfifPasAzKasrPoromotion > 0)
                                        {
                                            insertFaktorTakhfifHajmi(darkhastFaktorModel.getCcDarkhastFaktor(), takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghMandehTakhfifPasAzKasrPoromotion, takhfifHajmiTitrSatrModel.getForJayezeh());
                                            long ccDarkhastFaktorSatr =  Long.valueOf(rowGorohKalas.get(0).getFiled1());
                                            insertFaktorSatrTakhfifHajmi(ccDarkhastFaktorSatr, takhfifHajmiTitrSatrModel.getCcTakhfifHajmi(), takhfifHajmiTitrSatrModel.getSharhTakhfif(), takhfifHajmiSatr.getDarsadTakhfif(), mablaghMandehTakhfifPasAzKasrPoromotion, takhfifHajmiTitrSatrModel.getForJayezeh());
                                            //بروز رسانی مبلغ تخفیف تیتر
                                            //updateMablaghTakhfifDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor() , takhfifHajmi.getCcTakhfifHajmi());
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }

        private double setJayezehPromotionByGorohKala(int ccTakhfifHajmi, int ccGorohMoshtary, double MablaghTakhfifGorohKala, long ccDarkhastFaktor)
        {
            double MandehMablagh_TedadJayzeh = MablaghTakhfifGorohKala ;
            double MablaghForosh_KalaPromotion = 0;
            int TedadJayzehPromotion = 0;
            int TedadMojodyGhabelForosh = 0;

            JayezehEntekhabiDAO jayezehEntekhabiDAO = new JayezehEntekhabiDAO(mPresenter.getAppContext());
            JayezehEntekhabiModel jayezehEntekhabiModel = jayezehEntekhabiDAO.getKalaPromotionByccTakhfifHajmi(ccGorohMoshtary, ccTakhfifHajmi);

            MablaghForosh_KalaPromotion = jayezehEntekhabiModel.getMablaghForosh();
            String NameKala = jayezehEntekhabiModel.getNameKala();
            TedadJayzehPromotion = (int)( MablaghTakhfifGorohKala / MablaghForosh_KalaPromotion);

            //CheckTedadMojody.......
            KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
            //Kala kala = kalaDao.GetByccKalaCode(jayzehEntekhabi.getccKalaCode());
            TedadMojodyGhabelForosh = kalaDAO.getSumTedadMojodyKala(jayezehEntekhabiModel.getCcKalaCode());

            if(MablaghForosh_KalaPromotion > 0 & TedadMojodyGhabelForosh > TedadJayzehPromotion )
            {
                //TedadJayzehPromotion = (int)( MablaghTakhfifGorohKala / MablaghForosh_KalaPromotion);
                MandehMablagh_TedadJayzeh= (int)Math.round(MablaghTakhfifGorohKala - (MablaghForosh_KalaPromotion * TedadJayzehPromotion)) ;

                if(TedadJayzehPromotion > 0 )
                {
                    insertJayezeh(ccDarkhastFaktor, jayezehEntekhabiModel.getCcJayezeh(), 0, DarkhastFaktorJayezehModel.CodeNoeJayezehAuto(), mPresenter.getAppContext().getResources().getString(R.string.jayezePromotion) + NameKala, jayezehEntekhabiModel.getCcKala(), jayezehEntekhabiModel.getCcKalaCode(), TedadJayzehPromotion);
                }
            }
            return MandehMablagh_TedadJayzeh;
        }


        private void insertFaktorTakhfifHajmi(long ccDarkhastFaktor, int ccTakhfifHajmi, String sharhTakhfif, double darsadTakhfif, double mablaghTakhfif, int ForJayezeh)
        {
            try
            {
                ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                String codeTakhfifHajmi = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

                DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                DarkhastFaktorTakhfifModel model = new DarkhastFaktorTakhfifModel();
                model.setCcDarkhastFaktor(ccDarkhastFaktor);
                model.setCcTakhfif(ccTakhfifHajmi);
                model.setSharhTakhfif(sharhTakhfif);
                model.setCodeNoeTakhfif(Integer.parseInt(codeTakhfifHajmi));
                model.setDarsadTakhfif((float) darsadTakhfif);
                model.setMablaghTakhfif(Math.round(mablaghTakhfif));
                model.setExtraProp_ForJayezeh(ForJayezeh);
                model.setExtraProp_IsTakhfifMazad(0);
                darkhastFaktorTakhfifDAO.insert(model);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "InsertFaktorTakhfifHajmi", "");
                //mPresenter.onErrorCalculateDiscount(R.string.errorOperation);
                //onCalculateDiscountResponse.onFailedCalculate(R.string.errorCalculateDiscount);
                //onProgressUpdate(-1);
                resultOfProccess = -1;
                Log.d("VerifyRequestModel","insertFaktorTakhfifHajmi");
                mPresenter.onErrorCalculateDiscount(R.string.errorCalculateDiscount);
            }
        }


        private void insertFaktorSatrTakhfifHajmi(long ccDarkhastFaktorSatr, int ccTakhfifHajmi, String sharhTakhfif, double darsadTakhfif, double mablaghTakhfif, int ForJayezeh)
        {
            try
            {
                ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                String codeTakhfifHajmi = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_HAJMI());

                DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
                DarkhastFaktorSatrTakhfifModel darkhastFaktorSatrTakhfifModel = new DarkhastFaktorSatrTakhfifModel();
                darkhastFaktorSatrTakhfifModel.setCcDarkhastFaktorSatr(ccDarkhastFaktorSatr);
                darkhastFaktorSatrTakhfifModel.setCcTakhfif(ccTakhfifHajmi);
                darkhastFaktorSatrTakhfifModel.setSharhTakhfif(sharhTakhfif);
                darkhastFaktorSatrTakhfifModel.setCodeNoeTakhfif(Integer.parseInt(codeTakhfifHajmi));
                darkhastFaktorSatrTakhfifModel.setDarsadTakhfif((float) darsadTakhfif);
                darkhastFaktorSatrTakhfifModel.setMablaghTakhfif(Math.round(mablaghTakhfif));
                darkhastFaktorSatrTakhfifModel.setExtraProp_ForJayezeh(ForJayezeh);
                darkhastFaktorSatrTakhfifDAO.insert(darkhastFaktorSatrTakhfifModel);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "insertFaktorSatrTakhfifHajmi", "");
                //mPresenter.onErrorCalculateDiscount(R.string.errorOperation);
                //onCalculateDiscountResponse.onFailedCalculate(R.string.errorCalculateDiscount);
                //onProgressUpdate(-1);
                resultOfProccess = -1;
                Log.d("VerifyRequestModel","insertFaktorSatrTakhfifHajmi");
                mPresenter.onErrorCalculateDiscount(R.string.errorCalculateDiscount);
            }
        }



        /**
         *
         * @param ccDarkhastFaktor
         * @param ccJayezeh
         * @param codeNoeJayezeh اگر 1 باشد به این معنی است که جایزه به صورت اتوماتیک داده شده ولی اگر 2 باشد به این معنی است که تیتر جایزه ثبت شده و فروشنده باید جایزه را انتخاب نماید(یعنی جایزه انتخابی است)
         * @param SharhJayezeh
         * @param ccKala
         * @param ccKalaCode
         * @param Tedad
         * @return
         */
        private boolean insertJayezeh(long ccDarkhastFaktor, int ccJayezeh, int ccJayezehSatr, int codeNoeJayezeh, String SharhJayezeh,int ccKala, int ccKalaCode, int Tedad)
        {
            Log.d("jayezeh" , "ccDarkhastFaktor insert jayezeh : " + ccDarkhastFaktor);
            try
            {
                DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(mPresenter.getAppContext());
                DarkhastFaktorJayezehModel darkhastFaktorJayezehModel = new DarkhastFaktorJayezehModel();
                darkhastFaktorJayezehModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                darkhastFaktorJayezehModel.setCcJayezeh(ccJayezeh);
                darkhastFaktorJayezehModel.setSharh(SharhJayezeh);
                darkhastFaktorJayezehModel.setCcKala(ccKala);
                darkhastFaktorJayezehModel.setCcKalaCode(ccKalaCode);
                darkhastFaktorJayezehModel.setTedad(Tedad);
                darkhastFaktorJayezehModel.setExtraProp_IsJayezehEntekhabi(0);
                darkhastFaktorJayezehModel.setExtraProp_CodeNoeJayezeh(codeNoeJayezeh);
                darkhastFaktorJayezehModel.setExtraProp_ccJayezehSatr(ccJayezehSatr);
                return darkhastFaktorJayezehDAO.insert(darkhastFaktorJayezehModel);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "VerifyRequestModel", "", "insertJayezeh", "");
                return false;
            }
        }


        private void updateMablaghTakhfifDarkhastFaktor(long ccDarkhastFaktor , int ccTakhfif , long mablaghTakhfifTitr , long sumMablaghTakhfifSatr , String allMablaghTakhfifSatr)
        {
            DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
        /*long mablaghTakhfifTitr = darkhastFaktorTakhfifDAO.getByccDarkhastFaktorsAndccTakhfifs(ccDarkhastFaktor, ccTakhfif , mablaghTakhfif);

        DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
        long mablaghTakhfifSatr = darkhastFaktorSatrTakhfifDAO.getSumMablaghTakhfifByccDarkhastFaktorAndccTakhfif(ccDarkhastFaktor, ccTakhfif , allMablaghTakhfifSatr);*/

            int intMaxDiff = 10;
            try
            {
                intMaxDiff = Integer.parseInt(new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_MAX_DIFF_TAKHFIF_TITR_BY_SATR()));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "updateMablaghTakhfifDarkhastFaktor", "");
            }

            long diff = Math.abs(mablaghTakhfifTitr - sumMablaghTakhfifSatr);
            if (diff > 0 && diff <= intMaxDiff)
            {
                Log.d("takhfif" , "in diff , mablaghTakhfifTitr : " + mablaghTakhfifTitr + " , sumMablaghTakhfifSatr : " + sumMablaghTakhfifSatr);
                darkhastFaktorTakhfifDAO.updateMablaghTakhfif(ccDarkhastFaktor , ccTakhfif , mablaghTakhfifTitr,sumMablaghTakhfifSatr);
            }

        }



        ///////////////////////// Takhfif Naghdi /////////////////////////
        private void mohasebeTakhfifNaghdi(DarkhastFaktorModel darkhastFaktorModel)
        {
            try
            {
                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
                MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());

                TakhfifNaghdyDAO takhfifNaghdyDAO = new TakhfifNaghdyDAO(mPresenter.getAppContext());
                ArrayList<TakhfifNaghdyModel> takhfifNaghdyModels = takhfifNaghdyDAO.getByMoshtary(moshtaryModel, moshtaryModel.getCcNoeMoshtary(), darkhastFaktorModel.getDarajeh());
                Log.d("takhfifNaghdi" , "takhfifNaghdyModels.size = " + takhfifNaghdyModels.size());
                Log.d("takhfifNaghdi" , "moshtaryModel = " + moshtaryModel.toString());
                if (takhfifNaghdyModels.size()== 0)
                {
                    return;
                }

                double sumMablaghFaktor = new DarkhastFaktorSatrDAO(mPresenter.getAppContext()).getSumMablaghFaktorByccDarkhast(darkhastFaktorModel.getCcDarkhastFaktor());
                double sumTakhfifat = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext()).getSumMablaghByccDarkhastFaktorAndNoeTakhfif(darkhastFaktorModel.getCcDarkhastFaktor(), "1,2,5");
                double mablaghKhalesFaktor = sumMablaghFaktor - sumTakhfifat;
                double mablaghTakhfif = 0;
                double darsadTakhfif = 0;
                float minTakhfifNaghdyKhorde = 0;
                float minTakhfifNaghdyGheirKhorde = 0;
                int codeGorohKhorde = -1;

                String ccChildParameters = Constants.CC_CHILD_MIN_TAKHFIF_NAGHDI_KHORDE + "," + Constants.CC_CHILD_MIN_TAKHFIF_NAGHDI_GHEIRE_KHORDE + "," + Constants.CC_CHILD_GOROH_MOSHTARY_KHORDE();
                ArrayList<ParameterChildModel> parameterChildModels = new ParameterChildDAO(mPresenter.getAppContext()).getAllByccChildParameter(ccChildParameters);
                for (ParameterChildModel parameterChild : parameterChildModels)
                {
                    if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_MIN_TAKHFIF_NAGHDI_KHORDE)
                    {
                        minTakhfifNaghdyKhorde = Float.parseFloat(parameterChild.getValue());
                    }
                    else if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_MIN_TAKHFIF_NAGHDI_GHEIRE_KHORDE)
                    {
                        minTakhfifNaghdyGheirKhorde = Float.parseFloat(parameterChild.getValue());
                    }
                    else if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_GOROH_MOSHTARY_KHORDE())
                    {
                        codeGorohKhorde = Integer.parseInt(parameterChild.getValue());
                    }
                }

                Log.d("takhfifNaghdi" , "sumTakhfifat : " + sumTakhfifat);
                Log.d("takhfifNaghdi" , "mablaghKolDarkhast : " + darkhastFaktorModel.getMablaghKolDarkhast());
                Log.d("takhfifNaghdi" , "mablaghKolFaktor : " + darkhastFaktorModel.getMablaghKolFaktor());

                /*ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(Constants.CC_PARAMETER_GOROH_MOSHTARY());*/

                for (TakhfifNaghdyModel takhfifNaghdy : takhfifNaghdyModels)
                {
                    Log.d("takhfifNaghdi" , "TakhfifNaghdyModel : " + takhfifNaghdy.toString());
                    Log.d("takhfifNaghdi" , "darkhastFaktorModel.getModatRoozRaasGiri() : " + darkhastFaktorModel.getModatRoozRaasGiri());
                    if (takhfifNaghdy.getCodeNoeMohasebeh() == TakhfifNaghdyModel.CODE_NOE_MOHASEBEH_ADAD)
                    {
                        darsadTakhfif = takhfifNaghdy.getDarsadTakhfif();
                    }
                    else if (takhfifNaghdy.getCodeNoeMohasebeh() == TakhfifNaghdyModel.CODE_NOE_MOHASEBEH_FORMUL)
                    {
                        darsadTakhfif = darkhastFaktorModel.getModatRoozRaasGiri() * takhfifNaghdy.getDarsadTakhfif();
                    }

                    Log.d("takhfifNaghdi" , "darsadTakhfif : " + darsadTakhfif + " , minTakhfifNaghdyKhorde : " + minTakhfifNaghdyKhorde + " , minTakhfifNaghdyGheirKhorde : " + minTakhfifNaghdyGheirKhorde);
                    Log.d("takhfifNaghdi" , "moshtaryModel.getCcNoeMoshtary() : " + moshtaryModel.getCcNoeMoshtary() + " , codeGorohKhorde : " + codeGorohKhorde );
                    if (moshtaryModel.getCcNoeMoshtary() == codeGorohKhorde)
                    {
                        darsadTakhfif = Math.min(darsadTakhfif , minTakhfifNaghdyKhorde);
                    }
                    else
                    {
                        darsadTakhfif = Math.min(darsadTakhfif , minTakhfifNaghdyGheirKhorde);
                    }
                    Log.d("takhfifNaghdi" , "darsadTakhfif nahaee : " + darsadTakhfif);

                    //---------------------------------------------------------------------------------------------------------------------
                    // Be Rigly Takhfif Naghdi Taalogh Nemigirad..
                /*DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrModels = darkhastFaktorSatrDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
                int SumTedadBrand=0;
                int SumTedadKolKala=0;
                for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrModels)
                {
                    SumTedadKolKala += darkhastFaktorSatr.getTedad3();
                    if (darkhastFaktorSatr.getKala().getccBrand() == Brand.Orbit.getValue())
                        SumTedadBrand += darkhastFaktorSatr.getTedad3();
                }

                if(SumTedadKolKala == SumTedadBrand)
                {
                    for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrModels)
                    {
                        mablaghKhalesFaktor -= darkhastFaktorSatr.getTedad3()* darkhastFaktorSatr.getMablaghForosh();
                    }
                }*/
                    //---------------------------------------------------------------------------------------------------------------------

																											
																																						 

                    /*for (ParameterChildModel model : childParameterModels)
                    {
                        if (moshtaryGorohModel.getCcGoroh() == Integer.parseInt(model.getValue()))
                        {
                            //Khordeh & Darookhaneh..
                            if (model.getCcParameterChild() == Constants.CC_CHILD_GOROH_MOSHTARY_KHORDE())
                            {
                                mablaghTakhfif= mablaghKhalesFaktor * Math.min(darsadTakhfif, 1.5) / 100;
                                darsadTakhfif = Math.min(darsadTakhfif, 1.5);
                            }
                            //Omdeh..
                            else if (moshtaryGorohModel.getCcGoroh() == Constants.CC_CHILD_GOROH_MOSHTARY_OMDE())
                            {
                                mablaghTakhfif= mablaghKhalesFaktor* Math.min(darsadTakhfif, 2) / 100;
                            }
                            //TaavoniVijeh & Nemaiandeh & Bonakdar..
                            else if (moshtaryGorohModel.getCcGoroh() == Constants.CC_CHILD_GOROH_MOSHTARY_TAAVONI_VIJE() ||
                                    moshtaryGorohModel.getCcGoroh() == Constants.CC_CHILD_GOROH_MOSHTARY_NAMAYANDE1() ||
                                    moshtaryGorohModel.getCcGoroh() == Constants.CC_CHILD_GOROH_MOSHTARY_NAMAYANDE2())
                            {
                                mablaghTakhfif= mablaghKhalesFaktor * Math.min(darsadTakhfif, 2) / 100;
                                darsadTakhfif = Math.min(darsadTakhfif, 2);
                            }
                            break;
                        }
                    }*/

                    mablaghTakhfif= mablaghKhalesFaktor * darsadTakhfif / 100;
                    Log.d("takhfifNaghdi" , "darsadTakhfif Takhfif : " + darsadTakhfif);
                    Log.d("takhfifNaghdi" , "mablagh Takhfif : " + mablaghTakhfif);
                    if (mablaghTakhfif > 0)
                    {
                        insertFaktorTakhfifNaghdi(darkhastFaktorModel.getCcDarkhastFaktor() , takhfifNaghdy.getCcTakhfifNaghdy(), takhfifNaghdy.getSharhTakhfif(), darsadTakhfif, mablaghTakhfif);
                    }
                }
            }
            catch (Exception exception)
            {
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "takhfifNaghdi", "");
                //mPresenter.onErrorCalculateDiscount(R.string.errorOperation);
                //onCalculateDiscountResponse.onFailedCalculate(R.string.errorCalculateDiscount);
                //onProgressUpdate(-1);
                resultOfProccess = -1;
                Log.d("VerifyRequestModel","mohasebeTakhfifNaghdi");

                mPresenter.onErrorCalculateDiscount(R.string.errorCalculateDiscount);
            }
        }

        private void insertFaktorTakhfifNaghdi(long ccDarkhastFaktor, int ccTakhfifNaghdi, String sharhTakhfif, double darsadTakhfif, double mablaghTakhfif)
        {
            try
            {
                ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                String codeTakhfifNaghdi = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_NAGHDI());

                DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                DarkhastFaktorTakhfifModel model = new DarkhastFaktorTakhfifModel();
                model.setCcDarkhastFaktor(ccDarkhastFaktor);
                model.setCcTakhfif(ccTakhfifNaghdi);
                model.setSharhTakhfif(sharhTakhfif);
                model.setCodeNoeTakhfif(Integer.parseInt(codeTakhfifNaghdi));
                model.setDarsadTakhfif((float) darsadTakhfif);
                model.setMablaghTakhfif(Math.round(mablaghTakhfif));
                model.setExtraProp_ForJayezeh(0);
                model.setExtraProp_IsTakhfifMazad(0);

                Log.d("takhfif" , "takhfif model before insert : " + model.toString());

                darkhastFaktorTakhfifDAO.insert(model);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "VerifyRequestModel", "", "insertFaktorTakhfif", "");
            }
        }



        ///////////////////////// Jayezeh /////////////////////////
        private void calculateJayezehs(DarkhastFaktorModel darkhastFaktor, ArrayList<ParameterChildModel> parameterChildModels)
        {
			DiscountCalculation discountCalculation = new DiscountCalculation(parameterChildModels);																						
            try
            {
                ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
                JayezehDAO jayezehDAO = new JayezehDAO(mPresenter.getAppContext());
                JayezehSatrDAO jayezehSatrDAO= new JayezehSatrDAO(mPresenter.getAppContext());
                DarkhastFaktorSatrDAO darkhastFaktorSatrDAO= new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                MoshtaryDAO moshtaryDAO= new MoshtaryDAO(mPresenter.getAppContext());
                //ForoshandehDAO foroshandehDAO= new ForoshandehDAO(context);
                KalaDAO kalaDAO= new KalaDAO(mPresenter.getAppContext());

                ArrayList<DarkhastFaktorSatrModel> darkhastFaktorSatrs = darkhastFaktorSatrDAO.getByDarkhastFaktorForMohasebehJayezehTedadi(darkhastFaktor.getCcDarkhastFaktor());

                //Foroshandeh foroshandeh= foroshandehDAO.GetByccForoshandeh(darkhastFaktor.getccForoshandeh());
                MoshtaryModel moshtary= moshtaryDAO.getByccMoshtary(darkhastFaktor.getCcMoshtary());
                Log.d("jayezeh" , "CodeNoeHaml : " + darkhastFaktor.getCodeNoeHaml());
                ArrayList<JayezehModel> jayezehs = jayezehDAO.getByMoshtary(moshtary, darkhastFaktor.getCodeNoeHaml());

                Log.d("jayezeh" , "jayezehs.size : " + jayezehs.size());
                if (jayezehs.size() == 0)
                {
                    return;
                }

                int noeRialJayezehAdady = -1;
                int noeRialJayezehDarsadi = -1;
                ArrayList<ParameterChildModel> parameterChildModelsCodeNoeJayezeh = parameterChildDAO.getAllByccParameter(String.valueOf(Constants.CC_NOE_RIAL_JAYEZEH));
                for (ParameterChildModel parameterChild : parameterChildModelsCodeNoeJayezeh)
                {
                    if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_NOE_RIAL_JAYEZEH_ADADY)
                    {
                        try
                        {
                            noeRialJayezehAdady = Integer.parseInt(parameterChild.getValue());
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "VerifyRequestModel", "", "setJayezehs", "Integer.parseInt(parameterChild.getValue())");
                        }
                    }
                    else if (parameterChild.getCcParameterChild() == Constants.CC_CHILD_NOE_RIAL_JAYEZEH_DARSADI)
                    {
                        try
                        {
                            noeRialJayezehDarsadi = Integer.parseInt(parameterChild.getValue());
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "VerifyRequestModel", "", "setJayezehs", "Integer.parseInt(parameterChild.getValue())");
                        }
                    }
                }

                int tedadJayezeh= 0;
                int tedad, tedadBasteh, tedadKarton;
                double mablaghKala;
                double sumMablaghTakhfifSatr= 0;

                //---------------------------------- Kala.. --------------------------------------------------
                DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
                for (DarkhastFaktorSatrModel darkhastFaktorSatr : darkhastFaktorSatrs)
                {
                    Log.d("jayezeh" , "darkhastFaktorSatr : " + darkhastFaktorSatr.toString());
                    KalaModel kalaentity = kalaDAO.getByccKalaCode(darkhastFaktorSatr.getCcKalaCode());

                    sumMablaghTakhfifSatr = darkhastFaktorSatrTakhfifDAO.getSumMablaghTakhfifByccDarkhastFaktor(darkhastFaktor.getCcDarkhastFaktor());

                    for (JayezehModel jayezeh : jayezehs)
                    {
                        Log.d("jayezeh" , "jayezeh : " + jayezeh.toString());
                        Log.d("jayezeh" , "kalaentity : " + kalaentity.toString());
                        tedad= darkhastFaktorSatr.getTedad3();
                        tedadBasteh= tedad/ kalaentity.getTedadDarBasteh();
                        tedadKarton= tedad/ kalaentity.getTedadDarKarton();
                        mablaghKala= (tedad* darkhastFaktorSatr.getMablaghForosh()) - sumMablaghTakhfifSatr;

                        Log.d("jayezeh" , "tedad : " + tedad);
                        Log.d("jayezeh" , "tedadBasteh : " + tedadBasteh);
                        Log.d("jayezeh" , "tedadKarton : " + tedadKarton);
                        Log.d("jayezeh" , "mablaghKala : " + mablaghKala);																												 

                        ArrayList<JayezehSatrModel> jayezehSatrs = jayezehSatrDAO.getForFaktor(jayezeh.getCcJayezeh(), DiscountCalculation.NAME_NOE_FIELD_KALA,
								new int[]{discountCalculation.getTedadRialTedad(), discountCalculation.getTedadRialRial() , discountCalculation.getTedadRialAghlam()},
                                new int[]{discountCalculation.getBasteBandiCarton(), discountCalculation.getBasteBandiBaste(), discountCalculation.getBasteBandiAdad()},
								String.valueOf(darkhastFaktorSatr.getCcKalaCode()), tedad, tedadBasteh, tedadKarton, mablaghKala, jayezeh.getNoeTedadRial(),0);
                        Log.d("jayezeh" , "jayezehSatrs.size : " + jayezehSatrs.size());
                        for (JayezehSatrModel jayezehSatr : jayezehSatrs)
                        {
                            Log.d("jayezeh" , "jayezehSatr : " + jayezehSatr.toString());
                            int zarib = 0;
                            if (jayezehSatr.getBeEza()== 0)
                                zarib= 1;
                            else if (jayezeh.getNoeTedadRial() == discountCalculation.getTedadRialTedad() )
                            {
                                if (jayezehSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiAdad())
                                {
                                    zarib= (int)(tedad/ jayezehSatr.getBeEza());
                                    if (jayezeh.getCodeNoeMohasebeh()== 2)
                                    {
                                        tedad -= (int)(zarib* jayezehSatr.getBeEza());
                                    }
                                }
                                else if (jayezehSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiBaste())
                                {
                                    zarib= (int)(tedadBasteh/ jayezehSatr.getBeEza());
                                    if (jayezeh.getCodeNoeMohasebeh()== 2)
                                    {
                                        tedadBasteh -= (int)(zarib / jayezehSatr.getBeEza());
                                    }
                                }
                                else if (jayezehSatr.getCodeNoeBastehBandyBeEza() == discountCalculation.getBasteBandiCarton())
                                {
                                    zarib = (int)(tedadKarton/ jayezehSatr.getBeEza());
                                    if (jayezeh.getCodeNoeMohasebeh()== 2)
                                    {
                                        tedadKarton -= (int)(zarib * jayezehSatr.getBeEza());
                                    }
                                }
                            }
                            else if (jayezeh.getNoeTedadRial()== discountCalculation.getTedadRialRial())
                            {
                                zarib= (int)((tedad* darkhastFaktorSatr.getMablaghForosh())/ jayezehSatr.getBeEza());
                            }

                            tedadJayezeh= 0;
                            Log.d("jayezeh" , "zarib : " + zarib);
                            if (zarib != 0)
                            {
                                Log.d("jayezeh" , "tedadJayezeh : " + jayezehSatr.getTedadJayezeh());
                                Log.d("jayezeh" , "getCodeNoeBastehBandy : " + jayezehSatr.getCodeNoeBastehBandy());
                                Log.d("jayezeh" , "getCodeNoeBastehBandy : " + jayezehSatr.getCodeNoeBastehBandy());
                                Log.d("jayezeh" , "getCodeNoeBastehBandy : " + jayezehSatr.getCodeNoeBastehBandy());
                                Log.d("jayezeh" , "getTedadDarKarton : " + kalaentity.getTedadDarKarton());
                                Log.d("jayezeh" , "getTedadDarBasteh : " + kalaentity.getTedadDarBasteh());
                                if (jayezehSatr.getTedadJayezeh()!= 0)
                                {
                                    if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiCarton())
                                    {
                                        tedadJayezeh= zarib* jayezehSatr.getTedadJayezeh() ;//* kalaentity.getTedadDarKarton();
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiBaste())
                                    {
                                        tedadJayezeh= zarib* jayezehSatr.getTedadJayezeh() ;//* kalaentity.getTedadDarBasteh();
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandy() ==discountCalculation.getBasteBandiAdad())
                                    {
                                        tedadJayezeh= zarib* jayezehSatr.getTedadJayezeh();
                                    }
                                    Log.d("takhfif" , "tedadJayezeh : " + tedadJayezeh);
                                }
                                else
                                {
                                    //Find MablaghForosh..
                                    double mablghForoshKala= 0;
                                    if (jayezehSatr.getCcKalaCodeJayezeh()== darkhastFaktorSatr.getCcKalaCode())
                                    {
                                        mablghForoshKala= darkhastFaktorSatr.getMablaghForosh();
                                    }
                                    else
                                    {
                                        for (KalaModel kala : kalaDAO.getKalasByccKalaCode(jayezehSatr.getCcKalaCodeJayezeh()))
                                        {
                                            if (kala.getCcKalaCode() == jayezehSatr.getCcKalaCodeJayezeh())
                                            {
                                                mablghForoshKala= kala.getLastMablaghForosh();
                                                break;
                                            }
                                        }
                                    }

                                    if (jayezehSatr.getNoeRialJayezeh() == noeRialJayezehAdady)
                                    {
                                        tedadJayezeh= (int)(zarib * jayezehSatr.getRialJayezeh()/ mablghForoshKala);
                                    }
                                    Log.d("jayezeh" , "tedadJayezeh 2 : " + tedadJayezeh);
                                }
                            }
                            Log.d("jayezeh" , "tedadJayezeh final : " + tedadJayezeh);
                            if (tedadJayezeh!= 0)
                            {
                                checkForInsertJayezeh(darkhastFaktor.getCcDarkhastFaktor(), jayezeh.getCcJayezeh(), jayezehSatr.getCcJayezehSatr(), jayezeh.getSharhJayezeh(), jayezehSatr.getCcKalaCodeJayezeh(), jayezehSatr.getCcKalaCodeJayezeh(), tedadJayezeh, jayezeh.getIsJayezehEntekhabi());
                            }

                        }
                    }
                }

                //---------------------------------------- GorohKala.. -----------------------------------------
                double sumTedadGorohKala= 0;
                double sumTedadBastehGorohKala= 0;
                double sumTedadKartonGorohKala= 0;
                double sumMablaghKolGorohKala= 0;
                int tedadAghlam =0 ;

                for (JayezehModel jayezeh: jayezehs)
                {
                    sumTedadGorohKala = 0;
                    sumTedadBastehGorohKala = 0;
                    sumTedadKartonGorohKala = 0;
                    sumMablaghKolGorohKala = 0;
                    tedadAghlam = 0;

                    ArrayList<DataTableModel> gorohKalas = darkhastFaktorSatrDAO.getTedadBeTafkikGorohKalaAndJayezeh(darkhastFaktor.getCcDarkhastFaktor(), jayezeh.getCcJayezeh());

                    for (DataTableModel gorohKala : gorohKalas)
                    {
                        sumTedadGorohKala= Double.parseDouble(gorohKala.getFiled2());
                        sumTedadBastehGorohKala = Double.parseDouble(gorohKala.getFiled3());
                        sumTedadKartonGorohKala = Double.parseDouble(gorohKala.getFiled4());
                        sumMablaghKolGorohKala = Double.parseDouble(gorohKala.getFiled5());
                        tedadAghlam  = Integer.parseInt(gorohKala.getFiled6());

                        //Satrhaye Jayezeh..

                        Log.d("jayezeh" , "sumTedadGorohKala : " + sumTedadGorohKala);
                        Log.d("jayezeh" , "sumTedadBastehGorohKala : " + sumTedadBastehGorohKala);
                        Log.d("jayezeh" , "sumTedadKartonGorohKala : " + sumTedadKartonGorohKala);
                        Log.d("jayezeh" , "sumMablaghKolGorohKala : " + sumMablaghKolGorohKala);
                        Log.d("jayezeh" , "tedadAghlam : " + tedadAghlam);

                        ArrayList<JayezehSatrModel> jayezehSatrs = jayezehSatrDAO.getForFaktor(jayezeh.getCcJayezeh(), 
								DiscountCalculation.NAME_NOE_FIELD_GOROH_KALA, new int[]{discountCalculation.getTedadRialTedad(), discountCalculation.getTedadRialRial(),discountCalculation.getTedadRialAghlam()},
								new int[]{discountCalculation.getBasteBandiCarton(), discountCalculation.getBasteBandiBaste(), discountCalculation.getBasteBandiAdad()},
								gorohKala.getFiled1(), sumTedadGorohKala, sumTedadKartonGorohKala, sumTedadBastehGorohKala, sumMablaghKolGorohKala, jayezeh.getNoeTedadRial(),tedadAghlam);

                        Log.d("jayezeh" , "jayezehSatrs.size for goroh Kala : " + jayezehSatrs.size());
                        for (JayezehSatrModel jayezehSatr: jayezehSatrs)
                        {
                            Log.d("jayezeh" , "goroh kala jayezehSatr : " + jayezehSatr.toString());
                            int zarib= 0;
                            if (jayezehSatr.getBeEza() == 0)
                            {
                                zarib= 1;
                            }
                            else
                            {
                                //Tedad..
                                if (jayezeh.getNoeTedadRial()== discountCalculation.getTedadRialTedad())
                                {
                                    if (jayezehSatr.getCodeNoeBastehBandyBeEza()==  discountCalculation.getBasteBandiAdad())
                                    {
                                        zarib = (int)(sumTedadGorohKala/ jayezehSatr.getBeEza());
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandyBeEza()== discountCalculation.getBasteBandiBaste())
                                    {
                                        zarib= (int)(sumTedadBastehGorohKala/ jayezehSatr.getBeEza());
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandyBeEza()== discountCalculation.getBasteBandiCarton())
                                    {
                                        zarib = (int)(sumTedadKartonGorohKala/ jayezehSatr.getBeEza());
                                    }
                                }//if
                                //Rial..
                                else if (jayezeh.getNoeTedadRial()== discountCalculation.getTedadRialRial())
                                {
                                    zarib = (int)(sumMablaghKolGorohKala/ jayezehSatr.getBeEza());
                                }
                            }
                            Log.d("jayezeh" , "goroh kala zarib : " + zarib);
                            tedadJayezeh= 0;
                            if (zarib != 0)
                            {
                                if (jayezehSatr.getTedadJayezeh() != 0)
                                {
                                    Log.d("jayezeh" , "ccKalaCode of jayezeh Satr : " + jayezehSatr.getCcKalaCode());
                                    Log.d("jayezeh" , "getCcNoeField of jayezeh Satr : " + jayezehSatr.getCcNoeField());
                                    KalaModel kalaModel = kalaDAO.getccKalaByKalaGoroh(jayezehSatr.getCcNoeField());
                                    Log.d("jayezeh" , "kalaModel : " + kalaModel.toString());
                                    if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiCarton())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh() ;//* kalaModel.getTedadDarKarton();
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiBaste())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh() ;//* kalaModel.getTedadDarBasteh();
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiAdad())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh();
                                    }
                                    else
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh();
                                    }
                                }
                                else
                                {
                                    //Find MablaghForosh..
                                    double mablghForoshKala= 0;
                                    for (KalaModel kala: kalaDAO.getKalasByccKalaCode(jayezehSatr.getCcKalaCodeJayezeh()))
                                    {
                                        if (kala.getCcKalaCode()== jayezehSatr.getCcKalaCodeJayezeh())
                                        {
                                            mablghForoshKala= kala.getLastMablaghForosh();
                                            break;
                                        }
                                    }
                                    if (jayezehSatr.getNoeRialJayezeh() == noeRialJayezehAdady)
                                    {
                                        tedadJayezeh= (int)(zarib * jayezehSatr.getRialJayezeh() / mablghForoshKala);
                                    }
                                }
                            }

                            Log.d("jayezeh" , "goroh kala tedad Jayezeh : " + tedadJayezeh);
                            if (tedadJayezeh != 0)
                            {
                                checkForInsertJayezeh(darkhastFaktor.getCcDarkhastFaktor(), jayezeh.getCcJayezeh(), jayezehSatr.getCcJayezehSatr(), jayezeh.getSharhJayezeh(), jayezehSatr.getCcKalaJayezeh(), jayezehSatr.getCcKalaCodeJayezeh(), tedadJayezeh, jayezeh.getIsJayezehEntekhabi());
                            }
                        }
                    }
                }

                //---------------------------------------- Brand.. -----------------------------------------
                double sumTedadBrand= 0;
                double sumTedadBastehBrand= 0;
                double sumTedadKartonBrand= 0;
                double sumMablaghKolBrand= 0;
                int tedadAghlamBrand =0 ;

                for (JayezehModel jayezeh: jayezehs)
                {
                    sumTedadBrand = 0;
                    sumTedadBastehBrand = 0;
                    sumTedadKartonBrand = 0;
                    sumMablaghKolBrand = 0;
                    tedadAghlam = 0;

                    ArrayList<DataTableModel> brands = darkhastFaktorSatrDAO.getTedadBeTafkikBrandAndJayezeh(darkhastFaktor.getCcDarkhastFaktor(), jayezeh.getCcJayezeh());

                    for (DataTableModel brand : brands)
                    {
                        sumTedadBrand= Double.parseDouble(brand.getFiled2());
                        sumTedadBastehBrand = Double.parseDouble(brand.getFiled3());
                        sumTedadKartonBrand = Double.parseDouble(brand.getFiled4());
                        sumMablaghKolBrand = Double.parseDouble(brand.getFiled5());
                        tedadAghlamBrand  = Integer.parseInt(brand.getFiled6());

                        //Satrhaye Jayezeh..

                        Log.d("jayezeh" , "sumTedadbrand : " + sumTedadBrand);
                        Log.d("jayezeh" , "sumTedadBastehbrand : " + sumTedadBastehBrand);
                        Log.d("jayezeh" , "sumTedadKartonbrand : " + sumTedadKartonBrand);
                        Log.d("jayezeh" , "sumMablaghKolbrand : " + sumMablaghKolBrand);
                        Log.d("jayezeh" , "tedadAghlam : " + tedadAghlam);

                        ArrayList<JayezehSatrModel> jayezehSatrs = jayezehSatrDAO.getForFaktor(jayezeh.getCcJayezeh(),
                                DiscountCalculation.NAME_NOE_FIELD_BRAND, new int[]{discountCalculation.getTedadRialTedad(), discountCalculation.getTedadRialRial(),discountCalculation.getTedadRialAghlam()},
                                new int[]{discountCalculation.getBasteBandiCarton(), discountCalculation.getBasteBandiBaste(), discountCalculation.getBasteBandiAdad()},
                                brand.getFiled1(), sumTedadBrand, sumTedadKartonBrand, sumTedadBastehBrand, sumMablaghKolBrand, jayezeh.getNoeTedadRial(),tedadAghlam);

                        Log.d("jayezeh" , "jayezehSatrs.size for Brand : " + jayezehSatrs.size());
                        for (JayezehSatrModel jayezehSatr: jayezehSatrs)
                        {
                            Log.d("jayezeh" , "Brand jayezehSatr : " + jayezehSatr.toString());
                            int zarib= 0;
                            if (jayezehSatr.getBeEza() == 0)
                            {
                                zarib= 1;
                            }
                            else
                            {
                                //Tedad..
                                if (jayezeh.getNoeTedadRial()== discountCalculation.getTedadRialTedad())
                                {
                                    if (jayezehSatr.getCodeNoeBastehBandyBeEza()==  discountCalculation.getBasteBandiAdad())
                                    {
                                        zarib = (int)(sumTedadBrand/ jayezehSatr.getBeEza());
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandyBeEza()== discountCalculation.getBasteBandiBaste())
                                    {
                                        zarib= (int)(sumTedadBastehBrand/ jayezehSatr.getBeEza());
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandyBeEza()== discountCalculation.getBasteBandiCarton())
                                    {
                                        zarib = (int)(sumTedadKartonBrand/ jayezehSatr.getBeEza());
                                    }
                                }//if
                                //Rial..
                                else if (jayezeh.getNoeTedadRial()== discountCalculation.getTedadRialRial())
                                {
                                    zarib = (int)(sumMablaghKolBrand/ jayezehSatr.getBeEza());
                                }
                            }
                            Log.d("jayezeh" , "Brand zarib : " + zarib);
                            tedadJayezeh= 0;
                            if (zarib != 0)
                            {
                                if (jayezehSatr.getTedadJayezeh() != 0)
                                {
                                    Log.d("jayezeh" , "ccKalaCode of jayezeh Satr : " + jayezehSatr.getCcKalaCode());
                                    Log.d("jayezeh" , "getCcNoeField of jayezeh Satr : " + jayezehSatr.getCcNoeField());
                                    KalaModel kalaModel = kalaDAO.getccKalaByKalaGoroh(jayezehSatr.getCcNoeField());
                                    Log.d("jayezeh" , "kalaModel : " + kalaModel.toString());
                                    if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiCarton())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh() ;//* kalaModel.getTedadDarKarton();
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiBaste())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh() ;//* kalaModel.getTedadDarBasteh();
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiAdad())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh();
                                    }
                                    else
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh();
                                    }
                                }
                                else
                                {
                                    //Find MablaghForosh..
                                    double mablghForoshKala= 0;
                                    for (KalaModel kala: kalaDAO.getKalasByccKalaCode(jayezehSatr.getCcKalaCodeJayezeh()))
                                    {
                                        if (kala.getCcKalaCode()== jayezehSatr.getCcKalaCodeJayezeh())
                                        {
                                            mablghForoshKala= kala.getLastMablaghForosh();
                                            break;
                                        }
                                    }
                                    if (jayezehSatr.getNoeRialJayezeh() == noeRialJayezehAdady)
                                    {
                                        tedadJayezeh= (int)(zarib * jayezehSatr.getRialJayezeh() / mablghForoshKala);
                                    }
                                }
                            }

                            Log.d("jayezeh" , "brand tedad Jayezeh : " + tedadJayezeh);
                            if (tedadJayezeh != 0)
                            {
                                checkForInsertJayezeh(darkhastFaktor.getCcDarkhastFaktor(), jayezeh.getCcJayezeh(), jayezehSatr.getCcJayezehSatr(), jayezeh.getSharhJayezeh(), jayezehSatr.getCcKalaJayezeh(), jayezehSatr.getCcKalaCodeJayezeh(), tedadJayezeh, jayezeh.getIsJayezehEntekhabi());
                            }
                        }
                    }
                }

                //---------------------------------------- TaminKonandeh.. -----------------------------------------
                double sumTedadTaminKonandeh= 0;
                double sumTedadBastehTaminKonandeh= 0;
                double sumTedadKartonTaminKonandeh= 0;
                double sumMablaghKolTaminKonandeh= 0;
                int tedadAghlamTaminKonandeh =0 ;

                for (JayezehModel jayezeh: jayezehs)
                {
                    sumTedadTaminKonandeh = 0;
                    sumTedadBastehTaminKonandeh = 0;
                    sumTedadKartonTaminKonandeh = 0;
                    sumMablaghKolTaminKonandeh = 0;
                    tedadAghlam = 0;

                    ArrayList<DataTableModel> taminKonandehs = darkhastFaktorSatrDAO.getTedadBeTafkikTaminKonandehAndJayezeh(darkhastFaktor.getCcDarkhastFaktor(), jayezeh.getCcJayezeh());

                    for (DataTableModel taminKonandeh : taminKonandehs)
                    {
                        sumTedadTaminKonandeh= Double.parseDouble(taminKonandeh.getFiled2());
                        sumTedadBastehTaminKonandeh = Double.parseDouble(taminKonandeh.getFiled3());
                        sumTedadKartonTaminKonandeh = Double.parseDouble(taminKonandeh.getFiled4());
                        sumMablaghKolTaminKonandeh = Double.parseDouble(taminKonandeh.getFiled5());
                        tedadAghlamTaminKonandeh  = Integer.parseInt(taminKonandeh.getFiled6());

                        //Satrhaye Jayezeh..

                        Log.d("jayezeh" , "sumTedadTaminKonandeh : " + sumTedadTaminKonandeh);
                        Log.d("jayezeh" , "sumTedadBastehTaminKonandeh : " + sumTedadBastehTaminKonandeh);
                        Log.d("jayezeh" , "sumTedadKartonTaminKonandeh : " + sumTedadKartonTaminKonandeh);
                        Log.d("jayezeh" , "sumMablaghKolTaminKonandeh : " + sumMablaghKolTaminKonandeh);
                        Log.d("jayezeh" , "tedadAghlamTaminKonandeh : " + tedadAghlam);

                        ArrayList<JayezehSatrModel> jayezehSatrs = jayezehSatrDAO.getForFaktor(jayezeh.getCcJayezeh(),
                                DiscountCalculation.NAME_NOE_FIELD_TAMIN_KONANDE, new int[]{discountCalculation.getTedadRialTedad(), discountCalculation.getTedadRialRial(),discountCalculation.getTedadRialAghlam()},
                                new int[]{discountCalculation.getBasteBandiCarton(), discountCalculation.getBasteBandiBaste(), discountCalculation.getBasteBandiAdad()},
                                taminKonandeh.getFiled1(), sumTedadTaminKonandeh, sumTedadKartonTaminKonandeh, sumTedadBastehTaminKonandeh, sumMablaghKolTaminKonandeh, jayezeh.getNoeTedadRial(),tedadAghlam);

                        Log.d("jayezeh" , "jayezehSatrs.size for TaminKonandeh : " + jayezehSatrs.size());
                        for (JayezehSatrModel jayezehSatr: jayezehSatrs)
                        {
                            Log.d("jayezeh" , "TaminKonandeh jayezehSatr : " + jayezehSatr.toString());
                            int zarib= 0;
                            if (jayezehSatr.getBeEza() == 0)
                            {
                                zarib= 1;
                            }
                            else
                            {
                                //Tedad..
                                if (jayezeh.getNoeTedadRial()== discountCalculation.getTedadRialTedad())
                                {
                                    if (jayezehSatr.getCodeNoeBastehBandyBeEza()==  discountCalculation.getBasteBandiAdad())
                                    {
                                        zarib = (int)(sumTedadTaminKonandeh/ jayezehSatr.getBeEza());
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandyBeEza()== discountCalculation.getBasteBandiBaste())
                                    {
                                        zarib= (int)(sumTedadBastehTaminKonandeh/ jayezehSatr.getBeEza());
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandyBeEza()== discountCalculation.getBasteBandiCarton())
                                    {
                                        zarib = (int)(sumTedadKartonTaminKonandeh/ jayezehSatr.getBeEza());
                                    }
                                }//if
                                //Rial..
                                else if (jayezeh.getNoeTedadRial()== discountCalculation.getTedadRialRial())
                                {
                                    zarib = (int)(sumMablaghKolTaminKonandeh/ jayezehSatr.getBeEza());
                                }
                            }
                            Log.d("jayezeh" , "TaminKonandeh zarib : " + zarib);
                            tedadJayezeh= 0;
                            if (zarib != 0)
                            {
                                if (jayezehSatr.getTedadJayezeh() != 0)
                                {
                                    Log.d("jayezeh" , "ccKalaCode of jayezeh Satr : " + jayezehSatr.getCcKalaCode());
                                    Log.d("jayezeh" , "getCcNoeField of jayezeh Satr : " + jayezehSatr.getCcNoeField());
                                    KalaModel kalaModel = kalaDAO.getccKalaByKalaGoroh(jayezehSatr.getCcNoeField());
                                    Log.d("jayezeh" , "kalaModel : " + kalaModel.toString());
                                    if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiCarton())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh() ;//* kalaModel.getTedadDarKarton();
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiBaste())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh() ;//* kalaModel.getTedadDarBasteh();
                                    }
                                    else if (jayezehSatr.getCodeNoeBastehBandy() == discountCalculation.getBasteBandiAdad())
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh();
                                    }
                                    else
                                    {
                                        tedadJayezeh= zarib * jayezehSatr.getTedadJayezeh();
                                    }
                                }
                                else
                                {
                                    //Find MablaghForosh..
                                    double mablghForoshKala= 0;
                                    for (KalaModel kala: kalaDAO.getKalasByccKalaCode(jayezehSatr.getCcKalaCodeJayezeh()))
                                    {
                                        if (kala.getCcKalaCode()== jayezehSatr.getCcKalaCodeJayezeh())
                                        {
                                            mablghForoshKala= kala.getLastMablaghForosh();
                                            break;
                                        }
                                    }
                                    if (jayezehSatr.getNoeRialJayezeh() == noeRialJayezehAdady)
                                    {
                                        tedadJayezeh= (int)(zarib * jayezehSatr.getRialJayezeh() / mablghForoshKala);
                                    }
                                }
                            }

                            Log.d("jayezeh" , "TaminKonandeh tedad Jayezeh : " + tedadJayezeh);
                            if (tedadJayezeh != 0)
                            {
                                checkForInsertJayezeh(darkhastFaktor.getCcDarkhastFaktor(), jayezeh.getCcJayezeh(), jayezehSatr.getCcJayezehSatr(), jayezeh.getSharhJayezeh(), jayezehSatr.getCcKalaJayezeh(), jayezehSatr.getCcKalaCodeJayezeh(), tedadJayezeh, jayezeh.getIsJayezehEntekhabi());
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "VerifyRequestModel", "", "setJayezehs", "");
            }
        }


        private void checkForInsertJayezeh(long ccDarkhastFaktor, int ccJayezeh, int ccJayezehSatr, String sharhJayezeh, int ccKalaJayezeh, int ccKalaCodeJayezeh, int tedadJayezeh, int isJayezehEntekhabi)
        {
            Log.d("jayezeh" , "isKalaEntekhabi : " + isJayezehEntekhabi);
            Log.d("jayezeh" , "ccKalaJayezeh : " + ccKalaJayezeh);
            Log.d("jayezeh" , "ccJayezeh : " + ccJayezeh);
            Log.d("jayezeh" , "ccKalaCodeJayezeh : " + ccKalaCodeJayezeh);
            Log.d("jayezeh" , "tedadJayezeh : " + tedadJayezeh);
            Log.d("jayezeh" , "isJayezehEntekhabi : " + isJayezehEntekhabi);
            haveBonus = true;
            if (isJayezehEntekhabi == 0)
            {
                // todo auto
                KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
                int countKalaMojodi = kalaMojodiDAO.getCountByccKalaCode(ccKalaCodeJayezeh);
                int countMaxMojody = kalaMojodiDAO.getMaxMojodyByccKalaCode(ccKalaCodeJayezeh);

                Log.d("jayezeh" , "countKalaMojodi : " + countKalaMojodi +  " countMaxMojody:"+ countMaxMojody  + " tedadJayezeh: " + tedadJayezeh);
                if (countKalaMojodi <= 0)
                {
                    //insert takhfif naghdi
                    insertTakhfifNaghdi(ccJayezeh, sharhJayezeh, ccKalaJayezeh, tedadJayezeh, tedadJayezeh);
                }
                else if (countKalaMojodi >= tedadJayezeh && countMaxMojody >= tedadJayezeh)
                {
                    Log.d("jayezeh" , "ccDarkhastFaktor : " + ccDarkhastFaktor);
																		
                    //codeNoeJayezeh for auto == 1 else 2
                    if (insertJayezeh(ccDarkhastFaktor, ccJayezeh, ccJayezehSatr, DarkhastFaktorJayezehModel.CodeNoeJayezehAuto(), sharhJayezeh, ccKalaJayezeh, ccKalaCodeJayezeh, tedadJayezeh))
                    {
                        Log.d("jayezeh" , "ccKalaJayezeh : " + ccKalaJayezeh + " tedadJayezeh :" + tedadJayezeh);
                        if (!checkKalaForInsertKalaMojodi(ccKalaJayezeh , tedadJayezeh))
                        {
                            publishProgress(-2);
                        }
                    }
                }
                else //if (countMojodiForKala > 0 && countMojodiForKala < tedadJayezeh)
                {
                    //diff == count takhfif naghdi
                    int diff = tedadJayezeh - countMaxMojody;
                    if (checkKalaForInsertKalaMojodi(ccKalaJayezeh , countKalaMojodi))
                    {
                        insertTakhfifNaghdi(ccJayezeh, sharhJayezeh, ccKalaJayezeh, tedadJayezeh, diff);
                    }
                }
            }
            else
            {
                canSelectBonus = true;
                insertJayezeh(ccDarkhastFaktor, ccJayezeh, ccJayezehSatr, DarkhastFaktorJayezehModel.CodeNoeJayezehEntekhabi(), sharhJayezeh, ccKalaJayezeh, ccKalaCodeJayezeh, tedadJayezeh);
            }
        }


        private boolean checkKalaForInsertKalaMojodi(int ccKalaJayezeh , int tedadJayezeh)
        {
            KalaMojodiDAO kalaMojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
            ArrayList<KalaMojodiModel> kalaMojodiModels = kalaMojodiDAO.getForAutoBonus(ccKalaJayezeh);
            int insertedKala = 0;
            Log.d("Jayezeh" , "kalaMojodiModels.size : " + kalaMojodiModels.size() + " , ccKalaJayezeh : " +ccKalaJayezeh);
            for (KalaMojodiModel kala : kalaMojodiModels)
            {
                if (insertedKala < tedadJayezeh)
                {
                    int countNew = (kala.getTedad() >= tedadJayezeh) ? tedadJayezeh : kala.getTedad();
                    Log.d("Jayezeh" , "count new : " + countNew + " , kala.getTedad() : " + kala.getTedad() + " , tedadJayezeh : " + tedadJayezeh + " , FinalccAfrad : " + kala.getCcAfrad());
                    Log.d("Jayezeh" , "kala model " + kala.toString());
                    if (insertKalaMojodi(kalaMojodiDAO, kala.getCcTaminKonandeh(), countNew, kala.getShomarehBach(), kala.getTarikhTolid(), kala.getGheymatMasrafKonandeh(), kala.getGheymatForosh(), kala.getCcKalaCode(), kala.getCcForoshandeh(), kala.getCcAfrad(), kala.getTarikhEngheza()))
                    {
                        insertedKala += countNew;
                    }
                }
            }
            Log.d("Jayezeh" , "insertedKala : " + insertedKala + " , tedadJayezeh : " + tedadJayezeh);
            return insertedKala == tedadJayezeh;
        }

        private boolean insertKalaMojodi(KalaMojodiDAO kalaMojodiDAO, int ccTaminKonandeh, int count, String shomarehBach, String tarikhTolid, float gheymatMasrafKonandeh, float gheymatForosh, int ccKalaCode, int ccForoshandeh, int ccAfrad,  String tarikhEngheza)
        {
            String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
            KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();
            kalaMojodiModel.setCcTaminKonandeh(ccTaminKonandeh);
            kalaMojodiModel.setTedad(-1 * count);
            kalaMojodiModel.setTarikhDarkhast(currentDate);
            kalaMojodiModel.setShomarehBach(shomarehBach);
            kalaMojodiModel.setGheymatMasrafKonandeh(gheymatMasrafKonandeh);
            kalaMojodiModel.setGheymatForosh(gheymatForosh);
            kalaMojodiModel.setCcKalaCode(ccKalaCode);
            kalaMojodiModel.setCcForoshandeh(selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh() , ccForoshandeh));
            kalaMojodiModel.setCcDarkhastFaktor(ccDarkhastFaktor);
            kalaMojodiModel.setForJayezeh(1);
            kalaMojodiModel.setZamaneSabt(currentDate);
            kalaMojodiModel.setMax_Mojody(-1 * count);
            kalaMojodiModel.setMax_MojodyByShomarehBach(-1 * count);
            kalaMojodiModel.setCcAfrad(ccAfrad);
            kalaMojodiModel.setTarikhTolid(tarikhTolid);
            kalaMojodiModel.setTarikhEngheza(tarikhEngheza);
            if (kalaMojodiDAO.insert(kalaMojodiModel))
            {
                return insertDarkhastFaktorSatr(ccTaminKonandeh, ccKalaCode, count, shomarehBach, tarikhTolid, tarikhEngheza, gheymatForosh, gheymatMasrafKonandeh);
            }
            else
            {
                return false;
            }
        }

        private boolean insertDarkhastFaktorSatr(int ccTaminKonandeh, int ccKalaCode, int count, String shomarehBach, String tarikhTolid, String tarikhEngheza, float gheymatForosh, float gheymatMasrafKonandeh)
        {
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
            DarkhastFaktorSatrModel darkhastFaktorSatrModel = new DarkhastFaktorSatrModel();
            darkhastFaktorSatrModel.setCcDarkhastFaktor(ccDarkhastFaktor);
            darkhastFaktorSatrModel.setCcTaminKonandeh(ccTaminKonandeh);
            darkhastFaktorSatrModel.setCcKala(ccKalaCode);
            darkhastFaktorSatrModel.setCcKalaCode(ccKalaCode);
            darkhastFaktorSatrModel.setTedad3(count);
            darkhastFaktorSatrModel.setCodeNoeKala(2);
            darkhastFaktorSatrModel.setShomarehBach(shomarehBach);
            darkhastFaktorSatrModel.setTarikhTolid(tarikhTolid);
            darkhastFaktorSatrModel.setTarikhEngheza(tarikhEngheza);
            darkhastFaktorSatrModel.setMablaghForosh(1);
            darkhastFaktorSatrModel.setMablaghForoshKhalesKala(gheymatForosh);
            darkhastFaktorSatrModel.setMablaghTakhfifNaghdiVahed(0);
            darkhastFaktorSatrModel.setMaliat(0);
            darkhastFaktorSatrModel.setAvarez(0);
            darkhastFaktorSatrModel.setCcAfrad(0);
            darkhastFaktorSatrModel.setExtraProp_IsOld(false);
            darkhastFaktorSatrModel.setGheymatMasrafKonandeh(gheymatMasrafKonandeh);
            darkhastFaktorSatrModel.setGheymatForoshAsli(gheymatForosh);
            darkhastFaktorSatrModel.setGheymatMasrafKonandehAsli(gheymatMasrafKonandeh);
            return darkhastFaktorSatrDAO.insert(darkhastFaktorSatrModel);
        }


        private void insertTakhfifNaghdi(int ccJayezeh , String sharhJayezeh , int ccKalaCode , int countAllBonus , int countDiffBonus)
        {
            int ccGorohNoeMoshatry = selectFaktorShared.getInt(selectFaktorShared.getCcGorohNoeMoshtary() , -1);
            String codeNoeTakhfif = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_NAGHDI());
            DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());

            KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
            KalaModel kalaModel = kalaDAO.getByccKalaCode(ccKalaCode);
            if (kalaModel.getCcKalaCode() > 0)
            {
                int mablaghMandeh = Math.round(kalaModel.getMablaghForosh() * countDiffBonus);
                float sumMablaghJayezeh = kalaModel.getMablaghForosh() * countAllBonus;

                DarkhastFaktorTakhfifModel darkhastFaktorTakhfifModel = new DarkhastFaktorTakhfifModel();
                darkhastFaktorTakhfifModel.setCcTakhfif(new TakhfifNaghdyDAO(mPresenter.getAppContext()).getccTakhfifNaghdiByccGorohMoshtary(ccGorohNoeMoshatry));
                darkhastFaktorTakhfifModel.setCcDarkhastFaktor(ccDarkhastFaktor);
                darkhastFaktorTakhfifModel.setMablaghTakhfif(mablaghMandeh);
                darkhastFaktorTakhfifModel.setDarsadTakhfif((mablaghMandeh*100)/sumMablaghJayezeh);
                darkhastFaktorTakhfifModel.setCodeNoeTakhfif(Integer.parseInt(codeNoeTakhfif));
                darkhastFaktorTakhfifModel.setSharhTakhfif(sharhJayezeh);
                darkhastFaktorTakhfifModel.setExtraProp_ForJayezeh(0);
                darkhastFaktorTakhfifModel.setExtraProp_IsTakhfifMazad(1);
                darkhastFaktorTakhfifModel.setExtraProp_MustSendToSql(1);
                darkhastFaktorTakhfifModel.setExtraProp_ccJayezehTakhfif(ccJayezeh);
                darkhastFaktorTakhfifDAO.insert(darkhastFaktorTakhfifModel);
            }
            else
            {
                publishProgress(-3);
            }
        }


        @Override
        protected void onProgressUpdate(Integer... values)
        {
            if (values[0] == -1)
            {
                onCalculateDiscountResponse.onFailedCalculate(R.string.errorCalculateDiscount);
            }
            else if (values[0] == -2)
            {
                onCalculateDiscountResponse.onFailedCalculate(R.string.errorNegativeDiscountCost);
            }
        }

        @Override
        protected void onPostExecute(Integer integer)
        {
            if (integer == 1)
            {
                onCalculateDiscountResponse.onSuccessCalculate(haveBonus , ccDarkhastFaktor );
            }
        }
    }

    private class AsyncTaskRequestDetail extends AsyncTask<ArrayList<KalaDarkhastFaktorSatrModel> , Void , Integer>
    {
        double MablaghKolDarkhast = 0;
        long sumMablaghKol = 0;
        double sumMablagh = 0;
        double sumMablaghBaArzeshAfzoodeh = 0;
        //double sumMablaghTakhfif = 0;
        int sumTedadAghlam = 0;
        int tedadAghlam = 0;
        double sumHajm = 0;
        double sumVazn = 0;
        double vaznFaktor = 0; // after convert to Kg
        double hajmFaktor = 0; // after convert to m3
        double Takhfif_Satr = 0;
        double Mablagh_ArzeshAfzoodeh_Satr = 0;
        double JamDarsadTakhfifNaghdi = 0;
        double MablaghTakhfifNaghdi = 0;
        double maliat = 0;
        double avarez = 0;
        double sumMablaghKhales = 0;
        int sumTedadAghlamPishnehadiBiSetareh = 0;
        //int modatVosolDarkhast = 0;
        //int modatRoozRaasGiri = 0;

        @Override
        protected Integer doInBackground(ArrayList<KalaDarkhastFaktorSatrModel>... arrayLists)
        {
            ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorModels = arrayLists[0];
            //super.onPostExecute(kalaDarkhastFaktorModels);
            try
            {
                for (KalaDarkhastFaktorSatrModel model : kalaDarkhastFaktorModels)
                {
                    sumMablaghKol = sumMablaghKol + (long)(model.getMablaghForosh() * model.getTedad3());
                }

                ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                int codeTakhfifNaghdi = Integer.parseInt(childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CODE_TAKHFIF_NAGHDI()));

                SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
                long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
                DarkhastFaktorDAO darkhastfaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                darkhastfaktorDAO.updateMablaghKol(ccDarkhastFaktor , sumMablaghKol);
                MablaghKolDarkhast = sumMablaghKol;

                double sumMablaghJayezeh = new DarkhastFaktorSatrDAO(mPresenter.getAppContext()).getSumMablaghJayezehByccDarkhast(ccDarkhastFaktor);
                sumMablaghKol += sumMablaghJayezeh;

                DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels = darkhastFaktorTakhfifDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
                //SystemConfigDAO systemConfigDAO = new SystemConfigDAO(mPresenter.getAppContext());
                //ArrayList<SystemConfigModel> systemConfigs = systemConfigDAO.getAll();
                String ccChildParameters = Constants.CC_CHILD_MALIAT + "," + Constants.CC_CHILD_AVAREZ;
                ArrayList<ParameterChildModel> parameterChildModels = childParameterDAO.getAllByccChildParameter(ccChildParameters);
                float darsadMaliat = 0;
                float darsadAvarez = 0;
                for (ParameterChildModel parameterChildModel : parameterChildModels)
                {
                    if (parameterChildModel.getCcParameterChild() == Constants.CC_CHILD_MALIAT)
                    {
                        try
                        {
                            darsadMaliat = Float.parseFloat(parameterChildModel.getValue());
                        }
                        catch (Exception e){e.printStackTrace();}
                    }
                    else if (parameterChildModel.getCcParameterChild() == Constants.CC_CHILD_AVAREZ)
                    {
                        try
                        {
                            darsadAvarez = Float.parseFloat(parameterChildModel.getValue());
                        }
                        catch (Exception e){e.printStackTrace();}
                    }
                }

                DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
                //for (DarkhastFaktorSatrModel model : darkhastFaktorSatrDAO.getByccDarkhastFaktorAndNotNoeKala(ccDarkhastFaktor , 2))
                for (DarkhastFaktorSatrModel model : darkhastFaktorSatrDAO.getByccDarkhastFaktorAndNotNoeKala(ccDarkhastFaktor , -1))
                {
                    Log.d("verify" , "darkhastFaktorSatr : " + model.toString());
                    if (model.getTedad3() != 0)
                    {

                        /*if (model.getCodeNoeKala() == 2)
                        {
                            sumMablaghKol += model.getMablaghForosh() * model.getTedad3();
                        }*/

                        Takhfif_Satr = 0;
                        Mablagh_ArzeshAfzoodeh_Satr = 0;

                        KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
                        KalaModel kala = kalaDAO.getByccKalaCode(model.getCcKalaCode());

                        DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
                        ArrayList<DarkhastFaktorSatrTakhfifModel> darkhastFaktorSatrTakhfifs = darkhastFaktorSatrTakhfifDAO.getByccDarkhastFaktorSatr(model.getCcDarkhastFaktorSatr());
                        ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifs = darkhastFaktorTakhfifDAO.getByccDarkhastFaktorAndNoeTakhfif(ccDarkhastFaktor , codeTakhfifNaghdi);

                        //sumMablagh = model.getTedad3() * model.getMablaghForosh();
                        sumHajm += (model.getTedad3() * (kala.getTol() * kala.getArz() * kala.getErtefa())) / kala.getTedadDarKarton();
                        sumVazn += (kala.getVaznKarton() / kala.getTedadDarKarton()) * model.getTedad3();
                        sumTedadAghlam += model.getTedad3();
                        tedadAghlam += 1;

                        //--------- Mohasebeh ArzeshAfzoodeh ----------------------------
                        for (DarkhastFaktorSatrTakhfifModel entity_Takhfif : darkhastFaktorSatrTakhfifs)
                        {
                            Takhfif_Satr += entity_Takhfif.getMablaghTakhfif();
                        }
                        Log.d("mablagh" , "Takhfif_Satr : " + Takhfif_Satr);
                        Mablagh_ArzeshAfzoodeh_Satr = ((model.getTedad3() * model.getMablaghForosh()) - Takhfif_Satr);
                        Log.d("mablagh" , "Mablagh_ArzeshAfzoodeh_Satr : " + Mablagh_ArzeshAfzoodeh_Satr);
                        Log.d("mablagh" , "model.getTedad3() * model.getMablaghForosh() : " + (model.getTedad3() * model.getMablaghForosh()) + " , tedad3 : " + model.getTedad3() + ", mablaghforosh : " + model.getMablaghForosh());
                        JamDarsadTakhfifNaghdi = 0;
                        MablaghTakhfifNaghdi = 0;
                        for (DarkhastFaktorTakhfifModel darkhastFaktorTakhfif : darkhastFaktorTakhfifs)
                        {
                            JamDarsadTakhfifNaghdi = JamDarsadTakhfifNaghdi + darkhastFaktorTakhfif.getDarsadTakhfif();
                            MablaghTakhfifNaghdi = MablaghTakhfifNaghdi + darkhastFaktorTakhfif.getMablaghTakhfif();
                        }

                        //Mablagh_ArzeshAfzoodeh_Satr = Mablagh_ArzeshAfzoodeh_Satr - ((model.getMablaghForosh() * MablaghTakhfifNaghdi) / MablaghKolDarkhast) * model.getTedad3();
                        Mablagh_ArzeshAfzoodeh_Satr = Mablagh_ArzeshAfzoodeh_Satr - ((model.getMablaghForosh() * MablaghTakhfifNaghdi) / sumMablaghKol) * model.getTedad3();
                        Log.d("mablagh" , "2 Mablagh_ArzeshAfzoodeh_Satr : " + Mablagh_ArzeshAfzoodeh_Satr + "," + model.getMablaghForosh() + " , " + JamDarsadTakhfifNaghdi + " , " + MablaghTakhfifNaghdi + " , " + sumMablaghKol + " , " + model.getTedad3());
                        maliat = 0;
                        avarez = 0;
                        if (kala.getMashmolMaliatAvarez() == 1 && model.getCodeNoeKala() != 2)
                        {
                            //maliat = Mablagh_ArzeshAfzoodeh_Satr * systemConfigs.get(0).getMaliat() / 100;
                            //avarez = Mablagh_ArzeshAfzoodeh_Satr * systemConfigs.get(0).getAvarez() / 100;
                            maliat = (Mablagh_ArzeshAfzoodeh_Satr / model.getTedad3()) * darsadMaliat / 100;
                            avarez = (Mablagh_ArzeshAfzoodeh_Satr / model.getTedad3()) * darsadAvarez / 100;
                            Mablagh_ArzeshAfzoodeh_Satr += ((maliat + avarez) * model.getTedad3());
                            Log.d("mablagh" , "3 Mablagh_ArzeshAfzoodeh_Satr : " + Mablagh_ArzeshAfzoodeh_Satr  + " ,maliat : " + maliat + " , Avarez : " + avarez + " , KolMaliat : " + (maliat + avarez) * model.getTedad3());
                            //Mablagh_ArzeshAfzoodeh_Satr += Mablagh_ArzeshAfzoodeh_Satr * (systemConfigs.get(0).getMaliat() + systemConfigs.get(0).getAvarez()) / 100;
                        }
                        // اگر جایزه نبود مبلغ فروش خالص کالا باید آپدیت شود
                        if (model.getMablaghForosh() != 1)
                        {
                            darkhastFaktorSatrDAO.updateMablaghForoshKhalesKalaMaliatAvarez(model.getCcDarkhastFaktorSatr(), (Mablagh_ArzeshAfzoodeh_Satr / model.getTedad3()), maliat, avarez);
                        }
                        //sumMablaghBaArzeshAfzoodeh += (maliat + avarez);
                        sumMablaghBaArzeshAfzoodeh += ((maliat + avarez) * model.getTedad3());

                        Log.d("mablagh" , "4 Mablagh_ArzeshAfzoodeh_Satr : " + Mablagh_ArzeshAfzoodeh_Satr + " , " + maliat + " , " + avarez);
                        sumMablaghKhales += Mablagh_ArzeshAfzoodeh_Satr;
                        /*Log.d("mablagh" , "4 Mablagh_ArzeshAfzoodeh_Satr : " + Mablagh_ArzeshAfzoodeh_Satr);
                        Log.d("mablagh" , "1 sumMablaghBaArzeshAfzoodeh : " + sumMablaghBaArzeshAfzoodeh);*/

                    }
                }

                vaznFaktor = (sumVazn / 1000);
                hajmFaktor = (sumHajm / 1000000);

                Log.d("mablagh" , sumMablaghBaArzeshAfzoodeh + " , " + sumMablaghKhales + " , " + sumMablaghKol);


            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "", "getRequestDetail", "");
                return -1;
            }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer num)
        {
            if (num == 1)
            {
                //mPresenter.onGetRequestDetail(sumTedadAghlam, tedadAghlam, sumVazn, sumHajm, vaznFaktor, hajmFaktor, sumMablaghKol, sumMablaghBaArzeshAfzoodeh, sumMablaghKhales, sumTedadAghlamPishnehadiBiSetareh);
                SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
                long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
                DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                if (!VerifyRequestActivity.SuccessGetBonus && sumMablaghBaArzeshAfzoodeh>0)
                {
                    if (darkhastFaktorTakhfifDAO.deleteTakhfifByCodeNoe(ccDarkhastFaktor, DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh())) {
                        AsyncTaskMohasebehJayezehArzeshAfzoodeh asyncTaskMohasebehJayezehArzeshAfzoodeh = new AsyncTaskMohasebehJayezehArzeshAfzoodeh(sumMablaghKol, sumMablaghBaArzeshAfzoodeh, sumTedadAghlam, tedadAghlam, sumHajm, sumVazn, vaznFaktor, hajmFaktor, sumMablaghKhales, sumTedadAghlamPishnehadiBiSetareh);
                        asyncTaskMohasebehJayezehArzeshAfzoodeh.execute();
                    }
                    else
                        mPresenter.onErrorOperations(R.string.errorOperation);

                }else{

                    if (darkhastFaktorTakhfifDAO.deleteTakhfifByCodeNoe(ccDarkhastFaktor,DarkhastFaktorJayezehTakhfifModel.NoeArzeshAfzoodeh()))
                        mPresenter.onGetRequestDetail(sumTedadAghlam, tedadAghlam, sumVazn, sumHajm, vaznFaktor, hajmFaktor, sumMablaghKol, sumMablaghBaArzeshAfzoodeh, sumMablaghKhales, sumTedadAghlamPishnehadiBiSetareh,checkCanSelectBonus());
                    else
                        mPresenter.onErrorOperations(R.string.errorOperation);
                }
            }
            else if (num == -1)
            {
                mPresenter.onErrorOperations(R.string.errorOperation);
            }
        }

    }

    class AsyncTaskCalculateModatVosol extends AsyncTask<Void, Void, Integer>
    {
        boolean isSelectedVosolVajhNagh = false;
        boolean isSelectedVosolResidNaghd = false;

        public AsyncTaskCalculateModatVosol(boolean isSelectedVosolVajhNagh,boolean isSelectedResidNaghd)
        {
            this.isSelectedVosolVajhNagh = isSelectedVosolVajhNagh;
            this.isSelectedVosolResidNaghd = isSelectedResidNaghd;
        }

        @Override
        protected Integer doInBackground(Void... voids)
        {
            int modatRoozRaasGiri = 0;
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
            MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());

            long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
            int ccMoshtary = selectFaktorShared.getInt(selectFaktorShared.getCcMoshtary() , -1);
            Log.d("verifyRequest" , "ccMoshtary : " + ccMoshtary);
            MoshtaryEtebarSazmanForoshModel moshtaryEtebarSazmanForoshModel = moshtaryEtebarSazmanForoshDAO.getByccMoshtary(ccMoshtary);
            Log.d("modatVosol" , "moshtaryEtebarSazmanForoshModel.getModatVosol() : " + moshtaryEtebarSazmanForoshModel.getModatVosol());
            if(moshtaryEtebarSazmanForoshModel.getModatVosol()==0)
            {
                double SumMablaghKolDarkhast = 0;
                double SumMablaghWithModatvosol = 0;
                double SumMablaghBrandWithModatvosol = 0;
                double SumMablaghGorohKalaWithModatvosol = 0;

                //--------------------moshtary------------------------------------
                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
                MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(ccMoshtary);
                Log.d("VerifyRequest" , "moshtary : " + moshtary.toString());
                //---------------------modatvosol------------------------
                Log.d("verifyRequest" , "moshtary.getDarajeh() : " + moshtary.getDarajeh());
                ModatVosolDAO modatVosolDAO = new ModatVosolDAO(mPresenter.getAppContext());
                Log.d("modatVosol" , "darajeh moshtary : " + moshtary.getDarajeh());
                ArrayList<ModatVosolModel> modatvosols = modatVosolDAO.getForMohasebehModatvosol(moshtary.getCcNoeMoshtary(), moshtary.getDarajeh());
                //----------------------DarkhastFaktorSatr----------------
                ArrayList<DarkhastFaktorSatrModel> darkhastfaktorsatrs = darkhastFaktorSatrDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
                for(DarkhastFaktorSatrModel darkhastfaktorsatr : darkhastfaktorsatrs)
                {
                    SumMablaghKolDarkhast += (darkhastfaktorsatr.getMablaghForosh() * darkhastfaktorsatr.getTedad3());
                }
                Log.d("modatVosol" , "SumMablaghKolDarkhast : " + SumMablaghKolDarkhast);


                for(ModatVosolModel modatvosol : modatvosols)
                {
                    Log.d("modatVosol" , "modatvosol : " + modatvosol.toString());
                    SumMablaghGorohKalaWithModatvosol = 0;
                    if(modatvosol.getCcBrand()==0 & modatvosol.getCcGorohKala()!=0)//Gorohkala
                    {
                        Log.d("modatVosol", "in goroh kala");
                        KalaGorohDAO kalagorohDAO = new KalaGorohDAO(mPresenter.getAppContext());
                        ArrayList<KalaGorohModel> kalagorohs = kalagorohDAO.getByccGoroh(modatvosol.getCcGorohKala());
                        int TedadGorohKala=0;
                        for(KalaGorohModel kalagoroh : kalagorohs)
                        {
                            for (DarkhastFaktorSatrModel darkhastfaktorsatr : darkhastfaktorsatrs)
                            {
                                Log.d("modatVosol", "kalagoroh.getCcKalaCode(): " + kalagoroh.getCcKalaCode());
                                Log.d("modatVosol", "darkhastfaktorsatr.getCcKalaCode(): " + darkhastfaktorsatr.getCcKalaCode());
                                if (kalagoroh.getCcKalaCode() == darkhastfaktorsatr.getCcKalaCode())
                                {
                                    TedadGorohKala += darkhastfaktorsatr.getTedad3();
                                }

                            }
                        }
                        Log.d("modatVosol", "TedadGorohKala: " + TedadGorohKala);
                        for(KalaGorohModel kalagoroh : kalagorohs)
                        {
                            for(DarkhastFaktorSatrModel darkhastfaktorsatr : darkhastfaktorsatrs)
                            {
                                if(kalagoroh.getCcKalaCode()==darkhastfaktorsatr.getCcKalaCode() && (TedadGorohKala>=modatvosol.getAz() && TedadGorohKala<= modatvosol.getTa()))
                                {
                                    SumMablaghGorohKalaWithModatvosol += (darkhastfaktorsatr.getMablaghForosh()*darkhastfaktorsatr.getTedad3()*modatvosol.getModatVosol());
                                    Log.d("modatVosol" , "SumMablaghGorohKalaWithModatvosol goroh kala " + SumMablaghGorohKalaWithModatvosol);
                                }
                            }
                        }
                    }
                    else//Brand
                    {
                        Log.d("modatVosol", "in brand");
                        KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
                        SumMablaghBrandWithModatvosol = 0;
                        int TedadBrand=0;
                        for(DarkhastFaktorSatrModel darkhastfaktorsatr : darkhastfaktorsatrs)
                        {
                            KalaModel kala = kalaDAO.getByccKalaCode(darkhastfaktorsatr.getCcKalaCode());
                            if(modatvosol.getCcBrand() == kala.getCcBrand())
                            {
                                TedadBrand += darkhastfaktorsatr.getTedad3();
                            }
                        }
                        Log.d("modatVosol", "TedadBrand: " + TedadBrand);
                        for(DarkhastFaktorSatrModel darkhastfaktorsatr : darkhastfaktorsatrs)
                        {
                            KalaModel kala = kalaDAO.getByccKalaCode(darkhastfaktorsatr.getCcKalaCode());
                            if(modatvosol.getCcBrand() == kala.getCcBrand() && (TedadBrand>=modatvosol.getAz() && TedadBrand<= modatvosol.getTa()))
                            {
                                SumMablaghBrandWithModatvosol += (darkhastfaktorsatr.getMablaghForosh() * darkhastfaktorsatr.getTedad3() * modatvosol.getModatVosol());
                                Log.d("modatVosol" , "SumMablaghBrandWithModatvosol brand " + SumMablaghBrandWithModatvosol);
                            }
                        }
                    }
                    Log.d("modatVosol" , "SumMablaghWithModatvosol before " + SumMablaghWithModatvosol + " , SumMablaghBrandWithModatvosol : " + SumMablaghBrandWithModatvosol + " , SumMablaghGorohKalaWithModatvosol:" + SumMablaghGorohKalaWithModatvosol);
                    SumMablaghWithModatvosol += SumMablaghBrandWithModatvosol + SumMablaghGorohKalaWithModatvosol;
                    Log.d("modatVosol" , "SumMablaghWithModatvosol end " + SumMablaghWithModatvosol + " , SumMablaghBrandWithModatvosol : " + SumMablaghBrandWithModatvosol + " , SumMablaghGorohKalaWithModatvosol:" + SumMablaghGorohKalaWithModatvosol);
                }

                Log.d("modatVosol" , "SumMablaghWithModatvosol " + SumMablaghWithModatvosol + " , SumMablaghKolDarkhast : " + SumMablaghKolDarkhast);
                Log.d("modatVosol" , "SumMablaghWithModatvosol/SumMablaghKolDarkhast " + SumMablaghWithModatvosol/SumMablaghKolDarkhast);
                modatRoozRaasGiri = (int)(SumMablaghWithModatvosol/SumMablaghKolDarkhast);
            }
            else
            {
                modatRoozRaasGiri = moshtaryEtebarSazmanForoshModel.getModatVosol();
            }
            Log.d("modatVosol" , "modatRoozRaasGiri : " + modatRoozRaasGiri);

            darkhastFaktorDAO.updateModatRoozRaasGiri(ccDarkhastFaktor, modatRoozRaasGiri);
            return modatRoozRaasGiri;
        }

        @Override
        protected void onPostExecute(Integer modatRoozRaasGiri)
        {
            mPresenter.onGetModatRoozRaasgiri(modatRoozRaasGiri, isSelectedVosolVajhNagh, isSelectedVosolResidNaghd);
        }
    }

    class AsyncTaskMohasebehJayezehArzeshAfzoodeh extends AsyncTask<Void, Void, Integer>
    {

        double MablaghKolDarkhast = 0;
        long sumMablaghKol = 0;
        double sumMablagh = 0;
        double sumMablaghBaArzeshAfzoodeh;
        int sumTedadAghlam = 0;
        int tedadAghlam = 0;
        double sumHajm = 0;
        double sumVazn = 0;
        double vaznFaktor = 0; // after convert to Kg
        double hajmFaktor = 0; // after convert to m3
        double sumMablaghKhales = 0;
        int sumTedadAghlamPishnehadiBiSetareh = 0;


        public AsyncTaskMohasebehJayezehArzeshAfzoodeh(long sumMablaghKol, double sumMablaghBaArzeshAfzoodeh, int sumTedadAghlam, int tedadAghlam, double sumHajm, double sumVazn, double vaznFaktor, double hajmFaktor, double sumMablaghKhales, int sumTedadAghlamPishnehadiBiSetareh) {
            this.sumMablaghKol = sumMablaghKol;
            this.sumMablaghBaArzeshAfzoodeh = sumMablaghBaArzeshAfzoodeh;
            this.sumTedadAghlam = sumTedadAghlam;
            this.tedadAghlam = tedadAghlam;
            this.sumHajm = sumHajm;
            this.sumVazn = sumVazn;
            this.vaznFaktor = vaznFaktor;
            this.hajmFaktor = hajmFaktor;
            this.sumMablaghKhales = sumMablaghKhales;
            this.sumTedadAghlamPishnehadiBiSetareh = sumTedadAghlamPishnehadiBiSetareh;
        }

        @Override
        protected Integer doInBackground(Void... voids)
        {
            try {


                SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
                long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(), -1);
                DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
                MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary());

                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
                MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(moshtary.getCcMoshtary());
                JayezehDAO jayezehDAO = new JayezehDAO(mPresenter.getAppContext());
                JayezehSatrDAO jayezehSatrDAO = new JayezehSatrDAO(mPresenter.getAppContext());
                JayezehModel jayezehModel = jayezehDAO.getArzeshAfzoodehJayezeh(moshtaryModel, darkhastFaktorModel.getCodeNoeHaml());
                if(jayezehModel != null)
                {
                int ccJayezehSatr = jayezehSatrDAO.getccJayezehSatrForArzeshAfzoodeh(jayezehModel.getCcJayezeh(), sumMablaghBaArzeshAfzoodeh);


                    DarkhastFaktorTakhfifModel model = new DarkhastFaktorTakhfifModel();
                    model.setCcDarkhastFaktor(ccDarkhastFaktor);
                    model.setCcTakhfif(jayezehModel.getCcJayezeh());
                    model.setExtraProp_ccJayezehTakhfif(ccJayezehSatr);
                    model.setSharhTakhfif(jayezehModel.getSharhJayezeh());
                    model.setCodeNoeTakhfif(jayezehModel.getCodeNoe());
                    model.setMablaghTakhfif((long) sumMablaghBaArzeshAfzoodeh);
                    model.setExtraProp_ForJayezeh(1);
                    model.setExtraProp_IsTakhfifMazad(0);
                    DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                    if (darkhastFaktorTakhfifDAO.insert(model))
                        return 1;
                    else
                        return 0;
                }
                else
                {
                    return 1;
                }
                }catch(Exception e){
                    return 0;
                }


        }

        @Override
        protected void onPostExecute(Integer result)
        {
               if (result == 1)
                   mPresenter.onGetRequestDetail(sumTedadAghlam, tedadAghlam, sumVazn, sumHajm, vaznFaktor, hajmFaktor, sumMablaghKol, sumMablaghBaArzeshAfzoodeh, sumMablaghKhales, sumTedadAghlamPishnehadiBiSetareh,checkCanSelectBonus());
               else
                   mPresenter.onErrorOperations(R.string.errorOperationJayezehArzeshAfzodeh);

        }
    }
     private boolean checkCanSelectBonus(){
        boolean canSelectBonus = false;

        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
         long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
         if (ccDarkhastFaktor != -1) {
             DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
             ArrayList<Integer> ccTakhfifs = darkhastFaktorTakhfifDAO.getccTakhfifOfJayezehByccDarkhastFaktor(ccDarkhastFaktor);
             String strccTakhfifs = "";
             Log.d("ccTakhfif", "size : " + ccTakhfifs.size() + ccTakhfifs.toString());
             if (ccTakhfifs.size() > 0) {
                 canSelectBonus = true;
                 for (int i : ccTakhfifs) {
                     Log.d("ccTakhfif", "ccTakhfif : " + i);
                     strccTakhfifs += i + ",";
                 }
                 Log.d("ccTakhfif", "strccTakhfifs for : " + strccTakhfifs);
                 if (strccTakhfifs.length() > 0 && strccTakhfifs.endsWith(",")) {
                     strccTakhfifs = strccTakhfifs.substring(0, strccTakhfifs.length() - 1);
                 }
                 Log.d("ccTakhfif", "strccTakhfifs if : " + strccTakhfifs);
             }
             Log.d("ccTakhfif", "strccTakhfifs : " + strccTakhfifs);
             selectFaktorShared.putString(selectFaktorShared.getCcTakhfifJayezes(), strccTakhfifs);
         }
         return canSelectBonus;
     }

}
