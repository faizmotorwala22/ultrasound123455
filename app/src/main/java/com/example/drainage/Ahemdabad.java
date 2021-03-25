package com.example.drainage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
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

public class Ahemdabad extends FragmentActivity implements OnMapReadyCallback {
    private static final float zoom = 15f;
    FloatingActionButton floatingActionButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahemdabad);

        floatingActionButton1 = findViewById(R.id.fabahmedabad);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(this);

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
        getMenuInflater().inflate(R.menu.float_city, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Vadodara:
                Toast.makeText(this, "Vadodara", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Ahemdabad.this, cityview.class));

                break;
            case R.id.Ahmedabaad:
                Toast.makeText(this, "Ahmedabad", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Ahemdabad.this, Ahemdabad.class));


            case R.id.Surat:
                Toast.makeText(this, "Surat", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Ahemdabad.this, Surat.class));
                break;


        }
        return false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng ahemdabad = new LatLng(23.007390, 72.601657);
        googleMap.addMarker(new MarkerOptions().title("Ahmedabad").position(ahemdabad));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ahemdabad,zoom));

    }
}