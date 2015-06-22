package com.vaw.miguel.pruebaapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private EditText txtNombre;
    private Button btnEnviar;
    private SQLiteDatabase db;
    private String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos una referencia de los controles de la interfaz
        txtNombre = (EditText)findViewById(R.id.TxtNombre);
        btnEnviar = (Button)findViewById(R.id.btnEnviar);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "PRUEBAAPPUsuarios", null, 1);

        db = usdbh.getWritableDatabase();

        //Implementamos el evento click del botón
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //insertaBD(txtNombre.getText().toString());
                mensaje = txtNombre.getText().toString();
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, saludoActivity.class);

                //Si hemos abierto correctamente la base de datos
                if(db != null)
                {
                    //Insertar un registro
                    db.execSQL("INSERT INTO Usuarios (codigo,nombre)Values (24,'"+mensaje+"') ");

                    //Cerramos la base de datos
                    db.close();
                }



                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtNombre.getText().toString());

                //Añadimos la información al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
    }

    /*public void insertaBD(String nombre){
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "PRUEBAAPPUsuarios", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            //Insertar un registro
            db.execSQL("INSERT INTO Usuarios (codigo,nombre) VALUES (6,"+nombre+") ");

            //Cerramos la base de datos
            db.close();
        }
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
