package com.softulp.inmobiliaria.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;
import com.softulp.inmobiliaria.R;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Observa el callback del mapa (estilo profe)
        mViewModel.getMapaActual().observe(getViewLifecycleOwner(), mapaActual -> {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);

            mapFragment.getMapAsync(mapaActual);

        });

        // Dispara la carga del mapa
        mViewModel.cargarMapa();

        return v;
    }
}
