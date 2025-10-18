package com.softulp.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.softulp.inmobiliaria.R;

public class ContraseniaFragment extends Fragment {

    private EditText etActual, etNueva, etConfirmar;
    private Button btGuardar;
    private ContraseniaViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contrasenia, container, false);

        etActual = v.findViewById(R.id.etActual);
        etNueva = v.findViewById(R.id.etNueva);
        etConfirmar = v.findViewById(R.id.etConfirmar);
        btGuardar = v.findViewById(R.id.btGuardarContrasenia);

        vm = new ViewModelProvider(this).get(ContraseniaViewModel.class);

        btGuardar.setOnClickListener(view -> {
            // Solo leer y delegar al ViewModel
            vm.cambiarContrasenia(
                    etActual.getText().toString(),
                    etNueva.getText().toString(),
                    etConfirmar.getText().toString()
            );
        });

        // Observa el estado de carga para deshabilitar el botón
        vm.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            btGuardar.setEnabled(!isLoading);
            btGuardar.setAlpha(isLoading ? 0.5f : 1f);
        });

        // Observa mensajes tipo Toast
        vm.getToast().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

        // Observa finalización (éxito)
        vm.getDone().observe(getViewLifecycleOwner(), ok -> {
            if (ok != null && ok) {
                requireActivity().onBackPressed();
            }
        });

        return v;
    }
}

