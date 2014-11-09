package ba.fit.rent_a_car.app;

import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Moje_rezervacije extends ActionBarActivity {
   Button btnKlijentID;
    int KlijentID;
    static ImageView imgV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moje_rezervacije);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            KlijentID=extras.getInt("KlijentID");
        }
        btnKlijentID=(Button)findViewById(R.id.btnKlijentID);
        btnKlijentID.setText("Klijent ID: "+KlijentID);

        imgV=(ImageView)findViewById(R.id.imgAuto);

        btnKlijentID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadImageFromURL("http://hci020.app.fit.ba/androidPHP/Slike/", imgV);
                Picasso.with(getBaseContext()).load("http://hci020.app.fit.ba/androidPHP/Slike/Golf-VI-GTD.jpg").into(imgV);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean loadImageFromURL(String fileUrl,ImageView iv){
        try {
            URL myFileUrl = new URL (fileUrl);
            HttpURLConnection conn =(HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            iv.setImageBitmap(BitmapFactory.decodeStream(is));

            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
