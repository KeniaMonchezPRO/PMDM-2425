package com.example.formulario;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;


public class ActivityHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help);
    }

    /**
     * Callback method defined by the View
     *
     * @param v
     */
    public void finishHelp(View v) {
        ActivityHelp.this.finish();
    }

}
