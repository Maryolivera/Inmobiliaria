package com.softulp.inmobiliaria.ui.inquilinos;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.softulp.inmobiliaria.R;
import com.softulp.inmobiliaria.databinding.FragmentInquilinoBinding;
import com.softulp.inmobiliaria.modelos.Inmueble;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private FragmentInquilinoBinding binding; // Asegurate que tu layout sea fragment_inquilino.xml (singular)
    private InquilinosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);

        RecyclerView rv = binding.rvInquilinos;
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        vm.getmInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {

            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                     // ✅ Toast de verificación (usa requireContext para evitar null)
                Toast.makeText(requireContext(),
                        "Inmuebles: " + (inmuebles != null ? inmuebles.size() : -1),
                        Toast.LENGTH_SHORT).show();

                Log.d("INQUILINOS", "size=" + (inmuebles != null ? inmuebles.size() : -1));




                InquilinoAdapter adapter = new InquilinoAdapter(inmuebles, getContext());
                rv.setAdapter(adapter);
            }
        });

        vm.leerInquilinos();



        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

