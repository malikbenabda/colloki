package trendy.coloc;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AnnonceEdit extends Activity {
    Button add;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_edit);
        add = (Button) findViewById(R.id.sendbtn);
        layout = (LinearLayout) findViewById(R.id.parameter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tv = new TextView(getApplicationContext());
                tv.setBackgroundColor(Color.BLACK);

                tv.setWidth(255);
                tv.setHeight(55);
                layout.addView(tv);

                Toast.makeText(AnnonceEdit.this, "helo", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
