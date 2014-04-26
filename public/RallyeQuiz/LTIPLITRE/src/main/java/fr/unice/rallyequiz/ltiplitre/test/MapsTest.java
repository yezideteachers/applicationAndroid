package fr.unice.rallyequiz.ltiplitre.test;

import android.location.Location;
import android.os.Handler;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import fr.unice.rallyequiz.ltiplitre.Maps;

import static org.mockito.Mockito.mock;

public class MapsTest extends ActivityInstrumentationTestCase2<Maps> {
  /* public MapsTest(Class<Maps> activityClass) {
        super(activityClass);
    }*/

    private Maps maps;
    //private GoogleMapOptions m  = Mockito.mock(GoogleMapOptions.class);

    // TODO Mock with GoogleMap.class OR Maps.class,
    // TODO Simulate Map for tests
    public MapsTest() {
        super(Maps.class);
    }

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


    public void onLocationChagedTest(final Location location){
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
                    maps.onLocationChanged(locmaps);

                    assertEquals(location,locmaps);
                }
            }
        }, 100);
    }


    public void isLocationChagedTest(Location location){
        Maps mockmap = mock(Maps.class);
        mockmap.onLocationChanged(location);
        assertTrue(mockmap.isLocationChanged(location));
    }



}
