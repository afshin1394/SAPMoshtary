package com.saphamrah.MVP.Model;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.GoodsInfoMVP;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.RptKalaInfoDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.Network.AsyncDownloadFileResponse;
import com.saphamrah.Network.AsyncDownloadZipFile;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.LocalConfigShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class GoodsInfoModel implements GoodsInfoMVP.ModelOps
{

    RptKalaInfoDAO rptKalaInfoDAO = new RptKalaInfoDAO(BaseApplication.getContext());
    private GoodsInfoMVP.RequiredPresenterOps mPresenter;

    public GoodsInfoModel(GoodsInfoMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getListOfGoods()
    {
        ArrayList<RptKalaInfoModel> kalaInfoModels = rptKalaInfoDAO.getAll();
        mPresenter.onGetListOfGoods(kalaInfoModels);
    }

    @Override
    public void updateGallery()
    {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIp = serverIPShared.getString(serverIPShared.IP() , "");
        String serverPort = serverIPShared.getString(serverIPShared.PORT() , "");
        if (serverIp.equals("") || serverPort.equals(""))
        {
            mPresenter.onError(R.string.cantFindServer);
        }
        else
        {
            AsyncDownloadZipFile asyncDownloadZipFile = new AsyncDownloadZipFile(true, new AsyncDownloadFileResponse()
            {
                @Override
                public void updateProgressBar(int progress)
                {
                    mPresenter.updateProgress(progress);
                }

                @Override
                public void downloadCompleted()
                {
                    mPresenter.onSuccessUpdateGallery();
                }

                @Override
                public void downloadFailed(int resId)
                {
                    mPresenter.onError(resId);
                }
            });
            String zipFileName = "pic.zip";
            String downloadURL = "http://" + serverIp + ":" + serverPort + "/Album/" + zipFileName;
            String path = Environment.getExternalStorageDirectory() + "/SapHamrah/Album/";
            asyncDownloadZipFile.execute(downloadURL, path, zipFileName);
			updateLocalGalleryVersion();							
        }
    }

    private void updateLocalGalleryVersion()
    {
        int serverVersion = 0;
        try
        {
            serverVersion = Integer.parseInt(new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_UPDATE_GALLERY));
        }
        catch (Exception e){e.printStackTrace();}
        LocalConfigShared localConfigShared = new LocalConfigShared(mPresenter.getAppContext());
        localConfigShared.remove(LocalConfigShared.GALLERY_VERSION);
        localConfigShared.putInt(LocalConfigShared.GALLERY_VERSION , serverVersion);
    }

    @Override
    public void updateGoodsList()
    {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    mPresenter.onUpdateGoodsList(true , R.string.updateSuccessed);
                    getListOfGoods();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onUpdateGoodsList(false , R.string.errorSaveData);
                }
                return false;
            }
        });

        String ccMarkazSazmanSakhtarForosh = getccMarkazSazmanSakhtarForosh();
        if (ccMarkazSazmanSakhtarForosh == null || ccMarkazSazmanSakhtarForosh.trim().equals(""))
        {
            mPresenter.onUpdateGoodsList(false , R.string.notFoundMarkazSazmanSakhtarForosh);
        }
        else
        {

            rptKalaInfoDAO.fetchRptKalaInfo(mPresenter.getAppContext(), "GoodsInfoActivity", ccMarkazSazmanSakhtarForosh, new RetrofitResponse()
            {
                @Override
                public void onSuccess(final ArrayList arrayListData)
                {
                    Thread thread = new Thread()
                    {
                        @Override
                        public void run(){
                            boolean deleteResult = rptKalaInfoDAO.deleteAll();
                            boolean insertResult = rptKalaInfoDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult)
                            {
                                Message message = new Message();
                                message.arg1 = 1;
                                handler.sendMessage(message);
                            }
                            else
                            {
                                Message message = new Message();
                                message.arg1 = -1;
                                handler.sendMessage(message);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "GoodsInfoModel", "GoodsInfoActivity", "updateGoodsList", "onFailed");
                    mPresenter.onUpdateGoodsList(false , R.string.errorGetData);
                }
            });
        }
    }

    private String getccMarkazSazmanSakhtarForosh()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getOne();
        if (foroshandehMamorPakhshModel == null)
        {
            mPresenter.onUpdateGoodsList(false, R.string.errorFindForoshandehMamorPakhsh);
            return "-1";
        }
        else
        {
            ForoshandehMamorPakhshUtils foroshandehMamorPakhshUtils = new ForoshandehMamorPakhshUtils();
            int noeMasouliat = foroshandehMamorPakhshUtils.getNoeMasouliat(foroshandehMamorPakhshModel);
            if (noeMasouliat == 4 || noeMasouliat == 5)
            {
                DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
                return darkhastFaktorDAO.getAllccMarkazSazmanSakhtarForosh();
            }
            else
            {
                return String.valueOf(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
            }
        }
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

    /*
    get kala ccBrand list for show to filter list
     */
    @Override
    public void getKalaInfoCcBrandList() {
        ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList = rptKalaInfoDAO.getCcBrandList();
        mPresenter.onGetKalaInfoCcBrandList(kalaInfoByCcBrandList);
    }

    /*
    select filter and search in DB by ccBrand
     */
    @Override
    public void getKalaByCcBrand(String ccBrands) {
        ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList = rptKalaInfoDAO.getByCcBrandList(ccBrands);
        mPresenter.onGetListOfGoods(kalaInfoByCcBrandList);
    }

    /*
    get kala ccGoroh list for show to filter list
     */
    @Override
    public void getKalaInfoCcGorohList(String ccBrands) {
        if (ccBrands.length() == 0 || ccBrands.equals("0")){
            ArrayList<RptKalaInfoModel> kalaInfoModels = rptKalaInfoDAO.getAll();
            mPresenter.onGetListOfGoods(kalaInfoModels);
        } else {
            ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList = rptKalaInfoDAO.getByCcGorohList(ccBrands);
            mPresenter.onGetKalaInfoCcGorohList(kalaInfoByCcBrandList);
        }

    }

    /*
    select filter and search in DB by ccGoroh
     */
    @Override
    public void getKalaByCcGoroh(String ccBrands) {
        ArrayList<RptKalaInfoModel> kalaInfoByCcBrandList = rptKalaInfoDAO.getByCcGoroh(ccBrands);
        mPresenter.onGetListOfGoods(kalaInfoByCcBrandList);
    }
}
