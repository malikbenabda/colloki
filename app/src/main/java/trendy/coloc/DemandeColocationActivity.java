package trendy.coloc;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import trendy.coloc.entities.DemandeColocation;
import trendy.coloc.tools.Critera;
import trendy.coloc.tools.SaveSharedPreference;

public class DemandeColocationActivity extends AppCompatActivity {
    ArrayList<Critera> lc = new ArrayList<Critera>();
    Critera cr_custom;
    DemandeColocation dc;
    Button benvoyer;
    Spinner spville;
    boolean has_clicked_date = false;
    String userId = "";
    EditText et_nbr_chambres, et_nbr_colocs, et_pmax, et_pmin, tag, description, et_titre;

    RadioButton rb_ville, rb_rpart, rb_fille, rb_gars, rb_mixte;

    String ch_ville = "", ch_nbr_chbrs = "", ch_nbr_clocs = "", ch_pmin = "", ch_pmax = "", ch_va = "", ch_gf = "", d1 = "", d2 = "", tag_s = "", description_s = "", ch_titre = "";

    Date converted_d1, converted_d2;

    String ch_villa_apart = "", ch_fille_mecs = "";


    RadioGroup rbvb, rbfg;

    String ch_json_final = "";

    Date d1_converted, d2_converted;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private int year2, month2, day2;
    Date date_demande;
    TextView dateView, dateView2;

    Critera cr0, cr1, cr2, cr3, cr4, cr5, cr6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_colocation);

        benvoyer = (Button) findViewById(R.id.btndmd);


        spville = (Spinner) findViewById(R.id.spinner_vl);


        userId = SaveSharedPreference.getUserId(DemandeColocationActivity.this);

        Toast.makeText(getApplicationContext(), "id is: " + userId, Toast.LENGTH_SHORT).show();


        dateView = (TextView) findViewById(R.id.dateText1);
        dateView2 = (TextView) findViewById(R.id.dateText2);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        year2 = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        month2 = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        day2 = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        showDate2(year2, month2 + 1, day2);


        et_titre = (EditText) findViewById(R.id.et_titre);

        et_nbr_chambres = (EditText) findViewById(R.id.et_nbchambres);
        et_nbr_colocs = (EditText) findViewById(R.id.et_nbcolocs);
        et_pmax = (EditText) findViewById(R.id.et_pmax);
        et_pmin = (EditText) findViewById(R.id.et_pmin);

        rb_ville = (RadioButton) findViewById(R.id.villa_rb);
        rb_rpart = (RadioButton) findViewById(R.id.apart_rb);


        rb_fille = (RadioButton) findViewById(R.id.filles_rb);
        rb_gars = (RadioButton) findViewById(R.id.gars_rb);
        rb_mixte = (RadioButton) findViewById(R.id.mixte_rb);

        rbvb = (RadioGroup) findViewById(R.id.villapart);

        rbfg = (RadioGroup) findViewById(R.id.gfm);


        benvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ch_titre = et_titre.getText().toString();
                ch_ville = spville.getSelectedItem().toString();
                ch_nbr_chbrs = et_nbr_chambres.getText().toString();
                ch_nbr_clocs = et_nbr_colocs.getText().toString();
                ch_pmin = et_pmin.getText().toString();
                ch_pmax = et_pmax.getText().toString();
                d1 = dateView.getText().toString();
                d2 = dateView2.getText().toString();


                Toast.makeText(getApplicationContext(), "date1:" + d1, Toast.LENGTH_SHORT).show();


//////////////////////////////////get villa or apart:
                int radioButtonID = rbvb.getCheckedRadioButtonId();
                View radioButton = rbvb.findViewById(radioButtonID);
                int idx = rbvb.indexOfChild(radioButton);

                RadioButton r = (RadioButton) rbvb.getChildAt(idx);
                ch_va = r.getText().toString();
///////////////////////////////////get girls or guys or mixtes:
                int radioButtonID1 = rbfg.getCheckedRadioButtonId();
                View radioButton1 = rbfg.findViewById(radioButtonID1);
                int idx1 = rbfg.indexOfChild(radioButton1);

                RadioButton r1 = (RadioButton) rbfg.getChildAt(idx1);
                ch_gf = r1.getText().toString();

