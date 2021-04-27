package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.InvoiceSettlementMVP;
import com.saphamrah.DAO.BankDAO;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.CodeNoeVosolDAO;
import com.saphamrah.DAO.ConfigNoeVosolMojazeFaktorDAO;
import com.saphamrah.DAO.ConfigNoeVosolMojazeMoshtaryDAO;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MarkazShomarehHesabDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryShomarehHesabDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.PosShomarehHesabDAO;
import com.saphamrah.DAO.TaghvimTatilDAO;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.CodeNoeVosolModel;
import com.saphamrah.Model.ConfigNoeVosolMojazeMoshtaryModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.PosShomarehHesabModel;
import com.saphamrah.Model.TaghvimTatilModel;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.GetProgramShared;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceSettlementModel implements InvoiceSettlementMVP.ModelOps {

    private InvoiceSettlementMVP.RequiredPresenterOps mPresenter;
    private int ccDarajehModel, ccNoeMoshtaryModel;
    private String shomarehSanadCheckBargashty;
    private DateUtils dateUtils = new DateUtils();
    private String strCodeNoeVosol = "";
    private boolean canGetTajil = false;
    private double MablaghTajil_Nahaee, MablaghPasAzKasrTajil;
    private GetProgramShared getProgramShared = new GetProgramShared(BaseApplication.getContext());
    private SimpleDateFormat simpleDateFormatShort = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
    private SimpleDateFormat simpleDateFormatLong = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
    private SimpleDateFormat simpleDateFormatDATE_TIME_FORMAT = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
    private SimpleDateFormat simpleDateFormatDATE_TIME_WITH_SPACE = new SimpleDateFormat(Constants.DATE_TIME_WITH_SPACE());
    private DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
    ParameterChildDAO parameterChildDAO = new ParameterChildDAO(BaseApplication.getContext());
    DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());

    public InvoiceSettlementModel(InvoiceSettlementMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getConfig() {
        Log.i("showSaayadCheckI", "ModelInConfig");
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String showSayyadCheckScanner = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_SHOW_SAYYAD_CHECK_SCANNER);
        mPresenter.onGetConfig(showSayyadCheckScanner);
        Log.i("showSaayadCheckI", "showSayyadCheckScanner in model : " + showSayyadCheckScanner);

    }

    /**
     * form mean activity name
     * FROM_PISH_DARYAFT = 2
     * FROM_CHECK_BARGASHTI = 3
     */
    @Override
    public void getInfo(long ccDarkhastFaktor, int from) {
        if (ccDarkhastFaktor == -1) {
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
            ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(), -1);
        }

        if (ccDarkhastFaktor != -1) {
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

            String mablaghKhales = String.valueOf(darkhastFaktorModel.getMablaghKhalesFaktor());
            String shomarehDarkhast = String.valueOf(darkhastFaktorModel.getShomarehDarkhast());
            int ccNoeVosol = darkhastFaktorModel.getCodeNoeVosolAzMoshtary();
            String nameNoeVosol = darkhastFaktorModel.getNameNoeVosolAzMoshtary() == null ? "" : darkhastFaktorModel.getNameNoeVosolAzMoshtary();

            long mablaghMandeh = setMablaghMandehFaktor(ccDarkhastFaktor);

            mPresenter.onGetInfo(shomarehDarkhast, ccNoeVosol, nameNoeVosol, mablaghKhales, String.valueOf(mablaghMandeh), from);
        } else {
            mPresenter.onError(R.string.errorFindccDarkhastFaktor);
        }
    }

    /**
     * get vosol from TreasuryList and CheckBargashti
     *
     * @param ccDarkhastFaktor
     */
    @Override
    public void getVosols(long ccDarkhastFaktor) {
        //SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        Log.d("vosols", "getCcDarkhastFaktor : " + ccDarkhastFaktor);
        Log.d("vosols", "count : " + dariaftPardakhtDarkhastFaktorPPCModels.size());
        mPresenter.onGetVosols(dariaftPardakhtDarkhastFaktorPPCModels);
    }

    /**
     * get vosol from pish daryaft
     *
     * @param ccMoshtary
     */
    @Override
    public void getVosolsPishDariaft(long ccMoshtary) {
        //SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        ArrayList<DariaftPardakhtPPCModel> dariaftPardakhtPPCModels = dariaftPardakhtPPCDAO.getByccMoshtaryPishdariaft(ccMoshtary); //ispishdariaft
        Log.d("vosols", "getCcDarkhastFaktor : ");
//        Log.d("vosols", "count : " + dariaftPardakhtDarkhastFaktorPPCModels.size());
        mPresenter.onGetVosolsPishDaryaft(dariaftPardakhtPPCModels);
    }


    /**
     * FROM_TREASURYLIST = 1
     * FROM_PISH_DARYAFT = 2
     * FROM_CHECK_BARGASHTI = 3
     * FROM_DEFULT = -1
     * when is pishdaryaft ccDarkhastFaktor = 0
     * when is faktor ccDarkhastFaktor = ccDarkhastFaktor
     * when is check bargashti ccDarkhastFaktor = shomareh sanad
     */
    @Override
    public void getNoeVosols(long ccDarkhastFaktor, boolean getNoeVosolFromShared, int from, int ccMoshtary) {
        //get type of settlement from shared and according to settlement type get from database

        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        CodeNoeVosolDAO codeNoeVosolDAO = new CodeNoeVosolDAO(BaseApplication.getContext());
        ConfigNoeVosolMojazeFaktorDAO configNoeVosolMojazeFaktorDAO = new ConfigNoeVosolMojazeFaktorDAO(BaseApplication.getContext());

        Log.d("invoice", "ccDarkhastFaktor : " + ccDarkhastFaktor);
        Log.d("invoice", "darkhastFaktorModel.getCodeNoeVosolAzMoshtary() : " + darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
        String parents = "";
        // FROM_PISH_DARYAFT
        if (from == Constants.FROM_PISH_DARYAFT) {

            Log.d("invoice", "ccMoshtary : " + ccMoshtary);
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
            MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

            ConfigNoeVosolMojazeMoshtaryDAO configNoeVosolMojazeMoshtaryDAO = new ConfigNoeVosolMojazeMoshtaryDAO(BaseApplication.getContext());
            ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels = configNoeVosolMojazeMoshtaryDAO.getCodeVosolPishDaryaft(moshtaryModel.getCcNoeMoshtary(), moshtaryModel.getDarajeh());
            mPresenter.onGetNoeVosolsMojazMoshtary(configNoeVosolMojazeMoshtaryModels);


//            if (moshtaryModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID()) {
////                parents = childParameterDAO.getValueByccChildParameter(Constants.CC_VOSOL_RESID());
//                parents = configNoeVosolMojazeFaktorDAO.getValueByNoeVosol(Constants.CODE_NOE_VOSOL_MOSHTARY_RESID());
//                if (from == Constants.FROM_PISH_DARYAFT) {
//                    // حذف نوع وصول رسید برای فاکتورهای موجود در لیست وصول
//                    parents = parents.replace(String.valueOf(Constants.CC_CHILD_VOSOL_RESID()), "");
//                    parents = parents.replace(",,", ",");
//                    parents = parents.startsWith(",") ? parents.substring(1) : parents;
//                    parents = parents.endsWith(",") ? parents.substring(0, parents.length() - 1) : parents;
//                }
//            } else if (moshtaryModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK()) {
////                parents = childParameterDAO.getValueByccChildParameter(Constants.CC_VOSOL_CHECK());
//                parents = configNoeVosolMojazeFaktorDAO.getValueByNoeVosol(Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK());
//            } else if (moshtaryModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD()) {
////                parents = childParameterDAO.getValueByccChildParameter(Constants.CC_VOSOL_NAGHD());
//                parents = configNoeVosolMojazeFaktorDAO.getValueByNoeVosol(Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD());
//            }

//            ArrayList<CodeNoeVosolModel> codeNoeVosolModels = codeNoeVosolDAO.getAllByParentsId(parents);
//
//            mPresenter.onGetNoeVosols(codeNoeVosolModels);

        }
        // FROM_CHECK_BARGASHTI
        else if (from == Constants.FROM_CHECK_BARGASHTI) {
            Log.d("invoice", "ccMoshtary : " + ccMoshtary);
            ConfigNoeVosolMojazeMoshtaryDAO configNoeVosolMojazeMoshtaryDAO = new ConfigNoeVosolMojazeMoshtaryDAO(BaseApplication.getContext());
            ArrayList<ConfigNoeVosolMojazeMoshtaryModel> configNoeVosolMojazeMoshtaryModels = configNoeVosolMojazeMoshtaryDAO.getCodeVosolCheckBargashti(ccNoeMoshtaryModel, ccDarajehModel);
            mPresenter.onGetNoeVosolsMojazMoshtary(configNoeVosolMojazeMoshtaryModels);

        } else if (from == Constants.FROM_TREASURYLIST) {

//            int codeNoeVosolResid = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_RESID()));
//            int codeNoeVosolResidNaghd = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_Resid_Naghd()));

                parents = configNoeVosolMojazeFaktorDAO.getValueByNoeVosol(darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
//                if (from == Constants.FROM_TREASURYLIST && (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == codeNoeVosolResid
//                      || darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == codeNoeVosolResidNaghd)) {
//                    // حذف نوع وصول رسید برای فاکتورهای موجود در لیست وصول
//                    parents = parents.replace(String.valueOf(Constants.CC_CHILD_VOSOL_RESID()), "");
//                    parents = parents.replace(String.valueOf(Constants.CC_CHILD_VOSOL_RESID()), "");
//                    parents = parents.replace(",,", ",");
//                    parents = parents.startsWith(",") ? parents.substring(1) : parents;
//                    parents = parents.endsWith(",") ? parents.substring(0, parents.length() - 1) : parents;
//                }

            ArrayList<CodeNoeVosolModel> codeNoeVosolModels = codeNoeVosolDAO.getAllByParentsId(parents);
            mPresenter.onGetNoeVosols(codeNoeVosolModels);
        } else {

            parents = configNoeVosolMojazeFaktorDAO.getValueByNoeVosol(darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
            ArrayList<CodeNoeVosolModel> codeNoeVosolModels = codeNoeVosolDAO.getAllByParentsId(parents);
            mPresenter.onGetNoeVosols(codeNoeVosolModels);
        }
    }

    @Override
    public void getPosShomareHesab(int codeNoevosol) {
        PosShomarehHesabDAO posShomarehHesabDAO = new PosShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<PosShomarehHesabModel> posShomarehHesabModels = posShomarehHesabDAO.getAll();
        mPresenter.onGetPosShomareHesab(posShomarehHesabModels, codeNoevosol);
    }

    @Override
    public void getFishBanki(int codeNoevosol) {
        MarkazShomarehHesabDAO markazShomarehHesabDAO = new MarkazShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels = markazShomarehHesabDAO.getAll();
        mPresenter.onGetFishBanki(markazShomarehHesabModels, codeNoevosol);
    }

    @Override
    public void getMoshtaryHesab(int ccMoshtary, long ccDarkhastFaktor, int codeNoevosol, double mablaghMande) {
        MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = moshtaryShomarehHesabDAO.getAllByccMoshtary(ccMoshtary);

        BankDAO bankDAO = new BankDAO(mPresenter.getAppContext());
        ArrayList<BankModel> bankModels = bankDAO.getAll();

        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        String[] tarikhSarResidForCheck;
        if (ccDarkhastFaktor > 0) {
            tarikhSarResidForCheck = getTarikhSarResidForCheck(darkhastFaktorModel, codeNoevosol, mablaghMande);
            mPresenter.onGetMoshtaryHesab(moshtaryShomarehHesabModels, bankModels, codeNoevosol, tarikhSarResidForCheck[0], tarikhSarResidForCheck[1]);
        } else {
            tarikhSarResidForCheck = new String[]{dateUtils.todayDateWithSlash(mPresenter.getAppContext())};
            mPresenter.onGetMoshtaryHesab(moshtaryShomarehHesabModels, bankModels, codeNoevosol, tarikhSarResidForCheck[0], tarikhSarResidForCheck[0]);

        }

    }

    @Override
    public void getNoeIranCheck(int codeNoevosol) {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(Constants.CC_IRAN_CHECK());
        mPresenter.onGetNoeIranCheck(childParameterModels, codeNoevosol);
    }

    @Override
    public void checkInsert(int ccMoshtary, long ccDarkhastFaktor, int codeNoeVosolMoshtary, int flagInputHesab, String mablaghMandeh, String nameNoevosol, DariaftPardakhtPPCModel dariaftPardakhtPPCModel) {
        boolean hasError = false;
        long ccDariaftPardakht = 0;
        long ccDpdfNewInsert = 0;
        int valueVajhNaghd = Integer.parseInt(Constants.VALUE_VAJH_NAGHD());
        if (ccDarkhastFaktor == -1) {
            mPresenter.onErrorCheckInsert(R.string.errorFindccDarkhastFaktor);
            hasError = true;
            return;
        }

        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        if (darkhastFaktorModel.getCcDarkhastFaktor() == 0) {
            mPresenter.onErrorCheckInsert(R.string.errorFindDarkhastFaktor);
            hasError = true;
            return;
        }
        strCodeNoeVosol = String.valueOf(codeNoeVosolMoshtary);
        if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_CHECK()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI())) {
            if (dariaftPardakhtPPCModel.getShomarehSanad().length() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorInputNumber);
                return;
            }
        }

        try {
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
            String nameMoshtary = moshtaryDAO.getByccMoshtary(ccMoshtary).getNameMoshtary();
            Log.d("invoice", "nameMoshtary from moshtary dao : " + nameMoshtary);
            if (nameMoshtary == null || nameMoshtary.trim().equals("")) {
                mPresenter.onErrorCheckInsert(R.string.errorNameSahebSanad);
                return;
            }
            dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);

            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());

            int tedadRoozForRotbeh = 0;// = Get_TedadRoozForRotbeh(); remove this method because return 0
            Log.d("settlement", "darkhastFaktorModel.getCodeNoeVosolAzMoshtary() : " + darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
            Log.d("settlement", "valueVajhNaghd : " + valueVajhNaghd);
            if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) /*|| darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghd*/) {
                Date tarikhRooz = sdf.parse(sdf.format(new Date()));
                Date tarikhSanad = sdf.parse(dateUtils.persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi()));
                Date threeDaysLaterDate = dateUtils.addDay(tarikhRooz, -3);
                Date countDaysForRateLaterDate = dateUtils.addDay(tarikhRooz, +tedadRoozForRotbeh);

                if (tarikhSanad.getTime() > tarikhRooz.getTime() && darkhastFaktorModel.getCodeNoeVosolAzMoshtary() != valueVajhNaghd) {
                    mPresenter.onErrorCheckInsert(R.string.errorBiggerDate);
                    hasError = true;
                    return;
                }
                if (tarikhSanad.getTime() > countDaysForRateLaterDate.getTime() && darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghd) {
                    mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
                    hasError = true;
                    return;
                }
                if (tarikhSanad.getTime() < threeDaysLaterDate.getTime()) {
                    mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
                    hasError = true;
                    return;
                }
            }

            if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI())) {
                DariaftPardakhtDarkhastFaktorPPCDAO dpdfDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
                if (dariaftPardakhtPPCModel.getShomarehSanad().length() > 0) {
                    if (dpdfDAO.getCountShomarehSanad(dariaftPardakhtPPCModel.getShomarehSanad(), dariaftPardakhtPPCModel.getMablagh()) > 0) {
                        mPresenter.onErrorCheckInsert(R.string.errorDuplicateNumber);
                        hasError = true;
                    }
                }
            }

            if (strCodeNoeVosol.equals(Constants.VALUE_CHECK())) {
                String errors = "";

                Date dateTarikhSarResidForCheck = sdf.parse(getTarikhSarResidForCheck(darkhastFaktorModel, codeNoeVosolMoshtary, Double.parseDouble(mablaghMandeh))[1]);

                Date tarikhSanad = sdf.parse(dateUtils.persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi()));
                Log.d("invoice", "flagInputHesab : " + flagInputHesab);
                Log.d("invoice", "shomareHesab len : " + dariaftPardakhtPPCModel.getShomarehHesabSanad().trim().length());
                Log.d("invoice", "shomareHesab : " + dariaftPardakhtPPCModel.getShomarehHesabSanad());
                if (flagInputHesab == 0 && dariaftPardakhtPPCModel.getCcShomarehHesab() == 0) {
                    errors += mPresenter.getAppContext().getResources().getString(R.string.errorShomareHesab);
                    hasError = true;
                } else if (flagInputHesab == 0 && dariaftPardakhtPPCModel.getCcShomarehHesab() != 0) {
                    Log.d("invoice", "CcShomarehHesab : " + dariaftPardakhtPPCModel.getCcShomarehHesab());
                    nameMoshtary = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext()).getNameMoshtaryByccMoshtaryShomareHesab(dariaftPardakhtPPCModel.getCcShomarehHesab());
                    Log.d("invoice", "nameMoshtary : " + nameMoshtary);
                    if (nameMoshtary == null || nameMoshtary.trim().equals("")) {
                        mPresenter.onErrorCheckInsert(R.string.errorNameSahebSanad);
                        hasError = true;
                        return;
                    } else {
                        dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);
                    }
                } else if (flagInputHesab == 1) {
                    if (dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorBank);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getCodeShobehSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorCodeShobe);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getNameShobehSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorNameShobe);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getShomarehHesabSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorInputHesab);
                        hasError = true;
                    }
                }
                Log.d("check", "tarikhSanad.getTime() : " + tarikhSanad.getTime());
                Log.d("check", "tarikhSanad : " + tarikhSanad);
                Log.d("check", "tarikhSanad.getTime() : " + tarikhSanad.getTime());
                Log.d("check", "tedadRoozForRotbeh : " + tedadRoozForRotbeh);
                Log.d("check", "dateTarikhSarResidForCheck : " + dateTarikhSarResidForCheck);
                Log.d("check", "new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh).getTime() : " + dateUtils.addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh).getTime());
                Log.d("check", "new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh) : " + dateUtils.addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh));
                if (tarikhSanad.getTime() > dateUtils.addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh).getTime()) {
                    errors += mPresenter.getAppContext().getResources().getString(R.string.errorInvalidDate);
                    hasError = true;
                }
                if (errors.trim().length() > 0) {
                    mPresenter.onErrorCheckInsert(errors);
                    hasError = true;
                }
            }

            if (strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) && dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorBank);
                hasError = true;
                return;
            }

            else if (strCodeNoeVosol.equals(Constants.VALUE_POS()) && dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorAssignPosForSeller);
                hasError = true;
            }

            if (!strCodeNoeVosol.equals(Constants.VALUE_RESID()) && !strCodeNoeVosol.equals(Constants.VALUE_IRANCHECK())) {
                if (dariaftPardakhtPPCModel.getMablagh() == 0) {
                    mPresenter.onErrorCheckInsert(R.string.errorInputMablagh);
                    hasError = true;
                } else {
                    if (dariaftPardakhtPPCModel.getMablagh() <= 0) {
                        mPresenter.onErrorCheckInsert(R.string.errorNegativeInputMablagh);
                        hasError = true;
                    }
                }
            }

            int valueVajhNaghdYekSetare = Integer.parseInt(Constants.VALUE_VAJH_NAGHD_YEK_SETARE());
            int valueVajhNaghdDoSetare = Integer.parseInt(Constants.VALUE_VAJH_NAGHD_DO_SETARE());
            if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghdYekSetare || darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghdDoSetare) {
                if (strCodeNoeVosol.equals(Constants.VALUE_POS()) && strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) && strCodeNoeVosol.equals(Constants.VALUE_VAJH_NAGHD())) {
                    mPresenter.onErrorCheckInsert(R.string.errorInvalidSelectedNoeVosol);
                    hasError = true;
                }
            }
            DariaftPardakhtDarkhastFaktorPPCDAO dpdarkhastfaktorppcDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            int CodeNoeVosolSabtShode = 0;
            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dpdarkhastfaktorppcDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            if (dariaftPardakhtDarkhastFaktorPPCModels.size() > 0) {
                CodeNoeVosolSabtShode = dariaftPardakhtDarkhastFaktorPPCModels.get(0).getCodeNoeVosol();
            }
            if (CodeNoeVosolSabtShode > 0) {
                int valueResid = Integer.parseInt(Constants.VALUE_RESID());
                if (CodeNoeVosolSabtShode == valueResid && !strCodeNoeVosol.equals(Constants.VALUE_RESID())) {
                    mPresenter.onErrorCheckInsert(R.string.errorDuplicateResidForVosol);
                    hasError = true;
                }
                if (CodeNoeVosolSabtShode != valueResid && strCodeNoeVosol.equals(Constants.VALUE_RESID())) {
                    mPresenter.onErrorCheckInsert(R.string.errorDuplicateVosolForResid);
                    hasError = true;
                }
            }
            /*if (darkhastFaktorModel.getMablaghKhalesFaktor() == 0)
            {
                mPresenter.onErrorCheckInsert(R.string.errorMablaghKhalesFaktor);
                hasError = true;
            }*/

            if (!hasError) {
                /*if (strCodeNoeVosol.equals(Constants.VALUE_POS()))
                {
                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne();
                    dariaftPardakhtPPCModel.setCcShomarehHesab(foroshandehMamorPakhshModel.getCcPosShomarehHesab());
                }*/

                Log.d("dariaftPardakht", "ccshomarehesabe before insert : " + dariaftPardakhtPPCModel.getCcShomarehHesab());
                ccDariaftPardakht = insertDariaftPardakht(ccMoshtary, ccDarkhastFaktor, codeNoeVosolMoshtary, mablaghMandeh, nameNoevosol, dariaftPardakhtPPCModel, 0, 0, 0, Constants.FROM_TREASURYLIST);
                if (ccDariaftPardakht > 0) {
                    if (isValidDariaftPardakhtDarkhastFaktor((long) darkhastFaktorModel.getMablaghKhalesFaktor(), ccDariaftPardakht, strCodeNoeVosol, mablaghMandeh, darkhastFaktorModel.getCcDarkhastFaktor())) {
                        ccDpdfNewInsert = insertDariaftPardakhtDarkhastFaktor(ccDariaftPardakht, dariaftPardakhtPPCModel, strCodeNoeVosol, 0, 0, 0);
                    }
                }


                Date tarikhRoozDate = simpleDateFormatDATE_TIME_WITH_SPACE.parse(getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), ""));
