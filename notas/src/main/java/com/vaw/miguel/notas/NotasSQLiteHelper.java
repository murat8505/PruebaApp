package com.vaw.miguel.notas;

/**
 * Created by Miguel on 04/05/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NotasSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL
    String sqlCreate = "CREATE TABLE   Universidades (  idUniversidad VARCHAR(45) NOT NULL,  nomUniversidad VARCHAR(200) NOT NULL,  PRIMARY KEY (idUniversidad))";
    String sqlCreate1 = "CREATE TABLE  Ciudad (  idCiudad VARCHAR(10) NOT NULL,  nomCiudad VARCHAR(80) NOT NULL,  PRIMARY KEY (idCiudad))";
    String sqlCreate2 = "CREATE TABLE  UniversidadCiudad (idUniverCiu VARCHAR(30) NOT NULL,  idUniversidad VARCHAR(45) NOT NULL,  idCiudad VARCHAR(10) NOT NULL,  descUniversidad VARCHAR(120) NULL, PRIMARY KEY (idUniverCiu),  CONSTRAINT fk_UniversidadCiudad_Universidades    FOREIGN KEY (idUniversidad)    REFERENCES Universidades (idUniversidad),  CONSTRAINT fk_UniversidadCiudad_Ciudad1  FOREIGN KEY (idCiudad) REFERENCES Ciudad (idCiudad))";
    String sqlCreate3 = "CREATE TABLE  Materias (  idMateria VARCHAR(10) NOT NULL,  idUniverCiu VARCHAR(30) NOT NULL,  nomMateria VARCHAR(80) NOT NULL,  PRIMARY KEY (idMateria, idUniverCiu),  CONSTRAINT fk_Materias_UniversidadCiudad1    FOREIGN KEY (idUniverCiu)    REFERENCES UniversidadCiudad (idUniverCiu))";
    String sqlCreate4 = "CREATE TABLE  Corte (  idCorte VARCHAR(10) NOT NULL,  desCorte VARCHAR(45) NOT NULL,  PRIMARY KEY (idCorte))";
    String sqlCreate5 = "CREATE TABLE  Periodo (  idPeriodo VARCHAR(10) NOT NULL,  descSemestre VARCHAR(100) NOT NULL,  periodo INT NOT NULL,  ano INT NOT NULL,  PRIMARY KEY (idPeriodo))";
    String sqlCreate6 = "CREATE TABLE  Docentes (  idDocentes VARCHAR(30) NOT NULL,  nomDocente VARCHAR(45) NULL,  apeDocente VARCHAR(45) NULL,  idUniverCiu VARCHAR(30) NOT NULL,  PRIMARY KEY (idDocentes),  CONSTRAINT fk_Docentes_UniversidadCiudad1    FOREIGN KEY (idUniverCiu)    REFERENCES UniversidadCiudad (idUniverCiu))";
    String sqlCreate7 = "CREATE TABLE  Estudiante (  cedEst VARCHAR(30) NOT NULL,  nomEstudiante VARCHAR(200) NOT NULL,  apeEstudiante VARCHAR(200) NOT NULL,  PRIMARY KEY (cedEst))";
    String sqlCreate8 = "CREATE TABLE  EstudianteUni (  idEstudianteUni VARCHAR(40) NOT NULL,  cedEst VARCHAR(30) NOT NULL,  idUniverCiu VARCHAR(30) NOT NULL,  PRIMARY KEY (idEstudianteUni),  CONSTRAINT fk_EstudianteUni_Estudiante1    FOREIGN KEY (cedEst)    REFERENCES Estudiante (cedEst),  CONSTRAINT fk_EstudianteUni_UniversidadCiudad1    FOREIGN KEY (idUniverCiu)    REFERENCES UniversidadCiudad (idUniverCiu))";
    String sqlCreate9 = "CREATE TABLE  MateriasMatriculadas (  idMateriaMatriculada VARCHAR(75) NOT NULL,  idPeriodo VARCHAR(10) NOT NULL,  idMateria VARCHAR(10) NOT NULL,  idDocentes VARCHAR(30) NOT NULL,   idEstudianteUni VARCHAR(40) NOT NULL,  PRIMARY KEY (idMateriaMatriculada),  CONSTRAINT fk_MateriasMatriculadas_Periodo1    FOREIGN KEY (idPeriodo)    REFERENCES Periodo (idPeriodo),  CONSTRAINT fk_MateriasMatriculadas_Materias1    FOREIGN KEY (idMateria)    REFERENCES Materias (idMateria),  CONSTRAINT fk_MateriasMatriculadas_Docentes1    FOREIGN KEY (idDocentes)    REFERENCES Docentes (idDocentes),    CONSTRAINT fk_MateriasMatriculadas_EstudianteUni1    FOREIGN KEY (idEstudianteUni)    REFERENCES EstudianteUni (idEstudianteUni))";
    String sqlCreate10 = "CREATE TABLE  MateriasCorte (  idCorteMateriaMatriculada VARCHAR(120) NOT NULL,  idCorte VARCHAR(10) NOT NULL,  idMateriaMatriculada VARCHAR(75) NOT NULL,  porcenCorte DECIMAL(5,2) NOT NULL,  PRIMARY KEY (idCorteMateriaMatriculada),  CONSTRAINT fk_PeriodoCorte_Corte1    FOREIGN KEY (idCorte)    REFERENCES Corte (idCorte),  CONSTRAINT fk_PeriodoCorte_MateriasMatriculadas1    FOREIGN KEY (idMateriaMatriculada)    REFERENCES MateriasMatriculadas (idMateriaMatriculada))";
    String sqlCreate11 = "CREATE TABLE  Notas (  idNotas VARCHAR(85) NOT NULL,  idCorteMateriaMatriculada VARCHAR(120) NOT NULL,  tipNota VARCHAR(1) NOT NULL,  valorNota DECIMAL(5,2) NOT NULL,  porcenNota DECIMAL(5,2) NOT NULL,  fecha DATETIME NULL,  PRIMARY KEY (idNotas),  CONSTRAINT fk_Notas_MateriasCorte1    FOREIGN KEY (idCorteMateriaMatriculada)    REFERENCES MateriasCorte (idCorteMateriaMatriculada))";

    String sqlCreate12 = "INSERT INTO universidades (idUniversidad, nomUniversidad) VALUES ('FUCLG001', 'Fundación Universitaria Católica Lumen Gentium')";
    String sqlCreate13 = "INSERT INTO ciudad (idCiudad, nomCiudad) VALUES ('76001', 'CALI'), ('19001', 'POPAYAN')";
    String sqlCreate14 = "INSERT INTO estudiante (cedEst, nomEstudiante, apeEstudiante) VALUES ('1144076394', 'Miguel Angel', 'Manquillo Ruiz')";
    String sqlCreate15 = "INSERT INTO universidadciudad (idUniverCiu, idUniversidad, idCiudad, descUniversidad) VALUES ('CALFUCLG001', 'FUCLG001', '76001', 'Unicatolica Cali')";
    String sqlCreate16 = "INSERT INTO estudianteuni (idEstudianteUni, cedEst, idUniverCiu) VALUES ('000182598', '1144076394', 'CALFUCLG001')";
    String sqlCreate17 = "INSERT INTO periodo (idPeriodo, descSemestre, periodo, ano) VALUES ('2015-01', 'Primer Semestre 2015', '01', '2015'), ('2015-03', 'Cursos Intersemestrales', '03', '2015')";
    String sqlCreate18 = "INSERT INTO materias (idMateria, idUniverCiu, nomMateria) VALUES ('106', 'CALFUCLG001', 'Ingles IV'), ('33496', 'CALFUCLG001', 'Auditoria de Sistemas'), ('32477', 'CALFUCLG001', 'Seminarios'), ('33615', 'CALFUCLG001', 'Inteligencia Artificial'), ('33497', 'CALFUCLG001', 'Electiva IV'), ('29163', 'CALFUCLG001', 'Matematicas Discretas'), ('29330', 'CALFUCLG001', 'Identificación'), ('32479', 'CALFUCLG001', 'Señales y Sistemas')";
    String sqlCreate19 = "INSERT INTO corte (idCorte, desCorte) VALUES ('C001', 'Corte 1'), ('C002', 'Corte 2'), ('C003', 'Corte 3'), ('C004', 'Corte 4'), ('C005', 'Corte 5')";
    String sqlCreate20 = "INSERT INTO docentes (idDocentes, nomDocente, apeDocente, idUniverCiu) VALUES ('D00000', 'Sin Asignar', NULL, 'CALFUCLG001'), ('D00001', 'Carlos Alfonso', 'Lozano Caicedo', 'CALFUCLG001'), ('D00002', 'Enrique', 'Peña Fajardo', 'CALFUCLG001'), ('D00003', 'Victor Hugo', 'Hernandez Valencia', 'CALFUCLG001'), ('D00004', 'Juan', 'Rodriguez Ramirez', 'CALFUCLG001'), ('D00005', 'Alexander', 'Campo Ramirez', 'CALFUCLG001'), ('D00006', 'Alejandro', 'Paz Parra', 'CALFUCLG001'), ('D00007', 'Forbes', 'Pennington', 'CALFUCLG001')";
    String sqlCreate21 = "INSERT INTO materiasmatriculadas (idMateriaMatriculada, idPeriodo, idMateria, idDocentes, idEstudianteUni) VALUES ('1144076394-106', '2015-03', '106', 'D00007', '000182598'), ('1144076394-29163', '2015-01', '29163', 'D00004', '000182598'), ('1144076394-29330', '2015-01', '29330', 'D00005', '000182598'), ('1144076394-32477', '2015-01', '32477', 'D00002', '000182598'), ('1144076394-32479', '2015-01', '32479', 'D00006', '000182598'), ('1144076394-33496', '2015-01', '33496', 'D00001', '000182598'), ('1144076394-33497', '2015-01', '33497', 'D00003', '000182598'), ('1144076394-33615', '2015-01', '33615', 'D00001', '000182598')";

    String sqlCreate22 = "INSERT INTO materiascorte (idCorteMateriaMatriculada,idCorte, idMateriaMatriculada, porcenCorte) VALUES ('C001-1144076394-29163','C001', '1144076394-29163', '30'), ('C002-1144076394-29163','C002', '1144076394-29163', '35'), ('C003-1144076394-29163','C003', '1144076394-29163', '35'), ('C001-1144076394-106','C001', '1144076394-106', '100'), ('C001-1144076394-29330','C001', '1144076394-29330', '30'), ('C002-1144076394-29330','C002', '1144076394-29330', '35'), ('C003-1144076394-29330','C003', '1144076394-29330', '35'), ('C001-1144076394-32477','C001', '1144076394-32477', '30'), ('C002-1144076394-32477','C002', '1144076394-32477', '35'), ('C003-1144076394-32477', 'C003', '1144076394-32477', '35'), ('C001-1144076394-32479', 'C001', '1144076394-32479', '30'), ('C002-1144076394-32479', 'C002', '1144076394-32479', '35'), ('C003-1144076394-32479', 'C003', '1144076394-32479', '35'), ('C001-1144076394-33496', 'C001', '1144076394-33496', '30'), ('C002-1144076394-33496', 'C002', '1144076394-33496', '35'), ('C003-1144076394-33496', 'C003', '1144076394-33496', '35'), ('C001-1144076394-33497', 'C001', '1144076394-33497', '30'), ('C002-1144076394-33497', 'C002', '1144076394-33497', '35'), ('C003-1144076394-33497', 'C003', '1144076394-33497', '35'), ('C001-1144076394-33615', 'C001', '1144076394-33615', '30'), ('C002-1144076394-33615', 'C002', '1144076394-33615', '35'), ('C003-1144076394-33615', 'C003', '1144076394-33615', '35')";

    String sqlCreate23 = "INSERT INTO notas (idNotas, idCorteMateriaMatriculada, tipNota, valorNota, porcenNota, fecha) VALUES ('C001-1144076394-106-N001', 'C001-1144076394-106', 'P', '4.8', '100', '2015-06-25 00:00:00'), ('C001-1144076394-29163-N001', 'C001-1144076394-29163', 'P', '4', '100', '2015-03-25 00:00:00'), ('C002-1144076394-29163-N001', 'C002-1144076394-29163', 'P', '4.5', '100', '2015-04-25 00:00:00'), ('C003-1144076394-29163-N001', 'C003-1144076394-29163', 'P', '4.1', '100', '2015-06-25 00:00:00'), ('C001-1144076394-29330-N001', 'C001-1144076394-29330', 'P', '5', '100', '2015-03-25 00:00:00'), ('C002-1144076394-29330-N001', 'C002-1144076394-29330', 'P', '3.8', '100', '2015-04-25 00:00:00'), ('C003-1144076394-29330-N001', 'C003-1144076394-29330', 'P', '5', '100', '2015-06-25 00:00:00'), ('C001-1144076394-32477-N001', 'C001-1144076394-32477', 'P', '3.1', '100', '2015-03-25 00:00:00'), ('C002-1144076394-32477-N001', 'C002-1144076394-32477', 'P', '3.9', '100', '2015-04-25 00:00:00'), ('C003-1144076394-32477-N001', 'C003-1144076394-32477', 'P', '4.9', '100', '2015-06-25 00:00:00'), ('C001-1144076394-32479-N001', 'C001-1144076394-32479', 'P', '3.5', '100', '2015-03-25 00:00:00'), ('C002-1144076394-32479-N001', 'C002-1144076394-32479', 'P', '3.9', '100', '2015-04-25 00:00:00'), ('C003-1144076394-32479-N001', 'C003-1144076394-32479', 'P', '3.3', '100', '2015-06-25 00:00:00'), ('C001-1144076394-33496-N001', 'C001-1144076394-33496', 'P', '4.5', '100', '2015-03-25 00:00:00'), ('C002-1144076394-33496-N001', 'C002-1144076394-33496', 'P', '5', '100', '2015-04-25 00:00:00'), ('C003-1144076394-33496-N001', 'C003-1144076394-33496', 'P', '5', '100', '2015-06-25 00:00:00'), ('C001-1144076394-33497-N001', 'C001-1144076394-33497', 'P', '4.3', '100', '2015-03-25 00:00:00'), ('C002-1144076394-33497-N001', 'C002-1144076394-33497', 'P', '3.4', '100', '2015-04-25 00:00:00'), ('C003-1144076394-33497-N001', 'C003-1144076394-33497', 'P', '5', '100', '2015-06-25 00:00:00'), ('C001-1144076394-33615-N001', 'C001-1144076394-33615', 'P', '5', '100', '2015-03-25 00:00:00'), ('C002-1144076394-33615-N001', 'C002-1144076394-33615', 'P', '5', '100', '2015-04-25 00:00:00'), ('C003-1144076394-33615-N001', 'C003-1144076394-33615', 'P', '5', '100', '2015-06-25 00:00:00')";





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
        db.execSQL(sqlCreate18);
        db.execSQL(sqlCreate19);
        db.execSQL(sqlCreate20);
        db.execSQL(sqlCreate21);
        db.execSQL(sqlCreate22);
        db.execSQL(sqlCreate23);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        /*db.execSQL("DROP TABLE IF EXISTS materias_alumno");
        db.execSQL("DROP TABLE IF EXISTS materias");
        db.execSQL("DROP TABLE IF EXISTS estudiante");*/

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
        db.execSQL(sqlCreate18);
        db.execSQL(sqlCreate19);
        db.execSQL(sqlCreate20);
        db.execSQL(sqlCreate21);
        db.execSQL(sqlCreate22);
        db.execSQL(sqlCreate23);
    }
}
