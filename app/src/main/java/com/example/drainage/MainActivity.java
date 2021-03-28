package com.example.drainage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;

import static com.karumi.dexter.Dexter.withActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText email, pass;
    TextView forgetpass;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button login;
    FirebaseFirestore firebaseFirestore;
    int spinvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent=new Intent(MainActivity.this,cityview.class);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if (permissionDeniedResponse.isPermanentlyDenied()){
                            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Permission Denied");
                            builder.setMessage("Permission to Access Location is permanantly Denied You need to go to settings to Allow the permission");
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent= new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.fromParts("package",getPackageName(), null));

                                }
                            });
                        }else {
                            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                    }
                })
                .check();

        mAuth = FirebaseAuth.getInstance();
        spinner = findViewById(R.id.spinnner);
        email = findViewById(R.id.elemail);
        pass = findViewById(R.id.elpass);
        forgetpass = findViewById(R.id.fpass);
        login = findViewById(R.id.Login);
        progressBar = findViewById(R.id.progressbar);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Authority, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        //Global global = (Global) getApplicationContext();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* startActivity(new Intent(MainActivity.this, Areawise.class));
                finish();*/


                String semail = email.getText().toString();
                String spass = pass.getText().toString();
                if (TextUtils.isEmpty(semail)) {
                    email.setError("Cannot be empty");
                }
                if (TextUtils.isEmpty(spass)) {
                    pass.setError("Cannot be empty");
                }
                mAuth.signInWithEmailAndPassword(semail, spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Global global = (Global)getApplicationContext();
                            global.setEmail(semail);
                            firebaseFirestore = FirebaseFirestore.getInstance();
                            firebaseFirestore.collection("user").document(global.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int value = Integer.parseInt(task.getResult().getString("flag"));
                                        progressBar.setVisibility(View.GONE);
                                        if (value == spinvalue)
                                        {
                                            if (value == 0) {
                                                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(MainActivity.this, cityview.class));
                                                finish();
                                            }
                                            if (value == 1) {
                                                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(MainActivity.this, Townview.class));
                                                finish();
                                            }
                                            if (value == 2) {
                                                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(MainActivity.this, Areawise.class));
                                                finish();
                                            }

                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetpass=new EditText(v.getContext());
                final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Reset Password ?");
                builder.setMessage("Enter email to reset password");
                builder.setView(resetpass);

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetpass.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Reset Link sent to your Mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error ! link not sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        switch (item) {
            case "City":
                spinvalue = 0;
                break;
            case "Town Incharge":
                spinvalue = 1;
                break;
            case "Area Incharge":
                spinvalue = 2;
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }




}