package trendy.coloc.tools;

public class Critera {


    public Critera() {
    }

    private String id;
    private String tag;
    private String description;


    public String getId() {
        return this.id;
    }

    public String getTag() {
        return this.tag;
    }

    public String getdesc() {
        return this.description;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTag(String t) {
        this.tag = t;
    }

    public void setdesc(String d) {
        this.description = d;
    }

////////////////function that builds json array:

    public String buildJson(String ch1, String ch2) {

        String result = "";
        result = "{" + ch1 + ":" + ch2 + "}";

        return result;

        /////appel: cretera.buildjson(creteria.tag,criteria.description)
    }


}
