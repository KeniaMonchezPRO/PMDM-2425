package com.example.formulario;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import Entities.Usuario;

public class SegundaActivity extends BaseActivity {

    private TextView nombre;
    private TextView email;

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
    }

    @Override
    protected int getLayoutResourceId() { //para devolver el layout de la activity
        return R.layout.activity_segunda;
    }

}
