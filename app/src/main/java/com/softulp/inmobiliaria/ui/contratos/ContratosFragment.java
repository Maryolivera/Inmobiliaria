package com.softulp.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softulp.inmobiliaria.databinding.FragmentContratoBinding;
import com.softulp.inmobiliaria.modelos.Inmueble;

import java.util.List;

public class ContratosFragment extends Fragment {

    private FragmentContratoBinding binding; // Asegúrate que el layout se llame fragment_contratos.xml
    private ContratosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratoBinding.inflate(inflater, container, false);

        RecyclerView rv = binding.rvContratos;
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        vm.getmInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                // ✅ Toast de verificación (usa requireContext)
                Toast.makeText(requireContext(),
                        "Contratos: " + (inmuebles != null ? inmuebles.size() : -1),
                        Toast.LENGTH_SHORT).show();

                Log.d("CONTRATOS", "size=" + (inmuebles != null ? inmuebles.size() : -1));

                ContratoAdapter adapter = new ContratoAdapter(inmuebles, getContext());
                rv.setAdapter(adapter);
            }
        });

        vm.leerContratos();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
