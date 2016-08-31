package trendy.coloc.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DmdColoc {
    //attribus:
    private String id;
    private String ville;
    private Date dat_dmd_clc;
    private Date date1;
    private Date date2;
    private String preferences;

    ////////////////////preferences:

    private int nbr_chambres;
    private int nbr_colocs;
    private double prix_minimal;
    private double prix_maximal;


    //builder:
    public DmdColoc() {
    }

    //getters;
    public String getId() {
        return this.id;
    }

    public String getVille() {
        return this.ville;
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

    public String getpreferences() {
        return this.preferences;
    }

    public int getNbrChambres() {
        return this.nbr_chambres;
    }

    public int getNbr_colocs() {
        return this.nbr_colocs;
    }

    public double getPrix_minimal() {
        return this.prix_minimal;
    }

    public double getPrix_maximal() {
        return this.prix_maximal;
    }


    ///seters
    public void setId(String id) {
        this.id = id;
    }

    public void setVille(String v) {
        this.ville = v;
    }

    public void setNbrChambres(int nombre) {
        this.nbr_chambres = nombre;
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

    public void setpreferences(String n) {
        this.preferences = n;
    }

    public void setNbr_colocs(int nombre) {
        this.nbr_colocs = nombre;
    }

    public void setPrix_minimal(double prix_minimal) {
        this.prix_minimal = prix_minimal;
    }

    public void setPrix_maximal(double prix_maximal) {
        this.prix_maximal = prix_maximal;
    }


///////////////////////////CRUD:

//*************************************1.CREATE***********************************

    public DmdColoc addDdmd(String id, String ville, String preferences, Date d1, Date d2)

    {
        String methode = "add_dmd";
        DmdColoc result = new DmdColoc();

        //  DmdColoc result=null;

        try {

            // DataTask dt = new DataTask();
            // result = dt.execute(methode, id,ville ,preferences,d1, d2).get();

        } catch (Exception e) {
        }


        return result;

    }

    //**********************************2.READ********************************
///Read one: read one demmande with its id
    public DmdColoc fetch_one(String iddmd) {
        String result = "";
        // DmdColoc da = new DmdColoc();

        DmdColoc da = null;

        //DataTask db = new DataTask(ctx);

        String methode = "dmd_clc";
        SimpleDateFormat ss;
        try {

            // da = db.execute(methode,iddmd).get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                da.setId(obj_jsn.get("id").toString());
                da.setVille(obj_jsn.get("ville").toString());
                da.setpreferences(obj_jsn.get("preferences").toString());
                da.setNbrChambres(JsonExtractIntegers(obj_jsn.get("preferences").toString(), "nbr_chambres"));
                da.setNbr_colocs(JsonExtractIntegers(obj_jsn.get("preferences").toString(), "nbr_colocs"));
                da.setPrix_minimal(JsonExtractFloats(obj_jsn.get("preferences").toString(), "prix_minimal"));
                da.setPrix_maximal(JsonExtractFloats(obj_jsn.get("preferences").toString(), "prix_maximal"));


                ////////////get date of birth as a json string and then converting it to date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                try {
                    da.setDateDmd(dateFormat.parse(obj_jsn.get("date_dmd_clc").toString()));
                    da.setDate1(dateFormat.parse(obj_jsn.get("date1").toString()));
                    da.setDate2(dateFormat.parse(obj_jsn.get("date2").toString()));


                } catch (ParseException e) {


                }
                return da;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //read all:
/////////get all  dmds  from db in a an arraylist with user id
    public ArrayList<DmdColoc> fetch_all() {
        ArrayList<DmdColoc> demandes = new ArrayList<DmdColoc>();

        String result = "";

        //DataTask db = new DataTask(null);

        String methode = "dmd_clcs";


        try {

            //result = db.execute(methode).get();
            JSONArray ary_jsn = new JSONArray(result);

            for (int i = 0; i < ary_jsn.length(); i++) {
                DmdColoc dc = new DmdColoc();

                JSONObject obj_jsn = ary_jsn.getJSONObject(i);
                dc.setId(obj_jsn.get("id").toString());
                dc.setVille(obj_jsn.get("ville").toString());
                dc.setpreferences(obj_jsn.get("preferences").toString());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                try {
/////////////////////////CONVRT JSON STRINGS DATES TO ACTUAL DATES
                    dc.setDateDmd(dateFormat.parse(obj_jsn.get("date_dmd_clc").toString()));
                    dc.setDate1(dateFormat.parse(obj_jsn.get("date1").toString()));
                    dc.setDate2(dateFormat.parse(obj_jsn.get("date2").toString()));

                } catch (ParseException e) {
                }
                demandes.add(dc);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return demandes;
    }
/////////////////////read all demmandes without filer:

    public ArrayList<DmdColoc> fetch_all_by_id(String id) {
        ArrayList<DmdColoc> demandes = new ArrayList<DmdColoc>();

        //  ArrayList<DmdColoc> demandes = null;

        String result = "";

        //DataTask db = new DataTask(null);

        String methode = "dmd_clcs";

        try {

            //demandes = db.execute(methode,id).get();
            //JSONArray ary_jsn = new JSONArray(result);

            for (int i = 0; i < 10; i++) {

                DmdColoc da = new DmdColoc();
                da.setVille("hah");
                da.setId("1");
                demandes.add(da);
            }


            //} catch (JSONException e) {
            //    e.printStackTrace();
        } catch (Exception e) {
            //  e.printStackTrace();
            // }


        }
        return demandes;
    }


//************************************2.DELETE****************************

    public void DeleteDdmd(String id)

    {
        String methode = "delete_dmd";


        try {

            // DataTask dt = new DataTask();
            // dt.execute(methode, id);

        } catch (Exception e) {


        }


    }

    //*******************************3.UPDATE**************************
    public DmdColoc UpdateDdmd(String id, String ville, String preferences, Date d1, Date d2)

    {
        String methode = "update_dmd";
        DmdColoc result = new DmdColoc();

        try {

            // DataTask dt = new DataTask();
            // result = dt.execute(id,ville,preferences,d1,d2).get();

        } catch (Exception e) {
        }
        return result;


    }

    ///////////////////Json Extract: extract integers, doubles and srtrings
    public int JsonExtractIntegers(String ch, String tag) {

        int nbcolocs = 0;


        try {

            JSONObject obj = new JSONObject(ch);

            JSONArray ja = obj.getJSONArray("prefneces");

            int n = ja.length();
            for (int i = 0; i < n; ++i) {
                final JSONObject blabla = ja.getJSONObject(i);
                nbcolocs = blabla.getInt(tag);

            }

        } catch (JSONException e) {
        }

        return nbcolocs;

    }


    public Double JsonExtractFloats(String ch, String tag) {

        Double p = 0.0;


        try {

            JSONObject obj = new JSONObject(ch);

            JSONArray ja = obj.getJSONArray("prefneces");

            int n = ja.length();
            for (int i = 0; i < n; ++i) {
                final JSONObject blabla = ja.getJSONObject(i);
                p = blabla.getDouble(tag);

            }

        } catch (JSONException e) {
        }

        return p;

    }

}