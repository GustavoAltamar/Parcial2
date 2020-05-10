package com.example.parcial2;

public class Persona {

    private int cedula;
    private String nombre;
    private int estrato;
    private String salario;
    private int niveleducativo;

    public Persona(int cedula, String nombre, int estrato, String salario, int niveleducativo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.estrato = estrato;
        this.salario = salario;
        this.niveleducativo = niveleducativo;

    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String nombre) {
        this.salario = salario;
    }

    public int getNiveleducativo() {
        return niveleducativo;
    }

    public void setNiveleducativo(int niveleducativo) {
        this.niveleducativo = niveleducativo;
    }
}
