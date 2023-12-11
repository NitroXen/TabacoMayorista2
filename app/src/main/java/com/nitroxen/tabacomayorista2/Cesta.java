package com.nitroxen.tabacomayorista2;

public class Cesta {

    private String nomProd;
    private int cantidad;
    private double precioTotal;

    public Cesta(String nomProd, int cantidad, double precioTotal) {
        this.nomProd = nomProd;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public String getNomProd() {
        return nomProd;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    @Override
    public String toString() {
        return nomProd+"---"+cantidad+" = "+precioTotal;
    }
}
