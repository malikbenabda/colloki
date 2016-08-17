package trendy.coloc.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

/**
 * Created by malik on 16-Aug-16.
 */
public class Upload {
    RequestParams  paramsx = new RequestParams();
    SimpleDateFormat format;
    Bitmap bitmap;

    String encodedString;
    private  String SERVERIP="192.168.1.10"; // default value
    public void uploadImage(String filePath,String serverIP ) {
        SERVERIP = serverIP;
        // When Image is selected from Gallery
        if (filePath != null && !filePath.isEmpty()) {

            // Convert image to String using Base64
             encodeImageToString(filePath);
            // When Image is not selected from Gallery
        } else {
            // error message
        }
    }

    private void encodeImageToString(final String path) {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {     }     ;

            @Override
            protected String doInBackground(Void... params) {

                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(path,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                int size = bitmap.getRowBytes() * bitmap.getHeight();

                bitmap.compress(Bitmap.CompressFormat.JPEG , 50, stream);
                size = bitmap.getRowBytes() * bitmap.getHeight();
                Log.i("size of image", " after 50 compression  " + stream.toString());

                byte[] byte_arr = stream.toByteArray();
                Log.i( "stream",stream.toString());
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                Log.i("encoded" , encodedString);


                //// onpostexec method


                // Put converted Image string into Async Http Post param
                paramsx.put("image", encodedString);
                String name = new Random().nextInt(10)+""+new SimpleDateFormat("hh_mm_ss").toString()+".png";

                paramsx.put("filename",name);
                Log.i("tag" , paramsx.toString());
                // Trigger Image upload
                makeHTTPCall();




                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.i("tag" , "entered postexecute with messge = "+msg);
             //    params = new RequestParams();
                // Put converted Image string into Async Http Post param
              //  params.put("image", encodedString);
              //  String name = new Random().nextInt(10)+""+new SimpleDateFormat("hh_mm_ss").toString()+".png";

             //   params.put("filename",name);

                // Trigger Image upload
            //    makeHTTPCall();
            }
        }.execute(null, null, null);
    }

    private void makeHTTPCall() {

        AsyncHttpClient client = new AsyncHttpClient();

        Log.i("tag" , paramsx.toString());
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("192.168.1.10" + "/colloki/upload.php",
                paramsx, new AsyncHttpResponseHandler() {



                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.i("tag" , "success");
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.i("tag" , "failure");
                    }
                });
    }



}