//Date Now
                long diff = DateUtils.getDateDiffAsDay(simpleDateFormatShort.parse(darkhastFaktorModel.getTarikhErsal()), tarikhRoozDate);

//                darkhastFaktorModel.getTarikhErsal() , tarikhserver;
                // TODO ::
                if (darkhastFaktorModel.getIsTajil() == 1) {
                    if (diff <= 2)
                        setTajil(darkhastFaktorModel, codeNoeVosolMoshtary);

                if (canGetTajil) {
                    Insert_Tajil(codeNoeVosolMoshtary, darkhastFaktorModel);
                    //
                    setTajil(darkhastFaktorModel, codeNoeVosolMoshtary);
                }
                }
                mPresenter.onSuccessInsert(setMablaghMandehFaktor(ccDarkhastFaktor));
                getVosols(ccDarkhastFaktor);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "checkInsert", "");
            mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
        }
    }

    @Override
    public void checkInsertCheckBargashty(int ccMoshtary, long ccDarkhastFaktor, int codeNoeVosolMoshtary, int flagInputHesab, String mablaghMandeh, String nameNoevosol, DariaftPardakhtPPCModel dariaftPardakhtPPCModel) {
        boolean hasError = false;
        long ccDpdfInsert = 0;
        int valueVajhNaghd = Integer.parseInt(Constants.VALUE_VAJH_NAGHD());
        BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        BargashtyModel bargashtyModel = bargashtyDAO.getBargashtyByShomarehSanad(shomarehSanadCheckBargashty).get(0);
        if (ccDarkhastFaktor == -1) {
            mPresenter.onErrorCheckInsert(R.string.errorFindccDarkhastFaktor);
            hasError = true;
            return;
        }
        //DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        //DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        if (ccDarkhastFaktor == 0) {
            mPresenter.onErrorCheckInsert(R.string.errorFindDarkhastFaktor);
            hasError = true;
            return;
        }
        String strCodeNoeVosol = String.valueOf(codeNoeVosolMoshtary);
        if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_CHECK()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI())) {
            if (dariaftPardakhtPPCModel.getShomarehSanad().length() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorInputNumber);
                return;
            }
        }

        try {

            String nameMoshtary = bargashtyModel.getNameMoshtary();
            long ccDariaftPardakhtCheckBargashty = bargashtyDAO.getBargashtyByShomarehSanad(shomarehSanadCheckBargashty).get(0).getCcDariaftPardakht();
            Log.d("invoice", "nameMoshtary from moshtary dao : " + nameMoshtary);
            if (nameMoshtary == null || nameMoshtary.trim().equals("")) {
                mPresenter.onErrorCheckInsert(R.string.errorNameSahebSanad);
                return;
            }
            dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);

            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());
            int tedadRoozForRotbeh = 0;// = Get_TedadRoozForRotbeh(); remove this method because return 0
            //Log.d("settlement", "darkhastFaktorModel.getCodeNoeVosolAzMoshtary() : " + darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
            Log.d("settlement", "valueVajhNaghd : " + valueVajhNaghd);
            if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) /*|| darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghd*/) {
                Date tarikhRooz = sdf.parse(sdf.format(new Date()));
                Date tarikhSanad = sdf.parse(dateUtils.persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi()));
                Date threeDaysLaterDate = dateUtils.addDay(tarikhRooz, -3);
                Date countDaysForRateLaterDate = dateUtils.addDay(tarikhRooz, +tedadRoozForRotbeh);

