package trendy.coloc.entities;

/**
 * Created by soumaya on 23/08/16.
 */
public class Prefrences {

    private int nombre_chambres;
    private int nombre_colocs;
    private Double prix_min;
    private Double prix_max;
    private String Custom_Prefs;


    public void set_nombre_chambres(int nombre) {
        this.nombre_chambres = nombre;

    }

    public void set_nombre_colocs(int nombre) {
        this.nombre_colocs = nombre;

    }

    public int get_nombre_chambres() {

        return this.nombre_chambres;

    }

    public int getNombre_colocs() {

        return this.nombre_colocs;

    }

    public void set_prix_max(Double prix) {
        this.prix_max = prix;

    }

    public void set_prix_min(Double prix) {
        this.prix_min = prix;
    }

    public Double get_prix_max() {
        return this.prix_max;
    }

    public Double get_prix_min() {
        return this.prix_min;

    }


}
