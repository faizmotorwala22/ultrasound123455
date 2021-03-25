package com.example.drainage;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.drainage.Sama;

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

public class fetchdata extends AsyncTask<Void,Void,Void> {
        String data="";
        String level="";


@Override
protected Void doInBackground(Void... voids) {
        try {
        URL url = new URL("https://api.thingspeak.com/channels/1293964/fields/1.json?api_key=SPWU1EGZYVYFWWNL");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        while (line != null) {
        line = bufferedReader.readLine();
        data = data + line;
        }
        JSONArray jsonArray=new JSONArray(data);
        for (int i=0;i<jsonArray.length();i++)
        {
        JSONObject jsonObject= (JSONObject) jsonArray.get(i);
        JSONArray jA=jsonObject.getJSONArray("feeds");
        }


        } catch (MalformedURLException e) {

        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        } catch (JSONException e) {
        e.printStackTrace();
        }

    return null;

        }
        @Override
        protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Sama.data.setText(this.data);
        }

}

