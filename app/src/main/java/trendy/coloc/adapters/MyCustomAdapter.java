package trendy.coloc.adapters;

/**
 * Created by malik on 30-Aug-16.
 */


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import trendy.coloc.R;
import trendy.coloc.entities.Annonce;
import trendy.coloc.tools.AnnonceTools;
import trendy.coloc.tools.ConverterTools;

public class MyCustomAdapter extends ArrayAdapter<Annonce> {

    Context context;
    int layoutResourceId;
    ArrayList<Annonce> data = new ArrayList<Annonce>();

    public MyCustomAdapter(Context context, int layoutResourceId,
                           ArrayList<Annonce> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AnnonceHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new AnnonceHolder();
            holder.titleTV = (TextView) row.findViewById(R.id.rowTitle);
            holder.dateTV = (TextView) row.findViewById(R.id.rowDate);
            holder.prixTV = (TextView) row.findViewById(R.id.rowPrix);
            holder.coverImageIV = (ImageView) row.findViewById(R.id.rowImage);
            row.setTag(holder);
        } else {
            holder = (AnnonceHolder) row.getTag();
        }
        Annonce annonce = data.get(position);
        holder.titleTV.setText(annonce.getTitre());
        holder.prixTV.setText(Float.toString(annonce.getPrix()));
        String date = "de: " + ConverterTools.DateToString(annonce.getStartDate()) + " à " + ConverterTools.DateToString(annonce.getEndDate());
        holder.dateTV.setText(date);
        //   String imageurl = Image.getCoverImageByIdAnnonce(annonce.getId(),context).getUrl();
        String imageurl = AnnonceTools.URL;
        Picasso.with(context).load(imageurl).into(holder.coverImageIV);

        return row;

    }

    static class AnnonceHolder {
        TextView titleTV;
        TextView prixTV;
        TextView dateTV;
        ImageView coverImageIV;

    }


}
