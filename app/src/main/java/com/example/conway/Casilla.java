package com.example.conway;

public class Casilla {
    public int x, y, ancho;
    private int contenido;
    private boolean estado;

    public void fijarxy(int x, int y, int ancho) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
    }

    public boolean dentro(int xx, int yy) {
        return (xx >= this.x && xx <= this.x + ancho && yy >= this.y && yy <= this.y + ancho);
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    public int getContenido() {
        return contenido;
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "x=" + x +
                ", y=" + y +
                ", ancho=" + ancho +
                ", contenido=" + contenido +
                ", estado=" + estado +
                '}';
    }
}
