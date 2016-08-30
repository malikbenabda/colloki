package trendy.coloc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import trendy.coloc.adapters.MyCustomAdapter;
import trendy.coloc.entities.Annonce;
import trendy.coloc.tools.ConverterTools;

public class AnnonceList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_list);
        ListView annoncesLV = (ListView) findViewById(R.id.annonceListView);

        ArrayList<Annonce> annonces = new ArrayList<Annonce>();
        JSONObject preferences = null, x = null, y = null, z = null;
        try {
            x = new JSONObject().put("chambres", 5).put("description", "dess").put("wifi", "yes");
            y = new JSONObject().put("chambres", 8).put("description", "dess").put("wifi", "yes");
            z = new JSONObject().put("chambres", 5).put("description", "aaa").put("wifi", "no");
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


        ArrayList<Annonce> annoncesf = Annonce.selectByProperties(annonces, ConverterTools.JSONstringToMap(preferences.toString()));

        MyCustomAdapter adapter = new MyCustomAdapter(this, R.layout.row_annonce, annoncesf);
        annoncesLV.setAdapter(adapter);
    }
}
