package trendy.coloc.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    /*This is used in annonce to convert properties set by user from a json objzct to map
    * */
    public static Map<String, String> JSONstringToMap(String jsonString) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();

        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject != JSONObject.NULL) {
            Iterator<String> keysItr = jsonObject.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                String value = jsonObject.get(key).toString();
                map.put(key, value);

            }
        }
        return map;
    }

    public static String mapToJSONstring(Map<String, String> map) {
        return new JSONObject(map).toString();
    }

}
