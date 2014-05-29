package fr.unice.rallyequiz.ltiplitre.test;

import android.location.Location;
import android.os.Handler;
import android.test.InstrumentationTestCase;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.mockito.Mockito;

import java.io.IOException;

import fr.unice.rallyequiz.ltiplitre.Maps;

import static org.mockito.Mockito.mock;

public class MapsTest extends InstrumentationTestCase {
    private Maps map;
    //private GoogleMapOptions m  = Mockito.mock(GoogleMapOptions.class);


    /*public MapsTest() {
        super(Maps.class);
    }*/


    public void testInitial() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Maps tests
                Maps maps;
                // GoogleMap support des tests
                GoogleMap gmap;
                // Récupération de la Map
                gmap = SupportMapFragment.newInstance().getMap();
                if (gmap == null) {
                    handler.postDelayed(this, 100);
                } else {
                    maps = new Maps();
                    assertNotNull(maps);

                }
            }
        }, 100);
    }



    public void testEstIlProche() throws IOException {

        Location location = mock(Location.class);
        Location nextLocation = mock(Location.class);
        Mockito.when(location.getLatitude()).thenReturn(43.70);
        Mockito.when(location.getLongitude()).thenReturn(07.2);
        Mockito.when(nextLocation.getLatitude()).thenReturn(43.72);
        Mockito.when(nextLocation.getLongitude()).thenReturn(7.2);
        LatLng l1 = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng l2 = new LatLng(nextLocation.getLatitude(), nextLocation.getLongitude());

        assertEquals(Maps.estIlProche(l1, l2), true);

    }








    public void testonLocationChaged () throws IOException{
        final Location location = mock(Location.class);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Maps tests
                Maps mockmaps;
                // GoogleMaptests
                GoogleMap gmap;
                // Récupération de la Map
                gmap = SupportMapFragment.newInstance().getMap();
                if (gmap == null) {
                    handler.postDelayed(this, 100);
                } else {

                    mockmaps = new Maps();
                    mockmaps.onLocationChanged(location);
                    Location locmaps = null;
                    map.onLocationChanged(locmaps);

                    assertEquals(location,locmaps);
                }
            }
        }, 100);
    }


    public void testisLocationChaged() throws IOException{
        Location location = mock(Location.class);
       /* Maps mockmap = mock(Maps.class);
        mockmap.onLocationChanged(location);*/

        Mockito.when(location.getLatitude()).thenReturn(10.0);
        Mockito.when(location.getLongitude()).thenReturn(10.2);

        assertTrue(Maps.isLocationChanged(location));
    }

   /* public void testVerifier() throws IOException, InterruptedException {
        Maps map = mock(Maps.class);
        CharSequence [] choix = {"","vrai"};
        String reponse = "vrai";
        boolean rep = false;  //rep va vouloir tru si le joueur a repondu juste
        map.verifier(reponse);
        assertEquals(rep);
    }*/





}
