package com.vaw.miguel.notasinterfaz;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;



public class PruebaTabs extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Asesorias", "Chat", "Documentos"};
    private Context context;

    public PruebaTabs(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        switch(position) {
            case 0:
                f = Fragment1.newInstance();
                break;
            case 1:
                f = Fragment2.newInstance();
                break;
            case 2:
                f = Fragment1.newInstance();
                break;
        }

        return f;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
