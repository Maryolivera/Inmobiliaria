package com.softulp.inmobiliaria.ui.contratos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.softulp.inmobiliaria.R;
import com.softulp.inmobiliaria.modelos.Pago;

import java.util.List;

public class DetallePagosAdapter extends RecyclerView.Adapter<DetallePagosAdapter.PagoViewHolder> {

    private final List<Pago> lista;
    private final Context context;

    public DetallePagosAdapter(List<Pago> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pago, parent, false);
        return new PagoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago p = lista.get(position);

        // Fecha (si tenés getFechaPagoFormateada(), usala; si no, getFechaPago())
        String fecha = (p.getFechaPago() != null) ? p.getFechaPago() : "";
        holder.tvFechaPago.setText("Fecha: " + fecha);

        // Monto
        holder.tvMontoPago.setText("Monto: $ " + p.getMonto());

        // Detalle (opcional)
        if (holder.tvDetallePago != null) {
            holder.tvDetallePago.setText("Detalle: " + (p.getDetalle() != null ? p.getDetalle() : ""));
        }

        // Estado
        if (holder.tvEstadoPago != null) {
            holder.tvEstadoPago.setText("Estado: " + (p.isEstado() ? "Pagado" : "Pendiente"));
        }
    }

    @Override
    public int getItemCount() {
        return (lista == null) ? 0 : lista.size();
    }

    static class PagoViewHolder extends RecyclerView.ViewHolder {
        final CardView cardPago;
        final TextView tvFechaPago, tvMontoPago, tvDetallePago, tvEstadoPago;

        PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            cardPago      = itemView.findViewById(R.id.cardPago);
            tvFechaPago   = itemView.findViewById(R.id.tvFechaPago);
            tvMontoPago   = itemView.findViewById(R.id.tvMontoPago);
            tvDetallePago = itemView.findViewById(R.id.tvDetallePago); // puede ser null si lo quitaste del XML
            tvEstadoPago  = itemView.findViewById(R.id.tvEstadoPago);  // puede ser null si lo quitaste del XML
        }
    }
}

