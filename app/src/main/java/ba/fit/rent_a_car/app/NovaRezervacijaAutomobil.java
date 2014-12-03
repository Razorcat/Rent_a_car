package ba.fit.rent_a_car.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class NovaRezervacijaAutomobil extends ActionBarActivity {
    int RezervacijaID;
    String AutomobilID;
    String SlikaURL;
    static ImageView imgV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_rezervacija_automobil);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RezervacijaID = extras.getInt("RezervacijaID");
            AutomobilID = extras.getString("AutomobilID");
            SlikaURL = extras.getString("SlikaURL");
        }
        imgV=(ImageView)findViewById(R.id.imgViewNAuto);
        Picasso.with(getBaseContext()).load(SlikaURL).resize(550,435).into(imgV);
        Toast.makeText(this,"rez "+RezervacijaID+"- AutID "+AutomobilID,Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nova_rezervacija_automobil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
