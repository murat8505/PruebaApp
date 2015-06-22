package com.vaw.miguel.notas;

/**
 * Created by Miguel on 04/05/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NotasSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE materias_alumno (cod_est TEXT,cod_mat TEXT, nota1 NUMBER, nota2 NUMBER, nota3 NUMBER, notap NUMBER, notaf NUMBER)";
    String sqlCreate1 = "CREATE TABLE materias (cod_mat TEXT, nom_mat TEXT, num_cred NUMBER)";
    String sqlCreate2 = "CREATE TABLE estudiante (cod_est TEXT, nom_est TEXT, ape_est TEXT, email_est TEXT)";
    String sqlCreate3 = "insert into estudiante values('000182598','Miguel','Manquillo','mmanquillo@outlook.com');";
    String sqlCreate4 =       "insert into materias values('33496','Auditoria de Sistemas',3)";
    String sqlCreate5 =        "insert into materias values('32477','Seminarios',3)";
    String sqlCreate6 =            "insert into materias values('33497','Electiva(IV)',3)";
    String sqlCreate7 ="insert into materias values('33615','Inteligencia Artificial',3)";
    String sqlCreate8 =        "insert into materias values('29163','Matematicas Discretas',3)";
    String sqlCreate9 ="insert into materias values('29330','Identificacion',3)";
    String sqlCreate10 ="insert into materias values('32479','Señales y Sistemas',3)";
    String sqlCreate11 ="insert into materias_alumno values('00182598','33496',4.5,5,5,4.9,4.9)";
    String sqlCreate12 ="insert into materias_alumno values('00182598','32477',3.1,3.9,4.9,4,4)";
    String sqlCreate13 ="insert into materias_alumno values('00182598','33497',4.3,3.4,5,4.2,4.2)";
    String sqlCreate14 ="insert into materias_alumno values('00182598','33615',5,5,5,5,5)";
    String sqlCreate15 ="insert into materias_alumno values('00182598','29163',4,4.5,4.1,4.2,4.2)";
    String sqlCreate16 ="insert into materias_alumno values('00182598','29330',5,3.8,5,4.6,4.6)";
    String sqlCreate17 ="insert into materias_alumno values('00182598','32479',3.5,3.9,3.3,3.6,3.6)";

    public NotasSQLiteHelper(Context contexto, String nombre,
                                CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla

        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate1);
        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
        db.execSQL(sqlCreate4);
        db.execSQL(sqlCreate5);
        db.execSQL(sqlCreate6);
        db.execSQL(sqlCreate7);
        db.execSQL(sqlCreate8);
        db.execSQL(sqlCreate9);
        db.execSQL(sqlCreate10);
        db.execSQL(sqlCreate11);
        db.execSQL(sqlCreate12);
        db.execSQL(sqlCreate13);
        db.execSQL(sqlCreate14);
        db.execSQL(sqlCreate15);
        db.execSQL(sqlCreate16);
        db.execSQL(sqlCreate17);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS materias_alumno");
        db.execSQL("DROP TABLE IF EXISTS materias");
        db.execSQL("DROP TABLE IF EXISTS estudiante");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate1);
        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
        db.execSQL(sqlCreate4);
        db.execSQL(sqlCreate5);
        db.execSQL(sqlCreate6);
        db.execSQL(sqlCreate7);
        db.execSQL(sqlCreate8);
        db.execSQL(sqlCreate9);
        db.execSQL(sqlCreate10);
        db.execSQL(sqlCreate11);
        db.execSQL(sqlCreate12);
        db.execSQL(sqlCreate13);
        db.execSQL(sqlCreate14);
        db.execSQL(sqlCreate15);
        db.execSQL(sqlCreate16);
        db.execSQL(sqlCreate17);
    }
}
