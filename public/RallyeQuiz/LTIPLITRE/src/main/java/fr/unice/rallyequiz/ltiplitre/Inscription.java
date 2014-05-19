package fr.unice.rallyequiz.ltiplitre;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inscription extends Activity {
    private Socket client;
    Button inscrir;
    Button btnLinkToLogin;
    EditText pseudo;
    EditText pass;
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

                            client = new Socket("192.168.1.37", 3003);
                            //client.connect();
                            messsage = pseudo.getText().toString()+";"+pass.getText().toString();
                            printwriter = new PrintWriter(client.getOutputStream(), true);
                            printwriter.write(messsage);  //write the message to output stream

                            printwriter.flush();
                            printwriter.close();
                            // inputStreamReader.close();
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