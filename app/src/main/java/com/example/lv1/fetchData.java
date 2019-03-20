package com.example.lv1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data="";
    String dataParsed = "";
    String strlatFeeds0 = "";
    String strlongFeeds0 = "";
    String strlatFeeds1 = "";
    String strlongFeeds1 = "";

    public static boolean latReady = false;
    public static boolean lngReady = false;
    public static double pickLat;
    public static double pickLng;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://api.thingspeak.com/channels/716456/feeds.json?api_key=QXSDP7OJ1I8Y9H0G&results=2");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null)
            {
                line = bufferedReader.readLine();
                data = data+line;
            }

            JSONObject JO = new JSONObject(data);
            JSONArray Feeds = (JSONArray) JO.get("feeds");
            JSONObject feed0 = (JSONObject) Feeds.get(0);
            JSONObject feed1 = (JSONObject) Feeds.get(1);
            strlatFeeds0 = "Feeds 0 Latitude :" + feed0.getString("field1") + "\n";
            strlongFeeds0 = "Feeds 0 Longitude :" + feed0.getString("field2") + "\n";
            strlatFeeds1 = "Feeds 1 Latitude :" + feed1.getString("field1") + "\n";
            strlongFeeds1 = "Feeds 1 Longitude :" + feed1.getString("field2") + "\n";

            if(feed1.getString("field1").equals("null")==false){
                pickLat = feed1.getDouble("field1");
                dataParsed = dataParsed + strlatFeeds1;
                latReady = true;
            } else if(feed0.getString("field1").equals("null")==false){
                pickLat = feed0.getDouble("field1");
                dataParsed = dataParsed + strlatFeeds0;
                latReady = true;
            } else{
                pickLat = -6.8881;
                lngReady = true;
                dataParsed = dataParsed + "ERROR\n";
            }
            if(feed1.getString("field2").equals("null")==false){
                pickLng = feed1.getDouble("field2");
                dataParsed = dataParsed + strlongFeeds1;
                lngReady = true;
            } else if(feed0.getString("field2").equals("null")==false){
                pickLng = feed0.getDouble("field2");
                dataParsed = dataParsed + strlongFeeds0;
                lngReady = true;
            } else{
                pickLng = 107.6001;
                lngReady = true;
                dataParsed = dataParsed + "ERROR";
            }
            //dataParsed = dataParsed+Feeds;
            //dataParsed = dataParsed + strlatFeeds0 + strlongFeeds0 + strlatFeeds1 + strlongFeeds1;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double getPickLat(){
        return pickLat;
    }
    public static double getPickLng(){
        return pickLng;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MapsActivity.data.setText(this.dataParsed);
    }
}
