package trendy.coloc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import trendy.coloc.adapters.DmdsCustomAdapter;
import trendy.coloc.entities.DemandeColocation;
import trendy.coloc.tools.SaveSharedPreference;

public class ListDmdsActivity extends AppCompatActivity {


    ListView dmdList;
    DmdsCustomAdapter Demmandeadapter;
    ArrayList<DemandeColocation> dmdArray = new ArrayList<DemandeColocation>();
    Context cnx;

    String nbcolocs = "";
    String nb_cmbres = "";

    DemandeColocation dc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dmds);
        cnx = ListDmdsActivity.this;

        dc = new DemandeColocation();

        String idUser = SaveSharedPreference.getUserId(ListDmdsActivity.this);

        dmdArray = dc.fetch_all_by_user_id(idUser);


        //  for(int i=0;i<10;i++){

        //    DmdColoc dc = new DmdColoc();
        //    dc.setVille("qq");
        //    dc.setId("1");
        //    dmdArray.add(dc);

//        }


        Demmandeadapter = new DmdsCustomAdapter(cnx, R.layout.view_row,
                dmdArray);
        dmdList = (ListView) findViewById(R.id.listView);
        dmdList.setItemsCanFocus(false);
        dmdList.setAdapter(Demmandeadapter);
        /**
         * get on item click listener
         */
        dmdList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                Log.i("List View Clicked", "**********");
                Toast.makeText(ListDmdsActivity.this,
                        "List View Clicked:" + position, Toast.LENGTH_LONG)
                        .show();


            }
        });

        //JsonExtract(JSON_DATA);
    }

}
