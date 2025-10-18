package com.softulp.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.softulp.inmobiliaria.MainActivity;
import com.softulp.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mMensaje = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMensaje() {
        return mMensaje;
    }

    public void login(String mail, String clave) {
        Context context = getApplication().getApplicationContext();

        ApiClient.InmoService api = ApiClient.getInmoService();


        Call<String> llamada = api.loginForm(mail, clave);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().trim();

                    ApiClient.guardarToken(context, token);
                    mMensaje.postValue("Bienvenido");

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else if (response.code() == 401) {
                    mMensaje.postValue("Usuario y/o contraseña incorrecta; reintente");
                } else {
                    mMensaje.postValue("Error de Servidor (" + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mMensaje.postValue("Error de Servidor");
            }
        });
    }
}
