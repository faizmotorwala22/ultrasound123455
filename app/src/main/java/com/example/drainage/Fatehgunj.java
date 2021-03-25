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

public class Fatehgunj extends FragmentActivity implements OnMapReadyCallback {
    private static final float zoom = 15f;
    FloatingActionButton floatingActionButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatehgunj);
        floatingActionButton1 = findViewById(R.id.ffatehgunj);
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
        getMenuInflater().inflate(R.menu.float_area, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fatehgunj:
                Toast.makeText(this, "Fatehgunj", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Fatehgunj.this, Fatehgunj.class));
                break;
            case R.id.Sama:
                Toast.makeText(this, "Sama", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Fatehgunj.this,Sama.class));
                break;
        }

        return false;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng fatehgunj = new LatLng(22.324999, 73.188981);
        googleMap.addMarker(new MarkerOptions().title("Fatehgunj").position(fatehgunj));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fatehgunj,zoom));

    }
}