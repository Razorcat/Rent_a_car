package ba.fit.rent_a_car.app;

//import android.support.v7.app.ActionBarActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
//import android.app.Activity;

public class PregledVozilaActivity extends ListActivity {



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
          //  setContentView(R.layout.activity_pregled_vozila);////////////////////////////
            String[] str=new  String[]{"Opel","Renault","Bendžo" };
            setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str));

        }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object O = this.getListAdapter().getItem(position);
        String stri = O.toString();
        Toast.makeText(this,"Odabrali ste" + stri,Toast.LENGTH_LONG).show();

    }
    //////////////////////////////// nije ovo sigurno,al za potrebe I parc

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.pregled_vozila, menu);
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


}
