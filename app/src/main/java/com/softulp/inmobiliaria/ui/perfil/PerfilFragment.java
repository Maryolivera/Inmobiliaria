package com.softulp.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.softulp.inmobiliaria.R;
import com.softulp.inmobiliaria.databinding.FragmentPerfilBinding;
import com.softulp.inmobiliaria.modelos.Propietario;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        // Propietario -> completa campos
        mViewModel.getMP().observe(getViewLifecycleOwner(), (Propietario p) -> {
            if (p == null) return;
            binding.etnombre.setText(p.getNombre());
            binding.etapellido.setText(p.getApellido());
            binding.etdni.setText(p.getDni());
            binding.ettelefono.setText(p.getTelefono());
            binding.etemail.setText(p.getEmail());
        });

        // Estado edición -> habilita/deshabilita
        mViewModel.getbMestado().observe(getViewLifecycleOwner(), (Boolean on) -> {
            boolean e = on != null && on;
            binding.etnombre.setEnabled(e);
            binding.etapellido.setEnabled(e);
            binding.etdni.setEnabled(e);
            binding.ettelefono.setEnabled(e);
            binding.etemail.setEnabled(e);
        });

        // Texto del botón Editar/Guardar
        mViewModel.getmTexto().observe(getViewLifecycleOwner(),
                s -> binding.bteditar.setText(s));

        // Click Editar/Guardar
        binding.bteditar.setOnClickListener(v -> {
            String txt = binding.bteditar.getText().toString();

            if (txt.equalsIgnoreCase("Editar perfil")) {
                // Solo cambiar a modo edición
                mViewModel.guardarEditar(txt, null, null, null, null, null);
            } else {
                // Enviar datos editados para guardar
                String nom   = binding.etnombre.getText().toString().trim();
                String ap    = binding.etapellido.getText().toString().trim();
                String dni   = binding.etdni.getText().toString().trim();
                String email = binding.etemail.getText().toString().trim();
                String tel   = binding.ettelefono.getText().toString().trim();

                mViewModel.guardarEditar(txt, nom, ap, dni, email, tel);
            }
        });

        // Click Cambiar contraseña -> navega al fragment de contraseña
        binding.btcambiarContrasena.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .navigate(R.id.action_nav_perfil_to_nav_contrasenia)
        );

        // Carga inicial
        mViewModel.leerPropietario();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
