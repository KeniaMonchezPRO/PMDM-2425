package com.example.formulario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class BottomMenuFragment extends Fragment {

    public BottomMenuFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false); //inflate para mostrar el xml del menu inferior

        Button btnIrActivity1 = view.findViewById(R.id.btnIrActivity1);
        Button btnIrActivity2 = view.findViewById(R.id.btnIrActivity2);

        btnIrActivity1.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) return;
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        btnIrActivity2.setOnClickListener(v -> {
            if (getActivity() instanceof SegundaActivity) return;
            Intent intent = new Intent(getActivity(), SegundaActivity.class);
            startActivity(intent);
        });

        return view;
    }

}
