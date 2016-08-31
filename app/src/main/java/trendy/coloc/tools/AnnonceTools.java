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


    public static ArrayList<Property> tempProps = new ArrayList<Property>();
    public static Annonce tempAnnonce = new Annonce();


    public static List<String> villes = Arrays.asList("sousse", "nabel", "sfax");

    public final static String[] keySuggestions = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain", "aaaa", "abbbb", "aabbb", "abbaaa"
    };

    public static int indexOfItem(String key, List<String> list) {
        int pos = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(key)) ;
            pos = i;
        }
        return pos;
    }

}
