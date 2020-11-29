package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.InvoiceSettlementMVP;
import com.saphamrah.DAO.BankDAO;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MarkazShomarehHesabDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryShomarehHesabDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.PosShomarehHesabDAO;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MarkazShomarehHesabModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.PosShomarehHesabModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceSettlementModel implements InvoiceSettlementMVP.ModelOps
{

    private InvoiceSettlementMVP.RequiredPresenterOps mPresenter;

    public InvoiceSettlementModel(InvoiceSettlementMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

	@Override
    public void getConfig()
    {
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String showSayyadCheckScanner = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_SHOW_SAYYAD_CHECK_SCANNER);
        mPresenter.onGetConfig(showSayyadCheckScanner);
    }
	
    @Override
    public void getInfo(long ccDarkhastFaktor)
    {
        if(ccDarkhastFaktor == -1)
        {
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
            ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        }

        if (ccDarkhastFaktor != -1)
        {
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);

            String mablaghKhales = String.valueOf(darkhastFaktorModel.getMablaghKhalesFaktor());
            String shomarehDarkhast = String.valueOf(darkhastFaktorModel.getShomarehDarkhast());
            int ccNoeVosol = darkhastFaktorModel.getCodeNoeVosolAzMoshtary();
            String nameNoeVosol = darkhastFaktorModel.getNameNoeVosolAzMoshtary() == null ? "" : darkhastFaktorModel.getNameNoeVosolAzMoshtary();

            long mablaghMandeh = setMablaghMandehFaktor(ccDarkhastFaktor);

            mPresenter.onGetInfo(shomarehDarkhast, ccNoeVosol, nameNoeVosol, mablaghKhales, String.valueOf(mablaghMandeh));
        }
        else
        {
            mPresenter.onError(R.string.errorFindccDarkhastFaktor);
        }
    }

    @Override
    public void getVosols(long ccDarkhastFaktor)
    {
        //SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        Log.d("vosols" , "getCcDarkhastFaktor : " + ccDarkhastFaktor);
        Log.d("vosols" , "count : " + dariaftPardakhtDarkhastFaktorPPCModels.size());
        mPresenter.onGetVosols(dariaftPardakhtDarkhastFaktorPPCModels);
    }

    @Override
    public void getNoeVosols(long ccDarkhastFaktor , boolean getNoeVosolFromShared , boolean isFromTreasuryList)
    {
        //get type of settlement from shared and according to settlement type get from database

        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        String parents = "";

        /*if (getNoeVosolFromShared)
        {
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
            codeNoeVosolAzMoshtary = selectFaktorShared.getInt(selectFaktorShared.getnoe)
        }*/

        Log.d("invoice" , "ccDarkhastFaktor : " + ccDarkhastFaktor);
        Log.d("invoice" , "darkhastFaktorModel.getCodeNoeVosolAzMoshtary() : " + darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
        if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_RESID())
        {
            parents = childParameterDAO.getValueByccChildParameter(Constants.CC_VOSOL_RESID());
            if (isFromTreasuryList)
            {
                // حذف نوع وصول رسید برای فاکتورهای موجود در لیست وصول
                parents = parents.replace(String.valueOf(Constants.CC_CHILD_VOSOL_RESID()) , "");
                parents = parents.replace(",," , ",");
                parents = parents.startsWith(",") ? parents.substring(1) : parents;
                parents = parents.endsWith(",") ? parents.substring(0 , parents.length() - 1) : parents;
            }
        }
        else if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_CHECK())
        {
            parents = childParameterDAO.getValueByccChildParameter(Constants.CC_VOSOL_CHECK());
        }
        else if (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Constants.CODE_NOE_VOSOL_MOSHTARY_VAJH_NAGHD())
        {
            parents = childParameterDAO.getValueByccChildParameter(Constants.CC_VOSOL_NAGHD());
        }
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByParentsId(parents);
        mPresenter.onGetNoeVosols(childParameterModels);
    }

    @Override
    public void getPosShomareHesab(int codeNoevosol)
    {
        PosShomarehHesabDAO posShomarehHesabDAO = new PosShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<PosShomarehHesabModel> posShomarehHesabModels = posShomarehHesabDAO.getAll();
        /*for (PosShomarehHesabModel model : posShomarehHesabModels)
        {
            Log.d("pos" , "pos : " + model.toString());
        }*/
        mPresenter.onGetPosShomareHesab(posShomarehHesabModels , codeNoevosol);
    }

    @Override
    public void getFishBanki(int codeNoevosol)
    {
        MarkazShomarehHesabDAO markazShomarehHesabDAO = new MarkazShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<MarkazShomarehHesabModel> markazShomarehHesabModels = markazShomarehHesabDAO.getAll();
        mPresenter.onGetFishBanki(markazShomarehHesabModels , codeNoevosol);
    }

    @Override
    public void getMoshtaryHesab(int ccMoshtary , long ccDarkhastFaktor, int codeNoevosol , double mablaghMande)
    {
        MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = moshtaryShomarehHesabDAO.getAllByccMoshtary(ccMoshtary);

        BankDAO bankDAO = new BankDAO(mPresenter.getAppContext());
        ArrayList<BankModel> bankModels = bankDAO.getAll();

        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        String[] tarikhSarResidForCheck = getTarikhSarResidForCheck(darkhastFaktorModel , codeNoevosol , mablaghMande);

        mPresenter.onGetMoshtaryHesab(moshtaryShomarehHesabModels , bankModels , codeNoevosol , tarikhSarResidForCheck[0] , tarikhSarResidForCheck[1]);
    }

    @Override
    public void getNoeIranCheck(int codeNoevosol)
    {
        ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(Constants.CC_IRAN_CHECK());
        mPresenter.onGetNoeIranCheck(childParameterModels , codeNoevosol);
    }

    @Override
    public void checkInsert(int ccMoshtary , long ccDarkhastFaktor , int codeNoeVosolMoshtary , int flagInputHesab , String mablaghMandeh, String nameNoevosol, DariaftPardakhtPPCModel dariaftPardakhtPPCModel)
    {
        boolean hasError = false;
        int valueVajhNaghd = Integer.parseInt(Constants.VALUE_VAJH_NAGHD());
        if (ccDarkhastFaktor == -1)
        {
            mPresenter.onErrorCheckInsert(R.string.errorFindccDarkhastFaktor);
            hasError = true;
            return;
        }
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        if (darkhastFaktorModel.getCcDarkhastFaktor() == 0)
        {
            mPresenter.onErrorCheckInsert(R.string.errorFindDarkhastFaktor);
            hasError = true;
            return;
        }
        String strCodeNoeVosol = String.valueOf(codeNoeVosolMoshtary);
        if(strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_CHECK()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()))
        {
            if (dariaftPardakhtPPCModel.getShomarehSanad().length() == 0)
            {
                mPresenter.onErrorCheckInsert(R.string.errorInputNumber);
                return;
            }
        }

        try
        {
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
            String nameMoshtary = moshtaryDAO.getByccMoshtary(ccMoshtary).getNameMoshtary();
            Log.d("invoice" , "nameMoshtary from moshtary dao : " + nameMoshtary);
            if (nameMoshtary == null || nameMoshtary.trim().equals(""))
            {
                mPresenter.onErrorCheckInsert(R.string.errorNameSahebSanad);
                return;
            }
            dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);

            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());
            int tedadRoozForRotbeh = 0;// = Get_TedadRoozForRotbeh(); remove this method because return 0
            Log.d("settlement" , "darkhastFaktorModel.getCodeNoeVosolAzMoshtary() : " + darkhastFaktorModel.getCodeNoeVosolAzMoshtary());
            Log.d("settlement" , "valueVajhNaghd : " + valueVajhNaghd);
            if(strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) /*|| darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghd*/)
            {
                Date tarikhRooz = sdf.parse(sdf.format(new Date()));
                Date tarikhSanad = sdf.parse(new PubFunc().new DateUtils().persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi()));
                Date threeDaysLaterDate = new PubFunc().new DateUtils().addDay(tarikhRooz, -3);
                Date countDaysForRateLaterDate = new PubFunc().new DateUtils().addDay(tarikhRooz, +tedadRoozForRotbeh);

                if (tarikhSanad.getTime() > tarikhRooz.getTime() && darkhastFaktorModel.getCodeNoeVosolAzMoshtary() != valueVajhNaghd)
                {
                    mPresenter.onErrorCheckInsert(R.string.errorBiggerDate);
                    hasError = true;
                    return;
                }
                if (tarikhSanad.getTime() > countDaysForRateLaterDate.getTime() && darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghd)
                {
                    mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
                    hasError = true;
                    return;
                }
                if (tarikhSanad.getTime() < threeDaysLaterDate.getTime())
                {
                    mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
                    hasError = true;
                    return;
                }
            }

            if (strCodeNoeVosol.equals(Constants.VALUE_POS()) || strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()))
            {
                DariaftPardakhtDarkhastFaktorPPCDAO dpdfDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
                if (dariaftPardakhtPPCModel.getShomarehSanad().length() > 0)
                {
                    if (dpdfDAO.getCountShomarehSanad(dariaftPardakhtPPCModel.getShomarehSanad() , dariaftPardakhtPPCModel.getMablagh()) > 0)
                    {
                        mPresenter.onErrorCheckInsert(R.string.errorDuplicateNumber);
                        hasError = true;
                    }
                }
            }

            if(strCodeNoeVosol.equals(Constants.VALUE_CHECK()))
            {
                String errors = "";

                Date dateTarikhSarResidForCheck = sdf.parse(getTarikhSarResidForCheck(darkhastFaktorModel, codeNoeVosolMoshtary, Double.parseDouble(mablaghMandeh))[1]);

                Date tarikhSanad = sdf.parse(new PubFunc().new DateUtils().persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi()));
                Log.d("invoice" , "flagInputHesab : " + flagInputHesab);
                Log.d("invoice" , "shomareHesab len : " + dariaftPardakhtPPCModel.getShomarehHesabSanad().trim().length());
                Log.d("invoice" , "shomareHesab : " + dariaftPardakhtPPCModel.getShomarehHesabSanad());
                if(flagInputHesab == 0 && dariaftPardakhtPPCModel.getCcShomarehHesab() == 0)
                {
                    errors += mPresenter.getAppContext().getResources().getString(R.string.errorShomareHesab);
                    hasError = true;
                }
                else if (flagInputHesab == 0 && dariaftPardakhtPPCModel.getCcShomarehHesab() != 0)
                {
                    Log.d("invoice" , "CcShomarehHesab : " + dariaftPardakhtPPCModel.getCcShomarehHesab());
                    nameMoshtary = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext()).getNameMoshtaryByccMoshtaryShomareHesab(dariaftPardakhtPPCModel.getCcShomarehHesab());
                    Log.d("invoice" , "nameMoshtary : " + nameMoshtary);
                    if (nameMoshtary == null || nameMoshtary.trim().equals(""))
                    {
                        mPresenter.onErrorCheckInsert(R.string.errorNameSahebSanad);
                        hasError = true;
                        return;
                    }
                    else
                    {
                        dariaftPardakhtPPCModel.setNameSahebHesab(nameMoshtary);
                    }
                }
                else if(flagInputHesab == 1)
                {
                    if (dariaftPardakhtPPCModel.getCcBankSanad() == 0)
                    {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorBank);
                        hasError = true;
                    }
                    if(dariaftPardakhtPPCModel.getCodeShobehSanad().trim().length() == 0)
                    {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorCodeShobe);
                        hasError = true;
                    }
                    if(dariaftPardakhtPPCModel.getNameShobehSanad().trim().length() == 0)
                    {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorNameShobe);
                        hasError = true;
                    }
                    if(dariaftPardakhtPPCModel.getShomarehHesabSanad().trim().length() == 0)
                    {
                        errors += mPresenter.getAppContext().getResources().getString(R.string.errorInputHesab);
                        hasError = true;
                    }
                }
                Log.d("check" , "tarikhSanad.getTime() : " + tarikhSanad.getTime());
                Log.d("check" , "tarikhSanad : " + tarikhSanad);
                Log.d("check" , "tarikhSanad.getTime() : " + tarikhSanad.getTime());
                Log.d("check" , "tedadRoozForRotbeh : " + tedadRoozForRotbeh);
                Log.d("check" , "dateTarikhSarResidForCheck : " + dateTarikhSarResidForCheck);
                Log.d("check" , "new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh).getTime() : " + new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh).getTime());
                Log.d("check" , "new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh) : " + new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh));
                if(tarikhSanad.getTime() > new PubFunc().new DateUtils().addDay(dateTarikhSarResidForCheck , tedadRoozForRotbeh).getTime())
                {
                    errors += mPresenter.getAppContext().getResources().getString(R.string.errorInvalidDate);
                    hasError = true;
                }
                if (errors.trim().length() > 0)
                {
                    mPresenter.onErrorCheckInsert(errors);
                    hasError = true;
                }
            }


            if(strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) && dariaftPardakhtPPCModel.getCcBankSanad() == 0)
            {
                mPresenter.onErrorCheckInsert(R.string.errorBank);
                hasError = true;
                return;
            }
            else if (strCodeNoeVosol.equals(Constants.VALUE_POS()) && dariaftPardakhtPPCModel.getCcBankSanad() == 0)
            {
                mPresenter.onErrorCheckInsert(R.string.errorAssignPosForSeller);
                hasError = true;
            }

            if(!strCodeNoeVosol.equals(Constants.VALUE_RESID()) && !strCodeNoeVosol.equals(Constants.VALUE_IRANCHECK()))
            {
                if(dariaftPardakhtPPCModel.getMablagh() == 0)
                {
                    mPresenter.onErrorCheckInsert(R.string.errorInputMablagh);
                    hasError = true;
                }
                else
                {
                    if (dariaftPardakhtPPCModel.getMablagh() <= 0 )
                    {
                        mPresenter.onErrorCheckInsert(R.string.errorNegativeInputMablagh);
                        hasError = true;
                    }
                }
            }

            int valueVajhNaghdYekSetare = Integer.parseInt(Constants.VALUE_VAJH_NAGHD_YEK_SETARE());
            int valueVajhNaghdDoSetare = Integer.parseInt(Constants.VALUE_VAJH_NAGHD_DO_SETARE());
            if(darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghdYekSetare || darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == valueVajhNaghdDoSetare)
            {
                if(strCodeNoeVosol.equals(Constants.VALUE_POS()) && strCodeNoeVosol.equals(Constants.VALUE_FISH_BANKI()) && strCodeNoeVosol.equals(Constants.VALUE_VAJH_NAGHD()))
                {
                    mPresenter.onErrorCheckInsert(R.string.errorInvalidSelectedNoeVosol);
                    hasError = true;
                }
            }
            DariaftPardakhtDarkhastFaktorPPCDAO dpdarkhastfaktorppcDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            int CodeNoeVosolSabtShode = 0;
            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dpdarkhastfaktorppcDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
            if (dariaftPardakhtDarkhastFaktorPPCModels.size() > 0)
            {
                CodeNoeVosolSabtShode = dariaftPardakhtDarkhastFaktorPPCModels.get(0).getCodeNoeVosol();
            }
            if (CodeNoeVosolSabtShode > 0)
            {
                int valueResid = Integer.parseInt(Constants.VALUE_RESID());
                if (CodeNoeVosolSabtShode == valueResid && !strCodeNoeVosol.equals(Constants.VALUE_RESID()))
                {
                    mPresenter.onErrorCheckInsert(R.string.errorDuplicateResidForVosol);
                    hasError = true;
                }
                if (CodeNoeVosolSabtShode != valueResid && strCodeNoeVosol.equals(Constants.VALUE_RESID()))
                {
                    mPresenter.onErrorCheckInsert(R.string.errorDuplicateVosolForResid);
                    hasError = true;
                }
            }
            /*if (darkhastFaktorModel.getMablaghKhalesFaktor() == 0)
            {
                mPresenter.onErrorCheckInsert(R.string.errorMablaghKhalesFaktor);
                hasError = true;
            }*/

            if (!hasError)
            {
                /*if (strCodeNoeVosol.equals(Constants.VALUE_POS()))
                {
                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getOne();
                    dariaftPardakhtPPCModel.setCcShomarehHesab(foroshandehMamorPakhshModel.getCcPosShomarehHesab());
                }*/

                Log.d("dariaftPardakht" , "ccshomarehesabe before insert : " + dariaftPardakhtPPCModel.getCcShomarehHesab());
                long ccDariaftPardakht = insertDariaftPardakht(ccMoshtary, ccDarkhastFaktor, codeNoeVosolMoshtary, mablaghMandeh, nameNoevosol, dariaftPardakhtPPCModel);
                if (ccDariaftPardakht > 0)
                {
                    if (isValidDariaftPardakhtDarkhastFaktor(darkhastFaktorModel , ccDariaftPardakht, strCodeNoeVosol, mablaghMandeh))
                    {
                        long ccDpdfNewInsert = insertDariaftPardakhtDarkhastFaktor(ccDariaftPardakht , dariaftPardakhtPPCModel , strCodeNoeVosol);
                    }
                }
                mPresenter.onSuccessInsert(setMablaghMandehFaktor(ccDarkhastFaktor));
                getVosols(ccDarkhastFaktor);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "checkInsert", "");
            mPresenter.onErrorCheckInsert(R.string.errorInvalidDate);
        }
    }

    private long insertDariaftPardakht(int ccMoshtary , long ccDarkhastFaktor , int codeNoeVosolMoshtary, String mablaghMandeh, String nameNoeVosol, DariaftPardakhtPPCModel dariaftPardakhtPPCModel)
    {
        String strCodeNoeVosolMoshtary = String.valueOf(codeNoeVosolMoshtary);
        DariaftPardakhtPPCDAO dariaftPardakhtDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehmamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());

        long ccDariaftPardakht = -1L;
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehmamorPakhshDAO.getOne();
        int isDirkard = 0;
        //int isTajil = 0;

        try
        {
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
            ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(Constants.CC_CODE_NOE_SANAD());
            int codeNoeCheck = new PubFunc().new VosolUtil().getCodeNoeCheck(codeNoeVosolMoshtary , childParameterModels);
            String tarikhSarResidGregorian = "";
            try
            {
				if (!dariaftPardakhtPPCModel.getTarikhSanadShamsi().trim().equals(""))
                {
                    tarikhSarResidGregorian = new PubFunc().new DateUtils().persianWithSlashToGregorianSlash(dariaftPardakhtPPCModel.getTarikhSanadShamsi());
                }
            }
            catch (Exception exception) {exception.printStackTrace();}


            Log.d("invoice" , "mablagh dariaftpardakhat : " + dariaftPardakhtPPCModel.getMablagh());

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
            dariaftPardakhtPPCModel.setIsPishDariaft(0);
            dariaftPardakhtPPCModel.setTabdil_NaghdBeFish(0);
            dariaftPardakhtPPCModel.setExtraProp_IsSend(0);
            dariaftPardakhtPPCModel.setExtraProp_IsDirkard(isDirkard);

            ccDariaftPardakht = dariaftPardakhtDAO.insert(dariaftPardakhtPPCModel);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "prepareData", "");
        }
        return ccDariaftPardakht;
    }


    private boolean isValidDariaftPardakhtDarkhastFaktor(DarkhastFaktorModel darkhastFaktorModel, long ccDariaftPardakht, String strCodeNoeVosolMoshtary, String mablaghMandeh)
    {
        double MablaghDpdf = 0;
        double MablaghDariaftPardakht = 0;
        //double MablaghKhalesFaktor = darkhastFaktorModel.getMablaghKhalesFaktor();
        double MablaghKhalesFaktor = darkhastFaktorModel.getMablaghKhalesFaktor();
        double mablaghTakhsis = 0L;

        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());

        ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCModels = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
        DariaftPardakhtPPCModel dariaftPardakhtPPCModel = dariaftPardakhtPPCDAO.getByccDariaftPardakht(ccDariaftPardakht);
        MablaghDariaftPardakht = dariaftPardakhtPPCModel.getMablagh();

        if(!strCodeNoeVosolMoshtary.equals(Constants.VALUE_RESID()))
        {
            double doubleMablaghMandeh = Double.valueOf(mablaghMandeh.trim().replace("," , ""));
            if(doubleMablaghMandeh <= dariaftPardakhtPPCModel.getMablagh())
            {
                mablaghTakhsis = doubleMablaghMandeh;
            }
            if(dariaftPardakhtPPCModel.getMablagh() <= doubleMablaghMandeh)
            {
                mablaghTakhsis =  dariaftPardakhtPPCModel.getMablagh();
            }
        }
        /// جمع دریافتی های فاکتور
        for (DariaftPardakhtDarkhastFaktorPPCModel model : dariaftPardakhtDarkhastFaktorPPCModels)
        {
            MablaghDariaftPardakht += model.getMablaghDariaftPardakht();
            MablaghDpdf += model.getMablagh();
        }

        Log.d("invoice" , "MablaghDpdf : " + MablaghDpdf);
        Log.d("invoice" , "mablaghTakhsis : " + mablaghTakhsis);
        Log.d("invoice" , "sum of two : " + MablaghDpdf + mablaghTakhsis);
        Log.d("invoice" , "MablaghKhalesFaktor : " + MablaghKhalesFaktor);
        if (MablaghDpdf + mablaghTakhsis > MablaghKhalesFaktor)
        {
            mPresenter.onErrorCheckInsert(R.string.errorVosoliMoreThanFaktor);
            return false;
        }
        if (MablaghDariaftPardakht != 0)
        {
            if(MablaghDariaftPardakht < MablaghDpdf + mablaghTakhsis)
            {
                mPresenter.onErrorCheckInsert(R.string.errorVosoliBiggerThanFaktor);
                return false;
            }
        }
        return true;
    }


    private long insertDariaftPardakhtDarkhastFaktor(long ccDariaftPardakht , DariaftPardakhtPPCModel dariaftPardakhtPPCModel , String strCodeNoeVosolMoshtary)
    {
        long ccDariaftPardakhtDarkhastFaktor = 0;
        try
        {
            DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPC = new DariaftPardakhtDarkhastFaktorPPCModel();
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());

            ForoshandehMamorPakhshDAO foroshandehmamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehmamorPakhshDAO.getOne();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            String currentDate = sdf.format(new Date());
            double mablaghTakhsis = 0;
            if(!strCodeNoeVosolMoshtary.equals(Constants.VALUE_RESID()))
            {
                if(dariaftPardakhtPPCModel.getMablaghMandeh() <= dariaftPardakhtPPCModel.getMablagh())
                {
                    mablaghTakhsis = dariaftPardakhtPPCModel.getMablaghMandeh();
                }
                if(dariaftPardakhtPPCModel.getMablagh() <= dariaftPardakhtPPCModel.getMablaghMandeh())
                {
                    mablaghTakhsis =  dariaftPardakhtPPCModel.getMablagh();
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
            dariaftPardakhtDarkhastFaktorPPC.setZamaneTakhsiseFaktorShamsi(new PubFunc().new DateUtils().gregorianToPersianDateTime(sdf.parse(currentDate)));
            dariaftPardakhtDarkhastFaktorPPC.setCcAfradErsalKonandeh(foroshandehMamorPakhshModel.getCcAfrad());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazAnbar(foroshandehMamorPakhshModel.getCcMarkazAnbar());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazForosh(foroshandehMamorPakhshModel.getCcMarkazForosh());
            dariaftPardakhtDarkhastFaktorPPC.setCcMarkazSazmanForoshSakhtarForosh(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
            dariaftPardakhtDarkhastFaktorPPC.setTabdil_NaghdBeFish(0);
            dariaftPardakhtDarkhastFaktorPPC.setCcTafkikJoze(0);
            dariaftPardakhtDarkhastFaktorPPC.setNaghlAzGhabl(0);
            dariaftPardakhtDarkhastFaktorPPC.setIsForTasviehTakhir(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsDirkard(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccKardexSatr(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsBestankari_ForTasviehTakhir(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsSend(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_CanDelete(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_IsTajil(0);
            dariaftPardakhtDarkhastFaktorPPC.setExtraProp_ccDarkhastFaktorServer(dariaftPardakhtPPCModel.getCcDarkhastFaktor());
            //strCodeNoeVosolMoshtary.equals(Constants.VALUE_MARJOEE()) ? 1 : 0;

            Log.d("invoice" , "mablagh dariaftpardakhatdarkhastfaktor : " + dariaftPardakhtDarkhastFaktorPPC.getMablagh());

            ccDariaftPardakhtDarkhastFaktor = dariaftPardakhtDarkhastFaktorPPCDAO.insert(dariaftPardakhtDarkhastFaktorPPC);

            if(dariaftPardakhtPPCModel.getMablagh() > mablaghTakhsis)
            {
                /*flag_Bestankari = true;
                ccDariaftPardakhtDarkhastFaktor_ForBestankari = ccDariaftPardakhtDarkhastFaktor;*/
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "insertDariaftPardakhtDarkhastFaktor", "");
        }
        return ccDariaftPardakhtDarkhastFaktor;
    }


    private long setMablaghMandehFaktor(long ccDarkhastFaktor)
    {
        //--------------------- Update_MandehDarkhastFaktor --------------------------
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        darkhastFaktorDAO.updateMandehDarkhastFaktor(ccDarkhastFaktor);
        Log.d("mablaghMandeh" , "mandeh in invoice : " + darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh());
        return darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor).getMablaghMandeh();
    }

    /**
     * getTarikhSarResidForCheck in persian and gregorian format
     * @param darkhastFaktorModel
     * @param codeNoeVosol
     * @return String[0] = tarikhSarResidShamsi , String[1] = tarikhSarResidGregorian
     */
    private String[] getTarikhSarResidForCheck(DarkhastFaktorModel darkhastFaktorModel , int codeNoeVosol , double mablaghMandehFaktor)
    {
        PubFunc.DateUtils dateUtils = new PubFunc().new DateUtils();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH());
        Date tarikhSarResidCheck = null;
        Date tarikhErsal = null;
        String strTarikhSarResidCheck = "";
        String tarikhSarResidShamsiCheck = null;
        int valueVajhNaghd = Integer.parseInt( Constants.VALUE_VAJH_NAGHD());
        if(codeNoeVosol != valueVajhNaghd)
        {
            try
            {
                tarikhErsal = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).parse(darkhastFaktorModel.getTarikhErsal());
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "InvoiceSettlementModel", "", "getTarikhSarResidForCheck", "");
            }
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
            ArrayList<DariaftPardakhtDarkhastFaktorPPCModel> dariaftPardakhtDarkhastFaktorPPCs = dariaftPardakhtDarkhastFaktorPPCDAO.getByccDarkhastFaktor(darkhastFaktorModel.getCcDarkhastFaktor());
            tarikhSarResidCheck = dariaftPardakhtDarkhastFaktorPPCDAO.getTarikhSarResidShamsiCheck(dariaftPardakhtDarkhastFaktorPPCs, darkhastFaktorModel.getModateVosol(), (long) darkhastFaktorModel.getMablaghKhalesFaktor(), tarikhErsal, mablaghMandehFaktor);
            tarikhSarResidShamsiCheck = dateUtils.gregorianWithSlashToPersianSlash(sdf.format(dateUtils.addDay(tarikhSarResidCheck , -1)));
        }
        else
        {
            tarikhSarResidShamsiCheck = dateUtils.gregorianWithSlashToPersianSlash(sdf.format(new Date()));
        }

        strTarikhSarResidCheck = dateUtils.persianWithSlashToGregorianSlash(tarikhSarResidShamsiCheck);
        return new String[]{tarikhSarResidShamsiCheck , strTarikhSarResidCheck};
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
    public void removeItem(DariaftPardakhtDarkhastFaktorPPCModel dariaftPardakhtDarkhastFaktorPPCModel , int position)
    {
        boolean deleteResult = false;
        DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        DariaftPardakhtDarkhastFaktorPPCDAO dpdfPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        //---------------------------------------------------------------
        deleteResult = dariaftPardakhtPPCDAO.deleteByccDariaftPardakht(dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakht());
        //-----------------Update_Resid------------------------------------------
        if (deleteResult)
        {
            if(dariaftPardakhtDarkhastFaktorPPCModel.getCodeNoeVosol() == Integer.parseInt(Constants.VALUE_RESID()))
            {
                DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                darkhastFaktorDAO.updateResid(dariaftPardakhtDarkhastFaktorPPCModel.getCcDarkhastFaktor() , 0);
            }
            if (dpdfPCDAO.deleteByccDariaftPardakht(dariaftPardakhtDarkhastFaktorPPCModel.getCcDariaftPardakht()))
            {
                long mablaghMandeh = setMablaghMandehFaktor(dariaftPardakhtDarkhastFaktorPPCModel.getCcDarkhastFaktor());
                mPresenter.onSuccessRemoveItem(position , mablaghMandeh);
            }
            else
            {
                mPresenter.onFailedRemoveItem();
            }
        }
        else
        {
            mPresenter.onFailedRemoveItem();
        }
    }

    @Override
    public void checkRegisteredVosol(int position)
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        DarkhastFaktorModel darkhastFaktorModel = darkhastFaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor);
        DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        int countVosols = dariaftPardakhtDarkhastFaktorPPCDAO.getCountByccDarkhastFaktor(ccDarkhastFaktor);
        String codeNoeVosolMoshtaryVajhNaghd = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_VAJH_NAGHD());
        String codeNoeVosolMoshtaryCheck = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_VOSOL_MOSHTARY_CHECK());
        long mablaghMandehFaktor = setMablaghMandehFaktor(ccDarkhastFaktor);
        boolean isMojazForResid = selectFaktorShared.getBoolean(selectFaktorShared.getIsMojazForResid() , false);
        if ((darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Integer.parseInt(codeNoeVosolMoshtaryVajhNaghd) ||
                (darkhastFaktorModel.getCodeNoeVosolAzMoshtary() == Integer.parseInt(codeNoeVosolMoshtaryCheck)))
        && mablaghMandehFaktor > 0)
        {
            mPresenter.onError(R.string.errorRemainBiggerThanZeroForNagh);
        }
        else if (!isMojazForResid && mablaghMandehFaktor > 0)
        {
            mPresenter.onError(R.string.errorInvoiceSettlementForClosedCredit);
        }
        else if (countVosols == 0)
        {
            mPresenter.onError(R.string.errorEmptyVosols);
        }
        else
        {
            mPresenter.onSuccessCheckRegisteredVosol(position);
        }
    }

	@Override
    public void getBank(String codeBank)
    {
        BankDAO bankDAO = new BankDAO(mPresenter.getAppContext());
        BankModel bankModel = bankDAO.getByCodeBank(codeBank);
        mPresenter.onGetBank(bankModel);
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
