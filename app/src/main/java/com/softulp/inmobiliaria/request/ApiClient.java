package com.softulp.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softulp.inmobiliaria.modelos.Contrato;
import com.softulp.inmobiliaria.modelos.Inmueble;
import com.softulp.inmobiliaria.modelos.Pago;
import com.softulp.inmobiliaria.modelos.Propietario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public class ApiClient {

    public  final static String URLBASE =
            "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    public static InmoService getInmoService() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(InmoService.class);
    }

    public interface InmoService {

        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> loginForm(@Field("Usuario") String usuario,
                               @Field("Clave") String clave);

        @GET("api/Propietarios")
        Call<Propietario > obtenerPropietario(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token,
                                         @Body Propietario propietario);


        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarContrasenia(
                @Header("Authorization") String authorization,
                @Field("currentPassword") String currentPassword,
                @Field("newPassword") String newPassword
        );

        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @GET("api/Inmuebles")
        Call<List<Inmueble>>ObtenerInmuebles(@Header("Authorization") String token);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> CargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble") RequestBody inmuebleBody);

        //INMUEBLES CON CONTRATO VIGENTE
        @GET("api/Inmuebles/GetContratoVigente")
        Call<List<Inmueble>> obtenerInmueblesConContratoVigente(@Header("Authorization") String token);

        // ApiClient.InmoService
        @GET("api/contratos/inmueble/{id}")
        Call<Contrato> obtenerContratoPorInmueble(
                @Header("Authorization") String token,
                @retrofit2.http.Path("id") int idInmueble
        );


        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> cargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble") RequestBody inmuebleBody);


        @GET("api/pagos/contrato/{id}")
        Call<List<Pago>> obtenerPagosPorContrato(
                @Header("Authorization") String token,
                @retrofit2.http.Path("id") int idContrato
        );


    }


    public static void guardarToken(Context context, String token) {

        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("token", token);

        editor.apply();

    }


    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }
}

