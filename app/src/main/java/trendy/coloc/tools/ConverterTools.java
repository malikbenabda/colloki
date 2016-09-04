package trendy.coloc.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import trendy.coloc.entities.Property;

/**
 * Created by malik on 19-Aug-16.
 */
public class ConverterTools {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

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
    public static Map<String, String> JSONstringToMap(String jsonString) {
        Map<String, String> map = new HashMap<String, String>();
        if (!jsonString.isEmpty())
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject != JSONObject.NULL) {
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = jsonObject.get(key).toString();
                    map.put(key, value);

                }
            }

        } catch (JSONException x) {
            Toast.makeText(null, "JsonString to map convertion failed", Toast.LENGTH_SHORT).show();
        }

        return map;
    }

    public static String mapToJSONstring(Map<String, String> map) {
        return new JSONObject(map).toString();
    }


    public static Date stringToDate(String dateString) {
        Date convertedCurrentDate = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            convertedCurrentDate = sdf.parse(dateString);

        } catch (Exception x) {
            Toast.makeText(null, "Date Format Erroné", Toast.LENGTH_SHORT).show();
        }

        return convertedCurrentDate;
    }


    public static String DateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }


    public static ArrayList<Property> jsonStringToPropertiesArray(String jsonString) {
        ArrayList<Property> jsonArray = new ArrayList<Property>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject != JSONObject.NULL) {
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = jsonObject.get(key).toString();
                    AnnonceTools.tempProps.add(new Property(key, value));

                }
            }
        } catch (JSONException x) {
            Toast.makeText(null, "JsonString to map convertion failed", Toast.LENGTH_SHORT).show();
        }
        return jsonArray;

    }


    public static String ImageTo64String(String path, String compresstype_JPEG_PNG, int compressionRate) {


        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(path,
                options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Must compress the Image to reduce image size to make upload easy
        if (compresstype_JPEG_PNG.equalsIgnoreCase("jpeg"))
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRate, stream);
        else
            bitmap.compress(Bitmap.CompressFormat.PNG, compressionRate, stream);

        byte[] byte_arr = stream.toByteArray();
        Log.i("stream", stream.toString());
        // Encode Image to String

        String encodedString = Base64.encodeToString(byte_arr, 0);
        Log.i("encoded", encodedString);


        return encodedString;
    }




}
