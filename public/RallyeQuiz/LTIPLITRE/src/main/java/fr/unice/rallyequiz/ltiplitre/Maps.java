package fr.unice.rallyequiz.ltiplitre;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Random;

import static android.widget.Toast.LENGTH_LONG;


public class Maps extends Activity implements LocationListener,Runnable{
    Random rand = new Random();
    public static String score;
    Epreuve e ;
    LatLng l1 = new LatLng(43.7,7.4);
    private LocationManager locationManager;
    double latitude;
    double longitude;
    static String msgrep="mauvaise repone";
    static boolean deja_repondu=false;
    boolean deja_questionne=false;
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
    boolean bonus=false;
    double lat=43.7;
    double lon=7.4;

    Marker marker;

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
            lat=rand.nextInt(2) + 0.1 + location.getLatitude();
            lon=rand.nextInt(2) + 0.1 + location.getLongitude();

        } else {
            Toast.makeText(this, "couldn't get provider", LENGTH_LONG).show();
        }

//epreuve aleatoirement



        e= new Epreuve(lat , lon);
        //e=new Epreuve(43.7,7.4);


        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        l1 = new LatLng(e.getPositionx(), e.getPositiony());

        marker=map.addMarker(new MarkerOptions()
                .title("E1")
                .snippet("Rally Quiz")
                .position(new LatLng(latdepl,londepl)));

        e.positioneperueve(map);