//                if (tarikhSanad.getTime() > tarikhRooz.getTime() && darkhastFaktorModel.getCodeNoeVosolAzMoshtary() != valueVajhNaghd) {
//                    mPresenter.onErrorCheckInsert(R.string.errorBiggerDate);
//                    hasError = true;
//                    return;
//                }
//                if (tarikhSanad.getTime() > countDaysForRateLaterDate.getTime() && darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghd) {
//                    mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
//                    hasError = true;
//                    return;
//                }
                if (tarikhSanad.getTime() < threeDaysLaterDate.getTime()) {
                    mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
                    hasError = true;
                    return;
                }
            }

            if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI())) {
                DariaftPardakhtDarkhastFaktorPPCDAO dpdfDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
                if (dariaftPardakhtPPCModel.getShomarehSanad().length() > 0) {
                    if (dpdfDAO.getCountShomarehSanad(dariaftPardakhtPPCModel.getShomarehSanad(), dariaftPardakhtPPCModel.getMablagh()) > 0) {
                        mPresenter.onErrorCheckInsert(R.string.errorDuplicateNumber);
                        hasError = true;
                    }
                }
            }

            if (strCodeNoeVosol.equals(Constants.VALUE_CHECK())) {
                String errors = "";
//todo ModatVosol Check
                //   Date dateTarikhSarResidForCheck = sdf.parse(getTarikhSarResidForCheck(darkhastFaktorModel, codeNoeVosolMoshtary, Double.parseDouble(mablaghMandeh))[1]);

                Date tarikhSanad = sdf.parse(dateUtils.persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi()));
                Log.d("invoice", "flagInputHesab : " + flagInputHesab);
                Log.d("invoice", "shomareHesab len : " + dariaftPardakhtPPCModel.getShomarehHesabSanad().trim().length());
                Log.d("invoice", "shomareHesab : " + dariaftPardakhtPPCModel.getShomarehHesabSanad());
                if (flagInputHesab == 0 && dariaftPardakhtPPCModel.getCcShomarehHesab() == 0) {
                    errors += mPresenter.getAppContext().getResources().getString(R.string.errorShomareHesab);
                    hasError = true;
                } else if (flagInputHesab == 0 && dariaftPardakhtPPCModel.getCcShomarehHesab() != 0) {
                    Log.d("invoice", "CcShomarehHesab : " + dariaftPardakhtPPCModel.getCcShomarehHesab());
                    nameMoshtary = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext()).getNameMoshtaryByccMoshtaryShomareHesab(dariaftPardakhtPPCModel.getCcShomarehHesab());
                    Log.d("invoice", "nameMoshtary : " + nameMoshtary);
                    if (nameMoshtary == null || nameMoshtary.trim().equals("")) {
                        mPresenter.onErrorCheckInsert(R.string.errorNameSahebSanad);
                        hasError = true;
                        return;
                    } else {
                        dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);
                    }
                } else if (flagInputHesab == 1) {
                    if (dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorBank);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getCodeShobehSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorCodeShobe);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getNameShobehSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorNameShobe);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getShomarehHesabSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorInputHesab);
                        hasError = true;
                    }
                }
                Log.d("check", "tarikhSanad.getTime() : " + tarikhSanad.getTime());
                Log.d("check", "tarikhSanad : " + tarikhSanad);
                Log.d("check", "tarikhSanad.getTime() : " + tarikhSanad.getTime());
                Log.d("check", "tedadRoozForRotbeh : " + tedadRoozForRotbeh);
                // Log.d("check", "dateTarikhSarResidForCheck : " + dateTarikhSarResidForCheck);
                // Log.d("check", "new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh).getTime() : " + new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh).getTime());
                //Log.d("check", "new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh) : " + new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh));
//                if (tarikhSanad.getTime() > new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh).getTime()) {
//                    errors += mPresenter.getAppContext().getResources().getString(R.string.errorInvalidDate);
//                    hasError = true;
//                }
                if (errors.trim().length() > 0) {
                    mPresenter.onErrorCheckInsert(errors);
                    hasError = true;
                }
            }


            if (strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) && dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorBank);
                hasError = true;
                return;
            } else if (strCodeNoeVosol.equals(Constants.VALUE_POS()) && dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorAssignPosForSeller);
                hasError = true;
            }

            if (!strCodeNoeVosol.equals(Constants.VALUE_RESID()) && !strCodeNoeVosol.equals(Constants.VALUE_IRANCHECK())) {
                if (dariaftPardakhtPPCModel.getMablagh() == 0) {
                    mPresenter.onErrorCheckInsert(R.string.errorInputMablagh);
                    hasError = true;
                } else {
                    if (dariaftPardakhtPPCModel.getMablagh() <= 0) {
                        mPresenter.onErrorCheckInsert(R.string.errorNegativeInputMablagh);
                        hasError = true;
                    }
                }
            }

//            int valueVajhNaghdYekSetare = Integer.parseInt(Constants.VALUE_VAJH_NAGHD_YEK_SETARE());
//            int valueVajhNaghdDoSetare = Integer.parseInt(Constants.VALUE_VAJH_NAGHD_DO_SETARE());
//            if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghdYekSetare || darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghdDoSetare) {
//                if (strCodeNoeVosol.equals(Constants.VALUE_POS()) && strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) && strCodeNoeVosol.equals(Constants.VALUE_VAJH_NAGHD())) {
//                    mPresenter.onErrorCheckInsert(R.string.errorInvalidSelectedNoeVosol);
//                    hasError = true;
//                }
//            }
            DariaftPardakhtDarkhastFaktorPPCDAO dpdarkhastfaktorppcDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            int CodeNoeVosolSabtShode = 0;
            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dpdarkhastfaktorppcDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            if (dariaftPardakhtDarkhastFaktorPPCModels.size() > 0) {
                CodeNoeVosolSabtShode = dariaftPardakhtDarkhastFaktorPPCModels.get(0).getCodeNoeVosol();
            }
            if (CodeNoeVosolSabtShode > 0) {
                int valueResid = Integer.parseInt(Constants.VALUE_RESID());
                if (CodeNoeVosolSabtShode == valueResid && !strCodeNoeVosol.equals(Constants.VALUE_RESID())) {
                    mPresenter.onErrorCheckInsert(R.string.errorDuplicateResidForVosol);
                    hasError = true;
                }
                if (CodeNoeVosolSabtShode != valueResid && strCodeNoeVosol.equals(Constants.VALUE_RESID())) {
                    mPresenter.onErrorCheckInsert(R.string.errorDuplicateVosolForResid);
                    hasError = true;
                }
            }
            /*if (darkhastFaktorModel.getMablaghKhalesFaktor() == 0)
            {
                mPresenter.onErrorCheckInsert(R.string.errorMablaghKhalesFaktor);
                hasError = true;
            }*/

            if (!hasError) {
                /*if (strCodeNoeVosol.equals(Constants.VALUE_POS()))
                {
                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne();
                    dariaftPardakhtPPCModel.setCcShomarehHesab(foroshandehMamorPakhshModel.getCcPosShomarehHesab());
                }*/

                Log.d("dariaftPardakht", "ccshomarehesabe before insert : " + dariaftPardakhtPPCModel.getCcShomarehHesab());
                long ccDariaftPardakht = insertDariaftPardakht(ccMoshtary, ccDarkhastFaktor, codeNoeVosolMoshtary, mablaghMandeh, nameNoevosol, dariaftPardakhtPPCModel, 0, 0, bargashtyModel.getCcDariaftPardakht(), Constants.FROM_CHECK_BARGASHTI);
                if (ccDariaftPardakht > 0) {

                    boolean isValidDariaftPardakhtDarkhastFaktor = isValidDariaftPardakhtDarkhastFaktor((long) ((double) bargashtyModel.getMablagh()), ccDariaftPardakht, strCodeNoeVosol, mablaghMandeh, ccDarkhastFaktor);
                    if (isValidDariaftPardakhtDarkhastFaktor) {
                        ccDpdfInsert = insertDariaftPardakhtDarkhastFaktor(ccDariaftPardakht, dariaftPardakhtPPCModel, strCodeNoeVosol, ccDariaftPardakhtCheckBargashty, 0, 0);
                    }
                }

                bargashtyModel = bargashtyDAO.getBargashtyByShomarehSanad(shomarehSanadCheckBargashty).get(0);
//                dpdarkhastfaktorppcDAO.haveTajil(ccDarkhastFaktor);
                Log.i("asd", "adasd");
                long mablaghMandehOtherVosol = (long) setMablaghMandehOtherVosol(ccDarkhastFaktor);
                mPresenter.onSuccessInsert(mablaghMandehOtherVosol);


                boolean haveDirkardVosol = haveDirkardVosol(null, bargashtyModel);
                if (mablaghMandehOtherVosol == 0 && !haveDirkardVosol) {
                    insertDirkardVosol(null, bargashtyModel);
                    mablaghMandehOtherVosol = (long) setMablaghMandehOtherVosol(ccDarkhastFaktor);
                    mPresenter.onSuccessInsert(mablaghMandehOtherVosol);
                }
                getVosols(ccDarkhastFaktor);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "checkInsert", "");
            mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
        }
    }


    public void checkInsertCheckPishDrayaft(int ccMoshtary, int codeNoeVosolMoshtary, int flagInputHesab, String mablaghMandeh, String nameNoevosol, DariaftPardakhtPPCModel dariaftPardakhtPPCModel) {
        boolean hasError = false;
        int valueVajhNaghd = Integer.parseInt(Constants.VALUE_VAJH_NAGHD());
//        if (ccDarkhastFaktor == -1) {
//            mPresenter.onErrorCheckInsert(R.string.errorFindccDarkhastFaktor);
//            hasError = true;
//            return;
//        }
//        //DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
//        //DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
//        if (ccDarkhastFaktor == 0) {
//            mPresenter.onErrorCheckInsert(R.string.errorFindDarkhastFaktor);
//            hasError = true;
//            return;
//        }
        String strCodeNoeVosol = String.valueOf(codeNoeVosolMoshtary);
        if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_CHECK()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI())) {
            if (dariaftPardakhtPPCModel.getShomarehSanad().length() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorInputNumber);
                return;
            }
        }

        try {
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
            String nameMoshtary = moshtaryDAO.getByccMoshtary(ccMoshtary).getNameMoshtary();
            Log.d("invoice", "nameMoshtary from moshtary dao : " + nameMoshtary);
            if (nameMoshtary == null || nameMoshtary.trim().equals("")) {
                mPresenter.onErrorCheckInsert(R.string.errorNameSahebSanad);
                return;
            }
            dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);

            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());
            int tedadRoozForRotbeh = 0;// = Get_TedadRoozForRotbeh(); remove this method because return 0
            //Log.d("settlement", "darkhastFaktorModel.getCodeNoeVosolAzMoshtary() : " + darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
            Log.d("settlement", "valueVajhNaghd : " + valueVajhNaghd);
            if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) /*|| darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghd*/) {
                Date tarikhRooz = sdf.parse(sdf.format(new Date()));
                Date tarikhSanad = sdf.parse(dateUtils.persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi()));
                Date threeDaysLaterDate = dateUtils.addDay(tarikhRooz, -3);
                Date countDaysForRateLaterDate = dateUtils.addDay(tarikhRooz, +tedadRoozForRotbeh);

