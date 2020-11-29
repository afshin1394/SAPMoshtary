package com.saphamrah.MVP.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.CustomersListMVP;
import com.saphamrah.DAO.AllMoshtaryForoshandehDAO;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.MasirDAO;
import com.saphamrah.DAO.MasirVaznHajmMashinDAO;
import com.saphamrah.DAO.ModatVosolDAO;
import com.saphamrah.DAO.ModatVosolGorohDAO;
import com.saphamrah.DAO.ModatVosolMarkazDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryAfradDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.MoshtaryMorajehShodehRoozDAO;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.ModatVosolGorohModel;
import com.saphamrah.Model.ModatVosolMarkazModel;
import com.saphamrah.Model.ModatVosolModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class CustomersListModel implements CustomersListMVP.ModelOps
{

    private CustomersListMVP.RequiredPresenterOps mPresenter;

    public CustomersListModel(CustomersListMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }



    @Override
    public void getAllMasirs()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        int ccForoshandeh = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh().getCcForoshandeh();

        AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext());
        ArrayList<MasirModel> masirModels = allMoshtaryForoshandehDAO.getAllMasirsByccForoshande(ccForoshandeh);
        MasirModel masirModel = new MasirModel();
        masirModel.setCcMasir(-1);
        masirModel.setNameMasir(mPresenter.getAppContext().getResources().getString(R.string.allMasirs));
        masirModel.setCcForoshandeh(ccForoshandeh);
        masirModels.add(masirModel);
        mPresenter.onGetAllMasirs(masirModels);
    }

    @Override
    public void getAllCustomers()
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        int ccForoshandeh = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh().getCcForoshandeh();

        AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext());
        ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = allMoshtaryForoshandehDAO.getAllByccForoshandeh(ccForoshandeh);
        mPresenter.onGetAllCustomers(allMoshtaryForoshandehModels);
    }

    @Override
    public void getCustomersByccMasir(int ccMasir)
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        int ccForoshandeh = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh().getCcForoshandeh();

        AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext());
        ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = allMoshtaryForoshandehDAO.getAllByccMasir(ccForoshandeh , ccMasir);
        mPresenter.onGetAllCustomers(allMoshtaryForoshandehModels);
    }


    private Handler handler;
    private int itemCounter;
    private int ccForoshandeh = 0;
    private int ccMarkazForosh = 0;
    private int ccMarkazSazmanForoshSakhtarForosh = 0;
    private int ccMarkazSazmanForosh = 0;
    private int ccSazmanForosh = 0;
    private String ccGorohs;

    @Override
    public void getSelectedCustomerInfo(int position, final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        /*MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());*/
        //----------------------- Foroshandeh --------------------------------
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels = foroshandehMamorPakhshDAO.getAll();

        final int sumOfItems = mPresenter.getAppContext().getResources().getStringArray(R.array.getCustomerInfo).length;
        itemCounter = 0;
        ccForoshandeh = 0;
        ccMarkazForosh = 0;
        ccMarkazSazmanForoshSakhtarForosh = 0;
        ccMarkazSazmanForosh = 0;
        ccSazmanForosh = 0;
        ccGorohs = "";
		ccGorohs = new MoshtaryDAO(mPresenter.getAppContext()).getAllccNoeSenf();																		 
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = new ForoshandehMamorPakhshModel();
        if (foroshandehMamorPakhshModels.size() > 0)
        {
            foroshandehMamorPakhshModel = foroshandehMamorPakhshModels.get(0);
            ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
            ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
            ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
            ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
            ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
        }

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL())
                {
                    mPresenter.onSuccessfullyGetNewItem(sumOfItems , msg.arg2);
                }
                else if (msg.arg1 == Constants.BULK_INSERT_FAILED())
                {
                    mPresenter.onFailedGetNewItem(msg.arg2 , mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });

        getMoshtary(allMoshtaryForoshandehModel);
    }

    private void sendThreadMessage(int status , int itemIndex)
    {
        Message message = new Message();
        message.arg1 = status;
        message.arg2 = itemIndex;
        handler.sendMessage(message);
    }


    private void getMoshtary(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        ArrayList<MasirModel> masirModels = masirDAO.getAll();
        String ccMasir = "-1";
        for (MasirModel model : masirModels)
        {
            ccMasir += "," + model.getCcMasir();
        }

        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        moshtaryDAO.fetchAllMoshtaryByccMasir(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), ccMasir, String.valueOf(allMoshtaryForoshandehModel.getCodeMoshtary()), new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        if (arrayListData.size() > 0)
                        {
                            MoshtaryModel model = (MoshtaryModel)arrayListData.get(0);
                            int ccMoshtary = model.getCcMoshtary();
                            moshtaryDAO.deleteByccMoshtary(ccMoshtary);
                            ((MoshtaryModel) arrayListData.get(0)).setExtraProp_MoshtaryMojazKharejAzMasir(1);
                            ((MoshtaryModel) arrayListData.get(0)).setExtraProp_IsOld(1);
                            ((MoshtaryModel) arrayListData.get(0)).setOlaviat(999);

                            if (moshtaryDAO.insertGroup(arrayListData))
                            {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , itemCounter);
                                getMoshtaryEtebar(allMoshtaryForoshandehModel , String.valueOf(ccSazmanForosh));
                            }
                            else
                            {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , itemCounter);
                            }
                        }
                        else
                        {
                            mPresenter.onFailedGetNewItem(itemCounter , mPresenter.getAppContext().getResources().getString(R.string.errorIncompleteMoshtaryDataAndCall));
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }

    private void getMoshtaryEtebar(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel , String ccSazmanForosh)
    {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForosh(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), ccSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());

                if (moshtaryEtebarSazmanForoshDAO.insertGroup(arrayListData))
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
					sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
					
                    getBargashty(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    /*private void getMoshtaryGoroh(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final MoshtaryGorohDAO moshtaryGorohDAO = new MoshtaryGorohDAO(mPresenter.getAppContext());
        moshtaryGorohDAO.fetchAllvMoshtaryGoroh(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                moshtaryGorohDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                if (moshtaryGorohDAO.insertGroup(arrayListData))
                {
                    ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
                    ccGorohs = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_GOROH_MOSHTARY_KHORDE());
                    ArrayList<MoshtaryGorohModel> moshtarygorohs = moshtaryGorohDAO.getAll();
                    for (MoshtaryGorohModel model : moshtarygorohs)
                    {
                        if (!ccGorohs.contains(String.valueOf(model.getCcGoroh())))
                        {
                            ccGorohs += "," + model.getCcGoroh();
                        }
                    }
                    Log.d("GetcustomerInfo" , "ccGorohs : " + ccGorohs);
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getBargashty(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }*/


    private void getBargashty(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcForoshandeh()), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                bargashtyDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                for (int i=0 ; i<arrayListData.size() ; i++)
                {
                    Log.d("GetcustomerInfo" , "BargashtyModel : " + ((BargashtyModel)arrayListData.get(i)).toString());
                }
                if (bargashtyDAO.insertGroup(arrayListData))
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getMoshtaryParent(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }

    private void getMoshtaryParent(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        moshtaryDAO.fetchMoshtaryParent(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                for (int i=0 ; i<arrayListData.size() ; i++)
                {
                    Log.d("GetcustomerInfo" , "MoshtaryModel : " + arrayListData.get(i).toString());
                }
                if (moshtaryDAO.updateccMoshtaryParentInMoshtary(arrayListData))
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getMoshtaryAddress(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    private void getMoshtaryAddress(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        moshtaryAddressDAO.fetchAllvMoshtaryAddressByNoeMasouliat(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), String.valueOf(allMoshtaryForoshandehModel.getCcMasir()), String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
																  
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = moshtaryAddressDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                        boolean insertResult = moshtaryAddressDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryAfrad(allMoshtaryForoshandehModel);
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
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    private void getMoshtaryAfrad(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        moshtaryAfradDAO.fetchAllvMoshtaryAfrad(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                moshtaryAfradDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                if (moshtaryAfradDAO.insertGroup(arrayListData))
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getModatVosol(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) 
			{
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    private void getModatVosol(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final ModatVosolDAO modatVosolDAO= new ModatVosolDAO(mPresenter.getAppContext());
        modatVosolDAO.fetchAllvModatVosolByccMarkazForoshGoroh(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccMarkazSazmanForoshSakhtarForosh), ccGorohs, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                for (int i=0 ; i<arrayListData.size() ; i++)
                {
                    Log.d("GetcustomerInfo" , "ModatVosol : " + ((ModatVosolModel)arrayListData.get(i)).toString());
                }
                Log.d("GetcustomerInfo" , "ccMarkazForosh : " + ccMarkazSazmanForoshSakhtarForosh + " , ccGorohs : " + ccGorohs);
                modatVosolDAO.deleteByccMarkazAndccGoroh(String.valueOf(ccMarkazSazmanForoshSakhtarForosh), ccGorohs);
                if (modatVosolDAO.insertGroup(arrayListData))
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getModatVosolGoroh(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }

    private void getModatVosolGoroh(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final ModatVosolGorohDAO modatVosolGorohDAO= new ModatVosolGorohDAO(mPresenter.getAppContext());
        modatVosolGorohDAO.fetchAllModatVosolGoroh(mPresenter.getAppContext(), "CustomersListActivity", ccGorohs, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                for (int i=0 ; i<arrayListData.size() ; i++)
                {
                    Log.d("GetcustomerInfo" , "ModatVosolgoroh : " + ((ModatVosolGorohModel)arrayListData.get(i)).toString());
                }
                modatVosolGorohDAO.deleteAll();
                if (modatVosolGorohDAO.insertGroup(arrayListData))
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getModatVosolMarkazPakhsh(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }


    private void getModatVosolMarkazPakhsh(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final ModatVosolMarkazDAO modatVosolMarkazPakhshDAO = new ModatVosolMarkazDAO(mPresenter.getAppContext());
        modatVosolMarkazPakhshDAO.fetchAllModatVosolMarkaz(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccMarkazForosh), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                for (int i=0 ; i<arrayListData.size() ; i++)
                {
                    Log.d("GetcustomerInfo" , "ModatVosolMarkazPakhsh : " + ((ModatVosolMarkazModel)arrayListData.get(i)).toString());
                }
                modatVosolMarkazPakhshDAO.deleteAll();
                if (modatVosolMarkazPakhshDAO.insertGroup(arrayListData))
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getMasirVaznHajmMashin(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }

    private void getMasirVaznHajmMashin(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel)
    {
        final MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
        masirVaznHajmMashinDAO.fetchMasirVaznHajmMashin(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMasir()), new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = masirVaznHajmMashinDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                        boolean insertResult = masirVaznHajmMashinDAO.insertGroup(arrayListData);
                        if (insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryMorajehShodeRooz();
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }

    private void getMoshtaryMorajehShodeRooz()
    {
        final MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(mPresenter.getAppContext());
        String ccMasirs = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext()).getAllccMasirsWithComma(ccForoshandeh);
        moshtaryMorajehShodehRoozDAO.fetchMoshtaryMorajehShodehRooz(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), ccMasirs, new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        if (arrayListData.size() > 0)
                        {
                            boolean deleteResult = moshtaryMorajehShodehRoozDAO.deleteAll();
                            boolean insertResult = moshtaryMorajehShodehRoozDAO.insertGroup(arrayListData);
                            if (insertResult)
                            {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            }
                            else
                            {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED() , ++itemCounter);
                            }
                        }
                        else
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                        }
                    }
                };
                thread.start();
            }
            @Override
            public void onFailed(String type, String error)
            {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
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
    public void getAllMoshtarian(String tag, int ccForoshandeh) {
        AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(BaseApplication.getContext());

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    getAllCustomers();
                    mPresenter.onUpdateData();
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.failedUpdate();
                }
                return false;
            }
        });

        allMoshtaryForoshandehDAO.fetchAllMoshtaryforoshandeh(BaseApplication.getContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {

                Thread thread = new Thread()
                {
                    @Override
                    public void run(){

                        boolean deleteResult = allMoshtaryForoshandehDAO.deleteAll();
                        boolean insertResult = allMoshtaryForoshandehDAO.insertGroup(arrayListData);
                        Message message = new Message();
                        if (deleteResult && insertResult)
                        {
                            mPresenter.onUpdateData();
                            message.arg1 = 1;
                        }
                        else
                        {
                            mPresenter.failedUpdate();
                            message.arg1 = -1;

                        }
                        handler.sendMessage(message);

                    }
                };
                thread.start();

            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.failedUpdate();
            }
        });

    }
    }


