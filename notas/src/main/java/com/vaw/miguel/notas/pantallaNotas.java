package com.vaw.miguel.notas;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class pantallaNotas extends ActionBarActivity {

    private SQLiteDatabase db;
    private NotasSQLiteHelper usdbh;
    private RecyclerView recView;
    private ArrayList<Titular> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_notas);
        // Habilitas icono
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String [] spin = spinner();
        //Appbar page filter
        Spinner cmbToolbar = (Spinner) findViewById(R.id.CmbToolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,new String[] {"Opcion 1","Opcion 2"});

        adapter.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbar.setAdapter(adapter);

        cmbToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //... Acciones al seleccionar una opción de la lista
                Log.i("Toolbar 3", "Seleccionada opción " + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //... Acciones al no existir ningún elemento seleccionado
                getSupportActionBar().setTitle("Todos");
            }
        });
        datos = new ArrayList<Titular>();
        //buscar("");

    }

    public void retornarPantalla(String nrc1){
        //Creamos el Intent
        Intent intent = new Intent(pantallaNotas.this, Notas.class);
        //Creamos la información a pasar entre actividades
        Bundle b = new Bundle();
        b.putString("etNRC", nrc1);
        //Añadimos la información al intent
        intent.putExtras(b);

        //Iniciamos la nueva actividad
        startActivity(intent);
                
    }

    public String[] spinner(){

        int tamaño=0;
        usdbh = new NotasSQLiteHelper(this, "vawnotas", null, 1);
        db = usdbh.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            Cursor c = db.rawQuery("SELECT Count(p.idPeriodo) FROM materiasMatriculadas mm, periodo p \n" +
                    "WHERE mm.idPeriodo = p.idPeriodo\n" +
                    "group by p.idPeriodo", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    tamaño = Integer.parseInt(c.getString(0));
                } while(c.moveToNext());
            }
        }

        String spin[] = new String[tamaño];
        int i=0;
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            Cursor c = db.rawQuery("SELECT p.idPeriodo FROM materiasMatriculadas mm, periodo p \n" +
                    "WHERE mm.idPeriodo = p.idPeriodo\n" +
                    "group by p.idPeriodo", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    spin[i] = c.getString(0);
                } while(c.moveToNext());
            }

            //Cerramos la base de datos
            db.close();
        }

        return spin;

    }

    public void buscar(String busca){
        datos.clear();

        recView = (RecyclerView) findViewById(R.id.RecView);
        recView.removeAllViews();
        recView.clearAnimation();

        usdbh = new NotasSQLiteHelper(this, "vawnotas", null, 1);

        db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            Cursor c = db.rawQuery("SELECT mm.idMateriaMatriculada, m.nomMateria, sum(n.valorNota), c.idCorte, c.desCorte \n" +
                    "FROM materiasmatriculadas mm, notas n, materias m, corte c, materiasCorte mc\n" +
                    "WHERE mm.idEstudianteUni ='000182598'\n" +
                    "and n.idCorteMateriaMatriculada = mc.idCorteMateriaMatriculada\n" +
                    "and mm.idMateriaMatriculada = mc.idMateriaMatriculada\n" +
                    "and m.idMateria = mm.idMateria\n" +
                    "and c.idCorte = mc.idCorte\n" +
                    "and lower(m.nomMateria) like lower('%"+busca+"%')\n" +
                    "group by mm.idMateriaMatriculada, m.nomMateria,c.idCorte, c.desCorte \n" +
                    "order by m.nomMateria, c.idCorte", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    datos.add(new Titular(c.getString(5),c.getString(0), "Corte 1: "+c.getString(1), "Corte 2: "+c.getString(2), "Corte 3: "+c.getString(3), "Nota Final: "+c.getString(4)));
                } while(c.moveToNext());
            }

            //Cerramos la base de datos
            db.close();
        }


        recView.setHasFixedSize(true);
        final AdaptadorTitulares adaptador = new AdaptadorTitulares(datos);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DemoRecView", "Pulsado el elemento " + recView.getChildPosition(v));
                retornarPantalla(datos.get(recView.getChildPosition(v)).getCodigo());
            }
        });

        recView.setAdapter(adaptador);
        recView.setHasFixedSize(true);


        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recView.addItemDecoration(
                new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        recView.setItemAnimator(new DefaultItemAnimator());
        // Fin recycler View
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_notas, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchView));
        mSearchView.setQueryHint("Buscar Materias");

        if (mSearchView != null )
        {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            mSearchView.setIconifiedByDefault(false);

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
            {
                public boolean onQueryTextChange(String newText)
                {
                    buscar(newText);
                    return true;
                }

                public boolean onQueryTextSubmit(String query)
                {
                    buscar(query);
                    return true;
                }
            };

            mSearchView.setOnQueryTextListener(queryTextListener);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notas) {
            return true;
        }else if (id == R.id.acerca_menu) {
            //Creamos el Intent
            Intent intent = new Intent(pantallaNotas.this, AcercaDe.class);

            //Iniciamos la nueva actividad
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
