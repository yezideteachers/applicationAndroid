package fr.unice.rallyequiz.ltiplitre;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Epreuve extends FragmentActivity{
    private  double positionx;
    private double positiony;
    //private Question question;

    public Epreuve(double x , double y){
        this.positionx =x;
        this.positiony =y;

    }

    public void setPositionx(double x){
        this.positionx = x;

    }

    public void setPositiony(double y){
        this.positiony = y;
    }

    public double getPositionx(){
        return this.positionx;
    }

    public double getPositiony(){
        return this.positiony;
    }

    public void positioneperueve(GoogleMap map ){


        LatLng epreuve1 = new LatLng(this.getPositionx(),this.getPositiony());


        map.setMyLocationEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(epreuve1, 15));
        map.addMarker(new MarkerOptions()
                .title("E1")
                .snippet("epreuve 1")
                .position(epreuve1));

    }





    public void detecterPosition() {

        final CharSequence[] items = {"res", "bleu"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("pick a color");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String choice = (String) items[which];
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



}