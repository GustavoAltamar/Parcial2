package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ControladorDB db;

    int estrato;
    int nivelEducativo;


    EditText et_cedula, et_nombre, et_salario;
    Spinner sp_estrato, sp_edu;
    Button guardar, lista, buscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = findViewById(R.id.txtnombre);
        et_cedula = findViewById(R.id.txtcedula);
        et_salario = findViewById(R.id.txtsalario);
        sp_estrato = findViewById(R.id.spinner);
        sp_edu = findViewById(R.id.spinner2);

        guardar = findViewById(R.id.button);
       buscar = findViewById(R.id.button2);
        lista = findViewById(R.id.button3);

        db = new ControladorDB(getApplicationContext());

        ArrayAdapter<CharSequence> primerAdapter = ArrayAdapter.createFromResource(this
                , R.array.estrato, R.layout.support_simple_spinner_dropdown_item);
        sp_estrato.setAdapter(primerAdapter);

        sp_estrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estrato = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> segundoAdapter = ArrayAdapter.createFromResource(this
                , R.array.nivelEducativo, R.layout.support_simple_spinner_dropdown_item);
        sp_edu.setAdapter(segundoAdapter);

        sp_edu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivelEducativo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        guardar.setOnClickListener(this);
        lista.setOnClickListener(this);
        buscar.setOnClickListener(this);

    }


    public void listar() {
        Intent i = new Intent(this, ListaActivity.class);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                try {
                    if (!et_cedula.getText().toString().isEmpty()) {

                        int cedula = Integer.parseInt(et_cedula.getText().toString());
                        Persona persona =
                                new Persona(cedula, et_nombre.getText().toString(), estrato, et_salario.getText().toString(), nivelEducativo);
                        long retorno = db.Registrar(persona);
                        limpiarCampo();
                        if (retorno == -1) {
                            Toast.makeText(getApplicationContext(), "a ocurrido un error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "registro exitoso", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button3:
                listar();
                break;
        }


    }


    public void buscar(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, ModeloDB.NOMBRE_DB, null,1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String cedula = et_cedula.getText().toString();
        if (!cedula.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery("select nombre from ModeloDB where cedula = " + cedula, null);

            if(fila.moveToFirst()){
                et_cedula.setText(fila.getString(0));
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "Persona no encontrada", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debe ingresar una cedula", Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();
        }

        buscar.setOnClickListener(this);

    }

    private void limpiarCampo() {
        et_nombre.setText("");
        et_cedula.setText("");
        et_salario.setText("");
    }

}
