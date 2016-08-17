package trendy.coloc.entities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import trendy.coloc.backgroundtask.DataTask;

/**
 * Created by malik on 16-Aug-16.
 */
public class Image {

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    private Context ctx;

    private int id;
    private boolean cover;
    private String url;
    private String titre;
    private int idAnnonce;

    public Image(int id) {
        this.id = id;
    }

    public Image() {
    }

    public Image(int id, boolean cover, String url, String titre, int idAnnonce) {
        this.id = id;
        this.cover = cover;
        this.url = url;
        this.titre = titre;
        this.idAnnonce = idAnnonce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public Image getCover(int idAnnonce) {
        return new Image();
    }
//*****************************************************************************
    //Services


    public ArrayList<Image> getAllByIdAnnonce(int idAnnonce) {
        ArrayList<Image> images = new ArrayList<Image>();
        String result;
        Image image = new Image();
        DataTask db = new DataTask(ctx);

        String methode = "getAllByIdAnnonce";

        try {

            result = db.execute(methode).get();
            JSONArray ary_jsn = new JSONArray(result);

            for (int i = 0; i < ary_jsn.length(); i++) {
                JSONObject obj_jsn = ary_jsn.getJSONObject(i);
                image.setId((Integer) obj_jsn.get("id"));
                image.setIdAnnonce((Integer) obj_jsn.get("idAnnonce"));
                image.setTitre(obj_jsn.get("titre").toString());
                image.setUrl(obj_jsn.get("url").toString());

                if (obj_jsn.get("cover").toString().equals("1")) {
                    image.setCover(true);
                } else {
                    image.setCover(false);
                }

                images.add(image);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return images;
    }

    public Image getOneByIdAnnonce(int idAnnonce) {
        String result;
        Image image = new Image();
        DataTask db = new DataTask(ctx);

        String methode = "getOneByIdAnnonce";

        try {

            result = db.execute(methode, idAnnonce+"").get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                image.setId((Integer) obj_jsn.get("id"));
                image.setIdAnnonce((Integer) obj_jsn.get("idAnnonce"));
                image.setTitre(obj_jsn.get("titre").toString());
                image.setUrl(obj_jsn.get("url").toString());

                if (obj_jsn.get("cover").toString().equals("1")) {
                    image.setCover(true);
                } else {
                    image.setCover(false);
                }

                return image;
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

    public Image updateToCover(int idAnnonce, int id) {
        String result;
        Image image = new Image();
        DataTask db = new DataTask(ctx);

        String methode = "updateToCover";

        try {

            result = db.execute(methode, idAnnonce+"",id+"").get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                image.setId((Integer) obj_jsn.get("id"));
                image.setIdAnnonce((Integer) obj_jsn.get("idAnnonce"));
                image.setTitre(obj_jsn.get("titre").toString());
                image.setUrl(obj_jsn.get("url").toString());

                if (obj_jsn.get("cover").toString().equals("1")) {
                    image.setCover(true);
                } else {
                    image.setCover(false);
                }

                return image;
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

    public Image updateImage(int id, boolean cover, String url, String titre, int idAnnonce) {
        String result;
        String c;
        Image image = new Image();
        DataTask db = new DataTask(ctx);
        if ( cover) c="1" ;else c="0";
        String methode = "updateImage";

        try {

            result = db.execute(methode, id+"",c,url,titre,idAnnonce+"" ).get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                image.setId((Integer) obj_jsn.get("id"));
                image.setIdAnnonce((Integer) obj_jsn.get("idAnnonce"));
                image.setTitre(obj_jsn.get("titre").toString());
                image.setUrl(obj_jsn.get("url").toString());

                if (obj_jsn.get("cover").toString().equals("1")) {
                    image.setCover(true);
                } else {
                    image.setCover(false);
                }

                return image;
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

    public Boolean removeImage(int id) {
        String result;

        DataTask db = new DataTask(ctx);
        String methode = "removeImage";

        try {

            result = db.execute(methode, id+"").get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);
            String response =  obj_jsn.get("response").toString();
            if ( response.equalsIgnoreCase("success")) return true;
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


    public Image addImage(boolean cover, String url, String titre, int idAnnonce) {
        String result;
        String c;
        Image image = new Image();
        DataTask db = new DataTask(ctx);
        if ( cover) c="1" ;else c="0";
        String methode = "addImage";

        try {

            result = db.execute(methode,c,url,titre,idAnnonce+"" ).get();

            JSONArray ary_jsn = new JSONArray(result);
            JSONObject obj_jsn = ary_jsn.getJSONObject(0);

            if (!obj_jsn.isNull("id")) {
                image.setId((Integer) obj_jsn.get("id"));
                image.setIdAnnonce((Integer) obj_jsn.get("idAnnonce"));
                image.setTitre(obj_jsn.get("titre").toString());
                image.setUrl(obj_jsn.get("url").toString());

                if (obj_jsn.get("cover").toString().equals("1")) {
                    image.setCover(true);
                } else {
                    image.setCover(false);
                }

                return image;
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















}
