package com.saphamrah.MVP.Model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.saphamrah.BaseMVP.AddCustomerListMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.GorohDAO;
import com.saphamrah.DAO.MasirVaznHajmMashinDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryAfradDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.MoshtaryEtebarSazmanForoshDAO;
import com.saphamrah.DAO.MoshtaryPhotoPPCDAO;
import com.saphamrah.DAO.MoshtaryRotbehDAO;
import com.saphamrah.DAO.MoshtaryShomarehHesabDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.PolygonForoshSatrDAO;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryAfradModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.MoshtaryPhotoPPCModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.AddCustomerShared;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServicePost;
import com.saphamrah.WebService.ApiClient;
import com.saphamrah.WebService.ServiceResponse.CreateAddressResult;
import com.saphamrah.WebService.ServiceResponse.CreateAfradResult;
import com.saphamrah.WebService.ServiceResponse.CreateMoshtaryWithJSONResult;
import com.saphamrah.WebService.ServiceResponse.CreateMoshtaryWithMadarekResult;
import com.saphamrah.WebService.ServiceResponse.CreateShomarehHesabResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerListModel implements AddCustomerListMVP.ModelOps
{

    private AddCustomerListMVP.RequiredPresenterOps mPresenter;

    public AddCustomerListModel(AddCustomerListMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getConfig()
    {
        ParameterChildDAO parameterChildDAO = new ParameterChildDAO(mPresenter.getAppContext());
        boolean canInsert = parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_CAN_INSERT_MOSHTARY_JADID()).trim().equals("1");
        mPresenter.onGetConfig(canInsert);
    }

    @Override
    public void getNewCustomers()
    {
        ArrayList<AddCustomerInfoModel> addCustomerInfoModels = new ArrayList<>();
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        GorohDAO gorohDAO = new GorohDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryModel> moshtaryModels = moshtaryDAO.getNewCustomers();
        for (MoshtaryModel moshtary : moshtaryModels)
        {
            AddCustomerInfoModel addCustomerInfoModel = new AddCustomerInfoModel();
            addCustomerInfoModel.setCcMoshtary(moshtary.getCcMoshtary());
            addCustomerInfoModel.setFirstName(moshtary.getFNameMoshtary());
            addCustomerInfoModel.setLastName(moshtary.getLNameMoshtary());
            addCustomerInfoModel.setIsOld(moshtary.getExtraProp_IsOld());

            // get address
            ArrayList<MoshtaryAddressModel> moshtaryAddressModels = moshtaryAddressDAO.getByccMoshtary(moshtary.getCcMoshtary());
            for (MoshtaryAddressModel address : moshtaryAddressModels)
            {
                if (address.getCcNoeAddress() == Constants.ADDRESS_TYPE_DARKHAST_TAHVIL())
                {
                    moshtaryAddressModels = new ArrayList<>();
                    moshtaryAddressModels.add(address);
                    addCustomerInfoModel.setMoshtaryAddressModels(moshtaryAddressModels);
                    break;
                }
                else if (address.getCcNoeAddress() == Constants.ADDRESS_TYPE_DARKHAST())
                {
                    moshtaryAddressModels = new ArrayList<>();
                    moshtaryAddressModels.add(address);
                    addCustomerInfoModel.setMoshtaryAddressModels(moshtaryAddressModels);
                    break;
                }
            }

			Log.d("addCustomer" , "noeMoshtary : " + moshtary.getCcNoeMoshtary() + " , noeSenf : " + moshtary.getCcNoeSenf());
            addCustomerInfoModel.setNoeFaaliatId(String.valueOf(moshtary.getCcNoeMoshtary()));
            addCustomerInfoModel.setNoeFaaliatTitle(gorohDAO.getNameGoroh(moshtary.getCcNoeMoshtary()));																						

			addCustomerInfoModel.setNoeSenfId(String.valueOf(moshtary.getCcNoeSenf()));
            addCustomerInfoModel.setNoeSenfTitle(gorohDAO.getNameGoroh(moshtary.getCcNoeSenf()));


            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
            String darajeName = childParameterDAO.getTextByParameterNameAndValue(Constants.ROTBE() , String.valueOf(moshtary.getDarajeh()));
            addCustomerInfoModel.setRotbeId(String.valueOf(moshtary.getDarajeh()));
            addCustomerInfoModel.setRotbeTitle(darajeName);

            /*String[] darajehItems = mPresenter.getAppContext().getResources().getStringArray(R.array.noeDarajehItems);
            for (int i=0 ; i<darajehItems.length ; i++)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(darajehItems[i]);
                    if (jsonObject.getInt("value") == moshtary.getDarajeh())
                    {
                        addCustomerInfoModel.setRotbeId(String.valueOf(moshtary.getDarajeh()));
                        addCustomerInfoModel.setRotbeTitle(jsonObject.getString("name"));
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), AddCustomerListModel.class.getSimpleName(), "" , "getNewCustomers" , "");
                }
            }*/

            addCustomerInfoModels.add(addCustomerInfoModel);
        }
        mPresenter.onGetNewCustomers(addCustomerInfoModels);
    }

    @Override
    public void deleteCustomer(int ccMoshtary , int position)
    {
        boolean deleteMoshtaryAfrad = deleteMoshtaryAfrad(ccMoshtary);
        boolean deleteVaznHajmMashin = deleteMasirVaznHajmMashin(ccMoshtary);
        boolean deleteShomareHesab = deleteShomareHesab(ccMoshtary);
        boolean deleteAddress = deleteAddress(ccMoshtary);
        boolean deleteMoshtaryRotbeh = deleteMoshtaryRotbeh(ccMoshtary);
        boolean deleteMoshtaryEtebarSazmanForosh = deleteMoshtaryEtebarSazmanForosh(ccMoshtary);
        boolean deleteMoshtary = deleteMoshtary(ccMoshtary);

        if (deleteMoshtaryAfrad && deleteVaznHajmMashin && deleteShomareHesab && deleteAddress && deleteMoshtaryRotbeh && deleteMoshtaryEtebarSazmanForosh && deleteMoshtary)
        {
            mPresenter.onDeletedCustomer(ccMoshtary , position);
        }
        else
        {
            mPresenter.onFailedDeleteCustomer();
        }
    }

    private boolean deleteMoshtaryAfrad(int ccMoshtary)
    {
        MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        return moshtaryAfradDAO.deleteByccMoshtary(ccMoshtary);
    }

    private boolean deleteMasirVaznHajmMashin(int ccMoshtary)
    {
        MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
        return masirVaznHajmMashinDAO.deleteByccMoshtary(ccMoshtary);
    }

    private boolean deleteShomareHesab(int ccMoshtary)
    {
        MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        return moshtaryShomarehHesabDAO.deleteByccMoshtary(ccMoshtary);
    }

    private boolean deleteAddress(int ccMoshtary)
    {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        return moshtaryAddressDAO.deleteByccMoshtary(ccMoshtary);
    }

    private boolean deleteMoshtaryRotbeh(int ccMoshtary)
    {
        MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(mPresenter.getAppContext());
        return moshtaryRotbehDAO.deleteByccMoshtaryRotbeh(ccMoshtary);
    }

    private boolean deleteMoshtaryEtebarSazmanForosh(int ccMoshtary)
    {
        MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        return moshtaryEtebarSazmanForoshDAO.deleteByccMoshtary(ccMoshtary);
    }

    private boolean deleteMoshtary(int ccMoshtary)
    {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        return moshtaryDAO.deleteByccMoshtary(ccMoshtary);
    }

    @Override
    public void checkAndRemoveAddCustomerInfoShared()
    {
        AddCustomerShared shared = new AddCustomerShared(mPresenter.getAppContext());
        shared.removeAll();

        UserTypeShared userTypeShared = new UserTypeShared(mPresenter.getAppContext());
        int isTest = userTypeShared.getInt(userTypeShared.USER_TYPE() , 0); //0-Main , 1-Test
        //isTest = 0;
        if (isTest == 0)
        {
            ParameterChildDAO childParameterDAO = new ParameterChildDAO(mPresenter.getAppContext());
            String checkLocationInPolygonForNewCustomer = childParameterDAO.getValueByccChildParameter(Constants.CC_CHILD_CHECK_LOCATION_IN_POLYGON_FOR_NEW_CUSTOMER());
            //checkLocationInPolygonForNewCustomer = "1";
            Log.d("addCustomer" , "checkLocationInPolygonForNewCustomer : " + checkLocationInPolygonForNewCustomer);
            if (checkLocationInPolygonForNewCustomer.trim().equals("0"))
            {
                mPresenter.onRemoveAddCustomerInfoShared();
            }
            else
            {
                int checkMoshtaryPointResult = checkMoshtaryPoint();
                if (checkMoshtaryPointResult == 1)
                {
                    mPresenter.onRemoveAddCustomerInfoShared();
                }
                else if (checkMoshtaryPointResult == -2)
                {
                    mPresenter.onErrorAccessToLocation();
                }
                else
                {
                    mPresenter.onOutOfPolygonError();
                }
            }
        }
        else if (isTest == 1)
        {
            mPresenter.onRemoveAddCustomerInfoShared();
        }
    }


    private int checkMoshtaryPoint()
    {
        int isInPolygon = -1;
        PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider(mPresenter.getAppContext());
        /*if (!googleLocationProvider.getHasAccess())
        {
            return -2;
        }
        else
        {*/
            //GeoPoint currentLocation = new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude());

            PolygonForoshSatrDAO polygonForoshSatrDAO = new PolygonForoshSatrDAO(mPresenter.getAppContext());
            ArrayList<String> ccPolygonForoshs = polygonForoshSatrDAO.getAllccPolygonForosh();
            Log.d("addCustomer" , "ccPolygonForoshs size : " + ccPolygonForoshs.size());
            //ArrayList<GeoPoint> polygonPoints = new ArrayList<>();
            ArrayList<LatLng> polygonPoints = new ArrayList<>();

            if (ccPolygonForoshs.size() != 0)
            {
                for (String ccPolygon : ccPolygonForoshs)
                {
                    Log.d("addCustomer" , "ccPolygonForoshs : " + ccPolygon);
                    polygonPoints.clear();
                    ArrayList<PolygonForoshSatrModel> polygonForoshSatr = polygonForoshSatrDAO.getAllByCcPolygonForosh(ccPolygon);
                    Log.d("addCustomer" , "polygonForoshSatr size : " + polygonForoshSatr.size());
                    for (PolygonForoshSatrModel polygonSatrs : polygonForoshSatr)
                    {
                        Log.d("addCustomer" , "polygonForoshSatr : " + polygonSatrs.toString());
                        //polygonPoints.add(new GeoPoint(polygonSatrs.getLat_y(),polygonSatrs.getLng_x()));
                        polygonPoints.add(new LatLng(polygonSatrs.getLat_y() , polygonSatrs.getLng_x()));
                    }
                    if (PolyUtil.containsLocation(locationProvider.getLatitude() , locationProvider.getLongitude(), polygonPoints, false))
                    {
                        isInPolygon = 1;
                    }
                }
                return isInPolygon;
            }
            else
            {
                return -1;
            }
        //}
    }

    public boolean contains(GeoPoint currentLocation , ArrayList<GeoPoint> polygonGeoPoints)
    {
        int i;
        int j;
        boolean result = false;
        Log.d("addCustomer" , "currentLocation : " + currentLocation.getLatitude() + " , " + currentLocation.getLongitude());
        for (i = 0, j = polygonGeoPoints.size() - 1; i < polygonGeoPoints.size(); j = i++)
        {
            if ((polygonGeoPoints.get(i).getLongitude() > currentLocation.getLongitude()) != (polygonGeoPoints.get(j).getLongitude() > currentLocation.getLongitude()) &&
                    (currentLocation.getLatitude() < (polygonGeoPoints.get(j).getLatitude() - polygonGeoPoints.get(i).getLatitude()) * (currentLocation.getLongitude() - polygonGeoPoints.get(i).getLongitude())
                            / (polygonGeoPoints.get(j).getLatitude() - polygonGeoPoints.get(i).getLatitude()) + polygonGeoPoints.get(i).getLatitude()))
            {
                //Log.d("addCustomer" , "in if and result : " + result);
                result = !result;
            }
        }
        Log.d("addCustomer" , "result before return : " + result);
        return result;
    }


    private int sendedItemCounter = 0;
    @Override
    public void sendToServer(final int ccMoshtary)
    {
        //sendMoshtaryAddress(ccMoshtary);
        prepareAllMoshtaryDataAsJson(ccMoshtary);
    }



    private void prepareAllMoshtaryDataAsJson(int ccMoshtary)
    {
        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh();

        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

        MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryAfradModel> moshtaryAfradModels = moshtaryAfradDAO.getByccMoshtary(ccMoshtary);

        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = moshtaryAddressDAO.getByccMoshtary(ccMoshtary);

        MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = moshtaryShomarehHesabDAO.getAllByccMoshtary(ccMoshtary);

        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = moshtaryPhotoPPCDAO.getAllByccMoshtary(ccMoshtary);

        if (moshtaryModel.getCcMoshtary() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteMoshtaryData);
            return;
        }
        if (moshtaryAfradModels.size() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteMoshtaryAfrad);
            return;
        }
        if (moshtaryAddressModels.size() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorFindCustomerAddress);
            return;
        }
        /*if (moshtaryShomarehHesabModels.size() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteMoshtaryShomareHesab);
            return;
        }*/
        /*if (moshtaryPhotoPPCModels.size() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteMoshtaryMadarek);
            return;
        }*/
        if (foroshandehMamorPakhshModel.getCcMarkazForosh() <= 0 || foroshandehMamorPakhshModel.getCcForoshandeh() <= 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorForoshandehData);
            return;
        }



        // Moshtary and MoshtaryAfrad
        JSONObject moshtaryJson = moshtaryModel.toJsonObject(foroshandehMamorPakhshModel.getCcMarkazForosh(), foroshandehMamorPakhshModel.getCcMarkazSazmanForosh());
        JSONObject afradJson = moshtaryAfradModels.get(0).toJsonObject(moshtaryModel.getMobile());
        if (moshtaryJson.toString().length() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteMoshtaryData);
            return;
        }
        if (afradJson.toString().length() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteMoshtaryAfrad);
            return;
        }


        // Address and MoshtaryAddress
        JSONArray jsonArrayAddress = new JSONArray(); //Address Table
        JSONArray jsonArrayMoshtaryAddress = new JSONArray();//MoshtaryAddress Table
        for (MoshtaryAddressModel address : moshtaryAddressModels)
        {
            jsonArrayAddress.put(address.toJsonObjectAddress());
            jsonArrayMoshtaryAddress.put(address.toJsonObjectMoshtaryAddress());
        }
        if (jsonArrayAddress.toString().length() == 0 || jsonArrayMoshtaryAddress.toString().length() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorFindCustomerAddress);
            return;
        }

        //ShomarehHesab and MoshtaryShomarehHesab
        JSONArray jsonArrayShomareHesab = new JSONArray();
        JSONArray jsonArrayMoshtaryShomareHesab = new JSONArray();
        for (MoshtaryShomarehHesabModel shomareHesab : moshtaryShomarehHesabModels)
        {
            jsonArrayShomareHesab.put(shomareHesab.toJsonObjectShomarehHesab());
            jsonArrayMoshtaryShomareHesab.put(shomareHesab.toJsonObjectMoshtaryShomarehHesab());
        }
        /*if (jsonArrayShomareHesab.toString().length() == 0 || jsonArrayMoshtaryShomareHesab.toString().length() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteMoshtaryShomareHesab);
            return;
        }*/


        //MoshtaryPhotoPPC
        JSONArray jsonArrayMoshtaryPhotoPPC = new JSONArray();
        for (MoshtaryPhotoPPCModel photoPPCModel : moshtaryPhotoPPCModels)
        {
            jsonArrayMoshtaryPhotoPPC.put(photoPPCModel.toJsonObject());
        }
        /*if (jsonArrayMoshtaryPhotoPPC.toString().length() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteMoshtaryMadarek);
            return;
        }*/


        JSONObject jsonObjectAllData = new JSONObject();
        try
        {
            jsonObjectAllData.put("Moshtary" , moshtaryJson);
            jsonObjectAllData.put("Afrad" , afradJson);
            jsonObjectAllData.put("Address" , jsonArrayAddress);
            jsonObjectAllData.put("MoshtaryAddress" , jsonArrayMoshtaryAddress);
            jsonObjectAllData.put("MoshtaryNoeEtebar" , new JSONArray());
            jsonObjectAllData.put("MoshtaryEtebar" , new JSONArray());
            jsonObjectAllData.put("MoshtaryEtebarSazmanForosh" , new JSONArray());
            jsonObjectAllData.put("ShomarehHesab" , jsonArrayShomareHesab);
            jsonObjectAllData.put("MoshtaryShomarehHesab" , jsonArrayMoshtaryShomareHesab);
            jsonObjectAllData.put("MoshtaryPhotoPPC" , jsonArrayMoshtaryPhotoPPC);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "AddCustomerListModel", "", "sendAllMoshtaryDataAsJson", "");
            mPresenter.onErrorSendToServer(R.string.errorOccurred);
            return;
        }

        String jsonAllData = jsonObjectAllData.toString();
        if (jsonAllData.length() == 0)
        {
            mPresenter.onErrorSendToServer(R.string.errorIncompleteAllMoshtaryData);
        }
        else
        {
            sendAllMoshtaryDataAsJson(jsonAllData , moshtaryModel.getCcMoshtary());
        }
    }


    private void sendAllMoshtaryDataAsJson(String jsonAllData , final int ccMoshtaryOld)
    {
        saveToFile(jsonAllData, ccMoshtaryOld);

        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
        String serverPort = serverIPShared.getString(serverIPShared.PORT() , "");
        if (serverIP.trim().equals("") || serverPort.trim().equals(""))
        {
            mPresenter.onErrorSendToServer(R.string.errorFindServerIP);
        }
        else
        {
            final APIServicePost apiServicePost = ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
            Call<CreateMoshtaryWithJSONResult> call = apiServicePost.createMoshtaryWithJSON(jsonAllData);
            call.enqueue(new Callback<CreateMoshtaryWithJSONResult>()
            {
                @Override
                public void onResponse(Call<CreateMoshtaryWithJSONResult> call, Response<CreateMoshtaryWithJSONResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            CreateMoshtaryWithJSONResult result = response.body();
                            if (result.getSuccess())
                            {
                                updateMoshtaryAndAddress(ccMoshtaryOld , result.getMessage());
                            }
                            else
                            {
                                showResultError(result.getMessage());
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "AddCustomerListModel", "" , "sendMoshtary" , "onResponse");
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "AddCustomerListModel", "" , "sendMoshtary" , "onResponse");
                            mPresenter.onErrorSendToServer(R.string.errorOperation);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerListModel", "" , "sendMoshtary" , "onResponse");
                        mPresenter.onErrorSendToServer(R.string.errorOperation);
                    }
                }

                @Override
                public void onFailure(Call<CreateMoshtaryWithJSONResult> call, Throwable t)
                {
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "AddCustomerListModel", "" , "sendToServer" , "onFailure");
                    mPresenter.onErrorSendToServer(R.string.errorSendData);
                }
            });
        }
    }


    private void saveToFile(String jsonAllData , final int ccMoshtaryOld)
    {
        try
        {
            File path = mPresenter.getAppContext().getExternalFilesDir(null);
            File file = new File(path, "addCustomer" + ccMoshtaryOld + ".txt");
            FileOutputStream stream = new FileOutputStream(file);
            try
            {
                stream.write(jsonAllData.getBytes());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private void updateMoshtaryAndAddress(int ccMoshtaryOld , String response)
    {
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            int ccMoshtaryNew = jsonObject.getInt("ccMoshtary");
            int ccAfrad = jsonObject.getInt("ccAfrad");
            JSONArray jsonArrayAddress = jsonObject.getJSONArray("Address");

            MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
            for (int i=0 ; i<jsonArrayAddress.length() ; i++)
            {
                moshtaryAddressDAO.updateMoshtaryJadid(ccMoshtaryOld, ccMoshtaryNew, jsonArrayAddress.getJSONObject(i).getInt("ccAddress"), jsonArrayAddress.getJSONObject(i).getInt("ccNoeAddress"));
            }
            updateMoshtaryJadid(ccAfrad , ccMoshtaryNew , ccMoshtaryOld);
            mPresenter.onSuccessSendToServer(ccMoshtaryNew , ccMoshtaryOld);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerListModel", "", "updateMoshtaryAndAddress", "");
            mPresenter.onErrorSendToServer(R.string.errorSendData);
        }
    }

    private void showResultError(String errorId)
    {
        if (errorId.equals("-1"))
        {
            mPresenter.onErrorSendToServer(R.string.errorMasirForMoshtaryJadid);
        }
        else if (errorId.equals("-2"))
        {
            mPresenter.onErrorSendToServer(R.string.errorAddressForMoshtaryJadid);
        }
    }



    private void sendMoshtaryAddress(final int ccMoshtary)
    {
        ServerIPShared serverIPShared = new ServerIPShared(mPresenter.getAppContext());
        String serverIP = serverIPShared.getString(serverIPShared.IP() , "");
        String serverPort = serverIPShared.getString(serverIPShared.PORT() , "");
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        final MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
        if (serverIP.trim().equals("") || serverPort.trim().equals(""))
        {
            mPresenter.onErrorSendToServer(R.string.errorFindServerIP);
        }
        else
        {
            final MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
            final ArrayList<MoshtaryAddressModel> moshtaryAddressModels = moshtaryAddressDAO.getByccMoshtary(ccMoshtary);
            if (moshtaryAddressModels.size() > 0)
            {
                sendedItemCounter = 0;
                final APIServicePost apiServicePost = ApiClient.getClient(serverIP , serverPort).create(APIServicePost.class);
                for (final MoshtaryAddressModel model : moshtaryAddressModels)
                {
                    String jsonString = model.toJsonString();
                    Log.d("address" , "moshtary address : "  + jsonString);
                    sendMoshtary(apiServicePost , ccMoshtary);
                    Call<CreateAddressResult> call = apiServicePost.createAddressResult(jsonString);
                    call.enqueue(new Callback<CreateAddressResult>()
                    {
                        @Override
                        public void onResponse(Call<CreateAddressResult> call, Response<CreateAddressResult> response)
                        {
                            try
                            {
                                if (response.isSuccessful() && response.body() != null)
                                {
                                    Log.d("noTemp" , "in if success and body not null");
                                    CreateAddressResult result = response.body();
                                    if (result.getSuccess())
                                    {
                                        int newCcAddress = Integer.parseInt(result.getMessage());
                                        if (newCcAddress > 0)
                                        {
                                            if (moshtaryAddressDAO.updateccAddressAndSendToSQL(model.getCcMoshtaryAddress(), newCcAddress))
                                            {
                                                model.setCcAddress(newCcAddress);
                                                model.setExtraProp_IsSendToSql(1);
                                            }
                                            sendedItemCounter++;
                                            Log.d("addCustomer" , "sendedItemCounter : " + sendedItemCounter + " , moshtaryAddressModels.size() : " + moshtaryAddressModels.size());
                                            if (sendedItemCounter == moshtaryAddressModels.size())
                                            {
                                                sendMoshtaryAfrad(apiServicePost , ccMoshtary , moshtaryModel.getMobile());
                                            }
                                        }
                                        else
                                        {
                                            mPresenter.onErrorSendToServer(R.string.errorOperation);
                                        }
                                    }
                                    else
                                    {
                                        Log.d("noTemp" , "in else not success");
                                        setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "AddCustomerListModel", "" , "sendToServer" , "onResponse");
                                        mPresenter.onErrorSendToServer(R.string.errorOperation);
                                    }
                                }
                                else
                                {
                                    String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                                    if (response.errorBody() != null)
                                    {
                                        errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                                    }
                                    setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "AddCustomerListModel", "" , "sendToServer" , "onResponse");
                                    Log.d("tempRequest" , "message : " + errorMessage);
                                    mPresenter.onErrorSendToServer(R.string.errorOperation);
                                }
                            }
                            catch (Exception exception)
                            {
                                Log.d("noTemp" , "in exception");
                                exception.printStackTrace();
                                setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerListModel", "" , "sendToServer" , "onResponse");
                                mPresenter.onErrorSendToServer(R.string.errorOperation);
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateAddressResult> call, Throwable t)
                        {
                            Log.d("noTemp" , "in onFailure");
                            setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "AddCustomerListModel", "" , "sendToServer" , "onFailure");
                            mPresenter.onErrorSendToServer(R.string.errorOperation);
                        }
                    });
                }
            }
            else
            {
                mPresenter.onErrorSendToServer(R.string.notFoundAddress);
            }
        }
    }

    private void sendMoshtaryAfrad(final APIServicePost apiServicePost , final int ccMoshtary , String customerMobile)
    {
        final MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        final ArrayList<MoshtaryAfradModel> moshtaryAfradModels = moshtaryAfradDAO.getByccMoshtary(ccMoshtary);
        sendedItemCounter = 0;
        for (final MoshtaryAfradModel model : moshtaryAfradModels)
        {
            String jsonString = model.toJsonString(customerMobile);
            Log.d("addCustomer" , "jsonString afrad : " + jsonString);
            Call<CreateAfradResult> call = apiServicePost.createAfradResult(jsonString);
            call.enqueue(new Callback<CreateAfradResult>()
            {
                @Override
                public void onResponse(Call<CreateAfradResult> call, Response<CreateAfradResult> response)
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            Log.d("noTemp" , "in if success and body not null");
                            CreateAfradResult result = response.body();
                            if (result.getSuccess())
                            {
                                int newccAfrad = Integer.parseInt(result.getMessage());
                                if (newccAfrad > 0)
                                {
                                    if (moshtaryAfradDAO.updateccAfrad(newccAfrad , model.getCcAfrad()))
                                    {
                                        model.setCcAfrad(newccAfrad);
                                        sendedItemCounter++;
                                        Log.d("addCustomer" , "sendedItemCounter : " + sendedItemCounter + " , moshtaryAfradModels.size() : " + moshtaryAfradModels.size());
                                        if (sendedItemCounter == moshtaryAfradModels.size())
                                        {
                                            sendMoshtaryShomarehHesab(apiServicePost, ccMoshtary);
                                        }
                                    }
                                }
                                else
                                {
                                    mPresenter.onErrorSendToServer(R.string.errorOperation);
                                    setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "AddCustomerListModel", "" , "sendMoshtaryAfrad" , "onResponse");
                                }
                            }
                            else
                            {
                                Log.d("noTemp" , "in else not success");
                                mPresenter.onErrorSendToServer(R.string.errorOperation);
                                setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "AddCustomerListModel", "" , "sendMoshtaryAfrad" , "onResponse");
                            }
                        }
                        else
                        {
                            String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                            if (response.errorBody() != null)
                            {
                                errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                            }
                            setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "AddCustomerListModel", "" , "sendMoshtaryAfrad" , "onResponse");
                            Log.d("tempRequest" , "message : " + errorMessage);
                            mPresenter.onErrorSendToServer(R.string.errorOperation);
                        }
                    }
                    catch (Exception exception)
                    {
                        Log.d("noTemp" , "in exception");
                        exception.printStackTrace();
                        setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerListModel", "" , "sendMoshtaryAfrad" , "onResponse");
                        mPresenter.onErrorSendToServer(R.string.errorOperation);
                    }
                }

                @Override
                public void onFailure(Call<CreateAfradResult> call, Throwable t)
                {
                    Log.d("noTemp" , "in onFailure");
                    setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "AddCustomerListModel", "" , "sendMoshtaryAfrad" , "onFailure");
                    mPresenter.onErrorSendToServer(R.string.errorOperation);
                }
            });
        }

    }


    private void sendMoshtaryShomarehHesab(final APIServicePost apiServicePost , final int ccMoshtary)
    {
        final MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        final ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = moshtaryShomarehHesabDAO.getAllByccMoshtary(ccMoshtary);
        sendedItemCounter = 0;
        if (moshtaryShomarehHesabModels.size() > 0)
        {
            for (final MoshtaryShomarehHesabModel model : moshtaryShomarehHesabModels)
            {
                String jsonString = model.toJsonString();
                Call<CreateShomarehHesabResult> call = apiServicePost.createShomarehHesabResult(jsonString);
                call.enqueue(new Callback<CreateShomarehHesabResult>()
                {
                    @Override
                    public void onResponse(Call<CreateShomarehHesabResult> call, Response<CreateShomarehHesabResult> response)
                    {
                        try
                        {
                            if (response.isSuccessful() && response.body() != null)
                            {
                                Log.d("noTemp" , "in if success and body not null");
                                CreateShomarehHesabResult result = response.body();
                                if (result.getSuccess())
                                {
                                    moshtaryShomarehHesabDAO.updateccShomareHesab(model.getCcMoshtaryShomarehHesab() , Integer.parseInt(result.getMessage()));
                                    sendedItemCounter++;
                                    Log.d("addCustomer" , "sendedItemCounter : " + sendedItemCounter + " , moshtaryShomarehHesabModels.size() : " + moshtaryShomarehHesabModels.size());
                                    if (sendedItemCounter == moshtaryShomarehHesabModels.size())
                                    {
                                        sendMoshtary(apiServicePost, ccMoshtary);
                                    }
                                }
                                else
                                {
                                    Log.d("noTemp" , "in else not success");
                                    mPresenter.onErrorSendToServer(R.string.errorOperation);
                                    setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "AddCustomerListModel", "" , "sendMoshtaryShomarehHesab" , "onResponse");
                                }
                            }
                            else
                            {
                                String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                                if (response.errorBody() != null)
                                {
                                    errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                                }
                                setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "AddCustomerListModel", "" , "sendMoshtaryShomarehHesab" , "onResponse");
                                Log.d("tempRequest" , "message : " + errorMessage);
                                mPresenter.onErrorSendToServer(R.string.errorOperation);
                            }
                        }
                        catch (Exception exception)
                        {
                            Log.d("noTemp" , "in exception");
                            exception.printStackTrace();
                            setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerListModel", "" , "sendMoshtaryShomarehHesab" , "onResponse");
                            mPresenter.onErrorSendToServer(R.string.errorOperation);
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateShomarehHesabResult> call, Throwable t)
                    {
                        Log.d("noTemp" , "in onFailure");
                        setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "AddCustomerListModel", "" , "sendMoshtaryShomarehHesab" , "onFailure");
                        mPresenter.onErrorSendToServer(R.string.errorOperation);
                    }
                });
            }
        }
        else
        {
            sendMoshtary(apiServicePost, ccMoshtary);
        }
    }


    private void sendMoshtary(APIServicePost apiServicePost , int ccMoshtary)
    {
        MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryAddressModel> moshtaryAddressModels = moshtaryAddressDAO.getByccMoshtary(ccMoshtary);

        MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryAfradModel> moshtaryAfradModels = moshtaryAfradDAO.getByccMoshtary(ccMoshtary);

        MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = moshtaryShomarehHesabDAO.getAllByccMoshtary(ccMoshtary);

        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels = moshtaryPhotoPPCDAO.getAllByccMoshtary(ccMoshtary);

        final MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        final MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);

        ForoshandehMamorPakhshDAO foroshandehMamorPakhshDAO = new ForoshandehMamorPakhshDAO(mPresenter.getAppContext());
        ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehMamorPakhshDAO.getForoshandehMamorPakhsh();

        String jsonString = moshtaryModel.toJsonString(moshtaryAddressModels, moshtaryAfradModels, moshtaryShomarehHesabModels, moshtaryPhotoPPCModels, String.valueOf(foroshandehMamorPakhshModel.getCcAfrad()), String.valueOf(foroshandehMamorPakhshModel.getCcMarkazForosh()), String.valueOf(foroshandehMamorPakhshModel.getCcForoshandeh()));
        Log.d("addCustomer" , "json moshtary : " + jsonString);
        Call<CreateMoshtaryWithMadarekResult> call = apiServicePost.createMoshtaryWithMadarekResult(jsonString);
        call.enqueue(new Callback<CreateMoshtaryWithMadarekResult>()
        {
            @Override
            public void onResponse(Call<CreateMoshtaryWithMadarekResult> call, Response<CreateMoshtaryWithMadarekResult> response)
            {
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        Log.d("noTemp" , "in if success and body not null");
                        CreateMoshtaryWithMadarekResult result = response.body();
                        if (result.getSuccess())
                        {
                            //updateMoshtaryJadid(Integer.parseInt(result.getMessage()) , moshtaryModel.getCcMoshtary());
                        }
                        else
                        {
                            Log.d("noTemp" , "in else not success");
                            mPresenter.onErrorSendToServer(R.string.errorOperation);
                            setLogToDB(Constants.LOG_EXCEPTION(), result.getMessage(), "AddCustomerListModel", "" , "sendMoshtary" , "onResponse");
                        }
                    }
                    else
                    {
                        String errorMessage = "response not successful " + response.message() ;//+ "\n" + "can't send this log : " + logMessage;
                        if (response.errorBody() != null)
                        {
                            errorMessage = "errorCode : " + response.code() + " , " + response.errorBody().string() ;//+ "\n" + "can't send this log : " + logMessage;
                        }
                        setLogToDB(Constants.LOG_EXCEPTION(), errorMessage, "AddCustomerListModel", "" , "sendMoshtary" , "onResponse");
                        Log.d("tempRequest" , "message : " + errorMessage);
                        mPresenter.onErrorSendToServer(R.string.errorOperation);
                    }
                }
                catch (Exception exception)
                {
                    Log.d("noTemp" , "in exception");
                    exception.printStackTrace();
                    setLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "AddCustomerListModel", "" , "sendMoshtary" , "onResponse");
                    mPresenter.onErrorSendToServer(R.string.errorOperation);
                }
            }

            @Override
            public void onFailure(Call<CreateMoshtaryWithMadarekResult> call, Throwable t)
            {
                Log.d("noTemp" , "in onFailure");
                setLogToDB(Constants.LOG_EXCEPTION(), t.getMessage(), "AddCustomerListModel", "" , "sendMoshtary" , "onFailure");
                mPresenter.onErrorSendToServer(R.string.errorOperation);
            }
        });
    }

    private void updateMoshtaryJadid(int newccAfrad, int newccMoshtary, int oldccMoshtary)
    {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        moshtaryDAO.updateMoshtaryJadid(newccMoshtary , oldccMoshtary);

        MoshtaryEtebarSazmanForoshDAO moshtaryEtebarSazmanForoshDAO = new MoshtaryEtebarSazmanForoshDAO(mPresenter.getAppContext());
        moshtaryEtebarSazmanForoshDAO.updateMoshtaryJadid(newccMoshtary , oldccMoshtary);

        /*MoshtaryAddressDAO moshtaryAddressDAO = new MoshtaryAddressDAO(mPresenter.getAppContext());
        moshtaryAddressDAO.updateMoshtaryJadid(newccMoshtary , oldccMoshtary);*/

        MoshtaryShomarehHesabDAO moshtaryShomarehHesabDAO = new MoshtaryShomarehHesabDAO(mPresenter.getAppContext());
        moshtaryShomarehHesabDAO.updateMoshtaryJadid(newccMoshtary , oldccMoshtary);

        MoshtaryAfradDAO moshtaryAfradDAO = new MoshtaryAfradDAO(mPresenter.getAppContext());
        moshtaryAfradDAO.updateMoshtaryJadid(newccMoshtary , oldccMoshtary , newccAfrad);

        MasirVaznHajmMashinDAO masirVaznHajmMashinDAO = new MasirVaznHajmMashinDAO(mPresenter.getAppContext());
        masirVaznHajmMashinDAO.updateMoshtaryJadid(newccMoshtary , oldccMoshtary);

        MoshtaryRotbehDAO moshtaryRotbehDAO = new MoshtaryRotbehDAO(mPresenter.getAppContext());
        moshtaryRotbehDAO.updateMoshtaryJadid(newccMoshtary , oldccMoshtary);

        MoshtaryPhotoPPCDAO moshtaryPhotoPPCDAO = new MoshtaryPhotoPPCDAO(mPresenter.getAppContext());
        moshtaryPhotoPPCDAO.updateMoshtaryJadid(newccMoshtary, oldccMoshtary);

        mPresenter.onSuccessSendToServer(newccMoshtary, oldccMoshtary);
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
