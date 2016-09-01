package trendy.coloc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import trendy.coloc.entities.Annonce;
import trendy.coloc.entities.Property;
import trendy.coloc.tools.AnnonceTools;
import trendy.coloc.tools.ConverterTools;
import trendy.coloc.tools.SaveSharedPreference;

public class AnnonceAjout extends Activity {
    Button add, send;
    private final int ID_KEY_MARGIN = 1000;
    private final int ID_VALUE_MARGIN = 10000;
    private final int ID_DELETE_MARGIN = 20000;
    private final int ID_OPTIONLAYOUT_MARGIN = 30000;

    EditText titreET, descriptionET, prixET, chambresET, dateStartET, dateEndET, ftagET, fvalueET;
    Spinner ville;
    JSONObject options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ajout_annonce);
        TextView pt = (TextView) findViewById(R.id.pageTitle);
        pt.setText("Creation d'annonce");

        AnnonceTools.tempProps = new ArrayList<Property>();

        titreET = (EditText) findViewById(R.id.titreET);
        prixET = (EditText) findViewById(R.id.priceET);
        chambresET = (EditText) findViewById(R.id.chambresET);
        descriptionET = (EditText) findViewById(R.id.descriptionET);
        dateStartET = (EditText) findViewById(R.id.dateStartET);
        dateEndET = (EditText) findViewById(R.id.dateEndET);
        ville = (Spinner) findViewById(R.id.villeSpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AnnonceTools.villes);
        //selected item will look like a spinner set from XML
        ville.setAdapter(spinnerArrayAdapter);
        dateStartET.setFocusable(false);

        dateEndET.setFocusable(false);
        dateStartET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnnonceAjout.this, "HELLO", Toast.LENGTH_SHORT).show();
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
        add = (Button) findViewById(R.id.btnAddCriterias);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDiologue();
            }
        });
        send = (Button) findViewById(R.id.sendbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Annonce annonce = new Annonce();
                String titre = titreET.getText().toString();
                float prix = Float.parseFloat(prixET.getText().toString());
                int nbrchambre = Integer.parseInt(chambresET.getText().toString());
                String dateStart_s = dateStartET.getText().toString();
                String dateEnd_s = dateEndET.getText().toString();


                String msg = "Veuillez saisir ";

                if (titre.isEmpty()) {
                    Toast.makeText(AnnonceAjout.this, msg + "le Titre d'annonce", Toast.LENGTH_SHORT).show();
                } else if (prix < 1f) {
                    Toast.makeText(AnnonceAjout.this, msg + "le Prix de Loyer", Toast.LENGTH_SHORT).show();
                } else if (nbrchambre < 1) {
                    Toast.makeText(AnnonceAjout.this, msg + "le nombre des chambres", Toast.LENGTH_SHORT).show();
                } else if (dateStart_s.isEmpty()) {
                    Toast.makeText(AnnonceAjout.this, msg + "la date de début de location", Toast.LENGTH_SHORT).show();
                } else if (dateEnd_s.isEmpty()) {
                    Toast.makeText(AnnonceAjout.this, msg + "la date de fin de location", Toast.LENGTH_SHORT).show();
                } else if (ConverterTools.stringToDate(dateEnd_s).before(ConverterTools.stringToDate(dateStart_s))) {
                    Toast.makeText(AnnonceAjout.this, msg + "la date de fin doit etre apres la date de debut de location", Toast.LENGTH_SHORT).show();
                } else {
                    //add shit to annonce :)
                    annonce.setCreatedDate(Calendar.getInstance().getTime());
                    annonce.setStartDate(ConverterTools.stringToDate(dateStart_s));
                    annonce.setEndDate(ConverterTools.stringToDate(dateEnd_s));
                    annonce.setCity(ville.getSelectedItem().toString());
                    annonce.setPrix(prix);
                    annonce.setState(true);
                    annonce.setTitre(titre);
                    annonce.setUser(SaveSharedPreference.getUserId(getApplicationContext()));
                    String description = descriptionET.getText().toString();
                    options = new JSONObject();

                    try {
                        options.put("description", description);
                        options.put("chambres", nbrchambre);
                        for (Property x : AnnonceTools.tempProps) {
                            String key = ((TextView) findViewById(x.getIdkey())).getText().toString();
                            String value = ((EditText) findViewById(x.getIdvalue())).getText().toString();
                            options.put(key, value);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.w("option", options.toString());
                    annonce.setProperty(options.toString());

                    AnnonceTools.tempProps = new ArrayList<Property>();

                    // add annonce to DB that returns annonce with id

                    try {
                        AnnonceTools.tempAnnonce = Annonce.addAnnonce(annonce.getTitre(), annonce.getProperty(), annonce.getUser(), annonce.getCity(), annonce.getPrix(), annonce.isState()
                                , annonce.getCreatedDate(), annonce.getStartDate(), annonce.getEndDate(), AnnonceAjout.this);
                    } finally {
                    }
                    // go to imageupdate
                    Intent intent = new Intent(getBaseContext(), ImageActivity.class);
                    startActivity(intent);

                }


            }
        });

    }


    private void addDiologue() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnnonceAjout.this);
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
        tab.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        tab.setOrientation(LinearLayout.HORIZONTAL);
        int order = AnnonceTools.tempProps.size();
        Property p = new Property();

        TextView labelKey = new TextView(getApplicationContext());
        labelKey.setText(key);
        labelKey.setTextColor(Color.BLACK);
        labelKey.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        int idLabel = ID_KEY_MARGIN + order;
        labelKey.setId(idLabel);
        p.setIdkey(idLabel);


        EditText editValue = new EditText(getApplicationContext());
        editValue.setTextColor(Color.BLUE);
        LayoutParams parm = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        parm.setMargins(25, 5, 25, 5);
        editValue.setLayoutParams(parm);
        editValue.setText(value);
        int idValueET = ID_VALUE_MARGIN + order;
        editValue.setId(idValueET);
        p.setIdvalue(idValueET);


        ImageButton removebtn = new ImageButton(getApplicationContext());

        parm = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
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


}
