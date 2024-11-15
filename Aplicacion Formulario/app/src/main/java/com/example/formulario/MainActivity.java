package com.example.formulario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private ImageButton imaxe;
    private Button axuda;

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

        gardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Enviado",Toast.LENGTH_SHORT).show();
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

    public void startActivityHelp(View v) {
        Intent intent = new Intent(MainActivity.this, ActivityHelp.class);
        startActivity(intent);
    }
}