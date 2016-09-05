package trendy.coloc;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;

import trendy.coloc.entities.Property;
import trendy.coloc.tools.AnnonceTools;
import trendy.coloc.tools.Keys;

public class AnnonceSearchPrefsActivity extends AppCompatActivity {
    private final int ID_KEY_MARGIN = 1000;
    private final int ID_VALUE_MARGIN = 10000;
    private final int ID_DELETE_MARGIN = 20000;
    private final int ID_OPTIONLAYOUT_MARGIN = 30000;

    EditText prixMinET, prixMaxET;
    TextView dateStartET, dateEndET;
    ImageButton btnsearchconfirm, btnaddSearchCriteria;
    ArrayAdapter<String> prefAdapter, cityAdapter;
    AutoCompleteTextView villeAC;
    private LinearLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_dialog_layout);
        cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, AnnonceTools.villes);


        villeAC = (AutoCompleteTextView) findViewById(R.id.searchVilleTV);
        villeAC.setAdapter(cityAdapter);
        villeAC.setThreshold(1);

        rootLayout = (LinearLayout) findViewById(R.id.searchOptionalCritLLTab);

        dateStartET = (TextView) findViewById(R.id.searchStartdateTV);
        dateEndET = (TextView) findViewById(R.id.searchDateFinTV);
        rootLayout = (LinearLayout) findViewById(R.id.searchOptionalCritLLTab);
        dateEndET.setFocusable(false);
        dateStartET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateStartET.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), 9, 1);
                dialog.show();
            }
        });
        dateEndET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateEndET.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR) + 1, 6, 31);
                dialog.show();
            }
        });


        btnaddSearchCriteria = (ImageButton) findViewById(R.id.btnaddSearchCriteria);
        btnaddSearchCriteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDiologue();
            }
        });

        btnsearchconfirm = (ImageButton) findViewById(R.id.btnSearchannonceconfirmFilter);
        btnsearchconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save all prefs in prearrya into a jsonObject
                JSONObject crits = new JSONObject();

                try {
                    crits.put(Keys.VILLE, villeAC.getText().toString());
                    crits.put(Keys.ANNONCE_DATE_DEBUT, dateStartET.getText().toString());
                    crits.put(Keys.ANNONCE_DATE_FIN, dateEndET.getText().toString());
                    crits.put(Keys.PRIX_MIN, prixMinET.getText().toString());
                    crits.put(Keys.PRIX_MAX, prixMaxET.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (Property x : AnnonceTools.tempProps) {
                    try {
                        crits.put(x.getKey(), x.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //end of copy crits intojsonobj
                //clear the temp prefs
                AnnonceTools.tempProps.clear();

                AnnonceTools.searchPreferencesJsonString = crits.toString();
                finish();

            }
        });

    }


    private void addDiologue() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnnonceSearchPrefsActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();


        //create autocomplete adapter
        prefAdapter = new ArrayAdapter<String>(AnnonceSearchPrefsActivity.this,
                android.R.layout.simple_dropdown_item_1line, AnnonceTools.keySuggestions);


        View view = inflater.inflate(R.layout.input_dialog_preference, null);
        AutoCompleteTextView keyfield = (AutoCompleteTextView) view.findViewById((R.id.tag));
        keyfield.setAdapter(prefAdapter);
        keyfield.setThreshold(1);

        keyfield.setHint("critere ");
        alertDialog.setView(view);

			/* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Dialog inDialog = (Dialog) dialog;
                String ftag_s = "";
                ftag_s = ((AutoCompleteTextView) inDialog.findViewById((R.id.tag))).getText().toString();

                EditText fvalueET = (EditText) inDialog.findViewById(R.id.value);
                String fvalue_s = "";
                fvalue_s = fvalueET.getText().toString();

                addTab(ftag_s, fvalue_s, R.id.searchOptionalCritLLTab);

            }
        });
               /* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //  finish();
            }
        });
        alertDialog.show();

    }

    private void addTab(String key, String value, int rootLayoutId) {

        final LinearLayout optionsLayout = (LinearLayout) findViewById(rootLayoutId);
        LinearLayout tab = new LinearLayout(getApplicationContext());

        tab.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tab.setHorizontalGravity(LinearLayout.HORIZONTAL);
        tab.setWeightSum(10f);
        tab.setOrientation(LinearLayout.HORIZONTAL);
        int order = AnnonceTools.tempProps.size();
        Property editingPreference = new Property();
        Log.w("add tab", " after creating property");
        TextView labelKey = new AutoCompleteTextView(getApplicationContext());
        labelKey.setBackgroundColor(Color.argb(55, 255, 255, 255));
        labelKey.setText(key);
        labelKey.setTextSize(16f);
        labelKey.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parm.weight = 4;
        parm.width = 0;
        parm.setMargins(10, 10, 10, 10);
        labelKey.setLayoutParams(parm);
        int idLabel = ID_KEY_MARGIN + order;
        labelKey.setId(idLabel);

        editingPreference.setIdkey(idLabel);
        editingPreference.setKey(key);


        EditText editValue = new EditText(getApplicationContext());
        editValue.setTextColor(Color.WHITE);
        editValue.setBackgroundColor(Color.argb(55, 255, 255, 255));
        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parm.weight = 5;
        parm.width = 0;
        parm.setMargins(10, 10, 10, 10);
        editValue.setLayoutParams(parm);
        editValue.setText(value);
        editValue.setHint("Valeure");
        int idValueET = ID_VALUE_MARGIN + order;
        editValue.setId(idValueET);
        editValue.setTextSize(16f);
        editingPreference.setIdvalue(idValueET);
        editingPreference.setValue(value);
        Log.w("add tab", "created valueET with order " + order + "&& id  = " + idValueET);


        ImageButton removebtn = new ImageButton(getApplicationContext());

        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parm.weight = 1;
        parm.weight = 0;
        removebtn.setBackgroundResource(R.drawable.effacer);
        parm.setMargins(10, 10, 10, 10);
        removebtn.setLayoutParams(parm);
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnnonceSearchPrefsActivity.this, "btn remove order : " + v.getId(), Toast.LENGTH_SHORT).show();
                ((LinearLayout) rootLayout).removeViewAt(v.getId() - ID_DELETE_MARGIN);
                AnnonceTools.tempProps.remove(v.getId() - ID_DELETE_MARGIN);
            }
        });

        int idremove = ID_DELETE_MARGIN + order;
        removebtn.setId(idremove);
        editingPreference.setRemovebtn(idremove);
        Log.w("add tab", "created btn remove with order " + order + "&& idlabel  = " + idremove);


        tab.addView(labelKey);
        tab.addView(editValue);
        tab.addView(removebtn);
        Log.w("add tab", "all views added to tab");

        tab.setId(ID_OPTIONLAYOUT_MARGIN + order);
        editingPreference.setIdOptionLayout(ID_OPTIONLAYOUT_MARGIN + order);


        //add criteres to temportary insertedcritsArrays
        AnnonceTools.tempProps.add(editingPreference);
        optionsLayout.addView(tab);


    }

    private void fillSearchFrame() {
        String input = AnnonceTools.searchPreferencesJsonString;
        if (!input.isEmpty() && input != null) {
            try {
                JSONObject js = new JSONObject(input);

                //get data from save json sting
                prixMaxET.setText(js.get(Keys.PRIX_MAX).toString());
                prixMinET.setText(js.get(Keys.PRIX_MIN).toString());
                villeAC.setText(js.get(Keys.VILLE).toString());
                dateStartET.setText(js.get(Keys.ANNONCE_DATE_DEBUT).toString());
                dateEndET.setText(js.get(Keys.ANNONCE_DATE_FIN).toString());
                //remove fixed items from json

                js.remove(Keys.PRIX_MAX);
                js.remove(Keys.PRIX_MIN);
                js.remove(Keys.VILLE);
                js.remove(Keys.ANNONCE_DATE_DEBUT);
                js.remove(Keys.ANNONCE_DATE_FIN);
                //add all other crits to array


                Iterator<String> iter = js.keys();
                while (iter.hasNext()) {

                    try {
                        String key = iter.next();
                        String value = js.get(key).toString();
                        addTab(key, value, R.id.searchOptionalCritLLTab);

                    } catch (JSONException e) {
                        Toast.makeText(AnnonceSearchPrefsActivity.this, "Error parsing extra prefs", Toast.LENGTH_SHORT).show();
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


}

