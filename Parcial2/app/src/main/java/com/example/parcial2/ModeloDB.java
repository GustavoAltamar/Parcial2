package com.example.parcial2;

public class ModeloDB {

    public static final String NOMBRE_DB = "registro";
    public static final String NOMBRE_TABLA = "persona";
    public static final String COL_CEDULA = "cedula";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_ESTRATO = "estrato";
    public static final String COL_SALARIO = "salario";
    public static final String COL_EDUCAION = "educacion";

    public static final String CREAR_TABLA_REGISTRO = "CREATE TABLE " +
            "" + NOMBRE_TABLA + " ( " + COL_CEDULA + " INTEGER PRIMARY KEY, " +
            " " + COL_NOMBRE + " TEXT, " + COL_ESTRATO + " TEXT, " +
            " " + COL_SALARIO + " TEXT, " + COL_EDUCAION + " TEXT)";

}
