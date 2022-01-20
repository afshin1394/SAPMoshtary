
package com.saphamrah.MVP.Model;

        import static com.saphamrah.Utils.Constants.REST;
        import static com.saphamrah.Utils.Constants.gRPC;

        import android.os.Build;
        import android.os.Handler;
        import android.os.Message;
        import android.util.Log;

        import androidx.annotation.RequiresApi;

        import com.saphamrah.BaseMVP.CustomerListMapMVP;
        import com.saphamrah.BaseMVP.RptCustomersListByDistanceMVP;
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
        import com.saphamrah.DAO.ParameterChildDAO;
        import com.saphamrah.DAO.ListMoshtarianDAO;
        import com.saphamrah.Model.AllMoshtaryForoshandehModel;
        import com.saphamrah.Model.BargashtyModel;
        import com.saphamrah.Model.ForoshandehMamorPakhshModel;
        import com.saphamrah.Model.MasirModel;
        import com.saphamrah.Model.ModatVosolGorohModel;
        import com.saphamrah.Model.ModatVosolMarkazModel;
        import com.saphamrah.Model.ModatVosolModel;
        import com.saphamrah.Model.MoshtaryModel;
        import com.saphamrah.Model.ParameterChildModel;
        import com.saphamrah.Model.ListMoshtarianModel;
        import com.saphamrah.Model.ServerIpModel;
        import com.saphamrah.Network.RetrofitResponse;
        import com.saphamrah.PubFunc.PubFunc;
        import com.saphamrah.R;
        import com.saphamrah.Utils.Constants;

        import java.util.ArrayList;

public class CustomersListMapModel implements CustomerListMapMVP.ModelOps
{

    private CustomerListMapMVP.RequiredPresenterOps mPresenter;
    private Handler handler;
    private int itemCounter;
    private int ccMasir;
    private int ccForoshandeh = 0;
    private int ccMarkazForosh = 0;
    private int ccMarkazSazmanForoshSakhtarForosh = 0;
    private int ccMarkazSazmanForosh = 0;
    private int ccSazmanForosh = 0;
    private String ccGorohs;

    public CustomersListMapModel(CustomerListMapMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }
    private void sendThreadMessage(int status , int itemIndex)
    {
        Message message = new Message();
        message.arg1 = status;
        message.arg2 = itemIndex;
        handler.sendMessage(message);
    }


    @Override
    public void getRadiusConfig()
    {
        String maxRadius = "0";
        String stepRadius = "0";
        String ccChildParameters = Constants.CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST() + "," + Constants.CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST();
        ArrayList<ParameterChildModel> childParameterModelsConfig = new ParameterChildDAO(mPresenter.getAppContext()).getAllByccChildParameter(ccChildParameters);
        for (ParameterChildModel model : childParameterModelsConfig)
        {
            if (model.getCcParameterChild() == Constants.CC_CHILD_MAX_RADIUS_FOR_CUSTOMERS_LIST())
            {
                maxRadius = model.getValue();
            }
            else if (model.getCcParameterChild() == Constants.CC_CHILD_STEP_RADIUS_FOR_CUSTOMERS_LIST())
            {
                stepRadius = model.getValue();
            }
        }
        mPresenter.onGetRadiusConfig(maxRadius , stepRadius);
    }

