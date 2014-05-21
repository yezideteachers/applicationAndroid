package fr.unice.rallyequiz.ltiplitre;

import android.app.Activity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by yezide on 11/05/2014.
 */
public class TempsCourse extends Activity {

    private static Socket client;


    // A method that converts the nano-seconds to Seconds-Minutes-Hours form
    public static String formatTime(long nanoSeconds)
    {
        int hours, minutes, remainder, totalSecondsNoFraction;
        double totalSeconds, seconds;


        // Calculating hours, minutes and seconds
        totalSeconds = (double) nanoSeconds / 1000000000.0;
        String s = Double.toString(totalSeconds);
        String [] arr = s.split("\\.");
        totalSecondsNoFraction = Integer.parseInt(arr[0]);
        hours = totalSecondsNoFraction / 3600;
        remainder = totalSecondsNoFraction % 3600;
        minutes = remainder / 60;
        seconds = remainder % 60;
        if(arr[1].contains("E")) seconds = Double.parseDouble("." + arr[1]);
        else seconds += Double.parseDouble("." + arr[1]);


        // Formatting the string that conatins hours, minutes and seconds
        StringBuilder result = new StringBuilder(".");
        String sep = "", nextSep = " and ";
        if(seconds > 0)
        {
            result.insert(0, " seconds").insert(0, seconds);
            sep = nextSep;
            nextSep = ", ";
        }
        if(minutes > 0)
        {
            if(minutes > 1) result.insert(0, sep).insert(0, " minutes").insert(0, minutes);
            else result.insert(0, sep).insert(0, " minute").insert(0, minutes);
            sep = nextSep;
            nextSep = ", ";
        }
        if(hours > 0)
        {
            if(hours >= 1) result.insert(0, sep).insert(0, " hours").insert(0, hours);
            else result.insert(0, sep).insert(0, " hour").insert(0, hours);
        }
        return result.toString();
    }




    /*cette fonction permet de stocker le score du joueur dans la base de donnée en passant par le serveur
 *  le score va ètre en format du suite d'entier (nano-seconde)
 *  */
    public static void stockerScore(long time){
        final String message = ""+time;
        new Thread(new Runnable() {

            public void run() {
                //client = null;

                try {

                    client = new Socket("192.168.31.245", 3003);
                    //client.connect();
                    PrintWriter printwriter = new PrintWriter(client.getOutputStream(), true);
                    printwriter.write(message);  //write the message to output stream
                    System.out.println(message + "\n");
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



}
