package com.saphamrah.MVP.Model;


import static com.saphamrah.Utils.Constants.FAKTOR_GHATI;
import static com.saphamrah.Utils.Constants.FAKTOR_HAVALEH;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.GetProgramMVP;
import com.saphamrah.DAO.*;
import com.saphamrah.MVP.View.GetProgramActivity;
import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.BarkhordForoshandehBaMoshtaryModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.GetImageStringModel;
import com.saphamrah.Model.JayezehEntekhabiModel;
import com.saphamrah.Model.JayezehModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MandehMojodyMashinModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.MoshtaryAfradModel;
import com.saphamrah.Model.MoshtaryGharardadKalaModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.Network.RxNetwork.RxResponseHandler;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.PubFunc.DeviceInfo;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.GetProgramShared;
import com.saphamrah.Shared.LastOlaviatMoshtaryShared;
import com.saphamrah.Shared.LocalConfigShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.UIModel.OlaviatMorajehModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.ServiceResponse.GetLoginInfoCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.mail.internet.ParameterList;

import io.reactivex.disposables.Disposable;


public class GetProgramModel implements GetProgramMVP.ModelOps {
    private GetProgramMVP.RequiredPresenterOps mPresenter;

    private ForoshandehMamorPakhshModel foroshandehMamorPakhshModel;
    private int ccForoshandeh = 0;
    private int ccMamorPakhsh = 0;
    private int ccAfrad = 0;
    private int ccMarkazForosh = 0;
    private String ccMarkazForoshPakhsh = "-1";
    private int ccPosShomarehHesab = 0;
    private String ccTakhfifHajmis = "-1";
    private String ccTakhfifSenfis = "-1";
    private int ccMarkazAnbar = 0;
    private int ccMarkazSazmanForoshSakhtarForosh = 0;
    private int ccMarkazSazmanForosh = 0;
    private String ccMarkazSazmanForoshPakhsh = "-1";
    private int ccSazmanForosh = 0;
    private String ccSazmanForoshPakhsh = "-1";
    private String ccMasirs = "-1";
    private String ccMoshtarys = "-1,";
    private String ccGorohs = "347";
    private String ccDarkhastFaktors = "-1";
    private String ccDarkhastFaktorPakhsh = "-1";
    private String ccDarkhastFaktorsGhati = "-1";
    private String ccDarkhastFaktorsHavaleh = "-1";
    private String ccMoshtaryPakhsh = "-1";
    private String ccForoshandehString = "-1";
    private String[] foroshandehArray;
    private String anbarakAfrad = "-1";
    private int noeMasouliat = -1;
    private Calendar calendar;
    private String date;
    private String selectedDateGregorian;
    private String activityNameForLog = "GetProgramActivity";
    private int itemCounter;
    private int getProgramItemCount = 0; //count of all item that exist in string-array of resources
    private Handler handler;
    public static long responseSize = 0;

    private String ccdpBargashty = "-1,";

