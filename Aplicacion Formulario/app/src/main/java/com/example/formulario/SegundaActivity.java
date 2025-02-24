package com.example.formulario;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Entities.Usuario;

public class SegundaActivity extends BaseActivity {

    private TextView nombre;
    private TextView email;

    //foto
    private ImageView imageView; //para mostrar la foto
    private String currentPhotoPath; //la ruta de la foto
    private static final int REQUEST_CAMERA_PERMISSION = 100; //solicita permismo para usar la camara

    //video
    private VideoView videoView;
    private String currentVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        //configurando fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contenedor_fragment, new BottomMenuFragment());
        transaction.commit();

        //esperamos recibir de la mainactivity un usuario por eso lo creamos
        Usuario usuario = getIntent().getParcelableExtra("usuario");

        //si la ocmunicacion fue fructifera o no
        if(usuario != null) {
            Log.d("Usuario","None: " + usuario.getNombre() + ", Mail: " + usuario.getEmail());
            nombre = findViewById(R.id.tv_nombre);
            email = findViewById(R.id.tv_email);

            nombre.setText(usuario.getNombre());
            email.setText(usuario.getEmail());
        }

        //camara
        imageView = findViewById(R.id.imageView);
        Button btnCapture = findViewById(R.id.btnCapture);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //miramos si tenemos permisos para acceder a la camara, en caso de que no, pedimos permiso, en caso de que si llamamos openCamera()
                if (ContextCompat.checkSelfPermission(SegundaActivity.this, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SegundaActivity.this,
                            new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    openCamera();
                }
            }
        });

        //video
        videoView = findViewById(R.id.videoView);
        Button btnCaptureVideo = findViewById(R.id.btnCaptureVideo);

        // Capturar Video
        btnCaptureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraForVideo();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() { //para devolver el layout de la activity
        return R.layout.activity_segunda;
    }

    // camara: abrir
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //llama a la camara y en caso que se produzca un error muestra mensaje, sino la almacena en la ruta establecida
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error al crear el archivo", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.formulario.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    // camara: crear imaxe
    //intenta crear un arquivo de tipo imagen que nos devuelve un File
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //indicamos patron de la fecha
        //el nombre
        String imageFileName = "JPEG_" + timeStamp + "_";
        //el path en el que lo almacenamos
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //definimos formato de la imagen
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        //la ruta completa en la que guardaremos la imagen
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        imageView.setImageURI(Uri.fromFile(new File(currentPhotoPath)));
                    }
                }
            });

    private void openCameraForVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            File videoFile = null;
            try {
                videoFile = createVideoFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error al crear archivo de video", Toast.LENGTH_SHORT).show();
            }
            if (videoFile != null) {
                Uri videoURI = FileProvider.getUriForFile(this, "com.example.formulario.fileprovider", videoFile);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
                videoLauncher.launch(takeVideoIntent);
            }
        }
    }

    private final ActivityResultLauncher<Intent> videoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        videoView.setVideoURI(Uri.fromFile(new File(currentVideoPath)));
                        videoView.start(); // Reproducir automáticamente
                    }
                }
            });

    private File createVideoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = "VID_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        File video = File.createTempFile(videoFileName, ".mp4", storageDir);
        currentVideoPath = video.getAbsolutePath();
        return video;
    }

    //comprobar si tenemos permisos para acceder a la camara (es casi que lo mismo de lo de permisos de ubicación)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
