package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saphamrah.BaseMVP.SellPolygonMVP;
import com.saphamrah.BuildConfig;
import com.saphamrah.MVP.Presenter.SellPolygonPresenter;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SellPolygonFragment extends Fragment implements SellPolygonMVP.RequiredViewOps
{

    private Context context;
    private MapView map;
    private double customerLat;
    private double customerLng;
    private Marker markerCurrentLocation;

    SellPolygonMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;



    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        customerLat = 0.0;
        customerLng = 0.0;
        try
        {
            if (this.getArguments() != null)
            {
                customerLat = this.getArguments().getDouble("customerlat");
                customerLng = this.getArguments().getDouble("customerlng");
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return LayoutInflater.from(context).inflate(R.layout.sell_polygon_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Calligrapher calligrapher = new Calligrapher(context);
        calligrapher.setFont(view , context.getResources().getString(R.string.fontPath));

        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        map = view.findViewById(R.id.map);
        ImageView imgShowCurrentLocation = view.findViewById(R.id.imgCurrentLocation);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , context);
        startMVPOps();

        mPresenter.getCurrentLocation();
        mPresenter.getSellPolygon();

        if (customerLat != 0.0 && customerLng != 0.0)
        {
            showCustomerLocation();
        }


        imgShowCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCurrentLocation();
            }
        });

    }

    @Override
    public void onPause()
    {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        map.onResume();
    }

    @Override
    public Context getAppContext()
    {
        return context;
    }

    @Override
    public void onGetCurrentLocation(GeoPoint currentPoint)
    {
        IMapController mapController = new MapController(map);
        mapController.setCenter(currentPoint);
        mapController.setZoom(19.0);

        if (markerCurrentLocation != null)
        {
            map.getOverlays().remove(markerCurrentLocation);
        }
        markerCurrentLocation = new Marker(map);
        markerCurrentLocation.setPosition(currentPoint);
        markerCurrentLocation.setIcon(getResources().getDrawable(R.drawable.ic_user_marker));
        markerCurrentLocation.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(markerCurrentLocation);
        map.invalidate();
    }

    @Override
    public void drawSellPolygon(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels , String polygonColor)
    {
        ArrayList<GeoPoint> geoPoints = new ArrayList<>();
        Polygon polygon = new Polygon();
        try
        {
            JSONObject jsonObject = new JSONObject(polygonColor);
            polygon.setFillColor(Color.parseColor(jsonObject.getString("fill")));
            polygon.setStrokeColor(Color.parseColor(jsonObject.getString("stroke")));
            polygon.setStrokeWidth((float) 3.0);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "SellPolygonFragment", "drawSellPolygon", "");
        }
        for (PolygonForoshSatrModel polygonForoshSatrModel : polygonForoshSatrModels)
        {
            geoPoints.add(new GeoPoint(polygonForoshSatrModel.getLat_y() , polygonForoshSatrModel.getLng_x()));
        }
        polygon.setPoints(geoPoints);
        polygon.setTitle("");
        map.getOverlayManager().add(polygon);
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showToast((Activity)context, getResources().getString(resId), messageType, duration);
    }


    private void showCustomerLocation()
    {
        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(customerLat , customerLng));
        marker.setIcon(getResources().getDrawable(R.drawable.ic_customer_location));
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);


        IMapController mapController = new MapController(map);
        mapController.setCenter(new GeoPoint(customerLat , customerLng));
        mapController.setZoom(19.0);

        Polygon circle = new Polygon(map);
        circle.setPoints(Polygon.pointsAsCircle(new GeoPoint(customerLat , customerLng) , 50.0));
        circle.setFillColor(0x12121212);
        circle.setStrokeColor(Color.RED);
        circle.setStrokeWidth(2);

        map.getOverlays().add(circle);
        map.getOverlays().add(marker);
        map.invalidate();
    }

    private void startMVPOps()
    {
        try
        {
            if ( stateMaintainer.firstTimeIn() )
            {
                initialize(this);
            }
            else
            {
                reinitialize(this);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", SellPolygonFragment.class.getSimpleName(), "startMVPOps", "");
        }
    }

    private void initialize(SellPolygonMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new SellPolygonPresenter(view);
            stateMaintainer.put(SellPolygonMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", SellPolygonFragment.class.getSimpleName(), "initialize", "");
        }
    }

    private void reinitialize(SellPolygonMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(SellPolygonMVP.PresenterOps.class.getSimpleName());
            if ( mPresenter == null )
            {
                initialize( view );
            }
            else
            {
                mPresenter.onConfigurationChanged(view);
            }
        }
        catch (Exception exception)
        {
            if (mPresenter != null)
            {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", SellPolygonFragment.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


}
