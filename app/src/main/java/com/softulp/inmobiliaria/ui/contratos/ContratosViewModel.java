package com.softulp.inmobiliaria.ui.contratos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softulp.inmobiliaria.modelos.Inmueble;
import com.softulp.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private final MutableLiveData<List<Inmueble>> mInmueble = new MutableLiveData<>();

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        leerContratos(); // Igual que en Inquilinos, lo llamamos al crear
    }

    public LiveData<String> getmText() {
        return mText;
    }

    public LiveData<List<Inmueble>> getmInmueble() {
        return mInmueble;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void leerContratos() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getInmoService();

        // Es el mismo endpoint que usás para listar inmuebles con contrato vigente
        Call<List<Inmueble>> llamada = api.obtenerInmueblesConContratoVigente("Bearer " + token);

        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    mInmueble.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(),
                            "No hay contratos vigentes: " + response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(getApplication(),
                        "Error en servidor: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
