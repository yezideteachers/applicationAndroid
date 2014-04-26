package fr.unice.rallyequiz.ltiplitre;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.util.FloatMath;

import com.google.android.gms.maps.model.LatLng;


public class Perimetre extends FragmentActivity {
    double d2r = 180/ Math.PI;

    public void detecterPosition() {

        final CharSequence[] items = {"repone1", "reponse2", "reponse3"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Question");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String choice = (String) items[which];
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }






    public double distanceEntreDeuxPoints(LatLng l1, LatLng l2) {
        int R =  6371 ;
        double lat1 = l1.latitude/d2r;
        double lat2 = l2.latitude/d2r;
        double lon1 = l1.longitude/d2r;
        double lon2 = l2.longitude/d2r;

        double dLat = Math.toRadians(lat2-lat1);

        double dLon = Math.toRadians(lon2-lon1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +

                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *

                        Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R*c;
    }

   /* @Override
    protected void onResume() {
        super.onResume();

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lm.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        accuracy = location.getAccuracy();

        String msg = String.format(
                getResources().getString(R.string.new_location), latitude,
                longitude, altitude, accuracy);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }*/



}
