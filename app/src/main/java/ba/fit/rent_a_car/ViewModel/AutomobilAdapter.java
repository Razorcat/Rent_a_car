package ba.fit.rent_a_car.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ba.fit.rent_a_car.app.Automobil;
import ba.fit.rent_a_car.app.Narudzba;
import ba.fit.rent_a_car.app.R;

/**
 * Created by Zrinko on 11.11.2014..
 */
public class AutomobilAdapter extends ArrayAdapter<Automobil> {
    // View lookup cache
    private static class ViewHolder {
        TextView Naslov;
        TextView Info;
        ImageView Slika;
    }
    public AutomobilAdapter(Context context, ArrayList<Automobil> automobil) {
        super(context, 0, automobil);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Automobil automobil = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.nav_lst_item, parent, false);

            viewHolder.Naslov = (TextView) convertView.findViewById(R.id.txtNaslov);
            viewHolder.Info = (TextView) convertView.findViewById(R.id.txtInfo);
            viewHolder.Slika=(ImageView) convertView.findViewById(R.id.iconCar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.Naslov.setText(automobil.getBoja());
        viewHolder.Info.setText(automobil.getGodinaProizvodnje());
        Picasso.with(getContext()).load(automobil.getSikaURL()).resize(150,150).into(viewHolder.Slika);

        return convertView;
    }
}
