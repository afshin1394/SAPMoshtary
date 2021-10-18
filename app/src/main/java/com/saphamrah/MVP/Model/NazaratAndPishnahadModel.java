package com.saphamrah.MVP.Model;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.NazaratAndPishnahadMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.NoePishnahadDAO;
import com.saphamrah.DAO.SuggestDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.NoePishnahadModel;
import com.saphamrah.Model.SuggestModel;
import com.saphamrah.PubFunc.DateUtils;

import java.util.ArrayList;

public class NazaratAndPishnahadModel implements NazaratAndPishnahadMVP.ModelOps {
    private NazaratAndPishnahadMVP.RequiredPresenterOps mPresenter;
    private ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(BaseApplication.getContext());
    private SuggestDAO suggestDAO = new SuggestDAO(BaseApplication.getContext());
    private NoePishnahadDAO noePishnahadatDAO = new NoePishnahadDAO(BaseApplication.getContext());
    private DateUtils dateUtils = new DateUtils();

    public NazaratAndPishnahadModel(NazaratAndPishnahadMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void insertPishnahad(int ccNoePishnahad, String description,String descriptionPishnehad,int ccMoshtary) {
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        SuggestModel model = new SuggestModel();
        model.setCcNoePishnehad(ccNoePishnahad);
        model.setDescription(description);
        model.setDescriptionPishnehad(descriptionPishnehad);
        model.setCcForoshandeh(foroshandehMamorPakhshModel.getCcForoshandeh());
        model.setCcAmargar(foroshandehMamorPakhshModel.getCcAmargar());
        model.setCcAfrad(foroshandehMamorPakhshModel.getCcAfrad());
        model.setCcMamorPakhsh(foroshandehMamorPakhshModel.getCcMamorPakhsh());
        model.setDate(dateUtils.todayDateGregorianWithSeconds());
        model.setExtraProp_IsSend(0);
        model.setCcMoshtary(ccMoshtary);

        boolean insert = suggestDAO.insert(model);
        if (insert){
            mPresenter.onSuccessInsert();
            getSuggest(ccMoshtary);
        } else {
            mPresenter.onErrorInsert();
        }
    }

    @Override
    public void deleteSuggest(int ccSuggest,int ccMoshtary) {
        boolean delete = suggestDAO.deleteByccSuggest(ccSuggest);

        if (delete){
            mPresenter.onSuccessDeleteSuggest();
            getSuggest(ccMoshtary);
        } else {
            mPresenter.onErrorDeleteSuggest();
        }
    }

    @Override
    public void getNoePishnahad(int noePishnahad) {
        ArrayList<NoePishnahadModel> noePishnahadModels = noePishnahadatDAO.getNoePishnahad(noePishnahad);
        ArrayList<String> noePishnahadTitles = new ArrayList<>();
        for (NoePishnahadModel model : noePishnahadModels)
        {
            noePishnahadTitles.add(model.getNameNoePishnahad());
        }
        mPresenter.onGetNoePishnahadat(noePishnahadModels,noePishnahadTitles);
    }

    @Override
    public void getSuggest(int ccMoshtary) {
        ArrayList<SuggestModel> suggestModels = suggestDAO.getSuggest(ccMoshtary);
        mPresenter.onGetSuggest(suggestModels);

    }
}
