package trendy.coloc.tools;

/**
 * Created by soumaya on 20/08/16.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by thegohst on 7/23/16.
 */
public class SaveSharedPreference {
    static final String PREF_USER_NAME = "";
    static final String PREF_USER_PHOTO = "";
    static final String PREF_USER_ID = "";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {

        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }


    public static void setUserId(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, id);
        editor.commit();
    }


    public static void setUserPhoto(Context ctx, String userPhoto) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PHOTO, userPhoto);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getUserId(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }

    public static String getUserPhoto(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_PHOTO, "");
    }
}
