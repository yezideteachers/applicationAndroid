package fr.unice.rallyequiz.ltiplitre;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inscription extends Activity
{

    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;

  /* Réponse JSON */
    private static String KEY_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
        setTitle("Register new account");
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);


        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                JSONObject jObj;
        /* Teste du mot de passe */
                if(isValidPassword(password))
                {

          /* Teste de l'adresse mail */

                    if(isValidEmail(email))
                    {

                        ArrayList nameValuePairs = new ArrayList();
                        nameValuePairs.add(new BasicNameValuePair("tag", "inscription"));
                        nameValuePairs.add(new BasicNameValuePair("name", name));
                        nameValuePairs.add(new BasicNameValuePair("email", email));
                        nameValuePairs.add(new BasicNameValuePair("password", password));

                        try
                        {

              /* Exécute la requête vers le serveur local */

                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("http://10.0.2.2/android_login_api/index.php");
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();
                            InputStream is = entity.getContent();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                            StringBuilder sb = new StringBuilder();
                            String line = reader.readLine();
                            sb.append(line + "\n");
                            is.close();
                /* Résultats de la requête */

                            String result = sb.toString();
                            jObj = new JSONObject(result);

                            if (jObj.getString(KEY_SUCCESS) != null)
                            {
                                String res = jObj.getString(KEY_SUCCESS);

                                if(Integer.parseInt(res) == 1)
                                {

                                    Toast.makeText(getApplicationContext(), "Success Registred", Toast.LENGTH_SHORT).show();
                                    Intent login = new Intent(getApplicationContext(), Connection.class);
                                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(login);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), jObj.get("error_msg").toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        catch(Exception e)
                        {
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Invalid email !", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Password too short (&lt;8) !", Toast.LENGTH_SHORT).show();
            }
        });

        btnLinkToLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

                Intent i = new Intent(getApplicationContext(),  Connection.class);
                startActivity(i);
                finish();
            }
        });
    }


  /* Teste de l'adresse mail */


    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean isValidEmail(String email)
    {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


  /* Teste si le mot de passe fait plus de 8 caractères */

    public static boolean isValidPassword(String password)
    {
        if(password.length() >= 8) {
            return true;
        }
        else {
            return false;
        }
    }
}
