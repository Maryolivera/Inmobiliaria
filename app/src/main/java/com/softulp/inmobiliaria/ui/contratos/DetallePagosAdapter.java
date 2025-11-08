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
import com.softulp.inmobiliaria.utilidades.Utilidades;

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
        String fecha = p.getFechaPago();
        holder.tvFechaPago.setText(
                (fecha != null && !fecha.isEmpty())
                        ? Utilidades.formatearFecha(fecha)
                        : ""
        );


        holder.tvMontoPago.setText(Utilidades.formatearMoneda(p.getMonto()));
        String det = p.getDetalle();
        holder.tvDetallePago.setText(det != null ? det : "");
        holder.tvEstadoPago.setText(p.isEstado() ? "Pagado" : "Anulado");
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
            tvDetallePago = itemView.findViewById(R.id.tvDetallePago);
            tvEstadoPago  = itemView.findViewById(R.id.tvEstadoPago);
        }
    }
}
