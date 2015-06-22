package com.vaw.miguel.notas;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Notas extends ActionBarActivity {

    private TextView etNParcial, etNFinal, etNRC, etCreditos, etMateria;
    private EditText etN1,etN2,etN3;
    private Button btnCalcular;
    private String tN1,tN2,tN3,cadena,nrc;
    private String tNP,tNF;
    private double P1 = 0.3,P2 = 0.35,P3 = 0.35,N1,N2,N3;
    public Cursor c;
    NotasSQLiteHelper usdbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        etN1 = (EditText)findViewById(R.id.etN1);
        etN2 = (EditText)findViewById(R.id.etN2);
        etN3 = (EditText)findViewById(R.id.etN3);
        etNRC = (TextView)findViewById(R.id.etNRC);
        etCreditos = (TextView)findViewById(R.id.etCreditos);
        etMateria = (TextView)findViewById(R.id.etMateria);
        etNParcial = (TextView)findViewById(R.id.etNParcial);
        etNFinal = (TextView)findViewById(R.id.etNFinal);
        btnCalcular = (Button)findViewById(R.id.bCalcular);
        cadena ="";
        cargarPantalla();
        nrc = etNRC.getText().toString();

        validaFocus(etN1,etN2,etN3);

        //Implementamos el evento click del botón
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cadena=calculaNotas(etN1,etN2,etN3);
               if (!TextUtils.isEmpty(cadena)){
                    Toast toast1 = Toast.makeText(getApplicationContext(), cadena, Toast.LENGTH_LONG);
                    toast1.show();
               }
               retonarPantalla();
            }

        });
        //Edit Text onChange()
        etN1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                tN1 = etN1.getText().toString();

                if (!TextUtils.isEmpty(tN1)) {
                    if (tN1.equals(".")){
                        tN1 = "0";
                    }
                    N1 = (double)(Double.parseDouble(tN1));
                    if (N1>5 ||N1<0){
                        AlertDialog.Builder alert = new AlertDialog.Builder(Notas.this);
                        alert.setTitle("Advertencia");
                        alert.setMessage("El campo solo puede tomar valores entre 0 y 5");
                        alert.setCancelable(false);
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                etN1.setText("");
                                etN1.requestFocus();
                                return;
                           }
                        });
                        alert.show();
                    }
                }
            }
        });
        // Nota 2
        etN2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                tN2 = etN2.getText().toString();

                if (!TextUtils.isEmpty(tN2)) {
                    if (tN2.equals(".")){
                        tN2 = "0";
                    }
                    N2 = (double)(Double.parseDouble(tN2));
                    if (N2>5 ||N2<0){
                        AlertDialog.Builder alert = new AlertDialog.Builder(Notas.this);
                        alert.setTitle("Advertencia");
                        alert.setMessage("El campo solo puede tomar valores entre 0 y 5");
                        alert.setCancelable(false);
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                etN2.setText("");
                                etN2.requestFocus();
                                return;
                            }
                        });
                        alert.show();
                    }
                }
            }
        });// Nota 3
        etN3.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                tN3 = etN3.getText().toString();

                if (!TextUtils.isEmpty(tN3)) {
                    if (tN3.equals(".")){
                        tN3 = "0";
                    }
                    N3 = (double)(Double.parseDouble(tN3));
                    if (N3>5 ||N3<0){
                        AlertDialog.Builder alert = new AlertDialog.Builder(Notas.this);
                        alert.setTitle("Advertencia");
                        alert.setMessage("El campo solo puede tomar valores entre 0 y 5");
                        alert.setCancelable(false);
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                etN3.setText("");
                                etN3.requestFocus();
                                return;
                            }
                        });
                        alert.show();
                    }
                }
            }
        });
        //Fin Edit Text onChange()
    }

    public void cargarPantalla(){
        Bundle bundle = this.getIntent().getExtras();
        etNRC.setText(bundle.getString("etNRC"));
        nrc = etNRC.getText().toString();

        //Abrimos la base de datos 'vawnotas' en modo escritura
        usdbh = new NotasSQLiteHelper(this, "vawnotas", null, 1);

        db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            c = db.rawQuery(" SELECT cod_est,ma.cod_mat,nota1, nota2, nota3, notap, notaf, nom_mat, num_cred FROM materias m, materias_alumno ma where m.cod_mat = ma.cod_mat and ma.cod_mat ='"+nrc+"'", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    etN1.setText(c.getString(2));
                    etN2.setText(c.getString(3));
                    etN3.setText(c.getString(4));
                    etNParcial.setText(c.getString(5));
                    etNFinal.setText(c.getString(6));
                    etMateria.setText(c.getString(7));
                    etCreditos.setText(c.getString(8));
                } while(c.moveToNext());
            }

            //Cerramos la base de datos
            db.close();
        }


    }
    public void validaFocus(final EditText etN1 , final EditText etN2, final EditText etN3){
        //Campo 1
        etN1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    if (TextUtils.isEmpty(etN1.getText().toString())){
                        etN2.setText("");
                        etN3.setText("");
                    }
                }
            }
        });
        //Campo 2
        etN2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){

                    if (TextUtils.isEmpty(etN1.getText().toString())){
                        AlertDialog.Builder alert = new AlertDialog.Builder(Notas.this);
                        alert.setTitle("Advertencia");
                        alert.setMessage("Llenar el campo % 1 Corte");
                        alert.setCancelable(false);
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                etN1.requestFocus();
                                return;
                            }
                        });
                        alert.show();
                    }

                }else {
                    if (TextUtils.isEmpty(etN2.getText().toString())){

                        etN3.setText("");
                    }
                }
            }
        });
        //Campo 3
        etN3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){

                    if (TextUtils.isEmpty(etN1.getText().toString())){
                        AlertDialog.Builder alert = new AlertDialog.Builder(Notas.this);
                        alert.setTitle("Advertencia");
                        alert.setMessage("Llenar los campos % 1 Corte - % 2 Corte");
                        alert.setCancelable(false);
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                etN1.requestFocus();
                                return;
                            }
                        });
                        alert.show();
                    }else if (TextUtils.isEmpty(etN2.getText().toString())){
                        AlertDialog.Builder alert = new AlertDialog.Builder(Notas.this);
                        alert.setTitle("Advertencia");
                        alert.setMessage("Llenar el campo % 2 Corte");
                        alert.setCancelable(false);
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                etN2.requestFocus();
                                return;
                            }
                        });
                        alert.show();
                    }

                }
            }
        });


    }//Fin validaFocus

    public void retonarPantalla(){
        nrc = etNRC.getText().toString();

        //Creamos el Intent
        Intent intent = new Intent(Notas.this, pantallaNotas.class);
        //Creamos la información a pasar entre actividades
        Bundle b = new Bundle();

        //Abrimos la base de datos 'vawnotas' en modo escritura
        usdbh = new NotasSQLiteHelper(this, "vawnotas", null, 1);

        db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            c = db.rawQuery(" SELECT cod_est,cod_mat,nota1, nota2, nota3, notap, notaf FROM materias_alumno where cod_mat ='"+nrc+"'", null);

            tN1 = etN1.getText().toString();
            tN2 = etN2.getText().toString();
            tN3 = etN3.getText().toString();
            tNP = etNParcial.getText().toString();
            tNF = etNFinal.getText().toString();

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {

                //Creamos el registro a insertar como objeto ContentValues
                ContentValues actualizar = new ContentValues();

                if (tN1 != ""){
                    actualizar.put("nota1",tN1);
                }else{
                    actualizar.put("nota1","");
                }
                if (tN2 != ""){
                    actualizar.put("nota2",tN2);
                }
                if (tN3 != ""){
                    actualizar.put("nota3",tN3);
                }
                if (tNP != ""){
                    actualizar.put("notaP",tNP);
                }
                if (tNF != "" && tN3 != ""){
                    actualizar.put("notaF",tNF);
                }else{
                    actualizar.put("notaF","");
                }
                //Insertamos el registro en la base de datos
                //Actualizamos el registro en la base de datos
                db.update("materias_alumno", actualizar, "cod_mat='"+nrc+"'", null);

                //String prueba ="update materias_alumno set nota1 = "+etN1.getText().toString()+", nota2 = "+etN2.getText().toString()+", nota3 = "+etN3.getText().toString()+",notap = "+etNParcial.getText().toString()+",notaf = "+etNFinal.getText().toString()+" where cod_mat ='"+nrc+"'";
                //db.execSQL(prueba);
                // = prueba;
            }else{
                //Creamos el registro a insertar como objeto ContentValues
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("cod_est", "000182598");
                nuevoRegistro.put("cod_mat",nrc);
                if (tN1 != ""){
                    nuevoRegistro.put("nota1",tN1);
                }
                if (tN2 != ""){
                    nuevoRegistro.put("nota2",tN2);
                }
                if (tN3 != ""){
                    nuevoRegistro.put("nota3",tN3);
                }
                if (tNP != ""){
                    nuevoRegistro.put("notaP",tNP);
                }
                if (tNF != ""){
                    nuevoRegistro.put("notaF",tNF);
                }
                //Insertamos el registro en la base de datos
                db.insert("materias_alumno", null, nuevoRegistro);

                String prueba ="insert into materias_alumno values('000182598','"+nrc+"',"+etN1.getText().toString()+","+etN2.getText().toString()+","+etN3.getText().toString()+","+etNParcial.getText().toString()+","+etNFinal.getText().toString()+")";
                //db.execSQL(prueba);
            }


            //Cerramos la base de datos
            db.close();
        }

        //Añadimos la información al intent
        //intent.putExtras(b);

        //Iniciamos la nueva actividad
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public String calculaNotas(EditText etN1 , EditText etN2, EditText etN3){

        int div = 1, pasa = 3;
        String cadena;
        double notaFinal,notaParcial,notaFalta;
        //Localizar los controles

        tN1 = etN1.getText().toString();
        tN2 = etN2.getText().toString();
        tN3 = etN3.getText().toString();
        etNParcial.setText("");
        etNFinal.setText("");
        N1 = 0;
        N2 = 0;
        N3 = 0;
        cadena ="";

        if (!TextUtils.isEmpty(tN1)) {
            N1 = (double)(Double.parseDouble(tN1)*P1);
        }else {
            retonarPantalla();
        }
        if (!TextUtils.isEmpty(tN2)) {
            N2 = (double)(Double.parseDouble(tN2)*P2);
            div = 2;
        }
        if (!TextUtils.isEmpty(tN3)) {
            N3 = (double)(Double.parseDouble(tN3)*P3);
            div = 3;
        }

        notaParcial = Math.round((N1+N2+N3)*10);
        notaParcial = notaParcial/10;

        etNParcial.setText(""+notaParcial);

        if (notaParcial>=pasa && div ==3) {
            cadena = "Su nota final es de: " + notaParcial + " Ya paso la materia";
            notaFinal = notaParcial;
            etNFinal.setText(""+notaFinal);
        }else if (notaParcial<pasa && div ==3) {
            cadena = "Su nota final es de: " + notaParcial + " No paso la materia";
            notaFinal = notaParcial;
            etNFinal.setText(""+notaFinal);
        }else if (notaParcial>pasa && div != 3){
            cadena = "Su nota parcial es de: "+notaParcial+" Ya paso la materia";
        }else if(div == 1){
            notaFalta = Math.round((((pasa) - N1)/(P2+P3))*10);
            notaFalta = notaFalta/10;
            if (notaFalta >5){
                cadena = "Su nota parcial es de: "+notaParcial+" necesita sacar por lo menos "+notaFalta+" mejor no vuelva, no alcanza a pasar la materia";
            }else{
                cadena = "Su nota parcial es de: "+notaParcial+" necesita sacar por lo menos "+notaFalta+" y "+notaFalta+" para pasar la materia";
            }

        }else if(div == 2){
            notaFalta = Math.round((((pasa) - (N1+N2))/P3)*10);
            notaFalta = notaFalta /10;
            if (notaFalta > 5){
                cadena = "Su nota parcial es de: "+notaParcial+" necesita sacar por lo menos "+notaFalta+" mejor no vuelva, no alcanza a pasar la materia";
            }else {
                cadena = "Su nota parcial es de: " + notaParcial + " necesita sacar por lo menos " + notaFalta + " para pasar la materia";
            }
        }

       return cadena;

    }
}
