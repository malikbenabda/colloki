package trendy.coloc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import trendy.coloc.entities.User;
import trendy.coloc.tools.MD5;
import trendy.coloc.tools.SaveSharedPreference;

public class ConnectionNative extends AppCompatActivity {

    Button bcon;
    EditText ete, etp;
    String smail = "", spass = "";
    Context context;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_native);
        bcon = (Button) findViewById(R.id.btnLogin);
        context = ConnectionNative.this;


    }

    public void ConnTime(View v) {

        ////checking user input:

///////////////////1.get texts entered by user
        ete = (EditText) findViewById(R.id.et_email);
        smail = ete.getText().toString().trim();
        etp = (EditText) findViewById(R.id.et_pass);
        spass = etp.getText().toString().trim();
/////////////////////////check if text fileds are empty:

        if (!IsFieldEmpty(spass) && !IsFieldEmpty(smail)) {


            if (hasRightLength(spass)) {

                if (checkEmail(smail)) {

                    //passed all contron tests, asyncrunous task to check if ueser exists:

                    User user = new User();


                    spass = MD5.crypt(spass);


                    user = user.CheckUserExistsByKey("email", "[{email" + smail + ",pass" + spass + "}]");


                    if (user.getId() != null && !user.getId().equals("")) {
                        ////////////////////////////open session

                        // Toast.makeText(getApplicationContext(), "session opens", Toast.LENGTH_SHORT).show();


                        SaveSharedPreference.setUserId(ConnectionNative.this, user.getId());

                        //  SaveSharedPreference.setUserId(ConnectionNative.this, "2332234455543");

                        Intent i = new Intent(ConnectionNative.this, MenuActivity.class);
                        startActivity(i);

                    } else {

                        Toast.makeText(getApplicationContext(), "email ou mot de passe incorrect!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ConnectionNative.this, MenuActivity.class);
                        startActivity(i);


                    }

                } else {
                    Toast.makeText(getApplicationContext(), "email non valide", Toast.LENGTH_SHORT).show();

                }

            } else {

                Toast.makeText(getApplicationContext(), "mot de passe doit avoir au moins 6 et max 10 caracteres", Toast.LENGTH_SHORT).show();

            }


        } else {

            Toast.makeText(getApplicationContext(), "verifiez que tous les champs sont saisis", Toast.LENGTH_SHORT).show();


        }


    }


//////////////////////function that checks if email has right format:

    public static boolean checkEmail(String email) {

        Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    /////////////////////////////function that checks if string is null
    public boolean IsFieldEmpty(String s) {

        return s.matches("");


    }

    //////////////////////////////check if pass is not too short or not too long
    public boolean hasRightLength(String p) {

        boolean test = true;
        if (p.length() < 6 || p.length() > 10) {
            test = false;

        }

        return test;


    }


}
