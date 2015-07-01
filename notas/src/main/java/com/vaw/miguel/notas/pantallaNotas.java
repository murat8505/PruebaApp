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
    private Spinner cmbToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_notas);
        // Habilitas icono
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        usdbh = new NotasSQLiteHelper(this, "vawnotas", null, 1);
        db = usdbh.getWritableDatabase();
        String[] spin = spinner();

        //Appbar page filter
       cmbToolbar = (Spinner) findViewById(R.id.CmbToolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,spin);

        adapter.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbar.setAdapter(adapter);

        cmbToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //... Acciones al seleccionar una opción de la lista
                Log.i("Toolbar 3", "Seleccionada opción " + i);
                buscar("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //... Acciones al no existir ningún elemento seleccionado
                getSupportActionBar().setTitle("Todos");
            }
        });
        datos = new ArrayList<Titular>();
        buscar("");

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

    public int tamSpinner(){
        int tamaño=0;

        //usdbh = new NotasSQLiteHelper(this, "vawnotas", null, 1);
        //db = usdbh.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            Cursor c = db.rawQuery("select count(*) from (SELECT p.idPeriodo FROM materiasMatriculadas mm, periodo p WHERE mm.idPeriodo = p.idPeriodo group by p.idPeriodo)", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    tamaño = Integer.parseInt(c.getString(0));
                } while(c.moveToNext());
            }
            //Cerramos la base de datos
            //db.close();
        }

        return tamaño;
    }

    public String[] spinner(){

        int tamaño = tamSpinner();
        String spin[] = new String[tamaño+1];
        int i=0;
        spin[i] = "Todos";
        i++;
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            Cursor d = db.rawQuery("SELECT p.idPeriodo FROM materiasMatriculadas mm, periodo p WHERE mm.idPeriodo = p.idPeriodo group by p.idPeriodo ", null);


            //Nos aseguramos de que existe al menos un registro
            if (d.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    spin[i] = "Periodo "+d.getString(0);
                    i++;
                } while(d.moveToNext());
            }

            //Cerramos la base de datos
            //db.close();
        }

        return spin;

    }

    public void buscar(String busca){
        datos.clear();
        recView = (RecyclerView) findViewById(R.id.RecView);
        recView.removeAllViews();
        recView.clearAnimation();

        if(db != null){
            String s= cmbToolbar.getSelectedItem().toString();
            s=s.replaceAll("Periodo ","");
            busca = busca.toLowerCase();
            String select ="";
            if (s.equals("Todos")){
                select ="SELECT mm.idMateriaMatriculada, m.nomMateria, sum(n.valorNota), c.idCorte, c.desCorte " +
                        "FROM materiasmatriculadas mm, notas n, materias m, corte c, materiasCorte mc " +
                        "WHERE mm.idEstudianteUni ='000182598' and n.idCorteMateriaMatriculada = mc.idCorteMateriaMatriculada " +
                        "and mm.idMateriaMatriculada = mc.idMateriaMatriculada and m.idMateria = mm.idMateria " +
                        "and c.idCorte = mc.idCorte and (lower(m.nomMateria) like '%"+busca+"%' or" +
                        " lower(m.nomMateria) like '%"+busca+"' or lower(m.nomMateria) like '"+busca+"%' or lower(m.nomMateria) like '"+busca+"') " +
                        "group by mm.idMateriaMatriculada, m.nomMateria,c.idCorte, c.desCorte order by m.nomMateria, c.idCorte";
            }else{
                select ="SELECT mm.idMateriaMatriculada, m.nomMateria, sum(n.valorNota), c.idCorte, c.desCorte " +
                        "FROM materiasmatriculadas mm, notas n, materias m, corte c, materiasCorte mc " +
                        "WHERE mm.idEstudianteUni ='000182598' and n.idCorteMateriaMatriculada = mc.idCorteMateriaMatriculada " +
                        "and mm.idMateriaMatriculada = mc.idMateriaMatriculada and m.idMateria = mm.idMateria " +
                        "and mm.idPeriodo = '"+s+"' " +
                        "and c.idCorte = mc.idCorte and (lower(m.nomMateria) like '%"+busca+"%' or" +
                        " lower(m.nomMateria) like '%"+busca+"' or lower(m.nomMateria) like '"+busca+"%' or lower(m.nomMateria) like '"+busca+"') " +
                        "group by mm.idMateriaMatriculada, m.nomMateria,c.idCorte, c.desCorte order by m.nomMateria, c.idCorte";
            }

            Cursor c = db.rawQuery(select, null);
            String id="",id_aux="", nomMateria="";
            String [] nombre = {"","","",""};
            String [] valor = {"","","",""};
            int i=0;
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    id_aux=id;
                    id=c.getString(0);

                    if (!id_aux.equals(id)){

                        if (i>0){
                            int a=0,j=0;
                            float vlr=0;
                            while(a==0){
                                if (nombre[j].equals(null)||nombre[j].equals("")){
                                    nombre[j] = "Nota Final ";

                                    vlr = Math.round((vlr/j)*10);
                                    vlr = vlr /10;

                                    valor[j] = ""+vlr;
                                    a=1;
                                }else{
                                    vlr += Float.parseFloat(valor[j]);
                                }
                                j++;
                            }
                            datos.add(new Titular(id_aux,nomMateria, nombre[0]+valor[0], nombre[1]+valor[1], nombre[2]+valor[2], nombre[3]+valor[3]));
                        }
                        for(int k=0;k<nombre.length;k++){
                            nombre[k]="";
                            valor[k]="";
                        }
                        i=0;
                        nomMateria = c.getString(1);
                        valor[i] = c.getString(2);
                        nombre[i] = c.getString(4)+"    ";
                    }else{
                        valor[i] = c.getString(2);
                        nombre[i] = c.getString(4)+"    ";
                    }

                    i++;

                    //datos.add(new Titular(c.getString(5),c.getString(0), "Corte 1: "+c.getString(1), "Corte 2: "+c.getString(2), "Corte 3: "+c.getString(3), "Nota Final: "+c.getString(4)));
                } while(c.moveToNext());

                if (i>0){
                    int a=0,j=0;
                    float vlr=0;
                    while(a==0){
                        if (nombre[j].equals(null)||nombre[j].equals("")){
                            nombre[j] = "Nota Final ";

                            vlr = Math.round((vlr/j)*10);
                            vlr = vlr /10;

                            valor[j] = ""+vlr;
                            a=1;
                        }else{
                            vlr += Float.parseFloat(valor[j]);
                        }
                        j++;
                    }
                    datos.add(new Titular(id_aux,nomMateria, nombre[0]+valor[0], nombre[1]+valor[1], nombre[2]+valor[2], nombre[3]+valor[3]));
                }
            }

            //Cerramos la base de datos
           // db.close();
        }


        recView.setHasFixedSize(true);
        final AdaptadorTitulares adaptador = new AdaptadorTitulares(datos);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DemoRecView", "Pulsado el elemento " + recView.getChildPosition(v));
                //retornarPantalla(datos.get(recView.getChildPosition(v)).getCodigo());
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
