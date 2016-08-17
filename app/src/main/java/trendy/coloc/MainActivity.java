package trendy.coloc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;
import trendy.coloc.tools.Upload;

public class MainActivity extends AppCompatActivity {

    /*********  work only for Dedicated IP ***********/
    static final String SERVERIP= "192.168.1.10";

    /*********  FTP USERNAME ***********/
    static final String FTP_USER = "admin";

    /*********  FTP PASSWORD ***********/
    static final String FTP_PASS  ="";
    File file;
    ImageView im ;
    TextView textView;
    TextView textView2;
    String filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        im = ( ImageView) findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                   filepath= Environment.getExternalStorageDirectory().getPath();
                    textView.setText(filepath);
                     file = new File(filepath+"/Download/demo1.jpg");
                    filepath+="/logo.jpg";
                    im.setImageBitmap(BitmapFactory.decodeFile(filepath));
                }catch (Exception x){
                    Toast.makeText(null,"cnt make file",Toast.LENGTH_SHORT);
                    Log.e("errer","cnt make file");             }

            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              try {
                  Upload up = new Upload();
                  up.uploadImage(filepath,SERVERIP);
                  Toast.makeText(null,"fileuploaded with success",Toast.LENGTH_SHORT).show();
              }catch (Exception x) {

              }

            }
        });









    }
}
