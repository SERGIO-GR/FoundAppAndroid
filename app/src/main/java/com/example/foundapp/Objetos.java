package com.example.foundapp;

public class Objetos {
    private String nombre,descripcion,tipo,categoria,pid,fecha,hora,imagen;

    public Objetos(){}

    public Objetos(String nombre, String descripcion, String tipo,String imagen, String categoria, String pid, String fecha, String hora) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.tipo = tipo;
        this.categoria = categoria;
        this.pid = pid;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
 }
