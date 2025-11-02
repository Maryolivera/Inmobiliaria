package com.softulp.inmobiliaria.ui.contratos;

import static com.softulp.inmobiliaria.request.ApiClient.URLBASE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softulp.inmobiliaria.R;
import com.softulp.inmobiliaria.modelos.Inmueble;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder> {

    private final List<Inmueble> lista;   // lista de inmuebles con contrato vigente
    private final Context context;

    public ContratoAdapter(List<Inmueble> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contrato, parent, false);
        return new ContratoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoViewHolder holder, int position) {
        Inmueble i = lista.get(position);

        // Dirección del inmueble
        holder.tvDireccionContrato.setText(i.getDireccion());

        // Imagen del inmueble (usa Glide igual que en Inquilinos)
        String img = i.getImagen();
        if (!TextUtils.isEmpty(img)) {
            Glide.with(context)
                    .load(URLBASE + img)
                    .placeholder(R.drawable.outline_home_24)
                    .error(R.drawable.outline_home_24)
                    .into(holder.imgInmuebleContrato);
        } else {
            holder.imgInmuebleContrato.setImageResource(R.drawable.outline_home_24);
        }

        // Navegación al detalle del contrato
        View.OnClickListener goDetalle = v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", i);
            Navigation.findNavController((Activity) v.getContext(),
                            R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_nav_contratos_to_detalleContratoFragment, bundle);
        };

        holder.cardContrato.setOnClickListener(goDetalle);
        if (holder.btnVerContrato != null) holder.btnVerContrato.setOnClickListener(goDetalle);
    }

    @Override
    public int getItemCount() {
        return (lista == null) ? 0 : lista.size();
    }

    static class ContratoViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDireccionContrato;
        final ImageView imgInmuebleContrato;
        final CardView cardContrato;
        final Button btnVerContrato;

        ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccionContrato  = itemView.findViewById(R.id.tvDireccionContrato);
            imgInmuebleContrato  = itemView.findViewById(R.id.imgInmuebleContrato);
            cardContrato         = itemView.findViewById(R.id.cardContrato);
            btnVerContrato       = itemView.findViewById(R.id.btnVerContrato);
        }
    }
}


