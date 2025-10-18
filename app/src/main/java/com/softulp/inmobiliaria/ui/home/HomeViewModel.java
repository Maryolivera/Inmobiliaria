package com.softulp.inmobiliaria.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<MapaActual> mapaActual = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaActual> getMapaActual() {
        return mapaActual;
    }

    // Llamalo desde el Fragment para disparar el mapa
    public void cargarMapa() {
        mapaActual.setValue(new MapaActual());
    }

    // ====== Clase del profe, tal cual pero con addMarker + camera ======
    public class MapaActual implements OnMapReadyCallback {

        private final LatLng inmobiliaria = new LatLng(-33.305221, -66.340690);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MarkerOptions marcadorInmobiliaria = new MarkerOptions()
                    .position(inmobiliaria)
                    .title("Inmobiliaria");

            googleMap.addMarker(marcadorInmobiliaria);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inmobiliaria, 18f));
            // opcional: vista satelital como en la consigna
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    }
}
