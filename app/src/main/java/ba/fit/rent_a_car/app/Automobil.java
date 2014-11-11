package ba.fit.rent_a_car.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Zrinko on 11.11.2014..
 */
public class Automobil {
    int Zauzeto;
    String SikaURL;
    String Boja;
    String GodinaProizvodnje;

    public int getZauzeto() {
        return Zauzeto;
    }

    public void setZauzeto(int zauzeto) {
        Zauzeto = zauzeto;
    }

    public String getSikaURL() {
        return SikaURL;
    }

    public void setSikaURL(String slika) {
        SikaURL = slika;
    }

    public String getBoja() {
        return Boja;
    }

    public void setBoja(String boja) {
        Boja = boja;
    }

    public String getGodinaProizvodnje() {
        return GodinaProizvodnje;
    }

    public void setGodinaProizvodnje(String godinaProizvodnje) {
        GodinaProizvodnje = godinaProizvodnje;
    }

    // Constructor to convert JSON object into a Java class instance
    public Automobil(JSONObject object){
        try {
            this.Zauzeto = object.getInt("Zauzeto");
            this.Boja = object.getString("Boja");
            this.SikaURL=object.getString("Slika");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // Narudzba.fromJson(jsonArray);
    public static ArrayList<Automobil> fromJson(JSONArray jsonObjects) {
        ArrayList<Automobil> automobili = new ArrayList<Automobil>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                automobili.add(new Automobil(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return automobili;
    }
}
