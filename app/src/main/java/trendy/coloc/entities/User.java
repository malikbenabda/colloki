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

/**
 * Created by soumaya on 15/08/16.
 */


public class User {
    //attribus:
    private String id;
    private String email;
    private String pass;
    private String nom;
    private String prenom;
    private Date date_naiss;
    private String photo_profil;
    private String occupation;
    private String tel;
    private String etat_civil;
    private ArrayList<Critera> prefs;
    private String gender;
    private Date date_signup;
    private String adresse;

    //builder:
    public User() {
    }

    //getters;
    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPass() {
        return this.pass;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Date date_naiss() {
        return this.date_naiss;
    }

    public String getPhoto() {
        return this.photo_profil;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public String getTel() {
        return this.tel;
    }

    public String getEtat_civil() {
        return this.etat_civil;
    }

    public ArrayList<Critera> getprefs() {
        return this.prefs;
    }

    public String getGender() {
        return this.gender;
    }

    public Date getDate_signup() {
        return this.date_signup;
    }
/////setters:

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void set_date_naiss(Date d) {
        this.date_naiss = d;
    }

    public void setPhoto(String photo) {
        this.photo_profil = photo;
    }

    public void setAdresse(String a) {
        this.adresse = a;
    }

    public void setOccup(String occ) {
        this.occupation = occ;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setEtat_civ(String ec) {
        this.etat_civil = ec;
    }

    public void setprefs(ArrayList<Critera> prefs) {
        this.prefs = prefs;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDate_signup(Date ds) {
        this.date_signup = ds;
    }


    ///////////////////////////////////////CRUD*********************************************************
////////////////////////////////////////1.CREATE
    public User addUser(String id, String fname, String lname, Date birthday, String email, String password, String gender, String occupation, String tel,
                        String prefs, String photo) {
        String methode = "add_user";
        User user = new User();
        String ch = "";

        try {

            // DataTask dt = new DataTask();
            // ch=dt.execute( id, fname, lname, birthday,  email, password, gender , occupation , tel,
            // prefs,  photo).get();

            user = JsonToUser(ch);


        } catch (Exception e) {
        }
        return user;
    }////////////////////////////////////////2.READ:

    ///method to get user by his id:
    public User fetch_one(String id) {
        String ch = "";

        User user = new User();
        //DataTask db = new DataTask(ctx);

        String methode = "get_user";
        SimpleDateFormat ss;
        try {

            // ch = db.execute(methode, id).get();
            user = JsonToUser(ch);

        } catch (Exception e) {
        }
        return user;
    }

    ////////////////////////////////////2.UPDATE
    public User UpdateUser(String id, String fname, String lname, Date birthday, String email, String password, String gender, String occupation, String tel,
                           String prefs, String photo)


    {
        String methode = "update_usr";
        User user = new User();
        String ch = "";
        try {
            // DataTask dt = new DataTask();
            // ch = dt.execute( id, fname, lname, birthday,  email, password, gender , occupation , tel,
            // prefs,  photo).get();
            user = JsonToUser(ch);
        } catch (Exception e) {
        }
        return user;
    }
//////////////////////////////////////3.DELETE:

    public String DeleteUser(String id) {
        String methode = "delete_usr";
        String ch = "";
        String result = "";
        try {
            // DataTask dt = new DataTask(null);
            // ch=dt.execute(methode,id);
            result = JsnToString(ch);

        } catch (Exception e) {
        }
        return result;
    }

///////////////////////////////////////////fonctions divers:
    /////////////////////method to check if user id exists in DB:

    public User CheckUserExistsByKey(String key, String value) {

        String methode = "check_user_by_key";

        String ch = "";

        User user = new User();

        try {

            //DataTask db = new DataTask(null);

            //ch = db.execute(methode,key,value).get();

            user = JsonToUser(ch);

        } catch (Exception e) {
        }


        return user;
    }

//////////////////////function to add prefrences:

    public User addPrefs(String ch, String id) {
        String methode = "add_prefs";
        User user = new User();
        String chr = "";

        try {

            // DataTask dt = new DataTask();
            // chr = dt.execute(ch,id);
            user = JsonToUser(chr);

        } catch (Exception e) {


        }
        return user;

    }

    /////////get all users from db ina an arraylist:
    public ArrayList<User> fetch_all() {
        ArrayList<User> users = null;
        String ch = "";

        String methode = "users";

        try {
            //DataTask dt = new DataTaks(null);
            //ch=dt.execute().get()
            users = JsonToUsers(ch);

        } catch (Exception e) {
        }

        return users;
    }


    public boolean isFbId(String ch) {

        return ch.contains("fb");

    }
/////////////////////////////////////////JSON FUNCTIONS:


    /////////////////////GET DELET DEMANDE RESULT SUCCESS OR FAILURE:
    public String JsnToString(String ch) {

        String c = "";

        try {

            JSONArray ja = new JSONArray(ch);
            JSONObject jo = ja.getJSONObject(0);

            if (!jo.isNull("success"))

            {
                c = jo.getString("success");
            }
        } catch (JSONException e) {
        }


        return c;
    }

    //////////////////////////////////////////////function for count users:
    public int JsnToStringCount(String ch) {

        int c = 0;
        try {

            JSONArray ja = new JSONArray(ch);
            JSONObject jo = ja.getJSONObject(0);

            if (!jo.isNull("count"))

            {
                c = Integer.valueOf(jo.getString("count"));
            }
        } catch (JSONException e) {
        }
        return c;

    }

/////////////////////////////////function converts string to user object:


    public static User JsonToUser(String ch) {

        ArrayList<Critera> preferences = new ArrayList<Critera>();


        String id = "";
        String email = "";
        String pass = "";
        String nom = "";
        String prenom = "";
        Date date_naiss;
        String photo_profil = "";
        String occupation = "";
        String tel = "";
        String etat_civil = "";
        String prefs;
        String gender = "";
        Date date_signup;
        String adresse = "";

        User user = new User();


        try {
            JSONObject obj = new JSONObject(ch);

            date_naiss = new Date();

            JSONArray ja = obj.getJSONArray(ch);

            JSONArray ary_jsn = new JSONArray(ch);
            JSONObject ob = ary_jsn.getJSONObject(0);

            if (!ob.isNull("id")) {
//////////////////////////////////do the prefrences extraction by itself:
                id = ob.getString("id").toString();
                email = ob.getString("email").toString();
                pass = ob.getString("pass").toString();
                nom = ob.getString("nom").toString();
                prenom = ob.getString("prenom").toString();
                photo_profil = ob.getString("photo_profil").toString();
                occupation = ob.getString("occupation").toString();
                tel = ob.getString("tel").toString();
                etat_civil = ob.getString("etat_civil").toString();
                gender = ob.getString("gender").toString();
                adresse = ob.getString("adresse").toString();

                preferences = JsonPrefrences(ob.getString("preferences"));


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                try {
/////////////////////////CONVRT JSON STRINGS DATES TO ACTUAL DATES
                    date_naiss = (dateFormat.parse(ob.getString("birthday")));

                } catch (ParseException e) {
                }

            }

        } catch (JSONException je) {
        }

        user.setId(id);
        user.setEmail(email);
        user.setPass(pass);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setPhoto(photo_profil);
        user.setOccup(occupation);
        user.setTel(tel);
        user.setEtat_civ(etat_civil);
        user.setGender(gender);
        user.setAdresse(adresse);
        user.setprefs(preferences);
        return user;
    }

    public static ArrayList<User> JsonToUsers(String ch) {

        ArrayList<User> users = new ArrayList<User>();

        try {
            JSONArray ary_jsn = new JSONArray(ch);
            for (int i = 0; i < ary_jsn.length(); i++) {

                User user = new User();
                JSONObject jo = ary_jsn.getJSONObject(i);
                JsonToUser(jo.toString());
                users.add(user);
            }

        } catch (JSONException e) {
        }
        return users;
    }

    public static ArrayList<Critera> JsonPrefrences(String ch) {

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

    //////////////////////////////
    public int get_users_count() {

        int c = 0;
        String methode = "users_count";
        String ch = "";
        //DataTask dt = new Datatask();
        //ch=dt.execute(methode).get();
        JsnToStringCount(ch);
        return c;


    }


}