////////////////////////////input check:

                if (!IsFieldEmpty(ch_ville) && !IsFieldEmpty(ch_nbr_chbrs) && !IsFieldEmpty(ch_nbr_clocs) &&
                        !IsFieldEmpty(ch_pmax) && !IsFieldEmpty(ch_pmin) && !IsFieldEmpty(ch_titre)) {

//////////////////////////check room number
                    if (HomehasRightRoomNumber(Integer.parseInt(ch_nbr_chbrs))) {


                    } else {

                        Toast.makeText(getApplicationContext(), "Le nombre des chambres doit etre compris entre 1 et 5", Toast.LENGTH_SHORT).show();


                    }
//////////////////////check colocs number:

                    if (ColocsNumber(Integer.parseInt(ch_nbr_clocs))) {


                    } else {

                        Toast.makeText(getApplicationContext(), "Le nombre des colocs ne peut pas exceder 5", Toast.LENGTH_SHORT).show();


                    }

                    if (!priceCheck(Float.valueOf(ch_pmax))) {
                        Toast.makeText(getApplicationContext(), "le prix max  doit etre compris entre 150dt et 1500 dt !", Toast.LENGTH_SHORT).show();

                    }


                    if (!priceCheck(Float.valueOf(ch_pmin))) {
                        Toast.makeText(getApplicationContext(), "le prix min  doit etre compris entre 150dt et 1500 dt !", Toast.LENGTH_SHORT).show();

                    }


                    if (!MinPriceInferiorMaxPrice(Float.valueOf(ch_pmin), Float.valueOf(ch_pmax))) {
                        Toast.makeText(getApplicationContext(), "le prix min  doit etre inferieur a prix max !", Toast.LENGTH_SHORT).show();

                    }

                    ///////////////////////////if everythin is ok


                    if (!has_clicked_date) {

                        Toast.makeText(getApplicationContext(), "choisissez une date!", Toast.LENGTH_SHORT).show();

                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    try {

                        converted_d1 = dateFormat.parse(d1);
                        converted_d1 = dateFormat.parse(d2);

                    } catch (ParseException e) {
                    }

                    if (!date1inferiordate1(converted_d1, converted_d2)) {

                        Toast.makeText(getApplicationContext(), "1ere doit etre inferieure a la deuxieme date !", Toast.LENGTH_SHORT).show();

                    }


                    if (HomehasRightRoomNumber(Integer.parseInt(ch_nbr_chbrs)) && ColocsNumber(Integer.parseInt(ch_nbr_clocs)) &&
                            priceCheck(Float.valueOf(ch_pmax)) && priceCheck(Float.valueOf(ch_pmin))
                            && MinPriceInferiorMaxPrice(Float.valueOf(ch_pmin), Float.valueOf(ch_pmax)) &&
                            has_clicked_date &&

                            date1inferiordate1(converted_d1, converted_d2)

                            ) {
////////////////////////////////rechek if strings are not empty then create a creteria and add it to list


                        if (!ch_ville.equals("")) {
                            cr0 = CerateCreteria("ville", ch_ville);
                            lc.add(cr0);
                        }


                        if (!ch_nbr_chbrs.equals("")) {
                            cr1 = CerateCreteria("nombreChbr", ch_nbr_chbrs);
                            lc.add(cr1);
                        }

                        if (!ch_nbr_clocs.equals(""))

                        {
                            cr2 = CerateCreteria("nombreclc", ch_nbr_clocs);
                            lc.add(cr2);
                        }

                        if (!ch_pmin.equals(""))

                        {
                            cr3 = CerateCreteria("pmin", ch_pmin);
                            lc.add(cr3);
                        }

                        if (!ch_pmax.equals("")) {
                            cr4 = CerateCreteria("pmax", ch_pmax);
                            lc.add(cr4);
                        }

                        if (!ch_villa_apart.equals("")) {
                            cr5 = CerateCreteria("villa_ou_apart", ch_villa_apart);
                            lc.add(cr5);
                        }

                        if (!ch_fille_mecs.equals("")) {
                            cr6 = CerateCreteria("fille_mecs", ch_fille_mecs);
                            lc.add(cr6);
                        }

/////////////////////build json string:
                        if (lc.size() > 0 && lc != null) {
                            for (int i = 0; i < lc.size(); i++) {

                                Critera cr = lc.get(i);

                                ch_json_final = ch_json_final + cr.buildJson(cr.getTag(), cr.getdesc());

                            }

                        }

//////////////////////////Convert dates:

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        try {
                            d1_converted = df.parse(d1);
                            d2_converted = df.parse(d2);


                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(c.getTime());
                            date_demande = df.parse(formattedDate);

                        } catch (ParseException e) {
                        }


                        ///////////////////call async task:

                        DemandeColocation dc = new DemandeColocation();
                        dc = dc.addDdmd(userId, ch_titre, ch_json_final, d1_converted, d2_converted, date_demande);


                        if (dc != null)

                        {

                            // Toast.makeText(getApplicationContext(), ch_json_final, Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(), "Votre demmande a ete envoye!", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getApplicationContext(), "Une erreur s'est produite ressayer!", Toast.LENGTH_SHORT).show();


                        }


                    }
/////////////////////////////////////
                    else {

                        Toast.makeText(getApplicationContext(), "Une erreur s'est produite ressayer!", Toast.LENGTH_SHORT).show();

                    }


//////////////////////////////////////prevent multiple clicks on submit button
                    benvoyer.setEnabled(false);

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            DemandeColocationActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    benvoyer.setEnabled(true);

                                }
                            });
                        }
                    }).start();


                }
