package com.example.drainage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Map;

public class Sama extends FragmentActivity implements OnMapReadyCallback {
    private static final float zoom = 15f;
    FloatingActionButton floatingActionButton1;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng samaa = new LatLng(22.342811, 73.196277);
    LatLng ganeshdeep = new LatLng(22.348078, 73.192583);
    LatLng chandanpark = new LatLng(22.349338, 73.192819);
    LatLng bansidhar = new LatLng(22.348733, 73.194196);
    float water = (float) 6.6;
     public static TextView data;
    int Permission_Request_Code = 1;
    String No;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;

    String url = "https://api.thingspeak.com/channels/1293964/fields/1.json?api_key=SPWU1EGZYVYFWWNL";
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sama);
        data = findViewById(R.id.text);
        floatingActionButton1 = findViewById(R.id.fsama);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(this);
        arrayList.add(samaa);
        arrayList.add(ganeshdeep);
        arrayList.add(chandanpark);
        arrayList.add(bansidhar);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Toast.makeText(this, "outside if ", Toast.LENGTH_SHORT).show();

        if (water > 5.0) {
            Toast.makeText(this, "inside if ", Toast.LENGTH_SHORT).show();

            if (checkpermission(Manifest.permission.SEND_SMS)) {

                fetch();
                onsend();



            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, Permission_Request_Code);
                Toast.makeText(this, "permission denied" + water, Toast.LENGTH_SHORT).show();
            }

        }

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(floatingActionButton1);

            }
        });
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
        for (int i = 0; i < arrayList.size(); i++) {
            googleMap.addMarker(new MarkerOptions().title("Sama").position(arrayList.get(i)).title("Sama"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayList.get(i), zoom));
        }
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(Sama.this, "Sama area", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void onsend() {

        Global global = (Global) getApplicationContext();
        Toast.makeText(this, "" + global.getEmail(), Toast.LENGTH_SHORT).show();
        firebaseFirestore.collection("user").document(global.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    No = task.getResult().getString("Contact");
                    Toast.makeText(Sama.this, "contact" + No, Toast.LENGTH_SHORT).show();

                    // Toast.makeText(global, "data"+process.execute(), Toast.LENGTH_SHORT).show();
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(No, null, "https://www.google.com/maps/search/?api=1&query=22.3578876,73.1939609", null, null);
                } else {
                    Toast.makeText(Sama.this, "sorry contact cant be retrived", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Sama.this, "add on failure", Toast.LENGTH_SHORT).show();
            }

        });
        Toast.makeText(global, "calling fetch", Toast.LENGTH_SHORT).show();
    }


    public boolean checkpermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    public void fetch() {
            fetchdata process=new fetchdata();
            process.execute();
    }
}


