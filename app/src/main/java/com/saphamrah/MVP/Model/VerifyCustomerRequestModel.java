package com.saphamrah.MVP.Model;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.VerifyCustomerRequestMVP;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyCustomerRequestModel implements VerifyCustomerRequestMVP.ModelOps
{

    private VerifyCustomerRequestMVP.RequiredPresenterOps mPresenter;

    public VerifyCustomerRequestModel(VerifyCustomerRequestMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getAgreementContent(int ccMoshtary)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        //get name
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        String customerFullName = moshtaryDAO.getByccMoshtary(ccMoshtary).getNameMoshtary();

        //calculate count and cost of goods
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        //KalaDarkhastFaktorSatrDAO kalaDarkhastFaktorDAO = new KalaDarkhastFaktorSatrDAO(mPresenter.getAppContext());
        //ArrayList<KalaDarkhastFaktorModel> kalaDarkhastFaktorModels = kalaDarkhastFaktorDAO.getByccDarkhast(ccDarkhastFaktor);
        DarkhastFaktorModel darkhastFaktorModel = new DarkhastFaktorDAO(mPresenter.getAppContext()).getByccDarkhastFaktor(ccDarkhastFaktor);
        int tedadAghlam = new DarkhastFaktorSatrDAO(mPresenter.getAppContext()).getTedadAghlamByccDarkhastFaktor(ccDarkhastFaktor);
        double sumMablagh = darkhastFaktorModel.getMablaghKolFaktor();
        /*for (KalaDarkhastFaktorModel model : kalaDarkhastFaktorModels)
        {
            tedadAghlam++;
            //sumMablagh += model.getMablaghForosh() * model.getTedad3();
        }*/

        //get name noe vosol
        String nameNoeVosol = "";
        if (darkhastFaktorModel != null && darkhastFaktorModel.getNameNoeVosolAzMoshtary() != null)
        {
            nameNoeVosol = darkhastFaktorModel.getNameNoeVosolAzMoshtary();
        }
        String agreementContent = String.format(mPresenter.getAppContext().getString(R.string.agreementContent), customerFullName, String.valueOf(tedadAghlam), formatter.format(sumMablagh), nameNoeVosol);
        mPresenter.onGetAgreementContent(agreementContent);
    }

    @Override
    public void saveBitmap(String description ,int ccMoshtary, byte[] customerSignPic)
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        if (ccDarkhastFaktor > 0)
        {
            byte[] darkhastFaktorImage = null;
            DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
            DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel = new DarkhastFaktorEmzaMoshtaryModel();
            darkhastFaktorEmzaMoshtaryModel.setCcDarkhastFaktor(ccDarkhastFaktor);
            darkhastFaktorEmzaMoshtaryModel.setCcMoshtary(ccMoshtary);
            darkhastFaktorEmzaMoshtaryModel.setEmzaImage(customerSignPic);
            darkhastFaktorEmzaMoshtaryModel.setDarkhastFaktorImage(darkhastFaktorImage);
            darkhastFaktorEmzaMoshtaryModel.setHave_FaktorImage(0);
            darkhastFaktorEmzaMoshtaryModel.setHave_ReceiptImage(0);

            darkhastFaktorEmzaMoshtaryDAO.deleteByccDarkhastFaktor(ccDarkhastFaktor);
            if (darkhastFaktorEmzaMoshtaryDAO.insert(darkhastFaktorEmzaMoshtaryModel))
            {
                DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                darkhastFaktorDAO.updateSaateKhorojAzMaghazehAndInsertInPPC(ccDarkhastFaktor , new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));
                darkhastFaktorDAO.updateDescriptionFaktor(ccDarkhastFaktor , description);
                mPresenter.onSuccessInsertCustomerSign();
            }
            else
            {
                mPresenter.onFailedInsertCustomerSign(R.string.errorOperation);
            }
        }
        else
        {
            mPresenter.onFailedInsertCustomerSign(R.string.errorFindccDarkhastFaktor);
        }
    }

    @Override
    public void getccDarkhastFaktor()
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        long ccDarkhastFaktor = selectFaktorShared.getLong(selectFaktorShared.getCcDarkhastFaktor() , -1);
        mPresenter.onGetccDarkhastFaktor(ccDarkhastFaktor);
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

    @Override
    public void checkLayoutTozihat() {
       ParameterChildDAO parameterChildDAO = new ParameterChildDAO(BaseApplication.getContext());
       int visibility =  Integer.parseInt( parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_Description_Darkhast_Enable()));
       mPresenter.onCheckLayoutTozihat(visibility);
    }

}
