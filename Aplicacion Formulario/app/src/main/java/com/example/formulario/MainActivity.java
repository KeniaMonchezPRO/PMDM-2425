package com.example.formulario;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity {


    private Button gardar;
    private Button limpar;
    private EditText nome, apelidos, mobil, email, contrasinal;
    private CheckBox publicidade;
    private ImageButton imaxe;
    private Button axuda;

    //probando xeolocalizacion:
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1; //codigo de permiso
    private FusedLocationProviderClient fusedLocationClient; //var para cliente del localizador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //inicializamos c/compoñente co seu id:
        gardar = findViewById(R.id.btn_gardar);
        limpar = findViewById(R.id.btn_limpar);
        nome = findViewById(R.id.edit_nome);
        apelidos = findViewById(R.id.edit_apelidos);
        mobil = findViewById(R.id.edit_mobil);
        email = findViewById(R.id.edit_email);
        contrasinal = findViewById(R.id.edit_contrasinal);
        publicidade = findViewById(R.id.check_publicidade);
        imaxe = findViewById(R.id.imaxe);
        imaxe.setImageResource(R.drawable.journey);
        axuda = findViewById(R.id.btn_axuda);

        /*gardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Enviado",Toast.LENGTH_SHORT).show();
            }
        });*/

        gardar.setOnClickListener(view -> getCurrentLocation()); //nos definimos este método
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome.getText().clear();
                apelidos.getText().clear();
                mobil.getText().clear();
                email.getText().clear();
                contrasinal.getText().clear();
                publicidade.setChecked(false);
            }
        });
    }

    private void getCurrentLocation() {
        // Verifica os permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Solicita os permisos
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Obten a ubicacion actual
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Toast.makeText(this, "Latitude: " + latitude + ", Lonxitude: "
                                    + longitude, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Nin idea de onde andas", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startActivityHelp(View v) {
        Intent intent = new Intent(MainActivity.this, ActivityHelp.class);
        startActivity(intent);
    }
}