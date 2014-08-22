package ba.fit.rent_a_car.app;

//import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.res.TypedArray;
import java.util.ArrayList;

import ba.fit.rent_a_car.ViewModel.NavigationDrawerItem;
import ba.fit.rent_a_car.ViewModel.NavigationDrawerListAdapter;
//import android.widget.TextView;

public class GlavniActivity extends Activity {


    private DrawerLayout NavDrawerLayout;
    private ListView NavDraverListView;
    private ActionBarDrawerToggle NavDrawerToogle;

    private CharSequence NavDrawerTitle;
    private CharSequence AppTitle;

    private String[] NavDrawerItemTitles;
    private TypedArray NavDrawerIcons;

    private ArrayList<NavigationDrawerItem> NavDrawerItems;
    private NavigationDrawerListAdapter NavDrawerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni);

        this.AppTitle = this.getTitle();
        this.NavDrawerTitle = this.getTitle();

        this.NavDrawerItemTitles = this.getResources().getStringArray(R.array.nav_drawer_strings);
        this.NavDrawerIcons = this.getResources().obtainTypedArray(R.array.nav_drawer_icons);

        this.NavDrawerItems.add(new NavigationDrawerItem(NavDrawerItemTitles[0], NavDrawerIcons.getResourceId(0, -1)));
        this.NavDrawerItems.add(new NavigationDrawerItem(NavDrawerItemTitles[1], NavDrawerIcons.getResourceId(1, -1)));
        this.NavDrawerItems.add(new NavigationDrawerItem(NavDrawerItemTitles[2], NavDrawerIcons.getResourceId(2, -1)));
//        this.NavDrawerItems.add(new NavigationDrawerItem(NavDrawerItemTitles[1],NavDrawerIcons.getResourceId(3,-1)));

        NavDrawerIcons.recycle();

        NavDraverListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DisplayFragment(i);
                setTitle(NavDrawerItemTitles[i]);
                NavDraverListView.setItemChecked(i, true);
                NavDrawerLayout.closeDrawer(NavDraverListView);

            }
        });

        this.NavDrawerAdapter = new NavigationDrawerListAdapter(this.getApplicationContext(), NavDrawerItems);
        this.NavDraverListView.setAdapter(NavDrawerAdapter);

        this.getActionBar().setDisplayHomeAsUpEnabled(true);
        this.getActionBar().setHomeButtonEnabled(true);
        this.NavDrawerToogle = new ActionBarDrawerToggle(this,this.NavDrawerLayout, R.drawable.ic_navigation_drawer, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(NavDrawerTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(AppTitle);
                invalidateOptionsMenu();
            }
        };

        this.NavDrawerLayout.setDrawerListener(this.NavDrawerToogle);

        if (savedInstanceState == null) {
            DisplayFragment(0);
        }
    }


    private void DisplayFragment(int position) {
        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment = new PregledVozila_Fragment();
                break;

            case 1:
                fragment = new RezervacijaVozila_Fragment();
                break;
            case 2:
                fragment = new PregledRezervacija_Fragment();
                break;

            default:
                break;

            if (fragment != null)
                this.getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            else
                Log.e("24 hours rent", "Unable to create fragment !");

        }

    }


//       String strUsername= getIntent().getExtras().getString("login");
//        TextView txtDobrodosao =(TextView) findViewById(R.id.txt);
//        txtDobrodosao.setText("Dobrodosli :" + strUsername);


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(this.NavDrawerLayout.isDrawerOpen(this.NavDraverListView));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.NavDrawerToogle.syncState();
    }

    @Override
    public void setTitle(CharSequence title) {
        this.getActionBar().setTitle(title);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.glavni, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (this.NavDrawerToogle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /*public void Mojerezervacije(View view) { //mora biti public i primati view,
        Intent intent = new Intent(this, Moje_rezervacije.class);
        startActivity(intent);
    }
    */
    public void btnLogin_onClick(View view) { //mora biti public i primati view,
        Intent intent = new Intent(this, PregledVozilaActivity.class);
        startActivity(intent);


    }
}


