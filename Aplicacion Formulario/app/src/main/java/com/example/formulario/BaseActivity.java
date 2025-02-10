package com.example.formulario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Contenedor donde se inyectará el layout de cada Activity hija
        FrameLayout contenedorPrincipal = findViewById(R.id.contenedorPrincipal);
        View contenido = LayoutInflater.from(this).inflate(getLayoutResourceId(), contenedorPrincipal, false);
        contenedorPrincipal.addView(contenido);

    }

    // Método que las subclases sobrescribirán para definir su layout
    protected int getLayoutResourceId() { //para identificar las activities, será una activity abstracta que nunca se creará unha instancia dela por eso return 0 pero será sobreescrito por las hijas
        return 0;
    }

}
