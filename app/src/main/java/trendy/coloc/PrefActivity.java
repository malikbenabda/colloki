package trendy.coloc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import trendy.coloc.tools.Critera;

public class PrefActivity extends AppCompatActivity {

    RadioButton rban, rbnan, rbs, rbnnn;
    RadioGroup rgs1, rgs2;
    Button bother_prefs;
    EditText tag, description;
    String pref_smoker = "";
    String pref_anim = "";
    String tag_s = "", description_s = "";
    ArrayList<Critera> pref_list;
    String ch_custom = "";
    Critera cr0, cr1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        bother_prefs = (Button) findViewById(R.id.autre_prfs);
        pref_list = new ArrayList<Critera>();

        rgs1 = (RadioGroup) findViewById(R.id.prefs1);
        rgs2 = (RadioGroup) findViewById(R.id.prefs2);

        rban = (RadioButton) findViewById(R.id.an);
        rbnan = (RadioButton) findViewById(R.id.nan);

        rbs = (RadioButton) findViewById(R.id.fum);
        rbnnn = (RadioButton) findViewById(R.id.nfum);


////////////////////////get if user likes animals:

        int radioButtonID = rgs1.getCheckedRadioButtonId();
        View radioButton = rgs1.findViewById(radioButtonID);
        int idx = rgs1.indexOfChild(radioButton);

        RadioButton r = (RadioButton) rgs1.getChildAt(idx);
        pref_anim = r.getText().toString();


//////////////////////get if user smoker


        int radioButtonID2 = rgs2.getCheckedRadioButtonId();
        View radioButton2 = rgs2.findViewById(radioButtonID2);
        int idx2 = rgs2.indexOfChild(radioButton2);

        RadioButton r2 = (RadioButton) rgs2.getChildAt(idx2);
        pref_anim = r2.getText().toString();
//////////////////////get prefs:
        if (!pref_anim.equals("")) {

            cr0 = new Critera();
            cr0.setTag("animal_views");
            cr0.setdesc(pref_anim);
            pref_list.add(cr0);
        }

        if (!pref_smoker.equals("")) {

            cr1 = new Critera();
            cr1.setTag("smoker_not");
            cr1.setdesc(pref_smoker);
            pref_list.add(cr1);
        }


    }

    /////////////////function called every time user wants to add a new pref
    public void otherPrefs(View v) {


        DialogOtherPrefs();

        Toast.makeText(getApplicationContext(), "inside btn ", Toast.LENGTH_SHORT).show();


    }

    public void DialogOtherPrefs() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PrefActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        alertDialog.setView(inflater.inflate(R.layout.dialog_other_prefs, null));

/* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dialog.cancel(); // Your custom code


                Dialog inDialog = (Dialog) dialog;
                tag = (EditText) inDialog.findViewById(R.id.tag);
                tag_s = tag.getText().toString();


                description = (EditText) inDialog.findViewById(R.id.description);
                description_s = description.getText().toString();

                ch_custom = ConcatenatePrefs(tag_s, description_s);
                /////if prefrences is set:
                if (!ch_custom.equals("")) {

                    Critera cr = new Critera();
                    cr.setTag(tag_s);
                    cr.setdesc(description_s);
                    pref_list.add(cr);
                }


            }
        });


/* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish(); // Your custom code
            }
        });
        alertDialog.show();
    }

    public String getAllPrefs(String ch1, String ch2, String ch3) {

        String result = "";

        result = "{fumeur:" + ch1 + "animaux:" + ch2 + "}";


        return result;

    }


    public String ConcatenatePrefs(String ch1, String ch2) {

        String result = "";
        result = "{" + ch1 + ":" + ch2 + "}";

        return result;


    }


}
