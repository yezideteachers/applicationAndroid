package fr.unice.rallyequiz.ltiplitre.test;


import android.test.InstrumentationTestCase;

import fr.unice.rallyequiz.ltiplitre.Inscription;

public class InscriptionTest extends InstrumentationTestCase{


    /*
    cette fonction verifie que le compte n'existe pas auparavant.
     */
    public void test_verifier_peudo_email() throws Exception{
        String [][] s= new String[1][2];
        s[0][0]="nom";
        s[0][1]="a";
       /* Inscription ins = new Inscription("nom","a","xxxxxx",s);
        assertTrue(ins.verifier_pseudo_email());
        assertTrue(ins.verifier_mot_de_passe());
*/
    }

     /*cette fonction verifie que le mot de passe  n'est pas vide et contient au moins 4 caracteres
      */
}
