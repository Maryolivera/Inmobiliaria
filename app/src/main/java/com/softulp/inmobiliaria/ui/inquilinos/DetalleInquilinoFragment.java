package com.softulp.inmobiliaria.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.softulp.inmobiliaria.databinding.FragmentDetalleInquilinoBinding;

public class DetalleInquilinoFragment extends Fragment {

    private FragmentDetalleInquilinoBinding binding;
    private DetalleInquilinoViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        vm.getInquilino().observe(getViewLifecycleOwner(), inq -> {
            binding.tvNombreInquilino.setText("Nombre: " + inq.getNombre());
            binding.tvApellidoInquilino.setText("Apellido: " + inq.getApellido());
            binding.tvDniInquilino.setText("DNI: " + inq.getDni());
            binding.tvTelefonoInquilino.setText("Teléfono: " + inq.getTelefono());
            binding.tvEmailInquilino.setText("Email: " + inq.getEmail());
        });

        vm.cargarInquilinoDesdeInmueble(getArguments());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
