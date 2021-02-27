package com.saphamrah.MVP.Model;


import android.util.Log;

import com.saphamrah.BaseMVP.MojodiGiriMVP;
import com.saphamrah.DAO.AdamDarkhastDAO;
import com.saphamrah.DAO.ElatAdamDarkhastDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GPSDataPpcDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaMojodiGiriDAO;
import com.saphamrah.DAO.MojoodiGiriDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.Model.AdamDarkhastModel;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.MojoodiGiriModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.UIModel.KalaMojodiGiriModel;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MojodiGiriModel implements MojodiGiriMVP.ModelOps
{

    private MojodiGiriMVP.RequiredPresenterOps mPresenter;

    public MojodiGiriModel(MojodiGiriMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void updateHaveMojodiGiri()
    {
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        selectFaktorShared.putBoolean(selectFaktorShared.getHaveMojoodiGiri() , true);
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
    public void getKala(int ccMoshtary)
    {
        KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
        ArrayList<KalaModel> kalaModels = kalaDAO.getAllForMojodiGiri(ccMoshtary);
        mPresenter.onGetKala(kalaModels);
    }

    @Override
    public void getAllInsertedKalaMojodi(int ccMoshtary)
    {
        SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
        KalaMojodiGiriDAO kalaMojodiGiriDAO = new KalaMojodiGiriDAO(mPresenter.getAppContext());
        ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels = kalaMojodiGiriDAO.getAll(ccMoshtary , shared.getInt(shared.getCcForoshandeh() , 0));
        mPresenter.onGetInsertedKalaMojodi(kalaMojodiGiriModels);
    }

    @Override
    public void addNewKala(int ccMoshtary , int ccKalaCode , float countMojodi)
    {
        String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
        //MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
        SelectFaktorShared selectFaktorShared = new SelectFaktorShared(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());

        int ccForoshandeh = selectFaktorShared.getInt(selectFaktorShared.getCcForoshandeh() , 0);
        //ArrayList<MojoodiGiriModel> mojoodiGiriModels = mojoodiGiriDAO.getForNewFaktor(ccForoshandeh , ccMoshtary);
        try
        {
            MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
            MojoodiGiriModel mojoodiGiriModel = mojoodiGiriDAO.getByMoshtaryAndKalaCode(ccMoshtary, ccKalaCode, true);
            if (mojoodiGiriModel.getCcMojoodiGiri() == 0)
            {
                PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
                /*if (!googleLocationProvider.getHasAccess())
                {
                    mPresenter.onErrorAccessToLocation();
                }
                else
                {*/
                    float latitude = Float.parseFloat(selectFaktorShared.getString(selectFaktorShared.getLatitude() , String.valueOf(googleLocationProvider.getLatitude())));
                    float longtitude = Float.parseFloat(selectFaktorShared.getString(selectFaktorShared.getLongitude() , String.valueOf(googleLocationProvider.getLongitude())));

                    ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
                    int ccMamorPakhsh = 0;
                    int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
                    if (noeMasouliat == 4 || noeMasouliat == 5)
                    {
                        ccMamorPakhsh = foroshandehMamorPakhshModel.getCcMamorPakhsh();
                        ccForoshandeh = 0;
                    }

                    mojoodiGiriModel.setCcForoshandeh(ccForoshandeh);
                    mojoodiGiriModel.setCcMoshtary(ccMoshtary);
                    mojoodiGiriModel.setCcKalaCode(ccKalaCode);
                    mojoodiGiriModel.setTarikh(currentDate);
                    mojoodiGiriModel.setTedadMojoodiGiri(countMojodi);
                    mojoodiGiriModel.setSaatVorod(selectFaktorShared.getString(selectFaktorShared.getSaatVorodBeMaghazeh() , new SimpleDateFormat(Constants.TIME_FORMAT()).format(new Date())));
                    mojoodiGiriModel.setSaatKhoroj(currentDate);
                    mojoodiGiriModel.setLatitude(latitude);
                    mojoodiGiriModel.setLongitude(longtitude);
                    mojoodiGiriModel.setInsertInPPC(true);
                    mojoodiGiriModel.setOld(false);
                    mojoodiGiriDAO.insert(mojoodiGiriModel);
                    boolean result = insertGpsData(ccForoshandeh, foroshandehMamorPakhshModel.getCcAfrad(), currentDate , latitude , longtitude , ccMamorPakhsh, ccMoshtary);
                    Log.d("GPS" , "result of insert GPS Data : " + result);
                    //selectFaktorShared.putBoolean(selectFaktorShared.getHaveMojoodiGiri() , true);
                    mPresenter.onSuccessAddNewKala();
                    getAllInsertedKalaMojodi(ccMoshtary);
                //}
            }
            else
            {
                mojoodiGiriModel.setTedadMojoodiGiri(countMojodi);
                //mojoodiGiriModel.setTedadPishnahady(mojoodiGiriModel.getTedadPishnahady());
                mojoodiGiriDAO.update(mojoodiGiriModel);
                mPresenter.onSuccessAddNewKala();
                getAllInsertedKalaMojodi(ccMoshtary);
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MojodiGiriModel", "", "addNewKala", "");
            mPresenter.onErrorAddNewKala();
        }
    }

    private boolean insertGpsData(int ccForoshandeh, int ccAfrad, String currentDate , double latitude , double longtitude , int ccMamorPakhsh, int ccMoshtary)
    {
        GPSDataModel gpsDataModel = new GPSDataModel();
        GPSDataPpcDAO gpsDataDAO = new GPSDataPpcDAO(mPresenter.getAppContext()) ;
		MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());

        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

        gpsDataModel.setCcAfrad(ccAfrad);
        gpsDataModel.setCcForoshandeh(ccForoshandeh);
        gpsDataModel.setCcMasir(moshtaryModel.getCcMasir());
        gpsDataModel.setTarikh(currentDate);
        gpsDataModel.setLatitude(latitude);
        gpsDataModel.setLongitude(longtitude);
        gpsDataModel.setExtraProp_IsSend(0);
        gpsDataModel.setDistance(0D);
        gpsDataModel.setCcMamorPakhsh(ccMamorPakhsh);
        return gpsDataDAO.insert(gpsDataModel);
    }


    @Override
    public void removeKalaMojodiGiri(int ccKalaMojodiGiri, int position)
    {
        MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
        if (mojoodiGiriDAO.deleteByccMojodiGiri(ccKalaMojodiGiri))
        {
            mPresenter.onSuccessRemoveKalaMojodiGiri(position);
        }
        else
        {
            mPresenter.onFailedRemoveKalaMojodiGiri();
        }
    }

    @Override
    public void getElatAdamDarkhast(int ccMoshtary)
    {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

        ElatAdamDarkhastDAO elatAdamDarkhastDAO = new ElatAdamDarkhastDAO(mPresenter.getAppContext());
        ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels = elatAdamDarkhastDAO.getElatAdamDarkhast(moshtaryModel.getCodeVazeiat() , moshtaryModel.getCcNoeMoshtary());

        mPresenter.onGetElatAdamDarkhast(elatAdamDarkhastModels);
    }

    @Override
    public void insertAdamDarkhast(int ccMoshtary , int ccElatAdamDarkhast, byte[] imageAdamDarkhast, String codeMoshtaryTekrari)
    {
        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
        /*if (!googleLocationProvider.getHasAccess())
        {
            mPresenter.onErrorAccessToLocation();
        }
        else
        {*/
            SelectFaktorShared shared = new SelectFaktorShared(mPresenter.getAppContext());
            int ccForoshandeh = shared.getInt(shared.getCcForoshandeh() , 0);
            AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(mPresenter.getAppContext());
            AdamDarkhastModel adamDarkhastModel = new AdamDarkhastModel();

            adamDarkhastModel.setCcElatAdamDarkhast(ccElatAdamDarkhast);
            adamDarkhastModel.setCodeMoshtaryTekrari(codeMoshtaryTekrari);
            adamDarkhastModel.setAdamDarkhastImage(imageAdamDarkhast);
            adamDarkhastModel.setCcMoshtary(ccMoshtary);
            adamDarkhastModel.setCcForoshandeh(ccForoshandeh);
            adamDarkhastModel.setDateAdamDarkhast(getCurrentDate());
            adamDarkhastModel.setLongitude((float) googleLocationProvider.getLongitude());
            adamDarkhastModel.setLatitude((float) googleLocationProvider.getLatitude());
            adamDarkhastModel.setIsSentToServer(false);

            if (adamDarkhastDAO.insert(adamDarkhastModel))
            {
                mPresenter.onSuccessInsertAdamDarkhast();
            }
            else
            {
                mPresenter.onFailedInsertAdamDarkhast();
            }
        //}
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
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "MojoodiGiriModel", "", "insertAdamDarkhast", "");
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

}