//////////////////////////////////////////////les champs sont vides:
                else {

                    Toast.makeText(getApplicationContext(), "verifiez que tous les champs sont remplis!", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    ////////////////////////////////////DATE PICKER FUNCTIONS:
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "1ere date", Toast.LENGTH_SHORT)
                .show();
        has_clicked_date = true;
    }

    @SuppressWarnings("deprecation")
    public void setDate2(View view) {
        showDialog(991);
        Toast.makeText(getApplicationContext(), "2nd date", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        if (id == 991) {
            return new DatePickerDialog(this, myDateListener2, year2, month2, day2);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate2(arg1, arg2 + 1, arg3);
        }
    };

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

        Toast.makeText(getApplicationContext(), dateView.getText().toString(), Toast.LENGTH_SHORT).show();

    }

    private void showDate2(int year2, int month2, int day2) {
        dateView2.setText(new StringBuilder().append(day2).append("/")
                .append(month2).append("/").append(year2));
        dateView2.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), dateView2.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.m.main, menu);
        return true;
    }

////////////////////////////////////////////////////////////////


    public void CustomCre(View v) {

        DialogOtherCrs();


    }

    public void DialogOtherCrs() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DemandeColocationActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        alertDialog.setView(inflater.inflate(R.layout.dialog_custom_cre, null));

/* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dialog.cancel(); // Your custom code


                Dialog inDialog = (Dialog) dialog;
                tag = (EditText) inDialog.findViewById(R.id.tag_dmd);
                tag_s = tag.getText().toString();


                description = (EditText) inDialog.findViewById(R.id.description_dmd);
                description_s = description.getText().toString();


                if (!tag_s.equals("") && !description_s.equals("")) {
                    cr_custom = CerateCreteria(tag_s, description_s);

                    lc.add(cr_custom);
                }


            }
        });


/* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //finish(); // Your custom code
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
///////////////////////////function that puts a criteria together:

    public Critera CerateCreteria(String tag, String desc) {

        Critera cr = new Critera();

        cr.setTag(tag);
        cr.setdesc(desc);


        return cr;

    }
/////////////////////////is field empty?

    public boolean IsFieldEmpty(String s) {

        return s.matches("");


    }

    public boolean HomehasRightRoomNumber(int p) {

        boolean test = true;
        if (p <= 0 || p > 5) {
            test = false;

        }

        return test;


    }

    public boolean ColocsNumber(int p) {

        boolean test = true;
        if (p < 0 || p > 5) {
            test = false;

        }

        return test;


    }

    public boolean priceCheck(Float p) {
        boolean test = true;

        if (p > 1500) {
            test = false;

        }

        if (p < 250) {
            test = false;

        }

        return test;


    }


    public boolean titleHasRightLength() {
        return false;

    }


    public boolean MinPriceInferiorMaxPrice(Float p1, Float p2) {

        return p1 < p2;


    }

    public boolean date1inferiordate1(Date d1, Date d2) {

        return d1.before(d2);

    }


}

