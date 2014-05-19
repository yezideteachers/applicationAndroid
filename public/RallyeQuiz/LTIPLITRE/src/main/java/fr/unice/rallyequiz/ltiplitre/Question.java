package fr.unice.rallyequiz.ltiplitre;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by yezide on 20/04/2014.
 */
public class Question extends Activity {

    public static String mess;

    /*recuperer la lise des questions de la base de données*/
    public static String recupererLesQuestions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Socket client = new Socket("192.168.31.245",3003);
                    InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                    mess = bufferedReader.readLine();
                    System.out.println(mess);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return mess;

    }
}
