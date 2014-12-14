package ba.fit.rent_a_car.app;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by X on 14.12.2014.
 */
public class HttpManager {
    private InputStream inputStream;
    private HttpClient httpClient;
    private HttpResponse httpResponse;
    private HttpPost httpPost;
    private HttpEntity httpEntity;
    private StringBuilder result;


    public HttpManager(){

        this.inputStream = null;
        this.httpClient = null;
        this.httpResponse = null;
        this.httpPost = null;
        this.httpEntity = null;
        this.result = null;

    }

    public String getResponseFromUrl(String url, List<NameValuePair> params)
    {

        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (Exception e) {
            // TODO: handle exception
            //e.getMessage();
            e.printStackTrace();
            //
        }


        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),8);
            this.result = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {

                result.append(line + "\n");
            }

            inputStream.close();
            return result.toString();

        } catch (Exception e) {
            // TODO: handle exception

            //Log.e("Buffering error, ",e.getMessage());
        }



        return null;
    }


}
