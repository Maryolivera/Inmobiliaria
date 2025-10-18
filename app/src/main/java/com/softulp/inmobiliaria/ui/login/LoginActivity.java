package com.softulp.inmobiliaria.ui.login;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.softulp.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(LoginViewModel.class);

        vm.getMensaje().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        );




        binding.btLogin.setOnClickListener(v -> {
            String mail = binding.etUsuario.getText().toString().trim();
            String clave = binding.etClave.getText().toString().trim();

            if (mail.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Complete usuario y clave", Toast.LENGTH_SHORT).show();
                return;
            }
            vm.login(mail, clave);
        });
    }
}
