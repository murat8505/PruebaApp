package com.vaw.miguel.calculaimc;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CalculaIMC extends ActionBarActivity {

    private EditText EtPeso,EtMetros;
    private TextView tvResultado;
    private Button btnCalcular;
    private float peso,altura,imc;
    private String clasificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula_imc);

        //Localizar los controles
        tvResultado = (TextView)findViewById(R.id.tvResultado);

        //Obtenemos una referencia de los controles de la interfaz
        EtPeso = (EditText)findViewById(R.id.EtPeso);
        EtMetros = (EditText)findViewById(R.id.EtMetros);
        btnCalcular = (Button)findViewById(R.id.btnCalcular);

        //Implementamos el evento click del botón
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                peso = Float.parseFloat(EtPeso.getText().toString());
                altura = Float.parseFloat(EtMetros.getText().toString());
                imc = peso / (float)Math.pow(altura,2);

                if (imc < 18.5){
                    clasificacion = "Peso Bajo";
                }else if (imc>=18.5 && imc < 25){
                    clasificacion = "Peso Normal";
                }else if (imc>=25 && imc < 30){
                    clasificacion = "Sobrepeso";
                }else {
                    clasificacion = "Obesidad";
                }

                //Construimos el mensaje a mostrar
                tvResultado.setText("Su IMC es de: "+imc+" y se encuentra en el rango de: "+clasificacion );
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calcula_imc, menu);
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