    @Override
    public void getCustomerList(final String radius , String latitude , String longitude)
    {
        final ListMoshtarianDAO listMoshtarianDAO = new ListMoshtarianDAO(mPresenter.getAppContext());

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg)
            {
                if (msg.arg1 == 1)
                {
                    ArrayList<ListMoshtarianModel> listMoshtarianModels = listMoshtarianDAO.getAll();
                    Log.d("moshtarian" , "size : " + listMoshtarianModels.size());
                    for (ListMoshtarianModel model : listMoshtarianModels)
                    {
                        Log.d("moshtarian" , model.toString());
                    }
                    mPresenter.onGetCustomerList(listMoshtarianModels , radius);
                }
                else if (msg.arg1 == -1)
                {
                    mPresenter.onErrorGetCustomerList();
                }
                return false;
            }
        });
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
          switch (serverIpModel.getWebServiceType()){
              case REST:
                  listMoshtarianDAO.fetchByRadius(mPresenter.getAppContext(), "RptCustomersListByDistanceActivity", radius, latitude, longitude, new RetrofitResponse()
                  {
                      @Override
                      public void onSuccess(final ArrayList arrayListData)
                      {
                          Thread thread = new Thread()
                          {
                              @Override
                              public void run()
                              {
                                  boolean deleteResult = listMoshtarianDAO.deleteAll();
                                  boolean insertResult = listMoshtarianDAO.insertGroup(arrayListData);
                                  Log.d("moshtarian" , "size arrayListData : " + arrayListData.size());
                                  Log.d("moshtarian" , "deleteResult : " + deleteResult);
                                  Message message = new Message();
                                  if (deleteResult && insertResult)
                                  {
                                      message.arg1 = 1;
                                  }
                                  else
                                  {
                                      message.arg1 = -1;
                                  }
                                  handler.sendMessage(message);
                              }
                          };
                          thread.start();
                      }
                      @Override
                      public void onFailed(String type, String error)
                      {
                          mPresenter.onErrorGetCustomerList();
                          setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCustomersListByDistanceModel", "RptListVosolActivity", "updateListVosol", "onFailed");
                      }
                  });
                  break;

              case gRPC:
                  listMoshtarianDAO.fetchByRadiusGrpc(mPresenter.getAppContext(), "RptCustomersListByDistanceActivity", radius, latitude, longitude, new RetrofitResponse()
                  {
                      @Override
                      public void onSuccess(final ArrayList arrayListData)
                      {
                          Thread thread = new Thread()
                          {
                              @Override
                              public void run()
                              {
                                  boolean deleteResult = listMoshtarianDAO.deleteAll();
                                  boolean insertResult = listMoshtarianDAO.insertGroup(arrayListData);
                                  Log.d("moshtarian" , "size arrayListData : " + arrayListData.size());
                                  Log.d("moshtarian" , "deleteResult : " + deleteResult);
                                  Message message = new Message();
                                  if (deleteResult && insertResult)
                                  {
                                      message.arg1 = 1;
                                  }
                                  else
                                  {
                                      message.arg1 = -1;
                                  }
                                  handler.sendMessage(message);
                              }
                          };
                          thread.start();
                      }
                      @Override
                      public void onFailed(String type, String error)
                      {
                          mPresenter.onErrorGetCustomerList();
                          setLogToDB(Constants.LOG_EXCEPTION(), String.format(" type : %1$s \n error : %2$s", type , error), "RptCustomersListByDistanceModel", "RptListVosolActivity", "updateListVosol", "onFailed");
                      }
                  });
                  break;

          }

    }

    @Override
    public void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(mPresenter.getAppContext(), logType, message, logClass , logActivity , functionParent , functionChild);
    }

    @Override
    public void onDestroy()
    {

    }
    @Override
    public void getSelectedCustomerInfo(ListMoshtarianModel listMoshtarianModel)
    {
        /*MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());*/
        //----------------------- Foroshandeh --------------------------------
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        MoshtaryDAO moshtaryDAO =new MoshtaryDAO(mPresenter.getAppContext());


        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getIsSelect();

        final int sumOfItems = mPresenter.getAppContext().getResources().getStringArray(R.array.getCustomerInfo).length;
        itemCounter = 0;
        ccForoshandeh = 0;
        ccMarkazForosh = 0;
        ccMarkazSazmanForoshSakhtarForosh = 0;
        ccMarkazSazmanForosh = 0;
        ccSazmanForosh = 0;
        ccGorohs = "";
        ccGorohs = new MoshtaryDAO(mPresenter.getAppContext()).getAllccNoeSenf();
        ccMasir = moshtaryDAO.getCcMasirByCcMoshtary(listMoshtarianModel.getCcMoshtary());
        Log.i("ccMasir", "getSelectedCustomerInfo: "+ccMasir);



        ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
        ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
        ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
        ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
        ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();


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

        getMoshtary(listMoshtarianModel);
    }
    private void getMoshtary(final ListMoshtarianModel listMoshtarianModel)
    {
        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        moshtaryDAO.fetchAllMoshtaryByccMasir(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), "-1", String.valueOf(listMoshtarianModel.getCodeMoshtaryOld()), new RetrofitResponse() {
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
                                getMoshtaryEtebar(listMoshtarianModel , String.valueOf(ccSazmanForosh));
                            }
                            else
                            {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , itemCounter);
                            }
                        }
                        else
                        {
                            PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(() -> {
                                mPresenter.onFailedGetNewItem(itemCounter , mPresenter.getAppContext().getResources().getString(R.string.errorIncompleteMoshtaryDataAndCall));
                            });

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
    private void getMoshtaryEtebar(final ListMoshtarianModel listMoshtarianModel , String ccSazmanForosh)
    {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());

        switch (serverIpModel.getWebServiceType()){
            case REST:
                moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForosh(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(listMoshtarianModel.getCcMoshtary()), ccSazmanForosh, new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData) {
                        moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(listMoshtarianModel.getCcMoshtary());

                        if (moshtaryEtebarSazmanForoshDAO.insertGroup(arrayListData))
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);

                            getBargashty(listMoshtarianModel);
                        }
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(() -> {
                            mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                        });
                    }
                });
                break;

            case gRPC:
                moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForoshGrpc(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(listMoshtarianModel.getCcMoshtary()), ccSazmanForosh, new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData) {
                        moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(listMoshtarianModel.getCcMoshtary());

                        if (moshtaryEtebarSazmanForoshDAO.insertGroup(arrayListData))
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);

                            getBargashty(listMoshtarianModel);
                        }
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(() -> {
                            mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                        });
                    }
                });

                break;

        }

    }


    private void getBargashty(final ListMoshtarianModel listMoshtarianModel)
    {
        final BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        ServerIpModel serverIpModel = new PubFunc().new NetworkUtils().getServerFromShared(mPresenter.getAppContext());
        switch (serverIpModel.getWebServiceType()){
            case REST:
                bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData)
                    {
                        bargashtyDAO.deleteByccMoshtary(listMoshtarianModel.getCcMoshtary());
                        for ( int i=0 ; i < arrayListData.size() ; i++ )
                        {
                            Log.d("GetcustomerInfo" , "BargashtyModel:" + ((BargashtyModel)arrayListData.get(i)).toString());
                        }
                        if (bargashtyDAO.insertGroup(arrayListData))
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryParent(listMoshtarianModel);
                        }
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(() -> {
                            mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                        });
                    }
                });
                break;

            case gRPC:
                bargashtyDAO.fetchBargashtyGrpc(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), new RetrofitResponse() {
                    @Override
                    public void onSuccess(ArrayList arrayListData)
                    {
                        bargashtyDAO.deleteByccMoshtary(listMoshtarianModel.getCcMoshtary());
                        for ( int i=0 ; i < arrayListData.size() ; i++ )
                        {
                            Log.d("GetcustomerInfo" , "BargashtyModel:" + ((BargashtyModel)arrayListData.get(i)).toString());
                        }
                        if (bargashtyDAO.insertGroup(arrayListData))
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryParent(listMoshtarianModel);
                        }
                    }

                    @Override
                    public void onFailed(String type, String error) {
                        PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(() -> {
                            mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
                        });
                    }
                });
                break;
        }

    }


    private void getMoshtaryParent(final ListMoshtarianModel listMoshtarianModel)
    {
        getMoshtaryAddress(listMoshtarianModel);
    }
    private void getMoshtaryAddress(final ListMoshtarianModel listMoshtarianModel)
    {
        final MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        moshtaryAddressDAO.fetchAllvMoshtaryAddressByNoeMasouliat(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), String.valueOf(ccMasir), String.valueOf(listMoshtarianModel.getCcMoshtary()), new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()

                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = moshtaryAddressDAO.deleteByccMoshtary(listMoshtarianModel.getCcMoshtary());
                        boolean insertResult = moshtaryAddressDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult)
                        {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                            getMoshtaryAfrad(listMoshtarianModel);
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
    private void getMoshtaryAfrad(final ListMoshtarianModel listMoshtarianModel)
    {
        final MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        moshtaryAfradDAO.fetchAllvMoshtaryAfrad(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(listMoshtarianModel.getCcMoshtary()), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData)
            {
                moshtaryAfradDAO.deleteByccMoshtary(listMoshtarianModel.getCcMoshtary());
                if (moshtaryAfradDAO.insertGroup(arrayListData))
                {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL() , ++itemCounter);
                    getModatVosol(listMoshtarianModel);
                }
            }

            @Override
            public void onFailed(String type, String error)
            {
                PubFunc.ConcurrencyUtils.getInstance().runOnUiThread(() -> {
                    mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));

                });
            }
        });
    }
    private void getModatVosol(final ListMoshtarianModel listMoshtarianModel)
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
                    getModatVosolGoroh(listMoshtarianModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }
    private void getModatVosolGoroh(final ListMoshtarianModel listMoshtarianModel)
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
                    getModatVosolMarkazPakhsh(listMoshtarianModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }
    private void getModatVosolMarkazPakhsh(final ListMoshtarianModel listMoshtarianModel)
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
                    getMasirVaznHajmMashin(listMoshtarianModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter , String.format(" type : %1$s \n error : %2$s", type , error));
            }
        });
    }
    private void getMasirVaznHajmMashin(final ListMoshtarianModel listMoshtarianModel)
    {
        final MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());


        masirVaznHajmMashinDAO.fetchMasirVaznHajmMashin(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccMasir), new RetrofitResponse()
        {
            @Override
            public void onSuccess(final ArrayList arrayListData)
            {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        boolean deleteResult = masirVaznHajmMashinDAO.deleteByccMoshtary(listMoshtarianModel.getCcMoshtary());
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
        moshtaryMorajehShodehRoozDAO.fetchMoshtaryMorajehShodehRooz(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh),String.valueOf(ccMasir), new RetrofitResponse()
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
}
