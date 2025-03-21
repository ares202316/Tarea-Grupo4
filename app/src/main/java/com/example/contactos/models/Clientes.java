package com.example.contactos.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactos.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Clientes extends RecyclerView.Adapter<Clientes.MyViewHolder> {

    private Context context;
    private ArrayList<String> id,notas;
    private ArrayList<byte[]> imagenes; // Agregar lista de imágenes




    public Clientes(Context context, ArrayList<String> id, ArrayList<String> notas,  ArrayList<byte[]> imagenes) {
        this.context = context;
        this.id = id;
        this.notas = notas;
         this.imagenes = imagenes; // Inicializar la lista de imágenes
    }

    @NonNull
    @Override
    public Clientes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Clientes.MyViewHolder holder, int position) {


        holder.id_txt.setText(id.get(position));
       holder.notas_txt.setText(notas.get(position));
             //holder.mainlayout.setonClickListener(new View.OnClickListener(){
            //@Override
          //  public void onclic
        //});

        // Obtener bytes de imagen
        byte[] imagenBytes = imagenes.get(position);

        if (imagenBytes != null && imagenBytes.length > 0) {
            // Convertir a Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
            holder.img_foto.setImageBitmap(bitmap);
        } else {
            // Si no hay imagen, mostrar una por defecto
            holder.img_foto.setImageResource(R.drawable.ic_usuario);
        }


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_txt, telefono_txt, id_txt,notas_txt,pais_txt;
        ImageView img_foto;
        ConstraintLayout mainlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);

            notas_txt = itemView.findViewById(R.id.notas_txt);
                       img_foto = itemView.findViewById(R.id.perfil); // Asegúrate de que este ID coincide con el XML
            //mainlayout = itemView.findViewById(R.id.mainLayout);
            mainlayout = itemView.findViewById(R.id.mainLayout);

        }
    }

    public Uri saveImage(byte[] imageBytes, Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput("profile_image.png", Context.MODE_PRIVATE);
        fos.write(imageBytes);
        fos.close();
        return Uri.fromFile(new File(context.getFilesDir(), "profile_image.png"));  // Devuelve la URI
    }
}
