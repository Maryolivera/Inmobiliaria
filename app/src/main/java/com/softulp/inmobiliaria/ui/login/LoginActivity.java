package com.softulp.inmobiliaria.ui.login;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Context; // Necesario para getSystemService

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.softulp.inmobiliaria.R;
import com.softulp.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityLoginBinding binding;
    private LoginViewModel vm;

    // Sensores
    private SensorManager sensorManager;
    private Sensor accelerometer; // Usaremos solo el acelerómetro principal

    // Umbrales y control para 1 SHAKE simple
    private static final float SHAKE_THRESHOLD = 15.0f; // Umbral bajo para fácil detección
    private static final int MIN_INTERVAL_MS = 1500;    // Antirebote: 1.5 segundos
    private long lastDialMs = 0L;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(LoginViewModel.class);
        vm.getMensaje().observe(this, msg -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());

        binding.btLogin.setOnClickListener(v -> {
            String mail = binding.etUsuario.getText().toString().trim();
            String clave = binding.etClave.getText().toString().trim();
            if (mail.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Complete usuario y clave", Toast.LENGTH_SHORT).show();
                return;
            }
            vm.login(mail, clave);
        });

        // Inicialización de Sensores
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            // Intentamos obtener el acelerómetro principal (el más común)
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override protected void onResume() {
        super.onResume();
        if (sensorManager != null && accelerometer != null) {
            // Registra el listener solo con el acelerómetro principal
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override protected void onPause() {
        super.onPause();
        if (sensorManager != null) sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Aseguramos que solo procesamos el acelerómetro
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        // El acelerómetro mide fuerza G + movimiento. Calculamos el valor de la aceleración total (magnitud).
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];

        float magnitude = (float) Math.sqrt(ax * ax + ay * ay + az * az);
        long now = System.currentTimeMillis();

        // Si la magnitud es mayor al umbral (15.0f)
        if (magnitude > SHAKE_THRESHOLD) {
            // Aplicamos antirebote (1.5 segundos)
            if (now - lastDialMs > MIN_INTERVAL_MS) {
                lastDialMs = now;
                callAgency();
            }
        }
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) { /* no-op */ }

    // Método para abrir el marcador del teléfono (ACTION_DIAL)
    private void callAgency() {
        // Obtenemos el número del archivo strings.xml
        String phone = getString(R.string.agency_phone);

        Toast.makeText(this, "Agitación detectada. Abriendo marcador para: " + phone, Toast.LENGTH_LONG).show();

        // Usamos ACTION_DIAL, que abre el marcador con el número cargado (el usuario debe pulsar llamar).
        // NO requiere el permiso CALL_PHONE en el Manifest, solo el permiso de Internet/Red si estuviera allí.
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));

        try {
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(this, "No se pudo abrir el marcador.", Toast.LENGTH_LONG).show();
        }
    }
}