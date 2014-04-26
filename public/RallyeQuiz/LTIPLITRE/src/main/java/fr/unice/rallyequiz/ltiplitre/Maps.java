package fr.unice.rallyequiz.ltiplitre;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;


public class Maps extends Activity implements LocationListener,Runnable{

    Epreuve e = new Epreuve(43.72, 07.24);
    LatLng l1 = new LatLng(e.getPositionx(),e.getPositiony());
    private LocationManager locationManager;


    double latitude;
    double longitude;
    double altitude;
    Perimetre p = new Perimetre();
    Criteria criteria = new Criteria();
    String provider ;
    GoogleMap map;
    String msg;
    Location location;
    double distance;
    private ProgressDialog mprogressDialog;

    ArrayList array = new ArrayList<CharSequence[]>();


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

     locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();

        }
        else{
            Toast.makeText(this, "couldn't get provider", LENGTH_LONG).show();
        }

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        e.positioneperueve(map);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void questionnerJoueur() {
        CharSequence [] qcm = {"Le GC existe il dans le langage C","faux","vrai","faux"};
        final CharSequence[] items = new CharSequence[2];
        items[0]=qcm[2];
        items[1]=qcm[3];
        array.add(items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(qcm[0]);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String choice = (String) items[which];

                try {
                    verifier(choice);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }



            }

        });


        mprogressDialog = new ProgressDialog(this);
        // Message de la barre de progression
        //  mprogressDialog.setMessage("Chargement en cours...");
        // Titre de la barre de progression
        //   mprogressDialog.setTitle("www.blog.erlem.fr");
        //Style de la barre de progression(STYLE_HORIZONTAL ou STYLE_SPINNER)
        mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        AlertDialog alert = builder.create();
        alert.show();
        // Affichage de la barre de progression
        mprogressDialog.show();
    }

    public void verifier(String choice) throws InterruptedException {
        CharSequence [] ch = (CharSequence[]) array.get(0);
        String s="";
        if(choice.equals(ch[1])) {
            s="bonne reponse : "+ch[1];
            Toast.makeText(this,s , LENGTH_LONG).show();
        }
        else {

            Intent intent = new Intent(Maps.this, Question.class);
            startActivity(intent);
           /* s="mauvaise reponse : "+ch[1];
            Toast.makeText(this,s , LENGTH_LONG).show();
            Thread.sleep(10000);*/

        }

    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();


        Marker marker =map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude,longitude))
                .title("Joueur")
                .snippet("game is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));

        /*
        affichage et mise à jour des coordonnées du joueur
         */
        msg = String.format(
                getResources().getString(R.string.new_location), latitude,
                longitude, altitude, distance);
        Toast.makeText(this, msg, LENGTH_LONG).show();

        distance = p.distanceEntreDeuxPoints(l1,new LatLng(latitude,longitude));

        /*
        questionner le joueur quand il arrive à 1km du point final
         */
        Thread thread = new Thread(this);
        if(distance<=1.0){
            this.questionnerJoueur();
            thread.start();
        }


    }

    public boolean isLocationChanged(Location location){
        return (location != null);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int i = msg.what;
            mprogressDialog.setProgress(msg.arg1);
            switch (i)
            {
                case 1: mprogressDialog.setMessage("cliquer pour repondre   5"); break;
                case 2: mprogressDialog.setMessage("cliquer pour repondre  4"); break;
                case 3: mprogressDialog.setMessage("cliquer pour repondre  3"); break;
                case 4: mprogressDialog.setMessage("cliquer pour repondre  2"); break;
                case 5: mprogressDialog.setMessage("cliquer pour repondre  1"); break;
                case 6: mprogressDialog.setMessage("le temps est écoulé"); break;
                default:
                    // Fermer le message
                    mprogressDialog.dismiss();
            }
        }
    };

    public void run() {
        try {
            // Traitement numéro 1
            // Mettre un traitement à la place de Thread.sleep()
            // Attente de 3000ms = 5s
            Thread.sleep(3000);
            handler.sendEmptyMessage(1);
            // Traitement numéro 2
            // Mettre un traitement à la place de Thread.sleep()
            // Attente de 3000ms = 3s
            Thread.sleep(3000);
            handler.sendEmptyMessage(2);
            // Traitement numéro 3
            // Mettre un traitement à la place de Thread.sleep()
            // Attente de31000ms = 5s
            Thread.sleep(3000);
            handler.sendEmptyMessage(3);
            // Traitement numéro 4
            // Mettre un traitement à la place de Thread.sleep()
            // Attente de3000ms = 2s
            Thread.sleep(3000);
            handler.sendEmptyMessage(4);
            // Traitement par défaut
            // Mettre un traitement à la place de Thread.sleep()
            // Attente de 3000ms = 10s
            Thread.sleep(3000);
            handler.sendEmptyMessage(5);
            Thread.sleep(3000);
            handler.sendEmptyMessage(6);
            mprogressDialog.dismiss();
            Intent intent = new Intent(Maps.this, Question.class);
            startActivity(intent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}