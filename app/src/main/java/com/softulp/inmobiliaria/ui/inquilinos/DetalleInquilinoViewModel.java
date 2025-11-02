package com.softulp.inmobiliaria.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softulp.inmobiliaria.modelos.Contrato;
import com.softulp.inmobiliaria.modelos.Inmueble;
import com.softulp.inmobiliaria.modelos.Inquilino;
import com.softulp.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private final MutableLiveData<Inquilino> inquilino = new MutableLiveData<>();

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getInquilino() {
        return inquilino;
    }

    public void cargarInquilinoDesdeInmueble(Bundle args) {
        if (args == null) return;
        Inmueble inm = (Inmueble) args.getSerializable("inmueble");
        if (inm == null) return;



        //  pedimos el contrato por inmueble y de ahí sacamos el inquilino
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getInmoService();

        Call<Contrato> call = api.obtenerContratoPorInmueble("Bearer " + token, inm.getIdInmueble());
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplication(),
                            "No se pudo obtener el contrato: " + response.message(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Contrato c = response.body();
                if (c != null && c.getInquilino() != null) {
                    inquilino.postValue(c.getInquilino());
                } else {
                    Toast.makeText(getApplication(),
                            "Sin datos de inquilino para este inmueble.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(getApplication(),
                        "Error servidor: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
