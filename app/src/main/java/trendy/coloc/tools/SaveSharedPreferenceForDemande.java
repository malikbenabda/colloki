package trendy.coloc.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by soumaya on 21/08/16.
 */
public class SaveSharedPreferenceForDemande {

    static final String PREF_DEMMANDE_ID = "";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setDemandeId(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_DEMMANDE_ID, id);
        editor.commit();
    }


    public static String getDemmandeId(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_DEMMANDE_ID, "");
    }

}
