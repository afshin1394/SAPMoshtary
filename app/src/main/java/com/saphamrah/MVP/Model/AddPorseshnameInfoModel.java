package com.saphamrah.MVP.Model;

import android.util.Log;

import com.saphamrah.BaseMVP.AddPorseshnameInfoMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GorohDAO;
import com.saphamrah.DAO.ListMoshtarianDAO;
import com.saphamrah.DAO.MahalDAO;
import com.saphamrah.DAO.NoeFaaliatForMoarefiMoshtaryJadidDAO;
import com.saphamrah.DAO.NoeTablighatDAO;
import com.saphamrah.DAO.PorseshnamehDAO;
import com.saphamrah.DAO.PorseshnamehTablighatDAO;
import com.saphamrah.DAO.PorseshnamehTozihatDAO;
import com.saphamrah.DAO.VisitMoshtaryDAO;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.NoeTablighatModel;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.Model.PorseshnamehTablighatModel;
import com.saphamrah.Model.PorseshnamehTozihatModel;
import com.saphamrah.Model.VisitMoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.PorseshnamehInfoShared;
import com.saphamrah.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddPorseshnameInfoModel implements AddPorseshnameInfoMVP.ModelOps
{

    private AddPorseshnameInfoMVP.RequiredPresenterOps mPresenter;

    public AddPorseshnameInfoModel(AddPorseshnameInfoMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void saveInfo()
    {
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider(mPresenter.getAppContext());
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        PorseshnamehInfoShared shared = new PorseshnamehInfoShared(mPresenter.getAppContext());
        shared.removeAll();
        shared.putString(PorseshnamehInfoShared.LATITUDE_KEY, String.valueOf(locationProvider.getLatitude()));
        shared.putString(PorseshnamehInfoShared.LONGITUDE_KEY, String.valueOf(locationProvider.getLongitude()));
        shared.putString(PorseshnamehInfoShared.ZAMANE_VOROD_KEY, String.valueOf(sdf.format(new Date())));
    }

    @Override
    public void getMoshtary(int ccMoshtary, String codeMoshtary)
    {
        ListMoshtarianDAO listMoshtarianDAO = new ListMoshtarianDAO(mPresenter.getAppContext());
        ListMoshtarianModel listMoshtarianModel = listMoshtarianDAO.getByccMoshtaryAndCode(ccMoshtary, codeMoshtary);
        mPresenter.onGetMoshtary(listMoshtarianModel);

        Log.d("AddPorsesh" , "listMoshtarianModel : " + listMoshtarianModel.toString());

        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        int ccMantaghe = listMoshtarianModel.getCcMahaleh();
        MahalModel mantagheModel = mahalDAO.getByCodeNoeAndccMahal(MahalModel.MAHAL_TYPE_MANTAGHE, ccMantaghe);
        MahalModel shahrModel = mahalDAO.getByParent(ccMantaghe, MahalModel.MAHAL_TYPE_MANTAGHE);
        MahalModel bakhshModel = mahalDAO.getByParent(shahrModel.getCcMahal(), MahalModel.MAHAL_TYPE_SHAHR);
        MahalModel shahrestanModel = mahalDAO.getByParent(bakhshModel.getCcMahal(), MahalModel.MAHAL_TYPE_BAKHSH);
        MahalModel ostannModel = mahalDAO.getByParent(shahrestanModel.getCcMahal(), MahalModel.MAHAL_TYPE_SHAHRESTAN);
        mPresenter.onGetAddress(ostannModel, shahrestanModel, bakhshModel, shahrModel, mantagheModel);
    }

    @Override
    public void getPorseshnamehInfo(Integer ccPorseshname)
    {
        PorseshnamehModel porseshnamehModel = new PorseshnamehDAO(mPresenter.getAppContext()).get(ccPorseshname);
        mPresenter.onGetPorseshnamehInfo(porseshnamehModel);
    }

    @Override
    public void getAdsOfPorseshnameh(int ccPorseshnameh)
    {
        List<PorseshnamehTablighatModel> porseshnamehTablighatModels = new PorseshnamehTablighatDAO(mPresenter.getAppContext()).getAllByPorseshname(ccPorseshnameh);
        mPresenter.onGetAdsOfPorseshnameh(porseshnamehTablighatModels);
    }

    @Override
    public void getNameSenfMoshtary(int ccSenfMoshtary)
    {
        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        mPresenter.onGetNameSenfMoshtary(gorohDAO.getNameGoroh(ccSenfMoshtary));
    }

    @Override
    public void getStatesOfMantaghe(int ccMahal)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        MahalModel mantagheModel = mahalDAO.getByCodeNoeAndccMahal(MahalModel.MAHAL_TYPE_MANTAGHE, ccMahal);
        MahalModel shahrModel = mahalDAO.getByParent(mantagheModel.getCcMahal(), MahalModel.MAHAL_TYPE_MANTAGHE);
        MahalModel bakhshModel = mahalDAO.getByParent(shahrModel.getCcMahal(), MahalModel.MAHAL_TYPE_SHAHR);
        MahalModel shahrestanModel = mahalDAO.getByParent(bakhshModel.getCcMahal(), MahalModel.MAHAL_TYPE_BAKHSH);
        MahalModel ostanModel = mahalDAO.getByParent(shahrestanModel.getCcMahal(), MahalModel.MAHAL_TYPE_SHAHRESTAN);
        mPresenter.onGetStatesOfMantaghe(mantagheModel, shahrModel, bakhshModel, shahrestanModel, ostanModel);
    }

    @Override
    public void getProductsItem()
    {
        Map<String,Integer> mapProducts = new LinkedHashMap<>();
        mapProducts.put(mPresenter.getAppContext().getString(R.string.has), 1);
        mapProducts.put(mPresenter.getAppContext().getString(R.string.hasnt), 0);
        mPresenter.onGetProductsItem(mapProducts);
    }

    @Override
    public void getAnbarItems()
    {
        Map<String,Integer> mapAnbarItems = new LinkedHashMap<>();
        mapAnbarItems.put(mPresenter.getAppContext().getString(R.string.has), 1);
        mapAnbarItems.put(mPresenter.getAppContext().getString(R.string.hasnt), 0);
        mPresenter.onGetAnbarItems(mapAnbarItems);
    }

    @Override
    public void getAds()
    {
        NoeTablighatDAO noeTablighatDAO = new NoeTablighatDAO(mPresenter.getAppContext());
        List<NoeTablighatModel> noeTablighatModels = noeTablighatDAO.getAll();
        mPresenter.onGetAds(noeTablighatModels);
    }

    @Override
    public void getDescription()
    {
        PorseshnamehTozihatDAO porseshnamehTozihatDAO = new PorseshnamehTozihatDAO(mPresenter.getAppContext());
        List<PorseshnamehTozihatModel> porseshnamehTozihatModels = porseshnamehTozihatDAO.getAll();
        mPresenter.onGetDescription(porseshnamehTozihatModels);
    }

    @Override
    public void getOstanItems()
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_OSTAN , 1);
        mPresenter.onGetOstanItems(mahalModels);
    }

    @Override
    public void getAllNoeFaaliat()
    {
        NoeFaaliatForMoarefiMoshtaryJadidDAO dao = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        String ccGorohsOfNoeFaaliat = dao.getDistinctccNoeMoshtary();

        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        ArrayList<GorohModel> noeFaaliatItems = gorohDAO.getByccGorohs(ccGorohsOfNoeFaaliat);
        mPresenter.onGetAllNoeFaaliat(noeFaaliatItems);
    }

    @Override
    public void getNoeSenf(Integer selectedNoeFaaliatId)
    {
        NoeFaaliatForMoarefiMoshtaryJadidDAO dao = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        String ccGorohsOfSenf = dao.getDistinctccSenfMoshtary(selectedNoeFaaliatId);

        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        ArrayList<GorohModel> noeSenfItems = gorohDAO.getccNoeSenfByccGorohLink(ccGorohsOfSenf);
        mPresenter.onGetNoeSenfItems(noeSenfItems);
    }

    @Override
    public void getShahrestanItems(int ccOstan)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_SHAHRESTAN , ccOstan);
        mPresenter.onGetShahrestanItems(mahalModels);
    }

    @Override
    public void getBakhshItems(int shahrestanId)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_BAKHSH , shahrestanId);
        mPresenter.onGetBakhshItems(mahalModels);
    }

    @Override
    public void getShahrItems(int bakhshId)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_SHAHR , bakhshId);
        mPresenter.onGetShahrItems(mahalModels);
    }

    @Override
    public void getMantagheItems(int shahrId)
    {
        MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        ArrayList<MahalModel> mahalModels = mahalDAO.getByCodeNoeAndccMahalLink(MahalModel.MAHAL_TYPE_MANTAGHE , shahrId);
        mPresenter.onGetMantagheItems(mahalModels);
    }

    @Override
    public void insertPorseshname(PorseshnamehModel porseshnamehModel, List<Integer> selectedAdsId, boolean changedPhone)
    {
        int ccAmargar = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect().getCcAmargar();
        PorseshnamehDAO porseshnamehDAO = new PorseshnamehDAO(mPresenter.getAppContext());
        ArrayList<PorseshnamehModel> porseshnamehModels = new ArrayList<>();
        PorseshnamehInfoShared shared = new PorseshnamehInfoShared(mPresenter.getAppContext());

        porseshnamehModel.setLatitude_y(Double.valueOf(shared.getString(PorseshnamehInfoShared.LATITUDE_KEY , "0.0")));
        porseshnamehModel.setLongitude_x(Double.valueOf(shared.getString(PorseshnamehInfoShared.LONGITUDE_KEY , "0.0")));
        porseshnamehModel.setCcAmargar(ccAmargar);

        porseshnamehModels.add(porseshnamehModel);
        porseshnamehDAO.insertGroup(porseshnamehModels);

        int ccPorseshname = porseshnamehDAO.getLastccPorseshname();
        insertAds(ccPorseshname, selectedAdsId);
        insertVisitMoshtary(ccAmargar, porseshnamehModel.getCcMoshtary(), ccPorseshname, shared.getString(PorseshnamehInfoShared.ZAMANE_VOROD_KEY, ""));
        new ListMoshtarianDAO(mPresenter.getAppContext()).updateStatus(porseshnamehModel.getCcMoshtary(), 1);

        mPresenter.onInsertPorseshname(ccPorseshname, changedPhone);
    }

    @Override
    public void updatePorseshname(int ccPorseshname, PorseshnamehModel porseshnamehModel, List<Integer> selectedAdsId)
    {
        //update porseshnameh
        PorseshnamehDAO porseshnamehDAO = new PorseshnamehDAO(mPresenter.getAppContext());
        porseshnamehDAO.update(ccPorseshname, porseshnamehModel);

        //update ads
        PorseshnamehTablighatDAO porseshnamehTablighatDAO = new PorseshnamehTablighatDAO(mPresenter.getAppContext());
        porseshnamehTablighatDAO.deleteAll();
        insertAds(ccPorseshname, selectedAdsId);

        mPresenter.onUpdatePorseshnameh(ccPorseshname);
    }

    private void insertAds(int ccPorseshname, List<Integer> selectedAdsId)
    {
        if (ccPorseshname > 0)
        {
            PorseshnamehTablighatDAO porseshnamehTablighatDAO = new PorseshnamehTablighatDAO(mPresenter.getAppContext());
            List<PorseshnamehTablighatModel> porseshnamehTablighatModels = new ArrayList<>();
            PorseshnamehTablighatModel porseshnamehTablighatModel;
            for (int adsId : selectedAdsId)
            {
                porseshnamehTablighatModel = new PorseshnamehTablighatModel();
                porseshnamehTablighatModel.setCcPorseshnameh(ccPorseshname);
                porseshnamehTablighatModel.setCcNoeTablighat(adsId);

                porseshnamehTablighatModels.add(porseshnamehTablighatModel);
            }
            porseshnamehTablighatDAO.insertGroup(porseshnamehTablighatModels);
        }
    }


    private void insertVisitMoshtary(int ccAmargar, int ccMoshtary, int ccPorseshname, String zamaneVorod)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
        String currentDate = sdf.format(new Date());
        VisitMoshtaryDAO visitMoshtaryDAO = new VisitMoshtaryDAO(mPresenter.getAppContext());
        VisitMoshtaryModel visitMoshtaryModel = new VisitMoshtaryModel();
        zamaneVorod = zamaneVorod.equals("") ? currentDate : zamaneVorod;

        visitMoshtaryModel.setCcAmargar(ccAmargar);
        visitMoshtaryModel.setCcMoshtary(ccMoshtary);
        visitMoshtaryModel.setCcPorseshnameh(ccPorseshname);
        visitMoshtaryModel.setTarikhVisitMoshtary(zamaneVorod);
        visitMoshtaryModel.setCodeVazeiatMoshtary(0);
        visitMoshtaryModel.setSaatVorod(zamaneVorod);
        visitMoshtaryModel.setSaatKhoroj(currentDate);

        visitMoshtaryDAO.insert(visitMoshtaryModel);
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
