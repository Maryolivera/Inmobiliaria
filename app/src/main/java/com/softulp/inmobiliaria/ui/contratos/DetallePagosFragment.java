package com.softulp.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softulp.inmobiliaria.databinding.FragmentDetallePagosBinding;
import com.softulp.inmobiliaria.modelos.Pago;

import java.util.List;

public class DetallePagosFragment extends Fragment {

    private FragmentDetallePagosBinding binding;
    private DetallePagosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetallePagosViewModel.class);
        binding = FragmentDetallePagosBinding.inflate(inflater, container, false);

        RecyclerView rv = binding.rvPagos;
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));


        vm.getPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                Toast.makeText(requireContext(),
                        "Pagos: " + (pagos != null ? pagos.size() : -1),
                        Toast.LENGTH_SHORT).show();

                Log.d("PAGOS", "size=" + (pagos != null ? pagos.size() : -1));

                DetallePagosAdapter adapter = new DetallePagosAdapter(pagos, getContext());
                rv.setAdapter(adapter);
            }
        });

        // lee el idContrato desde los argumentos (enviado desde DetalleContrato)
        int idContrato = (getArguments() != null) ? getArguments().getInt("idContrato", -1) : -1;
        vm.leerPagos(idContrato);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
