package trendy.coloc.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by malik on 19-Aug-16.
 */
public class ConverterTools {
    public String ImageToString64(String imagePath) {

        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Must compress the Image to reduce image size to make upload easy
        int size = bitmap.getRowBytes() * bitmap.getHeight();

        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        //size = bitmap.getRowBytes() * bitmap.getHeight();
        Log.i("size of image", "  " + stream.toString());

        byte[] byte_arr = stream.toByteArray();
        Log.i("stream", stream.toString());
        // Encode Image to String
        String encodedString = Base64.encodeToString(byte_arr, 0);
        Log.i("encoded", encodedString);

        return encodedString;


    }


    public ArrayList jsonToArray(String source) {


        return null;

    }

}
