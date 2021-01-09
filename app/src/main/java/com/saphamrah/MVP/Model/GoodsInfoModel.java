package com.saphamrah.MVP.Model;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.GoodsInfoMVP;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KalaPhotoDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.RptKalaInfoDAO;
import com.saphamrah.MVP.View.GoodsInfoActivity;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.Model.RptKalaInfoModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.LocalConfigShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.ServiceResponse.KalaPhotoResult;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class GoodsInfoModel implements GoodsInfoMVP.ModelOps
{

    RptKalaInfoDAO rptKalaInfoDAO = new RptKalaInfoDAO(BaseApplication.getContext());
    private GoodsInfoMVP.RequiredPresenterOps mPresenter;
    public static final String activityNameForLog= GoodsInfoActivity.class.getName();


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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void updateGallery() {
        KalaPhotoDAO kalaPhotoDAO = new KalaPhotoDAO(mPresenter.getAppContext());
//        startUpdateGalleryProcess(kalaPhotoDAO);
        startUpdateGalleryProcessRx(kalaPhotoDAO);
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
                    mPresenter.onUpdateGoodsList(true, mPresenter.getAppContext().getString(R.string.updateSuccessed));
                    getListOfGoods();
                } else if (msg.arg1 == -1) {
                    mPresenter.onUpdateGoodsList(false,mPresenter.getAppContext().getString(R.string.errorSaveData) );
                }
                return false;
            }
        });

        String ccMarkazSazmanSakhtarForosh = getccMarkazSazmanSakhtarForosh();
        if (ccMarkazSazmanSakhtarForosh == null || ccMarkazSazmanSakhtarForosh.trim().equals("")) {
            mPresenter.onUpdateGoodsList(false,mPresenter.getAppContext().getString( R.string.notFoundMarkazSazmanSakhtarForosh));
        } else {
            final RptKalaInfoDAO rptKalaInfoDAO = new RptKalaInfoDAO(mPresenter.getAppContext());
            rptKalaInfoDAO.fetchRptKalaInfoRx(mPresenter.getAppContext(), "GoodsInfoActivity", ccMarkazSazmanSakhtarForosh, new RxResponseHandler() {
                @Override
                public void onStart(Disposable disposable) {
                    super.onStart(disposable);
                }

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
                public void onFailed(String message, String type) {

                    mPresenter.onUpdateGoodsList(false,message );
                }

                @Override
                public void onComplete() {
                    super.onComplete();
                }
            });
        }
    }

    private String getccMarkazSazmanSakhtarForosh()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        if (foroshandehMamorPakhshModel == null)
        {
            mPresenter.onUpdateGoodsList(false,mPresenter.getAppContext().getString(R.string.errorFindForoshandehMamorPakhsh));
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

//        kalaPhotoDAO.fetchKalaPhoto(mPresenter.getAppContext(), "GoodsInfoActivity", ccKalaCode, new RetrofitResponse() {
//            @Override
//            public void onSuccess(ArrayList arrayListData) {
//                final int[] message = {0};
//
//
//                if (arrayListData.size() > 0) {
//
//                    Log.i("AFTER_RESPONSE", "run: inserting Into table 2");
//                    if ((((KalaPhotoResult) arrayListData.get(0)).getImage()) != null) {
//                        Log.i("AFTER_RESPONSE", "run: inserting Into table 3");
//                        KalaPhotoModel kalaPhotoModel = new KalaPhotoModel();
//                        kalaPhotoModel.setCcKalaPhotoDb(kalaPhoto);
//                        kalaPhotoModel.setCcKalaCodeDb((((KalaPhotoResult) arrayListData.get(0)).getCcKalaCode()));
//                        kalaPhotoModel.setImageDb(Base64.decode(((KalaPhotoResult) arrayListData.get(0)).getImage(), Base64.NO_WRAP));
//
//                        boolean insertResult = kalaPhotoDAO.insert(kalaPhotoModel);
//
//                        Log.i("AFTER_RESPONSE", "run: inserting Into table 4");
//                        if (insertResult) {
//                            Log.i("AFTER_RESPONSE", "run: inserting Into table 5");
//                            Log.i("--UPDATING--TABLE--", "run: inserted successfully");
//                            message[0] = 1;
//
//                        } else {
//                            Log.i("AFTER_RESPONSE", "run: inserting Into table 6");
//                            Log.i("--UPDATING--TABLE--", "run: inserted fail");
//                            message[0] = -1;
//
//                        }
//                    }
//                }
//                resultMessage[0] = message[0];
//            }
//
//
//            @Override
//            public void onFailed(String type, String error) {
//                Log.i("--UPDATING--TABLE--", "run: inserted fail" + type + " " + error + "ccKalaCode:" + ccKalaCode);
//                setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type, error), "GoodsInfoModel", "GoodsInfoActivity", "updateGoodsImages", "onFailed");
//                resultMessage[0] = -1;
//            }
//        });
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
 * I Used To Do GalleryUpdate With Async Task that contains multiple Chained Api Calls
 *      but it is a heavy process for AsyncTask
 *      instead work with thread_looper_handlers or RxJava
 * {@link startGalleryUpdate()}
 **/


/**
 public class AsyncGetImages extends AsyncTask<Void, Integer, ArrayList> {
 KalaPhotoDAO kalaPhotoDAO;
 ArrayList<KalaPhotoResult> KalaPhotoResult = new ArrayList<>();
 ArrayList<Integer> allKalaCodes = new ArrayList<>();
 ArrayList<KalaPhotoModel> kalaPhotoModels = new ArrayList<>();

 public AsyncGetImages(KalaPhotoDAO kalaPhotoDAO) {
 this.kalaPhotoDAO = kalaPhotoDAO;
 }


 @Override
 protected void onPreExecute() {
 super.onPreExecute();
 mPresenter.startProgressBar();
 }

 @Override
 protected ArrayList doInBackground(Void... voids) {
 kalaPhotoDAO.deleteAll();
 ArrayList<Integer> messages = new ArrayList<>();
 ArrayList<Integer> allKalaCodes = kalaPhotoDAO.getAllKalaCodes();


 if (allKalaCodes != null && allKalaCodes.size() > 0) {
 for (int i = 0; i < allKalaCodes.size(); i++) {
 int kalaPhoto = i;
 int kalaCode = allKalaCodes.get(i);

 //make retrofit calls and insert images into table
 int[] message = getKalaImageByCode(kalaPhoto, kalaCode);
 messages.add(message[0]);
 //publishing progress after each retrofit call end
 publishProgress(((i * 100) / allKalaCodes.size()));
 }

 } else {
 //if the ccKalaCodes has not been yet fetched finish process and in OnCancel...
 cancel(true);
 }
 return messages;
 }

 @Override
 protected void onCancelled() {
 super.onCancelled();
 //show error message because of empty ccKalaCodes
 mPresenter.onError(R.string.updateKalaInfoError);
 }

 @Override
 protected void onProgressUpdate(Integer... values) {
 super.onProgressUpdate(values);
 mPresenter.updateProgress(values[0]);
 }

 @Override
 protected void onPostExecute(ArrayList KalaPhotoResult) {
 new Handler().postDelayed(new Runnable() {
 @Override
 public void run() {
 mPresenter.onCompleteUpdateGallery(KalaPhotoResult);
 }
 }, 1000);

 cancel(true);

 }


 }
 **/



@RequiresApi(api = Build.VERSION_CODES.N)
private void startUpdateGalleryProcessRx(KalaPhotoDAO kalaPhotoDAO){


    ArrayList<Integer> ccKalaCodes=kalaPhotoDAO.getAllKalaCodes();
    ArrayList<KalaPhotoModel> kalaPhotoModels=new ArrayList<>();

     kalaPhotoDAO.fetchKalaPhotoRxJava(mPresenter.getAppContext(), activityNameForLog, ccKalaCodes, new RxResponseHandler() {
                 @Override
                 public void onStart(Disposable disposable) {
                     super.onStart(disposable);
                     mPresenter.startProgressBar();
                     kalaPhotoDAO.deleteAll();
                 }

                 @Override
                 public void onProgress(int progress)
                 {
                     mPresenter.updateProgress(progress);
                 }

                 @Override
                 public void onSuccess(ArrayList kalaPhotoModelsStream) {
                             KalaPhotoModel kalaPhotoModel= ((KalaPhotoModel) kalaPhotoModelsStream.get(0));
                     Log.i("KalaPhotoModel", "onSuccess: "+kalaPhotoModel.getImageDb());
                             boolean insertResult = kalaPhotoDAO.insert(kalaPhotoModel);

                             Log.i("AFTER_RESPONSE", "run: inserting Into table 4");
                             if (insertResult) {
                                 Log.i("AFTER_RESPONSE", "run: inserting Into table 5");
                                 Log.i("--UPDATING--TABLE--", "run: inserted successfully");
                                 kalaPhotoModels.add(kalaPhotoModel);
                             } else {
                                 Log.i("AFTER_RESPONSE", "run: inserting Into table 6");
                                 Log.i("--UPDATING--TABLE--", "run: inserted fail");
                                 mPresenter.onError(mPresenter.getAppContext().getString(R.string.errorGroupInsert));

                             }

                 }

                 @Override
                 public void onFailed(String message, String type) {
                    mPresenter.onError(message);
                 }

                 @Override
                 public void onComplete() {
                     super.onComplete();
                    mPresenter.onCompleteUpdateGallery(kalaPhotoModels);

                 }
             });
}

//        mPresenter.startProgressBar();
//    kalaPhotoDAO.fetchKalaPhotoRxJava(mPresenter.getAppContext(), GoodsInfoModel.class.getName(), ccKalaCodes, new IRetrofitRxResponse() {
//        @Override
//        public void onFinish() {
//            mPresenter.finishProgress();
//        }
//
//        @Override
//        public void onUpdate(int progress, Object response) {
//            mPresenter.updateProgress(progress);
//        }
//
//        @Override
//        public void onError(Object error) {
//
//        }
//
//        @Override
//        public void onFailed(Object type, Object error) {
//            Log.i("jiefipowf", "onFailed: "+type);
//        }
//
//
//    });


                 /**
                  * I Used To Do GalleryUpdate With Async Task that contains multiple Chained Api Calls
                  * but it is a heavy process for AsyncTask and AsyncTask cause memory leaks on heavy processes such as multiple ApiCalls
                  * because of limitless thread generating
                  * instead work with thread_looper_handlers or RxJava
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
                                     Log.i("KalaPhotoDaoGetAll", "run: " + kalaPhotoDAO.getCount() + "\n");

                                     for (KalaPhotoModel kalaPhotoModel : kalaPhotoModels) {
                                         Log.i("KalaPhotoDaoGetAll", "run: " + kalaPhotoModel.toString() + "\n");
                                     }
                                     PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(new PubFunc.ConcurrencyEvents() {
                                         @Override
                                         public void uiThreadIsReady() {
                                             mPresenter.finishProgress();
//TODO
//                                             mPresenter.onCompleteUpdateGallery( kalaPhotoModels,messages);

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

//    public  void startUpdatingGalleryRxjava(){
//        ArrayList<Integer> allKalaCodes = kalaPhotoDAO.getAllKalaCodes();
//
//    }
