package fr.unice.admin.ltiplitre;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.unice.admin.ltiplitre.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Question extends Activity {

    public String message;
    String tabQues[];
     String tabchoix1[]=new String[4];
    String tabchoix2[]=new String[4];
    String tabchoix3[]=new String[4];
    String tabchoix4[]=new String[4];
    String tabchoix5[]=new String[4];

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.question1).setOnTouchListener(mDelayHideTouchListener);

        message=recoverAllQuestion();





    }



    /** cette fonction permet de recuperer une question de la base de donnée,
     * renvoie true en cas de succes false sinon
     *
     * @return boolean
     */

    public boolean recoverOneQuestion(){

        return false;
    }


    /** cette fonction permet de recuperer toute les question de la base de donnée,
     * renvoie true en cas de succes false sinon
     *
     * @return boolean
     */

    public String recoverAllQuestion(){
        final String[] s = new String[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Socket client = new Socket("192.168.31.245",3003);
                    InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                    s[0] = bufferedReader.readLine();
                    String tabQues[];
                    tabQues=s[0].split("__");
                    if(tabQues[0].equals("questions")) {
                        message=s[0];
                        System.out.println("MESSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGE :" + message);
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                displayQuestions(s[0]);
            }
        }).start();


        return s[0];
    }

    /**
     * cette fonction permet d'envoyer le nom de la fonction a supprimer au serveur
     * qui lui de son va en voyer un msg au BD pour la suppression
     * @param s
     */
    public void deleteQuestion(String s){
        final String ques=s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Socket client = new Socket("192.168.31.245",3003);
                    PrintWriter p = new PrintWriter(client.getOutputStream());
                    p.write("delete"+";"+ques);
                    p.flush();
                    p.close();

                    client.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }



    /**
     * Cette fonction permet d'afficher toutes les questions dans la page Question
     * @param m
     */
    public void displayQuestions(String m){

        final String msg =m;

        if(m != null){tabQues=message.split("__");
            tabchoix1=tabQues[1].split("#");
            tabchoix2=tabQues[2].split("#");
            tabchoix3=tabQues[3].split("#");
            tabchoix4=tabQues[4].split("#");
            tabchoix5=tabQues[5].split("#");
            System.out.println("LISTE DE SQUESTIONNNNNNNNNNNS :"+m);
        }



        final CharSequence [] items1=new CharSequence[4];
        final CharSequence [] items2=new CharSequence[4];
        final CharSequence [] items3=new CharSequence[4];
        final CharSequence [] items4=new CharSequence[4];
        final CharSequence [] items5=new CharSequence[4];
        items1[0]="la bonne reponse :"+tabchoix1[1];
        items1[1]="les choix :"+tabchoix1[2]+" et "+tabchoix1[3];
        items1[2]="delete";
        items1[3]="back";

        items2[0]="la bonne reponse :"+tabchoix2[1];
        items2[1]="les choix :"+tabchoix2[2]+" et "+tabchoix2[3];
        items2[2]="delete";
        items2[3]="back";

        items3[0]="la bonne reponse :"+tabchoix3[1];
        items3[1]="les choix :"+tabchoix3[2]+" et "+tabchoix3[3];
        items3[2]="delete";
        items3[3]="back";

        items4[0]="la bonne reponse :"+tabchoix4[1];
        items4[1]="les choix :"+tabchoix4[2]+" et "+tabchoix4[3];
        items4[2]="delete";
        items4[3]="back";

        items5[0]="la bonne reponse :"+tabchoix5[1];
        items5[1]="les choix :"+tabchoix5[2]+" et "+tabchoix5[3];
        items5[2]="delete";
        items5[3]="back";

        final Button question1Button = (Button) findViewById(R.id.question1);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        question1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.setTitle("question : "+tabchoix1[0]);
               /* alertDialog.setMessage("la bonne reponse :"+tabchoix1[1]+"\n"+"les choix :"+
                        tabchoix1[2]+" et "+tabchoix1[3]);*/

                alertDialog.setItems(items1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            String choice = (String) items1[which];
                        if(choice.equals(items1[2])) {
                            deleteQuestion("quiz");
                        }
                    }
                });
                // alertDialog.setIcon(R.drawable.coureur);
                alertDialog.show();

            }

        });


        final Button question2Button = (Button) findViewById(R.id.question2);
        final AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);

        question2Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alertDialog2.setTitle("question :"+tabchoix2[0]);


                alertDialog2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String choice = (String) items2[which];

                    }
                });
                // alertDialog.setIcon(R.drawable.coureur);
                alertDialog2.show();

            }

        });


        final Button question3Button = (Button) findViewById(R.id.question3);
        final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(this);

        question3Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alertDialog3.setTitle("question :\n"+tabchoix3[0]);


                alertDialog3.setItems(items3, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String choice = (String) items3[which];

                    }
                });
                // alertDialog.setIcon(R.drawable.coureur);
                alertDialog3.show();

            }

        });

        final Button question4Button = (Button) findViewById(R.id.question4);
        final AlertDialog.Builder alertDialog4 = new AlertDialog.Builder(this);

        question4Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alertDialog4.setTitle("question :\n"+tabchoix4[0]);


                alertDialog4.setItems(items4, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String choice = (String) items4[which];

                    }
                });
                // alertDialog.setIcon(R.drawable.coureur);
                alertDialog4.show();

            }

        });


        final Button question5Button = (Button) findViewById(R.id.question5);
        final AlertDialog.Builder alertDialog5 = new AlertDialog.Builder(this);

        question5Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alertDialog5.setTitle("question :\n"+tabchoix5[0]);


                alertDialog5.setItems(items5, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String choice = (String) items5[which];

                    }
                });
                // alertDialog.setIcon(R.drawable.coureur);
                alertDialog5.show();

            }

        });
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
