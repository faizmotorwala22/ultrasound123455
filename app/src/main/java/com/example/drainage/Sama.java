package com.example.drainage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
import java.util.ArrayList;
import java.util.Map;

public class Sama extends FragmentActivity implements OnMapReadyCallback {
    private static final float zoom = 15f;
    FloatingActionButton floatingActionButton1;

    private static final String TAG = "";
    TextView t1, t2;
    private GoogleMap map;
    private String name1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sama);

        floatingActionButton1 = findViewById(R.id.fsama);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(this);


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(floatingActionButton1);

            }
        });
        final Thread thread = new Thread() {

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

        thread.start();


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.float_area, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Sama:
                Toast.makeText(this, "Sama", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Sama.this, Sama.class));

                break;
            case R.id.fatehgunj:
                Toast.makeText(this, "Fatehgunj", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Sama.this, Fatehgunj.class));
                break;
        }

        return false;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        LatLng sama = new LatLng(22.342811, 73.196277);
        googleMap.addMarker(new MarkerOptions().title("sama").position(sama));

        map.addMarker(new MarkerOptions().title("sama").position(sama).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sama, zoom));
        /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                LatLng chhanii = new LatLng(33.823083, 75.270572);
                googleMap.addMarker(new MarkerOptions().title("Chhani").position(chhanii).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("Chhani");
                //googleMap.addMarker(marker);
                //googleMap.addMarker(new MarkerOptions().title("Chhani").position(chhanii));

                new fetchdata1().execute();

                System.out.println(point.latitude+"---"+ point.longitude);
            }
        });*/


    }



    public class fetchdata1 extends AsyncTask<Void, Void, Void> {
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


    }



}


