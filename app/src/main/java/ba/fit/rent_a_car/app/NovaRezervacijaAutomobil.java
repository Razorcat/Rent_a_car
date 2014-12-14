package ba.fit.rent_a_car.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;


public class NovaRezervacijaAutomobil extends ActionBarActivity {
    int RezervacijaID;
    String AutomobilID;
    String SlikaURL;
    static ImageView imgV;
    Button btnRezerviraj;
    TextView txtGorivo;
    TextView txtTransmisija;
    TextView txtGodina;
    TextView txtCijena;
    String Gorivo;
    String Transmisija;
    int CijenaPoDanu;
    String GodinaProizvodnje;
    int KlijentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_rezervacija_automobil);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RezervacijaID = extras.getInt("RezervacijaID");
            AutomobilID = extras.getString("AutomobilID");
            SlikaURL = extras.getString("SlikaURL");
            GodinaProizvodnje=extras.getString("GodinaProizvodnje");
            Gorivo=extras.getString("Gorivo");
            Transmisija = extras.getString("Transmisija");
            CijenaPoDanu = extras.getInt("Cijena");
            KlijentID =extras.getInt("KlijentID");
        }
       // Toast.makeText(this,"AutoID "+AutomobilID,Toast.LENGTH_SHORT).show();

        txtGodina=(TextView)findViewById(R.id.txtAutoGodina);
        txtCijena=(TextView)findViewById(R.id.txtCijena);
        txtGorivo=(TextView)findViewById(R.id.txtGorivo);
        txtTransmisija=(TextView)findViewById(R.id.txtTransmisija);

        txtGorivo.setText("Gorivo: "+Gorivo);
        txtTransmisija.setText("Transmisija: "+Transmisija);
        txtCijena.setText("Cijena: " + CijenaPoDanu+" KM");
        txtGodina.setText("Godina: "+ GodinaProizvodnje);


        btnRezerviraj=(Button)findViewById(R.id.btnRezervirajAuto);
        imgV=(ImageView)findViewById(R.id.imgViewNAuto);
        Picasso.with(getBaseContext()).load(SlikaURL).resize(550,435).into(imgV);
       // Toast.makeText(this,"rez "+RezervacijaID+"- AutID "+AutomobilID,Toast.LENGTH_LONG).show();

        btnRezerviraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NovaRezervacijaAutomobil.this);
                builder.setCancelable(true);
                builder.setTitle("Rezervirati automobil?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String RezID= String.valueOf(RezervacijaID);
                        DoSetRezervaciju SetRezervaciju=new DoSetRezervaciju(NovaRezervacijaAutomobil.this);
                        SetRezervaciju.execute("http://hci020.app.fit.ba/androidPHP/db_dodajRezervacijuAutomobilu.php",AutomobilID,RezID);

                    }
                }).setNegativeButton("Ne",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ništa ne radi
                    }
                });
                builder.show();


            }
        });
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
        if (id == R.id.logout) {
            finish();
            Intent i = new Intent(NovaRezervacijaAutomobil.this,LoginActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // prosiruje asyncTask<stoJaSaljem,PrimaIteracija,PrimaKrajRezultat>
    public class DoSetRezervaciju extends AsyncTask<String, Void, String> {
        Context mContext = null;

        DoSetRezervaciju(Context context) {
            mContext = context;
        }
        //niz stringova prima
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String AutomobilID = arg0[1];
                String RezervacijaID = arg0[2];

                //lista keyova i vag
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("automobil_id", AutomobilID));
                nameValuePairs.add(new BasicNameValuePair("rezervacija_id", RezervacijaID));
                // Create the HTTP request

                HttpParams httpParameters = new BasicHttpParams();
                // Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                //instanciranje post-a
                HttpPost httppost = new HttpPost(arg0[0]);

                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);

                return result;

            } catch (Exception e) {
                Log.e("Rent a car", "Error:", e);
                return "";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            // Update the UI
            // Create a JSON object from the request response
            JSONObject jsonObject;

            String RezID= String.valueOf(RezervacijaID);
            DoUpdateRezervaciju rez=new DoUpdateRezervaciju(NovaRezervacijaAutomobil.this);
            rez.execute("http://hci020.app.fit.ba/androidPHP/db_updateRezervacijaAutoID.php",AutomobilID,RezID);
            Toast.makeText(NovaRezervacijaAutomobil.this,"Uspješno ste rezervirali automobil!",Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(NovaRezervacijaAutomobil.this,Glavni_activity.class);
            i.putExtra("KlijentID",KlijentID);
            startActivity(i);
            try {
                jsonObject = new JSONObject(result);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public class DoUpdateRezervaciju extends AsyncTask<String, Void, String> {
        Context mContext = null;

        DoUpdateRezervaciju(Context context) {
            mContext = context;
        }
        //niz stringova prima
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String AutomobilID = arg0[1];
                String RezervacijaID = arg0[2];

                //lista keyova i valuea
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("automobil_id", AutomobilID));
                nameValuePairs.add(new BasicNameValuePair("rezervacija_id", RezervacijaID));
                // Create the HTTP request

                HttpParams httpParameters = new BasicHttpParams();
                // Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                //instanciranje post-a
                HttpPost httppost = new HttpPost(arg0[0]);

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);

                return result;

            } catch (Exception e) {
                Log.e("Rent a car", "Error:", e);
                return "";
            }
        }
       // @Override
       /* protected void onPostExecute(String result) {
            // Update the UI
            // Create a JSON object from the request response
            JSONObject jsonObject;
            Toast.makeText(NovaRezervacijaAutomobil.this,"Uspješno ste rezervirali automobil!",Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(NovaRezervacijaAutomobil.this,Nova_rezervacija.class);
            startActivity(i);
            try {
                jsonObject = new JSONObject(result);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }*/

    }

}
