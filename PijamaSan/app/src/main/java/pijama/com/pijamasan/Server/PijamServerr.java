package pijama.com.pijamasan.Server;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Yoav on 8/23/2015.
 */
public class PijamServerr {

    public void sendTemprature(String temprature)
    {
        JSONObject jsonObject = null;
        try {
            new Request("/sendtemp", temprature).execute();
        } catch (Exception e) {}
    }

    public void sendConnected()
    {
        JSONObject jsonObject = null;
        try {
            new Request("/connected", null).execute();
        } catch (Exception e) {}
    }



    private static class Request extends AsyncTask<String, String, JSONObject> {

        String request;
        String temp;

        public Request(String request, String temp) {
            this.request = request;
            this.temp = temp;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser json = new JSONParser();
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            Log.d("Loggggggggg",   Consts.REQUEST_PREFIX + "" + request);
            if(temp  != null)
                params.add(new BasicNameValuePair("temp", temp));
            JSONObject jObj = json.getJSONFromUrl(Consts.REQUEST_PREFIX + "" + request, params);

            return jObj;

        }

        @Override
        protected void onPostExecute(JSONObject json) {


        }
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}