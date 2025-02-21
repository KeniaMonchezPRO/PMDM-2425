package com.example.formulario;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.formulario.broadcastReceivers.WifiReceiver;
import com.example.formulario.services.MiServicio;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import Dao.AppDataBase;
import Dao.UsuarioDao;
import Entities.Usuario;


public class MainActivity extends BaseActivity {

    private Button gardar;
    private Button limpar;
    private EditText nome, apelidos, mobil, email, contrasinal;
    private CheckBox publicidade;
    private ImageButton imaxe;
    private Button axuda;

    private WifiReceiver wifiReceiver;

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

        //gardar.setOnClickListener(view -> getCurrentLocation()); //nós definimos este método
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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

        gardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
                Usuario usuario = new Usuario("Kenny","kenny@gmail.com");
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });


        //probarPreferenciasCompartidas();
        cargarDatosAlForm();

        /*//almaceamento interno
        escribirAlmacenamentoInterno("arquivo.txt", "Exemplo de almacenamento interno en Android");
        lerAlmacenamentoInterno("arquivo.txt");
        localizarArquivo("arquivo.txt");

        //almaceamento externo
        gardarAlmaceamentoExterno();
        lerAlmaceamentoExterno();*/

        probarRoom();

        //Fragments:
        //coje e inyecta el fragmento del menu inferior
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contenedor_fragment, new BottomMenuFragment());
        transaction.commit();

        //iniciar servizo
        //crea un intent y le pasamos la clase servicio
        Intent intent = new Intent(this, MiServicio.class);
        startService(intent);

        //broadcast receiver, este ejemplo nos avisa en caso de que nos quedemos sin wifi
        wifiReceiver = new WifiReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiReceiver, filter);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void probarRoom() {
        AppDataBase bd = AppDataBase.getInstance(this);
        UsuarioDao usuarioDao = bd.usuarioDao();

        //Usuario nuevoUsuario = new Usuario("María Sanxe","ms@gmail.com");
        Usuario nuevoUsuario = new Usuario("María","Sanxe","ms@gmail.com","623456789","abc123.",true);
        usuarioDao.insertar(nuevoUsuario);

        List<Usuario> usuarios = usuarioDao.obtenerTodos();
        for (Usuario u : usuarios) {
            Log.d("Base de datos de room: ", "Usuario: " + u.getNombre() + " - " + u.getApelidos() + " - " + u.getEmail() + " - " + u.getMobil() + " - " + u.getContrasinal() + " - " + u.isPublicidade());
            nome.setText(u.getNombre());
            apelidos.setText(u.getApelidos());
            email.setText(u.getEmail());
            mobil.setText(u.getMobil());
            contrasinal.setText(u.getContrasinal());
            publicidade.setChecked(u.isPublicidade());
        }

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
           editor.putString("mobil","123456789");
           editor.putString("contrasinal","matrix10");
           editor.putBoolean("publicidade",true);
           editor.apply();
           String nomeUsuario = prefs.getString("nome"," ");
           String apelidoUsuario = prefs.getString("apelido"," ");
           String emailUsuario = prefs.getString("email"," ");
           String mobilUsuario = prefs.getString("mobil"," ");
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

    private void escribirAlmacenamentoInterno(String nomeArquivo, String contido) {

        try {
            //abrimos arquivo e escribimos o contido
            FileOutputStream arquivo = openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
            arquivo.write(contido.getBytes());
            arquivo.close();

            Log.d("AlmacenamentoInterno", "Arquivo gardado OK");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AlmacenamentoInterno", "Erro ao gardar");
        }
    }

    private void lerAlmacenamentoInterno(String nomeArquivo) {
        try {
            FileInputStream fis = openFileInput(nomeArquivo);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            //ler liña a liña
            StringBuilder contido = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                contido.append(linha).append("\n");
            }

            br.close();
            fis.close();

            // Mostrar el contenido en el log
            Log.d("AlmacenamentoInterno", "Contido:  " + contido.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AlmacenamentoInterno", "Erro ao ler o arquivo");
        }
    }

    private void localizarArquivo(String arquivo) {
        File archivo = new File(getFilesDir(), "archivo.txt");
        Log.d("AlmacenamentoInterno", "Ruta do arquivo: " + archivo.getAbsolutePath());
    }


    private void gardarAlmaceamentoExterno() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //si podemos escribir o si tenemos permisos de escritura... crea un novo archivo e escribimos nel
        if (isExternalStorageWritable()) {
            File file = new File(getExternalFilesDir(null), "hello all.txt"); // Carpeta privada
            try {
                FileWriter writer = new FileWriter(file);
                writer.append("Hola Mundo!");
                writer.append("\r\nEste arquivo pode verlo todo dios!");
                writer.close();
                Log.d("MainActivity", "Arquivo escrito: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("MainActivity", "O almaceamento externo non está dispoñibel.");
        }

    }

    private boolean isExternalStorageWritable() {
        //nos devuelve el estado de nuestro almacenamiento
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private boolean isExternalStorageReadable() {
        //nos mira si podemos ler del sistema de almacenamiento
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private void lerAlmaceamentoExterno() {
        if (isExternalStorageReadable()) {
            File file = new File(getExternalFilesDir(null), "hello all.txt");
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    Log.d("MainActivity", "Contenido del archivo: " + stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("MainActivity", "Archivo no encontrado.");
            }
        } else {
            Log.e("MainActivity", "No se puede leer desde almacenamiento externo.");
        }
    }
}