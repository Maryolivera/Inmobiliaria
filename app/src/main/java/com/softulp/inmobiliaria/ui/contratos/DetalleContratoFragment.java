package com.softulp.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.softulp.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.softulp.inmobiliaria.modelos.Contrato;

public class DetalleContratoFragment extends Fragment {

    private FragmentDetalleContratoBinding binding;
    private DetalleContratoViewModel vm;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        // Deshabilitar el botón hasta que llegue el contrato
        binding.btnPagos.setEnabled(false);

        vm.getContrato().observe(getViewLifecycleOwner(), c -> {

            binding.tvCodigoContrato.setText("Código de contrato: " + c.getIdContrato());
            binding.tvFechaInicio.setText("Fecha de inicio: " + c.getFechaInicio());
            binding.tvFechaFinalizacion.setText("Fecha de finalización: " + c.getFechaFinalizacion());
            binding.tvMontoAlquiler.setText("Monto del alquiler: $" + c.getMontoAlquiler());
            binding.tvEstadoContrato.setText("Estado: " + (c.isEstado() ? "Vigente" : "Finalizado"));

            // Activar botón PAGOS y navegar al fragment de pagos
            binding.btnPagos.setEnabled(true);
            binding.btnPagos.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putInt("idContrato", c.getIdContrato());
                androidx.navigation.fragment.NavHostFragment.findNavController(this)
                        .navigate(com.softulp.inmobiliaria.R.id.detallePagosFragment, b);
            });
        });

        // Cargar contrato desde el inmueble recibido
        vm.cargarContratoDesdeInmueble(getArguments());

        return binding.getRoot();
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