//                if (tarikhSanad.getTime() > tarikhRooz.getTime() && darkhastFaktorModel.getCodeNoeVosolAzMoshtary() != valueVajhNaghd) {
//                    mPresenter.onErrorCheckInsert(R.string.errorBiggerDate);
//                    hasError = true;
//                    return;
//                }
//                if (tarikhSanad.getTime() > countDaysForRateLaterDate.getTime() && darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghd) {
//                    mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
//                    hasError = true;
//                    return;
//                }
                if (tarikhSanad.getTime() < threeDaysLaterDate.getTime()) {
                    mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
                    hasError = true;
                    return;
                }
            }

            if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI())) {
                DariaftPardakhtDarkhastFaktorPPCDAO dpdfDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
                if (dariaftPardakhtPPCModel.getShomarehSanad().length() > 0) {
                    if (dpdfDAO.getCountShomarehSanad(dariaftPardakhtPPCModel.getShomarehSanad(), dariaftPardakhtPPCModel.getMablagh()) > 0) {
                        mPresenter.onErrorCheckInsert(R.string.errorDuplicateNumber);
                        hasError = true;
                    }
                }
            }

            if (strCodeNoeVosol.equals(Constants.VALUE_CHECK())) {
                String errors = "";
//todo ModatVosol Check
                //   Date dateTarikhSarResidForCheck = sdf.parse(getTarikhSarResidForCheck(darkhastFaktorModel, codeNoeVosolMoshtary, Double.parseDouble(mablaghMandeh))[1]);

                Date tarikhSanad = sdf.parse(dateUtils.persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi()));
                Log.d("invoice", "flagInputHesab : " + flagInputHesab);
                Log.d("invoice", "shomareHesab len : " + dariaftPardakhtPPCModel.getShomarehHesabSanad().trim().length());
                Log.d("invoice", "shomareHesab : " + dariaftPardakhtPPCModel.getShomarehHesabSanad());
                if (flagInputHesab == 0 && dariaftPardakhtPPCModel.getCcShomarehHesab() == 0) {
                    errors += mPresenter.getAppContext().getResources().getString(R.string.errorShomareHesab);
                    hasError = true;
                } else if (flagInputHesab == 0 && dariaftPardakhtPPCModel.getCcShomarehHesab() != 0) {
                    Log.d("invoice", "CcShomarehHesab : " + dariaftPardakhtPPCModel.getCcShomarehHesab());
                    nameMoshtary = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext()).getNameMoshtaryByccMoshtaryShomareHesab(dariaftPardakhtPPCModel.getCcShomarehHesab());
                    Log.d("invoice", "nameMoshtary : " + nameMoshtary);
                    if (nameMoshtary == null || nameMoshtary.trim().equals("")) {
                        mPresenter.onErrorCheckInsert(R.string.errorNameSahebSanad);
                        hasError = true;
                        return;
                    } else {
                        dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);
                    }
                } else if (flagInputHesab == 1) {
                    if (dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorBank);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getCodeShobehSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorCodeShobe);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getNameShobehSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorNameShobe);
                        hasError = true;
                    }
                    if (dariaftPardakhtPPCModel.getShomarehHesabSanad().trim().length() == 0) {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorInputHesab);
                        hasError = true;
                    }
                }
                Log.d("check", "tarikhSanad.getTime() : " + tarikhSanad.getTime());
                Log.d("check", "tarikhSanad : " + tarikhSanad);
                Log.d("check", "tarikhSanad.getTime() : " + tarikhSanad.getTime());
                Log.d("check", "tedadRoozForRotbeh : " + tedadRoozForRotbeh);
                // Log.d("check", "dateTarikhSarResidForCheck : " + dateTarikhSarResidForCheck);
                // Log.d("check", "new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh).getTime() : " + new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh).getTime());
                //Log.d("check", "new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh) : " + new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh));
//                if (tarikhSanad.getTime() > new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck, tedadRoozForRotbeh).getTime()) {
//                    errors += mPresenter.getAppContext().getResources().getString(R.string.errorInvalidDate);
//                    hasError = true;
//                }
                if (errors.trim().length() > 0) {
                    mPresenter.onErrorCheckInsert(errors);
                    hasError = true;
                }
            }


            if (strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) && dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorBank);
                hasError = true;
                return;
            } else if (strCodeNoeVosol.equals(Constants.VALUE_POS()) && dariaftPardakhtPPCModel.getCcBankSanad() == 0) {
                mPresenter.onErrorCheckInsert(R.string.errorAssignPosForSeller);
                hasError = true;
            }

            if (!strCodeNoeVosol.equals(Constants.VALUE_RESID()) && !strCodeNoeVosol.equals(Constants.VALUE_IRANCHECK())) {
                if (dariaftPardakhtPPCModel.getMablagh() == 0) {
                    mPresenter.onErrorCheckInsert(R.string.errorInputMablagh);
                    hasError = true;
                } else {
                    if (dariaftPardakhtPPCModel.getMablagh() <= 0) {
                        mPresenter.onErrorCheckInsert(R.string.errorNegativeInputMablagh);
                        hasError = true;
                    }
                }
            }

