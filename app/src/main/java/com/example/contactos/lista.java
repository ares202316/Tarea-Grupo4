package com.example.contactos;

import static com.example.contactos.configuracion.Transacciones.Tabla;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactos.configuracion.SQLiteConexion;
import com.example.contactos.configuracion.Transacciones;
import com.example.contactos.models.Clientes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class lista extends AppCompatActivity {
    FloatingActionButton btnlista;
    ArrayList<String> id, nombres, telefonos,notas,pais;
    ArrayList<byte[]> imagenes; // Nueva lista para imágenes
    Clientes clientes;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView = findViewById(R.id.viewlista);
        btnlista = findViewById(R.id.btnregresar);

        btnlista.setOnClickListener(v -> {
            Intent intent = new Intent(lista.this, MainActivity.class);
            startActivity(intent);
        });

        id = new ArrayList<>();

        notas = new ArrayList<>();

        imagenes = new ArrayList<>(); // Inicializar la lista de imágenes

        displayData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clientes = new Clientes(this, id ,notas,  imagenes);
        recyclerView.setAdapter(clientes);
    }

    Cursor listaRegistros() {
        String query = "SELECT id, nota, imagen FROM contacto"; // Asegurar que 'imagen' está en la consulta
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameBD, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void displayData() {
        try (Cursor cursor = listaRegistros()) {
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No hay datos", Toast.LENGTH_LONG).show();
            } else {
                while (cursor.moveToNext()) {
                    id.add(cursor.getString(0));

                    notas.add(cursor.getString(1));


                    // Recuperar imagen como BLOB
                    byte[] imagenBytes = cursor.getBlob(2);
                    imagenes.add(imagenBytes);
                }
            }
        }
    }
}
