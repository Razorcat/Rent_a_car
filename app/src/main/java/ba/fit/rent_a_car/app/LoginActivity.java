package ba.fit.rent_a_car.app;

import java.util.ArrayList;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    static String user = "3";
    Button btn_login;
    EditText txt_username;
    EditText txt_password;
    int klijentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicijalizacija inputa, referencirat input iz xml-a sa ovim iz .java klase
        setContentView(R.layout.activity_login);

        txt_username = (EditText) findViewById(R.id.txtUsername);
        txt_password = (EditText) findViewById(R.id.txtPassword);

        Toast.makeText(this,"Dobrodošli!",Toast.LENGTH_SHORT).show();

        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( !AppStatus.getInstance(LoginActivity.this).isOnline())
                 Toast.makeText(LoginActivity.this,"Niste povezani na mrežu!",Toast.LENGTH_SHORT).show();
                else {
                if (txt_username.getText().length() > 0 && txt_password.getText().length() > 0) {
                    DoPOST mDoPOST = new DoPOST(LoginActivity.this);
                    mDoPOST.execute("http://hci020.app.fit.ba/androidPHP/db_login.php",
                            txt_username.getText().toString(), txt_password .getText().toString());
                }    }
            }
        });
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
                String username = arg0[1];
                String password = arg0[2];

                //lista keyova i valuea
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("password", password));
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

                String username = jsonObject.getString("username");
                klijentID =jsonObject.getInt("id");

                if (username != "null") {
                    user=username;
                    finish();
                    Intent i = new Intent(LoginActivity.this,Glavni_activity.class);
                    i.putExtra("KlijentID",klijentID);
                    startActivity(i);
                } else {
                    throw new Exception();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(LoginActivity.this, "Neispravni podaci! ",Toast.LENGTH_LONG).show();
            }
        }
    }
}
