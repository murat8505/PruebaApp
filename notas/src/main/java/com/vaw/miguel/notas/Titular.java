package com.vaw.miguel.notas;

/**
 * Created by Miguel on 18/06/2015.
 */
public class Titular
{
    private String codigo;
    private String titulo;
    private String corte1;
    private String corte2;
    private String corte3;
    private String notaF;

    public Titular(String cod,String tit, String c1, String c2, String c3, String nF){
        codigo = cod;
        titulo = tit;
        corte1 = c1;
        corte2 = c2;
        corte3 = c3;
        notaF = nF;
    }

    public String getTitulo(){
        return titulo;
    }
    public String getCodigo(){
        return codigo;
    }

    public String getCorte1(){
        return corte1;
    }
    public String getCorte2(){
        return corte2;
    }
    public String getCorte3(){
        return corte3;
    }
    public String getNotaF(){
        return notaF;
    }

}