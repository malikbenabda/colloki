package trendy.coloc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import trendy.coloc.tools.SaveSharedPreference;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        String ch = SaveSharedPreference.getUserId(MenuActivity.this);

        Toast.makeText(getApplicationContext(), "id is: " + ch, Toast.LENGTH_SHORT).show();


    }

    public void DmdColoc(View v) {


        Intent i = new Intent(MenuActivity.this, DemandeColocationActivity.class);
        startActivity(i);


    }

    public void Bhist(View v) {

        Intent i = new Intent(MenuActivity.this, ListDmdsActivity.class);
        startActivity(i);


    }


    public void GotoProfile(View v) {
        Intent i = new Intent(MenuActivity.this, ProfileActivity.class);
        startActivity(i);


    }


}