//tracé de l'itineraire du joueur
        final PolylineOptions polylines = new PolylineOptions();
        polylines.color(Color.RED);

        //On construit le polyline
        LatLng latLng = new  LatLng(this.latitude,this.longitude);
        LatLng lt = new LatLng(e.getPositionx(),e.getPositiony());
        //on aoute la position du joueur
        polylines.add(latLng);
        //on ajoute la position de l'epreuve
        polylines.add(lt);
        map.addPolyline(polylines);


        simulerDeplacement();

        simulerAcceleration();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* cette fonction elle permet de simuler de delacement du joueur
     @return void
      */
    public void simulerDeplacement(){

        final Thread thread = new Thread(this);
        System.out.println("---------------------------------------------------------- \n" + latdepl + "   " +londepl) ;
        final Button avancerButton = (Button) findViewById(R.id.avancer);

      final Marker m=marker;

        avancerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((e.getPositionx() - latdepl) > 0.004){
                    latdepl +=0.004;
                }
                if ((e.getPositiony() - londepl) > 0.004){
                    londepl +=0.004;
                }

                System.out.println("#################################################### \n" + latdepl + "##### \n" +londepl) ;
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latdepl,londepl), 10));
                m.setPosition(new LatLng(latdepl,londepl));

                distance = p.distanceEntreDeuxPoints(l1,new LatLng(latdepl,londepl));
                System.out.println("DISTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANCE " + distance);
        /*
        questionner le joueur quand il arrive à 200m du point final
         */

                if(estProche(l1, new LatLng(latdepl, londepl)) && !deja_questionne){  //(distance<=1.0 && distance>=0.2)
                    System.out.println("QUESTIONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNS :");
                    questionnerJoueur();
                    thread.start();



                }

        /*si le joueur est arrivé on lui affichage le temps qu'il a mis*/
                if(estArrive(l1,new LatLng(latdepl,londepl))){
                    score=TempsCourse.formatTime(System.nanoTime()-startTime);
                    afficherScore(score);
                    TempsCourse.envoyerScore(startTime);

                }



            }
        });

    }

    /*cette fonction permet de simuler l'acceleration de la vitesse du joueur
    @return void
     */
    public void simulerAcceleration(){

        final Marker m=marker;

        if(bonus){
            Toast.makeText(this, "bonus déjà utilisé ", LENGTH_LONG).show();
        }

        final Thread thread = new Thread(this);
        final Button accelererButton = (Button) findViewById(R.id.accelerer);
        accelererButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((e.getPositionx() - latdepl) > 0.05) {
                    latdepl += 0.02;
                }

                if ((e.getPositiony() - londepl) > 0.05) {
                    londepl += 0.02;
                }

                bonus=true;
                System.out.println("#################################################### \n" + latdepl + "##### \n" + londepl);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latdepl, londepl), 10));
                m.setPosition(new LatLng(latdepl, londepl));


            }
        });
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
        deja_questionne=true;
        deja_repondu=false;
        int indexQuiz = random.nextInt(2); //pour le moment on a que deux questions dans la BD
        final String s=Question.recupererLesQuestions();
        String[] question;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(Question.recupererLesQuestions()!=null) {
            question = listeQuestions(s).get(indexQuiz);


            final CharSequence[] qcm = {question[0], question[1], question[2], question[3]};
            final CharSequence[] items = new CharSequence[2];
            items[0] = qcm[2];
             items[1] = qcm[3];
            array.add(qcm);

            builder.setTitle(qcm[0]);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String choice = (String) items[which];
                    try {

                        verifier(choice,(String)qcm[1]);
                        System.out.println("Questionnement du joueur : 000000000000000000000000000000000"+choice+"  "+qcm[1]);



                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }


                }

            });
            Toast.makeText(this,msgrep , LENGTH_LONG).show();
        }

        else{
            question = new String[]{"Problem rencontré  partie serveur "+"\n"+"Cliquer pour continuez votre course", "ok"};

            builder.setTitle(question[0]);
            final CharSequence[] items = new CharSequence[1];
            items[0]=question[0];
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String choice = (String) items[which];
                    deja_repondu=true;




                }

            });
        }



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


    public boolean verifier(String s1,String s2) throws InterruptedException {

        deja_repondu=true;
        String choiceS=s1;
        String repS=s2;

        System.out.println("REPOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOONSE :"+ s1+"  "+s2);
        if(choiceS.equals(repS) || choiceS.equals(repS+" ") || choiceS.equals(" "+repS) || (" "+choiceS).equals(repS) || (choiceS+" ").equals(repS)) {
            System.out.println("REPOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOONSE BIZZARE:"+ s1+"  "+s2);
            msgrep="bonne reponse";

            return true;

        }
        else {

            Intent intent = new Intent(Maps.this, Penalite.class);
            startActivity(intent);
            return false;

        }



    }


    @Override
    public void onLocationChanged(Location location) {


        latitude = location.getLatitude();
        longitude =location.getLatitude();

        Question.recupererLesQuestions();


        LatLng j = new LatLng(location.getLatitude(), location.getLongitude());

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(j, 10));


        /*
        affichage et mise à jour des coordonnées du joueur
         */
      /*  msg = String.format(
                getResources().getString(R.string.new_location), latitude,
                longitude, altitude, distance);
        Toast.makeText(this, msg, LENGTH_LONG).show();*/

        distance = p.distanceEntreDeuxPoints(new LatLng(l1.latitude,l1.longitude),new LatLng(location.getLatitude(),location.getLongitude()));
               System.out.println("DIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIISTANCE : "+distance);

        /*
        questionner le joueur quand il arrive à 200 m du point final
         */
        Thread thread = new Thread(this);
       if(estProche(l1, new LatLng(location.getLatitude(), location.getLongitude())) && !deja_questionne){
           this.questionnerJoueur();
            thread.start();

        }

        /*si le joueur est arrivé on lui affichage le temps qu'il a mis*/
        if(estArrive(l1,new LatLng(location.getLatitude(), location.getLongitude()))){
            score=TempsCourse.formatTime(System.nanoTime()-startTime);
            afficherScore(score);
            TempsCourse.envoyerScore(startTime);

        }


    }

  /*cette fonction affiche le temps que l'utilisateur a mis pour arrivé
    @return void*/
   public void afficherScore(String time){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Score...");
        alertDialog.setMessage("Vous avez fait :"+time);
        final Intent intent = new Intent(Maps.this, Profil.class);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                startActivity(intent);
            }
        });
        alertDialog.setIcon(R.drawable.coureur);
        alertDialog.show();
    }

    public static boolean  estProche(LatLng pos1, LatLng pos2){
         double distance = Perimetre.distanceEntreDeuxPoints(pos1, pos2);
        if(distance<0.2 && distance>0){

            return true;
        }
        else{
            return false;
        }
    }

    public static boolean estArrive(LatLng pos1, LatLng pos2){
        double distance = Perimetre.distanceEntreDeuxPoints(pos1, pos2);
        if(distance<=0.02 && distance>=0){
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
            if(!deja_repondu){startActivity(intent);}
        /*
            Intent intent2 = new Intent(Maps.this, Penalite.class);
            startActivity(intent);
            System.out.println("###################################################################################");
*/

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
