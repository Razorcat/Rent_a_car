package ba.fit.rent_a_car.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import ba.fit.rent_a_car.app.R;

public class NavigationDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavigationDrawerItem>NavDrawerItems;

    public NavigationDrawerListAdapter(Context context, ArrayList<NavigationDrawerItem> navDrawerItems) {
        this.context = context;
        NavDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return NavDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return NavDrawerItems.get(position); //on je pisao i
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nav_lst_item,null);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.iconCar);
        TextView title = (TextView) convertView.findViewById(R.id.txtNaslov);
        TextView counter = (TextView) convertView.findViewById(R.id.txtInfo);

        icon.setImageResource(NavDrawerItems.get(position).getIcon());
        title.setText(NavDrawerItems.get(position).getLabelNaziv());

        if (NavDrawerItems.get(position).isCounterVisible())
            counter.setText(NavDrawerItems.get(position).getCounter()); //  counter.setText(NavDrawerItems.get(position).getCounter().toString());
        else
        counter.setVisibility(View.GONE);

        return convertView;
    }
}
