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

import ba.fit.rent_a_car.app.Narudzba;
import ba.fit.rent_a_car.app.R;

/**
 * Created by Zrinko on 9.11.2014..
 */
public class NarudzbaAdapter extends ArrayAdapter<Narudzba> {
    public NarudzbaAdapter(Context context, ArrayList<Narudzba> narudzba) {
        super(context, 0, narudzba);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Narudzba narudzba = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.nav_lst_item, parent, false);
        }

        TextView Naslov = (TextView) convertView.findViewById(R.id.txtNaslov);
        TextView Info = (TextView) convertView.findViewById(R.id.txtInfo);
        ImageView Slika=(ImageView) convertView.findViewById(R.id.iconCar);
        Naslov.setText(narudzba.getNaslov());
        Info.setText(narudzba.getInfo());
        Picasso.with(getContext()).load(narudzba.getSlikaURL()).resize(50,50).into(Slika);

        return convertView;
    }

    }
