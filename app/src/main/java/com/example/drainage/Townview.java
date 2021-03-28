package com.example.drainage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.Inflater;

public class Townview extends FragmentActivity implements OnMapReadyCallback {
    private static final float zoom=15f;
    FloatingActionButton floatingActionButton;
    private GoogleMap map;

   /* private static final String TAG = "";
    TextView t1, t2;

    private String name1;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_townview);
        floatingActionButton = findViewById(R.id.fabtown);
       /* t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);*/


        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);


        if (supportMapFragment == null) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(floatingActionButton);
                Log.d("mylog" , "it happens...");

            }
        });
       /* final Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(200);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {



                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        new fetchdata1().execute();
                                        System.out.println("first");}




                                }, 5000);



                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();*/


    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.float_town, menu);
        //getMenuInflater().inflate(R.menu.float_town, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Chhani:
                Toast.makeText(this, "Chhani", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Townview.this,chhani.class));

                break;
            case R.id.Savli:
                Toast.makeText(this, "Savli", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Townview.this, Savli.class));
                break;
        }
        return false;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng vadodara= new LatLng(22.33852835, 73.21193260);
        googleMap.addMarker(new MarkerOptions().title("vadodara").position(vadodara));

        map.addMarker(new MarkerOptions().title("vadodara").position(vadodara).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vadodara, zoom));

    }


   /* public class fetchdata1 extends AsyncTask<Void, Void, Void> {
        String data = "";
        String level = "";
        public String name, strAmount;
        float in2;
        int in1;
        float in,hun;


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //URL url = new URL("https://api.thingspeak.com/channels/1293964/fields/1.json?api_key=SPWU1EGZYVYFWWNL");
                URL url = new URL("https://api.thingspeak.com/channels/1293964/feeds/last?api_key=SPWU1EGZYVYFWWNL");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                //JSONArray jsonArray = new JSONArray(data);

                JSONObject jsonObject = new JSONObject(data);

                String name = jsonObject.getString("field1");
                name1 = name;
                float number = Float.parseFloat(name);
                System.out.println(number);
                in2=number;

                String strAmount = String.valueOf(number);
                System.out.println(strAmount);
                float hun= (float) 100.00;

                try {
                    int in1 =Integer.parseInt(name);
                    System.out.println(in1);


                    System.out.println(name);
                } catch (NumberFormatException ex) { // handle your exception

                }


                //JSONArray jA = jsonObject.getJSONArray("field1");
                //Log.d("mylog", "map");


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
            System.out.println("the value of in2"+in2);

            if (in2 >= 100.00) {

                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);


                t1.setText("OverFlow");
                t2.setText(name1);
                // t2.setText(Float.toString(in));
                //t2.setText(String.format("%.03f",in));
                t1.setTextColor(getResources().getColor(R.color.red));
                t2.setTextColor(getResources().getColor(R.color.red));
                LatLng chhanii = new LatLng(33.823083, 75.270572);
                map.addMarker(new MarkerOptions().title("Chhani").position(chhanii).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            }

            else  {
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t1.setText("CONTROL");
                t2.setText(name1);
                t1.setTextColor(getResources().getColor(R.color.green));
                t2.setTextColor(getResources().getColor(R.color.green));
                // t2.setText(Float.toString(in));
                LatLng chhanii2 = new LatLng(33.823083, 75.270572);
                map.addMarker(new MarkerOptions().title("Chhani").position(chhanii2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            }

        }


    }*/



}
