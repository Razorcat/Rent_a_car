package ba.fit.rent_a_car.app;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Zrinko on 9.11.2014..
 */
public class Narudzba {
    public String Naslov;
    public String Info;
    public ImageView Slika;
    public String SlikaURL;
    public String automobilID;
    public String datumRezervacije;
    public int CijenaPoDanu;

    public String getDatumRezervacije() {
        return datumRezervacije;
    }

    public void setDatumRezervacije(String datumRezervacije) {
        this.datumRezervacije = datumRezervacije;
    }

    public int getCijenaPoDanu() {
        return CijenaPoDanu;
    }

    public void setCijenaPoDanu(int cijenaPoDanu) {
        CijenaPoDanu = cijenaPoDanu;
    }

    public String getSlikaURL() {
        return SlikaURL;
    }

    public void setSlikaURL(String slikaURL) {
        SlikaURL = slikaURL;
    }

    public ImageView getSlika() {
        return Slika;
    }

    public void setSlika(ImageView slika) {
        Slika = slika;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getNaslov() {
        return Naslov;
    }

    public String getAutomobilID() {
        return automobilID;
    }

    public void setAutomobilID(String automobilID) {
        this.automobilID = automobilID;
    }

    public void setNaslov(String naslov) {
        Naslov = naslov;
    }

    public Narudzba(String naslov,String info,String slikaURL,String AutomobilID){
        this.Naslov=naslov;
        this.Info=info;
        this.SlikaURL=slikaURL;
        this.automobilID=AutomobilID;
    }

    // Constructor to convert JSON object into a Java class instance
    public Narudzba(JSONObject object){
        try {
            this.Naslov = object.getString("Naziv_proizvodjaca");
            this.Info = object.getString("Naziv_modela");
            this.SlikaURL=object.getString("Slika");
            this.automobilID=object.getString("id");
            this.CijenaPoDanu = object.getInt("cijenaPoDanu");
            this.datumRezervacije= object.getString("datumRezervacije");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // Narudzba.fromJson(jsonArray);
    public static ArrayList<Narudzba> fromJson(JSONArray jsonObjects) {
        ArrayList<Narudzba> narudzbe = new ArrayList<Narudzba>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                narudzbe.add(new Narudzba(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return narudzbe;
    }

}
