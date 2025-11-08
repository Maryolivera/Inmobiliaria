package com.softulp.inmobiliaria.ui.inmuebles;

import static com.softulp.inmobiliaria.request.ApiClient.URLBASE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softulp.inmobiliaria.R;
import com.softulp.inmobiliaria.modelos.Inmueble;
import com.softulp.inmobiliaria.utilidades.Utilidades;


import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {
    private List<Inmueble> lista;
    private Context context;

    public InmuebleAdapter(List<Inmueble> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inmueble, parent, false);
        return new InmuebleViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {

        Inmueble i = lista.get(position);
        holder.tvDireccion.setText(i.getDireccion());
        holder.tvTipo.setText(i.getTipo());
        holder.tvPrecio.setText(Utilidades.formatearMoneda(i.getValor()));

        Glide.with(context)
                .load(URLBASE+ i.getImagen())
                .placeholder(R.drawable.outline_home_24)
                .error(R.drawable.outline_home_24)
                .into(holder.imgInmueble);

        holder.cardView.setOnClickListener(v ->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", i);
            Navigation.findNavController(holder.itemView).navigate(R.id.action_nav_inmuebles_to_detalleInmuebleFragment, bundle);

        });


        //Necesito pedir la imagen al servidor pasando la URL para que me traiga la imagen.
        //La peticion es asyncrona
        //Usar libreria de Glide para cargar la imagen
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class InmuebleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccion, tvTipo, tvPrecio;
        private ImageView imgInmueble;
        private CardView cardView;


        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            imgInmueble = itemView.findViewById(R.id.imgInmueble);
            cardView = itemView.findViewById(R.id.idCard);
        }
    }
}
