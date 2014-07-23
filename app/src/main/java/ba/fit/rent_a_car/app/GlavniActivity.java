package ba.fit.rent_a_car.app;

//import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class GlavniActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni);

        String strUsername= getIntent().getExtras().getString("login");
        TextView txtDobrodosao =(TextView) findViewById(R.id.txtDobrodosao);
        txtDobrodosao.setText("Dobrodosli :" + strUsername);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.glavni, menu);
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

    /*public void Mojerezervacije(View view) { //mora biti public i primati view,
        Intent intent = new Intent(this, Moje_rezervacije.class);
        startActivity(intent);
    }
    */
    public void btnLogin_onClick(View view) { //mora biti public i primati view,
        Intent intent = new Intent(this,PregledVozilaActivity.class);
        startActivity(intent);


    }
}
