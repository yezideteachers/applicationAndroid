package fr.unice.rallyequiz.ltiplitre;

import android.app.Activity;
import android.widget.EditText;

public class Connexion extends Activity{

    /*
    rejouter un constructeur
     */

    /*
    une fonction permettant de verifier les données entrées par l'utilisateur
     */

    public boolean verifier_donnees(){
        EditText email= (EditText) findViewById(R.id.user_email);
        EditText password = (EditText) findViewById(R.id.user_password);
        /*
        a rajouter : vérifier la compatibilité des données entrées par l'utilisateur avec celles de la "base de donnée"
         */
        return false;
    }
}
