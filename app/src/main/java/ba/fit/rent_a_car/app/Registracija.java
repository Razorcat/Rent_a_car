package ba.fit.rent_a_car.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;


public class Registracija extends ActionBarActivity {
    //deklaracija parametara
    Button btn_RegistracijaF;
    EditText Ime;
    EditText Prezime;
    EditText Adresa;
    EditText Korisnicko_ime;
    EditText Lozinka;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);


        Ime = (EditText) findViewById(R.id.txt_Ime);
        Prezime = (EditText) findViewById(R.id.txt_Prezime);
        Adresa = (EditText) findViewById(R.id.txt_Adresa);
        Korisnicko_ime = (EditText) findViewById(R.id.txt_Korisnicko_ime);
        Lozinka = (EditText) findViewById(R.id.txt_Lozinka);

        btn_RegistracijaF=(Button)findViewById(R.id.btn_RegistracijaForm);
        btn_RegistracijaF.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick (View arg0){

             if (Ime.getText().length() == 0 || Prezime.getText().length() == 0 || Adresa.getText().length() == 0 || Korisnicko_ime.getText().length() == 0 || Lozinka.getText().length() == 0)
                 Toast.makeText(getApplicationContext(), "Imate prazna polja.", Toast.LENGTH_SHORT).show();

             else {
                 /* List<NameValuePair> params = new ArrayList<NameValuePair>();
                   params.add(new BasicNameValuePair("Ime", Ime.getText().toString()));
                   params.add(new BasicNameValuePair("Prezime", Prezime.getText().toString()));
                   params.add(new BasicNameValuePair("Adresa", Adresa.getText().toString()));
                   params.add(new BasicNameValuePair("Korisnicko_Ime", Korisnicko_ime.getText().toString()));
                   params.add(new BasicNameValuePair("Lozinka", Lozinka.getText().toString()));

                   String responseFromUrl = new HttpManager().getResponseFromUrl("http://hci025.app.fit.ba/androidPHP/db_registracijaKorisnika.php", params);

                   Toast.makeText(getApplicationContext(), Ime.getText().toString(), Toast.LENGTH_SHORT).show(); */
                   DoPOST registracija =new DoPOST(Registracija.this);
                   registracija.execute("http://hci020.app.fit.ba/androidPHP/db_registracijaKorisnika.php",Ime.getText().toString(),Prezime.getText().toString(),
                            Adresa.getText().toString(),Korisnicko_ime.getText().toString(),Lozinka.getText().toString());
                   }
                 }
             }
        );
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registracija, menu);
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
            Intent i = new Intent(Registracija.this, LoginActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class DoPOST extends AsyncTask<String, Void, String> {
        Context mContext = null;

        DoPOST(Context context) {
            mContext = context;
        }

        //niz stringova prima
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String Ime = arg0[1];
                String Prezime = arg0[2];
                String Adresa = arg0[3];
                String KorisnickoIme = arg0[4];
                String Lozinka = arg0[5];

                //lista keyova i valuea
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Ime", Ime));
                nameValuePairs.add(new BasicNameValuePair("Prezime", Prezime));
                nameValuePairs.add(new BasicNameValuePair("Adresa", Adresa));
                nameValuePairs.add(new BasicNameValuePair("Korisnicko_Ime", KorisnickoIme));
                nameValuePairs.add(new BasicNameValuePair("Lozinka", Lozinka));
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
           // JSONObject jsonObject;
            try {
              //  jsonObject = new JSONObject(result);

                Toast.makeText(Registracija.this,"Novi Korisnik dodan",Toast.LENGTH_SHORT).show();

            } catch (Exception ex) {
                ex.printStackTrace();
              //  Toast.makeText(Registracija.this, "Neispravni podaci! ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
