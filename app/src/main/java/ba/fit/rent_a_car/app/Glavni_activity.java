package ba.fit.rent_a_car.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Glavni_activity extends ActionBarActivity {
    Button btnMojerezervacije;
    Button btnNovaRezervacija;
    String KlijentIDtemp;
    int KlijentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            KlijentID=extras.getInt("KlijentID");
            KlijentIDtemp=String.valueOf(KlijentID);
        }

        btnMojerezervacije=(Button)findViewById(R.id.btn_rezervacijeID);
        btnMojerezervacije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Glavni_activity.this,Moje_rezervacije.class);
                i.putExtra("KlijentID",KlijentID);
                startActivity(i);
            }
        });

        btnNovaRezervacija=(Button)findViewById(R.id.btn_novaRezervacijaID);
        btnNovaRezervacija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Glavni_activity.this,Nova_rezervacija.class);
                i.putExtra("KlijentID",KlijentID);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_glavni_activity, menu);
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
