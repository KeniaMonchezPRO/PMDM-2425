package com.example.formulario;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

        //probarPreferenciasCompartidas();
        cargarDatosAlForm();

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

    public void probarPreferenciasCompartidas() {
        //definimos la clase y creamos un editor q guarda la info
        SharedPreferences prefs = this.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //metodos para guardar datos primitos en clave,valor
        editor.putString("usuario", "monoreken");
        editor.putInt("clave", 123);
        editor.putBoolean("recordar_usuario", true);
        //apply para guardar esos datos declarados e inicializados anteriormente
        //dif entre apply e commit: si alguien intenta guardar muchos datos con SharedPreferences con put, tarda en persistirse, apply guarda en 2º plano, commit la guarda al momento, si tarda 2s en guardar la app "queda congelada"
        editor.apply();

        //leemos lo que hemos escrito, un valor por defecto en caso de que no lo encuentra o no exista el valor
        //get el 2º valor es el por defecto
        String nombreUsuario = prefs.getString("usuario", " ");
        int claveUsuario = prefs.getInt("clave", 1234);
        boolean recordarUsuario = prefs.getBoolean("recordarUsuario", false);

        //inicializa el atributo nombre de nuestro form, el log nos añade esa linea en nuestro logCat
        nome.setText(nombreUsuario);
        Log.d("prefs", "String: " + nombreUsuario);
        Log.d("prefs", "Int: " + claveUsuario);
        Log.d("prefs", "Boolean: " + recordarUsuario);
    }
    public void cargarDatosAlForm() {
           SharedPreferences prefs = this.getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
           SharedPreferences.Editor editor = prefs.edit();
           editor.putString("nome","John");
           editor.putString("apelido","Doe");
           editor.putString("email","johndoe@gmail.com");
           editor.putInt("mobil",123456789);
           editor.putString("contrasinal","matrix10");
           editor.putBoolean("publicidade",true);
           editor.apply();
           String nomeUsuario = prefs.getString("nome"," ");
           String apelidoUsuario = prefs.getString("apelido"," ");
           String emailUsuario = prefs.getString("email"," ");
           int mobilUsuario = prefs.getInt("mobil",0);
           String contrasinalUsuario = prefs.getString("contrasinal"," ");
           boolean publicidadeUsuario = prefs.getBoolean("publicidade",false);

           nome.setText(nomeUsuario);
           apelidos.setText(apelidoUsuario);
           email.setText(emailUsuario);
           mobil.setText(mobilUsuario);
           contrasinal.setText(contrasinalUsuario);
           publicidade.setChecked(publicidadeUsuario);
           Log.d("prefs","String: " + nomeUsuario);
           Log.d("prefs","String: " + apelidoUsuario);
           Log.d("prefs","String: " + emailUsuario);
           Log.d("prefs","Int: " + mobilUsuario);
           Log.d("prefs","String: " + contrasinalUsuario);
           Log.d("prefs","Boolean: " + publicidadeUsuario);
    }
}