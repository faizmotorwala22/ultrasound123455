package com.example.drainage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.zip.Inflater;

public class Townview extends FragmentActivity implements OnMapReadyCallback {
    private static final float zoom=15f;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_townview);
        floatingActionButton = findViewById(R.id.fabtown);


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
        LatLng vadodara= new LatLng(22.33852835, 73.21193260);
        googleMap.addMarker(new MarkerOptions().position(vadodara));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vadodara,zoom));

    }
}
