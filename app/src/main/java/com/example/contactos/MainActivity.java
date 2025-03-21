package com.example.contactos;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contactos.configuracion.SQLiteConexion;
import com.example.contactos.configuracion.Transacciones;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {




    EditText nombre, telefono, nota;

    Button btnguardar, btnlista;


    ImageView imgFoto;
    ImageButton btnTomarFoto;

    String currentPhotoPath;
    int paisSeleccionado;

    static final int REQUEST_CAMERA = 1;
    static final int REQUEST_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        nota = (EditText) findViewById(R.id.txtNota);
        imgFoto = (ImageView) findViewById(R.id.imageView2);
        btnTomarFoto = (ImageButton) findViewById(R.id.btnfoto);

        btnguardar= (Button) findViewById(R.id.btnGuardar);
        btnlista =(Button) findViewById(R.id.btneliminar);




        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOpciones();
            }
        });

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lista.class);
                startActivity(intent);
            }
        });




    }



    private void validarDatos() {
         if (nota.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Debe de escribir una nota" ,Toast.LENGTH_LONG).show();
        } else {
            guardarContacto();
        }
    }

    private void guardarContacto() {
        try {
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameBD, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();



            valores.put(Transacciones.nota, nota.getText().toString());

            // Convertir la imagen en bytes y guardarla en la BD
            Drawable drawable = imgFoto.getDrawable();
            if (drawable == null) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una imagen", Toast.LENGTH_LONG).show();
                return;
            }

            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            byte[] imagenBytes = convertirBitmapABytes(bitmap);
            valores.put(Transacciones.imagen, imagenBytes);

            Long resultado = db.insert(Transacciones.Tabla, null, valores);
            if (resultado == -1) {
                Toast.makeText(getApplicationContext(), "Error al guardar el contacto", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Registro ingresado con éxito", Toast.LENGTH_LONG).show();
            }

            db.close();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Se produjo un error", Toast.LENGTH_LONG).show();
        }

        limpiarPantalla();
    }

    private void limpiarPantalla() {

        nota.setText("");
        imgFoto.setImageDrawable(null);

    }
    private byte[] convertirBitmapABytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    private void mostrarOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción");
        builder.setItems(new CharSequence[]{"Abrir Cámara", "Abrir Galería"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    abrirCamara();
                } else {
                    abrirGaleria();
                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                // Imagen tomada con la cámara
                Bundle extras = data.getExtras();
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                imgFoto.setImageBitmap(imgBitmap);
            } else if (requestCode == REQUEST_GALLERY) {
                // Imagen seleccionada de la galería
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}