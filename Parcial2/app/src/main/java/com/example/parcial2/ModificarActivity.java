package com.example.parcial2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ModificarActivity extends AppCompatActivity implements View.OnClickListener {

ArrayList<Persona> personas;

int id;
int indice;

ControladorDB db;

int estrato;
int nivelEducativo;

    EditText et_cedula, et_nombre, et_salario;
    Spinner sp_estrato, sp_edu;
    Button guardar, lista, buscar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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



        Intent intent = getIntent();
        indice = intent.getIntExtra("indice", 5);

        db = new ControladorDB(getApplicationContext());

        personas = db.optenerRegistro();

        Persona persona= personas.get(indice);
        id=persona.getCedula();

        et_cedula.setText(Integer.toString(persona.getCedula()));
        et_salario.setText(persona.getSalario());
        et_nombre.setText(persona.getNombre());

        ArrayAdapter<CharSequence> primerAdapter = ArrayAdapter.createFromResource(this
                , R.array.estrato, R.layout.support_simple_spinner_dropdown_item);
        sp_estrato.setAdapter(primerAdapter);
        sp_estrato.setSelection(persona.getEstrato());
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
        sp_edu.setSelection(persona.getNiveleducativo());
        sp_edu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivelEducativo = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        lista.setText("cancelar");
        guardar.setOnClickListener(this);
        lista.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom:
                try {
                    if (!et_cedula.getText().toString().isEmpty()) {
                        int cedula = Integer.parseInt(et_cedula.getText().toString());
                        Persona persona =
                                new Persona(cedula, et_nombre.getText().toString(), estrato, et_salario.getText().toString(), nivelEducativo);
                        int retorno = db.modificar(persona);
                        if (retorno == 0) {
                            Toast.makeText(getApplicationContext(), "a ocurrido un error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "modificacion exitoso", Toast.LENGTH_SHORT).show();
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    }
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button3:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }
}
