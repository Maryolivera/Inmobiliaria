package com.softulp.inmobiliaria.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softulp.inmobiliaria.request.ApiClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContraseniaViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> toast = new MutableLiveData<>();
    private final MutableLiveData<Boolean> done = new MutableLiveData<>(false);

    public ContraseniaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getToast() { return toast; }
    public LiveData<Boolean> getDone() { return done; }

    public void cambiarContrasenia(String actual, String nueva, String confirmar) {

        if (actual == null || actual.isEmpty()) { toast.setValue("Ingresá tu contraseña actual"); return; }
        if (nueva == null || nueva.length() < 6) { toast.setValue("La nueva contraseña debe tener al menos 6 caracteres"); return; }
        if (!nueva.equals(confirmar)) { toast.setValue("Las contraseñas no coinciden"); return; }

        String token = ApiClient.leerToken(getApplication());
        if (token == null || token.isEmpty()) {
            toast.setValue("Sesión expirada: volvé a iniciar sesión");
            return;
        }

        loading.setValue(true);

        ApiClient.getInmoService()
                .cambiarContrasenia("Bearer " + token, actual, nueva)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        loading.postValue(false);
                        if (response.isSuccessful()) {
                            toast.postValue("Contraseña actualizada");
                            done.postValue(true);
                        } else {
                            String msg = "Error " + response.code();
                            ResponseBody eb = response.errorBody();
                            if (eb != null) {
                                try { msg += ": " + eb.string(); } catch (IOException ignored) {}
                            }
                            toast.postValue(msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        loading.postValue(false);
                        toast.postValue(t.getMessage() != null ? t.getMessage() : "Fallo de red");
                    }
                });
    }
}
