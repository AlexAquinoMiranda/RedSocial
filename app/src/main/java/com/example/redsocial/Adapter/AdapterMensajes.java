package com.example.redsocial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.Publicationes;
import com.example.redsocial.R;
import com.example.redsocial.dto.Mensaje;

import java.util.ArrayList;

public class AdapterMensajes extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Mensaje> mensajes;
    Context context;
    private static final int ENVIA = 1;
    private static final int RECIBE = 2;

    public AdapterMensajes(ArrayList<Mensaje> mensajes, Context context) {
        this.mensajes = mensajes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ENVIA) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mensajes, parent, false);
            return new MensajeEnvia(view);
        } else if (viewType == RECIBE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recibe_mensaje, parent, false);
            return new Mensajes(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Mensaje message = mensajes.get(position);

        switch (holder.getItemViewType()) {
            case ENVIA:
                MensajeEnvia sentMessageViewHolder = (MensajeEnvia) holder;
                sentMessageViewHolder.mensaje.setText(message.getMensaje());
                //HH:mm
                sentMessageViewHolder.date.setText(message.getDate().substring(0,4));
                break;

            case RECIBE:
                Mensajes receiber = (Mensajes) holder;
                receiber.mensaje.setText(message.getMensaje());
                receiber.date.setText(message.getDate().substring(0,4));
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Mensaje message = mensajes.get(position);
        if (message.getFromUser().equals(Publicationes.getUserglobal().getUid())) {
            return ENVIA;
        } else {
            return RECIBE;
        }
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    public class Mensajes extends RecyclerView.ViewHolder {
        TextView  mensaje, date;


        public Mensajes(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.RecibeMensajeDate);
            mensaje = itemView.findViewById(R.id.RecibeMensajeUserName);
        }
    }

    public class MensajeEnvia extends RecyclerView.ViewHolder {
        TextView  mensaje, date;

        public MensajeEnvia(@NonNull View itemView) {
            super(itemView);
            mensaje = itemView.findViewById(R.id.mensajeEnvia);
            date = itemView.findViewById(R.id.dateEnvia);


        }
    }
}
