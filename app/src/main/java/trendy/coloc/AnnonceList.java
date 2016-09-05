package trendy.coloc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class AnnonceList extends AppCompatActivity {
    private final int ID_KEY_MARGIN = 1000;
    private final int ID_VALUE_MARGIN = 10000;
    private final int ID_DELETE_MARGIN = 20000;
    private final int ID_OPTIONLAYOUT_MARGIN = 30000;
    private MyCustomAdapter listViewAnnonceSearchAdapter;
    private ArrayList<Annonce> annonces;
    private JSONObject preferences, z, x, y;

    TextView dateStartET, dateEndET;
    ImageButton btnprefs, btnaddSearchCriteria;
    ArrayAdapter<String> prefAdapter, cityAdapter;
    AutoCompleteTextView villeAC;
    private LinearLayout rootLayout;
    private ListView annoncesLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_list);


        annoncesLV = (ListView) findViewById(R.id.annonceListView);

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
                AnnonceTools.tempProps = new ArrayList<Property>();
                AnnonceTools.tempProps = new ArrayList<Property>();
                Intent go2Crits = new Intent(getApplicationContext(), AnnonceSearchPrefsActivity.class);
                startActivity(go2Crits);


            }
        });
        annonces = Annonce.searchAnnonceBloxByKeys(AnnonceTools.searchPreferencesJsonString, AnnonceList.this);
        listViewAnnonceSearchAdapter = new MyCustomAdapter(this, R.layout.row_annonce, annonces);
        listViewAnnonceSearchAdapter.setNotifyOnChange(true);
        annoncesLV.setAdapter(listViewAnnonceSearchAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        listViewAnnonceSearchAdapter = new MyCustomAdapter(this, R.layout.row_annonce, annonces);
        listViewAnnonceSearchAdapter.setNotifyOnChange(true);
        annoncesLV.setAdapter(listViewAnnonceSearchAdapter);

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

        annonces.add(c);
        annonces.add(b);
        annonces.add(c);
        annonces.add(b);


    }


}
