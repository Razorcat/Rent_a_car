package ba.fit.rent_a_car.app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ba.fit.rent_a_car.ViewModel.AutomobilAdapter;
import ba.fit.rent_a_car.ViewModel.NarudzbaAdapter;


public class Nova_rezervacija extends ActionBarActivity {
    Button btnRezervacija;
    ArrayList<Automobil> Automobili=new ArrayList<Automobil>();
    ListView listView;
    AutomobilAdapter adapter;
    static ImageView imgV;
    int tempPozicija=0;
    int KlijentID;
    int RezervacijaID;
    String KlijentIDtemp;
    String RezervacijaIDtemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_rezervacija);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            KlijentID = extras.getInt("KlijentID");
            KlijentIDtemp=String.valueOf(KlijentID);
        }
        Toast.makeText(Nova_rezervacija.this,"klijentID: "+KlijentID,Toast.LENGTH_SHORT).show();

        btnRezervacija=(Button)findViewById(R.id.btnRezerviraj);

        listView = (ListView) findViewById(R.id.listViewAutomobili);
        imgV=(ImageView)findViewById(R.id.imgVSlikaAuta);

        DoPOSTnovaRezervacija novarerzervacija=new DoPOSTnovaRezervacija(Nova_rezervacija.this);
        novarerzervacija.execute("http://hci020.app.fit.ba/androidPHP/db_novaRezervacija.php",KlijentIDtemp);

        DoGETMaxRezervaciju getMaxRezervaciju=new DoGETMaxRezervaciju(Nova_rezervacija.this);
        getMaxRezervaciju.execute("http://hci020.app.fit.ba/androidPHP/db_getMaxIdRezervaciju.php");

        DoGetSlobodneAutomobile mDoPOST = new DoGetSlobodneAutomobile(Nova_rezervacija.this);
        mDoPOST.execute("http://hci020.app.fit.ba/androidPHP/db_getSlobodneAutomobile.php");

        btnRezervacija.setText("RezID: "+RezervacijaID);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Click ListItem Number " + i +"-autID "+Automobili.get(i).automobilID, Toast.LENGTH_SHORT).show();
                tempPozicija=i;
                Picasso.with(getBaseContext()).load(Automobili.get(i).getSikaURL()).resize(350,300).into(imgV);
                finish();
                Intent it = new Intent(Nova_rezervacija.this,NovaRezervacijaAutomobil.class);
                it.putExtra("RezervacijaID",RezervacijaID);
                it.putExtra("AutomobilID",Automobili.get(i).getAutomobilID());
                it.putExtra("SlikaURL",Automobili.get(i).getSikaURL());
                startActivity(it);
            }
        });
        btnRezervacija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRezervacija.setText("RezID: "+ RezervacijaID);
                if(KlijentID>0 && RezervacijaID>0 && tempPozicija>0) {/// greška!!!!!!
                    RezervacijaIDtemp=String.valueOf(RezervacijaID);
                    String AutomobilIDTemp=String.valueOf(tempPozicija);
                    //sql(Automobili.get(i).getAutomobilID());
                   // DoPOST3 mDoPOST3 = new DoPOST3(Nova_rezervacija.this);
                   // mDoPOST3.execute("http://hci020.app.fit.ba/androidPHP/db_dodajRezervacijuAutomobilu.php",AutomobilIDTemp,RezervacijaIDtemp);
                    Automobili.remove(tempPozicija);
                    adapter=new AutomobilAdapter(Nova_rezervacija.this,Automobili);
                    listView.setAdapter(adapter);
                    tempPozicija=0;
                }
            }
        });

    }
//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nova_rezervacija, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout) {
            finish();
            Intent i = new Intent(Nova_rezervacija.this,LoginActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // prosiruje asyncTask<stoJaSaljem,PrimaIteracija,PrimaKrajRezultat>
    public class DoGetSlobodneAutomobile extends AsyncTask<String, Void, String> {
        Context mContext = null;

        DoGetSlobodneAutomobile(Context context) {
            mContext = context;
        }
        //niz stringova prima
        @Override
        protected String doInBackground(String... arg0) {
            try {
                /*String klijentID = arg0[1];

                //lista keyova i valuea
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("klijent_id", klijentID));*/
                // Create the HTTP request

                HttpParams httpParameters = new BasicHttpParams();
                // Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                //instanciranje post-a
                HttpPost httppost = new HttpPost(arg0[0]);

               // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(result);
                Automobili.addAll(Automobil.fromJson(jsonArray));
                adapter = new AutomobilAdapter(Nova_rezervacija.this, Automobili);
                listView.setAdapter(adapter);

                if (Automobili.size()<=0)
                    Toast.makeText(Nova_rezervacija.this,"Prazno automobili",Toast.LENGTH_SHORT);
                if(jsonArray.length()<=0){
                    Toast.makeText(Nova_rezervacija.this,"JSON: "+jsonArray.length(),Toast.LENGTH_SHORT);
                }

                else {
                    throw new Exception();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                //Toast.makeText(LoginActivity.this, "Neispravni podaci! ",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DoPOSTnovaRezervacija novarerzervacija=new DoPOSTnovaRezervacija(Nova_rezervacija.this);
        novarerzervacija.execute("http://hci020.app.fit.ba/androidPHP/db_novaRezervacija.php",KlijentIDtemp);

        DoGetSlobodneAutomobile mDoPOST = new DoGetSlobodneAutomobile(Nova_rezervacija.this);
        mDoPOST.execute("http://hci020.app.fit.ba/androidPHP/db_getRezervacije.php", KlijentIDtemp);
        Toast.makeText(Nova_rezervacija.this,"RESUME!",Toast.LENGTH_SHORT).show();
    }

    // prosiruje asyncTask<stoJaSaljem,PrimaIteracija,PrimaKrajRezultat>
    public class DoPOSTnovaRezervacija extends AsyncTask<String, Void, String> {
        Context mContext = null;

        DoPOSTnovaRezervacija(Context context) {
            mContext = context;
        }
        //niz stringova prima
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String klijent_id = arg0[1];

                //lista keyova i valuea
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("klijent_id", klijent_id));
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
            try {
                jsonObject = new JSONObject(result);
                RezervacijaID =jsonObject.getInt("id");
                btnRezervacija.setText("RezID: "+RezervacijaID);
                Toast.makeText(Nova_rezervacija.this,"rezID "+RezervacijaID,Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public class DoGETMaxRezervaciju extends AsyncTask<String, Void, String> {
        Context mContext = null;

        DoGETMaxRezervaciju(Context context) {
            mContext = context;
        }
        //niz stringova prima
        @Override
        protected String doInBackground(String... arg0) {
            try {
               /* String klijent_id = arg0[1];

                //lista keyova i valuea
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("klijent_id", klijent_id));
                // Create the HTTP request  */
                HttpParams httpParameters = new BasicHttpParams();
                // Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                //instanciranje post-a
                HttpPost httppost = new HttpPost(arg0[0]);

               // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
            try {
                jsonObject = new JSONObject(result);
                RezervacijaID =jsonObject.getInt("id");
                btnRezervacija.setText("RezID: "+RezervacijaID);
                Toast.makeText(Nova_rezervacija.this,"rezID "+RezervacijaID,Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}

