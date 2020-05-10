package com.example.parcial2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControladorDB {
    private AdminSQLiteOpenHelper baseDatos;

    public ControladorDB(Context context) {

        this.baseDatos = new AdminSQLiteOpenHelper(context, ModeloDB.NOMBRE_DB, null, 1);

    }

    public long Registrar(Persona persona) {

        SQLiteDatabase BaseDeDatos = baseDatos.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(ModeloDB.COL_CEDULA, persona.getCedula());
        registro.put(ModeloDB.COL_NOMBRE, persona.getNombre());
        registro.put(ModeloDB.COL_ESTRATO, persona.getEstrato());
        registro.put(ModeloDB.COL_SALARIO, persona.getSalario());
        registro.put(ModeloDB.COL_EDUCAION, persona.getNiveleducativo());
        return BaseDeDatos.insert(ModeloDB.NOMBRE_TABLA, null, registro);

    }

    public int eliminar(Persona persona) {
        SQLiteDatabase BaseDeDatos = baseDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(persona.getCedula())};
        return BaseDeDatos.delete(ModeloDB.NOMBRE_TABLA, ModeloDB.COL_CEDULA + " = ?", argumentos);
    }

    public int modificar(Persona persona){
        SQLiteDatabase BaseDeDatos = baseDatos.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(ModeloDB.COL_NOMBRE, persona.getNombre());
        registro.put(ModeloDB.COL_ESTRATO, persona.getEstrato());
        registro.put(ModeloDB.COL_SALARIO, persona.getSalario());
        registro.put(ModeloDB.COL_EDUCAION, persona.getNiveleducativo());

        String campoParaActualizar = ModeloDB.COL_CEDULA + " = ?";
        String[] argumentosParaActualizar = {String.valueOf(persona.getCedula())};

        return BaseDeDatos.update(ModeloDB.NOMBRE_TABLA, registro, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Persona> optenerRegistro(){
        ArrayList<Persona> personas = new ArrayList<>();

        SQLiteDatabase database = baseDatos.getReadableDatabase();

        String[]columnasConsultar= {ModeloDB.COL_CEDULA, ModeloDB.COL_NOMBRE, ModeloDB.COL_ESTRATO
                ,ModeloDB.COL_SALARIO, ModeloDB.COL_EDUCAION };

        Cursor cursor =database.query(ModeloDB.NOMBRE_TABLA, columnasConsultar,
                null,null,null,null, null);

                if (cursor==null){
                return personas;
                }

                if (!cursor.moveToFirst()){
                return personas;
                }
                do { Persona persona = new Persona(cursor.getInt(0), cursor.getString(1)
                        , cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
                    personas.add(persona);
                } while (cursor.moveToNext());

            cursor.close();
                return personas;


    }

}