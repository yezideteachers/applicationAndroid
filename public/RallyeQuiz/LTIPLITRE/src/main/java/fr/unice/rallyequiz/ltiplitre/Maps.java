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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import static android.widget.Toast.LENGTH_LONG;


public class Maps extends Activity implements LocationListener,Runnable{

    Epreuve e = new Epreuve(43.7, 07.4);
    LatLng l1 = new LatLng(e.getPositionx(),e.getPositiony());
    private LocationManager locationManager;
    double latitude;
    double longitude;
    double altitude;
    static boolean a_t_il_repondu=false;
    boolean est_il_questionne = false;
    Perimetre p = new Perimetre();
    Criteria criteria = new Criteria();
    String provider ;
    GoogleMap map;
    String msg;
    Location location;
    double distance;
    private ProgressDialog mprogressDialog;
    double latdepl =0.0;
    double londepl = 0.0;


    ArrayList array = new ArrayList<CharSequence[]>();
    long startTime= System.nanoTime();

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
            latdepl = location.getLatitude();
            londepl = location.getLongitude();
            startTime = System.nanoTime();
        } else {
            Toast.makeText(this, "couldn't get provider", LENGTH_LONG).show();
        }



        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        LatLng epreuve = new LatLng(e.getPositionx(), e.getPositiony());

        e.positioneperueve(map);

        /*
        affichage et mise à jour des coordonnées du joueur
         */
        msg = String.format(
                getResources().getString(R.string.new_location), latitude,
                longitude, altitude, distance);
        Toast.makeText(this, msg, LENGTH_LONG).show();



        final Thread thread = new Thread(this);



        final Button avancerButton = (Button) findViewById(R.id.avancer);

        avancerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((e.getPositionx() - latdepl) < 0.01 && (e.getPositionx() - latdepl) >= 0) {
                    latdepl = location.getLatitude();
                }
                else{
                    latdepl +=0.001;
                }
                if ((e.getPositiony() - londepl) < 0.001 && (e.getPositiony() - londepl) >= 0){
                    londepl = location.getLongitude();
                }
                else{
                    londepl +=0.001;
                }
                System.out.println("#################################################### \n" + latdepl + "##### \n" +londepl) ;
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latdepl,londepl), 10));
                map.addMarker(new MarkerOptions()
                        .title("E1")
                        .snippet("Rally Quiz")
                        .position(new LatLng(latdepl,londepl)));

                distance = p.distanceEntreDeuxPoints(l1,new LatLng(latdepl,londepl));

        /*
        questionner le joueur quand il arrive à 200m du point final
         */

                if(estIlProche(l1, new LatLng(latitude, longitude)) && !est_il_questionne){  //(distance<=1.0 && distance>=0.2)
                    questionnerJoueur();
                    thread.start();

                }

        /*si le joueur est arrivé on lui affichage le temps qu'il a mis*/
                if(distance==0){
                    String s=TempsCourse.formatTime(System.nanoTime()-startTime);
                    afficherScore(s);
                    TempsCourse.stockerScore(startTime);
                    Intent intent = new Intent(Maps.this, MainActivity.class);
                    startActivity(intent);
                }



            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void gererView(){
        final CharSequence [] items ={"Allez à la position du joueur"};
        final LatLng joueur = new LatLng(this.latitude, this.longitude);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(items[0]);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(joueur, 10));



            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String []> listeQuestions(String s){
            String []table = s.split("__");
            ArrayList<String[]> listeQuiz = new ArrayList(table.length);
            for(int i=0; i<table.length; i++){
                listeQuiz.add(table[i].split("#"));

            }

        return listeQuiz;
    }
    public void questionnerJoueur() {
        Random random = new Random();
        est_il_questionne=true;
        int indexQuiz = random.nextInt(2); //pour le moment on a que deux questions dans la BD
        String s=Question.recupererLesQuestions();
        String[] question;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(Question.recupererLesQuestions()!=null) {
            question = listeQuestions(s).get(indexQuiz);


            CharSequence[] qcm = {question[0], question[1], question[2], question[3]};
            final CharSequence[] items = new CharSequence[2];
           items[0] = qcm[1];
           // items[1] = qcm[3];
            array.add(items);

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
        }

        else{
            question = new String[]{"Problem rencontré de la partie serveur ", "resoudre le problem", " ", " "};

            builder.setTitle(question[0]);
            final CharSequence[] items = new CharSequence[1];
            items[0]=question[0];
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String choice = (String) items[which];



                }

            });
        }

       est_il_questionne=true;
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

        a_t_il_repondu=true;
        CharSequence [] ch = (CharSequence[]) array.get(0);
        String s="";
        if(choice.equals(ch[1])) {

            s="bonne reponse : "+ch[0];
            Toast.makeText(this,s , LENGTH_LONG).show();

        }
        else {

            Intent intent = new Intent(Maps.this, Penalite.class);
            startActivity(intent);


        }



    }


    @Override
    public void onLocationChanged(Location location) {


        latitude = location.getLatitude();
        longitude =location.getLongitude();

        Question.recupererLesQuestions();
        Marker marker =map.addMarker(new MarkerOptions()
                .position(new LatLng(this.latitude,this.latitude))
                .title("Joueur")
                .snippet("game is cool")
                        .position(new LatLng(this.latitude, this.latitude)));

        LatLng j = new LatLng(latdepl, londepl);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(j, 10));


        /*
        affichage et mise à jour des coordonnées du joueur
         */
      /*  msg = String.format(
                getResources().getString(R.string.new_location), latitude,
                longitude, altitude, distance);
        Toast.makeText(this, msg, LENGTH_LONG).show();*/

        distance = p.distanceEntreDeuxPoints(l1,new LatLng(latitude,longitude));

        /*
        questionner le joueur quand il arrive à 1km du point final
         */
        Thread thread = new Thread(this);
        if(estIlProche(l1, new LatLng(latitude, longitude)) && !est_il_questionne){
           this.questionnerJoueur();
            thread.start();

        }

        /*si le joueur est arrivé on lui affichage le temps qu'il a mis*/
        if(distance==0){
            String s=TempsCourse.formatTime(System.nanoTime()-startTime);
            afficherScore(s);
            TempsCourse.stockerScore(startTime);
            Intent intent = new Intent(Maps.this, MainActivity.class);
            startActivity(intent);
        }


    }
  /*cette fonction affiche le temps que l'utilisateur a mis pour arrivé
    @return void*/
   public void afficherScore(String time){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Score...");
        alertDialog.setMessage("Vous avez fait :"+time);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// here you can add functions
            }
        });
        alertDialog.setIcon(R.drawable.android);
        alertDialog.show();
    }

    public static boolean  estIlProche(LatLng pos1, LatLng pos2){
         double distance = Perimetre.distanceEntreDeuxPoints(pos1, pos2);
        if(distance<0.1){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isLocationChanged(Location location){
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
            Intent intent = new Intent(Maps.this, Penalite.class);
            if(!a_t_il_repondu){startActivity(intent);}
        /*    a_t_il_repondu=true;
            Intent intent2 = new Intent(Maps.this, Penalite.class);
            startActivity(intent);
            System.out.println("###################################################################################");
*/

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
