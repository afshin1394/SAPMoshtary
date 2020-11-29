package com.saphamrah.Utils;

import com.google.android.gms.maps.model.LatLng;
import com.saphamrah.R;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class RoutingUtils
{

	public static final String drawableIdentifier = "drawable/ic_route_type_";																		  
    public static ArrayList<GeoPoint> decode(String encodedString, boolean hasAltitude)
    {
        double precision = 1.0/1E6;
        int index = 0;
        int len = encodedString.length();
        int lat = 0, lng = 0, alt = 0;
        ArrayList<GeoPoint> polyline = new ArrayList<GeoPoint>(len/3);
        //capacity estimate: polyline size is roughly 1/3 of string length for a 5digits encoding, 1/5 for 10digits.

        while (index < len) {
            int b, shift, result;
            shift = result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            if (hasAltitude){
                shift = result = 0;
                do {
                    b = encodedString.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dalt = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                alt += dalt;
            }

            GeoPoint p = new GeoPoint(lat*precision, lng*precision, alt/100);
            polyline.add(p);
        }

        //Log.d("BONUSPACK", "decode:string="+len+" points="+polyline.size());

        return polyline;
    }


    public static ArrayList<LatLng> decodeToLatLng(String encodedString, boolean hasAltitude)
    {
        double precision = 1.0/1E6;
        int index = 0;
        int len = encodedString.length();
        int lat = 0, lng = 0, alt = 0;
        ArrayList<LatLng> polyline = new ArrayList<>(len/3);
        //capacity estimate: polyline size is roughly 1/3 of string length for a 5digits encoding, 1/5 for 10digits.

        while (index < len) {
            int b, shift, result;
            shift = result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            if (hasAltitude){
                shift = result = 0;
                do {
                    b = encodedString.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dalt = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                alt += dalt;
            }

            LatLng p = new LatLng(lat*precision, lng*precision);
            polyline.add(p);
        }

        //Log.d("BONUSPACK", "decode:string="+len+" points="+polyline.size());

        return polyline;
    }



    /*public static int getImageResourceId(int type)
    {
        switch (type)
        {
            case 1:
                return R.drawable.next;
            case 2:
                return R.drawable.previous;
            default:
                return 0;
        }
    }*/


}
