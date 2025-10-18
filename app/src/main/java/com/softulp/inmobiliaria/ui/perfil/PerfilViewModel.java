package com.softulp.inmobiliaria.ui.perfil;

import static android.text.TextUtils.isEmpty;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softulp.inmobiliaria.modelos.Propietario;
import com.softulp.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private final MutableLiveData<Propietario> mp = new MutableLiveData<>();
    private final MutableLiveData<Boolean> bMestado = new MutableLiveData<>();
    private final MutableLiveData<String> mTexto = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        bMestado.setValue(false);
        mTexto.setValue("Editar perfil");
    }

    public LiveData<Propietario> getMP()       { return mp; }
    public LiveData<Boolean> getbMestado()     { return bMestado; }
    public LiveData<String>  getmTexto()       { return mTexto; }

    private boolean isInteger(String s){
        try {
            Integer.parseInt(s.trim());
            return true;
        } catch (Exception e){
            return false;
        }
    }


    public void guardarEditar(String textoBoton,
                              String nom, String ap, String dni, String email, String tel) {

        if (textoBoton == null) textoBoton = "";

        if (textoBoton.equalsIgnoreCase("Editar perfil")) {
            // Cambiar a modo edición
            bMestado.setValue(true);
            mTexto.setValue("Guardar");
            Toast.makeText(getApplication(), "Modo edición activado", Toast.LENGTH_SHORT).show();
            return;
        }


        if (isEmpty(nom))   { Toast.makeText(getApplication(),"Nombre requerido", Toast.LENGTH_LONG).show(); return; }
        if (isEmpty(ap))    { Toast.makeText(getApplication(),"Apellido requerido", Toast.LENGTH_LONG).show(); return; }
        if (isEmpty(dni))   { Toast.makeText(getApplication(),"DNI requerido", Toast.LENGTH_LONG).show(); return; }
        if (!isInteger(dni)){ Toast.makeText(getApplication(),"DNI debe ser un número entero", Toast.LENGTH_LONG).show(); return; }
        if (isEmpty(email)) { Toast.makeText(getApplication(),"Email requerido", Toast.LENGTH_LONG).show(); return; }
        if (isEmpty(tel))   { Toast.makeText(getApplication(),"Teléfono requerido", Toast.LENGTH_LONG).show(); return; }

        // Si llegamos acá, los datos están válidos. Armamos el objeto y guardamos.
        Propietario actual = mp.getValue();
        if (actual == null) {
            Toast.makeText(getApplication(), "Sin datos para actualizar", Toast.LENGTH_LONG).show();
            // mantener edición para corregir
            return;
        }

        Propietario p = new Propietario();
        p.setIdPropietario(actual.getIdPropietario());
        p.setNombre(nom.trim());
        p.setApellido(ap.trim());
        p.setDni(dni.trim());
        p.setEmail(email.trim());
        p.setTelefono(tel.trim());

        String token = ApiClient.leerToken(getApplication());
        ApiClient.getInmoService()
                .actualizarPropietario("Bearer " + token, p)
                .enqueue(new Callback<Propietario>() {
                    @Override
                    public void onResponse(Call<Propietario> call, Response<Propietario> r) {
                        if (r.isSuccessful() && r.body() != null) {
                            mp.postValue(r.body());
                            Toast.makeText(getApplication(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                            // salir de edición
                            bMestado.setValue(false);
                            mTexto.setValue("Editar perfil");
                        } else {
                            Toast.makeText(getApplication(),
                                    "Error al actualizar (" + r.code() + ")", Toast.LENGTH_LONG).show();
                            Log.d("PerfilVM", "Guardar error: " + r.code() + " - " + r.message());
                            // mantener edición para corregir
                        }
                    }

                    @Override
                    public void onFailure(Call<Propietario> call, Throwable t) {
                        Toast.makeText(getApplication(), "Error en el servidor o conexión", Toast.LENGTH_LONG).show();
                        Log.e("PerfilVM", "onFailure guardar: " + t.getMessage(), t);
                        // mantener edición para reintentar
                    }
                });
    }


    public void leerPropietario() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.getInmoService()
                .obtenerPropietario("Bearer " + token)
                .enqueue(new Callback<Propietario>() {
                    @Override
                    public void onResponse(Call<Propietario> c, Response<Propietario> r) {
                        if (r.isSuccessful() && r.body() != null) {
                            mp.postValue(r.body());

                            Toast.makeText(getApplication(), "Perfil cargado", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getApplication(),
                                    "Error al obtener el perfil (" + r.code() + ")", Toast.LENGTH_LONG).show();
                            Log.d("PerfilVM", "Leer error: " + r.code() + " - " + r.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Propietario> c, Throwable t) {

                        Toast.makeText(getApplication(), "Error en el servidor o conexión", Toast.LENGTH_LONG).show();
                        Log.e("PerfilVM", "onFailure leer: " + t.getMessage(), t);
                    }
                });
    }
}
