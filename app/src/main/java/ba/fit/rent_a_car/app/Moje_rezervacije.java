package ba.fit.rent_a_car.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import org.json.JSONArray;
import java.util.ArrayList;

import ba.fit.rent_a_car.ViewModel.NarudzbaAdapter;

public class Moje_rezervacije extends Activity {
    Button btnKlijentID;
    int KlijentID;
    static ImageView imgV;
    String KlijentIDtemp;
    TextView INFO;


    ArrayList<Narudzba> nar;//=new ArrayList<Narudzba>();
    ListView listView;
    NarudzbaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moje_rezervacije);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            KlijentID=extras.getInt("KlijentID");
            KlijentIDtemp=String.valueOf(KlijentID);
        }
        btnKlijentID=(Button)findViewById(R.id.btnKlijentID);
        btnKlijentID.setText("Klijent ID: "+KlijentID);

        imgV=(ImageView)findViewById(R.id.imgAuto);
        INFO=(TextView)findViewById(R.id.txtINFO);
        INFO.setText("JSON: ");

        listView = (ListView) findViewById(R.id.listViewNarudzbe);

        DoPOST mDoPOST = new DoPOST(Moje_rezervacije.this);
        mDoPOST.execute("http://hci020.app.fit.ba/androidPHP/db_getRezervacije.php", KlijentIDtemp);

        btnKlijentID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getBaseContext()).load("http://hci020.app.fit.ba/androidPHP/Slike/Golf-VI-GTD.jpg").into(imgV);
                //adapter.add(nar2);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Click ListItem Number " + i, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"ID: "+nar.get(i).getAutomobilID(),Toast.LENGTH_SHORT).show();
                Picasso.with(getBaseContext()).load(nar.get(i).getSlikaURL()).into(imgV);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pozicija=i;
                AlertDialog.Builder builder = new AlertDialog.Builder(Moje_rezervacije.this);
                builder.setCancelable(true);
                builder.setTitle("Poništi rezervaciju?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nar.remove(pozicija);
                        adapter=new NarudzbaAdapter(Moje_rezervacije.this,nar);
                        listView.setAdapter(adapter);
                    }
                }).setNegativeButton("Ne",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ništa ne radi
                    }
                });
                builder.show();
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.moje_rezervacije, menu);
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
            Intent i = new Intent(Moje_rezervacije.this,LoginActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.nova_rezervacija) {
            Intent i = new Intent(Moje_rezervacije.this,Nova_rezervacija.class);
            i.putExtra("KlijentID",KlijentID);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // prosiruje asyncTask<stoJaSaljem,PrimaIteracija,PrimaKrajRezultat>
    public class DoPOST extends AsyncTask<String, Void, String> {
        Context mContext = null;

        DoPOST(Context context) {
            mContext = context;
        }
        //niz stringova prima
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String klijentID = arg0[1];

                //lista keyova i valuea
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("klijent_id", klijentID));
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
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(result);
                nar=new ArrayList<Narudzba>();
                nar.addAll(Narudzba.fromJson(jsonArray));
                adapter = new NarudzbaAdapter(Moje_rezervacije.this, nar);
                listView.setAdapter(adapter);

                if (nar.size()<=0)
                    btnKlijentID.setText("Lista: 0");
                if(jsonArray.length()<=0){
                    INFO.setText("JSON: 0");
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
        Toast.makeText(Moje_rezervacije.this,"RESUME!",Toast.LENGTH_SHORT).show();
        DoPOST mDoPOST = new DoPOST(Moje_rezervacije.this);
        mDoPOST.execute("http://hci020.app.fit.ba/androidPHP/db_getRezervacije.php", KlijentIDtemp);
    }

}
