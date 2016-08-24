package trendy.coloc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import trendy.coloc.entities.Property;
import trendy.coloc.tools.AnnonceTools;

public class AnnonceEdit extends Activity {
    Button add, send;
    private final int ID_KEY_MARGIN = 10000;
    private final int ID_VALUE_MARGIN = 10000;
    EditText titre, prix, chambres, dateStart, dateEnd, ftag, fvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ajout_annonce);
        add = (Button) findViewById(R.id.btnAddCriterias);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDiologue();


            }
        });

    }


    private void addDiologue() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnnonceEdit.this);
        LayoutInflater inflater = this.getLayoutInflater();


        //create autocomplete adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, AnnonceTools.TagSuggestions);


        View view = inflater.inflate(R.layout.input_dialog_preference, null);
        AutoCompleteTextView labelKey = (AutoCompleteTextView) view.findViewById((R.id.tag));
        labelKey.setAdapter(adapter);
        labelKey.setThreshold(1);

        labelKey.setHint("type in tag ");
        alertDialog.setView(view);

			/* When positive (yes/ok) is clicked */
        alertDialog.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Dialog inDialog = (Dialog) dialog;
                ftag = (EditText) inDialog.findViewById(R.id.tag);
                String ftag_s = ftag.getText().toString();

                fvalue = (EditText) inDialog.findViewById(R.id.value);
                String fvalue_s = "";
                fvalue_s = fvalue.getText().toString();
                CheckBox checkBox = (CheckBox) inDialog.findViewById(R.id.checher);
                if (checkBox.isChecked()) fvalue_s += "1";
                else fvalue_s += "0";
                int size = AnnonceTools.tempProps.size();
                AnnonceTools.tempProps.add(new Property(ftag_s, fvalue_s, size));
                addTab();

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

    private void addTab() {
        LinearLayout tab = new LinearLayout(getApplicationContext());
        tab.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        tab.setOrientation(LinearLayout.HORIZONTAL);
        int counter = AnnonceTools.tempProps.size();
        Property p = AnnonceTools.tempProps.get(counter - 1);

        TextView labelKey = new TextView(getApplicationContext());
        labelKey.setText(p.getKey());
        labelKey.setTextColor(Color.BLACK);
        labelKey.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        int idLabel = ID_KEY_MARGIN + p.getOrder();
        labelKey.setId(idLabel);
        p.setIdkey(idLabel);


        EditText editValue = new EditText(getApplicationContext());

        editValue.setTextColor(Color.BLUE);
        editValue.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        editValue.setText(p.getValue());
        editValue.setId(ID_VALUE_MARGIN + p.getOrder());

        p.setIdvalue(editValue.getId());

        tab.addView(labelKey);
        tab.addView(editValue);
        LinearLayout optionLayout = (LinearLayout) findViewById(R.id.optionsLayout);
        optionLayout.addView(tab);

    }




}
