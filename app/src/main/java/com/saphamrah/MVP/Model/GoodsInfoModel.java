package com.saphamrah.MVP.Model;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.GoodsInfoMVP;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KalaPhotoDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.RptKalaInfoDAO;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KalaPhotoModel;
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
import com.saphamrah.WebService.ServiceResponse.KalaPhotoResult;

import java.util.ArrayList;

public class GoodsInfoModel implements GoodsInfoMVP.ModelOps
{

    RptKalaInfoDAO rptKalaInfoDAO = new RptKalaInfoDAO(BaseApplication.getContext());
    private GoodsInfoMVP.RequiredPresenterOps mPresenter;


    public GoodsInfoModel(GoodsInfoMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;

    }

    // TODO
    @Override
    public void getListOfGoods() {
        RptKalaInfoDAO rptKalaInfoDAO = new RptKalaInfoDAO(mPresenter.getAppContext());
        ArrayList<RptKalaInfoModel> kalaInfoModels = rptKalaInfoDAO.getAll();
        mPresenter.onGetListOfGoods(kalaInfoModels);
    }

    //TODO Update Gallery

    @Override
    public void updateGallery() {
        KalaPhotoDAO kalaPhotoDAO = new KalaPhotoDAO(mPresenter.getAppContext());
        startUpdateGalleryProcess(kalaPhotoDAO);
        updateLocalGalleryVersion();
    }

    @Override
    public void getGallery() {
        KalaPhotoDAO kalaPhotoDAO =new KalaPhotoDAO(mPresenter.getAppContext());
        ArrayList<KalaPhotoModel> kalaPhotoModels=kalaPhotoDAO.getAll();
        mPresenter.onGetGallery(kalaPhotoModels);
    }


