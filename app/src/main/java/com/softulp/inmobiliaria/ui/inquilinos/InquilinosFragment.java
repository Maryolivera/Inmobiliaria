package com.softulp.inmobiliaria.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softulp.inmobiliaria.databinding.FragmentInquilinoBinding;
import com.softulp.inmobiliaria.modelos.Inmueble;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private FragmentInquilinoBinding binding;
    private InquilinosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);

        vm.getmInmueble().observe(getViewLifecycleOwner(), (List<Inmueble> inmuebles) -> {
            InquilinoAdapter adapter = new InquilinoAdapter(inmuebles, getContext());
            GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
            RecyclerView rv = binding.rvInquilinos;
            rv.setAdapter(adapter);
            rv.setLayoutManager(glm);
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
