package trendy.coloc.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import trendy.coloc.tools.Critera;

public class DemandeColocation {
    //attribus:

    private String id;

    private Date dat_dmd_clc;
    private Date date1;
    private Date date2;
    private String titre;
    private ArrayList<Critera> preferences;

    ////////////////////preferences:


    String ch1, ch2, ch3;


    public DemandeColocation(String s1, String s2, String s3) {


        ch1 = s1;
        ch2 = s2;
        ch3 = s3;

    }

    //builder:
    public DemandeColocation() {
    }

    //getters;
    public String getId() {
        return this.id;
    }

    public String getTitre() {
        return this.titre;
    }


    public Date getdat_dmd_clc() {
        return this.dat_dmd_clc;
    }

    public Date getdat1() {
        return this.date1;
    }

    public Date getdat2() {
        return this.date2;
    }

    public ArrayList<Critera> getPreferences() {
        return this.preferences;
    }


    ///setters
    public void setId(String id) {
        this.id = id;
    }


    public void setDateDmd(Date d) {
        this.dat_dmd_clc = d;
    }

    public void setDate1(Date d) {
        this.date1 = d;
    }

    public void setDate2(Date d) {
        this.date2 = d;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setPrefrences(ArrayList<Critera> al) {

        this.preferences = al;
    }


///////////////////////////CRUD:

//*************************************1.CREATE***********************************

    public DemandeColocation addDdmd(String idUser, String titre, String preferences, Date d1, Date d2, Date date_creation)

    {
        String methode = "add_demande";
        DemandeColocation result = new DemandeColocation();
        String ch = "";

        try {

            // DataTask dt = new DataTask();
            // ch = dt.execute(methode,idUser, titre,ville,preferences, date1,date2,date_demande_colocation,date_demande_colocation).get();
            //EXTRACTION:

            // result= JsonToDemmandecolocation(ch);

        } catch (Exception e) {
        }


        return result;

    }

    //**********************************2.READ********************************
///Read one: read one demmande with its id
    public DemandeColocation fetch_one(String id_demmande_colocation) {
        String result = "";
        DemandeColocation da = new DemandeColocation();

        //DataTask db = new DataTask(ctx);

        String methode = "demande_colocation";

        try {

            // result = db.execute(methode,id_demmande_colocation).get();
            da = JsonToDemmandecolocation(result);
            return da;
        } catch (Exception e) {
        }

        return da;
    }

    //read all:
/////////get all  dmds  from db in a an arraylist with user id
    public ArrayList<DemandeColocation> fetch_all() {
        ArrayList<DemandeColocation> demandes = new ArrayList<DemandeColocation>();

        String result = "";

        //DataTask db = new DataTask(null);

        String methode = "demandes_colocations";


        try {

            //result = db.execute(methode).get();
            JSONArray ja = new JSONArray(result);

            for (int i = 0; i < ja.length(); i++) {

                DemandeColocation dc = new DemandeColocation();

                JSONObject obj = ja.getJSONObject(i);

                dc = JsonToDemmandecolocation(obj.toString());

                demandes.add(dc);

            }
        } catch (Exception e) {
        }

        return demandes;
    }
/////////////////////read all demmandes by user id:

    public ArrayList<DemandeColocation> fetch_all_by_user_id(String userId) {
        ArrayList<DemandeColocation> demandes = new ArrayList<DemandeColocation>();

        String result = "";

        //DataTask db = new DataTask(null);

        String methode = "demandes_colocations_user_id";


        try {

            //result = db.execute(methode).get();
            JSONArray ja = new JSONArray(result);

            for (int i = 0; i < ja.length(); i++) {

                DemandeColocation dc = new DemandeColocation();

                JSONObject obj = ja.getJSONObject(i);

                dc = JsonToDemmandecolocation(obj.toString());

                demandes.add(dc);

            }
        } catch (Exception e) {
        }

        return demandes;
    }


//************************************2.DELETE****************************

    public String DeleteDemande(String AnnonceId)

    {
        String methode = "delete_demande";
        String ch = "";
        String json_result = "";

        try {

            // DataTask dt = new DataTask(null);
            //json_result= dt.execute(methode, AnnonceId);

            ch = JsnToString(ch);
        } catch (Exception e) {


        }

        return ch;

    }

    //*******************************3.UPDATE**************************
    public DemandeColocation UpdateDdmd(String idAnnonce, String titre, String ville, String preferences, Date d1, Date d2, Date date_modification)

    {
        String methode = "update_demande";
        String ch = "";
        DemandeColocation result = new DemandeColocation();

        try {

            // DataTask dt = new DataTask();
            // ch = dt.execute(id,titre,ville,preferences,d1,d2,date_modification_demande_colocation).get();
            result = JsonToDemmandecolocation(ch);

        } catch (Exception e) {
        }
        return result;


    }

    ////////////////////////////////////function converts from json to demande colocation:
    public DemandeColocation JsonToDemmandecolocation(String ch) {


        ArrayList<Critera> preferences = new ArrayList<Critera>();

        Date date_inferieure;
        Date date_superieure;
        Date date_demande;
        String titre = "";

        DemandeColocation dc = new DemandeColocation();


        try {
            JSONObject obj = new JSONObject(ch);

            date_inferieure = new Date();
            date_superieure = new Date();
            date_demande = new Date();
            JSONArray ja = obj.getJSONArray(ch);
            int n = ja.length();

            for (int i = 0; i < n; ++i) {
                JSONObject ob = ja.getJSONObject(i);

//////////////////////////////////do the prefrences extraction by itself:
                preferences = JsonPrefrences(ob.getString("preferences"));


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                try {
/////////////////////////CONVRT JSON STRINGS DATES TO ACTUAL DATES
                    date_inferieure = (dateFormat.parse(ob.getString("date_inferieur")));
                    date_superieure = (dateFormat.parse(ob.getString("date_superieure")));
                    date_demande = (dateFormat.parse(ob.getString("date_demande")));

                } catch (ParseException e) {
                }


            }

            dc.setDate1(date_inferieure);
            dc.setDate2(date_superieure);
            dc.setPrefrences(preferences);
            dc.setDateDmd(date_demande);


            //  dc.setpreferences(preferences);
        } catch (JSONException e) {
        }
        return dc;

    }

    /////////////////////GET DELET DEMANDE RESULT SUCCESS OR FAILURE:
    public String JsnToString(String ch) {

        String c = "";

        try {
            JSONObject obj = new JSONObject(ch);
            JSONArray ja = obj.getJSONArray("resultat");
            int n = ja.length();
            for (int i = 0; i < n; ++i) {
                final JSONObject blabla = ja.getJSONObject(i);
                c = blabla.getString("success");
            }


        } catch (JSONException e) {
        }


        return c;
    }

    /////////////////////////
    public ArrayList<Critera> JsonPrefrences(String ch) {


        String param1 = "";
        String param2 = "";

        ArrayList<Critera> liste = new ArrayList<Critera>();


        try {

            JSONArray jsonArray = new JSONArray(ch);
            for (int i = 0; i < jsonArray.length(); i++) {


                String ch1 = jsonArray.getJSONObject(i).get("preferences").toString();

                Critera cr = new Critera();

                //  cr.setTag(ch1);
                //  cr.setdesc(ch2);

                liste.add(cr);
            }

        } catch (JSONException e) {
        }
        return liste;
    }

////////////////////

    public ArrayList<Critera> getPrefrences(String ch) {

        Critera cr;
        ArrayList<Critera> la = new ArrayList<Critera>();
        String param1 = "";
        String param2 = "";
        try {

            JSONArray jsonArray = new JSONArray(ch);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jo = jsonArray.getJSONObject(i);
                Iterator<String> iter = jo.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    try {
                        param1 = key;
                        param2 = jo.get(key).toString();

                    } catch (JSONException e) {
                    }

                }
                cr = new Critera();
                cr.setTag(param1);
                cr.setdesc(param2);
                la.add(cr);

            }

        } catch (JSONException e) {
        }

        return la;


    }
}

