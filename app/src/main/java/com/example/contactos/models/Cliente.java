package com.example.contactos.models;



public class Cliente {

    private Integer id;

    private String nota;
    private byte[] imagen; // Nuevo atributo para la imagen

    // Constructor vacío
    public Cliente() {
    }

    // Constructor con parámetros
    public Cliente(Integer id, String nota, byte[] imagen) {
        this.id = id;
        this.nota = nota;
        this.imagen = imagen;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
