package com.saphamrah.MVP.Model;




import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.saphamrah.BaseMVP.RouteMVP;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Shared.RoutingServerShared;
import com.saphamrah.Valhalla.Location;
import com.saphamrah.Valhalla.SourcesToTargetsFailedResult;
import com.saphamrah.WebService.APIServiceValhalla;
import com.saphamrah.WebService.ApiClientValhalla;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteModel implements RouteMVP.ModelOps
{

    private RouteMVP.RequiredPresenterOps mPresenter;
	private static final String CLASS_NAME = "RouteModel";													  


    public RouteModel(RouteMVP.RequiredPresenterOps mPresenter)
    {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getCustomerName(int ccMoshtary)
    {
        MoshtaryDAO moshtaryDAO = new MoshtaryDAO(mPresenter.getAppContext());
        MoshtaryModel moshtaryModel = moshtaryDAO.getByccMoshtary(ccMoshtary);
        mPresenter.onGetCustomerName(moshtaryModel);
    }

	@Override
    public void route(Location startLocation, Location destinationLocation, String routeId)
    {
        Log.d("Route" , "start location model : " + startLocation.getLat() + " , " + startLocation.getLon());
        Log.d("Route" , "des location model : " + destinationLocation.getLat() + " , " + destinationLocation.getLon());

        JsonArray jsonArrayLocations = new JsonArray();
        jsonArrayLocations.add(locationToJson(startLocation));
        jsonArrayLocations.add(locationToJson(destinationLocation));

        JsonObject jsonObjectOptions = new JsonObject();
        jsonObjectOptions.addProperty("units" , "kilometer");

        JsonObject jsonObjectAllData = new JsonObject();
        jsonObjectAllData.add("locations" , jsonArrayLocations);
        jsonObjectAllData.addProperty("costing" , "auto");
        jsonObjectAllData.add("directions_options" , jsonObjectOptions);
        jsonObjectAllData.addProperty("id" , routeId);


        String routingServerIP = new RoutingServerShared(mPresenter.getAppContext()).getString(RoutingServerShared.IP , "");
        if (routingServerIP.length() > 0)
        {
            APIServiceValhalla apiServiceValhalla = ApiClientValhalla.getClient(routingServerIP).create(APIServiceValhalla.class);
            Call<Object> call = apiServiceValhalla.getOptimizedRoute(jsonObjectAllData.toString());
            call.enqueue(new Callback<Object>()
            {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response)
                {
                    try
                    {
                        Gson gson = new Gson();
                        if (response.isSuccessful())
                        {
                            String jsonObjectResponse = gson.toJson(response.body());
                            mPresenter.onGetRoute(jsonObjectResponse);
                        }
                        else
                        {
                            SourcesToTargetsFailedResult responseError = gson.fromJson(gson.toJson(response), SourcesToTargetsFailedResult.class);

                            String endpoint = getEndpoint(call);
                            String message = String.format("message : %1$s \n code : %2$s * %3$s", responseError.getError(), responseError.getErrorCode(), endpoint);
                            PubFunc.Logger logger = new PubFunc().new Logger();
                            logger.insertLogToDB(mPresenter.getAppContext(), LogPPCModel.LOG_EXCEPTION, message, CLASS_NAME, "", "route", "onResponse");
                            mPresenter.onError(R.string.errorGetData);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        PubFunc.Logger logger = new PubFunc().new Logger();
                        logger.insertLogToDB(mPresenter.getAppContext(), LogPPCModel.LOG_EXCEPTION, exception.toString(), CLASS_NAME, "", "route", "onResponse");
                        mPresenter.onError(R.string.errorGetData);
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t)
                {
                    String endpoint = getEndpoint(call);
                    PubFunc.Logger logger = new PubFunc().new Logger();
                    logger.insertLogToDB(mPresenter.getAppContext(), LogPPCModel.LOG_EXCEPTION, String.format("%1$s * %2$s", t.getMessage(), endpoint), CLASS_NAME, "", "route", "onFailure");
                    mPresenter.onError(R.string.errorGetData);
                }
            });
        }
        else
        {
            mPresenter.onError(R.string.errorFindServerIP);
        }

    }


    private JsonObject locationToJson(Location location)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lat" , location.getLat());
        jsonObject.addProperty("lon" , location.getLon());
        return jsonObject;
    }


    private String getEndpoint(Call call)
    {
        String endpoint = "";
        try
        {
            endpoint = call.request().url().toString();
            endpoint = endpoint.substring(endpoint.lastIndexOf("/")+1);
        }
        catch (Exception e)
        {e.printStackTrace();}
        return endpoint;
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
