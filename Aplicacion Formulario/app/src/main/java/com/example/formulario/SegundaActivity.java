package com.example.formulario;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SegundaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        //configurando fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.contenedor_fragment, new BottomMenuFragment());
        transaction.commit();
    }

    @Override
    protected int getLayoutResourceId() { //para devolver el layout de la activity
        return R.layout.activity_segunda;
    }

}
