package com.saphamrah.Test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.GPSDataModel;
import com.saphamrah.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder>
{

    private List<GPSDataModel> gpsDataModelList;

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView lblId;
        private TextView lblLat;
        private TextView lblLng;
        private TextView lblSpeed;
        private TextView lblTime;
        private TextView lblAltitude;
        private TextView lblAccuracy;
        private TextView lblBearing;
        private TextView lblDistance;
        private TextView lblElapsedRealTimeNanos;
        private TextView lblProvider;
        private TextView lblStatus;
        private TextView lblBearingAccuracyDegrees;
        private TextView lblSpeedAccuracyMetersPerSecond;
        private TextView lblVerticalAccuracyMeters;

        private MyViewHolder(View view)
        {
            super(view);
            lblId = view.findViewById(R.id.lblId);
            lblLat = view.findViewById(R.id.lblLat);
            lblLng = view.findViewById(R.id.lblLng);
            lblSpeed = view.findViewById(R.id.lblSpeed);
            lblTime = view.findViewById(R.id.lblTime);
            lblAltitude = view.findViewById(R.id.lblAltitude);
            lblAccuracy = view.findViewById(R.id.lblAccuracy);
            lblBearing = view.findViewById(R.id.lblBearing);
            lblDistance = view.findViewById(R.id.lblDistance);
            lblElapsedRealTimeNanos = view.findViewById(R.id.lblElapsedRealTimeNanos);
            lblProvider = view.findViewById(R.id.lblProvider);
            lblStatus = view.findViewById(R.id.lblStatus);
            lblBearingAccuracyDegrees = view.findViewById(R.id.lblBearingAccuracyDegrees);
            lblSpeedAccuracyMetersPerSecond = view.findViewById(R.id.lblgetSpeedAccuracyMetersPerSecond);
            lblVerticalAccuracyMeters = view.findViewById(R.id.lblVerticalAccuracyMeters);
        }
    }


    public LocationAdapter(List<GPSDataModel> gpsDataModels) {
        this.gpsDataModelList = gpsDataModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        GPSDataModel gpsDataModel = gpsDataModelList.get(position);

        holder.lblId.setText("id : " + gpsDataModel.getCcGpsData_PPC());
        holder.lblLat.setText("lat : " + gpsDataModel.getLatitude());
        holder.lblLng.setText("lng : " + gpsDataModel.getLongitude());
        holder.lblSpeed.setText("speed : " + gpsDataModel.getSpeed());
        holder.lblTime.setText("time : " + gpsDataModel.getTarikh());
        holder.lblAltitude.setText("altitude : " + gpsDataModel.getAltitude());
        holder.lblAccuracy.setText("accuracy : " + gpsDataModel.getAccurancy());
        holder.lblBearing.setText("bearing : " + gpsDataModel.getBearing());
        holder.lblDistance.setText("distance : " + gpsDataModel.getDistance());
        holder.lblElapsedRealTimeNanos.setText("elapsed : " + gpsDataModel.getElapsedRealTimeNanos());
        holder.lblProvider.setText("provider : " + gpsDataModel.getProvider());
        holder.lblProvider.setText("status : " + gpsDataModel.getStatus());
    }

    @Override
    public int getItemCount() {
        return gpsDataModelList.size();
    }

}
