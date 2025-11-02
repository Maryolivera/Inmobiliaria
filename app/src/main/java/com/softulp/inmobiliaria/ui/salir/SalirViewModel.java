package com.softulp.inmobiliaria.ui.salir;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.softulp.inmobiliaria.request.ApiClient;
import com.softulp.inmobiliaria.ui.login.LoginActivity;

public class SalirViewModel extends ViewModel {

    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void cerrarSesion(Context context) {
        ApiClient.guardarToken(context, "");
        mensaje.setValue("Sesión cerrada correctamente");

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }
}
