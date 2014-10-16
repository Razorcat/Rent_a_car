package ba.fit.rent_a_car.app;

//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent= new Intent(this,Moje_rezervacije.class);       //prije svega potrebno import Intent

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void btnLogin_onClick(View view){ //mora biti public i primati view,

        TextView txtUsername = (TextView) findViewById(R.id.txtUsername);
        String strLogin =txtUsername.getText().toString();  //ovo sivo kad podvlačii,ne brini ,šali se android

       TextView txtPassword= (TextView) findViewById(R.id.txtPassword);
        String strPassword= txtPassword.getText().toString();

        if(strLogin.startsWith("S") && strPassword.startsWith("S")){

            Intent intent= new Intent(this,Moje_rezervacije.class);       //prije svega potrebno import Intent
            intent.putExtra("login",strLogin);
            startActivity(intent);

    }
        else {

            //public void alertMessage (String Message)
            //{
                Toast.makeText(this, "Pogrešan username/password", Toast.LENGTH_LONG).show();
            //}

        }

    }

}
