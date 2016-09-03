package trendy.coloc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import trendy.coloc.adapters.MyCustomAdapter;
import trendy.coloc.entities.Annonce;
import trendy.coloc.entities.Property;
import trendy.coloc.tools.AnnonceTools;

public class AnnonceList extends AppCompatActivity {
    private final int ID_KEY_MARGIN = 1000;
    private final int ID_VALUE_MARGIN = 10000;
    private final int ID_DELETE_MARGIN = 20000;
    private final int ID_OPTIONLAYOUT_MARGIN = 30000;
    private MyCustomAdapter listViewAnnonceSearchAdapter;
    private ArrayList<Annonce> annonces, annoncesFiltered;
    private JSONObject preferences, z, x, y;
    EditText ftagET, fvalueET;
    ImageButton btnprefs, btnaddSearchCriteria;
    ArrayAdapter<String> prefAdapter, cityAdapter;
    AutoCompleteTextView villeAC;
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_list);
        cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, AnnonceTools.villes);
        prefAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, AnnonceTools.keySuggestions);

        ListView annoncesLV = (ListView) findViewById(R.id.annonceListView);
        AnnonceTools.tempProps = new ArrayList<Property>();

        //Annonces DB fill
        fillupAnnoncesTest();

        //         preferences add in them all the static search criterias also
        //    annoncesFiltered = Annonce.getAllByKeys(preferences.toString(),AnnonceList.this);
//        annoncesFiltered = Annonce.selectByProperties(annonces, ConverterTools.JSONstringToMap(preferences.toString()));
        // annoncesFiltered = Annonce.selectByProperties(annonces, ConverterTools.JSONstringToMap(""));

        btnprefs = (ImageButton) findViewById(R.id.searchAnnonceListPrefs);

        btnprefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiologue();


            }
        });

        listViewAnnonceSearchAdapter = new MyCustomAdapter(this, R.layout.row_annonce, annonces);
        annoncesLV.setAdapter(listViewAnnonceSearchAdapter);
    }


    private void openDiologue() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnnonceList.this);
        LayoutInflater inflater = this.getLayoutInflater();

        //set dialog layout
        View searchDialogLayout = inflater.inflate(R.layout.search_dialog_layout, null);
        villeAC = (AutoCompleteTextView) searchDialogLayout.findViewById(R.id.searchVilleTV);
        villeAC.setAdapter(cityAdapter);
        villeAC.setThreshold(0);
        btnaddSearchCriteria = (ImageButton) searchDialogLayout.findViewById(R.id.btnaddSearchCriteria);
        rootLayout = (LinearLayout) searchDialogLayout.findViewById(R.id.searchOptionalCritLLTab);
        btnaddSearchCriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add 2 tabs
                addTab("", "", rootLayout);
                Toast.makeText(AnnonceList.this, "pref add", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setView(searchDialogLayout);


        /*
        Configure dialog
         */
            /* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // save all prefs in tempprefs
                //send DB request for a filtrerd annonces lits with theses prefs
                //result annoncelist is set to filtered annonce array
                // change adapter data to new annonce filtered  array
                //addapter . set notification of datat change

            }
        });
               /* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });


        /**
         * en of dialog configure
         */


        alertDialog.show();

    }

    private void addTab(String key, String value, View rootLayout) {

        final LinearLayout optionsLayout = (LinearLayout) rootLayout;
        LinearLayout tab = new LinearLayout(getApplicationContext());


        tab.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tab.setWeightSum(10f);
        tab.setOrientation(LinearLayout.HORIZONTAL);
        int order = AnnonceTools.tempProps.size();
        Property p = new Property();
        Log.w("add tab", " after creating property");
        AutoCompleteTextView labelKey = new AutoCompleteTextView(getApplicationContext());
        labelKey.setBackgroundColor(Color.argb(55, 255, 255, 255));
        labelKey.setAdapter(prefAdapter);
        labelKey.setThreshold(1);
        labelKey.setText(key);
        labelKey.setHint("Critere");
        labelKey.setTextSize(16f);
        labelKey.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parm.weight = 4;
        parm.width = 0;
        labelKey.setLayoutParams(parm);
        int idLabel = ID_KEY_MARGIN + order;
        labelKey.setId(idLabel);

        Log.w("add tab", "created label with order " + order + "&& idlabel  = " + idLabel);
        p.setIdkey(idLabel);


        EditText editValue = new EditText(getApplicationContext());
        editValue.setTextColor(Color.WHITE);
        editValue.setBackgroundColor(Color.argb(55, 255, 255, 255));
        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parm.weight = 4;
        parm.width = 0;
        editValue.setLayoutParams(parm);
        editValue.setText(value);
        editValue.setHint("Valeure de critere");
        int idValueET = ID_VALUE_MARGIN + order;
        editValue.setId(idValueET);
        editValue.setTextSize(16f);
        p.setIdvalue(idValueET);
        Log.w("add tab", "created valueET with order " + order + "&& id  = " + idValueET);


        ImageButton removebtn = new ImageButton(getApplicationContext());

        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        removebtn.setBackgroundResource(R.drawable.effacer);
        removebtn.setLayoutParams(parm);
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsLayout.removeViewAt(v.getId() - ID_DELETE_MARGIN);
                AnnonceTools.tempProps.remove(v.getId() - ID_DELETE_MARGIN);
            }
        });

        int idremove = ID_DELETE_MARGIN + order;
        removebtn.setId(idremove);
        p.setRemovebtn(idremove);
        Log.w("add tab", "created btn remove with order " + order + "&& idlabel  = " + idremove);

        AnnonceTools.tempProps.add(p);

        tab.addView(removebtn);
        tab.addView(labelKey);
        tab.addView(editValue);
        Log.w("add tab", "all views added to tab");

        tab.setId(ID_OPTIONLAYOUT_MARGIN + order);
        p.setIdOptionLayout(ID_OPTIONLAYOUT_MARGIN + order);
        optionsLayout.addView(tab);

        Log.w("add tab", "tab views added to option pane");
    }


    private void fillupAnnoncesTest() {
        annonces = new ArrayList<Annonce>();

        preferences = null;
        x = null;
        y = null;
        z = null;

        try {
            x = new JSONObject().put("chambres", 5).put("description", "dess").put("wifi", "yes");
            //  y = new JSONObject().put("chambres", 8).put("description", "dess").put("wifi", "yes");
            //   z = new JSONObject().put("chambres", 5).put("description", "aaa").put("wifi", "no");

            //preferences is the chosen criteria for search
            preferences = new JSONObject().put("chambres", 5).put("wifi", "no");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Annonce a = new Annonce("titre", "sfax", 500f, true, new Date(), new Date(), new Date(), x.toString(), "1");
        // Annonce b = new Annonce("7ouma", "nabeul", 250f, true, new Date(), new Date(), new Date(), y.toString(), "1");
        //  Annonce c = new Annonce("9antawi", "sousse", 888f, true, new Date(), new Date(), new Date(), z.toString(), "1");
        annonces.add(a);

        //  annonces.add(c);
        //   annonces.add(b);


    }

}
