package fr.unice.rallyequiz.ltiplitre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connexion extends Activity {

    private Socket client;
    Button connect;
    EditText pseudo;
    EditText pass;
    TextView text;
    private String messsage;
    private PrintWriter printwriter;

    /* Réponse JSON */
    private static String KEY_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        setTitle("connexion");
        pseudo = (EditText) findViewById(R.id.pcon);
        pass = (EditText) findViewById(R.id.passconn);
        text =(TextView) findViewById(R.id.connect);
        connect = (Button) findViewById(R.id.connect);



        connect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(new Runnable() {

                    public void run() {
                        //client = null;

                        try {

                            client = new Socket("192.168.1.37", 3003);
                            //client.connect();
                            messsage = pseudo.getText().toString() + ";" + pass.getText().toString();
                            printwriter = new PrintWriter(client.getOutputStream(), true);
                            printwriter.write(messsage);  //write the message to output stream

                            printwriter.flush();
                            printwriter.close();
                            InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                            messsage = bufferedReader.readLine();
                            System.out.println(messsage);
                            //if(messsage.equals("connexion reussi")){
                            //Intent intent = new Intent(Connexion.this, Maps.class);
                            //startActivity(intent);
                            text.setText(messsage);
                            //}

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