//            int valueVajhNaghdYekSetare = Integer.parseInt(Constants.VALUE_VAJH_NAGHD_YEK_SETARE());
//            int valueVajhNaghdDoSetare = Integer.parseInt(Constants.VALUE_VAJH_NAGHD_DO_SETARE());
//            if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghdYekSetare || darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghdDoSetare) {
//                if (strCodeNoeVosol.equals(Constants.VALUE_POS()) && strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) && strCodeNoeVosol.equals(Constants.VALUE_VAJH_NAGHD())) {
//                    mPresenter.onErrorCheckInsert(R.string.errorInvalidSelectedNoeVosol);
//                    hasError = true;
//                }
//            }
//            DariaftPardakhtDarkhastFaktorPPCDAO dpdarkhastfaktorppcDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            int CodeNoeVosolSabtShode = 0;
//            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dpdarkhastfaktorppcDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
//            if (dariaftPardakhtDarkhastFaktorPPCModels.size() > 0) {
//                CodeNoeVosolSabtShode = dariaftPardakhtDarkhastFaktorPPCModels.get(0).getCodeNoeVosol();
//            }
            if (CodeNoeVosolSabtShode > 0) {
                int valueResid = Integer.parseInt(Constants.VALUE_RESID());
                if (CodeNoeVosolSabtShode == valueResid && !strCodeNoeVosol.equals(Constants.VALUE_RESID())) {
                    mPresenter.onErrorCheckInsert(R.string.errorDuplicateResidForVosol);
                    hasError = true;
                }
                if (CodeNoeVosolSabtShode != valueResid && strCodeNoeVosol.equals(Constants.VALUE_RESID())) {
                    mPresenter.onErrorCheckInsert(R.string.errorDuplicateVosolForResid);
                    hasError = true;
                }
            }
            /*if (darkhastFaktorModel.getMablaghKhalesFaktor() == 0)
            {
                mPresenter.onErrorCheckInsert(R.string.errorMablaghKhalesFaktor);
                hasError = true;
            }*/

            if (!hasError) {
                /*if (strCodeNoeVosol.equals(Constants.VALUE_POS()))
                {
                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne();
                    dariaftPardakhtPPCModel.setCcShomarehHesab(foroshandehMamorPakhshModel.getCcPosShomarehHesab());
                }*/

                Log.d("dariaftPardakht", "ccshomarehesabe before insert : " + dariaftPardakhtPPCModel.getCcShomarehHesab());
                long ccDariaftPardakht = insertDariaftPardakht(ccMoshtary, 0, codeNoeVosolMoshtary, mablaghMandeh, nameNoevosol, dariaftPardakhtPPCModel, 0, 0, 0, Constants.FROM_PISH_DARYAFT);
//                if (ccDariaftPardakht > 0) {
//
//
////                    if (isValidDariaftPardakhtDarkhastFaktor(Long.parseLong(mablaghMandeh), ccDariaftPardakht, strCodeNoeVosol, mablaghMandeh, ccDarkhastFaktor)) {
////                        long ccDpdfNewInsert = insertDariaftPardakhtDarkhastFaktor(ccDariaftPardakht, dariaftPardakhtPPCModel, strCodeNoeVosol, ccDariaftPardakhtCheckBargashty);
////                    }
//                }
                mPresenter.onSuccessInsert(0);
                getVosolsPishDariaft(ccMoshtary);

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "checkInsert", "");
            mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
        }
    }

    /**
     * insert DariaftPardakht
     *
     * @param ccMoshtary
     * @param ccDarkhastFaktor
     * @param codeNoeVosolMoshtary
     * @param mablaghMandeh
     * @param nameNoeVosol
     * @param dariaftPardakhtPPCModel
     * @param isDirkard
     * @param isTajil
     * @param ccDaryaftPardakhtCheckBargashty
     * @param from
     * @return
     */
    private long insertDariaftPardakht(int ccMoshtary, long ccDarkhastFaktor, int codeNoeVosolMoshtary, String mablaghMandeh, String nameNoeVosol, DariaftPardakhtPPCModel dariaftPardakhtPPCModel, int isDirkard, int isTajil, long ccDaryaftPardakhtCheckBargashty, int from) {
        String strCodeNoeVosolMoshtary = String.valueOf(codeNoeVosolMoshtary);
        DariaftPardakhtPPCDAO dariaftPardakhtDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehmamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());

        long ccDariaftPardakht = -1L;
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehmamorPakhshDAO.getIsSelect();


        try {
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
            ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(Constants.CC_CODE_NOE_SANAD());
            int codeNoeCheck = new PubFunc().new VosolUtil().getCodeNoeCheck(codeNoeVosolMoshtary, childParameterModels);
            String tarikhSarResidGregorian = "";
            try {
                if (isDirkard == 0 && isTajil == 0) {
                    if (!dariaftPardakhtPPCModel.getTarikhSanadShamsi().trim().equals("")) {
                        tarikhSarResidGregorian = dateUtils.persianToGregorianWhithTime(dariaftPardakhtPPCModel.getTarikhSanadShamsi());
                    } else {
                        tarikhSarResidGregorian = dateUtils.persianToGregorianWhithTime(new Date().toString());
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }


            Log.d("invoice", "mablagh dariaftpardakhat : " + dariaftPardakhtPPCModel.getMablagh());

            dariaftPardakhtPPCModel.setCcMarkazAnbar(foroshandehMamorPakhshModel.getCcMarkazAnbar());
            dariaftPardakhtPPCModel.setCcMarkazForosh(foroshandehMamorPakhshModel.getCcMarkazForosh());
            dariaftPardakhtPPCModel.setCcMarkazSazmanForoshSakhtarForosh(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
            dariaftPardakhtPPCModel.setCodeNoeVosol(codeNoeVosolMoshtary);
            dariaftPardakhtPPCModel.setNameNoeVosol(nameNoeVosol);
            dariaftPardakhtPPCModel.setZamaneSabt(new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
            dariaftPardakhtPPCModel.setCcNoeHesabSanad(0);
            dariaftPardakhtPPCModel.setNameNoeHesabSanad("");
            dariaftPardakhtPPCModel.setTarikhSanad(tarikhSarResidGregorian);
            dariaftPardakhtPPCModel.setCodeNoeCheck(codeNoeCheck);
            dariaftPardakhtPPCModel.setNameNoeCheck("");
            dariaftPardakhtPPCModel.setMablaghMandeh(Double.parseDouble(mablaghMandeh));
            dariaftPardakhtPPCModel.setCcDariaftPardakhtLink(0);
            dariaftPardakhtPPCModel.setCcAfradErsalKonandeh(foroshandehMamorPakhshModel.getCcAfrad());
            dariaftPardakhtPPCModel.setCcDarkhastFaktor(ccDarkhastFaktor);
            dariaftPardakhtPPCModel.setCcKardex(0);
            dariaftPardakhtPPCModel.setCodeVazeiat(0);
            dariaftPardakhtPPCModel.setCcMoshtary(ccMoshtary);
            dariaftPardakhtPPCModel.setIsCheckMoshtary(0);
            if (from == Constants.FROM_PISH_DARYAFT) {
                dariaftPardakhtPPCModel.setIsPishDariaft(Constants.FROM_PISH_DARYAFT);
            } else {
                dariaftPardakhtPPCModel.setIsPishDariaft(0);
            }
            dariaftPardakhtPPCModel.setTabdil_NaghdBeFish(0);
            dariaftPardakhtPPCModel.setExtraProp_IsSend(0);
            dariaftPardakhtPPCModel.setExtraProp_IsDirkard(isDirkard);
            dariaftPardakhtPPCModel.setExtraProp_ccDaryaftPardakhtCheckBargashty(ccDaryaftPardakhtCheckBargashty);

            ccDariaftPardakht = dariaftPardakhtDAO.insert(dariaftPardakhtPPCModel);
        } catch (Exception exception) {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "prepareData", "");
        }
        return ccDariaftPardakht;
    }


    //private boolean isValidDariaftPardakhtDarkhastFaktor(DarkhastFaktorModel darkhastFaktorModel, long ccDariaftPardakht, String strCodeNoeVosolMoshtary, String mablaghMandeh) {
    private boolean isValidDariaftPardakhtDarkhastFaktor(long Mablagh, long ccDariaftPardakht, String strCodeNoeVosolMoshtary, String mablaghMandeh, long ccDarkhastFaktor) {
        double MablaghDpdf = 0;
        double MablaghDariaftPardakht = 0;
        //double MablaghKhalesFaktor = darkhastFaktorModel.getMablaghKhalesFaktor();
        double MablaghKhalesFaktor = Mablagh;
        double mablaghTakhsis = 0L;

        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());

        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        DariaftPardakhtPPCModel dariaftPardakhtPPCModel = dariaftPardakhtPPCDAO.getByccDariaftPardakht(ccDariaftPardakht);
        MablaghDariaftPardakht = dariaftPardakhtPPCModel.getMablagh();

        if (!strCodeNoeVosolMoshtary.equals(Constants.VALUE_RESID())) {
            double doubleMablaghMandeh = Double.valueOf(mablaghMandeh.trim().replace(",", ""));
            if (doubleMablaghMandeh <= dariaftPardakhtPPCModel.getMablagh()) {
                mablaghTakhsis = doubleMablaghMandeh;
            }
            if (dariaftPardakhtPPCModel.getMablagh() <= doubleMablaghMandeh) {
                mablaghTakhsis = dariaftPardakhtPPCModel.getMablagh();
            }
        }
        /// جمع دریافتی های فاکتور
        for (DariaftPardakhtDarkhastFaktorPPCModel model : dariaftPardakhtDarkhastFaktorPPCModels) {
            MablaghDariaftPardakht += model.getMablaghDariaftPardakht();
            MablaghDpdf += model.getMablagh();
        }

        Log.d("invoice", "MablaghDpdf : " + MablaghDpdf);
        Log.d("invoice", "mablaghTakhsis : " + mablaghTakhsis);
        Log.d("invoice", "sum of two : " + MablaghDpdf + mablaghTakhsis);
        Log.d("invoice", "MablaghKhalesFaktor : " + MablaghKhalesFaktor);
        if (MablaghDpdf + mablaghTakhsis > MablaghKhalesFaktor) {
            mPresenter.onErrorCheckInsert(R.string.errorVosoliMoreThanFaktor);
            return false;
        }
        if (MablaghDariaftPardakht != 0) {
            if (MablaghDariaftPardakht < MablaghDpdf + mablaghTakhsis) {
                mPresenter.onErrorCheckInsert(R.string.errorVosoliBiggerThanFaktor);
                return false;
            }
        }
        return true;
    }


    /**
     * insert DariaftPardakhtDarkhastFaktor
     *
     * @param ccDariaftPardakht
     * @param dariaftPardakhtPPCModel
     * @param strCodeNoeVosolMoshtary
     * @param ccDariaftPardakhtCheckBargashty
     * @param isDirKard
     * @return
     */
    private long insertDariaftPardakhtDarkhastFaktor(long ccDariaftPardakht, DariaftPardakhtPPCModel dariaftPardakhtPPCModel, String strCodeNoeVosolMoshtary, long ccDariaftPardakhtCheckBargashty, int isDirKard, int isTajil) {
        long ccDariaftPardakhtDarkhastFaktor = 0;
        try {
            DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPC = new DariaftPardakhtDarkhastFaktorPPCModel();
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());

            ForoshandehMamorPakhshDAO foroshandehmamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehmamorPakhshDAO.getOne();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            String currentDate = sdf.format(new Date());
            double mablaghTakhsis = 0;
            if (!strCodeNoeVosolMoshtary.equals(Constants.VALUE_RESID())) {
                if (dariaftPardakhtPPCModel.getMablaghMandeh() <= dariaftPardakhtPPCModel.getMablagh()) {
                    mablaghTakhsis = dariaftPardakhtPPCModel.getMablaghMandeh();
                }
                if (dariaftPardakhtPPCModel.getMablagh() <= dariaftPardakhtPPCModel.getMablaghMandeh()) {
                    mablaghTakhsis = dariaftPardakhtPPCModel.getMablagh();
                }
            }
            dariaftPardakhtDarkhastFaktorPPC.setCcDarkhastFaktor(dariaftPardakhtPPCModel.getCcDarkhastFaktor());
            dariaftPardakhtDarkhastFaktorPPC.setCcDariaftPardakht(ccDariaftPardakht);
            dariaftPardakhtDarkhastFaktorPPC.setCodeNoeVosol(dariaftPardakhtPPCModel.getCodeNoeVosol());
            dariaftPardakhtDarkhastFaktorPPC.setNameNoeVosol(dariaftPardakhtPPCModel.getNameNoeVosol());
            dariaftPardakhtDarkhastFaktorPPC.setShomarehSanad(dariaftPardakhtPPCModel.getShomarehSanad());
            dariaftPardakhtDarkhastFaktorPPC.setTarikhSanad(dariaftPardakhtPPCModel.getTarikhSanad());
            dariaftPardakhtDarkhastFaktorPPC.setTarikhSanadShamsi(dariaftPardakhtPPCModel.getTarikhSanadShamsi());
            dariaftPardakhtDarkhastFaktorPPC.setMablaghDariaftPardakht(dariaftPardakhtPPCModel.getMablagh());
            dariaftPardakhtDarkhastFaktorPPC.setMablagh(mablaghTakhsis);
            dariaftPardakhtDarkhastFaktorPPC.setCodeVazeiat(0);
            dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktor(currentDate);
            dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktorShamsi(dateUtils.gregorianToPersianDateTime(sdf.parse(currentDate)));
            dariaftPardakhtDarkhastFaktorPPC.setCcAfradErsalKonandeh(foroshandehMamorPakhshModel.getCcAfrad());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazAnbar(foroshandehMamorPakhshModel.getCcMarkazAnbar());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazForosh(foroshandehMamorPakhshModel.getCcMarkazForosh());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazSazmanForoshSakhtarForosh(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
            dariaftPardakhtDarkhastFaktorPPC.setTabdil_NaghdBeFish(0);
            dariaftPardakhtDarkhastFaktorPPC.setCcTafkikJoze(0);
            dariaftPardakhtDarkhastFaktorPPC.setNaghlAzGhabl(0);
            dariaftPardakhtDarkhastFaktorPPC.setIsForTasviehTakhir(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsDirkard(isDirKard);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccKardexSatr(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsBestankari_ForTasviehTakhir(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsSend(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_CanDelete(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsTajil(isTajil);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccDarkhastFaktorServer(dariaftPardakhtPPCModel.getCcDarkhastFaktor());
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccDaryaftPardakhtCheckBargashty(ccDariaftPardakhtCheckBargashty);
            //strCodeNoeVosolMoshtary.equals(Constants.VALUE_MARJOEE()) ? 1 : 0;

            Log.d("invoice", "mablagh dariaftpardakhatdarkhastfaktor : " + dariaftPardakhtDarkhastFaktorPPC.getMablagh());

            ccDariaftPardakhtDarkhastFaktor = dariaftPardakhtDarkhastFaktorPPCDAO.insert(dariaftPardakhtDarkhastFaktorPPC);

            if (dariaftPardakhtPPCModel.getMablagh() > mablaghTakhsis) {
                /*flag_Bestankari = true;
                ccDariaftPardakhtDarkhastFaktor_ForBestankari = ccDariaftPardakhtDarkhastFaktor;*/
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "insertDariaftPardakhtDarkhastFaktor", "");
        }
        return ccDariaftPardakhtDarkhastFaktor;
    }


    private long setMablaghMandehFaktor(long ccDarkhastFaktor) {
        //--------------------- Update_MandehDarkhastFaktor --------------------------
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        darkhastFaktorDAO.updateMandehDarkhastFaktor(ccDarkhastFaktor);
        Log.d("mablaghMandeh Faktor", "mandeh in invoice : " + darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh());
        return darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh();
    }

    private float setMablaghMandehOtherVosol(long ccDarkhastFaktor) {
        //--------------------- Update_MandehDarkhastFaktor --------------------------

        BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        Log.d("mablaghMandeh Bargashti", "ccDarkhastFaktor: " + ccDarkhastFaktor + "mandeh in invoice : " + bargashtyDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh());

        bargashtyDAO.updateMandehBargashti(ccDarkhastFaktor);
        return bargashtyDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh();
    }

    /**
     * getTarikhSarResidForCheck in persian and gregorian format
     *
     * @param darkhastFaktorModel
     * @param codeNoeVosol
     * @return String[0] = tarikhSarResidShamsi , String[1] = tarikhSarResidGregorian
     */
    private String[] getTarikhSarResidForCheck(DarkhastFaktorModel darkhastFaktorModel, int codeNoeVosol, double mablaghMandehFaktor) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());
        Date tarikhSarResidCheck = null;
        Date tarikhErsal = null;
        String strTarikhSarResidCheck = "";
        String tarikhSarResidShamsiCheck = null;
        int valueVajhNaghd = Integer.parseInt(Constants.VALUE_VAJH_NAGHD());
        if (codeNoeVosol != valueVajhNaghd) {
            try {
                tarikhErsal = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(darkhastFaktorModel.getTarikhErsal());
            } catch (Exception exception) {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "getTarikhSarResidForCheck", "");
            }
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCs = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
            //todo ModatVosol Check chera -1
            tarikhSarResidCheck = dariaftPardakhtDarkhastFaktorPPCDAO.getTarikhSarResidShamsiCheck(dariaftPardakhtDarkhastFaktorPPCs, darkhastFaktorModel.getModateVosol(), (long) darkhastFaktorModel.getMablaghKhalesFaktor(), tarikhErsal, mablaghMandehFaktor);
            tarikhSarResidShamsiCheck = dateUtils.gregorianWithSlashToPersianSlash(sdf.format(dateUtils.addDay(tarikhSarResidCheck, -1)));
        } else {
            tarikhSarResidShamsiCheck = dateUtils.gregorianWithSlashToPersianSlash(sdf.format(new Date()));
        }

        strTarikhSarResidCheck = dateUtils.persianWithSlashToGregorianSlash(tarikhSarResidShamsiCheck);
        return new String[]{tarikhSarResidShamsiCheck, strTarikhSarResidCheck};
    }

    /*private int getTedadRoozForRotbeh(int darajeh , int codeNoeVosolAzMoshtary , int ccGorohMoshtary)
    {
        final int Namayandeh1 = 351, Namayandeh2 = 352, Khordeh = 347, Omdeh=348, Zanjirei = 350, TaavoniVijeh = 349;
        int tedadRooz = 0;
        if(codeNoeVosolAzMoshtary <= 3)
        {
            if(darajeh == 0)
            {
                if(ccGorohMoshtary == Khordeh || ccGorohMoshtary == Omdeh)
                {
                    tedadRooz = 2;
                }
                else if(ccGorohMoshtary == Namayandeh1 )
                {
                    tedadRooz = 5;
                }
                else if(ccGorohMoshtary == Namayandeh2 )
                {
                    tedadRooz = 3;
                }
            }
            if(darajeh == 1)
            {
                if(ccGorohMoshtary == Khordeh || ccGorohMoshtary == Omdeh)
                    tedadRooz = 1;
                if(ccGorohMoshtary == Namayandeh1 )
                    tedadRooz = 3;
                if(ccGorohMoshtary == Namayandeh2 )
                    tedadRooz = 2;
            }
            if(darajeh == 2)
            {
                if(ccGorohMoshtary == Namayandeh1 )
                    tedadRooz = 2;
                if(ccGorohMoshtary == Namayandeh2 )
                    tedadRooz = 1;
            }
        }
        tedadRooz = 0;
        return tedadRooz;
    }*/




    @Override
    public void removeItem(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel, int position, int from , long ccDarkhastFaktor) {
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());

        boolean biggerCcDaryaftPardakht = biggerCcDaryaftPardakht(dariaftPardakhtDarkhastFaktorPPCModel, ccDarkhastFaktor);

        if (from == Constants.FROM_CHECK_BARGASHTI) {


                boolean deleteResult = false;

                //---------------------------------------------------------------
                deleteResult = dariaftPardakhtPPCDAO.deleteByccDariaftPardakht_checkBargashty(dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_ccDaryaftPardakhtCheckBargashty());
                //-----------------Update_Check bargashty------------------------------------------
                if (deleteResult) {
                    Long ccDpDf_Extra = dariaftPardakhtDarkhastFaktorPPCModel.getExtraProp_ccDaryaftPardakhtCheckBargashty();
                    boolean deletedpdfPPC = dariaftPardakhtDarkhastFaktorPPCDAO.deleteByccDariaftPardakht(dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakht());
                    long mablaghMandeh = (long) setMablaghMandehOtherVosol(dariaftPardakhtDarkhastFaktorPPCModel.getCcDarkhastFaktor());
                    if (deletedpdfPPC) {
                       if (!biggerCcDaryaftPardakht || mablaghMandeh > 0){
                           dariaftPardakhtDarkhastFaktorPPCDAO.deleteDirKardAndTajil(0, ccDpDf_Extra);
                       }
                        mablaghMandeh = (long) setMablaghMandehOtherVosol(dariaftPardakhtDarkhastFaktorPPCModel.getCcDarkhastFaktor());

                        if (mablaghMandeh == 0){
                          dariaftPardakhtDarkhastFaktorPPCDAO.deleteDirKardAndTajil(0, ccDpDf_Extra);
                      }

                        mPresenter.onSuccessRemoveItem(position, mablaghMandeh);
                    } else {
                        mPresenter.onFailedRemoveItem();
                    }
                } else {
                    mPresenter.onFailedRemoveItem();
                }



        } else if (from == Constants.FROM_TREASURYLIST || from == -1) {
            boolean deleteResult = false;
            //---------------------------------------------------------------
            deleteResult = dariaftPardakhtPPCDAO.deleteByccDariaftPardakht(dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakht());
            //-----------------Update_Resid------------------------------------------
            if (deleteResult) {
                if (dariaftPardakhtDarkhastFaktorPPCModel.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_RESID())) {

                    DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                    darkhastFaktorDAO.updateResid(dariaftPardakhtDarkhastFaktorPPCModel.getCcDarkhastFaktor(), 0);
                }
                ccDarkhastFaktor = dariaftPardakhtDarkhastFaktorPPCModel.getCcDarkhastFaktor();
                boolean deleteDpdfPPC = dariaftPardakhtDarkhastFaktorPPCDAO.deleteByccDariaftPardakht(dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakht());
                long mablaghMandeh = setMablaghMandehFaktor(ccDarkhastFaktor);
                if (deleteDpdfPPC) {
                   if (!biggerCcDaryaftPardakht || mablaghMandeh > 0){
                       dariaftPardakhtDarkhastFaktorPPCDAO.deleteDirKardAndTajil(ccDarkhastFaktor, 0);
                   }


                     mPresenter.onSuccessRemoveItem(position, mablaghMandeh);
                } else {
                    mPresenter.onFailedRemoveItem();
                }
            } else {
                mPresenter.onFailedRemoveItem();
            }
        }

    }

    /**
     * this 'for' check ccDaryaftPardakht for delete item vosol
     *  when ccDaryaftPardakht item selected bigger another ccDaryaftPardakht vosols , we can not delete "DirKard"
     */
    private boolean biggerCcDaryaftPardakht(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel,long ccDarkhastFaktor){
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

        boolean biggerCcDaryaftPardakht = false;
        for (int i = 0; i < dariaftPardakhtDarkhastFaktorPPCModels.size(); i++) {
            if (dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakht() > dariaftPardakhtDarkhastFaktorPPCModels.get(i).getCcDariaftPardakht() ){
                biggerCcDaryaftPardakht = true;
            }

        }

        return biggerCcDaryaftPardakht;
    }

    /**
     * remove item pish daryaft
     *
     * @param dariaftPardakhtPPCModel
     * @param position
     * @param from
     */
    @Override
    public void removeItemPishDaryaft(DariaftPardakhtPPCModel dariaftPardakhtPPCModel, int position, int from) {
        boolean deleteResult = false;
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        //---------------------------------------------------------------
        deleteResult = dariaftPardakhtPPCDAO.deleteByIsPishDaryaft(dariaftPardakhtPPCModel.getCcDariaftPardakht());
        //-----------------Update_Resid------------------------------------------
        if (deleteResult) {
            mPresenter.onSuccessRemoveItem(position, 0);
        } else {
            mPresenter.onFailedRemoveItem();
        }
    }

    /**
     * call tajil for first
     *
     * @param codeNoeVosol
     */
    @Override
    public void callTajil(long ccDarkhastfaktor, int codeNoeVosol) {
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastfaktor);
        if (darkhastFaktorModel.getIsTajil() == 1)
            setTajil(darkhastFaktorModel, codeNoeVosol);
    }

    @Override
    public void checkRegisteredVosol(int position) {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor(), -1);
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        int countVosols = dariaftPardakhtDarkhastFaktorPPCDAO.getCountByccDarkhastFaktor(ccDarkhastFaktor);
        String codeNoeVosolMoshtaryVajhNaghd = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD());
        String codeNoeVosolMoshtaryCheck = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_CHECK());
        long mablaghMandehFaktor = setMablaghMandehFaktor(ccDarkhastFaktor);
        boolean isMojazForResid = selectFaktorShared.getBoolean(selectFaktorShared.getIsMojazForResid(), false);
        if ((darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Integer.parseInt(codeNoeVosolMoshtaryVajhNaghd) ||
                (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Integer.parseInt(codeNoeVosolMoshtaryCheck)))
                && mablaghMandehFaktor > 0) {
            mPresenter.onError(R.string.errorRemainBiggerThanZeroForNagh);
        } else if (!isMojazForResid && mablaghMandehFaktor > 0) {
            mPresenter.onError(R.string.errorInvoiceSettlementForClosedCredit);
        } else if (countVosols == 0) {
            mPresenter.onError(R.string.errorEmptyVosols);
        } else {
            mPresenter.onSuccessCheckRegisteredVosol(position);
        }
    }

    @Override
    public void getBank(String codeBank) {
        BankDAO bankDAO = new BankDAO(mPresenter.getAppContext());
        BankModel bankModel = bankDAO.getByCodeBank(codeBank);
        mPresenter.onGetBank(bankModel);
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {

    }

    //////////////////////////////// Takhir /////////////////////////////////

    /**
     * get check bargashti by shomareh sanad for show details
     */
    @Override
    public void getDetailsCheckBargashti(String shomarehSanad, int ccDarajeh, int ccNoeMoshtary) {
        BargashtyDAO bargashtyDAO = new BargashtyDAO(BaseApplication.getContext());
        ccDarajehModel = ccDarajeh;
        ccNoeMoshtaryModel = ccNoeMoshtary;
        ArrayList<BargashtyModel> bargashtyModels = bargashtyDAO.getBargashtyByShomarehSanad(shomarehSanad);
        shomarehSanadCheckBargashty = shomarehSanad;
        mPresenter.onGetDetailsCheckBargashti(bargashtyModels);

    }

    /**
     * Have dirkard vosol
     *
     * @return
     */
    private boolean haveDirkardVosol(DarkhastFaktorModel darkhastFaktorModel, BargashtyModel bargashtyModel) {
        boolean flag = false;
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtBargashtyPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
        if (darkhastFaktorModel != null) {
            if(darkhastFaktorModel.getIsTakhir()==1)
                flag = dariaftPardakhtBargashtyPPCDAO.HaveDirkardDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        } else {
            flag = dariaftPardakhtBargashtyPPCDAO.HaveDirkardBargahsty(bargashtyModel.getCcDariaftPardakht());
        }
        return flag;
    }

    /**
     * dirkard vosol
     */
    private void insertDirkardVosol(DarkhastFaktorModel darkhastFaktorModel, BargashtyModel bargashtyModel) {
        long mablaghTakhsis;
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels;
        DariaftPardakhtPPCModel dariaftPardakhtPPCModel = new DariaftPardakhtPPCModel();

        int codeNoeVosolD = Integer.valueOf(Constants.VALUE_SANAD_DIRKARD);
        String nameNoeVosol = "دیرکرد وصول";
        //------------------------------ MablaghTakhsis -----------------
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());

        if (darkhastFaktorModel != null) {
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
            String nameMoshtary = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).getNameMoshtary();
            dariaftPardakhtDarkhastFaktorPPCModels = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());

            mablaghTakhsis = mohasebehTakhir(dariaftPardakhtDarkhastFaktorPPCModels, darkhastFaktorModel.getTarikhErsal(), darkhastFaktorModel.getModateVosol());
            if (mablaghTakhsis != 0) {
                int isDirkard = 1;
                dariaftPardakhtPPCModel.setMablagh(mablaghTakhsis);
                dariaftPardakhtPPCModel.setCodeShobehSanad("");
                dariaftPardakhtPPCModel.setShomarehHesabSanad("");
                dariaftPardakhtPPCModel.setShomarehSanad("");
                dariaftPardakhtPPCModel.setNameShobehSanad("");
                dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);
                dariaftPardakhtPPCModel.setExtraProp_IsDirkard(isDirkard);

                long ccDariaftPardakht = insertDariaftPardakht(darkhastFaktorModel.getCcMoshtary(), darkhastFaktorModel.getCcDarkhastFaktor(), codeNoeVosolD, String.valueOf(darkhastFaktorModel.getMablaghMandeh()), nameNoeVosol, dariaftPardakhtPPCModel, 1, 0, 0, Constants.FROM_TREASURYLIST);
                dariaftPardakhtPPCModel = dariaftPardakhtPPCDAO.getByccDariaftPardakht(ccDariaftPardakht);
                if (ccDariaftPardakht > 0)
                    insertDariaftPardakhtDarkhastFaktor(ccDariaftPardakht, dariaftPardakhtPPCModel, String.valueOf(codeNoeVosolD), 0, 0, 0);

            }
        } else {

            dariaftPardakhtDarkhastFaktorPPCModels = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDariaftPardakht_CheckBargashty(bargashtyModel.getCcDariaftPardakht());
            long mablaghTakhsisCheckBarghasty = mohasebehTakhirCheckBarghasty(dariaftPardakhtDarkhastFaktorPPCModels, bargashtyModel.getTarikhSanad());

//            mablaghTakhsis = mohasebehTakhir(dariaftPardakhtDarkhastFaktorPPCModels, bargashtyModel.getTarikhSanad(), 0);
            if (mablaghTakhsisCheckBarghasty != 0) {
                int isDirkard = 1;
                dariaftPardakhtPPCModel.setMablagh(mablaghTakhsisCheckBarghasty);
                dariaftPardakhtPPCModel.setNameShobehSanad("");
                dariaftPardakhtPPCModel.setCodeShobehSanad("");
                dariaftPardakhtPPCModel.setShomarehHesabSanad("");
                dariaftPardakhtPPCModel.setShomarehSanad("");
                dariaftPardakhtPPCModel.setNameSahebHesab(bargashtyModel.getNameMoshtary());
                dariaftPardakhtPPCModel.setExtraProp_IsDirkard(isDirkard);
                long ccDariaftPardakht = insertDariaftPardakht(bargashtyModel.getCcMoshtary(), bargashtyModel.getCcDariaftPardakht(), codeNoeVosolD, String.valueOf(bargashtyModel.getMablaghMandeh()), nameNoeVosol, dariaftPardakhtPPCModel, 1, 0, bargashtyModel.getCcDariaftPardakht(), Constants.FROM_CHECK_BARGASHTI);
                dariaftPardakhtPPCModel = dariaftPardakhtPPCDAO.getByccDariaftPardakht(ccDariaftPardakht);
                if (ccDariaftPardakht > 0)
                    insertDariaftPardakhtDarkhastFaktor(ccDariaftPardakht, dariaftPardakhtPPCModel, String.valueOf(codeNoeVosolD), bargashtyModel.getCcDariaftPardakht(), isDirkard, 0);

            }
        }


    }

    /**
     * modat takhir
     *
     * @param dariaftPardakhtDarkhastFaktorPPCModels
     * @param tarikh
     * @param modatVosol
     * @return
     */
    public long mohasebehTakhir(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels, String tarikh, int modatVosol) {
        int codeNoeVosol = 0;
        int ConvertToDay = (24 * 60 * 60 * 1000);
        float Sum_Mablagh_diffDay = 0;
        float Sum_MablaghTakhsisyFaktor = 0;
        long diff_days = 0;
        long MablaghDirkard_Nahaee = 0;
        double modatRoozRaasGiri = 0;
        int modatDirkard = 0;
        Date tarikhDate = null;
        //-------------------
//        SystemConfigDAO systemConfigDAO = new SystemConfigDAO(context);
//        double Zarib_DirkardVosol = systemConfigDAO.GetAll().get(0).getZarib_DirkardVosol();
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(BaseApplication.getContext());
        double zaribDirkardVosol = Double.parseDouble(parameterChildDAO.getValueByccChildParameter(159));

        ;
//        double Zarib_DirkardVosol = 1;
        //---------ModatRoozRaasGiri...
        for (DariaftPardakhtDarkhastFaktorPPCModel entity : dariaftPardakhtDarkhastFaktorPPCModels) {


            Date ZamaneTakhsiseFaktor = null;
            Date tarikhSanad = null;
            try {
                if (entity.getZamaneTakhsiseFaktor() != null) {
                    ZamaneTakhsiseFaktor = simpleDateFormatShort.parse(entity.getZamaneTakhsiseFaktor());
                }
                if (entity.getTarikhSanad() != null) {
                    tarikhSanad = simpleDateFormatShort.parse(entity.getTarikhSanad());
                }
            } catch (Exception e) {
                e.getMessage();
            }

            try {
                tarikhDate = simpleDateFormatShort.parse(tarikh);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            diff_days = 0;
            codeNoeVosol = entity.getCodeNoeVosol();
            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_VAJH_NAGHD()) | codeNoeVosol == Integer.parseInt(Constants.VALUE_IRANCHECK()))
                diff_days = (ZamaneTakhsiseFaktor.getTime() - tarikhDate.getTime()) / ConvertToDay;
            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_SANAD_POS()) | codeNoeVosol == Integer.parseInt(Constants.VALUE_FISH_BANKI()))
                diff_days = (tarikhSanad.getTime() - tarikhDate.getTime()) / ConvertToDay;

            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_CHECK()) | codeNoeVosol == Integer.parseInt(Constants.VALUE_CHECK_BANKI)) {
                Date Max_Rooz_TarikhSanad;
                if (ZamaneTakhsiseFaktor.getTime() > tarikhSanad.getTime())
                    Max_Rooz_TarikhSanad = ZamaneTakhsiseFaktor;
                else
                    Max_Rooz_TarikhSanad = tarikhSanad;

                diff_days = (Max_Rooz_TarikhSanad.getTime() - tarikhDate.getTime()) / ConvertToDay;
            }

