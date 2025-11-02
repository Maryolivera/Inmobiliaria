package com.softulp.inmobiliaria.ui.contratos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softulp.inmobiliaria.modelos.Pago;
import com.softulp.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePagosViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Pago>> mPagos = new MutableLiveData<>();

    public DetallePagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getPagos() {
        return mPagos;
    }

    public void leerPagos(int idContrato) {
        if (idContrato <= 0) {
            Toast.makeText(getApplication(), "Contrato inválido.", Toast.LENGTH_SHORT).show();
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getInmoService();
        Call<List<Pago>> llamada = api.obtenerPagosPorContrato("Bearer " + token, idContrato);

        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    mPagos.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(),
                            "No hay pagos: " + response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Toast.makeText(getApplication(),
                        "Error en servidor: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
