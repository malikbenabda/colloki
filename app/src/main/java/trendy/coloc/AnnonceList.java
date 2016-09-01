package trendy.coloc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import trendy.coloc.adapters.MyCustomAdapter;
import trendy.coloc.entities.Annonce;
import trendy.coloc.entities.Property;
import trendy.coloc.tools.AnnonceTools;
import trendy.coloc.tools.ConverterTools;

public class AnnonceList extends AppCompatActivity {
    private final int ID_KEY_MARGIN = 1000;
    private final int ID_VALUE_MARGIN = 10000;
    private final int ID_DELETE_MARGIN = 20000;
    private final int ID_OPTIONLAYOUT_MARGIN = 30000;
    private MyCustomAdapter listViewAnnonceSearchAdapter;
    private ArrayList<Annonce> annonces, annoncesFiltered;
    private JSONObject preferences, z, x, y;
    EditText ftagET, fvalueET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_list);
        ListView annoncesLV = (ListView) findViewById(R.id.annonceListView);
        AnnonceTools.tempProps.clear();
        //Annonces DB fill
        fillupAnnoncesTest();

        //         preferences add in them all the static search criterias also
        //    annoncesFiltered = Annonce.getAllByKeys(preferences.toString(),AnnonceList.this);
        annoncesFiltered = Annonce.selectByProperties(annonces, ConverterTools.JSONstringToMap(preferences.toString()));

        listViewAnnonceSearchAdapter = new MyCustomAdapter(this, R.layout.row_annonce, annoncesFiltered);
        annoncesLV.setAdapter(listViewAnnonceSearchAdapter);
    }


    private void addDiologue() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnnonceList.this);
        LayoutInflater inflater = this.getLayoutInflater();


        //create autocomplete adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, AnnonceTools.keySuggestions);


        View view = inflater.inflate(R.layout.input_dialog_preference, null);
        AutoCompleteTextView labelKey = (AutoCompleteTextView) view.findViewById((R.id.tag));
        labelKey.setAdapter(adapter);
        labelKey.setThreshold(1);

        labelKey.setHint("inserer un tag ");
        alertDialog.setView(view);

			/* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Dialog inDialog = (Dialog) dialog;
                ftagET = (EditText) inDialog.findViewById(R.id.tag);
                String ftag_s = ftagET.getText().toString();

                fvalueET = (EditText) inDialog.findViewById(R.id.value);
                String fvalue_s = "";
                fvalue_s = fvalueET.getText().toString();
                CheckBox checkBox = (CheckBox) inDialog.findViewById(R.id.checher);
                if (checkBox.isChecked()) fvalue_s += "1";
                else fvalue_s += "0";

                addTab(ftag_s, fvalue_s, R.id.optionsLayout);

            }
        });
               /* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();

    }

    private void addTab(String key, String value, int rootLayoutId) {

        final LinearLayout optionsLayout = (LinearLayout) findViewById(rootLayoutId);
        LinearLayout tab = new LinearLayout(getApplicationContext());
        tab.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        tab.setOrientation(LinearLayout.HORIZONTAL);
        int order = AnnonceTools.tempProps.size();
        Property p = new Property();

        TextView labelKey = new TextView(getApplicationContext());
        labelKey.setText(key);
        labelKey.setTextColor(Color.BLACK);
        labelKey.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        int idLabel = ID_KEY_MARGIN + order;
        labelKey.setId(idLabel);
        p.setIdkey(idLabel);


        EditText editValue = new EditText(getApplicationContext());
        editValue.setTextColor(Color.BLUE);
        LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parm.setMargins(25, 5, 25, 5);
        editValue.setLayoutParams(parm);
        editValue.setText(value);
        int idValueET = ID_VALUE_MARGIN + order;
        editValue.setId(idValueET);
        p.setIdvalue(idValueET);


        ImageButton removebtn = new ImageButton(getApplicationContext());

        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parm.height = 100;
        parm.width = 150;
        parm.setMargins(50, 25, 50, 25);


        removebtn.setBackgroundResource(R.drawable.effacer);
        removebtn.setLayoutParams(parm);
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change the 1 to id of option panel
                ((LinearLayout) findViewById(R.id.optionsLayout)).removeViewAt(v.getId() - ID_DELETE_MARGIN);
                AnnonceTools.tempProps.remove(v.getId() - ID_DELETE_MARGIN);
            }
        });

        int idremove = ID_DELETE_MARGIN + order;
        removebtn.setId(idremove);
        p.setRemovebtn(idremove);

        AnnonceTools.tempProps.add(p);

        tab.addView(removebtn);
        tab.addView(labelKey);
        tab.addView(editValue);

        tab.setId(ID_OPTIONLAYOUT_MARGIN + order);
        p.setIdOptionLayout(ID_OPTIONLAYOUT_MARGIN + order);
        optionsLayout.addView(tab);

    }


    private void fillupAnnoncesTest() {
        annonces = new ArrayList<Annonce>();

        preferences = null;
        x = null;
        y = null;
        z = null;

        try {
            x = new JSONObject().put("chambres", 5).put("description", "dess").put("wifi", "yes");
            y = new JSONObject().put("chambres", 8).put("description", "dess").put("wifi", "yes");
            z = new JSONObject().put("chambres", 5).put("description", "aaa").put("wifi", "no");

            //preferences is the chosen criteria for search
            preferences = new JSONObject().put("chambres", 5).put("wifi", "no");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Annonce a = new Annonce("titre", "sfax", 500f, true, new Date(), new Date(), new Date(), x.toString(), "1");
        Annonce b = new Annonce("7ouma", "nabeul", 250f, true, new Date(), new Date(), new Date(), y.toString(), "1");
        Annonce c = new Annonce("9antawi", "sousse", 888f, true, new Date(), new Date(), new Date(), z.toString(), "1");
        annonces.add(a);

        annonces.add(b);
        annonces.add(a);

        annonces.add(b);
        annonces.add(a);

        annonces.add(b);
        annonces.add(a);

        annonces.add(b);
        annonces.add(c);
        annonces.add(b);
        annonces.add(c);
        annonces.add(b);
        annonces.add(c);

    }

}
