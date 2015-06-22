package com.vaw.miguel.notasinterfaz;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MenuNotas extends ActionBarActivity {

    private String[] opcionesMenu;
    private String[] listaPrincipal;
    private DrawerLayout drawerLayout;
    private ListView drawerList,list;
    private ActionBarDrawerToggle toggle; // Menu Toggle para el ListView en el action Bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_notas);

        opcionesMenu = new String[] {"Visualizar Notas", "Calcular Notas", "Agregar Materias"};
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter(this,R.layout.listlayout,R.id.listTextView, opcionesMenu));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position+1, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        //Creamos el Intent
                        Intent intent2 = new Intent(MenuNotas.this, PruebaTabs2.class);

                        //Iniciamos la nueva actividad
                        startActivity(intent2);

                        break;
                    case 2:
                        //Creamos el Intent
                        Intent intent = new Intent(MenuNotas.this, AgregarMaterias.class);
                        //Creamos la informacion a pasar entre actividades
                        Bundle b = new Bundle();
                        //Anadimos la informacion al intent
                        intent.putExtras(b);
                        //Iniciamos la nueva actividad
                        startActivity(intent);
                        break;
                    case 3:

                        break;
                }

            }

        });

        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        //List View Pantalla Principal
        listaPrincipal = new String[] {"Agregar Materias","Visualizar Notas","Calcular Notas"};
        list = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaPrincipal);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
