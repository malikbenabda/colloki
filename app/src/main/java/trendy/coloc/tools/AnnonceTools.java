package trendy.coloc.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import trendy.coloc.entities.Annonce;
import trendy.coloc.entities.Property;

/**
 * Created by malik on 24-Aug-16.
 */
public class AnnonceTools {

    public static String URL = "https://pixabay.com/static/uploads/photo/2014/06/16/23/39/black-370118_960_720.png";
    public static ArrayList<Property> tempProps = new ArrayList<Property>();
    public static Annonce tempAnnonce = new Annonce();


    public static List<String> villes = Arrays.asList("sousse", "mehdia", "ariana", "nabel", "sfax");
    public static List<String> keySuggestions = Arrays.asList("Wifi", "chiens", "fumer", "alcool", "chats");


    public static int indexOfItem(String key, List<String> list) {
        int pos = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(key)) ;
            pos = i;
        }
        return pos;
    }

}
