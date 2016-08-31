package trendy.coloc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import trendy.coloc.entities.DemandeColocation;
import trendy.coloc.tools.Critera;

public class DemandeColocationSearchActivity extends AppCompatActivity {

    Context context;
    TextView tv_crs;
    ArrayList<String> la = new ArrayList<String>();
    EditText et_nbr_chambres, et_nbr_colocs, et_prix_min, et_prix_max;
    DemandeColocation dc;
    String ch_nbr_chambres = "", ch_nbr_colocs = "", ch_pmin = "", ch_pmax = "", ch_tag_custom = "", ch_desc_custom = "";
    ArrayList<Critera> lc;
    EditText et;
    TextView tvi;
    RadioButton rb_ville, rb_rpart;
    RadioGroup rbvb;
    String ch_apart = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_colocation_search);
        context = DemandeColocationSearchActivity.this;
        tv_crs = (TextView) findViewById(R.id.tv_criteres);

        et_nbr_chambres = (EditText) findViewById(R.id.et_nbchambres);
        et_nbr_colocs = (EditText) findViewById(R.id.et_nbcolocs);
        et_prix_max = (EditText) findViewById(R.id.et_pmax);
        et_prix_min = (EditText) findViewById(R.id.et_pmin);
        lc = new ArrayList<Critera>();


        rb_ville = (RadioButton) findViewById(R.id.villa_rb_search);
        rb_rpart = (RadioButton) findViewById(R.id.apart_rb_search);

        rbvb = (RadioGroup) findViewById(R.id.villapartSearch);


    }


    public void ShowOtherCrs(View v) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setIcon(R.drawable.redp);
        builderSingle.setTitle("Select un autre critere:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Hardik");
        arrayAdapter.add("Archit");
        arrayAdapter.add("Jignesh");
        arrayAdapter.add("Umang");
        arrayAdapter.add("Gatti");

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                context);

                        // TextView tv = new TextView(context);
                        final LinearLayout ln = (LinearLayout) findViewById(R.id.linearLayout);

                        builderInner.setMessage(strName + " pour supprimer cet element cliquer sur lui");
                        la.add(strName);
                        builderInner.setTitle("vous avez choisi");
                        et = new EditText(context);
                        et.setEnabled(true);


                        //ln.addView(tv);
                        // tv_crs.setVisibility(View.VISIBLE);

                        tvi = new TextView(context);
                        tvi.setText(la.get(la.size() - 1));
                        tvi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ln.removeView(tvi);
                                ln.removeView(et);
                            }
                        });


                        ln.addView(tvi);
                        ln.addView(et);

                        //}
                        //}

                        builderInner.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();


    }

    public void SubmitSearchReq(View v) {

        dc = new DemandeColocation();
        ch_nbr_chambres = et_nbr_chambres.getText().toString();


        int radioButtonID = rbvb.getCheckedRadioButtonId();
        View radioButton = rbvb.findViewById(radioButtonID);
        int idx = rbvb.indexOfChild(radioButton);

        RadioButton r = (RadioButton) rbvb.getChildAt(idx);
        ch_apart = r.getText().toString();

        Toast.makeText(getApplicationContext(), "des: " + ch_apart, Toast.LENGTH_SHORT).show();

        if (!IsFieldEmpty(ch_apart)) {

            Critera cr = new Critera();
            cr.setTag("apart_villa");
            cr.setdesc(ch_apart);

            if (!Critera_exists(lc, cr))

            {
                lc.add(cr);
            }

        }


        if (!IsFieldEmpty(ch_nbr_chambres)) {

            if (!HomehasRightRoomNumber(Integer.parseInt(ch_nbr_chambres)))

            {

                Toast.makeText(getApplicationContext(), "Le nombre de chmabres ne peut pas etre superieur a 5: ", Toast.LENGTH_SHORT).show();


            } else


            {
                Critera cr = new Critera();
                cr.setTag("nbr_chambre");
                cr.setdesc(ch_nbr_chambres);
                if (!Critera_exists(lc, cr))

                {
                    lc.add(cr);
                }

            }
        }

        ch_nbr_colocs = et_nbr_colocs.getText().toString();

        if (!IsFieldEmpty(ch_nbr_colocs)) {


            if (!ColocsNumber(Integer.parseInt(ch_nbr_colocs)))

            {

                Toast.makeText(getApplicationContext(), "Le nombre de colocataires ne peut pas etre superieur a 5: ", Toast.LENGTH_SHORT).show();


            } else

            {
                Critera cr = new Critera();
                cr.setTag("nbr_colocs");
                cr.setdesc(ch_nbr_colocs);
                if (!Critera_exists(lc, cr))

                {
                    lc.add(cr);
                }
            }

        }


        ch_pmin = et_prix_min.getText().toString();

        if (!IsFieldEmpty(ch_pmin)) {


            if (!priceCheck(Float.parseFloat(ch_pmin)))

            {

                Toast.makeText(getApplicationContext(), "Le prix doit etre entre 250 et 1500 dt: ", Toast.LENGTH_SHORT).show();


            } else {

                if (et != null) {
                    ch_pmax = et_prix_max.getText().toString();

                    if (MinPriceInferiorMaxPrice(Float.parseFloat(ch_pmin), Float.parseFloat(ch_pmax))) {
                        Critera cr = new Critera();
                        cr.setTag("prix_min");
                        cr.setdesc(ch_pmin);
                        if (!Critera_exists(lc, cr))

                        {
                            lc.add(cr);
                        }
                    }

                }

            }

        }


        ch_pmax = et_prix_max.getText().toString();

        if (!IsFieldEmpty(ch_pmax)) {

            if (!priceCheck(Float.parseFloat(ch_pmax)))

            {

                Toast.makeText(getApplicationContext(), "Le prix doit etre entre 250 et 1500 dt: ", Toast.LENGTH_SHORT).show();


            } else {

                if (MinPriceInferiorMaxPrice(Float.parseFloat(ch_pmin), Float.parseFloat(ch_pmax))) {

                    Critera cr = new Critera();
                    cr.setTag("prix_max");
                    cr.setdesc(ch_pmax);
                    if (!Critera_exists(lc, cr))

                    {
                        lc.add(cr);
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Le prix min doit etre inferieur au prix max ", Toast.LENGTH_SHORT).show();

                }
            }
        }


        if (et != null) {

            ch_desc_custom = et.getText().toString();
            ch_tag_custom = tvi.getText().toString();


            if (!IsFieldEmpty(ch_desc_custom)) {

                Critera cr = new Critera();
                cr.setTag(ch_tag_custom);
                cr.setdesc(ch_desc_custom);

                if (!Critera_exists(lc, cr))

                {
                    lc.add(cr);
                }

            }
        }
////////////////////////////////


        // Toast.makeText(getApplicationContext(), "det: "+ et.getText().toString(), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < lc.size(); i++) {

            Toast.makeText(getApplicationContext(), "des: " + lc.get(i).getdesc(), Toast.LENGTH_SHORT).show();

        }


//////////////////////////////////////transform them into json:


        String ch_sjon_result = Convert_to_json(lc);

        Toast.makeText(getApplicationContext(), "resultat json: " + ch_sjon_result, Toast.LENGTH_SHORT).show();


    }

    public String Convert_to_json(ArrayList<Critera> la) {


        //[{"plat_id":"7","nom_plat":"hhj","ing1":"ing1","ing2":"ing2","ing3":"ing3","op1":"supp1","op2":"supp2","op3":"supp3","qte":"1","prix":"0"},

        String ch = "";
        String ch_res = "";

        for (int i = 0; i < la.size(); i++)

        {
            ch = ch + la.get(i).getTag() + ":" + la.get(i).getdesc() + " ,";
        }

        ch_res = "[{" + ch + "}]";

        return ch_res;

    }


    public boolean Critera_exists(ArrayList<Critera> la, Critera cr) {

        boolean test = false;

        for (int i = 0; i < la.size(); i++) {
            if (cr.getdesc().equals(la.get(i).getdesc())
                    && cr.getTag().equals(la.get(i).getTag())) {

                test = true;

            }

        }

        return test;


    }


    public boolean IsFieldEmpty(String s) {

        return s.matches("");


    }


    public boolean MinPriceInferiorMaxPrice(Float p1, Float p2) {

        return p1 < p2;


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


}
