package fr.unice.rallyequiz.ltiplitre.test;

import android.test.InstrumentationTestCase;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.unice.rallyequiz.ltiplitre.TempsCourse;

import static org.mockito.Mockito.mock;

/**
 * Created by yezide on 14/05/2014.
 */
public class TempsCourseTest extends InstrumentationTestCase {


    static Socket client;

    public void testFormatTime() throws Exception {
        long time = 122121212; //en nano-seconde
        TempsCourse temps = new TempsCourse();
        TempsCourse mockTemps = mock(TempsCourse.class);
        String tempsString = temps.formatTime(time);
        String mockString = mockTemps.formatTime(time);
        assertNotNull(mockString);
        assertNotNull(tempsString);
        assertEquals(tempsString,mockString);

    }


    public void testEnvoyerScore() throws IOException{


        long time=100000; //nano secondes
        final String message = ""+time;
        final boolean[] reussi = {false};


                try {

                    client = new Socket("192.168.31.245", 3003);
                    //client.connect();
                    reussi[0] =true;
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





        assertEquals(reussi[0],true);
    }


}

