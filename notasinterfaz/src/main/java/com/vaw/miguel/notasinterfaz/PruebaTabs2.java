package com.vaw.miguel.notasinterfaz;


import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class PruebaTabs2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_tabs2);

//------------------------------------Pestañas--------------------------------------------------------------------//
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new PruebaTabs(
                getSupportFragmentManager(),
                PruebaTabs2.this));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingtabs);

        slidingTabLayout.setDistributeEvenly(true);

        //Establecer el layout de la pestaña
        slidingTabLayout.setCustomTabView(R.layout.tab_view, 0);

        //Establecer el color de los indicadores y divisores
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.primary_material_light);
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.primary_material_light);
            }
        });


        //Asignar el ViewPager al SlidingTabLayout
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prueba_tabs2, menu);
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