    public GetProgramModel(GetProgramMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getAllForoshandehMamorPakhsh() {
        final ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        final ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels = foroshandehMamorPakhshDAO.getAll();
        //final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
        ForoshandehMamorPakhshUtils foroshandehMamorPakhshUtil = new ForoshandehMamorPakhshUtils();
        for (int i = 0; i < foroshandehMamorPakhshModels.size(); i++) {
            int resId = foroshandehMamorPakhshUtil.getNoeForoshandehMamorPakhsh(foroshandehMamorPakhshModels.get(i));
            foroshandehMamorPakhshModels.get(i).setNameNoeForoshandehMamorPakhsh(mPresenter.getAppContext().getResources().getString(resId));
        }
        mPresenter.onGetAllForoshandehMamorPakhsh(foroshandehMamorPakhshModels);

        /*final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                final ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
                final ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels = foroshandehMamorPakhshDAO.getAll();
                final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();
                PubFunc.ForoshandehMamorPakhsh foroshandehMamorPakhshUtil = new PubFunc().new ForoshandehMamorPakhsh();
                for (int i=0 ; i < foroshandehMamorPakhshModels.size() ; i++)
                {
                    int resId = foroshandehMamorPakhshUtil.getNoeForoshandehMamorPakhsh(foroshandehMamorPakhshModels.get(i));
                    foroshandehMamorPakhshModels.get(i).setNameNoeForoshandehMamorPakhsh(mPresenter.getAppContext().getResources().getString(resId));
                }

                File storageDir = new File(Environment.getExternalStorageDirectory() + "/SapHamrah/Pictures/Profile/");
                if (storageDir.exists())
                {
                    if (storageDir.listFiles().length > 0)
                    {
                        storageDir = new File(Environment.getExternalStorageDirectory() + "/SapHamrah/Pictures/Profile/profile-" + foroshandehMamorPakhshModel.getCcAfrad() + ".jpg");
                        Bitmap profile = BitmapFactory.decodeFile(storageDir.getAbsolutePath());
                        final byte[] profileBytes = new PubFunc().new ImageUtils().convertBitmapToByteArray(mPresenter.getAppContext() , profile , Constants.BITMAP_TO_BYTE_QUALITY());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.onGetAllForoshandehMamorPakhsh(foroshandehMamorPakhshModels , profileBytes, foroshandehMamorPakhshModel.getCcAfrad());
                            }
                        });
                    }
                    else
                    {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.onGetAllForoshandehMamorPakhsh(foroshandehMamorPakhshModels , new byte[]{}, foroshandehMamorPakhshModel.getCcAfrad());
                            }
                        });
                    }
                }
                else
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.onGetAllForoshandehMamorPakhsh(foroshandehMamorPakhshModels , new byte[]{}, foroshandehMamorPakhshModel.getCcAfrad());
                        }
                    });
                }
            }
        }).start();*/
    }

    @Override
    public void getProgram(int getProgramType, String date, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        this.foroshandehMamorPakhshModel = foroshandehMamorPakhshModel;
        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());


        ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
        ccMamorPakhsh = foroshandehMamorPakhshModel.getCcMamorPakhsh();
        ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccPosShomarehHesab = foroshandehMamorPakhshModel.getCcPosShomarehHesab();
        ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbar();
        ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
        ccMasirs = "-1";
        ccMoshtarys = "-1,";
        ccGorohs = "347";
        ccDarkhastFaktors = "-1";
        ccDarkhastFaktorPakhsh = "-1";
        ccMoshtaryPakhsh = "-1";
        ccForoshandehString = "-1";
        anbarakAfrad = "-1";
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect());
        itemCounter = 0;
        this.date = date;
        selectedDateGregorian = "";
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);

        getProgramShared.putString(getProgramShared.SELECT_FOROSHANDEH(), String.valueOf(ccForoshandeh));
        foroshandehMamorPakhshDAO.updateIsSelect(ccForoshandeh);

        mPresenter.onGetNoeMasouliat(noeMasouliat);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessfullyGetNewProgramItem(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedGetProgram(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });

        try {
            String[] sepratedDate = date.split("/");
            PubFunc.DateConverter dateConverter = new PubFunc().new DateConverter();
            dateConverter.persianToGregorian(Integer.parseInt(sepratedDate[0]), Integer.parseInt(sepratedDate[1]), Integer.parseInt(sepratedDate[2]));
            String year = String.valueOf(dateConverter.getYear());
            String month = dateConverter.getMonth() > 9 ? String.valueOf(dateConverter.getMonth()) : "0" + dateConverter.getMonth();
            String day = dateConverter.getDay() > 9 ? String.valueOf(dateConverter.getDay()) : "0" + dateConverter.getDay();
            selectedDateGregorian = mPresenter.getAppContext().getResources().getString(R.string.dateWithSplashFormat, year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (noeMasouliat != 7) {
            getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.getProgramItems).length;
            deleteLogPPC(getProgramType, String.valueOf(ccForoshandeh));
        } else // if (noeMasouliat == 7) Amargar
        {
            getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.getProgramAmargarItems).length;
            deletePorseshnameh();
        }
    }


    @Override
    public void setProgramDateToShared() {
        GetProgramShared shared = new GetProgramShared(mPresenter.getAppContext());
        Log.d("getProgram", "date : " + date);
        Log.d("getProgram", "dateGregorian : " + selectedDateGregorian);
        shared.removeAll();
        shared.putString(shared.PERSIAN_DATE_OF_GET_PROGRAM(), date);
        shared.putString(shared.GREGORIAN_DATE_OF_GET_PROGRAM(), selectedDateGregorian);
    }

    @Override
    public void updateForoshandeh(final ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        int ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();

        PubFunc.DeviceInfo deviceInfo = new PubFunc().new DeviceInfo();
        final String deviceIMEI = deviceInfo.getIMEI(mPresenter.getAppContext());

        UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());
        //int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE() , 0); //0-Main , 1-Test
        String usingIMEI = userTypeShared.getString(userTypeShared.USING_IMEI(), deviceIMEI);

        final ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        foroshandehMamorPakhshDAO.fetchForoshandehMamorPakhshForUpdate(mPresenter.getAppContext(), GetProgramActivity.class.getSimpleName(), usingIMEI, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                            mPresenter.onSuccessUpdateForoshandeh();
                        } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                            mPresenter.onFailedUpdateForoshandeh(mPresenter.getAppContext().getResources().getString(R.string.errorUpdateForoshandeh));
                        }
                        return false;
                    }
                });

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = foroshandehMamorPakhshDAO.deleteAll();
                        boolean insertResult = foroshandehMamorPakhshDAO.insertGroup(arrayListData);

                        if (((ForoshandehMamorPakhshModel) arrayListData.get(0)).getNoeForoshandehMamorPakhsh() == 3) {
                            foroshandehMamorPakhshDAO.updateCanSetFaktorKharejAzMahal(((ForoshandehMamorPakhshModel) arrayListData.get(0)).getCcForoshandeh());
                        }

                        if (deleteResult && insertResult) {
                            Message message = new Message();
                            message.arg1 = Constants.BULK_INSERT_SUCCESSFUL();
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.arg1 = Constants.BULK_INSERT_FAILED();
                            handler.sendMessage(message);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                String message = type + "\n" + error;
                mPresenter.onFailedUpdateForoshandeh(message);
            }
        });

    }

    @Override
    public void updateKalaModatVosol(int getProgramType, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
        ccGorohs = "347";
        ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
        anbarakAfrad = "-1";
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect());
        itemCounter = 0;
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateKalaModatVosol).length;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateKalaModatVosolItem(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateKalaModatVosolItem(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });

        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());

        ccGorohs = moshtaryDAO.getAllccNoeSenf();
        Log.d("GetProgram", "ccGorohs before modat vosol: " + ccGorohs);
        getModatVosol(getProgramType, String.valueOf(ccMarkazSazmanForoshSakhtarForosh), ccGorohs);
    }

    @Override
    public void updateJayezehTakhfif(int getProgramType, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        itemCounter = 0;
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect());
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateJayezehTakhfif).length;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateJayezehTakhfifItem(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateJayezehTakhfif(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });
        getJayezeh(getProgramType);
    }

    @Override
    public void updateCustomers(int getProgramType, String date, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        this.foroshandehMamorPakhshModel = foroshandehMamorPakhshModel;
        ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
        ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        itemCounter = 0;
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateCustomers).length;
        ccMoshtarys = "-1,";
        ccMasirs = "-1";
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(mPresenter.getAppContext()).getIsSelect());
        this.date = date;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateCustomers(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateCustomers(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });

        try {
            String[] sepratedDate = date.split("/");
            PubFunc.DateConverter dateConverter = new PubFunc().new DateConverter();
            dateConverter.persianToGregorian(Integer.parseInt(sepratedDate[0]), Integer.parseInt(sepratedDate[1]), Integer.parseInt(sepratedDate[2]));
            String year = String.valueOf(dateConverter.getYear());
            String month = dateConverter.getMonth() > 9 ? String.valueOf(dateConverter.getMonth()) : "0" + dateConverter.getMonth();
            String day = dateConverter.getDay() > 9 ? String.valueOf(dateConverter.getDay()) : "0" + dateConverter.getDay();
            selectedDateGregorian = mPresenter.getAppContext().getResources().getString(R.string.dateWithSplashFormat, year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMasir(getProgramType, String.valueOf(ccForoshandeh), String.valueOf(ccMarkazForosh), selectedDateGregorian, selectedDateGregorian, "1");
    }


    @Override
    public void updateParameter(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        ccMarkazAnbar = foroshandehMamorPakhshModel.getCcMarkazAnbarVosol();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        itemCounter = -1;
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateParameters).length;
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateParameters(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateParameters(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });
        getParameter(Constants.GET_PROGRAM_UPDATE_PARAMETERS());
    }

    @Override
    public void updateEtebarForoshandeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateEtebarForoshandeh).length;
        itemCounter = -1;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateEtebarForoshandeh(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateEtebarForoshandeh(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });
        getEtebar(Constants.GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH());
    }

    @Override
    public void updateGharardadKalaMosavabeh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel) {
        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
        getProgramItemCount = mPresenter.getAppContext().getResources().getStringArray(R.array.updateMoshtaryGharardadKalaMosavabeh).length;
        itemCounter = -1;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessUpdateGharardadKalaMosavabeh(getProgramItemCount, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedUpdateGharardadKalaMosavabeh(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });
        prepareToGetMoshtaryGharardad(Constants.GET_PROGRAM_UPDATE_GHARARDAD_KALAMOSAVABEH(), foroshandehMamorPakhshModel.getCcForoshandeh());
    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild) {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void clearRam() {

    }

    @Override
    public void releaseResources() {

    }

    @Override
    public void getProgramServiceType() {
        SystemConfigTabletDAO systemConfigTabletDAO = new SystemConfigTabletDAO(mPresenter.getAppContext());
        int service = systemConfigTabletDAO.getProgramService();
        mPresenter.onGetProgramServiceType(service);
    }


    ////////////////////////// GET PROGRAM //////////////////////////

    private void sendThreadMessage(int status, int itemIndex) {
        Message message = new Message();
        message.arg1 = status;
        message.arg2 = itemIndex;
        handler.sendMessage(message);
    }


    private void deleteLogPPC(final int getProgramType, String ccForoshandeh) {
        LogPPCDAO logPPCDAO = new LogPPCDAO(mPresenter.getAppContext());
        logPPCDAO.deleteAll();
        deleteAdamDarkhast(getProgramType, ccForoshandeh);
    }

    private void deleteAdamDarkhast(int getProgramType, String ccForoshandeh) {
        AdamDarkhastDAO adamDarkhastDAO = new AdamDarkhastDAO(mPresenter.getAppContext());
        adamDarkhastDAO.deleteAll();
        getBank(getProgramType);
    }

    private void getBank(final int getProgramType) {
        final BankDAO bankDAO = new BankDAO(mPresenter.getAppContext());
        bankDAO.fetchBank(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = bankDAO.deleteAll();
                        boolean insertResult = bankDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), itemCounter);
                            getBrand(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getBrand(final int getProgramType) {
        final BrandDAO brandDAO = new BrandDAO(mPresenter.getAppContext());
        brandDAO.fetchBrand(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = brandDAO.deleteAll();
                        boolean insertResult = brandDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getElatAdamDarkhast(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getElatAdamDarkhast(final int getProgramType) {
        final ElatAdamDarkhastDAO elatAdamDarkhastDAO = new ElatAdamDarkhastDAO(mPresenter.getAppContext());
        elatAdamDarkhastDAO.fetchElatAdamDarkhast(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = elatAdamDarkhastDAO.deleteAll();
                        boolean insertResult = elatAdamDarkhastDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getElatMarjoeeKala(getProgramType);
                        } else {
                            sendThreadMessage(Constants.FAILED_MESSAGE(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getElatMarjoeeKala(final int getProgramType) {
        final ElatMarjoeeKalaDAO marjoeeKalaDAO = new ElatMarjoeeKalaDAO(mPresenter.getAppContext());
        marjoeeKalaDAO.fetchElatMarjoeeKala(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = marjoeeKalaDAO.deleteAll();
                        boolean insertResult = marjoeeKalaDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getGoroh(getProgramType);
                        } else {
                            sendThreadMessage(Constants.FAILED_MESSAGE(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getGoroh(final int getProgramType) {
        final GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        gorohDAO.fetchAllGoroh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = gorohDAO.deleteAll();
                        boolean insertResult = gorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            deleteGPSDataMashin(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void deleteGPSDataMashin(int getProgramType) {
        GPSDataMashinDAO gpsDataMashinDAO = new GPSDataMashinDAO(mPresenter.getAppContext());
        gpsDataMashinDAO.deleteAll();
        getGPSData(getProgramType, String.valueOf(ccForoshandeh), String.valueOf(ccMamorPakhsh));
    }

    private void getGPSData(final int getProgramType, String ccForoshandeh, String ccMamorPakhsh) {
        GPSDataPpcDAO gpsDataPpcDAO = new GPSDataPpcDAO(mPresenter.getAppContext());
        gpsDataPpcDAO.deleteSendedRecords();
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getMahal(getProgramType, String.valueOf(ccMarkazSazmanForosh));
    }

    private void getMahal(final int getProgramType, String ccMarkazSazmanForosh) {
        final MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        mahalDAO.fetchAllMahalByccMarkazForosh(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = mahalDAO.deleteAll();
                        boolean insertResult = mahalDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMarkazPakhsh(getProgramType, String.valueOf(ccMarkazForosh), String.valueOf(ccMarkazAnbar));
                            Log.d("GetProgram", "ccMarkazForosh:" + ccMarkazForosh + " ,ccMarkazAnbar:" + ccMarkazAnbar);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMarkazPakhsh(final int getProgramType, String ccMarkazForosh, final String ccMarkazAnbar) {
        final MarkazDAO markazDAO = new MarkazDAO(mPresenter.getAppContext());
        markazDAO.fetchAllMarkaz(mPresenter.getAppContext(), activityNameForLog, ccMarkazForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = markazDAO.deleteAll();
                        boolean insertResult = markazDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            getMarkazAnbar(getProgramType, ccMarkazAnbar);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMarkazAnbar(final int getProgramType, String ccMarkazAnbar) {
        final MarkazDAO markazDAO = new MarkazDAO(mPresenter.getAppContext());
        markazDAO.fetchAllMarkaz(mPresenter.getAppContext(), activityNameForLog, ccMarkazAnbar, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean insertResult = markazDAO.insertGroup(arrayListData);
                        if (insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMarkazShomareHesab(getProgramType, String.valueOf(ccMarkazAnbar));
                            Log.d("GetProgram", "ccMarkazAnbar:" + ccMarkazAnbar);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMarkazShomareHesab(final int getProgramType, final String ccMarkazAnbar) {
        final MarkazShomarehHesabDAO markazShomarehHesabDAO = new MarkazShomarehHesabDAO(mPresenter.getAppContext());
        markazShomarehHesabDAO.fetchMarkazShomareHesab(mPresenter.getAppContext(), activityNameForLog, ccMarkazAnbar, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = markazShomarehHesabDAO.deleteAll();
                        boolean insertResult = markazShomarehHesabDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getPOSInfo(getProgramType, String.valueOf(ccPosShomarehHesab));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getPOSInfo(final int getProgramType, String ccPosShomarehHesab) {
        final PosShomarehHesabDAO posShomarehHesabDAO = new PosShomarehHesabDAO(mPresenter.getAppContext());
        posShomarehHesabDAO.fetchPosShomareHesab(mPresenter.getAppContext(), activityNameForLog, ccPosShomarehHesab, String.valueOf(ccMarkazAnbar), new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = posShomarehHesabDAO.deleteAll();
                        boolean insertResult = posShomarehHesabDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMasir(getProgramType, String.valueOf(ccForoshandeh), String.valueOf(ccMarkazForosh), selectedDateGregorian, selectedDateGregorian, "1");
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMasir(final int getProgramType, final String ccForoshandeh, String ccMarkazForosh, String azTarikh, String taTarikh, String codeNoe) {
        final MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        masirDAO.fetchAllMasir(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, ccMarkazForosh,
                azTarikh, taTarikh, codeNoe, new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                boolean deleteResult = masirDAO.deleteAll();
                                boolean insertResult = masirDAO.insertGroup(arrayListData);
                                boolean updateTarikhMasirResult = masirDAO.updateTarikhMasir(date);
                                ArrayList<MasirModel> masirModels = arrayListData;
                                for (MasirModel masir : masirModels) {
                                    ccMasirs += "," + masir.getCcMasir();
                                }

                                if (deleteResult && insertResult && updateTarikhMasirResult) {
                                    if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), itemCounter);
                                        getMoshtary(getProgramType, ccForoshandeh, ccMasirs, "-1");
                                    } else {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        getForoshandehPolygon(getProgramType, String.valueOf(ccForoshandeh));
                                    }
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                    }
                });
    }

    private void getForoshandehPolygon(final int getProgramType, String ccForoshandeh) {
        final PolygonForoshSatrDAO polygonForoshSatrDAO = new PolygonForoshSatrDAO(mPresenter.getAppContext());
        polygonForoshSatrDAO.fetchAllvPolygonForoshSatrByForoshandeh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = polygonForoshSatrDAO.deleteAll();
                        boolean insertResult = polygonForoshSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMasirVaznHajmMashin(getProgramType, ccMasirs);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMasirVaznHajmMashin(final int getProgramType, final String ccMasirs) {
        final MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
        masirVaznHajmMashinDAO.fetchMasirVaznHajmMashin(mPresenter.getAppContext(), activityNameForLog, ccMasirs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = masirVaznHajmMashinDAO.deleteAll();
                        boolean insertResult = masirVaznHajmMashinDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getFoshandehMoshtary(getProgramType, ccMasirs);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    int globalCounter = 0;

    private void getFoshandehMoshtary(final int getProgramType, String ccMasirs) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        deleteMojodiGiri(getProgramType);
    }


    private void deleteMojodiGiri(final int getProgramType) {
        final MojoodiGiriDAO mojoodiGiriDAO = new MojoodiGiriDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = mojoodiGiriDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMoshtary(getProgramType, String.valueOf(ccForoshandeh), ccMasirs, "-1");
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }


    private void getMoshtary(final int getProgramType, String ccForoshandeh, String ccMasir, String codeMoshtary) {
        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        moshtaryDAO.fetchAllMoshtaryByccMasir(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, ccMasir, codeMoshtary, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryDAO.deleteAll();
                        boolean insertResult = moshtaryDAO.insertGroup(arrayListData);
                        boolean updateOlaviatResult = moshtaryDAO.updateExtraOlaviatFromOlaviat();
                        boolean updateToorVisitResult = true;//moshtaryDAO.updateToorVisitMoshtary();
                        Log.d("getProgram",deleteResult +","+ insertResult +","+ updateOlaviatResult +","+ updateToorVisitResult);
                        if (deleteResult && insertResult && updateOlaviatResult && updateToorVisitResult) {
                            for (int i = 0; i < arrayListData.size(); i++) {
                                ccMoshtarys += ((MoshtaryModel) arrayListData.get(i)).getCcMoshtary() + ",";
                                Log.d("getProgram",ccMoshtarys);

                            }
                            if (ccMoshtarys.length() != 0) {
                                ccMoshtarys = ccMoshtarys.substring(0, ccMoshtarys.length() - 1);
                            }
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryParent(getProgramType, ccMoshtarys);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();

            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryParent(final int getProgramType, String ccMoshtarys) {


//        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
//        moshtaryDAO.fetchMoshtaryParent(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, new RetrofitResponse()
//        {
//            @Override
//            public void onSuccess(final ArrayList arrayListData)
//            {
        Thread thread = new Thread() {
            @Override
            public void run() {
                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                deleteMoshtaryAmargarImage(getProgramType);

//                        int successfulUpdateCounter = 0;
//                        for (int i = 0 ; i < arrayListData.size() ; i++)
//                        {
//                            boolean updateResult = moshtaryDAO.updateccMoshtaryParentInMoshtary(arrayListData);
//                            if (updateResult)
//                            {
//                                successfulUpdateCounter++;
//                            }
//                        }

//                        boolean result = true;//moshtaryDAO.updateccMoshtaryParentInMoshtary(arrayListData);
//                        if (result)
//                        {
//                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
//                            deleteMoshtaryAmargarImage(getProgramType);
//                        }
//                        else
//                        {
//                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
//                        }

            }
        };
        thread.start();
//            }
//            @Override
//            public void onFailed(String type, String error)
//            {
//                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
//            }
//        });
    }


    private void deleteMoshtaryAmargarImage(final int getProgramType) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                MoshtaryAmargarImageDAO moshtaryAmargarImageDAO = new MoshtaryAmargarImageDAO(mPresenter.getAppContext());
                MoshtaryAmargarImageTmpDAO moshtaryAmargarImageTmpDAO = new MoshtaryAmargarImageTmpDAO(mPresenter.getAppContext());

                boolean deleteMoshtaryAmargarImage = moshtaryAmargarImageDAO.deleteAll();
                boolean deleteMoshtaryAmargarImageTemp = moshtaryAmargarImageTmpDAO.deleteAll();

                if (deleteMoshtaryAmargarImage && deleteMoshtaryAmargarImageTemp) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMoshtaryAddress(getProgramType, ccMoshtarys, String.valueOf(ccMarkazSazmanForosh));
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }

    private void getMoshtaryAddress(final int getProgramType, final String ccMoshtarys, String ccMarkazSazmanForosh) {
        final MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        moshtaryAddressDAO.fetchAllvMoshtaryAddressByNoeMasouliat(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccForoshandeh), ccMasirs, "-1", new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryAddressDAO.deleteAll();
                        boolean insertResult = moshtaryAddressDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryAfrad(getProgramType, ccMoshtarys);
//                            if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY())
//                            {
//                                getMoshtaryEtebarSazmanForosh(getProgramType , ccMoshtarys , String.valueOf(ccSazmanForosh));
//                            }
//                            else
//                            {
//                                getMoshtaryAfrad(getProgramType , ccMoshtarys);
//                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryAfrad(final int getProgramType, final String ccMoshtarys) {
        final MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        Log.d("getprogram afrad",ccMoshtarys);

        moshtaryAfradDAO.fetchAllvMoshtaryAfrad(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryAfradDAO.deleteAll();
                        boolean insertResult = moshtaryAfradDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryEtebarSazmanForosh(getProgramType, ccMoshtarys, String.valueOf(ccSazmanForosh));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMoshtaryEtebarSazmanForosh(final int getProgramType, final String ccMoshtarys, String ccSazmanForosh) {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForosh(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, ccSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryEtebarSazmanForoshDAO.deleteAll();
                        boolean insertResult = moshtaryEtebarSazmanForoshDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryGoroh(getProgramType, ccMoshtarys);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMoshtaryGoroh(final int getProgramType, final String ccMoshtarys) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
            getBargashty(getProgramType);
        } else {
            getMoshtaryPolygon(getProgramType, ccMasirs, ccMoshtarys);
        }
    }


    private void getMoshtaryPolygon(final int getProgramType, String ccMasirs, final String ccMoshtarys) {
        final MoshtaryPolygonDAO moshtaryPolygonDAO = new MoshtaryPolygonDAO(mPresenter.getAppContext());
        moshtaryPolygonDAO.fetchMoshtaryPolygon(mPresenter.getAppContext(), activityNameForLog, ccMasirs, ccMoshtarys, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryPolygonDAO.deleteAll();
                        boolean insertResult = moshtaryPolygonDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryShomareHesab(getProgramType, ccMoshtarys);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMoshtaryShomareHesab(final int getProgramType, String ccMoshtarys) {
        final MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        moshtaryShomarehHesabDAO.fetchAllvMoshtaryShomarehHesab(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryShomarehHesabDAO.deleteAll();
                        boolean insertResult = moshtaryShomarehHesabDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            deleteMoshtaryTaghirat(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void deleteMoshtaryTaghirat(final int getProgramType) {
        final MoshtaryTaghiratDAO moshtaryTaghiratDAO = new MoshtaryTaghiratDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = moshtaryTaghiratDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getDarkhastFaktor(getProgramType, String.valueOf(ccAfrad), ccMoshtarys);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }


    //TODO get MoshtaryBrand

    private void getDarkhastFaktor(final int getProgramType, String ccAfrad, String ccMoshtarys) {
        Log.d("getProgram", "ccMoshtarys: " + ccMoshtarys);
        final DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        darkhastFaktorDAO.fetchDarkhastFaktor(mPresenter.getAppContext(), activityNameForLog, ccAfrad, ccMoshtarys, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = darkhastFaktorDAO.deleteAll();
                        boolean insertResult = darkhastFaktorDAO.insertGroupFromGetProgram(arrayListData, noeMasouliat);
                        if (deleteResult && insertResult) {
                            final DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(mPresenter.getAppContext());
                            darkhastFaktorEmzaMoshtaryDAO.deleteAll();
                            ImageDarkhastFaktorDAO imageDarkhastFaktorDAO = new ImageDarkhastFaktorDAO(mPresenter.getAppContext());
                            for (int i = 0; i < arrayListData.size(); i++) {
                                final DarkhastFaktorModel darkhastFaktorModel = (DarkhastFaktorModel) arrayListData.get(i);

                                /**  separate havaleh and tafkik **/
                                if (darkhastFaktorModel.getCcDarkhastFaktorNoeForosh() == FAKTOR_GHATI){
                                    ccDarkhastFaktorsGhati += "," + darkhastFaktorModel.getCcDarkhastFaktor();
                                }
                                if (darkhastFaktorModel.getCcDarkhastFaktorNoeForosh() == FAKTOR_HAVALEH){
                                    ccDarkhastFaktorsHavaleh += "," + darkhastFaktorModel.getCcDarkhastFaktor();
                                }
                                /**  separate havaleh and tafkik **/

                                if (darkhastFaktorModel.getFaktorRooz() == 0) {
                                    getImageDarkhastFaktor(imageDarkhastFaktorDAO, darkhastFaktorEmzaMoshtaryDAO, darkhastFaktorModel.getCodeVazeiat(), darkhastFaktorModel.getCcDarkhastFaktor(), darkhastFaktorModel.getCcMoshtary(), darkhastFaktorModel.getCcDarkhastFaktorNoeForosh());
                                    ccDarkhastFaktors += "," + darkhastFaktorModel.getCcDarkhastFaktor();
                                }
                                if (darkhastFaktorModel.getForTasviehVosol() == 1) {
                                    ccDarkhastFaktorPakhsh += "," + darkhastFaktorModel.getCcDarkhastFaktor();
                                    ccForoshandehString += "," + darkhastFaktorModel.getCcForoshandeh();
                                }
                                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
                                if (moshtaryDAO.getByccMoshtary(darkhastFaktorModel.getCcMoshtary()).getCcMoshtary() == 0) {
                                    ccMoshtaryPakhsh += "," + darkhastFaktorModel.getCcMoshtary();
                                    ccSazmanForoshPakhsh += "," + darkhastFaktorModel.getCcSazmanForosh();
                                    ccMarkazForoshPakhsh += "," + darkhastFaktorModel.getCcMarkazForosh();
                                    if (!ccMarkazSazmanForoshPakhsh.contains(String.valueOf(darkhastFaktorModel.getCcMarkazSazmanForosh()))) {
                                        ccMarkazSazmanForoshPakhsh += "," + darkhastFaktorModel.getCcMarkazSazmanForosh();
                                    }
                                }
                            }
                            Log.d("getProgram", "ccDarkhastFaktors : -1 + " + ccDarkhastFaktors);
                            Log.d("getProgram", "ccMoshtaryPakhsh : " + ccMoshtaryPakhsh);
                            Log.d("getProgram", "ccSazmanForoshPakhsh : " + ccSazmanForoshPakhsh);
                            Log.d("getProgram", "ccMarkazForoshPakhsh : " + ccMarkazForoshPakhsh);
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getDarkhastFaktorSatr(getProgramType, ccDarkhastFaktors);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();

            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getImageDarkhastFaktor(ImageDarkhastFaktorDAO imageDarkhastFaktorDAO, final DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO, int codeVazeiatFaktor, final long ccDarkhastFaktorHavaleh, final int ccMoshtary, final int ccDarkhastFaktorNoeForosh) {
        if (codeVazeiatFaktor != Constants.CODE_VAZEIAT_FAKTOR_TASVIEH) {
            String ccDarkhastFaktor = "0";
            String ccDarkhastHavaleh = "0";
            if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat == 8) {
                ccDarkhastFaktor = String.valueOf(ccDarkhastFaktorHavaleh);
            } else if ((noeMasouliat == 4 || noeMasouliat == 5) && ccDarkhastFaktorNoeForosh == Constants.ccNoeHavale) {
                ccDarkhastHavaleh = String.valueOf(ccDarkhastFaktorHavaleh);
            }else if ((noeMasouliat == 4 || noeMasouliat == 5) && ccDarkhastFaktorNoeForosh == Constants.ccNoeFaktor) {
                ccDarkhastFaktor = String.valueOf(ccDarkhastFaktorHavaleh);
            }
            Log.d("getProgram", "ccDarkhastFaktorNoeForosh : " + ccDarkhastFaktorNoeForosh );

            Log.d("getProgram", "getImageDarkhastFaktor ccDarkhastFaktors : " + ccDarkhastFaktor + " , ccDarkhastHavaleh:" + ccDarkhastHavaleh);
            imageDarkhastFaktorDAO.fetchGetImageJSON(mPresenter.getAppContext(), "GetProgramActivity", ccDarkhastFaktor, ccDarkhastHavaleh, new RetrofitResponse() {
                @Override
                public void onSuccess(ArrayList arrayListData) {
                    if (arrayListData != null && arrayListData.size() > 0) {
                        try {
                            DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel = new DarkhastFaktorEmzaMoshtaryModel();
                            darkhastFaktorEmzaMoshtaryModel.setCcDarkhastFaktor(ccDarkhastFaktorHavaleh);
                            darkhastFaktorEmzaMoshtaryModel.setCcMoshtary(ccMoshtary);
                            darkhastFaktorEmzaMoshtaryModel.setDarkhastFaktorImage(Base64.decode(((GetImageStringModel) arrayListData.get(0)).getImage(), Base64.NO_WRAP));
                            darkhastFaktorEmzaMoshtaryModel.setHave_FaktorImage(1);
                            darkhastFaktorEmzaMoshtaryDAO.insert(darkhastFaktorEmzaMoshtaryModel);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "GetProgramModel", "", "getDarkhastFaktor", "imageDarkhastFaktorDAO.fetchImageStringForMamorPakhsh.onResponse");
                        }
                    }
                }

                @Override
                public void onFailed(String type, String error) {
                    setLogToDB(Constants.LOG_EXCEPTION(), type + "\n" + error, "GetProgramModel", "", "getDarkhastFaktor", "imageDarkhastFaktorDAO.fetchImageStringForMamorPakhsh.onFailed");
                }
            });
        }
    }

    private void getDarkhastFaktorSatr(final int getProgramType, String ccDarkhastFaktors) {
        Log.d("getProgram", "ccDarkhastFaktors : " + ccDarkhastFaktors);
        final DarkhastFaktorSatrDAO darkhastFaktorSatrDAO = new DarkhastFaktorSatrDAO(mPresenter.getAppContext());
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        String ccDarkhastFaktorNoeFaktor = darkhastFaktorDAO.getccDarkhastFaktorsByNoeFaktorHavale(Constants.ccNoeFaktor);
        String ccDarkhastFaktorNoeHavale = darkhastFaktorDAO.getccDarkhastFaktorsByNoeFaktorHavale(Constants.ccNoeHavale);
        final Map<Integer, Boolean> mapResult = new HashMap<>();
        mapResult.put(Constants.ccNoeFaktor, true);
        mapResult.put(Constants.ccNoeHavale, true);
        final boolean deleteResult = darkhastFaktorSatrDAO.deleteAll();
        Log.d("getProgram", "ccDarkhastFaktorNoeFaktor:" + ccDarkhastFaktorNoeFaktor + " , ccDarkhastFaktorNoeHavale:" + ccDarkhastFaktorNoeHavale);
        if (!ccDarkhastFaktorNoeFaktor.trim().equals("")) {
            Log.d("getProgram", "ccDarkhastFaktorNoeFaktor:" + ccDarkhastFaktorNoeFaktor);
            darkhastFaktorSatrDAO.fetchDarkhastFaktorSatr(mPresenter.getAppContext(), activityNameForLog, String.valueOf(Constants.ccNoeFaktor), ccDarkhastFaktorNoeFaktor, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            Log.d("getProgram", "insert faktor:" + String.valueOf(Constants.ccNoeFaktor) + "-" + arrayListData.toString());
                            //boolean deleteResult = darkhastFaktorSatrDAO.deleteAll();
                            boolean insertResult = darkhastFaktorSatrDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                mapResult.put(Constants.ccNoeFaktor, true);
                            } else {
                                mapResult.put(Constants.ccNoeFaktor, false);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        }

        if (!ccDarkhastFaktorNoeHavale.trim().equals("")) {
            Log.d("getProgram", "ccDarkhastFaktorNoeHavale:" + ccDarkhastFaktorNoeHavale);
            darkhastFaktorSatrDAO.fetchDarkhastFaktorSatr(mPresenter.getAppContext(), activityNameForLog, String.valueOf(Constants.ccNoeHavale), ccDarkhastFaktorNoeHavale, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            Log.d("getProgram", "insert Havaleh:" + String.valueOf(Constants.ccNoeHavale + "-" + arrayListData.toString()));
                            //boolean deleteResult = darkhastFaktorSatrDAO.deleteAll();
                            boolean insertResult = darkhastFaktorSatrDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                mapResult.put(Constants.ccNoeHavale, true);
                                Log.d("getProgram", "deleteResult success:" + deleteResult + ", insertResult:" + insertResult);
                            } else {
                                mapResult.put(Constants.ccNoeHavale, false);
                            }

                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        }

        if (mapResult.get(Constants.ccNoeFaktor) && mapResult.get(Constants.ccNoeHavale)) {
            Log.d("getProgram", "ccDarkhastFaktorNoeHavale Success");
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getForoshandehs(getProgramType, ccForoshandehString);
        } else {
            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
        }
    }


    private void getForoshandehs(final int getProgramType, String ccForoshandehString) {
        final ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());
        Log.d("getProgram", "ccForoshandehs : " + foroshandehMamorPakhshModel.getCcForoshandehs());
        if (new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel) != 4) {
            if (foroshandehMamorPakhshModel.getCcForoshandehs().contains(",")) {
                foroshandehArray = foroshandehMamorPakhshModel.getCcForoshandehs().split(",");//از فروشنده مامور پخش
            } else {
                foroshandehArray = new String[]{foroshandehMamorPakhshModel.getCcForoshandehs()};
            }
        } else {
            if (ccForoshandehString.contains(",")) {
                foroshandehArray = ccForoshandehString.split(",");//از فروشنده مامور پخش
            } else {
                foroshandehArray = new String[]{ccForoshandehString};
            }
        }
        final ArrayList<ForoshandehModel> foroshandehModels = new ArrayList<>();
        globalCounter = 0;

        //remove duplicated ccForoshandeh
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(foroshandehArray));

        foroshandehArray = hashSet.toArray(new String[hashSet.size()]);
        for (String ccForoshandeh : foroshandehArray) {
            Log.d("GetProgram", "foroshandehArray_GetForoshandeh:" + ccForoshandeh);
            foroshandehDAO.fetchForoshandeh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            if (arrayListData != null && arrayListData.size() > 0) {
                                for (int i = 0; i < arrayListData.size(); i++) {
                                    foroshandehModels.add((ForoshandehModel) arrayListData.get(i));
                                }
                            }
                            globalCounter++;
                            if (globalCounter == foroshandehArray.length) {
                                boolean deleteResult = foroshandehDAO.deleteAll();
                                if (foroshandehModels != null && foroshandehModels.size() > 0) {
                                    boolean insertResult = foroshandehDAO.insertGroup(foroshandehModels);
                                    if (deleteResult && insertResult) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        getdarkhastFaktorKalaPishnahadi(getProgramType, ccForoshandeh, ccMoshtarys);
                                    } else {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    }
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    getdarkhastFaktorKalaPishnahadi(getProgramType, ccForoshandeh, ccMoshtarys);
                                }
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        }
    }


    private void getdarkhastFaktorKalaPishnahadi(int getProgramType, String ccForoshandeh, String ccMoshtarys) {

        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat == 8) {


            DarkhastFaktorKalaPishnahadiDAO darkhastFaktorKalaPishnahadiDAO = new DarkhastFaktorKalaPishnahadiDAO(BaseApplication.getContext());
            darkhastFaktorKalaPishnahadiDAO.fetchDarkhastFaktorKalaPishnahadi(BaseApplication.getContext(), activityNameForLog, ccForoshandeh, ccMoshtarys, new RetrofitResponse() {
                @Override
                public void onSuccess(ArrayList arrayListData) {
                    {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                if (arrayListData != null && arrayListData.size() > 0) {
                                    boolean deleteResult = darkhastFaktorKalaPishnahadiDAO.deleteAll();
                                    boolean insertResult = darkhastFaktorKalaPishnahadiDAO.insertGroup(arrayListData);
                                    if (deleteResult && insertResult) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        getListKalaForMarjoee(getProgramType);
                                    } else {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    }
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });

        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getListKalaForMarjoee(getProgramType);
        }

    }


    private void getListKalaForMarjoee(final int getProgramType) {
        //String ccForoshandeh = String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh());
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        String ccForoshandehs = darkhastFaktorDAO.getAllccForoshandeh();
        Log.d("GetProgram", "ccForoshandehs:" + ccForoshandehs);
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            final ListKalaForMarjoeeDAO listKalaForMarjoeeDAO = new ListKalaForMarjoeeDAO(mPresenter.getAppContext());
            Log.d("GetProgram", "MarjoeeKala-ccForoshandehs:" + ccForoshandehs);
            listKalaForMarjoeeDAO.fetchListKala(mPresenter.getAppContext(), "GetProgramActivity", ccForoshandehs, "-1", new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            if (arrayListData != null && arrayListData.size() > 0) {
                                boolean deleteResult = listKalaForMarjoeeDAO.deleteAll();
                                boolean insertResult = listKalaForMarjoeeDAO.insertGroup(arrayListData);
                                if (deleteResult && insertResult) {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    getBargashty(getProgramType);
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                }
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getBargashty(getProgramType);
                            }
                        }
                    };
                    thread.start();
                }


                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getBargashty(getProgramType);
            return;
        }

//        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
//        getBargashty(getProgramType);
    }

    private void getBargashty(final int getProgramType) {
        final BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat == 8) {
            bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccForoshandeh), new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = bargashtyDAO.deleteAll();
                            boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                    getMoshtaryChidman(getProgramType, ccMasirs);
                                } else {
                                    getMoshtaryPakhsh(getProgramType);
                                }
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else if (noeMasouliat == 4 || noeMasouliat == 5) {
            boolean deleteResult = bargashtyDAO.deleteAll();
            globalCounter = 0;
            for (String strccForoshandeh : foroshandehArray) {
                bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), activityNameForLog, strccForoshandeh, new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                boolean insertResult = bargashtyDAO.insertGroup(arrayListData);
                                if (insertResult) {
                                    globalCounter++;
                                    if (globalCounter == foroshandehArray.length) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                            getMoshtaryChidman(getProgramType, ccMasirs);
                                        } else {
                                            getMoshtaryPakhsh(getProgramType);
                                        }
                                    }
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                    }
                });
            }
        }
    }

    private void getMoshtaryPakhsh(final int getProgramType) {
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        final Map<Integer, String> map = darkhastFaktorDAO.getccMoshtaryPakhshForForoshandeh(Constants.CODE_VAZEIAT_FAKTOR_TASVIEH);
        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        globalCounter = 0;
        if (map.size() > 0) {
            for (final Integer ccForoshandeh : map.keySet()) {
                final String codeMoshtary = map.get(ccForoshandeh);
                if (codeMoshtary != null && !codeMoshtary.trim().equals("")) {
                    Log.d("getProgram", "ccForoshandeh: " + ccForoshandeh + " , codeMoshtary: " + codeMoshtary);
                    moshtaryDAO.fetchAllMoshtaryByccMasir(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccForoshandeh), "-1", codeMoshtary.replace("'", ""), new RetrofitResponse() {
                        @Override
                        public void onSuccess(final ArrayList arrayListData) {
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    boolean deleteResult = moshtaryDAO.deleteByCodeMoshtarys(codeMoshtary);
                                    boolean insertResult = moshtaryDAO.insertGroup(arrayListData);
                                    boolean updateOlaviatResult = moshtaryDAO.updateExtraOlaviatFromOlaviat();
                                    if (insertResult) {
                                        globalCounter++;
                                        if (globalCounter == map.size()) {
                                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                            getMoshtaryMorajeShodehRooz(getProgramType, String.valueOf(ccForoshandeh), ccMasirs);
                                        }
                                    } else {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    }
                                }
                            };
                            thread.start();
                        }

                        @Override
                        public void onFailed(String type, String error) {
                            mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                        }
                    });
                } else {
                    globalCounter++;
                    if (globalCounter == map.size()) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        getMoshtaryMorajeShodehRooz(getProgramType, String.valueOf(ccForoshandeh), ccMasirs);
                    }
                }
            }
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getMoshtaryMorajeShodehRooz(getProgramType, String.valueOf(ccForoshandeh), ccMasirs);
        }
    }


    private void getMoshtaryMorajeShodehRooz(final int getProgramType, String ccForoshandeh, String ccMasir) {
        String ccForoshande = String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh());
        final MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(mPresenter.getAppContext());
        moshtaryMorajehShodehRoozDAO.fetchMoshtaryMorajehShodehRooz(mPresenter.getAppContext(), activityNameForLog, ccForoshande, ccMasir, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryMorajehShodehRoozDAO.deleteAll();
                        if (arrayListData.size() > 0) {
                            boolean insertResult = moshtaryMorajehShodehRoozDAO.insertGroup(arrayListData);
                            if (insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getMoshtaryAddressPakhsh(getProgramType, ccMoshtaryPakhsh);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryAddressPakhsh(getProgramType, ccMoshtaryPakhsh);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMoshtaryAddressPakhsh(final int getProgramType, final String ccMoshtaryPakhsh) {
        final MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        final ArrayList<DarkhastFaktorModel> darkhastFaktorModels = darkhastFaktorDAO.getAllByNotCodeVazeiat(Constants.CODE_VAZEIAT_FAKTOR_TASVIEH);
        if (darkhastFaktorModels.size() > 0) {
            globalCounter = 0;
            for (final DarkhastFaktorModel model : darkhastFaktorModels) {
                moshtaryAddressDAO.fetchAllvMoshtaryAddressByNoeMasouliat(mPresenter.getAppContext(), activityNameForLog, String.valueOf(model.getCcForoshandeh()), "-1", String.valueOf(model.getCcMoshtary()), new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                boolean deleteResult = moshtaryAddressDAO.deleteByccMoshtary(model.getCcMoshtary());
                                boolean insertResult = moshtaryAddressDAO.insertGroup(arrayListData);
                                if (deleteResult && insertResult) {
                                    globalCounter++;
                                    if (globalCounter == darkhastFaktorModels.size()) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        getMoshtaryAfradPakhsh(getProgramType, ccMoshtaryPakhsh);
                                    }
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                    }
                });
            }
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getMoshtaryAfradPakhsh(getProgramType, ccMoshtaryPakhsh);
        }
    }

    private void getMoshtaryAfradPakhsh(final int getProgramType, final String ccMoshtaryPakhsh) {
        Log.d("MoshtaryPakhsh", "ccMoshtaryPakhsh : " + ccMoshtaryPakhsh);
        final MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        moshtaryAfradDAO.fetchAllvMoshtaryAfrad(mPresenter.getAppContext(), activityNameForLog, ccMoshtaryPakhsh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (arrayListData.size() > 0) {
                            String ccAfrads = "-1";
                            for (int i = 0; i < arrayListData.size(); i++) {
                                ccAfrads += "," + ((MoshtaryAfradModel) arrayListData.get(i)).getCcAfrad();
                            }
                            boolean deleteResult = moshtaryAfradDAO.deleteGroup(ccAfrads);
                            boolean insertResult = moshtaryAfradDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getMoshtaryGorohPakhsh(getProgramType, ccMoshtaryPakhsh);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryGorohPakhsh(getProgramType, ccMoshtaryPakhsh);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryGorohPakhsh(final int getProgramType, final String ccMoshtaryPakhsh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getMoshtaryShomareHesabPakhsh(getProgramType, ccMoshtaryPakhsh);
    }


    private void getMoshtaryShomareHesabPakhsh(final int getProgramType, final String ccMoshtaryPakhsh) {
        final MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        moshtaryShomarehHesabDAO.fetchAllvMoshtaryShomarehHesab(mPresenter.getAppContext(), activityNameForLog, ccMoshtaryPakhsh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean insertResult = moshtaryShomarehHesabDAO.insertGroup(arrayListData);
                        if (insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryEtebarPakhsh(getProgramType, ccMoshtaryPakhsh, String.valueOf(ccSazmanForosh));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMoshtaryEtebarPakhsh(final int getProgramType, String ccMoshtaryPakhsh, String ccSazmanForosh) {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForoshForPakhsh(mPresenter.getAppContext(), activityNameForLog, ccMoshtaryPakhsh, ccSazmanForoshPakhsh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean insertResult = moshtaryEtebarSazmanForoshDAO.insertGroup(arrayListData);
                        if (insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getDariaftPardakht(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getDariaftPardakht(final int getProgramType) {
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        final DariaftPardakhtPPCDAO dariaftPardakhtPPCDAO = new DariaftPardakhtPPCDAO(mPresenter.getAppContext());
        dariaftPardakhtPPCDAO.deleteAll();
        final ArrayList<DarkhastFaktorModel> darkhastFaktorModels = darkhastFaktorDAO.getAllByNoeFaktorHavaleAndNotCodeVazeiat(1, Constants.CODE_VAZEIAT_FAKTOR_TASVIEH);//darkhastfaktor
        globalCounter = 0;
        if (darkhastFaktorModels.size() > 0) {
            for (DarkhastFaktorModel darkhastFaktorModel : darkhastFaktorModels) {
                dariaftPardakhtPPCDAO.fetchDariaftPardakhtPPC(mPresenter.getAppContext(), activityNameForLog, "1", "-1," + String.valueOf(darkhastFaktorModel.getCcDarkhastFaktor()), new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                String ccDariaftPardakhts = "";
                                for (int i = 0; i < arrayListData.size(); i++) {
                                    ccDariaftPardakhts += ((DariaftPardakhtPPCModel) arrayListData.get(i)).getCcDariaftPardakht() + ",";
                                }
                                if (ccDariaftPardakhts.endsWith(",")) {
                                    ccDariaftPardakhts = ccDariaftPardakhts.substring(0, ccDariaftPardakhts.length() - 1);
                                }
                                dariaftPardakhtPPCDAO.deleteByccDariaftPardakhts(ccDariaftPardakhts);
                                if (dariaftPardakhtPPCDAO.insertGroup(arrayListData, true)) {

                                    globalCounter++;
                                    if (globalCounter == darkhastFaktorModels.size()) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        getDariaftPardakhtDarkhastFaktor(getProgramType, darkhastFaktorModels);
                                    }
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                    }
                });
            }
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getDariaftPardakhtDarkhastFaktor(getProgramType, darkhastFaktorModels);
        }
    }


    private void getDariaftPardakhtDarkhastFaktor(final int getProgramType, final ArrayList<DarkhastFaktorModel> darkhastFaktorModels) {
        final DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());
        globalCounter = 0;
        dariaftPardakhtDarkhastFaktorPPCDAO.deleteAll();
        if (darkhastFaktorModels.size() > 0) {
            for (int i = 0; i < darkhastFaktorModels.size(); i++) {
                dariaftPardakhtDarkhastFaktorPPCDAO.fetchDariaftPardakhtDarkhastFaktorPPC(mPresenter.getAppContext(), activityNameForLog, "1", String.valueOf(darkhastFaktorModels.get(i).getCcDarkhastFaktor()), new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                boolean insertResult = dariaftPardakhtDarkhastFaktorPPCDAO.insertGroup(arrayListData, true);
                                if (insertResult) {
                                    globalCounter++;
                                    if (globalCounter == darkhastFaktorModels.size()) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        getDarkhastFaktorSatrTakhfif(getProgramType, ccMoshtarys);
                                    }
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                    }
                });
            }
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getDarkhastFaktorSatrTakhfif(getProgramType, ccMoshtarys);
        }

    }


    private void getDarkhastFaktorSatrTakhfif(final int getProgramType, String ccMoshtarys) {
        /*String fromDate = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT_WITH_SLASH()).format(calendar.getTimeInMillis());
        String endDate = new PubFunc().new DateUtils().persianWithSlashToGregorianSlash(date);*/
        Thread thread = new Thread() {
            @Override
            public void run() {
                DarkhastFaktorSatrTakhfifDAO darkhastFaktorSatrTakhfifDAO = new DarkhastFaktorSatrTakhfifDAO(mPresenter.getAppContext());
                boolean deleteResult = darkhastFaktorSatrTakhfifDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getDarkhastFaktorTakhfif(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }

    private void getDarkhastFaktorTakhfif(final int getProgramType) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
                DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(mPresenter.getAppContext());
                boolean deleteResult = darkhastFaktorTakhfifDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);

                    ccGorohs = moshtaryDAO.getAllccNoeSenf();
                    Log.d("GetProgram", "ccGorohs getdarkhastFaktorTakhfif : " + ccGorohs);
                    getModatVosol(getProgramType, String.valueOf(ccMarkazSazmanForoshSakhtarForosh), ccGorohs);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }

    private void getModatVosol(final int getProgramType, String ccMarkazSazmanForoshSakhtarForosh, final String ccGorohs) {
        Log.d("GetProgram", "ccGorohs before modat vosol : " + ccGorohs);
        final ModatVosolDAO modatVosolDAO = new ModatVosolDAO(mPresenter.getAppContext());
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
            int ccMarkazSazmanForoshSakhtarForoshMamorPakhsh = darkhastFaktorDAO.getccMarkazSazmanForoshSakhtarForosh();
            modatVosolDAO.fetchAllvModatVosolByccMarkazForoshGoroh(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccMarkazSazmanForoshSakhtarForoshMamorPakhsh), ccGorohs, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = modatVosolDAO.deleteAll();
                            boolean insertResult = modatVosolDAO.insertGroup(arrayListData);

                            int counter = itemCounter;
                            if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                counter = ++itemCounter;
                            } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                counter = itemCounter;
                            }
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), counter);
                                getModatVosolGoroh(getProgramType, ccGorohs);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), counter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            modatVosolDAO.fetchAllvModatVosolByccMarkazForoshGoroh(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshSakhtarForosh, ccGorohs, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = modatVosolDAO.deleteAll();
                            boolean insertResult = modatVosolDAO.insertGroup(arrayListData);

                            int counter = itemCounter;
                            if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                counter = ++itemCounter;
                            } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                counter = itemCounter;
                            }
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), counter);
                                getModatVosolGoroh(getProgramType, ccGorohs);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), counter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        }
    }

    private void getModatVosolGoroh(final int getProgramType, String ccGorohs) {
        final ModatVosolGorohDAO modatVosolGorohDAO = new ModatVosolGorohDAO(mPresenter.getAppContext());
        modatVosolGorohDAO.fetchAllModatVosolGoroh(mPresenter.getAppContext(), activityNameForLog, ccGorohs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = modatVosolGorohDAO.deleteAll();
                        boolean insertResult = modatVosolGorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getModatVosolMarkaz(getProgramType, String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getModatVosolMarkaz(final int getProgramType, String ccMarkazSazmanForoshSakhtarForosh) {
        final ModatVosolMarkazDAO modatVosolMarkazDAO = new ModatVosolMarkazDAO(mPresenter.getAppContext());
        modatVosolMarkazDAO.fetchAllModatVosolMarkaz(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshSakhtarForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = modatVosolMarkazDAO.deleteAll();
                        boolean insertResult = modatVosolMarkazDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getAnbarakInfo(getProgramType, String.valueOf(ccAfrad), String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getAnbarakInfo(final int getProgramType, final String ccAfrad, final String ccMarkazSazmanForoshSakhtarForosh) {
        final AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
        anbarakAfradDAO.fetchAnbarakAfrad(mPresenter.getAppContext(), activityNameForLog, ccAfrad, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = anbarakAfradDAO.deleteAll();
                        boolean insertResult = anbarakAfradDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            if (arrayListData.size() > 0) {
                                anbarakAfrad = String.valueOf(((AnbarakAfradModel) arrayListData.get(0)).getCcAnbarak());
                            }
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                getKala(getProgramType, ccAfrad, ccMarkazSazmanForoshSakhtarForosh);
                            } else {
                                getEtebar(getProgramType);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getEtebar(final int getProgramType) {
        final ForoshandehEtebarDAO etebarDAO = new ForoshandehEtebarDAO(mPresenter.getAppContext());

        ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());

        globalCounter = 0;

        if (getProgramType == Constants.GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH()) {
            final ArrayList<String> ccForoshandehs = foroshandehDAO.getDistinctccForoshandeh();

            if (ccForoshandehs.size() > 0) {
                for (final String strccForoshandeh : ccForoshandehs) {
                    Log.d("GetProgram", "Etebar_ccForoshandes:" + ccForoshandehs.toString() + " strccForoshandeh:" + strccForoshandeh);
                    etebarDAO.fetchEtebarForoshandeh(mPresenter.getAppContext(), activityNameForLog, strccForoshandeh, new RetrofitResponse() {
                        @Override
                        public void onSuccess(final ArrayList arrayListData) {
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    boolean deleteResult = etebarDAO.deleteByccForoshanhde(Integer.parseInt(strccForoshandeh));
                                    boolean insertResult = etebarDAO.insertGroup(arrayListData);
                                    if (deleteResult && insertResult) {
                                        globalCounter++;
                                    } else {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    }
                                    if (globalCounter == ccForoshandehs.size()) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        //getEtebarForoshandeh(getProgramType);
                                        //getKala(getProgramType , String.valueOf(ccAfrad));
                                    }
                                }
                            };
                            thread.start();
                        }

                        @Override
                        public void onFailed(String type, String error) {
                            mPresenter.onFailedUpdateEtebarForoshandeh(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                        }
                    });
                }
            } else {
                mPresenter.onFailedUpdateEtebarForoshandeh(++itemCounter, String.format(" type : %1$s \n error : %2$s", "", ""));
            }
        } else {
            final ArrayList<ForoshandehModel> foroshandehModels = foroshandehDAO.getAll();

            if (foroshandehModels.size() > 0) {
                for (final String strccForoshandeh : foroshandehArray) {
                    Log.d("GetProgram", "strccForoshandeh:" + strccForoshandeh);
                    etebarDAO.fetchEtebarForoshandeh(mPresenter.getAppContext(), activityNameForLog, strccForoshandeh, new RetrofitResponse() {
                        @Override
                        public void onSuccess(final ArrayList arrayListData) {
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    boolean deleteResult = etebarDAO.deleteByccForoshanhde(Integer.parseInt(strccForoshandeh));
                                    boolean insertResult = etebarDAO.insertGroup(arrayListData);
                                    if (deleteResult && insertResult) {
                                        globalCounter++;
                                    } else {
                                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                    }
                                    if (globalCounter == foroshandehModels.size()) {
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        getEtebarForoshandeh(getProgramType);
                                        //getKala(getProgramType , String.valueOf(ccAfrad));
                                    }
                                }
                            };
                            thread.start();
                        }

                        @Override
                        public void onFailed(String type, String error) {
                            mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                        }
                    });
                }
            } else {
                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                //getKala(getProgramType , String.valueOf(ccAfrad));
                getEtebarForoshandeh(getProgramType);
            }
        }
    }

    private void getEtebarForoshandeh(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getKala(getProgramType, String.valueOf(ccAfrad), String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
    }

    private void getKala(final int getProgramType, String ccAfrad, String ccMarkazSazmanForoshSakhtarForosh) {
        final KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
        kalaDAO.fetchMojodyAnbar(mPresenter.getAppContext(), activityNameForLog, ccAfrad, ccMarkazSazmanForoshSakhtarForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = kalaDAO.deleteAll();
                        boolean insertResult = kalaDAO.insertGroup(arrayListData);
                        for (KalaModel kala : (ArrayList<KalaModel>) arrayListData)
                            Log.d("getProgram", "KalaModel = " + kala.toString());
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (anbarakAfrad.trim().equals("-1")) {
                                //if go to this way, then getkalaolaviat and getAnbarakMojodi didn't call but we show to user that this two items selected in list
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getKalaMojodi(getProgramType);
                            } else {
                                getKalaolaviat(getProgramType, anbarakAfrad);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getKalaolaviat(final int getProgramType, final String anbarakAfrad) {
        final KalaOlaviatDAO kalaOlaviatDAO = new KalaOlaviatDAO(mPresenter.getAppContext());
        kalaOlaviatDAO.fetchKalaOlaviat(mPresenter.getAppContext(), activityNameForLog, anbarakAfrad, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = kalaOlaviatDAO.deleteAll();
                        boolean insertResult = kalaOlaviatDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getAnbarakMojodi(getProgramType, anbarakAfrad, String.valueOf(ccForoshandeh), String.valueOf(ccMamorPakhsh));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAnbarakMojodi(final int getProgramType, String anbarakAfrad, String ccForoshandeh, String ccMamorPakhsh) {
        final MandehMojodyMashinDAO mandehMojodyMashinDAO = new MandehMojodyMashinDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();

        ccForoshandeh = String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh());
        String ccSazmanForosh = String.valueOf(foroshandehMamorPakhshModel.getCcSazmanForosh());
        String ccKalaCode = "-1";
//        if (noeMasouliat == 1 || noeMasouliat == 6 || noeMasouliat == 8)//1-Foroshandeh-Sard
//        {
//            anbarakAfrad = "0";
//            ccMamorPakhsh = "0";
//        } else if (noeMasouliat == 2 || noeMasouliat == 3)//2-Foroshandeh-Garm //3-Foroshandeh-Smart
//        {
//            ccMamorPakhsh = "0";
//        } else if (noeMasouliat == 4 || noeMasouliat == 5)//4-MamorPakhsh-Sard // 5-MamorPakhsh-Smart
//        {
//            ccForoshandeh = "0";
//        } else //6-SarparastForoshandeh 7-Amargar
//        {
//            ccForoshandeh = "0";
//            ccMamorPakhsh = "0";
//        }
//
//        Log.d("GetProgram", "Online: " + anbarakAfrad + " - " + ccForoshandeh + " - " + ccMamorPakhsh + " - " + ccKalaCode + " - " + ccSazmanForosh);
//        mandehMojodyMashinDAO.fetchMandehMojodyMashin(mPresenter.getAppContext(), activityNameForLog, anbarakAfrad, ccForoshandeh, ccMamorPakhsh, new RetrofitResponse() {
//            @Override
//            public void onSuccess(final ArrayList arrayListData) {
//                Thread thread = new Thread() {
//                    @Override
//                    public void run() {
//                        boolean deleteResult = mandehMojodyMashinDAO.deleteAll();
//                        boolean insertResult = mandehMojodyMashinDAO.insertGroup(arrayListData);
//                        if (deleteResult && insertResult) {
//                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                            getKalaMojodi(getProgramType);
//                        } else {
//                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
//                        }
//                    }
//                };
//                thread.start();
//            }
//
//            @Override
//            public void onFailed(String type, String error) {
//                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
//            }
//        });
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getKalaMojodi(getProgramType);
    }


    private void getKalaMojodi(final int getProgramType) {
        final MandehMojodyMashinDAO mandehMojodyMashinDAO = new MandehMojodyMashinDAO(mPresenter.getAppContext());
        final KalaMojodiDAO kalamojodiDAO = new KalaMojodiDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                ArrayList<MandehMojodyMashinModel> mandehMojodyMashinModels = mandehMojodyMashinDAO.getAll();
                kalamojodiDAO.deleteAll();
                ArrayList<KalaMojodiModel> kalaMojodiModels = new ArrayList<>();
                if (mandehMojodyMashinModels.size() > 0) {
                    String currentDate = new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date());
                    for (MandehMojodyMashinModel mandehMojodyMashinModel : mandehMojodyMashinModels) {
                        KalaMojodiModel kalaMojodiModel = new KalaMojodiModel();

                        kalaMojodiModel.setCcKalaCode(mandehMojodyMashinModel.getCcKalaCode());
                        kalaMojodiModel.setCcForoshandeh(ccForoshandeh);
                        kalaMojodiModel.setTedad(mandehMojodyMashinModel.getMojody());
                        kalaMojodiModel.setCcDarkhastFaktor(0);
                        kalaMojodiModel.setTarikhDarkhast(currentDate);
                        kalaMojodiModel.setShomarehBach(mandehMojodyMashinModel.getShomarehBach());
                        kalaMojodiModel.setTarikhTolid(mandehMojodyMashinModel.getTarikhTolid());
                        kalaMojodiModel.setTarikhEngheza(mandehMojodyMashinModel.getTarikhEngheza());
                        kalaMojodiModel.setGheymatMasrafKonandeh(mandehMojodyMashinModel.getGheymatMasrafKonandeh());
                        kalaMojodiModel.setGheymatForosh(mandehMojodyMashinModel.getGheymatForosh());
                        kalaMojodiModel.setCcTaminKonandeh(mandehMojodyMashinModel.getCcTaminKonandeh());
                        kalaMojodiModel.setZamaneSabt(currentDate);
                        kalaMojodiModel.setIsAdamForosh(mandehMojodyMashinModel.getIsAdamForosh());
                        kalaMojodiModel.setMax_Mojody(mandehMojodyMashinModel.getMaxMojody());
                        kalaMojodiModel.setMax_MojodyByShomarehBach(mandehMojodyMashinModel.getMax_MojodyByShomarehBach());
                        kalaMojodiModel.setCcAfrad(ccAfrad);
                        Log.d("GetProgramModel", "ccAfrad:" + ccAfrad);

                        kalaMojodiModels.add(kalaMojodiModel);
                    }
                    boolean insertResult = kalamojodiDAO.insertGroup(kalaMojodiModels);
                    if (insertResult) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        getKalaGoroh(getProgramType);
                    } else {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    }
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getKalaGoroh(getProgramType);
                }
            }
        };
        thread.start();
    }


    private void getKalaGoroh(final int getProgramType) {
        final KalaGorohDAO kalaGorohDAO = new KalaGorohDAO(mPresenter.getAppContext());
        kalaGorohDAO.fetchAllvKalaGoroh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = kalaGorohDAO.deleteAll();
                        boolean insertResult = kalaGorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getZaribForoshKala(getProgramType, ccGorohs);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getZaribForoshKala(final int getProgramType, String ccGorohs) {
        final KalaZaribForoshDAO kalaZaribForoshDAO = new KalaZaribForoshDAO(mPresenter.getAppContext());
        final ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        int ccForoshandeh = foroshandehMamorPakhshDAO.getIsSelect().getCcForoshandeh();


        if (noeMasouliat == 1 || noeMasouliat == 6 || noeMasouliat == 8)//1-Foroshandeh-Sard
        {
            anbarakAfrad = "0";
            ccMamorPakhsh = 0;
        } else if (noeMasouliat == 2 || noeMasouliat == 3)//2-Foroshandeh-Garm //3-Foroshandeh-Smart
        {
            ccMamorPakhsh = 0;
        } else if (noeMasouliat == 4 || noeMasouliat == 5)//4-MamorPakhsh-Sard // 5-MamorPakhsh-Smart
        {
            ccForoshandeh = 0;
        } else //6-SarparastForoshandeh 7-Amargar
        {
            ccForoshandeh = 0;
            ccMamorPakhsh = 0;
        }
        kalaZaribForoshDAO.fetchKalaZaribForosh(mPresenter.getAppContext(), activityNameForLog,
                Integer.parseInt(anbarakAfrad),
                ccForoshandeh,
                ccMamorPakhsh,
                ccGorohs,
                new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                boolean deleteResult = kalaZaribForoshDAO.deleteAll();
                                boolean insertResult = kalaZaribForoshDAO.insertGroup(arrayListData);
                                if (deleteResult && insertResult) {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    getJayezeh(getProgramType);
                                } else {
                                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                                }
                            }
                        };
                        thread.start();
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                    }
                });
    }


    private void getJayezeh(final int getProgramType) {
        final JayezehDAO jayezehDAO = new JayezehDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        jayezehDAO.fetchAllJayezeh(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        String ccJayezehs = "-1";
                        boolean deleteResult = jayezehDAO.deleteAll();
                        boolean insertResult = jayezehDAO.insertGroup(arrayListData);
                        int counter = itemCounter;
                        if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE()) {
                            counter = itemCounter;
                        } else {
                            counter = ++itemCounter;
                        }
                        if (deleteResult && insertResult) {
                            ccJayezehs = jayezehDAO.getDistinctccJayezeh();
                            Log.d("GetProgram","ccJayezehs:" + ccJayezehs);
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), counter);
                            getJayezehSatr(getProgramType, ccJayezehs);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), counter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE()) {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        });
    }

    private void getJayezehSatr(final int getProgramType, String ccJayezehs) {
        final JayezehSatrDAO jayezehSatrDAO = new JayezehSatrDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        jayezehSatrDAO.fetchJayezehSatr(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, ccJayezehs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = jayezehSatrDAO.deleteAll();
                        boolean insertResult = jayezehSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getJayezehEntekhabi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });

    }

    private void getJayezehEntekhabi(final int getProgramType) {
        final JayezehEntekhabiDAO jayezehEntekhabiDAO = new JayezehEntekhabiDAO(mPresenter.getAppContext());
        if (noeMasouliat != 4) {
            jayezehEntekhabiDAO.fetchJayezehEntekhabi(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccMarkazSazmanForosh), new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = jayezehEntekhabiDAO.deleteAll();
                            boolean insertResult = jayezehEntekhabiDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
                                for (int i = 0; i < arrayListData.size(); i++) {
                                    JayezehEntekhabiModel jayezehEntekhabiModel = (JayezehEntekhabiModel) arrayListData.get(i);
                                    double mablaghForosh = kalaDAO.getKalaByMaxMojody(jayezehEntekhabiModel.getCcKalaCode()).getMablaghForosh();
                                    if (mablaghForosh > 0) {
                                        jayezehEntekhabiDAO.updateMablaghForosh(mablaghForosh, jayezehEntekhabiModel.getCcKalaCode());
                                    }
                                }
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                    getNoeHesab(getProgramType);
                                } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE() || getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                    //getTakhfifHajmi(getProgramType , ccMarkazSazmanForosh);
                                    getTakhfifHajmi(getProgramType);
                                }
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            jayezehEntekhabiDAO.fetchJayezehEntekhabiForPakhsh(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshPakhsh, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = jayezehEntekhabiDAO.deleteAll();
                            boolean insertResult = jayezehEntekhabiDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
                                for (int i = 0; i < arrayListData.size(); i++) {
                                    JayezehEntekhabiModel jayezehEntekhabiModel = (JayezehEntekhabiModel) arrayListData.get(i);
                                    double mablaghForosh = kalaDAO.getKalaByMaxMojody(jayezehEntekhabiModel.getCcKalaCode()).getMablaghForosh();
                                    if (mablaghForosh > 0) {
                                        jayezehEntekhabiDAO.updateMablaghForosh(mablaghForosh, jayezehEntekhabiModel.getCcKalaCode());
                                    }
                                }
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                    getNoeHesab(getProgramType);
                                } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE() || getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                    //getTakhfifHajmi(getProgramType , ccMarkazSazmanForosh);
                                    getTakhfifHajmi(getProgramType);
                                }
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        }
    }


    private void getNoeHesab(final int getProgramType) {
        final NoeHesabDAO noeHesabDAO = new NoeHesabDAO(mPresenter.getAppContext());
        noeHesabDAO.fetchNoeHesab(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeHesabDAO.deleteAll();
                        boolean insertResult = noeHesabDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeMalekiatMoshtary(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeMalekiatMoshtary(final int getProgramType) {
        final NoeMalekiatMoshtaryDAO noeMalekiatMoshtaryDAO = new NoeMalekiatMoshtaryDAO(mPresenter.getAppContext());
        noeMalekiatMoshtaryDAO.fetchNoeMalekiatMoshtary(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeMalekiatMoshtaryDAO.deleteAll();
                        boolean insertResult = noeMalekiatMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTedadFaktorMoshtary(getProgramType, ccMoshtarys);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getTedadFaktorMoshtary(final int getProgramType, String ccMoshtarys) {
        final TedadFaktorMoshtaryDAO tedadFaktorMoshtaryDAO = new TedadFaktorMoshtaryDAO(mPresenter.getAppContext());
        tedadFaktorMoshtaryDAO.fetchTedadFaktorMoshtary(mPresenter.getAppContext(), activityNameForLog, ccMoshtarys, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = tedadFaktorMoshtaryDAO.deleteAll();
                        boolean insertResult = tedadFaktorMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            //getTakhfifHajmi(getProgramType , String.valueOf(ccMarkazSazmanForosh));
                            getTakhfifHajmi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    //new
    private void getTakhfifHajmi(final int getProgramType) {
        final TakhfifHajmiDAO takhfifHajmiDAO = new TakhfifHajmiDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        Log.d("takhfifHajmi", "ccMarkazSazmanForosh : " + ccMarkazSazmanForosh);
        takhfifHajmiDAO.fetchTakhfifHajmiTitr(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, "-1", new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifHajmiDAO.deleteAll();
                        ccTakhfifHajmis = takhfifHajmiDAO.insertGroup(arrayListData);
                        if (deleteResult && ccTakhfifHajmis != null && !ccTakhfifHajmis.trim().equals("")) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTakhfifHajmiSatr(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    //new
    private void getTakhfifHajmiSatr(final int getProgramType) {
        TakhfifHajmiDAO takhfifHajmiDAO = new TakhfifHajmiDAO(mPresenter.getAppContext());
        ccTakhfifHajmis = takhfifHajmiDAO.getCcTakhfifHajmiForGetProgram();
        final TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(mPresenter.getAppContext());
        takhfifHajmiSatrDAO.fetchTakhfifHajmiSatr(mPresenter.getAppContext(), activityNameForLog, "-1", ccTakhfifHajmis, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifHajmiSatrDAO.deleteAll();
                        boolean insertResult = takhfifHajmiSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTakhfifNaghdi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    /*private void getTakhfifHajmiSatr(final int getProgramType)
    {
        final TakhfifHajmiSatrDAO takhfifHajmiSatrDAO = new TakhfifHajmiSatrDAO(mPresenter.getAppContext());
        takhfifHajmiSatrDAO.fetchTakhfifHajmiSatr(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = takhfifHajmiSatrDAO.deleteAll();
                        boolean insertResult = takhfifHajmiSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getTakhfifNaghdi(getProgramType , String.valueOf(ccMarkazSazmanForosh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }*/


    private void getTakhfifNaghdi(final int getProgramType) {
        final TakhfifNaghdyDAO takhfifNaghdyDAO = new TakhfifNaghdyDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        takhfifNaghdyDAO.fetchTakhfifNaghdy(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifNaghdyDAO.deleteAll();
                        boolean insertResult = takhfifNaghdyDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTakhfifSenfi(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getTakhfifSenfi(final int getProgramType) {
        final TakhfifSenfiDAO takhfifSenfiDAO = new TakhfifSenfiDAO(mPresenter.getAppContext());
        String ccMarkazSazmanForosh = "-1";
        if (noeMasouliat != 4) {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForosh);
        } else {
            ccMarkazSazmanForosh = String.valueOf(this.ccMarkazSazmanForoshPakhsh);
        }
        takhfifSenfiDAO.fetchTakhfifSenfi(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForosh, "-1", new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifSenfiDAO.deleteAll();
                        ccTakhfifSenfis = takhfifSenfiDAO.insertGroup(arrayListData);
                        if (deleteResult && ccTakhfifSenfis != null && !ccTakhfifSenfis.trim().equals("")) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTakhfifSenfiSatr(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getTakhfifSenfiSatr(final int getProgramType) {
        final TakhfifSenfiSatrDAO takhfifSenfiSatrDAO = new TakhfifSenfiSatrDAO(mPresenter.getAppContext());
        takhfifSenfiSatrDAO.fetchTakhfifSenfiSatr(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccMarkazSazmanForosh), ccTakhfifSenfis, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = takhfifSenfiSatrDAO.deleteAll();
                        boolean insertResult = takhfifSenfiSatrDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                getTaghvimTatil(getProgramType, String.valueOf(ccMarkazAnbar));
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA()) {
                                getRptKalaInfo(getProgramType);
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            } else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE()) {
                                updateJayezehTakhfifVersion();
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void updateJayezehTakhfifVersion() {
        try {
            int serverVersion = Integer.parseInt(new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_UPDATE_JAYEZEH_TAKHFIF));
            Log.d("GetProgram", "updateJayezehTakhfifVersion : " + serverVersion);
            LocalConfigShared localConfigShared = new LocalConfigShared(mPresenter.getAppContext());
            localConfigShared.remove(LocalConfigShared.JAYEZEH_TAKHFIF_VERSION);
            localConfigShared.putInt(LocalConfigShared.JAYEZEH_TAKHFIF_VERSION, serverVersion);
            Log.d("GetProgram", "updateJayezehTakhfifVersion : " + serverVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTaghvimTatil(final int getProgramType, String ccMarkazAnbar) {
        final TaghvimTatilDAO taghvimTatilDAO = new TaghvimTatilDAO(mPresenter.getAppContext());
        taghvimTatilDAO.fetchTaghvimTatil(mPresenter.getAppContext(), activityNameForLog, ccMarkazAnbar, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = taghvimTatilDAO.deleteAll();
                        boolean insertResult = taghvimTatilDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getCodeNoeVosol(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getCodeNoeVosol(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        deleteKardex(getProgramType);
//        final CodeNoeVosolDAO codeNoeVosolDAO = new CodeNoeVosolDAO(mPresenter.getAppContext());
//        codeNoeVosolDAO.fetchCodeNoeVosol(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
//        {
//            @Override
//            public void onSuccess(final ArrayList arrayListData)
//            {
//                Thread thread = new Thread()
//                {
//                    @Override
//                    public void run()
//                    {
//                        boolean deleteResult = codeNoeVosolDAO.deleteAll();
//                        boolean insertResult = codeNoeVosolDAO.insertGroup(arrayListData);
//                        if (deleteResult && insertResult)
//                        {
//                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
//                            deleteKardex(getProgramType);
//                        }
//                        else
//                        {
//                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
//                        }
//                    }
//                };
//                thread.start();
//            }
//            @Override
//            public void onFailed(String type, String error)
//            {
//                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
//            }
//        });
    }


    private void deleteKardex(final int getProgramType) {
        final KardexDAO kardexDAO = new KardexDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = kardexDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    deleteKardexSatr(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }


    private void deleteKardexSatr(final int getProgramType) {
        final KardexSatrDAO kardexSatrDAO = new KardexSatrDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = kardexSatrDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    deleteMarjoeeKamelImage(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }


    private void deleteMarjoeeKamelImage(final int getProgramType) {
        final MarjoeeKamelImageDAO marjoeeKamelImageDAO = new MarjoeeKamelImageDAO(mPresenter.getAppContext());
        final MarjoeeKamelImageTmpDAO marjoeeKamelImageTmpDAO = new MarjoeeKamelImageTmpDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = marjoeeKamelImageDAO.deleteAll();
                boolean deleteTempResult = marjoeeKamelImageTmpDAO.deleteAll();
                if (deleteResult && deleteTempResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getRptMojodyAnbar(getProgramType, anbarakAfrad);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }

    private void getRptMojodyAnbar(final int getProgramType, String ccAnbarakAfrad) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getRptFaktorVazeiat(getProgramType, String.valueOf(ccForoshandeh), String.valueOf(ccMamorPakhsh));

        /*final RptMojodiAnbarDAO rptMojodiAnbarDAO = new RptMojodiAnbarDAO(mPresenter.getAppContext());
        rptMojodiAnbarDAO.fetchRptMojodyAnbarak(mPresenter.getAppContext(), activityNameForLog, ccAnbarakAfrad, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptMojodiAnbarDAO.deleteAll();
                        boolean insertResult = rptMojodiAnbarDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getRptFaktorVazeiat(getProgramType, String.valueOf(ccForoshandeh), String.valueOf(ccMamorPakhsh));
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getRptFaktorVazeiat(final int getProgramType, String ccForoshandeh, String ccMamorPakhsh) {
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            ccForoshandeh = "0";
        } else {
            ccMamorPakhsh = "0";
        }

        final RptDarkhastFaktorVazeiatPPCDAO rptDarkhastFaktorVazeiatPPCDAO = new RptDarkhastFaktorVazeiatPPCDAO(mPresenter.getAppContext());
        rptDarkhastFaktorVazeiatPPCDAO.fetchRptDarkhastFaktorVazeiat(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, ccMamorPakhsh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rptDarkhastFaktorVazeiatPPCDAO.deleteAll();
                        boolean insertResult = rptDarkhastFaktorVazeiatPPCDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getRptKalaInfo(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getRptKalaInfo(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        if (getProgramType == Constants.GET_PROGRAM_FULL()) {
            getRptFaktorTozieNashode(getProgramType, String.valueOf(ccForoshandeh));
        }

        /*final RptKalaInfoDAO rptKalaInfoDAO = new RptKalaInfoDAO(mPresenter.getAppContext());
        rptKalaInfoDAO.fetchRptKalaInfo(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptKalaInfoDAO.deleteAll();
                        boolean insertResult = rptKalaInfoDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            if (getProgramType == Constants.GET_PROGRAM_FULL())
                            {
                                getRptFaktorTozieNashode(getProgramType , String.valueOf(ccForoshandeh));
                            }
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }

    private void getRptFaktorTozieNashode(final int getProgramType, final String ccForoshandeh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getRptForoshandehVisit(getProgramType, ccForoshandeh);

        /*final RptFaktorTozieNashodehDAO rptFaktorTozieNashodehDAO = new RptFaktorTozieNashodehDAO(mPresenter.getAppContext());
        rptFaktorTozieNashodehDAO.fetchAllrptFaktorTozieNashodeh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptFaktorTozieNashodehDAO.deleteAll();
                        boolean insertResult = rptFaktorTozieNashodehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getRptForoshandehVisit(getProgramType , ccForoshandeh);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getRptForoshandehVisit(final int getProgramType, String ccForoshandeh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getRptVersionInfo(getProgramType);

        /*final RptVisitForoshandehForoshandehDAO rptVisitForoshandehForoshandehDAO = new RptVisitForoshandehForoshandehDAO(mPresenter.getAppContext());
        final RptVisitForoshandehMoshtaryDAO rptVisitForoshandehMoshtaryDAO = new RptVisitForoshandehMoshtaryDAO(mPresenter.getAppContext());
        rptVisitForoshandehMoshtaryDAO.fetchAllrptVisitForoshandehMoshtary(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteForoshandehResult = rptVisitForoshandehForoshandehDAO.deleteAll();
                        boolean deleteResult = rptVisitForoshandehMoshtaryDAO.deleteAll();
                        boolean insertResult = rptVisitForoshandehMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteForoshandehResult && deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getRptVersionInfo(getProgramType , "1");
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getRptVersionInfo(final int getProgramType) {
        String version = new DeviceInfo().getCurrentVersion(mPresenter.getAppContext());
        final TaghiratVersionPPCDAO taghiratVersionPPCDAO = new TaghiratVersionPPCDAO(mPresenter.getAppContext());
        taghiratVersionPPCDAO.fetchTaghiratVersionPPC(mPresenter.getAppContext(), activityNameForLog, "1", version, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = taghiratVersionPPCDAO.deleteAll();
                        boolean insertResult = taghiratVersionPPCDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getTizerTablighat(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getTizerTablighat(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getMoshtaryChidman(getProgramType, ccMasirs);
     /*   final TizerTablighatPegahDAO tizerTablighatPegahDAO = new TizerTablighatPegahDAO(mPresenter.getAppContext());
        tizerTablighatPegahDAO.fetchTizerTablighat(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = tizerTablighatPegahDAO.deleteAll();
                        boolean insertResult = tizerTablighatPegahDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryChidman(getProgramType , ccMasirs);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        }); */
    }


    private void getMoshtaryChidman(final int getProgramType, String ccMasirs) {
        final MoshtaryChidmanDAO moshtaryChidmanDAO = new MoshtaryChidmanDAO(mPresenter.getAppContext());
        moshtaryChidmanDAO.fetchMoshtaryChidman(mPresenter.getAppContext(), activityNameForLog, ccMasirs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryChidmanDAO.deleteAll();
                        boolean insertResult = moshtaryChidmanDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                getRptMandehDar(getProgramType, String.valueOf(ccAfrad));
                            } else {
                                getSystemConfigTablet(getProgramType);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getSystemConfigTablet(final int getProgramType) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getAllMoshtaryForoshandeh(getProgramType, String.valueOf(ccForoshandeh));
    }


    private void getAllMoshtaryForoshandeh(final int getProgramType, final String ccForoshandeh) {
        final AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext());
        allMoshtaryForoshandehDAO.fetchAllMoshtaryforoshandeh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = allMoshtaryForoshandehDAO.deleteAll();
                        boolean insertResult = allMoshtaryForoshandehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getRptForosh(getProgramType, ccForoshandeh, selectedDateGregorian);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getRptForosh(final int getProgramType, String ccForoshandeh, String currentDate) {
        final RptForoshDAO rptForoshDAO = new RptForoshDAO(mPresenter.getAppContext());
        rptForoshDAO.fetchAllrptAmarForosh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, currentDate, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rptForoshDAO.deleteAll();
                        boolean insertResult = rptForoshDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getRptMandehDar(getProgramType, String.valueOf(ccAfrad));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getRptMandehDar(final int getProgramType, final String ccAfrad) {
        final RptMandehdarDAO mandehdarDAO = new RptMandehdarDAO(mPresenter.getAppContext());
        mandehdarDAO.fetchAllMandehdar(mPresenter.getAppContext(), activityNameForLog, ccAfrad, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = mandehdarDAO.deleteAll();
                        boolean insertResult = mandehdarDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType != Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                getRptAsnad(getProgramType);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getRptAsnad(final int getProgramType) {
        String ccForoshandehAsnad;
        if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART) {
            ccForoshandehAsnad = String.valueOf(ccForoshandeh);
        } else {
            ccForoshandehAsnad = ccForoshandehString;
        }
        final RptSanadDAO rptSanadDAO = new RptSanadDAO(mPresenter.getAppContext());
        rptSanadDAO.fetchAllRptSanad(mPresenter.getAppContext(), activityNameForLog, ccForoshandehAsnad, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rptSanadDAO.deleteAll();
                        boolean insertResult = rptSanadDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType != Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                getRptHadaf(getProgramType);
                            }
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getRptHadaf(final int getProgramType) {
        if (noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SARD || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_GARM || noeMasouliat == ForoshandehMamorPakhshUtils.FOROSHANDEH_SMART) {
            final RptHadafForoshDAO rptHadafForoshDAO = new RptHadafForoshDAO(mPresenter.getAppContext());
            rptHadafForoshDAO.fetchAllrpHadafeForosh(mPresenter.getAppContext(), activityNameForLog, String.valueOf(ccForoshandeh), new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = rptHadafForoshDAO.deleteAll();
                            boolean insertResult = rptHadafForoshDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                if (getProgramType != Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                    getRptListVosol(getProgramType, String.valueOf(ccAfrad));
                                }
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getRptListVosol(getProgramType, String.valueOf(ccAfrad));
        }
    }


    private void getRptListVosol(final int getProgramType, String ccAfrad) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getRptKharidKala(getProgramType, "2", "");

        /*final RptListVosolDAO rptListVosolDAO = new RptListVosolDAO(mPresenter.getAppContext());
        rptListVosolDAO.fetchRPTListVosol(mPresenter.getAppContext(), activityNameForLog, ccAfrad, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptListVosolDAO.deleteAll();
                        boolean insertResult = rptListVosolDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getRptKharidKala(getProgramType , "2" , "");
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getRptKharidKala(final int getProgramType, String level, String ccKalaGoroh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        deleteRptMarjoee(getProgramType);

        /*final RptKharidKalaDAO rptKharidKalaDAO = new RptKharidKalaDAO(mPresenter.getAppContext());
        String foroshandeh = "";
        if(noeMasouliat == 5)
        {
            foroshandeh = ccForoshandehString;
        }
        else
        {
            foroshandeh = String.valueOf(ccForoshandeh);
        }
        rptKharidKalaDAO.fetchKharidKalaByccForoshandeh(mPresenter.getAppContext(), activityNameForLog, level, foroshandeh, ccKalaGoroh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptKharidKalaDAO.deleteAll();
                        boolean insertResult = rptKharidKalaDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            deleteRptMarjoee(getProgramType);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void deleteRptMarjoee(int getProgramType) {
        RptMarjoeeDAO rptMarjoeeDAO = new RptMarjoeeDAO(mPresenter.getAppContext());
        rptMarjoeeDAO.deleteAll();
        getRptMasir(getProgramType, String.valueOf(ccForoshandeh));
    }

    private void getRptMasir(final int getProgramType, final String ccForoshandeh) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getElamMarjoee(getProgramType);

        /*final RptMasirDAO rptMasirDAO = new RptMasirDAO(mPresenter.getAppContext());
        rptMasirDAO.fetchRPTMasirForoshandeh(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = rptMasirDAO.deleteAll();
                        boolean insertResult = rptMasirDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            //sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryEtebarPishfarz(getProgramType);
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetProgram(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });*/
    }


    private void getElamMarjoee(final int getProgramType) {
        final ElamMarjoeePPCDAO elamMarjoeePPCDAO = new ElamMarjoeePPCDAO(mPresenter.getAppContext());
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            Log.d("getprogram", "fetch elam marjoee" + ccDarkhastFaktorPakhsh);
            elamMarjoeePPCDAO.fetchMarjoee(mPresenter.getAppContext(), activityNameForLog, ccDarkhastFaktorPakhsh, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = elamMarjoeePPCDAO.deleteAll();
                            boolean insertResult = elamMarjoeePPCDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getElamMarjoeeSatr(getProgramType);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Log.d("getprogram", "delete elam marjoee");
                    elamMarjoeePPCDAO.deleteAll();
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getElamMarjoeeSatr(getProgramType);
                }
            };
            thread.start();
        }
    }


    private void getElamMarjoeeSatr(final int getProgramType) {
        final ElamMarjoeeSatrPPCDAO elamMarjoeeSatrPPCDAO = new ElamMarjoeeSatrPPCDAO(mPresenter.getAppContext());
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            Log.d("getprogram", "fetch elam marjoee satr");
            elamMarjoeeSatrPPCDAO.fetchMarjoeeSatr(mPresenter.getAppContext(), activityNameForLog, ccDarkhastFaktorPakhsh, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = elamMarjoeeSatrPPCDAO.deleteAll();
                            boolean insertResult = elamMarjoeeSatrPPCDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                deleteElamMarjoeeTedad(getProgramType);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Log.d("getprogram", "delete elam marjoee satr");
                    elamMarjoeeSatrPPCDAO.deleteAll();
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    deleteElamMarjoeeTedad(getProgramType);
                }
            };
            thread.start();
        }
    }


    private void deleteElamMarjoeeTedad(final int getProgramType) {
        final ElamMarjoeeSatrPPCTedadDAO elamMarjoeeSatrPPCTedadDAO = new ElamMarjoeeSatrPPCTedadDAO(mPresenter.getAppContext());
        Thread thread = new Thread() {
            @Override
            public void run() {
                boolean deleteResult = elamMarjoeeSatrPPCTedadDAO.deleteAll();
                if (deleteResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMoshtaryEtebarPishfarz(getProgramType, String.valueOf(ccForoshandeh));
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }
        };
        thread.start();
    }

    private void getMoshtaryEtebarPishfarz(final int getProgramType, final String ccForoshandeh) {
        final MoshtaryEtebarPishFarzDAO moshtaryEtebarPishFarzDAO = new MoshtaryEtebarPishFarzDAO(mPresenter.getAppContext());
        moshtaryEtebarPishFarzDAO.fetchMoshtaryEtebarPishFarz(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryEtebarPishFarzDAO.deleteAll();
                        boolean insertResult = moshtaryEtebarPishFarzDAO.insertGroup(arrayListData);
                        Log.d("GetProgram", "MoshtaryEtebarPishfarz: " + arrayListData.toString());
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeFaaliatForMoarefiMoshtaryJadid(getProgramType, String.valueOf(ccForoshandeh));
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getNoeFaaliatForMoarefiMoshtaryJadid(final int getProgramType, final String ccForoshandeh) {
        final NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        noeFaaliatForMoarefiMoshtaryJadidDAO.fetchNoeFaaliatForMoarefiMoshtaryJadid(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeFaaliatForMoarefiMoshtaryJadidDAO.deleteAll();
                        boolean insertResult = noeFaaliatForMoarefiMoshtaryJadidDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryJadidDarkhast(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryJadidDarkhast(final int getProgramType) {
        final MoshtaryJadidDarkhastDAO moshtaryJadidDarkhastDAO = new MoshtaryJadidDarkhastDAO(mPresenter.getAppContext());
        moshtaryJadidDarkhastDAO.fetchMoshtaryJadidDarkhast(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryJadidDarkhastDAO.deleteAll();
                        boolean insertResult = moshtaryJadidDarkhastDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            deleteDarkhastFaktorRoozSort(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void deleteDarkhastFaktorRoozSort(int getProgramType) {
        DarkhastFaktorRoozSortDAO darkhastFaktorRoozSortDAO = new DarkhastFaktorRoozSortDAO(mPresenter.getAppContext());
        darkhastFaktorRoozSortDAO.deleteAll();
        getTafkikJozePakhsh(getProgramType);
    }

    private void getTafkikJozePakhsh(final int getProgramType) {
        if (noeMasouliat == 4 && !ccDarkhastFaktors.trim().equals("-1")) {
            final TafkikJozeDAO tafkikJozeDAO = new TafkikJozeDAO(mPresenter.getAppContext());

            tafkikJozeDAO.fetchTafkikJozePakhsh(mPresenter.getAppContext(), activityNameForLog, ccDarkhastFaktorsGhati, ccDarkhastFaktorsHavaleh, new RetrofitResponse() {
                @Override
                public void onSuccess(ArrayList arrayListData) {
                    boolean deleteResult = tafkikJozeDAO.deleteAll();
                    boolean insertResult = tafkikJozeDAO.insertGroup(arrayListData);
                    if (deleteResult && insertResult) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        getGorohKalaNoeSenf(getProgramType);
                    } else {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    }
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getGorohKalaNoeSenf(getProgramType);
        }
    }

    private void getGorohKalaNoeSenf(final int getProgramType) {
        final GorohKalaNoeSenfDAO gorohKalaNoeSenfDAO = new GorohKalaNoeSenfDAO(mPresenter.getAppContext());
        gorohKalaNoeSenfDAO.fetchAllGorohKalaNoeSenf(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = gorohKalaNoeSenfDAO.deleteAll();
                boolean insertResult = gorohKalaNoeSenfDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMoshtaryBrand(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryBrand(final int getProgramType) {
        String allccMoshtary = ccMoshtarys;
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            allccMoshtary = ccMoshtaryPakhsh;
        }
        final MoshtaryBrandDAO moshtaryBrandDAO = new MoshtaryBrandDAO(mPresenter.getAppContext());
        moshtaryBrandDAO.fetchMoshtaryBrand(mPresenter.getAppContext(), activityNameForLog, allccMoshtary, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = moshtaryBrandDAO.deleteAll();
                boolean insertResult = moshtaryBrandDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMarjoeeMamorPakhsh(getProgramType);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMarjoeeMamorPakhsh(int getProgramType) {
        Log.i("itemCounter", "getMarjoeeMamorPakhsh : " + itemCounter);
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
        //String ccMoshtary = darkhastFaktorDAO.getCcdarkhastFaktorsForZanjirei();
        String ccMoshtary = darkhastFaktorDAO.getCcMoshtaryForZanjire();
        Log.d("getProgram","ccmoshtary for mamorpakhshmarjoee api:"+ccMoshtary);
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            MarjoeeMamorPakhshDAO marjoeeMamorPakhshDAO = new MarjoeeMamorPakhshDAO(mPresenter.getAppContext());
            marjoeeMamorPakhshDAO.fetchMarjoeeMamorPakhsh(mPresenter.getAppContext(), activityNameForLog, ccMoshtary, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = marjoeeMamorPakhshDAO.deleteAll();
                            boolean insertResult = marjoeeMamorPakhshDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getAllMarkazShahrMarkazi(getProgramType);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        } else {
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            getAllMarkazShahrMarkazi(getProgramType);
        }

    }

    private void getAllMarkazShahrMarkazi(final int getProgramType) {
        final MarkazShahrMarkaziDAO markazShahrMarkaziDAO = new MarkazShahrMarkaziDAO(mPresenter.getAppContext());
        markazShahrMarkaziDAO.fetchMarkazShahrMarkazi(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = markazShahrMarkaziDAO.deleteAll();
                boolean insertResult = markazShahrMarkaziDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    Log.d("GetProgram", "getProgramType:" + getProgramType + " , ccMarkazSazmanForoshSakhtarForosh hadeaghal " + ccMarkazSazmanForoshSakhtarForosh);
                    getAllNoeMoshtaryRialKharid(getProgramType, String.valueOf(ccMarkazSazmanForoshSakhtarForosh));
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllNoeMoshtaryRialKharid(final int getProgramType, String ccMarkazSazmanForoshSakhtarForoshs) {
        final NoeMoshtaryRialKharidDAO noeMoshtaryRialKharidDAO = new NoeMoshtaryRialKharidDAO(mPresenter.getAppContext());
        /*sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
        getParameter(getProgramType);*/
        noeMoshtaryRialKharidDAO.fetchNoeMoshtaryRialKharid(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshSakhtarForoshs, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                boolean deleteResult = noeMoshtaryRialKharidDAO.deleteAll();
                boolean insertResult = noeMoshtaryRialKharidDAO.insertGroup(arrayListData);
                if (deleteResult && insertResult) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getAllcodePosti(getProgramType, ccMarkazForosh);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllcodePosti(final int getProgramType, int ccMarkazForosh) {
        final MahalCodePostiDAO mahalCodePostiDAO = new MahalCodePostiDAO(mPresenter.getAppContext());
        mahalCodePostiDAO.fetchMahalCodePosti(mPresenter.getAppContext(), activityNameForLog, ccMarkazForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = mahalCodePostiDAO.deleteAll();
                        boolean insertResult = mahalCodePostiDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getConfigNoeVosolMojazeFaktor(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getConfigNoeVosolMojazeFaktor(int getProgramType) {
        Log.i("itemCounter", "getConfigNoeVosolMojazeFaktor :" + itemCounter);
        ConfigNoeVosolMojazeFaktorDAO configNoeVosolMojazeFaktorDAO = new ConfigNoeVosolMojazeFaktorDAO(BaseApplication.getContext());
        configNoeVosolMojazeFaktorDAO.fetchConfigNoeVosolMojazeFaktor(BaseApplication.getContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = configNoeVosolMojazeFaktorDAO.deleteAll();
                        boolean insertResult = configNoeVosolMojazeFaktorDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType != Constants.GET_PROGRAM_UPDATE_MOSHTARY()) {
                                getConfigNoeVosolMojazeMoshtary(getProgramType);
                            }

                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }


            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getConfigNoeVosolMojazeMoshtary(int getProgramType) {
        Log.i("itemCounter", "getConfigNoeVosolMojazeMoshtary :" + itemCounter);
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(BaseApplication.getContext());
        String moshtarys = "-1,347,348,349,350,351,352,353," + moshtaryDAO.getAllccNoeSenf();
        Log.i("NoeVosolMojazeMoshtary", "moshtarys :" + moshtarys);
        ConfigNoeVosolMojazeMoshtaryDAO configNoeVosolMojazeMoshtaryDAO = new ConfigNoeVosolMojazeMoshtaryDAO(BaseApplication.getContext());
        configNoeVosolMojazeMoshtaryDAO.fetchConfigNoeVosolMojazeMoshtary(BaseApplication.getContext(), activityNameForLog, moshtarys, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = configNoeVosolMojazeMoshtaryDAO.deleteAll();
                        boolean insertResult = configNoeVosolMojazeMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                            getSupportCrisp(getProgramType,ccSazmanForosh);
                            getDariaftPardakhtBargashty(getProgramType, ccdpBargashty);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getDariaftPardakhtBargashty(final int getProgramType, String ccDarkhastFaktors) {
        final DariaftPardakhtDarkhastFaktorPPCDAO dariaftPardakhtDarkhastFaktorPPCDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(mPresenter.getAppContext());

        dariaftPardakhtDarkhastFaktorPPCDAO.fetchDariaftPardakhtDarkhastFaktorPPC(mPresenter.getAppContext(), activityNameForLog, "3", ccDarkhastFaktors, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean insertResult = dariaftPardakhtDarkhastFaktorPPCDAO.insertGroup(arrayListData, true);
                        if (insertResult) {

                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (noeMasouliat == 4)
                                getSupportCrisp(getProgramType, 0);
                            else
                                getSupportCrisp(getProgramType, ccSazmanForosh);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getSupportCrisp(final int getProgramType, int ccSazmanForosh) {
        final SupportCrispDAO supportCrispDAO = new SupportCrispDAO(mPresenter.getAppContext());
        supportCrispDAO.fetchSupportCrisp(mPresenter.getAppContext(), activityNameForLog, ccSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = supportCrispDAO.deleteAll();
                        boolean insertResult = supportCrispDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            Log.d("GetProgram", "ccMarkazSazmanForosh" + ccMarkazSazmanForosh);
                            Log.d("GetProgram", "ccMarkazSazmanForosh" + ccMarkazSazmanForoshPakhsh);
                            getNoeVosolMoshtary(getProgramType, ccMarkazSazmanForosh);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeVosolMoshtary(final int getProgramType, int ccMarkazSazmanForosh) {
        String ccMarkazSazmanForoshSend = "";
        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(BaseApplication.getContext());
        if (noeMasouliat == 4 || noeMasouliat == 5) {
            ccMarkazSazmanForoshSend = darkhastFaktorDAO.getCcMarkazSazmanForosh();
        } else {
            ccMarkazSazmanForoshSend = String.valueOf(ccMarkazSazmanForosh);
        }
        final NoeVosolMoshtaryDAO noeVosolMoshtaryDAO = new NoeVosolMoshtaryDAO(mPresenter.getAppContext());
        noeVosolMoshtaryDAO.fetchNoeVosolMoshtary(mPresenter.getAppContext(), activityNameForLog, ccMarkazSazmanForoshSend, ccGorohs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeVosolMoshtaryDAO.deleteAll();
                        boolean insertResult = noeVosolMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            //getParameter(getProgramType);

                            prepareToGetMoshtaryGharardad(getProgramType, ccForoshandeh);

                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void prepareToGetMoshtaryGharardad(int getProgramType, int ccForoshandeh) {

        DarkhastFaktorDAO darkhastFaktorDAO = new DarkhastFaktorDAO(mPresenter.getAppContext());
        JSONArray jsonArray = darkhastFaktorDAO.getZangireiFaktorInfo();
        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3)
            getAllMoshtaryGharardad(getProgramType, String.valueOf(ccForoshandeh));
        else if (noeMasouliat == 4 || noeMasouliat == 5)
            getAllMoshtaryGharardad_pakhsh(getProgramType, jsonArray);
        else
            getAllMoshtaryGharardad(getProgramType, "-1");

        Log.d("getProgram", "prepareToGetMoshtaryGharardad noeMasouliat:" + noeMasouliat + " ,ccForoshandeh:" + ccForoshandeh + " , jsonArray:" + jsonArray);
    }


    /**
     * overload getAllMoshtaryGharardad
     * {@link #getAllMoshtaryGharardad_pakhsh(int, JSONArray)}
     *
     * @param getProgramType
     * @param jsonArray
     * @throws
     */
    public void getAllMoshtaryGharardad_pakhsh(int getProgramType, JSONArray jsonArray) {

            try {

                MoshtaryGharardadDAO moshtaryGharardadDAO = new MoshtaryGharardadDAO(mPresenter.getAppContext());
                int ccForoshandeh=-1;
                int ccMoshtaryGharardad;
                int moshtaryGharardadccSazmanForosh;
                ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //ccForoshandeh = jsonObject.getInt("ccForoshandeh");
                    ccMoshtaryGharardad = jsonObject.getInt("ccMoshtaryGharardad");
                    moshtaryGharardadccSazmanForosh = jsonObject.getInt("MoshtaryGharardadccSazmanForosh");
                    Log.d("getProgram", "getAllMoshtaryGharardad ccForoshandeh:" + ccForoshandeh + " ,ccMoshtaryGharardad:" + ccMoshtaryGharardad + " , moshtaryGharardadccSazmanForosh:" + moshtaryGharardadccSazmanForosh);

                    moshtaryGharardadDAO.fetchMoshtaryGharardadSync(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, ccMoshtaryGharardad, moshtaryGharardadccSazmanForosh, new RetrofitResponse() {
                        @Override
                        public void onSuccess(ArrayList arrayListData) {


                            Log.i("getProgram", "arrayListData :" + arrayListData);
                            Log.i("getProgram", "arrayListData.size :" + arrayListData.size());
                            moshtaryGharardadModels.addAll(arrayListData);

                        }

                        @Override
                        public void onFailed(String type, String error) {
                            Log.i("getProgram", "onFailed: ");
                            mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                            String message = String.format("error body : %1$s , code : %2$s", type, error);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, GetProgramModel.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                        }


                    });
                }
                boolean deleteAll = moshtaryGharardadDAO.deleteAll();
                boolean insertGroup = moshtaryGharardadDAO.insertGroup(moshtaryGharardadModels);
                if (deleteAll && insertGroup) {

                    Log.i("getProgram", "run: " + insertGroup + " " + deleteAll);
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    Log.i("getProgram", "onSuccess: 4");

                    ArrayList<MoshtaryGharardadModel> kalaMosavabeh = new ArrayList<>(new PubFunc().new DAOUtil().deleteDuplicates(moshtaryGharardadModels));
                    /**
                     *now we have all ccSazman and cc Gharardad in our MoshtaryGharardad Table
                     * we need to find ccGharardads for each ccSazman and send a request for each set
                     *{@param ccSazmanForosh}
                     *{@param ccMoshtaryGharardad}
                     * we send all moshtary models to our getKalaMosavabModel And extract each set of{@param ccSazmanForosh}  && {@param ccMoshtaryGharardad}
                     */
                    getAllKalaMosavab(getProgramType, kalaMosavabeh);
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                }
            } catch (Exception exception) {

                Log.i("getProgram", "Exception: ");
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", exception.getMessage(), exception.getLocalizedMessage()));
                String message = String.format("error body : %1$s , code : %2$s", exception.getMessage(), exception.getLocalizedMessage());
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, GetProgramModel.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "Exception");
            }



    }


    /**
     * overload getAllMoshtaryGharardad
     * {@link #getAllMoshtaryGharardad(int, JSONArray)}
     * get All contractions which a seller can sell to them weather a cold seller, a warm seller and a smart seller or a distributer
     * {@link #getKalaMosavabBySazmanGharardad(int, int)
     *
     * @param ccForoshandeh:each person in System has a cc
     */

    public void getAllMoshtaryGharardad(final int getProgramType, String ccForoshandeh) {


        MoshtaryGharardadDAO moshtaryGharardadDAO = new MoshtaryGharardadDAO(mPresenter.getAppContext());

        moshtaryGharardadDAO.fetchMoshtaryGharardadASync(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {

                Log.i("getProgram", "onSuccess1: " + arrayListData.size());
                try {
                    Log.i("getProgram", "arrayListData1" + arrayListData);
                    Log.i("getProgram", "arrayListData.size1" + arrayListData.size());
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = moshtaryGharardadDAO.deleteAll();
                            boolean insertResult = moshtaryGharardadDAO.insertGroup(arrayListData);
                            Log.i("getProgram", "3: " + insertResult + " " + deleteResult);
                            if (deleteResult && insertResult) {

                                Log.i("getProgram", "run: " + insertResult + " " + deleteResult);
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                Log.i("getProgram", "onSuccess: 4");

                                ArrayList<MoshtaryGharardadModel> moshtaryGharardadModelsFinal = new ArrayList<>();
                                ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels = ((ArrayList<MoshtaryGharardadModel>) arrayListData);


                                moshtaryGharardadModelsFinal.addAll(new PubFunc().new DAOUtil().deleteDuplicates(moshtaryGharardadModels));
                                /**
                                 *now we have all ccSazman and cc Gharardad in our MoshtaryGharardad Table
                                 * we need to find ccGharardads for each ccSazman and send a request for each set
                                 *{@param ccSazmanForosh}
                                 *{@param ccMoshtaryGharardad}
                                 * we send all moshtary models to our getKalaMosavabModel And extract each set of{@param ccSazmanForosh}  && {@param ccMoshtaryGharardad}
                                 */
                                getAllKalaMosavab(getProgramType, moshtaryGharardadModelsFinal);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();


                } catch (Exception exception) {

                    Log.i("getProgram", "onFailed: ");
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", exception.getMessage(), exception.getLocalizedMessage()));
                    String message = String.format("error body : %1$s , code : %2$s", exception.getMessage(), exception.getLocalizedMessage());
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, GetProgramModel.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                }

            }

            @Override
            public void onFailed(String type, String error) {
                Log.i("getProgram", "onFailed: ");
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                String message = String.format("error body : %1$s , code : %2$s", type, error);
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, GetProgramModel.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
            }
        });

    }


    /**
     * now we have all ccSazman and cc Gharardad in our MoshtaryGharardad Table
     * we neea to find ccGharardads for each ccSazman and send a request for each set
     * {@param ccSazmanForosh}
     * {@param ccMoshtaryGharardad}
     * we extract each set in this method and after that we make apicalls  for each set
     */
    public final String __GET_ALL_KALA_MOSAVAB__ = "GET_ALL_KALA_MOSAVAB";

    private void getAllKalaMosavab(int getProgramType, ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels) {
        ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadArrayLists = new ArrayList<>();
        for (MoshtaryGharardadModel moshtaryGharardadModel : moshtaryGharardadModels) {
            ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModels;
            /**
             * moshtaryGharardadModel.getCcSazmanForosh() {@param ccSazmanForosh}
             * moshtaryGharardadModel.getCcMoshtaryGharardad() {@param ccMoshtaryGharardad}
             */

            Log.i("touples", "getAllKalaMosavab: " + moshtaryGharardadModel.getCcSazmanForosh() + "," + moshtaryGharardadModel.getCcMoshtaryGharardad() + "\t");
            moshtaryGharardadKalaModels = getKalaMosavabBySazmanGharardad(moshtaryGharardadModel.getCcSazmanForosh(), moshtaryGharardadModel.getCcMoshtaryGharardad());
            if (moshtaryGharardadKalaModels.size() > 0)
                moshtaryGharardadArrayLists.addAll(moshtaryGharardadKalaModels);
        }

        MoshtaryGharardadKalaDAO moshtaryGharardadKalaDAO = new MoshtaryGharardadKalaDAO(mPresenter.getAppContext());

        boolean deleteAll = moshtaryGharardadKalaDAO.deleteAll();
        boolean insertGroup = moshtaryGharardadKalaDAO.insertGroup(moshtaryGharardadArrayLists);
        if (deleteAll && insertGroup) {
            Log.i(__GET_ALL_KALA_MOSAVAB__, "run: " + deleteAll + " " + insertGroup);
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
            if (getProgramType != Constants.GET_PROGRAM_UPDATE_GHARARDAD_KALAMOSAVABEH())
                getMarjoeePakhsh(getProgramType, ccDarkhastFaktorPakhsh);

        } else {

            Log.i(__GET_ALL_KALA_MOSAVAB__, "run: " + deleteAll + " " + insertGroup);
            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);


        }
    }


    /**
     * gets all kala for each ccGharardad that is related to a sazmanForosh:
     *
     * @param ccMoshtaryGharardad :a MoshtaryParent Can have multiple contractions with multiple sazmanForosh and each contraction has a ccGharardad
     * @param ccSazmanForosh      :All sazman forsh for instance mesal forostland , pegah , bastaniPanda ,zangirei ....
     * ccsazmanForosh IN {1,2,3,4,5,6,11}
     **/


    public final String API_CALL_FOREACH_SAZMAN_MOSHTARYGHARARDA = "SazmanMoshtaryGharardadApiCalls";


    @SuppressLint("LongLogTag")
    private ArrayList<MoshtaryGharardadKalaModel> getKalaMosavabBySazmanGharardad(
            int ccSazmanForosh, int ccMoshtaryGharardad) {
        ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModels = new ArrayList<>();
        MoshtaryGharardadKalaDAO moshtaryGharardadKalaDAO = new MoshtaryGharardadKalaDAO(mPresenter.getAppContext());
        moshtaryGharardadKalaDAO.fetchMoshtaryGharadadKala(mPresenter.getAppContext(), activityNameForLog, ccSazmanForosh, ccMoshtaryGharardad, new RetrofitResponse() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Log.i(API_CALL_FOREACH_SAZMAN_MOSHTARYGHARARDA, "getMoshtaryGharardadAllKalaMosavabe: " + ccSazmanForosh + " " + ccMoshtaryGharardad);
                if (arrayListData != null) {
                    Log.i(API_CALL_FOREACH_SAZMAN_MOSHTARYGHARARDA, "onSuccess: " + arrayListData.size());
                    if (arrayListData.size() > 0) {
                        for (int i = 0; i < arrayListData.size(); i++) {
                            MoshtaryGharardadKalaModel moshtaryGharardadKalaModel = (MoshtaryGharardadKalaModel) arrayListData.get(i);
                            moshtaryGharardadKalaModel.setExtraprop_ccSazmanForosh(ccSazmanForosh);
                            moshtaryGharardadKalaModels.add(moshtaryGharardadKalaModel);

                            Log.i(API_CALL_FOREACH_SAZMAN_MOSHTARYGHARARDA, "onSuccess: " + moshtaryGharardadKalaModel.toString());
                        }
                    }


                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailed(String type, String error) {
                Log.i(API_CALL_FOREACH_SAZMAN_MOSHTARYGHARARDA, "onFailed: " + type + " " + error);


            }
        });
        Log.i(API_CALL_FOREACH_SAZMAN_MOSHTARYGHARARDA, "getMoshtaryGharardadAllKalaMosavabe" + moshtaryGharardadKalaModels.size());
        return moshtaryGharardadKalaModels;
    }

    private void getMarjoeePakhsh(final int getProgramType, String ccDarkhastFaktors) {
        Log.d("getProgram", "ccDarkhastFaktors:" + ccDarkhastFaktors);
        ElamMarjoeeForoshandehDAO elamMarjoeeForoshandehDAO = new ElamMarjoeeForoshandehDAO(mPresenter.getAppContext());
        elamMarjoeeForoshandehDAO.fetchElamMarjoeeForoshandeh(mPresenter.getAppContext(), activityNameForLog, ccDarkhastFaktors, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = elamMarjoeeForoshandehDAO.deleteAll();
                        boolean insertResult = elamMarjoeeForoshandehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getKalaOlaviatGheymat(getProgramType, anbarakAfrad, ccForoshandeh, ccMamorPakhsh, "-1");
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }

                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getKalaOlaviatGheymat(int getProgramType, String ccAnbarak, int ccForoshandeh, int ccMamorPakhsh, String ccKalaCode) {
        Log.d("getProgram", "ccDarkhastFaktors:" + ccDarkhastFaktors);
        KalaOlaviatGheymatDAO kalaOlaviatGheymatDAO = new KalaOlaviatGheymatDAO(mPresenter.getAppContext());
        kalaOlaviatGheymatDAO.fetchKalaOlaviat(mPresenter.getAppContext(), activityNameForLog, ccAnbarak, ccForoshandeh, ccMamorPakhsh, ccKalaCode, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = kalaOlaviatGheymatDAO.deleteAll();
                        boolean insertResult = kalaOlaviatGheymatDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoePishnahad(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }

                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private  void getNoePishnahad( int getProgramType){
        SuggestDAO suggestDAO = new SuggestDAO(BaseApplication.getContext());
        NoePishnahadDAO noePishnahadDAO = new NoePishnahadDAO(BaseApplication.getContext());

//        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//        getParameter(getProgramType);

        noePishnahadDAO.fetchNoePishnahad(BaseApplication.getContext(), "GetProgramActivity", new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                    boolean deleteResult = noePishnahadDAO.deleteAll();
                    boolean insertResult = noePishnahadDAO.insertGroup(arrayListData);
                    suggestDAO.deleteIsSend();
                    if (deleteResult && insertResult) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        if(noeMasouliat ==1 || noeMasouliat==2 || noeMasouliat==3 || noeMasouliat==6 || noeMasouliat==8)
                        {
                            getJashnvareh(getProgramType,ccForoshandeh,"-1");
                        }
                        else if (noeMasouliat == 4 || noeMasouliat == 5)
                        {
                            getJashnvareh(getProgramType,-1,ccMoshtaryPakhsh);
                        }
                        else
                        {
                            getParameter(getProgramType);
                        }

                    } else {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private  void getJashnvareh( int getProgramType, int ccForoshandeh, String ccMoshtarys){
        SuggestDAO suggestDAO = new SuggestDAO(BaseApplication.getContext());
        RptJashnvarehDAO rptJashnvarehDAO = new RptJashnvarehDAO(BaseApplication.getContext());

//        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//        getParameter(getProgramType);

        rptJashnvarehDAO.fetchRptJashnvareh(BaseApplication.getContext(), "GetProgramActivity", ccForoshandeh, ccMoshtarys, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rptJashnvarehDAO.deleteAll();
                        boolean insertResult = rptJashnvarehDAO.insertGroup(arrayListData);
                        suggestDAO.deleteIsSend();
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getParameter(getProgramType);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getParameter(final int getProgramType) {
        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
        final String lastDateTimeGetConfig = getProgramShared.getString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), "20190101 00:00:00");

        final ParameterDAO parameterDAO = new ParameterDAO(mPresenter.getAppContext());
        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat == 8) {
            ccMarkazAnbar = -1;
        } else {
            ccMarkazSazmanForosh = -1;
        }
        parameterDAO.fetchParameter(mPresenter.getAppContext(), "GetProgramActivity", "1", String.valueOf(ccMarkazSazmanForosh), String.valueOf(ccMarkazAnbar), lastDateTimeGetConfig, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (arrayListData.size() > 1) {
                            boolean deleteResult = parameterDAO.deleteAll();
                            boolean insertResult = parameterDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                getParameterChild(getProgramType, lastDateTimeGetConfig, String.valueOf(ccMarkazSazmanForosh));
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        } else {
                            getParameterChild(getProgramType, lastDateTimeGetConfig, String.valueOf(ccMarkazSazmanForosh));
                            //sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getParameterChild(final int getProgramType, String lastDateTimeGetConfig, String ccMarkazSazmanForosh) {
        final ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat == 8) {
            ccMarkazAnbar = -1;
        } else {
            ccMarkazSazmanForosh = "-1";
        }
        parameterChildDAO.fetchParameterChild(mPresenter.getAppContext(), "GetProgramActivity", "3", ccMarkazSazmanForosh, String.valueOf(ccMarkazAnbar), lastDateTimeGetConfig, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                if (arrayListData.size() > 0) {
                    boolean deleteResult = parameterChildDAO.deleteAll();
                    boolean insertResult = parameterChildDAO.insertGroup(arrayListData);
                    if (deleteResult && insertResult) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                            getBarkhordForoshandehBaMoshtary(getProgramType, String.valueOf(ccForoshandeh));
                        }
                    } else {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    }
                } else {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                        getBarkhordForoshandehBaMoshtary(getProgramType, String.valueOf(ccForoshandeh));
                    }
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getBarkhordForoshandehBaMoshtary(final int getProgramType, String ccForoshandeh) {
        String tedadMah = new ParameterChildDAO(mPresenter.getAppContext()).getValueByccChildParameter(Constants.CC_CHILD_MAX_ROOZ_PISHBINI_TAHVIL);
        tedadMah = (tedadMah == null || tedadMah.trim().equals("")) ? "1" : tedadMah;
        final BarkhordForoshandehBaMoshtaryDAO barkhordForoshandehBaMoshtaryDAO = new BarkhordForoshandehBaMoshtaryDAO(mPresenter.getAppContext());
        barkhordForoshandehBaMoshtaryDAO.fetchBarkhordForoshandehBaMoshtary(mPresenter.getAppContext(), activityNameForLog, ccForoshandeh, ccMoshtarys, tedadMah, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {

                        for (int i = 0; i < arrayListData.size(); i++) {
                            ((BarkhordForoshandehBaMoshtaryModel) arrayListData.get(i)).setExtraProp_IsOld(1);
                        }
                        boolean deleteResult = barkhordForoshandehBaMoshtaryDAO.deleteAll();
                        boolean insertResult = barkhordForoshandehBaMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            if (getProgramType == Constants.GET_PROGRAM_FULL()) {
                                saveLastGetProgramDate();
                                updateJayezehTakhfifVersion();
                                setLogToDB(Constants.LOG_EXCEPTION(), "GetProgram Success", "GetProgramModel", "", "getBarkhordForoshandehBaMoshtary", "onSuccess");

                            }
                            checkLastOlaviat();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }

                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void checkLastOlaviat() {
        MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(BaseApplication.getContext());
        OlaviatMorajehModel olaviatMorajehModel = moshtaryMorajehShodehRoozDAO.getOlaviatMorajeh();

        Log.d("getProgram","olaviat:"+ olaviatMorajehModel.getOlaviat() + "," + olaviatMorajehModel.getCcMoshtary());

        LastOlaviatMoshtaryShared lastOlaviatMoshtaryShared = new LastOlaviatMoshtaryShared(mPresenter.getAppContext());
        lastOlaviatMoshtaryShared.removeAll();

        lastOlaviatMoshtaryShared.putInt(LastOlaviatMoshtaryShared.OLAVIAT, olaviatMorajehModel.getOlaviat());
        lastOlaviatMoshtaryShared.putInt(LastOlaviatMoshtaryShared.CCMOSHTARY, olaviatMorajehModel.getCcMoshtary());
        lastOlaviatMoshtaryShared.putString(LastOlaviatMoshtaryShared.TARIKH, new SimpleDateFormat(Constants.DATE_TIME_FORMAT()).format(new Date()));

    }

    private void saveLastGetProgramDate() {
        GetProgramShared getProgramShared = new GetProgramShared(mPresenter.getAppContext());
        String currentDate = new SimpleDateFormat(Constants.DATE_TIME_WITH_SPACE()).format(new Date());
        getProgramShared.putString(getProgramShared.GREGORIAN_DATE_TIME_OF_GET_CONFIG(), currentDate);
        Log.d("GetProgram", "currentDate : " + currentDate);
    }


    ////////////////////////// GET PROGRAM AMARGAR //////////////////////////

    private void deletePorseshnameh() {
        new PorseshnamehTablighatDAO(mPresenter.getAppContext()).deleteAll();
        new PorseshnamehDAO(mPresenter.getAppContext()).deleteAll();
        new PorseshnamehShomareshDAO(mPresenter.getAppContext()).deleteAll();
        new VisitMoshtaryDAO(mPresenter.getAppContext()).deleteAll();
        getBrandAmargar();
    }

    private void getBrandAmargar() {
        final BrandDAO brandDAO = new BrandDAO(mPresenter.getAppContext());
        brandDAO.fetchAmargarBrand(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = brandDAO.deleteAll();
                        boolean insertResult = brandDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), itemCounter);
                            getAmargarGoroh();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAmargarGoroh() {
        final AmargarGorohDAO amargarGorohDAO = new AmargarGorohDAO(mPresenter.getAppContext());
        amargarGorohDAO.fetchamrgarGoroh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = amargarGorohDAO.deleteAll();
                        boolean insertResult = amargarGorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getKalaAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getKalaAmargar() {
        final KalaDAO kalaDAO = new KalaDAO(mPresenter.getAppContext());
        kalaDAO.fetchKalaAmargar(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = kalaDAO.deleteAll();
                        boolean insertResult = kalaDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getKalaGorohAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getKalaGorohAmargar() {
        final KalaGorohDAO kalaGorohDAO = new KalaGorohDAO(mPresenter.getAppContext());
        kalaGorohDAO.fetchAllvKalaGorohAmargar(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = kalaGorohDAO.deleteAll();
                        boolean insertResult = kalaGorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeTablighat();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeTablighat() {
        final NoeTablighatDAO noeTablighatDAO = new NoeTablighatDAO(mPresenter.getAppContext());
        noeTablighatDAO.fetchNoeTablighat(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeTablighatDAO.deleteAll();
                        boolean insertResult = noeTablighatDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getAmargarMarkazForosh();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAmargarMarkazForosh() {
        final AmargarMarkazSazmanForoshDAO amargarMarkazForoshDAO = new AmargarMarkazSazmanForoshDAO(mPresenter.getAppContext());
        amargarMarkazForoshDAO.fetchAmargarMarkazForosh(mPresenter.getAppContext(), activityNameForLog, String.valueOf(foroshandehMamorPakhshModel.getCcAmargar()), new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = amargarMarkazForoshDAO.deleteAll();
                        boolean insertResult = amargarMarkazForoshDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMahalAmargar(amargarMarkazForoshDAO);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMahalAmargar(AmargarMarkazSazmanForoshDAO amargarMarkazForoshDAO) {
        final MahalDAO mahalDAO = new MahalDAO(mPresenter.getAppContext());
        String markazForoshAmargar = amargarMarkazForoshDAO.getAllccMarkazForosh();
        Log.d("GetProgram", "markazForoshAmargar : " + markazForoshAmargar);
        if (markazForoshAmargar.trim().equals("")) {
            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
        } else {
            mahalDAO.fetchAllMahalByccMarkazForoshAmargar(mPresenter.getAppContext(), activityNameForLog, markazForoshAmargar, new RetrofitResponse() {
                @Override
                public void onSuccess(final ArrayList arrayListData) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = mahalDAO.deleteAll();
                            boolean insertResult = mahalDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getRotbehAmargar();
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();
                }

                @Override
                public void onFailed(String type, String error) {
                    mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                }
            });
        }
    }

    private void getRotbehAmargar() {
        final RotbehDAO rotbehDAO = new RotbehDAO(mPresenter.getAppContext());
        rotbehDAO.fetchRotbeh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = rotbehDAO.deleteAll();
                        boolean insertResult = rotbehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getPorseshnameTozihatAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getPorseshnameTozihatAmargar() {
        final PorseshnamehTozihatDAO porseshnamehTozihatDAO = new PorseshnamehTozihatDAO(mPresenter.getAppContext());
        porseshnamehTozihatDAO.fetchPorseshnamehTozihat(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = porseshnamehTozihatDAO.deleteAll();
                        boolean insertResult = porseshnamehTozihatDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMarkazForoshAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMarkazForoshAmargar() {
        final MarkazDAO markazDAO = new MarkazDAO(mPresenter.getAppContext());
        markazDAO.fetchAllMarkazForosh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = markazDAO.deleteAll();
                        boolean insertResult = markazDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getAllForoshandehAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getAllForoshandehAmargar() {
        final ForoshandehDAO foroshandehDAO = new ForoshandehDAO(mPresenter.getAppContext());
        foroshandehDAO.fetchAllForoshandeh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = foroshandehDAO.deleteAll();
                        boolean insertResult = foroshandehDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMasirFaalForoshandehAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMasirFaalForoshandehAmargar() {
        final MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        masirDAO.fetchAllMasirFaalForoshandeh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = masirDAO.deleteAll();
                        boolean insertResult = masirDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeFaaliatSenfAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeFaaliatSenfAmargar() {
        final NoeFaaliatForMoarefiMoshtaryJadidDAO noeFaaliatForMoarefiMoshtaryJadidDAO = new NoeFaaliatForMoarefiMoshtaryJadidDAO(mPresenter.getAppContext());
        noeFaaliatForMoarefiMoshtaryJadidDAO.fetchNoeFaaliatForMoarefiMoshtaryJadid(mPresenter.getAppContext(), activityNameForLog, "0", new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeFaaliatForMoarefiMoshtaryJadidDAO.deleteAll();
                        boolean insertResult = noeFaaliatForMoarefiMoshtaryJadidDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getGorohAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getGorohAmargar() {
        final GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        gorohDAO.fetchAllGoroh(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = gorohDAO.deleteAll();
                        boolean insertResult = gorohDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoePishnahadAmargar();
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }
    private  void getNoePishnahadAmargar(){
        SuggestDAO suggestDAO = new SuggestDAO(BaseApplication.getContext());
        NoePishnahadDAO noePishnahadDAO = new NoePishnahadDAO(BaseApplication.getContext());

//        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//        getElatAdamMoarefiMoshtaryAmargar();

        noePishnahadDAO.fetchNoePishnahad(BaseApplication.getContext(), "GetProgramActivity", new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                    boolean deleteResult = noePishnahadDAO.deleteAll();
                    boolean insertResult = noePishnahadDAO.insertGroup(arrayListData);
                    suggestDAO.deleteIsSend();
                    if (deleteResult && insertResult) {
                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        getElatAdamMoarefiMoshtaryAmargar();
                    } else {
                        sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }
    private void getElatAdamMoarefiMoshtaryAmargar() {
        final ElatAdamMoarefiMoshtaryDAO elatAdamMoarefiMoshtaryDAO = new ElatAdamMoarefiMoshtaryDAO(mPresenter.getAppContext());
        elatAdamMoarefiMoshtaryDAO.fetchElatAdamMoarefiMoshtary(mPresenter.getAppContext(), activityNameForLog, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = elatAdamMoarefiMoshtaryDAO.deleteAll();
                        boolean insertResult = elatAdamMoarefiMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetProgram(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    @Override
    public void getServerTime()
    {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP_GET_REQUEST()
                , "");
        String port = serverIPShared.getString(serverIPShared.PORT_GET_REQUEST()
                , "");
        if (serverIP.equals("") || port.equals(""))
        {
            mPresenter.notFoundServerIP();
        }
        else
        {
            PubFunc.LoginInfo loginInfo = new PubFunc().new LoginInfo();
            loginInfo.callLoginInfoService(mPresenter.getAppContext(), serverIP, port, new GetLoginInfoCallback()
            {
                @Override
                public void onSuccess(boolean validDiffTime, String serverDateTime, String deviceDateTime, long diff)
                {
                    String message = String.format("%1$s \n %2$s : %3$s \n %4$s : %5$s \n %6$s ( %7$s %8$s) : %9$s %10$s", mPresenter.getAppContext().getString(R.string.errorLocalDateTime),
                            mPresenter.getAppContext().getString(R.string.serverTime), serverDateTime, mPresenter.getAppContext().getString(R.string.deviceTime), deviceDateTime,
                            mPresenter.getAppContext().getString(R.string.timeDiff), Constants.ALLOWABLE_SERVER_LOCAL_TIME_DIFF(),
                            mPresenter.getAppContext().getString(R.string.second), diff, mPresenter.getAppContext().getString(R.string.second));
                    mPresenter.onGetServerTime(validDiffTime, message);
                }

                @Override
                public void onFailure(String error)
                {
                    setLogToDB(LogPPCModel.LOG_EXCEPTION, error, "MainModel", "", "getServerTime", "onFailure");
                    mPresenter.onGetServerTime(false, mPresenter.getAppContext().getString(R.string.errorGetDateTimeData));
                }
            });
        }
    }
}