//            for (int i = 0; i <dariaftPardakhtDarkhastFaktorPPCModels.size() ; i++) {
//                if (dariaftPardakhtDarkhastFaktorPPCModels.get(i).getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_SANAD_DIRKARD)){
//                    try {
//                        Date zamane = simpleDateFormatShort.parse(entity.getZamaneTakhsiseFaktor());
//                        diff_days = (zamane.getTime() - tarikhDate.getTime()) / ConvertToDay;
//                        break;
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }

            if (diff_days <= 0)
                diff_days = 1;

            if (diff_days > 0) {
                Sum_Mablagh_diffDay += diff_days * ((int) entity.getMablagh());
                Sum_MablaghTakhsisyFaktor += entity.getMablagh();
            }
        }
        if (Sum_MablaghTakhsisyFaktor > 0)
            modatRoozRaasGiri = Math.round(Sum_Mablagh_diffDay / Sum_MablaghTakhsisyFaktor);
        //------------------------------------------------
        modatVosol += 1;
        modatDirkard = (int) modatRoozRaasGiri - modatVosol;
        MablaghDirkard_Nahaee = -1 * Math.round((Sum_MablaghTakhsisyFaktor * modatDirkard * zaribDirkardVosol));
        return MablaghDirkard_Nahaee;
    }


    ////////////////////////////// Takhir Check Bargashty ////////////////////////////

    public long mohasebehTakhirCheckBarghasty(ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCs, String tarikhSarResidCheck ) {
        int codeNoeVosol = 0;
        int convertToDay = (24 * 60 * 60 * 1000);
        float sumMablaghdiffDay = 0;
        float sumMablaghTakhsisyFaktor = 0;
        long diffDays = 0;
        long mablaghDirkardNahaee = 0;
        double modatRoozRaasGiri = 0;
        //-------------------

        double zaribDirkardVosol = Double.parseDouble(parameterChildDAO.getValueByccChildParameter(159));

        //---------ModatRoozRaasGiri...
        for (DariaftPardakhtDarkhastFaktorPPCModel entity : dariaftPardakhtDarkhastFaktorPPCs) {
            Date zamaneTakhsiseFaktor = null;
            Date tarikhSanad = null;
            Date tarikhSarResidCheckBargashty = null;
            if (entity.getZamaneTakhsiseFaktor() != null)
                try {
                    zamaneTakhsiseFaktor = simpleDateFormatShort.parse(entity.getZamaneTakhsiseFaktor());

                    if (entity.getTarikhSanad() != null)
                        tarikhSanad = simpleDateFormatShort.parse(entity.getZamaneTakhsiseFaktor());


                    tarikhSarResidCheckBargashty = simpleDateFormatShort.parse(tarikhSarResidCheck);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            diffDays = 0;
            codeNoeVosol = entity.getCodeNoeVosol();
            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_VAJH_NAGHD()) || codeNoeVosol == Integer.parseInt(Constants.VALUE_IRANCHECK()))
                diffDays = (zamaneTakhsiseFaktor.getTime() - tarikhSarResidCheckBargashty.getTime()) / convertToDay;
            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_POS()) | codeNoeVosol == Integer.parseInt(Constants.VALUE_FISH_BANKI()))
                diffDays = (tarikhSanad.getTime() - tarikhSarResidCheckBargashty.getTime()) / convertToDay;

            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_CHECK()) | codeNoeVosol ==Integer.parseInt(Constants.VALUE_CHECK_BANKI)) {
                Date maxRoozTarikhSanad;
                if (zamaneTakhsiseFaktor.getTime() > tarikhSanad.getTime())
                    maxRoozTarikhSanad = zamaneTakhsiseFaktor;
                else
                    maxRoozTarikhSanad = tarikhSanad;

                diffDays = (maxRoozTarikhSanad.getTime() - tarikhSarResidCheckBargashty.getTime()) / convertToDay;
            }

            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dpdf = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(entity.getCcDarkhastFaktor());

            for (int i = 0; i <dpdf.size() ; i++) {
                if (dpdf.get(i).getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_SANAD_DIRKARD)){
                    try {
                        Date zamane = simpleDateFormatShort.parse(entity.getZamaneTakhsiseFaktor());
                        diffDays = (zamane.getTime() - dateUtils.getCurrentDate().getTime()) / convertToDay;
                        break;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }

//            if (diffDays <= 0)
//                diffDays = 1;

            if (diffDays > 0) {
                sumMablaghdiffDay += diffDays * ((int) entity.getMablagh());
                sumMablaghTakhsisyFaktor += entity.getMablagh();
            }
        }
        if (sumMablaghTakhsisyFaktor > 0)
            modatRoozRaasGiri = Math.round(sumMablaghdiffDay / sumMablaghTakhsisyFaktor);
        //------------------------------------------------

        mablaghDirkardNahaee = -1 * Math.round((sumMablaghTakhsisyFaktor * modatRoozRaasGiri * zaribDirkardVosol));
        return mablaghDirkardNahaee;
    }


    ///////////////////////////// Tajil ///////////////////////////////////////

    public void setTajil(DarkhastFaktorModel darkhastFaktorModel, int codeNoeVosol) {


        boolean flag_IsYeksan_dp_SabtShodeh = true;
        boolean flag_HaveTajil = false;
        int TedadRoozTatil = 0;
        long TedadRoozSabtVosol = 0;
        boolean Is_TarikhRooz = false;

        DariaftPardakhtPPCDAO dpDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
        DariaftPardakhtDarkhastFaktorPPCDAO dpdfDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
        //ArrayList<DariaftPardakhtPPCModel> dps = dpDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dps = dpdfDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());

        // set All Time
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());


        Date tarikhRoozDate = null;
        String tarikhRoozShamsiString = "";
        try {
            String DateNow = simpleDateFormatShort.format(new Date());
            tarikhRoozShamsiString = dateUtils.todayDateWithoutSlash((BaseApplication.getContext()));
            tarikhRoozDate = simpleDateFormatShort.parse(getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), DateNow));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Date tarikhSarResidMiladyDate = new Date();
        String tarikhSarResidMiladyShamsiString = dateUtils.gregorianToPersianDateTime(tarikhSarResidMiladyDate);


        //.... Is All Of Them Insert Today........
