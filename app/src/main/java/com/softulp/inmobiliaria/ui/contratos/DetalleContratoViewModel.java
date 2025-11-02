package com.softulp.inmobiliaria.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softulp.inmobiliaria.modelos.Contrato;
import com.softulp.inmobiliaria.modelos.Inmueble;
import com.softulp.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private final MutableLiveData<Contrato> contrato = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getContrato() {
        return contrato;
    }

    public void cargarContratoDesdeInmueble(Bundle args) {
        if (args == null) return;

        Inmueble inm = (Inmueble) args.getSerializable("inmueble");
        if (inm == null) return;

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
                if (c != null) {
                    contrato.postValue(c);
                } else {
                    Toast.makeText(getApplication(),
                            "No se encontró contrato para este inmueble.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(getApplication(),
                        "Error en el servidor: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
