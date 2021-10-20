package com.saphamrah.MVP.Model;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.CustomersListMVP;
import com.saphamrah.DAO.AllMoshtaryForoshandehDAO;
import com.saphamrah.DAO.AnbarakAfradDAO;
import com.saphamrah.DAO.BargashtyDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KalaZaribForoshDAO;
import com.saphamrah.DAO.MasirDAO;
import com.saphamrah.DAO.MasirVaznHajmMashinDAO;
import com.saphamrah.DAO.ModatVosolDAO;
import com.saphamrah.DAO.ModatVosolGorohDAO;
import com.saphamrah.DAO.ModatVosolMarkazDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryAfradDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.MoshtaryGharardadDAO;
import com.saphamrah.DAO.MoshtaryGharardadKalaDAO;
import com.saphamrah.DAO.MoshtaryMorajehShodehRoozDAO;
import com.saphamrah.DAO.NoeVosolMoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.Model.AllMoshtaryForoshandehModel;
import com.saphamrah.Model.AnbarakAfradModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.KalaZaribForoshModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.Model.ModatVosolGorohModel;
import com.saphamrah.Model.ModatVosolMarkazModel;
import com.saphamrah.Model.ModatVosolModel;
import com.saphamrah.Model.MoshtaryGharardadKalaModel;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class CustomersListModel implements CustomersListMVP.ModelOps {

    private CustomersListMVP.RequiredPresenterOps mPresenter;
    private String activityNameForLog = "CustomerListActivity";

    public CustomersListModel(CustomersListMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getAllMasirs() {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        int ccForoshandeh = foroshandehMamorPakhshDAO.getIsSelect().getCcForoshandeh();

        AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext());
        // ArrayList<MasirModel> masirModels = allMoshtaryForoshandehDAO.getAllMasirsByccForoshande(ccForoshandeh);
        ArrayList<MasirModel> masirModels = allMoshtaryForoshandehDAO.getAllMasirs();
        MasirModel masirModel = new MasirModel();
        masirModel.setCcMasir(-1);
        masirModel.setNameMasir(mPresenter.getAppContext().getResources().getString(R.string.allMasirs));
        masirModel.setCcForoshandeh(ccForoshandeh);
        masirModels.add(masirModel);
        mPresenter.onGetAllMasirs(masirModels);
    }

    @Override
    public void getAllCustomers() {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext());
        // int ccForoshandeh = foroshandehMamorPakhshDAO.getIsSelect().getCcForoshandeh();


        //ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = allMoshtaryForoshandehDAO.getAllByccForoshandeh(ccForoshandeh);
        ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = allMoshtaryForoshandehDAO.getAll();
        mPresenter.onGetAllCustomers(allMoshtaryForoshandehModels);
    }

    @Override
    public void getCustomersByccMasir(int ccMasir) {
        //ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext());

        int ccForoshandeh = allMoshtaryForoshandehDAO.getccForoshandehByccMasir(ccMasir);

        ArrayList<AllMoshtaryForoshandehModel> allMoshtaryForoshandehModels = allMoshtaryForoshandehDAO.getAllByccMasir(ccForoshandeh, ccMasir);
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
    private int noeMoshtary;

    @Override
    public void getSelectedCustomerInfo(int position, final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
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
        if (foroshandehMamorPakhshModels.size() > 0) {
            foroshandehMamorPakhshModel = foroshandehMamorPakhshModels.get(0);
            //ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
            ccMarkazForosh = foroshandehMamorPakhshModel.getCcMarkazForosh();
            ccMarkazSazmanForoshSakhtarForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh();
            ccMarkazSazmanForosh = foroshandehMamorPakhshModel.getCcMarkazSazmanForosh();
            ccSazmanForosh = foroshandehMamorPakhshModel.getCcSazmanForosh();
        }

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == Constants.BULK_INSERT_SUCCESSFUL()) {
                    mPresenter.onSuccessfullyGetNewItem(sumOfItems, msg.arg2);
                } else if (msg.arg1 == Constants.BULK_INSERT_FAILED()) {
                    mPresenter.onFailedGetNewItem(msg.arg2, mPresenter.getAppContext().getResources().getString(R.string.errorUpdateDatabase));
                }
                return false;
            }
        });

        getMoshtary(allMoshtaryForoshandehModel);
    }

    private void sendThreadMessage(int status, int itemIndex) {
        Message message = new Message();
        message.arg1 = status;
        message.arg2 = itemIndex;
        handler.sendMessage(message);
    }


    private void getMoshtary(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        MasirDAO masirDAO = new MasirDAO(mPresenter.getAppContext());
        ArrayList<MasirModel> masirModels = masirDAO.getAll();
        String ccMasir = "-1";
        for (MasirModel model : masirModels) {
            ccMasir += "," + model.getCcMasir();
        }

        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        ccForoshandeh = allMoshtaryForoshandehModel.getCcForoshandeh();
        moshtaryDAO.fetchAllMoshtaryByccMasir(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), ccMasir, String.valueOf(allMoshtaryForoshandehModel.getCodeMoshtary()), new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (arrayListData.size() > 0) {
                            MoshtaryModel model = (MoshtaryModel) arrayListData.get(0);
                            int ccMoshtary = model.getCcMoshtary();
                            noeMoshtary = model.getCcNoeMoshtary();
                            moshtaryDAO.deleteByccMoshtary(ccMoshtary);
                            ((MoshtaryModel) arrayListData.get(0)).setExtraProp_MoshtaryMojazKharejAzMasir(1);
                            ((MoshtaryModel) arrayListData.get(0)).setExtraProp_IsOld(1);
                            ((MoshtaryModel) arrayListData.get(0)).setOlaviat(0);

                            if (moshtaryDAO.insertGroup(arrayListData)) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), itemCounter);
                                getMoshtaryEtebar(allMoshtaryForoshandehModel, String.valueOf(ccSazmanForosh));
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), itemCounter);
                            }
                        } else {
                            mPresenter.onFailedGetNewItem(itemCounter, mPresenter.getAppContext().getResources().getString(R.string.errorIncompleteMoshtaryDataAndCall));
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryEtebar(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel, String ccSazmanForosh) {
        final MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        moshtaryEtebarSazmanForoshDAO.fetchAllvMoshtaryEtebarSazmanForosh(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), ccSazmanForosh, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());

                if (moshtaryEtebarSazmanForoshDAO.insertGroup(arrayListData)) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);

                    getBargashty(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
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


    private void getBargashty(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        final BargashtyDAO bargashtyDAO = new BargashtyDAO(mPresenter.getAppContext());
        bargashtyDAO.fetchBargashty(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                bargashtyDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                for (int i = 0; i < arrayListData.size(); i++) {
                    Log.d("GetcustomerInfo", "BargashtyModel : " + ((BargashtyModel) arrayListData.get(i)).toString());
                }
                if (bargashtyDAO.insertGroup(arrayListData)) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMoshtaryParent(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryParent(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
        getMoshtaryAddress(allMoshtaryForoshandehModel);

//        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
//        moshtaryDAO.fetchMoshtaryParent(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), new RetrofitResponse() {
//            @Override
//            public void onSuccess(ArrayList arrayListData) {
//                for (int i = 0; i < arrayListData.size(); i++) {
//                    Log.d("GetcustomerInfo", "MoshtaryModel : " + arrayListData.get(i).toString());
//                }
//                if (moshtaryDAO.updateccMoshtaryParentInMoshtary(arrayListData)) {
//                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
//                    getMoshtaryAddress(allMoshtaryForoshandehModel);
//                }
//            }
//
//            @Override
//            public void onFailed(String type, String error) {
//                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
//            }
//        });
    }


    private void getMoshtaryAddress(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        final MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        moshtaryAddressDAO.fetchAllvMoshtaryAddressByNoeMasouliat(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), String.valueOf(allMoshtaryForoshandehModel.getCcMasir()), String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = moshtaryAddressDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                        boolean insertResult = moshtaryAddressDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryAfrad(allMoshtaryForoshandehModel);
                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getMoshtaryAfrad(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        final MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        moshtaryAfradDAO.fetchAllvMoshtaryAfrad(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMoshtary()), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                moshtaryAfradDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                if (moshtaryAfradDAO.insertGroup(arrayListData)) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getModatVosol(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getModatVosol(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        final ModatVosolDAO modatVosolDAO = new ModatVosolDAO(mPresenter.getAppContext());
        modatVosolDAO.fetchAllvModatVosolByccMarkazForoshGoroh(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccMarkazSazmanForoshSakhtarForosh), ccGorohs, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                for (int i = 0; i < arrayListData.size(); i++) {
                    Log.d("GetcustomerInfo", "ModatVosol : " + ((ModatVosolModel) arrayListData.get(i)).toString());
                }
                Log.d("GetcustomerInfo", "ccMarkazForosh : " + ccMarkazSazmanForoshSakhtarForosh + " , ccGorohs : " + ccGorohs);
                modatVosolDAO.deleteByccMarkazAndccGoroh(String.valueOf(ccMarkazSazmanForoshSakhtarForosh), ccGorohs);
                if (modatVosolDAO.insertGroup(arrayListData)) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getModatVosolGoroh(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getModatVosolGoroh(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        final ModatVosolGorohDAO modatVosolGorohDAO = new ModatVosolGorohDAO(mPresenter.getAppContext());
        modatVosolGorohDAO.fetchAllModatVosolGoroh(mPresenter.getAppContext(), "CustomersListActivity", ccGorohs, new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                for (int i = 0; i < arrayListData.size(); i++) {
                    Log.d("GetcustomerInfo", "ModatVosolgoroh : " + ((ModatVosolGorohModel) arrayListData.get(i)).toString());
                }
                modatVosolGorohDAO.deleteAll();
                if (modatVosolGorohDAO.insertGroup(arrayListData)) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getModatVosolMarkazPakhsh(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }


    private void getModatVosolMarkazPakhsh(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        final ModatVosolMarkazDAO modatVosolMarkazPakhshDAO = new ModatVosolMarkazDAO(mPresenter.getAppContext());
        modatVosolMarkazPakhshDAO.fetchAllModatVosolMarkaz(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccMarkazForosh), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {
                for (int i = 0; i < arrayListData.size(); i++) {
                    Log.d("GetcustomerInfo", "ModatVosolMarkazPakhsh : " + ((ModatVosolMarkazModel) arrayListData.get(i)).toString());
                }
                modatVosolMarkazPakhshDAO.deleteAll();
                if (modatVosolMarkazPakhshDAO.insertGroup(arrayListData)) {
                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                    getMasirVaznHajmMashin(allMoshtaryForoshandehModel);
                }
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMasirVaznHajmMashin(final AllMoshtaryForoshandehModel allMoshtaryForoshandehModel) {
        final MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
        masirVaznHajmMashinDAO.fetchMasirVaznHajmMashin(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(allMoshtaryForoshandehModel.getCcMasir()), new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = masirVaznHajmMashinDAO.deleteByccMoshtary(allMoshtaryForoshandehModel.getCcMoshtary());
                        boolean insertResult = masirVaznHajmMashinDAO.insertGroup(arrayListData);
                        if (insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getNoeVosolMoshtary(allMoshtaryForoshandehModel);

                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getNoeVosolMoshtary(AllMoshtaryForoshandehModel moshtaryForoshandehModel) {
        final NoeVosolMoshtaryDAO noeVosolMoshtaryDAO = new NoeVosolMoshtaryDAO(mPresenter.getAppContext());
        ccGorohs = new MoshtaryDAO(mPresenter.getAppContext()).getAllccNoeSenf();
        noeVosolMoshtaryDAO.fetchNoeVosolMoshtary(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccMarkazSazmanForosh), ccGorohs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        boolean deleteResult = noeVosolMoshtaryDAO.deleteAll();
                        boolean insertResult = noeVosolMoshtaryDAO.insertGroup(arrayListData);
                        if (deleteResult && insertResult) {
                            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                            getMoshtaryMorajehShodeRooz(moshtaryForoshandehModel);

                        } else {
                            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                        }
                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getMoshtaryMorajehShodeRooz(AllMoshtaryForoshandehModel moshtaryForoshandehModel) {
        final MoshtaryMorajehShodehRoozDAO moshtaryMorajehShodehRoozDAO = new MoshtaryMorajehShodehRoozDAO(mPresenter.getAppContext());
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());

        String ccMasirs = new AllMoshtaryForoshandehDAO(mPresenter.getAppContext()).getAllccMasirsWithComma(ccForoshandeh);
        moshtaryMorajehShodehRoozDAO.fetchMoshtaryMorajehShodehRooz(mPresenter.getAppContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), ccMasirs, new RetrofitResponse() {
            @Override
            public void onSuccess(final ArrayList arrayListData) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (arrayListData.size() > 0) {
                            boolean deleteResult = moshtaryMorajehShodehRoozDAO.deleteAll();
                            boolean insertResult = moshtaryMorajehShodehRoozDAO.insertGroup(arrayListData);
                            if (deleteResult && insertResult) {
                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                //TODO add kala zarib forosh

                                getKalaZaribForosh(moshtaryForoshandehModel,ccForoshandeh);


                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                            //TODO add kala zarib forosh

                        } else {
                         sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                         getKalaZaribForosh(moshtaryForoshandehModel,ccForoshandeh);
                        }

                    }
                };
                thread.start();
            }

            @Override
            public void onFailed(String type, String error) {
                mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
    }

    private void getKalaZaribForosh(AllMoshtaryForoshandehModel moshtaryForoshandehModel, int ccForoshandeh) {

        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        AnbarakAfradDAO anbarakAfradDAO = new AnbarakAfradDAO(mPresenter.getAppContext());
        int ccAnbarak = -1;
        ArrayList<AnbarakAfradModel> anbarakAfradModels= anbarakAfradDAO.getByccAfradForoshandeh(ccForoshandeh);
        if (anbarakAfradModels.size()>0)
        {
             ccAnbarak = anbarakAfradDAO.getByccAfradForoshandeh(ccForoshandeh).get(0).getCcAnbarak();
        }
        KalaZaribForoshDAO kalaZaribForoshDAO = new KalaZaribForoshDAO(mPresenter.getAppContext());
        kalaZaribForoshDAO.fetchKalaZaribForosh(mPresenter.getAppContext(), activityNameForLog,
                ccAnbarak,
                ccForoshandeh,
                0,
                String.valueOf(noeMoshtary),
                new RetrofitResponse() {
                    @Override
                    public void onSuccess(final ArrayList arrayListData) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                boolean deleteResult = true;
                                ArrayList<KalaZaribForoshModel> kalaZaribForoshModels = arrayListData;
                                if (kalaZaribForoshModels!=null)
                                {
                                     deleteResult  =  kalaZaribForoshDAO.deleteByccGoroh(String.valueOf(noeMoshtary));
                                }
                                boolean insertResult = kalaZaribForoshDAO.insertGroup(arrayListData);

                                if (deleteResult && insertResult) {
                                    sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                    Log.d("CustomerListModel", "noeMoshtary:"  + noeMoshtary + "," + itemCounter);
                                    if (noeMoshtary ==  Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_GOROH_MOSHTARY_ZANJIRE())))
                                        getAllMoshtaryGharardad(moshtaryForoshandehModel);
                                    else{
                                        Log.d("CustomerListModel", "itemCounter before:"  + itemCounter);
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                        Log.d("CustomerListModel", "itemCounter after:"  + itemCounter);
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
                        mPresenter.onFailedGetNewItem(++itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
                    }
                });


    }

    public final String GET_ALL_MOSHTARY_GHARARDAD_TAG = "__GET_ALL_GHARAR_DAD__";

    /**
     * overload getAllMoshtaryGharardad
     * get All contractions which a seller can sell to them weather a cold seller, a warm seller and a smart seller or a distributer
     * {@link #getKalaMosavabBySazmanGharardad(int, int)
     *
     * @param ccForoshandeh:each person in System has a cc
     */

    public void getAllMoshtaryGharardad( AllMoshtaryForoshandehModel moshtaryForoshandehModel) {
        MoshtaryGharardadDAO moshtaryGharardadDAO = new MoshtaryGharardadDAO(mPresenter.getAppContext());
        moshtaryGharardadDAO.fetchMoshtaryGharardadASync(mPresenter.getAppContext(), activityNameForLog, String.valueOf(moshtaryForoshandehModel.getCcForoshandeh()), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {

                Log.i("__GET_ALL_GHARAR_DAD__", "onSuccess: " + arrayListData.size());
                try {
                    Log.i("__GET_ALL_GHARAR_DAD__", "onSuccess: 1" + arrayListData);
                    Log.i("__GET_ALL_GHARAR_DAD__", "onSuccess: 2" + arrayListData.size());
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            boolean deleteResult = moshtaryGharardadDAO.deleteAll();
                            boolean insertResult = moshtaryGharardadDAO.insertGroup(arrayListData);
                            Log.i(GET_ALL_MOSHTARY_GHARARDAD_TAG, "3: " + insertResult + " " + deleteResult);
                            if (deleteResult && insertResult) {
                                Log.i("__GET_ALL_GHARAR_DAD__", "run: " + insertResult + " " + deleteResult);
                                Log.i("__GET_ALL_GHARAR_DAD__", "onSuccess: 4");
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

                                sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);
                                getAllKalaMosavab(moshtaryForoshandehModel);
                            } else {
                                sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                            }
                        }
                    };
                    thread.start();


                } catch (Exception exception) {
                    sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
                    String message = String.format("error body : %1$s , code : %2$s", exception.getMessage(), exception.getLocalizedMessage());
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(mPresenter.getAppContext(), Constants.LOG_EXCEPTION(), message, GetProgramModel.class.getSimpleName(), activityNameForLog, "fetchMoshtaryGharardad", "onResponse");
                }

            }

            @Override
            public void onFailed(String type, String error) {
                Log.i(GET_ALL_MOSHTARY_GHARARDAD_TAG, "onFailed: ");
                mPresenter.onFailedGetNewItem(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
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

    private void getAllKalaMosavab(AllMoshtaryForoshandehModel moshtaryForoshandehModel) {
        MoshtaryGharardadDAO moshtaryGharardadDAO = new MoshtaryGharardadDAO(mPresenter.getAppContext());
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(moshtaryForoshandehModel.getCcMoshtary());
        int ccMoshtaryparent = moshtaryModel.getccMoshtaryParent();
        ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadArrayLists = new ArrayList<>();
        ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels = moshtaryGharardadDAO.getByccMoshtary(ccMoshtaryparent);

        for (MoshtaryGharardadModel moshtaryGharardadModel : moshtaryGharardadModels) {
            moshtaryGharardadArrayLists.addAll(getKalaMosavabBySazmanGharardad(moshtaryGharardadModel.getCcSazmanForosh(), moshtaryGharardadModel.getCcMoshtaryGharardad()));
        }
        MoshtaryGharardadKalaDAO moshtaryGharardadKalaDAO = new MoshtaryGharardadKalaDAO(mPresenter.getAppContext());

        boolean deleteAll = moshtaryGharardadKalaDAO.deleteAll();
        boolean insertGroup = moshtaryGharardadKalaDAO.insertGroup(moshtaryGharardadArrayLists);
        if (deleteAll && insertGroup) {
            Log.i("getAllKalaMosavab", "run: " + deleteAll + " " + insertGroup);
            sendThreadMessage(Constants.BULK_INSERT_SUCCESSFUL(), ++itemCounter);

        } else {
            sendThreadMessage(Constants.BULK_INSERT_FAILED(), ++itemCounter);
            Log.i("getAllKalaMosavab", "run: " + deleteAll + " " + insertGroup);
        }

    }


    /**
     * gets all kala for each ccGharardad that is related to a sazmanForosh:
     *
     * @param ccMoshtaryGharardad :a MoshtaryParent Can have multiple contractions with multiple sazmanForosh and each contraction has a ccGharardad
     * @param ccSazmanForosh      :All sazman forsh for instance mesal forostland , pegah , bastaniPanda ,zangirei ....
     *                            ccsazmanForosh IN {1,2,3,4,5,6,11}
     **/
    @SuppressLint("LongLogTag")
    private ArrayList<MoshtaryGharardadKalaModel> getKalaMosavabBySazmanGharardad(
            int ccSazmanForosh, int ccMoshtaryGharardad) {
        ArrayList<MoshtaryGharardadKalaModel> moshtaryGharardadKalaModels = new ArrayList<>();
        MoshtaryGharardadKalaDAO moshtaryGharardadKalaDAO = new MoshtaryGharardadKalaDAO(mPresenter.getAppContext());
        moshtaryGharardadKalaDAO.fetchMoshtaryGharadadKala(mPresenter.getAppContext(), activityNameForLog, ccSazmanForosh, ccMoshtaryGharardad, new RetrofitResponse() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(ArrayList arrayListData) {
                Log.i("SazmanMoshtaryGharardadApiCalls", "getMoshtaryGharardadAllKalaMosavabe: " + ccSazmanForosh + " " + ccMoshtaryGharardad);
                if (arrayListData != null) {
                    Log.i("SazmanMoshtaryGharardadApiCalls", "onSuccess: " + arrayListData.size());
                    if (arrayListData.size() > 0) {
                        for (int i = 0; i < arrayListData.size(); i++) {
                            MoshtaryGharardadKalaModel moshtaryGharardadKalaModel = (MoshtaryGharardadKalaModel) arrayListData.get(i);
                            moshtaryGharardadKalaModel.setExtraprop_ccSazmanForosh(ccSazmanForosh);
                            moshtaryGharardadKalaModels.add(moshtaryGharardadKalaModel);

                            Log.i("SazmanMoshtaryGharardadApiCalls", "onSuccess: " + moshtaryGharardadKalaModel.toString());
                        }
                    }


                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailed(String type, String error) {
                Log.i("SazmanMoshtaryGharardadApiCalls", "onFailed: " + type + " " + error);
                mPresenter.onFailedGetNewItem(itemCounter, String.format(" type : %1$s \n error : %2$s", type, error));
            }
        });
        Log.i("SazmanMoshtaryGharardadApiCalls", "getMoshtaryGharardadAllKalaMosavabe" + moshtaryGharardadKalaModels.size());
        return moshtaryGharardadKalaModels;
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
    public void getAllMoshtarian(String tag, int ccForoshandeh) {
        AllMoshtaryForoshandehDAO allMoshtaryForoshandehDAO = new AllMoshtaryForoshandehDAO(BaseApplication.getContext());

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    getAllCustomers();
                    mPresenter.onUpdateData();
                } else if (msg.arg1 == -1) {
                    mPresenter.failedUpdate();
                }
                return false;
            }
        });

        allMoshtaryForoshandehDAO.fetchAllMoshtaryforoshandeh(BaseApplication.getContext(), "CustomersListActivity", String.valueOf(ccForoshandeh), new RetrofitResponse() {
            @Override
            public void onSuccess(ArrayList arrayListData) {

                Thread thread = new Thread() {
                    @Override
                    public void run() {

                        boolean deleteResult = allMoshtaryForoshandehDAO.deleteAll();
                        boolean insertResult = allMoshtaryForoshandehDAO.insertGroup(arrayListData);
                        Message message = new Message();
                        if (deleteResult && insertResult) {
                            mPresenter.onUpdateData();
                            message.arg1 = 1;
                        } else {
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


