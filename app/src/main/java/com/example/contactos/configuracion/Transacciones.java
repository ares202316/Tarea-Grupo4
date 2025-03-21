package com.example.contactos.configuracion;

public class Transacciones {
    public static final String NameBD = "ContactosDB";
    public static final String Tabla = "contacto";  // <- Asegúrate de que el nombre coincide con el del error

    public static final String id = "id";
    public static final String nota = "nota";
    public static final String imagen = "imagen";

    public static final String CREATE_TABLE_CONTACTO = "CREATE TABLE " + Tabla + " (" +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            nota + " TEXT NOT NULL, " +
            imagen + " BLOB)";
}
