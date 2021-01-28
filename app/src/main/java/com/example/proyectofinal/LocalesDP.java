package com.example.proyectofinal;

public class LocalesDP {
    private String calif, ciudad, creador, disca, nombre, tipo, ubi,resena;

    public String getResena() {
        return resena;
    }

    public void setResena(String calif) {
        this.resena = calif;
    }

    public String getCalif() {
        return calif;
    }

    public void setCalif(String calif) {
        this.calif = calif;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String CDMX) {
        this.ciudad = CDMX;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getDisca() {
        return disca;
    }

    public void setDisca(String disca) {
        this.disca = disca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbi() {
        return ubi;
    }

    public void setUbi(String ubi) {
        this.ubi = ubi;
    }

    public String toString(){
        return calif+"$"+ciudad+"$"+ creador+"$"+ disca+"$"+ nombre+"$"+ tipo+"$"+ ubi;
    }
}
