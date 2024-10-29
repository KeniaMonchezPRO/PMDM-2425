package com.example.formulario;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button gardar;
    private Button limpar;
    private EditText nome, apelidos, mobil, email, contrasinal;
    private CheckBox publicidade;

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

        gardar = (Button) findViewById(R.id.btnGardar);
        limpar = (Button) findViewById(R.id.btnLimpar);
        nome = (EditText) findViewById(R.id.editTextNome);
        apelidos = (EditText) findViewById(R.id.editTextApelidos);
        mobil = (EditText) findViewById(R.id.editTextMobil);
        email = (EditText) findViewById(R.id.editTextEmail);
        contrasinal = (EditText) findViewById(R.id.editTextContrasinal);
        publicidade = (CheckBox) findViewById(R.id.checkBoxPublicidade);

        gardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,nome.getText() + " " + apelidos.getText(),Toast.LENGTH_SHORT).show();
            }
        });

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
}