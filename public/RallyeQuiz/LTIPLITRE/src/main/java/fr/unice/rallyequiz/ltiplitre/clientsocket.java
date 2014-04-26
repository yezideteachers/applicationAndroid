package fr.unice.rallyequiz.ltiplitre;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.Thread;

/**
 * Created by hasso on 19/04/14.
 */


public class clientsocket extends Activity {

    private Socket client;
    private PrintWriter printwriter;
    private EditText textField;
    private Button button;
    private String messsage;
    private TextView tex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        textField = (EditText) findViewById(R.id.m); //reference to the text field
        button = (Button) findViewById(R.id.be);   //reference to the send button

        //Button press event listener
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                messsage = textField.getText().toString(); //get the text message on the text field
                //textField.setText("");      //Reset the text field to blank
                //tex.append("hello");
                new Thread(new Runnable() {

                    public void run() {
                        //client = null;

                        try {

                            client = new Socket("192.168.1.37 ",4444);
                            //client.connect();

                            printwriter = new PrintWriter(client.getOutputStream(),true);
                            printwriter.write(messsage);  //write the message to output stream

                            printwriter.flush();
                            printwriter.close();
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

