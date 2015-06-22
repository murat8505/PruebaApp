package com.vaw.miguel.pruebaapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class saludoActivity extends ActionBarActivity {

    private TextView txtSaludo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);

        //Localizar los controles
        txtSaludo = (TextView)findViewById(R.id.txtSaludo);

        //Recuperamos la información pasada en el intent
       /* Bundle bundle = this.getIntent().getExtras();

        //Construimos el mensaje a mostrar
        txtSaludo.setText("Hola " + bundle.getString("NOMBRE"));*/
        recuperaRegistro();
    }

    public void recuperaRegistro(){
        //Localizar los controles
        txtSaludo = (TextView)findViewById(R.id.txtSaludo);
        String mensaje ="";
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "PRUEBAAPPUsuarios", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            Cursor c = db.rawQuery(" SELECT codigo,nombre FROM Usuarios ", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String codigo= c.getString(0);
                    String nombre = c.getString(1);
                    mensaje = mensaje+"\n Codigo: "+codigo+" Nombre: "+nombre;
                } while(c.moveToNext());
            }

            //Cerramos la base de datos
            db.close();
        }



        txtSaludo.setText(mensaje);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saludo, menu);
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