    private void updateLocalGalleryVersion() {
        int serverVersion = 0;
        try {
            serverVersion = Integer.parseInt(new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_UPDATE_GALLERY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocalConfigShared localConfigShared = new LocalConfigShared(mPresenter.getAppContext());
        localConfigShared.remove(LocalConfigShared.GALLERY_VERSION);
        localConfigShared.putInt(LocalConfigShared.GALLERY_VERSION, serverVersion);
    }

    @Override
    public void updateGoodsList() {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    mPresenter.onUpdateGoodsList(true, R.string.updateSuccessed);
                    getListOfGoods();
                } else if (msg.arg1 == -1) {
                    mPresenter.onUpdateGoodsList(false, R.string.errorSaveData);
                }
                return false;
            }
        });

        String ccMarkazSazmanSakhtarForosh = getccMarkazSazmanSakhtarForosh();
        if (ccMarkazSazmanSakhtarForosh == null || ccMarkazSazmanSakhtarForosh.trim().equals("")) {
            mPresenter.onUpdateGoodsList(false, R.string.notFoundMarkazSazmanSakhtarForosh);
        } else {
            final RptKalaInfoDAO rptKalaInfoDAO = new RptKalaInfoDAO(mPresenter.getAppContext());
            rptKalaInfoDAO.fetchRptKalaInfo(mPresenter.getAppContext(), "GoodsInfoActivity", ccMarkazSazmanSakhtarForosh, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = rptKalaInfoDAO.deleteAll();
                            boolean insertResult = rptKalaInfoDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                Message message = new Message();
                                message.arg1 = 1;
                                handler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.arg1 = -1;
                                handler.sendMessage(message);
                            }
                        }
                    }.start();

                }

                @Override
                public void onFailed(String type, String error) {
                    setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type, error), "GoodsInfoModel", "GoodsInfoActivity", "updateGoodsList", "onFailed");
                    mPresenter.onUpdateGoodsList(false, R.string.errorGetData);
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




    private int[] getKalaImageByCode(int kalaPhoto, int ccKalaCode) {

        Log.d("AFTER_RESPONSE", "ccKalaCode" + ccKalaCode);
        int[] resultMessage = new int[1];
        KalaPhotoDAO kalaPhotoDAO = new KalaPhotoDAO(mPresenter.getAppContext());

        kalaPhotoDAO.fetchKalaPhoto(mPresenter.getAppContext(), "GoodsInfoActivity", ccKalaCode, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                final int[] message = {0};


                if (arrayListData.size() > 0) {

                    Log.i("AFTER_RESPONSE", "run: inserting Into table 2");
                    if ((((KalaPhotoResult) arrayListData.get(0)).getImage()) != null) {
                        Log.i("AFTER_RESPONSE", "run: inserting Into table 3");
                        KalaPhotoModel kalaPhotoModel = new KalaPhotoModel();
                        kalaPhotoModel.setCcKalaPhotoDb(kalaPhoto);
                        kalaPhotoModel.setCcKalaCodeDb((((KalaPhotoResult) arrayListData.get(0)).getCcKalaCode()));
                        kalaPhotoModel.setImageDb(Base64.decode(((KalaPhotoResult) arrayListData.get(0)).getImage(), Base64.NO_WRAP));

                        boolean insertResult = kalaPhotoDAO.insert(kalaPhotoModel);

                        Log.i("AFTER_RESPONSE", "run: inserting Into table 4");
                        if (insertResult) {
                            Log.i("AFTER_RESPONSE", "run: inserting Into table 5");
                            Log.i("--UPDATING--TABLE--", "run: inserted successfully");
                            message[0] = 1;

                        } else {
                            Log.i("AFTER_RESPONSE", "run: inserting Into table 6");
                            Log.i("--UPDATING--TABLE--", "run: inserted fail");
                            message[0] = -1;

                        }
                    }
                }
                resultMessage[0] = message[0];
            }


            @Override
            public void onFailed(String type, String error) {
                Log.i("--UPDATING--TABLE--", "run: inserted fail" + type + " " + error + "ccKalaCode:" + ccKalaCode);
                setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type, error), "GoodsInfoModel", "GoodsInfoActivity", "updateGoodsImages", "onFailed");
                resultMessage[0] = -1;
            }
        });
        return resultMessage;

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








    /**
     I Used To Do GalleryUpdate With Async Task that contains multiple Chained Api Calls
     but it is a heavy process for AsyncTask and AsyncTask cause memory leaks on heavy processes such as multiple ApiCalls
     because of limitless thread generating
     instead work with thread_looper_handlers or RxJava
     */
    private void startUpdateGalleryProcess(KalaPhotoDAO kalaPhotoDAO) {
        try {
            mPresenter.startProgressBar();
            kalaPhotoDAO.deleteAll();
            ArrayList<Integer> messages = new ArrayList<>();
            ArrayList<Integer> allKalaCodes = kalaPhotoDAO.getAllKalaCodes();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    if (allKalaCodes != null && allKalaCodes.size() > 0) {
                        for (int i = 0; i < allKalaCodes.size(); i++) {
                            int kalaPhoto = i;
                            int kalaCode = allKalaCodes.get(i);

                            //make retrofit calls and insert images into table
                            int[] message = getKalaImageByCode(kalaPhoto, kalaCode);
                            messages.add(message[0]);


                            //publishing progress after each retrofit call end
                            int finalI = i;
                            PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(new PubFunc.ConcurrencyEvents() {
                                @Override
                                public void uiThreadIsReady() {
                                    mPresenter.updateProgress(((finalI * 100) / allKalaCodes.size()));

                                }
                            });

                        }
                        ArrayList<KalaPhotoModel> kalaPhotoModels = kalaPhotoDAO.getAll();
                        Log.i("KalaPhotoDaoGetAll", "run: "+kalaPhotoDAO.getCount() +"\n");

                        for (KalaPhotoModel kalaPhotoModel:kalaPhotoModels){
                            Log.i("KalaPhotoDaoGetAll", "run: "+kalaPhotoModel.toString()+"\n");
                        }
                        PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(new PubFunc.ConcurrencyEvents() {
                            @Override
                            public void uiThreadIsReady() {

                                mPresenter.onCompleteUpdateGallery(messages,kalaPhotoModels);

                            }
                        });

                    }

                }
            }.start();


        } catch (Exception exception) {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.getMessage(), "GoodsInfoModel", "GoodsInfoModel", "updateGallery", "onFailed");

        }
    }
}