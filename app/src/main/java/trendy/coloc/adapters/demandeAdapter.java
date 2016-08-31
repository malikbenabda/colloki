package trendy.coloc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import trendy.coloc.DemandeColocationActivityShow;
import trendy.coloc.R;
import trendy.coloc.entities.DemandeColocation;

/**
 * Created by soumaya on 29/08/16.
 */
public class demandeAdapter extends ArrayAdapter<DemandeColocation> {
    Context context;
    int layoutResourceId;
    ArrayList<DemandeColocation> data = new ArrayList<DemandeColocation>();

    public demandeAdapter(Context context, int layoutResourceId,
                          ArrayList<DemandeColocation> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.textVille = (TextView) row.findViewById(R.id.textView1);
            holder.textId = (TextView) row.findViewById(R.id.textView2);

            holder.btnEdit = (Button) row.findViewById(R.id.button1);

            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        final DemandeColocation cd = data.get(position);
        holder.textId.setText(cd.getId());
        //  holder.textVille.setText(cd.getVille());
        //  holder.textLocation.setText(c.getdat_dmd_clc());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, DemandeColocationActivityShow.class);


                //  SaveSharedPreferenceForDemande.setDemandeId(context, cd.getId());

                context.startActivity(i);


            }
        });

        return row;

    }

    static class UserHolder {
        TextView textId;
        TextView textVille;

        Button btnEdit;

    }
}

