package com.example.parcial2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    ListView Lista;
    ArrayList<Persona> ListaArray;
    ArrayList<String> ListaString;

    ControladorDB controladorDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Lista = findViewById(R.id.Lista);
        controladorDB = new ControladorDB(getApplicationContext());
        ListaArray = controladorDB.optenerRegistro();
        ListaString = mostrarLista(ListaArray);
        ArrayAdapter ArrayAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, ListaString);
        Lista.setAdapter(ArrayAdp);
        registerForContextMenu(Lista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Persona> lista = controladorDB.optenerRegistro();
                ListaString = mostrarLista(lista);
                ArrayAdapter ArrayAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, ListaString);
                Lista.setAdapter(ArrayAdp);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "modificacion cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.item1:
                modificarPersona(menuInfo.position);
                return true;
            case R.id.item2:
                eliminarPersona(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void eliminarPersona(int position) {
        int retorno = controladorDB.eliminar(ListaArray.get(position));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "registro eliminado", Toast.LENGTH_SHORT).show();
            ListaArray = controladorDB.optenerRegistro();
            ListaString = mostrarLista(ListaArray);
            ArrayAdapter ArrayAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, ListaString);
            Lista.setAdapter(ArrayAdp);
        } else {
            Toast.makeText(getApplicationContext(), "error al borrar", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarPersona(int position) {
        Intent i = new Intent(this, ModificarActivity.class);
        i.putExtra("indice", position);
        startActivityForResult(i, 2);
    }

    private ArrayList<String> mostrarLista(ArrayList<Persona> personas) {
        ArrayList<String> lista = new ArrayList<>();
        for (Persona persona : personas) {
            String estrat = "";
            switch (persona.getEstrato()) {
                case 0:
                    estrat = "Estrato 1";
                    break;
                case 1:
                    estrat = "Estrato 2";
                    break;
                case 2:
                    estrat = "Estrato 3";
                    break;
                case 3:
                    estrat = "Estrato 4";
                    break;
                case 4:
                    estrat = "Estrato 5";
                    break;
                case 5:
                    estrat = "Estrato 6";
                    break;
            }


            String Edu = "";
            switch (persona.getNiveleducativo()){

                case 1:
                    Edu = "Bachillerato";
                    break;
                case 2:
                    Edu = "Pregrado";
                    break;
                case 3:
                    Edu = "Posgrado";
                    break;
                case 4:
                    Edu = "Maestria";
                    break;
                case 5:
                    Edu = "Doctorado";
                    break;
            }

            String temp2 = persona.getCedula() + " " + persona.getNombre() + " " +
                    estrat + " " + persona.getSalario() + " " + Edu;
            lista.add(temp2);

        }
        return lista;
    }


}