//        boolean Is_TarikhRooz = true;
//        Date tarikhRoozDate = PubFuncs.GetDateWithoutTime(MainActivity.dateFromServer);
//        tarikhRoozShamsiString = PubFuncs.GetShamsiDateWithOutSlash(MainActivity.dateFromServer);


        if (tarikhSarResidMiladyDate != null) //date
        {
//            TarikhSarResidMilady = PubFuncs.GetDateWithoutTime(TarikhSarResidMilady);
            try {
                tarikhSarResidMiladyShamsiString = dateUtils.getDateWithoutSlashShamsi(BaseApplication.getContext(), tarikhSarResidMiladyDate);
                tarikhSarResidMiladyDate = sdf.parse(sdf.format(tarikhSarResidMiladyDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        Date zamaneSabt = null;
        String taTarikh = null;
        String azTarikh = null;
        TaghvimTatilDAO taghvimTatilDAO = new TaghvimTatilDAO(BaseApplication.getContext());
        ArrayList<TaghvimTatilModel> lst_taghvimTatil = new ArrayList<>();
        for (DariaftPardakhtDarkhastFaktorPPCModel entity : dps) {
            zamaneSabt = null;
            if (entity.getCodeNoeVosol() != Integer.parseInt(Constants.VALUE_CHECK())) {
//                if (dateUtils.GetDateWithoutTime(entity.getZamaneSabt()).getDay() != tarikhRoozDate.getDay() |
//                        dateUtils.GetDateWithoutTime(entity.getZamaneSabt()).getMonth() != tarikhRoozDate.getMonth())

                try {
                    Date date = null;
                    if (darkhastFaktorModel.getTarikhFaktor() != null) {
                        date = simpleDateFormatLong.parse(darkhastFaktorModel.getTarikhFaktor());
                        azTarikh = simpleDateFormatShort.format(date);
                        Date taTarikhDate = simpleDateFormatDATE_TIME_WITH_SPACE.parse(getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), ""));
                        taTarikh = simpleDateFormatDATE_TIME_WITH_SPACE.format(taTarikhDate);//Date Now
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                lst_taghvimTatil = taghvimTatilDAO.getTarikhTatilBetweenTwoDates(azTarikh, taTarikh);


                try {
                    if (entity.getZamaneTakhsiseFaktor() != null) {
                        if (!entity.getZamaneTakhsiseFaktor().equals("")) {
                            zamaneSabt = simpleDateFormatShort.parse(entity.getZamaneTakhsiseFaktor());
                        } else {
                            zamaneSabt = tarikhRoozDate;
                        }

                    } else {
                        zamaneSabt = tarikhRoozDate;
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diffTime = 0;
                if (zamaneSabt != null && tarikhRoozDate != null)
                    diffTime = DateUtils.getDateDiffAsDay(zamaneSabt, tarikhRoozDate);

                if (diffTime != 0)
                    Is_TarikhRooz = false;
            }

            if (entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_MARJOEE())) {
                try {
                    //Date date = simpleDateFormatLong.parse(darkhastFaktorModel.getTarikhDarkhast());
                    Date date = simpleDateFormatLong.parse(darkhastFaktorModel.getTarikhFaktor());
                    azTarikh = simpleDateFormatShort.format(date);
//                    taTarikh = simpleDateFormatShort.format(getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_SERVER(), simpleDateFormatLong.format(new Date())));//Date Now
                    taTarikh = getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_SERVER(), simpleDateFormatLong.format(new Date()));//Date Now
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                lst_taghvimTatil = taghvimTatilDAO.getTarikhTatilBetweenTwoDates(azTarikh, taTarikh);

                TedadRoozTatil = lst_taghvimTatil.size();
                TedadRoozSabtVosol = DateUtils.getDateDiffAsDay(zamaneSabt, tarikhRoozDate);
                if (TedadRoozSabtVosol - TedadRoozTatil == 1)
                    Is_TarikhRooz = true;
            }

            if (entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_CHECK())) {

                try {
                    if (!(entity.getTarikhSanad().equals("") | entity.getTarikhSanad() == null)) {
                        zamaneSabt = simpleDateFormatDATE_TIME_FORMAT.parse(entity.getTarikhSanad());
                    } else {
                        zamaneSabt = tarikhRoozDate;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diffTime = DateUtils.getDateDiffAsDay(zamaneSabt, tarikhRoozDate);
//todo
                if (diffTime != 0)
                    Is_TarikhRooz = false;
            }
        }

        if (codeNoeVosol == Integer.parseInt(Constants.VALUE_CHECK())) //dropdown
        {
            //if( tarikhSarResidMiladyShamsiString.trim() != tarikhRoozShamsiString.trim())
            if (!(tarikhSarResidMiladyShamsiString.contains(tarikhRoozShamsiString))) {
                Is_TarikhRooz = false;
            }
        }
        flag_HaveTajil = dpdfDAO.HaveTajil(darkhastFaktorModel.getCcDarkhastFaktor());
        //------------------------------ بررسی اینکه آیا همه وصولیهای ثبت شده یکسان هستند یا نه -----------------
        int Have_Naghd_SabtShodeh = 0;
        int Have_Check_SabtShodeh = 0;

        for (DariaftPardakhtDarkhastFaktorPPCModel entity : dps) {
            if (entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_VAJH_NAGHD()) || entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_POS()) ||
                    entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_FISH_BANKI()) || entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_MARJOEE()) ||
                    entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_IRANCHECK()))
                Have_Naghd_SabtShodeh = 1;
            if (entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_CHECK()))
                Have_Check_SabtShodeh = 1;
        }
        if (Have_Check_SabtShodeh == 1 & Have_Naghd_SabtShodeh == 1)
            flag_IsYeksan_dp_SabtShodeh = false;
        //----------------------------------------------------------------
        if (codeNoeVosol != 0) {
            //Naghd......
            if ((codeNoeVosol == Integer.parseInt(Constants.VALUE_VAJH_NAGHD()) || codeNoeVosol == Integer.parseInt(Constants.VALUE_POS()) ||
                    codeNoeVosol == Integer.parseInt(Constants.VALUE_FISH_BANKI()) || codeNoeVosol == Integer.parseInt(Constants.VALUE_POS()) ||
                    codeNoeVosol == Integer.parseInt(Constants.VALUE_IRANCHECK())) && Have_Check_SabtShodeh == 0) {
                MablaghTajil_Nahaee = Get_MablaghTajil(darkhastFaktorModel.getCcDarkhastFaktor(), 1, darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
                flag_IsYeksan_dp_SabtShodeh = true;
            }

            if ((codeNoeVosol == Integer.parseInt(Constants.VALUE_VAJH_NAGHD()) || codeNoeVosol == Integer.parseInt(Constants.VALUE_POS()) ||
                    codeNoeVosol == Integer.parseInt(Constants.VALUE_FISH_BANKI()) || codeNoeVosol == Integer.parseInt(Constants.VALUE_POS())) &&
                    Have_Check_SabtShodeh == 1) {
//                      if( (codeNoeVosol == CodeNoeSanad.Pos.getValue() |
//                            codeNoeVosol == CodeNoeSanad.FishBanki.getValue() | codeNoeVosol == CodeNoeSanad.Pos.getValue() )   &
//                                  Have_Check_SabtShodeh == 1 )
//                      {
                MablaghTajil_Nahaee = 0;
                MablaghPasAzKasrTajil = 0;
                flag_IsYeksan_dp_SabtShodeh = false;
            }

            //Check......
            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_CHECK()) & Have_Naghd_SabtShodeh == 0) {
                if (tarikhSarResidMiladyDate.getDay() == tarikhRoozDate.getDay() | tarikhSarResidMiladyDate.getMonth() == tarikhRoozDate.getMonth()) {
                    MablaghTajil_Nahaee = Get_MablaghTajil(darkhastFaktorModel.getCcDarkhastFaktor(), 2, darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
                    flag_IsYeksan_dp_SabtShodeh = true;
                }
            }
            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_CHECK()) & Have_Naghd_SabtShodeh == 1) {
                MablaghTajil_Nahaee = 0;
                MablaghPasAzKasrTajil = 0;
                flag_IsYeksan_dp_SabtShodeh = false;
            }
        }
        long mandehFaktorPasAzTajil_Naghd = 0;
        long mandehFaktorPasAzTajil_Check = 0;
        long mablaghTajil_Naghd = 0;
        long mablaghTajil_Check = 0;
        //------------------------------------
        //if (Is_TarikhRooz & !flag_HaveTajil & flag_IsYeksan_dp_SabtShodeh & darkhastFaktorModel.getCodeNoeVosolAzMoshtary() != Integer.parseInt(Constants.VALUE_VAJH_NAGHD_YEK_SETARE())) {
        if (Is_TarikhRooz & !flag_HaveTajil & flag_IsYeksan_dp_SabtShodeh & darkhastFaktorModel.getIsTajil()==1) {
            long mablaghMandehFaktor = setMablaghMandehFaktor(darkhastFaktorModel.getCcDarkhastFaktor());


            mablaghTajil_Naghd = Get_MablaghTajil(darkhastFaktorModel.getCcDarkhastFaktor(), 1, darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
            mablaghTajil_Check = Get_MablaghTajil(darkhastFaktorModel.getCcDarkhastFaktor(), 2, darkhastFaktorModel.getCodeNoeVosolAzMoshtary());

            mandehFaktorPasAzTajil_Naghd = (mablaghMandehFaktor - mablaghTajil_Naghd) > 0 ? (mablaghMandehFaktor - mablaghTajil_Naghd) : 0;
            mandehFaktorPasAzTajil_Check = (mablaghMandehFaktor - mablaghTajil_Check) > 0 ? (mablaghMandehFaktor - mablaghTajil_Check) : 0;


            if (codeNoeVosol == Integer.parseInt(Constants.VALUE_CHECK()))
                MablaghTajil_Nahaee = mablaghTajil_Check;
            else
                MablaghTajil_Nahaee = mablaghTajil_Naghd;

            MablaghPasAzKasrTajil = ((mablaghMandehFaktor - MablaghTajil_Nahaee) > 0 ? (mablaghMandehFaktor - MablaghTajil_Nahaee) : 0);
            //-----------
            canGetTajil = true;
        } else {

            canGetTajil = false;
        }

        mPresenter.oncallTajil(mablaghTajil_Naghd, mandehFaktorPasAzTajil_Naghd, mablaghTajil_Check, mandehFaktorPasAzTajil_Check, canGetTajil);
    }

    /**
     * Insert Tajil
     */
    private void Insert_Tajil(int CodeNoeVosol_Sabt, DarkhastFaktorModel darkhastFaktorModel) {
        long mablaghTakhsis;
        long ccDariaftPardakht = 0;
        long ccDariaftPardakhtDarkhastFaktor = 0;
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(BaseApplication.getContext());
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels;
        DariaftPardakhtPPCModel dariaftPardakhtPPCModel = new DariaftPardakhtPPCModel();
        double Sum_MablaghDariaftPardakht = 0;
        boolean Tajil = false;
        DariaftPardakhtDarkhastFaktorPPCDAO dpdfDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dpdfs = dpdfDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());


        String DateNow = simpleDateFormatLong.format(new Date());
        Date TarikhRooz_Shamsi = null;
        Date tarikhRoozDate = null;
        try {
            tarikhRoozDate = simpleDateFormatDATE_TIME_WITH_SPACE.parse(getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), ""));
            TarikhRooz_Shamsi = simpleDateFormatDATE_TIME_WITH_SPACE.parse(getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), ""));

