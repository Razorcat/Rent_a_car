package ba.fit.rent_a_car.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Zrinko on 11.11.2014..
 */
public class Automobil {
    String SikaURL;
    String Boja;
    String GodinaProizvodnje;
    String automobilID;
    String NazivProzvodjaca;
    String NazivModela;
    String Gorivo;
    String Transmisija;
    int CijenaPoDanu;

    public String getGorivo() {
        return Gorivo;
    }

    public void setGorivo(String gorivo) {
        Gorivo = gorivo;
    }

    public String getTransmisija() {
        return Transmisija;
    }

    public void setTransmisija(String transmisija) {
        Transmisija = transmisija;
    }

    public int getCijenaPoDanu() {
        return CijenaPoDanu;
    }

    public void setCijenaPoDanu(int cijenaPoDanu) {
        CijenaPoDanu = cijenaPoDanu;
    }

    public String getNazivProzvodjaca() {
        return NazivProzvodjaca;
    }

    public void setNazivProzvodjaca(String nazivProzvodjaca) {
        NazivProzvodjaca = nazivProzvodjaca;
    }

    public String getNavizModela() {
        return NazivModela;
    }

    public void setNavizModela(String nazivModela) {
        NazivModela = nazivModela;
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

    public String getAutomobilID() {
        return automobilID;
    }

    public void setAutomobilID(String automobilID) {
        this.automobilID = automobilID;
    }

    // Constructor to convert JSON object into a Java class instance
    public Automobil(JSONObject object){
        try {
            this.Boja = object.getString("Boja");
            this.SikaURL=object.getString("Slika");
            this.automobilID=object.getString("autoID");
            this.NazivProzvodjaca=object.getString("Naziv_proizvodjaca");
            this.NazivModela=object.getString("Naziv_Modela");
            this.GodinaProizvodnje=object.getString("Godina_proizvodnje");
            this.Gorivo=object.getString("Gorivo");
            this.Transmisija=object.getString("Transmisija");
            this.CijenaPoDanu=object.getInt("cijenaPoDanu");
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
