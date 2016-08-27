package trendy.coloc.entities;

/**
 * Created by malik on 24-Aug-16.
 */
public class Property {
    public String getKey() {
        return key;
    }

    public Property(String key, String value, int order) {
        this.key = key;
        this.value = value;
        this.order = order;
    }

    public Property(String key, String value) {
        this.key = key;
        this.value = value;

    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getIdvalue() {
        return idvalue;
    }

    public void setIdvalue(int idvalue) {
        this.idvalue = idvalue;
    }

    public int getIdkey() {
        return idkey;
    }

    public void setIdkey(int idkey) {
        this.idkey = idkey;
    }

    public Property() {
    }

    public Property(String key, String value, int order, int idvalue, int idkey) {

        this.key = key;
        this.value = value;

        this.order = order;
        this.idvalue = idvalue;
        this.idkey = idkey;
    }

    private String key;
    private String value;

    private int order = 0;

    private int idvalue;
    private int idkey;
    private int removebtn;

    public int getRemovebtn() {
        return removebtn;
    }

    private int idOptionLayout;

    public int getIdOptionLayout() {
        return idOptionLayout;
    }

    public void setIdOptionLayout(int idOptionLayout) {
        this.idOptionLayout = idOptionLayout;
    }

    public void setRemovebtn(int removebtn) {
        this.removebtn = removebtn;
    }
}
