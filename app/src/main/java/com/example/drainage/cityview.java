package com.example.drainage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ZoomButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.zip.Inflater;

public class cityview extends FragmentActivity implements OnMapReadyCallback {
    private static final float zoom = 15f;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityview);
        floatingActionButton = findViewById(R.id.fabvadodara);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(floatingActionButton);

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
                startActivity(new Intent(cityview.this, cityview.class));

                break;
            case R.id.Ahmedabaad:
                Toast.makeText(this, "Ahmedabad", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(cityview.this, Ahemdabad.class));
                break;
            case R.id.Surat:
                Toast.makeText(this, "Surat", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(cityview.this, Surat.class));
                break;

        }
        return false;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng Vadodara = new LatLng(22.311279, 73.178729);
        googleMap.addMarker(new MarkerOptions().title("Vadodara").position(Vadodara));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Vadodara,zoom));
    }

}