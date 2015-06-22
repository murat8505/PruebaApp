package com.vaw.miguel.notas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class AcercaDe extends ActionBarActivity {
    private String N1_33496,N2_33496,N3_33496,NP_33496,NF_33496;
    private String N1_32477,N2_32477,N3_32477,NP_32477,NF_32477;
    private String N1_33497,N2_33497,N3_33497,NP_33497,NF_33497;
    private String N1_33615,N2_33615,N3_33615,NP_33615,NF_33615;
    private String N1_29163,N2_29163,N3_29163,NP_29163,NF_29163;
    private String N1_29330,N2_29330,N3_29330,NP_29330,NF_29330;
    private String N1_32479,N2_32479,N3_32479,NP_32479,NF_32479;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        try
        {
            cargarPantalla();
        }
        catch(Exception e)
        {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acerca_de, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notas) {
            retornarPantalla();
        }else if (id == R.id.acerca_menu) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cargarPantalla(){
        Bundle bundle = this.getIntent().getExtras();

        //33496
        N1_33496 = bundle.getString("N1_33496");
        N2_33496 = bundle.getString("N2_33496");
        N3_33496 = bundle.getString("N3_33496");
        NP_33496 = bundle.getString("NP_33496");
        NF_33496 = bundle.getString("NF_33496");

        //32477
        N1_32477 = bundle.getString("N1_32477");
        N2_32477 = bundle.getString("N2_32477");
        N3_32477 = bundle.getString("N3_32477");
        NP_32477 = bundle.getString("NP_32477");
        NF_32477 = bundle.getString("NF_32477");

        //33497
        N1_33497 = bundle.getString("N1_33497");
        N2_33497 = bundle.getString("N2_33497");
        N3_33497 = bundle.getString("N3_33497");
        NP_33497 = bundle.getString("NP_33497");
        NF_33497 = bundle.getString("NF_33497");

        //33615
        N1_33615 = bundle.getString("N1_33615");
        N2_33615 = bundle.getString("N2_33615");
        N3_33615 = bundle.getString("N3_33615");
        NP_33615 = bundle.getString("NP_33615");
        NF_33615 = bundle.getString("NF_33615");

        //29163
        N1_29163 = bundle.getString("N1_29163");
        N2_29163 = bundle.getString("N2_29163");
        N3_29163 = bundle.getString("N3_29163");
        NP_29163 = bundle.getString("NP_29163");
        NF_29163 = bundle.getString("NF_29163");

        //29330
        N1_29330 = bundle.getString("N1_29330");
        N2_29330 = bundle.getString("N2_29330");
        N3_29330 = bundle.getString("N3_29330");
        NP_29330 = bundle.getString("NP_29330");
        NF_29330 = bundle.getString("NF_29330");

        //32479
        N1_32479 = bundle.getString("N1_32479");
        N2_32479 = bundle.getString("N2_32479");
        N3_32479 = bundle.getString("N3_32479");
        NP_32479 = bundle.getString("NP_32479");
        NF_32479 = bundle.getString("NF_32479");

    }

    public void retornarPantalla(){
        //Creamos el Intent
        Intent intent = new Intent(AcercaDe.this, pantallaNotas.class);
        //Creamos la información a pasar entre actividades
        Bundle b = new Bundle();

        //Nota 1
        b.putString("N1_33496",N1_33496);
        b.putString("N2_33496",N2_33496);
        b.putString("N3_33496",N3_33496);
        b.putString("NP_33496",NP_33496);
        b.putString("NF_33496",NF_33496);

        //Nota 2
        b.putString("N1_32477",N1_32477);
        b.putString("N2_32477",N2_32477);
        b.putString("N3_32477",N3_32477);
        b.putString("NP_32477",NP_32477);
        b.putString("NF_32477",NF_32477);

        //Nota 3
        b.putString("N1_33497",N1_33497);
        b.putString("N2_33497",N2_33497);
        b.putString("N3_33497",N3_33497);
        b.putString("NP_33497",NP_33497);
        b.putString("NF_33497",NF_33497);

        //Nota 4
        b.putString("N1_33615",N1_33615);
        b.putString("N2_33615",N2_33615);
        b.putString("N3_33615",N3_33615);
        b.putString("NP_33615",NP_33615);
        b.putString("NF_33615",NF_33615);

        //Nota 5
        b.putString("N1_29163",N1_29163);
        b.putString("N2_29163",N2_29163);
        b.putString("N3_29163",N3_29163);
        b.putString("NP_29163",NP_29163);
        b.putString("NF_29163",NF_29163);

        //Nota 6
        b.putString("N1_29330",N1_29330);
        b.putString("N2_29330",N2_29330);
        b.putString("N3_29330",N3_29330);
        b.putString("NP_29330",NP_29330);
        b.putString("NF_29330",NF_29330);

        //Nota 7
        b.putString("N1_32479",N1_32479);
        b.putString("N2_32479",N2_32479);
        b.putString("N3_32479",N3_32479);
        b.putString("NP_32479",NP_32479);
        b.putString("NF_32477",NF_32479);

        //Añadimos la información al intent
        intent.putExtras(b);

        //Iniciamos la nueva actividad
        startActivity(intent);

    }
}
