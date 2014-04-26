package fr.unice.rallyequiz.ltiplitre.test;


import android.test.ActivityInstrumentationTestCase2;

import fr.unice.rallyequiz.ltiplitre.Connexion;

public class ConnexionTest extends ActivityInstrumentationTestCase2<Connexion>{

    public ConnexionTest(Class<Connexion> activityClass) {
        super(activityClass);
    }

    public void test_verifier_donnees() throws Exception{
        Connexion c = new Connexion();
        assertTrue(c.verifier_donnees());
    }
}