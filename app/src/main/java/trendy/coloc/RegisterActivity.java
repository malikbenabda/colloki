package trendy.coloc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import trendy.coloc.adapters.CustomOnItemSelectedListener;
import trendy.coloc.entities.User;

public class RegisterActivity extends Activity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    Spinner s_etat, s_sexe;
    EditText ete, etp, etnom, etprenom, ettel, etoccup, etpr;
    String smail = "", spass = "", nom = "", prenom = "", occ = "", tel = "", gender = "", dn = "", etat_c = "", prefsmok = "", prefan = "";
    String img = "";
    Context context;
    Date converted_birthday;
    private int year, month, day;

    RadioGroup rgs, rgs1;

    EditText tag, description;

    String tag_s = "", description_s = "";

    RadioButton rbsmo, rbnonsm, rbnan, rban;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;
        addListenerOnSpinnerItemSelection();
        dateView = (TextView) findViewById(R.id.dateText);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        dateView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.m.main, menu);
        return true;
    }

    public void UploadPhoto(View v) {


    }

    public void Register(View v) {


        s_etat = (Spinner) findViewById(R.id.spinner_status);
        s_etat.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        s_sexe = (Spinner) findViewById(R.id.spinner_gender);
        s_sexe.setOnItemSelectedListener(new CustomOnItemSelectedListener());


        gender = s_sexe.getSelectedItem().toString();
        etat_c = s_etat.getSelectedItem().toString();
        dateView = (TextView) findViewById(R.id.dateText);


        dn = dateView.getText().toString();

        ete = (EditText) findViewById(R.id.et_email);


////////////////////////////build json array:

        //  Toast.makeText(getApplicationContext(), "selected"+selectedtext, Toast.LENGTH_SHORT).show();


        smail = ete.getText().toString().trim();
        etp = (EditText) findViewById(R.id.et_pass);
        spass = etp.getText().toString().trim();
        etnom = (EditText) findViewById(R.id.et_nom);
        nom = etnom.getText().toString();
        etprenom = (EditText) findViewById(R.id.et_prenom);
        prenom = etprenom.getText().toString();

        ettel = (EditText) findViewById(R.id.et_tel);
        tel = ettel.getText().toString();

        etoccup = (EditText) findViewById(R.id.et_occ);
        occ = etoccup.getText().toString();

        if (!IsFieldEmpty(spass) && !IsFieldEmpty(smail) && !IsFieldEmpty(nom) && !IsFieldEmpty(prenom) && !IsFieldEmpty(tel) && !IsFieldEmpty(occ)) {

            if (hasRightLength(spass)) {

                if (checkEmail(smail)) {

                    /////check if user exists by email:

                    User user = new User();

                    user = user.CheckUserExistsByKey("email", "[{email: " + smail + ",pass: " + spass + "}]");


                    if (user.getId() != null && !user.getId().equals("")) {

                        Toast.makeText(getApplicationContext(), "email existe deja", Toast.LENGTH_SHORT).show();

                    } else {
                        ///////////add user:
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            converted_birthday = dateFormat.parse(dn);
                        } catch (ParseException e) {
                        }

                        String generated_id = String.valueOf(user.get_users_count() + 1);
                        user.addUser(generated_id, nom, prenom, converted_birthday, smail, spass, gender, occ, tel, "", img);
                        Intent i = new Intent(RegisterActivity.this, PrefActivity.class);
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


    public void addListenerOnSpinnerItemSelection() {
        s_etat = (Spinner) findViewById(R.id.spinner_status);
        s_etat.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        s_sexe = (Spinner) findViewById(R.id.spinner_gender);
        s_sexe.setOnItemSelectedListener(new CustomOnItemSelectedListener());


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