//            tarikhRoozDate = simpleDateFormatLong.parse(getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG() , DateNow));
//            TarikhRooz_Shamsi = simpleDateFormatLong.parse(getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG() , DateNow));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Date zamaneSabt = null;
        String taTarikh = null;
        String azTarikh = null;
        TaghvimTatilDAO taghvimTatilDAO = new TaghvimTatilDAO(BaseApplication.getContext());
        ArrayList<TaghvimTatilModel> lst_taghvimTatil = new ArrayList<>();
        for (DariaftPardakhtDarkhastFaktorPPCModel entity : dpdfs) {

            try {
                if (entity.getTarikhSanad() != null) {
                    if (!entity.getTarikhSanad().equals("")) {
                        zamaneSabt = simpleDateFormatDATE_TIME_FORMAT.parse(entity.getTarikhSanad());
                    }else {
                        zamaneSabt = tarikhRoozDate;
                    }
                }else {
                    zamaneSabt = tarikhRoozDate;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diffTime = DateUtils.getDateDiffAsDay(zamaneSabt, tarikhRoozDate);


            if (entity.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_CHECK()) && diffTime != 0)
                Tajil = false;
            else
                Tajil = true;
        }

        for (DariaftPardakhtDarkhastFaktorPPCModel entity : dpdfs)
            Sum_MablaghDariaftPardakht += entity.getMablagh();

        double MandehTajil = (MablaghPasAzKasrTajil - Sum_MablaghDariaftPardakht);
        if (MandehTajil < 1000 & MandehTajil > 0)
            Sum_MablaghDariaftPardakht += MandehTajil;

        if (Sum_MablaghDariaftPardakht >= MablaghPasAzKasrTajil && Tajil && CodeNoeVosol_Sabt != Integer.parseInt(Constants.VALUE_VAJH_NAGHD())) {
            int codeNoeVosolD = Integer.valueOf(Constants.VALUE_SANAD_TAJIL);
            String nameNoeVosol = "تعجیل وصول";
            int isTajil = 1;

            mablaghTakhsis = (long) MablaghTajil_Nahaee;


            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
            String nameMoshtary = moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).getNameMoshtary();
//                dariaftPardakhtDarkhastFaktorPPCModels = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
//                mablaghTakhsis = mohasebehTakhir(dariaftPardakhtDarkhastFaktorPPCModels, darkhastFaktorModel.getTarikhErsal(), darkhastFaktorModel.getModateVosol());
            if (mablaghTakhsis != 0) {
                dariaftPardakhtPPCModel.setMablagh(mablaghTakhsis);
                dariaftPardakhtPPCModel.setCodeShobehSanad("");
                dariaftPardakhtPPCModel.setShomarehHesabSanad("");
                dariaftPardakhtPPCModel.setShomarehSanad("");
                dariaftPardakhtPPCModel.setNameShobehSanad("");
                dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);
                dariaftPardakhtPPCModel.setExtraProp_IsDirkard(0);
                ccDariaftPardakht = insertDariaftPardakht(darkhastFaktorModel.getCcMoshtary(), darkhastFaktorModel.getCcDarkhastFaktor(), codeNoeVosolD, String.valueOf(darkhastFaktorModel.getMablaghMandeh()), nameNoeVosol, dariaftPardakhtPPCModel, 1, 0, 0, Constants.FROM_TREASURYLIST);
                dariaftPardakhtPPCModel = dariaftPardakhtPPCDAO.getByccDariaftPardakht(ccDariaftPardakht);
                if (ccDariaftPardakht > 0) {
                    ccDariaftPardakhtDarkhastFaktor = insertDariaftPardakhtDarkhastFaktor(ccDariaftPardakht, dariaftPardakhtPPCModel, String.valueOf(codeNoeVosolD), 0, 0, isTajil);
                }
            }


            Update_DariaftPardakhtDarkhastFaktorPPC_PasAzTajil(ccDariaftPardakhtDarkhastFaktor, darkhastFaktorModel);
        }
    }

    private void Update_DariaftPardakhtDarkhastFaktorPPC_PasAzTajil(long ccdpdf, DarkhastFaktorModel darkhastFaktorModel) {
        double Sum_MablaghDariaftPardakht = 0;
        DariaftPardakhtDarkhastFaktorPPCDAO dpdfDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(BaseApplication.getContext());
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dpdfs = dpdfDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        for (DariaftPardakhtDarkhastFaktorPPCModel entity : dpdfs)
            Sum_MablaghDariaftPardakht += entity.getMablagh();

        if (Sum_MablaghDariaftPardakht > darkhastFaktorModel.getMablaghKhalesFaktor())
            dpdfDAO.Update_MablaghDariaftPardakht(ccdpdf, (Sum_MablaghDariaftPardakht - darkhastFaktorModel.getMablaghKhalesFaktor()));


    }

    public long Get_MablaghTajil(long ccDarkhastFaktor, int CodeNoeVosol, int CodeNoeVosolAzMoshtary) {
        int ModatVosol = 0;
        double Zarib_SaghfTakhfif = 0;
        double MablaghFaktorByZarib_SaghfTakhfif;
        double Mablagh_Takhfif;
        double MablaghNahaee;
        //parameterchild
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(BaseApplication.getContext());
        double Zarib_Tajil = Double.parseDouble(parameterChildDAO.getValueByccChildParameter(167));
        String pattern = "yyyy-MM-dd";


        //DarkhastFaktor........
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
        DarkhastFaktorModel darkhastFaktor = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        if (CodeNoeVosolAzMoshtary != Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK())
            ModatVosol = darkhastFaktor.getModatRoozRaasGiri();
        else {
            if (CodeNoeVosol == 1)

                ModatVosol = darkhastFaktor.getModateVosol();
            if (CodeNoeVosol == 2)
                ModatVosol = darkhastFaktor.getModateVosol() - 2;//todo 2
        }

        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
        int ccNoeMoshtary = moshtaryDAO.getByccMoshtary(darkhastFaktor.getCcMoshtary()).getCcNoeMoshtary();
        //TODO :: set 99 for test pegah
        //  double MaxTakhfifNaghdiKhorde = Double.parseDouble(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_MIN_TAKHFIF_NAGHDI_KHORDE));
        //   double MaxTakhfifNaghdiGheirKhorde = Double.parseDouble(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_MIN_TAKHFIF_NAGHDI_GHEIRE_KHORDE));
        double MaxTakhfifNaghdiKhorde = 99;
        double MaxTakhfifNaghdiGheirKhorde = 99;
        if (ccNoeMoshtary == 347)
            Zarib_SaghfTakhfif = MaxTakhfifNaghdiKhorde;
        else if (ccNoeMoshtary != 347)
            Zarib_SaghfTakhfif = MaxTakhfifNaghdiGheirKhorde;

        MablaghFaktorByZarib_SaghfTakhfif = (darkhastFaktor.getMablaghKhalesFaktor() * Zarib_SaghfTakhfif) / 100;
        Mablagh_Takhfif = darkhastFaktor.getMablaghKhalesFaktor() * Zarib_Tajil * ModatVosol;

        if (Mablagh_Takhfif > MablaghFaktorByZarib_SaghfTakhfif)
            MablaghNahaee = MablaghFaktorByZarib_SaghfTakhfif;
        else
            MablaghNahaee = Mablagh_Takhfif;
        return Math.round(MablaghNahaee);
    }


}
