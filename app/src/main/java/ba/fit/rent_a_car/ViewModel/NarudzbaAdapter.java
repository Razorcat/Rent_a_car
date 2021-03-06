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
    // View lookup cache
    private static class ViewHolder {
        TextView Naslov;
        TextView Info;
        ImageView Slika;
    }
    public NarudzbaAdapter(Context context, ArrayList<Narudzba> narudzba) {
        super(context, 0, narudzba);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Narudzba narudzba = getItem(position);
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

        viewHolder.Naslov.setText(narudzba.getNaslov());
        viewHolder.Info.setText(narudzba.getInfo());
        Picasso.with(getContext()).load(narudzba.getSlikaURL()).resize(150,150).into(viewHolder.Slika);

        return convertView;
      }

    }
