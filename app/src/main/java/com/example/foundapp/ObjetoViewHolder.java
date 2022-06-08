package com.example.foundapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ObjetoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView objetonom, objetodes,objetotip,objetocat;
    public ImageView ObjetoImagen;
    public ItemClickListener listener;

    public ObjetoViewHolder(@NonNull View itemView) {
        super(itemView);
        objetonom=(TextView) itemView.findViewById(R.id.publicacion_nombre);
        objetodes=(TextView) itemView.findViewById(R.id.publicacion_descripcion);
        objetotip=(TextView) itemView.findViewById(R.id.publicacion_tipo);
        objetocat=(TextView) itemView.findViewById(R.id.publicacion_pero);
        ObjetoImagen=(ImageView) itemView.findViewById(R.id.image_publi);
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }
}
