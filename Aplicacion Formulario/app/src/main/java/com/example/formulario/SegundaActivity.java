package com.example.formulario;

import android.os.Bundle;

public class SegundaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
    }

    @Override
    protected int getLayoutResourceId() { //para devolver el layout de la activity
        return R.layout.activity_segunda;
    }

}
