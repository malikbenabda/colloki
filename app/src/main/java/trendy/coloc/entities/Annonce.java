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

    private String city;
    private float prix;
    private boolean state;
    private Date endDate;
    private Date startDate;
    private Date createdDate;
    private String property;
    private String user;


    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    private Context ctx = null;

    public Annonce() {
    }

    public Annonce(String titre, String city, float prix, boolean state, Date endDate, Date startDate, Date createdDate, String property, String user) {
        this.titre = titre;
        this.city = city;
        this.prix = prix;
        this.state = state;
        this.endDate = endDate;
        this.startDate = startDate;
        this.createdDate = createdDate;
        this.property = property;
        this.user = user;
    }

    public Annonce(int id, String titre, String property, String user, String city, float prix, boolean state, Date endDate, Date startDate, Date createdDate, Context ctx) {
        this.id = id;
        this.titre = titre;
        this.property = property;
        this.user = user;
        this.city = city;
        this.prix = prix;
        this.state = state;
        this.endDate = endDate;
        this.startDate = startDate;
        this.createdDate = createdDate;
        this.ctx = ctx;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

        String methode = "getAllAnnoncesByKey";

        try {
            result = db.execute(methode, key ,value).get();
            JSONArray ary_jsn = new JSONArray(result);

            for (int i = 0; i < ary_jsn.length(); i++) {
                JSONObject obj_jsn = ary_jsn.getJSONObject(i);
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString().toLowerCase().trim());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));
                annonce.setStartDate(ConverterTools.stringToDate( obj_jsn.get("startDate").toString()) ) ;
                annonce.setEndDate(ConverterTools.stringToDate( obj_jsn.get("endDate").toString()) ) ;
                annonce.setCreatedDate(ConverterTools.stringToDate( obj_jsn.get("createdDate").toString()) ); ;

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

    /**
     * json string is formatted : {"key":"value,"jey2":"value2"}
     * @param jsonString
     * @return
     */
    public ArrayList<Annonce> getAllByKeys(String jsonString) {
        ArrayList<Annonce> annonces = new ArrayList<Annonce>();
        String result;
        Annonce annonce = new Annonce();
        DataTask db = new DataTask(ctx);

            String methode = "getAllAnnoncesByKeys";

        try {
            result = db.execute(methode, jsonString).get();
            JSONArray ary_jsn = new JSONArray(result);

            for (int i = 0; i < ary_jsn.length(); i++) {
                JSONObject obj_jsn = ary_jsn.getJSONObject(i);
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString().toLowerCase().trim());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));
                annonce.setStartDate(ConverterTools.stringToDate( obj_jsn.get("startDate").toString()) ) ;
                annonce.setEndDate(ConverterTools.stringToDate( obj_jsn.get("endDate").toString()) ) ;
                annonce.setCreatedDate(ConverterTools.stringToDate( obj_jsn.get("createdDate").toString()) ); ;

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

    /**
     *
     * @param id
     * @return
     */
    public Annonce getOneById(int id) {
        String result;
        Annonce annonce = new Annonce();
        DataTask db = new DataTask(ctx);

        String methode = "getAnnonceById";

        try {

            result = db.execute(methode, Integer.toString(id)).get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString().toLowerCase().trim());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));
                annonce.setStartDate(ConverterTools.stringToDate( obj_jsn.get("startDate").toString()) ) ;
                annonce.setEndDate(ConverterTools.stringToDate( obj_jsn.get("endDate").toString()) ) ;
                annonce.setCreatedDate(ConverterTools.stringToDate( obj_jsn.get("createdDate").toString()) ); ;

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

    /**
     *
     * @param id
     * @param titre
     * @param property
     * @param user
     * @param city
     * @param prix
     * @param state
     * @return @Annonce_updated
     */
    public Annonce updateAnnonce(int id, String titre, String property, String user, String city, float prix, boolean state,
                                 Date createdDate, Date startDate, Date endDate ) {
        String result;
        Annonce annonce = new Annonce();
        DataTask db = new DataTask(ctx);
        String st = "";
        if (state) st = "1";
        else st = "0";

        String createdDate_S = ConverterTools.DateToString(createdDate);
        String startDate_S = ConverterTools.DateToString(startDate);
        String endDate_S = ConverterTools.DateToString(endDate);
        String methode = "updateAnnonce";

        try {

            result = db.execute(methode,Integer.toString(id), titre, property, user, city, String.valueOf(prix), st
            ,createdDate_S,startDate_S,endDate_S).get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));
                annonce.setStartDate(ConverterTools.stringToDate( obj_jsn.get("startDate").toString()) ) ;
                annonce.setEndDate(ConverterTools.stringToDate( obj_jsn.get("endDate").toString()) ) ;
                annonce.setCreatedDate(ConverterTools.stringToDate( obj_jsn.get("createdDate").toString()) ); ;

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

    public Annonce addAnnonce(int id, String titre, String property, String user, String city, float prix, boolean state ,  Date createdDate, Date startDate, Date endDate) {
        String result;
        Annonce annonce = new Annonce();
        DataTask db = new DataTask(ctx);
        String st = "";
        if (state) st = "1";
        else st = "0";
        String methode = "addAnnonce";
        String createdDate_S = ConverterTools.DateToString(createdDate);
        String startDate_S = ConverterTools.DateToString(startDate);
        String endDate_S = ConverterTools.DateToString(endDate);
        try {

            result = db.execute(methode, titre, property, user, city, String.valueOf(prix), st, createdDate_S,startDate_S,endDate_S).get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                annonce.setId((Integer) obj_jsn.get("id"));
                annonce.setTitre(obj_jsn.get("titre").toString());
                annonce.setProperty(obj_jsn.get("property").toString());
                annonce.setUser(obj_jsn.get("user").toString());
                annonce.setCity(obj_jsn.get("city").toString());
                annonce.setPrix((float) obj_jsn.get("prix"));
                annonce.setStartDate(ConverterTools.stringToDate( obj_jsn.get("startDate").toString()) ) ;
                annonce.setEndDate(ConverterTools.stringToDate( obj_jsn.get("endDate").toString()) ) ;
                annonce.setCreatedDate(ConverterTools.stringToDate( obj_jsn.get("createdDate").toString()) ); ;

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
