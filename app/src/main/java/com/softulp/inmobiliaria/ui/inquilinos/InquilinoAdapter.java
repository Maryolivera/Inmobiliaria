package com.softulp.inmobiliaria.ui.inquilinos;

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

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.InquilinoViewHolder> {

    private final List<Inmueble> lista;   // inmuebles con contrato vigente
    private final Context context;

    public InquilinoAdapter(List<Inmueble> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public InquilinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inquilino, parent, false);
        return new InquilinoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InquilinoViewHolder holder, int position) {
        Inmueble i = lista.get(position);

        // Dirección (lo que mostrás en la tarjeta)
        holder.tvDireccion.setText(i.getDireccion());

        // Imagen del inmueble (con fallback si viene null/vacía)
        String img = i.getImagen();
        if (!TextUtils.isEmpty(img)) {
            Glide.with(context)
                    .load(URLBASE + img)
                    .placeholder(R.drawable.outline_home_24)
                    .error(R.drawable.outline_home_24)
                    .into(holder.imgInmueble);
        } else {
            holder.imgInmueble.setImageResource(R.drawable.outline_home_24);
        }

        // Navegación: misma acción para Card y para el botón VER
        View.OnClickListener goDetalle = v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", i);
            Navigation.findNavController((Activity) v.getContext(),
                            R.id.nav_host_fragment_content_main)
                    .navigate(R.id.action_nav_inquilinos_to_detalleInquilino, bundle);
        };

        holder.cardView.setOnClickListener(goDetalle);
        if (holder.btnVer != null) holder.btnVer.setOnClickListener(goDetalle);
    }

    @Override
    public int getItemCount() {
        return (lista == null) ? 0 : lista.size();
    }

    static class InquilinoViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDireccion;
        final ImageView imgInmueble;
        final CardView cardView;
        final Button btnVer; // está en item_inquilino

        InquilinoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion  = itemView.findViewById(R.id.tvDireccion);
            imgInmueble  = itemView.findViewById(R.id.imgInmueble);
            cardView     = itemView.findViewById(R.id.idCard);
            btnVer       = itemView.findViewById(R.id.btnVer); // puede ser null si no existe en el layout
        }
    }
}
