package fr.unice.rallyequiz.ltiplitre;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Inscription extends Activity {
    private Socket client;
    Button inscrir;
    Button btnLinkToLogin;
    EditText pseudo;
    EditText pass;
    //edit text
    EditText f;
    private String messsage;
    private PrintWriter printwriter;

    /* Réponse JSON */
    private static String KEY_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
        setTitle("Inscription");
        pseudo = (EditText) findViewById(R.id.pse);
        pass = (EditText) findViewById(R.id.pass);

        inscrir = (Button) findViewById(R.id.inscrip);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);


        inscrir.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(new Runnable() {

                    public void run() {
                        //client = null;

                        try {

                            client = new Socket("192.168.31.245", 3003);  //"10.0.3.2"
                            //client.connect();
                            messsage = "Inscription; "+ pseudo.getText().toString()+";"+pass.getText().toString();
                            printwriter = new PrintWriter(client.getOutputStream(), true);
                            printwriter.write(messsage);  //write the message to output stream

                            printwriter.flush();
                            printwriter.close();

                            InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                            messsage = bufferedReader.readLine();
                            System.out.println(messsage);
                            if(messsage.equals("connexion reussi")){
                                Intent intent = new Intent(Inscription.this, Joueur.class);
                                startActivity(intent);

                            }


                            client.close();   //closing the connection

                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


            }
        });
    }
}