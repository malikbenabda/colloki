package trendy.coloc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import trendy.coloc.adapters.demandeAdapter;
import trendy.coloc.entities.DemandeColocation;

public class ListeDesDemandesActivity extends Activity {
    ListView userList;
    demandeAdapter userAdapter;
    ArrayList<DemandeColocation> userArray = new ArrayList<DemandeColocation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_des_demandes);

        /**
         * add item in arraylist
         */
        userArray.add(new DemandeColocation("Mumer", "Spain", "Spain"));
        userArray.add(new DemandeColocation("Jon", "EW", "USA"));
        userArray.add(new DemandeColocation("Broom", "Span", "SWA"));
        userArray.add(new DemandeColocation("Lee", "Aus", "AUS"));
        userArray.add(new DemandeColocation("Jon", "EW", "USA"));
        userArray.add(new DemandeColocation("Broom", "Span", "SWA"));
        userArray.add(new DemandeColocation("Lee", "Aus", "AUS"));
        /**
         * set item into adapter
         */
        userAdapter = new demandeAdapter(ListeDesDemandesActivity.this, R.layout.view_row,
                userArray);
        userList = (ListView) findViewById(R.id.listView);
        userList.setItemsCanFocus(false);
        userList.setAdapter(userAdapter);
        /**
         * get on item click listener
         */
        userList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                Log.i("List View Clicked", "**********");
                Toast.makeText(ListeDesDemandesActivity.this,
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

}

