package trendy.coloc.entities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import trendy.coloc.backgroundtask.DataTask;
import trendy.coloc.tools.ConverterTools;

/**
 * Created by malik on 19-Aug-16.
 */
public class Annonce {
    private int id;
    private String titre;
    private String property;
    private String user;
    private Map<String, String> propMap;
    private String city;
    private float prix;
    private boolean state;
    private Date endDate;
    private Date startDate;
    private Date createdDate;

    public Map<String, String> getPropMap() {
        return propMap;
    }

    public void setPropMap(Map<String, String> propMap) {
        this.propMap = propMap;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    private Context ctx = null;

    public Annonce() {
    }

    public Annonce(int id, String titre, float prix, boolean state, String property, String user, String city) {
        this.id = id;
        this.titre = titre;
        this.prix = prix;
        this.state = state;
        this.property = property;
        this.user = user;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    //******************************************************
    /*              Services
  *************************************************************/


    public ArrayList<Annonce> getAllByKey(String key, String value) {
        ArrayList<Annonce> annonces = new ArrayList<Annonce>();
        String result;
        Annonce annonce = new Annonce();
        DataTask db = new DataTask(ctx);

        String methode = "getAllByKey";

        try {
            result = db.execute(methode, key + "", value + "").get();
            JSONArray ary_jsn = new JSONArray(result);

            for (int i = 0; i < ary_jsn.length(); i++) {
                JSONObject obj_jsn = ary_jsn.getJSONObject(i);
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString().toLowerCase().trim());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));

                if (obj_jsn.get("state").toString().equals("1")) {
                    annonce.setState(true);
                } else {
                    annonce.setState(false);
                }

                annonces.add(annonce);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
/* sort by key
   Collections.sort(annonces, new Comparator<Annonce>() {
            @Override
            public int compare(Annonce lhs, Annonce rhs) {
                return return lhs.key>rhs.key;
            }
        });
  */
        return annonces;
    }

    public Annonce getOneById(int id) {
        String result;
        Annonce annonce = new Annonce();
        DataTask db = new DataTask(ctx);

        String methode = "getOneById";

        try {

            result = db.execute(methode, id + "").get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString().toLowerCase().trim());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));

                if (obj_jsn.get("state").toString().equals("1")) {
                    annonce.setState(true);
                } else {
                    annonce.setState(false);
                }

                return annonce;
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Annonce updateAnnonce(int id, String titre, String property, String user, String city, float prix, boolean state) {
        String result;
        Annonce annonce = new Annonce();
        DataTask db = new DataTask(ctx);
        String st = "";
        if (state) st = "1";
        else st = "0";
        String methode = "updateAnnonce";

        try {

            result = db.execute(methode, id + "", titre, property, user, city, prix + "", st).get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));

                if (obj_jsn.get("state").toString().equals("1")) {
                    annonce.setState(true);
                } else {
                    annonce.setState(false);
                }

                return annonce;
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Annonce addAnnonce(int id, String titre, String property, String user, String city, float prix, boolean state) {
        String result;
        Annonce annonce = new Annonce();
        DataTask db = new DataTask(ctx);
        String st = "";
        if (state) st = "1";
        else st = "0";
        String methode = "addAnnonce";

        try {

            result = db.execute(methode, titre, property, user, city, prix + "", st).get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));

                if (obj_jsn.get("state").toString().equals("1")) {
                    annonce.setState(true);
                } else {
                    annonce.setState(false);
                }

                return annonce;
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean removeAnnonce(int id) {
        String result;

        DataTask db = new DataTask(ctx);
        String methode = "removeAnnonce";

        try {

            result = db.execute(methode, id + "").get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);
            String response = obj_jsn.get("response").toString();
            if (response.equalsIgnoreCase("success")) return true;
            else return false;


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    //** Recherche*****************************************************************

    public ArrayList<Annonce> selectByProperties(ArrayList<Annonce> searchList, Map<String, String> propsSelection) throws JSONException {
        ArrayList<Annonce> annonceArrayList = new ArrayList<Annonce>();
        for (Annonce annonce : searchList) {
            //parcourir liste des annnonces
            Map<String, String> annonceProperty = ConverterTools.JSONstringToMap(annonce.property);
            //parcourir liste des criteres de selection ( search options)
            Set<Map.Entry<String, String>> options = propsSelection.entrySet();
            if (isAllOptionsInProperty(options, annonceProperty)) {
                annonceArrayList.add(annonce);
            }
        }
        return annonceArrayList;
    }

    //check if all options are valid in this annonceProperty
    //required for SelectByProperties
    private static boolean isAllOptionsInProperty(Set<Map.Entry<String, String>> options, Map<String, String> annonceProperty) {
        boolean result = true;
        //for each option do test if it exists
        for (Map.Entry<String, String> option : options) {
            String optionKey = option.getKey().toLowerCase();
            // if the seach option is not in annonce then the options do not confirm
            if (!(annonceProperty.containsKey(optionKey))) return false;
                // if the value of the option is not the same as user requested
                // the entire options mark this annonce as non confirm
            else if (!annonceProperty.get(optionKey).equalsIgnoreCase(option.getValue()))
                return false;
        }
        return result;
    }

////////////////************************************************************************
    //recherche REMAKE




}
